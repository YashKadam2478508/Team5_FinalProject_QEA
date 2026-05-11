package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    private static final Logger log = LogManager.getLogger(ExcelUtil.class);

    private ExcelUtil() { /* utility class */ }

    /**
     * Writes listing data to an .xlsx file.
     *
     * @param filePath  output path  e.g. "reports/excel/CarWashServices.xlsx"
     * @param sheetName name of the sheet inside the workbook
     * @param headers   column header labels
     * @param rows      each row is a String[] matching the header count
     */
    public static void writeListings(String filePath,
                                     String sheetName,
                                     String[] headers,
                                     List<String[]> rows) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(sheetName);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            for (int i = 0; i < rows.size(); i++) {
                Row row = sheet.createRow(i + 1);
                String[] rowData = rows.get(i);
                for (int j = 0; j < rowData.length; j++) {
                    row.createCell(j).setCellValue(rowData[j] != null ? rowData[j] : "N/A");
                }
            }

            // ── Auto-size all columns ─────────────────────────────────────
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // ── Save file ─────────────────────────────────────────────────
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            log.info("Excel report saved: {}", file.getAbsolutePath());

        } catch (IOException e) {
            log.error("Failed to write Excel file '{}': {}", filePath, e.getMessage());
        }
    }
}
