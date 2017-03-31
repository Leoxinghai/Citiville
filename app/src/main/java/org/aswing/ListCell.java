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
 * Cell for <code>JList</code>.
 * @see JList
 * @author iiley
 */
public interface ListCell extends Cell{
	/**
	 * Sets the table cell status, include the owner-JList, isSelected, the cell index.
	 * @param the cell's owner, a JList
	 * @param isSelected true to set the cell selected, false to set not selected.
	 * @param index the index of the list item
	 */
	void  setListCellStatus (JList list ,boolean isSelected ,int index );
}


