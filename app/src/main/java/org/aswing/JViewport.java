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


import org.aswing.event.InteractiveEvent;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntPoint;
import org.aswing.geom.IntRectangle;
import org.aswing.plaf.basic.BasicViewportUI;

/**
 * Dispatched when the viewport's state changed. the state is all about:
 * <ul>
 * <li>view position</li>
 * <li>verticalUnitIncrement</li>
 * <li>verticalBlockIncrement</li>
 * <li>horizontalUnitIncrement</li>
 * <li>horizontalBlockIncrement</li>
 * </ul>
 * </p>
 * 
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * JViewport is an basic viewport to view normal components. Generally JViewport works 
 * with JScrollPane together, for example:<br>
 * <pre>
 *JScrollPane scrollPane =new JScrollPane ();
 *JViewport viewport =new JViewport(yourScrollContentComponent ,true ,false );
 *     scrollPane.setViewport(viewport);
 * </pre>
 * Then you'll get a scrollpane with scroll content and only vertical scrollbar. And 
 * the scroll content will always tracks the scroll pane width.
 * @author iiley
 */
public class JViewport extends Container implements Viewportable{
 	
 	/**
 	 * The default unit/block increment, it means auto count a value.
 	 */
 	public static  int AUTO_INCREMENT =int.MIN_VALUE ;
 	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static int CENTER =AsWingConstants.CENTER ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static int TOP =AsWingConstants.TOP ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int LEFT =AsWingConstants.LEFT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int BOTTOM =AsWingConstants.BOTTOM ;
 	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int RIGHT =AsWingConstants.RIGHT ;
 	
	private int verticalUnitIncrement ;
	private int verticalBlockIncrement ;
	private int horizontalUnitIncrement ;
	private int horizontalBlockIncrement ;
	
	private boolean tracksHeight ;
	private boolean tracksWidth ;
	
    private int verticalAlignment ;
    private int horizontalAlignment ;
	
	private Component view ;
	
	/**
	 * Create a viewport with view and size tracks properties.
	 * @see #setView()
	 * @see #setTracksWidth()
	 * @see #setTracksHeight()
	 */
	public  JViewport (Component view =null ,boolean tracksWidth =false ,boolean tracksHeight =false ){
		super();
		setName("JViewport");
		this.tracksWidth = tracksWidth;
		this.tracksHeight = tracksHeight;
		verticalUnitIncrement = AUTO_INCREMENT;
		verticalBlockIncrement = AUTO_INCREMENT;
		horizontalUnitIncrement = AUTO_INCREMENT;
		horizontalBlockIncrement = AUTO_INCREMENT;
		
    	verticalAlignment = CENTER;
    	horizontalAlignment = CENTER;
    	
		if(view != null) setView(view);
		setLayout(new ViewportLayout());
		updateUI();
	}
    
	 public void  updateUI (){
    	setUI(UIManager.getUI(this));
    }
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicViewportUI;
    }
	
	 public String  getUIClassID (){
		return "ViewportUI";
	}	

	/**
	 * @throws Error if the layout is not a ViewportLayout
	 */
	 public void  setLayout (LayoutManager layout ){
		if(layout is ViewportLayout){
			super.setLayout(layout);
		}else{
			throw new Error("Only on set ViewportLayout to JViewport");
		}
	}
	
	/**
	 * Sets whether the view tracks viewport width. Default is false<br>
	 * If true, the view will always be set to the same size as the viewport.<br>
	 * If false, the view will be set to it's preffered size.
	 * @param b tracks width
	 */
	public void  setTracksWidth (boolean b ){
		if(b != tracksWidth){
			tracksWidth = b;
			revalidate();
		}
	}
	
	/**
	 * Returns whether the view tracks viewport width. Default is false<br>
	 * If true, the view will always be set to the same width as the viewport.<br>
	 * If false, the view will be set to it's preffered width.
	 * @return whether tracks width
	 */
	public boolean  isTracksWidth (){
		return tracksWidth;
	}
	
	/**
	 * Sets whether the view tracks viewport height. Default is false<br>
	 * If true, the view will always be set to the same height as the viewport.<br>
	 * If false, the view will be set to it's preffered height.
	 * @param b tracks height
	 */
	public void  setTracksHeight (boolean b ){
		if(tracksHeight != b){
			tracksHeight = b;
			revalidate();
		}
	}
	
	/**
	 * Returns whether the view tracks viewport height. Default is false<br>
	 * If true, the view will always be set to the same height as the viewport.<br>
	 * If false, the view will be set to it's preffered height.
	 * @return whether tracks height
	 */
	public boolean  isTracksHeight (){
		return tracksHeight;
	}

    /**
     * Returns the vertical alignment of the view if the view is lower than extent height.
     *
     * @return the <code>verticalAlignment</code> property, one of the
     *		following values: 
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public int  getVerticalAlignment (){
        return verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment of the view if the view is lower than extent height.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public void  setVerticalAlignment (int alignment ){
        if (alignment == verticalAlignment){
        	return;
        }else{
        	verticalAlignment = alignment;
        	setViewPosition(getViewPosition());//make it to be restricted
        }
    }
    
    /**
     * Returns the horizontal alignment of the view if the view is narrower than extent width.
     * @return the <code>horizontalAlignment</code> property,
     *		one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     */
    public int  getHorizontalAlignment (){
        return horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment of the view if the view is narrower than extent width.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     */
    public void  setHorizontalAlignment (int alignment ){
        if (alignment == horizontalAlignment){
        	return;
        }else{
        	horizontalAlignment = alignment;
        	setViewPosition(getViewPosition());//make it to be restricted
        }
    }
		
	/**
	 * Sets the view component.
	 * 
	 * <p>The view is the visible content of the JViewPort.</p>
	 * 
	 * <p>JViewport use to manage the scroll view of a component.
	 * the component will be set size to its preferred size, then scroll in the viewport.<br>
	 * </p>
	 * <p>If the component's isTracksViewportWidth method is defined and return true,
	 * when the viewport's show size is larger than the component's,
	 * the component will be widen to the show size, otherwise, not widen.
	 * Same as isTracksViewportHeight method.
	 * </p>
	 */
	public void  setView (Component view ){
		if(this.view != view){
			this.view = view;
			removeAll();
			
			if(view != null){
				insertImp(-1, view);
			}
			fireStateChanged();
		}
	}
	
	public Component  getView (){
		return view;
	}
		
	/**
	 * Sets the unit value for the Vertical scrolling.
	 */
    public void  setVerticalUnitIncrement (int increment ){
    	if(verticalUnitIncrement != increment){
    		verticalUnitIncrement = increment;
			fireStateChanged();
    	}
    }
    
    /**
     * Sets the block value for the Vertical scrolling.
     */
    public void  setVerticalBlockIncrement (int increment ){
    	if(verticalBlockIncrement != increment){
    		verticalBlockIncrement = increment;
			fireStateChanged();
    	}
    }
    
	/**
	 * Sets the unit value for the Horizontal scrolling.
	 */
    public void  setHorizontalUnitIncrement (int increment ){
    	if(horizontalUnitIncrement != increment){
    		horizontalUnitIncrement = increment;
			fireStateChanged();
    	}
    }
    
    /**
     * Sets the block value for the Horizontal scrolling.
     */
    public void  setHorizontalBlockIncrement (int increment ){
    	if(horizontalBlockIncrement != increment){
    		horizontalBlockIncrement = increment;
			fireStateChanged();
    	}
    }		
			
	
	/**
	 * In fact just call setView(com) in this method
	 * @see #setView()
	 */
	 public void  append (Component com ,Object constraints =null ){
		setView(com);
	}
	
	/**
	 * In fact just call setView(com) in this method
	 * @see #setView()
	 */	
	 public void  insert (int i ,Component com ,Object constraints =null ){
		setView(com);
	}
	
	//--------------------implementatcion of Viewportable---------------

	/**
	 * Returns the unit value for the Vertical scrolling.
	 */
    public int  getVerticalUnitIncrement (){
    	if(verticalUnitIncrement != AUTO_INCREMENT){
    		return verticalUnitIncrement;
    	}else{
    		return Math.max(getExtentSize().height/40, 1);
    	}
    }
    
    /**
     * Return the block value for the Vertical scrolling.
     */
    public int  getVerticalBlockIncrement (){
    	if(verticalBlockIncrement != AUTO_INCREMENT){
    		return verticalBlockIncrement;
    	}else{
    		return getExtentSize().height-1;
    	}
    }
    
	/**
	 * Returns the unit value for the Horizontal scrolling.
	 */
    public int  getHorizontalUnitIncrement (){
    	if(horizontalUnitIncrement != AUTO_INCREMENT){
    		return horizontalUnitIncrement;
    	}else{
    		return Math.max(getExtentSize().width/40, 1);
    	}
    }
    
    /**
     * Return the block value for the Horizontal scrolling.
     */
    public int  getHorizontalBlockIncrement (){
    	if(horizontalBlockIncrement != AUTO_INCREMENT){
    		return horizontalBlockIncrement;
    	}else{
    		return getExtentSize().width - 1;
    	}
    }
    
    public void  setViewportTestSize (IntDimension s ){
    	setSize(s);
    }

	public IntDimension  getExtentSize (){
		return getInsets().getInsideSize(getSize());
	}
	
	/**
     * Usually the view's preffered size.
     * @return the view's size, (0, 0) if view is null.
	 */
	public IntDimension  getViewSize (){
		if(view == null){
			return new IntDimension();
		}else{
			if(isTracksWidth() && isTracksHeight()){
				return getExtentSize();
			}else{
				IntDimension viewSize =view.getPreferredSize ();
				IntDimension extentSize =getExtentSize ();
				if(isTracksWidth()){
					viewSize.width = extentSize.width;
				}else if(isTracksHeight()){
					viewSize.height = extentSize.height;
				}
				return viewSize;
			}
		}
	}
	
	/**
	 * Returns the view's position, if there is not any view, return (0,0).
	 * @return the view's position, (0,0) if view is null.
	 */
	public IntPoint  getViewPosition (){
		if(view != null){
			IntPoint p =view.getLocation ();
			IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
			p.x = ir.x - p.x;
			p.y = ir.y - p.y;
			return p;
		}else{
			return new IntPoint(0, 0);
		}
	}

	public void  setViewPosition (IntPoint p ,boolean programmatic =true ){
		restrictionViewPos(p);
		if(!p.equals(getViewPosition())){
			IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
			if(view){
				view.setLocationXY(ir.x-p.x, ir.y-p.y);
			}
			fireStateChanged(programmatic);
		}
	}

	public void  scrollRectToVisible (IntRectangle contentRect ,boolean programmatic =true ){
		setViewPosition(new IntPoint(contentRect.x, contentRect.y), programmatic);
	}
	
	/**
	 * Make a scroll or not to ensure specified rect will be visible.
	 * @param contentRect the rect to be ensure visible
	 * @programmatic whether or not a programmatic call
	 */
	public void  ensureRectVisible (IntRectangle contentRect ,boolean programmatic =true ){
		contentRect = contentRect.clone();
		IntPoint vp =getViewPosition ();
		IntDimension es =getExtentSize ();
		IntDimension vs =getViewSize ();
		IntRectangle range =new IntRectangle(vp.x ,vp.y ,es.width ,es.height );
		if(contentRect.x < 0){
			contentRect.width += contentRect.x;
			contentRect.x = 0;
		}
		if(contentRect.y < 0){
			contentRect.height += contentRect.y;
			contentRect.y = 0;
		}
		if(contentRect.x + contentRect.width > vs.width){
			contentRect.width = vs.width - contentRect.x;
		}
		if(contentRect.y + contentRect.height > vs.height){
			contentRect.height = vs.height - contentRect.y;
		}
		IntPoint newVP =vp.clone ();
		if(contentRect.x + contentRect.width > range.x + range.width){
			newVP.x = contentRect.x + contentRect.width - es.width;
		}
		if(contentRect.y + contentRect.height > range.y + range.height){
			newVP.y = contentRect.y + contentRect.height - es.height;
		}
		if(contentRect.x < range.x){
			newVP.x = contentRect.x;
		}
		if(contentRect.y < range.y){
			newVP.y = contentRect.y;
		}
		setViewPosition(newVP, programmatic);
	}
	
	/**
	 * Scrolls view vertical with delta pixels.
	 */
	public void  scrollVertical (int delta ){
		setViewPosition(getViewPosition().move(0, delta));
	}
	
	/**
	 * Scrolls view horizontal with delta pixels.
	 */
	public void  scrollHorizontal (int delta ){
		setViewPosition(getViewPosition().move(delta, 0));
	}
	
	/**
	 * Scrolls to view bottom left content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */
	public void  scrollToBottomLeft (){
		setViewPosition(new IntPoint(0, int.MAX_VALUE));
	}
	/**
	 * Scrolls to view bottom right content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToBottomRight (){
		setViewPosition(new IntPoint(int.MAX_VALUE, int.MAX_VALUE));
	}
	/**
	 * Scrolls to view top left content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToTopLeft (){
		setViewPosition(new IntPoint(0, 0));
	}
	/**
	 * Scrolls to view to right content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToTopRight (){
		setViewPosition(new IntPoint(int.MAX_VALUE, 0));
	}
	
	/**
	 * Restrict the view pos in valid range based on the align.
	 */	
	protected IntPoint  restrictionViewPos (IntPoint p ){
		IntDimension showSize =getExtentSize ();
		IntDimension viewSize =getViewSize ();
		if(showSize.width < viewSize.width){
			p.x = Math.max(0, Math.min(viewSize.width-showSize.width, p.x));
		}else if(showSize.width > viewSize.width){
			if(horizontalAlignment == CENTER){
				p.x = -(showSize.width - viewSize.width)/2;
			}else if(horizontalAlignment == RIGHT){
				p.x = -(showSize.width - viewSize.width);
			}else{
				p.x = 0;
			}
		}else{//equals
			p.x = 0;
		}
		
		if(showSize.height < viewSize.height){
			p.y = Math.max(0, Math.min(viewSize.height-showSize.height, p.y));
		}else if(showSize.height > viewSize.height){
			if(verticalAlignment == CENTER){
				p.y = -(showSize.height - viewSize.height)/2;
			}else if(verticalAlignment == BOTTOM){
				p.y = -(showSize.height - viewSize.height);
			}else{
				p.y = 0;
			}
		}else{//equals
			p.y = 0;
		}
		return p;
	}
    	
	/**
	 * Add a listener to listen the viewpoat state change event.
	 * <p>
	 * When the viewpoat's state changed, the state is all about:
	 * <ul>
	 * <li>viewPosition</li>
	 * <li>verticalUnitIncrement</li>
	 * <li>verticalBlockIncrement</li>
	 * <li>horizontalUnitIncrement</li>
	 * <li>horizontalBlockIncrement</li>
	 * </ul>
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	public void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, listener, false, priority);
	}	
	
	/**
	 * Removes a state listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */	
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}
	
	protected void  fireStateChanged (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
	
	public Component  getViewportPane (){
		return this;
	}
}


