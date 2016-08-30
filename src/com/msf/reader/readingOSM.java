/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msf.reader;

import com.msf.main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.msf.writer.osmWriter;

/**
 *
 * @author s1525754
 */
public class readingOSM {	 
	 private HashMap<String,Boolean> found = new HashMap<String, Boolean>();
	 private static ArrayList<HashMap<String,String>> rows;
	 
	 public readingOSM (String xml_file_location, final HashMap<String, String> col_dict, final ArrayList<String> keys) {
	        try {

	            SAXParserFactory factory = SAXParserFactory.newInstance();
	            SAXParser saxParser = factory.newSAXParser();

	            DefaultHandler handler = new DefaultHandler() {

	                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	                	for(String key: keys){
	                		if(qName.equalsIgnoreCase(col_dict.get(key))){
	                			found.put(key, true);
	                		}
	                	}
	                }

	                @Override
	                public void characters(char ch[], int start, int length) throws SAXException {
	                	HashMap<String,String> hash = new HashMap<String,String>();
	                	for(String key:keys){
	                		if(found.containsKey(keys)){
	                			if(found.get(key)){
	    	                        String a = new String(ch, start, length);
	    	                        hash.put(key, a);
	    	                        found.put(key, false);
	                			}
	                		}
	                	}
	                	rows.add(hash);
	                		                }
	            };

	            saxParser.parse(xml_file_location, handler);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }		 
    public static void finish() {

        new readingOSM(Main.get,null,null);
		@SuppressWarnings("unchecked")
		HashMap<String,String>[] a = new HashMap[0];
        new osmWriter(rows.toArray(a), null);

    }

  
}
