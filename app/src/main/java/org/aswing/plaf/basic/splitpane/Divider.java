package org.aswing.plaf.basic.splitpane;

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
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.basic.icon.SolidArrowIcon;

/**
 * @private
 */
public class Divider extends Container
	
	protected Icon icon ;
	protected AbstractButton leftButton ;
	protected AbstractButton rightButton ;
	protected SolidArrowIcon leftIcon ;
	protected SolidArrowIcon rightIcon ;
	protected JSplitPane sp ;
	
	public  Divider (JSplitPane sp ){
		super();
		this.sp = sp;
		setOpaque(false);
		setFocusable(false);
		setBackground(sp.getBackground());
		leftButton = createCollapseLeftButton();
		rightButton = createCollapseRightButton();
		leftButton.setSize(leftButton.getPreferredSize());
		rightButton.setSize(rightButton.getPreferredSize());
		icon = new DividerIcon();
		append(leftButton);
		append(rightButton);
	}
	
	public JSplitPane  getOwner (){
		return sp;
	}
	
	protected void  layoutButtons (){
		if(sp.isOneTouchExpandable()){
			if(sp.getOrientation() == JSplitPane.VERTICAL_SPLIT){
				leftIcon.setArrow(-Math.PI/2);
				rightIcon.setArrow(Math.PI/2);
				leftButton.setLocationXY(4, 0);
				rightButton.setLocationXY(14, getHeight()-rightButton.getHeight());
			}else{
				leftIcon.setArrow(Math.PI);
				rightIcon.setArrow(0);
				leftButton.setLocationXY(0, 4);
				rightButton.setLocationXY(getWidth()-rightButton.getWidth(), 14);
			}
			leftButton.setVisible(true);
			rightButton.setVisible(true);
			leftButton.validate();
			rightButton.validate();
			leftButton.repaint();
			rightButton.repaint();
		}else{
			leftButton.setVisible(false);
			rightButton.setVisible(false);
		}
	}
	
	 protected void  paint (IntRectangle b ){
		super.paint(b);
		Graphics2D g =new Graphics2D(this.graphics );
		if(icon != null){
			icon.updateIcon(this, g, b.x, b.y);
		}
		layoutButtons();
	}
	
	protected AbstractButton  createCollapseLeftButton (){
		leftIcon = new SolidArrowIcon(Math.PI, 8, sp.getForeground());
		return createButton(leftIcon);
	}
	
	protected AbstractButton  createCollapseRightButton (){
		rightIcon = new SolidArrowIcon(0, 8, sp.getForeground());
		return createButton(rightIcon);
	}
	
	private AbstractButton  createButton (Icon icon ){
		JButton btn =new JButton ();
		btn.setOpaque(false);
		btn.setFocusable(false);
		btn.setBorder(null);
		btn.setMargin(new Insets());
		btn.setIcon(icon);
		return btn;
	}
	
	public AbstractButton  getCollapseLeftButton (){
		return leftButton;
	}
	
	public AbstractButton  getCollapseRightButton (){
		return rightButton;
	}
}


