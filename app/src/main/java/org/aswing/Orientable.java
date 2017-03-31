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
 * An component or any thing has HORIZONTAL or VERTICAL form.
 * @author iiley
 */
public interface Orientable{

    /**
     * Sets the orientation, or how the splitter is divided. The options
     * are:
     * <ul>
     * <li>AsWingConstants.HORIZONTAL</li>
     * <li>AsWingConstants.VERTICAL</li>
     * </ul>
     *
     * @param orientation an integer specifying the orientation
     */
    void  setOrientation (int ori );

    /**
     * Returns the orientation.
     * 
     * @return an integer giving the orientation
     * @see #setOrientation()
     */
    int  getOrientation ();
}


