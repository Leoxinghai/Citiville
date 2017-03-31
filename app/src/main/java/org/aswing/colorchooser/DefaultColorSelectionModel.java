package org.aswing.colorchooser;

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

import flash.events.EventDispatcher;

import org.aswing.ASColor;
import org.aswing.event.*;

/**
 * A generic implementation of <code>ColorSelectionModel</code>.
 * @author iiley
 */
public class DefaultColorSelectionModel extends EventDispatcher implements ColorSelectionModel{
		
	private ASColor selectedColor ;
	
	/**
	 * DefaultColorSelectionModel(selectedColor:ASColor)<br>
	 * DefaultColorSelectionModel() default to ASColor.WHITE
	 * <p>
     * Creates a <code>DefaultColorSelectionModel</code> with the
     * current color set to <code>color</code>.  Note that setting the color to
     * <code>null</code> means default to WHITE.
	 */
	public  DefaultColorSelectionModel (ASColor selectedColor =null ){
		super();
		if (selectedColor == null) selectedColor=ASColor.WHITE;
		this.selectedColor = selectedColor;
	}

	public ASColor  getSelectedColor (){
		return selectedColor;
	}

	public void  setSelectedColor (ASColor color ){
		if((selectedColor == null && color != null)
			 || (color == null && selectedColor != null)
			 || (color != null && !color.equals(selectedColor))){
			selectedColor = color;
			fireStateChanged();
		}else{
			selectedColor = color;
		}
	}

	public void  addChangeListener (Function func ){
		addEventListener(InteractiveEvent.STATE_CHANGED, func);
	}
	
    public void  addColorAdjustingListener (Function func ){
    	addEventListener(ColorChooserEvent.COLOR_ADJUSTING, func);
    }
	
    public void  removeChangeListener (Function func ){
    	removeEventListener(InteractiveEvent.STATE_CHANGED, func);
    }

    public void  removeColorAdjustingListener (Function func ){
    	removeEventListener(ColorChooserEvent.COLOR_ADJUSTING, func);    	
    }
	
	private void  fireStateChanged (){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED));
	}
	
	public void  fireColorAdjusting (ASColor color ){
		dispatchEvent(new ColorChooserEvent(ColorChooserEvent.COLOR_ADJUSTING, color));
	}

}


