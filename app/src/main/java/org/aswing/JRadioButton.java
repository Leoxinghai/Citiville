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

import org.aswing.plaf.basic.BasicRadioButtonUI;
	
	
/**
 * An implementation of a radio button -- an item that can be selected or
 * deselected, and which displays its state to the user.
 * Used with a {@link ButtonGroup} object to create a group of buttons
 * in which only one button at a time can be selected. (Create a ButtonGroup
 * object and use its <code>append</code> method to include the JRadioButton objects
 * in the group.)
 * <blockquote>
 * <strong>Note:</strong>
 * The ButtonGroup object is a logical grouping -- not a physical grouping.
 * Tocreate a button panel, you should still create a {@link JPanel} or similar
 * container-object and add a {@link org.aswing.border.Border} to it to set it off from surrounding
 * components.
 * </blockquote>
 * @author iiley
 */	
public class JRadioButton extends JToggleButton{
	
	public  JRadioButton (String text ="",Icon icon =null )
	{
		super(text, icon);
		setName("JRadioButton");
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicRadioButtonUI;
    }
    
	 public String  getUIClassID (){
		return "RadioButtonUI";
	}
	
}


