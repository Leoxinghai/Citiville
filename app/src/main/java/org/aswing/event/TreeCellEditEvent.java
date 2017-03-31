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


import org.aswing.tree.TreePath;
import flash.events.Event;

/**
 * The event for tree cell editing.
 * @author iiley
 */
public class TreeCellEditEvent extends AWEvent{

	/**
     *  The <code>TreeCellEditEvent.EDITING_STARTED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>treeCellEditingStarted</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getPath()</code></td><td>the path be edit</td></tr>
     *     <tr><td><code>getOldValue()</code></td><td>the old value</td></tr>
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
     *  @eventType treeCellEditingStarted
	 */
	public static  String EDITING_STARTED ="treeCellEditingStarted";

	/**
     *  The <code>TreeCellEditEvent.EDITING_CANCELED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>treeCellEditingCanceled</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getPath()</code></td><td>the path be edit</td></tr>
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
     *  @eventType treeCellEditingCanceled
	 */
	public static  String EDITING_CANCELED ="treeCellEditingCanceled";

	/**
     *  The <code>TreeCellEditEvent.EDITING_STOPPED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>treeCellEditingStopped</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getPath()</code></td><td>the path be edit</td></tr>
     *     <tr><td><code>getOldValue()</code></td><td>the old value</td></tr>
     *     <tr><td><code>getNewValue()</code></td><td>the new value edited</td></tr>
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
     *  @eventType treeCellEditingStopped
	 */
	public static  String EDITING_STOPPED ="treeCellEditingStopped";

	private TreePath path ;
	private Object oldValue;
	private Object newValue;

	, = nullnewValuenull){
		super(type, bubbles, cancelable);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public TreePath  getPath (){
		return path;
	}

	public Object getOldValue () {
		return oldValue;
	}

	public Object getNewValue () {
		return newValue;
	}

	 public Event  clone (){
		return new TreeCellEditEvent(type, path, oldValue, newValue);
	}
}


