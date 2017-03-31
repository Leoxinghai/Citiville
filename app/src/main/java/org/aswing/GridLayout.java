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


import org.aswing.Component;
import org.aswing.Container;
import org.aswing.EmptyLayout;
import org.aswing.geom.IntDimension;
import org.aswing.Insets;
import org.aswing.geom.IntRectangle;

/**
 * @author feynixs(Cai Rong)
 * @author iiley
 */
public class GridLayout extends EmptyLayout{
	/**
     * This is the horizontal gap (in pixels) which specifies the space
     * between columns.  They can be changed at any time.
     * This should be a non-negative integer.
     *
     * @see #getHgap()
     * @see #setHgap(hgap:int)
     */
    private int hgap ;
    /**
     * This is the vertical gap (in pixels) which specifies the space
     * between rows.  They can be changed at any time.
     * This should be a non negative integer.
     *
     * @see #getVgap()
     * @see #setVgap(vgap:int)
     */
    private int vgap ;
    /**
     * This is the number of rows specified for the grid.  The number
     * of rows can be changed at any time.
     * This should be a non negative integer, where '0' means
     * 'any number' meaning that the number of Rows in that
     * dimension depends on the other dimension.
     *
     * @see #getRows()
     * @see #setRows(rows:int)
     */
    private int rows ;
    /**
     * This is the number of columns specified for the grid.  The number
     * of columns can be changed at any time.
     * This should be a non negative integer, where '0' means
     * 'any number' meaning that the number of Columns in that
     * dimension depends on the other dimension.
     *
     * @see #getColumns()
     * @see #setColumns(cols:int)
     */
    private int cols ;



    /**
     * <p> 
     * Creates a grid layout with the specified number of rows and 
     * columns. All components in the layout are given equal size. 
     * </p>
     * <p>
     * In addition, the horizontal and vertical gaps are set to the 
     * specified values. Horizontal gaps are placed between each
     * of the columns. Vertical gaps are placed between each of
     * the rows. 
     * </p>
     * <p>
     * One, but not both, of <code>rows</code> and <code>cols</code> can 
     * be zero, which means that any number of objects can be placed in a 
     * row or in a column. 
     * </p>
     * <p>
     * All <code>GridLayout</code> constructors defer to this one.
     * </p>
     * @param     rows   the rows, with the value zero meaning 
     *                   any number of rows
     * @param     cols   the columns, with the value zero meaning 
     *                   any number of columns
     * @param     hgap   (optional)the horizontal gap, default 0
     * @param     vgap   (optional)the vertical gap, default 0
     * @throws ArgumentError  if the value of both
     *			<code>rows</code> and <code>cols</code> is 
     *			set to zero
     */
    public  GridLayout (int rows =1,int cols =0,int hgap =0,int vgap =0){
		if ((rows == 0) && (cols == 0)) {
	     	throw new ArgumentError("rows and cols cannot both be zero");
	 	}
	    
		this.rows = rows;
		this.cols = cols;
		this.hgap = hgap;
		this.vgap = vgap;
    }

    /**
     * Gets the number of rows in this layout.
     * @return    the number of rows in this layout
     * 
     */
    public int  getRows (){
		return rows;
    }

    /**
     * Sets the number of rows in this layout to the specified value.
     * @param        rows   the number of rows in this layout
     */
    public void  setRows (int rows ){
		this.rows = rows;
    }

    /**
     * Gets the number of columns in this layout.
     * @return  the number of columns in this layout
     * 
     */
    public int  getColumns (){
		return cols;
    }

    /**
     * Sets the number of columns in this layout. 
     * Setting the number of columns has no effect on the layout 
     * if the number of rows specified by a constructor or by 
     * the <tt>setRows</tt> method is non-zero. In that case, the number 
     * of columns displayed in the layout is determined by the total 
     * number of components and the number of rows specified.
     * @param        cols   the number of columns in this layout
     * 
     */
    public void  setColumns (int cols ){
		this.cols = cols;
    }

    /**
     * Gets the horizontal gap between components.
     * @return       the horizontal gap between components
     * 
     */
    public int  getHgap (){
		return hgap;
    }
    
    /**
     * Sets the horizontal gap between components to the specified value.
     * @param    hgap   the horizontal gap between components
     *
     */
    public void  setHgap (int hgap ){
		this.hgap = hgap;
    }
    
    /**
     * Gets the vertical gap between components.
     * @return       the vertical gap between components
     * 
     */
    public int  getVgap (){
		return vgap;
    }
    
    /**
     * Sets the vertical gap between components to the specified value.
     * @param         vgap  the vertical gap between components
     * 
     */
    public void  setVgap (int vgap ){
		this.vgap = vgap;
    }
	
     public IntDimension  preferredLayoutSize (Container target ){
		Insets insets =target.getInsets ();
		int ncomponents =target.getComponentCount ();
		int nrows =rows ;
		int ncols =cols ;
		if (nrows > 0){
			ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
		}else{
			nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
		}
		int w =0;
		int h =0;
		for(int i =0;i <ncomponents ;i ++){
			Component comp =target.getComponent(i );
			IntDimension d =comp.getPreferredSize ();
			if (w < d.width){
				w = d.width;
			}
			if (h < d.height){
				h = d.height;
			}
		}
		return new IntDimension((((insets.left + insets.right) + (ncols * w)) + ((ncols - 1) * hgap)), (((insets.top + insets.bottom) + (nrows * h)) + ((nrows - 1) * vgap))); 	
    }

     public IntDimension  minimumLayoutSize (Container target ){
		return target.getInsets().getOutsideSize();
    }
	
	/**
	 * return new IntDimension(1000000, 1000000);
	 */
     public IntDimension  maximumLayoutSize (Container target ){
    	return new IntDimension(1000000, 1000000);
    }
    
     public void  layoutContainer (Container target ){
		Insets insets =target.getInsets ();
		int ncomponents =target.getComponentCount ();
		int nrows =rows ;
		int ncols =cols ;
		if (ncomponents == 0){
			return ;
		}
		if (nrows > 0){
			ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
		}else{
			nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
		}
		int w =(target.getWidth ()-(insets.left +insets.right ));
		int h =(target.getHeight ()-(insets.top +insets.bottom ));
		w = Math.floor((w - ((ncols - 1) * hgap)) / ncols);
		h = Math.floor((h - ((nrows - 1) * vgap)) / nrows);
		int x =insets.left ;
		int y =insets.top ;
		for(int c =0;c <ncols ;c ++){
			y = insets.top;
			for(int r =0;r <nrows ;r ++){
				int i =((r *ncols )+c );
				if (i < ncomponents){
					target.getComponent(i).setBounds(new IntRectangle(x, y, w, h));
				}
				y += (h + vgap);
			}
			x += (w + hgap);
		}
	}
	public String  toString (){
		return ((((((((("GridLayout.get(hgap=") + hgap) + ",vgap=") + vgap) + ",rows=") + rows) + ",cols=") + cols) + ")");
	}
    
	/**
	 * return 0.5
	 */
     public double  getLayoutAlignmentX (Container target ){
    	return 0.5;
    }

	/**
	 * return 0.5
	 */
     public double  getLayoutAlignmentY (Container target ){
    	return 0.5;
    }
}


