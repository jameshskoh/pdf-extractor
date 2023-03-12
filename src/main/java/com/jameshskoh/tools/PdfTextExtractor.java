package com.jameshskoh.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;

public class PdfTextExtractor {

    final PDFTextStripper ts;
    final FileHelper fh;

    public PdfTextExtractor(PDFTextStripper ts, FileHelper fh) {
        this.ts = ts;
        this.fh = fh;
    }

    public Pair<String, String> createTextMap(File pdf, boolean caseSensitive) {
        try (PDDocument doc = fh.getPDDoc(pdf)) {
            if (caseSensitive)
                return new Pair<>(pdf.getName(), ts.getText(doc));
            else
                return new Pair<>(pdf.getName(), ts.getText(doc).toLowerCase());
        } catch (IOException e) {
            System.out.println("Text extraction failed: " + pdf.getName());
            System.out.println("Returns null.");
            return new Pair<>(pdf.getName(), null);
        }
    }
}
