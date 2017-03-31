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


/**
 * This interface represents the current state of the 
 * selection for any of the components that display a 
 * list of values with stable indices.  The selection is 
 * modeled as a set of intervals, each interval represents
 * a contiguous range of selected list elements.
 * The methods for modifying the set of selected intervals
 * all take a pair of indices, index0 and index1, that represent
 * a closed interval, i.e. the interval includes both index0 and
 * index1.
 * 
 * @see org.aswing.DefaultListSelectionModel
 * @author iiley
 */
public interface ListSelectionModel{

	/** 
     * Change the selection to be between index0 and index1 inclusive.
     * If this represents a change to the current selection, then
     * notify each ListSelectionListener. Note that index0 doesn't have
     * to be less than or equal to index1.  
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval.
     * @param programmatic indicate if this is a programmatic change.
     * @see #addListSelectionListener()
     */	
	void  setSelectionInterval (int index0 ,int index1 ,boolean programmatic =true );
	
    /** 
     * Change the selection to be the set union of the current selection
     * and the indices between index0 and index1 inclusive.  If this represents 
     * a change to the current selection, then notify each 
     * ListSelectionListener. Note that index0 doesn't have to be less
     * than or equal to index1.  
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval.
     * @param programmatic indicate if this is a programmatic change.
     * @see #addListSelectionListener()
     */	
	void  addSelectionInterval (int index0 ,int index1 ,boolean programmatic =true );

    /** 
     * Change the selection to be the set difference of the current selection
     * and the indices between index0 and index1 inclusive.  If this represents 
     * a change to the current selection, then notify each 
     * ListSelectionListener.  Note that index0 doesn't have to be less
     * than or equal to index1.  
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval.
     * @param programmatic indicate if this is a programmatic change.
     * @see #addListSelectionListener()
     */	
	void  removeSelectionInterval (int index0 ,int index1 ,boolean programmatic =true );

    /**
     * Returns the first selected index or -1 if the selection is empty.
     */	
	int  getMinSelectionIndex ();

    /**
     * Returns the last selected index or -1 if the selection is empty.
     */	
	int  getMaxSelectionIndex ();

    /** 
     * Returns true if the specified index is selected.
     */	
	boolean  isSelectedIndex (int index );

    /**
     * Return the first index argument from the most recent call to 
     * setSelectionInterval(), addSelectionInterval() or removeSelectionInterval().
     * The most recent index0 is considered the "anchor" and the most recent
     * index1 is considered the "lead".  Some interfaces display these
     * indices specially, e.g. Windows95 displays the lead index with a 
     * dotted yellow outline.
     * 
     * @see #getLeadSelectionIndex()
     * @see #setSelectionInterval()
     * @see #addSelectionInterval()
     */	
	int  getAnchorSelectionIndex ();
 
    /**
     * Set the anchor selection index. 
     * 
     * @see #getAnchorSelectionIndex()
     */	
	void  setAnchorSelectionIndex (int index );	

    /**
     * Return the second index argument from the most recent call to 
     * setSelectionInterval(), addSelectionInterval() or removeSelectionInterval().
     * 
     * @see #getAnchorSelectionIndex()
     * @see #setSelectionInterval()
     * @see #addSelectionInterval()
     */
	int  getLeadSelectionIndex ();

    /**
     * Set the lead selection index. 
     * 
     * @see #getLeadSelectionIndex()
     */
	void  setLeadSelectionIndex (int index );

    /**
     * Change the selection to the empty set.  If this represents
     * a change to the current selection then notify each ListSelectionListener.
     * 
     * @param programmatic indicate if this is a programmatic change.
     * @see #addListSelectionListener()
     */
	void  clearSelection (boolean programmatic =true );	

    /**
     * Returns true if no indices are selected.
     */
	boolean  isSelectionEmpty ();
 
    /** 
     * Insert length indices beginning before/after index.  This is typically 
     * called to sync the selection model with a corresponding change
     * in the data model.
     * @param index the index.
     * @param length the length.
     * @param before whether before or after.
     * @param programmatic indicate if this is a programmatic change.
     */
    void  insertIndexInterval (int index ,int length ,boolean before ,boolean programmatic =true );

    /** 
     * Remove the indices in the interval index0,index1 (inclusive) from
     * the selection model.  This is typically called to sync the selection
     * model width a corresponding change in the data model.
     * @param index the first index.
     * @param length the second index.
     * @param programmatic indicate if this is a programmatic change.
     */
    void  removeIndexInterval (int index0 ,int index1 ,boolean programmatic =true );
 
    /**
     * Set the selection mode. The following selectionMode values are allowed:
     * <ul>
     * <li> <code>SINGLE_SELECTION</code> 
     *   Only one list index can be selected at a time.  In this
     *   mode the setSelectionInterval and addSelectionInterval 
     *   methods are equivalent, and only the second index
     *   argument (the "lead index") is used.</li>
     * <li> <code>MULTIPLE_SELECTION</code> 
     *   In this mode, there's no restriction on what can be selected.</li>
     * </ul>
     * 
     * @see #getSelectionMode()
     */
	void  setSelectionMode (int selectionMode );

    /**
     * Returns the current selection mode.
     * @return The value of the selectionMode property.
     * @see #setSelectionMode()
     */
	int  getSelectionMode ();

    /**
     * Add a listener to the list that's notified each time a change
     * to the selection occurs.
     * @param listener the listener to be add.
     */  
	void  addListSelectionListener (Function listener );
	
	/**
	 * Removes a listener from the list selection listeners.
	 * @param listener the listener to be removed.
	 */
	void  removeListSelectionListener (Function listener );
	
}


