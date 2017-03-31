
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
import flash.events.MouseEvent;

import org.aswing.ListCell;

/**
 * The event for items of List.
 * @author iiley
 */
public class ListItemEvent extends MouseEvent{

	/**
     *  The <code>ListItemEvent.ITEM_CLICK</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemClick</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemClick
	 */
	public static  String ITEM_CLICK ="itemClick";

	/**
     *  The <code>ListItemEvent.ITEM_DOUBLE_CLICK</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemDoubleClick</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemDoubleClick
	 */
	public static  String ITEM_DOUBLE_CLICK ="itemDoubleClick";

	/**
     *  The <code>ListItemEvent.ITEM_MOUSE_DOWN</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemMouseDown</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemMouseDown
	 */
	public static  String ITEM_MOUSE_DOWN ="itemMouseDown";

	/**
     *  The <code>ListItemEvent.ITEM_ROLL_OVER</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemRollOver</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemRollOver
	 */
	public static  String ITEM_ROLL_OVER ="itemRollOver";

	/**
     *  The <code>ListItemEvent.ITEM_ROLL_OUT</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemRollOut</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemRollOut
	 */
	public static  String ITEM_ROLL_OUT ="itemRollOut";

	/**
     *  The <code>ListItemEvent.ITEM_RELEASE_OUT_SIDE</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>itemReleaseOutSide</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getValue()</code></td><td>the value of this item</td></tr>
     *     <tr><td><code>getCell()</code></td><td>the cell(cell renderer) of this item</td></tr>
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
     *  @eventType itemReleaseOutSide
	 */
	public static  String ITEM_RELEASE_OUT_SIDE ="itemReleaseOutSide";

	private Object value;
	private ListCell cell ;

	/**
	 * @param type
	 * @param value
	 * @param cell
	 * @param e the original mouse event
	 */
	public  ListItemEvent (String type ,*value ,ListCell cell ,MouseEvent e ){
		super(type, false, false, e.localX, e.localY, e.relatedObject, e.ctrlKey, e.altKey, e.shiftKey, e.buttonDown);
		this.value = value;
		this.cell = cell;
	}

	public Object getValue () {
		return value;
	}

	public ListCell  getCell (){
		return cell;
	}

	 public Event  clone (){
		return new ListItemEvent(type, value, cell, this);
	}
}


