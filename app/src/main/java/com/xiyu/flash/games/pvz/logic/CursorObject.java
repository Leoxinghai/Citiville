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
import android.graphics.Point;

import com.xiyu.util.Array;
import com.xiyu.util.*;

import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.graphics.Color;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
//import flash.geom.Matrix;
import com.xiyu.flash.games.pvz.PVZApp;

    public class CursorObject extends GameObject {

        public static final int SEED_EXPLODE_O_NUT =50;
        private static final int CURSOR_TYPE_PLANT_FROM_BANK =1;
        public static final int SEED_PUFFSHROOM =8;
        private static final int CURSOR_TYPE_NORMAL =0;
        private static final int SEED_NONE =-1;
        private static final int SEED_CHERRYBOMB =2;
        private static final int SEED_SQUASH =4;
        private static final int SEED_PEASHOOTER =0;
        private static final int CURSOR_TYPE_HAMMER =4;
        private static final int SEED_CHOMPER =6;
        public static final int SEED_SUNSHROOM =9;
        private static final int SEED_REPEATER =7;
        private static  String STATE_HOLDING_SEED ="holding seed";
        private static final int CURSOR_TYPE_SHOVEL =3;
        private static final int SEED_WALLNUT =3;
        public static final int SEED_FUMESHROOM =10;
        private static final int CURSOR_TYPE_PLANT_FROM_USABLE_COIN =2;
        private static  String STATE_EMPTY ="empty";
        public static final int SEED_LEFTPEATER =49;
        private static  String VARIATION_NORMAL ="normal";
        public static final int SEED_GRAVEBUSTER =11;
        private static final int SEED_SUNFLOWER =1;
        private static final int SEED_SNOWPEA =5;

        public int mCursorType ;

        public void  draw (Graphics2D g ){

            ImageInst anImg ;
            int aOffsetX ;
            int aOffsetY ;
            g.pushState();
            if (this.mCursorType == CURSOR_TYPE_NORMAL)
            {
            	g.blitImage(this.mNormalCursor, mX+40, (mY-this.mNormalCursor.height()));
            }
//            this.mCursorType = CURSOR_TYPE_SHOVEL;

//            System.out.println("Cursor." + this.mBoard.mSunMoney);
            if (this.mCursorType == CURSOR_TYPE_SHOVEL)
            {
                g.scale(0.3, 0.3);
            	g.blitImage(this.mShovelImage, mX*3.33, (mY*3.33-this.mShovelImage.height()));
            }
            else
            {
            	if ((((this.mCursorType == CURSOR_TYPE_PLANT_FROM_BANK)) || ((this.mCursorType == CURSOR_TYPE_PLANT_FROM_USABLE_COIN))))
                {

            		anImg=(ImageInst)this.mSeedImages.elementAt(this.mType);
                    if (anImg == null)
                    {
                        anImg = this.GetPlantImage(this.mType);
                    };
                    aOffsetX = (-(anImg.width()) / 2)+40;
                    aOffsetY = (-(anImg.height()) / 2);
                    g.drawImage(anImg, (mX + aOffsetX), (mY + aOffsetY));
                };
            };
            g.popState();
        }

        public int mType ;

        public void  update (){
            mVisible = true;

            mX = app.widgetManager().lastMouseX;
            mY = app.widgetManager().lastMouseY;
        }

        private ImageInst mShovelImage ;
        private ImageInst mNormalCursor;

        private ImageInst  GetPlantImage (int theType ){
            ImageInst aPlantImg =null;
            switch (theType)
            {
                case SEED_PEASHOOTER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTERSINGLE);
                    break;
                case SEED_SUNFLOWER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNFLOWER);
                    break;
                case SEED_CHERRYBOMB:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHERRYBOMB);
                    break;
                case SEED_SQUASH:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SQUASH);
                    break;
                case SEED_CHOMPER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHOMPER);
                    break;
                case SEED_SNOWPEA:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SNOWPEA);
                    break;
                case SEED_REPEATER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTER);
                    break;
                case SEED_WALLNUT:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                    break;
                case SEED_EXPLODE_O_NUT:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                    aPlantImg.useColor = true;
                    aPlantImg.setColor(1, 1, (64 / 0xFF), (64 / 0xFF));
                    break;
                case SEED_PUFFSHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PUFFSHROOM);
                    break;
                case SEED_SUNSHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNSHROOM);
                    break;
                case SEED_FUMESHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_FUMESHROOM);
                    break;
                case SEED_GRAVEBUSTER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_GRAVEDIGGER);
                    break;
                case SEED_LEFTPEATER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_LEFTFACINGPEASHOOTER);
                    break;
            };
            this.mSeedImages.put(theType,aPlantImg);
            return (aPlantImg);
        }
        private ImageInst  drawBufferedSeed (int seedType ){
            String aPlantReanimType =mBoard.GetPlantDefinition(seedType );
//            trace(aPlantReanimType);
            Reanimation aPlantReanim =app.reanimator().createReanimation(aPlantReanimType );
            switch (seedType)
            {
                case SEED_PEASHOOTER:
                case SEED_REPEATER:
                case SEED_SNOWPEA:
                    aPlantReanim.currentTrack("anim_full_idle");
                    break;
                case SEED_WALLNUT:
                    aPlantReanim.currentTrack("anim_idle1");
                    break;
                case SEED_EXPLODE_O_NUT:
                    aPlantReanim.currentTrack("anim_idle1");
                    aPlantReanim.useColor = true;
                    aPlantReanim.overrideColor = Color.RGB(1, (64 / 0xFF), (64 / 0xFF));
                    break;
                case SEED_PUFFSHROOM:
                    break;
                default:
                    aPlantReanim.currentTrack("anim_idle");
            };
            aPlantReanim.update();
            Rectangle aBounds =aPlantReanim.getBoundsForFrame ();
            ImageInst aImage =new ImageInst(new ImageData(new BitmapData(aBounds.width() ,aBounds.height() ,true ,0)));
            Graphics2D bufferG =new Graphics2D(aImage.pixels() );
            aPlantReanim.drawLerp(bufferG, new Matrix(),0);
            this.mSeedImages.put(seedType,aImage);
            return (aImage);
        }

        private Dictionary mSeedImages ;
        public int mSeedBankIndex ;
        public String cursorState ;
        public Coin mCoin ;

        public  CursorObject (PVZApp app ,Board theBoard ){
            super();
            this.mSeedImages = new Dictionary();
            this.mCoin = null;
            this.mCursorType = CURSOR_TYPE_NORMAL;
            this.cursorState = STATE_EMPTY;
            mBoard = theBoard;
            this.app = app;
            this.mShovelImage = app.imageManager().getImageInst(PVZImages.IMAGE_SHOVEL_SMALL);
            this.mNormalCursor = app.imageManager().getImageInst("ZOMBIE_BUNGI_HAND");
        }
    }


