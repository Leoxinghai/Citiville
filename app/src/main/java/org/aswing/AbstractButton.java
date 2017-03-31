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
import flash.display.SimpleButton;
import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.error.ImpMissError;
import org.aswing.event.*;
import org.aswing.plaf.*;
import org.aswing.util.*;

/**
 * Dispatched when the button's model take action, generally when user click the
 * button or <code>doClick()</code> method is called.
 * @eventType org.aswing.event.AWEvent.ACT
 * @see org.aswing.AbstractButton#addActionListener()
 */
.get(Event(name="act", type="org.aswing.event.AWEvent"))

/**
 * Dispatched when the button's state changed. the state is all about:
 * <ul>
 * <li>enabled</li>
 * <li>rollOver</li>
 * <li>pressed</li>
 * <li>released</li>
 * <li>selected</li>
 * </ul>
 * </p>
 * <p>
 * Buttons always fire <code>programmatic=false</code> InteractiveEvent.
 * </p>
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 *  Dispatched when the button's selection changed.
 * <p>
 * Buttons always fire <code>programmatic=false</code> InteractiveEvent.
 * </p>
 *  @eventType org.aswing.event.InteractiveEvent.SELECTION_CHANGED
 */
.get(Event(name="selectionChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * Defines common behaviors for buttons and menu items.
 * @author iiley
 */
public class AbstractButton extends Component{

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


    /** The data model that determines the button's state. */
    private ButtonModel model ;

    private String text ;
	private String displayText ;
	private int mnemonic ;
	private int mnemonicIndex ;
	private boolean mnemonicEnabled ;
    private Insets margin ;
    private Insets defaultMargin ;

    // Button icons
    private Icon defaultIcon ;
    private Icon pressedIcon ;
    private Icon disabledIcon ;

    private Icon selectedIcon ;
    private Icon disabledSelectedIcon ;

    private Icon rolloverIcon ;
    private Icon rolloverSelectedIcon ;

    // Display properties
    private boolean rolloverEnabled ;

    // Icon/Label Alignment
    private int verticalAlignment ;
    private int horizontalAlignment ;

    private int verticalTextPosition ;
    private int horizontalTextPosition ;

    private int iconTextGap ;
    private int shiftOffset =0;
    private boolean shiftOffsetSet =false ;

    private Array textFilters =null ;

	public  AbstractButton (String text ="",Icon icon =null ){
		super();
		setName("AbstractButton");

    	rolloverEnabled = true;

    	verticalAlignment = CENTER;
    	horizontalAlignment = CENTER;
    	verticalTextPosition = CENTER;
    	horizontalTextPosition = RIGHT;

    	iconTextGap = 2;
    	mnemonicEnabled = true;
    	this.text = text;
    	this.analyzeMnemonic();
    	this.defaultIcon = icon;
    	//setText(text);
    	//setIcon(icon);
    	initSelfHandlers();
    	updateUI();
    	installIcon(defaultIcon);
	}

    /**
     * Returns the model that this button represents.
     * @return the <code>model</code> property
     * @see #setModel()
     */
    public ButtonModel  getModel (){
        return model;
    }

    /**
     * Sets the model that this button represents.
     * @param m the new <code>ButtonModel</code>
     * @see #getModel()
     */
    public void  setModel (ButtonModel newModel ){

        ButtonModel oldModel =getModel ();

        if (oldModel != null) {
        	oldModel.removeActionListener(__modelActionListener);
            oldModel.removeStateListener(__modelStateListener);
            oldModel.removeSelectionListener(__modelSelectionListener);
        }

        model = newModel;

        if (newModel != null) {
        	newModel.addActionListener(__modelActionListener);
            newModel.addStateListener(__modelStateListener);
            newModel.addSelectionListener(__modelSelectionListener);
        }

        if (newModel != oldModel) {
            revalidate();
            repaint();
        }
    }

    /**
     * Resets the UI property to a value from the current look
     * and feel.  Subtypes of <code>AbstractButton</code>
     *should  this to update the UI .For
     * example, <code>JButton</code> might do the following:
     * <pre>
     *      setUI(ButtonUI(UIManager.getUI(this)));
     * </pre>
     */
     public void  updateUI (){
    	throw new ImpMissError();
    }

    /**
     * Programmatically perform a "click".
     */
    public void  doClick (){
    	dispatchEvent(new MouseEvent(MouseEvent.ROLL_OVER, true, false, 0, 0));
    	dispatchEvent(new MouseEvent(MouseEvent.MOUSE_DOWN, true, false, 0, 0));
    	if(isOnStage()){
    		dispatchEvent(new MouseEvent(MouseEvent.MOUSE_UP, true, false, 0, 0));
    	}else{
    		dispatchEvent(new ReleaseEvent(ReleaseEvent.RELEASE, this, false, new MouseEvent(MouseEvent.MOUSE_UP)));
    	}
    	dispatchEvent(new MouseEvent(MouseEvent.CLICK, true, false, 0, 0));
    	dispatchEvent(new MouseEvent(MouseEvent.ROLL_OUT, true, false, 0, 0));
    }

    /**
     * Adds a action listener to this button. Buttons fire a action event when
     * user clicked on it.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
     */
    public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(AWEvent.ACT, listener, false, priority, useWeakReference);
    }
	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	public void  removeActionListener (Function listener ){
		removeEventListener(AWEvent.ACT, listener);
	}

	/**
	 * Add a listener to listen the button's selection change event.
	 * When the button's selection changed, fired when diselected or selected.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.SELECTION_CHANGED, listener, false, priority);
	}

	/**
	 * Removes a selection listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */
	public void  removeSelectionListener (Function listener ){
		removeEventListener(InteractiveEvent.SELECTION_CHANGED, listener);
	}

	/**
	 * Adds a listener to listen the button's state change event.
	 * <p>
	 * When the button's state changed, the state is all about:
	 * <ul>
	 * <li>enabled</li>
	 * <li>rollOver</li>
	 * <li>pressed</li>
	 * <li>released</li>
	 * <li>selected</li>
	 * </ul>
	 * </p>
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
     * Enabled (or disabled) the button.
     * @param b  true to enable the button, otherwise false
     */
	 public void  setEnabled (boolean b ){
		if (!b && model.isRollOver()) {
	    	model.setRollOver(false);
		}
        super.setEnabled(b);
        model.setEnabled(b);
    }

    /**
     * Returns the state of the button. True if the
     * toggle button is selected, false if it's not.
     * @return true if the toggle button is selected, otherwise false
     */
    public boolean  isSelected (){
        return model.isSelected();
    }

    /**
     * Sets the state of the button. Note that this method does not
     * trigger an Event for users.
     * Call <code>click</code> to perform a programatic action change.
     *
     * @param b  true if the button is selected, otherwise false
     */
    public void  setSelected (boolean b ){
        model.setSelected(b);
    }

    /**
     * Sets the <code>rolloverEnabled</code> property, which
     * must be <code>true</code> for rollover effects to occur.
     * The default value for the <code>rolloverEnabled</code>
     * property is <code>false</code>.
     * Some look and feels might not implement rollover effects;
     * they will ignore this property.
     *
     * @param b if <code>true</code>, rollover effects should be painted
     * @see #isRollOverEnabled()
     */
    public void  setRollOverEnabled (boolean b ){
    	if(rolloverEnabled != b){
    		rolloverEnabled = b;
    		repaint();
    	}
    }

    /**
     * Gets the <code>rolloverEnabled</code> property.
     *
     * @return the value of the <code>rolloverEnabled</code> property
     * @see #setRollOverEnabled()
     */
    public boolean  isRollOverEnabled (){
    	return rolloverEnabled;
    }

	/**
	 * Sets space for margin between the button's border and
     * the label. Setting to <code>null</code> will cause the button to
     * use the default margin.  The button's default <code>Border</code>
     * object will use this value to create the proper margin.
     * However, if a non-default border is set on the button,
     * it is that <code>Border</code> object's responsibility to create the
     * appropriate margin space (else this property will
     * effectively be ignored).
     *
     * @param m the space between the border and the label
	 */
	public void  setMargin (Insets m ){
        // Cache the old margin if it comes from the UI
        if(m is UIResource) {
            defaultMargin = m;
        }

        // If the client passes in a null insets, restore the margin
        // from the UI if possible
        if(m == null && defaultMargin != null) {
            m = defaultMargin;
        }

        Insets old =margin ;
        margin = m;
        if (old == null || !m.equals(old)) {
            revalidate();
        	repaint();
        }
	}

	public Insets  getMargin (){
		Insets m =margin ;
		if(margin == null){
			m = defaultMargin;
		}
		if(m == null){
			return new InsetsUIResource();
		}else if(m is UIResource){//make it can be replaced by LAF
			return new InsetsUIResource(m.top, m.left, m.bottom, m.right);
		}else{
			return new Insets(m.top, m.left, m.bottom, m.right);
		}
	}

	public void  setTextFilters (Array fs ){
		if(textFilters != fs){
			textFilters = fs;
			repaint();
		}
	}

	public Array  getTextFilters (){
		return textFilters;
	}

	/**
	 * Wrap a SimpleButton to be this button's representation.
	 * @param btn the SimpleButton to be wrap.
	 * @return the button self
	 */
	public AbstractButton  wrapSimpleButton (SimpleButton btn ){
		setShiftOffset(0);
		setIcon(new SimpleButtonIconToggle(btn));
		setBorder(null);
		setMargin(new Insets());
		setBackgroundDecorator(null);
		setOpaque(false);
		return this;
	}

	/**
	 * Sets the text include the "&"(mnemonic modifier char). For example,
	 * if you set "&File" to be the text, then "File" will be displayed, and "F"
	 * will be the mnemonic.
	 * <p>
	 * This method will make button repaint, but will not make button relayout,
	 * so if you sets a different size text, you may need to call <code>revalidate()</code>
	 * to make this button to be relayouted by his container.
	 * </p>
	 * @param text the text.
	 * @see #getDisplayText()
	 * @see #getMnemonic()
	 * @see #getMnemonicIndex()
	 */
	public void  setText (String text ){
		if(this.text != text){
			this.text = text;
			analyzeMnemonic();
			repaint();
			invalidate();
		}
	}

	/**
	 * Sets whether or not enabled mnemonic.
	 */
	public void  setMnemonicEnabled (boolean b ){
		if(mnemonicEnabled != b){
			mnemonicEnabled = b;
			analyzeMnemonic();
		}
	}

	/**
	 * Returns whether or not enabled mnemonic.
	 */
	public boolean  isMnemonicEnabled (){
		return mnemonicEnabled;
	}

	private void  analyzeMnemonic (){
		displayText = text;
		mnemonic = -1;
		mnemonicIndex = -1;
		if(text == null){
			return;
		}
		if(!mnemonicEnabled){
			return;
		}
		int mi =text.indexOf("&");
		String mc ="";
		boolean found =false ;
		while(mi >= 0){
			if(mi+1 < text.length()){
				mc = text.charAt(mi+1);
				if(StringUtils.isLetter(mc)){
					found = true;
					break;
				}
			}else{
				break;
			}
			mi = text.indexOf("&", mi+1);
		}
		if(found){
			displayText = text.substring(0, mi) + text.substring(mi+1);
			mnemonic = mc.toUpperCase().charCodeAt(0);
			mnemonicIndex = mi;
		}
	}

	/**
	 * Returns the text include the "&"(mnemonic modifier char).
	 * @return the text.
	 * @see #getDisplayText()
	 */
	public String  getText (){
		return text;
	}

	/**
	 * Returns the text to be displayed, it is a text that removed the "&"(mnemonic modifier char).
	 * @return the text to be displayed.
	 */
	public String  getDisplayText (){
		return displayText;
	}

	/**
	 * Returns the mnemonic char index in the display text, -1 means no mnemonic.
	 * @return the mnemonic char index or -1.
	 * @see #getDisplayText()
	 */
	public int  getMnemonicIndex (){
		return mnemonicIndex;
	}

	/**
	 * Returns the keyboard mnemonic for this button, -1 means no mnemonic.
	 * @return the keyboard mnemonic or -1.
	 */
	public int  getMnemonic (){
		return mnemonic;
	}

	protected void  installIcon (Icon icon ){
		if(icon != null && icon.getDisplay(this) != null){
			addChild(icon.getDisplay(this));
		}
	}

	protected void  uninstallIcon (Icon icon ){
		DisplayObject iconDis =(icon ==null ? null : icon.getDisplay(this));
		if(iconDis != null && isChild(iconDis)){
			removeChild(icon.getDisplay(this));
		}
	}

	/**
	 * Sets the default icon for the button.
	 * <p>
	 * This method will make button repaint, but will not make button relayout,
	 * so if you sets a different size icon, you may need to call <code>revalidate()</code>
	 * to make this button to be relayouted by his container.
	 * </p>
	 * @param defaultIcon the default icon for the button.
	 */
	public void  setIcon (Icon defaultIcon ){
		if(this.defaultIcon != defaultIcon){
			uninstallIcon(this.defaultIcon);
			this.defaultIcon = defaultIcon;
			installIcon(defaultIcon);
			repaint();
			invalidate();
		}
	}

	public Icon  getIcon (){
		return defaultIcon;
	}

    /**
     * Returns the pressed icon for the button.
     * @return the <code>pressedIcon</code> property
     * @see #setPressedIcon()
     */
    public Icon  getPressedIcon (){
        return pressedIcon;
    }

    /**
     * Sets the pressed icon for the button.
     * @param pressedIcon the icon used as the "pressed" image
     * @see #getPressedIcon()
     */
    public void  setPressedIcon (Icon pressedIcon ){
        Icon oldValue =this.pressedIcon ;
        this.pressedIcon = pressedIcon;
        if (pressedIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(pressedIcon);
			//if (getModel().isPressed()) {
                repaint();
            //}
        }
    }

    /**
     * Returns the selected icon for the button.
     * @return the <code>selectedIcon</code> property
     * @see #setSelectedIcon()
     */
    public Icon  getSelectedIcon (){
        return selectedIcon;
    }

    /**
     * Sets the selected icon for the button.
     * @param selectedIcon the icon used as the "selected" image
     * @see #getSelectedIcon()
     */
    public void  setSelectedIcon (Icon selectedIcon ){
        Icon oldValue =this.selectedIcon ;
        this.selectedIcon = selectedIcon;
        if (selectedIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(selectedIcon);
            //if (isSelected()) {
                repaint();
            //}
        }
    }

    /**
     * Returns the rollover icon for the button.
     * @return the <code>rolloverIcon</code> property
     * @see #setRollOverIcon()
     */
    public Icon  getRollOverIcon (){
        return rolloverIcon;
    }

    /**
     * Sets the rollover icon for the button.
     * @param rolloverIcon the icon used as the "rollover" image
     * @see #getRollOverIcon()
     */
    public void  setRollOverIcon (Icon rolloverIcon ){
        Icon oldValue =this.rolloverIcon ;
        this.rolloverIcon = rolloverIcon;
        setRollOverEnabled(true);
        if (rolloverIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(rolloverIcon);
			//if(getModel().isRollOver()){
            	repaint();
            //}
        }

    }

    /**
     * Returns the rollover selection icon for the button.
     * @return the <code>rolloverSelectedIcon</code> property
     * @see #setRollOverSelectedIcon()
     */
    public Icon  getRollOverSelectedIcon (){
        return rolloverSelectedIcon;
    }

    /**
     * Sets the rollover selected icon for the button.
     * @param rolloverSelectedIcon the icon used as the
     *		"selected rollover" image
     * @see #getRollOverSelectedIcon()
     */
    public void  setRollOverSelectedIcon (Icon rolloverSelectedIcon ){
        Icon oldValue =this.rolloverSelectedIcon ;
        this.rolloverSelectedIcon = rolloverSelectedIcon;
        setRollOverEnabled(true);
        if (rolloverSelectedIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(rolloverSelectedIcon);
            //if (isSelected()) {
                repaint();
            //}
        }
    }

    /**
     * Returns the icon used by the button when it's disabled.
     * If no disabled icon has been set, the button constructs
     * one from the default icon.
     * <p>
     * The disabled icon really should be created
     * (if necessary) by the L&F.-->
     *
     * @return the <code>disabledIcon</code> property
     * @see #getPressedIcon()
     * @see #setDisabledIcon()
     */
    public Icon  getDisabledIcon (){
        if(disabledIcon == null) {
            if(defaultIcon != null) {
            	//TODO imp with UIResource??
                //return new GrayFilteredIcon(defaultIcon);
                return defaultIcon;
            }
        }
        return disabledIcon;
    }

    /**
     * Sets the disabled icon for the button.
     * @param disabledIcon the icon used as the disabled image
     * @see #getDisabledIcon()
     */
    public void  setDisabledIcon (Icon disabledIcon ){
        Icon oldValue =this.disabledIcon ;
        this.disabledIcon = disabledIcon;
        if (disabledIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(disabledIcon);
            //if (!isEnabled()) {
                repaint();
            //}
        }
    }

    /**
     * Returns the icon used by the button when it's disabled and selected.
     * If not no disabled selection icon has been set, the button constructs
     * one from the selection icon.
     * <p>
     * The disabled selection icon really should be
     * created (if necessary) by the L&F. -->
     *
     * @return the <code>disabledSelectedIcon</code> property
     * @see #getPressedIcon()
     * @see #setDisabledIcon()
     */
    public Icon  getDisabledSelectedIcon (){
        if(disabledSelectedIcon == null) {
            if(selectedIcon != null) {
            	//TODO imp with UIResource??
                //disabledSelectedIcon = new GrayFilteredIcon(selectedIcon);
            } else {
                return getDisabledIcon();
            }
        }
        return disabledSelectedIcon;
    }

    /**
     * Sets the disabled selection icon for the button.
     * @param disabledSelectedIcon the icon used as the disabled
     * 		selection image
     * @see #getDisabledSelectedIcon()
     */
    public void  setDisabledSelectedIcon (Icon disabledSelectedIcon ){
        Icon oldValue =this.disabledSelectedIcon ;
        this.disabledSelectedIcon = disabledSelectedIcon;
        if (disabledSelectedIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(disabledSelectedIcon);
            //if (!isEnabled() && isSelected()) {
                repaint();
                revalidate();
            //}
        }
    }

    /**
     * Returns the vertical alignment of the text and icon.
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
     * Sets the vertical alignment of the icon and text.
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
        	repaint();
        }
    }

    /**
     * Returns the horizontal alignment of the icon and text.
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
     * Sets the horizontal alignment of the icon and text.
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
        	repaint();
        }
    }


    /**
     * Returns the vertical position of the text relative to the icon.
     * @return the <code>verticalTextPosition</code> property,
     *		one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER  (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public int  getVerticalTextPosition (){
        return verticalTextPosition;
    }

    /**
     * Sets the vertical position of the text relative to the icon.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public void  setVerticalTextPosition (int textPosition ){
        if (textPosition == verticalTextPosition){
	        return;
        }else{
        	verticalTextPosition = textPosition;
        	repaint();
        	revalidate();
        }
    }

    /**
     * Returns the horizontal position of the text relative to the icon.
     * @return the <code>horizontalTextPosition</code> property,
     * 		one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     */
    public int  getHorizontalTextPosition (){
        return horizontalTextPosition;
    }

    /**
     * Sets the horizontal position of the text relative to the icon.
     * @param textPosition one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     */
    public void  setHorizontalTextPosition (int textPosition ){
        if (textPosition == horizontalTextPosition){
        	return;
        }else{
        	horizontalTextPosition = textPosition;
        	repaint();
        	revalidate();
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
     * Returns the shift offset when mouse press.
     *
     * @return the shift offset when mouse press.
     */
    public int  getShiftOffset (){
        return shiftOffset;
    }

    /**
     * Set the shift offset when mouse press.
     */
    public void  setShiftOffset (int shiftOffset ){
        int oldValue =this.shiftOffset ;
        this.shiftOffset = shiftOffset;
        setShiftOffsetSet(true);
        if (shiftOffset != oldValue) {
            revalidate();
            repaint();
        }
    }

    /**
     * Return whether or not the shiftOffset has set by user. The LAF will not change this value if it is true.
     */
    public boolean  isShiftOffsetSet (){
    	return shiftOffsetSet;
    }

   /**
    * Set whether or not the shiftOffset has set by user. The LAF will not change this value if it is true.
    */
    public void  setShiftOffsetSet (boolean b ){
    	shiftOffsetSet = b;
    }

    //--------------------------------------------------------------
    //			internal handlers
    //--------------------------------------------------------------

	private void  initSelfHandlers (){
		addEventListener(MouseEvent.ROLL_OUT, __rollOutListener);
		addEventListener(MouseEvent.ROLL_OVER, __rollOverListener);
		addEventListener(MouseEvent.MOUSE_DOWN, __mouseDownListener);
		//addEventListener(MouseEvent.MOUSE_UP, __mouseUpListener);
		addEventListener(ReleaseEvent.RELEASE, __mouseReleaseListener);
		addEventListener(Event.ADDED_TO_STAGE, __addedToStage);
		addEventListener(Event.REMOVED_FROM_STAGE, __removedFromStage);
	}

	private JRootPane rootPane ;
	private void  __addedToStage (Event e ){
		rootPane = getRootPaneAncestor();
		if(rootPane != null){
			rootPane.registerMnemonic(this);
		}
	}
	private void  __removedFromStage (Event e ){
		if(rootPane != null){
			rootPane.unregisterMnemonic(this);
			rootPane = null;
		}
	}

	private void  __rollOverListener (MouseEvent e ){
		ButtonModel m =getModel ();
		if(isRollOverEnabled()) {
			if(m.isPressed() || !e.buttonDown){
				m.setRollOver(true);
			}
		}
		if(m.isPressed()){
			m.setArmed(true);
		}
	}
	private void  __rollOutListener (MouseEvent e ){
		ButtonModel m =getModel ();
		if(isRollOverEnabled()) {
			if(!m.isPressed()){
				m.setRollOver(false);
			}
		}
		m.setArmed(false);
	}
	private void  __mouseDownListener (Event e ){
		getModel().setArmed(true);
		getModel().setPressed(true);
	}
	/*private void  __mouseUpListener (Event e ){
		if(isRollOverEnabled()) {
			getModel().setRollOver(true);
		}
	}*/
	private void  __mouseReleaseListener (Event e ){
		getModel().setPressed(false);
		getModel().setArmed(false);
		if(isRollOverEnabled() && !hitTestMouse()){
			getModel().setRollOver(false);
		}
	}

	private void  __modelActionListener (AWEvent e ){
		dispatchEvent(new AWEvent(AWEvent.ACT));
	}

	private void  __modelStateListener (AWEvent e ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED));
	}

	private void  __modelSelectionListener (AWEvent e ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.SELECTION_CHANGED));
	}


}


