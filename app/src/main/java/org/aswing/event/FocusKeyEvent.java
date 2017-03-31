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

	
import flash.events.KeyboardEvent;
import flash.events.Event;

public class FocusKeyEvent extends KeyboardEvent{

	/**
     *  The <code>FocusKeyEvent.FOCUS_KEY_DOWN</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>focusKeyDown</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>charCode</code></td><td>The character code value of the key pressed or released.</td></tr>
     *     <tr><td><code>keyCode</code></td><td>The key code value of the key pressed or released.</td></tr>
     *     <tr><td><code>keyLocation</code></td><td>The location of the key on the keyboard.</td></tr>
     *     <tr><td><code>ctrlKey</code></td><td>true if the Control key is active; false if it is inactive.</td></tr>
     *     <tr><td><code>altKey</code></td><td>false, reserved</td></tr>
     *     <tr><td><code>shiftKey</code></td><td>true if the Shift key is active; false if it is inactive.</td></tr>
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
     *  @eventType focusKeyDown
	 */	 
	public static  String FOCUS_KEY_DOWN ="focusKeyDown";

	/**
     *  The <code>FocusKeyEvent.FOCUS_KEY_UP</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>focusKeyUp</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>charCode</code></td><td>The character code value of the key pressed or released.</td></tr>
     *     <tr><td><code>keyCode</code></td><td>The key code value of the key pressed or released.</td></tr>
     *     <tr><td><code>keyLocation</code></td><td>The location of the key on the keyboard.</td></tr>
     *     <tr><td><code>ctrlKey</code></td><td>true if the Control key is active; false if it is inactive.</td></tr>
     *     <tr><td><code>altKey</code></td><td>false, reserved</td></tr>
     *     <tr><td><code>shiftKey</code></td><td>true if the Shift key is active; false if it is inactive.</td></tr>
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
     *  @eventType focusKeyUp
	 */	 	 
	public static  String FOCUS_KEY_UP ="focusKeyUp";		
	
	public  FocusKeyEvent (String type ,int charCode =0.0,int keyCode =0.0,int keyLocation =0.0,boolean ctrlKey =false ,boolean altKey =false ,boolean shiftKey =false ){
		super(type, false, false, charCode, keyCode, keyLocation, ctrlKey, altKey, shiftKey);
	}
	
	 public Event  clone (){
		return new FocusKeyEvent(type, charCode, keyCode, keyLocation, ctrlKey, altKey, shiftKey);
	}
	
}


