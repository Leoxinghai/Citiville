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

	
import flash.text.*;
import flash.ui.Keyboard;

import org.aswing.*;
import org.aswing.event.AWEvent;
import org.aswing.event.FocusKeyEvent;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.Graphics2D;
import org.aswing.plaf.*;

/**
 * Basic Button implementation.
 * @author iiley
 * @private
 */
public class BasicButtonUI extends BaseComponentUI{
	
	protected AbstractButton button ;
	protected TextField textField ;
	
	public  BasicButtonUI (){
		super();
	}

    protected String  getPropertyPrefix (){
        return "Button.";
    }
    
	 public void  installUI (Component c ){
		button = AbstractButton(c);
		installDefaults(button);
		installComponents(button);
		installListeners(button);
	}
    
	 public void  uninstallUI (Component c ){
		button = AbstractButton(c);
		uninstallDefaults(button);
		uninstallComponents(button);
		uninstallListeners(button);
 	}
 	
 	protected void  installDefaults (AbstractButton b ){
        // load shared instance defaults
        String pp =getPropertyPrefix ();
        if(!b.isShiftOffsetSet()){
        	b.setShiftOffset(getInt(pp + "textShiftOffset"));
        	b.setShiftOffsetSet(false);
        }
        
        if(b.getMargin() is UIResource) {
            b.setMargin(getInsets(pp + "margin"));
        }
        
        LookAndFeel.installColorsAndFont(b, pp);
        LookAndFeel.installBorderAndBFDecorators(b, pp);
        LookAndFeel.installBasicProperties(b, pp);
        button.mouseChildren = false;
 	}
	
 	protected void  uninstallDefaults (AbstractButton b ){
 		LookAndFeel.uninstallBorderAndBFDecorators(b);
 	}
 	
 	protected void  installComponents (AbstractButton b ){
 		textField = AsWingUtils.createLabel(b, "label");
 		b.setFontValidated(false);
 	}
	
 	protected void  uninstallComponents (AbstractButton b ){
 		b.removeChild(textField);
 	}
 	
 	protected void  installListeners (AbstractButton b ){
 		b.addStateListener(__stateListener);
 		b.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
 		b.addEventListener(FocusKeyEvent.FOCUS_KEY_UP, __onKeyUp);
 	}
	
 	protected void  uninstallListeners (AbstractButton b ){
 		b.removeStateListener(__stateListener);
 		b.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
 		b.removeEventListener(FocusKeyEvent.FOCUS_KEY_UP, __onKeyUp);
 	}
 	
 	//-----------------------------------------------------
 	        
    protected int  getTextShiftOffset (){
    	return button.getShiftOffset();
    }
    
    //--------------------------------------------------------
    
    private void  __stateListener (AWEvent e ){
    	button.repaint();
    }
    
    private void  __onKeyDown (FocusKeyEvent e ){
		if(!(button.isShowing() && button.isEnabled())){
			return;
		}
		ButtonModel model =button.getModel ();
		if(e.keyCode == Keyboard.SPACE && !(model.isRollOver() && model.isPressed())){
	    	setTraversingTrue();
			model.setRollOver(true);
			model.setArmed(true);
			model.setPressed(true);
		}
    }
    
    private void  __onKeyUp (FocusKeyEvent e ){
		if(!(button.isShowing() && button.isEnabled())){
			return;
		}
		if(e.keyCode == Keyboard.SPACE){
			ButtonModel model =button.getModel ();
	    	setTraversingTrue();
			model.setPressed(false);
			model.setArmed(false);
			//b.fireActionEvent();
			model.setRollOver(false);
		}
    }
    
    protected void  setTraversingTrue (){
    	FocusManager fm =FocusManager.getManager(button.stage );
    	if(fm){
    		fm.setTraversing(true);
    	}
    }
    
    //--------------------------------------------------
    
    /* These rectangles/insets are allocated once for all 
     * ButtonUI.paint() calls.  Re-using rectangles rather than 
     * allocating them in each paint call substantially reduced the time
     * it took paint to run.  Obviously, this method can't be re-entered.
     */
	private static IntRectangle viewRect =new IntRectangle ();
    private static IntRectangle textRect =new IntRectangle ();
    private static IntRectangle iconRect =new IntRectangle ();

     public void  paint (Component c ,Graphics2D g ,IntRectangle r ){
    	super.paint(c, g, r);
    	AbstractButton b =AbstractButton(c );
    	
    	Insets insets =b.getMargin ();
    	if(insets != null){
    		r = insets.getInsideBounds(r);
    	}
    	viewRect.setRect(r);
    	
    	textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

        // layout the text and icon
        String text =AsWingUtils.layoutCompoundLabel(c ,
            c.getFont(), b.getDisplayText(), getIconToLayout(), 
            b.getVerticalAlignment(), b.getHorizontalAlignment(),
            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
            viewRect, iconRect, textRect, 
	    	b.getDisplayText() == null ? 0 : b.getIconTextGap());
	   	
    	
    	paintIcon(b, g, iconRect);
    	
        if (text != null && text != ""){
        	textField.visible = true;
        	if(b.getModel().isArmed()){
        		textRect.x += getTextShiftOffset();
        		textRect.y += getTextShiftOffset();
        	}
			paintText(b, textRect, text);
        }else{
        	textField.text = "";
        	textField.visible = false;
        }
    }
    
    protected Icon  getIconToLayout (){
    	return button.getIcon();
    }
    
    /**
     * do nothing here, let background decorator to paint the background
     */
	 protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
		//do nothing here, let background decorator to paint the background
	}    
    
    /**
     * paint the text to specified button with specified bounds
     */
    protected void  paintText (AbstractButton b ,IntRectangle textRect ,String text ){
    	b.bringToTop(textField);
    	ASFont font =b.getFont ();
    	
		if(textField.text != text){
			textField.text = text;
		}
		if(!b.isFontValidated()){
			AsWingUtils.applyTextFont(textField, font);
			b.setFontValidated(true);
		}
    	AsWingUtils.applyTextColor(textField, getTextPaintColor(b));
		textField.x = textRect.x;
		textField.y = textRect.y;
		if(b.getMnemonicIndex() >= 0){
			textField.setTextFormat(
				new TextFormat(null, null, null, null, null, true), 
				b.getMnemonicIndex());
		}
    	textField.filters = b.getTextFilters();
    }
    
    protected ASColor  getTextPaintColor (AbstractButton b ){
    	if(b.isEnabled()){
    		return b.getForeground();
    	}else{
    		return BasicGraphicsUtils.getDisabledColor(b);
    	}
    }
    
    /**
     * paint the icon to specified button's mc with specified bounds
     */
    protected void  paintIcon (AbstractButton b ,Graphics2D g ,IntRectangle iconRect ){
        ButtonModel model =b.getModel ();
        Icon icon =b.getIcon ();
        Icon tmpIcon =null ;
        
        Array icons =getIcons ();
        for(int i =0;i <icons.length ;i ++){
        	Icon ico =icons.get(i) ;
			setIconVisible(ico, false);
        }
        
	    if(icon == null) {
	    	return;
	    }

		if(!model.isEnabled()) {
			if(model.isSelected()) {
				tmpIcon = b.getDisabledSelectedIcon();
			} else {
				tmpIcon = b.getDisabledIcon();
			}
		} else if(model.isPressed() && model.isArmed()) {
			tmpIcon = b.getPressedIcon();
		} else if(b.isRollOverEnabled() && model.isRollOver()) {
			if(model.isSelected()) {
				tmpIcon = b.getRollOverSelectedIcon();
			} else {
				tmpIcon = b.getRollOverIcon();
			}
		} else if(model.isSelected()) {
			tmpIcon = b.getSelectedIcon();
		}
              
		if(tmpIcon != null) {
			icon = tmpIcon;
		}
		setIconVisible(icon, true);
		if(model.isPressed() && model.isArmed()) {
			icon.updateIcon(b, g, iconRect.x + getTextShiftOffset(),
                        iconRect.y + getTextShiftOffset());
		}else{
			icon.updateIcon(b, g, iconRect.x, iconRect.y);
		}
    }
    
    protected void  setIconVisible (Icon icon ,boolean visible ){
    	if(icon.getDisplay(button) != null){
    		icon.getDisplay(button).visible = visible;
    	}
    }
    
    protected Array  getIcons (){
    	Array arr =new Array ();
    	if(button.getIcon() != null){
    		arr.push(button.getIcon());
    	}
    	if(button.getDisabledIcon() != null){
    		arr.push(button.getDisabledIcon());
    	}
    	if(button.getSelectedIcon() != null){
    		arr.push(button.getSelectedIcon());
    	}
    	if(button.getDisabledSelectedIcon() != null){
    		arr.push(button.getDisabledSelectedIcon());
    	}
    	if(button.getRollOverIcon() != null){
    		arr.push(button.getRollOverIcon());
    	}
    	if(button.getRollOverSelectedIcon() != null){
    		arr.push(button.getRollOverSelectedIcon());
    	}
    	if(button.getPressedIcon() != null){
    		arr.push(button.getPressedIcon());
    	}
    	return arr;
    }
    
      
    /**
     * Returns the a button's preferred size with specified icon and text.
     */
    protected IntDimension  getButtonPreferredSize (AbstractButton b ,Icon icon ,String text ){
    	viewRect.setRectXYWH(0, 0, 100000, 100000);
    	textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        
        AsWingUtils.layoutCompoundLabel(b, 
            b.getFont(), text, icon, 
            b.getVerticalAlignment(), b.getHorizontalAlignment(),
            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
            viewRect, iconRect, textRect, 
	    	b.getDisplayText() == null ? 0 : b.getIconTextGap()
        );
        /* The preferred size of the button is the size of 
         * the text and icon rectangles plus the buttons insets.
         */
        IntDimension size ;
        if(icon == null){
        	size = textRect.getSize();
        }else if(b.getDisplayText()==null || b.getDisplayText()==""){
        	size = iconRect.getSize();
        }else{
        	IntRectangle r =iconRect.union(textRect );
        	size = r.getSize();
        }
        size = b.getInsets().getOutsideSize(size);
		if(b.getMargin() != null)
        	size = b.getMargin().getOutsideSize(size);
        return size;
    }
    
    /**
     * Returns the a button's minimum size with specified icon and text.
     */    
    protected IntDimension  getButtonMinimumSize (AbstractButton b ,Icon icon ,String text ){
        IntDimension size =b.getInsets ().getOutsideSize ();
		if(b.getMargin() != null)
        	size = b.getMargin().getOutsideSize(size);
		return size;
    }    
    
     public IntDimension  getPreferredSize (Component c ){
    	AbstractButton b =AbstractButton(c );
    	return getButtonPreferredSize(b, getIconToLayout(), b.getDisplayText());
    }

     public IntDimension  getMinimumSize (Component c ){
    	AbstractButton b =AbstractButton(c );
    	return getButtonMinimumSize(b, getIconToLayout(), b.getDisplayText());
    }

     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }
}


