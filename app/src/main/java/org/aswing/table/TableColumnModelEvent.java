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

	
import org.aswing.event.ModelEvent;

/**
 * @author iiley
 */
public class TableColumnModelEvent extends ModelEvent{
	
    /** The index of the column from where it was moved or removed */
    private 	int fromIndex ;

    /** The index of the column to where it was moved or added from */
    private 	int toIndex ;

    /**
     * Constructs a TableColumnModelEvent object.
     *
     * @param source  the TableColumnModel that originated the event
     *                (typically <code>this</code>)
     * @param from    an int specifying the first row in a range of affected rows
     * @param to      an int specifying the last row in a range of affected rows
     */
    public  TableColumnModelEvent (TableColumnModel source ,int from ,int _to ){
		super(source);
		fromIndex = from;
		toIndex = _to;
    }

    /** Returns the fromIndex.  Valid for removed or moved events */
    public fromIndex return int  getFromIndex (){;};

    /** Returns the toIndex.  Valid for add and moved events */
    public toIndex return int  getToIndex (){;};
}


