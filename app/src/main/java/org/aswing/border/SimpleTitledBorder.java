
package org.aswing.border;

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

	
/**
 * A poor Title Border.
 * @author iiley
 */
import flash.display.*;
import flash.text.*;

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.HashMap;

public class SimpleTitledBorder extends DecorateBorder
	
	public static  int TOP =AsWingConstants.TOP ;
	public static  int BOTTOM =AsWingConstants.BOTTOM ;
	
	public static  int CENTER =AsWingConstants.CENTER ;
	public static  int LEFT =AsWingConstants.LEFT ;
	public static  int RIGHT =AsWingConstants.RIGHT ;
	

    // Space between the border and the component's edge
    public static int EDGE_SPACING =0;	
	
	private String title ;
	private int position ;
	private int align ;
	private int offset ;
	private ASFont font ;
	private ASColor color ;
	
	private TextField textField ;
	private IntDimension textFieldSize ;
	private boolean colorFontValid ;
	
	/**
	 * Create a simple titled border.
	 * @param title the title text string.
	 * @param position the position of the title(TOP or BOTTOM), default is TOP
	 * @see #TOP
	 * @see #BOTTOM
	 * @param align the align of the title(CENTER or LEFT or RIGHT), default is CENTER
	 * @see #CENTER
	 * @see #LEFT
	 * @see #RIGHT
	 * @param offset the addition of title text's x position, default is 0
	 * @param font the title text's ASFont
	 * @param color the color of the title text
	 * @see org.aswing.border.TitledBorder
	 */
	public  SimpleTitledBorder (Border interior =null ,String title ="",int position =AsWingConstants .TOP ,int align =CENTER ,int offset =0,ASFont font =null ,ASColor color =null ){
		super(interior);
		this.title = title;
		this.position = position;
		this.align = align;
		this.offset = offset;
		this.font = (font==null ? UIManager.getFont("systemFont") : font);
		this.color = (color==null ? ASColor.BLACK : color);
		textField = null;
		colorFontValid = false;
		textFieldSize = null;
	}
	
	
	//------------get set-------------
	
		
	public int  getPosition (){
		return position;
	}

	public void  setPosition (int position ){
		this.position = position;
	}

	public ASColor  getColor (){
		return color;
	}

	public void  setColor (ASColor color ){
		this.color = color;
		this.invalidateColorFont();
	}

	public ASFont  getFont (){
		return font;
	}

	public void  setFont (ASFont font ){
		this.font = font;
		invalidateColorFont();
		invalidateExtent();
	}

	public int  getAlign (){
		return align;
	}

	public void  setAlign (int align ){
		this.align = align;
	}

	public String  getTitle (){
		return title;
	}

	public void  setTitle (String title ){
		this.title = title;
		this.invalidateExtent();
		this.invalidateColorFont();
	}

	public int  getOffset (){
		return offset;
	}

	public void  setOffset (int offset ){
		this.offset = offset;
	}
	
	private void  invalidateExtent (){
		textFieldSize = null;
	}
	private void  invalidateColorFont (){
		colorFontValid = false;
	}
	
	private IntDimension  getTextFieldSize (){
    	if (textFieldSize == null){
	    	TextFormat tf =getFont ().getTextFormat ();
			textFieldSize = AsWingUtils.computeStringSize(tf, title);   	
    	}
    	return textFieldSize;
	}
	
	private TextField  getTextField (){
    	if(textField == null){
	    	textField = new TextField();
	    	textField.selectable = false;
	    	textField.autoSize = TextFieldAutoSize.CENTER;	    	
    	}
    	return textField;
	}
	
	 public void  updateBorderImp (Component c ,Graphics2D g ,IntRectangle bounds ){
    	if(!colorFontValid){
    		textField.text = title;
    		AsWingUtils.applyTextFontAndColor(textField, font, color);
    		colorFontValid = true;
    	}
    	
    	int width =Math.ceil(textField.width );
    	int height =Math.ceil(textField.height );
    	int x =offset ;
    	if(align == LEFT){
    		x += bounds.x;
    	}else if(align == RIGHT){
    		x += (bounds.x + bounds.width - width);
    	}else{
    		x += (bounds.x + bounds.width/2 - width/2);
    	}
    	int y =bounds.y +EDGE_SPACING ;
    	if(position == BOTTOM){
    		y = bounds.y + bounds.height - height + EDGE_SPACING;
    	}
    	textField.x = x;
    	textField.y = y;
    }
    	   
     public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	Insets insets =new Insets ();
    	IntDimension cs =bounds.getSize ();
		if(cs.width < getTextFieldSize().width){
			int delta =Math.ceil(getTextFieldSize ().width )-cs.width ;
			if(align == RIGHT){
				insets.left = delta;
			}else if(align == CENTER){
				insets.left = delta/2;
				insets.right = delta/2;
			}else{
				insets.right = delta;
			}
		}
    	if(position == BOTTOM){
    		insets.bottom = EDGE_SPACING*2 + Math.ceil(getTextFieldSize().height);
    	}else{
    		insets.top = EDGE_SPACING*2 + Math.ceil(getTextFieldSize().height);
    	}
    	return insets;
    }
	
	 public DisplayObject  getDisplayImp ()
	{
		return getTextField();
	}	
}


