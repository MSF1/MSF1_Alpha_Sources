/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msf.reader;

import com.msf.main.Main;

import java.util.ArrayList;

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
	 private ArrayList <String> lat = new ArrayList<String>();
	 private ArrayList <String> lon = new ArrayList<String>();
	 private ArrayList <String> borehole_access = new ArrayList<String>();
	 private ArrayList <String> handpump_condition = new ArrayList<String>();
	 private ArrayList <String> waterPointName = new ArrayList<String>();
	 private ArrayList <String> villageName = new ArrayList<String>();
	 private ArrayList <String> altVillageName = new ArrayList<String>();
	 
	  public readingOSM (String xml_file_location) {
	        try {

	            SAXParserFactory factory = SAXParserFactory.newInstance();
	            SAXParser saxParser = factory.newSAXParser();

	            DefaultHandler handler = new DefaultHandler() {

	                boolean loc = false;
	                boolean bhp = false;
	                boolean hpw = false;
	                boolean wpn = false;
	                boolean vn = false;
	                boolean avn = false;

	                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

	                    if (qName.equalsIgnoreCase("LOCATION")) {
	                        loc = true;
	                    }
	                    if (qName.equalsIgnoreCase("BOREHOLE_PROTECTED")) {
	                        bhp = true;
	                    }
	                    if (qName.equalsIgnoreCase("HANDPUMP_WORKING")) {
	                        hpw = true;
	                    }
	                    if (qName.equalsIgnoreCase("WATERPOINT_NAME")) {
	                        wpn = true;
	                    }
	                    if (qName.equalsIgnoreCase("VILLAGE_NAME")) {
	                        vn = true;
	                    }
	                    if (qName.equalsIgnoreCase("ALT_VILLAGE_NAME")) {
	                        avn = true;
	                    }

	                }

	                @Override
	                public void characters(char ch[], int start, int length) throws SAXException {

	                    if (loc) {
	                        String a = new String(ch, start, length);

	                        lat.add(a.substring(0, a.indexOf(" ")));

	                        a = a.substring(a.indexOf(" ") + 1, a.length());

	                        lon.add(a.substring(0, a.indexOf(" ")));
	                        loc = false;
	                    

	                    if (wpn) {
	                        waterPointName.add(new String(ch, start, length));
	                        wpn = false;
	                    }else {waterPointName.add(null);}

	                    if (hpw) {
	                        handpump_condition.add(new String(ch, start, length));
	                        hpw = false;
	                    }else {handpump_condition.add(null);}

	                    if (bhp) {
	                        borehole_access.add(new String(ch, start, length));
	                        bhp = false;
	                    }else {borehole_access.add(null);}

	                    if (vn) {
	                        villageName.add(new String(ch, start, length));
	                        vn = false;
	                    } else {villageName.add(null);}
	                    if (avn) {
	                        altVillageName.add(new String(ch, start, length));
	                        avn = false;
	                    } else {altVillageName.add(null);}
	                }
	                }
	            };

	            saxParser.parse(xml_file_location, handler);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	  public String[] getLat(){
		  String [] la = new String[lat.size()];
			 return lat.toArray(la);
		 }
		 public String[] getLon(){
			 String [] lo = new String[lon.size()];
			 return lon.toArray(lo);
		 }
		 public String[] getBA(){
			 String [] ba = new String[borehole_access.size()];
			 return borehole_access.toArray(ba);
		 }
		 public String[] getHC(){
			 String [] hc = new String[handpump_condition.size()];
			 return handpump_condition.toArray(hc);
		 }
		 public String[] getWPN(){
			 String [] wpn = new String[waterPointName.size()];
			 return waterPointName.toArray(wpn);
		 }
		 public String[] getVN(){
			 String [] vn = new String[villageName.size()];
			 return (String[]) villageName.toArray(vn);
		 }
		 public String[] getAVN(){
			 String [] avn = new String[altVillageName.size()];
			 return (String[]) altVillageName.toArray(avn);
		 }
		 
    public static void finish() {

        readingOSM xml = new readingOSM(Main.get);
        String[] lat1 = xml.getLat();
        String[] lon1 = xml.getLon();
        String[] borehole_access1 = xml.getBA();
        String[] handpump_condition1 = xml.getHC();
        String[] waterPointName1 = xml.getWPN();
        String[] villageName1 = xml.getVN();
        String[] altVillageName1 = xml.getAVN();

        osmWriter.writer(lat1, lon1, borehole_access1, handpump_condition1, waterPointName1, villageName1, altVillageName1);

    }

  
}
