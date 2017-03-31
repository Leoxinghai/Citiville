/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.background;

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
import org.aswing.geom.IntRectangle;
import flash.display.DisplayObject;
import org.aswing.plaf.*;
import flash.display.Sprite;
import org.aswing.geom.IntDimension;
import flash.events.*;
import org.aswing.event.*;
import org.aswing.plaf.basic.BasicGraphicsUtils;

/**
 * The thumb decorator for JScrollBar.
 * @author iiley
 * @private
 */
public class ScrollBarThumb implements GroundDecorator, UIResource
	private ASColor thumbHighlightColor ;
    private ASColor thumbLightHighlightColor ;
    private ASColor thumbLightShadowColor ;
    private ASColor thumbDarkShadowColor ;
    private ASColor thumbColor ;
    private AWSprite thumb ;
    private IntDimension size ;
    private boolean verticle ;
        
	protected boolean rollover ;
	protected boolean pressed ;
    
	public  ScrollBarThumb (){
		thumb = new AWSprite();
		rollover = false;
		pressed = false;
		initSelfHandlers();
	}
	
	private void  reloadColors (ComponentUI ui ){
		thumbHighlightColor = ui.getColor("ScrollBar.thumbHighlight");
		thumbLightHighlightColor = ui.getColor("ScrollBar.thumbLightHighlight");
		thumbLightShadowColor = ui.getColor("ScrollBar.thumbShadow");
		thumbDarkShadowColor = ui.getColor("ScrollBar.thumbDarkShadow");
		thumbColor = ui.getColor("ScrollBar.thumbBackground");
	}
	
	public void  updateDecorator (Component c ,Graphics2D g ,IntRectangle bounds ){
		if(thumbColor == null){
			reloadColors(c.getUI());
		}
		thumb.x = bounds.x;
		thumb.y = bounds.y;
		size = bounds.getSize();
		JScrollBar sb =JScrollBar(c );
		verticle = (sb.getOrientation() == JScrollBar.VERTICAL);
		paint();
	}
	
	private void  paint (){
    	double w =size.width ;
    	double h =size.height ;
    	thumb.graphics.clear();
    	Graphics2D g =new Graphics2D(thumb.graphics );
    	IntRectangle rect =new IntRectangle(0,0,w ,h );
    	
    	if(pressed){
    		g.fillRectangle(new SolidBrush(thumbColor), rect.x, rect.y, rect.width, rect.height);
    	}else{
	    	BasicGraphicsUtils.drawControlBackground(g, rect, thumbColor, 
	    		verticle ? 0 : Math.PI/2);
    	}
    	
    	BasicGraphicsUtils.drawBezel(g, rect, pressed, 
    		thumbLightShadowColor, 
    		thumbDarkShadowColor, 
    		thumbHighlightColor, 
    		thumbLightHighlightColor
    		);
    		
    		
    	Pen p =new Pen(thumbDarkShadowColor ,0);
    	if(verticle){
	    	double ch =h /2.0;
	    	g.drawLine(p, 4, ch, w-4, ch);
	    	g.drawLine(p, 4, ch+2, w-4, ch+2);
	    	g.drawLine(p, 4, ch-2, w-4, ch-2);
    	}else{
	    	double cw =w /2.0;
	    	g.drawLine(p, cw, 4, cw, h-4);
	    	g.drawLine(p, cw+2, 4, cw+2, h-4);
	    	g.drawLine(p, cw-2, 4, cw-2, h-4);
    	}		
	}
	
	public DisplayObject  getDisplay (Component c ){
		return thumb;
	}

	private void  initSelfHandlers (){
		thumb.addEventListener(MouseEvent.ROLL_OUT, __rollOutListener);
		thumb.addEventListener(MouseEvent.ROLL_OVER, __rollOverListener);
		thumb.addEventListener(MouseEvent.MOUSE_DOWN, __mouseDownListener);
		thumb.addEventListener(ReleaseEvent.RELEASE, __mouseUpListener);
	}
	
	private void  __rollOverListener (Event e ){
		rollover = true;
		paint();
	}
	private void  __rollOutListener (Event e ){
		rollover = false;
		if(!pressed){
			paint();
		}
	}
	private void  __mouseDownListener (Event e ){
		pressed = true;
		paint();
	}
	private void  __mouseUpListener (Event e ){
		if(pressed){
			pressed = false;
			paint();
		}
	}

}


