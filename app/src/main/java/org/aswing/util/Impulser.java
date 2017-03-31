/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.util;

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
 * Declares API to fire one or more action events after a 
 * specified delay.  
 * 
 * @author iiley
 * @author Igor Sadovskiy
 */	
public interface Impulser{
	/**
     * Adds an action listener to the <code>Impulser</code>
     * instance.
     *
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
     * Sets the <code>Impulser</code>'s delay between fired events.
     *
     * @param delay the delay
     * @see #setInitialDelay()
     */	
	 void  setDelay (int delay );
	
    /**
     * Returns the delay between firings of events.
     *
     * @see #setDelay()
     * @see #getInitialDelay()
     */	
	 int  getDelay ();
	
    /**
     * Sets the <code>Impulser</code>'s initial delay,
     * which by default is the same as the between-event delay.
     * This is used only for the first action event.
     * Subsequent events are spaced using the delay property.
     * 
     * @param initialDelay the delay 
     *                     between the invocation of the <code>start</code>
     *                     method and the first event
     *                     fired by this impulser
     *
     * @see #setDelay()
     */	
	 void  setInitialDelay (int initialDelay );
	
    /**
     * Returns the <code>Impulser</code>'s initial delay.
     *
     * @see #setInitialDelay()
     * @see #setDelay()
     */	
	 int  getInitialDelay ();
	
	/**
     * If <code>flag</code> is <code>false</code>,
     * instructs the <code>Impulser</code> to send only once
     * action event to its listeners after a start.
     *
     * @param flag specify <code>false</code> to make the impulser
     *             stop after sending its first action event.
	 */
	 void  setRepeats (boolean flag );
	
    /**
     * Returns <code>true</code> (the default)
     * if the <code>Impulser</code> will send
     * an action event to its listeners multiple times.
     *
     * @see #setRepeats()
     */	
	 boolean  isRepeats ();
	
    /**
     * Starts the <code>Impulser</code>,
     * causing it to start sending action events
     * to its listeners.
     *
     * @see #stop()
     */
     void  start ();
    
    /**
     * Returns <code>true</code> if the <code>Impulser</code> is running.
     *
     * @see #start()
     */
     boolean  isRunning ();
    
    /**
     * Stops the <code>Impulser</code>,
     * causing it to stop sending action events
     * to its listeners.
     *
     * @see #start()
     */
     void  stop ();
    
    /**
     * Restarts the <code>Impulser</code>,
     * canceling any pending firings and causing
     * it to fire with its initial delay.
     */
     void  restart ();
}


