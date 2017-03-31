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

	
import org.aswing.plaf.BaseComponentUI;
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.*;
import org.aswing.event.*;
import flash.events.*;
import org.aswing.plaf.basic.icon.ArrowIcon;
import flash.display.Sprite;
import flash.events.MouseEvent;
import flash.ui.Keyboard;

/**
 * The basic scrollbar ui.
 * @author iiley
 * @private
 */
public class BasicScrollBarUI extends BaseComponentUI{
	
	protected int scrollBarWidth ;
	protected int minimumThumbLength ;
	protected IntRectangle thumbRect ;
	protected boolean isDragging ;
	protected int offset ;
	
    private ASColor arrowShadowColor ;
    private ASColor arrowLightColor ;
    
    protected JScrollBar scrollbar ;
    protected AWSprite thumMC ;
	protected GroundDecorator thumbDecorator ;
    protected JButton incrButton ;
    protected JButton decrButton ;
    protected Icon leftIcon ;
    protected Icon rightIcon ;
    protected Icon upIcon ;
    protected Icon downIcon ;
        
    private static int scrollSpeedThrottle =60;//delay in milli seconds
    private static int initialScrollSpeedThrottle =500;//first delay in milli seconds
    private Timer scrollTimer ;
    private int scrollIncrement ;
    private int scrollContinueDestination ;
	
	public  BasicScrollBarUI (){
		scrollBarWidth = 16;
		minimumThumbLength = 9;
		thumbRect = new IntRectangle();
		isDragging = false;
		offset = 0;
		scrollIncrement = 0;
	}
    	
    protected String  getPropertyPrefix (){
        return "ScrollBar.";
    }    	
    	
     public void  installUI (Component c ){
		scrollbar = JScrollBar(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
		scrollbar = JScrollBar(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
	
	protected void  installDefaults (){
		configureScrollBarColors();
		String pp =getPropertyPrefix ();
		if(containsKey(pp+"barWidth")){
			scrollBarWidth = getInt(pp+"barWidth");
		}
		if(containsKey(pp+"minimumThumbLength")){
			minimumThumbLength = getInt(pp+"minimumThumbLength");
		}
		LookAndFeel.installBasicProperties(scrollbar, pp);
        LookAndFeel.installBorderAndBFDecorators(scrollbar, pp);
	}
	
    private void  configureScrollBarColors (){
		String pp =getPropertyPrefix ();
    	LookAndFeel.installColorsAndFont(scrollbar, pp);
		arrowShadowColor = getColor(pp + "arrowShadowColor");
		arrowLightColor = getColor(pp + "arrowLightColor");
    }
    
    protected void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(scrollbar);
    }
    
	protected void  installComponents (){
		thumMC = new AWSprite();
		String pp =getPropertyPrefix ();
		thumbDecorator = getGroundDecorator(pp + "thumbDecorator");
		if(thumbDecorator != null){
			if(thumbDecorator.getDisplay(scrollbar) != null){
				thumMC.addChild(thumbDecorator.getDisplay(scrollbar));
			}
		}
		scrollbar.addChild(thumMC);
		thumMC.addEventListener(MouseEvent.MOUSE_DOWN, __startDragThumb);
		thumMC.addEventListener(ReleaseEvent.RELEASE, __stopDragThumb);
		createIcons();
    	incrButton = createArrowButton();
    	incrButton.setName("JScrollbar_incrButton");
    	decrButton = createArrowButton();
    	decrButton.setName("JScrollbar_decrButton");
    	setButtonIcons();
        incrButton.setUIElement(true);
		decrButton.setUIElement(true);
        scrollbar.addChild(incrButton);
        scrollbar.addChild(decrButton);
		scrollbar.setEnabled(scrollbar.isEnabled());
    }
	protected void  uninstallComponents (){
		scrollbar.removeChild(incrButton);
		scrollbar.removeChild(decrButton);
		scrollbar.removeChild(thumMC);
		thumMC.removeEventListener(MouseEvent.MOUSE_DOWN, __startDragThumb);
		thumMC.removeEventListener(ReleaseEvent.RELEASE, __stopDragThumb);
		thumbDecorator = null;
    }
	
	protected void  installListeners (){
		scrollbar.addStateListener(__adjustChanged);
		
		incrButton.addEventListener(MouseEvent.MOUSE_DOWN, __incrButtonPress);
		incrButton.addEventListener(ReleaseEvent.RELEASE, __incrButtonReleased);
		
		decrButton.addEventListener(MouseEvent.MOUSE_DOWN, __decrButtonPress);
		decrButton.addEventListener(ReleaseEvent.RELEASE, __decrButtonReleased);
				
		scrollbar.addEventListener(MouseEvent.MOUSE_DOWN, __trackPress);
		scrollbar.addEventListener(ReleaseEvent.RELEASE, __trackReleased);
		
		scrollbar.addEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
		scrollbar.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		
		scrollbar.addEventListener(Event.REMOVED_FROM_STAGE, __destroy);
		
		scrollTimer = new Timer(scrollSpeedThrottle);
		scrollTimer.setInitialDelay(initialScrollSpeedThrottle);
		scrollTimer.addActionListener(__scrollTimerPerformed);
	}
    
    protected void  uninstallListeners (){
		scrollbar.removeStateListener(__adjustChanged);
		
		incrButton.removeEventListener(MouseEvent.MOUSE_DOWN, __incrButtonPress);
		incrButton.removeEventListener(ReleaseEvent.RELEASE, __incrButtonReleased);
		
		decrButton.removeEventListener(MouseEvent.MOUSE_DOWN, __decrButtonPress);
		decrButton.removeEventListener(ReleaseEvent.RELEASE, __decrButtonReleased);
				
		scrollbar.removeEventListener(MouseEvent.MOUSE_DOWN, __trackPress);
		scrollbar.removeEventListener(ReleaseEvent.RELEASE, __trackReleased);
		
		scrollbar.removeEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
		scrollbar.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		scrollbar.removeEventListener(Event.REMOVED_FROM_STAGE, __destroy);
		scrollTimer.stop();
		scrollTimer = null;
    }
	    
    protected boolean  isVertical (){
    	return scrollbar.getOrientation() == JScrollBar.VERTICAL;
    }
    
    protected IntRectangle  getThumbRect (){
    	return thumbRect.clone();
    }
    
    //-------------------------listeners--------------------------
    
    private void  __destroy (Event e ){
    	scrollTimer.stop();
    	if(isDragging){
    		scrollbar.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMoveThumb);
    	}
    }
    
    private void  __onMouseWheel (MouseEvent e ){
		if(!scrollbar.isEnabled()){
			return;
		}
    	scrollByIncrement(-e.delta * scrollbar.getUnitIncrement());
    }
    
    private void  __onKeyDown (FocusKeyEvent e ){
		if(!(scrollbar.isEnabled() && scrollbar.isShowing())){
			return;
		}
    	int code =e.keyCode ;
    	if(code == Keyboard.UP || code == Keyboard.LEFT){
    		scrollByIncrement(-scrollbar.getUnitIncrement());
    	}else if(code == Keyboard.DOWN || code == Keyboard.RIGHT){
    		scrollByIncrement(scrollbar.getUnitIncrement());
    	}else if(code == Keyboard.PAGE_UP){
    		scrollByIncrement(-scrollbar.getBlockIncrement());
    	}else if(code == Keyboard.PAGE_DOWN){
    		scrollByIncrement(scrollbar.getBlockIncrement());
    	}else if(code == Keyboard.HOME){
    		scrollbar.setValue(scrollbar.getMinimum());
    	}else if(code == Keyboard.END){
    		scrollbar.setValue(scrollbar.getMaximum() - scrollbar.getVisibleAmount());
    	}
    }
    
    private void  __scrollTimerPerformed (AWEvent e ){
    	int value =scrollbar.getValue ()+scrollIncrement ;
    	boolean finished =false ;
    	if(scrollIncrement > 0){
    		if(value >= scrollContinueDestination){
    			finished = true;
    		}
    	}else{
    		if(value <= scrollContinueDestination){
    			finished = true;
    		}
    	}
    	if(finished){
    		scrollbar.setValue(scrollContinueDestination, false);
    		scrollTimer.stop();
    	}else{
    		scrollByIncrement(scrollIncrement);
    	}
    }
    
    private void  __adjustChanged (Event e ){
    	if(scrollbar.isVisible() && !isDragging)
    		paintAndLocateThumb(scrollbar.getPaintBounds());
    }
    
    private void  __incrButtonPress (Event e ){
    	scrollIncrement = scrollbar.getUnitIncrement();
    	scrollByIncrement(scrollIncrement);
    	scrollContinueDestination = scrollbar.getMaximum() - scrollbar.getVisibleAmount();
    	scrollTimer.restart();
    }
    
    private void  __incrButtonReleased (Event e ){
    	scrollTimer.stop();
    }
    
    private void  __decrButtonPress (Event e ){
    	scrollIncrement = -scrollbar.getUnitIncrement();
    	scrollByIncrement(scrollIncrement);
    	scrollContinueDestination = scrollbar.getMinimum();
    	scrollTimer.restart();
    }
    
    private void  __decrButtonReleased (Event e ){
    	scrollTimer.stop();
    }
    
    private void  __trackPress (MouseEvent e ){
    	IntPoint aimPoint =scrollbar.getMousePosition ();
    	boolean isPressedInRange =false ;
    	IntRectangle tr =getThumbRect ();
    	int mousePos ;
    	if(isVertical()){
    		mousePos = aimPoint.y;
    		aimPoint.y -= tr.height/2;
    		if(mousePos < tr.y && mousePos > decrButton.y + decrButton.height){
    			isPressedInRange = true;
    		}else if(mousePos > tr.y + tr.height && mousePos < incrButton.y){
    			isPressedInRange = true;
    		}
    	}else{
    		mousePos = aimPoint.x;
    		aimPoint.x -= tr.width/2;
    		if(mousePos < tr.x && mousePos > decrButton.x + decrButton.width){
    			isPressedInRange = true;
    		}else if(mousePos > tr.x + tr.width && mousePos < incrButton.x){
    			isPressedInRange = true;
    		}
    	}
    	
    	if(isPressedInRange){
    		scrollContinueDestination = getValueWithPosition(aimPoint);
    		if(scrollContinueDestination > scrollbar.getValue()){
    			scrollIncrement = scrollbar.getBlockIncrement();
    		}else{
    			scrollIncrement = -scrollbar.getBlockIncrement();
    		}
    		scrollByIncrement(scrollIncrement);
    		scrollTimer.restart();
    	}
    }
    
    private void  __trackReleased (Event e ){
    	scrollTimer.stop();
    }
        
    private void  scrollByIncrement (int increment ){
    	scrollbar.setValue(scrollbar.getValue() + increment, false);
    }
    
    private void  __startDragThumb (Event e ){
    	if(!scrollbar.isEnabled()){
    		return;
    	}
    	scrollbar.setValueIsAdjusting(true);
    	IntPoint mp =scrollbar.getMousePosition ();
    	int mx =mp.x ;
    	int my =mp.y ;
    	IntRectangle tr =getThumbRect ();
    	if(isVertical()){
    		offset = my - tr.y;
    	}else{
    		offset = mx - tr.x;
    	}
    	isDragging = true;
    	__startHandleDrag();
    }
    
    private void  __stopDragThumb (Event e ){
    	__stopHandleDrag();
    	if(!scrollbar.isEnabled()){
    		return;
    	}
    	if(isDragging){
    		scrollThumbToCurrentMousePosition();
    	}
    	offset = 0;
    	isDragging = false;
    	scrollbar.setValueIsAdjusting(false);
    }
    
    private void  __startHandleDrag (){
    	scrollbar.stage.addEventListener(MouseEvent.MOUSE_MOVE, __onMoveThumb, false, 0, true);
    }
    private void  __stopHandleDrag (){
    	scrollbar.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMoveThumb);
    }
    
    private void  __onMoveThumb (MouseEvent e ){
    	if(!scrollbar.isEnabled()){
    		return;
    	}
    	scrollThumbToCurrentMousePosition();
    	e.updateAfterEvent();
    }
    
    private void  scrollThumbToCurrentMousePosition (){
    	IntPoint mp =scrollbar.getMousePosition ();
    	int mx =mp.x ;
    	int my =mp.y ;
    	IntRectangle thumbR =getThumbRect ();
    	
	    int thumbMin ,thumbMax int ,thumbPos ;
	    
    	if(isVertical()){
			thumbMin = decrButton.getY() + decrButton.getHeight();
			thumbMax = incrButton.getY() - thumbR.height;
			thumbPos = Math.min(thumbMax, Math.max(thumbMin, (my - offset)));
			setThumbRect(thumbR.x, thumbPos, thumbR.width, thumbR.height);	
    	}else{
		    thumbMin = decrButton.getX() + decrButton.getWidth();
		    thumbMax = incrButton.getX() - thumbR.width;
			thumbPos = Math.min(thumbMax, Math.max(thumbMin, (mx - offset)));
			setThumbRect(thumbPos, thumbR.y, thumbR.width, thumbR.height);
    	}
    	
    	int scrollBarValue =getValueWithThumbMaxMinPos(thumbMin ,thumbMax ,thumbPos );
    	scrollbar.setValue(scrollBarValue, false);
    }
    
    private int  getValueWithPosition (IntPoint point ){
    	int mx =point.x ;
    	int my =point.y ;
    	IntRectangle thumbR =getThumbRect ();
    	
	    int thumbMin ,thumbMax int ,thumbPos ;
	    
    	if(isVertical()){
			thumbMin = decrButton.getY() + decrButton.getHeight();
			thumbMax = incrButton.getY() - thumbR.height;
			thumbPos = my;
    	}else{
		    thumbMin = decrButton.getX() + decrButton.getWidth();
		    thumbMax = incrButton.getX() - thumbR.width;
		    thumbPos = mx;
    	}
    	return getValueWithThumbMaxMinPos(thumbMin, thumbMax, thumbPos);
    }
    
    private int  getValueWithThumbMaxMinPos (int thumbMin ,int thumbMax ,int thumbPos ){
    	BoundedRangeModel model =scrollbar.getModel ();
    	int scrollBarValue ;
    	if (thumbPos >= thumbMax) {
    		scrollBarValue = model.getMaximum() - model.getExtent();
    	}else{
			int valueMax =model.getMaximum ()-model.getExtent ();
			int valueRange =valueMax -model.getMinimum ();
			int thumbValue =thumbPos -thumbMin ;
			int thumbRange =thumbMax -thumbMin ;
			int value =Math.round ((thumbValue /thumbRange )*valueRange );
			scrollBarValue = value + model.getMinimum();
    	}
    	return scrollBarValue;    	
    }
    
    //--------------------------paints----------------------------
     public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
    	super.paint(c, g, b);
    	layoutScrollBar();
    	paintAndLocateThumb(b);
    }
    
    private void  paintAndLocateThumb (IntRectangle b ){
     	if(!scrollbar.isEnabled()){
    		if(isVertical()){
    			if(scrollbar.mouseChildren){
    				trace("Logic Wrong : Scrollbar is not enabled, but its children enabled ");
    			}
    		}
    		thumMC.visible = false;
    		return;
    	}
    	thumMC.visible = true;
    	int min =scrollbar.getMinimum ();
    	int extent =scrollbar.getVisibleAmount ();
    	int range =scrollbar.getMaximum ()-min ;
    	int value =scrollbar.getValue ();
    	
    	if(range <= 0){
    		if(range < 0)
    			trace("Logic Wrong : Scrollbar range = " + range + ", max="+scrollbar.getMaximum()+", min="+min);
    		thumMC.visible = false;
    		return;
    	}
    	
    	int trackLength ;
    	int thumbLength ;
    	if(isVertical()){
    		trackLength = b.height - incrButton.getHeight() - decrButton.getHeight();
    		thumbLength = Math.floor(trackLength*(extent/range));
    	}else{
    		trackLength = b.width - incrButton.getWidth() - decrButton.getWidth();
    		thumbLength = Math.floor(trackLength*(extent/range));
    	}
    	if(trackLength > minimumThumbLength){
    		thumbLength = Math.max(thumbLength, minimumThumbLength);
    	}else{
			//trace("The visible range is so short can't view thumb now!");
    		thumMC.visible = false;
    		return;
    	}
    	
		int thumbRange =trackLength -thumbLength ;
		int thumbPos ;
		if((range - extent) == 0){
			thumbPos = 0;
		}else{
			thumbPos = Math.round(thumbRange * ((value - min) / (range - extent)));
		}
		if(isVertical()){
			setThumbRect(b.x, thumbPos + b.y + decrButton.getHeight(), 
						scrollBarWidth, thumbLength);
		}else{
			setThumbRect(thumbPos + b.x + decrButton.getWidth(), b.y, 
						thumbLength, scrollBarWidth);
		}
    }
    
    private void  setThumbRect (int x ,int y ,int w ,int h ){
    	int oldW =thumbRect.width ;
    	int oldH =thumbRect.height ;
    	
    	thumbRect.setRectXYWH(x, y, w, h);
    	
    	if(w != oldW || h != oldH){
    		paintThumb(thumMC, thumbRect.getSize(), isDragging);
    	}
    	thumMC.x = x;
    	thumMC.y = y;
    }
    
    /**
     * LAF notice.
     * 
     * Override this method to paint diff thumb in your LAF.
     */
    private void  paintThumb (Sprite thumMC ,IntDimension size ,boolean isPressed ){
    	thumMC.graphics.clear();
    	Graphics2D g =new Graphics2D(thumMC.graphics );
    	if(thumbDecorator != null){
    		thumbDecorator.updateDecorator(scrollbar, g, size.getBounds());
    	}
    }
    /**
     * LAF notice.
     * 
     * Override this method to paint diff thumb in your LAF.
     */    
    protected Icon  createArrowIcon (double direction ){
    	Icon icon =new ArrowIcon(direction ,scrollBarWidth /2,
				    arrowLightColor,
				    arrowShadowColor);
		return icon;
    }
    
    /**
     * LAF notice.
     * 
     * Override this method to paint diff thumb in your LAF.
     */    
    protected JButton  createArrowButton (){
		JButton b =new JButton ();
		b.setFocusable(false);
		return b;
    }
        
    protected void  createIcons (){
    	leftIcon = createArrowIcon(Math.PI);
    	rightIcon = createArrowIcon(0);
    	upIcon = createArrowIcon(-Math.PI/2);
    	downIcon = createArrowIcon(Math.PI/2);
    }
    
    protected void  setButtonIcons (){
    	if(isVertical()){
    		incrButton.setIcon(downIcon);
    		decrButton.setIcon(upIcon);
    	}else{
    		incrButton.setIcon(rightIcon);
    		decrButton.setIcon(leftIcon);
    	}
    }     
    //--------------------------Dimensions----------------------------
    
     public IntDimension  getPreferredSize (Component c ){
		int w ,h int ;
		if(isVertical()){
			w = scrollBarWidth;
			h = scrollBarWidth*2;
		}else{
			w = scrollBarWidth*2;
			h = scrollBarWidth;
		}
		return scrollbar.getInsets().getOutsideSize(new IntDimension(w, h));
    }

     public IntDimension  getMaximumSize (Component c ){
		int w ,h int ;
		if(isVertical()){
			w = scrollBarWidth;
			h = 100000;
		}else{
			w = 100000;
			h = scrollBarWidth;
		}
		return scrollbar.getInsets().getOutsideSize(new IntDimension(w, h));
    }

     public IntDimension  getMinimumSize (Component c ){
		return getPreferredSize(c);
    }
	
	//--------------------------Layout----------------------------
	protected void  layoutVScrollbar (JScrollBar sb ){
    	IntRectangle rd =sb.getPaintBounds ();
    	int w =scrollBarWidth ;
    	decrButton.setComBoundsXYWH(rd.x, rd.y, w, w);
    	incrButton.setComBoundsXYWH(rd.x, rd.y + rd.height - w, w, w);
	}
	
	protected void  layoutHScrollbar (JScrollBar sb ){
    	IntRectangle rd =sb.getPaintBounds ();
    	int w =scrollBarWidth ;
    	decrButton.setComBoundsXYWH(rd.x, rd.y, w, w);
    	incrButton.setComBoundsXYWH(rd.x + rd.width - w, rd.y, w, w);
	}
	    
	public void  layoutScrollBar (){
		if(isDragging){
			return;
		}
		setButtonIcons();
		if(isVertical()){
			layoutVScrollbar(scrollbar);
		}else{
			layoutHScrollbar(scrollbar);
		}
    }
	

}


