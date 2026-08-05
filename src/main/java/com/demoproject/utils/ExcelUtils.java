package com.demoproject.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static final Logger logger = LoggerManager.getLogger(ExcelUtils.class);
    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelUtils() {
    }

    /**
     * Reads an Excel sheet and returns rows as Object[][] for TestNG DataProviders.
     * Row 0 is treated as the header. Each data row becomes one Object[] of cell values.
     */
    public static Object[][] getSheetData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new IllegalStateException("No data rows found in sheet: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();
            List<Object[]> rows = new ArrayList<>();

            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row, columnCount)) {
                    continue;
                }
                Object[] rowData = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    rowData[j] = getCellValueAsString(row.getCell(j));
                }
                rows.add(rowData);
            }

            logger.info("Loaded {} data rows from sheet '{}' in file '{}'", rows.size(), sheetName, filePath);
            return rows.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
    }

    /**
     * Reads an Excel sheet into a list of maps keyed by header column names.
     * Useful when column order may change.
     */
    public static List<Map<String, String>> getSheetDataAsMap(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();
            String[] headers = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                headers[j] = getCellValueAsString(headerRow.getCell(j));
            }

            List<Map<String, String>> data = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row, columnCount)) {
                    continue;
                }
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int j = 0; j < columnCount; j++) {
                    rowMap.put(headers[j], getCellValueAsString(row.getCell(j)));
                }
                data.add(rowMap);
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        return FORMATTER.formatCellValue(cell).trim();
    }

    private static boolean isRowEmpty(Row row, int columnCount) {
        for (int j = 0; j < columnCount; j++) {
            if (!getCellValueAsString(row.getCell(j)).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
