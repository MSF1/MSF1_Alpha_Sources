package com.msf.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.javatuples.Pair;

public class ColumnTableListener implements TableModelListener {
private static HashMap<Integer,HashMap<String,String>> dict;
private static HashMap<Integer,String> keys;
private static ArrayList<String> list;
private static HashMap<String, HashMap<String, String>> dictionary;

public void tableChanged(TableModelEvent e) {
	if(e.getType()== TableModelEvent.UPDATE){
	
		int row = e.getLastRow();
		int column = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		String value = model.getValueAt(row, column).toString();
		String columnName = model.getColumnName(column);
			if(column ==0){
				keys.put(row,value);
				if(!dict.containsKey(row)){
				dict.put(row, new HashMap<String,String>());
				}
			}else{
				if(dict.containsKey(row)){
					dict.get(row).put(columnName, value);
				}else{
					HashMap<String,String> hash = new HashMap<String,String>();
						hash.put(columnName,value);
					dict.put(row, hash);
				}
			}
		}
	}
public ColumnTableListener(){
	dict = new HashMap<Integer, HashMap<String, String>>();
	keys = new HashMap<Integer,String>();
	
			}
public static Pair<ArrayList<String>,HashMap<String, HashMap<String, String>>> getAttributes() {
	list = new ArrayList<String>();
	dictionary = new HashMap<String, HashMap<String, String>>();
	for(Integer row:keys.keySet()){
	if(!list.contains(keys.get(row))){
		list.add(keys.get(row));
	}
	}
	Integer[] set =keys.keySet().toArray(new Integer[0]);
	for(Integer key:set){
		if(dict.get(key).isEmpty()){
			continue;
		}
		String ke = dict.get(key).get("Key");
		String value = dict.get(key).get("Value");
		if(dictionary.containsKey(keys.get(key))){
			dictionary.get(keys.get(key)).put(ke, value);
		}else{
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(ke, value);
		dictionary.put(keys.get(key), hash);
		}
	}
	list.add(Main.getLatColName());
	list.add(Main.getLonColName());

	System.out.println(dictionary);
	System.out.println(list);
	return new Pair<ArrayList<String>, HashMap<String, HashMap<String, String>>>(list,dictionary);
}






}