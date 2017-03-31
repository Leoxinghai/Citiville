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


import flash.events.MouseEvent;

/**
 * The list cell for combobox drop down list.
 * @author iiley
 */
public class DefaultComboBoxListCell extends DefaultListCell{

	protected ASColor rolloverBackground ;
	protected ASColor rolloverForeground ;
	protected ASColor realBackground ;
	protected ASColor realForeground ;
		
	public  DefaultComboBoxListCell (){
		super();
	}
	
	 protected void  initJLabel (JLabel jlabel ){
		super.initJLabel(jlabel);
		jlabel.addEventListener(MouseEvent.ROLL_OVER, __labelRollover, false, 0, true);
		jlabel.addEventListener(MouseEvent.ROLL_OUT, __labelRollout, false, 0, true);
	}
	
	 public void  setListCellStatus (JList list ,boolean isSelected ,int index ){
		Component com =getCellComponent ();
		if(isSelected){
			com.setBackground((realBackground = list.getSelectionBackground()));
			com.setForeground((realForeground = list.getSelectionForeground()));
		}else{
			com.setBackground((realBackground = list.getBackground()));
			com.setForeground((realForeground = list.getForeground()));
		}
		com.setFont(list.getFont());
		rolloverBackground = list.getSelectionBackground().changeAlpha(0.8);
		rolloverForeground = list.getSelectionForeground();
	}
	
	private void  __labelRollover (MouseEvent e ){
		if(rolloverBackground){
			getJLabel().setBackground(rolloverBackground);
			getJLabel().setForeground(rolloverForeground);
		}
	}
	
	private void  __labelRollout (MouseEvent e ){
		if(realBackground){
			getJLabel().setBackground(realBackground);
			getJLabel().setForeground(realForeground);
		}
	}	
}


