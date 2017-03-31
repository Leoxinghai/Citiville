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


import org.aswing.Component;
import org.aswing.geom.*;
import org.aswing.JLabel;
import org.aswing.JTable;

/**
 * Default table cell to render text
 * @author iiley
 */
public class DefaultTextCell extends JLabel implements TableCell{

	protected Object value;

	public  DefaultTextCell (){
		super();
		setHorizontalAlignment(LEFT);
		setOpaque(true);
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  setComBounds (IntRectangle b ){
		readyToPaint = true;
		if(!b.equals(bounds)){
			if(b.width != bounds.width || b.height != bounds.height){
				repaint();
			}
			bounds.setRect(b);
			locate();
			valid = false;
		}
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  invalidate (){
		valid = false;
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  revalidate (){
		valid = false;
	}

	//**********************************************************
	//				  Implementing TableCell
	//**********************************************************
	public void  setCellValue (Object value){
		this.value = value;
		setText(value + "");
	}

	public Object getCellValue () {
		return value;
	}

	public void  setTableCellStatus (JTable table ,boolean isSelected ,int row ,int column ){
		if(isSelected){
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		}else{
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
		setFont(table.getFont());
	}

	public Component  getCellComponent (){
		return this;
	}

	 public String  toString (){
		return "TextCell.get(label:" + super.toString() + ")\n";
	}
}


