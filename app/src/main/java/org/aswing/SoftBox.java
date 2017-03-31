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


/**
 * A <code>JPanel</code> with <code>SoftBoxLayout</code>.
 * 
 * @author iiley
 */
public class SoftBox extends JPanel {
	
	/**
	 * SoftBox(axis:int, gap:int, align:int)<br>
	 * SoftBox(axis:int, gap:int)<br> default align to LEFT.
	 * SoftBox(axis:int) default gap to 0.
	 * Creates a panel with a SoftBoxLayout.
	 * @param axis the axis of layout.
	 *  {@link org.aswing.SoftBoxLayout#X_AXIS} or {@link org.aswing.SoftBoxLayout#Y_AXIS}
     * @param gap (optional)the gap between each component, default 0
     * @param align (optional)the alignment value, default is LEFT
	 * @see org.aswing.SoftBoxLayout
	 */
	public  SoftBox (int axis ,int gap =0,int align =AsWingConstants .LEFT ){
		super();
		setName("SoftBox");
		setLayout(new SoftBoxLayout(axis, gap, align));
	}
	
	/** Sets new axis for the default SoftBoxLayout.
	 * @param axis the new axis
	 */
	public void  setAxis (int axis ){
		SoftBoxLayout(getLayout()).setAxis(axis);
	}

	/**
	 * Gets current axis of the default SoftBoxLayout.
	 * @return axis 
	 */
	public int  getAxis (){
		return SoftBoxLayout(getLayout()).getAxis();
	}

	/**
	 * Sets new gap for the default SoftBoxLayout.
	 * @param gap the new gap
	 */
	public void  setGap (int gap ){
		SoftBoxLayout(getLayout()).setGap(gap);
	}

	/**
	 * Gets current gap of the default SoftBoxLayout.
	 * @return gap 
	 */
	public int  getGap (){
		return SoftBoxLayout(getLayout()).getGap();
	}
	
	/**
	 * Sets new align for the default SoftBoxLayout.
	 * @param align the new align
	 */
	public void  setAlign (int align ){
		SoftBoxLayout(getLayout()).setAlign(align);
	}

	/**
	 * Gets current align of the default SoftBoxLayout.
	 * @return align 
	 */
	public int  getAlign (){
		return SoftBoxLayout(getLayout()).getAlign();
	}	
	
	/**
	 * Creates and return a Horizontal SoftBox.
     * @param gap (optional)the gap between each component, default 0
     * @param align (optional)the alignment value, default is LEFT
     * @return a horizontal SoftBox.
	 */
	public static SoftBox  createHorizontalBox (int gap =0,int align =AsWingConstants .LEFT ){
		return new SoftBox(SoftBoxLayout.X_AXIS, gap, align);
	}
	
	/**
	 * Creates and return a Vertical SoftBox.
     * @param gap the gap between each component, default 0
     * @param align (optional)the alignment value, default is LEFT
     * @return a vertical SoftBox.
	 */
	public static SoftBox  createVerticalBox (int gap =0,int align =AsWingConstants .LEFT ){
		return new SoftBox(SoftBoxLayout.Y_AXIS, gap, align);
	}
	
	/**
	 * @see org.aswing.JSpacer#createHorizontalGlue
	 */
	public static Component  createHorizontalGlue (int width =4){
		return JSpacer.createHorizontalSpacer(width);
	}
	
	/**
	 * @see org.aswing.JSpacer#createVerticalGlue
	 */
	public static Component  createVerticalGlue (int height =4){
		return JSpacer.createVerticalSpacer(height);
	}

}


