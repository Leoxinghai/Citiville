/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf;

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
 * Pluginable ui for JFrame.
 * @see org.aswing.JFrame
 * @author iiley
 */
public interface FrameUI extends ComponentUI
	/**
	 * Flash the modal frame. (User clicked other where is not in the modal frame, 
	 * flash the frame to make notice this frame is modal.)
	 */
	void  flashModalFrame ();
	
	/**
	 * For <code>flashModalFrame</code> to judge whether paint actived color or inactived color.
	 */
	boolean  isPaintActivedFrame ();
}

