package com.jameshskoh.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class KeywordExtractorTest {

    KeywordExtractor ke;

    static final String input1 = """
            hello""";

    static final List<String> result1 = Stream.of("hello").collect(Collectors.toList());
    static final List<String> resultLow1 = result1;

    static final String input2 = """
            hello
            """;

    static final List<String> result2 = result1;
    static final List<String> resultLow2 = result2;

    static final String input3 = """
            economic factor
            civil procedures
            state-of-the-art
            """;

    static final List<String> result3 =
            Stream.of("economic factor", "civil procedures", "state-of-the-art")
                    .collect(Collectors.toList());
    static final List<String> resultLow3 = result3;

    static final String input4 = """
            ISO 9001
            ISO 9001:2015
            Albert Einstein
            Yang-Mills theory
            "quote"
            McFlurry
            McDonald's
            (KLCC)
            [10]
            Done.""";

    static final List<String> result4 =
            Stream.of("ISO 9001", "ISO 9001:2015", "Albert Einstein", "Yang-Mills theory", "\"quote\"",
                            "McFlurry", "McDonald's", "(KLCC)", "[10]", "Done.")
                    .collect(Collectors.toList());

    static final List<String> resultLow4 =
            Stream.of("iso 9001", "iso 9001:2015", "albert einstein", "yang-mills theory", "\"quote\"",
                            "mcflurry", "mcdonald's", "(klcc)", "[10]", "done.")
                    .collect(Collectors.toList());

    static final String input5 = """
            
            """;

    static final List<String> result5 = new ArrayList<>();
    static final List<String> resultLow5 = result5;

    @BeforeEach
    void init() {
        ke = new KeywordExtractor();
    }

    private static Stream<Arguments> readerToList() {
        return Stream.of(
                arguments(input1, result1),
                arguments(input2, result2),
                arguments(input3, result3),
                arguments(input4, result4),
                arguments(input5, result5)
        );
    }

    @ParameterizedTest
    @MethodSource("readerToList")
    void extractKeywords_caseSensitive_shouldExtractKeywordsLineByLineAndInOrder(String string, List<String> result) {
        BufferedReader br = new BufferedReader(new StringReader(string));

        try {
            assertEquals(ke.extractKeywords(br, true), result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private static Stream<Arguments> readerToLowList() {
        return Stream.of(
                arguments(input1, resultLow1),
                arguments(input2, resultLow2),
                arguments(input3, resultLow3),
                arguments(input4, resultLow4),
                arguments(input5, resultLow5)
        );
    }

    @ParameterizedTest
    @MethodSource("readerToLowList")
    void extractKeywords_caseInsensitive_shouldExtractLowerCaseKeywordsLineByLineAndInOrder(String string, List<String> result) {
        BufferedReader br = new BufferedReader(new StringReader(string));

        try {
            assertEquals(ke.extractKeywords(br, false), result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("---");
        System.out.println(input5);
        System.out.println("---");

        BufferedReader br = new BufferedReader(new StringReader(input4));

        String line = br.readLine();

        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}