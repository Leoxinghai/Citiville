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


import org.aswing.geom.IntPoint;
import flash.geom.Rectangle;

/**
 * A Rectangle specifies an area in a coordinate space that is enclosed by the Rectangle 
 * object's top-left point (x, y) in the coordinate space, its width, and its height.
 */
public class IntRectangle {
	public int x =0;
	public int y =0;
	public int width =0;
	public int height =0;
	
	/**
	 * Creates a rectangle.
	 */
	public  IntRectangle (int x =0,int y =0,int width =0,int height =0){
		setRectXYWH(x, y, width, height);
	}
	

	/**
	 * Return a Point instance with same value.
	 */
	public Rectangle  toRectangle (){
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Sets the location with a <code>Point</code>, the value will be transfer to int.
	 * @param p the location to be set.
	 */
	public void  setWithRectangle (Rectangle r ){
		x = r.x;
		y = r.y;
		width = r.width;
		height = r.height;
	}
	
	/**
	 * Create a int point with point.
	 */
	public static IntRectangle  creatWithRectangle (Rectangle r ){
		return new IntRectangle(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * Sets the rectangle to be as same as rect.
	 */
	public void  setRect (IntRectangle rect ){
		setRectXYWH(rect.x, rect.y, rect.width, rect.height);
	}
	
	/**
	 * Sets the rect with x, y, width and height.
	 */
	public void  setRectXYWH (int x ,int y ,int width ,int height ){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the x, y property of the rectangle.
	 */
	public void  setLocation (IntPoint p ){
		this.x = p.x;
		this.y = p.y;
	}
	
	/**
	 * Sets the width and height properties of the rectangle
	 */
	public void  setSize (IntDimension size ){
		this.width = size.width;
		this.height = size.height;
	}
	
	
	/**
	 * Returns the size of this rectangle.
	 */
	public IntDimension  getSize (){
		return new IntDimension(width, height);
	}
	
	/**
	 * Returns the location of this rectangle.
	 */
	public IntPoint  getLocation (){
		return new IntPoint(x, y);
	}
	
    /**
     * Computes the union of this <code>Rectangle</code> with the 
     * specified <code>Rectangle</code>. Returns a new 
     * <code>Rectangle</code> that 
     * represents the union of the two rectangles
     * @param r the specified <code>Rectangle</code>
     * @return    the smallest <code>Rectangle</code> containing both 
     *		  the specified <code>Rectangle</code> and this 
     *		  <code>Rectangle</code>.
     */
    public IntRectangle  union (IntRectangle r ){
		int x1 =Math.min(x ,r.x );
		int x2 =Math.max(x +width ,r.x +r.width );
		int y1 =Math.min(y ,r.y );
		int y2 =Math.max(y +height ,r.y +r.height );
		return new IntRectangle(x1, y1, x2 - x1, y2 - y1);
    }
    
    /**
     * Resizes the <code>Rectangle</code> both horizontally and vertically.
     * <br><br>
     * This method modifies the <code>Rectangle</code> so that it is 
     * <code>h</code> units larger on both the left and right side, 
     * and <code>v</code> units larger at both the top and bottom. 
     * <br><br>
     * The new <code>Rectangle</code> has (<code>x&nbsp;-&nbsp;h</code>, 
     * <code>y&nbsp;-&nbsp;v</code>) as its top-left corner, a 
     * width of 
     * <code>width</code>&nbsp;<code>+</code>&nbsp;<code>2h</code>, 
     * and a height of 
     * <code>height</code>&nbsp;<code>+</code>&nbsp;<code>2v</code>. 
     * <br><br>
     * If negative values are supplied for <code>h</code> and 
     * <code>v</code>, the size of the <code>Rectangle</code> 
     * decreases accordingly. 
     * The <code>grow</code> method does not check whether the resulting 
     * values of <code>width</code> and <code>height</code> are 
     * non-negative. 
     * @param h the horizontal expansion
     * @param v the vertical expansion
     */
    public void  grow (int h ,int v ){
		x -= h;
		y -= v;
		width += h * 2;
		height += v * 2;
    }
    
    public void  move (int dx ,int dy ){
    	x += dx;
    	y += dy;
    }

    public void  resize (int dwidth =0,int dheight =0){
    	width += dwidth;
    	height += dheight;
    }
	
	public IntPoint  leftTop (){
		return new IntPoint(x, y);
	}
	
	public IntPoint  rightTop (){
		return new IntPoint(x + width, y);
	}
	
	public IntPoint  leftBottom (){
		return new IntPoint(x, y + height);
	}
	
	public IntPoint  rightBottom (){
		return new IntPoint(x + width, y + height);
	}
	
	public boolean  containsPoint (IntPoint p ){
		if(p.x < x || p.y < y || p.x > x+width || p.y > y+height){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 *
	 */
	public boolean  equals (Object o ){
		IntRectangle r =(IntRectangle)o;
		if(r == null) return false;
		return x===r.x && y===r.y && width===r.width && height===r.height;
	}
		
	/**
	 * Duplicates current instance.
	 * @return copy of the current instance.
	 */
	public IntRectangle  clone (){
		return new IntRectangle(x, y, width, height);
	}
		
	public String  toString (){
		return "IntRectangle.get(x:"+x+",y:"+y+", width:"+width+",height:"+height+")";
	}
	

}


