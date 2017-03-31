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

	
 
import flash.events.EventDispatcher;
import org.aswing.event.AWEvent;

/**
 * Provides common routines for classes implemented
 * <code>Impulse</code> interface. 
 *
 * @author iiley
 * @author Igor Sadovskiy
 */
public class AbstractImpulser extends EventDispatcher implements Impulser{
	protected int delay ;
	protected int initialDelay ;
	protected boolean repeats ;
	protected boolean isInitalFire ;
		
	/**
	 * Constructs <code>AbstractImpulser</code>.
     * @throws Error when init delay <= 0 or delay == null
	 */
	public  AbstractImpulser (int delay ,boolean repeats =true ){
		this.delay = delay;
		this.initialDelay = 0;
		this.repeats = repeats;
		this.isInitalFire = true;
	}
	
    /**
     * Adds an action listener to the <code>AbstractImpulser</code>
     * instance.
     *
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
     */	
	public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		 addEventListener(AWEvent.ACT, listener, false, priority, useWeakReference);
	}
	
	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	public void  removeActionListener (Function listener ){
		this.removeEventListener(AWEvent.ACT, listener);
	}
	
    /**
     * Sets the <code>AbstractImpulser</code>'s delay between 
     * fired events.
     *
     * @param delay the delay
     * @see #setInitialDelay()
     * @throws Error when set delay <= 0 or delay == null
     */	
	public void  setDelay (int delay ){
		this.delay = delay;
	}
	
    /**
     * Returns the delay between firings of events.
     *
     * @see #setDelay()
     * @see #getInitialDelay()
     */	
	public int  getDelay (){
		return delay;
	}
	
    /**
     * Sets the <code>AbstractImpulser</code>'s initial delay,
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
     * @throws Error when set initialDelay <= 0 or initialDelay == null
     */	
	public void  setInitialDelay (int initialDelay ){

		this.initialDelay = initialDelay;
	}
	
    /**
     * Returns the <code>AbstractImpulser</code>'s initial delay.
     *
     * @see #setInitialDelay()
     * @see #setDelay()
     */	
	public int  getInitialDelay (){
		if(initialDelay == 0){
			return delay;
		}else{
			return initialDelay;
		}
	}
	
	/**
     * If <code>flag</code> is <code>false</code>,
     * instructs the <code>AbstractImpulser</code> to send only once
     * action event to its listeners after a start.
     *
     * @param flag specify <code>false</code> to make the impulser
     *             stop after sending its first action event.
     *             Default value is true.
	 */
	public void  setRepeats (boolean flag ){
		repeats = flag;
	}
	
    /**
     * Returns <code>true</code> (the default)
     * if the <code>AbstractImpulser</code> will send
     * an action event to its listeners multiple times.
     *
     * @see #setRepeats()
     */	
	public boolean  isRepeats (){
		return repeats;
	}
	
	public boolean  isRunning (){
		return false;
	}
	
	public void  stop (){}
	
	public void  start (){}
	
	public void  restart (){}
}


