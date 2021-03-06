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
import org.aswing.Container;
import org.aswing.Component;
	

/**
 * The Event class is used to Container events. 
 * @author iiley
 */
public class ContainerEvent extends AWEvent
{	
	/**
     *  The <code>AWEvent.COM_ADDED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>comAdded</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getContainer()</code></td><td>the container who just be added a child.</td></tr>
     *     <tr><td><code>getChild()</code></td><td>the added child.</td></tr>
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
     *  @eventType comAdded
	 */
	public static  String COM_ADDED ="comAdded";
	
	/**
     *  The <code>AWEvent.COM_REMOVED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>comRemoved</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>getContainer()</code></td><td>the container who just be removed a child.</td></tr>
     *     <tr><td><code>getChild()</code></td><td>the removed child.</td></tr>
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
     *  @eventType comRemoved
	 */
	public static  String COM_REMOVED ="comRemoved";	
	
	private Container container ;
	private Component child ;
	
	/**
	 * Create an Container Event.
	 */
	public  ContainerEvent (String type ,Container container ,Component child ){
		super(type, false, false);
		this.container = container;
		this.child = child;
	}
	
	 public Event  clone (){
		//TODO:check if need this; return new ContainerEvent(type, container, child, bubbles, cancelable);
		return new ContainerEvent(type, container, child);
	}
	
	/**
	 * Returns the container whos component child was just removed.
	 */
	public Container  getContainer (){
		return container;
	}
	
	/**
	 * Returns the child component was just removed from its parent container.
	 */
	public Component  getChild (){
		return child;
	}
}



