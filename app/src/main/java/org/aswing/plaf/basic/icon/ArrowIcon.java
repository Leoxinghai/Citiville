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

	
import org.aswing.graphics.*;
import org.aswing.*;
import org.aswing.geom.*;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;
import flash.geom.Point;

/**
 * @private
 */
public class ArrowIcon implements Icon, UIResource
	
	private double arrow ;
	private int width ;
	private int height ;
	private ASColor shadow ;
	private ASColor darkShadow ;
	
	public  ArrowIcon (double arrow ,int size ,ASColor shadow ,
			 darkShadow:ASColor){
		this.arrow = arrow;
		this.width = size;
		this.height = size;
		this.shadow = shadow;
		this.darkShadow = darkShadow;
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y )
	{
		Point center =new Point(c.getWidth ()/2,c.getHeight ()/2);
		double w =width ;
		Array ps1 =new Array ();
		ps1.push(nextPoint(center, arrow, w/2/2));
		Point back =nextPoint(center ,arrow +Math.PI ,w /2/2);
		ps1.push(nextPoint(back, arrow - Math.PI/2, w/2));
		ps1.push(nextPoint(back, arrow + Math.PI/2, w/2));
		
		//w -= (w/4);
		Array ps2 =new Array ();
		ps2.push(nextPoint(center, arrow, w/2/2-1));
		back = nextPoint(center, arrow + Math.PI, w/2/2-1);
		ps2.push(nextPoint(back, arrow - Math.PI/2, w/2-2));
		ps2.push(nextPoint(back, arrow + Math.PI/2, w/2-2));
		
		g.fillPolygon(new SolidBrush(darkShadow), ps1);
		g.fillPolygon(new SolidBrush(shadow), ps2);		
	}
	
	protected Point  nextPoint (Point p ,double dir ,double dis ){
		return new Point(p.x+Math.cos(dir)*dis, p.y+Math.sin(dir)*dis);
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
		return null;
	}
	
}


