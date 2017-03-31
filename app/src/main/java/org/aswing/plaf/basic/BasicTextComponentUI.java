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
import org.aswing.graphics.*;
import org.aswing.geom.*;
import org.aswing.error.ImpMissError;

/**
 * The base class for text component UIs.
 * @author iiley
 * @private
 */
public class BasicTextComponentUI extends BaseComponentUI{
	
	protected JTextComponent textComponent ;
	
	public  BasicTextComponentUI (){
		super();
	}

    protected String  getPropertyPrefix (){
    	throw new ImpMissError();
        return "";
    }
    
     public void  paint (Component c ,Graphics2D g ,IntRectangle r ){
    	super.paint(c, g, r);
    }
    
	 public void  installUI (Component c ){
		textComponent = JTextComponent(c);
		installDefaults();
		installComponents();
		installListeners();
	}
    
	 public void  uninstallUI (Component c ){
		textComponent = JTextComponent(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
 	}
 	
 	protected void  installDefaults (){
        String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(textComponent, pp);
        LookAndFeel.installBorderAndBFDecorators(textComponent, pp);
        LookAndFeel.installBasicProperties(textComponent, pp);
 	}
	
 	protected void  uninstallDefaults (){
 		LookAndFeel.uninstallBorderAndBFDecorators(textComponent);
 	}
 	
 	protected void  installComponents (){
 	}
	
 	protected void  uninstallComponents (){
 	}
 	
 	protected void  installListeners (){
 	}
	
 	protected void  uninstallListeners (){
 	}
	
	 public IntDimension  getMaximumSize (Component c )
	{
		return IntDimension.createBigDimension();
	}
	 public IntDimension  getMinimumSize (Component c )
	{
		return c.getInsets().getOutsideSize();
	}    
}


