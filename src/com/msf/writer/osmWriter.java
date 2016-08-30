/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msf.writer;

import com.msf.main.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author s1525754
 */
public class osmWriter {
	private Document osm;
	private Element node;
	private HashMap <String, HashMap<String, String>> dict;
	private ArrayList<String> keys;
//	public osmWriter(String[] lat, String[] lon, String[] borehole_access, String[] handpump_condition, String[] waterPointName, String[] villageName, String[] altVillageName) {
	public osmWriter(HashMap<String, String>[] rows, HashMap<String, HashMap<String, String>> dictionary) {
		try {
			this.dict = dictionary;
			this.keys = new ArrayList<String>();
			DocumentBuilderFactory dbFactory
			= DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder
			= dbFactory.newDocumentBuilder();
			this.osm = dBuilder.newDocument();
			// root element
			Element rootElement = osm.createElement("osm");
			osm.appendChild(rootElement);

			rootElement.setAttribute("generator", "JOSM");
			rootElement.setAttribute("upload", "true");
			rootElement.setAttribute("version", "0.6");

			for (int i = 1; i < rows.length; i++) {
				//  node element
				if (rows[i].get("latitude") != null && rows[i].get("longitude") != null) {//Checking to ensure the node has coordinates, else it's not written
					this.node = osm.createElement("node");
					rootElement.appendChild(node);

					// setting attribute to element
					Attr attr = osm.createAttribute("action");
					attr.setValue("modify");
					node.setAttributeNode(attr);

					Attr attr2 = osm.createAttribute("id");
					attr2.setValue(Integer.toString(-(i + 1)));
					node.setAttributeNode(attr2);

					Attr attr3 = osm.createAttribute("lat");
					attr3.setValue(rows[i].get(Main.getLatColName()));
					node.setAttributeNode(attr3);

					Attr attr4 = osm.createAttribute("lon");
					attr4.setValue(rows[i].get(Main.getLonColName()));
					node.setAttributeNode(attr4);

					Attr attr5 = osm.createAttribute("visible");
					attr5.setValue("true");
					node.setAttributeNode(attr5);
					
					
					//tag node elements and attributes
					for(String key:keys){
					constructElement(osm, node, key, rows[i]);
					}
//					if (waterPointName[i] != null) {
//						Element tag_waterPointName = osm.createElement("tag");
//						node.appendChild(tag_waterPointName);
//
//						tag_waterPointName.setAttribute("k", "name");
//						tag_waterPointName.setAttribute("v", waterPointName[i]);
//
//						Element tag_pump = osm.createElement("tag");
//						node.appendChild(tag_pump);
//
//						tag_pump.setAttribute("k", "pump");
//						tag_pump.setAttribute("v", "manual");
//					}
//
//					if (borehole_access[i] != null) {
//						Element tag_borehole_access = osm.createElement("tag");
//						node.appendChild(tag_borehole_access);
//						tag_borehole_access.setAttribute("k", "access");
//						if (borehole_access[i].equals("yes")) {
//							tag_borehole_access.setAttribute("v", "private");
//						} else {
//							if (borehole_access[i].equals("no")) {
//								tag_borehole_access.setAttribute("v", "public");
//							} else {
//								tag_borehole_access.setAttribute("v", "multifamily");
//							}
//						}
//					}
//
//					if (handpump_condition[i] != null) {
//						Element tag_handpump_condition = osm.createElement("tag");
//						node.appendChild(tag_handpump_condition);
//
//						tag_handpump_condition.setAttribute("k", "condition");
//						if (handpump_condition[i].equals("good")) {
//							tag_handpump_condition.setAttribute("v", "good");
//						} else {
//							if (handpump_condition[i].equals("no")) {
//								tag_handpump_condition.setAttribute("v", "deficient");
//							} else {
//								tag_handpump_condition.setAttribute("v", "fair");
//							}
//						}
//					}
//
//					if (villageName[i] != null) {
//						Element tag_villageName = osm.createElement("tag");
//						node.appendChild(tag_villageName);
//
//						tag_villageName.setAttribute("k", "name");
//						tag_villageName.setAttribute("v", villageName[i]);
//					}
//
//					if (altVillageName[i] != null) {
//
//						Element tag_altVillageName = osm.createElement("tag");
//						node.appendChild(tag_altVillageName);
//
//						tag_altVillageName.setAttribute("k", "alt_name");
//						tag_altVillageName.setAttribute("v", altVillageName[i]);
//					}

					Element tag_source = osm.createElement("tag");
					node.appendChild(tag_source);

					tag_source.setAttribute("k", "source");
					tag_source.setAttribute("v", "MSF Survey");

				}
			}

			// write the content into XML file
			TransformerFactory transformerFactory
			= TransformerFactory.newInstance();
			Transformer transformer
			= transformerFactory.newTransformer();
			DOMSource source = new DOMSource(osm);
			StreamResult result
			= new StreamResult(new File(Main.out));
			transformer.transform(source, result);
			// Output to console for testing
			//            StreamResult consoleResult
			//                    = new StreamResult(System.out);
			//            transformer.transform(source, consoleResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void constructElement(Document osm,Element node, String key, HashMap<String, String> value){
		if (value != null) {
			Element tag = osm.createElement("tag");
			node.appendChild(tag);
			tag.setAttribute("k", key);
			tag.setAttribute("v", translate(value.get(key), keys, dict));
		}

	}
	public String translate(String value,ArrayList<String> keys, HashMap<String, HashMap<String,String>> dict){
		
		for (String key:keys){ //iterating through each column name
			if(dict.containsKey(key)){
				HashMap<String,String> dic = dict.get(key);// getting the dictionary for that column
				if(dic.containsKey(value)){
					return (dic.get(value));//returning standard msf translations
				}else {
					return value;
				}
			}else{ return value;}
		}
		return value;
	}
		
}
