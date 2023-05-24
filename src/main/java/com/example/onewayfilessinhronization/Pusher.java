package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;
import com.example.onewayfilessinhronization.fileSnapshots.Recipient;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

public interface Pusher {
    static void push(FileSnapshot donor, FileSnapshot recipient) throws IOException {
        Set<Path> donorExistFiles = donor.getExistingFileLinks();
        Set<Path> recipientExistFiles = recipient.getExistingFileLinks();

        for (Path fileLink : recipientExistFiles)
            if (!donorExistFiles.contains(fileLink)) ((Recipient)recipient).delFile(fileLink);
        for (Path fileLink : donorExistFiles)
            if (!recipientExistFiles.contains(fileLink) | !donor.getModifiedTime(fileLink).equals(recipient.getModifiedTime(fileLink)))
                ((Recipient) recipient).pushFileInto(fileLink, donor.getRealFilePathToRead(fileLink));
    }
}
