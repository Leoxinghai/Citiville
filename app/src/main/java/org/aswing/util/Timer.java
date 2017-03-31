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

	
import flash.utils.clearInterval;
import flash.utils.setInterval;
import org.aswing.event.AWEvent;
/**
 * Fires one or more action events after a specified delay.  
 * For example, an animation object can use a <code>Timer</code>
 * as the trigger for drawing its frames.
 *
 * <p>
 * Setting up a timer
 * involves creating a <code>Timer</code> object,
 * registering one or more action listeners on it,
 * and starting the timer using
 * the <code>start</code> method.
 * For example, 
 * the following code creates and starts a timer
 * that fires an action event once per second
 * (as specified by the first argument to the <code>Timer</code> constructor).
 * The second argument to the <code>Timer</code> constructor
 * specifies a listener to receive the timer's action events.
 * </p>
 * <pre>
 *double delay =1000;//milliseconds
 *Object listener =new Object ();
 *listener .taskPerformer = (Event e ){
 *          <em>//...Perform a task...</em>
 *      }
 *Timer timer =new Timer(delay );
 *  timer.addActionListener(listener.taskPerformer);
 *  timer.start();
 * </pre>
 *
 * <p>
 * @author iiley
 */
public class Timer extends AbstractImpulser implements Impulser{
	private int intervalID ;
	
	/**
	 * Construct Timer.
	 * @see #setDelay()
     * @throws Error when init delay <= 0 or delay == null
	 */
	public  Timer (int delay ,boolean repeats =true ){
		super(delay, repeats);
		this.intervalID = 0;
	}
	
    /**
     * Starts the <code>Timer</code>,
     * causing it to start sending action events
     * to its listeners.
     *
     * @see #stop()
     */
     public void  start (){
    	isInitalFire = true;
    	clearInterval(intervalID);
    	intervalID = setInterval(fireActionPerformed, getInitialDelay());
    }
    
    /**
     * Returns <code>true</code> if the <code>Timer</code> is running.
     *
     * @see #start()
     */
     public boolean  isRunning (){
    	return intervalID != 0;
    }
    
    /**
     * Stops the <code>Timer</code>,
     * causing it to stop sending action events
     * to its listeners.
     *
     * @see #start()
     */
     public void  stop (){
    	clearInterval(intervalID);
    	intervalID = 0;
    }
    
    /**
     * Restarts the <code>Timer</code>,
     * canceling any pending firings and causing
     * it to fire with its initial delay.
     */
     public void  restart (){
        stop();
        start();
    }
    
    private void  fireActionPerformed (){
    	if(isInitalFire){
    		isInitalFire = false;
    		if(repeats){
    			clearInterval(intervalID);
    			intervalID = setInterval(fireActionPerformed,getDelay());
    		}else{
    			stop();
    		}
    	}
    	dispatchEvent(new AWEvent(AWEvent.ACT));
    }
    

	
}


