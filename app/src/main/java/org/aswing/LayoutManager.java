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

	
import org.aswing.geom.IntDimension;
	
/** 
 * Defines an interface for classes that know how to layout Containers
 * based on a layout constraints object.
 *
 * @see Component
 * @see Container
 *
 * @author 	iiley
 */
public interface LayoutManager
    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     * @param comp the component to be added
     * @param constraints  where/how the component is added to the layout.
     */
    void  addLayoutComponent (Component comp ,Object constraints );

    /**
     * Removes the specified component from the layout.
     * @param comp the component to be removed
     */
    void  removeLayoutComponent (Component comp );

    /**
     * Calculates the preferred size dimensions for the specified 
     * container, given the components it contains.
     * @param target the container to be laid out
     *  
     * @see #minimumLayoutSize
     */
    IntDimension  preferredLayoutSize (Container target );

    /** 
     * Calculates the minimum size dimensions for the specified 
     * container, given the components it contains.
     * @param target the component to be laid out
     * @see #preferredLayoutSize
     */
    IntDimension  minimumLayoutSize (Container target );

    /** 
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     * @param target the component to be laid out
     * @see #preferredLayoutSize
     */
    IntDimension  maximumLayoutSize (Container target );
    
    /** 
     * Lays out the specified container.
     * @param target the container to be laid out 
     */
    void  layoutContainer (Container target );

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other 
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    double  getLayoutAlignmentX (Container target );

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other 
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    double  getLayoutAlignmentY (Container target );

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    void  invalidateLayout (Container target );

}


