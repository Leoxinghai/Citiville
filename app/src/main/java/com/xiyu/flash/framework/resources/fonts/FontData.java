package com.xiyu.flash.framework.resources.fonts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

//import flash.utils.Dictionary;
    public class FontData {

        public int mPointSize ;

        public int  charWidthKern (int charCode ,int prevCode ){
            CharData aData ;
            if (this.layerList.length() == 0)
            {
                return (0);
            };
            int aCharWidth =0;
            int aNumLayers =this.layerList.length() ;
            int i =0;
            while (i < aNumLayers)
            {
            	aData=((FontLayer)this.layerList.elementAt(i)).getCharData(charCode);
                aCharWidth = (((aCharWidth)<aData.mWidth) ? aData.mWidth : aCharWidth);
                i++;
            };
            return (aCharWidth);
        }

        public Dictionary layerMap ;
        public Array layerList ;

        public int  getAscent (){
            FontLayer layer ;
            int maxVal =0;
			for(int i =0; i<this.layerList.length();i++)
			{
				layer = (FontLayer)this.layerList.elementAt(i);
                maxVal = (((layer.mAscent)>maxVal) ? layer.mAscent : maxVal);
            };
            return (maxVal);
        }
        public int  getDescent (){
            return (0);
        }
        public int  charWidth (int charCode ){
            return (this.charWidthKern(charCode, 0));
        }
        public int  stringImageWidth (String iStr ){
            int aCode ;
            int aMaxXPos ;
            int numLayers ;
            int j =0;
            FontLayer layer ;
            CharData aData ;
            int aLayerXPos ;
            int aSpacing ;
            int aCharWidth ;
            int anImageX ;
            int anImageY ;
            return 20;
            /*
            FontLayer baseLayer=(FontLayer)this.layerList.elementAt(0);
            int aWidth =0;
            int aCurXPos =0;
            int numChars =iStr.length() ;
            int i =0;
            while (i < numChars)
            {
                aCode = iStr.charAt(i);
                aMaxXPos = aCurXPos;
                numLayers = this.layerList.length();
                j = 0;
                while (j < numLayers)
                {
                	layer=(FontLayer)this.layerList.elementAt(j);
                    aData = layer.getCharData(aCode);
                    aLayerXPos = aCurXPos;
                    aSpacing = 0;
                    aCharWidth = aData.mWidth;
                    anImageX = ((aLayerXPos + baseLayer.mOffset.x) + aData.mOffset.x);
                    anImageY = -(((layer.mAscent - layer.mOffset.y) - aData.mOffset.y));
                    if (aData.mImage != null)
                    {
                        aWidth = (anImageX + aData.mImage.width());
                    };
                    aLayerXPos = (aLayerXPos + (aCharWidth + aSpacing));
                    if (aLayerXPos > aMaxXPos)
                    {
                        aMaxXPos = aLayerXPos;
                    };
                    j++;
                };
                aCurXPos = aMaxXPos;
                i++;
            };
            return (aWidth);
            */
        }
        public int  getHeight (){
            FontLayer layer ;
            int maxHeight =0;
			for(int i =0; i<this.layerList.length();i++)
			{
				layer = (FontLayer)this.layerList.elementAt(i);
                maxHeight = (((layer.height())>maxHeight) ? layer.height() : maxHeight);
            };
            return (maxHeight);
        }
        private int getMappedChar (int charCode ){
            return (0);
        }
        public int getAscentPadding (){
            return (this.mAscentPadding);
        }

        private int mAscent =19;

        public int getLineSpacingOffset (){
            return (0);
        }

        private int mAscentPadding =0;
        private int mHeight ;

        public int  stringWidth (String iStr ){
            int aCode ;
            int aWidth =0;
            int aPrevCode =0;
            int aLen =iStr.length() ;
            int i =0;
            while (i < aLen)
            {
                aCode = iStr.charAt(i);
                aWidth = (aWidth + this.charWidthKern(aCode, aPrevCode));
                aPrevCode = aCode;
                i++;
            };
            return (aWidth);
        }
        public int  getLineSpacing (){
            FontLayer layer ;
            int maxVal =0;
			for(int i =0; i<this.layerList.length();i++)
			{
				layer = (FontLayer)this.layerList.elementAt(i);
                maxVal = (((layer.mSpacing)>maxVal) ? layer.mSpacing : maxVal);
            };
            return (maxVal);
        }

        private int mLineSpacingOffset =0;

        public  FontData (){
            this.layerList = new Array();
            this.layerMap = new Dictionary();
        }
    }


