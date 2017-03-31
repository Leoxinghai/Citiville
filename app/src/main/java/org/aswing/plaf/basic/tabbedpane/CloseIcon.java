package org.aswing.plaf.basic.tabbedpane;

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

import flash.display.*;
import org.aswing.graphics.Graphics2D;
import org.aswing.plaf.UIResource;
import org.aswing.Component;
import org.aswing.Icon;
import org.aswing.ASColor;
import org.aswing.graphics.Pen;

/**
 * Close Icon for tab.
 */
public class CloseIcon implements Icon, UIResource{

	protected int width ;
	protected int height ;
	protected Shape shape ;
	private ASColor color ;
	
	public  CloseIcon (){
		width = 12;
		height = width;
		shape = new Shape();
	}
	
	public ASColor  getColor (){
		return color;
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		if(color == null){
			color = c.getUI().getColor("ClosableTabbedPane.darkShadow");
		}
		shape.graphics.clear();
		if(!c.isEnabled()){
			return; //do not paint X when not enabled
		}
		double w =width /2;
		g.drawLine(
			new Pen(getColor(), w/3), 
			x+(width-w)/2, y+(width-w)/2,
			x+(width+w)/2, y+(width+w)/2);
		g.drawLine(
			new Pen(getColor(), w/3), 
			x+(width-w)/2, y+(width+w)/2,
			x+(width+w)/2, y+(width-w)/2);		
	}
		
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


