package com.example.onewayfilessinhronization.fileSnapshots.Directory;

import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Directory extends FileSnapshot {
    public Directory(Path basePath) {
        super(basePath);
    }

    @Override
    public Set<Path> getExistingFileLinks() {
        return recursiveFileLinkSearch(basePath);
    }

    @Override
    public Path getRealFilePathToRead(Path link) {
        return Path.of(basePath.toString(), link.toString());
    }


    private Set<Path> recursiveFileLinkSearch(Path directory) {
        HashSet<Path> listWithFileNames = new HashSet<>();
        File f = new File(directory.toString());
        for (File file: Objects.requireNonNull(f.listFiles())) {
            Path filePath = Paths.get(file.getAbsolutePath());
            if (file.isFile())
                listWithFileNames.add(basePath.relativize(filePath));
            else if (file.isDirectory())
                listWithFileNames.addAll(recursiveFileLinkSearch(filePath));
        }
        return listWithFileNames;
    }
}
