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
import org.aswing.graphics.*;
import org.aswing.plaf.ComponentUI;
import org.aswing.plaf.UIResource;
import flash.geom.Matrix;

/**
 * @private
 */
public class RadioButtonIcon implements Icon, UIResource
	private ASColor shadow ;
    private ASColor darkShadow ;
    private ASColor highlight ;
    private ASColor lightHighlight ;
	
	public  RadioButtonIcon (){
	}
	
	private void  reloadColors (ComponentUI ui ){
		shadow = ui.getColor("RadioButton.shadow");
		darkShadow = ui.getColor("RadioButton.darkShadow");
		highlight = ui.getColor("RadioButton.light");
		lightHighlight = ui.getColor("RadioButton.highlight");
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		if(shadow == null){
			reloadColors(c.getUI());
		}
		AbstractButton rb =AbstractButton(c );
		ButtonModel model =rb.getModel ();
		boolean drawDot =model.isSelected ();
		
		ASColor periphery =darkShadow ;
		ASColor middle =highlight ;
		ASColor inner =shadow ;
		ASColor dot =rb.getForeground ();
		
		// Set up colors per RadioButtonModel condition
		if (!model.isEnabled()) {
			periphery = middle = inner = rb.getBackground();
			dot = darkShadow;
		} else if (model.isPressed()) {
			periphery = shadow;
			inner = darkShadow;
		}
		
		double w =getIconWidth(c );
		double h =getIconHeight(c );
		double cx =x +w /2;
		double cy =y +h /2;
		double xr =w /2;
		double yr =h /2;
		
		SolidBrush brush =new SolidBrush(darkShadow );
		g.fillEllipse(brush, x, y, w, h);
		brush.setColor(highlight);
		g.fillEllipse(brush, x+1, y+1, w-2, h-2);
        
        Array colors =.get(rb.getBackground ().getRGB (),0xffffff) ;
		Array alphas =.get(1 ,1) ;
		Array ratios =.get(0 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w-3, h-3, (45/180)*Math.PI, x+2, y+2);    
	    GradientBrush gbrush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
	    g.fillEllipse(gbrush, x+2, y+2, w-4, h-4);
		
		if(drawDot){
			xr = w/5;
			yr = h/5;
			brush = new SolidBrush(dot);
			g.fillEllipse(brush, cx-xr, cy-yr, xr*2, yr*2);			
		}
	}
	
	public int  getIconHeight (Component c ){
		return 13;
	}
	
	public int  getIconWidth (Component c ){
		return 13;
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}
	
}


