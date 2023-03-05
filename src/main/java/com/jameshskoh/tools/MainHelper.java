package com.jameshskoh.tools;

import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainHelper {

    static final PdfTextExtractor extractor = new PdfTextExtractor();
    static final TextStatisticsGenerator statGen = new TextStatisticsGenerator();
    static final ReportGenerator reportGen = new ReportGenerator();

    static final String PDF_SUFFIX = ".pdf";

    public void generateXlsxStatisticsFromPdf(String pdfFolderPath, String keywordFilePath, String outputFilePath,
                                              boolean caseSensitive, boolean debug) {
        List<String> keywords = getKeywords(keywordFilePath);
        File[] pdfs = getPdfHandles(pdfFolderPath);
        Stream<Pair<String, String>> pdfTextPairStream = Arrays
                .stream(pdfs)
                .filter(this::fileNameIsPdf)
                .map((pdf) -> extractText(pdf, caseSensitive));
        var results = statGen.generateStatistics(pdfTextPairStream, keywords);

        if (debug) printResults(results);

        reportGen.generateXlsxReport(results, keywords, outputFilePath);
    }

    private static List<String> getKeywords(String keywordFilePath) {
        File keywordFile = new File(keywordFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(keywordFile))) {
            return new KeywordExtractor().extractKeywords(br, true);
        } catch (IOException e) {
            System.out.println("Unable to load keyword file.");
            throw new RuntimeException(e);
        }
    }

    private static File[] getPdfHandles(String pdfFolderPath) {
        File pdfFolder = new File(pdfFolderPath);
        File[] pdfs = pdfFolder.listFiles();
        if (pdfs == null) throw new RuntimeException("PDF file path is not acceptable. Please pass in a folder path that contains PDF files.");
        return pdfs;
    }

    private boolean fileNameIsPdf(File pdf) {
        if (!pdf.getName().toLowerCase().endsWith(PDF_SUFFIX)) {
            System.out.println(pdf.getName() + " skipped: file name did not end with .pdf\n");
            return false;
        }

        return true;
    }

    private Pair<String, String> extractText(File pdf, boolean caseSensitive) {
        System.out.println(pdf.getName() + " found: extracting texts\n");

        try {
            String text = extractor.extractText(pdf);
            if (caseSensitive) return new Pair<>(pdf.getName(), text);
            else return new Pair<>(pdf.getName(), text.toLowerCase());
        } catch(IOException exc) {
            System.out.println(pdf.getName() + " skipped: extraction failed.");
            exc.printStackTrace();
            return new Pair<>(pdf.getName(), null);
        }
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
