
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
 * The event for table cell editing.
 * @author iiley
 */
public class TableCellEditEvent extends AWEvent{

	/**
     *  The <code>TableCellEditEvent.EDITING_STARTED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>tableCellEditingStarted</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getRow()</code></td><td>the row be edit</td></tr>
     *     <tr><td><code>getColumn()</code></td><td>the column be edit</td></tr>
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
     *  @eventType tableCellEditingStarted
	 */
	public static  String EDITING_STARTED ="tableCellEditingStarted";

	/**
     *  The <code>TableCellEditEvent.EDITING_CANCELED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>tableCellEditingCanceled</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getRow()</code></td><td>the row be edit</td></tr>
     *     <tr><td><code>getColumn()</code></td><td>the column be edit</td></tr>
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
     *  @eventType tableCellEditingCanceled
	 */
	public static  String EDITING_CANCELED ="tableCellEditingCanceled";

	/**
     *  The <code>TableCellEditEvent.EDITING_STOPPED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>tableCellEditingStopped</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getRow()</code></td><td>the row be edit</td></tr>
     *     <tr><td><code>getColumn()</code></td><td>the column be edit</td></tr>
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
     *  @eventType tableCellEditingStopped
	 */
	public static  String EDITING_STOPPED ="tableCellEditingStopped";

	private int row ;
	private int column ;
	private Object oldValue;
	private Object newValue;

	/**
	 * Create a cell edit event.
	 * @param type the type
	 * @param row the edit row
	 * @param column the edit column
	 * @param oldValue the old value
	 * @param newValue the edited new value
	 */
	, = nullnewValuenull){
		super(type, false, false);
		this.row = row;
		this.column = column;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public int  getRow (){
		return row;
	}

	public int  getColumn (){
		return column;
	}

	public Object getOldValue () {
		return oldValue;
	}

	public Object getNewValue () {
		return newValue;
	}

	 public Event  clone (){
		return new TableCellEditEvent(type, row, column, oldValue, newValue);
	}
}


