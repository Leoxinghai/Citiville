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

	
import org.aswing.*;
import org.aswing.graphics.Graphics2D;
import org.aswing.geom.IntRectangle;
import org.aswing.geom.IntDimension;
import org.aswing.plaf.*;
	
/**
 * Basic ToggleButton implementation.
 * @author iiley
 * @private
 */	
public class BasicToggleButtonUI extends BasicButtonUI{
	
	public  BasicToggleButtonUI (){
		super();
	}
	
     protected String  getPropertyPrefix (){
        return "ToggleButton.";
    }
        
    /**
     * Overriden so that the text will not be rendered as shifted for
     * Toggle buttons and subclasses.
     */
     protected int  getTextShiftOffset (){
    	return 0;
    }
    
     protected void  paintIcon (AbstractButton b ,Graphics2D g ,IntRectangle iconRect ){
		ButtonModel model =b.getModel ();
		Icon icon =null ;
        
        Array icons =getIcons ();
        for(int i =0;i <icons.length ;i ++){
        	Icon ico =icons.get(i) ;
			setIconVisible(ico, false);
        }
        
        if(!model.isEnabled()) {
			if(model.isSelected()) {
				icon = b.getDisabledSelectedIcon();
			} else {
				icon = b.getDisabledIcon();
			}
		} else if(model.isPressed() && model.isArmed()) {
			icon = b.getPressedIcon();
			if(icon == null) {
				// Use selected icon
				icon = b.getSelectedIcon();
			} 
		} else if(model.isSelected()) {
			if(b.isRollOverEnabled() && model.isRollOver()) {
				icon = b.getRollOverSelectedIcon();
				if (icon == null) {
					icon = b.getSelectedIcon();
				}
			} else {
				icon = b.getSelectedIcon();
			}
		} else if(b.isRollOverEnabled() && model.isRollOver()) {
			icon = b.getRollOverIcon();
		} 
        
		if(icon == null) {
			icon = b.getIcon();
		}
		if(icon == null){
			icon = getIconToLayout();
		}
        if(icon != null){
			setIconVisible(icon, true);
			icon.updateIcon(b, g, iconRect.x, iconRect.y);
        }
    }

}


