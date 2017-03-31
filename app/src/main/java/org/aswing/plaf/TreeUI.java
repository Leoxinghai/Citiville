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


import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.JTree;
import org.aswing.plaf.ComponentUI;
import org.aswing.tree.TreePath;

/**
 * Pluggable look and feel interface for JTree.
 * @author iiley
 * @private
 */
public interface TreeUI extends ComponentUI {
	
    /**
      * Returns the IntRectangle enclosing the label portion that the
      * last item in path will be drawn into.  Will return null if
      * any component in path is currently valid.
      */
    IntRectangle  getPathBounds (JTree tree ,TreePath path );

    /**
      * Returns the path for passed in row.  If row is not visible
      * null is returned.
      */
    TreePath  getPathForRow (JTree tree ,int row );

    /**
      * Returns the row that the last item identified in path is visible
      * at.  Will return -1 if any of the elements in path are not
      * currently visible.
      */
    int  getRowForPath (JTree tree ,TreePath path );

    /**
      * Returns the number of rows that are being displayed.
      */
    int  getRowCount (JTree tree );

    /**
      * Returns the path to the node that is closest to x,y.  If
      * there is nothing currently visible this will return null, otherwise
      * it'll always return a valid path.  If you need to test if the
      * returned object is exactly at x, y you should get the bounds for
      * the returned path and test x, y against that.
      */
    TreePath  getClosestPathForLocation (JTree tree ,int x ,int y );

    /**
      * Returns true if the tree is being edited.  The item that is being
      * edited can be returned by getEditingPath().
      */
    boolean  isEditing (JTree tree );

    /**
      * Stops the current editing session.  This has no effect if the
      * tree isn't being edited.  Returns true if the editor allows the
      * editing session to stop.
      */
    boolean  stopEditing (JTree tree );

    /**
      * Cancels the current editing session. This has no effect if the
      * tree isn't being edited.  Returns true if the editor allows the
      * editing session to stop.
      */
    void  cancelEditing (JTree tree );

    /**
      * Selects the last item in path and tries to edit it.  Editing will
      * fail if the CellEditor won't allow it for the selected item.
      * 
      * @return true is started sucessful, editing fail
      */
    boolean  startEditingAtPath (JTree tree ,TreePath path );

    /**
     * Returns the path to the element that is being edited.
     */
    TreePath  getEditingPath (JTree tree );
    
    /**
     * Returns the view size.
     */    
	IntDimension  getViewSize (JTree tree );

    /**
     * Returns the treePath that the user mouse pointed, null if no path was pointed.
     */	
	TreePath  getMousePointedPath ();
}


