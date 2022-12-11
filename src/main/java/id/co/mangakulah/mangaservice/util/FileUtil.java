package id.co.mangakulah.mangaservice.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public static void writeToFile(String baseDir, String fileName, String content) throws IOException {
        String path = baseDir+fileName;
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path), charset)) {
            writer.write(content, 0, content.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
