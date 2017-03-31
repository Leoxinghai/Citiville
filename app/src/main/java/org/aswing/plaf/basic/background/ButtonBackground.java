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

	
import flash.display.DisplayObject;

import org.aswing.*;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.*;
import org.aswing.plaf.basic.BasicGraphicsUtils;
import org.aswing.plaf.*;

/**
 * @private
 */
public class ButtonBackground implements GroundDecorator, UIResource{
    private ASColor shadow ;
    private ASColor darkShadow ;
    private ASColor highlight ;
    private ASColor lightHighlight ;
    
	public  ButtonBackground (){
	} 
	

	private void  reloadColors (ComponentUI ui ){
		shadow = ui.getColor("Button.shadow");
		darkShadow = ui.getColor("Button.darkShadow");
		highlight = ui.getColor("Button.light");
		lightHighlight = ui.getColor("Button.highlight");
	}
	
	public void  updateDecorator (Component c ,Graphics2D g ,IntRectangle bounds ){
		if(shadow == null){
			reloadColors(c.getUI());
		}
		AbstractButton b =(AbstractButton)c;
		bounds = bounds.clone();
		if(b == null) return;
		if(c.isOpaque()){
			ButtonModel model =b.getModel ();
	    	boolean isPressing =model.isArmed ()|| model.isSelected() || !b.isEnabled();
			BasicGraphicsUtils.drawBezel(g, bounds, isPressing, shadow, darkShadow, highlight, lightHighlight);
			bounds.grow(-2, -2);
			BasicGraphicsUtils.paintButtonBackGround(b, g, bounds);
			if(b is JButton && JButton(b).isDefaultButton()){
				g.drawRectangle(new Pen(darkShadow.changeAlpha(0.4), 2), bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


