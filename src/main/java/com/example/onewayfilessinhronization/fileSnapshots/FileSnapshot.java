package com.example.onewayfilessinhronization.fileSnapshots;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Set;

public abstract class FileSnapshot {
    protected Path basePath;

    public FileSnapshot(Path basePath) {
        this.basePath = basePath;
    }

    public abstract Set<Path> getExistingFileLinks() throws IOException;

    public abstract Path getRealFilePathToRead(Path link);
    public FileTime getModifiedTime(Path link) throws IOException {
        Path readFilePath = getRealFilePathToRead(link);
        if (readFilePath == null) return null;
        if (Files.exists(readFilePath)) return Files.getLastModifiedTime(readFilePath);
        return null;
    }

}
