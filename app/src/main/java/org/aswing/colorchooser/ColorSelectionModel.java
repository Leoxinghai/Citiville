
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

/**
 * @author iiley
 * A model that supports selecting a <code>Color</code>.
 * 
 * @see org.aswing.ASColor
 */
public interface ColorSelectionModel{
	
    /**
     * Returns the selected <code>ASColor</code> which should be
     * non-<code>null</code>.
     *
     * @return  the selected <code>ASColor</code>
     * @see     #setSelectedColor()
     */
    ASColor  getSelectedColor ();

    /**
     * Sets the selected color to <code>ASColor</code>.
     * Note that setting the color to <code>null</code>
     * is undefined and may have unpredictable results.
     * This method fires a state changed event if it sets the
     * current color to a new non-<code>null</code> color.
     *
     * @param color the new <code>ASColor</code>
     * @see   #getSelectedColor()
     * @see   #addChangeListener()
     */
    void  setSelectedColor (ASColor color );
    
    /**
     * Fires a event to indicate a color is adjusting, for example:
     * durring swatched rollover or mixer modification.
     * <p>
     * This should be called by AsWing core(generally called by UI classes), 
     * client app should not call this generally.
     * 
     * @param color the new adjusting <code>ASColor</code>
     * @see #addColorAdjustingListener()
     */
    void  fireColorAdjusting (ASColor color );

	/**
	 * addChangeListener(func:Function)<br>
	 * <p>
	 * Add a listener to listen the Model's change event.
	 * <p>
	 * When the selected color changed.
	 * 
	 * onStateChanged(source:ColorSelectionModel)
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 * @see flash.events.EventDispatcher#addEventListener()
	 */
    void  addChangeListener (Function func );
    
    /**
	 * addColorAdjustingListener(func:Function)<br>bject)
	 * <p>
	 * Add a listener to listen the color adjusting event.
	 * <p>
	 * When user adjusting to a new color.
	 * 
	 * onColorAdjusting(source:ColorSelectionModel, color:ASColor)
	 * @see org.aswing.event.ColorChooserEvent#COLOR_ADJUSTING
	 * @see flash.events.EventDispatcher#addEventListener()
     */
    void  addColorAdjustingListener (Function func );
    
    /**
    * removeChangeListener(func:Fuction)<br>
    * <p>
    * Remove the changeListener
    * <p>
    * @see flash.events.EventDispatcher#removeEventListener()
    */ 
    void  removeChangeListener (Function func );
    
    /**
    * removeColorAdjustingListener(func:Fuction)<br>
    * <p>
    * Remove the colorAdjustingListener
    * <p>
    * @see flash.events.EventDispatcher#removeEventListener()
    */ 
    void  removeColorAdjustingListener (Function func );
}


