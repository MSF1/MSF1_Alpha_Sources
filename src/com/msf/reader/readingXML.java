/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msf.reader;

import com.msf.main.Main;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.msf.*;
import com.msf.writer.osmWriter;

/**
 *
 * @author s1525754
 */
public class readingXML {

    public static String lat;
    public static String lon;
    public static String borehole_access;
    public static String handpump_condition;
    public static String waterPointName;
    public static String villageName;
    public static String altVillageName;

    public static void finish() {

        String[] lat1 = new String[2];
        String[] lon1 = new String[2];
        String[] borehole_access1 = new String[2];
        String[] handpump_condition1 = new String[2];
        String[] waterPointName1 = new String[2];
        String[] villageName1 = new String[2];
        String[] altVillageName1 = new String[2];
        readingXML.xmlReader(Main.get);
        lat1[1] = lat;
        lon1[1] = lon;
        borehole_access1[1] = borehole_access;
        handpump_condition1[1] = handpump_condition;
        waterPointName1[1] = waterPointName;
        villageName1[1] = villageName;
        altVillageName1[1] = altVillageName;

//        osmWriter.osmWriter(lat1, lon1, borehole_access1, handpump_condition1, waterPointName1, villageName1, altVillageName1);

    }

    public static void xmlReader(String xml_file_location) {

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

                        lat = a.substring(0, a.indexOf(" "));

                        a = a.substring(a.indexOf(" ") + 1, a.length());

                        lon = a.substring(0, a.indexOf(" "));
                        loc = false;
                    }

                    if (wpn) {
                        waterPointName = new String(ch, start, length);
                        wpn = false;
                    }

                    if (hpw) {
                        handpump_condition = new String(ch, start, length);
                        hpw = false;
                    }

                    if (bhp) {
                        borehole_access = new String(ch, start, length);
                        bhp = false;
                    }

                    if (vn) {
                        villageName = new String(ch, start, length);
                        vn = false;
                    }
                    if (avn) {
                        altVillageName = new String(ch, start, length);

                    }
                }

            };

            saxParser.parse(xml_file_location, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
