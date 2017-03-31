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


import flash.display.SimpleButton;

import org.aswing.plaf.basic.BasicButtonUI;

/**
 * An implementation of a "push" button.
 * @author iiley
 */
public class JButton extends AbstractButton {
	public  JButton (String text ="",Icon icon =null ){
		super(text, icon);
		setName("JButton");
    	setModel(new DefaultButtonModel());
	}
	
	/**
	 * Returns whether this button is the default button of its root pane or not.
	 * @return true if this button is the default button of its root pane, false otherwise.
	 */
	public boolean  isDefaultButton (){
		JRootPane rootPane =getRootPaneAncestor ();
		if(rootPane != null){
			return rootPane.getDefaultButton() == this;
		}
		return false;
	}
	
	/**
	 * Wrap a SimpleButton to be this button's representation.
	 * @param btn the SimpleButton to be wrap.
	 * @return the button self
	 */
	 public AbstractButton  wrapSimpleButton (SimpleButton btn ){
		mouseChildren = true;
		drawTransparentTrigger = false;
		setShiftOffset(0);
		setIcon(new SimpleButtonIcon(btn));
		setBorder(null);
		setMargin(new Insets());
		setBackgroundDecorator(null);
		setOpaque(false);
		setHorizontalTextPosition(AsWingConstants.CENTER);
		setVerticalTextPosition(AsWingConstants.CENTER);
		return this;
	}
	
     public void  updateUI (){
    	setUI(UIManager.getUI(this));
    }
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicButtonUI;
    }
    
	 public String  getUIClassID (){
		return "ButtonUI";
	}
	

}


