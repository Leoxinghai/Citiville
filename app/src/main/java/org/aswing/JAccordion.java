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


import org.aswing.plaf.ComponentUI;
import org.aswing.plaf.basic.BasicAccordionUI;

/**
 * Accordion Container.
 * @author iiley
 */
public class JAccordion extends AbstractTabbedPane{
	
    /**
     * Create an accordion.
     */
	public  JAccordion (){
		super();
		setName("JAccordion");
		
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicAccordionUI;
    }
	
	 public String  getUIClassID (){
		return "AccordionUI";
	}
	
	/**
	 * Generally you should not set layout to JAccordion.
	 * @param layout layoutManager for JAccordion
	 * @throws ArgumentError when you set a non-AccordionUI layout to JAccordion.
	 */
	 public void  setLayout (LayoutManager layout ){
		if(layout is ComponentUI){
			super.setLayout(layout);
		}else{
			throw ArgumentError("Cannot set non-AccordionUI layout to JAccordion!");
		}
	}
}


