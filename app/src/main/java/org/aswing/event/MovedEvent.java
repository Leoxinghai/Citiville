package org.aswing.event;

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


import flash.events.Event;
import org.aswing.geom.IntPoint;

/**
 * The event for component moved.
 * @author iiley
 */
public class MovedEvent extends AWEvent

	/**
     *  The <code>MovedEvent.MOVED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>moved</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getOldLocation()</code></td><td>the old location</td></tr>
     *     <tr><td><code>getNewLocation()</code></td><td>the new location</td></tr>
     *     <tr><td><code>currentTarget</code></td><td>The Object that defines the
     *       event listener that handles the event. For example, if you use
     *       <code>comp.addEventListener()</code> to register an event listener,
     *       comp is the value of the <code>currentTarget</code>. </td></tr>
     *     <tr><td><code>target</code></td><td>The Object that dispatched the event;
     *       it is not always the Object listening for the event.
     *       Use the <code>currentTarget</code> property to always access the
     *       Object listening for the event.</td></tr>
     *  </table>
     *
     *  @eventType moved
	 */
	public static  String MOVED ="moved";
		
	private IntPoint oldPos ;
	private IntPoint newPos ;
	
	public  MovedEvent (IntPoint oldPos ,IntPoint newPos ){
		super(MOVED, false, false);
		this.oldPos = oldPos.clone();
		this.newPos = newPos.clone();
	}
	
	 public Event  clone (){
		return new MovedEvent(oldPos, newPos);
	}
	
	public IntPoint  getOldLocation (){
		return oldPos.clone();
	}
	
	public IntPoint  getNewLocation (){
		return newPos.clone();
	}
}



