package generate_card.classes;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 *
 * @author Marvin
 */
public class exel_file {
    ///Declaration of the work book and the sheet of excel
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    
    ///Constructor which accepts String file path and the name of the sheet
    public exel_file(String file, String sheet_name) {
        try {
            //work book and sheet assignment
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheet_name);
        } catch (IOException ex) {
            Logger.getLogger(exel_file.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                                       
    //Set value at excel at given row and column with value data type date for  form 138 when trying to create a automatic generate a new row and cell 
    public void newSubject(int row, int column, String value){
        sheet.shiftRows(row, row+20, 1);
        Row r = sheet.createRow(row);
        sheet.addMergedRegion(new CellRangeAddress(row,row,column,9));
        Cell c = r.createCell(column);
        c.setCellValue(value);
    }
    
                                         /*           both the row and sheet is getted from the source file          */
    
    //Set value at excel at given row and column with value data type String
    public void setValueAt(int row, int column, String value){
        Row r = sheet.getRow(row);
        Cell c = r.createCell(column);
        c.setCellValue(value);
    }
    
    //Set value at excel at given row and column with value data type integer
    public void setValueAt(int row, int column, int value){
        Row r = sheet.getRow(row);
        Cell c = r.createCell(column);
        c.setCellValue(value);
    }
    
    //Set value at excel at given row and column with value data type float
    public void setValueAt(int row, int column, float value){
        Row r = sheet.getRow(row);
        Cell c = r.createCell(column);
        c.setCellValue(value);
    }
    
    //Set value at excel at given row and column with value data type date
    public void setValueAt(int row, int column, Date value){
        Row r = sheet.getRow(row);
        Cell c = r.createCell(column);
        c.setCellValue(value);
    }
    
    //Write the changes into a excel named result.xlsx
    public void writeout(){
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File f = chooser.getSelectedFile();
        String path = f.getPath();
        try {
            if(!f.getPath().contains(".xlsx"))
                path = f.getPath()  + ".xlsx";
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(exel_file.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
