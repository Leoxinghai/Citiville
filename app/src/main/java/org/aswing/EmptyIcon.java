package org.aswing;

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
import org.aswing.graphics.Graphics2D;

/**
 * EmptyIcon is just for sit a place.
 * @author iiley
 */
public class EmptyIcon implements Icon{
	
	private int width ;
	private int height ;
	
	public  EmptyIcon (int width ,int height ){
		this.width = width;
		this.height = height;
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}
	
	public int  getIconWidth (Component c )
	{
		return width;
	}
	
	public int  getIconHeight (Component c )
	{
		return height;
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
	}
	
}


