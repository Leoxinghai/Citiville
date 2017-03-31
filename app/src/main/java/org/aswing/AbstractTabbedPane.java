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

	
import flash.events.Event;

import org.aswing.event.*;
import org.aswing.plaf.*;
import org.aswing.util.*;

/**
 *  Dispatched when the selected tab changed.
 *
 *  @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * An abstract class for all Container pane class that have title, icon, tip for every sub pane.
 * For example JAccordion, JTabbedPane.
 * @author iiley
 */
public class AbstractTabbedPane extends Container{
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int CENTER =AsWingConstants.CENTER ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int TOP =AsWingConstants.TOP ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int LEFT =AsWingConstants.LEFT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int BOTTOM =AsWingConstants.BOTTOM ;
 	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int RIGHT =AsWingConstants.RIGHT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */        
	public static  int HORIZONTAL =AsWingConstants.HORIZONTAL ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int VERTICAL =AsWingConstants.VERTICAL ;
	
    private Array titles ;
    private Array icons ;
    private Array tips ;
    private Array enables ;
    private Array visibles ;
    private SingleSelectionModel model ;
    
	// Icon/Label Alignment
    private int verticalAlignment ;
    private int horizontalAlignment ;
    private int verticalTextPosition ;
    private int horizontalTextPosition ;
    private int iconTextGap ;
    private Insets margin ;
    
	public  AbstractTabbedPane (){
		super();
		//default
    	verticalAlignment = CENTER;
    	horizontalAlignment = CENTER;
    	verticalTextPosition = CENTER;
    	horizontalTextPosition = RIGHT;
    	iconTextGap = 4;
    	
		titles = new Array();
		icons = new Array();
		tips = new Array();
		enables = new Array();
		visibles = new Array();
		setModel(new DefaultSingleSelectionModel());
	}
	
    /**
     * Sets the model to be used with this tabbedpane.
     * @param model the model to be used
     * @see #getModel()
     */
	public void  setModel (SingleSelectionModel model ){
        SingleSelectionModel oldModel =getModel ();
        if (oldModel != null) {
            oldModel.removeStateListener(__modelStateChanged);
        }
        this.model = model;
        if (model != null) {
            model.addStateListener(__modelStateChanged);
        }
        repaint();
	}
	
    /**
     * Returns the model associated with this tabbedpane.
     * @see #setModel()
     */
	public SingleSelectionModel  getModel (){
		return model;
	}
	
	/**
	 * Adds a listener to listen the tab selection change event.
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
	
	/**
	 * Adds a component to the tabbedpane. 
	 * If constraints is a String or an Icon or an Object(object.toString() as a title), 
	 * it will be used for the tab title, 
	 * otherwise the component's name will be used as the tab title. 
	 * Shortcut of <code>insert(-1, com, constraints)</code>. 
	 * @param com  the component to be displayed when this tab is clicked
	 * @param constraints  the object to be displayed in the tab
	 * @see Container#append()
	 * @see #insert()
	 * @see #insertTab()
	 */
	 public void  append (Component com ,Object constraints =null ){
		insert(-1, com, constraints);
	}
	
	/**
	 * Adds a component to the tabbedpane with spesified index.
	 * If constraints is a String or an Icon or an Object(object.toString() as a title), 
	 * it will be used for the tab title, 
	 * otherwise the component's name will be used as the tab title. 
	 * Cover method for insertTab. 
	 * @param i index the position at which to insert the component, or less than 0 value to append the component to the end 
	 * @param com the component to be added
	 * @param constraints the object to be displayed in the tab
	 * @see Container#insert()
	 * @see #insertTab()
	 */
	 public void  insert (int i ,Component com ,Object constraints =null ){
		insertImp(i, com, constraints);
	}
	
	
	/**
	 * This will call insertTab()
	 */
	 protected void  insertImp (int i ,Component com ,Object constraints =null ){
		String title =null ;
		Icon icon =null ;
		if(constraints == null){
			title = com.getName();
		}else if(constraints is String){
			title =(String) constraints;
		}else if(constraints is Icon){
			icon = Icon(constraints);
		}else{
			title = constraints.toString();
		}
		insertTab(i, com, title, icon, null);
	}	
	
	/**
	 * Adds a component and tip represented by a title and/or icon, either of which can be null.
	 * Shortcut of <code>insertTab(-1, com, title, icon, tip)</code>
	 * @param com The component to be displayed when this tab is clicked
	 * @param title the title to be displayed in this tab
	 * @param icon the icon to be displayed in this tab
	 * @param tip the tooltip to be displayed for this tab, can be null means no tool tip.
	 */
	public void  appendTab (Component com ,String title ="",Icon icon =null ,String tip =null ){
		insertTab(-1, com, title, icon, tip);
	}
	
	/**
	 * Inserts a component, at index, represented by a title and/or icon, 
	 * either of which may be null.
	 * @param i the index position to insert this new tab, less than 0 means append to the end.
	 * @param com The component to be displayed when this tab is clicked
	 * @param title the title to be displayed in this tab
	 * @param icon the icon to be displayed in this tab
	 * @param tip the tooltip to be displayed for this tab, can be null means no tool tip.
	 * @throws RangeError when index > children count
	 */
	public void  insertTab (int i ,Component com ,String title ="",Icon icon =null ,String tip =null ){
		if(i > getComponentCount()){
			throw new RangeError("illegal component position when insert comp to container");
		}
		if(i < 0){
			i = getComponentCount();
		}
		insertProperties(i, title, icon, tip);
		int currentSelectedIndex =getSelectedIndex ();
		int selectedIndexAfterRemove =currentSelectedIndex ;
		if(i <= currentSelectedIndex){
			selectedIndexAfterRemove = currentSelectedIndex + 1;
		}else if(currentSelectedIndex < 0){
			selectedIndexAfterRemove = i;
		}
		super.insertImp(i, com);
		getModel().setSelectedIndex(selectedIndexAfterRemove);
	}
	
	protected void  insertProperties (int i ,String title ="",Icon icon =null ,String tip =null ){
		insertToArray(titles, i, title);
		insertToArray(icons, i, icon);
		insertToArray(tips, i, tip);
		insertToArray(enables, i, true);
		insertToArray(visibles, i, true);
	}
	
	/**
	 * This will call removeTabAt()
	 */	
	 protected Component  removeAtImp (int i ){
		return removeTabAt(i);
	}
	
	/**
	 * Removes the specified child component.
	 * After the component is removed, its visibility is reset to true to ensure it will be visible if added to other containers. 
	 * @param i the index of component.
	 * @return the component just removed, or null there is not component at this position.
	 */
	public Component  removeTabAt (int i ){
		if(i >= getComponentCount() || getComponentCount() < 0){
			return null;
		}
		
		removeProperties(i);
		
		int currentSelectedIndex =getSelectedIndex ();
		int selectedIndexAfterRemove =currentSelectedIndex ;
		if(i == currentSelectedIndex){
			selectedIndexAfterRemove = -1;
		}else if(i < currentSelectedIndex){
			selectedIndexAfterRemove = currentSelectedIndex - 1;
		}
		Component rc =super.removeAtImp(i );
		rc.setVisible(true);
		
		if(selectedIndexAfterRemove < 0){
			getModel().clearSelection();
		}else{
			getModel().setSelectedIndex(selectedIndexAfterRemove);
		}
		
		return rc;
	}
	
	protected void  removeProperties (int i ){
		removeFromArray(titles, i);
		removeFromArray(icons, i);
		removeFromArray(tips, i);
		removeFromArray(enables, i);
		removeFromArray(visibles, i);
	}
	
	/**
	 * Sets whether or not the tab at index is enabled. 
	 * Nothing will happen if there is no tab at that index.
	 * @param index the tab index which should be enabled/disabled
	 * @param enabled whether or not the tab should be enabled 
	 */
	public void  setEnabledAt (int index ,boolean enabled ){
		if(enables.get(index) != enabled){
			enables.put(index,  enabled);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Returns whether or not the tab at index is currently enabled. 
	 * false will be returned if there is no tab at that index.
	 * @param index  the index of the item being queried 
	 * @return if the tab at index is enabled; false otherwise.
	 */
	public boolean  isEnabledAt (int index ){
		return enables.get(index) == true;
	}

	/**
	 * Sets whether or not the tab at index is visible. 
	 * Nothing will happen if there is no tab at that index.
	 * @param index the tab index which should be shown/hidden
	 * @param shown whether or not the tab should be visible 
	 */
	public void  setVisibleAt (int index ,boolean visible ){
		if(visibles.get(index) != visible){
			visibles.put(index,  visible);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Returns whether or not the tab at index is currently visible. 
	 * false will be returned if there is no tab at that index.
	 * @param index  the index of the item being queried 
	 * @return if the tab at index is visible; false otherwise.
	 */
	public boolean  isVisibleAt (int index ){
		return visibles.get(index) == true;
	}
	
	/**
	 * Removes the specified child component.
	 * After the component is removed, its visibility is reset to true to ensure it will be visible if added to other containers. 
	 * 
	 * Cover method for removeTabAt. 
	 * @see Container#remove()
	 * @see #removeTabAt()
	 */
	 public Component  remove (Component com ){
		int index =getIndex(com );
		if(index >= 0){
			return removeAt(index);
		}
		return null;
	}
	
	/**
	 * Removes the specified index child component. 
	 * After the component associated with index is removed, its visibility is reset to true to ensure it will be visible if added to other containers.
	 * Cover method for removeTabAt. 
	 * @see #removeTabAt() 
	 * @see Container#removeAt()
	 */	
	 public Component  removeAt (int index ){
		return removeAtImp(index);
	}
	
	/**
	 * Remove all child components.
	 * After the component is removed, its visibility is reset to true to ensure it will be visible if added to other containers. 
	 * @see #removeAt()
	 * @see #removeTabAt()
	 * @see Container#removeAll()
	 */
	 public void  removeAll (){
		while(children.length > 0){
			removeAt(children.length - 1);
		}
	}
	
	/**
	 * Returns the count of tabs.
	 */
	public int  getTabCount (){
		return getComponentCount();
	}
	
	/**
	 * Returns the tab title at specified index. 
	 * @param i the index
	 * @return the tab title
	 */
	public String  getTitleAt (int i ){
		return titles.get(i);
	}
	
	/**
	 * Returns the tab icon at specified index. 
	 * @param i the index
	 * @return the tab icon
	 */	
	public Icon  getIconAt (int i ){
		return Icon(icons.get(i));
	}
	
	/**
	 * Returns the tab tool tip text at specified index. 
	 * @param i the index
	 * @return the tab tool tip text
	 */	
	public String  getTipAt (int i ){
		return tips.get(i);
	}
	
	/**
	 * Sets the title at index to title which can be null.
	 * Nothing will happen if there is no tab at that index. 
	 * @param i the index
	 * @param t the tab title
	 */
	public void  setTitleAt (int i ,String t ){
		if(i < 0 || i >= getComponentCount()){
			return;
		}
		if(titles.get(i) != t){
			titles.put(i,  t);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Sets the icon at index to tab icon which can be null.
	 * Nothing will happen if there is no tab at that index. 
	 * @param i the index
	 * @param icon the tab icon
	 */	
	public void  setIconAt (int i ,Icon icon ){
		if(i < 0 || i >= getComponentCount()){
			return;
		}
		if(icons.get(i) != icon){
			//uninstallIconWhenNextPaint(icons.get(i));
			icons.put(i,  icon);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Sets the tool tip at index to tab tooltip which can be null.
	 * Nothing will happen if there is no tab at that index. 
	 * @param i the index
	 * @param t the tab tool tip
	 */	
	public void  setTipAt (int i ,String t ){
		if(i < 0 || i >= getComponentCount()){
			return;
		}
		if(tips.get(i) != t){
			tips.put(i,  t);
			revalidate();
			repaint();
		}
	}	
	
	/**
	 * Returns the first tab index with a given title, or -1 if no tab has this title. 
	 * @param title the title for the tab 
	 * @return the first tab index which matches title, or -1 if no tab has this title
	 */
	public int  indexOfTitle (String title ){
		return ArrayUtils.indexInArray(titles, title);
	}
	
	/**
	 * Returns the first tab index with a given icon, or -1 if no tab has this icon. 
	 * @param title the title for the tab 
	 * @return the first tab index which matches icon, or -1 if no tab has this icon
	 */	
	public int  indexOfIcon (Icon icon ){
		return ArrayUtils.indexInArray(icons, icon);
	}
	
	/**
	 * Returns the first tab index with a given tip, or -1 if no tab has this tip. 
	 * @param title the title for the tab 
	 * @return the first tab index which matches tip, or -1 if no tab has this tip
	 */		
	public int  indexOfTip (String tip ){
		return ArrayUtils.indexInArray(tips, tip);
	}
	
	/**
     * Sets the selected index for this tabbedpane. The index must be
     * a valid tab index or -1, which indicates that no tab should be selected
     * (can also be used when there are no tabs in the tabbedpane).  If a -1
     * value is specified when the tabbedpane contains one or more tabs, then
     * the results will be implementation defined.
     *
     * @param index  the index to be selected
     * @param programmatic indicate if this is a programmatic change.
	 */
	public void  setSelectedIndex (int i ,boolean programmatic =true ){
		if(i>=-1 && i<getComponentCount()){
			getModel().setSelectedIndex(i, programmatic);
		}
	}
	/**
     * Sets the selected component for this tabbedpane.  This
     * will automatically set the <code>selectedIndex</code> to the index
     * corresponding to the specified component.
     *
     * @param com the component to be selected
     * @param programmatic indicate if this is a programmatic change.
     * @see #getSelectedComponent()
	 */
	public void  setSelectedComponent (Component com ,boolean programmatic =true ){
		setSelectedIndex(getIndex(com), programmatic);
	}
	
	public int  getSelectedIndex (){
		return getModel().getSelectedIndex();
	}
	
	public Component  getSelectedComponent (){
		int index =getModel ().getSelectedIndex ();
		if(index >= 0){
			return getComponent(index);
		}
		return null;
	}

    /**
     * Returns the vertical alignment of the text and icon.
     *
     * @return the <code>verticalAlignment</code> property, one of the
     *		following values: 
     * <ul>
     * <li>AsWingConstants.CENTER (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    public int  getVerticalAlignment (){
        return verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment of the icon and text.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    public void  setVerticalAlignment (int alignment ){
        if (alignment == verticalAlignment){
        	return;
        }else{
        	verticalAlignment = alignment;
        	revalidate();
        	repaint();
        }
    }
    
    /**
     * Returns the horizontal alignment of the icon and text.
     * @return the <code>horizontalAlignment</code> property,
     *		one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    public int  getHorizontalAlignment (){
        return horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment of the icon and text.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    public void  setHorizontalAlignment (int alignment ){
        if (alignment == horizontalAlignment){
        	return;
        }else{
        	horizontalAlignment = alignment;     
        	revalidate();
        	repaint();
        }
    }

    
    /**
     * Returns the vertical position of the text relative to the icon.
     * @return the <code>verticalTextPosition</code> property, 
     *		one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER  (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    public int  getVerticalTextPosition (){
        return verticalTextPosition;
    }
    
    /**
     * Sets the vertical position of the text relative to the icon.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    public void  setVerticalTextPosition (int textPosition ){
        if (textPosition == verticalTextPosition){
	        return;
        }else{
        	verticalTextPosition = textPosition;
        	revalidate();
        	repaint();
        }
    }
    
    /**
     * Returns the horizontal position of the text relative to the icon.
     * @return the <code>horizontalTextPosition</code> property, 
     * 		one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    public int  getHorizontalTextPosition (){
        return horizontalTextPosition;
    }
    
    /**
     * Sets the horizontal position of the text relative to the icon.
     * @param textPosition one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    public void  setHorizontalTextPosition (int textPosition ){
        if (textPosition == horizontalTextPosition){
        	return;
        }else{
        	horizontalTextPosition = textPosition;
        	revalidate();
        	repaint();
        }
    }
    
    /**
     * Returns the amount of space between the text and the icon
     * displayed in this button.
     *
     * @return an int equal to the number of pixels between the text
     *         and the icon.
     * @see #setIconTextGap()
     */
    public int  getIconTextGap (){
        return iconTextGap;
    }

    /**
     * If both the icon and text properties are set, this property
     * defines the space between them.  
     * <p>
     * The default value of this property is 4 pixels.
     * 
     * @see #getIconTextGap()
     */
    public void  setIconTextGap (int iconTextGap ){
        int oldValue =this.iconTextGap ;
        this.iconTextGap = iconTextGap;
        if (iconTextGap != oldValue) {
            revalidate();
            repaint();
        }
    }
    
	/**
	 * Sets space for margin between the tab border and
     * the tab label.
     *
     * @param m the space between the border and the label
	 */
	public void  setMargin (Insets m ){
        if (m!=null && !m.equals(margin)) {
        	margin = m;
            revalidate();
            repaint();
        }
	}
	
	/**
	 * Returns the space for margin between the tab border and
     * the tab label.
	 */
	public Insets  getMargin (){
		if(margin == null){
			return new InsetsUIResource();//make it can be replaced by LAF
		}else{
			if(margin is UIResource){//make it can be replaced by LAF
				return new InsetsUIResource(margin.top, margin.left, margin.bottom, margin.right);
			}else{
				return new Insets(margin.top, margin.left, margin.bottom, margin.right);
			}
		}
	}    
        	
	private void  __modelStateChanged (InteractiveEvent e ){
		fireStateChanged(e.isProgrammatic());
	}
	
	private void  fireStateChanged (boolean programmatic ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
	
    //----------------------------------------------------------------
    
	protected void  insertToArray (Array arr ,int i ,Object obj ){
		if(i < 0){
			arr.push(obj);
		}else{
			arr.splice(i, 0, obj);
		}
	}
	
	protected void  removeFromArray (Array arr ,int i ){
		if(i < 0){
			arr.pop();
		}else{
			arr.splice(i, 1);
		}
	}
	
}


