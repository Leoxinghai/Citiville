/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.icon;

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
import org.aswing.graphics.*;
import org.aswing.geom.*;

/**
 * The icon for frame normal.
 * @author iiley
 * @private
 */
public class FrameNormalIcon extends FrameIcon

	public  FrameNormalIcon (){
		super();
	}

	 public void  updateIconImp (Component c ,Graphics2D g ,int x ,int y )
	{
		double w =width /2;
		SolidBrush borderBrush =new SolidBrush(getColor(c ));
		g.beginFill(borderBrush);
		g.rectangle(x+w/2+1, y+w/4+0.5, w, w);
		g.rectangle(x+w/2+0.5+1, y+w/4+1.5+0.5, w-1, w-2);
		g.endFill();
		g.beginFill(borderBrush);
		g.rectangle(x+w/4, y+w/2+1.5, w, w);
		g.rectangle(x+w/4+0.5, y+w/2+1.5+1.5, w-1, w-2);
		g.endFill();	
	}	
}


