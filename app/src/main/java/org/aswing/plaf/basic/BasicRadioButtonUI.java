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
import org.aswing.geom.*;
import org.aswing.graphics.*;
	
/**
 * Basic RadioButton implementation.
 * @author iiley
 * @private
 */	
public class BasicRadioButtonUI extends BasicToggleButtonUI{
	
	protected Icon defaultIcon ;
	
	public  BasicRadioButtonUI (){
		super();
	}
	
	 protected void  installDefaults (AbstractButton b ){
		super.installDefaults(b);
		defaultIcon = getIcon(getPropertyPrefix() + "icon");
	}
	
	 protected void  uninstallDefaults (AbstractButton b ){
		super.uninstallDefaults(b);
		if(defaultIcon.getDisplay(b)){
    		if(button.contains(defaultIcon.getDisplay(b))){
    			button.removeChild(defaultIcon.getDisplay(b));
    		}
		}
	}
	
     protected String  getPropertyPrefix (){
        return "RadioButton.";
    }
    	
    public Icon  getDefaultIcon (){
        return defaultIcon;
    }
    
     protected Icon  getIconToLayout (){
    	if(button.getIcon() == null){
    		if(defaultIcon.getDisplay(button)){
	    		if(!button.contains(defaultIcon.getDisplay(button))){
	    			button.addChild(defaultIcon.getDisplay(button));
	    		}
    		}
    		return defaultIcon;
    	}else{
    		return button.getIcon();
    	}
    }
    
	 protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
		if(c.isOpaque()){
			g.fillRectangle(new SolidBrush(c.getBackground()), b.x, b.y, b.width, b.height);
		}
	}
}


