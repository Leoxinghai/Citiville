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


import flash.text.TextField;
import flash.text.TextFormat;

import org.aswing.geom.IntDimension;
import org.aswing.plaf.DefaultEmptyDecoraterResource;

/**
 * Font that specified the font name, size, style and whether or not embed.
 * @author iiley
 */
public class ASFont{

 	private String name ;
 	private int size ;
 	private boolean bold ;
 	private boolean italic ;
 	private boolean underline ;
 	private TextFormat textFormat ;
 	private ASFontAdvProperties advancedProperties ;
        private boolean fullFeatured =false ;

 	/**
 	 * Create a font.
 	 * @param embedFontsOrAdvancedPros a boolean to indicate whether or not embedFonts or
 	 * 			a <code>ASFontAdvProperties</code> instance.
 	 * @see org.aswing.ASFontAdvProperties
 	 */
	public  ASFont (String name ="Tahoma",double size =11,boolean bold =false ,boolean italic =false ,boolean underline =false ,
		: = embedFontsOrAdvancedProsnull){
		this.name = name;
		this.size = size;
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
		if(embedFontsOrAdvancedPros is ASFontAdvProperties){
			advancedProperties =(ASFontAdvProperties) embedFontsOrAdvancedPros;
		}else{
			advancedProperties = new ASFontAdvProperties(embedFontsOrAdvancedPros==true);
		}
		textFormat = getTextFormat();
		fullFeatured = true;
	}

	public String  getName (){
		return name;
	}

	public ASFont  changeName (String name ){
		return new ASFont(name, size, bold, italic, underline, advancedProperties);
	}

	public int  getSize (){
		return size;
	}

	public ASFont  changeSize (int size ){
		return new ASFont(name, size, bold, italic, underline, advancedProperties);
	}

	public boolean  isBold (){
		return bold;
	}

	public ASFont  changeBold (boolean bold ){
		return new ASFont(name, size, bold, italic, underline, advancedProperties);
	}

	public boolean  isItalic (){
		return italic;
	}

	public ASFont  changeItalic (boolean italic ){
		return new ASFont(name, size, bold, italic, underline, advancedProperties);
	}

	public boolean  isUnderline (){
		return underline;
	}

	public ASFont  changeUnderline (boolean underline ){
		return new ASFont(name, size, bold, italic, underline, advancedProperties);
	}

	public boolean  isEmbedFonts (){
		return advancedProperties.isEmbedFonts();
	}

	public ASFontAdvProperties  getAdvancedProperties (){
		return advancedProperties;
	}

	/**
	 * Applys the font to the specified text field.
	 * @param textField the text filed to be applied font.
	 * @param beginIndex The zero-based index position specifying the first character of the desired range of text.
	 * @param endIndex The zero-based index position specifying the last character of the desired range of text.
	 */
	public void  apply (TextField textField ,int beginIndex =-1,int endIndex =-1){
		advancedProperties.apply(textField);
		textField.setTextFormat(textFormat, beginIndex, endIndex);
		textField.defaultTextFormat = textFormat;
	}

	/**
	 * Return a new text format that contains the font properties.
	 * @return a new text format.
	 */
	public TextFormat  getTextFormat (){
		return new TextFormat(name, size, null, bold, italic, underline);
	}

	/**
	 * Computes text size with this font.
	 * @param text the text to be compute
	 * @includeGutters whether or not include the 2-pixels gutters in the result
	 * @return the computed size of the text
	 * @see org.aswing.AsWingUtils#computeStringSizeWithFont
	 */
	public IntDimension  computeTextSize (String text ,boolean includeGutters =true ){
		return AsWingUtils.computeStringSizeWithFont(this, text, includeGutters);
	}

        public ASFont  makeFullFeatured ()
        {
            if (!this.isFullFeatured())
            {
                return this.takeover(DefaultEmptyDecoraterResource.DEFAULT_FONT);
            }
            return this;
        }//end

        public boolean  isFullFeatured ()
        {
            return this.fullFeatured;
        }//end


        protected boolean  judegeWhetherFullFeatured ()
        {
            if (this.textFormat.align == null)
            {
                return false;
            }
            if (this.textFormat.blockIndent == null)
            {
                return false;
            }
            if (this.textFormat.bold == null)
            {
                return false;
            }
            if (this.textFormat.bullet == null)
            {
                return false;
            }
            if (this.textFormat.color == null)
            {
                return false;
            }
            if (this.textFormat.font == null)
            {
                return false;
            }
            if (this.textFormat.indent == null)
            {
                return false;
            }
            if (this.textFormat.italic == null)
            {
                return false;
            }
            if (this.textFormat.kerning == null)
            {
                return false;
            }
            if (this.textFormat.leading == null)
            {
                return false;
            }
            if (this.textFormat.leftMargin == null)
            {
                return false;
            }
            if (this.textFormat.letterSpacing == null)
            {
                return false;
            }
            if (this.textFormat.rightMargin == null)
            {
                return false;
            }
            if (this.textFormat.size == null)
            {
                return false;
            }
            if (this.textFormat.target == null)
            {
                return false;
            }
            if (this.textFormat.underline == null)
            {
                return false;
            }
            if (this.textFormat.url == null)
            {
                return false;
            }
            return this.advancedProperties.isFullFeatured();
        }//end

        public ASFont  takeover (ASFont param1 )
        {
            if (param1 == null)
            {
                param1 = DefaultEmptyDecoraterResource.DEFAULT_FONT;
            }
            if (param1 == this)
            {
                return this;
            }
            _loc_2 = param1.textFormat ;
            _loc_3 = this.cloneTextFormat(this.textFormat );
            if (_loc_3.align == null)
            {
                _loc_3.align = _loc_2.align;
            }
            if (_loc_3.blockIndent == null)
            {
                _loc_3.blockIndent = _loc_2.blockIndent;
            }
            if (_loc_3.bold == null)
            {
                _loc_3.bold = _loc_2.bold;
            }
            if (_loc_3.bullet == null)
            {
                _loc_3.bullet = _loc_2.bullet;
            }
            if (_loc_3.color == null)
            {
                _loc_3.color = _loc_2.color;
            }
            if (_loc_3.font == null)
            {
                _loc_3.font = _loc_2.font;
            }
            if (_loc_3.indent == null)
            {
                _loc_3.indent = _loc_2.indent;
            }
            if (_loc_3.italic == null)
            {
                _loc_3.italic = _loc_2.italic;
            }
            if (_loc_3.kerning == null)
            {
                _loc_3.kerning = _loc_2.kerning;
            }
            if (_loc_3.leading == null)
            {
                _loc_3.leading = _loc_2.leading;
            }
            if (_loc_3.leftMargin == null)
            {
                _loc_3.leftMargin = _loc_2.leftMargin;
            }
            if (_loc_3.letterSpacing == null)
            {
                _loc_3.letterSpacing = _loc_2.letterSpacing;
            }
            if (_loc_3.rightMargin == null)
            {
                _loc_3.rightMargin = _loc_2.rightMargin;
            }
            if (_loc_3.size == null)
            {
                _loc_3.size = _loc_2.size;
            }
            if (_loc_3.tabStops == null)
            {
                _loc_3.tabStops = _loc_2.tabStops;
            }
            if (_loc_3.target == null)
            {
                _loc_3.target = _loc_2.target;
            }
            if (_loc_3.underline == null)
            {
                _loc_3.underline = _loc_2.underline;
            }
            if (_loc_3.url == null)
            {
                _loc_3.url = _loc_2.url;
            }
            _loc_4 = this.advancedProperties.takeover(param1.advancedProperties );
            return new ASFont(_loc_3, 0, false, false, false, _loc_4);
        }//end

        private TextFormat  cloneTextFormat (TextFormat param1 )
        {
            _loc_2 = new TextFormat ();
            _loc_2.align = param1.align;
            _loc_2.blockIndent = param1.blockIndent;
            _loc_2.bold = param1.bold;
            _loc_2.bullet = param1.bullet;
            _loc_2.color = param1.color;
            _loc_2.font = param1.font;
            _loc_2.indent = param1.indent;
            _loc_2.italic = param1.italic;
            _loc_2.kerning = param1.kerning;
            _loc_2.leading = param1.leading;
            _loc_2.leftMargin = param1.leftMargin;
            _loc_2.letterSpacing = param1.letterSpacing;
            _loc_2.rightMargin = param1.rightMargin;
            _loc_2.size = param1.size;
            _loc_2.tabStops = param1.tabStops;
            _loc_2.target = param1.target;
            _loc_2.underline = param1.underline;
            _loc_2.url = param1.url;
            return _loc_2;
        }//end

	public String  toString (){
		return "ASFont["
			+ "name : " + name
			+ ", size : " + size
			+ ", bold : " + bold
			+ ", italic : " + italic
			+ ", underline : " + underline
			+ ", advanced : " + advancedProperties
			+ "]";
	}

}


