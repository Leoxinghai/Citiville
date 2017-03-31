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

	
import flash.utils.Dictionary;
	
/**
 * A collection that contains no duplicate elements. More formally, 
 * sets contain no pair of elements e1 and e2 such that e1 === e2.
 * @author iiley
 */
public class HashSet
	
	private Dictionary container ;
	private int length ;
	
	public  HashSet (){
		container = new Dictionary();
		length = 0;
	}
	
	public int  size (){
		return length;
	}
	
	public void  add (*)o {
		if(!contains(o)){
			length++;
		}
		container.put(o,  o);
	}
	
	public boolean  contains (*)o {
		return container.get(o) !== undefined;
	}
	
	public boolean  isEmpty (){
		return length == 0;
	}
	
	public boolean  remove (*)o {
		if(contains(o)){
			delete container.get(o);
			length--;
			return true;
		}else{
			return false;
		}
	}
	
	public void  clear (){
		container = new Dictionary();
		length = 0;
	}
	
	public void  addAll (Array arr ){
		for each(in i *arr ){
			add(i);
		}
	}
	
	public void  removeAll (Array arr ){
		for each(in i *arr ){
			remove(i);
		}
	}
	
	public boolean  containsAll (Array arr ){
		for(int i =0;i <arr.length ;i ++){
			if(!contains(arr.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public void  each (Function func ){
		for each(in i *container ){
			func(i);
		}
	}
	
	public Array  toArray (){
		Array arr =new Array(length );
		int index =0;
		for each(in i *container ){
			arr.put(index,  i);
			index ++;
		}
		return arr;
	}

}


