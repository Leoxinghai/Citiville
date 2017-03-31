/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table.sorter;

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
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.Icon;
import flash.geom.Point;
import flash.display.DisplayObject;
import flash.display.Shape;

/**
 * @author iiley
 */
public class Arrow implements Icon{
	
	private Shape shape ;
	private int width ;
	private double arrow ;
	
	public  Arrow (boolean descending ,int size ){
		shape = new Shape();
		arrow = descending ? Math.PI/2 : -Math.PI/2;
		this.width = size;
	}
	
	public int  getIconWidth (Component c ){
		return width;
	}

	public int  getIconHeight (Component c ){
		return width;
	}

	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		shape.graphics.clear();
		g = new Graphics2D(shape.graphics);
		Point center =new Point(x ,com.getHeight ()/2);
		double w =width ;
		
		Array ps1 =new Array ();
		ps1.push(nextPoint(center, arrow, w/2/2));
		Point back =nextPoint(center ,arrow +Math.PI ,w /2/2);
		ps1.push(nextPoint(back, arrow - Math.PI/2, w/2));
		ps1.push(nextPoint(back, arrow + Math.PI/2, w/2));
		
		g.fillPolygon(new SolidBrush(ASColor.BLACK), ps1);	
	}
	
	protected Point  nextPoint (Point p ,double dir ,double dis ){
		return new Point(p.x+Math.cos(dir)*dis, p.y+Math.sin(dir)*dis);
	}
	
	public DisplayObject  getDisplay (Component c ){
		return shape;
	}
}


