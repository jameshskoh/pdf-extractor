package com.jameshskoh.tools;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStatisticsGenerator {

    public Map<String, Map<String, Integer>> generateStatistics(Stream<Pair<String, String>> pdfTextPairStream, List<String> keywords) {
        return pdfTextPairStream
                .collect(
                        Collectors.toMap(
                                Pair::getValue0,
                                pair -> processStatistics(pair.getValue1(), keywords)
                        )
                );
    }

    private Map<String, Integer> processStatistics(String text, List<String> keywords) {
        if (text == null) return new HashMap<>();
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
