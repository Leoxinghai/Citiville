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


import flash.events.IEventDispatcher;

/**
 * A model that supports at most one indexed selection.
 * @author iiley
 */
public interface SingleSelectionModel
	
    /**
     * Returns the model's selection.
     *
     * @return  the model's selection, or -1 if there is no selection
     * @see     #setSelectedIndex()
     */
    int  getSelectedIndex ();

    /**
     * Sets the model's selected index to <I>index</I>.
     *
     * Notifies any listeners if the model changes.
     *
     * @param index an int specifying the model selection.
     * @param programmatic indicate if this is a programmatic change.
     * @see   #getSelectedIndex()
     * @see   #addChangeListener()
     */
    void  setSelectedIndex (int index ,boolean programmatic =true );

    /**
     * Clears the selection (to -1).
     * @param programmatic indicate if this is a programmatic change.
     */
    void  clearSelection (boolean programmatic =true );

    /**
     * Returns true if the selection model currently has a selected value.
     * @return true if a value is currently selected
     */
    boolean  isSelected ();

	/**
	 * Adds a listener to listen the Model's state change event.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false )
	
	/**
	 * Removes a state listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */	
	 void  removeStateListener (Function listener )
}


