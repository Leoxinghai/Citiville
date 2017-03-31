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
 * The default list cell factory for text cells.
 * @author iiley
 */
public class DefaultListTextCellFactory implements ListCellFactory{
	
	protected Class listCellClass ;
	protected boolean shareCelles ;
	protected int cellHeight ;
	protected boolean sameHeight ;
	
	/**
	 * Create a list cell(with text renderer) factory with a list cell class and other properties.
	 * @param listCellClass the ListCell implementation, for example com.xlands.ui.list.UserListCell
	 * @param shareCelles (optional)is share cells for list items, default is true.
	 * @param sameHeight (optional)is all cells with same height, default is true.
	 * @param height (optional)the height for all cells if sameHeight, if not <code>sameHeight</code>, 
	 * this param can be miss, default is -1, it means will use a sample to count at the first time.
	 * @see #isShareCells()
	 */	
	public  DefaultListTextCellFactory (Class listCellClass ,boolean shareCelles =true ,boolean sameHeight =true ,int height =-1){
		this.listCellClass = listCellClass;
		this.shareCelles = shareCelles;
		this.sameHeight = sameHeight;
		
		cellHeight = height;
	}
	
	public ListCell  createNewCell (){
		return new listCellClass();
	}
	
	/**
	 * @see ListCellFactory#isAllCellHasSameHeight()
	 */
	public boolean  isAllCellHasSameHeight (){
		return sameHeight;
	}
	
	/**
	 * @return is share cells for items.
	 * @see ListCellFactory#isShareCells()
	 */
	public boolean  isShareCells (){
		return shareCelles;
	}
	
	/**
	 * Sets the height for all cells
	 */
	public void  setCellHeight (int h ){
		cellHeight = h;
	}
	
	/**
	 * Returns the height for all cells
	 * @see ListCellFactory#getCellHeight()
	 */
	public int  getCellHeight (){
		if(cellHeight < 0){
			ListCell cell =createNewCell ();
			cell.setCellValue("JjHhWpqQ1@|");
			cellHeight = cell.getCellComponent().getPreferredSize().height;
		}
		return cellHeight;
	}
	
}


