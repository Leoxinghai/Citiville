/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.util;

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


/**
 * ArrayList, a List implemented based on Array
 *
 *
 * @author firdosh
 * @author iiley
 */
public class ArrayList implements List{

	protected Array _elements ;
	public static  int CASEINSENSITIVE =1;
	public static  int DESCENDING =2;
	public static  int UNIQUESORT =4;
	public static  int RETURNINDEXEDARRAY =8;
	public static  int NUMERIC =16;

	public  ArrayList (){
		_elements=new Array();
	}

	/**
	 * Call the operation by pass each element once.
	 * <p>
	 * for example:
	 * <pre>
	 * //hide all component in vector components
	 * components.each(
	 * (Component c ){
	 *         c.setVisible(false);
	 *     });
	 * <pre>
	 * @param operation the operation function for each element
	 */
	public void  each (Function operation ){
		for(int i =0;i <_elements.length ;i ++){
			operation(_elements.get(i));
		}
	}

	/**
	 * Call the operation by pass each element once without the specified element.
	 * <p>
	 * for example:
	 * <pre>
	 * //hide all component in list components without firstOne component
	 *Component firstOne =the first one ;
	 * components.eachWithout(
	 * 	   firstOne,
	 * (Component c ){
	 *         c.setVisible(false);
	 *     });
	 * <pre>
	 * @param obj the which will not be operated.
	 * @param operation the operation function for each element
	 */
	public void  eachWithout (Object obj ,Function operation ){
		for(int i =0;i <_elements.length ;i ++){
			if(_elements.get(i) != obj){
				operation(_elements.get(i));
			}
		}
	}

	public Object get(int i ) {
		return _elements.get(i);
	}

	public Object elementAt (int i ) {
		return get(i);
	}

	/**
	 * Append the object to the ArrayList
	 *
	 * @param obj the object to append
	 * @param index where to append, if omited, appedn to the last position.
	 */
	public void  append (*obj ,int index =-1){
		if(index == -1){
			_elements.push(obj);
		}else{
			_elements.splice(index, 0, obj);
		}
	}

	public void  appendAll (Array arr ,int index =-1){
		if(arr == null || arr.length <= 0){
			return;
		}
		if(index == -1 || index == _elements.length()){
			_elements = _elements.concat(arr);
		}else if(index == 0){
			_elements = arr.concat(_elements);
		}else{
			Array right =_elements.splice(index );
			_elements = _elements.concat(arr);
			_elements = _elements.concat(right);
		}
	}

	public  replaceAt (int index ,*)obj *{
		if(index<0 || index>= size()){
			return null;
		}
		else{
			Object oldObj =_elements.get(index) ;
			_elements.put(index,  obj);
			return oldObj;
		}
	}

	public Object removeAt (int index ) {
		if(index<0 || index>= size()){
			return null;
		}
		else{
			Object obj =_elements.get(index) ;
			_elements.splice(index, 1);
			return obj;
		}
	}
	public  remove (*)obj *{
		int i =indexOf(obj );
		if(i>=0){
			return removeAt(i);
		}else{
			return null;
		}
	}

	/**
	 * Removes from this List all of the elements whose index is between fromIndex,
	 * inclusive and toIndex inclusive. Shifts any succeeding elements to the left (reduces their index).
	 * This call shortens the ArrayList by (toIndex - fromIndex) elements. (If toIndex less than fromIndex,
	 * this operation has no effect.)
	 * @return the elements were removed from the vector
	 */
	public Array  removeRange (int fromIndex ,int toIndex ){
		fromIndex = Math.max(0, fromIndex);
		toIndex = Math.min(toIndex, _elements.length-1);
		if(fromIndex > toIndex){
			return new Array();
		}else{
			return _elements.splice(fromIndex, toIndex-fromIndex+1);
		}
	}

	public int  indexOf (*)obj {
		for(int i =0;i <_elements.length ;i ++){
			if(_elements.get(i) === obj){
				return i;
			}
		}
		return -1;
	}

	public void  appendList (List list ,int index =-1){
		appendAll(list.toArray(), index);
	}

	public Object pop () {
		if(size() > 0){
			return _elements.pop();
		}else{
			return null;
		}
	}

	public Object shift () {
		if(size() > 0){
			return _elements.shift();
		}else{
			return undefined;
		}
	}

	public int  lastIndexOf (*)obj {
		for(int i =_elements.length -1;i >=0;i --){
			if(_elements.get(i) === obj){
				return i;
			}
		}
		return -1;
	}

	public boolean  contains (*)obj {
		return indexOf(obj) >=0;
	}

	public Object first () {
		return _elements.get(0);
	}

	public Object last () {
		return _elements.get(_elements.length-1);
	}

	public int  size (){
		return _elements.length;
	}

	public void  setElementAt (int index ,*)element {
		replaceAt(index, element);
	}

	public int  getSize (){
		return size();
	}

	public void  clear (){
		if(!isEmpty()){
			_elements.splice(0);
			_elements=new Array();
		}
	}

	public ArrayList  clone (){
		ArrayList cloned =new ArrayList ();
		for(int i =0;i <_elements.length ;i ++){
			cloned.append(_elements.get(i));
		}
		return cloned;
	}

	public boolean  isEmpty (){
		if(_elements.length>0)
			return false;
		else
			return true;
	}

	public Array  toArray (){
		return _elements.concat();
	}

	/**
	 * Returns a array that contains elements start with startIndex and has length elements.
	 * @param startIndex the element started index(include)
	 * @param length length of the elements, if there is not enough elements left, return the elements ended to the end of the vector.
	 */
	public Array  subArray (int startIndex ,int length ){
		return _elements.slice(startIndex, Math.min(startIndex+length, size()));
	}

	public Array  sort (Object compare ,int options ){
		return _elements.sort(compare, options);
	}

	public Array  sortOn (Object key ,int options ){
		return _elements.sortOn(key, options);
	}

	public String  toString (){
		return "ArrayList : " + _elements.toString();
	}

}


