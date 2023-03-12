package com.jameshskoh.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordExtractor {

    public List<String> extractKeywords(BufferedReader br, boolean caseSensitive) throws IOException {
        List<String> keywords = new ArrayList<>();

        String line = br.readLine();

        while (line != null) {
            String result = line.trim();
            if (result.length() != 0) keywords.add(caseSensitive ? result : result.toLowerCase());
            line = br.readLine();
        }

        return keywords;
    }
}
