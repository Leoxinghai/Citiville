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
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.Graphics2D;
	
/**
 * Decorator for background or foreground of a components.
 * <p>
 * You can either return a display object to be the decorator or just return null and paint  
 * in <code>updateDecorator</code> method use the component g(Graphics).
 * <p>
 * (Maybe it is not good to paint on the component graphics for foreground decorator since component graphics 
 * is not on top of component children)
 */
public interface GroundDecorator extends Decorator
	/**
	 * Updates the decorator.
	 * @param c the component which owns the ground decorator.
	 * @param g the graphics of the component, you can paint picture onto it.
	 * @param b the bounds of the component can be decorated.
	 */
	void  updateDecorator (Component c ,Graphics2D g ,IntRectangle b );


}


