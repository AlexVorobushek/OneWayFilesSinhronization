package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.CanNotPushException;
import com.example.onewayfilessinhronization.exceptions.NoDiskSpaceException;
import com.example.onewayfilessinhronization.fileSpases.Directory;
import javafx.scene.effect.Light;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Pusher {
    FileSpace donor;
    FileSpace recipient;
    public Pusher(FileSpace donor, FileSpace recipient) {
        this.donor = donor;
        this.recipient = recipient;
    }

    public Pusher(String donorLink, String recipientLink) throws CanNotPushException {
        donor = InitFileSpace.init(donorLink);
        recipient = InitFileSpace.init(recipientLink);
        if (recipient.getClass() == Directory.class & !((Directory) recipient).isFreeDiskEnoughToPush(donor))
            throw new NoDiskSpaceException();
    }

    public void push() throws IOException {
        ArrayList<Path> donorExistFiles = donor.getExistingFilesLocalPaths();
        ArrayList<Path> recipientExistFiles = recipient.getExistingFilesLocalPaths();

        for (Path filePath : recipientExistFiles)
            if (donor.getFileTime(filePath) == null) recipient.delFile(filePath);
        for (Path filePath : donorExistFiles)
            if (!donor.getFileTime(filePath).equals(recipient.getFileTime(filePath)))
                recipient.pushFileInto(filePath, donor.getFilePathToRead(filePath));
    }
}
