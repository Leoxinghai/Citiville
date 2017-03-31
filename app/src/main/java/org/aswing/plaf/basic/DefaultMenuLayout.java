/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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


import org.aswing.SoftBoxLayout;
import org.aswing.plaf.UIResource;
import org.aswing.AsWingConstants;

/**
 * @private
 */
public class DefaultMenuLayout extends SoftBoxLayout implements UIResource{
	/**
     * Specifies that components should be laid out left to right.
     */
    public static  int X_AXIS =0;
    
    /**
     * Specifies that components should be laid out top to bottom.
     */
    public static  int Y_AXIS =1;
    
	public  DefaultMenuLayout (int axis ,int gap =0,int align =AsWingConstants .LEFT ){
		super(axis, gap, align);
	}
	
}


