package org.aswing.ext;

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


import org.aswing.JTextArea;

/**
 * MutilineLabel performances just like a label that wordwrap and multiline. 
 * It is simulated by a <code>JTextArea</code> with no border no backgorund decorator and background not opaque.
 * By default it is wrodwrap and selectable.
 * @author iiley
 */
public class MultilineLabel extends JTextArea{
	
	public  MultilineLabel (String text ="",int rows =0,int columns =0){
		super(text, rows, columns);
		setWordWrap(true);
		setBorder(null);
		setBackgroundDecorator(null);
		setOpaque(false);
		setEditable(false);
	}
	
}


