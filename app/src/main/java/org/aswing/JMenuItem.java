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


import org.aswing.plaf.*;
import org.aswing.plaf.basic.BasicMenuItemUI;

/**
 * An implementation of an item in a menu. A menu item is essentially a button
 * sitting in a list. When the user selects the "button", the action
 * associated with the menu item is performed. A <code>JMenuItem</code>
 *contained in a <code >JPopupMenu </code >performs exactly that  .
 *
 * @author iiley
 */
public class JMenuItem extends AbstractButton implements MenuElement{

	protected boolean menuInUse ;
	protected KeyType accelerator ;

	public  JMenuItem (String text ="",Icon icon =null ){
		super(text, icon);
		setName("JMenuItem");
		setModel(new DefaultButtonModel());
		initFocusability();
		menuInUse = false;
		accelerator = null;
	}

	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}

	 public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicMenuItemUI;
    }

	/**
	 * Sets the ui.
	 * <p>
	 * The ui should implemented <code>MenuElementUI</code> interface!
	 * </p>
	 * @param newUI the newUI
	 * @throws ArgumentError when the newUI is not an <code>MenuElementUI</code> instance.
	 */
     public void  setUI (ComponentUI newUI ){
    	if(newUI is MenuElementUI){
    		super.setUI(newUI);
    	}else{
    		throw new ArgumentError("JMenuItem just accept MenuElementUI instance!!!");
    	}
    }

    /**
     * Returns the ui for this frame with <code>MenuElementUI</code> instance
     * @return the menu element ui.
     */
    public MenuElementUI  getMenuElementUI (){
    	return getUI() as MenuElementUI;
    }

	 public String  getUIClassID (){
		return "MenuItemUI";
	}

    /**
     * Sets the key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy. It is the
     * UI's responsibility to install the correct action.  Note that
     * when the keyboard accelerator is typed, it will work whether or
     * not the menu is currently displayed.
     *
     * @param keyStroke the <code>KeyType</code> which will
     *		serve as an accelerator
     */
	public void  setAccelerator (KeyType acc ){
		if(accelerator != acc){
			accelerator = acc;
			revalidate();
			repaint();
		}
	}

    /**
     * Returns the <code>KeyType</code> which serves as an accelerator
     * for the menu item.
     * @return a <code>KeyType</code> object identifying the
     *		accelerator key
     */
	public KeyType  getAccelerator (){
		return accelerator;
	}

	/**
	 * Inititalizes the focusability of the the <code>JMenuItem</code>.
	 * <code>JMenuItem</code>'s are focusable, but subclasses may
	 *want to be ,this provides them the opportunity to  this
	 * and invoke something else, or nothing at all.
	 */
	protected void  initFocusability (){
		setFocusable(false);
	}

	/**
	 * Returns the window that owned this menu.
	 * @return window that owned this menu, or null no window owned this menu yet.
	 */
	public JRootPane  getRootPaneOwner (){
		Component pp =this ;
		do{
			pp = pp.getParent();
			if(pp is JPopupMenu){
				pp = JPopupMenu(pp).getInvoker();
			}
			if(pp is JRootPane){
				return JRootPane(pp);
			}
		}while(pp != null);
		return null;
	}

	protected void  inUseChanged (){
		KeyType acc =getAccelerator ();
		if(acc != null){
			JRootPane rOwner =getRootPaneOwner ();
			if(rOwner == null){
				throw new Error("The menu item has accelerator, " +
						"it or it's popupMenu must be in a JRootPane(or it's subclass).");
				return;
			}
			KeyMap keyMap =rOwner.getKeyMap ();
			if(keyMap != null){
				if(isInUse()){
					keyMap.registerKeyAction(acc, __acceleratorAction);
				}else{
					keyMap.unregisterKeyAction(acc);
				}
			}
		}
	}

	protected void  __acceleratorAction (){
		doClick();
	}

	//--------------------------------

    public void  setInUse (boolean b ){
    	if(menuInUse != b){
	    	menuInUse = b;
	    	inUseChanged();
    	}
    }

    public boolean  isInUse (){
    	return menuInUse;
    }

	public void  menuSelectionChanged (boolean isIncluded ){
		getModel().setRollOver(isIncluded);
	}

	public Array  getSubElements (){
		return new Array();
	}

	public Component  getMenuComponent (){
		return this;
	}

	public void  processKeyEvent (int code ){
		getMenuElementUI().processKeyEvent(code);
	}
}


