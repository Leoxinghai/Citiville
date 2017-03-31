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


import flash.display.InteractiveObject;
import flash.events.EventDispatcher;
import flash.events.KeyboardEvent;
import flash.ui.Keyboard;

import org.aswing.event.InteractiveEvent;
import org.aswing.util.ArrayUtils;
import org.aswing.util.ArrayList;
import org.aswing.util.WeakReference;

/**
 * Dispatched when the menu selection changed.
 *
 * @eventType org.aswing.event.InteractiveEvent.SELECTION_CHANGED
 */
.get(Event(name="selectionChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A MenuSelectionManager owns the selection in menu hierarchy.
 *
 * @author iiley
 */
public class MenuSelectionManager extends EventDispatcher{

	private static MenuSelectionManager instance ;

	protected ArrayList selection ;

	public  MenuSelectionManager (){
		selection = new ArrayList();
	}

	public static MenuSelectionManager  defaultManager (){
		if(instance == null){
			instance = new MenuSelectionManager();
		}
		return instance;
	}

	/**
	 * Replaces the default manager by yours.
	 */
	public static void  setDefaultManager (MenuSelectionManager m ){
		instance = m;
	}

	protected WeakReference lastTriggerRef =new WeakReference ();
    /**
     * Changes the selection in the menu hierarchy.  The elements
     * in the array are sorted in order from the root menu
     * element to the currently selected menu element.
     * <p>
     * Note that this method is public but is used by the look and
     * feel engine and should not be called by client applications.
     * </p>
     * @param path  an array of <code>MenuElement</code> objects specifying
     *        the selected path.
     * @param programmatic indicate if this is a programmatic change.
     */
    public MenuElement[] void  setSelectedPath (InteractiveObject trigger ,Array path ,boolean programmatic ){//
        int i ;
        int c ;
        double currentSelectionCount =selection.size ();
        int firstDifference =0;

        if(path == null) {
            path = new Array();
        }

        for(i=0,c=path.length; i<c; i++) {
            if(i < currentSelectionCount && selection.get(i) == path.get(i)){
                firstDifference++;
            }else{
                break;
            }
        }

        for(i=currentSelectionCount-1 ; i>=firstDifference; i--) {
            MenuElement me =MenuElement(selection.get(i ));
            selection.removeAt(i);
            me.menuSelectionChanged(false);
        }

        for(i = firstDifference, c = path.length ; i < c ; i++) {
        	MenuElement tm =MenuElement(path.get(i) );
		    if (tm != null) {
				selection.append(tm);
				tm.menuSelectionChanged(true);
		    }
		}
		if(firstDifference < path.length - 1 || currentSelectionCount != path.length()){
			fireSelectionChanged(programmatic);
		}
		InteractiveObject lastTrigger =lastTriggerRef.value ;
		if(selection.size() == 0){
			if(lastTrigger){
				lastTrigger.removeEventListener(KeyboardEvent.KEY_DOWN, __onMSMKeyDown);
				lastTriggerRef.clear();
			}
		}else{
			if(lastTrigger != trigger){
				if(lastTrigger){
					lastTrigger.removeEventListener(KeyboardEvent.KEY_DOWN, __onMSMKeyDown);
				}
				lastTrigger = trigger;
				if(trigger){
					trigger.addEventListener(KeyboardEvent.KEY_DOWN, __onMSMKeyDown, false, 0, true);
				}
				lastTriggerRef.value = trigger;
			}
		}
    }

	/**
	 * Adds a listener to listen the menu seletion change event.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.SELECTION_CHANGED, listener, false, priority);
	}

	/**
	 * Removes a menu seletion change listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	public void  removeSelectionListener (Function listener ){
		removeEventListener(InteractiveEvent.SELECTION_CHANGED, listener);
	}

    /**
     * Returns the path to the currently selected menu item
     *
     * @return an array of MenuElement objects representing the selected path
     */
    public MenuElement[] Array  getSelectedPath (){//
        return selection.toArray();
    }

    /**
     * Tell the menu selection to close and unselect all the menu components. Call this method
     * when a choice has been made.
     * @param programmatic indicate if this is a programmatic change.
     */
    public void  clearSelectedPath (boolean programmatic ){
        if (selection.size() > 0) {
            setSelectedPath(null, null, true);
        }
    }

    /**
     * Return true if c is part of the currently used menu
     */
    public boolean  isComponentPartOfCurrentMenu (Component c ){
        if(selection.size() > 0) {
            MenuElement me =MenuElement(selection.get(0));
            return isComponentPartOfMenu(me, c);
        }else{
            return false;
        }
    }

    public boolean  isNavigatingKey (int code ){
    	return isPageNavKey(code) || isItemNavKey(code);
    }
    public boolean  isPageNavKey (int code ){
    	return isPrevPageKey(code) || isNextPageKey(code);
    }
    public boolean  isItemNavKey (int code ){
    	return isPrevItemKey(code) || isNextItemKey(code);
    }
    public boolean  isPrevPageKey (int code ){
    	return code == Keyboard.LEFT;
    }
    public boolean  isPrevItemKey (int code ){
    	return code == Keyboard.UP;
    }
    public boolean  isNextPageKey (int code ){
    	return code == Keyboard.RIGHT;
    }
    public boolean  isNextItemKey (int code ){
    	return code == Keyboard.DOWN;
    }
    public boolean  isEnterKey (int code ){
    	return code == Keyboard.ENTER;
    }
    public boolean  isEscKey (int code ){
    	return code == Keyboard.TAB || code == Keyboard.ESCAPE;
    }

    public MenuElement  nextSubElement (MenuElement parent ,MenuElement sub ){
    	return besideSubElement(parent, sub, 1);
    }

    public MenuElement  prevSubElement (MenuElement parent ,MenuElement sub ){
    	return besideSubElement(parent, sub, -1);
    }

    protected MenuElement  besideSubElement (MenuElement parent ,MenuElement sub ,double dir ){
    	if(parent == null || sub == null){
    		return null;
    	}
    	Array subs =parent.getSubElements ();
    	int index =ArrayUtils.indexInArray(subs ,sub );
    	if(index < 0){
    		return null;
    	}
    	index += dir;
    	if(index >= subs.length()){
    		index = 0;
    	}else if(index < 0){
    		index = subs.length - 1;
    	}
    	return MenuElement(subs.get(index));
    }

    protected boolean  isComponentPartOfMenu (MenuElement root ,Component c ){
        Array children ;
        double i ;
        double d ;

		if (root == null){
		    return false;
		}

        if(root.getMenuComponent() == c){
            return true;
        }else {
            children = root.getSubElements();
            for(i=0,d=children.length; i<d; i++) {
            	MenuElement me =MenuElement(children.get(i) );
                if(me != null && isComponentPartOfMenu(me, c)){
                    return true;
                }
            }
        }
        return false;
	}

	protected void  fireSelectionChanged (boolean programmatic ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.SELECTION_CHANGED, programmatic));
	}

	protected void  __onMSMKeyDown (KeyboardEvent e ){
		if(selection.size() == 0){
			return;
		}
		int code =e.keyCode ;
		if(isEscKey(code)){
			setSelectedPath(null, null, true);
			return;
		}
		MenuElement element =MenuElement(selection.last ());
		element.processKeyEvent(code);
	}

}


