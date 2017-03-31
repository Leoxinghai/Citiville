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
 * Interface for editable components.
 * @author iiley
 */
public interface EditableComponent{
	
	/**
     * Returns true if the component is editable, false not editable.
     * @return true if the component is editable, false not editable.
     */
	boolean  isEditable ();
	
	/**
     * Sets the whether or not the component is editable.
     * @param b true to set to editable, false not editable.
     */
	void  setEditable (boolean b );	
}


