package com.msf.reader;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import com.msf.main.Main;
import com.msf.writer.osmWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
*
* @author s1525754
*/
public class XLSX_Reader {

public static String[] lon_array;
public static String[] lat_array;
public static String[] villageName_array;
public static String[] altVilageName_array;
public static String[] borehole_access_array;
public static String[] handpump_condition_array;
public static String[] waterPointName_array;
public static int lon_index = 10000000;
public static int lat_index = 10000000;
public static int villageName_index = 10000000;
public static int altVillageName_index = 10000000;
public static int borehole_access_index = 10000000;
public static int handpump_condition_index = 10000000;
public static int waterPointName_index = 10000000;
public static InputStream ExcelFileToRead;

public static void getIndexes(String arg) throws IOException {
    try {

        ExcelFileToRead = new FileInputStream(arg);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        XSSFCell cell;
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator cells = sheet.getRow(0).cellIterator();

        while (cells.hasNext()) {
            cell = (XSSFCell) cells.next();
            if (cell != null) {
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    if (cell.getStringCellValue().equalsIgnoreCase("_Location_Latitude")) {
                        lat_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("_LOCATION_longitude")) {
                        lon_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("Village_name")) {
                        villageName_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("Alt_village_name")) {
                        altVillageName_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("HANDPUMP_WORKING")) {
                        handpump_condition_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("WATERPOINT_NAME")) {
                        waterPointName_index = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("BOREHOLE_PROTECTED")) {
                        borehole_access_index = cell.getColumnIndex();
                    }
                }

            }

        }

        lon_array = new String[sheet.getPhysicalNumberOfRows()];

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);

            if (row.getCell(lon_index) == null || row.getCell(lon_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(lon_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(lon_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(lon_index).toString();
                lon_array[i] = var;
                // System.out.println(var);
            } else {
                lon_array[i] = "null";

            }
        }

        lat_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(lat_index) == null || row.getCell(lat_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(lat_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(lat_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(lat_index).toString();
                lat_array[i] = var;
                // System.out.println(var);
            } else {
                lat_array[i] = "null";

            }
        }

        villageName_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(villageName_index) == null || row.getCell(villageName_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(villageName_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(villageName_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(villageName_index).toString();
                villageName_array[i] = var;
                //  System.out.println(var);
            } else {
                villageName_array[i] = "null";

            }

        }

        altVilageName_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(altVillageName_index) == null || row.getCell(altVillageName_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(altVillageName_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(altVillageName_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(altVillageName_index).toString();
                altVilageName_array[i] = var;
                //  System.out.println(var);
            } else {
                altVilageName_array[i] = "null";

            }

        }

        borehole_access_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(borehole_access_index) == null || row.getCell(borehole_access_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(borehole_access_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(borehole_access_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(borehole_access_index).toString();
                borehole_access_array[i] = var;
                // System.out.println(var);
            } else {
                borehole_access_array[i] = "null";

            }

        }
        handpump_condition_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(handpump_condition_index) == null || row.getCell(handpump_condition_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(handpump_condition_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(handpump_condition_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(handpump_condition_index).toString();
                handpump_condition_array[i] = var;
                //System.out.println(var);
            } else {
                handpump_condition_array[i] = "null";

            }

        }

        waterPointName_array = new String[sheet.getPhysicalNumberOfRows()];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(waterPointName_index) == null || row.getCell(waterPointName_index).getCellType() == Cell.CELL_TYPE_BLANK) {
                i++;
            } else if (row.getCell(waterPointName_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(waterPointName_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String var = row.getCell(waterPointName_index).toString();
                waterPointName_array[i] = var;
                // System.out.println(var);
            } else {
                waterPointName_array[i] = "null";

            }

        }

//    public static String[] getLon_array() {
//        return lon_array;
//    }
//    
//    public static String[] getLat_array() {
//        return lat_array;
//    }
//    public static String[] getVillageName_array() {
//        return  villageName_array;
//    } 
//    public static String[] getAltVillageName_array() {
//        return altVilageName_array;
//    }
//    public static String[] getBoreholeAccess_array() {
//        return borehole_access_array;
//    }
//    public static String[] getHandPumpCondition_array() {
//        return handpump_condition_array;
//    }
//    public static String[] getWaterPoint_array() {
//        return waterPointName_array;
//    }
        wb.close();
    } catch (IOException e) {
    }
}

public static void XLSX_converter(String args) throws IOException {
    try {
        getIndexes(args);
        osmWriter.osmWriter(lat_array, lon_array, borehole_access_array, handpump_condition_array, waterPointName_array, villageName_array, altVilageName_array);
    } catch (IOException e) {

    }

}
}
