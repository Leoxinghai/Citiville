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


/**
 * Defines the requirements for an object that translates paths in
 * the tree into display rows.
 * @author iiley
 */
public interface RowMapper {
    /**
     * Returns the rows that the TreePath instances in <code>path</code>
     * are being displayed at. The receiver should return an array of
     * the same length as that passed in, and if one of the TreePaths
     * in <code>path</code> is not valid its entry in the array should
     * be set to -1.
     */
    Array  getRowsForPaths (Array path );
}


