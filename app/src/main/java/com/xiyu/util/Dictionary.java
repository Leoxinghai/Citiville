package com.xiyu.util;

import java.util.Hashtable;
import java.util.Vector;

public class Dictionary {
	private Hashtable dic;
	public Dictionary() {
		dic = new Hashtable();
	}
	
	public Object get(Object key) {
		return dic.get(key);
	}
	public Object get(int index) {
		return dic.values().toArray()[index];
	}

	public Object elementAt(Object key) {
		return dic.get(key);
	}


	public Object put(Object key,Object value) {
		return dic.put(key, value);
	}
	
	public int length() {
		return dic.size();
	}
	
	public Dictionary clone() {
		Dictionary tmp = new Dictionary();
		tmp.dic = (Hashtable)this.dic.clone();
		return tmp;
	}
}

