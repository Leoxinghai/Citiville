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
 * The default layout manager for <code>JViewport</code>. 
 * <code>ViewportLayout</code> defines
 * a policy for layout that should be useful for most applications.
 * The viewport makes its view the same size as the viewport,
 * however it will not make the view smaller than its minimum size.
 * As the viewport grows the view is kept bottom justified until
 * the entire view is visible, subsequently the view is kept top
 * justified.
 * 
 * @author iiley
 */
public class ViewportLayout extends EmptyLayout{

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     * @param parent the component which needs to be laid out
     * @return a <code>Dimension</code> object containing the
     *		preferred dimensions
     * @see #minimumLayoutSize
     */
     public IntDimension  preferredLayoutSize (Container parent ){
		JViewport vp =JViewport(parent );
		IntDimension viewPreferSize =null ;
		if(vp.getView() != null){
			viewPreferSize = vp.getView().getPreferredSize();
		}else{
			viewPreferSize = new IntDimension(0, 0);
		}
		return vp.getViewportPane().getInsets().getOutsideSize(viewPreferSize);
    }

    /**
     * Called by the AWT when the specified container needs to be laid out.
     *
     * @param parent  the container to lay out
     */
	 public void  layoutContainer (Container parent ){
		JViewport vp =(JViewport)parent;
		if(vp == null){
			return;
		}
		Component view =vp.getView ();
		if (view == null) {
		    return;
		}
	
		/* All of the dimensions below are in view coordinates, except
		 * vpSize which we're converting.
		 */
	
		//Insets insets =vp.getInsets ();
		//IntDimension viewPrefSize =view.getPreferredSize ();
		//IntDimension vpSize =vp.getSize ();
		IntDimension extentSize =vp.getExtentSize ();
		//IntRectangle showBounds =new IntRectangle(insets.left ,insets.top ,extentSize.width ,extentSize.height );

		IntDimension viewSize =vp.getViewSize ();
	
		IntPoint viewPosition =vp.getViewPosition ();
		viewPosition.x = Math.round(viewPosition.x);
		viewPosition.y = Math.round(viewPosition.y);
	
		/* justify
		 * the view when the width of the view is smaller than the
		 * container.
		 */
	    if((viewPosition.x + extentSize.width) > viewSize.width){
			viewPosition.x = Math.max(0, viewSize.width - extentSize.width);
	    }
	
		/* If the new viewport size would leave empty space below the
		 * view, bottom justify the view or top justify the view when
		 * the height of the view is smaller than the container.
		 */
		if ((viewPosition.y + extentSize.height) > viewSize.height) {
		    viewPosition.y = Math.max(0, viewSize.height - extentSize.height);
		}
	
		vp.setViewPosition(viewPosition);
		view.setSize(viewSize);
		//trace("set View Pos : " + viewPosition);
		//trace("set View Size : " + viewSize);
    }
	
}


