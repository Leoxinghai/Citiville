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
import flash.geom.Point;
import flash.text.TextField;
import flash.ui.Keyboard;

import org.aswing.event.*;
import org.aswing.util.DepthManager;
import org.aswing.util.ArrayList;
import org.aswing.util.WeakMap;

/**
 * FocusManager manages all the when a component should receive focus, i.e if it
 * can.
 * 
 * @author iiley
 */
public class FocusManager{
	
	private static WeakMap managers =new WeakMap ();
	private static boolean defaultTraversalEnabled =true ;
	
	private Component oldFocusOwner ;
	private Component focusOwner ;
	private ArrayList popups ;
	private JWindow activeWindow ;
	private boolean traversalEnabled =true ;
	private boolean traversalDefault =true ;
		
	private Stage stage ;
	private Sprite focusRect ;
	private boolean inited ;
	private FocusTraversalPolicy defaultPolicy ;
	private boolean traversing ;
		
	public  FocusManager (Stage theStage ){
		traversing = false;
		inited = false;
		defaultPolicy = new ContainerOrderFocusTraversalPolicy();
		popups = new ArrayList();
		init(theStage);
	}
	
    /**
     * Returns the current FocusManager instance
     *
     * @return this the current FocusManager instance
     * @see #setCurrentManager
     */
	public static FocusManager  getManager (Stage theStage ){
		if(theStage == null){
			return null;
		}
		FocusManager manager =managers.getValue(theStage );
		if(manager == null){
			manager = new FocusManager(theStage);
			managers.put(theStage, manager);
		}		
		return manager;
	}
	private manager this reference stage keep for just void  __referenceEvent (Event e ){//
	}
	
	/**
     * Sets the current FocusManager instance. If null is specified, 
     * then the current FocusManager is replaced with a new instance of FocusManager.
     * 
     * @param newManager the new FocusManager
     * @see #getCurrentManager
     * @see org.aswing.FocusManager
	 */
	public static void  setManager (Stage theStage ,FocusManager newManager ){
		if(theStage == null){
			throw new Error("theStage can't be null!");
		}
		if(newManager == null){
			newManager = new FocusManager(theStage);
		}
		FocusManager oldManager =managers.getValue(theStage );
		if(oldManager != newManager){
			if(oldManager != null){
				oldManager.uninit();
			}
			managers.put(theStage, newManager);
		}
	}
	
	/**
	 * Init the focus manager, it will only start works when it is inited.
	 * By default, it will be inited when a component is added to stage automatically.
	 */
	public void  init (Stage theStage ){
		if(!inited){
			stage = theStage;
			inited = true;
			stage.addEventListener(FocusEvent.KEY_FOCUS_CHANGE, __onKeyFocusChange, false, 0, true);
			stage.addEventListener(FocusEvent.MOUSE_FOCUS_CHANGE, __onMouseFocusChange, false, 0, true);
			stage.addEventListener(KeyboardEvent.KEY_DOWN, __onKeyDown, false, 0, true);
			stage.addEventListener(KeyboardEvent.KEY_UP, __onKeyUp, false, 0, true);
			stage.addEventListener(MouseEvent.MOUSE_DOWN, __onMouseDown, false, 0, true);
			focusRect = new Sprite();
			focusRect.mouseEnabled = false;
			focusRect.visible = false;
			stage.addChild(focusRect);
			//Make stage reference this manager to keep manager will not be GC until stage be GC.
			stage.addEventListener(Event.DEACTIVATE, __referenceEvent);
		}
	}
	
	public ArrayList  getPopupsVector (){
		return popups;
	}
	
	private Component focusPaintedComponent ;
	
	public Sprite  moveFocusRectUpperTo (Component c ){
		if(focusPaintedComponent != c){
			if(focusPaintedComponent != null){
				removeistenerToFocusPaintedComponent();
			}
			focusPaintedComponent = c;
			addListenerToFocusPaintedComponent();
		}
		
		DepthManager.bringToTop(focusRect);
		Point p =c.localToGlobal(new Point ());
		focusRect.x = p.x;
		focusRect.y = p.y;
		return focusRect;
	}
	
	private void  addListenerToFocusPaintedComponent (){
		focusPaintedComponent.addEventListener(MovedEvent.MOVED, __focusPaintedComMoved);
		focusPaintedComponent.addEventListener(ResizedEvent.RESIZED, __focusPaintedComResized);
		focusPaintedComponent.addEventListener(Event.REMOVED_FROM_STAGE, __focusPaintedComRemoved);
	}
	
	private void  removeistenerToFocusPaintedComponent (){
		if(focusPaintedComponent != null){
			focusPaintedComponent.removeEventListener(MovedEvent.MOVED, __focusPaintedComMoved);
			focusPaintedComponent.removeEventListener(ResizedEvent.RESIZED, __focusPaintedComResized);
			focusPaintedComponent.removeEventListener(Event.REMOVED_FROM_STAGE, __focusPaintedComRemoved);
			focusPaintedComponent = null;
		}
	}
	
	private void  __focusPaintedComRemoved (Event e ){
		focusRect.graphics.clear();
		removeistenerToFocusPaintedComponent();
	}
	
	private void  __focusPaintedComMoved (MovedEvent e ){
		if(focusRect.visible){
			int dx =e.getNewLocation ().x -e.getOldLocation ().x ;
			int dy =e.getNewLocation ().y -e.getOldLocation ().y ;
			focusRect.x += dx;
			focusRect.y += dy;
		}
	}
	private void  __focusPaintedComResized (ResizedEvent e ){
		if(focusRect.visible){
			focusPaintedComponent.paintFocusRect(true);
		}
	}
	
	/**
	 * Un-init this focus manager.
	 */
	public void  uninit (){
		if(stage != null){
			stage.removeEventListener(FocusEvent.KEY_FOCUS_CHANGE, __onKeyFocusChange, false);
			stage.removeEventListener(FocusEvent.MOUSE_FOCUS_CHANGE, __onMouseFocusChange, false);
			stage.removeEventListener(KeyboardEvent.KEY_DOWN, __onKeyDown, false);
			stage.removeEventListener(KeyboardEvent.KEY_UP, __onKeyUp, false);
			stage.removeEventListener(MouseEvent.MOUSE_DOWN, __onMouseDown, false);
			stage = null;
			focusOwner = null;
			activeWindow = null;
			defaultPolicy = null;
			focusPaintedComponent = null;
			if(focusRect.parent){
				focusRect.parent.removeChild(focusRect);
			}
			focusRect = null;
			inited = false;
			oldFocusOwner = null;
			traversing = false;
		}
	}
	
	private void  __onMouseDown (MouseEvent e ){
		setTraversing(false);
	}
	
	private void  __onMouseFocusChange (FocusEvent e ){
		//prevent default focus change if the related object is not tabEnabled
		if(focusOwner != null){
			InteractiveObject tar =(InteractiveObject)e.relatedObject;
			if(AsWingManager.isPreventNullFocus() 
				&& (tar == null || !(tar is TextField || tar.tabEnabled))
				|| (tar is Component && !Component(tar).isFocusable())){
				e.preventDefault();
			}
		}
	}
	
	private void  __onKeyFocusChange (FocusEvent e ){
		if(!isTraversalEnabled()){
			return;
		}
		if(focusOwner != null){
			e.preventDefault();
		}
		if(e.keyCode != Keyboard.TAB){
			return;
		}
		setTraversing(true);
		if(e.shiftKey){
			focusPrevious();
		}else{
			focusNext();
		}
	}
	
	private void  __onKeyDown (KeyboardEvent e ){
		if(focusOwner != null){
			focusOwner.fireFocusKeyDownEvent(e);
		}
	}
	
	private void  __onKeyUp (KeyboardEvent e ){
		if(focusOwner != null){
			focusOwner.fireFocusKeyUpEvent(e);
		}
	}
	
	/**
	 * Returns if the focus is traversing by keys.
	 * <p>
	 * Once when focus traversed by FocusTraversalKeys this is turned on, 
	 * true will be returned. Once when Mouse is down, this will be turned off, 
	 * false will be returned.
	 * @return true if the focus is traversing by FocusTraversalKeys, otherwise returns false.
	 * @see #getDefaultFocusTraversalKeys()
	 * @see Component#getFocusTraversalKeys()
	 */
	public boolean  isTraversing (){
		return traversing;
	}
	
	/**
	 * Sets if the focus is traversing by keys.
	 * <p>
	 * By default, the traversing property will only be set true when TRAVERSAL_KEYS down.
	 * If your component need view focus rect, you should set it to true when your component's 
	 * managed key down. 
	 * @param b true tag traversing to be true, false tag traversing to be false
	 * @see #isTraversing()
	 */
	public void  setTraversing (boolean b ){
		traversing = b;
		focusRect.visible = b;
		if(!b){
			focusRect.graphics.clear();
			removeistenerToFocusPaintedComponent();
		}
	}
		
	/**
	 * The default to disables or enables the traversal by keys pressing.
	 * @see #setTraversalEnabled()
	 * @see #setTraversalAsDefault()
	 */
	public static void  setDefaultTraversalEnabled (boolean b ){
		defaultTraversalEnabled = b;
	}
	
	/**
	 * Returns the default value for <code>defaultTraversalEnabled</code>.
	 * @see #setTraversalEnabled()
	 * @see #setTraversalAsDefault()
	 */
	public static boolean  isDefaultTraversalEnabled (){
		return defaultTraversalEnabled;
	}
	
	/**
	 * Disables or enables the traversal by keys pressing.
	 * This will call <code>setTraversalAsDefault(false)</code>
	 * <p>
	 * If this method called, TAB... keys will or not effect the focus traverse with this focus system. 
	 * </p>
	 * <p>
	 * And component will or not fire any Key events when there are focused and key pressed.
	 * </p>
	 * @see #setTraversalAsDefault()
	 * @see #setDefaultTraversalEnabled()
	 */
	public void  setTraversalEnabled (boolean b ){
		traversalEnabled = b;
		setTraversalAsDefault(false);
	}
	
	/**
	 * Returns traversal by keys pressing is enabled or not. 
	 * If <code>isTraversalAsDefault()</code> returns true, this will returns <code>isDefaultTraversalEnabled</code>
	 * <p>
	 * If this method called, TAB... keys will or not effect the focus traverse with this focus system. 
	 * </p>
	 * <p>
	 * And component will or not fire any Key events when there are focused and key pressed.
	 * </p>
	 * @see #isTraversalAsDefault()
	 * @see #setDefaultTraversalEnabled()
	 */
	public boolean  isTraversalEnabled (){
		return traversalEnabled;
	}
	
	/**
	 * Sets whether or not to use default value for traversal enabled.
	 * @see #isTraversalEnabled()
	 * @see #setDefaultTraversalEnabled()
	 */
	public void  setTraversalAsDefault (boolean b ){
		traversalDefault = b;
	}
	
	/**
	 * Returns whether or not to use default value for traversal enabled.
	 * @see #isTraversalEnabled()
	 * @see #setDefaultTraversalEnabled()
	 */
	public boolean  isTraversalAsDefault (){
		return traversalDefault;
	}
	
	/**
     * Returns the previous focused component.
     *
     * @return the previous focused component.
     */
	public Component  getPreviousFocusedComponent (){
		return oldFocusOwner;
	}

	/**
     * Returns the focus owner.
     *
     * @return the focus owner.
     * @see #setFocusOwner()
     */
	public Component  getFocusOwner (){
		return focusOwner;
	}
	
	/**
     * Sets the focus owner. The operation will be cancelled if the Component
     * is not focusable.
     * <p>
     * This method does not actually set the focus to the specified Component.
     * It merely stores the value to be subsequently returned by
     * <code>getFocusOwner()</code>. Use <code>Component.requestFocus()</code>
     * or <code>Component.requestFocusInWindow()</code> to change the focus
     * owner.
     *
     * @param focusOwner the focus owner
     * @see #getFocusOwner()
     * @see Component#requestFocus()
     * @see Component#isFocusable()
	 */
	public void  setFocusOwner (Component newFocusOwner ){
		if(focusOwner != newFocusOwner){
			oldFocusOwner = focusOwner;
			focusOwner = newFocusOwner;
		}
	}
	
	/**
     * Returns the active Window.
     * The active Window is always either the focused Window, or the first
     * Window that is an owner of the focused Window.
     *
     * @return the active Window
     * @see #setActiveWindow()
	 */
	public JWindow  getActiveWindow (){
		return activeWindow;
	}
	
	/**
     * Sets the active Window. The active Window is always either the 
     * focused Window, or the first Window that is an owner of the focused Window.
     * <p>
     * This method does not actually change the active Window . 
     * It merely stores the value to be
     * subsequently returned by <code>getActiveWindow()</code>. Use
     * <code>Component.requestFocus()</code> or
     * <code>Component.requestFocusInWindow()</code> or
     * <code>JWindow.setActive()</code>to change the active
     * Window.
     *
     * @param activeWindow the active Window
     * @see #getActiveWindow()
     * @see Component#requestFocus()
     * @see Component#requestFocusInWindow()
     * @see JWindow#setActive()
	 */
	public void  setActiveWindow (JWindow newActiveWindow ){
		activeWindow = newActiveWindow;
	}	
	
    /**
     * Focuses the Component after aComponent, typically based on a
     * FocusTraversalPolicy.
     *
     * @param aComponent the Component that is the basis for the focus
     *        traversal operation
     * @see FocusTraversalPolicy
     */
	public void  focusNextOfComponent (Component aComponent ){
        if (aComponent != null) {
            aComponent.transferFocus();
        }
	}
	
    /**
     * Focuses the Component before aComponent, typically based on a
     * FocusTraversalPolicy.
     *
     * @param aComponent the Component that is the basis for the focus
     *        traversal operation
     * @see FocusTraversalPolicy
     */	
	public void  focusPreviousOfComponent (Component aComponent ){
        if (aComponent != null) {
            aComponent.transferFocusBackward();
        }
	}

    /**
     * Focuses the Component after the current focus owner.
     * @see #focusNextOfComponent()
     */	
	public void  focusNext (){
		focusNextOfComponent(getFocusOwner());
	}
    /**
     * Focuses the Component before the current focus owner.
     * @see #focusPreviousOfComponent()
     */	
	public void  focusPrevious (){
		focusPreviousOfComponent(getFocusOwner());
	}
	
	/**
     * Returns the default FocusTraversalPolicy. Top-level components 
     * use this value on their creation to initialize their own focus traversal
     * policy by explicit call to Container.setFocusTraversalPolicy.
     *
     * @return the default FocusTraversalPolicy. null will never be returned.
     * @see #setDefaultFocusTraversalPolicy()
     * @see Container#setFocusTraversalPolicy()
     * @see Container#getFocusTraversalPolicy()
     */
	public FocusTraversalPolicy  getDefaultFocusTraversalPolicy (){
		return defaultPolicy;
	}

    /**
     * Sets the default FocusTraversalPolicy. Top-level components 
     * use this value on their creation to initialize their own focus traversal
     * policy by explicit call to Container.setFocusTraversalPolicy.
     * Note: this call doesn't affect already created components as they have 
     * their policy initialized. Only new components will use this policy as
     * their default policy.
     *
     * @param defaultPolicy the new, default FocusTraversalPolicy, if it is null, nothing will be done
     * @see #getDefaultFocusTraversalPolicy()
     * @see Container#setFocusTraversalPolicy()
     * @see Container#getFocusTraversalPolicy()
     */	
	public void  setDefaultFocusTraversalPolicy (FocusTraversalPolicy newDefaultPolicy ){
		if (newDefaultPolicy != null){
			defaultPolicy = newDefaultPolicy;
		}
	}
}


