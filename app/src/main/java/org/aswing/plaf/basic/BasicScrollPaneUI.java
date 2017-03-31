/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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

	
import org.aswing.plaf.BaseComponentUI;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

/**
 * The basic scroll pane ui imp.
 * @author iiley
 * @private
 */
public class BasicScrollPaneUI extends BaseComponentUI{

	protected JScrollPane scrollPane ;
	protected Viewportable lastViewport ;
	
	public  BasicScrollPaneUI (){
	}
    	
     public void  installUI (Component c ){
		scrollPane = JScrollPane(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
		scrollPane = JScrollPane(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
    
    protected String  getPropertyPrefix (){
        return "ScrollPane.";
    }
    
	private void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(scrollPane, pp);
        LookAndFeel.installBorderAndBFDecorators(scrollPane, pp);
        LookAndFeel.installBasicProperties(scrollPane, pp);
        if(!scrollPane.getVerticalScrollBar().isFocusableSet()){
        	scrollPane.getVerticalScrollBar().setFocusable(false);
        	scrollPane.getVerticalScrollBar().setFocusableSet(false);
        }
        if(!scrollPane.getHorizontalScrollBar().isFocusableSet()){
        	scrollPane.getHorizontalScrollBar().setFocusable(false);
        	scrollPane.getHorizontalScrollBar().setFocusableSet(false);
        }
	}
    
    private void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(scrollPane);
    }
    
	private void  installComponents (){
    }
	private void  uninstallComponents (){
    }
	
	private void  installListeners (){
		scrollPane.addAdjustmentListener(__adjustChanged);
		scrollPane.addEventListener(ScrollPaneEvent.VIEWPORT_CHANGED, __viewportChanged);
		__viewportChanged(null);
	}
    
    private void  uninstallListeners (){
		scrollPane.removeAdjustmentListener(__adjustChanged);
		scrollPane.removeEventListener(ScrollPaneEvent.VIEWPORT_CHANGED, __viewportChanged);
		if(lastViewport != null){
			lastViewport.removeStateListener(__viewportStateChanged);
		}
    }
    
	//-------------------------listeners--------------------------
    private void  syncScrollPaneWithViewport (){
		Viewportable viewport =scrollPane.getViewport ();
		JScrollBar vsb =scrollPane.getVerticalScrollBar ();
		JScrollBar hsb =scrollPane.getHorizontalScrollBar ();
		if (viewport != null) {
		    IntDimension extentSize =viewport.getExtentSize ();
		    if(extentSize.width <=0 || extentSize.height <= 0){
		    	//trace("/w/zero extent size");
		    	return;
		    }
		    IntDimension viewSize =viewport.getViewSize ();
		    IntPoint viewPosition =viewport.getViewPosition ();
			int extent ,max int ,value ;
		    if (vsb != null) {
				extent = extentSize.height;
				max = viewSize.height;
				value = Math.max(0, Math.min(viewPosition.y, max - extent));
				vsb.setValues(value, extent, 0, max);
				vsb.setUnitIncrement(viewport.getVerticalUnitIncrement());
				vsb.setBlockIncrement(viewport.getVerticalBlockIncrement());
	    	}

		    if (hsb != null) {
				extent = extentSize.width;
				max = viewSize.width;
				value = Math.max(0, Math.min(viewPosition.x, max - extent));
				hsb.setValues(value, extent, 0, max);
				hsb.setUnitIncrement(viewport.getHorizontalUnitIncrement());
				hsb.setBlockIncrement(viewport.getHorizontalBlockIncrement());
	    	}
		}
    }	
	
	private void  __viewportChanged (ScrollPaneEvent e ){
		if(lastViewport != null){
			lastViewport.removeStateListener(__viewportStateChanged);
		}
		lastViewport = scrollPane.getViewport();
		lastViewport.addStateListener(__viewportStateChanged);
	}
	
	private void  __viewportStateChanged (InteractiveEvent e ){
		syncScrollPaneWithViewport();
	}
	
    private void  __adjustChanged (ScrollPaneEvent e ){
    	Viewportable viewport =scrollPane.getViewport ();
    	viewport.scrollRectToVisible(scrollPane.getVisibleRect(), e.isProgrammatic());
    }
	
}


