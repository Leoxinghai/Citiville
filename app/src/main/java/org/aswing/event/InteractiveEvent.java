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

/**
 * The event that has is about interactive. 
 * <p>
 * The important property <code>programmatic</code> indicated that 
 * whether or not the event is fired by the programmatic reason or user mouse/keyboard interaction reason.
 * </p>
 * @see #isProgrammatic()
 * @author iiley
 */
public class InteractiveEvent extends AWEvent{
	
	/**
     *  The <code>InteractiveEvent.STATE_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>stateChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>isProgrammatic()</code></td><td>True means this event is fired by 
     * 		the programmatic reason, false means user mouse/keyboard interaction reason.</td></tr>
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
     *  @eventType stateChanged
	 */
	public static  String STATE_CHANGED ="stateChanged";
	
	/**
     *  The <code>InteractiveEvent.SELECTION_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>selectionChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>isProgrammatic()</code></td><td>True means this event is fired by 
     * 		the programmatic reason, false means user mouse/keyboard interaction reason.</td></tr>
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
     *  @eventType selectionChanged
	 */
	public static  String SELECTION_CHANGED ="selectionChanged";

	/**
     *  The <code>InteractiveEvent.SCROLL_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>scrollChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>isProgrammatic()</code></td><td>True means this event is fired by 
     * 		the programmatic reason, false means user mouse/keyboard interaction reason.</td></tr>
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
     *  @eventType scrollChanged
	 */
	public static  String SCROLL_CHANGED ="scrollChanged";
	
	/**
     *  The <code>InteractiveEvent.TEXT_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>textChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>isProgrammatic()</code></td><td>True means this event is fired by 
     * 		the programmatic reason, false means user mouse/keyboard interaction reason.</td></tr>
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
     *  @eventType textChanged
	 */
	public static  String TEXT_CHANGED ="textChanged";

	private boolean programmatic ;
	
	public  InteractiveEvent (String type ,boolean programmatic =false ,boolean bubbles =false ,boolean cancelable =false ){
		super(type, bubbles, cancelable);
		this.programmatic = programmatic;
	}
	
	/**
	 * Returns the programmatic property. True means this event is fired by the 
	 * programmatic reason, false means user mouse/keyboard interaction reason.
	 * <p>
	 * For example, if you drag the thumb of a slider, the slider will fire a event with <code>programmatic=false</code>, 
	 * in the slider internal programmatic progress, or you directly call <code>slider.setValue()</code> it may fire a event with <code>programmatic=true</code>.
	 * </p>
	 * @return the programmatic property.
	 */
	public boolean  isProgrammatic (){
		return programmatic;
	}
	
	 public Event  clone (){
		return new InteractiveEvent(type, isProgrammatic(), bubbles, cancelable);
	}
}


