package com.jameshskoh.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {

    FileHelper fh;

    @BeforeEach
    void init() {
        fh = new FileHelper();
    }

    @ParameterizedTest
    @ValueSource(strings = {".pdf", "a.pdf", "a.PDF", "a.Pdf", "123abc-0_1.pdf"})
    void isPdfFile_shouldReturnTrue(String fileName) {
        File file = new File(fileName);
        assertTrue(fh.isPdfFile(file));
    }

    @ParameterizedTest
    @ValueSource(strings = {"pdf", "PDF", "aPDF", "Apdf", "a.PDX", "a.pd1f", "123abc-0_1.txt"})
    void isPdfFile_shouldReturnFalse(String fileName) {
        File file = new File(fileName);
        assertFalse(fh.isPdfFile(file));
    }
}