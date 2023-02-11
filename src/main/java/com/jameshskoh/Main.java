package com.jameshskoh;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String extractedText = "";
        ClassLoader classLoader = Main.class.getClassLoader();

        try {
            File file = new File(classLoader.getResource("pdfs/file1.pdf").getFile());
            PDDocument doc = PDDocument.load(file);
            extractedText = new PDFTextStripper().getText(doc);
            // System.out.println(extractedText);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        try {
            File parentDir = new File(new URI(classLoader.getResource("").toString()));
            File file = new File(parentDir, "output.txt");
            System.out.println(parentDir);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(extractedText);

            writer.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        List<String> keywords = new ArrayList<>(Arrays.asList("malaria", "futuristic", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope", "kaleidoscope"));
        List<Boolean> results = new ArrayList<>();

        for (String keyword : keywords) {
            if (extractedText.contains(keyword)) results.add(true);
            else results.add(false);
        }

        for (boolean result : results) System.out.println(result);
    }
}
