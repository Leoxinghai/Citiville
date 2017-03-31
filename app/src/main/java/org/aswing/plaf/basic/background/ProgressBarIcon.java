/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.background;

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

	
import flash.display.*;

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;

/**
 * The barIcon decorator for ProgressBar.
 * @author senkay
 * @private
 */
public class ProgressBarIcon implements GroundDecorator, UIResource
	private double indeterminatePercent ;
	private ASColor color ;
	
	public  ProgressBarIcon (){
		indeterminatePercent = 0;
	}
	
	private void  reloadColors (ComponentUI ui ){
		color = ui.getColor("ProgressBar.progressColor");
	}
	
	public void  updateDecorator (Component com ,Graphics2D g ,IntRectangle b ){
		if(color == null){
			reloadColors(com.getUI());
		}
		JProgressBar pb =JProgressBar(com );
		if(pb == null){
			return;
		}
		
		IntRectangle box =b.clone ();
		double percent ;
		if(pb.isIndeterminate()){
			percent = indeterminatePercent;
			indeterminatePercent += 0.1;
			if(indeterminatePercent > 1){
				indeterminatePercent = 0;
			}
		}else{
			percent = pb.getPercentComplete();
		}
		
		double boxWidth =5;
		double gap =1;
		g.beginFill(new SolidBrush(color));
		
		if(pb.getOrientation() == JProgressBar.VERTICAL){
			box.height = boxWidth;
			double minY =b.y +b.height -b.height *percent ;
			for(box.y = b.y+b.height-boxWidth; box.y >= minY; box.y -= (boxWidth+gap)){
				g.rectangle(box.x, box.y, box.width, box.height);
			}
			if(box.y < minY && box.y + boxWidth > minY){
				box.height = boxWidth - (minY - box.y);
				box.y = minY;
				g.rectangle(box.x, box.y, box.width, box.height);
			}
		}else{
			box.width = boxWidth;
			double maxX =b.x +b.width *percent ;
			for(;box.x <= maxX - boxWidth; box.x += (boxWidth+gap)){
				g.rectangle(box.x, box.y, box.width, box.height);
			}
			box.width = maxX - box.x;
			if(box.width > 0){
				g.rectangle(box.x, box.y, box.width, box.height);
			}
		}
		g.endFill();
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}	
}


