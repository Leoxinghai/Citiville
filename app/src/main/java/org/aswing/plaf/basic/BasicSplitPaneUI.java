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

	
import flash.display.DisplayObject;
import flash.display.Shape;
import flash.events.*;
import flash.geom.Point;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.splitpane.*;

/**
 * @private
 */
public class BasicSplitPaneUI extends SplitPaneUI implements LayoutManager{
	
	protected JSplitPane sp ;
	protected Divider divider ;
	protected IntDimension lastContentSize ;
	protected Object spLis ;
	protected Object mouseLis ;
	protected DisplayObject vSplitCursor ;
	protected DisplayObject hSplitCursor ;
	protected ASColor presentDragColor ;
	protected int defaultDividerSize ;
	
	protected IntPoint startDragPos ;
	protected int startLocation ;
	protected IntPoint startDividerPos ;
	protected Shape dragRepresentationMC ;
	protected boolean pressFlag ;//the flag for pressed left or right collapseButton
	protected boolean mouseInDividerFlag ;	
	
	public  BasicSplitPaneUI (){
		super();
	}
	
    protected String  getPropertyPrefix (){
        return "SplitPane.";
    }
    
     public void  installUI (Component c ){
        sp = JSplitPane(c);
        installDefaults();
        installComponents();
        installListeners();
    }

     public void  uninstallUI (Component c ){
        sp = JSplitPane(c);
        uninstallDefaults();
        uninstallComponents();
        uninstallListeners();
    }
    
    protected void  installDefaults (){
    	String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(sp, pp);
        LookAndFeel.installBorderAndBFDecorators(sp, pp);
        LookAndFeel.installBasicProperties(sp, pp);
        presentDragColor = getColor(pp+"presentDragColor");
        defaultDividerSize = getInt(pp+"defaultDividerSize");
        lastContentSize = new IntDimension();
        sp.setLayout(this);
    }

    protected void  uninstallDefaults (){
        LookAndFeel.uninstallBorderAndBFDecorators(sp);
        sp.setDividerLocation(0, true);
    }
	
	protected void  installComponents (){
		vSplitCursor = createSplitCursor(true);
		hSplitCursor = createSplitCursor(false);
		divider = createDivider();
		divider.setUIElement(true);
		sp.append(divider, JSplitPane.DIVIDER);
		
		divider.addEventListener(MouseEvent.MOUSE_DOWN, __div_pressed);
		divider.addEventListener(ReleaseEvent.RELEASE, __div_released);
		divider.addEventListener(MouseEvent.ROLL_OVER, __div_rollover);
		divider.addEventListener(MouseEvent.ROLL_OUT, __div_rollout);
		
		divider.getCollapseLeftButton().addEventListener(MouseEvent.ROLL_OVER, __div_rollout);
		divider.getCollapseRightButton().addEventListener(MouseEvent.ROLL_OVER, __div_rollout);
		divider.getCollapseLeftButton().addEventListener(MouseEvent.ROLL_OUT, __div_rollover);
		divider.getCollapseRightButton().addEventListener(MouseEvent.ROLL_OUT, __div_rollover);		
		divider.getCollapseLeftButton().addActionListener(__collapseLeft);
		divider.getCollapseRightButton().addActionListener(__collapseRight);
	}
	
	protected void  uninstallComponents (){
		sp.remove(divider);
		divider.removeEventListener(MouseEvent.MOUSE_DOWN, __div_pressed);
		divider.removeEventListener(ReleaseEvent.RELEASE, __div_released);
		divider.removeEventListener(MouseEvent.ROLL_OVER, __div_rollover);
		divider.removeEventListener(MouseEvent.ROLL_OUT, __div_rollout);
		
		divider.getCollapseLeftButton().removeEventListener(MouseEvent.ROLL_OVER, __div_rollout);
		divider.getCollapseRightButton().removeEventListener(MouseEvent.ROLL_OVER, __div_rollout);
		divider.getCollapseLeftButton().removeEventListener(MouseEvent.ROLL_OUT, __div_rollover);
		divider.getCollapseRightButton().removeEventListener(MouseEvent.ROLL_OUT, __div_rollover);
		divider.getCollapseLeftButton().removeActionListener(__collapseLeft);
		divider.getCollapseRightButton().removeActionListener(__collapseRight);
		divider = null;
	}
	
	protected void  installListeners (){
		sp.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __on_splitpane_key_down);
		sp.addEventListener(InteractiveEvent.STATE_CHANGED, __div_location_changed);
	}
	
	protected void  uninstallListeners (){
		sp.removeEventListener(KeyboardEvent.KEY_DOWN, __on_splitpane_key_down);
		sp.removeEventListener(InteractiveEvent.STATE_CHANGED, __div_location_changed);
	}
	
	/**
	 * Override this method to return a different splitCursor for your UI<br>
	 * Credit to Kristof Neirynck for added this.
	 */
	protected DisplayObject  createSplitCursor (boolean vertical ){
		DisplayObject result ;
		if(vertical){
			result = Cursor.createCursor(Cursor.V_MOVE_CURSOR);
		}else{
			result = Cursor.createCursor(Cursor.H_MOVE_CURSOR);
		}
		return result;
	}
	
	/**
	 * Override this method to return a different divider for your UI
	 */
	protected Divider  createDivider (){
		return new Divider(sp);
	}
    
	/**
	 * Override this method to return a different default divider size for your UI
	 */
    protected int  getDefaultDividerSize (){
    	return defaultDividerSize;
    }
    /**
	 * Override this method to return a different default DividerDragingRepresention for your UI
	 */
    protected void  paintDividerDragingRepresention (Graphics2D g ){
		g.fillRectangle(new SolidBrush(presentDragColor.changeAlpha(0.4)), 0, 0, 1, 1);
    }
	
    /**
     * Messaged to relayout the JSplitPane based on the preferred size
     * of the children components.
     */
     public void  resetToPreferredSizes (JSplitPane jc ){
    	int loc =jc.getDividerLocation ();
    	if(isVertical()){
    		if(jc.getLeftComponent() == null){
    			loc = 0;
    		}else{
    			loc = jc.getLeftComponent().getPreferredHeight();
    		}
    	}else{
    		if(jc.getLeftComponent() == null){
    			loc = 0;
    		}else{
    			loc = jc.getLeftComponent().getPreferredWidth();
    		}
    	}
		loc = Math.max(
			getMinimumDividerLocation(), 
			Math.min(loc, getMaximumDividerLocation()));
		jc.setDividerLocation(loc);
    }
    
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		divider.paintImmediately();
	}    
    
    public void  layoutWithLocation (int location ){
    	IntRectangle rect =sp.getSize ().getBounds(0,0);
    	rect = sp.getInsets().getInsideBounds(rect);
    	Component lc =sp.getLeftComponent ();
    	Component rc =sp.getRightComponent ();
    	int dvSize =getDividerSize ();
    	location = Math.floor(location);
    	if(location < 0){
    		//collapse left
			if(isVertical()){
				divider.setComBoundsXYWH(rect.x, rect.y, rect.width, dvSize);
				if(rc)
					rc.setComBoundsXYWH(rect.x, rect.y+dvSize, rect.width, rect.height-dvSize);
			}else{
				divider.setComBoundsXYWH(rect.x, rect.y, dvSize, rect.height);
				if(rc)
					rc.setComBoundsXYWH(rect.x+dvSize, rect.y, rect.width-dvSize, rect.height);
			}
			if(lc)
    			lc.setVisible(false);
    		if(rc)
    			rc.setVisible(true);
    	}else if(location == int.MAX_VALUE){
    		//collapse right
			if(isVertical()){
				divider.setComBoundsXYWH(
					rect.x, 
					rect.y+rect.height-dvSize, 
					rect.width, 
					dvSize);
				if(lc){
					lc.setComBoundsXYWH(
						rect.x, 
						rect.y,
						rect.width, 
						rect.height-dvSize);
				}
			}else{
				divider.setComBoundsXYWH(
					rect.x+rect.width-dvSize, 
					rect.y, 
					dvSize, 
					rect.height);
				if(lc){
					lc.setComBoundsXYWH(
						rect.x, 
						rect.y,
						rect.width-dvSize, 
						rect.height);
				}
			}
			if(lc)
    			lc.setVisible(true);
    		if(rc)
    			rc.setVisible(false);
    	}else{
    		//both visible
			if(isVertical()){
				divider.setComBoundsXYWH(
					rect.x, 
					rect.y+location, 
					rect.width, 
					dvSize);
				if(lc)
				lc.setComBoundsXYWH(
					rect.x, 
					rect.y,
					rect.width, 
					location);
				if(rc)
				rc.setComBoundsXYWH(
					rect.x, 
					rect.y+location+dvSize, 
					rect.width, 
					rect.height-location-dvSize
				);
			}else{
				divider.setComBoundsXYWH(
					rect.x+location, 
					rect.y, 
					dvSize, 
					rect.height);
				if(lc)
				lc.setComBoundsXYWH(
					rect.x, 
					rect.y,
					location, 
					rect.height);
				if(rc)
				rc.setComBoundsXYWH(
					rect.x+location+dvSize, 
					rect.y, 
					rect.width-location-dvSize, 
					rect.height
				);
			}
			if(lc)
    		lc.setVisible(true);
    		if(rc)
    		rc.setVisible(true);
    	}
    	if(lc)
    	lc.revalidateIfNecessary();
    	if(rc)
    	rc.revalidateIfNecessary();
    	divider.revalidateIfNecessary();
    }
    
    public int  getMinimumDividerLocation (){
    	Component leftCom =sp.getLeftComponent ();
    	if(leftCom == null){
    		return 0;
    	}else{
    		if(isVertical()){
    			return leftCom.getMinimumHeight();
    		}else{
    			return leftCom.getMinimumWidth();
    		}
    	}
    }
    
    public int  getMaximumDividerLocation (){
    	Component rightCom =sp.getRightComponent ();
    	Insets insets =sp.getInsets ();
    	int rightComSize =0;
    	if(rightCom != null){
    		rightComSize = isVertical() ? rightCom.getMinimumHeight() : rightCom.getMinimumWidth();
    	}
		if(isVertical()){
			return sp.getHeight() - insets.top - insets.bottom - getDividerSize() - rightComSize;
		}else{
			return sp.getWidth() - insets.left - insets.right - getDividerSize() - rightComSize;
		}
    }
    
    protected boolean  isVertical (){
    	return sp.getOrientation() == JSplitPane.VERTICAL_SPLIT;
    }
    
    protected int  getDividerSize (){
    	int si =sp.getDividerSize ();
    	if(si < 0){
    		return getDefaultDividerSize();
    	}else{
    		return si;
    	}
    }
    
    protected int  restrictDividerLocation (int loc ){
    	return Math.max(
				getMinimumDividerLocation(), 
				Math.min(loc, getMaximumDividerLocation()));
    }
    //-----------------------------------------------------------------------
    
	protected void  __collapseLeft (AWEvent e ){
		pressFlag = true;
		if(sp.getDividerLocation() == int.MAX_VALUE){
			sp.setDividerLocation(sp.getLastDividerLocation());
			divider.getCollapseLeftButton().setEnabled(true);
			divider.getCollapseRightButton().setEnabled(true);
		}else if(sp.getDividerLocation() >= 0){
			sp.setDividerLocation(-1);
			divider.getCollapseLeftButton().setEnabled(false);
		}else{
			divider.getCollapseLeftButton().setEnabled(true);
		}
	}

	protected void  __collapseRight (AWEvent e ){
		pressFlag = true;		
		if(sp.getDividerLocation() < 0){
			sp.setDividerLocation(sp.getLastDividerLocation());
			divider.getCollapseRightButton().setEnabled(true);
			divider.getCollapseLeftButton().setEnabled(true);
		}else if(sp.getDividerLocation() != int.MAX_VALUE){
			sp.setDividerLocation(int.MAX_VALUE);
			divider.getCollapseRightButton().setEnabled(false);
		}else{
			divider.getCollapseRightButton().setEnabled(false);
		}
	}
	
	protected void  __on_splitpane_key_down (FocusKeyEvent e ){
		int code =e.keyCode ;
		double dir =0;
		if(code == KeyStroke.VK_HOME.getCode()){
			if(sp.getDividerLocation() < 0){
				sp.setDividerLocation(sp.getLastDividerLocation());
			}else{
				sp.setDividerLocation(-1);
			}
			return;
		}else if(code == KeyStroke.VK_END.getCode()){
			if(sp.getDividerLocation() == int.MAX_VALUE){
				sp.setDividerLocation(sp.getLastDividerLocation());
			}else{
				sp.setDividerLocation(int.MAX_VALUE);
			}
			return;
		}
		if(code == KeyStroke.VK_LEFT.getCode() || code == KeyStroke.VK_UP.getCode()){
			dir = -1;
		}else if(code == KeyStroke.VK_RIGHT.getCode() || code == KeyStroke.VK_DOWN.getCode()){
			dir = 1;
		}
		if(e.shiftKey){
			dir = 			10;
		}
		sp.setDividerLocation(restrictDividerLocation(sp.getDividerLocation() + dir));
	}
    
    protected void  __div_location_changed (InteractiveEvent e ){
    	layoutWithLocation(sp.getDividerLocation());
        if(sp.getDividerLocation() >= 0 && sp.getDividerLocation() != int.MAX_VALUE){
        	divider.setEnabled(true);
        }else{
        	divider.setEnabled(false);
        }
    }
	
	protected void  __div_pressed (MouseEvent e ){
		if (e.target != divider){
			pressFlag = true;
			return;
		}
		spliting = true;
		showMoveCursor();
		startDragPos = sp.getMousePosition();
		startLocation = sp.getDividerLocation();
		startDividerPos = divider.getGlobalLocation();
		sp.removeEventListener(MouseEvent.MOUSE_MOVE, __div_mouse_moving);
		sp.addEventListener(MouseEvent.MOUSE_MOVE, __div_mouse_moving);
	}

	protected void  __div_released (ReleaseEvent e ){
		if (e.getPressTarget() != divider) return;		
		if (pressFlag){
			pressFlag = false;
			return;
		}
		if(dragRepresentationMC != null && sp.contains(dragRepresentationMC)){
			sp.removeChild(dragRepresentationMC);
		}
		
		validateDivMoveWithCurrentMousePos();
		sp.removeEventListener(MouseEvent.MOUSE_MOVE, __div_mouse_moving);
		spliting = false;
		if(!mouseInDividerFlag){
			hideMoveCursor();
		}
	}

	protected void  __div_mouse_moving (MouseEvent e ){
		if(!sp.isContinuousLayout()){
			if(dragRepresentationMC == null){
				dragRepresentationMC = new Shape();
				Graphics2D g =new Graphics2D(dragRepresentationMC.graphics );
				paintDividerDragingRepresention(g);
			}
			if(!sp.contains(dragRepresentationMC)){
				sp.addChild(dragRepresentationMC);
			}
			IntPoint newGlobalPos =startDividerPos.clone ();
			if(isVertical()){
				newGlobalPos.y += getCurrentMovedDistance();
			}else{
				newGlobalPos.x += getCurrentMovedDistance();
			}
			Point newPoint =newGlobalPos.toPoint ();
			newPoint = dragRepresentationMC.parent.globalToLocal(newPoint);
			dragRepresentationMC.x = Math.round(newPoint.x);
			dragRepresentationMC.y = Math.round(newPoint.y);
			dragRepresentationMC.width = divider.width;
			dragRepresentationMC.height = divider.height;
		}else{
			validateDivMoveWithCurrentMousePos();
		}
	}
	
	protected void  validateDivMoveWithCurrentMousePos (){
		int newLocation =startLocation +getCurrentMovedDistance ();
		sp.setDividerLocation(newLocation);
	}
	
	protected int  getCurrentMovedDistance (){
		IntPoint mouseP =sp.getMousePosition ();
		int delta =0;
		if(isVertical()){
			delta = mouseP.y - startDragPos.y;
		}else{
			delta = mouseP.x - startDragPos.x;
		}
		int newLocation =startLocation +delta ;
		newLocation = Math.max(
			getMinimumDividerLocation(), 
			Math.min(newLocation, getMaximumDividerLocation()));
		return newLocation - startLocation;
	}
	
	protected void  __div_rollover (MouseEvent e ){
		mouseInDividerFlag = true;
		if(!e.buttonDown && !spliting){
			showMoveCursor();
		}
	}

	protected void  __div_rollout (Event e ){
		mouseInDividerFlag = false;
		if(!spliting){
			hideMoveCursor();
		}
	}
	
	protected boolean spliting =false ;
	protected CursorManager cursorManager ;
	protected void  showMoveCursor (){
		cursorManager = CursorManager.getManager(sp.stage);
		if(isVertical()){
			cursorManager.hideCustomCursor(hSplitCursor);
			cursorManager.showCustomCursor(vSplitCursor);
		}else{
			cursorManager.hideCustomCursor(vSplitCursor);
			cursorManager.showCustomCursor(hSplitCursor);
		}
	}
	
	protected void  hideMoveCursor (){
		if(cursorManager == null){
			return;
		}
		cursorManager.hideCustomCursor(vSplitCursor);
		cursorManager.hideCustomCursor(hSplitCursor);
		cursorManager = null;
	}
    
    //-----------------------------------------------------------------------
    //                     Layout implementation
    //-----------------------------------------------------------------------
	public void  addLayoutComponent (Component comp ,Object constraints ){
	}

	public void  removeLayoutComponent (Component comp ){
	}

	public IntDimension  preferredLayoutSize (Container target ){
		Insets insets =sp.getInsets ();
    	Component lc =sp.getLeftComponent ();
    	Component rc =sp.getRightComponent ();
    	IntDimension lcSize =(lc ==null ? new IntDimension() : lc.getPreferredSize());
    	IntDimension rcSize =(rc ==null ? new IntDimension() : rc.getPreferredSize());
    	IntDimension size ;
    	if(isVertical()){
    		size = new IntDimension(
    			Math.max(lcSize.width, rcSize.width), 
    			lcSize.height + rcSize.height + getDividerSize()
    		);
    	}else{
    		size = new IntDimension(
    			lcSize.width + rcSize.width + getDividerSize(), 
    			Math.max(lcSize.height, rcSize.height)
    		);
    	}
    	return insets.getOutsideSize(size);
	}

	public IntDimension  minimumLayoutSize (Container target ){
		return target.getInsets().getOutsideSize();
	}

	public IntDimension  maximumLayoutSize (Container target ){
		return IntDimension.createBigDimension();
	}
	
	public void  layoutContainer (Container target ){
		IntDimension size =sp.getSize ();
		size = sp.getInsets().getInsideSize(size);
		boolean layouted =false ;
		if(!size.equals(lastContentSize)){
			//re weight the split
			int deltaSize =0;
			if(isVertical()){
				deltaSize = size.height - lastContentSize.height;
			}else{
				deltaSize = size.width - lastContentSize.width;
			}
			lastContentSize = size.clone();
			int locationDelta =deltaSize *sp.getResizeWeight ();
			layouted = (locationDelta != 0);
			int newLocation =sp.getDividerLocation ()+locationDelta ;
			
			newLocation = Math.max(
				getMinimumDividerLocation(), 
				Math.min(newLocation, getMaximumDividerLocation()));
			
			sp.setDividerLocation(newLocation, true);
		}
		if(!layouted){
			layoutWithLocation(sp.getDividerLocation());
		}
	}

	public double  getLayoutAlignmentX (Container target ){
		return 0;
	}

	public double  getLayoutAlignmentY (Container target ){
		return 0;
	}

	public void  invalidateLayout (Container target ){
	}
}


