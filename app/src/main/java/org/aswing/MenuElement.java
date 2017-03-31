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
 * Any component that can be placed into a menu should implement this interface.
 * This interface is used by <code>MenuSelectionManager</code>
 * to handle selection and navigation in menu hierarchies.
 * 
 * @author iiley
 */
public interface MenuElement{
	
	/**
     * Call by the <code>MenuSelectionManager</code> when the
     * <code>MenuElement</code> is added or remove from 
     * the menu selection.
     */
    void  menuSelectionChanged (boolean isIncluded );

    /**
     * This method should return an array containing the sub-elements for the receiving menu element
     *
     * @return an array of MenuElements
     */
    MenuElement[] Array  getSubElements ();//
    
    /**
     * Precess the selection when key typed. 
     */
    void  processKeyEvent (int code );
    
    /**
     * Sets whether the menu element is in use.
     */
    void  setInUse (boolean b );
    
    /**
     * Returns whether the menu element is in use or not.
     */
    boolean  isInUse ();
    
    /**
     * This method should return the Component used to paint the receiving element.
     * The returned component will be used to convert events and detect if an event is inside
     * a MenuElement's component.
     *
     * @return the Component value
     */
    Component  getMenuComponent ();	
}


