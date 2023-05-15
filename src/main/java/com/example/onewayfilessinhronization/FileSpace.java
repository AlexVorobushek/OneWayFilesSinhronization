package com.example.onewayfilessinhronization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

abstract public class FileSpace {
    protected Path baseDirectory;
    protected ArrayList<Path> ExistingFilesLocalPaths;


    public FileSpace(Path baseDirectory) {
        this.baseDirectory = baseDirectory;
        updateExistingFilesLocalPaths();
    }

    abstract protected void updateExistingFilesLocalPaths();
    abstract public ArrayList<Path> getExistingFilesLocalPaths();
    abstract public Path getFilePathToRead(Path filePath);
    abstract public Path getFilePathToWrite(Path filePath);
    public FileTime getFileTime(Path filePath) throws IOException {
        Path readFileDirectory = getFilePathToRead(filePath);
        if (Files.exists(readFileDirectory))
            return Files.getLastModifiedTime(readFileDirectory);
        return null;
    }

    abstract public void delFile(Path filePath) throws IOException;
    abstract public void pushFileInto(Path filePath, Path directoryFrom) throws IOException;
}
