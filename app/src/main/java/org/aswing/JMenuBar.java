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
import org.aswing.plaf.basic.BasicMenuBarUI;
import flash.events.Event;
import org.aswing.event.ContainerEvent;

/**
 * An implementation of a menu bar. You add <code>JMenu</code> objects to the
 * menu bar to construct a menu. When the user selects a <code>JMenu</code>
 * object, its associated <code>JPopupMenu</code> is displayed, allowing the
 * user to select one of the <code>JMenuItems</code> on it.
 * @author iiley
 */
public class JMenuBar extends Container implements MenuElement{
	
	private SingleSelectionModel selectionModel ;
	private boolean menuInUse ;
	
	public  JMenuBar (){
		super();
		setSelectionModel(new DefaultSingleSelectionModel());
		layout = new EmptyLayoutUIResourse();
		menuInUse = false;
		
		addEventListener(Event.REMOVED_FROM_STAGE, __menuBarDestroied);
		addEventListener(Event.ADDED_TO_STAGE, __menuBarCreated);
		addEventListener(ContainerEvent.COM_ADDED, __menuBarChildAdd);
		addEventListener(ContainerEvent.COM_REMOVED, __menuBarChildRemove);
		
		updateUI();
	}

	 public void  updateUI (){
		setUI(UIManager.getUI(this));
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
    		throw new ArgumentError("JMenuBar just accept MenuElementUI instance!!!");
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
		return "MenuBarUI";
	}
	
	 public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicMenuBarUI;
    }
	
	/**
	 * Adds a menu to the menu bar.
	 * @param menu the menu to be added
	 * @return the menu be added
	 */
	public JMenu  addMenu (JMenu menu ){
		append(menu);
		return menu;
	}
	
	/**
	 * Returns the menu component at index, if it is not a menu component at that index, null will be returned.
	 * @return a menu instance or null
	 */
	public JMenu  getMenu (int index ){
		Component com =getComponent(index );
		if(com is JMenu){
			return JMenu(com);
		}else{
			return null;
		}
	}
	
	/**
	 * Returns the model object that handles single selections.
	 *
	 * @return the <code>SingleSelectionModel</code> property
	 * @see SingleSelectionModel
	 */
	public SingleSelectionModel  getSelectionModel (){
		return selectionModel;
	}

	/**
	 * Sets the model object to handle single selections.
	 *
	 * @param model the <code>SingleSelectionModel</code> to use
	 * @see SingleSelectionModel
	 */
	public void  setSelectionModel (SingleSelectionModel model ){
		selectionModel = model;
	}	

	/**
	 * Sets the currently selected component, producing a
	 * a change to the selection model.
	 *
	 * @param sel the <code>Component</code> to select
	 */
	public void  setSelected (Component sel ){	
		SingleSelectionModel model =getSelectionModel ();
		int index =getIndex(sel );
		model.setSelectedIndex(index);
	}

	/**
	 * Returns true if the menu bar currently has a component selected.
	 *
	 * @return true if a selection has been made, else false
	 */
	public boolean  isSelected (){	   
		return selectionModel.isSelected();
	}	
		
	//--------------------------------------------------------------
	//					MenuElement imp
	//--------------------------------------------------------------
		
	public void  menuSelectionChanged (boolean isIncluded ){
	}

	private void  __menuBarDestroied (Event e ){
		setInUse(false);
	}
	
	private void  __menuBarCreated (Event e ){
		setInUse(true);
	}
	
	private void  __menuBarChildAdd (ContainerEvent e ){
		if(e.getChild() is MenuElement){
			MenuElement(e.getChild()).setInUse(isInUse());
		}
	}

	private void  __menuBarChildRemove (ContainerEvent e ){
		if(e.getChild() is MenuElement){
			MenuElement(e.getChild()).setInUse(false);
		}
	}
	
	public Array  getSubElements (){
		Array arr =new Array ();
		for(int i =0;i <getComponentCount ();i ++){
			Component com =getComponent(i );
			if(com is MenuElement){
				arr.push(com);
			}
		}
		return arr;
	}
		
	public Component  getMenuComponent (){
		return this;
	}
	
	public void  processKeyEvent (int code ){
		getMenuElementUI().processKeyEvent(code);
	}
	
    public void  setInUse (boolean b ){
    	if(menuInUse != b){
	    	menuInUse = b;
	    	Array subs =getSubElements ();
	    	for(int i =0;i <subs.length ;i ++){
	    		MenuElement ele =MenuElement(subs.get(i) );
	    		ele.setInUse(b);
	    	}
    	}
    }
    
    public boolean  isInUse (){
    	return menuInUse;
    }	
}


