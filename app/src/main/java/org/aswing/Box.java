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

import org.aswing.BoxLayout;
import org.aswing.Component;
import org.aswing.JPanel;
import org.aswing.JSpacer;

/**
 * A <code>JPanel</code> with <code>BoxLayout</code>.
 * 
 * @author iiley
 */
public class Box extends JPanel{
	/**
	 * Box(axis:int, gap:int)<br>
	 * Box(axis:int) default gap to 0.
	 * Creates a panel with a BoxLayout.
	 * @param axis (optional)the axis of layout, default is X_AXIS
	 *  {@link org.aswing.BoxLayout#X_AXIS} or {@link org.aswing.BoxLayout#Y_AXIS}
     * @param gap (optional)the gap between each component, default 0
	 * @see org.aswing.SoftBoxLayout
	 */
	public  Box (int axis =0,int gap =0){
		super();
		setName("Box");
		setLayout(new BoxLayout(axis, gap));
	}
	
	/**
	 * Sets new axis for the default BoxLayout.
	 * @param axis the new axis
	 */
	public void  setAxis (int axis ){
		BoxLayout(getLayout()).setAxis(axis);
	}

	/**
	 * Gets current axis of the default BoxLayout.
	 * @return axis 
	 */
	public int  getAxis (){
		return BoxLayout(getLayout()).getAxis();
	}

	/**
	 * Sets new gap for the default BoxLayout.
	 * @param gap the new gap
	 */
	public void  setGap (int gap ){
		BoxLayout(getLayout()).setGap(gap);
	}

	/**
	 * Gets current gap of the default BoxLayout.
	 * @return gap 
	 */
	public int  getGap (){
		return BoxLayout(getLayout()).getGap();
	}
	
	/**
	 * Creates and return a Horizontal Box.
     * @param gap (optional)the gap between each component, default 0
     * @return a horizontal Box.
	 */	
	public static Box  createHorizontalBox (int gap =0){
		return new Box(BoxLayout.X_AXIS, gap);
	}
	
	/**
	 * Creates and return a Vertical Box.
     * @param gap (optional)the gap between each component, default 0
     * @return a vertical Box.
	 */
	public static Box  createVerticalBox (int gap =0){
		return new Box(BoxLayout.Y_AXIS, gap);
	}
	

	/**
	 * Creates a glue that displays its components from left to right.
	 * @see org.aswing.JSpacer#createHorizontalGlue
	 */
	public static Component  createHorizontalGlue (){
		return JSpacer.createHorizontalSpacer(0);
	}
	
	/**
	 * Creates a glue that displays its components from top to bottom.
	 * @see org.aswing.JSpacer#createVerticalGlue
	 */
	public static Component  createVerticalGlue (){
		return JSpacer.createVerticalSpacer(0);
	}
	
}


