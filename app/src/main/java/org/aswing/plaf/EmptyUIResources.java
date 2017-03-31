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


import org.aswing.Border;
import org.aswing.Icon;
import org.aswing.GroundDecorator;

/**
 * The default empty values for ui resources.
 * A value defined in LAF with null or missing definition, it will be treat as these 
 * default values here.
 * <p>
 * For example, if you define button.border = null in the LAF class, then in fact the 
 * <code>UIDefaults</code> will return <code>EmptyUIResources.BORDER</code> to you.
 * </p>
 * @author iiley
 */
public class EmptyUIResources
	/**
	 * The default empty value for border.
	 */
	public static  Border BORDER =DefaultEmptyDecoraterResource.INSTANCE ;
	
	/**
	 * The default empty value for icon.
	 */
	public static  Icon ICON =DefaultEmptyDecoraterResource.INSTANCE ;
	
	/**
	 * The default empty value for ground decorator.
	 */
	public static  GroundDecorator DECORATOR =DefaultEmptyDecoraterResource.INSTANCE ;
	
	/**
	 * The default empty value for insets.
	 */
	public static  InsetsUIResource INSETS =new InsetsUIResource ();
	
	/**
	 * The default empty value for font.
	 */
	public static  ASFontUIResource FONT =new ASFontUIResource ();
	
	/**
	 * The default empty value for color.
	 */
	public static  ASColorUIResource COLOR =new ASColorUIResource ();

}


