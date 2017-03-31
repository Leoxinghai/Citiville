package com.xiyu.flash.framework.utils;
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

import android.graphics.Point;

import com.xiyu.util.Array;
import com.xiyu.util.*;

import com.xiyu.flash.framework.resources.fonts.FontInst;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Point;
//import flash.geom.Matrix;
import java.util.Vector;
import java.util.*;

    public class Utils {

        public static  int ALIGN_LEFT =-1;
        public static  int ALIGN_RIGHT =1;
        public static  int ALIGN_BOTTOM =1;

        private static Array  splitText (String str ,FontInst font ,Rectangle bounds ){
            String aLine ;
            int aLineWidth ;
            String aLineFragment ;
            Array words ;
            int aNumWords ;
            int j =0;
            String aWord ;
            int aWidth ;
            Array results =new Array ();
            if ((((bounds.width() == 0)) || ((bounds.height() == 0))))
            {
                return (results);
            };
            if ((((str == null)) || ((str.length() == 0))))
            {
                return (results);
            };
            int aSpaceWidth =(int)font.stringWidth(" ");
            int aLineNumber =0;

            String[] strLines =str.split ("/[ \n\r]+/");
            Array lines = new Array(strLines); 
            int aNumLines =lines.length() ;
            int i =0;
            while (i < aNumLines)
            {
            	aLine=(String)lines.elementAt(i);
                aLineWidth = 0;
                aLineFragment = "";
                String[] strWords = aLine.split("/[ \t]+/");
                words = new Array(strWords);
                aNumWords = words.length();
                j = 0;
                while (j < aNumWords)
                {
                	aWord=(String)words.elementAt(j);
                    aWidth = (int)font.stringWidth(aWord);
                    if ((aLineWidth + aWidth) < bounds.width())
                    {
                        if (aLineFragment.length() == 0)
                        {
                            aLineFragment = aWord;
                        }
                        else
                        {
                            aLineFragment = ((aLineFragment + " ") + aWord);
                        };
                        aLineWidth = (aLineWidth + aWidth);
                    }
                    else
                    {
                    	results.add(aLineNumber,aLineFragment);
                        aLineNumber = (aLineNumber + 1);
                        aLineFragment = aWord;
                        aLineWidth = aWidth;
                    };
                    aLineWidth = (aLineWidth + aSpaceWidth);
                    j++;
                };
                
                results.add(aLineNumber,aLineFragment);
                aLineNumber = (aLineNumber + 1);
                aLineFragment = "";
                aLineWidth = 0;
                i++;
            };
            return (results);
        }

        public static  int JUSTIFY_RIGHT =1;

        public static ImageInst  createStringImage (String str ,FontInst font ,Rectangle bounds ,int justification ){
            String aLine ;
            int aLineWidth ;
            int x ;
            int y ;
            Array lines =splitText(str ,font ,bounds );
            if (lines.length() == 0)
            {
                return (null);
            };
            int aImgWidth =0;
            int aImgHeight =0;
            int i =0;
            int aNumLines =lines.length() ;
            i = 0;
            while (i < aNumLines)
            {
            	aLine=(String)lines.elementAt(i);
                aLineWidth = (int)font.stringImageWidth(aLine);
                aImgWidth = (((aImgWidth)<aLineWidth) ? aLineWidth : aImgWidth);
                i++;
            };
            if (aImgWidth == 0)
            {
        		Bitmap.Config config = Bitmap.Config.ALPHA_8;
        		Bitmap bitmap = Bitmap.createBitmap((int)40,(int)22, config);
                return new ImageInst(new ImageData(new BitmapData(bitmap)));
            	//return (null);
            };
            aImgHeight = (int)font.getHeight();
            aImgHeight = (int)(aImgHeight + ((aNumLines - 1) * (font.getAscent() + font.getLineSpacing())));
            if (aImgHeight == 0)
            {
        		Bitmap.Config config = Bitmap.Config.ALPHA_8;
        		Bitmap bitmap = Bitmap.createBitmap((int)40,(int)22, config);
                return new ImageInst(new ImageData(new BitmapData(bitmap)));
            	//return (null);
            };
            ImageInst textImage =new ImageInst(new ImageData(new BitmapData(aImgWidth ,aImgHeight ,true ,0)));
            Graphics2D g =textImage.graphics();
            g.setFont(font);
            i = 0;
            while (i < aNumLines)
            {
            	aLine=(String)lines.elementAt(i);
                x = 0;
                y = 0;
                if (justification == JUSTIFY_LEFT)
                {
                    x = 0;
                }
                else
                {
                    if (justification == JUSTIFY_RIGHT)
                    {
                        x = (int)(aImgWidth - font.stringWidth(aLine));
                    }
                    else
                    {
                        x = ((aImgWidth >> 1) - ((int)(font.stringWidth(aLine)) >> 1));
                    };
                };
                y = (int)(i * (font.getAscent() + font.getLineSpacing()));
                g.drawString(aLine, x, y);
                i++;
            };
            return (textImage);
        }

        public static  int ALIGN_CENTER =0;
        public static  int ALIGN_NONE =-2;
        public static  int JUSTIFY_CENTER =0;

        public static void  align (Rectangle targetBounds ,Rectangle anchorBounds ,int hAlign,int vAlign){
            if (hAlign == ALIGN_LEFT)
            {
                targetBounds.left(anchorBounds.x);
            }
            else
            {
                if (hAlign == ALIGN_RIGHT)
                {
                    targetBounds.right(anchorBounds.x+anchorBounds.width);
                }
                else
                {
                    if (hAlign == ALIGN_CENTER)
                    {
                        targetBounds.x = ((anchorBounds.x + (anchorBounds.width() / 2)) - (targetBounds.width() / 2));
                    };
                };
            };
            if (vAlign == ALIGN_TOP)
            {
                targetBounds.top(anchorBounds.y);
            }
            else
            {
                if (vAlign == ALIGN_BOTTOM)
                {
                    targetBounds.bottom(anchorBounds.y+anchorBounds.height);
                }
                else
                {
                    if (vAlign == ALIGN_CENTER)
                    {
                        targetBounds.y = ((anchorBounds.y + (anchorBounds.height() / 2)) - (targetBounds.height() / 2));
                    };
                };
            };
        }
        public static ImageInst  createMergedImage (Array images ,Array sourceRects ,Array destPoints ){
            ImageInst img ;
            Rectangle src ;
            Point dest ;
            int l ;
            int r ;
            int t ;
            int b ;
            Matrix matrix =new Matrix ();
            Rectangle bounds =new Rectangle ();
            int len =images.length() ;
            int i =0;
            while (i < len)
            {
            	src=(Rectangle)sourceRects.elementAt(i);
            	dest=(Point)destPoints.elementAt(i);
                l = dest.x;
                r = (dest.x + src.width());
                t = dest.y;
                b = (dest.y + src.height());
                bounds.left((((l)<bounds.x) ? l : bounds.x));
                bounds.right((((r)>(bounds.x+bounds.width)) ? r : (bounds.x+bounds.width)));
                bounds.top ((((t)<bounds.y) ? t : bounds.y));
                bounds.bottom ((((b)>(bounds.y+bounds.height)) ? b : (bounds.y+bounds.height)));
                i++;
            };
            BitmapData merged =new BitmapData(bounds.width() ,bounds.height() ,true ,0);
            int k =0;
            while (k < len)
            {
            	img=(ImageInst)images.elementAt(k);
            	src=(Rectangle)sourceRects.elementAt(k);
            	dest=(Point)destPoints.elementAt(k);
                dest.x = (dest.x - bounds.x);
                dest.y = (dest.y - bounds.y);
                merged.copyPixels(img.pixels(), src, dest);
                k++;
            };
            return (new ImageInst(new ImageData(merged)));
        }

        public static  int ALIGN_TOP =-1;

        public static Vector getDigits (int input){
            String str = ""+input;
            Vector digits = new Vector();
            int len =str.length() ;
            int i =(len -1);
            while (i >= 0)
            {
                digits.add((int)(str.charAt(i)));
                i--;
            };
            return (digits);
        }

        public static  int JUSTIFY_LEFT =-1;

    }


