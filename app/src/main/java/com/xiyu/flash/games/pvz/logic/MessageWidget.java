package com.xiyu.flash.games.pvz.logic;
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

import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.games.pvz.renderables.ImageRenderable;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.resources.fonts.FontInst;

    public class MessageWidget {

        public static final int MESSAGE_STYLE_HINT_TALL_UNLOCKMESSAGE =9;
        public static final int MESSAGE_STYLE_TUTORIAL_LATER_STAY =4;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL1 =0;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL2 =2;
        public static final int MESSAGE_STYLE_HINT_STAY =7;
        public static final int MESSAGE_STYLE_TUTORIAL_LATER =3;
        public static final int MESSAGE_STYLE_HINT_FAST =6;
        public static final int MESSAGE_STYLE_OFF =-1;
        public static final int MESSAGE_STYLE_BIG_MIDDLE =11;
        public static final int MESSAGE_STYLE_HUGE_WAVE =14;
        public static final int MESSAGE_STYLE_BIG_MIDDLE_FAST =12;
        public static final int MIN_MESSAGE_TIME =100;
        public static final int MESSAGE_STYLE_HINT_TALL_LONG =10;
        public static final int MESSAGE_STYLE_HINT_LONG =5;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY =1;
        public static final int SLIDE_OFF_TIME =100;
        public static final int MESSAGE_STYLE_HOUSE_NAME =13;
        public static final int MESSAGE_STYLE_HINT_TALL_FAST =8;

        public ImageInst  MakeGreyBox (int height ){
            int h =height ;
            int w =540;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            bufferG.fillRect(0, 0, w, h, 0x88000000);
            return (aBufferedImage);
        }

        public String mLabel ;
        public int mDisplayTime ;
        public int mMessageStyleNext ;

        public void  SetLabel (String newLabel ,int newStyle ){
            ImageInst anImage ;
            this.mText.text("");
            this.mSupplementalText.text("");
            if (this.mText.getIsDisposable())
            {
                this.mBoard.mRenderManager.add(this.mText);
            };
            if (this.mSupplementalText.getIsDisposable())
            {
                this.mBoard.mRenderManager.add(this.mSupplementalText);
            };
            if ((((newLabel.length() >= 3)) && ((newLabel.charAt(0) == '['))))
            {
                this.mLabel = this.app.stringManager().translateString(newLabel);
            }
            else
            {
                this.mLabel = newLabel;
            };
            this.mMessageStyle = newStyle;
            switch (this.mMessageStyle)
            {
                case MESSAGE_STYLE_HINT_LONG:
                case MESSAGE_STYLE_BIG_MIDDLE:
                case MESSAGE_STYLE_HINT_TALL_LONG:
                    this.mDuration = 1500;
                    break;
                case MESSAGE_STYLE_HINT_TALL_UNLOCKMESSAGE:
                    this.mDuration = 500;
                    break;
                case MESSAGE_STYLE_HINT_FAST:
                case MESSAGE_STYLE_HINT_TALL_FAST:
                case MESSAGE_STYLE_BIG_MIDDLE_FAST:
                case MESSAGE_STYLE_TUTORIAL_LEVEL1:
                case MESSAGE_STYLE_TUTORIAL_LEVEL2:
                case MESSAGE_STYLE_TUTORIAL_LATER:
                    this.mDuration = 500;
                    break;
                case MESSAGE_STYLE_HINT_STAY:
                case MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY:
                case MESSAGE_STYLE_TUTORIAL_LATER_STAY:
                    this.mDuration = 10000;
                    break;
                case MESSAGE_STYLE_HOUSE_NAME:
                    this.mDuration = 250;
                    break;
                case MESSAGE_STYLE_HUGE_WAVE:
                    this.mDuration = 750;
                    break;
            };
            this.mDisplayTime = this.mDuration;
            this.mBounds = new Rectangle(0, 0, 540, 405);
            switch (this.mMessageStyle)
            {
                case MESSAGE_STYLE_BIG_MIDDLE:
                    this.mBounds.y = 170;
                    this.mBounds.height = 100;
                    this.mFont.scale(0.8);
                    this.mText.justification(StringRenderable.JUSTIFY_CENTER);
                    break;
                case MESSAGE_STYLE_TUTORIAL_LEVEL1:
                case MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY:
                case MESSAGE_STYLE_TUTORIAL_LEVEL2:
                case MESSAGE_STYLE_TUTORIAL_LATER:
                case MESSAGE_STYLE_TUTORIAL_LATER_STAY:
                    this.mBounds.y = 270;
                    this.mBounds.height = 100;
                    this.mFont.scale(0.8);
                    this.mText.justification(StringRenderable.JUSTIFY_CENTER);
                    break;
                case MESSAGE_STYLE_HINT_STAY:
                case MESSAGE_STYLE_HINT_FAST:
                    this.mBounds.y = 350;
                    this.mBounds.height = 50;
                    this.mFont.scale(0.8);
                    this.mText.justification(StringRenderable.JUSTIFY_CENTER);
                    break;
                case MESSAGE_STYLE_HOUSE_NAME:
                    this.mBounds.y = 300;
                    this.mBounds.height = 55;
                    this.mFont.scale(0.8);
                    break;
                case MESSAGE_STYLE_HUGE_WAVE:
                    this.mBounds.x = 0;
                    this.mBounds.width = 540;
                    this.mFont.scale(0.8);
                    this.mText.justification(StringRenderable.JUSTIFY_CENTER);
                    break;
            };
            int aGreyBoxHeight =0;
            switch (this.mMessageStyle)
            {
                case MESSAGE_STYLE_TUTORIAL_LEVEL1:
                case MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY:
                case MESSAGE_STYLE_TUTORIAL_LEVEL2:
                case MESSAGE_STYLE_TUTORIAL_LATER:
                case MESSAGE_STYLE_TUTORIAL_LATER_STAY:
                case MESSAGE_STYLE_BIG_MIDDLE:
                    aGreyBoxHeight = 100;
                    break;
                case MESSAGE_STYLE_HINT_STAY:
                case MESSAGE_STYLE_HINT_FAST:
                    aGreyBoxHeight = 50;
                    break;
            };
            if ((((aGreyBoxHeight > 0)) && (this.mBoxRenderable.getIsDisposable())))
            {
                anImage = this.MakeGreyBox(aGreyBoxHeight);
                anImage.x(0);
                anImage.y(this.mBounds.y);
                this.mBoxRenderable.dead(false);
                this.mBoxRenderable = new ImageRenderable(anImage, Board.RENDER_LAYER_ABOVE_UI);
                this.mBoard.mRenderManager.add(this.mBoxRenderable);
            }
            else
            {
                if (aGreyBoxHeight == 0)
                {
                    this.mBoxRenderable.dead(true);
                };
            };
            this.mText.setBounds(this.mBounds.x, this.mBounds.y, this.mBounds.width(), this.mBounds.height());
            this.mSupplementalText.setBounds(this.mBounds.x, (this.mBounds.y + 40), 540, 40);
            this.mSupplementalText.dead(false);
            this.mSupplementalText.font(this.app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR16));
            this.mText.dead(false);
            this.mText.font(this.mFont);
            this.mText.text(this.mLabel);
        }
        public void  ClearLabel (){
            this.mDuration = 0;
            this.mText.text("");
            this.mText.dead(true);
            this.mSupplementalText.text("");
            this.mSupplementalText.dead(true);
            this.mBoxRenderable.dead(true);
        }

        public Reanimation mReanim ;
        public String mLabelNext ;
        public PVZApp app ;
        public FontInst mFont ;
        public Rectangle mBounds ;
        public int mDuration ;

        public void  Update (){
            if ((( this.mBoard ==null) || (this.mBoard.mPaused)))
            {
                return;
            };
            if ((((this.mDuration > 0)) && ((this.mDuration < 10000))))
            {
                this.mDuration--;
                if (this.mDuration == 0)
                {
                    this.mMessageStyle = MESSAGE_STYLE_OFF;
                    if (this.mMessageStyleNext != MESSAGE_STYLE_OFF)
                    {
                        this.SetLabel(this.mLabelNext, this.mMessageStyleNext);
                        this.mMessageStyleNext = MESSAGE_STYLE_OFF;
                    };
                };
            };
        }
        public void  Draw (Graphics2D g ){
            int aColor =0 ;
            double _local7 ;
            String aSubStr ;
            int aNumWavesPerFlag ;
            int aFlags ;
            String aFlagStr ;
            if (this.mDuration <= 0)
            {
                this.mText.dead(true);
                this.mSupplementalText.dead(true);
                this.mBoxRenderable.dead(true);
                return;
            };
            if ((((this.mMessageStyle == MESSAGE_STYLE_HINT_STAY)) || ((this.mMessageStyle == MESSAGE_STYLE_HINT_FAST))))
            {
                this.mSupplementalText.dead(true);
            };
            if ((((this.mMessageStyle == MESSAGE_STYLE_HOUSE_NAME)) || ((this.mMessageStyle == MESSAGE_STYLE_HUGE_WAVE))))
            {
            };
            int aFlashing =0xFF ;
            boolean aFadeOut ;
            int aGreyBoxHeight ;
            int aTextOffsetY ;
            if ((((this.mMessageStyle == MESSAGE_STYLE_HOUSE_NAME)) && ((this.mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                this.mBounds.x = (int)(25 - this.mBoard.x);
                this.mText.setBounds(this.mBounds.x, this.mBounds.y, this.mBounds.width(), this.mBounds.height());
                this.mSupplementalText.setBounds(this.mBounds.x, (this.mBounds.y + 40), 540, 40);
            };
            switch (this.mMessageStyle)
            {
                case MESSAGE_STYLE_TUTORIAL_LEVEL1:
                case MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY:
                case MESSAGE_STYLE_TUTORIAL_LEVEL2:
                case MESSAGE_STYLE_TUTORIAL_LATER:
                case MESSAGE_STYLE_TUTORIAL_LATER_STAY:
                    aGreyBoxHeight = 100;
                    aFlashing = 192;
                    aColor = TodCommon.TodAnimateCurve(75, 0, (this.mBoard.mMainCounter % 75), aFlashing, 0xFF, TodCommon.CURVE_BOUNCE_SLOW_MIDDLE);
                    this.mText.setColor((aColor / 0xFF), (253 / 0xFF), (245 / 0xFF), (123 / 0xFF));
                    break;
                case MESSAGE_STYLE_HINT_STAY:
                case MESSAGE_STYLE_HINT_FAST:
                    aGreyBoxHeight = 50;
                    this.mText.setColor(1, 1, 1, 1);
                    break;
                case MESSAGE_STYLE_HOUSE_NAME:
                    aColor = TodCommon.ClampInt((this.mDuration * 15), 0, 0xFF);
                    this.mText.setColor((aColor / 0xFF), 1, 1, 1);
                    break;
                case MESSAGE_STYLE_HUGE_WAVE:
                    _local7 = TodCommon.TodAnimateCurveFloat(0, 750, this.mDuration, 0, 30, TodCommon.CURVE_EASE_IN);
                    if (_local7 <= 1)
                    {
                        _local7 = 1;
                    };
                    this.mText.setColor(1, 1, 0, 0);
                    break;
            };
            if (this.mMessageStyle == MESSAGE_STYLE_HOUSE_NAME)
            {
                aSubStr = "";
                if (((this.app.IsSurvivalMode()) && ((this.mBoard.mChallenge.mSurvivalStage > 0))))
                {
                    aNumWavesPerFlag = this.mBoard.GetNumWavesPerFlag();
                    aFlags = ((this.mBoard.mChallenge.mSurvivalStage * this.mBoard.GetNumWavesPerSurvivalStage()) / aNumWavesPerFlag);
                    aFlagStr = this.mBoard.Pluralize(aFlags, "[ONE_FLAG]", "[COUNT_FLAGS]");
                    aSubStr = this.app.stringManager().translateString("[FLAGS_COMPLETED]").replace("{FLAGS}", aFlagStr);
                };
                if (aSubStr.length() > 0)
                {
                    this.mSupplementalText.text(aSubStr);
                    this.mSupplementalText.setColor((aColor / 0xFF), (224 / 0xFF), (187 / 0xFF), (98 / 0xFF));
                };
            }
            else
            {
                this.mSupplementalText.text("");
            };
        }

        public ImageRenderable mBoxRenderable ;
        public int mMessageStyle ;
        public Board mBoard ;
        public StringRenderable mText ;
        public StringRenderable mSupplementalText ;
        public int mSlideOffTime ;

        public  MessageWidget (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mDuration = 0;
            this.mLabel = null;
            this.mMessageStyle = MESSAGE_STYLE_OFF;
            this.mLabelNext = null;
            this.mMessageStyleNext = MESSAGE_STYLE_OFF;
            this.mSlideOffTime = SLIDE_OFF_TIME;
            this.mFont = app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR28);
            this.mFont.setColor(1, 1, 1, 1);
            this.mFont.scale(0.8);
            this.mText = new StringRenderable((Board.RENDER_LAYER_ABOVE_UI + 1));
            this.mText.setBounds(0, 0, 1, 1);
            this.mText.font(this.mFont);
            this.mText.text(" ");
            this.mText.x(0);
            this.mText.y(0);
            this.mSupplementalText = new StringRenderable((Board.RENDER_LAYER_ABOVE_UI + 1));
            this.mSupplementalText.setBounds(0, 0, 1, 1);
            this.mSupplementalText.font(app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR16));
            this.mSupplementalText.text("");
            this.mSupplementalText.x(0);
            this.mSupplementalText.y(0);
            this.mBoard.mRenderManager.add(this.mText);
            this.mBoard.mRenderManager.add(this.mSupplementalText);
            ImageInst anImage =this.MakeGreyBox(100);
            anImage.x(0);
            anImage.y(270);
            this.mBoxRenderable = new ImageRenderable(anImage, Board.RENDER_LAYER_ABOVE_UI);
            this.mBoard.mRenderManager.add(this.mBoxRenderable);
        }
    }


