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


import flash.display.DisplayObject;

/**
 * Decorator for components, it return a display object to be the UI decorator.
 */
public interface Decorator
	/**
	 * Returns the display object which is used as the component decorator.
	 * <p>
	 * For same component, this method must return same display object.
	 * </p>
	 * @param c the component which will use this decorator.
	 * @return the display object
	 */
	DisplayObject  getDisplay (Component c );
	

}


