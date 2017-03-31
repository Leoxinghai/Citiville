/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf;

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


import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import flash.display.InteractiveObject;

/**
 * The interface for all UI delegate objects in the AsWing pluggable look and feel architecture.
 * The UI delegate object for a AsWing component is responsible for implementing
 * the aspects of the component that depend on the look and feel. The Component
 * class invokes methods from this class in order to delegate operations (painting,
 * layout calculations, etc.) that may vary depending on the look and feel installed.
 * <b>Client programs should not invoke methods on this class directly.</b>
 *
 * @author iiley
 */
public interface ComponentUI

    /**
     * Configures the specified component appropriate for the look and feel.
     * This method is invoked when the <code>ComponentUI</code> instance is being installed
     * as the UI delegate on the specified component.  This method should
     * completely configure the component for the look and feel,
     * including the following:
     * <ol>
     * <li>Install any default property values for color, fonts, borders,
     *     icons, opacity, etc. on the component.  Whenever possible,
     *     property values initialized by the client program should <i>not</i>
     *     be overridden.
     * <li>Install a <code>LayoutManager</code> on the component if necessary.
     * <li>Create/add any required sub-components to the component.
     * <li>Create/install event listeners on the component.
     * <li>Install keyboard UI (mnemonics, traversal, etc.) on the component.
     * <li>Initialize any appropriate instance data.
     * </ol>
     * @param c the component where this UI delegate is being installed
     *
     * @see #uninstallUI
     * @see org.aswing.Component#setUI
     * @see org.aswing.Component#updateUI
     */
    void  installUI (Component c );

    /**
     * Reverses configuration which was done on the specified component during
     * <code>installUI</code>.  This method is invoked when this
     * <code>ComponentUI</code> instance is being removed as the UI delegate
     * for the specified component.  This method should undo the
     * configuration performed in <code>installUI</code>, being careful to
     * leave the <code>Component</code> instance in a clean state (no
     * extraneous listeners, look-and-feel-specific property objects, etc.).
     * This should include the following:
     * <ol>
     * <li>Remove any UI-set borders from the component.
     * <li>Remove any UI-set layout managers on the component.
     * <li>Remove any UI-added sub-components from the component.
     * <li>Remove any UI-added event listeners from the component.
     * <li>Remove any UI-installed keyboard UI from the component.
     * <li>Remove any UI-added MCs from this component.
     * <li>Nullify any allocated instance data objects to allow for GC.
     * </ol>
     * @param c the component from which this UI delegate is being removed;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see #installUI
     * @see org.aswing.Component#updateUI
     */
    void  uninstallUI (Component c );
    /**
     * Notifies this UI delegate that it's time to paint the specified
     * component.  This method is invoked by <code>Component</code>
     * when the specified component is being painted.
     *
     * <p>In general this method need be overridden by subclasses;
     * all look-and-feel rendering code should reside in this method.
     * And there is a default background paint method, you should call
     * it in your overridden paint method.
     *
     * @param c the component being painted;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components.
     * @param g the Graphics context in which to paint.
     * @param b the bounds to paint UI in.
     *
     * @see org.aswing.Component#paint
     * @see #paintBackGround
     */
    void  paint (Component c ,Graphics2D g ,IntRectangle b );

    /**
     * Paints focus representation to the component.
     * @param c the component being painted;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components.
     * @param g the Graphics context in which to paint.
     * @param b the bounds to paint focus in.
     */
    void  paintFocus (Component c ,Graphics2D g ,IntRectangle b );

    /**
     * Returns the object to receive the focus for the component.
     * @param c the component
     * @return the object to receive the focus.
     */
	InteractiveObject  getInternalFocusObject (Component c );

    /**
     * Returns the specified component's preferred size appropriate for
     * the look and feel.  If <code>null</code> is returned, the preferred
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method returns <code>null</code>.
     *
     * @param c the component whose preferred size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see org.aswing.Component#getPreferredSize
     * @see org.aswing.LayoutManager#preferredLayoutSize
     */
    IntDimension  getPreferredSize (Component c );

    /**
     * Returns the specified component's minimum size appropriate for
     * the look and feel.  If <code>null</code> is returned, the minimum
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method invokes <code>getPreferredSize</code> and returns that value.
     *
     * @param c the component whose minimum size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @return a <code>IntDimension</code> object or <code>null</code>
     *
     * @see org.aswing.Component#getMinimumSize
     * @see org.aswing.LayoutManager#minimumLayoutSize
     * @see #getPreferredSize
     */
    IntDimension  getMinimumSize (Component c );

    /**
     * Returns the specified component's maximum size appropriate for
     * the look and feel.  If <code>null</code> is returned, the maximum
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method invokes <code>getPreferredSize</code> and returns that value.
     *
     * @param c the component whose maximum size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     * @return a <code>IntDimension</code> object or <code>null</code>
     *
     * @see org.aswing.Component#getMaximumSize
     * @see org.aswing.LayoutManager#maximumLayoutSize
     * @see #getPreferredSize
     */
    IntDimension  getMaximumSize (Component c );

    //---------------

    /**
     * Puts a ui default value used to this UI instead of LAF defualt value.
     * The values should be put before installing then it can be used.
     * @param key the key.
     * @param value the value.
     */
    void  putDefault (String key ,Object value);

    /**
     * Returns the default property for this UI of specified key.
     * Generally, the ui will search the key in self holder that was put by
     * <code>putDefault</code> first, if it contains, then return it. If not
     * then it will call <code>UIManager.get</code> to get it from LAF defaults.
     * @return the default property of specifiled key.
     */
    Object getDefault (String key );

	boolean  getBoolean (String key );

	double  getNumber (String key );

	int  getInt (String key );

	int  getUint (String key );

	String  getString (String key );

	Border  getBorder (String key );

	Icon  getIcon (String key );

	GroundDecorator  getGroundDecorator (String key );

	ASColor  getColor (String key );

	ASFont  getFont (String key );

	Insets  getInsets (String key );

	Object getInstance (String key );

	Class  getClass (String key );

}


