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

	
import flash.display.DisplayObject;
import flash.display.DisplayObjectContainer;
import flash.display.InteractiveObject;
import flash.display.Sprite;
import flash.display.Stage;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.ui.Mouse;
import flash.utils.Dictionary;

import org.aswing.util.DepthManager;
import org.aswing.util.WeakMap;
	
/**
 * The CursorManager, manage the cursor, hide system mouse cursor, show custom cursor, 
 * etc.
 * @author iiley
 */
public class CursorManager{
	
	protected DisplayObjectContainer root =null ;
	protected DisplayObjectContainer cursorHolder =null ;
	protected DisplayObject currentCursor =null ;
	
	/**
	 * Create a CursorManage
	 * @param cursorRoot the container to hold the cursors
	 * @see #getManager()
	 */
	public  CursorManager (DisplayObjectContainer cursorRoot ){
		setCursorContainerRoot(cursorRoot);
	}
	
	private static WeakMap managers =new WeakMap ();
	
	/**
	 * Returns the default cursor manager for specified stage.
	 * <p>
	 * Generally, you should call this method to get cursor manager, 
	 * it will create one manager for each stage.
	 * </p>
	 * @param stage the stage, if pass null, the inital Stage of <code>AsWingManager.getStage()</code> 
	 * 			will be used.
	 */
	public static CursorManager  getManager (Stage stage =null ){
		if(stage == null){
			stage = AsWingManager.getStage();
		}
		if(stage == null){
			return null;
		}
		CursorManager manager =managers.getValue(stage );
		if(manager == null){
			manager = new CursorManager(stage);
			managers.put(stage, manager);
		}
		return manager;
	}
	
	/**
	 * Sets the container to hold the cursors(in fact it will hold the cursor's parent--a sprite).
	 * By default(if you have not set one), it is the stage if <code>AsWingManager</code> is inited.
	 * @param theRoot the container to hold the cursors.
	 */
	protected void  setCursorContainerRoot (DisplayObjectContainer theRoot ){
		if(theRoot != root){
			if(root){
				root.removeEventListener(Event.DEACTIVATE, __referenceEvent);
			}
			root = theRoot;
			//Make root reference this manager to keep manager will not be GC until root be GC.
			root.addEventListener(Event.DEACTIVATE, __referenceEvent);
			if(cursorHolder != null && cursorHolder.parent != root){
				root.addChild(cursorHolder);
			}
		}
	}
	private manager this reference stage keep for just void  __referenceEvent (Event e ){//
	}
	
	protected DisplayObjectContainer  getCursorContainerRoot (){
		return root;
	}
	
	/**
	 * Shows your display object as the cursor and/or not hide the system cursor. 
	 * If current another custom cursor is showing, the current one will be removed 
	 * and then the new one is shown.
	 * @param cursor the display object to be add to the cursor container to be the cursor
	 * @param hideSystemCursor whether or not hide the system cursor when custom cursor shows.
	 */
	public void  showCustomCursor (DisplayObject cursor ,boolean hideSystemCursor =true ){
		if(hideSystemCursor){
			Mouse.hide();
		}else{
			Mouse.show();
		}
		if(cursor == currentCursor){
			return;
		}
		
		DisplayObjectContainer ro =getCursorContainerRoot ();
		if(cursorHolder == null){
			if(ro != null){
				cursorHolder = new Sprite();
				cursorHolder.mouseEnabled = false;
				cursorHolder.tabEnabled = false;
				cursorHolder.mouseChildren = false;
				ro.addChild(cursorHolder);
			}
		}
		if(cursorHolder != null){
			if(currentCursor != cursor){
				if(currentCursor != null){
					cursorHolder.removeChild(currentCursor);
				}
				currentCursor = cursor;
				cursorHolder.addChild(currentCursor);
			}
			DepthManager.bringToTop(cursorHolder);
			ro.stage.addEventListener(MouseEvent.MOUSE_MOVE, __mouseMove, false, 0, true);
			__mouseMove(null);
		}
	}
	
	private void  __mouseMove (MouseEvent e ){
		cursorHolder.x = cursorHolder.parent.mouseX;
		cursorHolder.y = cursorHolder.parent.mouseY;
		DepthManager.bringToTop(cursorHolder);
	}
	
	/**
	 * Hides the custom cursor which is showing and show the system cursor.
	 * @param cursor the showing cursor, if it is not the showing cursor, nothing 
	 * will happen
	 */
	public void  hideCustomCursor (DisplayObject cursor ){
		if(cursor != currentCursor){
			return;
		}
		if(cursorHolder != null){
			if(currentCursor != null){
				cursorHolder.removeChild(currentCursor);
			}
		}
		currentCursor = null;
		Mouse.show();
		DisplayObjectContainer ro =getCursorContainerRoot ();
		if(ro != null){
			ro.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __mouseMove);
		}
	}
	
	private Dictionary tiggerCursorMap =new Dictionary(true );
	
	/**
	 * Sets the cursor when mouse on the specified trigger. null to remove cursor for that trigger.
	 * @param trigger where the cursor will shown when the mouse on the trigger
	 * @param cursor the cursor object, if cursor is null, the trigger's current cursor will be removed
	 */
	public void  setCursor (InteractiveObject trigger ,DisplayObject cursor ){
		tiggerCursorMap.put(trigger,  cursor);
		if(cursor != null){
			trigger.addEventListener(MouseEvent.ROLL_OVER, __triggerOver, false, 0, true);
			trigger.addEventListener(MouseEvent.ROLL_OUT, __triggerOut, false, 0, true);
			trigger.addEventListener(MouseEvent.MOUSE_UP, __triggerUp, false, 0, true);
		}else{
			trigger.removeEventListener(MouseEvent.ROLL_OVER, __triggerOver, false);
			trigger.removeEventListener(MouseEvent.ROLL_OUT, __triggerOut, false);
			trigger.removeEventListener(MouseEvent.MOUSE_UP, __triggerUp, false);
			delete tiggerCursorMap.get(trigger);
		}
	}
		
	private void  __triggerOver (MouseEvent e ){
		Object trigger =e.currentTarget ;
		DisplayObject cursor =(DisplayObject)tiggerCursorMap.get(trigger);
		if(cursor && !e.buttonDown){
			showCustomCursor(cursor);
		}
	}
	
	private void  __triggerOut (MouseEvent e ){
		Object trigger =e.currentTarget ;
		DisplayObject cursor =(DisplayObject)tiggerCursorMap.get(trigger);
		if(cursor){
			hideCustomCursor(cursor);
		}
	}
	
	private void  __triggerUp (MouseEvent e ){
		InteractiveObject trigger =(InteractiveObject)e.currentTarget;
		DisplayObject cursor =(DisplayObject)tiggerCursorMap.get(trigger);
		if(cursor && trigger.hitTestPoint(e.stageX, e.stageY, true)){
			showCustomCursor(cursor);
		}
	}
}


