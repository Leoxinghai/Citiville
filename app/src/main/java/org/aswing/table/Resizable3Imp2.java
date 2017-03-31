/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table;

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
 * @author iiley
 */
public class Resizable3Imp2 implements Resizable3{
	
	private TableColumnModel cm ;
	private int start ;
	private int end ;
		
	public  Resizable3Imp2 (TableColumnModel cm ,int start ,int end ){
		this.cm = cm;
		this.start = start;
		this.end = end;
	}
	
    public int  getElementCount (){
    	return end-start;
    }
    
    public int  getLowerBoundAt (int i ){
    	return cm.getColumn(i+start).getMinWidth(); 
    }
    
    public int  getUpperBoundAt (int i ){
    	return cm.getColumn(i+start).getMaxWidth(); 
    }
    
    public int  getMidPointAt (int i ){
    	return cm.getColumn(i+start).getWidth(); 
    }
    
    public void  setSizeAt (int s ,int i ){
    	cm.getColumn(i+start).setWidth(s); 
    }
}


