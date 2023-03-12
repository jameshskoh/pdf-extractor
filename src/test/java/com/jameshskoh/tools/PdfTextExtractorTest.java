package com.jameshskoh.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PdfTextExtractorTest {

    @InjectMocks
    PdfTextExtractor pte;

    @Mock
    PDFTextStripper pts;

    @Mock
    FileHelper fh;

    static String text1 = """
These condensed interim financial statements have been prepared in accordance with MFRS 134 Interim
Financial Reporting issued by the Malaysian Accounting Standards Board (“MASB”) and complies with the
International Accounting Standard ("IAS") 34 Interim Financial Reporting issued by the International
Accounting Standards Board.""";

    static String text2 = """
The amendments clarify that an entity is ‘testing whether the asset is functioning properly’ when
it assesses the technical and physical performance of the asset, and prohibit an entity from
deducting from the cost of an item of property, plant and equipment any proceeds received
from selling items produced while the entity is preparing the asset for its intended use (for
example, the proceeds from selling samples produced when testing a machine to see if it is
functioning properly). The proceeds from selling such samples, together with the costs of
producing them, shall be recognised in profit or loss. The adoption of these amendments did
not result in any impact to the financial statements of the Bank""";

    static String text3 = """
The amendments updated MFRS 3 Business Combinations to refer to the revised Conceptual
Framework for Financial Reporting ("Conceptual Framework") in order to determine what
constitutes an asset or a liability in a business combination. In addition, a new exception is
added in MFRS 3 in connection with liabilities and contingent liabilities. The exception specifies
that, for some types of liabilities and contingent liabilities, an entity applying MFRS 3 should
instead refer to MFRS 137 Provisions, Contingent Liabilities and Contingent Assets or IC
Interpretation 21 Levies , rather than the Conceptual Framework. The adoption of these
amendments did not result in any impact as there is no business combination or asset
acquisition occured during the financial quarter 30 June 2022.
""";

    static String text4 = """
The preparation of the condensed interim financial statements in accordance with MFRS requires
management to make judgements, estimates and assumptions that affect the application of
accounting policies and reported amounts of revenue, expenses, assets and liabilities, the
accompanying disclosures and the disclosure of contingent liabilities. Judgements, estimates and
assumptions are continually evaluated and are based on past experience, reasonable expectations
of future events and other factors. Uncertainty about these assumptions and estimates could result
in outcomes that require a material adjustment to the carrying amount of assets or liabilities affected
in future periods.
""";

    static String fileName1 = "myFile.pdf";
    static String fileName2 = "myFile.PDF";
    static String fileName3 = "abc_123.10.Pdf";
    static String fileName4 = "abc_123.10.pDF";

    static Stream<Arguments> testStrings() {
        return Stream.of(
                Arguments.of(text1, fileName1),
                Arguments.of(text2, fileName2),
                Arguments.of(text3, fileName3),
                Arguments.of(text4, fileName4)
        );
    }

    @ParameterizedTest
    @MethodSource("testStrings")
    void createTextMap_shouldReturnStringPairWithSameCase(String pdfText, String fileName) throws IOException {
        Mockito.when(pts.getText(any(PDDocument.class))).thenReturn(pdfText);
        Mockito.when(fh.getPDDoc(any(File.class))).thenReturn(new PDDocument());

        Pair<String, String> result = pte.createTextMap(new File(fileName), true);

        assertEquals(result.getValue0(), fileName);
        assertEquals(result.getValue1(), pdfText);
    }

    @ParameterizedTest
    @MethodSource("testStrings")
    void createTextMap_shouldReturnStringPairWithLowerCase(String pdfText, String fileName) throws IOException {
        Mockito.when(pts.getText(any(PDDocument.class))).thenReturn(pdfText);
        Mockito.when(fh.getPDDoc(any(File.class))).thenReturn(new PDDocument());

        Pair<String, String> result = pte.createTextMap(new File(fileName), false);

        assertEquals(result.getValue0(), fileName);
        assertEquals(result.getValue1(), pdfText.toLowerCase());
    }

    @Test
    void createTextMap_shouldReturnNullPairIfgetPDDocThrowsIOE() throws IOException {
        Mockito.when(fh.getPDDoc(any(File.class))).thenThrow(new FileNotFoundException());

        Pair<String, String> result = pte.createTextMap(new File(fileName1), false);

        assertEquals(result.getValue0(), fileName1);
        assertNull(result.getValue1());
    }

    @Test
    void createTextMap_shouldReturnNullPairIfPDFTextStripperThrowsIOE() throws IOException {
        Mockito.when(pts.getText(any(PDDocument.class))).thenThrow(new IOException());
        Mockito.when(fh.getPDDoc(any(File.class))).thenReturn(new PDDocument());

        Pair<String, String> result = pte.createTextMap(new File(fileName1), false);

        assertEquals(result.getValue0(), fileName1);
        assertNull(result.getValue1());
    }
}