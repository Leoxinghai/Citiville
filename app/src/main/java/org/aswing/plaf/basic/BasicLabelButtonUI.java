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
import org.aswing.plaf.UIResource;
import org.aswing.graphics.*;
import org.aswing.geom.*;

/**
 * @private
 */
public class BasicLabelButtonUI extends BasicButtonUI{
	
	public  BasicLabelButtonUI (){
		super();
	}
	
     protected String  getPropertyPrefix (){
        return "LabelButton.";
    }
	
	 protected void  installDefaults (AbstractButton bb ){
		super.installDefaults(bb);
		String pp =getPropertyPrefix ();
    	JLabelButton b =(JLabelButton)bb;
    	if(b.getRollOverColor() == null || b.getRollOverColor() is UIResource){
    		b.setRollOverColor(getColor(pp+"rollOver"));
    	}
    	if(b.getPressedColor() == null || b.getPressedColor() is UIResource){
    		b.setPressedColor(getColor(pp+"pressed"));
    	}
    	b.buttonMode = true;
	}
	
     protected ASColor  getTextPaintColor (AbstractButton bb ){
    	JLabelButton b =(JLabelButton)bb;
    	if(b.isEnabled()){
    		ButtonModel model =b.getModel ();
    		if(model.isSelected() || (model.isPressed() && model.isArmed())){
    			return b.getPressedColor();
    		}else if(b.isRollOverEnabled() && model.isRollOver()){
    			return b.getRollOverColor();
    		}
    		return b.getForeground();
    	}else{
    		return BasicGraphicsUtils.getDisabledColor(b);
    	}
    }
    
    /**
     * paint normal bg
     */
	 protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
		if(c.isOpaque()){
			g.fillRectangle(new SolidBrush(c.getBackground()), b.x, b.y, b.width, b.height);
		}		
	}
}


