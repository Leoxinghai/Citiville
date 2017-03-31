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


import flash.text.*;

/**
 * EmptyFont is a font that will not change text field's format.
 */
public class EmptyFont extends ASFont{
	
	public  EmptyFont (){
		super();
	}
	
	/**
	 * Do nothing here.
	 * @param textField the text filed to be applied font.
	 * @param beginIndex The zero-based index position specifying the first character of the desired range of text. 
	 * @param endIndex The zero-based index position specifying the last character of the desired range of text. 
	 */
	 public void  apply (TextField textField ,int beginIndex =-1,int endIndex =-1){
	}
	
	/**
	 * Returns <code>new TextFormat()</code>.
	 * @return <code>new TextFormat()</code>.
	 */
	 public TextFormat  getTextFormat (){
		return new TextFormat();
	}	
}


