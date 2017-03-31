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
import org.aswing.FocusManager;
import org.aswing.JComboBox;
import org.aswing.event.AWEvent;

/**
 * The default editor for table and tree cells, use a combobox.
 * <p>
 * @author iiley
 */
public class DefaultComboBoxCellEditor extends AbstractCellEditor{

	protected JComboBox comboBox ;

	public  DefaultComboBoxCellEditor (){
		super();
		setClickCountToStart(1);
	}

	public JComboBox  getComboBox (){
		if(comboBox == null){
			comboBox = new JComboBox();
		}
		return comboBox;
	}

 	 public Component  getEditorComponent (){
 		return getComboBox();
 	}

	 public Object getCellEditorValue () {
		return getComboBox().getSelectedItem();
	}

    /**
     * Sets the value of this cell.
     * @param value the new value of this cell
     */
	 protected void  setCellEditorValue (Object value){
		getComboBox().setSelectedItem(value);
	}

	public String  toString (){
		return "DefaultComboBoxCellEditor[]";
	}
}


