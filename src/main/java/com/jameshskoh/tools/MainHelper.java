package com.jameshskoh.tools;

import org.apache.pdfbox.text.PDFTextStripper;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MainHelper {

    final FileHelper fileHelper;
    final KeywordExtractor wordExtractor;
    final TextStatisticsGenerator statGen;
    final ReportGenerator reportGen;

    public MainHelper() {
        fileHelper = new FileHelper();
        wordExtractor = new KeywordExtractor();
        statGen = new TextStatisticsGenerator();
        reportGen = new ReportGenerator();
    }

    public void generateXlsxStatisticsFromPdf(String pdfFolderPath, String keywordFilePath, String outputFilePath,
                                              boolean caseSensitive, boolean parallel, boolean debug) {
        try (BufferedReader br = fileHelper.getBufferedReader(keywordFilePath)) {
            List<String> keywords = wordExtractor.extractKeywords(br, caseSensitive);

            File[] pdfs = fileHelper.getPdfHandlesFromFolder(pdfFolderPath);

            var baseStream = Arrays.stream(pdfs);

            if (parallel) baseStream = baseStream.parallel();
            else baseStream = baseStream.sequential();

            Stream<Pair<String, String>> pdfTextPairStream
                    = baseStream
                    .filter(fileHelper::isPdfFile)
                    .map((pdf) -> mapTextMap(pdf, caseSensitive));
            var results = statGen.generateStatistics(pdfTextPairStream, keywords);

            if (debug) printResults(results);

            reportGen.generateXlsxReport(results, keywords, outputFilePath);
        } catch (IOException e) {
            System.out.println("Unable to load keyword file.");
            throw new RuntimeException(e);
        }
    }

    private Pair<String, String> mapTextMap(File pdf, boolean caseSensitive) {
        // you need multiple instances of text extractor to extract text in parallel
        try {
            PdfTextExtractor pdfExtractor = new PdfTextExtractor(new PDFTextStripper(), new FileHelper());
            return pdfExtractor.createTextMap(pdf, caseSensitive);
        } catch (IOException e) {
            System.out.println("PDFTextStripper cannot be initialized, skipping this PDF file: " + pdf.getName());
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
