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

	
import flash.text.*;
import org.aswing.*;
import org.aswing.event.InteractiveEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.background.ProgressBarIcon;
import org.aswing.util.DepthManager;

/**
 * @private
 */
public class BasicProgressBarUI extends BaseComponentUI{
	
	protected GroundDecorator iconDecorator ;
	protected TextField stringText ;
	protected Object stateListener ;
	protected JProgressBar progressBar ;
	
	public  BasicProgressBarUI (){
		super();
	}

    protected String  getPropertyPrefix (){
        return "ProgressBar.";
    }    	

	 public void  installUI (Component c ){
		progressBar = JProgressBar(c);
		installDefaults();
		installComponents();
		installListeners();
	}
	
	 public void  uninstallUI (Component c ){
		progressBar = JProgressBar(c);		
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(progressBar, pp);
		LookAndFeel.installBasicProperties(progressBar, pp);
		LookAndFeel.installBorderAndBFDecorators(progressBar, pp);
		if(!progressBar.isIndeterminateDelaySet()){
			progressBar.setIndeterminateDelay(getUint(pp + "indeterminateDelay"));
			progressBar.setIndeterminateDelaySet(false);
		}
	}
	
	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(progressBar);
	}
	
	protected void  installComponents (){
		stringText = new TextField();
		stringText.autoSize = TextFieldAutoSize.CENTER;
		stringText.mouseEnabled = false;
		stringText.tabEnabled = false;
		stringText.selectable = false;
		progressBar.addChild(stringText);
	}
	
	protected void  uninstallComponents (){
		if(stringText.parent != null) {
    		stringText.parent.removeChild(stringText);
		}
		stringText = null;
		iconDecorator = null;
	}
	
	protected void  installListeners (){
		progressBar.addEventListener(InteractiveEvent.STATE_CHANGED, __stateChanged);
	}
	protected void  uninstallListeners (){
		progressBar.removeEventListener(InteractiveEvent.STATE_CHANGED, __stateChanged);
	}
	
	protected void  __stateChanged (JProgressBar source ){
		source.repaint();
	}
	
     public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		JProgressBar sp =JProgressBar(c );
		if(sp.getString() != null && sp.getString().length>0){
			stringText.text = sp.getString();
	    	AsWingUtils.applyTextFontAndColor(stringText, sp.getFont(), sp.getForeground());
			
			if (sp.getOrientation() == JProgressBar.VERTICAL){
				//TODO use bitmap to achieve rotate
				stringText.rotation = -90;
				stringText.x = Math.round(b.x + (b.width - stringText.width)/2);
				stringText.y = Math.round(b.y + (b.height - stringText.height)/2 + stringText.height);
			}else{
				stringText.rotation = 0;
				stringText.x = Math.round(b.x + (b.width - stringText.width)/2);
				stringText.y = Math.round(b.y + (b.height - stringText.height)/2);
			}
			DepthManager.bringToTop(stringText);
		}else{
			stringText.text = "";
		}
	}

    //--------------------------Dimensions----------------------------
    
	 public IntDimension  getPreferredSize (Component c ){
		JProgressBar sp =JProgressBar(c );
		IntDimension size ;
		if (sp.getOrientation() == JProgressBar.VERTICAL){
			size = getPreferredInnerVertical();
		}else{
			size = getPreferredInnerHorizontal();
		}
		
		if(sp.getString() != null){
			IntDimension textSize =c.getFont ().computeTextSize(sp.getString (),false );
			if (sp.getOrientation() == JProgressBar.VERTICAL){
				size.width = Math.max(size.width, textSize.height);
				size.height = Math.max(size.height, textSize.width);
			}else{
				size.width = Math.max(size.width, textSize.width);
				size.height = Math.max(size.height, textSize.height);
			}
		}
		return sp.getInsets().getOutsideSize(size);
	}
     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }
     public IntDimension  getMinimumSize (Component c ){
		return c.getInsets().getOutsideSize(new IntDimension(1, 1));
    }
    
    protected IntDimension  getPreferredInnerHorizontal (){
    	return new IntDimension(80, 12);
    }
    protected IntDimension  getPreferredInnerVertical (){
    	return new IntDimension(12, 80);
    }	
	
}


