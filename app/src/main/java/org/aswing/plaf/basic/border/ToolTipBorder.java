/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.border;

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

	
import org.aswing.ASColor;
import org.aswing.border.LineBorder;
import org.aswing.plaf.UIResource;
import org.aswing.Component;
import org.aswing.graphics.Graphics2D;
import org.aswing.geom.IntRectangle;

/**
 * @private
 */
public class ToolTipBorder extends LineBorder implements UIResource
	private ASColor color ;
	
	public  ToolTipBorder ()
	{
		super();
	}
	
	 public void  updateBorderImp (Component c ,Graphics2D g ,IntRectangle b )
	{
		if(color == null){
			color = c.getUI().getColor("ToolTip.borderColor");
			setColor(color);
		}
		super.updateBorderImp(c, g, b);
	}
}


