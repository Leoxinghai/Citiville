/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.tree;

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
import org.aswing.plaf.UIResource;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.tree.TreePath;

/**
 * @private
 */
public class BasicExpandControl implements ExpandControl, UIResource{
	
	public  paintExpandControl (Component c ,Graphics2D g ,IntRectangle bounds ,
		totalChildIndent:int, path:TreePath, row:int, expanded:Boolean, leaf:Boolean):void{
		if(leaf){
			return;
		}
		int w =totalChildIndent ;
		double cx =bounds.x -w /2;
		double cy =bounds.y +bounds.height /2;
		double r =4;
		Array trig ;
		if(!expanded){
			cx -= 2;
			trig = .get(new IntPoint(cx, cy-r), new IntPoint(cx, cy+r), new IntPoint(cx+r, cy));
		}else{
			cy -= 2;
			trig = .get(new IntPoint(cx-r, cy), new IntPoint(cx+r, cy), new IntPoint(cx, cy+r));
		}
		g.fillPolygon(new SolidBrush(ASColor.BLACK), trig);
	}
	
}


