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


import org.aswing.*;
import org.aswing.graphics.*;
import flash.display.DisplayObject;
/**
 * PreviewColorIcon represent two color rect, on previous, on current.
 * @author iiley
 */
public class PreviewColorIcon implements Icon{
	/** 
     * Horizontal orientation.
     */
    public static  int HORIZONTAL =AsWingConstants.HORIZONTAL ;
    /** 
     * Vertical orientation.
     */
    public static  int VERTICAL =AsWingConstants.VERTICAL ;
    
	private ASColor previousColor ;
	private ASColor currentColor ;
	private int width ;
	private int height ;
	private int orientation ;
	
	public  PreviewColorIcon (int width ,int height ,int orientation =AsWingConstants .VERTICAL ){
		this.width = width;
		this.height = height;
		this.orientation = orientation;
		previousColor = currentColor = ASColor.WHITE;
	}
	
	public void  setColor (ASColor c ){
		setCurrentColor(c);
		setPreviousColor(c);
	}
	
	public void  setOrientation (int o ){
		orientation = o;
	}
	
	public int  getOrientation (){
		return orientation;
	}
	
	public void  setPreviousColor (ASColor c ){
		previousColor = c;
	}
	
	public ASColor  getPreviousColor (){
		return previousColor;
	}
	
	public void  setCurrentColor (ASColor c ){
		currentColor = c;
	}
	
	public ASColor  getCurrentColor (){
		return currentColor;
	}
	
	public int  getIconWidth (Component c ){
		return width;
	}

	public int  getIconHeight (Component c ){
		return height;
	}

	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		double w =width ;
		double h =height ;
		g.fillRectangle(new SolidBrush(ASColor.WHITE), x, y, w, h);

		double t =5;
		for(double c =0;c <w ;c +=t ){
			g.drawLine(new Pen(ASColor.GRAY, 1), x+c, y, x+c, y+h);
		}
		for(double r =0;r <h ;r +=t ){
			g.drawLine(new Pen(ASColor.GRAY, 1), x, y+r, x+w, y+r);
		}
			
		if(previousColor == null && currentColor == null){
			paintNoColor(g, x, y, w, h);
			return;
		}
		
		if(orientation == HORIZONTAL){
			w = width/2;
			h = height;
			if(previousColor == null){
				paintNoColor(g, x, y, w, h);
			}else{
				g.fillRectangle(new SolidBrush(previousColor), x, y, w, h);
			}
			x += w;
			if(currentColor == null){
				paintNoColor(g, x, y, w, h);
			}else{
				g.fillRectangle(new SolidBrush(currentColor), x, y, w, h);
			}
		}else{
			w = width;
			h = height/2;
			if(previousColor == null){
				paintNoColor(g, x, y, w, h);
			}else{
				g.fillRectangle(new SolidBrush(previousColor), x, y, w, h);
			}
			y += h;
			if(currentColor == null){
				paintNoColor(g, x, y, w, h);
			}else{
				g.fillRectangle(new SolidBrush(currentColor), x, y, w, h);
			}
		}
	}
	
	private void  paintNoColor (Graphics2D g ,double x ,double y ,double w ,double h ){
		g.fillRectangle(new SolidBrush(ASColor.WHITE), x, y, w, h);
		g.drawLine(new Pen(ASColor.RED, 2), x+1, y+h-1, x+w-1, y+1);
	}

	public DisplayObject  getDisplay (Component c ){
		return null;
	}

}


