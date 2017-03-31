/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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
import org.aswing.*;
import org.aswing.event.*;
import flash.utils.Timer;
import flash.events.*;

/**
 * @private
 * @author iiley
 */
public class BasicMenuUI extends BasicMenuItemUI{
	
	protected Timer postTimer ;
	
	public  BasicMenuUI (){
		super();
	}

	 protected String  getPropertyPrefix (){
		return "Menu.";
	}

	 protected void  installDefaults (){
		super.installDefaults();
		updateDefaultBackgroundColor();
	}	
	
	 protected void  uninstallDefaults (){
		menuItem.getModel().setRollOver(false);
		menuItem.setSelected(false);
		super.uninstallDefaults();
	}

	 protected void  installListeners (){
		super.installListeners();
		menuItem.addSelectionListener(__menuSelectionChanged);
	}
	
	 protected void  uninstallListeners (){
		super.uninstallListeners();
		menuItem.removeSelectionListener(__menuSelectionChanged);
	}		
	
	protected JMenu  getMenu (){
		return JMenu(menuItem);
	}
	
	/*
	 * Set the background color depending on whether this is a toplevel menu
	 * in a menubar or a submenu of another menu.
	 */
	protected void  updateDefaultBackgroundColor (){
		if (!getBoolean("Menu.useMenuBarBackgroundForTopLevel")) {
			return;
		}
		JMenu menu =getMenu ();
		if (menu.getBackground() is UIResource) {
			if (menu.isTopLevelMenu()) {
				menu.setBackground(getColor("MenuBar.background"));
			} else {
				menu.setBackground(getColor(getPropertyPrefix() + ".background"));
			}
		}
	}
	
	/**
	 *SubUI  this to do different 
	 */
	 protected boolean  isMenu (){
		return true;
	}
	
	/**
	 *SubUI  this to do different 
	 */
	 protected boolean  isTopMenu (){
		return getMenu().isTopLevelMenu();
	}
	
	/**
	 *SubUI  this to do different 
	 */
	 protected boolean  shouldPaintSelected (){
		return menuItem.getModel().isRollOver() || menuItem.isSelected();
	}
	
	//---------------------
	
	 public void  processKeyEvent (int code ){
		MenuSelectionManager manager =MenuSelectionManager.defaultManager ();
		if(manager.isNextPageKey(code)){
			Array path =manager.getSelectedPath ();
			if(path.get(path.length-1) == menuItem){
				MenuElement popElement =getMenu ().getPopupMenu ();
				path.push(popElement);
				if(popElement.getSubElements().length > 0){
					path.push(popElement.getSubElements().get(0));
				}
				manager.setSelectedPath(menuItem.stage, path, false);
			}
		}else{
			super.processKeyEvent(code);
		}
	}	
	
	protected void  __menuSelectionChanged (InteractiveEvent e ){
		menuItem.repaint();
	}
	
	 protected void  __menuItemRollOver (MouseEvent e ){
		JMenu menu =getMenu ();
		MenuSelectionManager manager =MenuSelectionManager.defaultManager ();
		Array selectedPath =manager.getSelectedPath ();		
		if (!menu.isTopLevelMenu()) {
			if(!(selectedPath.length>0 && selectedPath.put(selectedPath.length-1, menu.getPopupMenu())));
				if(menu.getDelay() <= 0) {
					appendPath(getPath(), menu.getPopupMenu());
				} else {
					manager.setSelectedPath(menuItem.stage, getPath(), false);
					setupPostTimer(menu);
				}
			}
		} else {
			if(selectedPath.length > 0 && selectedPath.get(0) == menu.getParent()) {
				// A top level menu's parent is by definition a JMenuBar
				manager.setSelectedPath(menuItem.stage, [menu.getParent(), menu, menu.getPopupMenu()], false);
			}
		}
		menuItem.repaint();
	}
	
	 protected void  __menuItemAct (AWEvent e ){
		JMenu menu =getMenu ();
		Container cnt =menu.getParent ();
		if(cnt != null && cnt is JMenuBar) {
			Array me =.get(cnt ,menu ,menu.getPopupMenu ()) ;
			MenuSelectionManager.defaultManager().setSelectedPath(menuItem.stage, me, false);
		}
		menuItem.repaint();
	}
	
	protected void  __postTimerAct (Event e ){
		JMenu menu =getMenu ();
		Array path =MenuSelectionManager.defaultManager ().getSelectedPath ();
		if(path.length > 0 && path.get(path.length-1) == menu) {
			appendPath(path, menu.getPopupMenu());
		}
	}
	
	//---------------------
	protected void  appendPath (Array path ,Object end ){
		path.push(end);
		MenuSelectionManager.defaultManager().setSelectedPath(menuItem.stage, path, false);
	}

	protected void  setupPostTimer (JMenu menu ){
		if(postTimer == null){
			postTimer = new Timer(menu.getDelay(), 1);
			postTimer.addEventListener(TimerEvent.TIMER, __postTimerAct);
		}
		postTimer.reset();
		postTimer.start();
	}	
}


