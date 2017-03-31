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
import org.aswing.event.ToolTipEvent;
import flash.filters.DropShadowFilter;

/**
 * @private
 */
public class BasicToolTipUI extends BaseComponentUI{

	protected JToolTip tooltip ;
	protected JLabel label ;

	public  BasicToolTipUI (){
		super();
	}

     public void  installUI (Component c ){
    	tooltip = JToolTip(c);
        installDefaults();
        initallComponents();
        installListeners();
    }

    protected String  getPropertyPrefix (){
        return "ToolTip.";
    }

	protected void  installDefaults (){
        String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(tooltip, pp);
        LookAndFeel.installBorderAndBFDecorators(tooltip, pp);
        LookAndFeel.installBasicProperties(tooltip, pp);
        Array filters =getInstance(getPropertyPrefix ()+"filters")as Array ;
        tooltip.filters = filters;
	}

	protected void  initallComponents (){
		JToolTip b =tooltip ;
		b.setLayout(new BorderLayout());
		label = new JLabel(b.getTipText());
		label.setFont(null); //make it to use parent(JToolTip) font
		label.setForeground(null); //make it to user parent(JToolTip) foreground
		label.setUIElement(true);
		b.append(label, BorderLayout.CENTER);
	}

	protected void  installListeners (){
		tooltip.addEventListener(ToolTipEvent.TIP_TEXT_CHANGED, __tipTextChanged);
	}

	private void  __tipTextChanged (ToolTipEvent e ){
		label.setText(tooltip.getTipText());
	}

     public void  uninstallUI (Component c ){
        uninstallDefaults();
        uninstallListeners();
        uninstallComponents();
    }

    protected void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(tooltip);
        tooltip.filters = new Array();
    }

    protected void  uninstallComponents (){
    	tooltip.remove(label);
    	label = null;
    }

    protected void  uninstallListeners (){
    	tooltip.removeEventListener(ToolTipEvent.TIP_TEXT_CHANGED, __tipTextChanged);
    }


}


