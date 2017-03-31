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


import org.aswing.ASColor;
import org.aswing.Component;
import org.aswing.JLabel;

/**
 * The default grid list cell render value.toString() as texts.
 *
 */
public class DefaultGridCell extends JLabel implements GridListCell{

	protected Object value;

	public  DefaultGridCell (){
		super();
		setOpaque(true);
		setBackground(ASColor.WHITE);
	}

	public void  setCellValue (Object value){
		this.value = value;
		setText(value+"");
	}

	public Object getCellValue () {
		return value;
	}

	public Component  getCellComponent (){
		return this;
	}

	public void  setGridListCellStatus (GridList gridList ,boolean selected ,int index ){
		ASColor c =index % 2 == 0 ? ASColor.WHITE : ASColor.GRAY;
		if(selected){
			c = ASColor.BLUE;
		}
		setBackground(c);
	}

}


