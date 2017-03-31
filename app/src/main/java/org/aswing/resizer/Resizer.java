/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.resizer;

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


import org.aswing.Component;

/**
 * Component Resizer interface.
 * @author iiley
 */
public interface Resizer{
	
	/**
	 * Sets the owner of this resizer. 
	 * If the owner is changed, the last owner will be off-dressed the resizer, the new 
	 * owner will be on-dressed.
	 * Use null to off-dress the current owner.
	 * @param c the new owner or null.
	 */
	void  setOwner (Component c );
				
	/**
	 * <p>Indicate whether need resize component directly when drag the resizer arrow.
	 * <p>if set to false, there will be a rectange to represent then size what will be resized to.
	 * <p>if set to true, the component will be resize directly when drag, but this is need more cpu counting.
	 * <p>Default is false.
	 * @see org.aswing.JFrame
	 */	
	void  setResizeDirectly (boolean r );
	
	/**
	 * Returns whether need resize component directly when drag the resizer arrow.
	 * @see #setResizeDirectly
	 */
	boolean  isResizeDirectly ();
	
	/**
	 * Returns whether this resizer is enabled.
	 */
	boolean  isEnabled ();
	
	/**
	 * Sets the resizer to enabled or not.
	 */
	void  setEnabled (boolean b );
}


