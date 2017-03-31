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

	
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.Graphics2D;
	
/**
 * Interface describing an object capable of rendering a border around the edges of a component.
 * <p>
 * You can either return a display object to be the border or just return null and paint the border 
 * in <code>updateBorder</code> method use the component g(Graphics).
 * </p>
 */
public interface Border extends Decorator
	/**
	 * Updates the border.
	 * @param c the component which owns the border.
	 * @param g the graphics of the component, you can paint picture onto it.
	 * @param b the bounds of the border should be.
	 */
	void  updateBorder (Component c ,Graphics2D g ,IntRectangle b );
	
	/**
	 * Returns the insets of the border.
	 * @param c the component which owns the border.
	 * @param b the bounds of the border should be.
	 */
	Insets  getBorderInsets (Component c ,IntRectangle b );	

}


