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
 * Simple <code>LayoutManager</code> aligned the single contained component by the container's center.
 * 
 * @author iiley
 * @author Igor Sadovskiy
 */
public class CenterLayout extends EmptyLayout
	public  CenterLayout (){
		super();
	}
	
	/**
	 * Calculates preferred layout size for the given container.
	 */
     public IntDimension  preferredLayoutSize (Container target ){
    	return ( (target.getComponentCount() > 0) ?
    		target.getInsets().getOutsideSize(target.getComponent(0).getPreferredSize()) :
    		target.getInsets().getOutsideSize());
    }

    /**
     * Layouts component by center inside the given container. 
     *
     * @param target the container to lay out
     */
     public void  layoutContainer (Container target ){
        if (target.getComponentCount() > 0) {
	        IntDimension size =target.getSize ();
	        Insets insets =target.getInsets ();
	        IntRectangle rd =insets.getInsideBounds(size.getBounds ());
	        Component c =target.getComponent(0);
	        
	        IntRectangle cd =rd.clone ();
	        IntDimension preferSize =c.getPreferredSize ();
	        cd.setSize(preferSize);
	        
	        if (rd.width > preferSize.width) {
	        	cd.x += (rd.width - preferSize.width) / 2;
	        }
	        if (rd.height > preferSize.height) {
	        	cd.y += (rd.height - preferSize.height) / 2;
	        }
	     	cd.x = Math.round(cd.x);
	     	cd.y = Math.round(cd.y);
	     	c.setBounds(cd);   
        }
    }
}


