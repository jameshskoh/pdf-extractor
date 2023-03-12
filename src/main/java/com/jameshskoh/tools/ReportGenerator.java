package com.jameshskoh.tools;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    public void generateXlsxReport(Map<String, Map<String, Integer>> results, List<String> keywords, String reportFilePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Summary");

            // create header row
            Row headerRow = sheet.createRow(0);

            int headerColNum = 1;
            for (String keyword : keywords) {
                Cell headerCell = headerRow.createCell(headerColNum++);
                headerCell.setCellValue(keyword);
            }

            int rowNum = 1;
            for (String pdfName : results.keySet()) {
                Row row = sheet.createRow(rowNum++);

                Cell pdfNameCell = row.createCell(0);
                pdfNameCell.setCellValue(pdfName);

                Map<String, Integer> result = results.get(pdfName);

                // only report on PDFs that are successfully extracted
                if (result.size() > 0) {
                    int colNum = 1;
                    for (String keyword : keywords) {
                        Cell cell = row.createCell(colNum++);
                        cell.setCellValue(result.get(keyword));
                    }
                }
            }

            FileOutputStream outputStream = new FileOutputStream(reportFilePath);
            workbook.write(outputStream);
        } catch (FileNotFoundException exc) {
            System.out.println(reportFilePath + ": destination path is a directory. Please set destination path to a file.");
            throw new RuntimeException(exc);
        } catch (IOException exc) {
            System.out.println("Unable to save result as Excel.");
            throw new RuntimeException(exc);
        }
    }

}
