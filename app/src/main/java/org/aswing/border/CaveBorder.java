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


import flash.display.*;
import flash.text.*;

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.HashMap;

/**
 * CaveBorder, a border with a cave line rectangle(or roundrect).
 * It is like a TitledBorder with no title. :)
 */
public class CaveBorder extends DecorateBorder{
	
	public static ASColor  DEFAULT_LINE_COLOR (){
		return ASColor.GRAY;
	}
	public static ASColor  DEFAULT_LINE_LIGHT_COLOR (){
		return ASColor.WHITE;
	}
	public static  int DEFAULT_LINE_THICKNESS =1;
				
	private double round ;
	private ASColor lineColor ;
	private ASColor lineLightColor ;
	private double lineThickness ;
	private boolean beveled ;
	
	/**
	 * Create a cave border.
	 * @param interior the interior border.
	 * @param round round rect radius, default is 0 means normal rectangle, not rect.
	 * @see org.aswing.border.TitledBorder
	 * @see #setLineColor()
	 * @see #setLineThickness()
	 * @see #setBeveled()
	 */
	public  CaveBorder (Border interior =null ,double round =0){
		super(interior);
		this.round = round;
		
		lineColor = DEFAULT_LINE_COLOR;
		lineLightColor = DEFAULT_LINE_LIGHT_COLOR;
		lineThickness = DEFAULT_LINE_THICKNESS;
		beveled = true;
	}
	
	 public void  updateBorderImp (Component c ,Graphics2D g ,IntRectangle bounds ){
    	double x1 =bounds.x +lineThickness *0.5;
    	double y1 =bounds.y +lineThickness *0.5;
    	double w =bounds.width -lineThickness ;
    	double h =bounds.height -lineThickness ;
    	if(beveled){
    		w -= lineThickness;
    		h -= lineThickness;
    	}
    	double x2 =x1 +w ;
    	double y2 =y1 +h ;
    	
    	IntRectangle textR =new IntRectangle(bounds.x +bounds.width /2,bounds.y ,0,0);
    	
    	Pen pen =new Pen(lineColor ,lineThickness );
    	if(round <= 0){
			//draw dark rect
			g.beginDraw(pen);
			g.moveTo(x1, y1);
			g.lineTo(x1, y2);
			g.lineTo(x2, y2);
			g.lineTo(x2, y1);
			g.lineTo(x1, y1);
			g.endDraw();
			if(beveled){
    			//draw hightlight
    			pen.setColor(lineLightColor);
    			g.beginDraw(pen);
    			g.moveTo(x1+lineThickness, y1+lineThickness);
    			g.lineTo(x1+lineThickness, y2-lineThickness);
    			g.moveTo(x1, y2+lineThickness);
    			g.lineTo(x2+lineThickness, y2+lineThickness);
    			g.lineTo(x2+lineThickness, y1);
    			g.moveTo(x2-lineThickness, y1+lineThickness);
    			g.lineTo(x1+lineThickness, y1+lineThickness);
    			g.endDraw();
			}
    	}else{
			double r =round ;
			if(beveled){
				pen.setColor(lineLightColor);
    			g.beginDraw(pen);
    			double t =lineThickness ;
				x1+=t;
				x2+=t;
				y1+=t;
				y2+=t;
	    		g.moveTo(textR.x, y1);
				//Top left
				g.lineTo (x1+r, y1);
				g.curveTo(x1, y1, x1, y1+r);
				//Bottom left
				g.lineTo (x1, y2-r );
				g.curveTo(x1, y2, x1+r, y2);
				//bottom right
				g.lineTo(x2-r, y2);
				g.curveTo(x2, y2, x2, y2-r);
				//Top right
				g.lineTo (x2, y1+r);
				g.curveTo(x2, y1, x2-r, y1);
				g.lineTo(textR.x + textR.width, y1);
    			g.endDraw();  
				x1-=t;
				x2-=t;
				y1-=t;
				y2-=t;  				
			}		
			pen.setColor(lineColor);		
			g.beginDraw(pen);
    		g.moveTo(textR.x, y1);
			//Top left
			g.lineTo (x1+r, y1);
			g.curveTo(x1, y1, x1, y1+r);
			//Bottom left
			g.lineTo (x1, y2-r );
			g.curveTo(x1, y2, x1+r, y2);
			//bottom right
			g.lineTo(x2-r, y2);
			g.curveTo(x2, y2, x2, y2-r);
			//Top right
			g.lineTo (x2, y1+r);
			g.curveTo(x2, y1, x2-r, y1);
			g.lineTo(textR.x + textR.width, y1);
			g.endDraw();
		}	
    }
    	   
    public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	double cornerW =Math.ceil(lineThickness *2+round -round *0.707106781186547);
    	Insets insets =new Insets(cornerW ,cornerW ,cornerW ,cornerW );
    	return insets;
    }
	
	 public DisplayObject  getDisplayImp (){
		return null;
	}		
	
	//-----------------------------------------------------------------
	public ASColor  getLineColor (){
		return lineColor;
	}

	public void  setLineColor (ASColor lineColor ){
		if (lineColor != null){
			this.lineColor = lineColor;
		}
	}
	
	public ASColor  getLineLightColor (){
		return lineLightColor;
	}
	
	public void  setLineLightColor (ASColor lineLightColor ){
		if (lineLightColor != null){
			this.lineLightColor = lineLightColor;
		}
	}
	
	public boolean  isBeveled (){
		return beveled;
	}
	
	public void  setBeveled (boolean b ){
		beveled = b;
	}

	public double  getRound (){
		return round;
	}

	public void  setRound (double round ){
		this.round = round;
	}
	
	public int  getLineThickness (){
		return lineThickness;
	}

	public void  setLineThickness (double lineThickness ){
		this.lineThickness = lineThickness;
	}
}
	


