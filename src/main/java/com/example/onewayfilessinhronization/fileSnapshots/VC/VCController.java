package com.example.onewayfilessinhronization.fileSnapshots.VC;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

abstract public class VCController {
    public static boolean isVCInit(Path basePath){
        return Files.isDirectory(Path.of(basePath+"/.VC"));
    }
    static String initNewCommit(Path basePath, String previousCommitHash) throws IOException { // return new commit hash
        String newCommitHash;
        Path commitFile;
        if (previousCommitHash == null) {
            newCommitHash = "main";
            commitFile = Path.of(basePath + "/.VC/" + newCommitHash + ".txt");
            Files.createDirectory(commitFile.getParent());
            Files.createFile(commitFile);
            return newCommitHash;
        }
        newCommitHash = String.valueOf(new Date().getTime());
        commitFile = Path.of(basePath+"/.VC/"+newCommitHash+".txt");
        Files.createFile(commitFile);
        Files.write(commitFile, (previousCommitHash+"\n").getBytes());
        return newCommitHash;
    }

    static String getLastCommitHash(Path basePath) throws IOException {
        Path resultCommitFile = Path.of(basePath+"/.VC/main.txt");
        File[] files = new File(resultCommitFile.getParent().toUri()).listFiles();
        if (files == null) return null;
        for (File file: files)
            if (Files.getLastModifiedTime(resultCommitFile).toMillis() < Files.getLastModifiedTime(file.toPath()).toMillis())
                resultCommitFile = file.toPath();
        return resultCommitFile.getFileName().toString().split("\\.")[0];
    }

    private static ArrayList<Path> recursiveFileLinkSearch(Path basePath, Path directory) {
        ArrayList<Path> listWithFileNames = new ArrayList<>();
        File f = new File(directory.toString());
        for (File file: Objects.requireNonNull(f.listFiles())) {
            Path filePath = Paths.get(file.getAbsolutePath());
            if (file.isFile())
                listWithFileNames.add(basePath.relativize(filePath));
            else if (file.isDirectory())
                listWithFileNames.addAll(recursiveFileLinkSearch(basePath, filePath));
        }
        return listWithFileNames;
    }

    public static boolean isCommitExist(Path basePath, String commitHash){
        return Files.exists(Path.of(basePath+"/.VC/"+commitHash+".txt"));
    }
}
