/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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


import flash.display.*;
import flash.events.*;
import flash.geom.*;
import flash.utils.getTimer;

import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.plaf.basic.BasicToolTipUI;
import org.aswing.util.Timer;

/**
 * Dispatched when the tip text changed.
 * @eventType org.aswing.event.ToolTipEvent.TIP_TEXT_CHANGED
 */
.get(Event(name="tipTextChanged", type="org.aswing.event.ToolTipEvent"))

/**
 * Dispatched when the tip is showing(before showed), with this method you 
 * can change the tip text, then the showing text will be the new text.
 * @eventType org.aswing.event.ToolTipEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.ToolTipEvent"))

/**
 * Used to display a "Tip" for a Component. Typically components provide api
 * to automate the process of using <code>ToolTip</code>s.
 * For example, any AsWing component can use the <code>Component</code>
 * <code>setToolTipText</code> method to specify the text
 * for a standard tooltip. A component that wants to create a custom
 * <code>ToolTip</code>
 * display can create an owner <code>ToolTip</code> and apply it to a component by 
 * <code>JToolTip.setComponent</code> method.
 * @see org.aswing.JSharedToolTip
 * @author iiley
 */
public class JToolTip extends Container{
		
	//the time waiting after to view tool tip when roll over a component
	private static  int WAIT_TIME =600;
	//when there is one tooltip is just shown, next will shown fast as this time
	private static  int FAST_OCCUR_TIME =50;
	
	private static int last_tip_dropped_time =0;
	
	private static DisplayObjectContainer defaultRoot =null ;
	
	private DisplayObjectContainer containerRoot ;
	
	private String tipText ;
	private InteractiveObject comp ;
	private IntPoint offsets ;
	private boolean offsetsRelatedToMouse ;
	
	private Timer timer ;
	private boolean waitThenPopupEnabled ;
	
	public  JToolTip (){
		super();
		setName("JToolTip");
		offsets = new IntPoint(4, 20);
		offsetsRelatedToMouse = true;
		waitThenPopupEnabled  = true;
						
		timer = new Timer(WAIT_TIME, false);
		timer.setInitialDelay(WAIT_TIME);
		timer.addActionListener(__timeOnAction);
		
		mouseEnabled = false;
		mouseChildren = false;
		
		updateUI();
	}
		
	/**
	 * Sets the default container to hold tool tips.
	 * By default(if you have not set one), it is the <code>AsWingManager.getRoot()</code> .
	 * @param theRoot the default container to hold tool tips.
	 */
	public static void  setDefaultToolTipContainerRoot (DisplayObjectContainer theRoot ){
		if(theRoot != defaultRoot){
			defaultRoot = theRoot;
		}
	}
	
	private static DisplayObjectContainer  getDefaultToolTipContainerRoot (){
		if(defaultRoot == null){
			return AsWingManager.getRoot();
		}
		return defaultRoot;
	}
	
	/**
	 * Sets the container to hold this tool tip.
	 * By default(if you have not set one), it is the <code>getDefaultToolTipContainerRoot()</code>.
	 * @param theRoot the container to hold this tool tip.
	 */	
	public void  setToolTipContainerRoot (DisplayObjectContainer theRoot ){
		if(theRoot != containerRoot){
			containerRoot = theRoot;
		}
	}
	
	private DisplayObjectContainer  getToolTipContainerRoot (){
		if(containerRoot == null){
			DisplayObjectContainer cr ;
			if(getTargetComponent() != null){
				cr =(DisplayObjectContainer) getTargetComponent().root;
			}
			if(cr == null){
				cr = getDefaultToolTipContainerRoot();
			}
			return cr;
		}
		return containerRoot;
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicToolTipUI;
    }
	
	 public String  getUIClassID (){
		return "ToolTipUI";
	}
	
	/**
	 * Starts waits to popup, if mouse are not moved for a while, the tool tip will be popuped.
	 */
	public void  startWaitToPopup (){
		if(getTimer() - last_tip_dropped_time < FAST_OCCUR_TIME){
			timer.setInitialDelay(FAST_OCCUR_TIME);
		}else{
			timer.setInitialDelay(WAIT_TIME);
		}
		timer.restart();
		if(getTargetComponent()){
			getTargetComponent().addEventListener(MouseEvent.MOUSE_MOVE, __onMouseMoved, false, 0, true);
		}
	}
	
	/**
	 * Stops waiting.
	 */
	public void  stopWaitToPopup (){
		timer.stop();
		if(getTargetComponent()){
			getTargetComponent().removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMoved);
		}
		last_tip_dropped_time = getTimer();
	}
	
	/**
	 * Sets whether the tooltip should wait and then popup automatically when roll over the target component.
	 */
	public void  setWaitThenPopupEnabled (boolean b ){
		waitThenPopupEnabled = b;
	}
	
	/**
	 * Returns whether the tooltip should wait and then popup automatically when roll over the target component.
	 */
	public boolean  isWaitThenPopupEnabled (){
		return waitThenPopupEnabled;
	}
		
	protected void  __compRollOver (InteractiveObject source ){
		if(source == comp && isWaitThenPopupEnabled()){
			startWaitToPopup();
		}
	}
	
	protected void  __compRollOut (InteractiveObject source ){
		if(source == comp && isWaitThenPopupEnabled()){
			disposeToolTip();
		}
	}
		
	private void  __onMouseMoved (Event e ){
		if(timer.isRunning()){
			timer.restart();
		}
	}
	
	private void  __timeOnAction (Event e ){
		timer.stop();
		dispatchEvent(new ToolTipEvent(ToolTipEvent.TIP_SHOWING));
		disposeToolTip();
		viewToolTip();
	}
	
	/**
	 * view the tool tip on stage
	 */
	private void  viewToolTip (){
		if(tipText == null){
			return;
		}
		DisplayObjectContainer containerPane =getToolTipContainerRoot ();
		if(containerPane == null){
			trace("getToolTipContainerRoot null");
			return;
		}
		containerPane.addChild(this);
		
		IntPoint relatePoint =new IntPoint ();
		if(offsetsRelatedToMouse){
			Point gp =containerPane.localToGlobal(new Point(containerPane.mouseX ,containerPane.mouseY ));
			relatePoint.setWithPoint(gp);
		}else{
			relatePoint.setWithPoint(getTargetComponent().localToGlobal(new Point(0, 0)));
		}
		moveLocationRelatedTo(relatePoint);
	}
	
	/**
	 * Moves tool tip to new location related to the specified pos(global).
	 */
	public void  moveLocationRelatedTo (IntPoint globalPos ){
		if(!isShowing()){
			//not created, so can't move
			return;
		}
		globalPos = globalPos.clone();
		globalPos.move(offsets.x, offsets.y);
		IntDimension viewSize =getPreferredSize ();
		IntRectangle visibleBounds =AsWingUtils.getVisibleMaximizedBounds(parent );
		
		if(globalPos.x + viewSize.width > visibleBounds.x + visibleBounds.width){
			globalPos.x = visibleBounds.x + visibleBounds.width - viewSize.width;
		}
		if(globalPos.y + viewSize.height > visibleBounds.y + visibleBounds.height){
			globalPos.y = visibleBounds.y + visibleBounds.height - viewSize.height;
		}
		if(globalPos.x < visibleBounds.x){
			globalPos.x = visibleBounds.x;
		}
		if(globalPos.y < visibleBounds.y){
			globalPos.y = visibleBounds.y;
		}
		setGlobalLocation(globalPos);
		setSize(viewSize);
		revalidate();
	}
	
	/**
	 * Shows tooltip directly.
	 */
	public void  showToolTip (){
		viewToolTip();
	}
	
	/**
	 * Disposes the tool tip.
	 */
	public void  disposeToolTip (){
		stopWaitToPopup();
		removeFromContainer();
	}
		
	/**
	 * Sets the text to show when the tool tip is displayed. 
	 * null to set this tooltip will not be displayed.
	 * @param t the String to display, or null not display tool tip.
	 */
	public void  setTipText (String t ){
		if(t != tipText){
			tipText = t;
			dispatchEvent(new ToolTipEvent(ToolTipEvent.TIP_TEXT_CHANGED));
			if(t == null){
				disposeToolTip();
			}else{
				if(isShowing()){
					setSize(getPreferredSize());
					repaint();
					revalidate();
				}
			}
		}
	}
	
	/**
	 * Returns the text that is shown when the tool tip is displayed. 
	 * The returned value may be null. 
	 * @return the string that displayed.
	 */
	public String  getTipText (){
		return tipText;
	}
	
	/**
	 * Specifies the component that the tooltip describes. 
	 * The component c may be null and will have no effect. 
	 * @param the JComponent being described
	 */
	public void  setTargetComponent (InteractiveObject c ){
		if(c != comp){
			if(comp != null){
				unlistenOwner(comp);
			}
			comp = c;
			if(comp != null){
				listenOwner(comp);
			}
		}
	}
	
	/**
	 * Returns the component the tooltip applies to. 
	 * The returned value may be null. 
	 * @return the component that the tooltip describes
	 */
	public InteractiveObject  getTargetComponent (){
		return comp;
	}
	
	/**
	 * Sets the offsets of the tooltip related the described component.
	 * @param o the offsets point, delta x is o.x, delta y is o.y
	 */
	public void  setOffsets (IntPoint o ){
		offsets.setLocation(o);
	}
	
	/**
	 * Returns the offsets of the tooltip related the described component.
	 * @return the offsets point, delta x is o.x, delta y is o.y
	 */	
	public IntPoint  getOffsets (){
		return offsets.clone();
	}
	
	/**
	 * Sets whether the <code>offsets</code> is related the mouse position, otherwise 
	 * it will be related the described component position.
	 * <p>
	 * This change will be taked effect at the next showing, current showing will no be changed.
	 * @param b whether the <code>offsets</code> is related the mouse position
	 */
	public void  setOffsetsRelatedToMouse (boolean b ){
		offsetsRelatedToMouse = b;
	}
	
	/**
	 * Returns whether the <code>offsets</code> is related the mouse position.
	 * @return whether the <code>offsets</code> is related the mouse position
	 * @see #setOffsetsRelatedToMouse()
	 */
	public boolean  isOffsetsRelatedToMouse (){
		return offsetsRelatedToMouse;
	}
	
	protected void  listenOwner (InteractiveObject comp ,boolean useWeakReference =false ){
		comp.addEventListener(MouseEvent.ROLL_OVER, ____compRollOver, false, 0, useWeakReference);
		comp.addEventListener(MouseEvent.ROLL_OUT, ____compRollOut, false, 0, useWeakReference);
		comp.addEventListener(MouseEvent.MOUSE_DOWN, ____compRollOut, false, 0, useWeakReference);
	}
	protected void  unlistenOwner (InteractiveObject comp ){
		comp.removeEventListener(MouseEvent.ROLL_OVER, ____compRollOver);
		comp.removeEventListener(MouseEvent.ROLL_OUT, ____compRollOut);
		comp.removeEventListener(MouseEvent.MOUSE_DOWN, ____compRollOut);
		//maybe showing, so this event need to remove
		comp.removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMoved);
	}
	
	
	//-----------can't override these------
	private void  ____compRollOver (Event e ){
		InteractiveObject source =(InteractiveObject)e.currentTarget;
		__compRollOver(source);
	}
	
	private void  ____compRollOut (Event e ){
		InteractiveObject source =(InteractiveObject)e.currentTarget;
		__compRollOut(source);
	}
}


