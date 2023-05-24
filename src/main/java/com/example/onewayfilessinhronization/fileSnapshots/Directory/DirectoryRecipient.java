package com.example.onewayfilessinhronization.fileSnapshots.Directory;

import com.example.onewayfilessinhronization.fileSnapshots.Recipient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DirectoryRecipient extends Directory implements Recipient {
    public DirectoryRecipient(Path basePath) {
        super(basePath);
    }

    @Override
    public void pushFileInto(Path fileLink, Path donorRealFilePath) throws IOException {
        Path directoryTo = getRealFilePathToRead(fileLink);
        if (Files.notExists(directoryTo)) {
            if (Files.notExists(directoryTo.getParent()))
                Files.createDirectory(directoryTo.getParent());
            Files.createFile(directoryTo);
        }
        Files.copy(donorRealFilePath, directoryTo, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void delFile(Path fileLink) throws IOException {
        Files.delete(getRealFilePathToRead(fileLink));
    }
}
