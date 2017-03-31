/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf;

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

	
import flash.display.DisplayObject;

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.border.EmptyBorder;

/**
 * The default empty border to be the component border as default. So it can be 
 * replaced by LAF specified.
 * 
 * @author iiley
 */
public class DefaultEmptyDecoraterResource implements Icon, Border, GroundDecorator, UIResource
	/**
	 * Shared instance.
	 */
	public static  DefaultEmptyDecoraterResource INSTANCE =new DefaultEmptyDecoraterResource ();
	
	public static  ASColorUIResource DEFAULT_BACKGROUND_COLOR =new ASColorUIResource(0);
	public static  ASColorUIResource DEFAULT_FOREGROUND_COLOR =new ASColorUIResource(0xFFFFFF );
	public static  ASColorUIResource DEFAULT_MIDEGROUND_COLOR =new ASColorUIResource(1673215);

	public static  ASFontUIResource DEFAULT_FONT =new ASFontUIResource ();	
	
	/**
	 * Used to be a null ui resource color.
	 */
	public static  ASColorUIResource NULL_COLOR =new ASColorUIResource(0);
	
	/**
	 * Used to be a null ui resource font.
	 */
	public static  ASFontUIResource NULL_FONT =new ASFontUIResource ();
	


        public static  UIStyleTune DEFAULT_STYLE_TUNE =new UIStyleTune ();
        public static  StyleTune NULL_STYLE_TUNE =new StyleTune(0,0,0);

	
	public  DefaultEmptyDecoraterResource (){
	}
	
	/**
	 * return null
	 */
	public DisplayObject  getDisplay (Component c ){
		return null;
	}	
	
	/**
	 * return 0
	 */
	public int  getIconWidth (Component c ){
		return 0;
	}
	
	/**
	 * return 0
	 */
	public int  getIconHeight (Component c ){
		return 0;
	}
	
	/**
	 * do nothing
	 */
	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
	}	
	

	/**
	 * do nothing
	 */
	public void  updateBorder (Component com ,Graphics2D g ,IntRectangle bounds ){
	}
	
	/**
	 * return new Insets(0,0,0,0)
	 */
	public Insets  getBorderInsets (Component com ,IntRectangle bounds ){
		return new Insets(0,0,0,0);
	}
	
	/**
	 * do nothing
	 */
	public void  updateDecorator (Component com ,Graphics2D g ,IntRectangle bounds ){
	}
}


