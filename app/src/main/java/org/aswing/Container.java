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


import flash.display.DisplayObject;

import org.aswing.event.*;
import org.aswing.geom.*;

//--------------------------------------
//  Events
//--------------------------------------

/**
 * Dispatched when a component is added to a container.
 * The following methods trigger this event:
 * Container.addChild(com:Component),
 * Container.addChildAt(com:Component, index:int),
 * Container.append(), Container.insert().
 *
 * @eventType org.aswing.event.ContainerEvent.COM_ADDED
 */
.get(Event(name="comAdded", type="org.aswing.event.ContainerEvent"))

/**
 * Dispatched when a component is removed from a container.
 * The following methods trigger this event:
 * Container.removeChild(com:Component),
 * Container.removeChildAt(com:Component, index:int),
 * Container.remove(), Container.removeAt().
 *
 *  @eventType org.aswing.event.ContainerEvent.COM_REMOVED
 */
.get(Event(name="comRemoved", type="org.aswing.event.ContainerEvent"))


/**
 * Container can contain many component to be his child, all children are in its bounds,
 * and it moved, all children moved. It be removed from stage all children will be removed from stage.
 * <p>
 * It for component like <code>DisplayObjectContainer</code> for <code>DisplayObject</code>.
 * </p>
 * <p>
 * <ul>
 * <li>There are two scope for <code>Container</code> children,
 * One is <code>Component</code> children.
 * The indices and numbers for <code>insert()</code>, <code>getComponent()</code>,
 * <code>removeAt()</code>, <code>getComponentCount()</code> is in <code>Component</code>
 * children scope.
 * </li>
 * <li>Another is normal <code>DisplayObject</code> children,
 * The indices and numbers for <code>addChildAt()</code>, <code>getChildAt()</code>,
 * <code>removeChildAt()</code> and <code>numChildren()</code> is in normal <code>DisplayObject</code>
 * children scope.
 * </li>
 * </ul>
 * </p>
 * @author iiley
 */
public class Container extends Component{

	private FocusTraversalPolicy focusTraversalPolicy ;
	protected Array children ;
	protected LayoutManager layout ;

	public  Container ()
	{
		super();
		setName("Container");
		focusTraversalPolicy = null;
		children = new Array();
		layout = new EmptyLayout();
	}

	public void  setLayout (LayoutManager layout ){
		this.layout = layout;
		revalidate();
	}

	public LayoutManager  getLayout (){
		return layout;
	}

	/**
	 * Sets the focus traversal policy to this container, or sets null to
	 * make this container use its parent's focus traversal policy.
	 * (By default, it is null)
	 * @param ftp the focus traversal policy, or null.
	 */
	public void  setFocusTraversalPolicy (FocusTraversalPolicy ftp ){
		focusTraversalPolicy = ftp;
	}

	/**
	 * Returns the focus traversal policy of this container, it will return its parent's
	 * focus traversal policy if its self is null. If no focus traversal policy is found,
	 * it will return a default focus traversal policy.
	 * (<code>FocusManager.getCurrentManager().getDefaultFocusTraversalPolicy()</code>).
	 * @return the focus traversal policy
	 */
	public FocusTraversalPolicy  getFocusTraversalPolicy (){
		if(focusTraversalPolicy == null){
			FocusTraversalPolicy ftp =null ;
			if(getParent() != null){
				ftp = getParent().getFocusTraversalPolicy();
			}
			if(ftp == null){
				FocusManager fm =FocusManager.getManager(stage );
				if(fm != null){
					ftp = fm.getDefaultFocusTraversalPolicy();
				}
				if(ftp == null){
					ftp = new ContainerOrderFocusTraversalPolicy();
				}
			}
			return ftp;
		}else{
			return focusTraversalPolicy;
		}
	}

    /**
     * Invalidates the container.  The container and all parents
     * above it are marked as needing to be laid out.  This method can
     * be called often, so it needs to execute quickly.
     * @see #validate()
     * @see #doLayout()
     * @see org.aswing.LayoutManager
     */
     public void  invalidate (){
    	layout.invalidateLayout(this);
    	super.invalidate();
    }

    /**
     * Validates this container and all of its subcomponents.
     * <p>
     * The <code>validate</code> method is used to cause a container
     * to lay out its subcomponents again. It should be invoked when
     * this container's subcomponents are modified (added to or
     * removed from the container, or layout-related information
     * changed) after the container has been displayed.
     *
     * @see #append()
     * @see Component#invalidate()
     * @see org.aswing.Component#revalidate()
     */
     public void  validate (){
    	if(!valid){
    		doLayout();
    		for(int i =0;i <children.length ;i ++){
    			children.get(i).validate();
    		}
    		valid = true;
    	}
    }

	/**
	 * layout this container
	 */
	public void  doLayout (){
		if(isVisible()){
			layout.layoutContainer(this);
		}
	}

	/**
	 * Removes all children and then append them with their constraints.
	 * @see Component#getConstraints()
	 */
	public void  reAppendChildren (){
		Array chs =children.concat ();
		removeAll();
		for(int i =0;i <chs.length ;i ++){
			append(chs.get(i));
		}
		revalidate();
	}

	/**
	 * On Component just can add to one Container.
	 * So if the com has a parent, it will remove from its parent first, then add to
	 * this container.
	 * This method is as same as <code>insert(-1, com, constraints)</code>.
	 * @param com the component to be added
	 * @param constraints an object expressing layout contraints for this component
	 * @see #insert()
	 */
	public void  append (Component com ,Object constraints =null ){
	    insertImp(-1, com, constraints);
	}

	/**
	 * Adds one or more component to the container with null constraints
	 * @see #append()
	 */
	public void  appendAll (...coms ){
		for each(in i *coms ){
			Component com =(Component)i;
			if(com != null){
				append(com);
			}
		}
	}

	/**
	 * Add component to spesified index.
	 * So if the com has a parent, it will remove from its parent first, then add to
	 * this container.
	 * @param i index the position at which to insert the component, or less than 0 value to append the component to the end
	 * @param com the component to be added
	 * @param constraints an object expressing layout contraints for this component
	 * @throws RangeError when index > children count
	 * @throws ArgumentError when add container's parent(or itself) to itself
	 * @see Component#removeFromContainer()
	 * @see #append()
	 */
	public void  insert (int i ,Component com ,Object constraints =null ){
		insertImp(i, com, constraints);
	}

	/**
	 * Insets one or more component to the container with null constraints at specified starting index.
	 * @see #insert()
	 */
	public void  insertAll (int index ,...coms ){
		for each(in i *coms ){
			Component com =(Component)i;
			if(com != null){
				insert(index, com);
				index++;
			}
		}
	}

	/**
	 * @param i the index to be insert
	 * @param com the component to be insert
	 * @param constraints the layout constraints
	 * @param forceChildIndex the index to force the child to be added(for DisplayContainer scope),
	 * 			default -1 means not force.
	 */
	protected void  insertImp (int i ,Component com ,Object constraints =null ){
		if(i > getComponentCount()){
			throw new RangeError("illegal component position when insert comp to container");
		}
		if(com is Container){
			for(Container cn =this ;cn != null; cn = cn.getParent()) {
                if (cn == com) {
                	throw new ArgumentError("adding container's parent to itself");
                }
            }
		}
		if(com.getParent() != null){
			com.removeFromContainer();
		}
		if(i < 0){
			children.push(com);
			addChild(com);
		}else{
			addChildAt(com, getChildIndexWithComponentIndex(i));
			children.splice(i, 0, com);
		}
		com.container = this;
		layout.addLayoutComponent(com, (constraints == null) ? com.getConstraints() : constraints);
		dispatchEvent(new ContainerEvent(ContainerEvent.COM_ADDED, this, com));

		if (valid) {
			revalidate();
	    }else{
	    	invalidatePreferSizeCaches();
	    }
	}

	/**
	 * Removes a normal display object child.
	 * <p>
	 * If <code>child</code> is a <code>Component</code> child instance,
	 * a <code>ArgumentError</code> error will be thrown. Becasue you should call
	 * <code>remove</code> to remove a component child.
	 * </p>
	 * @param child the child to be removed.
	 * @inheritDoc
	 * @see #remove()
	 * @throws ArgumentError if the child is a component child of this container.
	 */
	 public DisplayObject  removeChild (DisplayObject child ){
		checkChildRemoval(child);
		return super.removeChild(child);
	}

	/**
	 * Removes a normal display object child with index.
	 * <p>
	 * If <code>child</code> is a <code>Component</code> child instance,
	 * a <code>ArgumentError</code> error will be thrown. Becasue you should call
	 * <code>removeAt</code> to remove a component child.
	 * </p>
	 * @param index the index of the child to be removed.
	 * @inheritDoc
	 * @see #removeAt()
	 * @throws ArgumentError if the child is a component child of this container.
	 */
	 public DisplayObject  removeChildAt (int index ){
		checkChildRemoval(getChildAt(index));
		return super.removeChildAt(index);
	}

	private void  checkChildRemoval (DisplayObject child ){
		if(child is Component){
			Component c =(Component)child;
			if(c.getParent() != null){
				throw new ArgumentError("You should call remove method to remove a component child!");
			}
		}
	}

	/**
	 * Remove the specified child component.
	 * @return the component just removed, null if the component is not in this container.
	 */
	public Component  remove (Component com ){
		int i =getIndex(com );
		if(i >= 0){
			return removeAt(i);
		}
		return null;
	}

	/**
	 * Remove the specified index child component.
	 * @param i the index of component.
	 * @return the component just removed. or null there is not component at this position.
	 */
	public Component  removeAt (int i ){
		return removeAtImp(i);
	}

	protected Component  removeAtImp (int i ){
		if(i < 0){
			return null;
		}
		Component com =children.get(i) ;
		if(com != null){
			children.splice(i, 1);
			super.removeChild(com);
			com.container = null;
			layout.removeLayoutComponent(com);
			dispatchEvent(new ContainerEvent(ContainerEvent.COM_REMOVED, this, com));

			if (valid) {
				revalidate();
		    }else{
	    		invalidatePreferSizeCaches();
	    	}
		}
		return com;
	}

	/**
	 * Remove all child components.
	 */
	public void  removeAll (){
		while(children.length > 0){
			removeAt(children.length - 1);
		}
	}

    /**
     * Gets the nth(index) component in this container.
     * @param  n   the index of the component to get.
     * @return the n<sup>th</sup> component in this container. returned null if
     * the index if out of bounds.
     * @throw RangeError if index out of container children bounds
     * @see #getComponentCount()
     */
	public Component  getComponent (int index ){
		if(index < 0 || index >= children.length()){
			throw new RangeError("Index out of container children bounds!!!");
		}
		return children.get(index);
	}

	/**
	 * Returns the index of the child component in this container.
	 * @return the index of the specified child component.
	 * @see #getComponent()
	 */
	public int  getIndex (Component com ){
		int n =children.length ;
		for(int i =0;i <n ;i ++){
			if(com == children.get(i)){
				return i;
			}
		}
		return -1;
	}

    /**
     * Gets the number of components in this container.
     * @return    the number of components in this container.
     * @see       #getComponent()
     */
	public int  getComponentCount (){
		return children.length;
	}

    /**
     * Checks if the component is contained in the component hierarchy of
     * this container.
     * @param c the component
     * @return     <code>true</code> if it is an ancestor;
     *             <code>false</code> otherwise.
     */
    public boolean  isAncestorOf (Component c ){
		Container p =c.getParent ();
		if (c == null || p == null) {
		    return false;
		}
		while (p != null) {
		    if (p == this) {
				return true;
		    }
		    p = p.getParent();
		}
		return false;
    }

	protected int  getChildIndexWithComponentIndex (int index ){
		int count =getComponentCount ();
		if(index < 0 || index > count){
			throw new RangeError("Out of index counting bounds, it should be >=0 and <= component count!");
		}
		if(index == count){
			return getHighestIndexUnderForeground();
		}else{
			return getChildIndex(getComponent(index));
		}
	}

	protected int  getComponentIndexWithChildIndex (int index ){
		int count =numChildren ;
		if(index < 0 || index > count){
			throw new RangeError("Out of index counting bounds, it should be >=0 and <= numChildren!");
		}
		if(index == count){
			return getComponentCount();
		}else{
			int aboveCount =0;
			for(int i =index ;i <count ;i ++){
				if(getChildAt(i) is Component){
					aboveCount++;
				}
			}
			return getComponentCount() - aboveCount;
		}
		return 0;
	}

	/**
	 * call the ui, if ui return null, ehn call layout to count.
	 */
	 protected IntDimension  countMinimumSize (){
		IntDimension size =null ;
		if(ui != null){
			size = ui.getMinimumSize(this);
		}
		if(size == null){
			size = layout.minimumLayoutSize(this);
		}
		if(size == null){//this should never happen
			size = super.countMinimumSize();
		}
		return size;
	}

	/**
	 * call the ui, if ui return null, ehn call layout to count.
	 */
	 protected IntDimension  countMaximumSize (){
		IntDimension size =null ;
		if(ui != null){
			size = ui.getMaximumSize(this);
		}
		if(size == null){
			size = layout.maximumLayoutSize(this);
		}
		if(size == null){//this should never happen
			size = super.countMaximumSize();
		}
		return size;
	}

	/**
	 * call the ui, if ui return null, ehn call layout to count.
	 */
	 protected IntDimension  countPreferredSize (){
		IntDimension size =null ;
		if(ui != null){
			size = ui.getPreferredSize(this);
		}
		if(size == null){
			size = layout.preferredLayoutSize(this);
		}
		if(size == null){//this should never happen
			size = super.countPreferredSize();
		}
		return size;
	}

        public Array  getChildren ()
        {
            return this.children.concat();
        }//end


}


