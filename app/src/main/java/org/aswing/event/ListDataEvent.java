package org.aswing.event;

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
 * The event for list model.
 * @see org.aswing.JList
 * @see org.aswing.ListModel
 * @see org.aswing.event.ListDataListener
 * @author iiley
 */
public class ListDataEvent extends ModelEvent{

    private int index0 ;
    private int index1 ;
    private Array removedItems ;

    /**
     * Returns the lower index of the range. For a single
     * element, this value is the same as that returned by {@link #getIndex1}.
     * @return an int representing the lower index value
     */
    public index0 return int  getIndex0 (){;}
    
    /**
     * Returns the upper index of the range. For a single
     * element, this value is the same as that returned by {@link #getIndex0}.
     * @return an int representing the upper index value
     */
    public index1 return int  getIndex1 (){;}
    
	/**
	 * Returns the removed items, it is null or empty array when this is not a removed event.
	 * @return a array that contains the removed items
	 */
	public concat removedItems return Array  getRemovedItems (){.();}
	
    /**
     * Constructs a ListDataEvent object.
     *
     * @param source  the source Object (typically <code>this</code>)
     * @param index0  an int specifying the bottom of a range
     * @param index1  an int specifying the top of a range
     * @param removedItems (optional) the items has been removed.
     */
	public  ListDataEvent (Object source ,int index0 ,int index1 ,Array removedItems ){
		super(source);
		this.index0 = index0;
		this.index1 = index1;
		this.removedItems  = removedItems.concat();
	}
	
}


