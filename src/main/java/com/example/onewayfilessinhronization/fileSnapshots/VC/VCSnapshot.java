package com.example.onewayfilessinhronization.fileSnapshots.VC;

import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class VCSnapshot extends FileSnapshot {
    protected String commitHash;
    protected String previousCommitHash;
    private HashMap<Path, String> existingLinkToLastChangedCommit;

    public VCSnapshot(Path basePath) throws IOException {
        super(basePath);
        commitHash = VCController.getLastCommitHash(basePath);
        previousCommitHash = getPreviousCommitHash();
        fillExistingLinkToLastChangedCommit();
    }

    protected void fillExistingLinkToLastChangedCommit() throws IOException {
        HashMap<Path, String> result = (previousCommitHash != null) ?
                new VCSnapshot(basePath, previousCommitHash).existingLinkToLastChangedCommit :
                new HashMap<>();
        HashMap<String, ArrayList<Path>> commitChanges = getCommitChanges();
        for (Path link: commitChanges.get("del")) result.remove(link);
        for (Path link: commitChanges.get("push")) result.put(link, commitHash);
        existingLinkToLastChangedCommit = result;
    }

    public VCSnapshot(Path basePath, String commitHash) throws IOException {
        super(basePath);
        this.commitHash = commitHash;
        previousCommitHash = getPreviousCommitHash();
        fillExistingLinkToLastChangedCommit();
    }

    @Override
    public Set<Path> getExistingFileLinks() {
        return existingLinkToLastChangedCommit.keySet();
    }

    @Override
    public Path getRealFilePathToRead(Path link) {
        String commitHashWhichLastChangeFile = existingLinkToLastChangedCommit.get(link);
        if (commitHashWhichLastChangeFile == null) return null;
        if (commitHashWhichLastChangeFile.equals("main")) return Path.of(basePath+"/"+link);
        return Path.of(basePath+"/"+((link.getParent()!=null)?"/"+link.getParent():"")+"/"+commitHashWhichLastChangeFile+link.getFileName());
    }

    private String getPreviousCommitHash() throws IOException {
        if (Objects.equals(commitHash, "main")) return null;
        BufferedReader fileReader = new BufferedReader(
                new FileReader(basePath.toString() + "/.VC/" + commitHash + ".txt")
        );
        return fileReader.readLine();
    }

    private HashMap<String, ArrayList<Path>> getCommitChanges() throws IOException {
        HashMap<String, ArrayList<Path>> result = new HashMap<>();
        result.put("push", new ArrayList<>());
        result.put("del", new ArrayList<>());

        BufferedReader fileReader = new BufferedReader(new FileReader(basePath.toString() + "/.VC/" + commitHash + ".txt"));
        for (String line; (line = fileReader.readLine()) != null; ) {
            if (line.split(":")[0].equals("push")) result.get("push").add(Path.of(line.split(":")[1]));
            if (line.split(":")[0].equals("del")) result.get("del").add(Path.of(line.split(":")[1]));
        }
        return result;
    }
}
