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


import flash.text.AntiAliasType;
import flash.text.GridFitType;
import flash.text.TextField;
	

/**
 * The advanced properties for font.
 * @see flash.text.TextField
 * @see flash.text.AntiAliasType
 * @see flash.text.GridFitType
 * @author iiley
 */
public class ASFontAdvProperties{
	
	
 	private String antiAliasType ;
 	private String gridFitType ;
 	private double sharpness ;
 	private double thickness ;
 	private boolean embedFonts ;
        
        private boolean fullFeatured =false ;
        
 	
	public  ASFontAdvProperties (
		embedFonts:Boolean=false, antiAliasType:String="normal", 
		gridFitType:String="pixel", sharpness:Number=0, thickness:Number=0){
		this.embedFonts = embedFonts;
		this.antiAliasType = antiAliasType;
		this.gridFitType = gridFitType;
		this.sharpness = sharpness;
		this.thickness = thickness;
	}
	
	public String  getAntiAliasType (){
		return antiAliasType;
	}
	
	public ASFontAdvProperties  changeAntiAliasType (String newType ){
		return new ASFontAdvProperties(embedFonts, newType, gridFitType, sharpness, thickness);
	}
	
	public String  getGridFitType (){
		return gridFitType;
	}
	
	public ASFontAdvProperties  changeGridFitType (String newType ){
		return new ASFontAdvProperties(embedFonts, antiAliasType, newType, sharpness, thickness);
	}
	
	public double  getSharpness (){
		return sharpness;
	}
	
	public ASFontAdvProperties  changeSharpness (double newSharpness ){
		return new ASFontAdvProperties(embedFonts, antiAliasType, gridFitType, newSharpness, thickness);
	}
	
	public double  getThickness (){
		return thickness;
	}
	
	public ASFontAdvProperties  changeThickness (double newThickness ){
		return new ASFontAdvProperties(embedFonts, antiAliasType, gridFitType, sharpness, newThickness);
	}
	
	public boolean  isEmbedFonts (){
		return embedFonts;
	}
	
	public ASFontAdvProperties  changeEmbedFonts (boolean ef ){
		return new ASFontAdvProperties(ef, antiAliasType, gridFitType, sharpness, thickness);
	}	
	
	/**
	 * Applys the properties to the specified text field.
	 * @param textField the text filed to be applied font.
	 */
	public void  apply (TextField textField ){
		textField.embedFonts = isEmbedFonts();
		textField.antiAliasType = getAntiAliasType();
		textField.gridFitType = getGridFitType();
		textField.sharpness = getSharpness();
		textField.thickness = getThickness();
	}
	
	public ASFontAdvProperties  takeover (ASFontAdvProperties param1 )
	{
	    ASFontAdvProperties _loc_2 =new ASFontAdvProperties(this.embedFonts ,this.antiAliasType ,this.gridFitType ,this.sharpness ,this.thickness );


	    _loc_2.embedFonts = param1.embedFonts;
	    
	    if (_loc_2.antiAliasType == null)
	    {
		_loc_2.antiAliasType = param1.antiAliasType;
	    }
	    if (_loc_2.gridFitType == null)
	    {
		_loc_2.gridFitType = param1.gridFitType;
	    }
	    if (_loc_2.sharpness == 0)
	    {
		_loc_2.sharpness = param1.sharpness;
	    }
	    if (_loc_2.thickness == 0)
	    {
		_loc_2.thickness = param1.thickness;
	    }
	    _loc_2.fullFeatured = _loc_2.judegeWhetherFullFeatured();
	    return _loc_2;
	}//end  

	public boolean  isFullFeatured ()
	{
	    return this.fullFeatured;
	}//end  

        protected boolean  judegeWhetherFullFeatured ()
        {
            if (this.antiAliasType == null)
            {
                return false;
            }

            if (this.gridFitType == null)
            {
                return false;
            }
            if (this.sharpness == 0)
            {
                return false;
            }
            if (this.thickness == 0)
            {
                return false;
            }
            return true;
        }//end  



	public String  toString (){
		return "ASFontAdvProperties[" 
			+ "embedFonts : " + embedFonts 
			+ ", antiAliasType : " + antiAliasType 
			+ ", gridFitType : " + gridFitType 
			+ ", sharpness : " + sharpness 
			+ ", thickness : " + thickness 
			+ "]";
	}
}


