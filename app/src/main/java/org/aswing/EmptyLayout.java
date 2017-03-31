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

import org.aswing.geom.IntDimension;
import org.aswing.Container;
import org.aswing.Component;
	
/**
 * LayoutManager's empty implementation.
 * @author iiley
 */
public class EmptyLayout implements LayoutManager
	public  EmptyLayout (){
	}
	
    /** 
     * Do nothing
     * @inheritDoc
     */
    public void  addLayoutComponent (Component comp ,Object constraints ){
    }

    /**
     * Do nothing
     * @inheritDoc
     */
    public void  removeLayoutComponent (Component comp ){
    }
	
	/**
	 * Simply return target.getSize();
	 */
    public IntDimension  preferredLayoutSize (Container target ){
    	return target.getSize();
    }

	/**
	 * new IntDimension(0, 0);
	 */
    public IntDimension  minimumLayoutSize (Container target ){
    	return new IntDimension(0, 0);
    }
	
	/**
	 * return IntDimension.createBigDimension();
	 */
    public IntDimension  maximumLayoutSize (Container target ){
    	return IntDimension.createBigDimension();
    }
    
    /**
     * do nothing
     */
    public void  layoutContainer (Container target ){
    }
    
	/**
	 * return 0
	 */
    public double  getLayoutAlignmentX (Container target ){
    	return 0;
    }

	/**
	 * return 0
	 */
    public double  getLayoutAlignmentY (Container target ){
    	return 0;
    }

    /**
     * do nothing
     */
    public void  invalidateLayout (Container target ){
    }		
	
}


