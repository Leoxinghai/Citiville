/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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

	
import flash.display.StageScaleMode;
import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.BasicFrameUI;
import org.aswing.resizer.*;

/**
 * Dispatched when the frame's state changed. the state is all about:
 * <ul>
 * <li>NORMAL</li>
 * <li>ICONIFIED</li>
 * <li>MAXIMIZED</li>
 * <li>MAXIMIZED_HORIZ</li>
 * <li>MAXIMIZED_VERT</li>
 * </ul>
 * </p>
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * Dispatched when the frame be iconified.
 * @eventType org.aswing.event.FrameEvent.FRAME_ICONIFIED
 */
.get(Event(name="frameIconified", type="org.aswing.event.FrameEvent"))
	
/**
 * Dispatched when the frame be restored.
 * @eventType org.aswing.event.FrameEvent.FRAME_RESTORED
 */
.get(Event(name="frameRestored", type="org.aswing.event.FrameEvent"))

/**
 * Dispatched when the frame be maximized.
 * @eventType org.aswing.event.FrameEvent.FRAME_MAXIMIZED
 */
.get(Event(name="frameMaximized", type="org.aswing.event.FrameEvent"))

/**
 * Dispatched when the frame is closing by user.
 * @eventType org.aswing.event.FrameEvent.FRAME_CLOSING
 */
.get(Event(name="frameClosing", type="org.aswing.event.FrameEvent"))

/**
 * Dispatched When the frame's ability changed. Include:
 * <ul>
 * <li> resizable </li>
 * <li> closable </li>
 * <li> dragable </li>
 * </ul>
 * @eventType org.aswing.event.FrameEvent.FRAME_ABILITY_CHANGED
 */
.get(Event(name="frameAbilityChanged", type="org.aswing.event.FrameEvent"))

/**
 * JFrame is a window with title and maximized/iconified/normal state, and resizer. 
 * @author iiley
 */
public class JFrame extends JWindow{
		
	/**
	 * @see #setState()
	 */
	public static  int NORMAL =0;//0
	/**
	 * @see #setState()
	 */
	public static  int ICONIFIED =2;//10
	/**
	 * @see #setState()
	 */
	public static  int MAXIMIZED_HORIZ =4;//100
	/**
	 * @see #setState()
	 */
	public static  int MAXIMIZED_VERT =8;//1000
	/**
	 * @see #setState()
	 */
	public static  int MAXIMIZED =12;//1100
	//-----------------------------------------
	
	/**
	 * @see #setDefaultCloseOperation()
	 */
	public static  int DO_NOTHING_ON_CLOSE =0;
	/**
	 * @see #setDefaultCloseOperation()
	 */
	public static  int HIDE_ON_CLOSE =1;
	/**
	 * @see #setDefaultCloseOperation()
	 */
	public static  int DISPOSE_ON_CLOSE =2;
	
	/**
	 * For title bar changed event property name.
	 */
	public static  String PROPERTY_TITLE_BAR ="titleBar";
	//--------------------------------------------------------
	
	protected FrameTitleBar titleBar ;
	protected String title ;
	protected Icon icon ;
	protected int state ;
	protected int defaultCloseOperation ;
	protected IntRectangle maximizedBounds ;
	protected IntRectangle lastNormalStateBounds ;
	
	protected boolean dragable ;
	protected boolean resizable ;
	protected boolean closable ;
	protected boolean dragDirectly ;
	protected boolean dragDirectlySet ;
	
	protected Resizer resizer ;
	protected ResizerController resizerController ;
	
	/**
	 * Create a JWindow
	 * @param owner the owner of this popup, it can be a DisplayObjectContainer or a JPopup, default it is default 
	 * is <code>AsWingManager.getRoot()</code>
	 * @param title the title, default is "".
	 * @param modal true for a modal dialog, false for one that allows other windows to be active at the same time,
	 *  default is false.
	 * @see org.aswing.AsWingManager#getRoot()
	 * @throw AsWingManagerNotInited if not specified the owner, and aswing default root is not specified either.
	 * @throw TypeError if the owner is not a JPopup nor DisplayObjectContainer
	 */	
	( = JFrameownernull,Stringtitle="",booleanmodal=false){
		super(owner, modal);
		
		this.title = title;
		
		state = NORMAL;
		defaultCloseOperation = DISPOSE_ON_CLOSE;
		dragable  = true;
		resizable = true;
		closable  = true;
		icon = DefaultEmptyDecoraterResource.INSTANCE;
		lastNormalStateBounds = new IntRectangle(0, 0, 200, 100);
		setName("JFrame");
		addEventListener(Event.ADDED_TO_STAGE, __frameAddedToStage);
		addEventListener(Event.REMOVED_FROM_STAGE, __frameRemovedFromStage);
		addEventListener(MovedEvent.MOVED, __frameMoved);
		updateUI();
		setTitleBar(new JFrameTitleBar());
	}
	
	 public void  updateUI (){
    	setUI(UIManager.getUI(this));
    }
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicFrameUI;
    }
    
	
	/**
	 * Sets the ui.
	 * <p>
	 * JFrame ui should implemented <code>FrameUI</code> interface!
	 * </p>
	 * @param newUI the newUI
	 * @throws ArgumentError when the newUI is not an <code>FrameUI</code> instance.
	 */
     public void  setUI (ComponentUI newUI ){
    	if(newUI is FrameUI){
    		super.setUI(newUI);
    		if(getTitleBar()){
    			getTitleBar().updateUIPropertiesFromOwner();
    		}
    	}else{
    		throw new ArgumentError("JFrame just accept FrameUI instance!!!");
    	}
    }
    
    public FrameTitleBar  getTitleBar (){
    	return titleBar;
    }
    
    public void  setTitleBar (FrameTitleBar t ){
    	if(titleBar != t){
    		FrameTitleBar old =titleBar ;
    		if(titleBar){
    			titleBar.setFrame(null);
    			remove(titleBar.getSelf());
    		}
    		titleBar = t;
    		if(titleBar){
    			titleBar.setText(getTitle());
    			titleBar.setIcon(getIcon());
	    		insert(0, titleBar.getSelf(), WindowLayout.TITLE);
	    		titleBar.setFrame(this);
    		}
    		dispatchEvent(new PropertyChangeEvent(PROPERTY_TITLE_BAR, old, t));
    	}
    }
    
    /**
     * Returns the ui for this frame with <code>FrameUI</code> instance
     * @return the frame ui.
     */
    public FrameUI  getFrameUI (){
    	return getUI() as FrameUI;
    }
    
	 public String  getUIClassID (){
		return "FrameUI";
	}
		
	/**
	 * Sets the text to be displayed in the title bar for this frame.
	 * @param t the text to be displayed in the title bar, 
	 * null to display no text in the title bar.
	 */
	public void  setTitle (String t ){
		if(title != t){
			title = t;
			if(getTitleBar()){
				getTitleBar().setText(t);
			}
			repaint();
			revalidate();
		}
	}
	
	/**
	 * Returns the text displayed in the title bar for this frame.
	 * @return the text displayed in the title bar for this frame.
	 */
	public String  getTitle (){
		return title;
	}
	
	/**
	 * Sets the icon to be displayed in the title bar for this frame.
	 * @param ico the icon to be displayed in the title bar, 
	 * null to display no icon in the title bar.
	 */	
	public void  setIcon (Icon ico ){
		if(icon != ico){
			icon = ico;
			if(getTitleBar()){
				getTitleBar().setIcon(ico);
			}
			repaint();
			revalidate();
		}
	}
	
	/**
	 * Returns the icon displayed in the title bar for this frame.
	 * @return the icon displayed in the title bar for this frame.
	 */
	public Icon  getIcon (){
		return icon;
	}
		
	/**
	 * Sets whether this frame is resizable by the user.
	 * 
	 * <p>"resizable" means include capability of restore normal resize, maximize, iconified and resize by drag.
	 * @param b true user can resize the frame by click resize buttons or drag to scale the frame, false user can't.
	 * @see #isResizable()
	 */
	public void  setResizable (boolean b ){
		if(resizable != b){
			resizable = b;
			getResizer().setEnabled(b);
			repaint();
			dispatchEvent(new FrameEvent(FrameEvent.FRAME_ABILITY_CHANGED, true));
		}
	}
	
	/**
	 * Returns whether this frame is resizable by the user. By default, all frames are initially resizable. 
	 * 
	 * <p>"resizable" means include capability of restore normal resize, maximize, iconified and resize by drag.
	 * @see #setResizable()
	 */
	public boolean  isResizable (){
		return resizable;
	}
	
	/**
	 * Sets whether this frame can be dragged by the user.  By default, it's true.
	 * 
	 * <p>"dragable" means drag to move the frame.
	 * @param b 
	 * @see #isDragable()
	 */
	public void  setDragable (boolean b ){
		if(dragable != b){
			dragable = b;
			repaint();
			revalidate();
			dispatchEvent(new FrameEvent(FrameEvent.FRAME_ABILITY_CHANGED, true));
		}
	}
	
	/**
	 * Returns whether this frame can be dragged by the user. By default, it's true.
	 * @see #setDragnable()
	 */
	public boolean  isDragable (){
		return dragable;
	}
	

	/**
	 * Sets whether this frame can be closed by the user. By default, it's true.
	 * Whether the frame will be hide or dispose, depend on the value returned by <code>getDefaultCloseOperation</code>.
	 * @param b true user can click close button to generate the close event, false user can't.
	 * @see #getClosable()
	 */	
	public void  setClosable (boolean b ){
		if(closable != b){
			closable = b;
			repaint();
			dispatchEvent(new FrameEvent(FrameEvent.FRAME_ABILITY_CHANGED, true));
		}
	}
	
	/**
	 * Returns whether this frame can be closed by the user. By default, it's true.
	 * @see #setClosable()
	 */		
	public boolean  isClosable (){
		return closable;
	}
	
	/**
	 * Only did effect when state is <code>NORMAL</code>
	 */
	 public void  pack (){
		if(getState() == NORMAL){
			super.pack();
		}
	}
	
	/**
	 * Gets maximized bounds for this frame.<br>
	 * If the maximizedBounds was setted by setMaximizedBounds it will return the setted value.
	 * else if the owner is a JWindow it will return the owner's content pane's bounds, if
	 * the owner is a movieclip it will return the movie's stage bounds.
	 */
	public IntRectangle  getMaximizedBounds (){
		if(maximizedBounds == null){
			IntRectangle b =AsWingUtils.getVisibleMaximizedBounds(this.parent );
			return getInsets().getOutsideBounds(b);
		}else{
			return maximizedBounds.clone();
		}
	}
	
	/**
	 * Sets the maximized bounds for this frame. 
	 * <br>
	 * @param b bounds for the maximized state, null to back to use default bounds descripted in getMaximizedBounds's comments.
	 * @see #getMaximizedBounds()
	 */
	public void  setMaximizedBounds (IntRectangle b ){
		if(b != null){
			maximizedBounds = b.clone();
			revalidate();
		}else{
			maximizedBounds = null;
		}
	}	

    /**                   
     * Sets the operation that will happen by default when
     * the user initiates a "close" on this frame.
     * You must specify one of the following choices:
     * <p>
     * <ul>
     * <li><code>DO_NOTHING_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Don't do anything; require the
     * program to handle the operation in the <code>windowClosing</code>
     * method of a registered EventListener object.
     *
     * <li><code>HIDE_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Automatically hide the frame after
     * invoking any registered EventListener objects.
     *
     * <li><code>DISPOSE_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Automatically hide and dispose the 
     * frame after invoking any registered EventListener objects.
     * </ul>
     * <p>
     * The value is set to <code>DISPOSE_ON_CLOSE</code> by default.
     * if you set a value is not three of them, think of it is will be changed to default value.
     * @param operation the operation which should be performed when the
     *        user closes the frame
     * @see org.aswing.Component#addEventListener()
     * @see #getDefaultCloseOperation()
     */
    public void  setDefaultCloseOperation (int operation ){
    	if(operation != DO_NOTHING_ON_CLOSE 
    		&& operation != HIDE_ON_CLOSE
    		&& operation != DISPOSE_ON_CLOSE)
    	{
    			operation = DISPOSE_ON_CLOSE;
    	}
    	defaultCloseOperation = operation;
    }
    
	/**
	 * Returns the operation that will happen by default when
     * the user initiates a "close" on this frame.
	 * @see #setDefaultCloseOperation()
	 */
	public int  getDefaultCloseOperation (){
		return defaultCloseOperation;
	}
	
	public void  setState (int s ,boolean programmatic =true ){
		if(state != s){
			if(state == NORMAL){
				lastNormalStateBounds.setRect(getComBounds());
			}
			state = s;
			fireStateChanged();
			if(state == ICONIFIED){
				precessIconified(programmatic);
			}else if(((state & MAXIMIZED_HORIZ) == MAXIMIZED_HORIZ) || ((state & MAXIMIZED_VERT) == MAXIMIZED_VERT)){
				precessMaximized(programmatic);
			}else{
				precessRestored(programmatic);
			}
			doStateChange();
		}
	}
	
	protected boolean  isMaximized (){
		return ((state & MAXIMIZED_HORIZ) == MAXIMIZED_HORIZ)
			|| ((state & MAXIMIZED_VERT) == MAXIMIZED_VERT);
	}
	
	
	protected void  doStateChange (){
		if(state == ICONIFIED){
			IntDimension iconifiedSize =new IntDimension(60,20);
			if(titleBar){
				iconifiedSize = titleBar.getSelf().getMinimumSize();
			}
			setSize(getInsets().getOutsideSize(iconifiedSize));
    		IntRectangle frameMaxBounds =getMaximizedBounds ();
			if(x < frameMaxBounds.x){
				x = frameMaxBounds.x;
			}
		}else if(state == NORMAL){
			setBounds(lastNormalStateBounds);
		}else{
			setSizeToFixMaxmimized();
		}
		if(getResizer() != null){
			getResizer().setEnabled(isResizable() && state == JFrame.NORMAL);
		}
		revalidateIfNecessary();
	}
	
	private void  __frameMoved (MovedEvent e ){
		if(state == ICONIFIED){
			lastNormalStateBounds.setLocation(e.getNewLocation());
		}
	}
	
	private void  __frameAddedToStage (Event e ){
		stage.addEventListener(Event.RESIZE, __frameStageResized, false, 0, true);
	}
	
	private void  __frameRemovedFromStage (Event e ){
		stage.removeEventListener(Event.RESIZE, __frameStageResized);
	}
	
	private void  __frameStageResized (Event e =null ){
		if(stage == null || stage.scaleMode != StageScaleMode.NO_SCALE){
			return;
		}
		if(isMaximized()){
			setSizeToFixMaxmimized();
			revalidateIfNecessary();
		}
	}
	
	protected void  setSizeToFixMaxmimized (){
		IntRectangle maxBounds =getMaximizedBounds ();
		IntRectangle b =getComBounds ();
		if((state & MAXIMIZED_HORIZ) == MAXIMIZED_HORIZ){
			b.x = maxBounds.x;
			b.width = maxBounds.width;
		}
		if((state & JFrame.MAXIMIZED_VERT) == JFrame.MAXIMIZED_VERT){
			b.y = maxBounds.y;
			b.height = maxBounds.height;
		}
		setBounds(b);
	}
		
	/**
	 * Do the precesses when iconified.
	 */
	protected void  precessIconified (boolean programmatic =true ){
		doSubPopusVisible();
		dispatchEvent(new FrameEvent(FrameEvent.FRAME_ICONIFIED, programmatic));
	}
	/**
	 * Do the precesses when restored.
	 */
	protected void  precessRestored (boolean programmatic =true ){
		doSubPopusVisible();
		dispatchEvent(new FrameEvent(FrameEvent.FRAME_RESTORED, programmatic));
	}
	/**
	 * Do the precesses when maximized.
	 */
	protected void  precessMaximized (boolean programmatic =true ){
		doSubPopusVisible();
		dispatchEvent(new FrameEvent(FrameEvent.FRAME_MAXIMIZED, programmatic));
	}
	
	private void  doSubPopusVisible (){
		Array owneds =getOwnedEquipedPopups ();
		for(int i =0;i <owneds.length ;i ++){
			JPopup pop =owneds.get(i) ;
			pop.getGroundContainer().visible = pop.shouldGroundVisible();
		}
	}
		
	 internal boolean  shouldOwnedPopupGroundVisible (JPopup popup ){
		if(getState() == ICONIFIED){
			return false;
		}
		return super.shouldOwnedPopupGroundVisible(popup);
	}
	
	public int  getState (){
		return state;
	}
	
	public void  setResizer (Resizer r ){
		if(r != resizer){
			resizer = r;
			if(resizerController == null){
				resizerController = ResizerController.create(this, r);
			}else{
				resizerController.setResizer(resizer);
			}
			resizerController.setResizable(isResizable());
		}
	}
	
	public Resizer  getResizer (){
		return resizer;
	}
	
	/**
	 * Indicate whether need resize frame directly when drag the resizer arrow.
	 * if set to false, there will be a rectange to represent then size what will be resized to.
	 * if set to true, the frame will be resize directly when drag, but this is need more cpu counting.<br>
	 * Default is false.
	 * @see org.aswing.Resizer#setResizeDirectly()
	 */
	public void  setResizeDirectly (boolean b ){
		if(resizerController){
			resizerController.setResizeDirectly(b);
		}
	}
	
	/**
	 * Return whether need resize frame directly when drag the resizer arrow.
	 * @see #setResizeDirectly()
	 */
	public boolean  isResizeDirectly (){
		if(resizerController){
			return resizer.isResizeDirectly();
		}else{
			return false;
		}
	}
	
	/**
	 * Indicate whether need move frame directly when drag the frame.
	 * if set to false, there will be a rectange to represent then bounds what will be move to.
	 * if set to true, the frame will be move directly when drag, but this is need more cpu counting.<br>
	 * Default is false.
	 */	
	public void  setDragDirectly (boolean b ){
		dragDirectly = b;
		setDragDirectlySet(true);
	}
	
	/**
	 * Return whether need move frame directly when drag the frame.
	 * @see #setDragDirectly()
	 */	
	public boolean  isDragDirectly (){
		return dragDirectly;
	}
	
	/**
	 * Sets is dragDirectly property is set by user.
	 */
	public void  setDragDirectlySet (boolean b ){
		dragDirectlySet = b;
	}
	
	/**
	 * Return is dragDirectly property is set by user.
	 */	
	public boolean  isDragDirectlySet (){
		return dragDirectlySet;
	}
	
	/**
	 * User pressed close button to close the Frame depend on the <code>defaultCloseOperation</code>
	 * <p>
	 * This method will fire a <code>FrameEvent.FRAME_CLOSING</code> event.
	 * </p>
	 * @see #tryToClose()
	 */
	public void  closeReleased (){
		dispatchEvent(new FrameEvent(FrameEvent.FRAME_CLOSING, false));
		tryToClose();
	}
	
	/**
	 * Try to close the Frame depend on the <code>defaultCloseOperation</code>
	 * @see #closeReleased()
	 */
	public void  tryToClose (){
		if(defaultCloseOperation == HIDE_ON_CLOSE){
			hide();
		}else if(defaultCloseOperation == DISPOSE_ON_CLOSE){
			dispose();
		}		
	}
	
	protected void  fireStateChanged (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
	
	 protected void  initModalMC (){
		super.initModalMC();
		getModalMC().addEventListener(MouseEvent.MOUSE_DOWN, __flashModelFrame);
	}
	
	private void  __flashModelFrame (MouseEvent e ){
		if(getFrameUI() != null){
			getFrameUI().flashModalFrame();
		}
	}
}


