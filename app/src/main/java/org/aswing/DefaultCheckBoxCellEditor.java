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


import org.aswing.AbstractCellEditor;
import org.aswing.Component;
import org.aswing.JCheckBox;

/**
 * @author iiley
 */
public class DefaultCheckBoxCellEditor extends AbstractCellEditor{

	protected JCheckBox checkBox ;

	public  DefaultCheckBoxCellEditor (){
		super();
		setClickCountToStart(1);
	}

	public JCheckBox  getCheckBox (){
		if(checkBox == null){
			checkBox = new JCheckBox();
		}
		return checkBox;
	}

 	 public Component  getEditorComponent (){
 		return getCheckBox();
 	}

	 public Object getCellEditorValue () {
		return getCheckBox().isSelected();
	}

    /**
     * Sets the value of this cell.
     * @param value the new value of this cell
     */
	 protected void  setCellEditorValue (Object value){
		boolean selected =false ;
		if(value == true){
			selected = true;
		}
		if(value is String){
			String va =(String)value;
			if(va.toLowerCase() == "true"){
				selected = true;
			}
		}
		getCheckBox().setSelected(selected);
	}

	public String  toString (){
		return "DefaultCheckBoxCellEditor[]";
	}
}


