package com.kamil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileWordAnalyser {

    private FilePartReader filePartReader;

    public FileWordAnalyser(FilePartReader filePartReader) {
        this.filePartReader = filePartReader;
    }

    public List<String> getWordsOrderedAlphabetically() throws IOException {
        List<String> words = getWords();
        Collections.sort(words);
        return words;
    }

    public List<String> getWordsContainingSubstring(String subString) throws IOException {
        String subStringLow = subString.toLowerCase();
        return getFilteredWords(word -> word.contains(subStringLow));
    }

    public List<String> getStringsWhichPalindromes() throws IOException {
        return getFilteredWords(word -> {
            String firstPart = word.substring(0, word.length() / 2);
            String secondPart = new StringBuilder(word.substring(word.length() / 2 + word.length() % 2))
                    .reverse()
                    .toString();
            return firstPart.equals(secondPart);
        });
    }

    private List<String> getFilteredWords(Predicate<String> predicate) throws IOException {
        return getWords().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private List<String> getWords() throws IOException {
        return Stream.of(filePartReader.readLines()
                .toLowerCase()
                .replaceAll("[;:.,!?]", "")
                .split("\\s|\n")).distinct().filter(w -> !w.isEmpty()).collect(Collectors.toList());
    }
}
