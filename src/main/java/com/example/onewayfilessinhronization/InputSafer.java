package com.example.onewayfilessinhronization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

abstract public class InputSafer {
    public static HashMap<String, String> getLastInput() {
        HashMap<String, String> result = new HashMap<>();
        result.put("donor", null);
        result.put("recipient", null);
        if (!Files.exists(Path.of("input.csv")))
            return result;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("input.csv"));
            result.put("donor", fileReader.readLine());
            result.put("recipient", fileReader.readLine());
        } catch (IOException e) {
            return result;
        }
        return result;
    }
    public static void safeInput(String donorLink, String recipientLink) {
        try {
            Files.write(Path.of("input.csv"), (donorLink+"\n"+recipientLink+"\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
