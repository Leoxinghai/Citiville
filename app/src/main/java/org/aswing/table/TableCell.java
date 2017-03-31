/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table;

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


import org.aswing.Cell;
import org.aswing.JTable;

/**
 * @author iiley
 */
public interface TableCell extends Cell{
	
	/**
	 * Sets the table cell status, include the owner-JTable isSelected, row position, column position.
	 * @param the cell's owner, a JTable
	 * @param isSelected true to set the cell selected, false to set not selected.
	 * @param row the row position
	 * @param column the column position
	 */
	void  setTableCellStatus (JTable table ,boolean isSelected ,int row ,int column );

}


