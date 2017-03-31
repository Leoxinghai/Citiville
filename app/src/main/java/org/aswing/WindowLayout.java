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
 * Layout for JWindow and JFrame.
 * @author iiley
 */
public class WindowLayout extends EmptyLayout{
	
	private Component titleBar ;

	private Component contentPane ;

    /**
     * The title bar layout constraint.
     */
    public static  String TITLE ="Title";

    /**
     * The content pane layout constraint.
     */
    public static  String CONTENT ="Content";
    
	public  WindowLayout (){
		super();
	}
	
	/**
	 * @param comp the child to add to the layout
	 * @param constraints the constraints indicate what position the child will be added.
	 * @throws ArgumentError when the constraints is not <code>TITLE</code> either <code>CONTENT</code>.
	 */
     public void  addLayoutComponent (Component comp ,Object constraints ){
	    if(constraints == TITLE){
	    	titleBar = comp;
	    }else if(constraints == CONTENT){
	    	contentPane = comp;
	    }else{
	    	throw new ArgumentError("ERROR When add component to JWindow/JFrame, constraints must be TITLE or CONTENT : " + constraints);
	    }
    }
    
    public Component  getTitleBar (){
    	return titleBar;
    }
    
    public Component  getContentPane (){
    	return contentPane;
    }
    
     public void  removeLayoutComponent (Component comp ){
     	if(comp == titleBar){
     		titleBar = null;
     	}else if(comp == contentPane){
     		contentPane = null;
     	}
     }

	 public IntDimension  minimumLayoutSize (Container target ){
		Insets insets =target.getInsets ();
		IntDimension size =insets.getOutsideSize ();
		if(titleBar != null){
			size.increaseSize(titleBar.getMinimumSize());
		}
		return size;
	}
	
	 public IntDimension  preferredLayoutSize (Container target ){
		Insets insets =target.getInsets ();
		IntDimension size =insets.getOutsideSize ();
		IntDimension titleBarSize ,contentSize IntDimension ;
		if(titleBar != null){
			titleBarSize = titleBar.getPreferredSize();
		}else{
			titleBarSize = new IntDimension(0, 0);
		}
		if(contentPane != null){
			contentSize = contentPane.getPreferredSize();
		}else{
			contentSize = new IntDimension(0, 0);
		}
		size.increaseSize(new IntDimension(Math.max(titleBarSize.width, contentSize.width), titleBarSize.height + contentSize.height));
		return size;
	}
	
	 public void  layoutContainer (Container target ){	
    	IntDimension td =target.getSize ();
		Insets insets =target.getInsets ();
		IntRectangle r =insets.getInsideBounds(td.getBounds ());
		
		IntDimension d ;
		if(titleBar != null){
			d = titleBar.getPreferredSize();
			titleBar.setBounds(new IntRectangle(r.x, r.y, r.width, d.height));
			r.y += d.height;
			r.height -= d.height;
		}
		if(contentPane != null){
			contentPane.setBounds(new IntRectangle(r.x, r.y, r.width, r.height));
		}
	}
	
    public String  toString (){
		return "WindowLayout[]";
    }
	
}


