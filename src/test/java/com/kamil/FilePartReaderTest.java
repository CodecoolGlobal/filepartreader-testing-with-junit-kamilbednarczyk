package com.kamil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilePartReaderTest {

    private FilePartReader fpr;

    @BeforeEach
    void setup() {
        fpr = new FilePartReader();
    }

    @Test
    void setup_ThrowsException_IfFromLineIsGreaterThanToLine() {
        assertThrows(IllegalArgumentException.class, () -> fpr.setup("file", 3, 1));
    }

    @Test
    void setup_ThrowsException_IfFromLineIsLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> fpr.setup("file", 0, 1));
    }

    @Test
    void read_ThrowsException_IfFileNotExist() {
        fpr.setup("target/classes/notExist", 1, 1);

        assertThrows(IOException.class, () -> fpr.read());
    }

    @Test
    void read_ReturnsEmptyString_IfFileEmpty() throws IOException {
        fpr.setup("target/classes/testEmpty.txt", 1, 1);

        assertEquals("", fpr.read());
    }

    @ParameterizedTest
    @MethodSource
    void read_ReturnsAllLines_IfFileExists(String expected, int fromLine, int toLine) throws IOException {
        fpr.setup("target/classes/testText.txt", fromLine, toLine);

        assertEquals(expected, fpr.read());
    }

    static Stream<Arguments> read_ReturnsAllLines_IfFileExists() {
        String expected = "O hello there. General Kenobi!\n" +
                "Oh my, on my, who do we have here.\n" +
                "Say my name.\n" +
                "Home is behind, world ahead and there are many paths to tread." +
                " Through shadow to the edge of nigh, until the stars\n" +
                "are all alight. Mist and shadow, cloud and shade. All shall fade. All shall fade.";
        return Stream.of(
                Arguments.arguments(expected, 1, 5)
        );
    }

    @Test
    void readLines_ParsesOneLine_IfFromLineEqualsToLine() throws IOException {
        fpr.setup("target/classes/testText.txt", 1, 1);

        assertEquals("O hello there. General Kenobi!", fpr.readLines());
    }

    @Test
    void readLines_ParsesEmptyString_IfNoLinesBetweenFromLineAndToLine() throws IOException {
        fpr.setup("target/classes/testText.txt", 20, 21);

        assertEquals("", fpr.readLines());
    }

    @ParameterizedTest
    @MethodSource
    void readLines_ParsesLines_IfLinesPresentFromLineAndToLine(String expected,
                                                               int fromLine,
                                                               int toLine) throws IOException {
        fpr.setup("target/classes/testText.txt", fromLine, toLine);

        assertEquals(expected, fpr.readLines());
    }

    static Stream<Arguments> readLines_ParsesLines_IfLinesPresentFromLineAndToLine() {
        return Stream.of(
                Arguments.arguments("O hello there. General Kenobi!\n" +
                        "Oh my, on my, who do we have here.", 1, 2),
                Arguments.arguments("O hello there. General Kenobi!\n" +
                        "Oh my, on my, who do we have here.\n" +
                        "Say my name.\n" +
                        "Home is behind, world ahead and there are many paths to tread. Through shadow to the edge of nigh, until the stars\n" +
                        "are all alight. Mist and shadow, cloud and shade. All shall fade. All shall fade.", 1, 5),
                Arguments.arguments("Say my name.\n" +
                        "Home is behind, world ahead and there are many paths to tread. Through shadow to the edge of nigh, until the stars\n" +
                        "are all alight. Mist and shadow, cloud and shade. All shall fade. All shall fade.", 3, 20)

        );
    }
}