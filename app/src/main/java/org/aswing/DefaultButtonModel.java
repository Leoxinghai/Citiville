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


import flash.events.EventDispatcher;
import org.aswing.event.*;

/**
 * The default implementation of a <code>Button</code> component's data model.
 */
public class DefaultButtonModel extends EventDispatcher implements ButtonModel{
	
	protected ButtonGroup group ;
	protected boolean enabled ;
	protected boolean rollOver ;
	protected boolean armed ;
	protected boolean pressed ;
	protected boolean selected ;
	
	public  DefaultButtonModel (){
		super();
		enabled = true;
		rollOver = false;
		armed = false;
		pressed = false;
		selected = false;
	}
	
	
	public boolean  isArmed (){
		return armed;
	}
	
	public boolean  isRollOver (){
		return rollOver;
	}
	
	public boolean  isSelected (){
		return selected;
	}
	
	public boolean  isEnabled (){
		return enabled;
	}
	
	public boolean  isPressed (){
		return pressed;
	}
	
	public void  setEnabled (boolean b ){
        if(isEnabled() == b) {
            return;
        }
            
        enabled = b;
        if (!b) {
            pressed = false;
            armed = false;
        }
            
        fireStateChanged();
	}
	
	public void  setPressed (boolean b ){
        if((isPressed() == b) || !isEnabled()) {
            return;
        }
        pressed = b;
        
        if(!isPressed() && isArmed()) {
        	fireActionEvent();
        }
		
        fireStateChanged();
	}
	
	public void  setRollOver (boolean b ){
        if((isRollOver() == b) || !isEnabled()) {
            return;
        }
        rollOver = b;
        fireStateChanged();
	}
	
	public void  setArmed (boolean b ){
        if((isArmed() == b) || !isEnabled()) {
            return;
        }
            
        armed = b;
            
        fireStateChanged();
	}
	
	public void  setSelected (boolean b ){
        if (isSelected() == b) {
            return;
        }

        selected = b;
        
        fireStateChanged();
        fireSelectionChanged();
	}
	
	public void  setGroup (ButtonGroup group ){
		this.group = group;
	}
	
    /**
     * Returns the group that this button belongs to.
     * Normally used with radio buttons, which are mutually
     * exclusive within their group.
     *
     * @return a <code>ButtonGroup</code> that this button belongs to
     */
    public ButtonGroup  getGroup (){
        return group;
    }	
	
	public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(AWEvent.ACT, listener, false, priority);
	}
	
	public void  removeActionListener (Function listener ){
		removeEventListener(AWEvent.ACT, listener);
	}
	
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.SELECTION_CHANGED, listener, false, priority);
	}
	
	public void  removeSelectionListener (Function listener ){
		removeEventListener(InteractiveEvent.SELECTION_CHANGED, listener);
	}
	
	public void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, listener, false, priority);
	}
	
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}
	
	protected void  fireActionEvent (){
		dispatchEvent(new AWEvent(AWEvent.ACT));
	}
	
	protected void  fireStateChanged (){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED));
	}
	
	protected void  fireSelectionChanged (){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.SELECTION_CHANGED));
	}
}


