/*
 Copyright aswing.org, see the LICENCE.txt.
*/

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

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;

/**
 * A class which implements a simple 2 line bevel border.
 * @author iiley
 */
public class BevelBorder extends DecorateBorder {
	
    /** Raised bevel type. */
    public static  int RAISED =0;
    /** Lowered bevel type. */
    public static  int LOWERED =1;

    private int bevelType ;
    private ASColor highlightOuter ;
    private ASColor highlightInner ;
    private ASColor shadowInner ;
    private ASColor shadowOuter ;
    private double thickness ;

    /**
     * BevelBorder()<br> default bevelType to LOWERED<br>
     * BevelBorder(interior:Border, bevelType:Number)<br>
     * Creates a bevel border with the specified type and whose
     * colors will be derived from the background color of the
     * component passed into the paintBorder method.
     * <p>
     * BevelBorder(interior:Border, bevelType:Number, highlight:ASColor, shadow:ASColor)<br>
     * Creates a bevel border with the specified type, highlight and
     * shadow colors.
     * <p>
     * BevelBorder(interior:Border, bevelType:Number, highlightOuterColor:ASColor, highlightInnerColor:ASColor, shadowOuterColor:ASColor, shadowInnerColor:ASColor)<br>
     * Creates a bevel border with the specified type, specified four colors.
     * @param interior the border in this border
     * @param bevelType the type of bevel for the border
     * @param highlightOuterColor the color to use for the bevel outer highlight
     * @param highlightInnerColor the color to use for the bevel inner highlight
     * @param shadowOuterColor the color to use for the bevel outer shadow
     * @param shadowInnerColor the color to use for the bevel inner shadow
     * @param thickness the thickness of border frame lines, default is 2
     */
    public  BevelBorder (Border interior =null ,int bevelType =LOWERED ,ASColor highlightOuterColor =null ,
                       highlightInnerColor:ASColor=null, shadowOuterColor:ASColor=null, 
                       shadowInnerColor:ASColor=null, thickness:Number=2) {
        super(interior);
        this.bevelType = bevelType;
        if(highlightInnerColor != null && shadowOuterColor == null){
        	this.highlightOuter = highlightOuterColor.brighter();
        	this.highlightInner = highlightOuterColor;
        	this.shadowOuter = null;
        	this.shadowInner = null;
        }else{
        	this.highlightOuter = highlightOuterColor;
        	this.highlightInner = highlightInnerColor;
        	this.shadowOuter = shadowOuterColor;
        	this.shadowInner = shadowInnerColor;
        }
        this.thickness = thickness;
    }


	 public void  updateBorderImp (Component com ,Graphics2D g ,IntRectangle b ){
        if (bevelType == RAISED) {
             paintRaisedBevel(com, g, b.x, b.y, b.width, b.height);
        }else{
             paintLoweredBevel(com, g, b.x, b.y, b.width, b.height);
        }
    }
    
     public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	return new Insets(thickness, thickness, thickness, thickness);
    }
    
	 public DisplayObject  getDisplayImp ()
	{
		return null;
	}
	
    /**
     * Sets the thickness of border frame lines
     * @param t the thickness of border frame lines to set.
     */
    public void  setThickness (double t ){
    	thickness = t;
    }
    
    /**
     * Returns the thickness of border frame lines
     * @return the thickness of border frame lines
     */
    public double  getThickness (){
    	return thickness;
    }

    /**
     * Returns the outer highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * @param c the component for which the highlight may be derived
     */
    public ASColor  getHighlightOuterColorWith (Component c ){
        ASColor highlight =getHighlightOuterColor ();
        if(highlight == null){
        	highlight = c.getBackground().brighter().brighter();
        }
        return highlight;
    }

    /**
     * Returns the inner highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * @param c the component for which the highlight may be derived
     */
    public ASColor  getHighlightInnerColorWith (Component c ){
        ASColor highlight =getHighlightInnerColor ();
        if(highlight == null){
        	highlight = c.getBackground().brighter();
        }
        return highlight;
    }

    /**
     * Returns the inner shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * @param c the component for which the shadow may be derived
     */
    public ASColor  getShadowInnerColorWith (Component c ){
        ASColor shadow =getShadowInnerColor ();
        if(shadow == null){
        	shadow = c.getBackground().darker();
        }
        return shadow ;
                                    
    }

    /**
     * Returns the outer shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * @param c the component for which the shadow may be derived
     */
    public ASColor  getShadowOuterColorWith (Component c ){
        ASColor shadow =getShadowOuterColor ();
        if(shadow == null){
        	shadow = c.getBackground().darker().darker();
        }
        return shadow ;
    }

	/**
	 * Sets new outer highlight color of the bevel border.
	 */
	public void  setHighlightOuterColor (ASColor color ){
		highlightOuter = color;
	}

    /**
     * Returns the outer highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     */
    public ASColor  getHighlightOuterColor (){
        return highlightOuter;
    }

	/**
	 * Sets new inner highlight color of the bevel border.
	 */
	public void  setHighlightInnerColor (ASColor color ){
		highlightInner = color;
	}

    /**
     * Returns the inner highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     */
    public ASColor  getHighlightInnerColor (){
        return highlightInner;
    }

	/**
	 * Sets new inner shadow color of the bevel border.
	 */
	public void  setShadowInnerColor (ASColor color ){
		shadowInner = color;
	}

    /**
     * Returns the inner shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     */
    public ASColor  getShadowInnerColor (){
        return shadowInner;
    }

	/**
	 * Sets new outer shadow color of the bevel border.
	 */
	public void  setShadowOuterColor (ASColor color ){
		shadowOuter = color;
	}

    /**
     * Returns the outer shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     */
    public ASColor  getShadowOuterColor (){
        return shadowOuter;
    }

    /**
     * Sets new type of the bevel border.
     */
    public void  setBevelType (double bevelType ){
        this.bevelType = bevelType;
    }

    /**
     * Returns the type of the bevel border.
     */
    public double  getBevelType (){
        return bevelType;
    }

    private  paintRaisedBevel (Component c ,Graphics2D g ,double x ,double y ,
                                    width:Number, height:Number):void  {
        double h =height ;
        double w =width ;
        double pt =thickness /2;
        x += pt/2;
        y += pt/2;
        w -= pt;
        h -= pt;
        
        Pen pen =new Pen(getHighlightOuterColorWith(c ),pt ,false ,"normal","square","miter");
        g.drawLine(pen, x, y, x, y+h-1*pt);
        g.drawLine(pen, x+1*pt, y, x+w-1*pt, y);
		
		pen.setColor(getHighlightInnerColorWith(c));
        g.drawLine(pen, x+1*pt, y+1*pt, x+1*pt, y+h-2*pt);
        g.drawLine(pen, x+2*pt, y+1*pt, x+w-2*pt, y+1*pt);

		pen.setColor(getShadowOuterColorWith(c));
        g.drawLine(pen, x, y+h-0*pt, x+w-0*pt, y+h-0*pt);
        g.drawLine(pen, x+w-0*pt, y, x+w-0*pt, y+h-1*pt);

		pen.setColor(getShadowInnerColorWith(c));
        g.drawLine(pen, x+1*pt, y+h-1*pt, x+w-1*pt, y+h-1*pt);
        g.drawLine(pen, x+w-1*pt, y+1*pt, x+w-1*pt, y+h-2*pt);

    }

    private  paintLoweredBevel (Component c ,Graphics2D g ,double x ,double y ,
                                    width:Number, height:Number):void  {
        double h =height ;
        double w =width ;
        double pt =thickness /2;
        x += pt/2;
        y += pt/2;
        w -= pt;
        h -= pt;
        
        Pen pen =new Pen(getShadowInnerColorWith(c ),pt ,false ,"normal","square","miter");
        g.drawLine(pen, x, y, x, y+h-1*pt);
        g.drawLine(pen, x+1*pt, y, x+w-1*pt, y);
        
       	pen.setColor(getShadowOuterColorWith(c));
        g.drawLine(pen, x+1*pt, y+1*pt, x+1*pt, y+h-2*pt);
        g.drawLine(pen, x+2*pt, y+1*pt, x+w-2*pt, y+1*pt);

        pen.setColor(getHighlightOuterColorWith(c));
        g.drawLine(pen, x, y+h-0*pt, x+w-0*pt, y+h-0*pt);
        g.drawLine(pen, x+w-0*pt, y, x+w-0*pt, y+h-1*pt);

        pen.setColor(getHighlightInnerColorWith(c));
        g.drawLine(pen, x+1*pt, y+h-1*pt, x+w-1*pt, y+h-1*pt);
        g.drawLine(pen, x+w-1*pt, y+1*pt, x+w-1*pt, y+h-2*pt);
    }

}

}


