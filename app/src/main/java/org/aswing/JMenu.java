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
import org.aswing.plaf.basic.BasicMenuUI;
import org.aswing.geom.*;
import flash.events.Event;

/**
 * An implementation of a menu -- a popup window containing
 * <code>JMenuItem</code>s that
 * is displayed when the user selects an item on the <code>JMenuBar</code>.
 * In addition to <code>JMenuItem</code>s, a <code>JMenu</code> can
 * also contain <code>JSeparator</code>s.
 * <p>
 * In essence, a menu is a button with an associated <code>JPopupMenu</code>.
 * When the "button" is pressed, the <code>JPopupMenu</code> appears. If the
 * "button" is on the <code>JMenuBar</code>, the menu is a top-level window.
 * If the "button" is another menu item, then the <code>JPopupMenu</code> is
 * "pull-right" menu.
 * </p>
 * @author iiley
 */
public class JMenu extends JMenuItem implements MenuElement{

	/*
	 * The popup menu portion of the menu.
	 */
	protected JPopupMenu popupMenu ;

	protected int delay ;

	public  JMenu (String text ="",Icon icon =null ){
		super(text, icon);
		setName("JMenu");
		delay = 200;
		menuInUse = false;
		addEventListener(Event.REMOVED_FROM_STAGE, __menuDestroied);
	}

	 public void  updateUI (){
		setUI(UIManager.getUI(this));
		if(popupMenu != null){
			popupMenu.updateUI();
		}
	}

	 public String  getUIClassID (){
		return "MenuUI";
	}

	 public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicMenuUI;
    }

    /**
     * Returns true if the menu is a 'top-level menu', that is, if it is
     * the direct child of a menubar.
     *
     * @return true if the menu is activated from the menu bar;
     *         false if the menu is activated from a menu item
     *         on another menu
     */
	public boolean  isTopLevelMenu (){
        if (!(getParent() is JPopupMenu)){
            return true;
        }
        return false;
	}


    /**
     * Returns true if the specified component exists in the
     * submenu hierarchy.
     *
     * @param c the <code>Component</code> to be tested
     * @return true if the <code>Component</code> exists, false otherwise
     */
    public boolean  isMenuComponent (Component c ){
    	if(c == null){
    		return false;
    	}
        if (c == this){
            return true;
        }
        if (c == popupMenu) {
        	return true;
        }
        int ncomponents =getComponentCount ();
        for(int i =0;i <ncomponents ;i ++){
            Component comp =getComponent(i );
            if (comp == c){
                return true;
            }
            // Recursive call for the Menu case
            if (comp is JMenu) {
                JMenu subMenu =JMenu(comp );
                if (subMenu.isMenuComponent(c)){
                    return true;
                }
            }
        }
        return false;
    }

	/**
	 * Returns the popupMenu for the Menu
	 */
	public JPopupMenu  getPopupMenu (){
		ensurePopupMenuCreated();
		return popupMenu;
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
	 * Adds a component(generally JMenuItem or JSeparator) to this menu.
	 */
	public void  append (Component c ){
		getPopupMenu().append(c);
	}

	/**
	 * Inserts a component(generally JMenuItem or JSeparator) to this menu.
	 */
	public void  insert (int i ,Component c ){
		getPopupMenu().insert(i, c);
	}

    /**
     * Returns the number of components on the menu.
     *
     * @return an integer containing the number of components on the menu
     */
    public int  getComponentCount (){
        if (popupMenu != null){
            return popupMenu.getComponentCount();
        }else{
			return 0;
        }
    }

    /**
     * Returns the component at position <code>index</code>.
     *
     * @param n the position of the component to be returned
     * @return the component requested, or <code>null</code>
     *			if there is no popup menu or no component at the position.
     *
     */
    public Component  getComponent (int index ){
        if (popupMenu != null){
            return popupMenu.getComponent(index);
        }else{
			return null;
        }
    }

    /**
	 * Remove the specified component.
	 * @return the component just removed, null if the component is not in this menu.
	 */
    public Component  remove (Component c ){
		if (popupMenu != null){
			return popupMenu.remove(c);
		}
		return null;
    }

	/**
	 * Remove the specified index component.
	 * @param i the index of component.
	 * @return the component just removed. or null there is not component at this position.
	 */
    public Component  removeAt (int i ){
		if (popupMenu != null){
			return popupMenu.removeAt(i);
		}
		return null;
    }

    /**
	 * Remove all components in the menu.
	 */
    public void  removeAll (){
		if (popupMenu != null){
			popupMenu.removeAll();
		}
    }

	/**
	 * Returns the suggested delay, in milliseconds, before submenus
	 * are popped up or down.
	 * Each look and feel (L&F) may determine its own policy for
	 * observing the <code>delay</code> property.
	 * In most cases, the delay is not observed for top level menus
	 * or while dragging.  The default for <code>delay</code> is 0.
	 * This method is a property of the look and feel code and is used
	 * to manage the idiosyncracies of the various UI implementations.
	 *
	 * @return the <code>delay</code> property
	 */
	public int  getDelay (){
		return delay;
	}

	/**
	 * Sets the suggested delay before the menu's <code>PopupMenu</code>
	 * is popped up or down.  Each look and feel (L&F) may determine
	 * it's own policy for observing the delay property.  In most cases,
	 * the delay is not observed for top level menus or while dragging.
	 * This method is a property of the look and feel code and is used
	 * to manage the idiosyncracies of the various UI implementations.
	 *
	 * @param d the number of milliseconds to delay
	 */
	public void  setDelay (int d ){
		if (d < 0){
			trace("/e/Delay must be a positive integer, ignored.");
			return;
		}
		delay = d;
	}

	/**
	 * Returns true if the menu's popup window is visible.
	 *
	 * @return true if the menu is visible, else false
	 */
	public boolean  isPopupMenuVisible (){
		return popupMenu != null && popupMenu.isVisible();
	}

	/**
	 * Sets the visibility of the menu's popup.  If the menu is
	 * not enabled, this method will have no effect.
	 *
	 * @param b  a boolean value -- true to make the menu visible,
	 *		   false to hide it
	 */
	public void  setPopupMenuVisible (boolean b ){
		boolean isVisible =isPopupMenuVisible ();
		if (b != isVisible && (isEnabled() || !b)) {
			ensurePopupMenuCreated();
			if ((b==true) && isShowing()) {
				// Set location of popupMenu (pulldown or pullright)
		 		IntPoint p =getPopupMenuOrigin ();
				getPopupMenu().show(this, p.x, p.y);
			} else {
				getPopupMenu().setVisible(false);
			}
		}
	}
	private void  ensurePopupMenuCreated (){
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.setInvoker(this);
        }
	}

	private IntPoint  getPopupMenuOrigin (){
		IntPoint p ;
		if(getParent() is JPopupMenu){
			p = new IntPoint(getWidth(), 0);
			int ofx =getUIPropertyNumber("Menu.submenuPopupOffsetX");
			int ofy =getUIPropertyNumber("Menu.submenuPopupOffsetY");
			p.x += ofx;
			p.y += ofy;
			if(stage){
				IntRectangle rect =AsWingUtils.getVisibleMaximizedBounds(this );
				IntDimension popupSize =getPopupMenu ().getPreferredSize ();
				if(p.x + popupSize.width > rect.x + rect.width){
					p.x = -ofx - popupSize.width;
				}
				if(p.y + popupSize.height > rect.y + rect.height){
					p.y = -ofy - popupSize.height + getHeight();
				}
			}
		}else{
			p = new IntPoint(0, getHeight());
			p.x += getUIPropertyNumber("Menu.menuPopupOffsetX");
			p.y += getUIPropertyNumber("Menu.menuPopupOffsetY");
		}
		return p;
	}

	private int  getUIPropertyNumber (String name ){
		int n =getUI ().getInt(name );
		return n;
	}

	private void  __menuDestroied (Event e ){
		if(popupMenu != null && popupMenu.isVisible()){
			popupMenu.dispose();
		}
	}

	//--------------------------------

     public void  setInUse (boolean b ){
    	if(menuInUse != b){
	    	menuInUse = b;
	    	if(b){
	    		ensurePopupMenuCreated();
	    	}
	    	Array subs =getSubElements ();
	    	for(int i =0;i <subs.length ;i ++){
	    		MenuElement ele =MenuElement(subs.get(i) );
	    		ele.setInUse(b);
	    	}
	    	inUseChanged();
    	}
    }

	 public void  menuSelectionChanged (boolean isIncluded ){
		setSelected(isIncluded);
	}

	 public Array  getSubElements (){
        if(popupMenu == null){
            return new Array();
        }else{
            return .get(popupMenu);
        }
	}

	 public Component  getMenuComponent (){
		return this;
	}
}


