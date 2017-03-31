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
import org.aswing.event.ContainerEvent;
import org.aswing.event.AWEvent;
import org.aswing.event.FocusKeyEvent;
import org.aswing.event.InteractiveEvent;

/**
 * @private
 */
public class BasicMenuBarUI extends BaseComponentUI implements MenuElementUI{
	
	protected JMenuBar menuBar ;
	
	public  BasicMenuBarUI (){
		super();
	}

	 public void  installUI (Component c ){
		menuBar = JMenuBar(c);
		installDefaults();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		menuBar = JMenuBar(c);
		uninstallDefaults();
		uninstallListeners();
	}
	
	protected String  getPropertyPrefix (){
		return "MenuBar.";
	}

	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(menuBar, pp);
		LookAndFeel.installBorderAndBFDecorators(menuBar, pp);
		LookAndFeel.installBasicProperties(menuBar, pp);
		LayoutManager layout =menuBar.getLayout ();
		if(layout == null || layout is UIResource){
			menuBar.setLayout(new DefaultMenuLayout(DefaultMenuLayout.X_AXIS));
		}
	}
	
	protected void  installListeners (){
		for(int i =0;i <menuBar.getComponentCount ();i ++){
			JMenu menu =menuBar.getMenu(i );
			if(menu != null){
				menu.addSelectionListener(__menuSelectionChanged);
			}
		}
		
		menuBar.addEventListener(ContainerEvent.COM_ADDED, __childAdded);
		menuBar.addEventListener(ContainerEvent.COM_REMOVED, __childRemoved);
		menuBar.addEventListener(AWEvent.FOCUS_GAINED, __barFocusGained);
		menuBar.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __barKeyDown);
	}

	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(menuBar);
	}
	
	protected void  uninstallListeners (){
		for(int i =0;i <menuBar.getComponentCount ();i ++){
			JMenu menu =menuBar.getMenu(i );
			if(menu != null){
				menu.removeSelectionListener(__menuSelectionChanged);
			}
		}
		
		menuBar.removeEventListener(ContainerEvent.COM_ADDED, __childAdded);
		menuBar.removeEventListener(ContainerEvent.COM_REMOVED, __childRemoved);
		menuBar.removeEventListener(AWEvent.FOCUS_GAINED, __barFocusGained);
		menuBar.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __barKeyDown);
	}
	
	//-----------------

	/**
	 *Subclass  this to process key event .
	 */
	public void  processKeyEvent (int code ){
		MenuSelectionManager manager =MenuSelectionManager.defaultManager ();
		if(manager.isNavigatingKey(code)){
			Array subs =menuBar.getSubElements ();
			Array path =.get(menuBar) ;
			if(subs.length > 0){
				if(manager.isNextItemKey(code) || manager.isNextPageKey(code)){
					path.push(subs.get(0));
				}else{//left
					path.push(subs.get(subs.length-1));
				}
				MenuElement smu =MenuElement(path.get(1) );
				if(smu.getSubElements().length > 0){
					path.push(smu.getSubElements().get(0));
				}
				manager.setSelectedPath(menuBar.stage, path, false);
			}
		}
	}
	
	protected void  __barKeyDown (FocusKeyEvent e ){
		if(MenuSelectionManager.defaultManager().getSelectedPath().length == 0){
			processKeyEvent(e.keyCode);
		}
	}
	
	protected void  __menuSelectionChanged (InteractiveEvent e ){
		for(int i =0;i <menuBar.getComponentCount ();i ++){
			JMenu menu =menuBar.getMenu(i );
			if(menu != null && menu.isSelected()){
				menuBar.getSelectionModel().setSelectedIndex(i, e.isProgrammatic());
				break;
			}
		}
	}
	
	protected void  __barFocusGained (AWEvent e ){
		MenuSelectionManager.defaultManager().setSelectedPath(menuBar.stage, [menuBar], false);
	}
	
	protected void  __childAdded (ContainerEvent e ){
		if(e.getChild() is JMenu){
			JMenu(e.getChild()).addSelectionListener(__menuSelectionChanged);
		}
	}
	
	protected void  __childRemoved (ContainerEvent e ){
		if(e.getChild() is JMenu){
			JMenu(e.getChild()).removeSelectionListener(__menuSelectionChanged);
		}
	}
}


