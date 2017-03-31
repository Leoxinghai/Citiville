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

import org.aswing.plaf.basic.BasicClosableTabbedPaneUI;
/**
 * Dispatched when a tab clos button is clicked. 
 * @eventType org.aswing.event.TabCloseEvent.TAB_CLOSING
 */
.get(Event(name="tabClosing", type="org.aswing.event.TabCloseEvent"))

/**
 * A TabbedPane with each tab a close button, you must listen the TabCloseEvent 
 * and then remove the related tab component if you want. 
 * By default, any thing will happen for close button click.
 * @author iiley
 */
public class JClosableTabbedPane extends JTabbedPane{
	
    protected Array closeEnables ;
    
	public  JClosableTabbedPane (){
		super();
		closeEnables = new Array();
		setName("JClosableTabbedPane");
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicClosableTabbedPaneUI;
    }
	
	 public String  getUIClassID (){
		return "ClosableTabbedPaneUI";
	}
	
	/**
	 * Sets whether or not the tab close button at index is enabled. 
	 * Nothing will happen if there is no tab at that index.
	 * @param index the tab index which should be enabled/disabled
	 * @param enabled whether or not the tab close button should be enabled 
	 */
	public void  setCloseEnabledAt (int index ,boolean enabled ){
		if(closeEnables.get(index) != enabled){
			closeEnables.put(index,  enabled);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Returns whether or not the tab close button at index is currently enabled. 
	 * false will be returned if there is no tab at that index.
	 * @param index  the index of the item being queried 
	 * @return if the tab close button at index is enabled; false otherwise.
	 */
	public boolean  isCloseEnabledAt (int index ){
		return closeEnables.get(index) == true;
	}
	
	 protected void  insertProperties (int i ,String title ="",Icon icon =null ,String tip =null ){
		super.insertProperties(i, title, icon, tip);
		insertToArray(closeEnables, i, true);
	}
	
	 protected void  removeProperties (int i ){
		super.removeProperties(i);
		removeFromArray(closeEnables, i);
	}
}


