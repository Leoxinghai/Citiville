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
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Point;
import com.xiyu.flash.framework.graphics.Color;

    public class ImageButtonWidget extends CWidget {

        public static  int BUTTON_LABEL_RIGHT =3;
        public static  int COLOR_BKG =5;
        public static  int BUTTON_LABEL_CENTER =2;
        public static  int COLOR_MEDIUM_OUTLINE =4;
        public static  int COLOR_DARK_OUTLINE =2;
        public static  int COLOR_LABEL_HILITE =1;
        public static  int COLOR_LABEL =0;
        public static  int COLOR_LIGHT_OUTLINE =3;
        public static  int BUTTON_LABEL_LEFT =1;

         public void  setDisabled (boolean isDisabled ){
            super.setDisabled(isDisabled);
            if (this.haveButtonImage(this.mDisabledImage, this.mDisabledRect))
            {
                markDirty(null);
            };
        }
         public void  onMouseUp (int x ,int y ){
            super.onMouseUp(x, y);
            if (((isOver) && (widgetManager.hasFocus)))
            {
                this.mButtonListener.buttonRelease(this.mId);
            };
            markDirty(null);
        }
        private boolean  haveButtonImage (ImageInst image ,Rectangle rect ){
            return (((!((image == null))) || (!((rect.width() == 0)))));
        }

        private int mOverAlphaFadeInSpeed ;

         public void  draw (Graphics2D g ){
            if ((((width == 0)) || ((height == 0))))
            {
                return;
            };
            if (!isDown)
            {
                if (disabled)
                {
                    g.drawImage(this.mDisabledImage, this.mDisableOffset.x, this.mDisableOffset.y);
                }
                else
                {
                    if ((((this.mOverAlpha > 0)) && (!((this.mOverImage == null)))))
                    {
                        if (((!((this.mUpImage == null))) && ((this.mOverAlpha < 1))))
                        {
                            g.drawImage(this.mUpImage, this.mUpOffset.x, this.mUpOffset.y);
                        };
                        g.drawImage(this.mOverImage, this.mOverOffset.x, this.mOverOffset.y);
                    }
                    else
                    {
                        if (((((isOver) || (isDown))) && (!((this.mOverImage == null)))))
                        {
                            g.drawImage(this.mOverImage, this.mOverOffset.x, this.mOverOffset.y);
                        }
                        else
                        {
                            if (this.mUpImage != null)
                            {
                                g.drawImage(this.mUpImage, this.mUpOffset.x, this.mUpOffset.y);
                            };
                        };
                    };
                };
            }
            else
            {
                if (this.mDownImage != null)
                {
                    g.drawImage(this.mDownImage, this.mDownOffset.x, this.mDownOffset.y);
                }
                else
                {
                    if (this.mOverImage != null)
                    {
                        g.drawImage(this.mOverImage, this.mOverOffset.x, this.mOverOffset.y);
                    }
                    else
                    {
                        g.drawImage(this.mUpImage, this.mUpOffset.x, this.mUpOffset.y);
                    };
                };
            };
        }

        private Rectangle mDisabledRect ;

         public void  onMouseDown (int x ,int y ){
            super.onMouseDown(x, y);
            this.mButtonListener.buttonPress(this.mId);
            markDirty(null);
        }

        private int mOverAlphaSpeed ;
        public ImageInst mDisabledImage ;
        public ImageInst mUpImage ;
        public Point mOverOffset ;
        public Point mUpOffset ;

         public boolean  contains (int x ,int y ){
            ImageInst targetImg ;
            if (isOver)
            {
                targetImg = this.mOverImage;
            }
            else
            {
                if (isDown)
                {
                    targetImg = this.mDownImage;
                }
                else
                {
                    if (disabled)
                    {
                        targetImg = this.mDisabledImage;
                    }
                    else
                    {
                        targetImg = this.mUpImage;
                    };
                };
            };
            if (targetImg == null)
            {
                return (false);
            };
            int localX =(int)(x -this.x );
            int localY =(int)(y -this.y );
            if (localX < 0)
            {
                return (false);
            };
            if (localY < 0)
            {
                return (false);
            };
            if (localX > (targetImg.width() - 1))
            {
                return (false);
            };
            if (localY > (targetImg.height() - 1))
            {
                return (false);
            };
            return true;
//            int color =targetImg.pixels().getPixel32(localX ,localY );
//            int alpha =((color & 0xFF000000) >> 24);
//            return ((alpha > 0));
        }
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
            if (((((isDown) || (this.haveButtonImage(this.mOverImage, this.mOverRect)))) )) //|| (!((colors.get(COLOR_LABEL_HILITE) == colors.get(COLOR_LABEL))))))
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

        public Point mDownOffset ;
        private Rectangle mOverRect ;
        private int mId ;
        private IButtonListener mButtonListener ;
        public ImageInst mDownImage ;
        public Point mDisableOffset ;

         public void  onMouseEnter (){
            super.onMouseEnter();
            if ((((this.mOverAlphaFadeInSpeed == 0)) && ((this.mOverAlpha > 0))))
            {
                this.mOverAlpha = 0;
            };
            if ((((isDown || this.haveButtonImage(this.mOverImage, this.mOverRect) )) ))//|| (!((colors.get(COLOR_LABEL_HILITE) == colors.get(COLOR_LABEL))))))
            {
                markDirty(null);
            };
//            Color aColor= (Color)colors.elementAt(COLOR_LABEL_HILITE);
            this.mButtonListener.buttonMouseEnter(this.mId);
        }

        private Rectangle mDownRect ;

         public void  onMouseMove (int x ,int y ){
            super.onMouseMove(x, y);
            this.mButtonListener.buttonMouseMove(this.mId, x, y);
        }

        private Rectangle mNormalRect ;
        public ImageInst mOverImage ;
        private int mOverAlpha ;

        public boolean  isButtonDown (){
            return (((((isDown) && (isOver))) && (!(disabled))));
        }

        public  ImageButtonWidget (int id ,IButtonListener listener ){
            super();
            this.mUpOffset = new Point();
            this.mOverOffset = new Point();
            this.mDownOffset = new Point();
            this.mDisableOffset = new Point();
            this.mId = id;
            this.mUpImage = null;
            this.mOverImage = null;
            this.mDownImage = null;
            this.mDisabledImage = null;
            this.mButtonListener = listener;
            this.mNormalRect = new Rectangle(0, 0, 0, 0);
            this.mOverRect = new Rectangle(0, 0, 0, 0);
            this.mDownRect = new Rectangle(0, 0, 0, 0);
            this.mDisabledRect = new Rectangle(0, 0, 0, 0);
            this.mOverAlpha = 0;
            this.mOverAlphaSpeed = 0;
            this.mOverAlphaFadeInSpeed = 0;
            doFinger = true;
        }
    }


