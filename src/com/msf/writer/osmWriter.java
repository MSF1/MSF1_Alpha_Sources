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

			for (int i = 0; i < rows.length; i++) {
				//  node element
				if (rows[i].get(Main.getLatColName()) != null && rows[i].get(Main.getLonColName()) != null) {//Checking to ensure the node has coordinates, else it's not written
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
					rows[i].remove(Main.getLatColName());
					node.setAttributeNode(attr3);

					Attr attr4 = osm.createAttribute("lon");
					attr4.setValue(rows[i].get(Main.getLonColName()));
					rows[i].remove(Main.getLonColName());
					node.setAttributeNode(attr4);

					Attr attr5 = osm.createAttribute("visible");
					attr5.setValue("true");
					node.setAttributeNode(attr5);
					
					
					//tag node elements and attributes
					for(String key:rows[i].keySet()){
					constructElement(osm, node, key, rows[i]);
					}

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
	public void constructElement(Document osm,Element node, String key, HashMap<String, String> row_values){
		if (row_values != null) {
			Element tag = osm.createElement("tag");
			node.appendChild(tag);
			tag.setAttribute("k", key);
			tag.setAttribute("v", translate(row_values.get(key),dict.get(key)));
		}

	}
	public String translate(String value, HashMap<String,String> dict){
		if(dict != null){
			if(dict.containsKey(value)){
					return (dict.get(value));//returning standard msf translations
				}else {
					return value;
				}
	}
	else{
		return value;
	}
}
	}
