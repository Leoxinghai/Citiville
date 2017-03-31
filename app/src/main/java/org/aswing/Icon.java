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

	
import org.aswing.Component;
import org.aswing.graphics.Graphics2D;
	
/**
 * A small fixed size picture, typically used to decorate components. 
 * <p>
 * You can either return a display object to be the icon or just return null and paint the picture 
 * in <code>updateIcon</code> method use the component g(Graphics).
 * </p>
 * But, you'd better to return a display object here, because if you just paint graphics 
 * to the target component graphics, there's a situation that your painted graphics maybe 
 * not eyeable, that is when the component has a background decorator with a display object, 
 * it will cover this graphics. If you return a display object here, it will be no problem of this case.
 */
public interface Icon extends Decorator
{	
	/**
	 * Returuns the icon width.
	 * <p>
	 * For same component param, this method must return same value.
	 * </p>
	 * @param c the component which owns the icon.
	 * @return the width of the icon.
	 */
	int  getIconWidth (Component c );
	
	/**
	 * Returns the icon height.
	 * <p>
	 * For same component param, this method must return same value.
	 * </p>
	 * @param c the component which owns the icon.
	 * @return the height of the icon.
	 */
	int  getIconHeight (Component c );
	
	/**
	 * Updates the icon.
	 * @param c the component which owns the icon.
	 * @param g the graphics of the component, you can paint picture onto it.
	 * @param x the x coordinates of the icon should be.
	 * @param y the y coordinates of the icon should be.
	 */
	void  updateIcon (Component c ,Graphics2D g ,int x ,int y );	

}


