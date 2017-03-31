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
public class Resizable3Imp1 implements Resizable3{
	
	private TableColumnModel cm ;
	private boolean inverse ;
		
	public  Resizable3Imp1 (TableColumnModel cm ,boolean inverse ){
		this.cm = cm;
		this.inverse = inverse;
	}
	
    public int  getElementCount (){
    	return cm.getColumnCount(); 
    }
    
    public int  getLowerBoundAt (int i ){
    	return cm.getColumn(i).getMinWidth(); 
    }
    
    public int  getUpperBoundAt (int i ){
    	return cm.getColumn(i).getMaxWidth(); 
    }
    
    public int  getMidPointAt (int i ){
        if (!inverse) {
	    	return cm.getColumn(i).getPreferredWidth();
        }else {
	    	return cm.getColumn(i).getWidth();
        }
    }
    
    public void  setSizeAt (int s ,int i ){
        if (!inverse) {
			cm.getColumn(i).setWidth(s);
        }else {
			cm.getColumn(i).setPreferredWidth(s);
        }
    }
}


