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
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.graphics.Color;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
//import flash.geom.Matrix;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.PVZApp;

    public class CursorPreview extends GameObject {

        private static final int SEED_SUNFLOWER =1;
        public static final int SEED_LEFTPEATER =49;
        public static final int SEED_PUFFSHROOM =8;
        private static  String PLANTING_OK ="ok";
        private static final int SEED_NONE =-1;
        private static final int SEED_CHERRYBOMB =2;
        private static final int LAWN_YMIN =40;
        private static final int SEED_SQUASH =4;
        private static final int SEED_PEASHOOTER =0;
        private static final int SEED_WALLNUT =3;
        private static final int SEED_REPEATER =7;
        private static final int SEED_CHOMPER =6;
        public static final int SEED_SUNSHROOM =9;
        private static final int LAWN_YMAX =365;
        public static final int SEED_FUMESHROOM =10;
        private static  String PLANTING_NOT_HERE ="not here";
        private static final int LAWN_XMIN =27;
        private static final int GRIDSIZEX =9;
        private static  String VARIATION_NORMAL ="normal";
        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int SEED_GRAVEBUSTER =11;
        private static final int GRIDSIZEY =5;
        private static final int SEED_SNOWPEA =5;
        private static final int LAWN_XMAX =513;

        public void  draw (Graphics2D g ){
            if (!mVisible)
            {
                return;
            };
            int aPlantingSeedType =mBoard.GetSeedTypeInCursor ();
            if (aPlantingSeedType == SEED_NONE)
            {
                return;
            };
            ImageInst anImg=(ImageInst)this.mSeedImages.elementAt(aPlantingSeedType);
            if (anImg == null)
            {
                anImg = this.GetPlantImage(aPlantingSeedType);
            };
            int aCenterX =28;
            int aCenterY =28;
            int aOffsetX =(aCenterX -(anImg.width() /2));
            int aOffsetY =(aCenterY -(anImg.height() /2));
            g.drawImage(anImg, (mX + aOffsetX), (mY + aOffsetY));
        }

        public int mGridX ;
        private Dictionary mSeedImages ;

        public void  update (){
            int aPlantingSeedType =mBoard.GetSeedTypeInCursor ();
            int aMouseX =app.widgetManager().lastMouseX ;
            int aMouseY =app.widgetManager().lastMouseY ;
            this.mGridX = mBoard.PlantingPixelToGridX(aMouseX, aMouseY, aPlantingSeedType);
            this.mGridY = mBoard.PlantingPixelToGridY(aMouseX, aMouseY, aPlantingSeedType);
            if ((((((((this.mGridX < 0)) || ((this.mGridX >= GRIDSIZEX)))) || ((this.mGridY < 0)))) || ((this.mGridY >= GRIDSIZEY))))
            {
                mVisible = false;
            }
            else
            {
//                if (((mBoard.IsPlantInCursor()) && ((mBoard.CanPlantAt(this.mGridX, this.mGridY, aPlantingSeedType) == PLANTING_OK))))
//                {
                    mX = mBoard.GridToPixelX(this.mGridX, this.mGridY);
                    mY = mBoard.GridToPixelY(this.mGridX, this.mGridY);
                    mVisible = true;
//                }
//                else
//                {
                    mVisible = false;
//                };
            };
        }
        private ImageInst  drawBufferedSeed (int seedType ){
            String aPlantReanimType =mBoard.GetPlantDefinition(seedType );
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
                default:
                    aPlantReanim.currentTrack("anim_idle");
            };
            aPlantReanim.update();
            Rectangle aBounds =aPlantReanim.getBoundsForFrame ();
            ImageInst aImage =new ImageInst(new ImageData(new BitmapData(aBounds.width() ,aBounds.height() ,true ,0)));
            Graphics2D bufferG =new Graphics2D(aImage.pixels());
            aPlantReanim.drawLerp(bufferG, new Matrix(), 0.5);
            this.mSeedImages.put(seedType, aImage);
            return (aImage);
        }

        public int mGridY ;

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
                    aPlantImg.setColor(0.5, 1, (64 / 0xFF), (64 / 0xFF));
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
            aPlantImg.useColor = true;
            if (theType != SEED_EXPLODE_O_NUT)
            {
                aPlantImg.setColor(0.5, 1, 1, 1);
            };
            this.mSeedImages.put(theType,aPlantImg);
            return (aPlantImg);
        }

        public  CursorPreview (PVZApp app ,Board theBoard ){
            super();
            this.mSeedImages = new Dictionary();
            this.app = app;
            mBoard = theBoard;
            mX = 0;
            mY = 0;
            mWidth = 54;
            mHeight = 54;
            this.mGridX = 0;
            this.mGridY = 0;
            mVisible = false;
        }
    }


