package com.jameshskoh;

import com.jameshskoh.tools.KeywordExtractor;
import com.jameshskoh.tools.TextStatisticsGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    private static final TextStatisticsGenerator gen = new TextStatisticsGenerator();

    public static void main(String[] args) {

        String keywordFilePath = "keyword";
        String pdfFolderPath = "pdf";
        String outputFilePath = "output";

        printWelcomeMessage(keywordFilePath, pdfFolderPath, outputFilePath);

        List<String> keywords = getKeywords(keywordFilePath);

        File[] pdfs = getPdfHandles(pdfFolderPath);

        Map<String, Map<String, Integer>> results = gen.generateStatistics(pdfs, keywords, true);

        printResults(results);
    }

    private static void printWelcomeMessage(String keywordFilePath, String pdfFolderPath, String outputFilePath) {
        System.out.println("Welcome!");
        System.out.println("Configurations: ");
        System.out.println("Keyword file path\t: " + keywordFilePath);
        System.out.println("PDF folder path\t: " + pdfFolderPath);
        System.out.println("Output file path\t: " + outputFilePath);
        System.out.println();
    }

    private static List<String> getKeywords(String keywordFilePath) {
        File keywordFile = new File(keywordFilePath);
        List<String> keywords;

        try {
            keywords = new KeywordExtractor().extractKeywords(keywordFile);
            return keywords;
        } catch (IOException exc) {
            System.out.println("Unable to load keyword file.");
            exc.printStackTrace();
            System.out.println("Exiting program.");
            System.exit(1);
        }

        // unreachable code
        throw new RuntimeException("Reached unreachable code!");
    }

    private static File[] getPdfHandles(String pdfFolderPath) {
        File pdfFolder = new File(pdfFolderPath);
        File[] pdfs = pdfFolder.listFiles();

        if (pdfs == null) throw new RuntimeException("PDF file path is not acceptable. Please pass in a folder path that contains PDF files.");

        return pdfs;
    }

    private static void printResults(Map<String, Map<String, Integer>> results) {
        for (String pdfKey : results.keySet()) {
            System.out.println("PDF file: " + pdfKey);

            var result = results.get(pdfKey);

            for (String keyword : result.keySet()) {
                System.out.println(keyword + "\t: " + result.get(keyword));
            }

            System.out.println();
        }
    }
}
