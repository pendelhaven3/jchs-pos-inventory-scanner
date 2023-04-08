package com.jchs.pos.inventoryscanner.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jchs.pos.inventoryscanner.model.Entry;

public class InventoryMonitoringSheetExcelGenerator {

    public Workbook generate(List<Entry> entries) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getResourceAsStream("/excel/inventory-monitoring-sheet.xlsx"));
        
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        int currentRow = 5;
        
        for (Entry entry : entries) {
            Row row = sheet.getRow(currentRow);
            Cell cell = row.getCell(1);
            cell.setCellValue(entry.getProduct().getUnit());
            cell = row.getCell(2);
            cell.setCellValue(entry.getProduct().getDescription());
            cell = row.getCell(12);
            cell.setCellValue(entry.getProduct().getBarcode());
                        
            currentRow++;
        }
        
        return workbook;
    }
    
}
