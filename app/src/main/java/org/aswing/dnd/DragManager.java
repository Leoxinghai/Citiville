
package org.aswing.dnd;

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


import flash.display.*;
import flash.events.MouseEvent;
import flash.geom.*;

import org.aswing.*;
import org.aswing.event.DragAndDropEvent;
import org.aswing.geom.*;
import org.aswing.util.*;

/**
 * Drag and Drop Manager.
 * <p>
 * Thanks Bill Lee for the original DnD implementation for AsWing AS2 version.
 * </p>
 * @author iiley
 */
public class DragManager{
	
	public static double TYPE_NONE =0;
	public static double TYPE_MOVE =1;
	public static double TYPE_COPY =2;
	
	public static DropMotion DEFAULT_DROP_MOTION =new DirectlyRemoveMotion ();
	public static DropMotion DEFAULT_REJECT_DROP_MOTION =new RejectedMotion ();
	
	private static boolean s_isDragging =false ;
	
	private static DragListener s_dragListener ;
	private static Component s_dragInitiator ;
	private static SourceData s_sourceData ;
	private static DraggingImage s_dragImage ;
	
	private static DisplayObjectContainer root =null ;
	private static DropMotion dropMotion ;
	private static DropMotion runningMotion ;
	private static Sprite dragProxyMC ;
	private static IntPoint mouseOffset ;
	private static Component enteredComponent ;
	
	private static Array listeners =new Array ();
	private static Stage curStage ;
	
	/**
	 * Sets the container to hold the draging image(in fact it will hold the image's parent--a sprite).
	 * By default(if you have not set one), it will be the <code>AsWingManager.getRoot()</code> value.
	 * @param theRoot the container to hold the draging image.
	 * @see org.aswing.AsWingManager#getRoot()
	 */
	public static void  setDragingImageContainerRoot (DisplayObjectContainer theRoot ){
		root = theRoot;
	}
		
	/**
	 * startDrag(dragInitiator:Component, dragSource, dragImage:MovieClip, lis:DragListener)<br>
	 * startDrag(dragInitiator:Component, dragSource, dragImage:MovieClip)<br>
	 * startDrag(dragInitiator:Component, dragSource)<br>
	 * <p>
	 * Starts dragging a initiator, with dragSource, a dragging Image, and a listener. The drag action will be finished 
	 * at next Mouse UP/Down Event(Mouse UP or Mouse Down, generally you start a drag when mouse down, then it will be finished 
	 * when mouse up, if you start a drag when mouse up, it will be finished when mouse down).
	 * 
	 * @param dragInitiator the dragging initiator
	 * @param sourceData the data source will pass to the listeners and target components
	 * @param dragImage (optional)the image to drag, default is a rectangle image.
	 * @param dragListener (optional)the listener added to just for this time dragging action, default is null(no listener)
	 */
	public static void  startDrag (Component dragInitiator ,SourceData sourceData ,DraggingImage dragImage =null ,DragListener dragListener =null ){
		if(s_isDragging){
			throw new Error("The last dragging action is not finished, can't start a new one!");
			return;
		}
		Stage stage =dragInitiator.stage ;
		if(stage == null){
			throw new Error("The drag initiator is not on stage!");
			return;
		}
		curStage = stage;
		if(dragImage == null){
			dragImage = new DefaultDragImage(dragInitiator);
		}
		
		s_isDragging = true;
		s_dragInitiator = dragInitiator;
		s_sourceData = sourceData;
		s_dragImage = dragImage;
		s_dragListener = dragListener;
		if(s_dragListener != null){
			addDragListener(s_dragListener);
		}
		if(runningMotion){
			runningMotion.forceStop();
			runningMotion = null;
		}
		DisplayObjectContainer container =stage ;
		if(dragProxyMC == null){
			dragProxyMC = new Sprite();
			dragProxyMC.mouseEnabled = false;
			dragProxyMC.name = "drag_image";
		}else{
			if(dragProxyMC.parent != null){
				dragProxyMC.parent.removeChild(dragProxyMC);
			}
		}
		if(dragProxyMC.numChildren > 0){
			dragProxyMC.removeChildAt(0);
		}
		container.addChild(dragProxyMC);
		
		IntPoint globalPos =AsWingUtils.getStageMousePosition(stage );
		Point dp =container.globalToLocal(dragInitiator.getGlobalLocation ().toPoint ());
		dragProxyMC.x = dp.x;
		dragProxyMC.y = dp.y;
		
		dragProxyMC.addChild(dragImage.getDisplay());
		dragProxyMC.startDrag(false);
		
		mouseOffset = new IntPoint(container.mouseX - dp.x, container.mouseY - dp.y);
		fireDragStartEvent(s_dragInitiator, s_sourceData, globalPos);
		
		enteredComponent = null;
		//initial image
		s_dragImage.switchToRejectImage();
		__onMouseMoveOnStage(stage);
		stage.addEventListener(MouseEvent.MOUSE_MOVE, __onMouseMove, false, 0, true);
		stage.addEventListener(MouseEvent.MOUSE_DOWN, __onMouseDown, false, 0, true);
		stage.addEventListener(MouseEvent.MOUSE_UP, __onMouseUp, false, 0, true);
	}
		
	/**
	 * Adds a drag listener to listener list.
	 * @param lis the listener to be add
	 */
	public static void  addDragListener (DragListener lis ){
		listeners.push(lis);
	}
	
	/**
	 * Removes the specified listener from listener list.
	 * @param lis the listener to be removed
	 */
	public static void  removeDragListener (DragListener lis ){
		ArrayUtils.removeFromArray(listeners, lis);
	}
	
	/**
	 * Sets the motion of drag movie clip when a drop acted.
	 * <p>
	 * Generally if you want to do a custom motion of the dragging movie clip when dropped, you may 
	 * call this method in the listener's <code>onDragDrop()</code> method.
	 * <p>
	 * Every drop acted, the default motion will be set to <code>DirectlyRemoveMotion</code> 
	 * so you need to set to yours every drop time if you want.
	 * @param motion the motion
	 * @see org.aswing.dnd.DropMotion
	 * @see org.aswing.dnd.DirectlyRemoveMotion
	 * @see org.aswing.dnd.RejectedMotion
	 * @see org.aswing.dnd.DragListener#onDragDrop()
	 */
	public static void  setDropMotion (DropMotion motion ){
		if(motion == null) motion = DEFAULT_DROP_MOTION;
		dropMotion = motion;
	}
	
	/**
	 * Returns the drag image.
	 */
	public static DraggingImage  getCurrentDragImage (){
		return s_dragImage;
	}
	
	/**
	 * Returns current drop target of dragging components by startDrag method.
	 * @return the drop target
	 * @see #startDrag()
	 * @see #getDropTarget()
	 */
	public static DisplayObject  getCurrentDropTarget (){
		return getDropTarget(curStage);
	}
	
	/**
	 * Returns current drop target component of specified position.
	 * @param pos the global point
	 * @return the drop target component
	 * @see #startDrag()
	 * @see #getDropTargetComponent()
	 */
	public static Component  getDropTargetComponent (Point pos =null ){
		return getDropTarget(curStage, pos, Component) as Component;
	}
	
	/**
	 * Returns current drop target component of dragging components by startDrag method.
	 * @return the drop target component
	 * @see #startDrag()
	 * @see #getDropTargetComponent()
	 */
	public static Component  getCurrentDropTargetComponent (){
		return getDropTarget(curStage, null, Component) as Component;
	}
	
	/**
	 * Returns drop target drop trigger component of specified global position.
	 * @param pos the point
	 * @return the drop target drop trigger component
	 * @see #startDrag()
	 * @see #getDropTargetDropTriggerComponent()
	 */
	public static Component  getDropTragetDropTriggerComponent (Point pos =null ){
		return getDropTarget(
			curStage, 
			pos, 
			Component, 
			____dropTargetCheck) as Component;
	}
	
	/**
	 * Returns current drop target drop trigger component of dragging components by startDrag method.
	 * @return the drop target drop trigger component
	 * @see #startDrag()
	 * @see #getDropTargetDropTriggerComponent()
	 */
	public static Component  getCurrentDropTargetDropTriggerComponent (){
		return getDropTarget(
			curStage, 
			null, 
			Component, 
			____dropTargetCheck) as Component;
	}
	
	private static boolean  ____dropTargetCheck (Component tar ){
		return tar.isDropTrigger();
	}
	
	/**
	 * Returns the drop target of specified position and specified class type.
	 * For example:<br/>
	 * <pre>
	 * getDropTarget(new Point(0, 0), TextField);
	 * will return the first textfield insance under the point, or null if not found.
	 * 
	 * getDropTarget(null, null);
	 * will return the first display object insance under the current mouse point, or null if not found.
	 * </pre>
	 * @param stage the stage where the drop target should be in
	 * @param pos The point under which to look, in the coordinate space of the Stage.
	 * @param targetType the class type of the target, default is null, means any display object.
	 * @param addtionCheck, a check function, only return the target when function(target:DisplayOject) return true. 
	 * default is null, means no this check.
	 * @return drop target
	 */
	public static  getDropTarget (Stage stage ,Point pos =null ,
		targetType:Class=null, 
		addtionCheck:Function=null):DisplayObject{
		if(stage == null){
			return null;
		}
		if(pos == null){
			pos = new Point(stage.mouseX, stage.mouseY);
		}
		if(targetType == null){
			targetType = DisplayObject;
		}
		if(addtionCheck == null){
			
		}
		Array targets =stage.getObjectsUnderPoint(pos );
		int n =targets.length ;
		for(int i =n -1;i >=0;i --){
			DisplayObject tar =targets.get(i) ;
			if(tar is targetType && tar != dragProxyMC && !dragProxyMC.contains(tar)){
				if(addtionCheck == null){
					return tar;
				}else if(addtionCheck(tar)){
					return tar;
				}
			}
		}
		return null;
	}
		
	//---------------------------------------------------------------------------------
	
	private static void  __onMouseMoveOnStage (Stage stage ){
		onMouseMove(stage.mouseX, stage.mouseY);
	}
	
	private static void  onMouseMove (double mx ,double my ){
		IntPoint globalPos =new IntPoint(mx ,my );
		Component dropC =getCurrentDropTargetDropTriggerComponent ();
		if(dropC != enteredComponent){
			if(enteredComponent != null){
				s_dragImage.switchToRejectImage();
				fireDragExitEvent(s_dragInitiator, s_sourceData, globalPos, enteredComponent, dropC);
				enteredComponent.fireDragExitEvent(s_dragInitiator, s_sourceData, globalPos, dropC);
			}
			if(dropC != null){
				if(dropC.isDragAcceptableInitiator(s_dragInitiator)){
					s_dragImage.switchToAcceptImage();
				}
				fireDragEnterEvent(s_dragInitiator, s_sourceData, globalPos, dropC, enteredComponent);
				dropC.fireDragEnterEvent(s_dragInitiator, s_sourceData, globalPos, enteredComponent);
			}
			enteredComponent = dropC;
		}else{
			if(enteredComponent != null){
				fireDragOverringEvent(s_dragInitiator, s_sourceData, globalPos, enteredComponent);
				enteredComponent.fireDragOverringEvent(s_dragInitiator, s_sourceData, globalPos);
			}
		}				
	}
	
	private static void  __onMouseMove (MouseEvent e ){
		onMouseMove(e.stageX, e.stageY);
	}
	
	private static void  __onMouseUp (MouseEvent e ){
		drop();
	}
	
	private static void  __onMouseDown (MouseEvent e ){
		drop(); 
		//why call drop again when mouse down?
		//just because if you released mouse outside of flash movie, then the mouse up
		//was not triggered. So if that happened, when user reclick mouse, the drop will 
		//fire (the right behavor to ensure dragging thing was dropped).
		//Well, if user released mouse rightly in the flash movie, then the drop be called, 
		//and in drop method, the mouse listener was removed, so it will not be called drop 
		//again when next mouse down. :)
	}
	
	private static void  drop (){
		dragProxyMC.stopDrag();
		IntPoint globalPos =AsWingUtils.getStageMousePosition ();
		Stage stage =curStage ;
		stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMove);
		stage.removeEventListener(MouseEvent.MOUSE_DOWN, __onMouseDown);
		stage.removeEventListener(MouseEvent.MOUSE_UP, __onMouseUp);
		s_isDragging = false;
		
		if(enteredComponent != null){
			setDropMotion(DEFAULT_DROP_MOTION);
		}else{
			setDropMotion(DEFAULT_REJECT_DROP_MOTION);
		}
		fireDragDropEvent(s_dragInitiator, s_sourceData, globalPos, enteredComponent);
		if(enteredComponent != null){
			enteredComponent.fireDragDropEvent(s_dragInitiator, s_sourceData, globalPos);
		}
		runningMotion = dropMotion;
		runningMotion.startMotionAndLaterRemove(s_dragInitiator, dragProxyMC);
		
		if(s_dragListener != null){
			removeDragListener(s_dragListener);
		}
		curStage = null;
		s_dragImage = null;
		s_dragListener = null;
		s_sourceData = null;
		enteredComponent = null;
	}
		
	private static void  fireDragStartEvent (Component dragInitiator ,SourceData sourceData ,IntPoint pos ){
		DragAndDropEvent e =new DragAndDropEvent(
			DragAndDropEvent.DRAG_START, 
			dragInitiator, 
			sourceData, 
			pos
		);
		for(int i =0;i <listeners.length ;i ++){
			DragListener lis =listeners.get(i) ;
			lis.onDragStart(e);
		}
	}
	
	private static void  fireDragEnterEvent (Component dragInitiator ,SourceData sourceData ,IntPoint pos ,Component targetComponent ,Component relatedTarget ){
		DragAndDropEvent e =new DragAndDropEvent(
			DragAndDropEvent.DRAG_ENTER, 
			dragInitiator, 
			sourceData, 
			pos, 
			targetComponent, 
			relatedTarget
		);
		for(int i =0;i <listeners.length ;i ++){
			DragListener lis =listeners.get(i) ;
			lis.onDragEnter(e);
		}
	}
	
	private static void  fireDragOverringEvent (Component dragInitiator ,SourceData sourceData ,IntPoint pos ,Component targetComponent ){
		DragAndDropEvent e =new DragAndDropEvent(
			DragAndDropEvent.DRAG_OVERRING, 
			dragInitiator, 
			sourceData, 
			pos, 
			targetComponent
		);
		for(int i =0;i <listeners.length ;i ++){
			DragListener lis =listeners.get(i) ;
			lis.onDragOverring(e);
		}
	}
	
	private static void  fireDragExitEvent (Component dragInitiator ,SourceData sourceData ,IntPoint pos ,Component targetComponent ,Component relatedTarget ){
		DragAndDropEvent e =new DragAndDropEvent(
			DragAndDropEvent.DRAG_EXIT, 
			dragInitiator, 
			sourceData, 
			pos, 
			targetComponent, 
			relatedTarget
		);
		for(int i =0;i <listeners.length ;i ++){
			DragListener lis =listeners.get(i) ;
			lis.onDragExit(e);
		}
	}
	
	private static void  fireDragDropEvent (Component dragInitiator ,SourceData sourceData ,IntPoint pos ,Component targetComponent ){
		DragAndDropEvent e =new DragAndDropEvent(
			DragAndDropEvent.DRAG_DROP, 
			dragInitiator, 
			sourceData, 
			pos, 
			targetComponent
		);		
		for(int i =0;i <listeners.length ;i ++){
			DragListener lis =listeners.get(i) ;
			lis.onDragDrop(e);
		}
	}	
}


