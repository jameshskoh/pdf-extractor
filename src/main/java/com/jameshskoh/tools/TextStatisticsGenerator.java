package com.jameshskoh.tools;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextStatisticsGenerator {

    static final PdfTextExtractor extractor = new PdfTextExtractor();
    static final String PDF_SUFFIX = ".pdf";

    public Map<String, Map<String, Integer>> generateStatistics(File[] pdfs, List<String> keywords, boolean toLowerCase) {
        return Arrays.stream(pdfs)
                .filter(this::fileNameIsPdf)
                .map(pdf -> extractText(pdf, toLowerCase))
                .collect(
                        Collectors.toMap(
                                Pair::getValue0,
                                pair -> processStatistics(pair.getValue1(), keywords)
                        )
                );
    }

    private boolean fileNameIsPdf(File pdf) {
        if (!pdf.getName().toLowerCase().endsWith(PDF_SUFFIX)) {
            System.out.println(pdf.getName() + " skipped: file name did not end with .pdf\n");
            return false;
        }

        return true;
    }

    private Pair<String, String> extractText(File pdf, boolean toLowerCase) {
        System.out.println(pdf.getName() + " found: extracting texts\n");

        try {
            String text = extractor.extractText(pdf);
            if (toLowerCase) return new Pair<>(pdf.getName(), text.toLowerCase());
            else return new Pair<>(pdf.getName(), text);
        } catch(IOException exc) {
            System.out.println(pdf.getName() + " skipped: extraction failed.");
            exc.printStackTrace();
            return new Pair<>(pdf.getName(), null);
        }
    }

    private Map<String, Integer> processStatistics(String text, List<String> keywords) {
        if (text == null) return null;
        return getKeywordFrequencies(text, keywords);
    }

    private Map<String, Integer> getKeywordFrequencies(String text, List<String> keywords) {
        return keywords.stream()
                .collect(Collectors.toMap(
                        keyword -> keyword,
                        keyword -> getKeywordFrequency(text, keyword)));
    }

    private int getKeywordFrequency(String text, String keyword) {
        return StringUtils.countMatches(text, keyword);
    }
}
