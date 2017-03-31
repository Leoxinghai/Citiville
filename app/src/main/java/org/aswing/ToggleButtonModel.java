/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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

	
/**
 * The ToggleButton model
 * @author iiley
 */
public class ToggleButtonModel extends DefaultButtonModel{
	
	public  ToggleButtonModel (){
		super();
	}

    /**
     * Sets the selected state of the button.
     * @param b true selects the toggle button,
     *          false deselects the toggle button.
     */
     public void  setSelected (boolean b ){
        ButtonGroup group =getGroup ();
        if (group != null) {
            // use the group model instead
            group.setSelected(this, b);
            b = group.isSelected(this);
        }
        super.setSelected(b);
    }
    
    /**
     * Sets the button to released or unreleased.
     */
	 public void  setPressed (boolean b ){
        if(isPressed()==b || !isEnabled()) {
            return;
        }
        
        if (b==false && isArmed()) {
            setSelected(!isSelected());
        }
        
        pressed = b;
            
        fireStateChanged();
		if(!isPressed() && isArmed()) {
			fireActionEvent();
		}
	}	
}


