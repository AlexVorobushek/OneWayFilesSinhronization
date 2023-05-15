package com.example.onewayfilessinhronization.fileSpases;

import com.example.onewayfilessinhronization.FileSpace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Directory extends FileSpace {
    public Directory(Path baseDirectory) {
        super(baseDirectory);
    }

    @Override
    protected void updateExistingFilesLocalPaths() {
        ExistingFilesLocalPaths = recursiveFileSearch(this.baseDirectory);
    }

    @Override
    public ArrayList<Path> getExistingFilesLocalPaths() {
        return ExistingFilesLocalPaths;
    }

    @Override
    public Path getFilePathToRead(Path filePath) {
        return Path.of(baseDirectory.toString(), filePath.toString());
    }

    @Override
    public Path getFilePathToWrite(Path filePath) {
        return Path.of(baseDirectory.toString(), filePath.toString());
    }

    @Override
    public void delFile(Path filePath) throws IOException {
        Files.delete(getFilePathToRead(filePath));
    }

    @Override
    public void pushFileInto(Path filePath, Path directoryFrom) throws IOException {
        Path directoryTo = getFilePathToWrite(filePath);
        if (Files.notExists(directoryTo)) {
            if (Files.notExists(directoryTo.getParent()))
                Files.createDirectory(directoryTo.getParent());
            Files.createFile(directoryTo);
        }
        Files.copy(directoryFrom, directoryTo, StandardCopyOption.REPLACE_EXISTING);
    }

    private ArrayList<Path> recursiveFileSearch(Path directory) {
        ArrayList<Path> listWithFileNames = new ArrayList<>();
        File f = new File(directory.toString());
        for (File file: f.listFiles()) {
            Path filePath = Paths.get(file.getAbsolutePath());
            if (file.isFile())
                listWithFileNames.add(baseDirectory.relativize(filePath));
            else if (file.isDirectory())
                listWithFileNames.addAll(recursiveFileSearch(filePath));
        }
        return listWithFileNames;
    }


}
