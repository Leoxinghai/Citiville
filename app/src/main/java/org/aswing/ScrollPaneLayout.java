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
 * The layout manager used by <code>JScrollPane</code>.  
 * <code>JScrollPaneLayout</code> is
 * responsible for three components: a viewportable's pane, two scrollbars.
 * <p>
 * @see JScrollPane
 * @see Viewportable
 * 
 * @author iiley
 */
public class ScrollPaneLayout extends EmptyLayout{
	
    /**
     * scrollbar are place at top and left
     */
    public static  int TOP_LEFT =3;
    /**
     * scrollbar are place at top and right
     */
    public static  int TOP_RIGHT =2;
	
    /**
     * scrollbar are place at bottom and left
     */
    public static  int BOTTOM_LEFT =1;
    /**
     * scrollbar are place at bottom and right
     */
    public static  int BOTTOM_RIGHT =0;
    	
	private int style ;
	
	
	/**
	 * @param style how to place the scrollbars, default is BOTTOM_RIGHT
	 * @see #TOP_LEFT
	 * @see #TOP_RIGHT
	 * @see #BOTTOM_LEFT
	 * @see #BOTTOM_RIGHT
	 */
	public  ScrollPaneLayout (int style =BOTTOM_RIGHT ){
		this.style = style;
	}
	
     public IntDimension  minimumLayoutSize (Container target ){
    	if(target is JScrollPane){
    		JScrollPane scrollPane =JScrollPane(target );
    		IntDimension size =getScrollBarsSize(scrollPane );
    		Insets i =scrollPane.getInsets ();
    		size = size.increaseSize(i.getOutsideSize());
	    	Viewportable viewport =scrollPane.getViewport ();
	    	if(viewport != null){
	    		i = viewport.getViewportPane().getInsets();
	    		size.increaseSize(i.getOutsideSize());
	    		size.increaseSize(viewport.getViewportPane().getMinimumSize());
	    	}
	    	return size;
    	}else{
    		return super.minimumLayoutSize(target);
    	}
    }
    
    private IntDimension  getScrollBarsSize (JScrollPane scrollPane ){
    	JScrollBar vsb =scrollPane.getVerticalScrollBar ();
    	JScrollBar hsb =scrollPane.getHorizontalScrollBar ();
    	IntDimension size =new IntDimension ();
    	if(vsb != null && scrollPane.getVerticalScrollBarPolicy() == JScrollPane.SCROLLBAR_ALWAYS){
    		size.increaseSize(vsb.getPreferredSize());
    	}
    	if(hsb != null && scrollPane.getHorizontalScrollBarPolicy() == JScrollPane.SCROLLBAR_ALWAYS){
    		size.increaseSize(vsb.getPreferredSize());
    	}
    	return size;
    }
    
	/**
	 * return target.getSize();
	 */
     public IntDimension  preferredLayoutSize (Container target ){
    	if(target is JScrollPane){
	    	JScrollPane scrollPane =JScrollPane(target );
	    	Insets i =scrollPane.getInsets ();
	    	IntDimension size =i.getOutsideSize ();
	    	size.increaseSize(getScrollBarsSize(scrollPane));
	    	
	    	Viewportable viewport =scrollPane.getViewport ();
	    	if(viewport != null){
	    		size.increaseSize(viewport.getViewportPane().getPreferredSize());
	    	}
	    	return size;
    	}else{
    		return super.preferredLayoutSize(target);
    	}
    }
	
     public void  layoutContainer (Container target ){
    	if(target is JScrollPane){
    		JScrollPane scrollPane =JScrollPane(target );
    		Viewportable viewport =scrollPane.getViewport ();
    		JScrollBar vScrollBar =scrollPane.getVerticalScrollBar ();
    		JScrollBar hScrollBar =scrollPane.getHorizontalScrollBar ();

    		IntDimension fcd =scrollPane.getSize ();
    		Insets insets =scrollPane.getInsets ();
    		IntRectangle cb =insets.getInsideBounds(fcd.getBounds ());
			
    		IntDimension vPreferSize =vScrollBar.getPreferredSize ();
    		IntDimension hPreferSize =hScrollBar.getPreferredSize ();
    		int vx ,vy int ,vh ,vw ;
    		int hx ,hy int ,hw ,hh ;
    		
    		Component vpPane =viewport.getViewportPane ();
    		int wdis =0;
    		int hdis =0;
    		if(scrollPane.getHorizontalScrollBarPolicy() == JScrollPane.SCROLLBAR_ALWAYS){
    			hdis = hPreferSize.height;
    		}
    		if(scrollPane.getVerticalScrollBarPolicy() == JScrollPane.SCROLLBAR_ALWAYS){
    			wdis = vPreferSize.width;
    		}
    		//inital bounds
    		//trace("------------------------------------------------------");
			//trace("----------setViewportTestSize : " + new IntDimension(cb.width-wdis, cb.height-hdis));
    		viewport.setViewportTestSize(new IntDimension(cb.width-wdis, cb.height-hdis));
    		IntDimension showSize =viewport.getExtentSize ();
    		IntDimension viewSize =viewport.getViewSize ();
    		//trace("extentSize : " + showSize);
    		//trace("viewSize : " + viewSize);
    		vw = vPreferSize.width;
    		hh = hPreferSize.height;
    		if(scrollPane.getHorizontalScrollBarPolicy() == JScrollPane.SCROLLBAR_NEVER){
   				hScrollBar.setVisible(false);
    			hh = 0;
    		}else if(viewSize.width <= showSize.width){
    			if(hScrollBar.isEnabled())
    				hScrollBar.setEnabled(false);
    			if(scrollPane.getHorizontalScrollBarPolicy() != JScrollPane.SCROLLBAR_ALWAYS){
   					hScrollBar.setVisible(false);
    				hh = 0;
    				viewport.setViewPosition(new IntPoint(0, viewport.getViewPosition().y));    				
    			}else{
    				hScrollBar.setVisible(true); 	
    			}
    		}else{
   				hScrollBar.setVisible(true);
    			if(!hScrollBar.isEnabled())
    				hScrollBar.setEnabled(true);
    		}
    		if(hh != hdis){
				//trace("----------Shown HScrollBar setViewportTestSize : " + new IntDimension(cb.width, cb.height-hh));
    			viewport.setViewportTestSize(new IntDimension(cb.width, cb.height-hh));
    			showSize = viewport.getExtentSize();
    			viewSize = viewport.getViewSize();
	    		//trace("extentSize : " + showSize);
	    		//trace("viewSize : " + viewSize);
    		}
    		if(scrollPane.getVerticalScrollBarPolicy() == JScrollPane.SCROLLBAR_NEVER){
   				vScrollBar.setVisible(false);
    			vw = 0;
    		}else if(viewSize.height <= showSize.height){
    			vScrollBar.setEnabled(false);
    			if(scrollPane.getVerticalScrollBarPolicy() != JScrollPane.SCROLLBAR_ALWAYS){
   					vScrollBar.setVisible(false);
    				vw = 0;
    				viewport.setViewPosition(new IntPoint(viewport.getViewPosition().x, 0));  
    			}else{
   					vScrollBar.setVisible(true);
    			}
    		}else{
   				vScrollBar.setVisible(true);
    			if(!vScrollBar.isEnabled())
    				vScrollBar.setEnabled(true);    
    		}
    		
    		if(vw != wdis){
				//trace("----------Shown VScrollBar setViewportTestSize : " + new IntDimension(cb.width-vw, cb.height-hh));
    			viewport.setViewportTestSize(new IntDimension(cb.width-vw, cb.height-hh));
    			showSize = viewport.getExtentSize();
    			viewSize = viewport.getViewSize();
    			//trace("extentSize : " + showSize);
    			//trace("viewSize : " + viewSize);
    		}
    		if(viewSize.width > showSize.width && scrollPane.getHorizontalScrollBarPolicy() == JScrollPane.SCROLLBAR_AS_NEEDED){
    			if(!hScrollBar.isVisible()){
    				hScrollBar.setEnabled(true);
    				hScrollBar.setVisible(true);
    				hh = hPreferSize.height;
					//trace("----------Shown HScrollBar setViewportTestSize : " + new IntDimension(cb.width-vw, cb.height-hh));
    				viewport.setViewportTestSize(new IntDimension(cb.width-vw, cb.height-hh));
	    			showSize = viewport.getExtentSize();
	    			viewSize = viewport.getViewSize();
	    			//trace("extentSize : " + showSize);
	    			//trace("viewSize : " + viewSize);
    			}
    		}
    		
    		
    		int viewPortX =cb.x ;
    		int viewPortY =cb.y ;
    		
    		if(style == TOP_LEFT){
    			vx = cb.x;
    			vy = cb.y + hh;
    			vh = cb.height - hh;
    			
    			hx = cb.x + vw;
    			hy = cb.y;
    			hw = cb.width - vw;
    			
    			viewPortY += hh;
    			viewPortX += vw;
    		}else if(style == TOP_RIGHT){
    			vx = cb.x + cb.width - vw;
    			vy = cb.y + hh;
    			vh = cb.height - hh;
    			
    			hx = cb.x;
    			hy = cb.y;
    			hw = cb.width - vw;
    			
    			viewPortY += hh;
    		}else if(style == BOTTOM_LEFT){
    			vx = cb.x;
    			vy = cb.y;
    			vh = cb.height - hh;
    			
    			hx = cb.x + vw;
    			hy = cb.y + cb.height - hh;
    			hw = cb.width - vw;
    			
    			viewPortX += vw;
    		}else{
    			vx = cb.x + cb.width - vw;
    			vy = cb.y;
    			vh = cb.height - hh;
    			
    			hx = cb.x;
    			hy = cb.y + cb.height - hh;
    			hw = cb.width - vw;
    		}
    		if(vScrollBar.isVisible()){
    			vScrollBar.setComBoundsXYWH(vx, vy, vw, vh);
    		}
    		if(hScrollBar.isVisible()){
    			hScrollBar.setComBoundsXYWH(hx, hy, hw, hh);
    		}
    			
			//trace("----------setViewportTestSize final : " + new IntDimension(cb.width - vw, cb.height - hh));
    		vpPane.setComBoundsXYWH(viewPortX, viewPortY, cb.width - vw, cb.height - hh);
    		    		
			if(hScrollBar.isVisible()){
    			hScrollBar.setValues(Math.max(Math.min(hScrollBar.getValue(), viewSize.width - showSize.width), 0), showSize.width, 0, viewSize.width);
    			hScrollBar.setUnitIncrement(viewport.getHorizontalUnitIncrement());
    			hScrollBar.setBlockIncrement(viewport.getHorizontalBlockIncrement());
			}
			if(vScrollBar.isVisible()){
    			vScrollBar.setValues(Math.max(Math.min(vScrollBar.getValue(), viewSize.height - showSize.height), 0), showSize.height, 0, viewSize.height);
    			vScrollBar.setUnitIncrement(viewport.getVerticalUnitIncrement());
    			vScrollBar.setBlockIncrement(viewport.getVerticalBlockIncrement());
			}
    	}
    }
	

}


