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


import org.aswing.Component;
import org.aswing.dnd.*;
import org.aswing.geom.IntPoint;
import flash.events.Event;

public class DragAndDropEvent extends AWEvent{
	
	/**
     *  The <code>DragAndDropEvent.DRAG_RECOGNIZED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragRecongnized</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
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
     *  @eventType dragRecongnized
	 */
	public static  String DRAG_RECOGNIZED ="dragRecognized";	
	
	/**
     *  The <code>DragAndDropEvent.DRAG_START</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragStart</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getSourceData()</code></td><td>the drag source data</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
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
     *  @eventType dragStart
	 */
	public static  String DRAG_START ="dragStart";	

	/**
     *  The <code>DragAndDropEvent.DRAG_ENTER</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragEnter</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getSourceData()</code></td><td>the drag source data</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
     *     <tr><td><code>getTargetComponent()</code></td><td>the mouse entered target component</td></tr>
     *     <tr><td><code>getRelatedTargetComponent()</code></td><td>the previouse entered 
     * 		target component</td></tr>
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
     *  @eventType dragEnter
	 */
	public static  String DRAG_ENTER ="dragEnter";	
		
	/**
     *  The <code>DragAndDropEvent.DRAG_OVERRING</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragOverring</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getSourceData()</code></td><td>the drag source data</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
     *     <tr><td><code>getTargetComponent()</code></td><td>the mouse entered target component</td></tr>
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
     *  @eventType dragOverring
	 */
	public static  String DRAG_OVERRING ="dragOverring";	
	
	/**
     *  The <code>DragAndDropEvent.DRAG_EXIT</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragExit</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getSourceData()</code></td><td>the drag source data</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
     *     <tr><td><code>getTargetComponent()</code></td><td>the mouse entered target component</td></tr>
     *     <tr><td><code>getRelatedTargetComponent()</code></td><td>the next being entered 
     * 		target component</td></tr>
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
     *  @eventType dragExit
	 */
	public static  String DRAG_EXIT ="dragExit";	
	
	/**
     *  The <code>DragAndDropEvent.DRAG_DROP</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>dragDrop</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getDragInitiator()</code></td><td>the drag initiator component</td></tr>
     *     <tr><td><code>getSourceData()</code></td><td>the drag source data</td></tr>
     *     <tr><td><code>getMousePosition()</code></td><td>the mouse point in stage scope</td></tr>
     *     <tr><td><code>getTargetComponent()</code></td><td>the mouse entered target component</td></tr>
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
     *  @eventType dragDrop
	 */
	public static  String DRAG_DROP ="dragDrop";		
	
	private Component dragInitiator ;
	private SourceData sourceData ;
	private IntPoint mousePos ;
	private Component targetComponent ;
	private Component relatedTargetComponent ;
	
	/**
	 * Create a drag and drop event.
	 * @param type the type.
	 * @param dragInitiator the drag initiator component.
	 * @param dragSource the data source.
	 * @param mousePos a <code>IntPoint</code> indicating the cursor location in global space.
	 * @param targetComponent the mouse entered component.
	 * @param relatedTargetComponent the related target component.
	 */
	public  DragAndDropEvent (String type ,
		dragInitiator:Component, sourceData:SourceData, mousePos:IntPoint, 
		targetComponent:Component=null, relatedTargetComponent:Component=null){
		super(type, false, false);
		this.dragInitiator = dragInitiator;
		this.sourceData = sourceData;
		this.mousePos = mousePos.clone();
		this.targetComponent = targetComponent;
		this.relatedTargetComponent = relatedTargetComponent;
	}
	
	 public Event  clone (){
		return new DragAndDropEvent(type, dragInitiator, sourceData, mousePos, targetComponent);
	}
	
	/**
	 * Returns the drag initiator component.
	 */
	public Component  getDragInitiator (){
		return dragInitiator;
	}
	/**
	 * Returns the data source.
	 * <p>
	 * For <code>DRAG_RECOGNIZED</code> events this property is null.
	 * </p>
	 */
	public SourceData  getSourceData (){
		return sourceData;
	}
	/**
	 * Returns a <code>IntPoint</code> indicating the cursor location in global space.
	 */
	public IntPoint  getMousePosition (){
		return mousePos;
	}
	/**
	 * Returns the mouse entered component. 
	 * <p>
	 * For <code>DRAG_START</code> and <code>DRAG_RECOGNIZED</code> events this property is always null.
	 * </p>
	 */
	public Component  getTargetComponent (){
		return targetComponent;
	}
	/**
	 * Returns the related mouse entered component. For <code>DRAG_ENTER</code> event, 
	 * it is the previous target component, for <code>DRAG_EXIT</code> it is the next being entered 
	 * target component.
	 * <p>
	 * For <code>DRAG_START</code>, <code>DRAG_RECOGNIZED</code>, 
	 * <code>DRAG_OVERRING</code>, <code>DRAG_DROP</code> events this property is always null.
	 * </p>
	 */
	public Component  getRelatedTargetComponent (){
		return relatedTargetComponent;
	}
}


