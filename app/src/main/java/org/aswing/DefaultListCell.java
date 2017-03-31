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


import org.aswing.event.*;
import org.aswing.geom.IntPoint;

/**
 * Default list cell, render item value.toString() text.
 * @author iiley
 */
public class DefaultListCell extends AbstractListCell{

	private JLabel jlabel ;

	private static JSharedToolTip sharedToolTip ;

	public  DefaultListCell (){
		super();
		if(sharedToolTip == null){
			sharedToolTip = new JSharedToolTip();
			sharedToolTip.setOffsetsRelatedToMouse(false);
			sharedToolTip.setOffsets(new IntPoint(0, 0));
		}
	}

	 public void  setCellValue (Object value){
		super.setCellValue(value);
		getJLabel().setText(getStringValue(value));
		__resized(null);
	}

	/**
	 * Override this if you need other value->string translator
	 */
	protected String  getStringValue (Object value){
		return value + "";
	}

	 public Component  getCellComponent (){
		return getJLabel();
	}

	protected JLabel  getJLabel (){
		if(jlabel == null){
			jlabel = new JLabel();
			initJLabel(jlabel);
		}
		return jlabel;
	}

	protected void  initJLabel (JLabel jlabel ){
		jlabel.setHorizontalAlignment(JLabel.LEFT);
		jlabel.setOpaque(true);
		jlabel.setFocusable(false);
		jlabel.addEventListener(ResizedEvent.RESIZED, __resized);
	}

	protected void  __resized (ResizedEvent e ){
		if(getJLabel().getWidth() < getJLabel().getPreferredWidth()){
			getJLabel().setToolTipText(value.toString());
			JSharedToolTip.getSharedInstance().unregisterComponent(getJLabel());
			sharedToolTip.registerComponent(getJLabel());
		}else{
			getJLabel().setToolTipText(null);
			sharedToolTip.unregisterComponent(getJLabel());
		}
	}
}


