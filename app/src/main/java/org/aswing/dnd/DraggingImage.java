
package org.aswing.dnd;

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
 * The Image for dragging representing.
 * 
 * @author iiley
 */
public interface DraggingImage{

	/**
	 * Returns the display object for the representation of dragging.
	 */
	DisplayObject  getDisplay ();
	
	/**
	 * Paints the image for accept state of dragging.(means drop allowed)
	 */
	void  switchToAcceptImage ();
	
	/**
	 * Paints the image for reject state of dragging.(means drop not allowed)
	 */
	void  switchToRejectImage ();	
}


