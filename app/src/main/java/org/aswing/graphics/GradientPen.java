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

		
import flash.geom.Matrix;
import flash.display.Graphics;

/**
 * GradientPen to draw Gradient lines.
 * @see org.aswing.graphics.Graphics2D
 * @see org.aswing.graphics.IPen
 * @see org.aswing.graphics.Pen
 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#lineGradientStyle()
 * @author n0rthwood
 */		
public class GradientPen implements IPen{
	
	private int thickness ;
	private String fillType ;
	private Array colors ;
	private Array alphas ;
	private Array ratios ;
	private Matrix matrix ;
	private String spreadMethod ;
	private String interpolationMethod ;
	private double focalPointRatio ;

	public  GradientPen (int thickness ,String fillType ,Array colors ,Array alphas ,Array ratios ,Matrix matrix =null ,String spreadMethod ="pad",String interpolationMethod ="rgb",double focalPointRatio =0){
		this.thickness = thickness;
		this.fillType = fillType;
		this.colors = colors;
		this.alphas = alphas;
		this.ratios = ratios;
		this.matrix = matrix;
		this.spreadMethod = spreadMethod;
		this.interpolationMethod = interpolationMethod;
		this.focalPointRatio = focalPointRatio;
	}
	
	public String  getSpreadMethod (){
		return this.spreadMethod;
	}
	
	/**
	 * 
	 */
	public void  setSpreadMethod (String spreadMethod ){
		this.spreadMethod=spreadMethod;
	}
	
	public String  getInterpolationMethod (){
		return this.interpolationMethod;
	}
	
	/**
	 * 
	 */
	public void  setInterpolationMethod (String interpolationMethod ){
		this.interpolationMethod=interpolationMethod;
	}
	
	public double  getFocalPointRatio (){
		return this.focalPointRatio;
	}
	
	/**
	 * 
	 */
	public void  setFocalPointRatio (double focalPointRatio ){
		this.focalPointRatio=focalPointRatio;
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
	 * 
	 */
	public void  setAlphas (Array alphas ){
		this.alphas = alphas;
	}
	
	public Array  getRatios (){
		return ratios;
	}
	
	/**
	 * 
	 */
	public void  setRatios (Array rs ){
		ratios = rs;		
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
	 * 
	 */
	public void  setTo (Graphics target ){
		target.lineGradientStyle(fillType,colors,alphas,ratios,matrix,spreadMethod,interpolationMethod,focalPointRatio);
	}

}


