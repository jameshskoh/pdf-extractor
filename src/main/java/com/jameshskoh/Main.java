package com.jameshskoh;

import com.jameshskoh.tools.KeywordExtractor;
import com.jameshskoh.tools.MainHelper;
import com.jameshskoh.tools.ReportGenerator;
import com.jameshskoh.tools.TextStatisticsGenerator;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {

    private static final MainHelper helper = new MainHelper();

    private static final String KEYWORD_FILE_PATH_FLAG = "--keyword-file-path";
    private static final String PDF_FOLDER_PATH_FLAG = "--pdf-folder-path";
    private static final String OUTPUT_FILE_PATH_FLAG = "--output-file-path";
    private static final String CASE_SENSITIVE_FLAG = "--case-sensitive";
    private static final String DEBUG_FLAG = "--debug";

    public static void main(String[] args) {
        String keywordFilePath = "";
        String pdfFolderPath = "";
        String outputFilePath = "";
        boolean caseSensitive = false;
        boolean debug = false;

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
            if (KEYWORD_FILE_PATH_FLAG.equals(args[i])) {
                i++;
                keywordFilePath = args[i];
                System.out.println(keywordFilePath);
            } else if (PDF_FOLDER_PATH_FLAG.equals(args[i])) {
                i++;
                pdfFolderPath = args[i];
                System.out.println(pdfFolderPath);
            } else if (OUTPUT_FILE_PATH_FLAG.equals(args[i])) {
                i++;
                outputFilePath = args[i];
                System.out.println(outputFilePath);
            } else if (CASE_SENSITIVE_FLAG.equals(args[i])) {
                caseSensitive = true;
            } else if (DEBUG_FLAG.equals(args[i])) {
                debug = true;
            }
        }

        printWelcomeMessage(keywordFilePath, pdfFolderPath, outputFilePath, caseSensitive, debug);

        if (keywordFilePath.equals("") || pdfFolderPath.equals("") || outputFilePath.equals(""))
            throw new RuntimeException("Please pass in appropriate environment variables.");

        helper.generateXlsxStatisticsFromPdf(pdfFolderPath, keywordFilePath, outputFilePath, caseSensitive, debug);
    }

    private static void printWelcomeMessage(String keywordFilePath, String pdfFolderPath, String outputFilePath, boolean caseSensitive, boolean debug) {
        System.out.println("Welcome!");
        System.out.println("Configurations: ");
        System.out.println("Keyword file path\t: " + keywordFilePath);
        System.out.println("PDF folder path\t\t: " + pdfFolderPath);
        System.out.println("Output file path\t: " + outputFilePath);
        System.out.println("Case sensitivity\t: " + caseSensitive);
        if (debug) System.out.println("""
                ***
                DEBUG MODE ON!
                ***
                """);
        System.out.println();
    }
}
