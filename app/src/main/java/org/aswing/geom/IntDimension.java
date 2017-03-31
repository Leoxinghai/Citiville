/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.geom;

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
 * The Dimension class encapsulates the width and height of a componentin a single object.<br>
 * Note this Class use <b>int</b> as its parameters on purpose to avoid problems that happended in aswing before.
 * @author iiley
 */
public class IntDimension{
	
	public int width =0;
	public int height =0;
	
	/**
	 * Creates a dimension.
	 */
	public  IntDimension (int width =0,int height =0){
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the size as same as the dim.
	 */
	public void  setSize (IntDimension dim ){
		this.width = dim.width;
		this.height = dim.height;
	}
	
	/**
	 * Sets the size with width and height.
	 */
	public void  setSizeWH (int width ,int height ){
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Increases the size by s and return its self(<code>this</code>).
	 * @return <code>this</code>.
	 */
	public IntDimension  increaseSize (IntDimension s ){
		width += s.width;
		height += s.height;
		return this;
	}
	
	/**
	 * Decreases the size by s and return its self(<code>this</code>).
	 * @return <code>this</code>.
	 */
	public IntDimension  decreaseSize (IntDimension s ){
		width -= s.width;
		height -= s.height;
		return this;
	}
	
	/**
	 * modify the size and return itself. 
	 */
	public IntDimension  change (int deltaW ,int deltaH ){
		width += deltaW;
		height += deltaH;
		return this;
	}
	
	/**
	 * return a new size with this size with a change.
	 */
	public IntDimension  changedSize (int deltaW ,int deltaH ){
		IntDimension s =new IntDimension(deltaW ,deltaH );
		return s;
	}
	
	/**
	 * Combines current and specified dimensions by getting max sizes
	 * and puts result into itself.
	 * @return the combined dimension itself.
	 */
	public IntDimension  combine (IntDimension d ){
		this.width = Math.max(this.width, d.width);	
		this.height = Math.max(this.height, d.height);
		return this;
	}

	/**
	 * Combines current and specified dimensions by getting max sizes
	 * and returns new IntDimension object
	 * @return a new dimension with combined size.
	 */
	public IntDimension  combineSize (IntDimension d ){
		return clone().combine(d);
	}
	
	/**
	 * return a new bounds with this size with a location.
	 * @param x the location x.
	 * @prame y the location y.
	 * @return the bounds.
	 */
	public IntRectangle  getBounds (int x =0,int y =0){
		IntPoint p =new IntPoint(x ,y );
		IntRectangle r =new IntRectangle ();
		r.setLocation(p);
		r.setSize(this);
		return r;
	}
	
	/**
	 * Returns whether or not the passing o is an same value IntDimension.
	 */
	public boolean  equals (Object o ){
		IntDimension d =(IntDimension)o;
		if(d == null) return false;
		return width===d.width && height===d.height;
	}

	/**
	 * Duplicates current instance.
	 * @return copy of the current instance.
	 */
	public IntDimension  clone (){
		return new IntDimension(width,height);
	}
	
	/**
	 * Create a big dimension for component.
	 * @return a IntDimension(100000, 100000)
	 */
	public static IntDimension  createBigDimension (){
		return new IntDimension(100000, 100000);
	}
	
	public String  toString (){
		return "IntDimension.get("+width+","+height+")";
	}

}


