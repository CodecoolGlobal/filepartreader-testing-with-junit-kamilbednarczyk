package com.kamil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileWordAnalyserTest {

    @Mock
    private FilePartReader fpr;

    private FileWordAnalyser fwa;

    @BeforeEach
    void setup() {
        fpr = mock(FilePartReader.class);
        fwa = new FileWordAnalyser(fpr);
    }

    @Test
    void getWordsOrderedAlphabetically_ReturnsOrderedWords_IfWordsPresent() throws IOException {
        when(fpr.readLines()).thenReturn("za sa hb\nha hc aa ab");

        List<String> expected = new ArrayList<>(Arrays.asList("aa", "ab", "ha", "hb", "hc", "sa", "za"));
        assertEquals(expected, fwa.getWordsOrderedAlphabetically());
    }

    @Test
    void getWordsOrderedAlphabetically_ReturnsEmptyList_IfWordsNotPresent() throws IOException {
        when(fpr.readLines()).thenReturn("");

        assertTrue(fwa.getWordsOrderedAlphabetically().isEmpty());
    }

    @Test
    void getWordsContainingSubstring_ReturnsMatches_IfWordsWithSubstringPresent() throws IOException {
        when(fpr.readLines()).thenReturn("tora tora tora gommora kaka raka otaka hogomo oragon oragon");

        List<String> expected = new ArrayList<>(Arrays.asList("tora", "gommora", "oragon"));
        assertEquals(expected, fwa.getWordsContainingSubstring("ora"));
    }

    @Test
    void getWordsContainingSubstring_ReturnsEmptyList_IfWordsWithSubstringNotPresent() throws IOException {
        when(fpr.readLines()).thenReturn("kaka raka otaka hogomo");

        assertTrue(fwa.getWordsContainingSubstring("ora").isEmpty());
    }

    @Test
    void getStringsWhichPalindromes_ReturnsMatches_IfPalindromesOccur() throws IOException {
        when(fpr.readLines()).thenReturn("oraaro aaa kara moro otr a aka");

        List<String> expected = new ArrayList<>(Arrays.asList("oraaro", "aaa", "a", "aka"));
        assertEquals(expected, fwa.getStringsWhichPalindromes());
    }

    @Test
    void getStringsWhichPalindromes_ReturnsEmptyList_IfPalindromesNotFound() throws IOException {
        when(fpr.readLines()).thenReturn("kara moro otr");

        assertTrue(fwa.getStringsWhichPalindromes().isEmpty());
    }

}