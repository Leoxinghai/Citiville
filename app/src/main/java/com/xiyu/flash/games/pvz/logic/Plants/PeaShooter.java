package com.xiyu.flash.games.pvz.logic.Plants;
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

import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopOnceAndIdle;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.Projectile;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;

    public class PeaShooter extends CPlant {

         public void  Update (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            this.UpdateShooting();
            this.UpdateShooter();
            this.Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanimColor();
            UpdateReanim();
        }
         public void  Animate (){
            int maxTime ;
            if (mRecentlyEatenCountdown > 0)
            {
                mRecentlyEatenCountdown--;
            };
            if (mEatenFlashCountdown > 0)
            {
                mEatenFlashCountdown--;
            };
            if (mSquished)
            {
                mFrame = 0;
                return;
            };
            UpdateBlink();
            if (mAnimPing)
            {
                maxTime = ((mFrameLength * mNumFrames) - 1);
                if (mAnimCounter < maxTime)
                {
                    mAnimCounter++;
                }
                else
                {
                    mAnimPing = false;
                    mAnimCounter = (mAnimCounter - mFrameLength);
                };
            }
            else
            {
                if (mAnimCounter > 0)
                {
                    mAnimCounter--;
                }
                else
                {
                    mAnimPing = true;
                    mAnimCounter = (mAnimCounter + mFrameLength);
                };
            };
            mFrame = (mAnimCounter / mFrameLength);
        }
        public boolean  FindTargetAndFire (int theRow ,int thePlantWeapon ){
            Zombie aZombie =FindTargetZombie(theRow ,thePlantWeapon );
            if (aZombie==null)
            {
                return (false);
            };
            double idleRate = TodCommon.RandRangeFloat(15,20);
            mHeadReanimation.animRate(35);
            mHeadReanimation.currentTrack("anim_shooting");
            mHeadReanimation.loopType(new ReanimLoopOnceAndIdle("anim_head_idle", mBodyReanimation.animRate()));
            mShootingCounter = 35;
            if (mSeedType == SEED_REPEATER)
            {
                mHeadReanimation.animRate(45);
                mShootingCounter = 26;
            };
            return (true);
        }
        public void  UpdateShooter (){
            mLaunchCounter--;
            if (mLaunchCounter <= 0)
            {
                mLaunchCounter = (int)(mLaunchRate - (Math.random() * 15));
                this.FindTargetAndFire(mRow, WEAPON_PRIMARY);
            };
            if (mLaunchCounter == 25)
            {
                if (mSeedType == SEED_REPEATER)
                {
                    this.FindTargetAndFire(mRow, WEAPON_PRIMARY);
                };
            };
        }
        public void  Fire (Zombie theTargetZombie ,int theRow ,int thePlantWeapon ){
            int aProjectileType =0;
            int aOriginX =0;
            int aOriginY =0;
            int aOffsetX =0;
            int aOffsetY =0;
            switch (mSeedType)
            {
                case SEED_PEASHOOTER:
                    aProjectileType = PROJECTILE_PEA;
                    break;
                case SEED_REPEATER:
                    aProjectileType = PROJECTILE_PEA;
                    break;
                case SEED_SNOWPEA:
                    aProjectileType = PROJECTILE_SNOWPEA;
                    break;
            };
            app.foleyManager().playFoley(PVZFoleyType.THROW);
            if (mSeedType == SEED_SNOWPEA)
            {
            };
            if ((((((mSeedType == SEED_PEASHOOTER)) || ((mSeedType == SEED_SNOWPEA)))) || ((mSeedType == SEED_REPEATER))))
            {
                this.GetPeaHeadOffset(aOffsetX, aOffsetY);
                aOriginX = ((mX + aOffsetX) + 30);
                aOriginY = ((mY + aOffsetY) + 5);
            };
            Projectile aProjectile =mBoard.AddProjectile(aOriginX ,aOriginY ,mRenderOrder ,theRow ,aProjectileType );
        }
        public void  GetPeaHeadOffset (int theOffsetX ,int theOffsetY ){
        }
        public void  UpdateShooting (){
            if (mShootingCounter == 0)
            {
                return;
            };
            mShootingCounter--;
            if (mShootingCounter == 1)
            {
                this.Fire(null, mRow, WEAPON_PRIMARY);
            };
            if (mShootingCounter != 0)
            {
                return;
            };
            mShootingCounter = 1;
        }

        public  PeaShooter (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
            this.app = app;
            mBoard = theBoard;
            mPlantCol = theGridX;
            mRow = theGridY;
            if (mBoard!=null)
            {
                mX = mBoard.GridToPixelX(theGridX, theGridY)/2;
                mY = mBoard.GridToPixelY(theGridX, theGridY)/2;
            };
            mAnimCounter = 0;
            mAnimPing = true;
            mFrame = 0;
            mShootingCounter = 0;
            mFrameLength = 18;
            mNumFrames = 5;
            mDead = false;
            mSquished = false;
            mSeedType = theSeedType;
            mPlantHealth = 300;
            mDoSpecialCountdown = 0;
            mDisappearCountdown = 200;
            mTargetX = -1;
            mTargetY = -1;
            mStateCountdown = 0;
            mStartRow = mRow;
            mBlinkCountdown = 0;
            mRecentlyEatenCountdown = 0;
            mEatenFlashCountdown = 0;
            mWidth = 54;
            mHeight = 54;
            mLaunchRate = 150;
            mReanimationType = REANIM_PEASHOOTER;
            mBodyReanimation = app.reanimator().createReanimation(PVZReanims.REANIM_PEASHOOTERSINGLE);
            mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            mBodyReanimation.currentTrack("anim_idle");
            mLaunchCounter = TodCommon.RandRangeInt(0, mLaunchRate);
            if (mBodyReanimation!=null)
            {
                mBodyReanimation.animRate(TodCommon.RandRangeFloat(15, 20));
                mHeadReanimation = app.reanimator().createReanimation(PVZReanims.REANIM_PEASHOOTERSINGLE);
                mHeadReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                mHeadReanimation.animRate(mBodyReanimation.animRate());
                mHeadReanimation.currentTrack("anim_head_idle");
                mBodyReanimation.attachReanimation(mHeadReanimation, "anim_stem",0,0);
            };
            mPlantMaxHealth = mPlantHealth;
        }
    }


