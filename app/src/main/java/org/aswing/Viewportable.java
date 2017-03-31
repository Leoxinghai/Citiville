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

/**
 * Dispatched when the viewport's state changed. the state is all about:
 * <ul>
 * <li>view position</li>
 * <li>verticalUnitIncrement</li>
 * <li>verticalBlockIncrement</li>
 * <li>horizontalUnitIncrement</li>
 * <li>horizontalBlockIncrement</li>
 * </ul>
 * </p>
 * 
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A viewportable object can scrolled by <code>JScrollPane</code>, 
 * <code>JScrollBar</code> to view its viewed content in a visible area.
 * 
 * @see JScrollPane
 * @see JViewport
 * @see JList
 * @see JTextArea
 * 
 * @author iiley
 */	
public interface Viewportable{

	/**
	 * Returns the unit value for the Vertical scrolling.
	 */
    int  getVerticalUnitIncrement ();
    
    /**
     * Return the block value for the Vertical scrolling.
     */
    int  getVerticalBlockIncrement ();
    
	/**
	 * Returns the unit value for the Horizontal scrolling.
	 */
    int  getHorizontalUnitIncrement ();
    
    /**
     * Return the block value for the Horizontal scrolling.
     */
    int  getHorizontalBlockIncrement ();
    
	/**
	 * Sets the unit value for the Vertical scrolling.
	 */
    void  setVerticalUnitIncrement (int increment );
    
    /**
     * Sets the block value for the Vertical scrolling.
     */
    void  setVerticalBlockIncrement (int increment );
    
	/**
	 * Sets the unit value for the Horizontal scrolling.
	 */
    void  setHorizontalUnitIncrement (int increment );
    
    /**
     * Sets the block value for the Horizontal scrolling.
     */
    void  setHorizontalBlockIncrement (int increment );
    
    /**
     * Before JScrollPane analyse the scroll properties(call getExtentSize and getViewSize), 
     * it will call this method to set the size of viewport will be to test.
     * 
     * @param s the size to test
     */
    void  setViewportTestSize (IntDimension s );
    
    /**
     * Returns the size of the visible part of the view in view logic coordinates.
     *
     * @return a <code>IntDimension</code> object giving the size of the view
     */
    IntDimension  getExtentSize ();
    
    /**
     * Returns the viewportable view's amount size if view all content in view logic coordinates.
     * Usually the view's preffered size.
     * @return the view's size.
     */
    IntDimension  getViewSize ();
    
    /**
     * Returns the view coordinates that appear in the upper left
     * hand corner of the viewport, or 0,0 if there's no view. in view logic coordinates.
     *
     * @return a <code>IntPoint</code> object giving the upper left coordinates
     */
    IntPoint  getViewPosition ();
    
    /**
     * Sets the view coordinates that appear in the upper left
     * hand corner of the viewport. in view logic coordinates.
     *
     * @param p  a <code>IntPoint</code> object giving the upper left coordinates
     * @param programmatic indicate if this is a programmatic change.
     */
    void  setViewPosition (IntPoint p ,boolean programmatic =true );
    
    /**
     * Scrolls the view so that <code>IntRectangle</code>
     * within the view becomes visible. in view logic coordinates.
     * <p>
     * Note that this method will not scroll outside of the
     * valid viewport; for example, if <code>contentRect</code> is larger
     * than the viewport, scrolling will be confined to the viewport's
     * bounds.
     * @param contentRect the <code>IntRectangle</code> to display
     * @param programmatic indicate if this is a programmatic change.
     */
    void  scrollRectToVisible (IntRectangle contentRect ,boolean programmatic =true );
    
	/**
	 * Add a listener to listen the viewpoat state change event.
	 * <p>
	 * When the viewpoat's state changed, the state is all about:
	 * <ul>
	 * <li>viewPosition
	 * </ul>
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false );

	/**
	 * Removes a state listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	void  removeStateListener (Function listener );
	    
    /**
     * Return the component of the viewportable's pane which would added to displayed on the stage.
     * 
     * @return the component of the viewportable pane.
     */
    Component  getViewportPane ();

}


