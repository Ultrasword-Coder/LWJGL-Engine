package engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileIO {

    public static String getFileContents(String filePath) {
        String line;
        StringBuilder result = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(FileIO.class.getResourceAsStream((filePath.startsWith("/") ? "" : "/") +filePath)))) {
            while ((line = reader.readLine()) != null)
                result.append(line + "\n");
        } catch (IOException e) {
            System.err.format("[FileIO| getFileContents] Could not find file at '%s'. Aborting program\n", filePath);
            assert false : "Failed to open file!";
        }
        return result.toString();
    }

}
