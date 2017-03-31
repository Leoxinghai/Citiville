package com.xiyu.flash.games.pvz.logic.UI;
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

import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZFonts;

    public class ToolTipWidget {

        public static final int TOOLTIP_LINE_SPACING =2;
        public static final int TOOLTIP_SIDE_SPACING =5;
        public static final int TOOLTIP_TOP_SPACING =3;

        public void  SetWarningText (String theWarningText ){
            if (theWarningText == "")
            {
                this.mWarningText = "";
            }
            else
            {
                this.mWarningText = this.app.stringManager().translateString(theWarningText);
            };
            this.CalculateSize();
        }

        public FontInst mFontBold ;
        public int mMaxBottom ;
        public boolean mVisible ;
        public String mLabel ;
        public int mY ;
        public int mGetsLinesWidth ;
        public int mX ;
        public int mMinLeft ;
        public ImageInst mToolTipImage ;
        public int mWidth ;
        public FontInst mFont ;
        public PVZApp app ;
        public boolean mCenter ;

        public void  Draw (Graphics2D g ){
            int x =0;
            int y =0;
            int aFontWidth ;
            Color aWarningColor ;
            String aLine ;
            if (!this.mVisible)
            {
                return;
            };
            if (this.mVisible)
            {
                return;
            };
            int aPosX =this.mX ;
            if (this.mCenter)
            {
                aPosX = (aPosX - (this.mWidth / 2));
            };
            if (aPosX < (this.mMinLeft - g.state.affineMatrix.tx))
            {
                aPosX = (this.mMinLeft - (int)(g.state.affineMatrix.tx));
            }
            else
            {
                if (((aPosX + this.mWidth) + g.state.affineMatrix.tx) > Board.BOARD_WIDTH)
                {
                    aPosX = ((Board.BOARD_WIDTH - this.mWidth) - (int)(g.state.affineMatrix.tx));
                };
            };
            int aPosY =this.mY ;
            if (aPosY < -(g.state.affineMatrix.ty))
            {
                aPosY = (int)(-(g.state.affineMatrix.ty));
            }
            else
            {
                if (((aPosY + this.mHeight) + g.state.affineMatrix.ty) > this.mMaxBottom)
                {
                    aPosY = ((this.mMaxBottom - this.mHeight) - (int)(g.state.affineMatrix.ty));
                };
            };
            if (this.mX > 540)
            {
                aPosX = (int)(aPosX + this.mBoard.x);
            };
            g.fillRect((aPosX - 1), (aPosY - 1), (this.mWidth + 2), (this.mHeight + 2), 0xFF000000);
            g.fillRect(aPosX, aPosY, this.mWidth, this.mHeight, 0xFFFFFFC8);
            if (this.mX > 540)
            {
                aPosX = (int)(aPosX - this.mBoard.x);
            };
            aPosY = (aPosY + (TOOLTIP_TOP_SPACING - 2));
            if (this.mTitle.length() > 0)
            {
                g.setFont(this.mFontBold);
                aFontWidth = (int)this.mFontBold.stringWidth(this.mTitle);
                x = (aPosX + ((this.mWidth - aFontWidth) / 2));
                y = aPosY;
                g.drawString(this.mTitle, x, y);
                aPosY = (int)(aPosY + (this.mFontBold.getAscent() + TOOLTIP_LINE_SPACING));
            };
            if (this.mWarningText.length() > 0)
            {
                g.setFont(this.mFont);
                aFontWidth = (int)this.mFont.stringWidth(this.mWarningText);
                x = (aPosX + ((this.mWidth - aFontWidth) / 2));
                y = aPosY;
                aWarningColor = Color.RGB(1, 0, 0);
                if (this.mWarningFlashCounter > 0)
                {
                    if ((this.mWarningFlashCounter % 20) < 10)
                    {
                        aWarningColor = Color.RGB(0, 0, 0);
                    };
                };
                this.mFont.setColor(aWarningColor.alpha, aWarningColor.red, aWarningColor.green, aWarningColor.blue);
                g.drawString(this.mWarningText, x, y);
                this.mFont.setColor(1, 0, 0, 0);
                aPosY = (int)(aPosY + (this.mFont.getAscent() + TOOLTIP_LINE_SPACING));
            };
            Array aLines =new Array ();
            this.GetLines(aLines);
            g.setFont(this.mFont);
            int i =0;
            while (i < aLines.length())
            {
            	aLine=(String)aLines.elementAt(i);
                aFontWidth = (int)this.mFont.stringWidth(aLine);
                x = (aPosX + ((this.mWidth - aFontWidth) / 2));
                y = aPosY;
                g.drawString(aLine, x, y);
                aPosY = (int)(aPosY + (this.mFont.getAscent() + TOOLTIP_LINE_SPACING));
                i++;
            };
        }
        public void  FlashWarning (){
            this.mWarningFlashCounter = 70;
        }
        public void  CalculateSize (){
            int aWidth ;
            Array aLines =new Array ();
            int aMaxWidth =(int)this.mFontBold.stringWidth(this.mTitle );
            int aWarningWidth =(int)this.mFont.stringWidth(this.mWarningText );
            aMaxWidth = Math.max(aWarningWidth, aMaxWidth);
            this.mGetsLinesWidth = Math.max(130, (aMaxWidth - 30));
            this.GetLines(aLines);
            int i =0;
            while (i < aLines.length())
            {
            	aWidth=(int)this.mFont.stringWidth((String)aLines.elementAt(i));
                aMaxWidth = Math.max(aMaxWidth, aWidth);
                i++;
            };
            int aHeight =(TOOLTIP_TOP_SPACING *2);
            if (this.mTitle.length() > 0)
            {
                aHeight = (int)(aHeight + (this.mFont.getAscent() + TOOLTIP_LINE_SPACING));
            };
            if (this.mWarningText.length() > 0)
            {
                aHeight = (int)(aHeight + (this.mFont.getAscent() + TOOLTIP_LINE_SPACING));
            };
            aHeight = (int)(aHeight + (this.mFont.getAscent() * aLines.length()));
            aHeight = (aHeight + (TOOLTIP_LINE_SPACING * (aLines.length() - 1)));
            aMaxWidth = (aMaxWidth + (TOOLTIP_SIDE_SPACING * 2));
            this.mWidth = aMaxWidth;
            this.mHeight = aHeight;
        }
        public void  SetLabel (String theLabel ){
            if (theLabel == "")
            {
                this.mLabel = "";
            }
            else
            {
                this.mLabel = this.app.stringManager().translateString(theLabel);
            };
            this.CalculateSize();
        }

        public String mTitle ;

        public void  Update (){
            if (this.mWarningFlashCounter > 0)
            {
                this.mWarningFlashCounter--;
            };
        }
        public void  GetLines (Array theLines ){
            String aWord ;
            int aWidth ;
            if (this.mLabel == "")
            {
                return;
            };
            int aIndexStart ;
            int aIndexCur ;
            int aCurWidth ;
            Array theWords = new Array(this.mLabel.split(" "));
            int numWords =theWords.length() ;
            int aSpaceWidth =(int)this.mFont.stringWidth(" ");
            int aLineNumber =0;
            int aLineWidth =0;
            String aLineFragment ="";
            int i =0;
            while (i < numWords)
            {
            	aWord=(String)theWords.elementAt(i);
                aWidth = (int)this.mFont.stringWidth(aWord);
                if ((aLineWidth + aWidth) < this.mGetsLinesWidth)
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
                	theLines.add(aLineNumber,aLineFragment);
                    aLineNumber = (aLineNumber + 1);
                    aLineFragment = aWord;
                    aLineWidth = aWidth;
                };
                aLineWidth = (aLineWidth + aSpaceWidth);
                i++;
            };
            theLines.add(aLineNumber,aLineFragment);
        }

        public int mHeight ;
        public Board mBoard ;
        public int mWarningFlashCounter ;
        public String mWarningText ;

        public void  SetTitle (String theTitle ){
            if (theTitle == "")
            {
                this.mTitle = "";
            }
            else
            {
                this.mTitle = this.app.stringManager().translateString(theTitle);
            };
            this.CalculateSize();
        }

        public  ToolTipWidget (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mX = 0;
            this.mY = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mVisible = true;
            this.mCenter = false;
            this.mMinLeft = 0;
            this.mMaxBottom = Board.BOARD_HEIGHT;
            this.mGetsLinesWidth = 0;
            this.mWarningFlashCounter = 0;
            this.mFont = app.fontManager().getFontInst(PVZFonts.FONT_PICO129);
            this.mFont.setColor(1, 0, 0, 0);
            this.mFontBold = app.fontManager().getFontInst(PVZFonts.FONT_PIX118BOLD);
            this.mFontBold.setColor(1, 0, 0, 0);
            this.mLabel = "";
            this.mTitle = "";
            this.mWarningText = "";
        }
    }


