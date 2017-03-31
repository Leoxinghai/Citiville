/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.tree;

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


import org.aswing.util.HashMap;

/**
 * A hash map that accept TreePath key.
 * @author iiley
 */
public class TreePathMap{

	private HashMap map ;
	private HashMap keyMap ;

	public  TreePathMap (){
		map = new HashMap();
		keyMap = new HashMap();
	}

 	public int  size (){
  		return map.size();
 	}

 	public boolean  isEmpty (){
  		return map.isEmpty();
 	}

 	public Array  keys (){
  		return keyMap.values();
 	}

 	/**
  	 * Returns an Array of the values in this HashMap.
  	 */
 	public Array  values (){
  		return map.values();
 	}

 	public boolean  containsValue (Object value){
 		return map.containsValue(value);
 	}

 	public boolean  containsKey (TreePath key ){
 		return map.containsKey(key.getLastPathComponent());
 	}

 	public Object (TreePath key ) {
  		return map.getValue(key.getLastPathComponent());
 	}

 	public Object getValue (TreePath key ) {
  		return map.getValue(key.getLastPathComponent());
 	}

 	public Object put (TreePath key ,Object value) {
 		keyMap.put(key.getLastPathComponent(), key);
  		return map.put(key.getLastPathComponent(), value);
 	}

 	public Object remove (TreePath key ) {
 		keyMap.remove(key.getLastPathComponent());
 		return map.remove(key.getLastPathComponent());
 	}

 	public void  clear (){
 		keyMap.clear();
  		map.clear();
 	}

 	/**
 	 * Return a same copy of HashMap object
 	 */
 	public TreePathMap  clone (){
  		TreePathMap temp =new TreePathMap ();
  		temp.map = map.clone();
  		temp.keyMap = keyMap.clone();
  		return temp;
 	}

 	public String  toString (){
  		return map.toString();
 	}
}


