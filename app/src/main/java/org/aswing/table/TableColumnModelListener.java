/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table;

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


import org.aswing.table.TableColumnModel;
import org.aswing.table.TableColumnModelEvent;

/**
 * TableColumnModelListener defines the interface for an object that listens
 * to changes in a TableColumnModel.
 * 
 * @author iiley
 */
public interface TableColumnModelListener{

    /** Tells listeners that a column was added to the model. */
    void  columnAdded (TableColumnModelEvent e );

    /** Tells listeners that a column was removed from the model. */
    void  columnRemoved (TableColumnModelEvent e );

    /** Tells listeners that a column was repositioned. */
    void  columnMoved (TableColumnModelEvent e );

    /** Tells listeners that a column was moved due to a margin change. */
    void  columnMarginChanged (TableColumnModel source );

    /**
     * Tells listeners that the selection model of the
     * TableColumnModel changed.
     */
    void  columnSelectionChanged (TableColumnModel source ,int firstIndex ,int lastIndex ,boolean programmatic );
}


