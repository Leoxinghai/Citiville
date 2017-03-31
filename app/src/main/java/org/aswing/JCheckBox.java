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

import org.aswing.plaf.basic.BasicCheckBoxUI;
	
	
/**
 * An implementation of a check box -- an item that can be selected or
 * deselected, and which displays its state to the user. 
 * By convention, any number of check boxes in a group can be selected.
 * @author iiley
 */	
public class JCheckBox extends JToggleButton{
	
	public  JCheckBox (String text ="",Icon icon =null )
	{
		super(text, icon);
		setName("JCheckBox");
	}
    
	 public String  getUIClassID (){
		return "CheckBoxUI";
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicCheckBoxUI;
    }
	
}


