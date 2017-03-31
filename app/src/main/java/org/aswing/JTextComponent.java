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


import flash.display.InteractiveObject;
import flash.events.*;
import flash.text.*;
import flash.ui.Keyboard;

import org.aswing.geom.*;

/**
 * JTextComponent is the base class for text components.
 * <p>
 * <code>JTextComponent</code> can be formated by <code>ASFont</code>,
 * but some times you need complex format,then <code>ASFont</code> is
 * not enough, so you can set a <code>EmptyFont</code> instance to the
 * <code>JTextComponent</code>, it will do nothing for the format, then
 * you can call <code>setTextFormat</code>, <code>setDefaultTextFormat</code>
 * to format the text with <code>TextFormat</code> instances. And don't forgot
 * to call <code>revalidate</code> if you think the component size should be
 * change after that. Because these method will not call <code>revalidate</code>
 * automatically.
 * </p>
 *
 * @author iiley
 * @see #setTextFormat()
 * @see EmptyFont
 * @see JTextField
 * @see JTextArea
 */
public class JTextComponent extends Component implements EditableComponent{

	private TextField textField ;
	private boolean editable ;

	protected int columnWidth ;
	protected int rowHeight ;
	protected int widthMargin ;
	protected int heightMargin ;
	protected boolean columnRowCounted ;

	public  JTextComponent (){
		super();
		textField = new TextField();
		textField.type = TextFieldType.INPUT;
		textField.autoSize = TextFieldAutoSize.NONE;
		textField.background = false;
		editable = true;
		columnRowCounted = false;
		addChild(textField);
		textField.addEventListener(TextEvent.TEXT_INPUT, __onTextComponentTextInput);
	}

	private void  __onTextComponentTextInput (TextEvent e ){
    	if(!getTextField().multiline){ //fix the bug that fp in interenet browser single line TextField Ctrl+Enter will entered a newline bug
    		String text =e.text ;
    		KeyboardManager km =getKeyboardManager ();
    		if(km){
	    		if(km.isKeyDown(Keyboard.CONTROL) && km.isKeyDown(Keyboard.ENTER)){
					if(text.length == 1 && text.charCodeAt(0) == 10){
						e.preventDefault();
					}
	    		}
    		}
    	}
	}

	/**
	 * Returns the internal <code>TextField</code> instance.
	 * @return the internal <code>TextField</code> instance.
	 */
	public TextField  getTextField (){
		return textField;
	}

	/**
	 *Subclass  this method to do right counting .
	 */
	protected boolean  isAutoSize (){
		return false;
	}

	 public void  setEnabled (boolean b ){
		super.setEnabled(b);
		getTextField().selectable = b;
		getTextField().mouseEnabled = b;
	}

	public void  setEditable (boolean b ){
		if(b != editable){
			editable = b;
			if(b){
				getTextField().type = TextFieldType.INPUT;
			}else{
				getTextField().type = TextFieldType.DYNAMIC;
			}
			invalidate();
			invalidateColumnRowSize();
			repaint();
		}
	}

	public boolean  isEditable (){
		return editable;
	}

	/**
	 * Sets the font to the text component.
	 * @param f the font.
	 * @see EmptyFont
	 */
	 public void  setFont (ASFont f ){
		super.setFont(f);
		setFontValidated(true);
		if(getFont() != null){
			getFont().apply(getTextField());
			invalidateColumnRowSize();
		}
	}

	 public void  setForeground (ASColor c ){
		super.setForeground(c);
		if(getForeground() != null){
    		getTextField().textColor = getForeground().getRGB();
    		getTextField().alpha = getForeground().getAlpha();
  		}
	}

	public void  setText (String text ){
		if(getTextField().text != text){
			getTextField().text = text;
			if(isAutoSize()){
				revalidate();
			}
		}
	}

	public String  getText (){
		return getTextField().text;
	}

	public void  setHtmlText (String ht ){
		getTextField().htmlText = ht;
		if(isAutoSize()){
			revalidate();
		}
	}

	public String  getHtmlText (){
		return getTextField().htmlText;
	}

	public void  appendText (String newText ){
		getTextField().appendText(newText);
		if(isAutoSize()){
			revalidate();
		}
	}

	/**
	 * Append text implemented by <code>replaceText</code> to avoid the
	 * <code>appendText()</code> method bug(the bug will make the text not be append at
	 * the end of the text, some times it appends to a middle position).
	 * @param newText the text to be append to the end of the text field.
	 */
	public void  appendByReplace (String newText ){
		int n =getLength ();
		getTextField().replaceText(n, n, newText);
	}

	public void  replaceSelectedText (String value ){
		getTextField().replaceSelectedText(value);
	}

	public void  replaceText (int beginIndex ,int endIndex ,String newText ){
		getTextField().replaceText(beginIndex, endIndex, newText);
	}

	public void  setSelection (int beginIndex ,int endIndex ){
		getTextField().setSelection(beginIndex, endIndex);
	}

	public void  selectAll (){
		getTextField().setSelection(0, getTextField().length());
	}

	public void  setCondenseWhite (boolean b ){
		if(getTextField().condenseWhite != b){
			getTextField().condenseWhite = b;
			revalidate();
		}
	}

	public boolean  isCondenseWhite (){
		return getTextField().condenseWhite;
	}

	/**
	 * Sets the default textFormat to the text.
	 * <p>
	 * You should set a <code>EmptyFont</code> instance to be the component
	 * font before this call to make sure the textFormat will be effective.
	 * </p>
	 * @param dtf the default textformat.
	 * @see #setFont()
	 */
	public void  setDefaultTextFormat (TextFormat dtf ){
		getTextField().defaultTextFormat = dtf;
	}

	public TextFormat  getDefaultTextFormat (){
		return getTextField().defaultTextFormat;
	}

	/**
	 * Sets the textFormat to the specified range.
	 * <p>
	 * You should set a <code>EmptyFont</code> instance to be the component
	 * font before this call to make sure the textFormat will be effective.
	 * </p>
	 * @param tf the default textformat.
	 * @param beginIndex the begin index.
	 * @param endIndex the end index.
	 * @see #setFont()
	 */
	public void  setTextFormat (TextFormat tf ,int beginIndex =-1,int endIndex =-1){
		getTextField().setTextFormat(tf, beginIndex, endIndex);
	}

	public TextFormat  getTextFormat (int beginIndex =-1,int endIndex =-1){
		return getTextField().getTextFormat(beginIndex, endIndex);
	}

	public void  setDisplayAsPassword (boolean b ){
		getTextField().displayAsPassword = b;
	}

	public boolean  isDisplayAsPassword (){
		return getTextField().displayAsPassword;
	}

	public int  getLength (){
		return getTextField().length;
	}

	public void  setMaxChars (int n ){
		getTextField().maxChars = n;
	}

	public int  getMaxChars (){
		return getTextField().maxChars;
	}

	public void  setRestrict (String res ){
		getTextField().restrict = res;
	}

	public String  getRestrict (){
		return getTextField().restrict;
	}

	public int  getSelectionBeginIndex (){
		return getTextField().selectionBeginIndex;
	}

	public int  getSelectionEndIndex (){
		return getTextField().selectionEndIndex;
	}

	public void  setCSS (StyleSheet css ){
		getTextField().styleSheet = css;
		if(isAutoSize()){
			revalidate();
		}
	}

	public StyleSheet  getCSS (){
		return getTextField().styleSheet;
	}

	public void  setWordWrap (boolean b ){
		getTextField().wordWrap = b;
		if(isAutoSize()){
			revalidate();
		}
	}

	public boolean  isWordWrap (){
		return getTextField().wordWrap;
	}

	public void  setUseRichTextClipboard (boolean b ){
		getTextField().useRichTextClipboard = b;
	}

	public boolean  isUseRichTextClipboard (){
		return getTextField().useRichTextClipboard;
	}

	//-------------------------------------------------------------

	/**
	 * JTextComponent need count preferred size itself.
	 */
	 protected IntDimension  countPreferredSize (){
		throw new Error("Subclass of JTextComponent need implement this method : countPreferredSize!");
		return null;
	}

	/**
	 * Invalidate the column and row size, make it will be recount when need it next time.
	 */
	protected void  invalidateColumnRowSize (){
		columnRowCounted = false;
	}

	/**
	 * Returns the column width. The meaning of what a column is can be considered a fairly weak notion for some fonts.
	 * This method is used to define the width of a column.
	 * By default this is defined to be the width of the character m for the font used.
	 * if the font size changed, the invalidateColumnRowSize will be called,
	 * then next call get method about this will be counted first.
	 */
	protected int  getColumnWidth (){
		if(!columnRowCounted) countColumnRowSize();
		return columnWidth;
	}

	/**
	 * Returns the row height. The meaning of what a column is can be considered a fairly weak notion for some fonts.
	 * This method is used to define the height of a row.
	 * By default this is defined to be the height of the character m for the font used.
	 * if the font size changed, the invalidateColumnRowSize will be called,
	 * then next call get method about this will be counted first.
	 */
	protected int  getRowHeight (){
		if(!columnRowCounted) countColumnRowSize();
		return rowHeight;
	}

	/**
	 * @see #getColumnWidth()
	 */
	protected int  getWidthMargin (){
		if(!columnRowCounted) countColumnRowSize();
		return widthMargin;
	}

	/**
	 * @see #getRowHeight()
	 */
	protected int  getHeightMargin (){
		if(!columnRowCounted) countColumnRowSize();
		return heightMargin;
	}

	protected IntDimension  getTextFieldAutoSizedSize (int forceWidth =0,int forceHeight =0){
		TextField tf =getTextField ();
		IntDimension oldSize =new IntDimension(tf.width ,tf.height );
		String old =tf.autoSize ;
		if(forceWidth != 0){
			tf.width = forceWidth;
		}
		if(forceHeight != 0){
			tf.height = forceHeight;
		}
		tf.autoSize = TextFieldAutoSize.LEFT;
		IntDimension size =new IntDimension(tf.width ,tf.height );
		tf.autoSize = old;
		tf.width = oldSize.width;
		tf.height = oldSize.height;
		if(forceWidth != 0){
			size.width = forceWidth;
		}
		if(forceHeight != 0){
			size.height = forceHeight;
		}
		return size;
	}

	protected void  countColumnRowSize (){
		String str ="mmmmm";
		TextFormat tf =getFont ().getTextFormat ();
		IntDimension textFieldSize =AsWingUtils.computeStringSize(tf ,str ,true ,getTextField ());
		IntDimension textSize =AsWingUtils.computeStringSize(tf ,str ,false ,getTextField ());

		columnWidth = textSize.width/5;
		rowHeight = textSize.height;
		widthMargin = textFieldSize.width - textSize.width;
		heightMargin = textFieldSize.height - textSize.height;
		columnRowCounted = true;
	}

    /**
     * Returns the text field to receive the focus for this component.
     * @return the object to receive the focus.
     */
     public InteractiveObject  getInternalFocusObject (){
    	return getTextField();
    }

	 protected void  paint (IntRectangle b ){
		super.paint(b);
		applyBoundsToText(b);
	}

    protected void  applyBoundsToText (IntRectangle b ){
		TextField t =getTextField ();
		t.x = b.x;
		t.y = b.y;
		t.width = b.width;
		t.height = b.height;
    }
}


