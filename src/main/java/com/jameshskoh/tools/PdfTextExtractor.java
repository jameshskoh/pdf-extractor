package com.jameshskoh.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfTextExtractor {

    private final PDFTextStripper ts;

    public PdfTextExtractor() {
        try {
            ts = new PDFTextStripper();
        } catch (IOException e) {
            System.out.println("Fatal error occurred. PDFTextStripper cannot be initialized.");
            throw new RuntimeException(e);
        }
    }

    public String extractText(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        return ts.getText(doc);
    }
}
