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
 * State Model for buttons.
 * This model is used for check boxes and radio buttons, which are
 * special kinds of buttons, as well as for normal buttons.
 * For check boxes and radio buttons, pressing the mouse selects
 * the button. For normal buttons, pressing the mouse "arms" the
 * button. Releasing the mouse over the button then initiates a
 * <i>button</i> press, firing its action event. Releasing the 
 * mouse elsewhere disarms the button.
 * <p>
 * In use, a UI will invoke <code>setSelected</code> when a mouse
 * click occurs over a check box or radio button. It will invoke
 * <code>setArmed</code when the mouse is pressed over a regular
 * button and invoke <code>setPressed</code when the mouse is released.
 * If the mouse travels outside the button in the meantime, 
 * <code>setArmed(false)</code> will tell the button not to fire
 * when it sees <code>setPressed</code>. (If the mouse travels 
 * back in, the button will be rearmed.)
 * </p>
 * <b>Note: </b><br>
 * A button is triggered when it is both "armed" and "pressed".
 * 
 * <p>
 * Buttons always fire <code>programmatic=false</code> InteractiveEvent.
 * </p>
 * @author iiley
 */	
public interface ButtonModel
	/**
	 * Adds a listener to listen the Model's state change event.
	 * <p>
	 * When the button's state changed, the state is all about:
	 * <ul>
	 * <li>enabled</li>
	 * <li>rollOver</li>
	 * <li>pressed</li>
	 * <li>released</li>
	 * <li>selected</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Buttons always fire <code>programmatic=false</code> InteractiveEvent.
	 * </p>
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false );
	
	/**
	 * Removes a state listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	void  removeStateListener (Function listener );
	
	/**
	 * Adds a listener to listen the Model's act event.
	 * When the button model's armed and pressed to fire this event.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false );
	
	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	void  removeActionListener (Function listener );	
	
	/**
	 * Add a listener to listen the Model's selection change event.
	 * When the button's selection changed, fired when diselected or selected.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */	
	void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false );
	
	/**
	 * Removes a selection listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */
	void  removeSelectionListener (Function listener );
		
	/**
	 * Indicates if the button can be selected or pressed by an input device (such as a mouse pointer).
	 * @return true if the button is enabled, and therefore selectable (or pressable)
	 */
	boolean  isEnabled ();
	
	/**
	 * Indicates that the mouse is over the button.
	 * @return true if the mouse is over the button
	 */
	boolean  isRollOver ();
	
	/**
	 * Indicates partial commitment towards pressing the button.
	 * <p>
	 * Note, this is similar to AS2 version's both <b>pressed</b> and <b>rollover</b>.
	 * </p>
	 * @return true if the button is armed, and ready to be pressed
	 */
	boolean  isArmed ();
	
	/**
	 * Indicates if button has been pressed.
	 * @return true if the button has been pressed
	 */
	boolean  isPressed ();
				
	/**
	 * Indicates if the button has been selected.
	 */
	boolean  isSelected ();
	
	/**
	 * Enables or disables the button.
	 */
	void  setEnabled (boolean b );
	
    /**
     * Sets or clears the button's rollover state
     * 
     * @param b true to turn on rollover
     * @see #isRollover
     */
	void  setRollOver (boolean b );

    /**
     * Marks the button as "armed". If the mouse button is
     * released while it is over this item, the button's action event
     * fires. If the mouse button is released elsewhere, the
     * event does not fire and the button is disarmed.
     * 
     * @param b true to arm the button so it can be selected
     */
	void  setArmed (boolean b );
	
	/**
	 * Sets the button to being pressed or unpressed.
	 */
	void  setPressed (boolean b );
		
    /**
     * Selects or deselects the button.
     *
     * @param b true selects the button,
     *          false deselects the button.
     */
	void  setSelected (boolean b );
	
    /**
     * Identifies the group this button belongs to --
     * needed for radio buttons, which are mutually
     * exclusive within their group.
     *
     * @param group the ButtonGroup this button belongs to
     */
	void  setGroup (ButtonGroup group );	
}


