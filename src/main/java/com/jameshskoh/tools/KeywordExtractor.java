package com.jameshskoh.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeywordExtractor {

    public List<String> extractKeywords(File file) throws IOException {
        List<String> keywords = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine();

        while (line != null) {
            keywords.add(line.trim());
            line = br.readLine();
        }

        return keywords;
    }
}
