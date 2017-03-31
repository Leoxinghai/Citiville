
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


import org.aswing.Component;
import flash.display.Sprite;

/**
 * The motion when a drag action dropped.
 * The motion must remove the drag movie clip when motion is completed.
 * @author iiley
 */
public interface DropMotion{
	
	/**
	 * Starts the drop motion and remove the dragObject from its parent when motion is completed.
	 * @param dragInitiator the drag initiator
	 * @param dragObject the display object to do motion
	 */
	void  startMotionAndLaterRemove (Component dragInitiator ,Sprite dragObject );
	
	/**
	 * A new drag is started, so the last motion should be stopped if it is still running.
	 */
	void  forceStop ();
		
}


