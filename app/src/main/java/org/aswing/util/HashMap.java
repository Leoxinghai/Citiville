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
 * To successfully store and retrieve (key->value) mapping from a HashMap.
 * HashMap accept any type of object to be the key: number, string, Object etc...
 * But it is only get fast accessing with string type keys. Others are slow.
 * <p>
 * ----------------------------------------------------------
 * This example creates a HashMap of friends. It uses the number of the friends as keys:
 * <listing>
 * person (name ,age ,sex ){
 *         this.name=name;
 *         this.age=age;
 *         this.sex=sex;
 *     }
 *friends =new HashMap ();
 *     friends.put("one", new person("iiley",21,"M"));
 *     friends.put("two", new person("gothic man",22,"M"));
 *     friends.put("three", new person("rock girl",19,"F"));
 * </listing>
 * </p>
 * <p>To retrieve a friends, use the following code:
 *
 * <listing>
 *thisperson =friends.get("two");
 *     if (thisperson != null) {
 *         trace("two name is "+thisperson.name);
 *         trace("two age is "+thisperson.age);
 *         trace("two sex is "+thisperson.sex);
 *     }else{
 *         trace("two is not in friends!");
 *     }
 * </listing>
 *</p>
 * @author iiley
 */
public class HashMap

    private int length ;
    private Dictionary content ;

 	public  HashMap (){
        length = 0;
        content = new Dictionary();
 	}

 	//-------------------public methods--------------------

 	/**
  	 * Returns the number of keys in this HashMap.
  	 */
 	public int  size (){
  		return length;
 	}

 	/**
  	 * Returns if this HashMap maps no keys to values.
  	 */
 	public boolean  isEmpty (){
  		return (length==0);
 	}

 	/**
  	 * Returns an Array of the keys in this HashMap.
  	 */
 	public Array  keys (){
  		Array temp =new Array(length );
  		int index =0;
  		for(in i *content ){
   			temp.put(index,  i);
   			index ++;
  		}
  		return temp;
 	}

 	/**
 	 * Call func(key) for each key.
 	 * @param func the function to call
 	 */
 	public void  eachKey (Function func ){
  		for(in i *content ){
  			func(i);
  		}
 	}

 	/**
 	 * Call func(value) for each value.
 	 * @param func the function to call
 	 */
 	public void  eachValue (Function func ){
  		for each(in i *content ){
  			func(i);
  		}
 	}

 	/**
  	 * Returns an Array of the values in this HashMap.
  	 */
 	public Array  values (){
  		Array temp =new Array(length );
  		int index =0;
  		for each(in i *content ){
   			temp.put(index,  i);
   			index ++;
  		}
  		return temp;
 	}

 	/**
  	 * Tests if some key maps into the specified value in this HashMap.
  	 * This operation is more expensive than the containsKey method.
  	 */
 	public boolean  containsValue (Object value){
  		for each(in i *content ){
   			if(i === value){
    			return true;
   			}
  		}
 		return false;
 	}

 	/**
  	 * Tests if the specified object is a key in this HashMap.
  	 * This operation is very fast if it is a string.
     * @param   key   The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
  	 */
 	public boolean  containsKey (*)key {
 		if(content.get(key) != undefined){
 			return true;
 		}
  		return false;
 	}

 	/**
 	 * Returns the value to which the specified key is mapped in this HashMap.
 	 * Return null if the key is not mapped to any value in this HashMap.
  	 * This operation is very fast if the key is a string.
     * @param   key the key whose associated value is to be returned.
     * @return  the value to which this map maps the specified key, or
     *          <tt>null</tt> if the map contains no mapping for this key
     *           or it is null value originally.
 	 */
 	public  (*)key *{
 		value = content.get(key);
 		if(value !== undefined){
 			return value;
 		}
  		return null;
 	}

 	/**
 	 * Same functionity method with different name to <code>get</code>.
 	 *
     * @param   key the key whose associated value is to be returned.
     * @return  the value to which this map maps the specified key, or
     *          <tt>null</tt> if the map contains no mapping for this key
     *           or it is null value originally.
 	 */
 	public  getValue (*)key *{
 		return get(key);
 	}

 	/**
 	 * Associates the specified value with the specified key in this map.
 	 * If the map previously contained a mapping for this key, the old value is replaced.
 	 * If value is null, means remove the key from the map.
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key. null to remove the key.
     * @return previous value associated with specified key, or <tt>null</tt>
     *	       if there was no mapping for key.  A <tt>null</tt> return can
     *	       also indicate that the HashMap previously associated
     *	       <tt>null</tt> with the specified key.
  	 */
 	public  put (*key ,Object value)*{
  		if(key == null){
   			throw new ArgumentError("cannot put a value with undefined or null key!");
   			return undefined;
  		}else if(value == null){
  			return remove(key);
  		}else{
  			boolean exist =containsKey(key );
 			if(!exist){
   				length++;
 			}
 			oldValue = this.get(key);
   			content.get(key)=value;
   			return oldValue;
  		}
 	}

 	/**
     * Removes the mapping for this key from this map if present.
     *
     * @param  key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or <tt>null</tt>
     *	       if there was no mapping for key.  A <tt>null</tt> return can
     *	       also indicate that the map previously associated <tt>null</tt>
     *	       with the specified key.
  	 */
 	public  remove (*)key *{
 		boolean exist =containsKey(key );
 		if(!exist){
 			return null;
 		}
  		temp = content.get(key);
   		delete content.get(key);
   		length--;
  		return temp;
 	}

 	/**
 	 * Clears this HashMap so that it contains no keys no values.
 	 */
 	public void  clear (){
  		length = 0;
  		content = new Dictionary();
 	}

 	/**
 	 * Return a same copy of HashMap object
 	 */
 	public HashMap  clone (){
  		HashMap temp =new HashMap ();
  		for(in i *content ){
   			temp.put(i, content.get(i));
  		}
  		return temp;
 	}

 	public String  toString (){
  		Array ks =keys ();
  		Array vs =values ();
  		String temp ="HashMap Content:\n";
  		for(int i =0;i <ks.length ;i ++){
   			temp += ks.get(i)+" -> "+vs.get(i) + "\n";
  		}
  		return temp;
 	}

}


