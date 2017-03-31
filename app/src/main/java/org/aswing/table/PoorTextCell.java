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


import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.plaf.ComponentUI;
import org.aswing.graphics.*;
import flash.text.*;

/**
 * A poor table cell to render text faster.
 * @author iiley
 */
public class PoorTextCell extends Component implements TableCell{

	protected TextField textField ;
	protected String text ;
	protected Object cellValue;

	public  PoorTextCell (){
		super();
		setOpaque(true);
		textField = new TextField();
		textField.autoSize = TextFieldAutoSize.LEFT;
		textField.selectable = false;
		textField.mouseEnabled = false;
		setFontValidated(false);
		addChild(textField);
	}

	 protected void  paint (IntRectangle b ){
		String t =text ==null ? "" : text;
		if(textField.text !== t){
			textField.text = t;
		}
		if(!isFontValidated()){
			AsWingUtils.applyTextFont(textField, getFont());
			setFontValidated(true);
		}
		AsWingUtils.applyTextColor(textField, getForeground());
		textField.x = b.x;
		textField.y = b.y + (b.height-textField.height)/2;
		if(isOpaque()){
			graphics.clear();
			Graphics2D g =new Graphics2D(graphics );
			g.fillRectangle(new SolidBrush(getBackground()), b.x, b.y, b.width, b.height);
		}
	}

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

	public void  setText (String text ){
		if(text != this.text){
			this.text = text;
			repaint();
		}
	}

	public String  getText (){
		return text;
	}

	//------------------------------------------------------------------------------------------------

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

	public void  setCellValue (Object value){
		cellValue = value;
		setText(value + "");
	}

	public Object getCellValue () {
		return cellValue;
	}

	public Component  getCellComponent (){
		return this;
	}

	 public String  toString (){
		return "PoorTextCell.get(component:" + super.toString() + ")\n";
	}
}


