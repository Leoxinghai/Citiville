/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.tree;

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

/**
 * Used by <code>AbstractLayoutCache</code> to determine the size
 * and x origin of a particular node.
 * @author iiley
 */
public interface NodeDimensions {
	/**
	 * Returns, by reference in bounds, the size and x origin to
	 * place value at. The calling method is responsible for determining
	 * the Y location. If bounds is <code>null</code>, a newly created
	 * <code>IntRectangle</code> should be returned,
	 * otherwise the value should be placed in bounds and returned.
	 *
	 * @param value the <code>value</code> to be represented
	 * @param row row being queried
  	 * @param depth the depth of the row
	 * @param expanded true if row is expanded, false otherwise
	 * @param bounds  a <code>IntRectangle</code> containing the size needed
	 *		to represent <code>value</code>
   	 * @return a <code>IntRectangle</code> containing the node dimensions,
	 * 		or <code>null</code> if node has no dimension
	 */
	IntRectangle  countNodeDimensions (*value ,int row ,int depth ,boolean expanded ,IntRectangle bounds );
}


