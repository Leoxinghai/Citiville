
package org.aswing.border;

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
import org.aswing.geom.*;
import flash.display.*;

/**
 * Line border, this will paint a rounded line for a component.
 */
public class LineBorder extends DecorateBorder{
	
	private ASColor color ;
	private double thickness ;
	private double round ;
	
	/**
	 * Create a line border.
	 * @param interior interior border. Default is null;
	 * @param color the color of the border. Default is null, means ASColor.BLACK
	 * @param thickness the thickness of the border. Default is 1
	 * @param round round rect radius, default is 0 means normal rectangle, not rect.
	 */	
	public  LineBorder (Border interior =null ,ASColor color =null ,double thickness =1,double round =0)
	{
		super(interior);
		if(color == null) color = ASColor.BLACK;
		
		this.color = color;
		this.thickness = thickness;
		this.round = round;
	}
	
	 public void  updateBorderImp (Component com ,Graphics2D g ,IntRectangle b )
	{
 		double t =thickness ;
    	if(round <= 0){
    		g.drawRectangle(new Pen(color, thickness), b.x + t/2, b.y + t/2, b.width - t, b.height - t);
    	}else{
    		g.fillRoundRectRingWithThickness(new SolidBrush(color), b.x, b.y, b.width, b.height, round, t);
    	}
	}
	
	 public Insets  getBorderInsetsImp (Component com ,IntRectangle bounds )
	{
    	double width =Math.ceil(thickness +round -round *0.707106781186547);//0.707106781186547=Math.sin(45degrees );
    	return new Insets(width, width, width, width);
	}
	
	 public DisplayObject  getDisplayImp ()
	{
		return null;
	}

	public ASColor  getColor (){
		return color;
	}

	public void  setColor (ASColor color ){
		this.color = color;
	}

	public double  getThickness (){
		return thickness;
	}

	public void  setThickness (double thickness ){
		this.thickness = thickness;
	}

	public double  getRound (){
		return round;
	}

	public void  setRound (double round ){
		this.round = round;
	}    	
}


