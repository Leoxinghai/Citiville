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
import com.xiyu.flash.framework.resources.images.*;

import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.Plants.CPlant;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.resources.PVZImages;

    public class GridItem {

        public static final int GRIDITEM_GRAVESTONE =1;
        public static final int GRIDITEM_SCARY_POT =2;
        public static final int GRIDITEM_STATE_SCARY_POT_LEAF =4;
        public static final int GRIDITEM_STATE_PORTAL_CLOSED =2;
        public static final int GRIDITEM_STATE_GRAVESTONE_SPECIAL =1;
        public static final int GRIDITEM_STATE_SCARY_POT_QUESTION =3;
        public static final int GRIDITEM_STATE_SCARY_POT_ZOMBIE =5;
        public static final int GRIDITEM_STATE_NORMAL =0;
        public static final int GRIDITEM_NONE =0;

        public int mScaryPotType ;
        private ImageInst mGravestoneImage ;
        private ImageInst mGravestoneMaskImage ;
        
        public int mPosX ;
        public int mPosY ;
        public int mGridItemCounter ;
        public int mTransparentCounter ;
        public PVZApp app ;
        public boolean mHighlighted ;

        public void  GridItemDie (){
            this.mDead = true;
            if (this.mGridItemReanim!=null)
            {
                this.mGridItemReanim.mIsDead = true;
                this.mGridItemReanim = null;
            };
            if (this.mGridItemParticle!=null)
            {
                this.mGridItemParticle.die();
                this.mGridItemParticle = null;
            };
        }
        public void  DrawScaryPot (Graphics2D g ){
            int aImageCol =this.mGridItemState ;
            int aXPos =(this.mBoard.GridToPixelX(this.mGridX ,this.mGridY )-2);
            int aYPos =(this.mBoard.GridToPixelY(this.mGridX ,this.mGridY )-17);
            this.mScaryPotImage.frame(aImageCol);
            g.scale(0.5, 0.5);
            g.drawImage(this.mScaryPotImage, aXPos, aYPos);
            if (this.mHighlighted)
            {
                if (this.mTransparentCounter == 0)
                {
                    this.mScaryPotImage.useColor = true;
                    this.mScaryPotImage.setColor((196 / 0xFF), 1.5, 1.5, 1.5);
                    g.drawImage(this.mScaryPotImage, aXPos, aYPos);
                };
            }
            else
            {
                this.mScaryPotImage.setColor(1, 1, 1, 1);
            };
        }

        public int mGridItemState ;

        public void  UpdateScaryPot (){
            if (this.mTransparentCounter > 0)
            {
                this.mTransparentCounter--;
            };
        }

        public int mRenderOrder ;
        public int mSunCount ;
        public int mGoalX ;
        public int mGoalY ;
        public ParticleSystem mGridItemParticle ;
        public int mZombieType ;
        private ImageInst mScaryPotImage ;
        public int mSeedType ;
        public int mGridItemType ;

        public void  Draw (Graphics2D g ){
            switch (this.mGridItemType)
            {
                case GRIDITEM_GRAVESTONE:
                    this.DrawGraveStone(g);
                    break;
                case GRIDITEM_SCARY_POT:
                    this.DrawScaryPot(g);
                    break;
            };
        }

        public int mGridX ;
        public int mGridY ;

        public void  Update (){
            if (this.mGridItemType == GRIDITEM_SCARY_POT)
            {
                this.UpdateScaryPot();
            };
        }

        public Reanimation mGridItemReanim ;
        private ImageInst mGravestoneMoundsImage ;

        public void  AddGraveStoneParticles (){
        	int aGridCelOffsetX=((Double)((Dictionary)((Dictionary)this.mBoard.mGridCelOffset.elementAt(this.mGridX)).elementAt(this.mGridY)).elementAt(0)).intValue();
        	int aGridCelOffsetY=((Double)((Dictionary)((Dictionary)this.mBoard.mGridCelOffset.elementAt(this.mGridX)).elementAt(this.mGridY)).elementAt(1)).intValue();
            int aXPos =(this.mBoard.GridToPixelX(this.mGridX ,this.mGridY )+aGridCelOffsetX );
            int aYPos =((this.mBoard.GridToPixelY(this.mGridX ,this.mGridY )+aGridCelOffsetY )+40);
            ParticleSystem anEffect =this.app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_GRAVESTONERISE );
            anEffect.setPosition(aXPos, aYPos);
            this.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_PARTICLE));
            this.app.foleyManager().playFoley(PVZFoleyType.DIRT_RISE);
        }

        public boolean mDead ;

        public void  DrawGraveStone (Graphics2D g ){
            if (this.mGridItemCounter <= 0)
            {
                return;
            };
            int aHeightPosition =TodCommon.TodAnimateCurve(0,100,this.mGridItemCounter ,1000,0,TodCommon.CURVE_EASE_IN_OUT );
            int	aGridCelLook=((Double)((Dictionary)this.mBoard.mGridCelLook.elementAt(this.mGridX)).elementAt(this.mGridY)).intValue();
            int aGridCelOffsetX=((Double)((Dictionary)((Dictionary)this.mBoard.mGridCelOffset.elementAt(this.mGridX)).elementAt(this.mGridY)).elementAt(0)).intValue();
            int	aGridCelOffsetY=((Double)((Dictionary)((Dictionary)this.mBoard.mGridCelOffset.elementAt(this.mGridX)).elementAt(this.mGridY)).elementAt(1)).intValue();
            int aCelWidth =40;//58;
            int aCelHeight =61;
            int aGraveCol =(aGridCelLook % 5);
            int aGraveRow ;
            if (this.mGridY == 0)
            {
                aGraveRow = 1;
            }
            else
            {
                if (this.mGridItemState == GRIDITEM_STATE_GRAVESTONE_SPECIAL)
                {
                    aGraveRow = 0;
                }
                else
                {
                    aGraveRow = (2 + (aGridCelLook % 2));
                };
            };
            this.mGravestoneImage.frame(aGridCelLook);
            if (aGridCelLook < 14)
            {
                this.mGravestoneMoundsImage.frame ((aGridCelLook + 5));
            }
            else
            {
                this.mGravestoneMoundsImage.frame(aGridCelLook);
            };
            int aVisibleHeight =TodCommon.TodAnimateCurve(0,1000,aHeightPosition ,aCelHeight ,0,TodCommon.CURVE_EASE_IN_OUT );
            int aExtraBottomClip =TodCommon.TodAnimateCurve(0,50,aHeightPosition ,0,14,TodCommon.CURVE_EASE_IN_OUT );
            int aVisibleHeightDirt =TodCommon.TodAnimateCurve(500,1000,aHeightPosition ,aCelHeight ,0,TodCommon.CURVE_EASE_IN_OUT );
            int aTopClip =0;
            CPlant aGraveBusterPlant =this.mBoard.GetPlantsOnLawn(this.mGridX ,this.mGridY );
            if (((aGraveBusterPlant!=null) && ((aGraveBusterPlant.mState == CPlant.STATE_GRAVEBUSTER_EATING))))
            {
                aTopClip = (int)TodCommon.TodAnimateCurveFloat(CPlant.GRAVE_BUSTER_EAT_TIME, 0, aGraveBusterPlant.mStateCountdown, 10, 40, TodCommon.CURVE_LINEAR);
            };
            Rectangle aSrcRect =new Rectangle ((aCelWidth *aGraveCol ),((aCelHeight *aGraveRow )+aTopClip ),aCelWidth ,((aVisibleHeight -aExtraBottomClip )-aTopClip ));
            Rectangle aSrcRectDirt =new Rectangle ((aCelWidth *aGraveCol ),(aCelHeight *aGraveRow ),aCelWidth ,aVisibleHeightDirt );
//            int aXPos =((this.mBoard.GridToPixelX(this.mGridX ,this.mGridY )+2)+aGridCelOffsetX );
//            int aYPos =((this.mBoard.GridToPixelY(this.mGridX ,this.mGridY )-5)+aGridCelOffsetY );
          int aXPos =((this.mBoard.GridToPixelX(this.mGridX ,this.mGridY ))+aGridCelOffsetX );
          int aYPos =((this.mBoard.GridToPixelY(this.mGridX ,this.mGridY ))+aGridCelOffsetY );
//          int aXPos =((this.mBoard.GridToPixelX(this.mGridX ,this.mGridY )-150));
//          int aYPos =((this.mBoard.GridToPixelY(this.mGridX ,this.mGridY )+140));
//            aXPos = (int)(aXPos /app.scale);
//            aYPos = (int)(aYPos /app.scale);
            
//            System.out.println("GraveStone."+aXPos+":"+aYPos+":"+this.mGridX+":"+this.mGridY);
            g.pushState();
            g.scale(1.5f, 1.5f);
//            g.scale(app.scale, app.scale);
            
            //g.setClipRect((aXPos + this.mBoard.x), (aYPos + aTopClip), 53, (52 - aTopClip),true);
//            g.setClipRect2((aXPos + this.mBoard.x), (aYPos + aTopClip), 53, (52 - aTopClip),true);
            
            g.drawImage(this.mGravestoneImage, aXPos/1.5, ((aYPos + 61) - aVisibleHeight)/1.5);
//            g.drawImage(this.mGravestoneMaskImage, aXPos, ((aYPos + 61) - aVisibleHeight));
            g.drawImage(this.mGravestoneMoundsImage, (aXPos - 5)/1.5, (aYPos + 21)/1.5);
            
            /*
            Bitmap bitmap = Bitmap.createBitmap(this.mGravestoneImage.width(),mGravestoneImage.height(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(this.mGravestoneImage.pixels().bitmap, 0, 0, null);
            canvas.drawBitmap(this.mGravestoneMaskImage.pixels().bitmap, 0, 0, null);
            g.drawImage(new ImageInst(new ImageData(new BitmapData(bitmap))), 0, 0);
            */
            g.popState();
        }

        public Board mBoard ;

        public  GridItem (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mGridItemType = GRIDITEM_NONE;
            this.mGridX = 0;
            this.mGridY = 0;
            this.mGridItemCounter = 0;
            this.mRenderOrder = 0;
            this.mDead = false;
            this.mPosX = 0;
            this.mPosY = 0;
            this.mGoalX = 0;
            this.mGoalY = 0;
            this.mGridItemReanim = null;
            this.mGridItemParticle = null;
            this.mZombieType = Board.ZOMBIE_INVALID;
            this.mSeedType = Board.SEED_NONE;
            this.mScaryPotType = Board.SCARYPOT_NONE;
            this.mHighlighted = false;
            this.mTransparentCounter = 0;
            this.mSunCount = 0;
            this.mScaryPotImage = app.imageManager().getImageInst(PVZImages.IMAGE_SCARY_POT);
            this.mScaryPotImage.setFrame(0, 3, 2);
            this.mGravestoneImage = app.imageManager().getImageInst(PVZImages.IMAGE_TOMBSTONES);
            this.mGravestoneImage.setFrame(0, 5, 4);

            this.mGravestoneMaskImage = app.imageManager().getImageInst(PVZImages.IMAGE_TOMBSTONES_);
            this.mGravestoneMaskImage.setFrame(0, 5, 4);
            
            this.mGravestoneMoundsImage = app.imageManager().getImageInst(PVZImages.IMAGE_TOMBSTONE_MOUNDS);
            this.mGravestoneMoundsImage.setFrame(0, 5, 4);
        }
    }


