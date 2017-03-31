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

	
import org.aswing.plaf.basic.BasicSeparatorUI;
	
/**
 * <code>JSeparator</code> provides a general purpose component for
 * implementing divider lines - most commonly used as a divider
 * between menu items that breaks them up into logical groupings.
 * Instead of using <code>JSeparator</code> directly,
 * you can use the <code>JMenu</code> or <code>JPopupMenu</code>
 * <code>addSeparator</code> method to create and add a separator.
 * <code>JSeparator</code>s may also be used elsewhere in a GUI
 * wherever a visual divider is useful.
 * 
 * @author iiley
 */	
public class JSeparator extends Component implements Orientable{
	
    /** 
     * Horizontal orientation.
     */
    public static  int HORIZONTAL =AsWingConstants.HORIZONTAL ;
    /** 
     * Vertical orientation.
     */
    public static  int VERTICAL =AsWingConstants.VERTICAL ;
	
	private int orientation ;
	
	/**
	 * JSeparator(orientation:Number)<br>
	 * JSeparator() default orientation to HORIZONTAL;
	 * <p>
	 * @param orientation (optional) the orientation.
	 */
	public  JSeparator (int orientation =AsWingConstants .HORIZONTAL ){
		super();
		setName("JSeparator");
		this.orientation = orientation;
		setFocusable(false);
		updateUI();
	}

	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
	 public String  getUIClassID (){
		return "SeparatorUI";
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicSeparatorUI;
    }
	
	public int  getOrientation (){
		return orientation;
	}
	
	public void  setOrientation (int orientation ){
		if (this.orientation != orientation){
			this.orientation = orientation;
			revalidate();
			repaint();
		}
	}

}


