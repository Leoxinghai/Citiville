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
 * The basic imp for ClosableTab
 * @author iiley
 */
public class BasicClosableTabbedPaneTab implements ClosableTab{
	
	protected Container panel ;
	protected JLabel label ;
	protected AbstractButton button ;
	protected Insets margin ;
	protected Component owner ;
	
	public  BasicClosableTabbedPaneTab (){
		super();
	}
	
	public void  initTab (Component owner ){
		this.owner = owner;
		panel = new Container();
		panel.setLayout(new BorderLayout());
		label = new JLabel();
		panel.append(label, BorderLayout.CENTER);
		button = createCloseButton();
		Container bc =new Container ();
		bc.setLayout(new CenterLayout());
		bc.append(button);
		panel.append(bc, BorderLayout.EAST);
		label.setFocusable(false);
		button.setFocusable(false);
		margin = new Insets(0,0,0,0);
	}
	
	protected AbstractButton  createCloseButton (){
		AbstractButton button =new JButton ();
		button.setMargin(new Insets());
		button.setOpaque(false);
		button.setBackgroundDecorator(null);
		button.setIcon(new CloseIcon());
		return button;
	}
	
	public void  setFont (ASFont font ){
		label.setFont(font);
	}
	
	public void  setForeground (ASColor color ){
		label.setForeground(color);
	}
	
	public void  setMargin (Insets m ){
		if(!margin.equals(m)){
			panel.setBorder(new EmptyBorder(null, m));
			margin = m.clone();
		}
	}
	
	public void  setEnabled (boolean b ){
		label.setEnabled(b);
		button.setEnabled(b);
	}
	
	public Component  getCloseButton (){
		return button;
	}
	
	public void  setVerticalAlignment (int alignment ){
		label.setVerticalAlignment(alignment);
	}
	
	public Component  getTabComponent (){
		return panel;
	}
	
	public void  setHorizontalTextPosition (int textPosition ){
		label.setHorizontalTextPosition(textPosition);
	}
	
	public void  setTextAndIcon (String text ,Icon icon ){
		label.setText(text);
		label.setIcon(icon);
	}
	
	public void  setIconTextGap (int iconTextGap ){
		label.setIconTextGap(iconTextGap);
	}
	
	public void  setSelected (boolean b ){
		//do nothing
	}
	
	public void  setVerticalTextPosition (int textPosition ){
		label.setVerticalTextPosition(textPosition);
	}
	
	public void  setHorizontalAlignment (int alignment ){
		label.setHorizontalAlignment(alignment);
	}
	
}


