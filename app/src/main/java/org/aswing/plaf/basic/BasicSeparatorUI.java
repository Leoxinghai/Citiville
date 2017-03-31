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

import org.aswing.*;
import org.aswing.error.ImpMissError;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.Graphics2D;
import org.aswing.graphics.Pen;
import org.aswing.graphics.SolidBrush;
import org.aswing.plaf.BaseComponentUI;
/**
 * A Basic L&F implementation of SeparatorUI.  This implementation 
 * is a "combined" view/controller.
 *
 * @author senkay
 * @private
 */
public class BasicSeparatorUI extends BaseComponentUI
		
	public  BasicSeparatorUI (){
		super();
	}
	
    protected String  getPropertyPrefix (){
        return "Separator.";
    }
	
	 public void  installUI (Component c ){
		installDefaults(JSeparator(c));
	}
	
	 public void  uninstallUI (Component c ){
		uninstallDefaults(JSeparator(c));
	}
	
	public void  installDefaults (JSeparator s ){
		String pp =getPropertyPrefix ();
		
		LookAndFeel.installColors(s, pp);
		LookAndFeel.installBasicProperties(s, pp);
		LookAndFeel.installBorderAndBFDecorators(s, pp);
		s.setAlignmentX(0.5);
		s.setAlignmentY(0.5);
	}
	
	public void  uninstallDefaults (JSeparator s ){
		LookAndFeel.uninstallBorderAndBFDecorators(s);
	}

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
    	super.paint(c, g, b); 	
		JSeparator sp =JSeparator(c );
		if (sp.getOrientation() == JSeparator.VERTICAL){
			Pen pen =new Pen(c.getBackground ().darker (),1);
			g.drawLine(pen, b.x+0.5, b.y, b.x+0.5, b.y+b.height);
			pen.setColor(c.getBackground().brighter());
			g.drawLine(pen, b.x+1.5, b.y, b.x+1.5, b.y+b.height);
		}else{
			Pen pen2 =new Pen(c.getBackground ().darker (),1);
			g.drawLine(pen2, b.x, b.y+0.5, b.x+b.width, b.y+0.5);
			pen2.setColor(c.getBackground().brighter());
			g.drawLine(pen2, b.x, b.y+1.5, b.x+b.width, b.y+1.5);
		}
	}
	
	 public IntDimension  getPreferredSize (Component c ){
		JSeparator sp =JSeparator(c );
		Insets insets =sp.getInsets ();
		if (sp.getOrientation() == JSeparator.VERTICAL){
			return insets.getOutsideSize(new IntDimension(2, 0));
		}else{
			return insets.getOutsideSize(new IntDimension(0, 2));
		}
	}
     public IntDimension  getMaximumSize (Component c ){
		JSeparator sp =JSeparator(c );
		Insets insets =sp.getInsets ();
		IntDimension size =insets.getOutsideSize ();
		if (sp.getOrientation() == JSeparator.VERTICAL){
			return new IntDimension(2 + size.width, 100000);
		}else{
			return new IntDimension(100000, 2 + size.height);
		}
    }
    
	 public IntDimension  getMinimumSize (Component c )
	{
		return getPreferredSize(c);
	}    
}


