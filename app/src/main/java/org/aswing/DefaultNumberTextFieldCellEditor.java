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


import org.aswing.DefaultTextFieldCellEditor;

/**
 * @author iiley
 */
public class DefaultNumberTextFieldCellEditor extends DefaultTextFieldCellEditor {

	public  DefaultNumberTextFieldCellEditor (){
		super();
	}

	/**
	 *Subclass  this method to implement specified input restrict
	 */
	 protected String  getRestrict (){
		return "-0123456789.E";
	}

	/**
	 *Subclass  this method to implement specified value transform
	 */
	 protected Object transforValueFromText (String text ) {
		return parseFloat(text);
	}
}


