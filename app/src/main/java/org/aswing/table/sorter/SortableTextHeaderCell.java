/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table.sorter;

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


import org.aswing.JTable;
import org.aswing.table.DefaultTextHeaderCell;
import org.aswing.UIManager;

/**
 * @author iiley
 */
public class SortableTextHeaderCell extends DefaultTextHeaderCell{
	
	private TableSorter tableSorter ;
	
	public  SortableTextHeaderCell (TableSorter tableSorter ){
		super();
		setBorder(UIManager.getBorder("TableHeader.sortableCellBorder"));
		setBackgroundDecorator(UIManager.getGroundDecorator("TableHeader.sortableCellBackground"));
		this.tableSorter = tableSorter;
		setHorizontalTextPosition(LEFT);
		setIconTextGap(6);
	}
	
	 public void  setTableCellStatus (JTable table ,boolean isSelected ,int row ,int column ){
		super.setTableCellStatus(table, isSelected, row, column);
		int modelColumn =table.convertColumnIndexToModel(column );
		setIcon(tableSorter.getHeaderRendererIcon(modelColumn, getFont().getSize()-2));
	}
}


