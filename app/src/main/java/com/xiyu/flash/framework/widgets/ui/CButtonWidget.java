package com.xiyu.flash.framework.widgets.ui;
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

import com.xiyu.flash.framework.widgets.CWidget;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;

    public class CButtonWidget extends CWidget {

        public static  int BUTTON_LABEL_RIGHT =3;
        public static  int COLOR_BKG =5;
        public static  int BUTTON_LABEL_CENTER =2;
        public static  int COLOR_MEDIUM_OUTLINE =4;
        public static  int COLOR_DARK_OUTLINE =2;
        public static  int COLOR_LABEL_HILITE =1;
        public static  int COLOR_LABEL =0;
        public static  int COLOR_LIGHT_OUTLINE =3;
        public static  int BUTTON_LABEL_LEFT =1;

        private ImageInst mOverImage ;

         public void  onMouseMove (int x ,int y ){
            super.onMouseMove(x, y);
            this.mButtonListener.buttonMouseMove(this.mId, x, y);
        }

        private String mLabel ;
        private int mLabelJustify ;

        private void  drawFrameUp (Graphics2D g ){
        	Color color = (Color)colors.elementAt(COLOR_BKG);
        	g.fillRect(0,0,width,height,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
        	g.fillRect(0,0,(width-1),1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
        	g.fillRect(0,0,1,(height-1),color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
        	g.fillRect(0,(height-1),width,1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
        	g.fillRect((width-1),0,1,height,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
        	g.fillRect(1,(height-2),(width-2),1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
        	g.fillRect((width-2),1,1,(height-2),color.red<<4 |color.green<<2|color.blue);
        	Color aColor=(Color)colors.elementAt(COLOR_LABEL);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
            this.mTextImage.draw(g);
        }

        private int mOverAlpha ;
        private Rectangle mDisabledRect ;
        private boolean mInverted ;

        public void  font (FontInst value ){
            this.mFont = value;
            this.mTextImage.font(value);
            this.refreshFrameImages();
        }

         public void  setColor (int index ,Color color ){
            super.setColor(index, color);
            Color aColor=(Color)colors.elementAt(COLOR_LABEL);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
        }
        private void  drawFrameDisabled (Graphics2D g ){
        	Color color = (Color)colors.elementAt(COLOR_BKG);
        	g.fillRect(0,0,width,height,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
        	g.fillRect(0,0,(width-1),1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
        	g.fillRect(0,0,1,(height-1),color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
        	g.fillRect(0,(height-1),width,1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
        	g.fillRect((width-1),0,1,height,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
        	g.fillRect(1,(height-2),(width-2),1,color.red<<4 |color.green<<2|color.blue);
        	color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
        	g.fillRect((width-2),1,1,(height-2),color.red<<4 |color.green<<2|color.blue);
        	Color aColor=(Color)colors.elementAt(COLOR_LABEL);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
            this.mTextImage.draw(g);
        }

        private boolean mBtnNoDraw ;
        private Rectangle mNormalRect ;
        private Rectangle mDownRect ;

         public void  resize (int x ,int y ,int width ,int height ){
            super.resize(x, y, width, height);
            this.mTextImage.setBounds(0, 0, width, height);
            this.refreshFrameImages();
        }
        public boolean  isButtonDown (){
            return (((((isDown) && (isOver))) && (!(disabled))));
        }

        private StringRenderable mTextImage ;

         public void  draw (Graphics2D g ){
            if (this.mBtnNoDraw)
            {
                return;
            };
            if ((((width == 0)) || ((height == 0))))
            {
                return;
            };
            boolean down =this.isButtonDown ();
            if ((((this.mUpImage == null)) && ((this.mDownImage == null))))
            {
                this.refreshFrameImages();
            };
            if (!isDown)
            {
                if (disabled)
                {
                    g.drawImage(this.mDisabledImage, 0, 0);
                }
                else
                {
                    if ((((this.mOverAlpha > 0)) && (this.haveButtonImage(this.mOverImage, this.mOverRect))))
                    {
                        if (((this.haveButtonImage(this.mUpImage, this.mNormalRect)) && ((this.mOverAlpha < 1))))
                        {
                            g.drawImage(this.mUpImage, 0, 0);
                        };
                        g.drawImage(this.mOverImage, 0, 0);
                    }
                    else
                    {
                        if (((((isOver) || (isDown))) && (this.haveButtonImage(this.mOverImage, this.mOverRect))))
                        {
                            g.drawImage(this.mOverImage, 0, 0);
                        }
                        else
                        {
                            if (this.haveButtonImage(this.mUpImage, this.mNormalRect))
                            {
                                g.drawImage(this.mUpImage, 0, 0);
                            };
                        };
                    };
                };
            }
            else
            {
                if (this.haveButtonImage(this.mDownImage, this.mDownRect))
                {
                    g.drawImage(this.mDownImage, 0, 0);
                }
                else
                {
                    if (this.haveButtonImage(this.mOverImage, this.mOverRect))
                    {
                        g.drawImage(this.mOverImage, 0, 0);
                    }
                    else
                    {
                        g.drawImage(this.mUpImage, 0, 0);
                    };
                };
            };
        }

        private boolean mFrameNoDraw ;

        private boolean  haveButtonImage (ImageInst image ,Rectangle rect ){
            return (((!((image == null))) || (!((rect.width() == 0)))));
        }

        private int mOverAlphaFadeInSpeed ;

        private void  drawFrameDown (Graphics2D g ){
        	Color color = (Color)colors.elementAt(COLOR_BKG);
			g.fillRect(0,0,width,height,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
			g.fillRect(0,0,(width-1),1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
			g.fillRect(0,0,1,(height-1),color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
			g.fillRect(0,(height-1),width,1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
			g.fillRect((width-1),0,1,height,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
			g.fillRect(1,1,(width-3),1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
			g.fillRect(1,1,1,(height-3),color.red<<4 |color.green<<2|color.blue);
            g.translate(1, 1);
            Color aColor=(Color)colors.elementAt(COLOR_LABEL_HILITE);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
            this.mTextImage.draw(g);
        }

        public FontInst  font (){
            return (this.mFont);
        }

        private int mOverAlphaSpeed ;
        private ImageInst mDisabledImage ;
        private ImageInst mUpImage ;

         public void  onMouseDown (int x ,int y ){
            super.onMouseDown(x, y);
            this.mButtonListener.buttonPress(this.mId);
            markDirty(null);
        }
         public void  onMouseUp (int x ,int y ){
            super.onMouseUp(x, y);
            if (((isOver) && (widgetManager.hasFocus)))
            {
                this.mButtonListener.buttonRelease(this.mId);
            };
            markDirty(null);
        }

        private IButtonListener mButtonListener ;
        private FontInst mFont ;

         public void  onMouseLeave (){
            super.onMouseLeave();
            if ((((this.mOverAlphaSpeed == 0)) && ((this.mOverAlpha > 0))))
            {
                this.mOverAlpha = 0;
            }
            else
            {
                if ((((this.mOverAlphaSpeed > 0)) && ((this.mOverAlpha == 0))))
                {
                    this.mOverAlpha = 1;
                };
            };
            if (isDown || this.haveButtonImage(this.mOverImage, this.mOverRect)) //|| (!((colors.get(COLOR_LABEL_HILITE) == colors.get(COLOR_LABEL))))))
            {
                markDirty(null);
            };
            this.mButtonListener.buttonMouseLeave(this.mId);
        }
         public void  update (){
            super.update();
            markDirty(null);
            if (((isDown) && (isOver)))
            {
                this.mButtonListener.buttonDownTick(this.mId);
            };
            if (((((!(isDown)) && (!(isOver)))) && ((this.mOverAlpha > 0))))
            {
                if (this.mOverAlphaSpeed > 0)
                {
                    this.mOverAlpha = (this.mOverAlpha - this.mOverAlphaSpeed);
                    if (this.mOverAlpha < 0)
                    {
                        this.mOverAlpha = 0;
                    };
                }
                else
                {
                    this.mOverAlpha = 0;
                };
                markDirty(null);
            }
            else
            {
                if (((((isOver) && ((this.mOverAlphaFadeInSpeed > 0)))) && ((this.mOverAlpha < 1))))
                {
                    this.mOverAlpha = (this.mOverAlpha + this.mOverAlphaFadeInSpeed);
                    if (this.mOverAlpha > 1)
                    {
                        this.mOverAlpha = 1;
                    };
                    markDirty(null);
                };
            };
        }

        private Rectangle mOverRect ;

        public void  label (String value ){
            this.mLabel = value;
            this.mTextImage.text(value);
            this.refreshFrameImages();
        }

        private void  refreshFrameImages (){
            if ((((width == 0)) || ((height == 0))))
            {
                return;
            };
            this.mUpImage = new ImageInst(new ImageData(new BitmapData(width, height, true, 0)));
            Graphics2D gUp =new Graphics2D(this.mUpImage.pixels() );
            this.drawFrameUp(gUp);
            this.mOverImage = new ImageInst(new ImageData(new BitmapData(width, height, true, 0)));
            Graphics2D gOver =new Graphics2D(this.mOverImage.pixels());
            this.drawFrameOver(gOver);
            this.mDownImage = new ImageInst(new ImageData(new BitmapData(width, height, true, 0)));
            Graphics2D gDown =new Graphics2D(this.mDownImage.pixels() );
            this.drawFrameDown(gDown);
            this.mDisabledImage = new ImageInst(new ImageData(new BitmapData(width, height, true, 0)));
            Graphics2D gDisabled =new Graphics2D(this.mDisabledImage.pixels() );
            this.drawFrameDisabled(gDisabled);
        }

        private ImageInst mDownImage ;
        private int mId ;

         public void  setColors (Array theColors ){
            super.setColors(theColors);
            Color aColor=(Color)colors.elementAt(COLOR_LABEL);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
        }

        public String  label (){
            return (this.mLabel);
        }

         public void  onMouseEnter (){
            super.onMouseEnter();
            if ((((this.mOverAlphaFadeInSpeed == 0)) && ((this.mOverAlpha > 0))))
            {
                this.mOverAlpha = 0;
            };
            if (isDown || this.haveButtonImage(this.mOverImage, this.mOverRect)) //|| (!((colors.get(COLOR_LABEL_HILITE) == colors.get(COLOR_LABEL))))))
            {
                markDirty(null);
            };
            Color aColor=(Color)colors.elementAt(COLOR_LABEL_HILITE);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
            this.mButtonListener.buttonMouseEnter(this.mId);
        }
        private void  drawFrameOver (Graphics2D g ){
        	Color color = (Color)colors.elementAt(COLOR_BKG);
			g.fillRect(0,0,width,height,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
			g.fillRect(0,0,(width-1),1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_LIGHT_OUTLINE);
			g.fillRect(0,0,1,(height-1),color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
			g.fillRect(0,(height-1),width,1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_DARK_OUTLINE);
			g.fillRect((width-1),0,1,height,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
			g.fillRect(1,(height-2),(width-2),1,color.red<<4 |color.green<<2|color.blue);
			color = (Color)colors.elementAt(COLOR_MEDIUM_OUTLINE);
			g.fillRect((width-2),1,1,(height-2),color.red<<4 |color.green<<2|color.blue);
			Color aColor=(Color)colors.elementAt(COLOR_LABEL_HILITE);
            this.mTextImage.setColor(aColor.alpha, aColor.red, aColor.green, aColor.blue);
            this.mTextImage.draw(g);
        }
         public void  setDisabled (boolean isDisabled ){
            super.setDisabled(isDisabled);
            if (this.haveButtonImage(this.mDisabledImage, this.mDisabledRect))
            {
                markDirty(null);
            };
        }

        public  CButtonWidget (int id ,IButtonListener listener ){
            this.mId = id;
            this.mFont = null;
            this.mLabelJustify = BUTTON_LABEL_CENTER;
            this.mUpImage = null;
            this.mOverImage = null;
            this.mDownImage = null;
            this.mDisabledImage = null;
            this.mInverted = false;
            this.mBtnNoDraw = false;
            this.mFrameNoDraw = false;
            this.mButtonListener = listener;
            this.mNormalRect = new Rectangle(0, 0, 0, 0);
            this.mOverRect = new Rectangle(0, 0, 0, 0);
            this.mDownRect = new Rectangle(0, 0, 0, 0);
            this.mDisabledRect = new Rectangle(0, 0, 0, 0);
            hasAlpha = true;
            this.mOverAlpha = 0;
            this.mOverAlphaSpeed = 0;
            this.mOverAlphaFadeInSpeed = 0;
            this.mTextImage = new StringRenderable(0);
            this.setColor(COLOR_LABEL, Color.RGB(0, 0, 0));
            this.setColor(COLOR_LABEL_HILITE, Color.RGB(0, 0, 0));
            this.setColor(COLOR_DARK_OUTLINE, Color.RGB(0, 0, 0));
            this.setColor(COLOR_LIGHT_OUTLINE, Color.RGB(1, 1, 1));
            this.setColor(COLOR_MEDIUM_OUTLINE, Color.RGB((132 / 0xFF), (132 / 0xFF), (132 / 0xFF)));
            this.setColor(COLOR_BKG, Color.RGB((212 / 0xFF), (212 / 0xFF), (212 / 0xFF)));
            doFinger = true;
        }
    }


