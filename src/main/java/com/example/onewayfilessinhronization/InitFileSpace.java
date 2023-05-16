package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.NotCorrectLinkException;
import com.example.onewayfilessinhronization.fileSpases.Directory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface InitFileSpace {
    static FileSpace init(String link) throws NotCorrectLinkException {
        Path path = Path.of(link);
        if (Files.isDirectory(path))
            return new Directory(path);
        throw new NotCorrectLinkException(link);
    };
}
