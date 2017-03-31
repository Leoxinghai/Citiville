package com.xiyu.util;

import java.util.*;

public class Array {
	private Vector vec;
	public Array(){
		vec = new Vector();
	}
	
	public Array(int size){
		vec = new Vector(size);
	}

	public Array(Array array){
		vec = new Vector();
		vec.add(array);
	}

	public Array(String obj){
		vec = new Vector();
		vec.add(obj);
	}

	public Array(String[] values){
		vec = new Vector();
		for(int i=0 ;i< values.length;i++) {
			vec.add(values[i]);
		}
	}

	public Array(int[] values){
		vec = new Vector();
		for(int i=0 ;i< values.length;i++) {
			vec.add(values[i]);
		}
	}
	
	public Array(double[] values){
		vec = new Vector();
		for(int i=0 ;i< values.length;i++) {
			vec.add(values[i]);
		}
	}

	public Array(Object[] values){
		vec = new Vector();
		for(int i=0 ;i< values.length;i++) {
			vec.add(values[i]);
		}
	}
	
	public Array(BitmapData obj) {
		vec = new Vector();
		vec.add(obj);
	}

	
	public void push(Object obj) {
		vec.add(obj);
	}


	public Object elementAt(int index) {
		return vec.elementAt(index);
	}

	public Object get(int index) {
		return vec.elementAt(index);
	}

	public void add(int index, Object obj) {
		vec.add(index, obj);
	}

	public int length() {
		return vec.size();
	}
	public int size() {
		return vec.size();
	}

	public int indexOf(Object obj) {
		return vec.indexOf(obj);
	}

	public Array splice(int index, int flag, Object obj) {
		vec.add(index, obj);
		return this;
	}
	
	public Array splice(int index, int flag) {
		vec.remove(index);
		return this;
	}
	
	public void unshift(Object obj) {
		vec.add(obj);
	}

	public Object shift() {
		Object obj = vec.remove(0);
		return obj;
	}
	
	public Array filter(Filter filter) {
		Vector result = new Vector();
		for(int i =0 ;i<vec.size();i++) {
			Object obj = vec.elementAt(i);
			if(filter.doFilt(obj)) {
				result.add(obj);
			}
		}
		vec = result;
		return this;
	}
	
	public Object pop() {
		Object obj = null;
		if(vec.size()>0) {
			obj = vec.remove(0);
		}
		return obj;
	}
	
	public Array clone() {
		Array tmp = new Array();
		tmp.vec = (Vector)this.vec.clone();
		return tmp;
	}
}

