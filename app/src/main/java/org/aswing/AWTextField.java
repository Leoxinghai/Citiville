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

	
import flash.text.TextField;
import flash.events.Event;
import org.aswing.event.InteractiveEvent;

/**
 * Dispatched when the text changed, programmatic change or user change.
 * 
 * @eventType org.aswing.event.InteractiveEvent.TEXT_CHANGED
 */
.get(Event(name="textChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * Dispatched when the scroll changed, programmatic change or user change, for 
 * example text scrolled by user use mouse wheel or by set the scrollH/scrollV 
 * properties of <code>TextField</code>.
 * 
 * @eventType org.aswing.event.InteractiveEvent.SCROLL_CHANGED
 */
.get(Event(name="scrollChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * TextField with more events support for AsWing text component use.
 */
public class AWTextField extends TextField{
	
	public  AWTextField (){
		super();
		addEventListener(Event.SCROLL, __onAWTextFieldScroll);
		addEventListener(Event.CHANGE, __onAWTextFieldChange);
	}
	
	private void  __onAWTextFieldScroll (Event e ){
		fireScrollChangeEvent(false);
	}
	
	private void  __onAWTextFieldChange (Event e ){
		fireTextChangeEvent(false);
	}
	
	protected void  fireTextChangeEvent (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.TEXT_CHANGED, programmatic));		
	}
	
	protected void  fireScrollChangeEvent (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.SCROLL_CHANGED, programmatic));		
	}
	
	/**
	 * Sets the <code>htmlText</code> and fire <code>InteractiveEvent.TEXT_CHANGED</code> event.
	 */
	 public void  htmlText (String value ){
		super.htmlText = value;
		fireTextChangeEvent();
	}
	
	/**
	 * Sets the <code>text</code> and fire <code>InteractiveEvent.TEXT_CHANGED</code> event.
	 */
	 public void  text (String value ){
		super.text = value;
		fireTextChangeEvent();
	}
	
	/**
	 * Appends new text and fire <code>InteractiveEvent.TEXT_CHANGED</code> event.
	 */
	 public void  appendText (String newText ){
		super.appendText(newText);
		fireTextChangeEvent();
	}
	
	/**
	 * Replace selected text and fire <code>InteractiveEvent.TEXT_CHANGED</code> event.
	 */	
	 public void  replaceSelectedText (String value ){
		super.replaceSelectedText(value);
		fireTextChangeEvent();
	}
	
	/**
	 * Replace text and fire <code>InteractiveEvent.TEXT_CHANGED</code> event.
	 */	
	 public void  replaceText (int beginIndex ,int endIndex ,String newText ){
		super.replaceText(beginIndex, endIndex, newText);
		fireTextChangeEvent();
	}
	
	 public void  scrollH (int value ){
		if(value != scrollH){
			super.scrollH = value;
			fireScrollChangeEvent();
		}
	}
	
	 public void  scrollV (int value ){
		if(value != scrollV){
			super.scrollV = value;
			fireScrollChangeEvent();		
		}
	}

}


