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


import org.aswing.AsWingConstants;
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.EmptyLayout;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.Insets;

/**
 * The SoftBoxLayout will layout the child components using their preferredWidth or preferredHeight instead of width or height.
 * It ignores the preferredWidth when set to Y_AXIS, ignores the preferredHeight when set to X_AXIS.
 * <p>
 * When set to X_AXIS, all of the child components share the same height from the container and use their own preferredWidth
 * When set to Y_AXIS, all of the child components share the same width  from the container and use their own preferredHeight
 * </p>
 * <p></p>
 * <p></p>
 * <p>	
 *   The picture below shows that when set X_AXIS,all of the child component share the same height no matter what value you set for the componnet.
 *   It ignores the width and height property you set for the child component.<br/>
 * 	 <strong>Note:</strong> The align is set to LEFT, so the children are ajusted to the left side,
 *    In the right,there are still free space.
 *  <br/>
 * 	<img src="../../aswingImg/SoftBoxLayout_X_AXIS.JPG" ></img>
 * </p>
 * <br/>
 * <br/>
 *  <p>	
 *   The picture below shows that when set Y_AXIS,all of the child component share the same width no matter what value you set for the componnet.
 *   It ignores the width and height property you set for the child component.<br/>
 * 	 <strong>Note:</strong> The align is set to <strong>RIGHT</strong>, when axis set to Y_AXIS and align set to right,  the children are ajusted to the bottom,
 *    at top ,there are still free space.
 *  <br/>
 * 	<img src="../../aswingImg/SoftBoxLayout_Y_AXIS.JPG" ></img>
 * </p>
 * <br/>
 * <br/>
 * <p>
 *   <strong>Note</strong> the container itself who applied SoftBoxLayout is not affected by the X_AXIS or Y_AXIS you set for SoftBoxLayout<br/>
 *   The container's size will be determined by its parents' layout manager.  
 * </p>	
 *   
 * @see BoxLayout
 * @author iiley
 */
public class SoftBoxLayout extends EmptyLayout{
	/**
     * Specifies that components should be laid out left to right.
     */
    public static  int X_AXIS =AsWingConstants.HORIZONTAL ;
    
    /**
     * Specifies that components should be laid out top to bottom.
     */
    public static  int Y_AXIS =AsWingConstants.VERTICAL ;
    
    
    /**
     * This value indicates that each row of components
     * should be left-justified(X_AXIS)/top-justified(Y_AXIS).
     */
    public static  int LEFT =AsWingConstants.LEFT ;

    /**
     * This value indicates that each row of components
     * should be centered.
     */
    public static  int CENTER =AsWingConstants.CENTER ;

    /**
     * This value indicates that each row of components
     * should be right-justified(X_AXIS)/bottom-justified(Y_AXIS).
     */
    public static  int RIGHT =AsWingConstants.RIGHT ;
    
    /**
     * This value indicates that each row of components
     * should be left-justified(X_AXIS)/top-justified(Y_AXIS).
     */
    public static  int TOP =AsWingConstants.TOP ;

    /**
     * This value indicates that each row of components
     * should be right-justified(X_AXIS)/bottom-justified(Y_AXIS).
     */
    public static  int BOTTOM =AsWingConstants.BOTTOM ;
    
    
    private int axis ;
    private int gap ;
    private int align ;
    
    /**
     * @param axis the layout axis, default X_AXIS
     * @param gap (optional)the gap between each component, default 0
     * @param align (optional)the alignment value, default is LEFT
     * @see #X_AXIS
     * @see #Y_AXIS
     */
    public  SoftBoxLayout (int axis =X_AXIS ,int gap =0,int align =AsWingConstants .LEFT ){
    	setAxis(axis);
    	setGap(gap);
    	setAlign(align);
    }
    	
    /**
     * Sets new axis. Must be one of:
     * <ul>
     *  <li>X_AXIS
     *  <li>Y_AXIS
     * </ul> Default is X_AXIS.
     * @param axis new axis
     */
    public void  setAxis (int axis =X_AXIS ){
    	this.axis = axis ;
    }
    
    /**
     * Gets axis.
     * @return axis
     */
    public int  getAxis (){
    	return axis;	
    }
    
    /**
     * Sets new gap.
     * @param get new gap
     */	
    public void  setGap (int gap =0){
    	this.gap = gap ;
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
    public void  setAlign (int align =AsWingConstants .LEFT ){
    	this.align =  align;
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
    	Insets insets =target.getInsets ();
    	int width =0;
    	int height =0;
    	int wTotal =0;
    	int hTotal =0;
    	for(int i =0;i <count ;i ++){
    		Component c =target.getComponent(i );
    		if(c.isVisible()){
	    		IntDimension size =c.getPreferredSize ();
	    		width = Math.max(width, size.width);
	    		height = Math.max(height, size.height);
	    		int g =i >0? gap : 0;
	    		wTotal += (size.width + g);
	    		hTotal += (size.height + g);
    		}
    	}
    	if(axis == Y_AXIS){
    		height = hTotal;
    	}else{
    		width = wTotal;
    	}
    	
    	IntDimension dim =new IntDimension(width ,height );
    	return insets.getOutsideSize(dim);
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
    	int count =target.getComponentCount ();
    	IntDimension size =target.getSize ();
    	Insets insets =target.getInsets ();
    	IntRectangle rd =insets.getInsideBounds(size.getBounds ());
    	int ch =rd.height ;
    	int cw =rd.width ;
    	int x =rd.x ;
    	int y =rd.y ;
    	if(align == RIGHT || align == BOTTOM){
    		if(axis == Y_AXIS){
    			y = y + ch;
    		}else{
    			x = x + cw;
    		}
	    	for(int i =count -1;i >=0;i --){
	    		Component c =target.getComponent(i );
	    		if(c.isVisible()){
		    		IntDimension ps =c.getPreferredSize ();
		    		if(axis == Y_AXIS){
		    			y -= ps.height;
		    			c.setBounds(new IntRectangle(x, y, cw, ps.height));
		    			y -= gap;
		    		}else{
		    			x -= ps.width;
		    			c.setBounds(new IntRectangle(x, y, ps.width, ch));
		    			x -= gap;
		    		}
	    		}
	    	}
    		
    	}else{//left or top or center
	    	if(align == CENTER){
	    		IntDimension prefferedSize =insets.getInsideSize(target.getPreferredSize ());
	    		if(axis == Y_AXIS){
	    			y = Math.round(y + (ch - prefferedSize.height)/2);
	    		}else{
	    			x = Math.round(x + (cw - prefferedSize.width)/2);
	    		}
	    	}
	    	for(int ii =0;ii <count ;ii ++){
	    		Component comp =target.getComponent(ii );
	    		if(comp.isVisible()){
		    		IntDimension cps =comp.getPreferredSize ();
		    		if(axis == Y_AXIS){
		    			comp.setBounds(new IntRectangle(x, y, cw, cps.height));
		    			y += (cps.height + gap);
		    		}else{
		    			comp.setBounds(new IntRectangle(x, y, cps.width, ch));
		    			x += (cps.width + gap);
		    		}
	    		}
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


