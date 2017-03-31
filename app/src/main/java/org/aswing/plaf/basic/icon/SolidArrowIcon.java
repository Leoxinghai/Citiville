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

import org.aswing.*;
import org.aswing.geom.IntPoint;
import org.aswing.graphics.*;
import org.aswing.plaf.UIResource;
import flash.geom.Point;
import flash.display.Shape;

/**
 * @private
 * @author iiley
 */
public class SolidArrowIcon implements Icon, UIResource{
	
	protected Shape shape ;
	protected double width ;
	protected double height ;
	protected double arrow ;
	protected ASColor color ;
	
	public  SolidArrowIcon (double arrow ,double size ,ASColor color ){
		this.arrow = arrow;
		this.width = size;
		this.height = size;
		this.color = color;
		shape = new Shape();
		int x =0;
		int y =0;
		Graphics2D g =new Graphics2D(shape.graphics );
		Point center =new Point(x +width /2,y +height /2);
		double w =width ;
		Array ps1 =new Array ();
		ps1.push(nextPoint(center, arrow, w/2/2));
		Point back =nextPoint(center ,arrow +Math.PI ,w /2/2);
		ps1.push(nextPoint(back, arrow - Math.PI/2, w/2));
		ps1.push(nextPoint(back, arrow + Math.PI/2, w/2));
		
		g.fillPolygon(new SolidBrush(color), ps1);
	}	
	
	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		shape.x = x;
		shape.y = y;
	}
	
	//nextPoint with Point
	protected Point  nextPoint (Point op ,double direction ,double distance ){
		return new Point(op.x+Math.cos(direction)*distance, op.y+Math.sin(direction)*distance);
	}
	
	public int  getIconHeight (Component c ){
		return height;
	}
	
	public int  getIconWidth (Component c ){
		return width;
	}
	
	public void  setArrow (double arrow ){
		this.arrow = arrow;
	}
	
	public DisplayObject  getDisplay (Component c ){
		return shape;
	}
	
}


