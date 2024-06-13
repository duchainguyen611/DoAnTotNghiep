package com.ra.model.serviceImp;

import com.ra.model.entity.dto.response.admin.dashboard.ExcelExportable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    public ByteArrayInputStream exportToExcel(List<? extends ExcelExportable> listObjects, String title) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        if (listObjects == null || listObjects.isEmpty()) {
            throw new IllegalArgumentException("The list of objects is empty or null");
        }

        // Create title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);

        // Merge the title row cells
        int numColumns = listObjects.get(0).getHeaders().length;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, numColumns - 1));

        // Style the title cell (optional)
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        // Create header row
        Row headerRow = sheet.createRow(1);
        String[] headers = listObjects.get(0).getHeaders();
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Create data rows
        int rowNum = 2;
        for (ExcelExportable obj : listObjects) {
            Row row = sheet.createRow(rowNum++);
            String[] data = obj.getData();
            for (int i = 0; i < data.length; i++) {
                row.createCell(i).setCellValue(data[i]);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}