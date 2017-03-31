
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

	
import org.aswing.JScrollBar;
import flash.events.Event;

/**
 * The event for <code>JScrollPane</code>.
 * @author iiley
 */
public class ScrollPaneEvent extends InteractiveEvent{
	
	/**
     *  The <code>ScrollPaneEvent.SCROLLBAR_STATE_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>scrollbarStateChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getScrollBar()</code></td><td>the state just changed scrollbar, null
     * 		if this is an viewport changed event</td></tr>
     *     <tr><td><code>isViewportChanged()</code></td><td>true if viewport just changed, 
     * 		false if this is an scrollbar scrolled event.</td></tr>
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
     *  @eventType scrollbarStateChanged
	 */
	public static  String SCROLLBAR_STATE_CHANGED ="scrollbarStateChanged";
	
	/**
     *  The <code>ScrollPaneEvent.VIEWPORT_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>viewportChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getScrollBar()</code></td><td>the state just changed scrollbar, null
     * 		if this is an viewport changed event</td></tr>
     *     <tr><td><code>isViewportChanged()</code></td><td>true if viewport just changed, 
     * 		false if this is an scrollbar scrolled event.</td></tr>
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
     *  @eventType viewportChanged
	 */
	public static  String VIEWPORT_CHANGED ="viewportChanged";			
	
	private JScrollBar scrollbar ;
	private boolean viewportChanged ;
	
	public  ScrollPaneEvent (String type ,boolean programmatic =false ,
		scrollbar:JScrollBar=null, viewportChanged:Boolean=false){
		super(type, programmatic, false, false);
	}
	
	 public Event  clone (){
		return new ScrollPaneEvent(type, isProgrammatic(), scrollbar, viewportChanged);
	}	
	
	/**
	 * Returns the state just changed scrollbar, return null if this is not a scroll event.
	 * @return the state just changed scrollbar.
	 * @see #isViewportChanged()
	 */
	public JScrollBar  getScrollBar (){
		return scrollbar;
	}
	
	/**
	 * Return whether or not the viewport changed.
	 * @return true if the viewport changed, false not.
	 * @see #getScrollBar()
	 */
	public boolean  isViewportChanged (){
		return viewportChanged;
	}
}


