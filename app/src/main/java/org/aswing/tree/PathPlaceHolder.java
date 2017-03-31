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

/*
 Copyright aswing.org, see the LICENCE.txt.
*/
import org.aswing.tree.TreePath;

/**
 * @author iiley
 */
public class PathPlaceHolder {
    internal boolean isNew ;
    internal TreePath path ;

    public  PathPlaceHolder (TreePath path ,boolean isNew ){
		this.path = path;
		this.isNew = isNew;
    }	
}


