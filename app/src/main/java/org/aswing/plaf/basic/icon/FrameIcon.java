/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.icon;

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
import flash.display.Shape;

import org.aswing.*;
import org.aswing.graphics.Graphics2D;
import org.aswing.plaf.UIResource;

/**
 * Frame title bar icon base.
 * @author iiley
 * @private
 */
public class FrameIcon implements Icon, UIResource{
		
	private static  int DEFAULT_ICON_WIDTH =13;
	
	protected int width ;
	protected int height ;
	protected Shape shape ;
	
	private ASColor color ;
	private ASColor disabledColor ;
		
	/**
	 * @param width the width of the icon square.
	 */	
	public  FrameIcon (int width =DEFAULT_ICON_WIDTH ){
		this.width = width;
		height = width;
		shape = new Shape();
	}
	
	public ASColor  getColor (Component c ){
		if(c.isEnabled()){
			return color;
		}else{
			return disabledColor;
		}
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y )
	{
		if(color == null){
			color = c.getUI().getColor("Frame.activeCaptionText");
			disabledColor = new ASColor(color.getRGB(), 0.5);
		}
		shape.graphics.clear();
		updateIconImp(c, new Graphics2D(shape.graphics), x, y);
	}
	
	public void  updateIconImp (Component c ,Graphics2D g ,int x ,int y ){}
	
	public int  getIconHeight (Component c )
	{
		return width;
	}
	
	public int  getIconWidth (Component c )
	{
		return height;
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return shape;
	}
	
}


