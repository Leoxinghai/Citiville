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


import org.aswing.event.ListDataListener;

/**
 * ListMode is a MVC pattern's mode for List UI, different List UI can connected to 
 * a same mode to view the mode's data. When the mode's data changed the mode should
 * fire a event to all its ListDataListeners.
 * @author iiley
 */
public interface ListModel{
	/**
	 * Adds a listener to the list that's notified each time a change to the data model occurs. 
	 */
 	void  addListDataListener (ListDataListener l );
    /**
     * Returns the value at the specified index. 
     */
 	 getElementAt (int index )*;
 	
 	/**
 	 * Returns the length of the list.
 	 */
 	int  getSize ();
    
    /**
     * Removes a listener from the list that's notified each time a change to the data model occurs. 
     */
 	void  removeListDataListener (ListDataListener l );
}


