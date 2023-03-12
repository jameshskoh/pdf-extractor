package com.jameshskoh.tools;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;

public class FileHelper {

    static final String PDF_SUFFIX = ".pdf";

    public BufferedReader getBufferedReader(String keywordFilePath) throws FileNotFoundException {
        File keywordFile = new File(keywordFilePath);
        return new BufferedReader(new FileReader(keywordFile));
    }

    public File[] getPdfHandlesFromFolder(String pdfFolderPath) {
        File pdfFolder = new File(pdfFolderPath);
        File[] pdfs = pdfFolder.listFiles();
        if (pdfs == null) throw new RuntimeException("PDF file path is not acceptable. Please pass in a folder path that contains PDF files.");
        return pdfs;
    }

    public boolean isPdfFile(File pdf) {
        if (!pdf.getName().toLowerCase().endsWith(PDF_SUFFIX)) {
            System.out.println(pdf.getName() + " skipped: file name did not end with .pdf\n");
            return false;
        }

        return true;
    }

    public PDDocument getPDDoc(File pdf) throws IOException {
        return PDDocument.load(pdf);
    }
}
