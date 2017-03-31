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

import org.aswing.BorderLayout;
import org.aswing.Container;
import org.aswing.FrameTitleBar;
import org.aswing.JLabel;
import org.aswing.UIManager;
import org.aswing.geom.IntDimension;
import org.aswing.plaf.ComponentUI;

public class FrameTitleBarLayout extends BorderLayout{
	
	protected IntDimension minSize ;
	
	public  FrameTitleBarLayout (int minWidth =50,int height =20){
		super();
		minSize = new IntDimension(minWidth, height);
	}
	
	protected void  countMinSize (Container target ){
		FrameTitleBar bar =FrameTitleBar(target );
		ComponentUI frameUI =bar.getFrame ().getUI ();
		if(frameUI){
			minSize.height = frameUI.getInt("Frame.titleBarHeight");
		}else{
			minSize.height = UIManager.getInt("Frame.titleBarHeight");
		}
	}
	
     public IntDimension  minimumLayoutSize (Container target ){
    	countMinSize(target);
		return preferredLayoutSize(target);
    }
	
	/**
	 * 
	 */
     public IntDimension  preferredLayoutSize (Container target ){
    	countMinSize(target);
    	IntDimension size =super.preferredLayoutSize(target );
		FrameTitleBar bar =FrameTitleBar(target );
    	JLabel label =bar.getLabel ();
    	if(label && label.isVisible()){
    		size.width -= Math.max(0, label.getPreferredWidth()-60);
    	}
    	size.width = Math.max(minSize.width, size.width);
    	size.height = Math.max(minSize.height, size.height);
    	return size;
    }	
}


