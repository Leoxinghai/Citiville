
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


import org.aswing.geom.IntDimension;
import flash.events.Event;

/**
 * The event for component resized.
 * @author iiley
 */
public class ResizedEvent extends AWEvent
{
	/**
     *  The <code>ResizedEvent.RESIZED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>resized</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getOldSize()</code></td><td>the old size</td></tr>
     *     <tr><td><code>getNewSize()</code></td><td>the new size</td></tr>
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
     *  @eventType resized
	 */
	public static  String RESIZED ="resized";		
		
	private IntDimension oldSize ;
	private IntDimension newSize ;
	
	public  ResizedEvent (IntDimension oldSize ,IntDimension newSize )
	{
		super(RESIZED, false, false);
		this.oldSize = oldSize.clone();
		this.newSize = newSize.clone();
	}
	
	 public Event  clone (){
		return new ResizedEvent(oldSize, newSize);
	}
	
	public IntDimension  getOldSize (){
		return oldSize.clone();
	}
	
	public IntDimension  getNewSize (){
		return newSize.clone();
	}
}


