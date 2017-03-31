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
 * Utils functions about Array.
 * @author iiley
 */
public class ArrayUtils{

	/**
	 * Call the operation by pass each element of the array once.
	 * <p>
	 * for example:
	 * <pre>
	 * //hide all component in vector components
	 * ArrayUtils.each(
	 *     components,
	 * (Component c ){
	 *         c.setVisible(false);
	 *     });
	 * <pre>
	 * @param arr the array for each element will be operated.
	 * @param the operation function for each element
	 * @see Vector#each
	 */
	public static void  each (Array arr ,Function operation ){
		for(int i =0;i <arr.length ;i ++){
			operation(arr.get(i));
		}
	}

	/**
	 * Sets the size of the array. If the new size is greater than the current size,
	 * new undefined items are added to the end of the array. If the new size is less than
	 * the current size, all components at index newSize and greater are removed.
	 * @param arr the array to resize
	 * @param size the new size of this vector
	 */
	public static void  setSize (Array arr ,int size ){
		//TODO test this method
		if(size < 0) size = 0;
		if(size == arr.length()){
			return;
		}
		if(size > arr.length()){
			arr.put(size - 1,  undefined);
		}else{
			arr.splice(size);
		}
	}

	/**
	 * Removes the object from the array and return the index.
	 * @return the index of the object, -1 if the object is not in the array
	 */
	public static int  removeFromArray (Array arr ,Object obj ){
		for(int i =0;i <arr.length ;i ++){
			if(arr.get(i) == obj){
				arr.splice(i, 1);
				return i;
			}
		}
		return -1;
	}

	public static void  removeAllFromArray (Array arr ,Object obj ){
		for(int i =0;i <arr.length ;i ++){
			if(arr.get(i) == obj){
				arr.splice(i, 1);
				i--;
			}
		}
	}

	public static void  removeAllBehindSomeIndex (Array array ,int index ){
		if(index <= 0){
			array.splice(0, array.length());
			return;
		}
		int arrLen =array.length ;
		for(int i =index +1;i <arrLen ;i ++){
			array.pop();
		}
	}

	public static int  indexInArray (Array arr ,Object obj ){
		for(int i =0;i <arr.length ;i ++){
			if(arr.get(i) == obj){
				return i;
			}
		}
		return -1;
	}

	public static Array  cloneArray (Array arr ){
		return arr.concat();
	}
}


