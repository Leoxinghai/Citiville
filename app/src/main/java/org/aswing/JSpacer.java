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


import org.aswing.geom.*;
import org.aswing.plaf.basic.BasicSpacerUI;

/**
 * <code>JSpacer</code> provides basic functionality to create empty spaces between
 * other components.
 * 
 * @author iiley
 * @author Igor Sadovskiy
 */
public class JSpacer extends Component{
	
	/**
	 * JSpacer(prefSize:Dimension)<br>
	 * JSpacer()
	 */
	public  JSpacer (IntDimension prefSize =null ){
		super();
		setPreferredSize(prefSize);
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicSpacerUI;
    }
	
	 public String  getUIClassID (){
		return "SpacerUI";
	}
	
	/**
	 * Creates a Spacer that displays its components from left to right.
	 * @param width (optional)the width of the spacer, default is 4
	 * @return the spacer
	 */
	public static JSpacer  createHorizontalSpacer (int width =4){
		JSpacer glue =new JSpacer ();
		glue.setPreferredSize(new IntDimension(width , 0));
		glue.setMaximumSize(new IntDimension(width , 10000));
		return glue;
	}
	
	/**
	 * Creates a Spacer that displays its components from top to bottom.
	 * @param height (optional)the height of the spacer, default is 4
	 * @return the spacer
	 */
	public static JSpacer  createVerticalSpacer (int height =4){
		JSpacer glue =new JSpacer ();
		glue.setPreferredSize(new IntDimension(0 , height));
		glue.setMaximumSize(new IntDimension(10000 , height));
		return glue;
	}
	
	/**
	 * Creates a solid Spacer with specified preffered width and height.
	 * @param width (optional)the width of the spacer, default is 4
	 * @param height (optional)the height of the spacer, default is 4
	 * @return the spacer
	 */
	public static JSpacer  createSolidSpacer (int width =4,int height =4){
		JSpacer glue =new JSpacer ();
		glue.setPreferredSize(new IntDimension(width , height));
		return glue;
	}
	
}


