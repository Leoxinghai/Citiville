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

/**
 * An Insets object is a representation of the borders of a container. 
 * It specifies the space that a container must leave at each of its edges. 
 * The space can be a border, a blank space, or a title. 
 * 
 * @author iiley
 */
public class Insets{
	
	/**
	 * Creates new <code>Insets</code> instance with identic edges.
	 * 
	 * @param edge the edge value for insets.
	 * @return new insets instance.
	 */
	public static Insets  createIdentic (int edge ){
		return new Insets(edge, edge, edge, edge);	
	}
	
	public int bottom =0;
	public int top =0;
	public int left =0;
	public int right =0;
	
	/**
	 * Creats an insets.
	 */
	public  Insets (int top =0,int left =0,int bottom =0,int right =0){
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}
	
	/**
	 * This insets add specified insets and return itself.
	 */
	public Insets  addInsets (Insets insets ){
		this.top += insets.top;
		this.left += insets.left;
		this.bottom += insets.bottom;
		this.right += insets.right;
		return this;
	}
	
	public int  getMarginWidth (){
		return left + right;
	}
	
	public int  getMarginHeight (){
		return top + bottom;
	}
	
	public IntRectangle  getInsideBounds (IntRectangle bounds ){
		IntRectangle r =bounds.clone ();
		r.x += left;
		r.y += top;
		r.width -= (left + right);
		r.height -= (top + bottom);
		return r;
	}
	
	public IntRectangle  getOutsideBounds (IntRectangle bounds ){
		IntRectangle r =bounds.clone ();
		r.x -= left;
		r.y -= top;
		r.width += (left + right);
		r.height += (top + bottom);
		return r;
	}
	
	public IntDimension  getOutsideSize (IntDimension size =null ){
		if(size == null) size = new IntDimension();
		IntDimension s =size.clone ();
		s.width += (left + right);
		s.height += (top + bottom);
		return s;
	}
	
	public IntDimension  getInsideSize (IntDimension size =null ){
		if(size == null) size = new IntDimension();
		IntDimension s =size.clone ();
		s.width -= (left + right);
		s.height -= (top + bottom);
		return s;
	}
	
	public boolean  equals (Object o ){
		Insets i =(Insets)o;
		if(i == null){
			return false;
		}else{
			return i.bottom == bottom && i.left == left && i.right == right && i.top == top;
		}
	}
	
	public Insets  clone (){
		return new Insets(top, left, bottom, right);
	}
	
	public String  toString (){
		return "Insets(top:"+top+", left:"+left+", bottom:"+bottom+", right:"+right+")";
	}

}


