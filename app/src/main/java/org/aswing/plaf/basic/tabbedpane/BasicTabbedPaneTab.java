/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.tabbedpane;

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
import org.aswing.border.EmptyBorder;

/**
 * BasicTabbedPaneTab implemented with a JLabel 
 * @author iiley
 * @private
 */
public class BasicTabbedPaneTab implements Tab{
	
	protected JLabel label ;
	protected Insets margin ;
	protected Component owner ;
	
	public  BasicTabbedPaneTab (){
	}
	
	public void  initTab (Component owner ){
		this.owner = owner;
		label = new JLabel();
		margin = new Insets(0,0,0,0);
	}
	
	public void  setFont (ASFont font ){
		label.setFont(font);
	}
	
	public void  setForeground (ASColor color ){
		label.setForeground(color);
	}
	
	public void  setMargin (Insets m )
	{
		if(!margin.equals(m)){
			label.setBorder(new EmptyBorder(null, m));
			margin = m.clone();
		}
	}
	
	public void  setVerticalAlignment (int alignment )
	{
		label.setVerticalAlignment(alignment);
	}
	
	public Component  getTabComponent ()
	{
		return label;
	}
	
	public void  setHorizontalTextPosition (int textPosition )
	{
		label.setHorizontalTextPosition(textPosition);
	}
	
	public void  setTextAndIcon (String text ,Icon icon )
	{
		label.setText(text);
		label.setIcon(icon);
	}
	
	public void  setIconTextGap (int iconTextGap )
	{
		label.setIconTextGap(iconTextGap);
	}
	
	public void  setSelected (boolean b )
	{
		//do nothing
	}
	
	public void  setVerticalTextPosition (int textPosition )
	{
		label.setVerticalTextPosition(textPosition);
	}
	
	public void  setHorizontalAlignment (int alignment )
	{
		label.setHorizontalAlignment(alignment);
	}
	
}


