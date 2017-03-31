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
import org.aswing.Icon;
import org.aswing.Component;
import flash.display.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.BasicGraphicsUtils;
import org.aswing.ASColor;
import org.aswing.geom.IntRectangle;

/**
 * @private
 */
public class SliderThumbIcon implements Icon, UIResource{
	
	protected Sprite thumb ;
	protected SimpleButton enabledButton ;
	protected SimpleButton disabledButton ;
	
	protected ASColor thumbLightHighlightColor ;
	protected ASColor thumbHighlightColor ;
	protected ASColor thumbLightShadowColor ;
	protected ASColor thumbDarkShadowColor ;
	protected ASColor thumbColor ;
	
	public  SliderThumbIcon (){
		thumb = new Sprite();
	}
	
	protected String  getPropertyPrefix (){
		return "Slider.";
	}	
	
	protected void  initThumb (ComponentUI ui ){
		String pp =getPropertyPrefix ();
		thumbHighlightColor = ui.getColor(pp+"thumbHighlight");
		thumbLightHighlightColor = ui.getColor(pp+"thumbLightHighlight");
		thumbLightShadowColor = ui.getColor(pp+"thumbShadow");
		thumbDarkShadowColor = ui.getColor(pp+"thumbDarkShadow");
		thumbColor = ui.getColor(pp+"thumb");
		
		//enabled
		enabledButton = new SimpleButton();
		Shape upState =new Shape ();
		Graphics2D g =new Graphics2D(upState.graphics );
    	
    	ASColor borderC =thumbDarkShadowColor ;
    	ASColor fillC =thumbColor ;
    	paintThumb(g, borderC, fillC, true);
    	enabledButton.upState = upState; 
		enabledButton.overState = upState;
		enabledButton.downState = upState;
		enabledButton.hitTestState = upState;
		enabledButton.useHandCursor = false;
		thumb.addChild(enabledButton);
		
		//disabled
		disabledButton = new SimpleButton();
		upState = new Shape();
		g = new Graphics2D(upState.graphics);
    	
    	borderC = thumbColor;
    	fillC = thumbColor;
    	paintThumb(g, borderC, fillC, false);
    	disabledButton.upState = upState; 
		disabledButton.overState = upState;
		disabledButton.downState = upState;
		disabledButton.hitTestState = upState;
		disabledButton.useHandCursor = false;
		thumb.addChild(disabledButton);
		disabledButton.visible = false;
	}
	
	private void  paintThumb (Graphics2D g ,ASColor borderC ,ASColor fillC ,boolean enabled ){
		if(!enabled){
	    	g.beginDraw(new Pen(borderC));
	    	g.beginFill(new SolidBrush(fillC));
	    	g.rectangle(1, 1, getIconWidth(null)-2, getIconHeight(null)-2);
	    	g.endFill();
	    	g.endDraw();
		}else{
    		BasicGraphicsUtils.drawControlBackground(
    			g, 
    			new IntRectangle(0, 0, getIconWidth(null), getIconHeight(null)), 
    			fillC,
    			0);
			g.drawRectangle(new Pen(borderC), 0.5, 0.5, getIconWidth(null)-1, getIconHeight(null)-1);
		}
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y )
	{
		if(thumbColor == null){
			initThumb(c.getUI());
		}
		thumb.x = x;
		thumb.y = y;
		disabledButton.visible = !c.isEnabled();
		enabledButton.visible = c.isEnabled();
	}
	
	public int  getIconHeight (Component c )
	{
		return 18;
	}
	
	public int  getIconWidth (Component c )
	{
		return 8;
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return thumb;
	}
	
}


