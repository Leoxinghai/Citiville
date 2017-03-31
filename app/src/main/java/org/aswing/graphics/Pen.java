/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.graphics;

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


import flash.display.Graphics;

import org.aswing.ASColor;

/**
 * Pen encapsulate normal lineStyle properties. <br>
 * You can use pen to draw an ordinary shape. To draw gradient lines, refer to <code>org.aswing.graphics.GradientPen</code>
 * 
 * @see org.aswing.graphics.IPen
 * @see org.aswing.graphics.GradientPen
 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#lineStyle()
 * @author iiley
 */
public class Pen implements IPen{
	
	private int _thickness ;
	private ASColor _color ;
	private boolean _pixelHinting ;
	private String _scaleMode ;
	private String _caps ;
	private String _joints ;
	private double _miterLimit ;
	
	/**
	 * Create a Pen.
	 */
	public  Pen (ASColor color ,
				 thickness:uint=1, 
				 pixelHinting:Boolean = false, 
				 scaleMode:String = "normal", 
				 caps:String = null, 
				 joints:String = null, 
				 miterLimit:Number = 3){
				 	
		this._color = color;
		this._thickness = thickness;
		this._pixelHinting = pixelHinting;
		this._scaleMode = scaleMode;
		this._caps = caps;
		this._joints = joints;
		this._miterLimit = miterLimit;
	}
	
	public ASColor  getColor (){
		return _color;
	}
	
	/**
	 * 
	 */
	public void  setColor (ASColor color ){
		this._color=color;
	}
	
	public int  getThickness (){
		return _thickness;
	}
	
	/**
	 * 
	 */
	public void  setThickness (int thickness ){
		this._thickness=thickness;
	}
	
 	public boolean  getPixelHinting (){
 		return this._pixelHinting;
 	}
 	
 	/**
	 * 
	 */
 	public void  setPixelHinting (boolean pixelHinting ){
 		this._pixelHinting = pixelHinting;
 	}
	
	public String  getScaleMode (){
		return this._scaleMode;
	}
	
	/**
	 * 
	 */
	public void  setScaleMode (String scaleMode ="normal"){
		this._scaleMode =  scaleMode;
	}
	
	public String  getCaps (){
		return this._caps;
	}
	
	/**
	 * 
	 */
	public void  setCaps (String caps ){
		this._caps=caps;
	}
	
	public String  getJoints (){
		return this._joints;
	}
	
	/**
	 * 
	 */
	public void  setJoints (String joints ){
		this._joints=joints;
	}
	
	public double  getMiterLimit (){
		return this._miterLimit;
	}
	
	/**
	 * 
	 */
	public void  setMiterLimit (double miterLimit ){
		this._miterLimit=miterLimit;
	}
	
	/**
	 * @inheritDoc 
	 */
	public void  setTo (Graphics target ){
		target.lineStyle(_thickness, _color.getRGB(), _color.getAlpha(), _pixelHinting,_scaleMode,_caps,_joints,_miterLimit);
	}

}


