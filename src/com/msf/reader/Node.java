package com.msf.reader;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private HashMap<String, String> attr;
	public Node(){
		attr = new HashMap<String, String>();
	}
	public void addAttr(String key, String value, ArrayList<String> keys){
		if(keys.contains(key)){
			attr.put(key, value);
		}
	}
	
	public HashMap<String, String> getAttr() {
		return attr;
	}

	public void setAttr(HashMap<String, String> attr) {
		this.attr = attr;
	}
}
