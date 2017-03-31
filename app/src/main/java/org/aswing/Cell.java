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
 * Complex component cell base class, like JList, JTable's cell.
 * @author iiley
 */
public interface Cell{
	/**
	 * Sets the value of this cell.
	 * @param value which should represent on the component of this cell.
	 */
	void  setCellValue (Object value);

	/**
	 * Returns the value of the cell.
	 * @return the value of the cell.
	 */
	Object getCellValue ();

	/**
	 * Return the represent component of this cell.
	 * @return the cell component.
	 */
	Component  getCellComponent ();
}


