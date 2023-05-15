package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.NotCorrectLinkException;

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

    public Pusher(String donorLink, String recipientLink) throws NotCorrectLinkException {
        donor = InitFileSpace.init(donorLink);
        recipient = InitFileSpace.init(recipientLink);
    }

    public void push() throws IOException {
        ArrayList<Path> donorExistFiles = donor.getExistingFilesLocalPaths();
        ArrayList<Path> recipientExistFiles = recipient.getExistingFilesLocalPaths();

        for (Path filePath : donorExistFiles)
            if (!donor.getFileTime(filePath).equals(recipient.getFileTime(filePath)))
                recipient.pushFileInto(filePath, donor.getFilePathToRead(filePath));
        for (Path filePath : recipientExistFiles)
            if (donor.getFileTime(filePath) == null) recipient.delFile(filePath);
    }
}
