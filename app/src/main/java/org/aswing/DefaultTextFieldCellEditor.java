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
import org.aswing.JTextField;

/**
 * The default editor for table and tree cells, use a textfield.
 * <p>
 * @author iiley
 */
public class DefaultTextFieldCellEditor extends AbstractCellEditor{

	protected JTextField textField ;

	public  DefaultTextFieldCellEditor (){
		super();
		setClickCountToStart(2);
	}

	public JTextField  getTextField (){
		if(textField == null){
			textField = new JTextField();
			//textField.setBorder(null);
			textField.setRestrict(getRestrict());
		}
		return textField;
	}

	/**
	 *Subclass  this method to implement specified input restrict
	 */
	protected String  getRestrict (){
		return null;
	}

	/**
	 *Subclass  this method to implement specified value transform
	 */
	protected Object transforValueFromText (String text ) {
		return text;
	}

 	 public Component  getEditorComponent (){
 		return getTextField();
 	}

	 public Object getCellEditorValue () {
		return transforValueFromText(getTextField().getText());
	}

   /**
    * Sets the value of this cell.
    * @param value the new value of this cell
    */
	 protected void  setCellEditorValue (Object value){
		getTextField().setText(value+"");
		getTextField().selectAll();
	}

	public String  toString (){
		return "DefaultTextFieldCellEditor[]";
	}
}


