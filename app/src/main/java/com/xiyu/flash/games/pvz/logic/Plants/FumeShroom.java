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

import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopOnceAndIdle;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.Board;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;

    public class FumeShroom extends CPlant {

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
            if (aZombie ==null)
            {
                return (false);
            };
            System.out.println("FumeShroom.FindTargetAndFire");
            double idleRate =TodCommon.RandRangeFloat(15,20);
            mBodyReanimation.animRate(35);
            mBodyReanimation.currentTrack("anim_shooting");
            mBodyReanimation.loopType(new ReanimLoopOnceAndIdle("anim_idle", idleRate));
            mShootingCounter = 50;
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
            this.DoRowAreaDamage(20);
            app.foleyManager().playFoley(PVZFoleyType.FUME);
        }
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
        public void  DoRowAreaDamage (int theDamage ){
            Zombie aZombie ;
            int aRowDiff ;
            Rectangle aZombieRect ;
            int aOverlap ;
            int aFinalDamage ;
            Rectangle aAttackRect =GetPlantAttackRect(WEAPON_PRIMARY );
			for(int i =0; i<mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)mBoard.mZombies.elementAt(i);
                aRowDiff = (aZombie.mRow - mRow);
                if (aRowDiff != 0)
                {
                }
                else
                {
                    if (!aZombie.EffectedByDamage())
                    {
                    }
                    else
                    {
                        aZombieRect = aZombie.GetZombieRect();
                        aOverlap = mBoard.GetRectOverlap(aAttackRect, aZombieRect);
                        if (aOverlap <= 0)
                        {
                        }
                        else
                        {
                            aFinalDamage = theDamage;
                            aZombie.TakeDamage(aFinalDamage, DAMAGE_HITS_SHIELD_AND_BODY);
                            app.foleyManager().playFoley(PVZFoleyType.SPLAT);
                        };
                    };
                };
            };
        }
        public void  UpdateShooting (){
            ParticleSystem anEffect ;
            if (mShootingCounter == 0)
            {
                return;
            };
            mShootingCounter--;
            if (mSeedType == SEED_FUMESHROOM)
            {
                if (mShootingCounter == 15)
                {
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_FUMECLOUD);
                    anEffect.setPosition((mX*3 + 58), (mY + 24));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_PARTICLE));
                };
            };
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

        public  FumeShroom (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
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
            mState = STATE_NOTREADY;
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
            mLaunchCounter = TodCommon.RandRangeInt(0, mLaunchRate);
            mBodyReanimation = app.reanimator().createReanimation(PVZReanims.REANIM_FUMESHROOM);
            mBodyReanimation.animRate(TodCommon.RandRangeFloat(15, 20));
            mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            mBodyReanimation.currentTrack("anim_idle");
            if ( IsNocturnal(mSeedType) && mBoard != null &&  mBoard.StageIsNight() )
            {
                SetSleeping(true);
            };
        }
    }


