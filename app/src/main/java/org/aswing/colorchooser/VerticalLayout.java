package org.aswing.colorchooser;

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

import org.aswing.Component;
import org.aswing.Container;
import org.aswing.EmptyLayout;
import org.aswing.Insets;
import org.aswing.geom.*;

/**
 * @author iiley
 */
public class VerticalLayout extends EmptyLayout {
	
    /**
     * This value indicates that each row of components
     * should be left-justified.
     */
    public static  int LEFT =0;

    /**
     * This value indicates that each row of components
     * should be centered.
     */
    public static  int CENTER =1;

    /**
     * This value indicates that each row of components
     * should be right-justified.
     */
    public static  int RIGHT =2;	
	
	private int align ;
	private int gap ;
	
	public  VerticalLayout (int align =LEFT ,int gap =0){
		this.align = align;
		this.gap   = gap;
	}
	
    
    /**
     * Sets new gap.
     * @param get new gap
     */	
    public void  setGap (int gap ){
    	this.gap = gap;
    }
    
    /**
     * Gets gap.
     * @return gap
     */
    public int  getGap (){
    	return gap;	
    }
    
    /**
     * Sets new align. Must be one of:
     * <ul>
     *  <li>LEFT
     *  <li>RIGHT
     *  <li>CENTER
     *  <li>TOP
     *  <li>BOTTOM
     * </ul> Default is LEFT.
     * @param align new align
     */
    public void  setAlign (int align ){
    	this.align = align;
    }
    
    /**
     * Returns the align.
     * @return the align
     */
    public int  getAlign (){
    	return align;
    }
   	
	/**
	 * Returns preferredLayoutSize;
	 */
     public IntDimension  preferredLayoutSize (Container target ){
    	int count =target.getComponentCount ();
    	int width =0;
    	int height =0;
    	for(int i =0;i <count ;i ++){
    		Component c =target.getComponent(i );
    		if(c.isVisible()){
	    		IntDimension size =c.getPreferredSize ();
	    		width = Math.max(width, size.width);
	    		int g =i >0? gap : 0;
	    		height += (size.height + g);
    		}
    	}
    	
    	IntDimension dim =new IntDimension(width ,height );
    	return dim;
    }
        
	/**
	 * Returns minimumLayoutSize;
	 */
     public IntDimension  minimumLayoutSize (Container target ){
    	return target.getInsets().getOutsideSize();
    }
    
    /**
     * do nothing
     */
     public void  layoutContainer (Container target ){
    	double count =target.getComponentCount ();
    	IntDimension size =target.getSize ();
    	Insets insets =target.getInsets ();
    	IntRectangle rd =insets.getInsideBounds(size.getBounds ());
    	int cw =rd.width ;
    	int x =rd.x ;
    	int y =rd.y ;
		int right =x +cw ;
    	for(int i =0;i <count ;i ++){
    		Component c =target.getComponent(i );
    		if(c.isVisible()){
	    		IntDimension ps =c.getPreferredSize ();
	    		if(align == RIGHT){
    				c.setBounds(new IntRectangle(right - ps.width, y, ps.width, ps.height));
	    		}else if(align == CENTER){
	    			c.setBounds(new IntRectangle(x + cw/2 - ps.width/2, y, ps.width, ps.height));
	    		}else{
	    			c.setBounds(new IntRectangle(x, y, ps.width, ps.height));
	    		}
    			y += ps.height + gap;
    		}
    	}
    }
    
	/**
	 * return 0.5
	 */
     public double  getLayoutAlignmentX (Container target ){
    	return 0.5;
    }

	/**
	 * return 0.5
	 */
     public double  getLayoutAlignmentY (Container target ){
    	return 0.5;
    }   
}


