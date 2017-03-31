/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.resizer;

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

	
import org.aswing.geom.*;
import org.aswing.Component;

/**
 * A basic implementation of ResizeStrategy.
 * 
 * <p>It will return the resized rectangle, the rectangle is not min than 
 * getResizableMinSize and not max than getResizableMaxSize if these method are defineded
 * in the resized comopoent.
 * </p>
 * @author iiley
 */
public class ResizeStrategyImp implements ResizeStrategy{
	
	private double wSign ;
	private double hSign ;
	
	public  ResizeStrategyImp (double wSign ,double hSign ){
		this.wSign = wSign;
		this.hSign = hSign;
	}
	
	/**
	 * Count and return the new bounds what the component would be resized to.<br>
	 * 
 	 * It will return the resized rectangle, the rectangle is not min than 
 	 * getResizableMinSize and not max than getResizableMaxSize if these method are defineded
 	 * in the resized comopoent.
	 */
	public IntRectangle  getBounds (IntRectangle origBounds ,IntDimension minSize ,IntDimension maxSize ,int movedX ,int movedY ){
		IntRectangle currentBounds =origBounds.clone ();
		if(minSize == null){
			minSize = new IntDimension(0, 0);
		}
		if(maxSize == null){
			maxSize = IntDimension.createBigDimension();
		}		
		int newX ;
		int newY ;
		int newW ;
		int newH ;
		if(wSign == 0){
			newW = currentBounds.width;
		}else{
			newW = currentBounds.width + wSign*movedX;
			newW = Math.min(maxSize.width, Math.max(minSize.width, newW));
		}
		if(wSign < 0){
			newX = currentBounds.x + (currentBounds.width - newW);
		}else{
			newX = currentBounds.x;
		}
		
		if(hSign == 0){
			newH = currentBounds.height;
		}else{
			newH = currentBounds.height + hSign*movedY;
			newH = Math.min(maxSize.height, Math.max(minSize.height, newH));
		}
		if(hSign < 0){
			newY = currentBounds.y + (currentBounds.height - newH);
		}else{
			newY = currentBounds.y;
		}
		newX = Math.round(newX);
		newY = Math.round(newY);
		newW = Math.round(newW);
		newH = Math.round(newH);
		return new IntRectangle(newX, newY, newW, newH);
	}
		
}


