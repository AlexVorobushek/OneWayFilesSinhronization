package com.example.onewayfilessinhronization.fileSnapshots.VC;

import com.example.onewayfilessinhronization.fileSnapshots.Recipient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class VCRecipient extends VCSnapshot implements Recipient {
    public VCRecipient(Path basePath) throws IOException {
        super(basePath, VCController.initNewCommit(basePath, VCController.getLastCommitHash(basePath)));
    }

    public VCRecipient(Path basePath, String commitHash) throws IOException {
        super(basePath, VCController.initNewCommit(basePath, commitHash));
    }

    @Override
    public void pushFileInto(Path fileLink, Path donorRealFilePath) throws IOException {
        Path PathToWrite = Path.of(basePath+((fileLink.getParent()!=null)?"/"+fileLink.getParent():"")+"/"+commitHash+fileLink.getFileName());
        if (Files.notExists(PathToWrite.getParent())) Files.createDirectory(PathToWrite.getParent());
        Files.copy(donorRealFilePath, PathToWrite);
        addChangeToCommitLog("push", fileLink);
    }

    @Override
    public void delFile(Path fileLink) throws IOException {
        addChangeToCommitLog("del", fileLink);
    }

    private void addChangeToCommitLog(String changeType, Path fileLink) throws IOException {
        Path commitFile = Path.of(basePath+"/.VC/"+commitHash + ".txt");
        Files.write(commitFile, (changeType+":"+fileLink+"\n").getBytes(), StandardOpenOption.APPEND);
    }

}
