
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
public class ColorRectIcon implements Icon {
	private double width ;
	private double height ;
	private ASColor color ;
	
	public  ColorRectIcon (int width ,int height ,ASColor color ){
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void  setColor (ASColor color ){
		this.color = color;
	}
	
	public ASColor  getColor (){
		return color;
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
	
	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		double w =width ;
		double h =height ;
		g.fillRectangle(new SolidBrush(ASColor.WHITE), x, y, w, h);
		if(color != null){
			double t =5;
			for(double c =0;c <w ;c +=t ){
				g.drawLine(new Pen(ASColor.GRAY, 1), x+c, y, x+c, y+h);
			}
			for(double r =0;r <h ;r +=t ){
				g.drawLine(new Pen(ASColor.GRAY, 1), x, y+r, x+w, y+r);
			}
			g.fillRectangle(new SolidBrush(color), x, y, width, height);
		}else{
			g.drawLine(new Pen(ASColor.RED, 2), x+1, y+h-1, x+w-1, y+1);
		}
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}	
}


