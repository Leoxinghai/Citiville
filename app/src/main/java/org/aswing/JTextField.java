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


import org.aswing.geom.*;
import org.aswing.event.FocusKeyEvent;
import flash.ui.Keyboard;
import org.aswing.event.AWEvent;
import org.aswing.plaf.basic.BasicTextFieldUI;

/**
 * Dispatched when the user input ENTER in the textfield.
 * @eventType org.aswing.event.AWEvent.ACT
 * @see org.aswing.JTextField#addActionListener()
 */
.get(Event(name="act", type="org.aswing.event.AWEvent"))

/**
 * JTextField is a component that allows the editing of a single line of text. 
 * @author Tomato, iiley
 */
public class JTextField extends JTextComponent{
	
	private static int defaultMaxChars =0;
	
	private int columns ;
	
	public  JTextField (String text ="",int columns =0){
		super();
		setName("JTextField");
		getTextField().multiline = false;
		getTextField().text = text;
		setMaxChars(defaultMaxChars);
		this.columns = columns;
		addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onFocusKeyDown);
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicTextFieldUI;
    }
	
	 public String  getUIClassID (){
		return "TextFieldUI";
	}
	
	/**
	 * Sets the maxChars property for default value when <code>JTextFeild</code> be created.
	 * By default it is 0, you can change it by this method.
	 * @param n the default maxChars to set
	 */
	public static void  setDefaultMaxChars (int n ){
		defaultMaxChars = n;
	}
	
	/**
	 * Returns the maxChars property for default value when <code>JTextFeild</code> be created.
	 * @return the default maxChars value.
	 */
	public static int  getDefaultMaxChars (){
		return defaultMaxChars;
	}	
	
	/**
	 * Sets the number of columns in this JTextField, if it changed then call parent to do layout. 
	 * @param columns the number of columns to use to calculate the preferred width;
	 * if columns is set to zero or min than zero, the preferred width will be matched just to view all of the text.
	 * default value is zero if missed this param.
	 */
	public void  setColumns (int columns =0){
		if(columns < 0) columns = 0;
		if(this.columns != columns){
			this.columns = columns;
			revalidate();
		}
	}
	
	/**
	 * @see #setColumns
	 */
	public double  getColumns (){
		return columns;
	}	
	
    /**
     * Adds a action listener to this text field. JTextField fire a action event when 
     * user press Enter Key when input to text field.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
     */
    public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(AWEvent.ACT, listener, false, priority, useWeakReference);
    }
    
	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	public void  removeActionListener (Function listener ){
		removeEventListener(AWEvent.ACT, listener);
	}   	
	
	 protected boolean  isAutoSize (){
		return columns == 0;
	}
	
	/**
	 * JTextComponent need count preferred size itself.
	 */
	 protected IntDimension  countPreferredSize (){
		if(columns > 0){
			int columnWidth =getColumnWidth ();
			int width =columnWidth *columns +getWidthMargin ();
			int height =getRowHeight ()+getHeightMargin ();
			IntDimension size =new IntDimension(width ,height );
			return getInsets().getOutsideSize(size);
		}else{
			return getInsets().getOutsideSize(getTextFieldAutoSizedSize());
		}
	}
	
	//-------------------------------------------------------------------------
	
	private void  __onFocusKeyDown (FocusKeyEvent e ){
		if(e.keyCode == Keyboard.ENTER){
			dispatchEvent(new AWEvent(AWEvent.ACT));
		}
	}
}


