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
import com.xiyu.util.BitmapData;
import com.xiyu.util.Dictionary;

//import flash.display.Graphics;
//import flash.display.BitmapData;
//import flash.geom.Matrix;



/**
 * Encapsulate the fill parameter for flash.display.Graphics.beginBitmapFill()
 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#beginBitmapFill()
 * @author n0rthwood
 */
public class BitmapBrush implements IBrush{
	
	private BitmapData _bitmapData ;
	private Matrix _matrix ;
	private boolean _repeat ;
	private boolean _smooth ;
	
	public  BitmapBrush (BitmapData bitmap ,Matrix matrix =null ,boolean repeat =true ,boolean smooth =false ){
		this._bitmapData=bitmap;
		this._matrix=matrix;
		this._repeat=repeat;
		this._smooth=smooth;
	}
	
	/**
	 * @inheritDoc 
	 */
	public void  beginFill (Graphics target ){
		target.beginBitmapFill(_bitmapData,_matrix,_repeat,_smooth);
	}
	
	/**
	 * @inheritDoc 
	 */
	public void  endFill (Graphics target ){
		target.endFill();
	}
	
	public BitmapData  getBitmapData (){
		return _bitmapData;
	}
	
	/**
	 *
	 */
	public void  setBitmapData (BitmapData bitmapData ){
		this._bitmapData = bitmapData;
	}
	
	public Matrix  getMatrix (){
		return _matrix;
	}
	
	/**
	 *
	 */
	public void  setMatrix (Matrix matrix ){
		this._matrix = matrix;
	}
	
	public boolean  isRepeat (){
		return _repeat;
	}
	
	/**
	 *
	 */
	public void  setRepeat (boolean repeat ){
		this._repeat = repeat;
	}
	
	public boolean  isSmooth (){
		return _smooth;
	}
	
	/**
	 *
	 */
	public void  setSmooth (boolean smooth ){
		this._smooth = smooth;
	}
	

}


