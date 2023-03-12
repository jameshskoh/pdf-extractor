package com.jameshskoh.tools;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TextStatisticsGeneratorTest {

    TextStatisticsGenerator tsg;

    static String text1 = """
            with each passing year, we increasingly recognise
            just how important our stakeholders are to our
            sustainable growth.
            """;

    static Stream<Pair<String, String>> input1 = Stream.of(new Pair<>("file1", text1));

    static List<String> list1 = List.of("with",
            "we",
            "our",
            "stakeholder");

    static Map<String, Map<String, Integer>> result1 = Map.of(
            "file1", Map.of(
                    "with", 1,
                    "we", 1,
                    "our", 2,
                    "stakeholder", 1
            )
    );

    static String text2 = """
            EMS Bank Accounts means all of the bank accounts, lock boxes and safety deposit
            boxes of EMS or relating to the EMS Business. Continuous review and enhancement
            of our compliance and risk management monitoring tools, systems and processes.
            """;

    static Stream<Pair<String, String>> input2 = Stream.of(new Pair<>("file2", text2));

    static List<String> list2 = List.of("EMS");

    static Map<String, Map<String, Integer>> result2 = Map.of(
            "file2", Map.of(
                    "EMS", 3
            )
    );

    static String text3 = """
            We were the first conventional bank
            to adopt an Islamic-first approach, namely offering
            customers Shariah-compliant products and services
            as the norm. Our M25 journey got off to a good start.
            """;

    static Stream<Pair<String, String>> input3 = Stream.of(new Pair<>("file3", text3));

    static List<String> list3 = List.of("Islamic",
            "Shariah-compliant",
            "conventional bank",
            "products and services");

    static Map<String, Map<String, Integer>> result3 = Map.of(
            "file3", Map.of(
                    "Islamic", 1,
                    "Shariah-compliant", 1,
                    "conventional bank", 1,
                    "products and services", 1
            )
    );

    static String text4 = """
            We have also continued to actively engage students and graduate talents
            throughout the year through various virtual recruitment and engagement
            initiatives such as #Mbassador, #MaybankDay at universities, Digital hangouts,
            Digital Challenges, M-Dash challenge, M-Fest, and virtual career fairs, to
            continue empowering young talents around us and allowing them to grow
            despite the challenging environment we are in. Environmental, social and governance (ESG)
            elements are inculcated in various aspects of our Total Rewards management through proper
            governance, performance measurement standards and risk management considerations.
            """;

    static Stream<Pair<String, String>> input4 = Stream.of(new Pair<>("file4", text4));

    static List<String> list4 = List.of("#MaybankDay",
            "Digital Challenges",
            "DEI",
            "ESG");

    static Map<String, Map<String, Integer>> result4 = Map.of(
            "file4", Map.of(
                    "#MaybankDay", 1,
                    "Digital Challenges", 1,
                    "DEI", 0,
                    "ESG", 1
            )
    );

    static String text5 = """
            Addressing various aspects of quality management and containing
            some of ISO’s best-known standards, there’s the ISO 9000 family.
            Continuous efforts to improve our compliance culture and awareness
            to comply with applicable laws, regulations and supervisory expectations.
            As was widely expected, the Federal Reserve announced a 0.50 percentage point interest rate
            hike Tuesday, further increasing the costs of credit cards, auto financing and variable-rate loans.
            RM11.4 billion
            financing disbursed to SMEs
            """;

    static Stream<Pair<String, String>> input5 = Stream.of(new Pair<>("file5", text5));

    static List<String> list5 = List.of("ISO",
            "best-known",
            "1.4",
            "0.50 percentage point");

    static Map<String, Map<String, Integer>> result5 = Map.of(
            "file5", Map.of(
                    "ISO", 2,
                    "best-known", 1,
                    "1.4", 1,
                    "0.50 percentage point", 1
            )
    );

    static Stream<Pair<String, String>> invalidInput = Stream.of(new Pair<>("invalidFile", null));



    static Map<String, Map<String, Integer>> invalidResult = Map.of(
            "invalidFile", Map.of()
    );

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(input1, list1, result1),
                Arguments.of(input2, list2, result2),
                Arguments.of(input3, list3, result3),
                Arguments.of(input4, list4, result4),
                Arguments.of(input5, list5, result5),
                Arguments.of(invalidInput, list5, invalidResult)
        );
    }

    @BeforeEach
    void init() {
        tsg = new TextStatisticsGenerator();
    }

    @ParameterizedTest
    @MethodSource("data")
    void generateStatistics_shouldReturnCorrectResult(Stream<Pair<String, String>> pdfTextPairs, List<String> keywords, Map<String, Map<String, Integer>> results) {
        Map<String, Map<String, Integer>> myResults = tsg.generateStatistics(pdfTextPairs, keywords);

        assertEquals(results, myResults);
    }
}