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


import org.aswing.tree.TreeCell;
import org.aswing.tree.TreeCellFactory;

/**
 * @author iiley
 */
public class GeneralTreeCellFactory implements TreeCellFactory {
		
	private Class cellClass ;
	
	/**
	 * Creates a GeneralTreeCellFactory with specified cell class.
	 * @param cellClass the cell class
	 */
	public  GeneralTreeCellFactory (Class cellClass ){
		this.cellClass = cellClass;
	}
	
	/**
	 * Creates and returns a new tree cell.
	 * @return the tree cell
	 */
	public TreeCell  createNewCell (){
		return new cellClass();
	}
	
	public String  toString (){
		return "GeneralTreeCellFactory.get(cellClass:" + cellClass + ")";
	}
}


