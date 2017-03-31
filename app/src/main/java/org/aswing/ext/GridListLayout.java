package org.aswing.ext;

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


import org.aswing.*;
import org.aswing.geom.*;

public class GridListLayout extends EmptyLayout{

    private int hgap ;

    private int vgap ;

    private int rows ;

    private int cols ;
    
    private int tileWidth ;
    private int tileHeight ;

    /**
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
    public  GridListLayout (int rows =1,int cols =0,int hgap =0,int vgap =0){
		if ((rows == 0) && (cols == 0)) {
	     	throw new ArgumentError("rows and cols cannot both be zero");
	 	}
	    
		this.rows = rows;
		this.cols = cols;
		this.hgap = hgap;
		this.vgap = vgap;
    }
    
    public int  getRows (){
		return rows;
    }
    
    public void  setRows (int rows ){
		this.rows = rows;
    }

    public int  getColumns (){
		return cols;
    }
    
    public void  setColumns (int cols ){
		this.cols = cols;
    }

    public int  getHgap (){
		return hgap;
    }
    
    public void  setHgap (int hgap ){
		this.hgap = hgap;
    }
    
    public int  getVgap (){
		return vgap;
    }
    
    public void  setVgap (int vgap ){
		this.vgap = vgap;
    }
	
	public int  getTileWidth (){
		return tileWidth;
	}
	
	public int  getTileHeight (){
		return tileHeight;
	}
	
	public void  setTileWidth (int w ){
		tileWidth = w;
	}
	
	public void  setTileHeight (int h ){
		tileHeight = h;
	}	
	
     public IntDimension  preferredLayoutSize (Container target ){
		if ((cols == 0) && (this.rows == 0)) {
	    	throw new Error("rows and cols cannot both be zero");
		}
		Insets insets =target.getInsets ();
		int ncomponents =target.getComponentCount ();
		int nrows =rows ;
		int ncols =cols ;
		if (nrows > 0){
			ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
		}else{
			nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
		}
		int w =tileWidth ;
		int h =tileHeight ;
		return new IntDimension((((insets.left + insets.right) + (ncols * w)) + ((ncols - 1) * hgap)), (((insets.top + insets.bottom) + (nrows * h)) + ((nrows - 1) * vgap))); 	
    }
    
    public IntDimension  getViewSize (GridCellHolder target ){
		if ((cols == 0) && (this.rows == 0)) {
	    	throw new Error("rows and cols cannot both be zero");
		}
		Insets insets =target.getInsets ();
		int ncomponents =target.getComponentCount ();
		int nrows =rows ;
		int ncols =cols ;
		GridList list =target.getList ();
		IntDimension bounds =list.getExtentSize ();
		if(list.isTracksWidth() || list.isTracksHeight()){
			if(list.isTracksHeight()){
				nrows = Math.floor((bounds.height+vgap)/(tileHeight+vgap));
				ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
			}else{
				ncols = Math.floor((bounds.width+hgap)/(tileWidth+hgap));
				nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
			}
		}else{
			if(nrows > 0){
				ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
			}else{
				nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
			}
		}
		int w =tileWidth ;
		int h =tileHeight ;
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
		if ((cols == 0) && (this.rows == 0)) {
	    	throw new Error("rows and cols cannot both be zero");
		}
		Insets insets =target.getInsets ();
		int ncomponents =target.getComponentCount ();
		int nrows =rows ;
		int ncols =cols ;
		if (ncomponents == 0){
			return ;
		}
		IntRectangle bounds =insets.getInsideBounds(target.getSize ().getBounds ());
		GridList list =GridCellHolder(target ).getList ();
		if(list.isTracksWidth() || list.isTracksHeight()){
			if(list.isTracksHeight()){
				nrows = Math.floor((bounds.height+vgap)/(tileHeight+vgap));
				ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
			}else{
				ncols = Math.floor((bounds.width+hgap)/(tileWidth+hgap));
				nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
			}
		}else{
			if(nrows > 0){
				ncols = Math.floor(((ncomponents + nrows) - 1) / nrows);
			}else{
				nrows = Math.floor(((ncomponents + ncols) - 1) / ncols);
			}
		}
		int w =getTileWidth ();
		int h =getTileHeight ();
		int x =insets.left ;
		int y =insets.top ;
		for(int r =0;r <nrows ;r ++){
			x = insets.left;
			for(int c =0;c <ncols ;c ++){
				int i =((r *ncols )+c );
				if (i < ncomponents){
					target.getComponent(i).setBounds(new IntRectangle(x, y, w, h));
					list.getCellByIndex(i).setGridListCellStatus(list, list.isSelectedIndex(i), i);
				}
				x += (w + hgap);
			}
			y += (h + vgap);
		}
	}
}


