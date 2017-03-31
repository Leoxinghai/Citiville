
package org.aswing.event;

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

/**
 * The event for list and table selection change.
 * @see org.aswing.JList
 * @see org.aswing.JTable
 * @see org.aswing.ListSelectionModel
 * @author iiley
 */
public class SelectionEvent extends InteractiveEvent{
		
        public static  String LIST_SELECTION_CHANGED ="listSelectionChanged";
        public static  String LIST_SELECTION_CHANGING ="listSelectionChanging";
        public static  String ROW_SELECTION_CHANGED ="rowSelectionChanged";
        public static  String COLUMN_SELECTION_CHANGED ="columnSelectionChanged";

			
	private int firstIndex ;
	private int lastIndex ;
	
	public  SelectionEvent (String type ,int firstIndex ,int lastIndex ,boolean programmatic ){
		super(type, programmatic);
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}
	
	/**
	 * Returns the first changed index(the begin).
	 * @returns the first changed index.
	 */
	public int  getFirstIndex (){
		return firstIndex;
	}
	
	/**
	 * Returns the last changed index(the end).
	 * @returns the last changed index.
	 */
	public int  getLastIndex (){
		return lastIndex;
	}
	
	 public Event  clone (){
		return new SelectionEvent(type, firstIndex, lastIndex, isProgrammatic());
	}
}


