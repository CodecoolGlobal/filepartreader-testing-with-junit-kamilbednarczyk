package com.kamil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FilePartReader {

    private String filePath;
    private Integer fromLine;
    private Integer toLine;

    public void setup(String filePath, Integer fromLine, Integer toLine) throws IllegalArgumentException {
        if (toLine < fromLine || fromLine < 1) {
            throw new IllegalArgumentException();
        }
        this.filePath = filePath;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    public String read() throws IOException {
        Path path = Paths.get(filePath);
        Scanner scanner = new Scanner(Files.newBufferedReader(path));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public String readLines() throws IOException {
        String[] content = read().split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = fromLine - 1; i < toLine && i < content.length; i++) {
            sb.append(content[i]);
            sb.append("\n");
        }
        return sb.toString().trim();
    }
}
