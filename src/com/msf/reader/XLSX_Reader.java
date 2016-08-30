package com.msf.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.msf.main.Main;
import com.msf.writer.osmWriter;

/**
 *
 * @author s1525754
 */
public class XLSX_Reader{

	public static InputStream ExcelFileToRead;	
	private static HashMap<String,String> cell_value;
	public static ArrayList<String> getXLSXColumnNames(){
		ArrayList<String> columnNames = new ArrayList<String>();
		try {
			ExcelFileToRead = new FileInputStream(Main.get);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow fstRow = sheet.getRow(0);
			Iterator<Cell> cells1 = sheet.getRow(0).cellIterator();
			XSSFCell cell;
			while(cells1.hasNext()){
				cell = (XSSFCell) cells1.next();
				columnNames.add(cell.getStringCellValue());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnNames;

	}
	public static ArrayList<HashMap<String,String>> constructNodes(String arg,ArrayList<String> keys) throws IOException {
		try {
			ExcelFileToRead = new FileInputStream(arg);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFCell cell;
			ArrayList<HashMap<String,String>> rows = new ArrayList<HashMap<String, String>>();      

			for(int h=0; h <wb.getNumberOfSheets();h++){
				XSSFSheet sheet = wb.getSheetAt(h);
				//Getting indexes of selected columns from the first row
				XSSFRow fstRow = sheet.getRow(0);
				Iterator<Cell> cells1 = sheet.getRow(0).cellIterator();
				HashMap<String,Integer> indexs= new HashMap<String,Integer>();
				int j =0;
				while(cells1.hasNext()){
					cell = (XSSFCell) cells1.next();
					for(String key:keys){
							if(fstRow.getCell(j, XSSFRow.RETURN_BLANK_AS_NULL)!=null){
									indexs.put(key, cell.getColumnIndex());
							}
						
					}
					j++;
				}
				for (int row_count=0;row_count<sheet.getPhysicalNumberOfRows();row_count++){
					cell_value = new HashMap<String, String>();
					XSSFRow row = sheet.getRow(row_count);
					for(String key:keys){
							addAttr(key,row.getCell(indexs.get(key), XSSFRow.RETURN_BLANK_AS_NULL));
					}
					if(!cell_value.isEmpty()){
						rows.add(cell_value);
					}
					//            if (cell != null) {
					//                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					//                    if (cell.getStringCellValue().equalsIgnoreCase(lat_cell)) {
					//                        lat_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(lon_cell)) {
					//                        lon_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(villageName_cell)) {
					//                        villageName_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(altVillageName_cell)) {
					//                        altVillageName_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(handpump_condition_cell)) {
					//                        handpump_condition_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(waterPointName_cell)) {
					//                        waterPointName_index = cell.getColumnIndex();
					//                    }
					//                    if (cell.getStringCellValue().equalsIgnoreCase(borehole_access_cell)) {
					//                        borehole_access_index = cell.getColumnIndex();
					//                    }
					//                }
					//
					//            }
					//
					//        }
					//
					//        lon_array = new String[sheet.getPhysicalNumberOfRows()];

					//						waterPointName_array = new String[sheet.getPhysicalNumberOfRows()];
					//						for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					//							XSSFRow row = sheet.getRow(i);
					//							if (row.getCell(waterPointName_index) == null || row.getCell(waterPointName_index).getCellType() == Cell.CELL_TYPE_BLANK) {
					//								i++;
					//							} else if (row.getCell(waterPointName_index).getCellType() == XSSFCell.CELL_TYPE_NUMERIC || row.getCell(waterPointName_index).getCellType() == XSSFCell.CELL_TYPE_STRING) {
					//								String var = row.getCell(waterPointName_index).toString();
					//								waterPointName_array[i] = var;
					//								// System.out.println(var);
					//							} else {
					//								waterPointName_array[i] = "null";
					//
					//							}
					//
					//						}
					//					}
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
				}}
			return rows;
		} catch (IOException e) {
		}
		return null;
	}



	public static void XLSX_converter(String args,ArrayList<String> keys,HashMap<String,HashMap<String,String>> dictionary) throws IOException {
		try {
			ArrayList<HashMap<String,String>>rows=constructNodes(args,keys);
			@SuppressWarnings("unchecked")
			HashMap<String,String>[] a = new HashMap[0];
			new osmWriter(rows.toArray(a),dictionary);
		} catch (IOException e) {

		}

	}
	public static void addAttr(String key, XSSFCell value){
		if(value!=null){
				cell_value.put(key, value.toString());
		}else{
			cell_value.put(key, "");
		}
	}
}
