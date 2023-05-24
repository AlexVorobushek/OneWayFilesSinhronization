package com.example.onewayfilessinhronization.fileSnapshots;

import java.io.IOException;
import java.nio.file.Path;

public interface Recipient {
    public void pushFileInto(Path fileLink, Path donorRealFilePath) throws IOException;
    public void delFile(Path fileLink) throws IOException;
}
