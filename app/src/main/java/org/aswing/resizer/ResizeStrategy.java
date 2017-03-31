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


import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;

/**
 * The strategy for DefaultResizer to count the new bounds of component would be resized to.
 * @author iiley
 */
public interface ResizeStrategy{
	
	/**
	 * Count and return the new bounds what the component would be resized to.
	 * @param origBounds the original bounds before resized
	 * @param minSize can be null, means (0, 1)
	 * @param maxSize can be null, means (very big)
	 * @param movedX 
	 * @param movedY 
	 */
	IntRectangle  getBounds (IntRectangle origBounds ,IntDimension minSize ,IntDimension maxSize ,int movedX ,int movedY );
}


