
package org.aswing.border;

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


import org.aswing.graphics.Graphics2D;
import org.aswing.Border;
import org.aswing.geom.IntRectangle;
import org.aswing.Component;
import org.aswing.Insets;
import flash.display.DisplayObject;

/**
 * EmptyBorder not draw any graphics, only use to hold a blank space around component.
 * @author iiley
 */
public class EmptyBorder extends DecorateBorder{
	
	private Insets margin ;
	
	public  EmptyBorder (Border interior =null ,Insets margin =null ){
		super(interior);
		if(margin == null){
			this.margin = new Insets();
		}else{
			this.margin = margin.clone();
		}
	}
	
	public void  setTop (int v ){
		margin.top = v;
	}
	public void  setLeft (int v ){
		margin.left = v;
	}
	public void  setBottom (int v ){
		margin.bottom = v;
	}
	public void  setRight (int v ){
		margin.right = v;
	}
	
	public int  getTop (){
		return margin.top;
	}
	public int  getLeft (){
		return margin.left;
	}
	public int  getBottom (){
		return margin.bottom;
	}
	public int  getRight (){
		return margin.right;
	}
	
	public static EmptyBorder  createIndent (int indent ){
		return new EmptyBorder(null, new Insets(indent, indent, indent, indent));
	}
	
	 public void  updateBorderImp (Component com ,Graphics2D g ,IntRectangle bounds ){
	}
	
     public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	return margin.clone();
    }
	
}


