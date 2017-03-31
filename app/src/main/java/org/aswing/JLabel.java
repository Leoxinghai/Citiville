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


import org.aswing.plaf.basic.BasicLabelUI;
import flash.display.DisplayObject;
	
/**
 * A display area for a short text string or an image, 
 * or both.
 * A label does not react to input events.
 * As a result, it cannot get the keyboard focus.
 * A label can, however, display a keyboard alternative
 * as a convenience for a nearby component
 * that has a keyboard alternative but can't display it.
 * <p>
 * A <code>JLabel</code> object can display
 * either text, an image, or both.
 * You can specify where in the label's display area
 * the label's contents are aligned
 * by setting the vertical and horizontal alignment.
 * By default, labels are vertically centered 
 * in their display area.
 * Text-only labels are leading edge aligned, by default;
 * image-only labels are horizontally centered, by default.
 * </p>
 * <p>
 * You can also specify the position of the text
 * relative to the image.
 * By default, text is on the trailing edge of the image,
 * with the text and image vertically aligned.
 * </p>
 * <p>
 * Finally, you can use the <code>setIconTextGap</code> method
 * to specify how many pixels
 * should appear between the text and the image.
 * The default is 4 pixels.
 * </p>
 * @author iiley
 */
public class JLabel extends Component{
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static int CENTER =AsWingConstants.CENTER ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static int TOP =AsWingConstants.TOP ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int LEFT =AsWingConstants.LEFT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int BOTTOM =AsWingConstants.BOTTOM ;
 	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static int RIGHT =AsWingConstants.RIGHT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */        
	public static int HORIZONTAL =AsWingConstants.HORIZONTAL ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static int VERTICAL =AsWingConstants.VERTICAL ;
	
	
	private Icon icon ;
	private String text ;
	private Icon disabledIcon ;
	
	// Icon/Label Alignment
    private int verticalAlignment ;
    private int horizontalAlignment ;
    
    private int verticalTextPosition ;
    private int horizontalTextPosition ;

    private int iconTextGap ;
    private boolean selectable ;
    private Array textFilters =null ;
    
    /**
     * Creates a label.
     * @param text the text
     * @param icon the icon
     * @param horizontalAlignment the horizontal alignment, default is <code>CENTER</code>
     */
	public  JLabel (String text ="",Icon icon =null ,int horizontalAlignment =0){
		super();
		setName("JLabel");
		//default
    	this.verticalAlignment = CENTER;
    	this.verticalTextPosition = CENTER;
    	this.horizontalTextPosition = RIGHT;
    	
    	this.text = text;
    	this.icon = icon;
    	installIcon(icon);
    	this.horizontalAlignment = horizontalAlignment;
    	
    	iconTextGap = 4;
    	selectable = false;
		
		updateUI();
	}
	
     public void  updateUI (){
    	setUI(UIManager.getUI(this));
    }
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicLabelUI;
    }
	
	 public String  getUIClassID (){
		return "LabelUI";
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
	
	public void  setText (String text ){
		if(this.text != text){
			this.text = text;
			repaint();
			invalidate();
		}
	}
	
	public String  getText (){
		return text;
	}
	
	public void  setSelectable (boolean b ){
		selectable = b;
	}
	
	public boolean  isSelectable (){
		return selectable;
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
	
	public void  setIcon (Icon icon ){
		if(this.icon != icon){
			uninstallIcon(this.icon);
			this.icon = icon;
			installIcon(this.icon);
			repaint();
			invalidate();
		}
	}

	public Icon  getIcon (){
		return icon;
	}

    /**
     * Returns the icon used by the label when it's disabled.
     * If no disabled icon has been set, the button constructs
     * one from the default icon if defalut icon setted. otherwish 
     * return null; 
     * <p>
     * The disabled icon really should be created 
     * (if necessary) by the L&F.-->
     *
     * @return the <code>disabledIcon</code> property
     * @see #setDisabledIcon()
     * @see #getEnabled()
     */
    public Icon  getDisabledIcon (){
        if(disabledIcon == null) {
            if(icon != null) {
            	//TODO imp
                //disabledIcon = new GrayFilteredIcon(icon);
            }
        }
        return disabledIcon;
    }
    
    /**
     * Sets the disabled icon for the label.
     * @param disabledIcon the icon used as the disabled image
     * @see #getDisabledIcon()
     * @see #setEnabled()
     */
    public void  setDisabledIcon (Icon disabledIcon ){
        Icon oldValue =this.disabledIcon ;
        this.disabledIcon = disabledIcon;
        if (disabledIcon != oldValue) {
        	uninstallIcon(oldValue);
        	installIcon(disabledIcon);
            if (!isEnabled()) {
                repaint();
				invalidate();
            }
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
    public double  getVerticalAlignment (){
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
    public void  setVerticalAlignment (double alignment ){
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
    public double  getHorizontalAlignment (){
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
    public void  setHorizontalAlignment (double alignment ){
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
    public double  getVerticalTextPosition (){
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
    public void  setVerticalTextPosition (double textPosition ){
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
    public double  getHorizontalTextPosition (){
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
    public void  setHorizontalTextPosition (double textPosition ){
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
    public double  getIconTextGap (){
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
    public void  setIconTextGap (double iconTextGap ){
        double oldValue =this.iconTextGap ;
        this.iconTextGap = iconTextGap;
        if (iconTextGap != oldValue) {
            revalidate();
            repaint();
        }
    }
	

}


