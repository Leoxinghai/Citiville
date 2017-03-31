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

import org.aswing.event.FocusKeyEvent;
import flash.events.MouseEvent;
import flash.events.Event;
import org.aswing.plaf.basic.tabbedpane.ClosableTab;
import flash.display.DisplayObject;
import flash.display.InteractiveObject;
import flash.display.DisplayObjectContainer;
import org.aswing.plaf.basic.tabbedpane.BasicClosableTabbedPaneTab;
import org.aswing.event.TabCloseEvent;
import org.aswing.plaf.basic.tabbedpane.Tab;
import org.aswing.JClosableTabbedPane;
import org.aswing.Component;

/**
 * Basic imp for JClosableTabbedPane UI.
 * @author iiley
 */
public class BasicClosableTabbedPaneUI extends BasicTabbedPaneUI{
	
	public  BasicClosableTabbedPaneUI (){
		super();
	}	
	
     protected String  getPropertyPrefix (){
        return "ClosableTabbedPane.";
    }
	
	protected ClosableTab  getClosableTab (int i ){
    	return ClosableTab(tabs.get(i));
	}
	
     protected void  setTabProperties (Tab header ,int i ){
    	super.setTabProperties(header, i);
    	ClosableTab(header).getCloseButton().setEnabled(
    		JClosableTabbedPane(tabbedPane).isCloseEnabledAt(i)
    		&& tabbedPane.isEnabledAt(i));
    }
	
	 protected void  installListeners (){
		super.installListeners();
		tabbedPane.addEventListener(MouseEvent.CLICK, __onTabPaneClicked);
	}
	
	 protected void  uninstallListeners (){
		super.uninstallListeners();
		tabbedPane.removeEventListener(MouseEvent.CLICK, __onTabPaneClicked);
	}
	
	 protected void  __onTabPanePressed (Event e ){
		if((prevButton.hitTestMouse() || nextButton.hitTestMouse())
			&& (prevButton.isShowing() && nextButton.isShowing())){
			return;
		}
		int index =getMousedOnTabIndex ();
		if(index >= 0 && tabbedPane.isEnabledAt(index) && !isButtonEvent(e, index)){
			tabbedPane.setSelectedIndex(index);
		}
	}
	
    /**
     *Just  this method if you want other LAF headers .
     */
     protected Tab  createNewTab (){
    	Tab tab =getInstance(getPropertyPrefix ()+"tab")as Tab ;
    	if(tab == null){
    		tab = new BasicClosableTabbedPaneTab();
    	}
    	tab.initTab(tabbedPane);
    	tab.getTabComponent().setFocusable(false);
    	return tab;
    }
    
	protected boolean  isButtonEvent (Event e ,int index ){
		DisplayObject eventTarget =(DisplayObject)e.target;
		if(eventTarget){
			Component button =getClosableTab(index ).getCloseButton ();
			if(button == eventTarget || button.contains(eventTarget)){
				return true;
			}
		}
		return false;
	}
	
	protected void  __onTabPaneClicked (Event e ){
		int index =getMousedOnTabIndex ();
		if(index >= 0 && tabbedPane.isEnabledAt(index) && isButtonEvent(e, index)){
			tabbedPane.dispatchEvent(new TabCloseEvent(index));
		}
	}	
}


