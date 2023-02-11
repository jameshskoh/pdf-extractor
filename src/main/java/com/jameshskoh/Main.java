package com.jameshskoh;

import com.jameshskoh.tools.KeywordExtractor;
import com.jameshskoh.tools.PdfTextExtractor;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    static final String PDF_SUFFIX = ".pdf";
    static final PdfTextExtractor extractor = new PdfTextExtractor();

    public static void main(String[] args) {

        String keywordFilePath = "keyword";
        String pdfFolderPath = "pdf";
        String outputFilePath = "output";

        printWelcomeMessage(keywordFilePath, pdfFolderPath, outputFilePath);

        List<String> keywords = getKeywords(keywordFilePath);

        var pdfTextStream = getPdfHandles(pdfFolderPath)
                .stream()
                .filter(Main::fileNameIsPdf)
                .map(Main::extractText)
                .collect(
                        Collectors.toMap(
                                Pair::getValue0,
                                pair -> processStatistics(pair.getValue1(), keywords)
                        )
                );


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

    private static List<File> getPdfHandles(String pdfFolderPath) {
        File pdfFolder = new File(pdfFolderPath);
        File[] pdfs = pdfFolder.listFiles();

        if (pdfs == null) throw new RuntimeException("PDF file path is not acceptable. Please pass in a folder path that contains PDF files.");

        return Arrays.asList(pdfs);
    }

    private static boolean fileNameIsPdf(File pdf) {
        if (!pdf.getName().toLowerCase().endsWith(PDF_SUFFIX)) {
            System.out.println(pdf.getName() + " skipped: file name did not end with .pdf\n");
            return false;
        }

        return true;
    }

    private static Pair<String, String> extractText(File pdf) {
        System.out.println(pdf.getName() + " found: extracting texts\n");

        try {
            return new Pair<>(pdf.getName() ,extractor.extractText(pdf));
        } catch(IOException exc) {
            System.out.println(pdf.getName() + " skipped: extraction failed.");
            exc.printStackTrace();
            return new Pair<>(pdf.getName(), null);
        }
    }

    private static Map<String, Integer> processStatistics(String text, List<String> keywords) {
        if (text == null) return null;
        return getKeywordFrequencies(text, keywords);
    }

    private static Map<String, Integer> getKeywordFrequencies(String text, List<String> keywords) {
        return keywords.stream()
                .collect(Collectors.toMap(
                                keyword -> keyword,
                                keyword -> getKeywordFrequency(text, keyword)));
    }

    private static int getKeywordFrequency(String text, String keyword) {
        return StringUtils.countMatches(text, keyword);
    }
}
