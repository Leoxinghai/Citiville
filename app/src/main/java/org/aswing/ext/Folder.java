
package org.aswing.ext;

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
import org.aswing.event.InteractiveEvent;

/**
 * Dispatched when the button's state changed. the state is all about:
 * <ul>
 * <li>enabled</li>
 * <li>rollOver</li>
 * <li>pressed</li>
 * <li>released</li>
 * <li>selected</li>
 * </ul>
 * </p>
 * <p>
 * Buttons always fire <code>programmatic=false</code> InteractiveEvent.
 * </p>
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A panel with a title bar, click the title bar can collapse or expand the panel content.
 * @author iiley
 */
public class Folder extends JPanel{

	public static  int TOP =AsWingConstants.TOP ;
	public static  int BOTTOM =AsWingConstants.BOTTOM ;
	
	private JToggleButton titleButton ;
	private Component contentPane ;
	private String title ;
	private int titlePosition ;
	private int gap ;
	
	/**
	 * Folder(title:String, titlePosition:Number, gap:Number)<br>
	 * Folder(title:String, titlePosition:Number) default gap to 4<br>
	 * Folder(title:String) default titlePosition to TOP<br>
	 * Folder() default title to ""
	 */
	public  Folder (String title ="",int titlePosition =AsWingConstants .TOP ,int gap =4){
		super();
		setName("Folder");
		this.title = title;
		this.titlePosition = titlePosition;
		this.gap = gap;
		setLayout(new BorderLayout(0, gap));
		titleButton = new JToggleButton();
		titleButton.setSelected(false);
		setForeground(new ASColor(0x336600));
		setFocusable(false);
		
		titleButton.addSelectionListener(__titleSelectionChanged);
		initTitleBar();
		changeTitleRepresentWhenStateChanged();
	}
	
	/**
	 * Override this method to init different LAF title bar
	 */
	private void  initTitleBar (){
		setFont(new ASFont("Dialog", 12, true));
		titleButton.setFont(null);
		titleButton.setHorizontalAlignment(AsWingConstants.LEFT);
		if(titlePosition == BOTTOM){
			titleButton.setConstraints(BorderLayout.SOUTH);
		}else{
			titleButton.setConstraints(BorderLayout.NORTH);
		}
		super.insertImp(0, titleButton);
	}
	
	/**
	 * Override this method to control the title representation.
	 */
	private void  changeTitleRepresentWhenStateChanged (){
		if(isExpanded()){
			titleButton.setText("- " + getTitle());
		}else{
			titleButton.setText("+ " + getTitle());
		}
	}
	
	private void  __titleSelectionChanged (InteractiveEvent e ){
		getContentPane().setVisible(titleButton.isSelected());
		changeTitleRepresentWhenStateChanged();
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, e.isProgrammatic()));
		revalidate();
	}
	
	/**
	 * Adds listener to listen the expand or collapse state change event.
	 */
	public void  addChangeListener (Function func ,int priority =0,boolean useWeekReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, func, false, priority, useWeekReference);
	}
	
	/**
	 * Sets the folder font, title font will keep same to this
	 */
	 public void  setFont (ASFont f ){
		super.setFont(f);
		if(titleButton){
			titleButton.repaint();
		}
	}
		
	public void  setTitleForeground (ASColor c ){
		titleButton.setForeground(c);
	}
	public ASColor  getTitleForeground (){
		return titleButton.getForeground();
	}
	
	public void  setTitleBackground (ASColor c ){
		titleButton.setBackground(c);
	}
	public ASColor  getTitleBackground (){
		return titleButton.getBackground();
	}
	
	public void  setTitleToolTipText (String t ){
		titleButton.setToolTipText(t);
	}
	public String  getTitleToolTipText (){
		return titleButton.getToolTipText();
	}
	
	/**
	 * Returns whether the folder is expanded or not.
	 */
	public boolean  isExpanded (){
		return titleButton.isSelected();
	}
	
	/**
	 * Sets whether to expand the folder or not.
	 */
	public void  setExpanded (boolean b ){
		titleButton.setSelected(b);
	}
	
	/**
	 * Sets the title
	 */
	public void  setTitle (String t ){
		if(t != title){
			title = t;
			changeTitleRepresentWhenStateChanged();
		}
	}
	
	/**
	 * Returns the title
	 */
	public String  getTitle (){
		return title;
	}
	
	/**
	 * Returns the content pane
	 */
	public Component  getContentPane (){
		if(contentPane == null){
			contentPane = new JPanel();
			contentPane.setConstraints(BorderLayout.CENTER);
			contentPane.setVisible(isExpanded());
			super.insert(-1, contentPane);
		}
		return contentPane;
	}
	
	/**
	 * Sets the content pane
	 * @param p the content pane
	 */
	public void  setContentPane (Component p ){
		if(contentPane != p){
			remove(contentPane);
			contentPane = p;
			contentPane.setConstraints(BorderLayout.CENTER);
			contentPane.setVisible(isExpanded());
			super.insert(-1, contentPane);
		}
	}
	
	 public void  append (Component c ,Object constraints =null ){
		if(c.getConstraints() == null){
			c.setConstraints(constraints);
		}
		setContentPane(c);
	}
	
	/**
	 * Adds this folder to a group, to achieve one time there just can be one or less folder are expanded.
	 * @param group the group to add in.
	 */
	public void  addToGroup (ButtonGroup group ){
		if(!group.contains(titleButton)){
			group.append(titleButton);
		}
	}
	
	/**
	 * Removes this folder from a group.
	 * @see #addToGroup()
	 */
	public void  removeFromGroup (ButtonGroup group ){
		group.remove(titleButton);
	}
}


