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
//import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.*;

import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Rectangle;
//import flash.geom.ColorTransform;

    public class FontInst {

        public double  getAscentPadding (){
            return ((this.mData.getAscentPadding() * this.mScale));
        }
        public double  stringImageWidth (String str ){
            return ((this.mData.stringImageWidth(str) * this.mScale));
        }
        public double  getDescent (){
            return ((this.mData.getDescent() * this.mScale));
        }
        public void  setColor (double a ,double r ,double g ,double b ){
            this.mColorTransform.alphaMultiplier = (int)a;
            this.mColorTransform.redMultiplier = (int)r;
            this.mColorTransform.greenMultiplier = (int)g;
            this.mColorTransform.blueMultiplier = (int)b;
        }

        public double  scale (){
            return (this.mScale);
        }

        public void  draw (Graphics2D g ,int x ,int y ,String str ,Rectangle clipRect ){
            FontLayer layer ;
            int aCurXPos ;
            int numChars ;
            int i =0;
            int aCode ;
            CharData aData ;
            int aMaxXPos ;
            int aLayerXPos ;
            int aSpacing ;
            int aCharWidth ;
            int anImageX ;
            int anImageY ;
            Color aLayerColor ;
            ImageInst aImage ;
            FontLayer baseLayer=(FontLayer)this.mData.layerList.elementAt(0);
            Matrix wordTransform =g.getTransform ();
            Matrix charTransform =new Matrix ();
            g.identity();
            int numLayers =this.mData.layerList.length() ;
            int j =0;
            while (j < numLayers)
            {
            	layer=(FontLayer)this.mData.layerList.elementAt(j);
                aCurXPos = 0;
                numChars = str.length();
                i = 0;
                while (i < numChars)
                {
                    aCode = str.charAt(i);
                    aData = layer.getCharData(aCode);
                    aMaxXPos = aCurXPos;
                    aLayerXPos = aCurXPos;
                    aSpacing = 0;
                    aCharWidth = aData.mWidth;
                    anImageX = ((aLayerXPos + baseLayer.mOffset.x) + aData.mOffset.x);
                    anImageY = -(((layer.mAscent - layer.mOffset.y) - aData.mOffset.y));
                    if (aData.mImage != null)
                    {
                        aLayerColor = Color.fromInt(layer.mColorMult);
                        aImage = aData.mImage;
                        aImage.setColor((this.mColorTransform.alphaMultiplier * aLayerColor.alpha), (this.mColorTransform.redMultiplier * aLayerColor.red), (this.mColorTransform.greenMultiplier * aLayerColor.green), (this.mColorTransform.blueMultiplier * aLayerColor.blue));
                        aImage.useColor = true;
                        charTransform.identity();
                        charTransform.translate(anImageX, 0);
                        charTransform.scale(this.mScale, this.mScale);
                        charTransform.concat(wordTransform);
                        g.setTransform(charTransform);
                        g.drawImage(aData.mImage, x, y);
                    };
                    aLayerXPos = (aLayerXPos + (aCharWidth + aSpacing));
                    if (aLayerXPos > aMaxXPos)
                    {
                        aMaxXPos = aLayerXPos;
                    };
                    aCurXPos = aMaxXPos;
                    i++;
                };
                j++;
            };
            g.setTransform(wordTransform);
        }
        public double  getAscent (){
            return ((this.mData.getAscent() * this.mScale));
        }

        public void  scale (double value ){
            this.mScale = value;
        }

        public double  getHeight (){
            return ((this.mData.getHeight() * this.mScale));
        }

        private ColorTransform mColorTransform ;
        private FontData mData ;

        public double  getLineSpacing (){
            return ((this.mData.getLineSpacing() * this.mScale));
        }
        public double  getLineSpacingOffset (){
            return ((this.mData.getLineSpacingOffset() * this.mScale));
        }
        public double  stringWidth (String str ){
            return ((this.mData.stringWidth(str) * this.mScale));
        }

        private double mScale ;

        public  FontInst (FontData data ){
            super();
            this.mColorTransform = new ColorTransform();
            this.mData = data;
            this.mScale = 1;
        }
    }


