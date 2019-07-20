/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;
//package net.codejava.excel;
 
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Gravicor
 */
public class CreateExcelFile {
    public boolean createFile(Object[][] data, String fileName, String[] columnas){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reporte");

            int rowCount = 0;
            Row row = sheet.createRow(rowCount);
            int columnCount = -1;
            for(String x : columnas){
                Cell cell = row.createCell(++columnCount);
                cell.setCellValue(x);
            }

            for(int i = 0; i < data[0].length ; i++){
                row = sheet.createRow(++rowCount);
                columnCount = -1;
                
                for(int j = 0; j < data.length; j++){
                    Cell cell = row.createCell(++columnCount);
                    String dataCell = (String) data[j][i];
                    try{
                        Double numero = new Double(dataCell);
                        cell.setCellValue(numero);
                    }
                    catch(Exception e){
                        cell.setCellValue(dataCell);
                    }
                    
                }
            }
            
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        }
        catch(Exception e){
            return false;
        }
        
        return true;
    }

    public CreateExcelFile() {
    }
}
