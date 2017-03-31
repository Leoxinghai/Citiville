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

	
import org.aswing.*;
import org.aswing.graphics.Graphics2D;
import org.aswing.geom.IntRectangle;
import org.aswing.geom.IntDimension;
import org.aswing.Component;
import org.aswing.plaf.*;
import org.aswing.event.AWEvent;
import flash.text.*;
import flash.filters.BlurFilter;
import flash.utils.getTimer;
import flash.filters.BevelFilter;

/**
 * Label UI basic imp.
 * @author iiley
 * @private
 */
public class BasicLabelUI extends BaseComponentUI{
	
	protected JLabel label ;
	protected TextField textField ;
	
	public  BasicLabelUI (){
		super();
	}

    protected String  getPropertyPrefix (){
        return "Label.";
    }
    
	 public void  installUI (Component c ){
		label = JLabel(c);
		installDefaults(label);
		installComponents(label);
		installListeners(label);
	}
    
	 public void  uninstallUI (Component c ){
		label = JLabel(c);
		uninstallDefaults(label);
		uninstallComponents(label);
		uninstallListeners(label);
 	}
 	
 	protected void  installDefaults (JLabel b ){
        String pp =getPropertyPrefix ();
        
        LookAndFeel.installColorsAndFont(b, pp);
        LookAndFeel.installBorderAndBFDecorators(b, pp);
        LookAndFeel.installBasicProperties(b, pp);
 	}
	
 	protected void  uninstallDefaults (JLabel b ){
 		LookAndFeel.uninstallBorderAndBFDecorators(b);
 	}
 	
 	protected void  installComponents (JLabel b ){
 		textField = new TextField();
 		textField.autoSize = TextFieldAutoSize.LEFT;
 		textField.selectable = false;
 		textField.mouseEnabled = false;
 		textField.mouseWheelEnabled = false;
 		b.addChild(textField);
 		b.setFontValidated(false);
 	}
	
 	protected void  uninstallComponents (JLabel b ){
 		b.removeChild(textField);
 	}
 	
 	protected void  installListeners (JLabel b ){
 	}
	
 	protected void  uninstallListeners (JLabel b ){
 	}
    
    //--------------------------------------------------
    
    /* These rectangles/insets are allocated once for all 
     * LabelUI.paint() calls.  Re-using rectangles rather than 
     * allocating them in each paint call substantially reduced the time
     * it took paint to run.  Obviously, this method can't be re-entered.
     */
	private static IntRectangle viewRect =new IntRectangle ();
    private static IntRectangle textRect =new IntRectangle ();
    private static IntRectangle iconRect =new IntRectangle ();

     public void  paint (Component c ,Graphics2D g ,IntRectangle r ){
    	super.paint(c, g, r);
    	JLabel b =JLabel(c );
    	
    	viewRect.setRect(r);
    	
    	textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        // layout the text and icon
        String text =AsWingUtils.layoutCompoundLabel(c ,
            c.getFont(), b.getText(), getIconToLayout(), 
            b.getVerticalAlignment(), b.getHorizontalAlignment(),
            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
            viewRect, iconRect, textRect, 
	    	b.getText() == null ? 0 : b.getIconTextGap());
	   	
    	
    	paintIcon(b, g, iconRect);
    	
        if (text != null && text != ""){
        	textField.visible = true;
			paintText(b, textRect, text);
        }else{
        	textField.text = "";
        	textField.visible = false;
        }
        
        textField.selectable = b.isSelectable();
        textField.mouseEnabled = b.isSelectable();
    }
    
    protected Icon  getIconToLayout (){
    	return label.getIcon();
    }
        
    /**
     * paint the text to specified button with specified bounds
     */
    protected void  paintText (JLabel b ,IntRectangle textRect ,String text ){
    	ASFont font =b.getFont ();
    	
		if(textField.text != text){
			textField.text = text;
		}
		if(!b.isFontValidated()){
			AsWingUtils.applyTextFont(textField, font);
			b.setFontValidated(true);
		}
    	AsWingUtils.applyTextColor(textField, b.getForeground());
		textField.x = textRect.x;
		textField.y = textRect.y;
    	if(!b.isEnabled()){
    		b.filters = .get(new BlurFilter(2, 2, 2));
    	}else{
    		b.filters = null;
    	}
    	textField.filters = label.getTextFilters();
    }
    
    /**
     * paint the icon to specified button's mc with specified bounds
     */
    protected void  paintIcon (JLabel b ,Graphics2D g ,IntRectangle iconRect ){
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

		if(!b.isEnabled()) {
			tmpIcon = b.getDisabledIcon();
		}
              
		if(tmpIcon != null) {
			icon = tmpIcon;
		}
		
		setIconVisible(icon, true);
		icon.updateIcon(b, g, iconRect.x, iconRect.y);
    }
    
    private void  setIconVisible (Icon icon ,boolean visible ){
    	if(icon.getDisplay(label) != null){
    		icon.getDisplay(label).visible = visible;
    	}
    }
    
    protected Array  getIcons (){
    	Array arr =new Array ();
    	if(label.getIcon() != null){
    		arr.push(label.getIcon());
    	}
    	if(label.getDisabledIcon() != null){
    		arr.push(label.getDisabledIcon());
    	}
    	return arr;
    }
    
      
    /**
     * Returns the a label's preferred size with specified icon and text.
     */
    protected IntDimension  getLabelPreferredSize (JLabel b ,Icon icon ,String text ){
    	viewRect.setRectXYWH(0, 0, 100000, 100000);
    	textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        
        AsWingUtils.layoutCompoundLabel(b, 
            b.getFont(), text, icon, 
            b.getVerticalAlignment(), b.getHorizontalAlignment(),
            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
            viewRect, iconRect, textRect, 
	    	b.getText() == null ? 0 : b.getIconTextGap()
        );
        /* The preferred size of the button is the size of 
         * the text and icon rectangles plus the buttons insets.
         */
        IntDimension size ;
        if(icon == null){
        	size = textRect.getSize();
        }else if(b.getText()==null || b.getText()==""){
        	size = iconRect.getSize();
        }else{
        	IntRectangle r =iconRect.union(textRect );
        	size = r.getSize();
        }
        size = b.getInsets().getOutsideSize(size);
        return size;
    }    
    
     public IntDimension  getPreferredSize (Component c ){
    	JLabel b =JLabel(c );
    	return getLabelPreferredSize(b, getIconToLayout(), b.getText());
    }

     public IntDimension  getMinimumSize (Component c ){
    	return c.getInsets().getOutsideSize();
    }

     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }
	
}


