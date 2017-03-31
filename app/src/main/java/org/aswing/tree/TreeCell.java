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


import org.aswing.Cell;
import org.aswing.JTree;

/**
 * @author iiley
 */
public interface TreeCell extends Cell {
	/**
	 * Sets the table cell status, include the owner-JTable isSelected, row position, column position.
	 * @param the cell's owner, a JTable
	 * @param seleted true indicated the cell selected, false not selected.
	 * @param expanded true the node is currently expanded, false not.
	 * @param leaf true the node represets a leaf, false not.
	 * @param row the row position
	 */
	void  setTreeCellStatus (JTree tree ,boolean selected ,boolean expanded ,boolean leaf ,int row );	
}


