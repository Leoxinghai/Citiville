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


import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.event.ContainerEvent;
import org.aswing.event.PopupEvent;
import org.aswing.geom.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.BasicPopupMenuUI;
import org.aswing.util.*;

/**
 * An implementation of a popup menu -- a small window that pops up
 * and displays a series of choices. A <code>JPopupMenu</code> is used for the
 * menu that appears when the user selects an item on the menu bar.
 * It is also used for "pull-right" menu that appears when the
 * selects a menu item that activates it. Finally, a <code>JPopupMenu</code>
 * can also be used anywhere else you want a menu to appear.  For
 * example, when the user right-clicks in a specified area.
 * 
 * @author iiley
 */
public class JPopupMenu extends Container implements MenuElement{
		
	private static boolean popupMenuMouseDownListening =false ;
	private static HashSet showingMenuPopups =new HashSet ();
	
	protected SingleSelectionModel selectionModel ;
	
	protected Component invoker ;
	protected JPopup popup ;
	protected boolean menuInUse ;
	
	/**
	 * Create a popup menu
	 * @see org.aswing.JPopup
	 */
	public  JPopupMenu (){
		super();
		setName("JPopupMenu");
		menuInUse = false;
		
		layout = new EmptyLayoutUIResourse();
		setSelectionModel(new DefaultSingleSelectionModel());
		//setFocusTraversalKeysEnabled(false);
		
		popup = new JPopup();
		popup.setLayout(new WindowLayout());
		popup.append(this, WindowLayout.CONTENT);
		popup.addEventListener(PopupEvent.POPUP_OPENED, __popupShown);
		popup.addEventListener(PopupEvent.POPUP_CLOSED, __popupClosed);
		
		popup.addEventListener(ContainerEvent.COM_ADDED, __popMenuChildAdd);
		popup.addEventListener(ContainerEvent.COM_REMOVED, __popMenuChildRemove);
		
		updateUI();
	}


	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
	 public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicPopupMenuUI;
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
    		throw new ArgumentError("JPopupMenu just accept MenuElementUI instance!!!");
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
		return "PopupMenuUI";
	}
	
	/**
	 * Creates a new menu item with the specified text and appends
	 * it to the end of this menu.
	 *  
	 * @param s the string for the menu item to be added
	 */
	public JMenuItem  addMenuItem (String s ){
		JMenuItem mi =new JMenuItem(s );
		append(mi);
		return mi;
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
	
	/**
	 * Sets the visibility of the popup menu.
	 * 
	 * @param b true to make the popup visible, or false to
	 *		  hide it
	 */
	 public void  setVisible (boolean b ){
		if (b == isVisible())
			return;
		//ensure the owner will be applied here
		owner = AsWingUtils.getOwnerAncestor(invoker);
		popup.changeOwner(owner);
		if(b){
			popup.setVisible(true);
			if(isPopupMenu()){
				setInUse(true);
			}
		}else{
			popup.dispose();
			if(isPopupMenu()){
				setInUse(false);
			}
		}
		// if closing, first close all Submenus
		if (b == false) {
			getSelectionModel().clearSelection();
		} else {
			// This is a popup menu with MenuElement children,
			// set selection path before popping up!
			if (isPopupMenu()) {
				MenuSelectionManager.defaultManager().setSelectedPath(stage, [this], true);
			}
		}
		if(b){
			popup.setMnemonicTriggerProxy(stage);
			//if the size is not changed, but children changed, this call make it fresh
			revalidate();
		}else{
			popup.setMnemonicTriggerProxy(null);
		}
	}
	
	 public boolean  isVisible (){
		return popup.isVisible();
	}
	
	/**
	 * Returns the component which is the 'invoker' of this 
	 * popup menu.
	 *
	 * @return the <code>Component</code> in which the popup menu is displayed
	 */
	public Component  getInvoker (){
		return invoker;
	}

	/**
	 * Sets the invoker of this popup menu -- the component in which
	 * the popup menu is to be displayed.
	 *
	 * @param invoker the <code>Component</code> in which the popup
	 *		menu is displayed
	 */
	public void  setInvoker (Component invoker ){
		//Component oldInvoker =this.invoker ;
		this.invoker = invoker;
		popup.changeOwner(AsWingUtils.getOwnerAncestor(invoker));
//		if ((oldInvoker != this.invoker) && (ui != null)) {
//			ui.uninstallUI(this);
//			ui.installUI(this);
//		}
//		invalidate();
	}	
	
	/**
	 * Displays the popup menu at the position x,y in the coordinate
	 * space of the component invoker.
	 *
	 * @param invoker the component in whose space the popup menu is to appear
	 * @param x the x coordinate in invoker's coordinate space at which 
	 * the popup menu is to be displayed
	 * @param y the y coordinate in invoker's coordinate space at which 
	 * the popup menu is to be displayed
	 */
	public void  show (Component invoker ,int x ,int y ){
		setInvoker(invoker);
		if(invoker){
			IntPoint gp =invoker.getGlobalLocation ();
			if(gp == null){
				gp = new IntPoint(x, y);
			}else{
				gp.move(x, y);
			}
		}else{
			gp = new IntPoint(x, y);
		}
		pack();
		setVisible(true);
		//ensure viewing in the eyeable area
		adjustPopupLocationToFitScreen(gp);
		popup.setGlobalLocation(gp);
	}
	
	/**
	 * Causes this Popup Menu to be sized to fit the preferred size.
	 */
	 public void  pack (){
		popup.pack();
	}
	
	/**
	 * Dispose(close) the popup.
	 */
	public void  dispose (){
		popup.dispose();
		if(isPopupMenu()){
			setInUse(false);
		}
	}
	
	/**
	 * Returns the popup menu which is at the root of the menu system
	 * for this popup menu.
	 *
	 * @return the topmost grandparent <code>JPopupMenu</code>
	 */
	public JPopupMenu  getRootPopupMenu (){
		JPopupMenu mp =this ;
		while((mp != null) && 
				(mp.isPopupMenu() != true) &&
				(mp.getInvoker() != null) &&
				(mp.getInvoker().getParent() != null) &&
				(mp.getInvoker().getParent() is JPopupMenu)
			  ) {
			mp = JPopupMenu(mp.getInvoker().getParent());
		}
		return mp;
	}
	
	/**
	 * Examines the list of menu items to determine whether
	 * <code>popupMenu</code> is a popup menu.
	 * 
	 * @param popup  a <code>JPopupMenu</code>
	 * @return true if <code>popupMenu</code>
	 */
	public boolean  isSubPopupMenu (JPopupMenu popupMenu ){
		int ncomponents =getComponentCount ();
		for(int i =0;i <ncomponents ;i ++){
			Component comp =getComponent(i );
			if (comp is JMenu) {
				JMenu menu =JMenu(comp );
				JPopupMenu subPopup =menu.getPopupMenu ();
				if (subPopup == popupMenu){
					return true;
				}
				if (subPopup.isSubPopupMenu(popupMenu)){
					return true;
				}
			}
		}
		return false;
	}	
		
	/**
	 * Returns true if the popup menu is a standalone popup menu
	 * rather than the submenu of a <code>JMenu</code>.
	 *
	 * @return true if this menu is a standalone popup menu, otherwise false
	 */	
	private boolean  isPopupMenu (){
		return (!(invoker is JMenu));
	}
	
	private IntPoint  adjustPopupLocationToFitScreen (IntPoint gp ){
		IntRectangle globalBounds =AsWingUtils.getVisibleMaximizedBounds(popup.parent );
		if(gp.x + popup.getWidth() > globalBounds.x + globalBounds.width){
			gp.x = gp.x - popup.getWidth();
		}
		if(gp.x < globalBounds.x){
			gp.x = globalBounds.x;
		}
		if(gp.y + popup.getHeight() > globalBounds.y + globalBounds.height){
			gp.y = gp.y - popup.getHeight();
		}
		if(gp.y < globalBounds.y){
			gp.y = globalBounds.y;
		}
		return gp;
	}
	
	//---------------------------------------------------------------------
	//--   MenuElement implementation   --
	//---------------------------------------------------------------------
	
	private void  __popMenuChildAdd (ContainerEvent e ){
		Component child =e.getChild ();
		if(child is MenuElement){
			MenuElement(child).setInUse(isInUse());
		}
	}

	private void  __popMenuChildRemove (ContainerEvent e ){
		Component child =e.getChild ();
		if(child is MenuElement){
			MenuElement(child).setInUse(false);
		}
	}
	
	public void  menuSelectionChanged (boolean isIncluded ){
		if(invoker is JMenu) {
			JMenu m =JMenu(invoker );
			if(isIncluded){
				m.setPopupMenuVisible(true);
			}else{
				m.setPopupMenuVisible(false);
			}
		}
		if (isPopupMenu() && !isIncluded){
			setVisible(false);
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
	
	//----------------------
	
	private static void  __popupMenuMouseDown (Event e ){
		boolean hittedPopupMenu =false ;
		Array ps =showingMenuPopups.toArray ();
		boolean hasPopupWindowShown =ps.length >0;
		
		for(int i =0;i <ps.length ;i ++){
			JPopup pp =JPopup(ps.get(i) );
			if(pp.hitTestMouse()){
				hittedPopupMenu = true;
				break;
			}
		}
		if(hasPopupWindowShown && !hittedPopupMenu){
			MenuSelectionManager.defaultManager().clearSelectedPath(false);
		}
	}

	private void  __popupShown (PopupEvent e ){
		source = e.target;
		showingMenuPopups.add(source);
		//to delay to next frame to add the listener to avoid listening in a mouse down event
		AsWingManager.callNextFrame(__addMouseDownListenerToStage);
	}
	
	private void  __addMouseDownListenerToStage (){
		if(showingMenuPopups.size()>0 && !popupMenuMouseDownListening && stage != null){
			stage.addEventListener(MouseEvent.MOUSE_DOWN, __popupMenuMouseDown, false, 0, true);
			popupMenuMouseDownListening = true;
		}
	}

	private void  __popupClosed (PopupEvent e ){
		source = e.target;
		showingMenuPopups.remove(source);
		if(showingMenuPopups.size() == 0 && popupMenuMouseDownListening && stage != null){
			stage.removeEventListener(MouseEvent.MOUSE_DOWN, __popupMenuMouseDown);
			popupMenuMouseDownListening = false;
		}
	}
}


