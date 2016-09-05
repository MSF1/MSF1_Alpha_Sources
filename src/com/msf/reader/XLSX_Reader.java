package com.msf.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
				Iterator<Cell> cells1 = sheet.getRow(0).cellIterator();
				HashMap<String,Integer> indexs= new HashMap<String,Integer>();
				while(cells1.hasNext()){
					cell = (XSSFCell) cells1.next();
					if(cell!=null ){
						if(keys.contains(cell.getStringCellValue())){
							indexs.put(cell.getStringCellValue(), cell.getColumnIndex());
						}
					}
				}
				System.out.println(indexs);
				for (int row_count=0;row_count<sheet.getPhysicalNumberOfRows();row_count++){
//					System.out.print(rows);
//					System.out.println();
					cell_value = new HashMap<String, String>();
					XSSFRow row = sheet.getRow(row_count);
					for(String key:keys){
							addAttr(key,row.getCell(indexs.get(key), XSSFRow.RETURN_BLANK_AS_NULL));
					}
//					if(!cell_value.isEmpty()){
						rows.add(cell_value);
//					}
				}
//				System.out.print(rows.size());
				}
			wb.close();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static void XLSX_converter(String args,ArrayList<String> keys,HashMap<String,HashMap<String,String>> dictionary) throws IOException {
		try {
			ArrayList<HashMap<String,String>>rows=constructNodes(args,keys);
			@SuppressWarnings("unchecked")
			HashMap<String,String>[] a = new HashMap[0];
			System.out.println(Arrays.toString(rows.toArray(a)));
			new osmWriter(rows.toArray(a),dictionary);
		} catch (IOException e) {
			e.printStackTrace();
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
