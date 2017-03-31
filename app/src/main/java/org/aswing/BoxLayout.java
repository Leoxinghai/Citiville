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

	
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.EmptyLayout;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.Insets;


/**
 * A layout manager that allows multiple components to be arranged either vertically or 
 * horizontally. The components will not be wrapped. The width, height, preferredWidth,preferredHeight doesn't affect the way 
 * this layout manager layout the components. Note, it does  not work the same way as Java swing boxlayout does.
 * <p>
 * If this boxlayout is set to X_AXIS, it will layout the child componnets evenly regardless the value of width,height,preferredWidth,preferredHeight.
 * The height of the child components is the same as the parent container.
 * The following picture illustrate this:
 * <img src="../../aswingImg/BoxLayout_X_AXIS.JPG" ></img>
 * </p>
 * <br/>
 * <br/>
 * <p>
 * It works the same way when it is set to Y_AXIS. 
 * </p>
 * <br>
 * Note that this layout will first subtract all of the gaps before it evenly layout the components.
 * If you have a container that is 100 pixel in width with 5 child components, the layout manager is boxlayout, and set to X_AXIS, the gap is 20.
 * You would not see any child componnet in visual. 
 * Because the layout mananager will first subtract 20(gap)*5(component) =100 pixels from the visual area. Then, each component's width would be 0.
 * Pay attention to this when you use this layout manager.
 * </br>
 * @author iiley
 */
public class BoxLayout extends EmptyLayout
	/**
     * Specifies that components should be laid out left to right.
     */
    public static  int X_AXIS =AsWingConstants.HORIZONTAL ;
    
    /**
     * Specifies that components should be laid out top to bottom.
     */
    public static  int Y_AXIS =AsWingConstants.VERTICAL ;
    
    
    private int axis ;
    private int gap ;
    
    /**
     * @param axis (optional)the layout axis, default is X_AXIS
     * @param gap  (optional)the gap between children, default is 0
     * 
     * @see #X_AXIS
     * @see #X_AXIS
     */
    public  BoxLayout (int axis =X_AXIS ,int gap =0){
    	setAxis(axis);
    	setGap(gap);
    }
    
    /**
     * Sets new axis.
     * @param axis new axis default is X_AXIS
     */
    public void  setAxis (int axis =X_AXIS ){
    	this.axis = axis;
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
    	this.gap = gap;
    }
    
    /**
     * Gets gap.
     * @return gap
     */
    public int  getGap (){
    	return gap;	
    }
    
     public IntDimension  preferredLayoutSize (Container target ){
    	return getCommonLayoutSize(target, false);
    }

     public IntDimension  minimumLayoutSize (Container target ){
    	return target.getInsets().getOutsideSize();
    }
    
     public IntDimension  maximumLayoutSize (Container target ){
    	return getCommonLayoutSize(target, true);
    }    
    
    
    private IntDimension  getCommonLayoutSize (Container target ,boolean isMax ){
    	int count =target.getComponentCount ();
    	Insets insets =target.getInsets ();
    	int width =0;
    	int height =0;
    	int amount =0;
    	for(int i =0;i <count ;i ++){
    		Component c =target.getComponent(i );
    		if(c.isVisible()){
	    		IntDimension size =isMax ? c.getMaximumSize() : c.getPreferredSize();
	    		width = Math.max(width, size.width);
	    		height = Math.max(height, size.height);
	    		amount++;
    		}
    	}
    	if(axis == Y_AXIS){
    		height = height*amount;
    		if(amount > 0){
    			height += (amount-1)*gap;
    		}
    	}else{
    		width = width*amount;
    		if(amount > 0){
    			width += (amount-1)*gap;
    		}
    	}
    	IntDimension dim =new IntDimension(width ,height );
    	return insets.getOutsideSize(dim);
    }
    
     public void  layoutContainer (Container target ){
    	int count =target.getComponentCount ();
    	int amount =0;
    	for(int i =0;i <count ;i ++){
    		Component c =target.getComponent(i );
    		if(c.isVisible()){
	    		amount ++;
    		}
    	}
    	IntDimension size =target.getSize ();
    	Insets insets =target.getInsets ();
    	IntRectangle rd =insets.getInsideBounds(size.getBounds ());
    	int ch ;
    	int cw ;
    	if(axis == Y_AXIS){
    		ch = Math.floor((rd.height - (amount-1)*gap)/amount);
    		cw = rd.width;
    	}else{
    		ch = rd.height;
    		cw = Math.floor((rd.width - (amount-1)*gap)/amount);
    	}
    	int x =rd.x ;
    	int y =rd.y ;
    	int xAdd =(axis ==Y_AXIS ? 0 : cw+gap);
    	int yAdd =(axis ==Y_AXIS ? ch+gap : 0);
    	
    	for(int ii =0;ii <count ;ii ++){
    		Component comp =target.getComponent(ii );
    		if(comp.isVisible()){
	    		comp.setBounds(new IntRectangle(x, y, cw, ch));
	    		x += xAdd;
	    		y += yAdd;
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


