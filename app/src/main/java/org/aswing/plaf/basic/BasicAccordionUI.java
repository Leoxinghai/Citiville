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


import flash.display.Sprite;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.events.TimerEvent;
import flash.ui.Keyboard;
import flash.utils.Timer;

import org.aswing.*;
import org.aswing.event.FocusKeyEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.accordion.*;
import org.aswing.plaf.basic.tabbedpane.Tab;

/**
 * Basic accordion ui imp.
 * @author iiley
 * @private
 */
public class BasicAccordionUI extends BaseComponentUI implements LayoutManager{

	private static  int MOTION_SPEED =50;

	protected JAccordion accordion ;
	protected Array headers ;
	protected Timer motionTimer ;
	protected Array headerDestinations ;
	protected Array childrenDestinations ;
	protected Array childrenOrderYs ;
	protected IntDimension destSize ;
	protected int motionSpeed ;

	protected Sprite headerContainer ;

	public  BasicAccordionUI (){
		super();
	}

     public void  installUI (Component c ){
    	headers = new Array();
    	destSize = new IntDimension();
    	accordion = JAccordion(c);
		installDefaults();
		installComponents();
		installListeners();
    }

	 public void  uninstallUI (Component c ){
    	accordion = JAccordion(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }

	protected String  getPropertyPrefix (){
		return "Accordion.";
	}

	protected void  installDefaults (){
		accordion.setLayout(this);
		String pp =getPropertyPrefix ();
        LookAndFeel.installBorderAndBFDecorators(accordion, pp);
        LookAndFeel.installColorsAndFont(accordion, pp);
        LookAndFeel.installBasicProperties(accordion, pp);
        motionSpeed = getInt(pp + "motionSpeed");
        if(motionSpeed <=0 || isNaN(motionSpeed)){
        	motionSpeed = MOTION_SPEED;
        }
       	Insets tabMargin =getInsets(pp +"tabMargin");
		if(tabMargin == null){
			tabMargin = new InsetsUIResource(1, 1, 1, 1);
		}
		Insets i =accordion.getMargin ();
		if (i == null || i is UIResource) {
	    	accordion.setMargin(tabMargin);
		}
	}

    protected void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(accordion);
    }

	protected void  installComponents (){
		headerContainer = new Sprite();
		headerContainer.tabEnabled = false;
		accordion.addChild(headerContainer);
		synTabs();
		synHeaderProperties();
    }

	protected void  uninstallComponents (){
		for(int i =0;i <headers.length ;i ++){
			Tab header =getHeader(i );
			headerContainer.removeChild(header.getTabComponent());
    		header.getTabComponent().removeEventListener(MouseEvent.CLICK, __tabClick);
		}
		headers.splice(0);
		accordion.removeChild(headerContainer);
    }

	protected void  installListeners (){
		accordion.addStateListener(__onSelectionChanged);
		accordion.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		motionTimer = new Timer(40);
		motionTimer.addEventListener(TimerEvent.TIMER, __onMotion);
	}

    protected void  uninstallListeners (){
		accordion.removeStateListener(__onSelectionChanged);
		accordion.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		motionTimer.stop();
		motionTimer = null;
    }

   	 public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
    	Tab header =getSelectedHeader ();
    	if(header != null){
    		header.getTabComponent().paintFocusRect(true);
    	}else{
    		super.paintFocus(c, g, b);
    	}
    }

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
    	super.paint(c, g, b);
    }

    /**
     *Just  this method if you want other LAF headers .
     */
    protected Tab  createNewHeader (){
    	Tab header =getInstance(getPropertyPrefix ()+"header")as Tab ;
    	if(header == null){
    		header = new BasicAccordionHeader();
    	}
    	header.initTab(accordion);
    	header.getTabComponent().setFocusable(false);
    	return header;
    }

    protected Tab  getHeader (int i ){
    	return Tab(headers.get(i));
    }

    protected void  synTabs (){
    	int comCount =accordion.getComponentCount ();
    	if(comCount != headers.length()){
    		int i ;
    		Tab header ;
    		if(comCount > headers.length()){
    			for(i = headers.length; i<comCount; i++){
    				header = createNewHeader();
    				header.setTextAndIcon(accordion.getTitleAt(i), accordion.getIconAt(i));
    				setHeaderProperties(header);
    				header.getTabComponent().setToolTipText(accordion.getTipAt(i));
    				header.getTabComponent().addEventListener(MouseEvent.CLICK, __tabClick);
    				headerContainer.addChild(header.getTabComponent());
    				headers.push(header);
    			}
    		}else{
    			for(i = headers.length-comCount; i>0; i--){
    				header = Tab(headers.pop());
    				header.getTabComponent().removeEventListener(MouseEvent.CLICK, __tabClick);
    				headerContainer.removeChild(header.getTabComponent());
    			}
    		}
    	}
    }

    protected void  synHeaderProperties (){
    	for(int i =0;i <headers.length ;i ++){
    		Tab header =getHeader(i );
    		header.setTextAndIcon(accordion.getTitleAt(i), accordion.getIconAt(i));
    		setHeaderProperties(header);
    		header.getTabComponent().setUIElement(true);
    		header.getTabComponent().setEnabled(accordion.isEnabledAt(i));
    		header.getTabComponent().setVisible(accordion.isVisibleAt(i));
    		header.getTabComponent().setToolTipText(accordion.getTipAt(i));
    	}
    }

    protected void  setHeaderProperties (Tab header ){
    	header.setHorizontalAlignment(accordion.getHorizontalAlignment());
    	header.setHorizontalTextPosition(accordion.getHorizontalTextPosition());
    	header.setIconTextGap(accordion.getIconTextGap());
    	header.setMargin(accordion.getMargin());
    	header.setVerticalAlignment(accordion.getVerticalAlignment());
    	header.setVerticalTextPosition(accordion.getVerticalTextPosition());
    	header.setFont(accordion.getFont());
    	header.setForeground(accordion.getForeground());
    }

    protected void  ensureHeadersOnTopDepths (){
    	accordion.bringToTop(headerContainer);
    }

    protected Tab  getSelectedHeader (){
    	if(accordion.getSelectedIndex() >= 0){
    		return getHeader(accordion.getSelectedIndex());
    	}else{
    		return null;
    	}
    }

    protected int  indexOfHeaderComponent (Component tab ){
    	for(int i =0;i <headers.length ;i ++){
    		if(getHeader(i).getTabComponent() == tab){
    			return i;
    		}
    	}
    	return -1;
    }

    //------------------------------Handlers--------------------------------

    private void  __tabClick (Event e ){
    	accordion.setSelectedIndex(indexOfHeaderComponent(e.currentTarget as Component));
    }

    private void  __onSelectionChanged (Event e ){
    	accordion.revalidate();
    	accordion.repaint();
    }

    private void  __onKeyDown (FocusKeyEvent e ){
    	if(headers.length > 0){
    		int n =accordion.getComponentCount ();
    		int code =e.keyCode ;
    		int index ;
	    	if(code == Keyboard.DOWN){
	    		setTraversingTrue();
		    	index = accordion.getSelectedIndex();
		    	index++;
		    	while(index<n && (!accordion.isEnabledAt(index) || !accordion.isVisibleAt(index))){
		    		index++;
		    	}
		    	if(index >= n){
		    		return;
		    	}
		    	accordion.setSelectedIndex(index);
	    	}else if(code == Keyboard.UP){
	    		setTraversingTrue();
		    	index = accordion.getSelectedIndex();
		    	index--;
		    	while(index >= 0 && (!accordion.isEnabledAt(index) || !accordion.isVisibleAt(index))){
		    		index--;
		    	}
		    	if(index < 0){
		    		return;
		    	}
		    	accordion.setSelectedIndex(index);
	    	}
    	}
    }

    protected void  setTraversingTrue (){
    	FocusManager fm =FocusManager.getManager(accordion.stage );
    	if(fm){
    		fm.setTraversing(true);
    	}
    }

    private void  __onMotion (TimerEvent e ){
    	boolean isFinished =true ;
    	int n =headerDestinations.length ;
    	int selected =accordion.getSelectedIndex ();
    	int i =0;
    	Component child ;

    	for(i=0; i<n; i++){
    		Tab header =getHeader(i );
    		Component tab =header.getTabComponent ();
    		int curY =tab.getY ();
    		int desY =headerDestinations.get(i) ;
    		int toY ;
    		if(Math.abs(desY - curY) <= motionSpeed){
    			toY = desY;
    		}else{
    			if(desY > curY){
    				toY = curY + motionSpeed;
    			}else{
    				toY = curY - motionSpeed;
    			}
    			isFinished = false;
    		}
    		tab.setLocationXY(tab.getX(), toY);
    		tab.validate();
    		child = accordion.getComponent(i);
    		child.setLocationXY(child.getX(), toY + tab.getHeight());
    	}

    	adjustClipSizes();

    	if(isFinished){
    		motionTimer.stop();
    		for(i=0; i<n; i++){
	    		child = accordion.getComponent(i);
	    		if(selected == i){
	    			child.setVisible(true);
	    		}else{
	    			child.setVisible(false);
	    		}
    		}
    	}

    	for(i=0; i<n; i++){
    		child = accordion.getComponent(i);
    		child.validate();
    	}
    	if(e != null)
    		e.updateAfterEvent();
    }

    private void  adjustClipSizes (){
    	int n =headerDestinations.length ;
    	for(int i =0;i <n ;i ++){
    		Component child =accordion.getComponent(i );
    		int orderY =childrenOrderYs.get(i) ;
    		if(child.isVisible()){
    			child.setClipSize(new IntDimension(destSize.width, destSize.height - (child.getY()-orderY)));
    		}
    	}
    }

	//---------------------------LayoutManager Imp-------------------------------

	public void  addLayoutComponent (Component comp ,Object constraints ){
		synTabs();
	}

	public void  removeLayoutComponent (Component comp ){
		synTabs();
	}

	public void  invalidateLayout (Container target ){
	}

	public void  layoutContainer (Container target ){
    	synHeaderProperties();

    	Insets insets =accordion.getInsets ();
    	int i =0;
    	int x =insets.left ;
    	int y =insets.top ;
    	int w =accordion.getWidth ()-x -insets.right ;
    	int h =accordion.getHeight ()-y -insets.bottom ;
		Tab header ;
		Component tab ;
		IntDimension size ;

    	int count =accordion.getComponentCount ();
    	int selected =accordion.getSelectedIndex ();
    	if(selected < 0){
    		if(count > 0){
    			accordion.setSelectedIndex(0);
    		}
    		return;
    	}

    	headerDestinations = new Array(count);
    	childrenOrderYs = new Array(count);

    	int vX ,vY int ,vWidth ,vHeight ;
    	vHeight = h;
    	vWidth = w;
    	vX = x;
    	for(i=0; i<=selected; i++){
    		if (!accordion.isVisibleAt(i)) continue;
    		header = getHeader(i);
    		tab = header.getTabComponent();
    		size = tab.getPreferredSize();
    		tab.setSizeWH(w, size.height);
    		tab.setLocationXY(x, tab.getY());
    		accordion.getComponent(i).setLocationXY(x, tab.getY()+size.height);
    		headerDestinations.put(i,  y);
    		y += size.height;
    		childrenOrderYs.put(i,  y);
    		vHeight -= size.height;
    		if(i == selected){
    			header.setSelected(true);
    			accordion.getComponent(i).setVisible(true);
    		}else{
    			header.setSelected(false);
    		}
    		tab.validate();
    	}
    	vY = y;
    	for(i=selected+1; i<count; i++){
    		if (!accordion.isVisibleAt(i)) continue;
    		header = getHeader(i);
    		tab = header.getTabComponent();
    		y += tab.getPreferredSize().height;
    		childrenOrderYs.put(i,  y);
    	}

    	y = accordion.getHeight() - insets.bottom;
    	for(i=count-1; i>selected; i--){
    		if (!accordion.isVisibleAt(i)) continue;
    		header = getHeader(i);
    		tab = header.getTabComponent();
    		size = tab.getPreferredSize();
    		y -= size.height;
    		headerDestinations.put(i,  y);
    		tab.setSizeWH(w, size.height);
    		tab.setLocationXY(x, tab.getY());
    		accordion.getComponent(i).setLocationXY(x, tab.getY()+size.height);
    		header.setSelected(false);
    		vHeight -= size.height;
    		tab.validate();
    	}
    	destSize.setSizeWH(vWidth, vHeight);
    	for(i=0; i<count; i++){
    		if (!accordion.isVisibleAt(i)) continue;
    		if(accordion.getComponent(i).isVisible()){
    			accordion.getComponent(i).setSize(destSize);
    		}
    	}
    	motionTimer.start();
    	__onMotion(null);
    	ensureHeadersOnTopDepths();
	}

	public IntDimension  preferredLayoutSize (Container target ){
    	if(target === accordion){
    		synHeaderProperties();

	    	Insets insets =accordion.getInsets ();

	    	int w =0;
	    	int h =0;
	    	int i =0;
	    	IntDimension size ;

	    	for(i=accordion.getComponentCount()-1; i>=0; i--){
	    		size = accordion.getComponent(i).getPreferredSize();
	    		w = Math.max(w, size.width);
	    		h = Math.max(h, size.height);
	    	}

	    	for(i=accordion.getComponentCount()-1; i>=0; i--){
	    		size = getHeader(i).getTabComponent().getPreferredSize();
	    		w = Math.max(w, size.width);
	    		h += size.height;
	    	}

	    	return insets.getOutsideSize(new IntDimension(w, h));
    	}
    	return null;
	}

	public IntDimension  minimumLayoutSize (Container target ){
    	if(target === accordion){
    		synHeaderProperties();

	    	Insets insets =accordion.getInsets ();

	    	int w =0;
	    	int h =0;
	    	int i =0;
	    	IntDimension size ;

	    	for(i=accordion.getComponentCount()-1; i>=0; i--){
	    		size = accordion.getComponent(i).getMinimumSize();
	    		w = Math.max(w, size.width);
	    		h = Math.max(h, size.height);
	    	}

	    	for(i=accordion.getComponentCount()-1; i>=0; i--){
	    		size = getHeader(i).getTabComponent().getMinimumSize();
	    		w = Math.max(w, size.width);
	    		h += size.height;
	    	}

	    	return insets.getOutsideSize(new IntDimension(w, h));
    	}
    	return null;
	}

	public IntDimension  maximumLayoutSize (Container target )
	{
		return IntDimension.createBigDimension();
	}

	public double  getLayoutAlignmentX (Container target ){
		return 0;
	}

	public double  getLayoutAlignmentY (Container target ){
		return 0;
	}

	 public IntDimension  getMaximumSize (Component c ){
		return maximumLayoutSize(accordion);
	}

	 public IntDimension  getMinimumSize (Component c ){
		return minimumLayoutSize(accordion);
	}

	 public IntDimension  getPreferredSize (Component c ){
		return preferredLayoutSize(accordion);
	}

}


