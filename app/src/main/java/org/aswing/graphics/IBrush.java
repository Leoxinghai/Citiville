/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.graphics;

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

	
import flash.display.Graphics;

/**
 * Brush to fill a closed area.<br>
 * Use it with a org.aswing.graphics.Graphics2D instance
 * @author iiley
 */
public interface IBrush{
	
	/**
	 * 
	 * This method will be called by Graphics2D autumaticlly.<br>
	 * It applys the fill paramters to the instance of flash.display.Graphics
	 * 
	 * @param target the instance of a flash.display.Graphics
	 */
	 void  beginFill (Graphics target );
	
	/**
	 * 
	 * This method will be called by Graphics2D autumaticlly.<br>
	 * It marks the end of filling
	 * 
	 * @param target the instance of a flash.display.Graphics
	 */
	 void  endFill (Graphics target );

}


