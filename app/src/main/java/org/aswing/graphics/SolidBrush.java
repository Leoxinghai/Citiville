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
import org.aswing.graphics.IBrush;

/**
 * SolidBrush encapsulate fill parameters for flash.display.Graphics.beginFill()
 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#beginFill()
 * @author iiley
 */
public class SolidBrush implements IBrush{
	
	private ASColor color ;
	
	public  SolidBrush (ASColor color ){
		this.color = color;
	}
		
	public ASColor  getColor (){
		return color;
	}
	
	/**
	 * Sets the color
	 */
	public void  setColor (ASColor color ){		
		this.color = color;	
	}
	
	/**
	 * Begins fill
	 */
	public void  beginFill (Graphics target ){
		target.beginFill(color.getRGB(), color.getAlpha());
	}
	
	/**
	 * Ends fill
	 */
	public void  endFill (Graphics target ){
		target.endFill();
	}
}


