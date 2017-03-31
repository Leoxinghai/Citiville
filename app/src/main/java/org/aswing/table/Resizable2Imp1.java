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
public class Resizable2Imp1 implements Resizable2{
	
	private Resizable3 r ;
	private boolean flag ;
	
	public  Resizable2Imp1 (Resizable3 r ,boolean flag ){
		this.r = r;
		this.flag = flag;
	}
	
    public int  getElementCount (){
    	return r.getElementCount(); 
    }
    
    public int  getLowerBoundAt (int i ){
    	if(flag){
    		return r.getLowerBoundAt(i);
    	}else{
    		return r.getMidPointAt(i);
    	}
    }
    
    public int  getUpperBoundAt (int i ){
    	if(flag){
    		return r.getMidPointAt(i);
    	}else{
    		return r.getUpperBoundAt(i);
    	} 
    }
    
    public void  setSizeAt (int newSize ,int i ){
    	r.setSizeAt(newSize, i); 
    }
}


