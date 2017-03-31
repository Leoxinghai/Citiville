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
import flash.display.Shape;
import flash.display.Sprite;
import flash.events.*;

import org.aswing.event.*;
import org.aswing.geom.*;

/**
 * Dispatched when the mouse released or released out side.
 * If you need a event like AS2 <code>onRelease</code> you can 
 * use <code>Event.CLICK</code>
 * 
 * @eventType org.aswing.event.ReleaseEvent.RELEASE
 */
.get(Event(name="release", type="org.aswing.event.ReleaseEvent"))

/**
 * Dispatched only when the mouse released out side.
 *
 * @eventType org.aswing.event.ReleaseEvent.RELEASE_OUT_SIDE
 */
.get(Event(name="releaseOutSide", type="org.aswing.event.ReleaseEvent"))

/**
 * AsWing component based Sprite.
 * <p>
 * The AsWing Component Assets structure:(Assets means flash player display objects)
 * <pre>
 *             | -- foreground decorator asset
 *             |
 *             |    .get(other assets, there is no depth restrict between them, see below )
 * AWSprite -- | -- .get(icon, border, ui creation, children component assets ...)          
 *             |    .get(they are above background decorator and below foreground decorator)
 *             |
 *             | -- background decorator asset
 * </pre>
 */
public class AWSprite extends Sprite
	private DisplayObject foregroundChild ;
	private DisplayObject backgroundChild ;
	
	private boolean clipMasked =false ;
	private IntRectangle clipMaskRect ;
	private Sprite content ;
	private Shape maskShape ;
	private boolean usingBitmap ;
	
	public  AWSprite (boolean clipMasked =false ){
		super();
		focusRect = false;
		usingBitmap = false;
		clipMaskRect = new IntRectangle();
		setClipMasked(clipMasked);
		addEventListener(MouseEvent.MOUSE_DOWN, __awSpriteMouseDownListener);
	}
	
	protected DisplayObject  d_addChild (DisplayObject child ){
		return super.addChild(child);
	}
	
	protected DisplayObject  d_addChildAt (DisplayObject child ,int index ){
		return super.addChildAt(child, index);
	}
	
	 public DisplayObject  addChildAt (DisplayObject child ,int index ){
		if(usingBitmap){
			return content.addChildAt(child, index);
		}else{
			return d_addChildAt(child, index);
		}
	}
	
	protected DisplayObject  d_removeChild (DisplayObject child ){
		return super.removeChild(child);
	}
	
	/**
	 * Returns whether or not the child is this sprite's direct child.
	 */
	protected boolean  isChild (DisplayObject child ){
		if(usingBitmap){
			return child.parent == content;
		}else{
			return child.parent == this;
		}		
	}
	
	 public DisplayObject  removeChild (DisplayObject child ){
		if(usingBitmap){
			return content.removeChild(child);
		}else{
			return d_removeChild(child);
		}
	}
	
	protected DisplayObject  d_removeChildAt (int index ){
		return super.removeChildAt(index);
	}
	
	 public DisplayObject  removeChildAt (int index ){
		if(usingBitmap){
			return content.removeChildAt(index);
		}else{
			return d_removeChildAt(index);
		}
	}
	
	protected DisplayObject  d_getChildAt (int index ){
		return super.getChildAt(index);
	}
	
	 public DisplayObject  getChildAt (int index ){
		if(usingBitmap){
			return content.getChildAt(index);
		}else{
			return d_getChildAt(index);
		}
	}
	
	protected DisplayObject  d_getChildByName (String name ){
		return super.getChildByName(name);
	}
	
	 public DisplayObject  getChildByName (String name ){
		if(usingBitmap){
			return content.getChildByName(name);
		}else{
			return d_getChildByName(name);
		}
	}
	
	protected int  d_getChildIndex (DisplayObject child ){
		return super.getChildIndex(child);
	}
	
	 public int  getChildIndex (DisplayObject child ){
		if(usingBitmap){
			return content.getChildIndex(child);
		}else{
			return d_getChildIndex(child);
		}
	}
	
	/**
	 * Returns whether child is directly child of this sprite, true only if getChildIndex(child) >= 0.
	 * @return true only if getChildIndex(child) >= 0.
	 */
	public boolean  containsChild (DisplayObject child ){
		if(usingBitmap){
			return child.parent.parent == this;
		}else{
			return child.parent == this;
		}		
	}
	
	protected void  d_setChildIndex (DisplayObject child ,int index ){
		super.setChildIndex(child, index);
	}
	
	 public void  setChildIndex (DisplayObject child ,int index ){
		if(usingBitmap){
			content.setChildIndex(child, index);
		}else{
			d_setChildIndex(child, index);
		}
	}
	
	protected void  d_swapChildren (DisplayObject child1 ,DisplayObject child2 ){
		super.swapChildren(child1, child2);
	}
	
	 public void  swapChildren (DisplayObject child1 ,DisplayObject child2 ){
		if(usingBitmap){
			content.swapChildren(child1, child2);
		}else{
			d_swapChildren(child1, child2);
		}
	}
	
	protected void  d_swapChildrenAt (int index1 ,int index2 ){
		super.swapChildrenAt(index1, index2);
	}
	
	 public void  swapChildrenAt (int index1 ,int index2 ){
		if(usingBitmap){
			content.swapChildrenAt(index1, index2);
		}else{
			d_swapChildrenAt(index1, index2);
		}
	}
	
	protected int  d_numChildren (){
		return super.numChildren;
	}
	
	 public int  numChildren (){
		if(usingBitmap){
			return content.numChildren;
		}else{
			return d_numChildren;
		}
	}
	
	/**
	 * Adds a child DisplayObject instance to this DisplayObjectContainer instance. 
	 * The child is added to the front (top) of all other children except foreground decorator child(It is topest)
	 *  in this DisplayObjectContainer instance. 
	 * (To avoid this restrict and add a child to a specific index position, use the <code>addChildAt()</code> method.)
	 * (<b>Note:</b> Generally if you don't want to break the component asset depth management, use 
	 * getHighestIndexUnderForeground() and getLowestIndexAboveBackground() to get the 
	 * right depth you can use. You can also refer to getChildIndex() to
	 * insert child after or before an existing child) 
	 * 
	 * @param dis The DisplayObject instance to add as a child of this DisplayObjectContainer instance. 
	 * @see #getLowestIndexAboveBackground()
	 * @see #getHighestIndexUnderForeground()
	 * @see #http://livedocs.macromedia.com/flex/2/langref/flash/display/DisplayObjectContainer.html#getChildIndex()
	 */
	public override function addChild(dis:DisplayObject):DisplayObject{
		if(foregroundChild != null){
			if(usingBitmap){
				return content.addChildAt(dis, content.getChildIndex(foregroundChild));
			}
			d_addChild(dis);
			d_swapChildren(dis, foregroundChild);
			return dis;
		}
		if(usingBitmap){
			return content.addChild(dis);
		}
		return d_addChild(dis);
	}
	
	/**
	 * Returns the current top index for a new child(none forground child).
	 * @return the current top index for a new child that is not a foreground child.
	 */
	public int  getHighestIndexUnderForeground (){
		if(foregroundChild == null){
			return numChildren;
		}else{
			return numChildren - 1;
		}
	}
	
	/**
	 * Returns the current bottom index for none background child.
	 * @return the current bottom index for child that is not a background child.
	 */		
	public int  getLowestIndexAboveBackground (){
		if(backgroundChild == null){
			return 0;
		}else{
			return 1;
		}
	}
	
	 public boolean  hitTestPoint (double x ,double y ,boolean shapeFlag =false ){
		if(isClipMasked() && !shapeFlag){
			return maskShape.hitTestPoint(x, y, shapeFlag);
		}else{
			//TODO use bounds to test the x, y
			return super.hitTestPoint(x, y, shapeFlag);
		}
	}
	
	 public boolean  hitTestObject (DisplayObject obj ){
		if(isClipMasked()){
			return maskShape.hitTestObject(obj);
		}else{
			//TODO use bounds to test the obj
			return super.hitTestObject(obj);
		}
	}
	
	/**
	 * Brings a child to top.
	 * This method will keep foreground child on top, if you bring a other object 
	 * to top, this method will only bring it on top of other objects
	 * (mean on top of others but bellow the foreground child).
	 * @param child the child to be bringed to top.
	 */
	public void  bringToTop (DisplayObject child ){
		int index =numChildren -1;
		if(foregroundChild != null){
			if(foregroundChild != child){
				index = numChildren-2;
			}
		}
		setChildIndex(child, index);
	}
	

	/**
	 * Brings a child to bottom.
	 * This method will keep background child on bottom, if you bring a other object 
	 * to bottom, this method will only bring it at bottom of other objects
	 * (mean at bottom of others but on top of the background child).
	 * @param child the child to be bringed to bottom.
	 */	
	public void  bringToBottom (DisplayObject child ){
		int index =0;
		if(backgroundChild != null){
			if(backgroundChild != child){
				index = 1;
			}
		}
		setChildIndex(child, index);
	}
	
	/**
	 * Sets the child to be the component background, it will be add to the bottom of all other children. 
	 * (old backgournd child will be removed). pass no paramter (null) to remove the background child.
	 * 
	 * @param child the background child to be added.
	 */
	protected void  setBackgroundChild (DisplayObject child =null ){
		if(child != backgroundChild){
			if(backgroundChild != null){
				removeChild(backgroundChild);
			}
			backgroundChild = child;
			if(child != null){
				addChildAt(child, 0);
			}
		}
	}
	
	/**
	 * Returns the background child. 
	 * @return the background child.
	 * @see #setBackgroundChild()
	 */
	protected DisplayObject  getBackgroundChild (){
		return backgroundChild;
	}
	
	/**
	 * Sets the child to be the component foreground, it will be add to the top of all other children. 
	 * (old foregournd child will be removed), pass no paramter (null) to remove the foreground child.
	 * 
	 * @param child the foreground child to be added.
	 */
	protected void  setForegroundChild (DisplayObject child =null ){
		if(child != foregroundChild){
			if(foregroundChild != null){
				removeChild(foregroundChild);
			}
			foregroundChild = child;
			if(child != null){
				addChild(child);
			}
		}
	}
	
	/**
	 * Returns the foreground child. 
	 * @return the foreground child.
	 * @see #setForegroundChild()
	 */
	protected DisplayObject  getForegroundChild (){
		return foregroundChild;
	}

	/**
	 * Sets whether the component clip should be masked by its bounds. By default it is true.
	 * <p>
	 * AsWing A3 use <code>scrollRect</code> property to do the clip mask.
	 * </p>
	 * @param m whether the component clip should be masked.
	 * @see #isClipMasked()
	 */
	public void  setClipMasked (boolean m ){
		if(m != clipMasked){
			clipMasked = m;
			setUsingBitmap(cacheAsBitmap && clipMasked);
			if(clipMasked){
				checkCreateMaskShape();
				if(maskShape.parent != this){
					d_addChild(maskShape);
					mask = maskShape;
				}
				setClipMaskRect(clipMaskRect);
			}else{
				if(maskShape != null && maskShape.parent == this){
					d_removeChild(maskShape);
				}
				mask = null;
			}
		}
	}
	
	protected void  setClipMaskRect (IntRectangle b ){
		if(maskShape){
			maskShape.x = b.x;
			maskShape.y = b.y;
			maskShape.height = b.height;
			maskShape.width = b.width;
		}
		clipMaskRect.setRect(b);
	}
	
	private void  setUsingBitmap (boolean b ){
		if(usingBitmap != b){
			usingBitmap = b;
			usingBitmapChanged();
		}
	}
	
	private void  usingBitmapChanged (){
		Array children ;
		int n ;
		int i ;
		if(usingBitmap){
			if(!content){
				content = new Sprite();
				content.tabEnabled = false;
				content.mouseEnabled = false;
			}
			//move children from this to content
			children = new Array();
			n = d_numChildren;
			for(i=0; i<n; i++){
				if(d_getChildAt(i) != maskShape){
					children.push(d_getChildAt(i));
				}
			}
			for(i=0; i<children.length; i++){
				content.addChild(children.get(i));
			}
			
			d_addChild(content);
			if(clipMasked){
				super.mask = null;
				content.mask = maskShape;
			}
		}else{
			d_removeChild(content);
			
			//move children from content to this
			children = new Array();
			n = content.numChildren;
			for(i=0; i<n; i++){
				children.push(content.getChildAt(i));
			}
			for(i=0; i<children.length; i++){
				d_addChild(children.get(i));
			}
			
			if(clipMasked){
				content.mask = null;
				super.mask = maskShape;
			}
		}
	}
	
	 public void  mask (DisplayObject value ){
		if(usingBitmap){
			content.mask = value;
		}else{
			super.mask = value;
		}
	}
	
	 public DisplayObject  mask (){
		if(usingBitmap){
			return content.mask;
		}else{
			return super.mask;
		}
	}
	
	 public void  filters (Array value ){
		super.filters = value;
		setUsingBitmap(super.cacheAsBitmap && clipMasked);
	}
	
	 public void  cacheAsBitmap (boolean value ){
		super.cacheAsBitmap = value;
		setUsingBitmap(value && clipMasked);
	}
	
	private void  checkCreateMaskShape (){
		if(!maskShape){
			maskShape = new Shape();
			maskShape.graphics.beginFill(0);
			maskShape.graphics.drawRect(0, 0, 1, 1);
			maskShape.graphics.endFill();
		}
	}
	
	/**
	 * Returns whether the component clip should be masked by its bounds. By default it is true.
	 * <p>
	 * AsWing A3 use <code>scrollRect</code> property to do the clip mask.
	 * </p>
	 * @return whether the component clip should be masked.
	 * @see #setClipMasked()
	 */
	public boolean  isClipMasked (){
		return clipMasked;
	}
	
	private DisplayObject pressedTarget ;
	private void  __awSpriteMouseDownListener (MouseEvent e ){
		pressedTarget =(DisplayObject) e.target;
		if(stage){
			stage.addEventListener(MouseEvent.MOUSE_UP, __awStageMouseUpListener, false, 0, true);
			addEventListener(Event.REMOVED_FROM_STAGE, __awStageRemovedFrom);
		}
	}
	private void  __awStageRemovedFrom (Event e ){
		pressedTarget = null;
		stage.removeEventListener(MouseEvent.MOUSE_UP, __awStageMouseUpListener);
	}
	private void  __awStageMouseUpListener (MouseEvent e ){
		if(stage) stage.removeEventListener(MouseEvent.MOUSE_UP, __awStageMouseUpListener);
		boolean isOutSide =false ;
		DisplayObject target =(DisplayObject)e.target;
		if(!(this == target || AsWingUtils.isAncestorDisplayObject(this, target))){
			isOutSide = true;
		}
		dispatchEvent(new ReleaseEvent(ReleaseEvent.RELEASE, pressedTarget, isOutSide, e));
		if(isOutSide){
			dispatchEvent(new ReleaseEvent(ReleaseEvent.RELEASE_OUT_SIDE, pressedTarget, isOutSide, e));
		}

		pressedTarget = null;
	}
	
	 public String  toString (){
		DisplayObject p =this ;
		String str =p.name ;
		while(p.parent != null){
			String name =(p.parent ==p.stage ? "Stage" : p.parent.name);
			p = p.parent;
			str = name + "." + str;
		}
		return str;
	}
}


