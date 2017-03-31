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


import org.aswing.error.ImpMissError;

/**
 * Abstract list cell.
 * @author iiley
 */
public class AbstractListCell implements ListCell{

	protected Object value;

	public  AbstractListCell (){
	}

	public void  setListCellStatus (JList list ,boolean isSelected ,int index ){
		Component com =getCellComponent ();
		if(isSelected){
			com.setBackground(list.getSelectionBackground());
			com.setForeground(list.getSelectionForeground());
		}else{
			com.setBackground(list.getBackground());
			com.setForeground(list.getForeground());
		}
		com.setFont(list.getFont());
	}

	public void  setCellValue (Object value){
		this.value = value;
	}

	public Object getCellValue () {
		return value;
	}

	/**
	 *Subclass should  this method
	 */
	public Component  getCellComponent (){
		throw new ImpMissError();
		return null;
	}
}


