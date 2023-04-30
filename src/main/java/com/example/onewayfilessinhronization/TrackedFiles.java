package com.example.onewayfilessinhronization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.BasicFileAttributes;

public class TrackedFiles {
    private HashMap<Path, FileTime> fileLocalPathToModifiedTime = new HashMap<Path, FileTime>();
    private String base_directory;
    public TrackedFiles(String base_directory) throws IOException {
        this.base_directory = base_directory;
        for (String path: getFilesList(base_directory)){
            fileLocalPathToModifiedTime.put(
                    Paths.get(base_directory).relativize(Paths.get(path)),
                    Files.readAttributes(Paths.get(path), BasicFileAttributes.class).lastModifiedTime()
            );
        }
    }


    public static ArrayList<String> getFilesList(String directory) {
        ArrayList<String> listWithFileNames = new ArrayList<>();
        File f = new File(directory);
        for (File s : f.listFiles()) {
            if (s.isFile()) {
                listWithFileNames.add(s.getAbsolutePath());
            } else if (s.isDirectory()) {
                listWithFileNames.addAll(getFilesList(s.getAbsolutePath()));
            }
        }
        return listWithFileNames;
    }

    public void pushTo(String directory) throws IOException {
        TrackedFiles hostDirectoryFiles = new TrackedFiles(directory);
        for (Path path : this.fileLocalPathToModifiedTime.keySet()){
            FileTime timeInHostDirectory = hostDirectoryFiles.fileLocalPathToModifiedTime.get(path);
            if (timeInHostDirectory == null) {
                System.out.println("write "+path);
                Files.copy(Paths.get(this.base_directory, path.toString()), Paths.get(hostDirectoryFiles.base_directory, path.toString()));
            } else if (!timeInHostDirectory.equals(this.fileLocalPathToModifiedTime.get(path))) {
                System.out.println("rewrite "+path);
                new File(Paths.get(hostDirectoryFiles.base_directory, path.toString()).toString()).delete();
                Files.copy(Paths.get(this.base_directory, path.toString()), Paths.get(hostDirectoryFiles.base_directory, path.toString()));
            }
//            this.fileLocalPathToModifiedTime.remove(path);
//            hostDirectoryFiles.fileLocalPathToModifiedTime.remove(path);
        }
        for (Path path : hostDirectoryFiles.fileLocalPathToModifiedTime.keySet()){
            if (this.fileLocalPathToModifiedTime.get(path) == null) {
                System.out.println("delete "+path);
                new File(Paths.get(hostDirectoryFiles.base_directory, path.toString()).toString()).delete();
            }
        }
    }

}
