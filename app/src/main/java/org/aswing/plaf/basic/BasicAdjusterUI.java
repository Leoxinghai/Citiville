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

	
import flash.display.InteractiveObject;
import flash.events.Event;
import flash.events.FocusEvent;
import flash.events.MouseEvent;
import flash.ui.Keyboard;

import org.aswing.*;
import org.aswing.border.BevelBorder;
import org.aswing.event.AWEvent;
import org.aswing.event.FocusKeyEvent;
import org.aswing.event.ReleaseEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.adjuster.PopupSliderUI;
import org.aswing.plaf.basic.icon.ArrowIcon;

/**
 * Basic adjust ui imp.
 * @author iiley
 * @private
 */
public class BasicAdjusterUI extends BaseComponentUI implements AdjusterUI{
	
	protected JAdjuster adjuster ;
	protected Component arrowButton ;
	protected JPopup popup ;
	protected JTextField inputText ;
	protected JSlider popupSlider ;
	protected SliderUI popupSliderUI ;
	protected IntPoint startMousePoint ;
	protected double startValue ;
	
	protected ASColor thumbLightHighlightColor ;
    protected ASColor thumbHighlightColor ;
    protected ASColor thumbLightShadowColor ;
    protected ASColor thumbDarkShadowColor ;
    protected ASColor thumbColor ;
    protected ASColor arrowShadowColor ;
    protected ASColor arrowLightColor ;
    
    protected ASColor highlightColor ;
    protected ASColor shadowColor ;
    protected ASColor darkShadowColor ;
    protected ASColor lightColor ;	
	
	public  BasicAdjusterUI (){
		super();
		inputText   = new JTextField("", 3);
		inputText.setFocusable(false);
		popupSlider = new JSlider();
		popupSlider.setFocusable(false);
	}
	
	public JSlider  getPopupSlider (){
		return popupSlider;
	}
	
	public JTextField  getInputText (){
		return inputText;
	}
	
     public void  installUI (Component c ){
    	adjuster = JAdjuster(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
    	adjuster = JAdjuster(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
    
	protected String  getPropertyPrefix (){
		return "Adjuster.";
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installBorderAndBFDecorators(adjuster, pp);
        LookAndFeel.installColorsAndFont(adjuster, pp);
        LookAndFeel.installBasicProperties(adjuster, pp);
		arrowShadowColor = getColor(pp+"arrowShadowColor");
		arrowLightColor = getColor(pp+"arrowLightColor");
	}
    
    protected void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(adjuster);
    }
    
	protected void  installComponents (){
		initInputText();
		initPopupSlider();
		arrowButton = createArrowButton();
		arrowButton.setUIElement(true);
		popupSlider.setUIElement(true);
		popupSliderUI = createPopupSliderUI();
		popupSlider.setUI(popupSliderUI);
		popupSlider.setModel(adjuster.getModel());
		adjuster.addChild(inputText);
		adjuster.addChild(arrowButton);
		
		inputText.addEventListener(MouseEvent.MOUSE_WHEEL, __onInputTextMouseWheel);
		arrowButton.addEventListener(MouseEvent.MOUSE_DOWN, __onArrowButtonPressed);
		arrowButton.addEventListener(ReleaseEvent.RELEASE, __onArrowButtonReleased);
    }
    
	protected void  uninstallComponents (){
		inputText.removeEventListener(MouseEvent.MOUSE_WHEEL, __onInputTextMouseWheel);
		arrowButton.removeEventListener(MouseEvent.MOUSE_DOWN, __onArrowButtonPressed);
		arrowButton.removeEventListener(ReleaseEvent.RELEASE, __onArrowButtonReleased);
		
		adjuster.removeChild(arrowButton);
		adjuster.removeChild(inputText);
		if(popup != null && popup.isVisible()){
			popup.dispose();
		}
    }
	
	protected void  installListeners (){
		adjuster.addStateListener(__onValueChanged);
		adjuster.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onInputTextKeyDown);
		adjuster.addEventListener(AWEvent.FOCUS_GAINED, __onFocusGained);
		adjuster.addEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
	}
    
    protected void  uninstallListeners (){
		adjuster.removeStateListener(__onValueChanged);
		adjuster.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onInputTextKeyDown);
		adjuster.removeEventListener(AWEvent.FOCUS_GAINED, __onFocusGained);
		adjuster.removeEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
    }
    
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		String text =getShouldFilledText ();
		if(text != inputText.getText()){
			inputText.setText(text);
		}
		layoutAdjuster();
		getInputText().setEditable(adjuster.isEditable());
		getInputText().setEnabled(adjuster.isEnabled());
		arrowButton.setEnabled(adjuster.isEnabled());
	}
	
	
	//*******************************************************************************
	//              Override these methods to easily implement different look
	//*******************************************************************************
    /**
     * Returns the input text to receive the focus for the component.
     * @param c the component
     * @return the object to receive the focus.
     */
	 public InteractiveObject  getInternalFocusObject (Component c ){
		return inputText.getTextField();
	}
	
	protected void  initInputText (){
		inputText.setColumns(adjuster.getColumns());
		inputText.setForeground(null);//make it grap the property from parent
		inputText.setFont(adjuster.getFont());
	}
	
	protected void  initPopupSlider (){
		popupSlider.setOrientation(adjuster.getOrientation());
	}
	
	protected Component  createArrowButton (){
		JButton btn =new JButton(null ,createArrowIcon ());
		btn.setMargin(new Insets(2, 2, 2, 2));
		btn.setForeground(null);//make it grap the property from parent
		btn.setBackground(null);//make it grap the property from parent
		btn.setFont(null);//make it grap the property from parent
		btn.setFocusable(false);
		return btn;
	}
	
	protected SliderUI  createPopupSliderUI (){
		return new PopupSliderUI();
	}
	
	protected Icon  createArrowIcon (){
		return new ArrowIcon(Math.PI/2, 6,
				    arrowLightColor,
				    arrowShadowColor);
	}
		
	protected JPopup  getPopup (){
		if(popup == null){
			popup = new JPopup();
			popup.setBorder(new BevelBorder(null, BevelBorder.RAISED));
			popup.append(popupSlider, BorderLayout.CENTER);
			popup.setBackground(adjuster.getBackground());
		}
		return popup;
	}
	
	protected void  fillInputTextWithCurrentValue (){
		inputText.setText(getShouldFilledText());
	}
	
	protected String  getShouldFilledText (){
		int value =adjuster.getValue ();
		String text =adjuster.getValueTranslator ()(value );
		return text;
	}
	
	protected int  getTextButtonGap (){
		return 1;
	}
	
	protected void  layoutAdjuster (){
    	IntDimension td =adjuster.getSize ();
		Insets insets =adjuster.getInsets ();
		int top =insets.top ;
		int left =insets.left ;
		int right =td.width -insets.right ;
		int gap =getTextButtonGap ();
		
		int height =td.height -insets.top -insets.bottom ;
    	IntDimension buttonSize =arrowButton.getPreferredSize ();
    	arrowButton.setSizeWH(buttonSize.width, height);
    	arrowButton.setLocationXY(right - buttonSize.width, top);
    	inputText.setLocationXY(left, top);
    	inputText.setSizeWH(td.width - insets.left - insets.right - buttonSize.width-gap, height);		
	}
    
     public IntDimension  getPreferredSize (Component c ){
    	Insets insets =adjuster.getInsets ();
    	IntDimension textSize =inputText.getPreferredSize ();
    	IntDimension btnSize =arrowButton.getPreferredSize ();
    	IntDimension size =new IntDimension(
    		textSize.width + getTextButtonGap() + btnSize.width,
    		Math.max(textSize.height, btnSize.height)
    	);
    	return insets.getOutsideSize(size);
    }

     public IntDimension  getMinimumSize (Component c ){
    	return adjuster.getInsets().getOutsideSize(arrowButton.getPreferredSize());
    }

     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }    	
	
	//--------------------- handlers--------------------
	
	private void  __onValueChanged (Event e ){
		fillInputTextWithCurrentValue();
	}
	
	private void  __onInputTextMouseWheel (MouseEvent e ){
		adjuster.setValue(adjuster.getValue()+e.delta*getUnitIncrement());
	}
	
	private void  __inputTextAction (boolean fireActOnlyIfChanged =false ){
		String text =inputText.getText ();
		int value =adjuster.getValueParser ()(text );
		adjuster.setValue(value);
		//revalidte a legic text
		fillInputTextWithCurrentValue();
		if(!fireActOnlyIfChanged){
			fireActionEvent();
		}else if(value != startEditingValue){
			fireActionEvent();
		}
	}
	
	protected int startEditingValue ;
	protected void  fireActionEvent (){
		startEditingValue = adjuster.getValue();
		adjuster.dispatchEvent(new AWEvent(AWEvent.ACT));
	}
	
	private void  __onFocusGained (AWEvent e ){
		startEditingValue = adjuster.getValue();
	}
	
	private void  __onFocusLost (AWEvent e ){
		__inputTextAction(true);
	}
	
	private void  __onInputTextKeyDown (FocusKeyEvent e ){
    	int code =e.keyCode ;
    	int unit =getUnitIncrement ();
    	int block =popupSlider.getMajorTickSpacing ()>0? popupSlider.getMajorTickSpacing() : unit*10;
    	int delta =0;
    	if(code == Keyboard.ENTER){
    		__inputTextAction(false);
    		return;
    	}
    	if(code == Keyboard.UP){
    		delta = unit;
    	}else if(code == Keyboard.DOWN){
    		delta = -unit;
    	}else if(code == Keyboard.PAGE_UP){
    		delta = block;
    	}else if(code == Keyboard.PAGE_DOWN){
    		delta = -block;
    	}else if(code == Keyboard.HOME){
    		adjuster.setValue(adjuster.getMinimum());
    		return;
    	}else if(code == Keyboard.END){
    		adjuster.setValue(adjuster.getMaximum() - adjuster.getExtent());
    		return;
    	}
    	adjuster.setValue(adjuster.getValue() + delta);
	}
	
	private void  __onArrowButtonPressed (Event e ){
		JPopup popupWindow =getPopup ();
		if(popupWindow.isOnStage()){
			popupWindow.dispose();
		}
		popupWindow.changeOwner(AsWingUtils.getOwnerAncestor(adjuster));
		popupWindow.pack();
		popupWindow.show();
		double max =adjuster.getMaximum ();
		double min =adjuster.getMinimum ();
		double pw =popupWindow.getWidth ();
		double ph =popupWindow.getHeight ();
		double sw =getSliderTrackWidth ();
		double sh =getSliderTrackHeight ();
		Insets insets =popupWindow.getInsets ();
		Insets sliderInsets =popupSliderUI.getTrackMargin ();
		insets.top += sliderInsets.top;
		insets.left += sliderInsets.left;
		insets.bottom += sliderInsets.bottom;
		insets.right += sliderInsets.right;
		IntPoint mouseP =adjuster.getMousePosition ();
		IntPoint windowP =new IntPoint(mouseP.x -pw /2,mouseP.y -ph /2);
		double value =adjuster.getValue ();
		double valueL ;
		if(adjuster.getOrientation() == JAdjuster.VERTICAL){
			valueL = (value - min)/(max - min) * sh;
			windowP.y = mouseP.y - (sh - valueL) - insets.top;
		}else{
			valueL = (value - min)/(max - min) * sw;
			windowP.x = mouseP.x - valueL - insets.left;
			windowP.y += adjuster.getHeight()/4;
		}
		IntPoint agp =adjuster.getGlobalLocation ();
		agp.move(windowP.x, windowP.y);
		popupWindow.setLocation(agp);
		
		startMousePoint = adjuster.getMousePosition();
		startValue = adjuster.getValue();
		if(adjuster.stage){
			adjuster.stage.addEventListener(MouseEvent.MOUSE_MOVE, __onMouseMoveOnSlider, false, 0, true);
			adjuster.addEventListener(Event.REMOVED_FROM_STAGE, __onMouseMoveOnSliderRemovedFromStage, false, 0, true);
		}
	}
	
	private void  __onMouseMoveOnSliderRemovedFromStage (Event e ){
		adjuster.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMoveOnSlider);
		adjuster.removeEventListener(Event.REMOVED_FROM_STAGE, __onMouseMoveOnSliderRemovedFromStage);
	}
	
	private void  __onArrowButtonReleased (Event e ){
		if(adjuster.stage){
			__onMouseMoveOnSliderRemovedFromStage(null);
		}
		popup.dispose();
		fireActionEvent();
	}
	
	private void  __onMouseMoveOnSlider (MouseEvent e ){
		double delta =0;
		double valueDelta =0;
		double range =adjuster.getMaximum ()-adjuster.getMinimum ();
		IntPoint p =adjuster.getMousePosition ();
		if(adjuster.getOrientation() == JAdjuster.VERTICAL){
			delta = -p.y + startMousePoint.y;
			valueDelta = delta/(getSliderTrackHeight()) * range;
		}else{
			delta = p.x - startMousePoint.x;
			valueDelta = delta/(getSliderTrackWidth()) * range;			
		}
		adjuster.setValue(startValue + valueDelta);
		e.updateAfterEvent();
	}	
	
    protected int  getUnitIncrement (){
    	int unit =0;
    	if(popupSlider.getMinorTickSpacing() >0 ){
    		unit = popupSlider.getMinorTickSpacing();
    	}else if(popupSlider.getMajorTickSpacing() > 0){
    		unit = popupSlider.getMajorTickSpacing();
    	}else{
    		double range =popupSlider.getMaximum ()-popupSlider.getMinimum ();
    		if(range > 2){
    			unit = Math.max(1, Math.round(range/500));
    		}else{
    			unit = range/100;
    		}
    	}
    	return unit;
    }
	
	protected double  getSliderTrackWidth (){
		Insets sliderInsets =popupSliderUI.getTrackMargin ();
		double w =popupSlider.getWidth ();
		if(w == 0){
			w = popupSlider.getPreferredWidth();
		}
		return w - sliderInsets.left - sliderInsets.right;
	}
	
	protected double  getSliderTrackHeight (){
		Insets sliderInsets =popupSliderUI.getTrackMargin ();
		double h =popupSlider.getHeight ();
		if(h == 0){
			h = popupSlider.getPreferredHeight();
		}
		return h - sliderInsets.top - sliderInsets.bottom;
	}
}


