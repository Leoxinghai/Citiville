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
public class ToggleButtonBackground implements GroundDecorator, UIResource
    private ASColor shadow ;
    private ASColor darkShadow ;
    private ASColor highlight ;
    private ASColor lightHighlight ;
    
	public  ToggleButtonBackground (){
	} 
	

	private void  reloadColors (ComponentUI ui ){
		shadow = ui.getColor("ToggleButton.shadow");
		darkShadow = ui.getColor("ToggleButton.darkShadow");
		highlight = ui.getColor("ToggleButton.light");
		lightHighlight = ui.getColor("ToggleButton.highlight");
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
	    	boolean isPressing =model.isArmed ()|| model.isSelected();
			BasicGraphicsUtils.drawBezel(g, bounds, isPressing, shadow, darkShadow, highlight, lightHighlight);
			bounds.grow(-2, -2);
			
			ASColor bgColor =(c.getBackground ()==null ? ASColor.WHITE : c.getBackground());
			if(model.isArmed() || model.isSelected()){
				g.fillRectangle(new SolidBrush(bgColor.darker(0.9)), bounds.x, bounds.y, bounds.width, bounds.height);
			}else{
				BasicGraphicsUtils.paintButtonBackGround(b, g, bounds);
			}
		}
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
		
}


