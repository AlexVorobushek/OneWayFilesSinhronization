package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.fileSnapshots.Directory.Directory;
import com.example.onewayfilessinhronization.fileSnapshots.Directory.DirectoryRecipient;
import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;
import com.example.onewayfilessinhronization.fileSnapshots.VC.VCController;
import com.example.onewayfilessinhronization.fileSnapshots.VC.VCRecipient;
import com.example.onewayfilessinhronization.fileSnapshots.VC.VCSnapshot;
import com.example.onewayfilessinhronization.exceptions.NotCorrectLinkException;
import com.example.onewayfilessinhronization.exceptions.VCExceptions.VCNotCorrectLinkExceptions.NoSuchCommit;
import com.example.onewayfilessinhronization.exceptions.VCExceptions.VCNotCorrectLinkExceptions.VCNotInit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public interface InitFileSnapshot {
    static FileSnapshot initDonor(String link) throws NotCorrectLinkException, IOException {
        Path basePath = Path.of(link.split(">")[0]);
        if (!Files.isDirectory(basePath)) throw new NotCorrectLinkException(basePath.toString());

        if (Pattern.matches(".+>VC>.+", link)) {
            if (!VCController.isVCInit(basePath)) throw new VCNotInit(link);
            if (!VCController.isCommitExist(basePath, link.split(">")[2])) throw new NoSuchCommit(link);
            return new VCSnapshot(basePath, link.split(">")[2]);
        }
        if (Pattern.matches(".+>VC", link)) {
            if (!VCController.isVCInit(basePath)) throw new VCNotInit(link);
            return new VCSnapshot(basePath);
        }
        return new Directory(basePath);
    }

    static FileSnapshot initRecipient(String link) throws NotCorrectLinkException, IOException {
        Path basePath = Path.of(link.split(">")[0]);
        if (!Files.isDirectory(basePath)) Files.createDirectory(basePath);

        if (Pattern.matches(".+>VC>.+", link)) {
            if (!VCController.isVCInit(basePath)) throw new VCNotInit(link);
            if (!VCController.isCommitExist(basePath, link.split(">")[2])) throw new NoSuchCommit(link.split(">")[2]);
            return new VCRecipient(basePath, link.split(">")[2]);
        }
        if (Pattern.matches(".+>VC", link)) {
//            if (!VCController.isVCInit(basePath)) VCController.initVC(basePath);
            return new VCRecipient(basePath);
        }
        return new DirectoryRecipient(basePath);
    }
}
