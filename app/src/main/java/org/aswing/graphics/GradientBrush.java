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


import org.aswing.graphics.IBrush;
import flash.display.Graphics;
import flash.geom.Matrix;
import flash.display.GradientType;

/**
 * GradientBrush encapsulate the fill paramters for flash.display.Graphics.beginGradientFill()
 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#beginGradientFill()
 * @author iiley
 */
public class GradientBrush implements IBrush{
	
	public static  String LINEAR =GradientType.LINEAR ;
	public static  String RADIAL =GradientType.RADIAL ;
	
	private String fillType ;
	private Array colors ;
	private Array alphas ;
	private Array ratios ;
	private Matrix matrix ;
	private String spreadMethod ;
	private String interpolationMethod ;
	private double focalPointRatio ;
	
	/**
	 * Create a GradientBrush object<br>
	 * you can refer the explaination for the paramters from Adobe's doc
	 * to create a Matrix, you can use matrix.createGradientBox() from the matrix object itself
	 * 
	 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#beginGradientFill()
	 * @see http://livedocs.macromedia.com/flex/2/langref/flash/geom/Matrix.html#createGradientBox()
	 */
	public  GradientBrush (String fillType ,Array colors ,Array alphas ,Array ratios ,Matrix matrix ,
					spreadMethod:String = "pad", interpolationMethod:String = "rgb", focalPointRatio:Number = 0){
		this.fillType = fillType;
		this.colors = colors;
		this.alphas = alphas;
		this.ratios = ratios;
		this.matrix = matrix;
		this.spreadMethod = spreadMethod;
		this.interpolationMethod = interpolationMethod;
		this.focalPointRatio = focalPointRatio;
	}
	
	public String  getFillType (){
		return fillType;
	}
	
	/**
	 * 
	 */
	public void  setFillType (String t ){
		fillType = t;
	}
		
	public Array  getColors (){
		return colors;
	}
	
	/**
	 * 
	 */
	public void  setColors (Array cs ){
		colors = cs;
	}
	
	public Array  getAlphas (){
		return alphas;
	}
	
	/**
	 * Pay attention that the value in the array should be between 0-1. if the value is greater than 1, 1 will be used, if the value is less than 0, 0 will be used
	 */
	public void  setAlphas (Array alphas ){
		this.alphas = alphas;
	}
	
	public Array  getRatios (){
		return ratios;
	}
	
	/**
	 * Ratios should be between 0-255, if the value is greater than 255, 255 will be used, if the value is less than 0, 0 will be used
	 */
	public void  setRatios (Array ratios ){
		ratios = ratios;
	}
	
	public Object  getMatrix (){
		return matrix;
	}
	
	/**
	 * 
	 */
	public void  setMatrix (Matrix m ){
		matrix = m;
	}
	
	/**
	 * @inheritDoc 
	 */
	public void  beginFill (Graphics target ){
		target.beginGradientFill(fillType, colors, alphas, ratios, matrix, 
			spreadMethod, interpolationMethod, focalPointRatio);
	}
	
	/**
	 * @inheritDoc 
	 */
	public void  endFill (Graphics target ){
		target.endFill();
	}	

}


