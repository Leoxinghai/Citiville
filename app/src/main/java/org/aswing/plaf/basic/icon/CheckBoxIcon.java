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
public class CheckBoxIcon implements Icon, UIResource
{    
	private ASColor shadow ;
    private ASColor darkShadow ;
    private ASColor highlight ;
    private ASColor lightHighlight ;
	
	public  CheckBoxIcon (){
	}
	
	private void  reloadColors (ComponentUI ui ){
		shadow = ui.getColor("CheckBox.shadow");
		darkShadow = ui.getColor("CheckBox.darkShadow");
		highlight = ui.getColor("CheckBox.light");
		lightHighlight = ui.getColor("CheckBox.highlight");
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		if(shadow == null){
			reloadColors(c.getUI());
		}
		AbstractButton rb =AbstractButton(c );
		ButtonModel model =rb.getModel ();
		boolean drawDot =model.isSelected ();
				
		ASColor periphery =darkShadow ;
		ASColor middle =darkShadow ;
		ASColor inner =shadow ;
		ASColor dot =rb.getForeground ();
		
		// Set up colors per RadioButtonModel condition
		if (!model.isEnabled()) {
			periphery = middle = inner = rb.getBackground();
			dot = darkShadow;;
		} else if (model.isPressed()) {
			periphery = shadow;
			inner = darkShadow;
		}
		
		double w =getIconWidth(c );
		double h =getIconHeight(c );
		double cx =x +w /2;
		double cy =y +h /2;
				
		SolidBrush brush =new SolidBrush(darkShadow );
		g.fillRectangle(brush,x, y, w, h);
		       
        brush.setColor(highlight);
        g.fillRectangle(brush,x+1, y+1, w-2, h-2);
       
        Array colors =.get(rb.getBackground ().getRGB (),0xffffff) ;
		Array alphas =.get(1 ,1) ;
		Array ratios =.get(0 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w-3, h-3, (45/180)*Math.PI, x+2, y+2);     
	    GradientBrush gbrush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
	    g.fillRectangle(gbrush,x+2,y+2,w-4,h-4);
       		
		if(drawDot){
			Pen pen =new Pen(dot ,2);
			g.drawLine(pen, cx-w/2+3, cy, cx-w/2/3, cy+h/2-3);
			g.drawLine(pen, cx-w/2/3, cy+h/2-1, cx+w/2, cy-h/2+1);
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


