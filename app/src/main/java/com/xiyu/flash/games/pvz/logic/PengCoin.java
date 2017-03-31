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
//import android.graphics.*;
import android.graphics.Point;

import com.xiyu.util.*;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.graphics.Color;
//import flash.geom.Matrix;

    public class PengCoin extends PGHCoin {

        public static final int CURVE_WEAK_FAST_IN_OUT =8;
        public static  String COIN_DYNAMITE ="dynamite";
        private static final int OBJECT_TYPE_PLANT =1;
        public static final int SEED_PUFFSHROOM =8;
        private static final int OBJECT_TYPE_PROJECTILE =2;
        public static final int CURVE_SIN_WAVE =12;
        public static final int CURVE_BOUNCE_FAST_MIDDLE =10;
        public static final int CURVE_CONSTANT =0;
        public static final int CURVE_LINEAR =1;
        public static final int SEED_REPEATER =7;
        public static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
        public static final int CURVE_EASE_IN_OUT =4;
        private static final int OBJECT_TYPE_NONE =0;
        public static final int SEED_WALLNUT =3;
        public static final int SEED_FUMESHROOM =10;
        public static final int CURVE_EASE_IN =2;
        public static  String COIN_MOTION_FROM_SKY ="from sky";
        public static  String COIN_NONE ="none";
        public static final int SEED_PEASHOOTER =0;
        public static  String COIN_SHOVEL ="shovel";
        public static final int SEED_SUNSHROOM =9;
        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int SEED_SUNFLOWER =1;
        public static final int SEED_SNOWPEA =5;
        public static  String COIN_USABLE_SEED_PACKET ="usableseedpacket";
        public static final int SEED_GRAVEBUSTER =11;
        public static final int SEED_NONE =-1;
        private static final int BOARD_HEIGHT =405 * 2;
        private static final int OBJECT_TYPE_SHOVEL =5;
        public static final int SEED_CHERRYBOMB =2;
        public static final int CURVE_EASE_OUT =3;
        public static final int CURVE_FAST_IN_OUT_WEAK =7;
        public static final int CURVE_BOUNCE_SLOW_MIDDLE =11;
        public static final int SEED_SQUASH =4;
        public static  String COIN_SMALLSUN ="small sun";
        public static  String COIN_MOTION_COIN ="coin";
        private static final int OBJECT_TYPE_SEEDPACKET =4;
        public static  String COIN_NOTE ="note";
        public static final int CURVE_EASE_SIN_WAVE =13;
        public static final int CURVE_FAST_IN_OUT =6;
        public static final int CURVE_EASE_IN_OUT_WEAK =5;
        public static final int SEED_LEFTPEATER =49;
        private static final int BOARD_WIDTH = 540 * 2;//540;
        public static final int SEED_CHOMPER =6;
        public static  String COIN_MOTION_FROM_PLANT ="from plant";
        private static final int OBJECT_TYPE_COIN =3;
        public static final int CURVE_BOUNCE =9;
        public static  String COIN_PRESENT_PLANT ="presentplant";
        public static  String COIN_SUN ="sun";
        public static  String COIN_FINAL_SEED_PACKET ="finalseedpacket";

        public int mCollectionDistance ;
        public int mCoinAge ;
        public Reanimation mReanimation ;
        public int mGroundY ;
        public int mFadeCount ;

        public int  GetDisappearTime (){
            int aDisappearTime =750;
            if (this.mHasBouncyArrow)
            {
                aDisappearTime = 1500;
            };
            if (app.IsScaryPotterLevel())
            {
                if (this.mType == COIN_USABLE_SEED_PACKET)
                {
                    aDisappearTime = 1500;
                };
            };
            return (aDisappearTime);
        }
        public void  UpdateFall (){
            double aParticleOffsetX ;
            double aParticleOffsetY ;
            double aDisappearTime ;
            double aFinalScale ;
            if ((this.mPosY + this.mVelY) < this.mGroundY)
            {
                this.mPosY = (int)(this.mPosY + this.mVelY);
                if (this.mCoinMotion == COIN_MOTION_FROM_PLANT)
                {
                    this.mVelY = (this.mVelY + 0.09);
                }
                else
                {
                    if (this.mCoinMotion == COIN_MOTION_COIN)
                    {
                        this.mVelY = (this.mVelY + 0.15);
                    };
                };
                this.mPosX = (int)(this.mPosX + this.mVelX);
                if (this.mPosX > (BOARD_WIDTH - mWidth))
                {
                    this.mPosX = (BOARD_WIDTH - mWidth);
                    this.mVelX = (-0.4 - (Math.random() * 0.4));
                }
                else
                {
                    if (this.mPosX < 0)
                    {
                        this.mPosX = 0;
                        this.mVelX = (0.4 + (Math.random() * 0.4));
                    };
                };
            }
            else
            {
                aParticleOffsetX = (((mWidth) * this.mScale) + 17);
                aParticleOffsetY = (((mHeight) * this.mScale) - 15);
                if (this.mType == COIN_SHOVEL || this.mType == COIN_NOTE)
                {
                    aParticleOffsetX = (aParticleOffsetX - 20);
                    aParticleOffsetY = (aParticleOffsetY - 30);
                };
                if (((this.mNeedsBouncyArrow) && (!(this.mHasBouncyArrow))))
                {
                    this.mBouncyArrow = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SEEDPACKET);
                    this.mBouncyArrow.setPosition((int)(this.mPosX + aParticleOffsetX), (int)(this.mPosY + aParticleOffsetY));
                    mBoard.mRenderManager.add(new ParticleRenderable(this.mBouncyArrow, RENDER_LAYER_PARTICLE));
                    this.mHasBouncyArrow = true;
                };
                if (!this.mHitGround)
                {
                    this.mHitGround = true;
                };
                this.mPosY = this.mGroundY;
                if (!this.IsLevelAward())
                {
                    this.mDisappearCounter++;
                    aDisappearTime = this.GetDisappearTime();
                    if (this.mDisappearCounter >= aDisappearTime)
                    {
                        this.StartFade();
                    };
                };
            };
            if (this.mCoinMotion == COIN_MOTION_FROM_PLANT)
            {
                aFinalScale = 1;
                if (this.mScale < aFinalScale)
                {
                    this.mScale = (this.mScale + 0.02);
                }
                else
                {
                    this.mScale = aFinalScale;
                };
            };
        }

        public boolean mHitGround ;

        public boolean  MouseHitTest (int theX ,int theY ,HitResult theHitResult ){
            boolean aResult ;
            int aOffsetX = 0;
            int aOffsetY = 0;
//            theX = theX + 200;
//            theY = theY + 50;
            
//            theX = theX * 2;
//            theY = theY * 2;
            if (this.mType == COIN_DYNAMITE)
            {
//                return (false);
            };
            if (this.mType == COIN_FINAL_SEED_PACKET)
            {
                aOffsetX = (int)((mWidth - (mWidth * this.mScale)));
                aOffsetY = (int)((mHeight - (mHeight * this.mScale)));
            };
            int aExtraClickSize =0;
            int aExtraClickHeight =0;
            if (this.mType == COIN_SUN)
            {
                aExtraClickSize = 15;
            };
            if (this.mDead)
            {
                aResult = false;
            }
            else
            {
                if (this.mIsBeingCollected)
                {
                    aResult = false;
                }
                else
                {
                    if (this.mType == COIN_USABLE_SEED_PACKET && mBoard!= null && mBoard.mCursorObject.mCursorType != Board.CURSOR_TYPE_NORMAL)
                    {
                        aResult = false;
                    }
                    else
                    {
                        if (theX >= this.mPosX - aExtraClickSize + aOffsetX && theX < this.mPosX + mWidth * this.mScale + aExtraClickSize + aOffsetX && theY >= this.mPosY + aOffsetY - aExtraClickSize && theY < this.mPosY + mHeight * this.mScale + aOffsetY + aExtraClickSize + aExtraClickHeight)
                        {
                            aResult = true;
                        }
                        else
                        {
                            aResult = false;
                        };
                    };
                };
            };

            if (aResult)
            {
                System.out.println("CoinX.mouseHit " + theX+":"+(this.mPosX - aExtraClickSize + aOffsetX)+":"+(this.mPosX + (mWidth * this.mScale) + aExtraClickSize + aOffsetX) );
                System.out.println("CoinY.mouseHit " + theY+":"+(this.mPosY - aExtraClickSize + aOffsetY)+":"+(this.mPosY + (mHeight * this.mScale) + aExtraClickSize + aOffsetY) );
                theHitResult.mObject = this;
                theHitResult.mObjectType = OBJECT_TYPE_COIN;
                return (true);
            };
            theHitResult.mObject = null;
            theHitResult.mObjectType = OBJECT_TYPE_NONE;
            return (false);
        }

        public int mPosX ;
        public int mPosY ;
        private ImageInst mZombieNote ;

        public boolean  IsPresentWithAdvice (){
            return (false);
        }
        public void  UpdateFade (){
            if (this.mType != COIN_NOTE)
            {
                if (this.IsLevelAward())
                {
                    return;
                };
            };
            this.mFadeCount--;
            if (this.mFadeCount == 0)
            {
                this.Die();
            };
        }

        public ParticleSystem mBouncyArrow ;
        public boolean mIsBeingCollected ;

        public void  ScoreCoin (){
            int aSunValue ;
            System.out.println("update ScoreCoin." + this.mFadeCount);
            if (this.IsSun())
            {
                aSunValue = this.GetSunValue();
                this.mIsBeingCollected = false;
                mBoard.AddSunMoney(aSunValue);
            };
            this.Die();
        }
        public void  Die (){
        	mBoard.KillAllZombiesInRadius(0, this.aDestX, this.aDestY, 100, 6, false);

            this.mDead = true;
            if (this.mReanimation != null)
            {
                this.mReanimation.mIsDead = true;
            };
        }

        public boolean mHasBouncyArrow ;

        public void  CoinInitialize (int theX ,int theY ,String theCoinType ,String theCoinMotion ,PVZApp app ,Board theBoard ){
            Reanimation aReanim ;
            this.app = app;
            mBoard = theBoard;
            this.mDead = false;
            this.mType = theCoinType;
            this.mPosX = theX;
            this.mPosY = theY;
            mWidth = 40 * 3;
            mHeight = 40 * 3;
            this.mDisappearCounter = 0;
            this.mIsBeingCollected = false;
            this.mFadeCount = 0;
            this.mCoinMotion = theCoinMotion;
            this.mCoinAge = 0;
            this.mCollectionDistance = 0;
            this.mScale = 1;
            this.mUsableSeedType = SEED_NONE;
            this.mNeedsBouncyArrow = false;
            this.mHasBouncyArrow = false;
            this.mHitGround = false;
            this.mTimesDropped = 0;
            int aOffsetX =(int)(mWidth );
            int aOffsetY =(int)(mHeight);
            if (this.IsSun())
            {
                aReanim = app.reanimator().createReanimation(PVZReanims.REANIM_COIN_SILVER);//"REANIM_SUN");
                aReanim.x((this.mPosX + aOffsetX));
                aReanim.y((this.mPosY + aOffsetY));
                aReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                aReanim.animRate(6);
                aReanim.currentTrack("Sun1");
                this.mReanimation = aReanim;
            }
            else
            {
                if (this.mType == COIN_DYNAMITE)
                {
                    this.mPosX = (this.mPosX - 20);
                    this.mPosY = (this.mPosY - 20);
                    this.mExplode = 0;//100;
                    mWidth = 54 * 2;
                    mHeight = 54 * 2;
                    this.mScale = 1;
                    aReanim = app.reanimator().createReanimation(PVZReanims.REANIM_COIN_SILVER);
                    aReanim.x(this.mPosX);
                    aReanim.y(this.mPosY);
                    aReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                    aReanim.animRate(15);
                    //aReanim.currentTrack("dynomite");
                    aReanim.currentTrack("glow");
                    this.mReanimation = aReanim;
                    app.foleyManager().playFoley(PVZFoleyType.REVERSE_EXPLOSION);
                }
                else
                {
                    if (this.mType == COIN_FINAL_SEED_PACKET)
                    {
                        mWidth = 68;
                        mHeight = 95;
                        this.mScale = 0.5;
                    }
                    else
                    {
                        if (this.mType == COIN_SHOVEL)
                        {
                            mWidth = 41;
                            mHeight = 41;
                            this.mScale = 1;
                            this.mShovelImage = app.imageManager().getImageInst(PVZImages.IMAGE_SHOVEL_SMALL);
                        }
                        else
                        {
                            if (this.mType == COIN_NOTE)
                            {
                                mWidth = 53;
                                mHeight = 35;
                                this.mScale = 1;
                                this.mZombieNote = app.imageManager().getImageInst(PVZImages.IMAGE_ZOMBIENOTESMALL);
                            }
                            else
                            {
                                if (this.mType == COIN_USABLE_SEED_PACKET)
                                {
                                    mWidth = 34;
                                    mHeight = 48;
                                    this.mScale = 0.5;
                                };
                            };
                        };
                    };
                };
            };
            if(this.mCoinMotion.equals(COIN_MOTION_FROM_SKY)) {
                    this.mVelY = 0.67;
                    this.mVelX = 0;
                    this.mGroundY = (int)((Math.random() * 169) + 202);
            } else if(this.mCoinMotion.equals(COIN_MOTION_FROM_SKY_SLOW)) {
                    this.mVelY = 0.33;
                    this.mVelX = 0;
                    this.mGroundY = (int)((Math.random() * 169) + 202);
            } else if(this.mCoinMotion.equals(COIN_MOTION_FROM_PLANT)) {
                    this.mVelY = (-1.7 - (Math.random() * 1.7));
                    this.mVelX = (-0.4 + (Math.random() * 0.8));
                    this.mGroundY = (int)((this.mPosY + 10) + (Math.random() * 13));
                    this.mScale = 0.4;
            } else if(this.mCoinMotion.equals(COIN_MOTION_COIN)) {
                    this.mVelY = (-2.1 - (Math.random() * 1.3));
                    this.mVelX = (-0.4 + (Math.random() * 0.7));
                    this.mGroundY = (int)((this.mPosY + 30) + (Math.random() * 13));
                    if (this.mGroundY > (BOARD_HEIGHT - 53))
                    {
                        this.mGroundY = (BOARD_HEIGHT - 53);
                    };
                    if (this.mGroundY < 54)
                    {
                        this.mGroundY = 54;
                    };
                    if (this.mType == COIN_FINAL_SEED_PACKET || this.mType == COIN_USABLE_SEED_PACKET || this.mType == COIN_SHOVEL || this.mType == COIN_NOTE)
                    {
                        this.mGroundY = (this.mGroundY - 21);
                    };
                    if (this.mType == COIN_DYNAMITE)
                    {
                        this.mVelX = 0;
                        this.mGroundY = (this.mGroundY - 54);
                    };
                    if (this.mPosY > this.mGroundY)
                    {
                        this.mPosY = this.mGroundY;
                    };
            };
            if (this.CoinGetsBouncyArrow())
            {
                this.mNeedsBouncyArrow = true;
            };
            this.PlayLaunchSound();
            this.Collect();
        }
        
        public boolean  IsSun (){
            if (this.mType == COIN_SUN || this.mType == COIN_SMALLSUN)
            {
                return (true);
            };
            return (false);
        }
        public boolean  IsLevelAward (){
            if (this.mType == COIN_FINAL_SEED_PACKET || this.mType == COIN_SHOVEL || this.mType == COIN_NOTE)
            {
                return (true);
            };
            return (false);
        }
        public void  Collect (){
            ParticleSystem anEffect ;
            int aParticleOffsetX ;
            int aParticleOffsetY ;
            int i =0;
            SeedPacket aSeedPacket ;
            int aCost ;
            int aAmountOver ;

            ParticleSystem anEffect2 =this.app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_FLYMAHJONG);
            anEffect2.setPosition(this.mPosX, this.mPosY);
            System.out.println("IMAGE_AWARDGLOW." + this.mPosX +":"+mPosY);
            this.mBoard.mRenderManager.add(new ParticleRenderable(anEffect2, Board.RENDER_LAYER_PARTICLE));

            
//            app.mBoard.KillOneZombie();
            if (this.mDead)
            {
                return;
            };
            this.mCollectX = this.mPosX;
            this.mCollectY = this.mPosY;
            
            aDestX = this.mPosX - 400;
            aDestY = this.mPosY;
            
            this.mIsBeingCollected = true;
            boolean aIsEndlessAward ;
            if (this.IsLevelAward())
            {
                if (this.mHasBouncyArrow)
                {
                    this.mBouncyArrow.mDead = true;
                };
                if (app.IsAdventureMode() && mBoard.mLevel == 4)
                {
                    app.foleyManager().playFoley(PVZFoleyType.SHOVEL);
                }
                else
                {
                    app.foleyManager().playFoley(PVZFoleyType.SEEDLIFT);
                    app.foleyManager().playFoley(PVZFoleyType.DROP);
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_STARBURST);
                    anEffect.setPosition((int)(this.mPosX + ((mWidth * this.mScale) / 2)), (int)(this.mPosY + ((mHeight * this.mScale) / 2)));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_SCREEN_FADE));
                };
                mBoard.FadeOutLevel();
                if (this.mType == COIN_NOTE)
                {
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_PRESENTPICKUP);
                    anEffect.setPosition((int)(this.mPosX + ((mWidth * this.mScale) / 2)), (int)(this.mPosY + ((mHeight * this.mScale) / 2)));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect,1));
                    this.StartFade();
                }
                else
                {
                    aParticleOffsetX = (int)((mWidth * this.mScale) / 2);
                    aParticleOffsetY = (int)((mHeight * this.mScale) / 2);
                    this.mSeedEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_AWARD);
                    this.mSeedEffect.setPosition((this.mPosX + aParticleOffsetX), (this.mPosY + aParticleOffsetY));
                    mBoard.mRenderManager.add(new ParticleRenderable(this.mSeedEffect, Board.RENDER_LAYER_PARTICLE));
                };
            }
            else
            {
                if (this.mType == COIN_USABLE_SEED_PACKET)
                {
                    mBoard.mCursorObject.mType = this.mUsableSeedType;
                    mBoard.mCursorObject.mCursorType = Board.CURSOR_TYPE_PLANT_FROM_USABLE_COIN;
                    this.mGroundY = this.mPosY;
                    this.mFadeCount = 0;
                    return;
                };
                if (this.IsSun() && mBoard !=null && !mBoard.HasConveyorBeltSeedBank())
                {
                    i = 0;
                    while (i < mBoard.mSeedBank.mNumPackets)
                    {
                    	aSeedPacket=(SeedPacket)mBoard.mSeedBank.mSeedPackets.elementAt(i);
                        aCost = mBoard.GetCurrentPlantCost(aSeedPacket.mPacketType);
                        aAmountOver = ((mBoard.mSunMoney + mBoard.CountSunBeingCollected()) - aCost);
                        if (aAmountOver >= 0 && aAmountOver < this.GetSunValue())
                        {
                            aSeedPacket.FlashIfReady();
                        };
                        i++;
                    };
                };
            };
        }

        public int mTimesDropped ;
        private ParticleSystem mSeedEffect ;
        public int mUsableSeedType ;
        public int mExplode ;
        

        public void  TryAutoCollectAfterLevelAward (){
            boolean aCollect =false ;
            if (this.IsSun())
            {
                aCollect = true;
            };
            if (aCollect)
            {
                this.PlayCollectSound();
                this.Collect();
            };
        }
        public void  PlayLaunchSound (){
        }
        public boolean  CoinGetsBouncyArrow (){
            if (this.IsLevelAward())
            {
                return (true);
            };
            return (false);
        }

        public String mType ;

        public void  DroppedUsableSeed (){
            this.mIsBeingCollected = false;
            if (this.mTimesDropped == 0)
            {
                this.mDisappearCounter = Math.min(this.mDisappearCounter, 1200);
            };
            this.mTimesDropped++;
        }

        private ImageInst mShovelImage ;
        private ImageInst mBufferedImage ;

        public int  GetFinalSeedPacketType (){
            int aArea =(((mBoard.mLevel -1)/10)+1);
            int aSubArea =(((mBoard.mLevel -1)% 10) + 1);
            int aSeeds =(((aArea -1)*8)+aSubArea );
            if (aSubArea >= 10)
            {
                aSeeds = (aSeeds - 2);
            }
            else
            {
                if (aSubArea >= 5)
                {
                    aSeeds--;
                };
            };
            if (aSeeds > 40)
            {
                aSeeds = 40;
            };
            return (aSeeds);
        }
        public void  MouseDown (int x ,int y ){
            if ( mBoard ==null  || mBoard.mPaused || mBoard.mGameScene != Board.SCENE_PLAYING)
            {
                return;
            };
            if (this.mDead)
            {
                return;
            };
            if (!this.mIsBeingCollected)
            {
                if (mBoard.mLevel == 1)
                {
                    mBoard.DisplayAdvice("[ADVICE_CLICKED_ON_SUN]", Board.MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, Board.ADVICE_CLICKED_ON_SUN);
                };
                this.PlayCollectSound();
                this.Collect();
            };
        }
        public int  GetSunValue (){
            if (this.mType == COIN_SMALLSUN)
            {
                return (15);
            };
            return (25);
        }

        public double mScale ;

        public void  drawBufferedImage (Graphics2D g ){
            int aSeedType ;
            ImageInst aSeedBackImg ;
            ImageInst aPlantImg =null;
            FontInst theFont ;
            int aCost ;
            int width ;
            int height ;
            int aTextOffsetX ;
            int aTextOffsetY ;
            if (this.mType == COIN_USABLE_SEED_PACKET)
            {
                aSeedType = this.mUsableSeedType;
            }
            else
            {
                aSeedType = this.GetFinalSeedPacketType();
            };
            aSeedBackImg = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDPACKET_LARGER);
            int w =aSeedBackImg.width() ;
            int h =aSeedBackImg.height() ;
            this.mBufferedImage = new ImageInst(new ImageData(new BitmapData(w, h, true, 0)));
            Graphics2D bufferG =this.mBufferedImage.graphics();
            bufferG.drawImage(aSeedBackImg, 0, 0);
            int aOffsetX =10;
            int aOffsetY =15;
            double aScale =1;
            switch (aSeedType)
            {
                case SEED_EXPLODE_O_NUT:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                    aPlantImg.useColor = true;
                    aPlantImg.setColor(1, 1, (64 / 0xFF), (64 / 0xFF));
                    break;
                case SEED_PEASHOOTER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTERSINGLE);
                    aOffsetY = (aOffsetY + 4);
                    break;
                case SEED_SUNFLOWER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNFLOWER);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + 2);
                    break;
                case SEED_CHERRYBOMB:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHERRYBOMB);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + -1);
                    break;
                case SEED_SQUASH:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SQUASH);
                    aOffsetX = (aOffsetX + 0);
                    aOffsetY = (aOffsetY + -2);
                    break;
                case SEED_CHOMPER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHOMPER);
                    aOffsetX = (aOffsetX + -2);
                    aOffsetY = (aOffsetY + -3);
                    aScale = 0.85;
                    break;
                case SEED_SNOWPEA:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SNOWPEA);
                    aOffsetY = (aOffsetY + 3);
                    break;
                case SEED_REPEATER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTER);
                    aOffsetY = (aOffsetY + 5);
                    break;
                case SEED_PUFFSHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PUFFSHROOM);
                    aOffsetY = (aOffsetY + 12);
                    aOffsetX = (aOffsetX + 9);
                    break;
                case SEED_SUNSHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNSHROOM);
                    aOffsetY = (aOffsetY + 13);
                    aOffsetX = (aOffsetX + 10);
                    break;
                case SEED_GRAVEBUSTER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_GRAVEDIGGER);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + -5);
                    aScale = 0.85;
                    break;
                case SEED_FUMESHROOM:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_FUMESHROOM);
                    aOffsetX = (aOffsetX - 2);
                    aOffsetY = (aOffsetY + 1);
                    aScale = 0.95;
                    break;
                case SEED_WALLNUT:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                    aOffsetX = (aOffsetX + 3);
                    break;
                case SEED_LEFTPEATER:
                    aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_LEFTFACINGPEASHOOTER);
                    aOffsetX = (aOffsetX + 2);
                    aOffsetY = (aOffsetY + 5);
                    break;
            };
            bufferG.pushState();
            bufferG.scale(aScale, aScale);
            bufferG.drawImage(aPlantImg, aOffsetX, aOffsetY);
            bufferG.popState();
            if (this.mType == COIN_FINAL_SEED_PACKET)
            {
                theFont = app.fontManager().getFontInst(PVZFonts.FONT_PICO129);
                theFont.scale(1.6);
                theFont.setColor(1, 0, 0, 0);
                aCost = mBoard.GetCurrentPlantCost(aSeedType);
                width = (int)theFont.stringWidth(""+aCost);
                height = (int)theFont.getAscent();
                aTextOffsetX = (45 - width);
                aTextOffsetY = 72;
                bufferG.setFont(theFont);
                bufferG.drawString(""+aCost, aTextOffsetX, aTextOffsetY);
            };
        }
        public void  Draw (Graphics2D g ){
            double aAlpha ;
            int aFade ;
            int aGray ;
            int aDisappearTime ;
            Color aFlashingColor =Color.ARGB(1,1,1,1);
            if (this.IsLevelAward() && !this.mIsBeingCollected)
            {
                aFlashingColor = TodCommon.GetFlashingColor(this.mCoinAge, 75);
            };
            //int aOffsetX =(int)(mWidth *0.5);
            //int aOffsetY =(int)(mHeight *0.5);
            
            int aOffsetX =(int)mWidth;
            int aOffsetY =(int)mHeight;
            
            if (this.mReanimation != null)
            {
                this.mReanimation.x((this.mPosX + aOffsetX));
                this.mReanimation.y((this.mPosY + aOffsetY));
                g.pushState();
                if (this.mType == COIN_SMALLSUN)
                {
                    this.mScaleMatrix.a = (this.mScale * 0.5);
                    this.mScaleMatrix.d = (this.mScale * 0.5);
                }
                else
                {
                    this.mScaleMatrix.a = this.mScale;
                    this.mScaleMatrix.d = this.mScale;
                };
                aAlpha = 1;
                if (this.IsSun() && this.mIsBeingCollected)
                {
                    aAlpha = TodCommon.ClampFloat((this.mCollectionDistance * 0.035), 0.35, 1);
                }
                else
                {
                    if (this.mFadeCount > 0)
                    {
                        aFade = TodCommon.TodAnimateCurve(15, 0, this.mFadeCount, 0xFF, 0, CURVE_LINEAR);
                        aAlpha = (aFade / 0xFF);
                    };
                };
                this.mReanimation.drawLerp(g, this.mScaleMatrix, aAlpha);
                g.popState();
            };
            if (this.mType == COIN_SUN || this.mType == COIN_SMALLSUN || this.mDead || this.mType == COIN_DYNAMITE)
            {
                return;
            };
            if (this.mType == COIN_FINAL_SEED_PACKET)
            {
                aOffsetX = (int)((mWidth - (mWidth * this.mScale)) * 0.5);
                aOffsetY = (int)((mHeight - (mHeight * this.mScale)) * 0.5);
                if (this.mBufferedImage == null)
                {
                    this.drawBufferedImage(g);
                };
                g.pushState();
                this.mBufferedImage.useColor = true;
                this.mBufferedImage.setColor(aFlashingColor.alpha, aFlashingColor.red, aFlashingColor.green, aFlashingColor.blue);
                g.scale(this.mScale, this.mScale);
                g.drawImage(this.mBufferedImage, (this.mPosX + aOffsetX), (this.mPosY + aOffsetY));
                g.popState();
                return;
            };
            if (this.mType == COIN_SHOVEL)
            {
                g.pushState();
                g.scale(this.mScale, this.mScale);
                this.mShovelImage.useColor = true;
                this.mShovelImage.setColor(aFlashingColor.alpha, aFlashingColor.red, aFlashingColor.green, aFlashingColor.blue);
                g.drawImage(this.mShovelImage, this.mPosX, this.mPosY);
                g.popState();
            }
            else
            {
                if (this.mType == COIN_NOTE)
                {
                    g.pushState();
                    g.scale(this.mScale, this.mScale);
                    this.mZombieNote.useColor = true;
                    this.mZombieNote.setColor(aFlashingColor.alpha, aFlashingColor.red, aFlashingColor.green, aFlashingColor.blue);
                    g.drawImage(this.mZombieNote, this.mPosX, this.mPosY);
                    g.popState();
                }
                else
                {
                    if (this.mType == COIN_USABLE_SEED_PACKET)
                    {
                        aGray = 0xFF;
                        if (this.mIsBeingCollected)
                        {
                            aGray = 128;
                        }
                        else
                        {
                            aDisappearTime = this.GetDisappearTime();
                            if ((((this.mDisappearCounter > (aDisappearTime - 300))) && (((this.mDisappearCounter % 60) < 30))))
                            {
                                aGray = 192;
                            };
                        };
                        if (this.mBufferedImage == null)
                        {
                            this.drawBufferedImage(g);
                        };
                        g.pushState();
                        this.mBufferedImage.useColor = true;
                        this.mBufferedImage.setColor(1, (aGray / 0xFF), (aGray / 0xFF), (aGray / 0xFF));
                        g.scale(0.5, 0.5);
                        g.drawImage(this.mBufferedImage, this.mPosX, this.mPosY);
                        g.popState();
                        return;
                    };
                };
            };
        }
        public void  StartFade (){
            this.mFadeCount = 1500;
        }

        public int mDisappearCounter ;
        int aDestX =0;
        int aDestY =0;

        public void  UpdateCollected (){
            if (this.mType == COIN_FINAL_SEED_PACKET)
            {
            };
            if (this.IsSun())
            {
                //aDestX = 16;
                //aDestY = 3;
            }
            else
            {
                if (this.mType == COIN_USABLE_SEED_PACKET)
                {
                    this.mDisappearCounter++;
                    return;
                };
                if (this.IsLevelAward())
                {
                    if (this.mType == COIN_SHOVEL)
                    {
                        aDestX = 250;
                        aDestY = 115;
                    }
                    else
                    {
                        aDestX = 236;
                        aDestY = 87;
                    };
                    this.mDisappearCounter++;
                };
            };
            if (this.IsLevelAward())
            {
                if (this.mType == COIN_SHOVEL)
                {
                    this.mScale = TodCommon.TodAnimateCurveFloat(0, 270, this.mDisappearCounter, 1, 1, TodCommon.CURVE_EASE_IN_OUT);
                }
                else
                {
                    this.mScale = TodCommon.TodAnimateCurveFloat(0, 270, this.mDisappearCounter, 0.51, 1, TodCommon.CURVE_EASE_IN_OUT);
                };
                this.mPosX = (int)TodCommon.TodAnimateCurveFloat(0, 236, this.mDisappearCounter, this.mCollectX, aDestX, TodCommon.CURVE_EASE_OUT);
                this.mPosY = (int)TodCommon.TodAnimateCurveFloat(0, 236, this.mDisappearCounter, this.mCollectY, aDestY, TodCommon.CURVE_EASE_OUT);
                return;
            };
            int aDeltaX =Math.abs ((this.mPosX -aDestX ));
            int aDeltaY =Math.abs ((this.mPosY -aDestY ));
            if (this.mPosX > aDestX)
            {
                this.mPosX = (this.mPosX - (aDeltaX / 21));
            }
            else
            {
                if (this.mPosX < aDestX)
                {
                    this.mPosX = (this.mPosX + (aDeltaX / 21));
                };
            };
            if (this.mPosY > aDestY)
            {
                this.mPosY = (this.mPosY - (aDeltaY / 21));
            }
            else
            {
                if (this.mPosY < aDestY)
                {
                    this.mPosY = (this.mPosY + (aDeltaY / 21));
                };
            };
            this.mCollectionDistance = (int)Math.sqrt(((aDeltaY * aDeltaY) + (aDeltaX * aDeltaX)));
//            System.out.println("updateCollect." + this.mCollectionDistance+":"+aDeltaX+":"+aDeltaY+":"+this.mPosX+":"+this.mPosY);
//            int aScoringDistance =8;
            int aScoringDistance =29;
            if (this.mCollectionDistance < aScoringDistance)
            {
                this.ScoreCoin();
            };
            this.mScale = TodCommon.ClampFloat((this.mCollectionDistance * 0.05), 0.5, 1);
            this.mScale = (this.mScale * 1);
        }

        public double mVelX ;
        public double mVelY ;

        public void  Update (){
            ParticleSystem anEffect;
            if (mBoard.mGameScene == Board.SCENE_ZOMBIES_WON || mBoard.mGameScene == Board.SCENE_AWARD || mBoard.mGameScene == Board.SCENE_LEVEL_INTRO)
            {
                return;
            };
            if (this.mExplode > 0)
            {
                this.mExplode--;
                if (this.mExplode == 0)
                {
                    app.foleyManager().playFoley(PVZFoleyType.CHERRYBOMB);
                    app.foleyManager().playFoley(PVZFoleyType.JUICY);
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_POWIE);
                    anEffect.setPosition((this.mPosX + 40), (this.mPosY + 50));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_PARTICLE));
                    mBoard.ShakeBoard(10, -15);
                    mBoard.mChallenge.ScaryPotterJackExplode((this.mPosX + 40), (this.mPosY + 50));
                    mBoard.KillAllPlantsInRadius((this.mPosX + 40), (this.mPosY + 50), 90);
                    this.Die();
                };
            };
            this.mCoinAge++;
            if (this.mReanimation != null)
            {
                this.mReanimation.update();
            };
            if (mBoard.mGameScene != Board.SCENE_PLAYING && mBoard.mGameScene != Board.SCENE_AWARD)
            {
                return;
            };
            if (this.mFadeCount > 0)
            {
                this.UpdateFade();
            }
            else
            {
            	if (!this.mIsBeingCollected)
                {
                    this.UpdateFall();
                }
                else
                {
                    this.UpdateCollected();
                };
            };
            int aParticleOffsetX =(mWidth /2);
            int aParticleOffsetY =(mHeight /2);
            if (this.mSeedEffect != null)
            {
                this.mSeedEffect.setPosition((this.mPosX + aParticleOffsetX), (this.mPosY + aParticleOffsetY));
            };
        }

//        public boolean mDead ;
        public String mCoinMotion ;

        public void  PlayCollectSound (){
            if (this.IsSun())
            {
                app.foleyManager().playFoley(PVZFoleyType.SUN);
            }
            else
            {
                if (this.mType == COIN_USABLE_SEED_PACKET)
                {
                    app.foleyManager().playFoley(PVZFoleyType.SEEDLIFT);
                };
            };
        }

        public int mCollectX ;
        public int mCollectY ;
        public boolean mNeedsBouncyArrow ;
        private Matrix mScaleMatrix ;

        public  PengCoin (){
            super();
            this.mScaleMatrix = new Matrix();
        }
    }


