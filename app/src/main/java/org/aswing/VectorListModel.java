/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;


import org.aswing.util.List;

/**
 * The mutable list model vector implementation.
 * @author iiley
 */
public class VectorListModel extends AbstractListModel implements MutableListModel, List{

	protected Array _elements ;
	public static  int CASEINSENSITIVE =1;
	public static  int DESCENDING =2;
	public static  int UNIQUESORT =4;
	public static  int RETURNINDEXEDARRAY =8;
	public static  int NUMERIC =16;

	/**
	 * Create a VectorListModel instance.
	 * @param initalData (optional)the to be copid to the model.
	 */
	public  VectorListModel (Array initalData =null ){
		super();
		if(initalData != null){
			_elements = initalData.concat();
		}else{
			_elements = new Array();
		}
	}


	public Object get(int i ) {
		return _elements.get(i);
	}

	/**
	 * implemented ListMode
	 */
	public Object getElementAt (int i ) {
		return _elements.get(i);
	}

	public void  append (Object obj ,int index =-1){
		if(index == -1){
			index = _elements.length();
			_elements.push(obj);
		}else{
			_elements.splice(index, 0, obj);
		}
		fireIntervalAdded(this, index, index);
	}

	public  replaceAt (int index ,*)obj *{
		if(index<0 || index>= size()){
			return null;
		}
		oldObj = _elements.get(index);
		_elements.put(index,  obj);
		fireContentsChanged(this, index, index, [oldObj]);
		return oldObj;
	}

	/**
	 * Append all the elements of a array(arr) to the specified position of the
	 * vector.
	 * @param arr the elements array
	 * @param index the position to be append, default is -1 means the end of the vector.
	 */
	public void  appendAll (Array arr ,int index =-1){
		if(arr == null || arr.length() <= 0){
			return;
		}
		if(index == -1){
			index = _elements.length();
		}
		if(index == 0){
			_elements = arr.concat(_elements);
		}else if(index == _elements.length()){
			_elements = _elements.concat(arr);
		}else{
			Array right =_elements.splice(index );
			_elements = _elements.concat(arr);
			_elements = _elements.concat(right);
		}
		fireIntervalAdded(this, index, index+arr.length-1);
	}

	/**
	 * Notice the listeners the specified obj's value changed.
	 */
	public void  valueChanged (Object obj) {
		valueChangedAt(indexOf(obj));
	}

	/**
	 * Notice the listeners the specified obj's value changed.
	 */
	public void  valueChangedAt (int index ){
		if(index>=0 && index<_elements.length()){
			fireContentsChanged(this, index, index, []);
		}
	}

	/**
	 * Notice the listeners the specified range values changed.
	 * .get(from, to)(include "from" and "to").
	 */
	public void  valueChangedRange (int from ,int to ){
		fireContentsChanged(this, from, to, []);
	}

	public Object removeAt (int index ) {
		if(index < 0 || index >= size()){
			return null;
		}
		obj = _elements.get(index);
		_elements.splice(index, 1);
		fireIntervalRemoved(this, index, index, [obj]);
		return obj;
	}

	public Object remove (Object obj) {
		int i =indexOf(obj );
		if(i>=0){
			return removeAt(i);
		}else{
			return null;
		}
	}

	/**
	 * Removes from this List all of the elements whose index is between fromIndex,
	 * and toIndex(both inclusive). Shifts any succeeding elements to the left (reduces their index).
	 * This call shortens the ArrayList by (toIndex - fromIndex) elements. (If toIndex==fromIndex,
	 * this operation has no effect.)
	 * @return the elements were removed from the vector
	 */
	public Array  removeRange (int fromIndex ,int toIndex ){
		if(_elements.length() > 0){
			fromIndex = Math.max(0, fromIndex);
			toIndex = Math.min(toIndex, _elements.length()-1);
			if(fromIndex > toIndex){
				return new Array();
			}else{
				Array removed =_elements.splice(fromIndex ,toIndex -fromIndex +1);
				fireIntervalRemoved(this, fromIndex, toIndex, removed);
				return removed;
			}
		}else{
			return new Array();
		}
	}

	/**
	 * @see #removeAt()
	 */
	public void  removeElementAt (int index ){
		removeAt(index);
	}

	/**
	 * @see #append()
	 */
	public void  insertElementAt (Object item ,int index ){
		append(item, index);
	}

	public int  indexOf (Object obj) {
		for(int i =0;i <_elements.length();i ++){
			if(_elements.get(i) == obj){
				return i;
			}
		}
		return -1;
	}

	public boolean  contains (*)obj {
		return indexOf(obj) >=0;
	}

	public void  appendList (List list ,int index =-1){
		appendAll(list.toArray(), index);
	}

	public Object pop () {
		if(size() > 0){
			return removeAt(size()-1);
		}else{
			return null;
		}
	}

	public Object shift () {
		if(size() > 0){
			return removeAt(0);
		}else{
			return null;
		}
	}


	public Object first () {
		return _elements.get(0);
	}

	public Object last () {
		return _elements.get(_elements.length - 1);
	}

	public int  size (){
		return _elements.length;
	}

	public boolean  isEmpty (){
		return _elements.length <= 0;
	}

	/**
	 * Implemented ListMode
	 */
	public int  getSize (){
		return size();
	}

	public void  clear (){
		int ei =size ()-1;
		if(ei >= 0){
			Array temp =toArray ();
			_elements.splice(0, 0);
			fireIntervalRemoved(this, 0, ei, temp);
		}
	}

	public Array  toArray (){
		return _elements.concat();;
	}

	/**
	 * Returns a array that contains elements start with startIndex and has length elements.
	 * @param startIndex the element started index(include)
	 * @param length length of the elements, if there is not enough elements left, return the elements ended to the end of the vector.
	 */
	public Array  subArray (int startIndex ,int length ){
		if(size() == 0 || length <= 0){
			return new Array();
		}
		return _elements.slice(startIndex, Math.min(startIndex+length, size()));
	}

	public Array  sort (Object compare ,int options ){
		Array returned =_elements.sort(compare ,options );
		fireContentsChanged(this, 0, _elements.length-1, []);
		return returned;
	}

	public Array  sortOn (Object key ,int options ){
		Array returned =_elements.sortOn(key ,options );
		fireContentsChanged(this, 0, _elements.length-1, []);
		return returned;
	}

	public String  toString (){
		return "VectorListModel : " + _elements.toString();
	}
}


