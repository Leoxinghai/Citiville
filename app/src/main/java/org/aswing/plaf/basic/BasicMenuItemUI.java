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


import flash.display.*;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.text.*;

import org.aswing.*;
import org.aswing.event.AWEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;

/**
 * @private
 * @author iiley
 */
public class BasicMenuItemUI extends BaseComponentUI implements MenuElementUI{

	/* Client Property keys for text and accelerator text widths */
	public static String MAX_TEXT_WIDTH ="maxTextWidth";
	public static String MAX_ACC_WIDTH ="maxAccWidth";

	protected JMenuItem menuItem ;

	protected ASColor selectionBackground ;
	protected ASColor selectionForeground ;
	protected ASColor disabledForeground ;
	protected ASColor acceleratorForeground ;
	protected ASColor acceleratorSelectionForeground ;

	protected int defaultTextIconGap ;
	protected ASFont acceleratorFont ;
	protected boolean acceleratorFontValidated ;

	protected Icon arrowIcon ;
	protected Icon checkIcon ;

	protected TextField textField ;
	protected TextField accelTextField ;

	protected Object menuItemLis ;

	public  BasicMenuItemUI (){
		super();
	}

	 public void  installUI (Component c ){
		menuItem = JMenuItem(c);
		installDefaults();
		installComponents();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		menuItem = JMenuItem(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}

	protected String  getPropertyPrefix (){
		return "MenuItem.";
	}

	protected void  installDefaults (){
		menuItem.setHorizontalAlignment(AsWingConstants.LEFT);
		menuItem.setVerticalAlignment(AsWingConstants.CENTER);
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(menuItem, pp);
        LookAndFeel.installBorderAndBFDecorators(menuItem, pp);
        LookAndFeel.installBasicProperties(menuItem, pp);

		selectionBackground = getColor(pp + "selectionBackground");
		selectionForeground = getColor(pp + "selectionForeground");
		disabledForeground = getColor(pp + "disabledForeground");
		acceleratorForeground = getColor(pp + "acceleratorForeground");
		acceleratorSelectionForeground = getColor(pp + "acceleratorSelectionForeground");
		acceleratorFont = getFont(pp + "acceleratorFont");
		acceleratorFontValidated = false;

		if(menuItem.getMargin() is UIResource) {
			menuItem.setMargin(getInsets(pp + "margin"));
		}

		arrowIcon = getIcon(pp + "arrowIcon");
		checkIcon = getIcon(pp + "checkIcon");
		installIcon(arrowIcon);
		installIcon(checkIcon);

		//TODO get this from UI defaults
		defaultTextIconGap = 4;
	}

	protected void  installIcon (Icon icon ){
		if(icon && icon.getDisplay(menuItem)){
			menuItem.addChild(icon.getDisplay(menuItem));
		}
	}

	protected void  uninstallIcon (Icon icon ){
		if(icon && icon.getDisplay(menuItem)){
			menuItem.removeChild(icon.getDisplay(menuItem));
		}
	}

	protected void  installComponents (){
 		textField = AsWingUtils.createLabel(menuItem, "label");
 		accelTextField = AsWingUtils.createLabel(menuItem, "accLabel");
 		menuItem.setFontValidated(false);
	}

	protected void  installListeners (){
		menuItem.addEventListener(MouseEvent.ROLL_OVER, ____menuItemRollOver);
		menuItem.addEventListener(MouseEvent.ROLL_OUT, ____menuItemRollOut);
		menuItem.addActionListener(____menuItemAct);
		menuItem.addStateListener(__menuStateChanged);
	}

	protected void  uninstallDefaults (){
 		LookAndFeel.uninstallBorderAndBFDecorators(menuItem);
		uninstallIcon(arrowIcon);
		uninstallIcon(checkIcon);
	}

	protected void  uninstallComponents (){
		menuItem.removeChild(textField);
		menuItem.removeChild(accelTextField);
	}

	protected void  uninstallListeners (){
		menuItem.removeEventListener(MouseEvent.ROLL_OVER, ____menuItemRollOver);
		menuItem.removeEventListener(MouseEvent.ROLL_OUT, ____menuItemRollOut);
		menuItem.removeActionListener(____menuItemAct);
		menuItem.removeStateListener(__menuStateChanged);
	}

	//---------------

	public void  processKeyEvent (int code ){
		MenuSelectionManager manager =MenuSelectionManager.defaultManager ();
		Array path =manager.getSelectedPath ();
		if(path.get(path.length-1) != menuItem){
			return;
		}
		if(manager.isEnterKey(code)){
			menuItem.doClick();
			return;
		}
		if(path.length > 1 && path.get(path.length-1) == menuItem){
			if(manager.isPageNavKey(code)){
				path.pop();
				manager.setSelectedPath(menuItem.stage, path, false);
				MenuElement(path.get(path.length-1)).processKeyEvent(code);
			}else if(manager.isItemNavKey(code)){
				path.pop();
				if(manager.isPrevItemKey(code)){
					path.push(manager.prevSubElement(MenuElement(path.get(path.length-1)), menuItem));
				}else{
					path.push(manager.nextSubElement(MenuElement(path.get(path.length-1)), menuItem));
				}
				manager.setSelectedPath(menuItem.stage, path, false);
			}
		}
	}

	protected void  __menuItemRollOver (MouseEvent e ){
		MenuSelectionManager.defaultManager().setSelectedPath(menuItem.stage, getPath(), false);
		menuItem.repaint();
	}

	protected void  __menuItemRollOut (MouseEvent e ){
		Array path =MenuSelectionManager.defaultManager ().getSelectedPath ();
		if(path.length > 1 && path.get(path.length-1) == menuItem){
			path.pop();
			MenuSelectionManager.defaultManager().setSelectedPath(menuItem.stage, path, false);
		}
		menuItem.repaint();
	}

	protected void  __menuItemAct (AWEvent e ){
		MenuSelectionManager.defaultManager().clearSelectedPath(false);
		menuItem.repaint();
	}

    protected void  __menuStateChanged (Event e ){
    	menuItem.repaint();
    }

	private void  ____menuItemRollOver (MouseEvent e ){
		__menuItemRollOver(e);
	}
	private void  ____menuItemRollOut (MouseEvent e ){
		__menuItemRollOut(e);
	}
	private void  ____menuItemAct (AWEvent e ){
		__menuItemAct(e);
	}

	//---------------

	/**
	 *SubUI  this to do different
	 */
	protected boolean  isMenu (){
		return false;
	}

	/**
	 *SubUI  this to do different
	 */
	protected boolean  isTopMenu (){
		return false;
	}

	/**
	 *SubUI  this to do different
	 */
	protected boolean  shouldPaintSelected (){
		return menuItem.getModel().isRollOver();
	}

    public MenuElement[] Array  getPath (){//
        MenuSelectionManager m =MenuSelectionManager.defaultManager ();
        Array oldPath =m.getSelectedPath ();
        Array newPath ;
        int i =oldPath.length ;
        if (i == 0){
            return new Array();
        }
        Component parent =menuItem.getParent ();
        if (MenuElement(oldPath.get(i-1)).getMenuComponent() == parent) {
            // The parent popup menu is the last so far
            newPath = oldPath.concat();
            newPath.push(menuItem);
        } else {
            // A sibling menuitem is the current selection
            //
            //  This probably needs to handle 'exit submenu into
            // a menu item.  Search backwards along the current
            // selection until you find the parent popup menu,
            // then copy up to that and add yourself...
            int j ;
            for (j = oldPath.length-1; j >= 0; j--) {
                if (MenuElement(oldPath.get(j)).getMenuComponent() == parent){
                    break;
                }
            }
            newPath = oldPath.slice(0, j+1);
            newPath.push(menuItem);
        }
        return newPath;
    }

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		JMenuItem mi =JMenuItem(c );
		paintMenuItem(mi, g, b, checkIcon, arrowIcon,
					  selectionBackground, selectionForeground,
					  defaultTextIconGap);
	}

	protected  paintMenuItem (JMenuItem b ,Graphics2D g ,IntRectangle r ,Icon checkIcon ,Icon arrowIcon ,
		background:ASColor, foreground:ASColor, defaultTextIconGap:int):void{

		ButtonModel model =b.getModel ();
		resetRects();
		viewRect.setRect( r );

		ASFont font =b.getFont ();

		String acceleratorText =getAcceleratorText(b );

		// layout the text and icon
		String text =layoutMenuItem(
			font, b.getDisplayText(), acceleratorFont, acceleratorText, b.getIcon(),
			checkIcon, arrowIcon,
			b.getVerticalAlignment(), b.getHorizontalAlignment(),
			b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
			viewRect, iconRect, textRect, acceleratorRect,
			checkIconRect, arrowIconRect,
			b.getDisplayText() == null ? 0 : defaultTextIconGap,
			defaultTextIconGap
		);

		// Paint background
		paintMenuBackground(b, g, r, background);

		boolean isSelected =shouldPaintSelected ();

		// Paint the Check
		paintCheckIcon(b, useCheckAndArrow(),
			g, checkIconRect.x, checkIconRect.y);

		Icon icon =null ;
		// Paint the Icon
		if(b.getIcon() != null) {
			if(!model.isEnabled()) {
				icon = b.getDisabledIcon();
			} else if(model.isPressed() && model.isArmed()) {
				icon = b.getPressedIcon();
				if(icon == null) {
					// Use default icon
					icon = b.getIcon();
				}
			} else {
				icon = b.getIcon();
			}
		}
		paintIcon(b, icon, g, iconRect.x, iconRect.y);
		ASColor tc ;
		// Draw the Text
		if(text != null && text != "") {
			tc = b.getForeground();
			if(isSelected){
				tc = selectionForeground;
			}
			if(!b.isEnabled()){
				if(disabledForeground != null){
					tc = disabledForeground;
				}else{
					tc = BasicGraphicsUtils.getDisabledColor(b);
				}
			}
			textField.visible = true;
			paintTextField(b, textRect, textField, text, font, tc, !b.isFontValidated());
			b.setFontValidated(true);
		}else{
			textField.visible = false;
		}

		// Draw the Accelerator Text
		if(acceleratorText != null && acceleratorText !="") {
			//Get the maxAccWidth from the parent to calculate the offset.
			int accOffset =0;
			Container parent =menuItem.getParent ();
			if (parent != null) {
				Container p =parent ;
				int maxValueInt ;
				if(p.getClientProperty(BasicMenuItemUI.MAX_ACC_WIDTH) == undefined){
					maxValueInt = acceleratorRect.width;
				}else{
					maxValueInt = p.getClientProperty(BasicMenuItemUI.MAX_ACC_WIDTH);
				}

				//Calculate the offset, with which the accelerator texts will be drawn with.
				accOffset = maxValueInt - acceleratorRect.width;
			}
	  		IntRectangle accTextFieldRect =new IntRectangle ();
			accTextFieldRect.x = acceleratorRect.x - accOffset;
			accTextFieldRect.y = acceleratorRect.y;
			tc = acceleratorForeground;
			if(!model.isEnabled()) {
				if(disabledForeground != null){
					tc = disabledForeground;
				}else{
					tc = BasicGraphicsUtils.getDisabledColor(b);
				}
			} else if (isSelected) {
				tc = acceleratorSelectionForeground;
			}
			accelTextField.visible = true;
			paintTextField(b, accTextFieldRect, accelTextField, acceleratorText, acceleratorFont, tc, !acceleratorFontValidated);
			acceleratorFontValidated = true;
		}else{
			accelTextField.visible = false;
		}

		// Paint the Arrow
		paintArrowIcon(b, useCheckAndArrow(),
			g, arrowIconRect.x, arrowIconRect.y);
	 }

	protected void  paintCheckIcon (JMenuItem b ,boolean isPaint ,Graphics2D g ,int x ,int y ){
		if(!checkIcon) return;

		if(!isPaint){
			setIconVisible(checkIcon, false);
		}else{
			setIconVisible(checkIcon, true);
			checkIcon.updateIcon(b, g, x, y);
		}
	}

	protected void  paintArrowIcon (JMenuItem b ,boolean isPaint ,Graphics2D g ,int x ,int y ){
		if(!arrowIcon) return;

		if(!isPaint){
			setIconVisible(arrowIcon, false);
		}else{
			setIconVisible(arrowIcon, true);
			arrowIcon.updateIcon(b, g, x, y);
		}
	}

	protected void  paintIcon (JMenuItem b ,Icon icon ,Graphics2D g ,int x ,int y ){
        Array icons =getIcons ();
        for(int i =0;i <icons.length ;i ++){
        	Icon ico =icons.get(i) ;
			setIconVisible(ico, false);
        }
        if(icon != null){
        	setIconVisible(icon, true);
        	icon.updateIcon(b, g, x, y);
        }
	}

    protected void  setIconVisible (Icon icon ,boolean visible ){
    	if(icon.getDisplay(menuItem) != null){
    		icon.getDisplay(menuItem).visible = visible;
    	}
    }

    protected Array  getIcons (){
    	Array arr =new Array ();
    	AbstractButton button =menuItem ;
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

	protected void  paintMenuBackground (JMenuItem menuItem ,Graphics2D g ,IntRectangle r ,ASColor bgColor ){
		ASColor color =bgColor ;
		if(menuItem.isOpaque()) {
			if (!shouldPaintSelected()) {
				color = menuItem.getBackground();
			}
			g.fillRectangle(new SolidBrush(color), r.x, r.y, r.width, r.height);
		}else if(shouldPaintSelected() && (menuItem.getBackgroundDecorator() == null || menuItem.getBackgroundDecorator() == DefaultEmptyDecoraterResource.INSTANCE)){
			g.fillRectangle(new SolidBrush(color), r.x, r.y, r.width, r.height);
		}
	}


	protected void  paintTextField (JMenuItem b ,IntRectangle tRect ,TextField textField ,String text ,ASFont font ,ASColor color ,boolean validateFont ){
		if(textField.text != text){
			textField.text = text;
		}
		if(validateFont){
			AsWingUtils.applyTextFont(textField, font);
		}
		AsWingUtils.applyTextColor(textField, color);
		textField.x = tRect.x;
		textField.y = tRect.y;
		if(b.getMnemonicIndex() >= 0){
			textField.setTextFormat(
				new TextFormat(null, null, null, null, null, true),
				b.getMnemonicIndex());
		}
	}


	// these rects are used for painting and preferredsize calculations.
	// they used to be regenerated constantly.  Now they are reused.
	protected static IntRectangle zeroRect =new IntRectangle ();
	protected static IntRectangle iconRect =new IntRectangle ();
	protected static IntRectangle textRect =new IntRectangle ();
	protected static IntRectangle acceleratorRect =new IntRectangle ();
	protected static IntRectangle checkIconRect =new IntRectangle ();
	protected static IntRectangle arrowIconRect =new IntRectangle ();
	protected static IntRectangle viewRect =new IntRectangle ();
	protected static IntRectangle r =new IntRectangle ();

	protected void  resetRects (){
		iconRect.setRect(zeroRect);
		textRect.setRect(zeroRect);
		acceleratorRect.setRect(zeroRect);
		checkIconRect.setRect(zeroRect);
		arrowIconRect.setRect(zeroRect);
		viewRect.setRectXYWH(0, 0, 100000, 100000);
		r.setRect(zeroRect);
	}

	/**
	 * Returns the a menu item's preferred size with specified icon and text.
	 */
	protected  getPreferredMenuItemSize (JMenuItem b ,
													 checkIcon:Icon,
													 arrowIcon:Icon,
													 defaultTextIconGap:int):IntDimension{
		Icon icon =b.getIcon ();
		String text =b.getDisplayText ();
		String acceleratorText =getAcceleratorText(b );

		ASFont font =b.getFont ();

		resetRects();

		layoutMenuItem(
				  font, text, acceleratorFont, acceleratorText, icon, checkIcon, arrowIcon,
				  b.getVerticalAlignment(), b.getHorizontalAlignment(),
				  b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
				  viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect,
				  text == null ? 0 : defaultTextIconGap,
				  defaultTextIconGap
				  );
		// find the union of the icon and text rects
		r = textRect.union(iconRect);

		// To make the accelerator texts appear in a column, find the widest MenuItem text
		// and the widest accelerator text.

		//Get the parent, which stores the information.
		Container parent =menuItem.getParent ();

		//Check the parent, and see that it is not a top-level menu.
		if (parent != null &&  !isTopMenu()) {
			Container p =parent ;
			//Get widest text so far from parent, if no one exists null is returned.
			int maxTextValue =p.getClientProperty(BasicMenuItemUI.MAX_TEXT_WIDTH );
			int maxAccValue =p.getClientProperty(BasicMenuItemUI.MAX_ACC_WIDTH );

			//Compare the text widths, and adjust the r.width to the widest.
			if (r.width < maxTextValue) {
				r.width = maxTextValue;
			} else {
				p.putClientProperty(BasicMenuItemUI.MAX_TEXT_WIDTH, r.width);
			}

		  //Compare the accelarator widths.
			if (acceleratorRect.width > maxAccValue) {
				maxAccValue = acceleratorRect.width;
				p.putClientProperty(BasicMenuItemUI.MAX_ACC_WIDTH, acceleratorRect.width);
			}

			//Add on the widest accelerator
			r.width += maxAccValue;
			r.width += defaultTextIconGap;
		}

		if(useCheckAndArrow()) {
			// Add in the checkIcon
			r.width += checkIconRect.width;
			r.width += defaultTextIconGap;

			// Add in the arrowIcon
			r.width += defaultTextIconGap;
			r.width += arrowIconRect.width;
		}

		r.width += 2*defaultTextIconGap;

		Insets insets =b.getInsets ();
		if(insets != null) {
			r.width += insets.left + insets.right;
			r.height += insets.top + insets.bottom;
		}
		r.width = Math.ceil(r.width);
		r.height = Math.ceil(r.height);
		// if the width is even, bump it up one. This is critical
		// for the focus dash line to draw properly
		if(r.width%2 == 0) {
			r.width++;
		}

		// if the height is even, bump it up one. This is critical
		// for the text to center properly
		if(r.height%2 == 0) {
			r.height++;
		}
		return r.getSize();
	}

	protected String  getAcceleratorText (JMenuItem b ){
		if(b.getAccelerator() == null){
			return "";
		}else{
			return b.getAccelerator().getDescription();
		}
	}

	/**
	 * Compute and return the location of the icons origin, the
	 * location of origin of the text baseline, and a possibly clipped
	 * version of the compound labels string.  Locations are computed
	 * relative to the viewRect rectangle.
	 */
	protected  layoutMenuItem (
		font:ASFont,
		text:String,
		accelFont:ASFont,
		acceleratorText:String,
		icon:Icon,
		checkIcon:Icon,
		arrowIcon:Icon,
		verticalAlignment:int,
		horizontalAlignment:int,
		verticalTextPosition:int,
		horizontalTextPosition:int,
		viewRect:IntRectangle,
		iconRect:IntRectangle,
		textRect:IntRectangle,
		acceleratorRect:IntRectangle,
		checkIconRect:IntRectangle,
		arrowIconRect:IntRectangle,
		textIconGap:int,
		menuItemGap:int
		):String
	{

		AsWingUtils.layoutCompoundLabel(menuItem, font, text, icon, verticalAlignment,
							horizontalAlignment, verticalTextPosition,
							horizontalTextPosition, viewRect, iconRect, textRect,
							textIconGap);

		/* Initialize the acceelratorText bounds rectangle textRect.  If a null
		 * or and empty String was specified we substitute "" here
		 * and use 0,0,0,0 for acceleratorTextRect.
		 */
		if(acceleratorText == null || acceleratorText == "") {
			acceleratorRect.width = acceleratorRect.height = 0;
			acceleratorText = "";
		}else {
			IntDimension td =accelFont.computeTextSize(acceleratorText );
			acceleratorRect.width = td.width;
			acceleratorRect.height = td.height;
		}

		/* Initialize the checkIcon bounds rectangle's width & height.
		 */
		if( useCheckAndArrow()) {
			if (checkIcon != null) {
				checkIconRect.width = checkIcon.getIconWidth(menuItem);
				checkIconRect.height = checkIcon.getIconHeight(menuItem);
			} else {
				checkIconRect.width = checkIconRect.height = 0;
			}
			/* Initialize the arrowIcon bounds rectangle width & height.
			 */
			if (arrowIcon != null) {
				arrowIconRect.width = arrowIcon.getIconWidth(menuItem);
				arrowIconRect.height = arrowIcon.getIconHeight(menuItem);
			} else {
				arrowIconRect.width = arrowIconRect.height = 0;
			}
		}

		IntRectangle labelRect =iconRect.union(textRect );
		textRect.x += menuItemGap;
		iconRect.x += menuItemGap;

		// Position the Accelerator text rect
		acceleratorRect.x = viewRect.x + viewRect.width - arrowIconRect.width - menuItemGap*2 - acceleratorRect.width;

		// Position the Check and Arrow Icons
		if (useCheckAndArrow()) {
			checkIconRect.x = viewRect.x + menuItemGap;
			textRect.x += menuItemGap + checkIconRect.width;
			iconRect.x += menuItemGap + checkIconRect.width;
			arrowIconRect.x = viewRect.x + viewRect.width - menuItemGap - arrowIconRect.width;
		}

		// Align the accelertor text and the check and arrow icons vertically
		// with the center of the label rect.
		acceleratorRect.y = labelRect.y + Math.floor(labelRect.height/2) - Math.floor(acceleratorRect.height/2);
		if( useCheckAndArrow() ) {
			arrowIconRect.y = labelRect.y + Math.floor(labelRect.height/2) - Math.floor(arrowIconRect.height/2);
			checkIconRect.y = labelRect.y + Math.floor(labelRect.height/2) - Math.floor(checkIconRect.height/2);
		}

		return text;
	}

	protected boolean  useCheckAndArrow (){
		return !isTopMenu();
	}

	 public IntDimension  getPreferredSize (Component c ){
		JMenuItem b =JMenuItem(c );
		return getPreferredMenuItemSize(b, checkIcon, arrowIcon, defaultTextIconGap);
	}

	 public IntDimension  getMinimumSize (Component c ){
		IntDimension size =menuItem.getInsets ().getOutsideSize ();
		if(menuItem.getMargin() != null){
			size = menuItem.getMargin().getOutsideSize(size);
		}
		return size;
	}

	 public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
	}
}


