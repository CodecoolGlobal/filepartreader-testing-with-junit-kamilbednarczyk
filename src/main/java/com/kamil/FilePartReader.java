package com.kamil;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FilePartReader {

    private String filePath;
    private Integer fromLine;
    private Integer toLine;

    public void setup(String filePath, Integer fromLine, Integer toLine) throws IllegalArgumentException {
        if (toLine < fromLine || fromLine < 0) {
            throw new IllegalArgumentException();
        }
        this.filePath = filePath;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    public String read() throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        Scanner scanner = new Scanner(Files.newBufferedReader(path));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }
        return sb.toString();
    }

    public String readLines() throws IOException {
        String content = read();
        int lineNum = fromLine;
        StringBuilder sb = new StringBuilder();
        for (String line : content.split("\n")) {
            sb.append(line);
            if (++lineNum > toLine) {
                break;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
