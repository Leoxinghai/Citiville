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


import flash.display.*;

/**
 * The Cursor definited from Look and Feels.
 * @author iiley
 */
public class Cursor{

	/**
	 * Horizontal resize cursor key.
	 */
	public static String H_RESIZE_CURSOR ="System.hResizeCursor";

	/**
	 * Vertical resize cursor key.
	 */
	public static String V_RESIZE_CURSOR ="System.vResizeCursor";

	/**
	 * Horizontal move cursor key.
	 */
	public static String H_MOVE_CURSOR ="System.hMoveCursor";

	/**
	 * Vertical move cursor key.
	 */
	public static String V_MOVE_CURSOR ="System.vMoveCursor";

	/**
	 * All direction resize cursor key.
	 */
	public static String HV_RESIZE_CURSOR ="System.hvResizeCursor";

	/**
	 * All direction move cursor key.
	 */
	public static String HV_MOVE_CURSOR ="System.hvMoveCursor";

	/**
	 * Create a cursor from the look and feel defined system cursor.
	 * @param the type of the cursor
	 * @return a cursor, or null if there is not such cursor of this type.
	 */
	public static DisplayObject  createCursor (String type ){
		DisplayObject cursor =UIManager.getInstance(type )as DisplayObject ;
		if(cursor == null){
			return null;
		}else if(cursor instanceof Bitmap){
			Sprite sp =AsWingUtils.createSprite(null ,"bmCursorAdap");
			sp.addChild(cursor);
			cursor.x = -cursor.width/2;
			cursor.y = -cursor.height/2;
			return sp;
		}else{
			return cursor;
		}
	}
}


