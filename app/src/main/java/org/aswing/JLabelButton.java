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


import org.aswing.plaf.basic.BasicLabelButtonUI;

/**
 * A button that performances like a hyper link text.
 * @author iiley
 */
public class JLabelButton extends AbstractButton{
	
	protected ASColor rolloverColor ;
	protected ASColor pressedColor ;
	
    /**
     * Creates a label button.
     * @param text the text.
     * @param icon the icon.
     * @param horizontalAlignment the horizontal alignment, default is <code>CENTER</code>
     */	
	public  JLabelButton (String text ="",Icon icon =null ,int horizontalAlignment =0){
		super(text, icon);
		setName("JLabelButton");
    	setModel(new DefaultButtonModel());
    	setHorizontalAlignment(horizontalAlignment);
	}
	
     public void  updateUI (){
    	setUI(UIManager.getUI(this));
    }
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicLabelButtonUI;
    }
    
	 public String  getUIClassID (){
		return "LabelButtonUI";
	}
	
	/**
	 * Sets the color for text rollover state.
	 * @param c the color.
	 */
	public void  setRollOverColor (ASColor c ){
		if(c != rolloverColor){
			rolloverColor = c;
			repaint();
		}
	}
	
	/**
	 * Gets the color for text rollover state.
	 * @param c the color.
	 */	
	public ASColor  getRollOverColor (){
		return rolloverColor;
	}	
	
	/**
	 * Sets the color for text pressed/selected state.
	 * @param c the color.
	 */	
	public void  setPressedColor (ASColor c ){
		if(c != pressedColor){
			pressedColor = c;
			repaint();
		}
	}
	
	/**
	 * Gets the color for text pressed/selected state.
	 * @param c the color.
	 */		
	public ASColor  getPressedColor (){
		return pressedColor;
	}	
}


