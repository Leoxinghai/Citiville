/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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
import org.aswing.geom.*;
import org.aswing.*;
import flash.geom.Matrix;

/**
 * @private
 */
public class BasicGraphicsUtils{
	
	public static ASColor  getDisabledColor (Component c ){
		ASColor bg =c.getBackground ();
		if(bg == null) bg = ASColor.BLACK;
		double hue =bg.getHue ();
		double lum =bg.getLuminance ();
		double sat =bg.getSaturation ();
		if(lum < 0.1){
			lum = 			1.4;
		}else{
			lum = 			0.7;
		}
		sat = 		0.7;
		return ASColor.getASColorWithHLS(hue, lum, sat, bg.getAlpha());
	}
	
	/**
	 *For buttons style bezel by fill  
	 */
	public static  drawUpperedBezel (Graphics2D g ,IntRectangle r ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void{
		double x1 =r.x ;
		double y1 =r.y ;
		double w =r.width ;
		double h =r.height ;
		
		SolidBrush brush =new SolidBrush(darkShadow );
		g.fillRectangleRingWithThickness(brush, x1, y1, w, h, 1);
		
        brush.setColor(lightHighlight);
        g.fillRectangleRingWithThickness(brush, x1, y1, w-1, h-1, 1);
        
        brush.setColor(highlight);
        g.fillRectangleRingWithThickness(brush, x1+1, y1+1, w-2, h-2, 1);
        
        brush.setColor(shadow);
        g.fillRectangle(brush, x1+w-2, y1+1, 1, h-2);
        g.fillRectangle(brush, x1+1, y1+h-2, w-2, 1);
	}
	
	/**
	 *For buttons style bezel by fill  
	 */
	public static  drawLoweredBezel (Graphics2D g ,IntRectangle r ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void{
                                 	
		double x1 =r.x ;
		double y1 =r.y ;
		double w =r.width ;
		double h =r.height ;
		
        SolidBrush brush =new SolidBrush(darkShadow );
		g.fillRectangleRingWithThickness(brush, x1, y1, w, h, 1);
		
		brush.setColor(darkShadow);
        g.fillRectangleRingWithThickness(brush, x1, y1, w-1, h-1, 1);
        
        brush.setColor(highlight);
        g.fillRectangleRingWithThickness(brush, x1+1, y1+1, w-2, h-2, 1);
        
        brush.setColor(highlight);
        g.fillRectangle(brush, x1+w-2, y1+1, 1, h-2);
        g.fillRectangle(brush, x1+1, y1+h-2, w-2, 1);
	}
	
	/**
	 *For buttons style bezel by fill  
	 */	
	public static  drawBezel (Graphics2D g ,IntRectangle r ,boolean isPressed ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void{
                                 
        if(isPressed) {
            drawLoweredBezel(g, r, shadow, darkShadow, highlight, lightHighlight);
        }else {
        	drawUpperedBezel(g, r, shadow, darkShadow, highlight, lightHighlight);
        }
	}
	
	/**
	 *For buttons by draw line  
	 */	
	public static  paintBezel (Graphics2D g ,IntRectangle r ,boolean isPressed ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void{
                                 
        if(isPressed) {
            paintLoweredBevel(g, r, shadow, darkShadow, highlight, lightHighlight);
        }else {
        	paintRaisedBevel(g, r, shadow, darkShadow, highlight, lightHighlight);
        }
	}	
	
	/**
	 * Use drawLine 
	 */
    public static  paintRaisedBevel (Graphics2D g ,IntRectangle r ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void  {
        double h =r.height -1;
        double w =r.width -1;
        double x =r.x +0.5;
        double y =r.y +0.5;
        Pen pen =new Pen(lightHighlight ,1,false ,"normal","square","miter");
        g.drawLine(pen, x, y, x, y+h-2);
        g.drawLine(pen, x+1, y, x+w-2, y);
		
		pen.setColor(highlight);
        g.drawLine(pen, x+1, y+1, x+1, y+h-3);
        g.drawLine(pen, x+2, y+1, x+w-3, y+1);

		pen.setColor(darkShadow);
        g.drawLine(pen, x, y+h-1, x+w-1, y+h-1);
        g.drawLine(pen, x+w-1, y, x+w-1, y+h-2);

		pen.setColor(shadow);
        g.drawLine(pen, x+1, y+h-2, x+w-2, y+h-2);
        g.drawLine(pen, x+w-2, y+1, x+w-2, y+h-3);
    }
    
	/**
	 * Use drawLine 
	 */
    public static  paintLoweredBevel (Graphics2D g ,IntRectangle r ,
                                    shadow:ASColor, darkShadow:ASColor, 
                                 highlight:ASColor, lightHighlight:ASColor):void  {
        double h =r.height -1;
        double w =r.width -1;
        double x =r.x +0.5;
        double y =r.y +0.5;
		Pen pen =new Pen(shadow ,1,false ,"normal","square","miter");
        g.drawLine(pen, x, y, x, y+h-1);
        g.drawLine(pen, x+1, y, x+w-1, y);

       	pen.setColor(darkShadow);
        g.drawLine(pen, x+1, y+1, x+1, y+h-2);
        g.drawLine(pen, x+2, y+1, x+w-2, y+1);

        pen.setColor(lightHighlight);
        g.drawLine(pen, x+1, y+h-1, x+w-1, y+h-1);
        g.drawLine(pen, x+w-1, y+1, x+w-1, y+h-2);

        pen.setColor(highlight);
        g.drawLine(pen, x+2, y+h-2, x+w-2, y+h-2);
        g.drawLine(pen, x+w-2, y+2, x+w-2, y+h-3);
    }
    
    public static void  paintButtonBackGround (AbstractButton c ,Graphics2D g ,IntRectangle b ){
		ASColor bgColor =(c.getBackground ()==null ? ASColor.WHITE : c.getBackground());
		if(c.isOpaque()){
			if(c.getModel().isArmed() || c.getModel().isSelected() || !c.isEnabled()){
				g.fillRectangle(new SolidBrush(bgColor), b.x, b.y, b.width, b.height);
			}else{
				drawControlBackground(g, b, bgColor, Math.PI/2);
			}
		}
    }

	public static void  drawControlBackground (Graphics2D g ,IntRectangle b ,ASColor bgColor ,double direction ){
		g.fillRectangle(new SolidBrush(bgColor), b.x, b.y, b.width, b.height);
		double x =b.x ;
		double y =b.y ;
		double w =b.width ;
		double h =b.height ;
        Array colors =.get(0xFFFFFF ,0xFFFFFF) ;
		Array alphas =.get(0 .75,0) ;
		Array ratios =.get(0 ,100) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w, h, direction, x, y);       
        GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
        g.fillRectangle(brush, x, y, w, h);
	}
	
	public static void  fillGradientRect (Graphics2D g ,IntRectangle b ,ASColor c1 ,ASColor c2 ,double direction ,Array ratios =null ){
		double x =b.x ;
		double y =b.y ;
		double w =b.width ;
		double h =b.height ;
        Array colors =.get(c1.getRGB (),c2.getRGB ()) ;
		Array alphas =.get(c1.getAlpha (),c2.getAlpha ()) ;
		if(ratios == null){
			ratios = .get(0, 255);
		}
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w, h, direction, x, y);       
        GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
        g.fillRectangle(brush, x, y, w, h);
	}    

}


