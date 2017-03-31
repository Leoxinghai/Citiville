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


import flash.geom.Point;
	
/**
 * A point with x and y coordinates in int.
 * @author iiley
 */
public class IntPoint{
	
	public int x =0;
	public int y =0;
	
	/**
	 * Constructor
	 */
	public  IntPoint (int x =0,int y =0){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Return a Point instance with same value.
	 */
	public Point  toPoint (){
		return new Point(x, y);
	}
	
	/**
	 * Sets the location with a <code>Point</code>, the value will be transfer to int.
	 * @param p the location to be set.
	 */
	public void  setWithPoint (Point p ){
		x = p.x;
		y = p.y;
	}
	
	/**
	 * Create a int point with point.
	 */
	public static IntPoint  creatWithPoint (Point p ){
		return new IntPoint(p.x, p.y);
	}
		
	/**
	 * Sets the location of this point as same as point p.
	 * @param p the location to be set.
	 */
	public void  setLocation (IntPoint p ){
		this.x = p.x;
		this.y = p.y;
	}
	
	/**
	 * Sets the location of this point with x and y.
	 * @param x the x coordinates.
	 * @param y the y coordinates.
	 */
	public void  setLocationXY (int x =0,int y =0){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Moves this point and return itself.
	 * @param dx delta of x.
	 * @param dy delta of y.
	 * @return the point itself.
	 */
	public IntPoint  move (int dx ,int dy ){
		x += dx;
		y += dy;
		return this;
	}
	
	/**
	 * Moves this point with an direction in radians and distance, then return itself.
	 * @param angle the angle in radians.
	 * @param distance the distance in pixels.
	 * @return the point itself.
	 */
	public IntPoint  moveRadians (int direction ,int distance ){
		x += Math.round(Math.cos(direction)*distance);
		y += Math.round(Math.sin(direction)*distance);
		return this;
	}
	
	
	
	/**
	 * Returns the point beside this point with direction and distance.
	 * @return the point beside.
	 */
	public IntPoint  nextPoint (double direction ,double distance ){
		return new IntPoint(x+Math.cos(direction)*distance, y+Math.sin(direction)*distance);
	}
	
	/**
	 * Returns the distance square between this point and passing point.
	 * @param p the another point.
	 * @return the distance square from this to p.
	 */
	public int  distanceSq (IntPoint p ){
		int xx =p.x ;
		int yy =p.y ;
		
		return ((x-xx)*(x-xx)+(y-yy)*(y-yy));	
	}


	/**
	 * Returns the distance between this point and passing point.
	 * @param p the another point.
	 * @return the distance from this to p.
	 */
	public int  distance (IntPoint p ){
		return Math.sqrt(distanceSq(p));
	}
    
    /**
     * Returns whether or not this passing object is a same value point.
     * @param toCompare the object to be compared.
     * @return equals or not.
     */
	public boolean  equals (Object o ){
		IntPoint toCompare =(IntPoint)o;
		if(toCompare == null) return false;
		return x===toCompare.x && y===toCompare.y;
	}

	/**
	 * Duplicates current instance.
	 * @return copy of the current instance.
	 */
	public IntPoint  clone (){
		return new IntPoint(x,y);
	}
    
    /**
    * 
    */
	public String  toString (){
		return "IntPoint.get("+x+","+y+")";
	}	

}


