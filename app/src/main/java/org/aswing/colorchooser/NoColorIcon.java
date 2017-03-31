package org.aswing.colorchooser;

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

import org.aswing.ASColor;
import org.aswing.Component;
import org.aswing.Icon;
import org.aswing.graphics.*;
import flash.display.DisplayObject;
/**
 * @author iiley
 */
public class NoColorIcon implements Icon {
	private double width ;
	private double height ;
	
	public  NoColorIcon (int width ,int height ){
		this.width = width;
		this.height = height;
	}

	/**
	 * Return the icon's width.
	 */
	public int  getIconWidth (Component c ){
		return width;
	}
	
	/**
	 * Return the icon's height.
	 */
	public int  getIconHeight (Component c ){
		return height;
	}
	
	/**
	 * Draw the icon at the specified location.
	 */
	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		g.beginDraw(new Pen(ASColor.BLACK, 1));
		g.beginFill(new SolidBrush(ASColor.WHITE));
		double w =width /2+1;
		double h =height /2+1;
		x = x + w/2 - 1;
		y = y + h/2 - 1;
		g.rectangle(x, y, w, h);
		g.endFill();
		g.endDraw();
		g.drawLine(new Pen(ASColor.RED, 2), x+1, y+h-1, x+w-1, y+1);
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}	
}


