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

import com.xiyu.util.*;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.logic.Coin;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;

    public class Sunflower extends CPlant {

        private static  String COIN_NONE ="none";
        private static  String COIN_MOTION_FROM_SKY ="from sky";
        private static  String COIN_MOTION_FROM_PLANT ="from plant";
        private static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
        private static  String COIN_SUN ="sun";

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
         public void  Update (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            this.UpdateAbilities();
            this.Animate();
            UpdateReanimColor();
            UpdateReanim();
        }
        public void  UpdateProductionPlant (){
            int aLightUpAmount ;
            if (mBoard.HasLevelAwardDropped())
            {
                return;
            };
            mLaunchCounter--;
            if (mLaunchCounter <= 100)
            {
                aLightUpAmount = TodCommon.TodAnimateCurve(100, 0, mLaunchCounter, 0, 100, TodCommon.CURVE_LINEAR);
                mEatenFlashCountdown = Math.max(mEatenFlashCountdown, aLightUpAmount);
            };
            if (mLaunchCounter <= 0)
            {
                mLaunchCounter = TodCommon.RandRangeInt((mLaunchRate - 150), mLaunchRate);
                app.foleyManager().playFoley(PVZFoleyType.SPAWN_SUN);
                mBoard.AddCoin(mX, mY, COIN_SUN, COIN_MOTION_FROM_PLANT);
                //mBoard.AddCoin(mX, mY, Coin.COIN_DYNAMITE, COIN_MOTION_FROM_PLANT);
            };
        }
        public void  UpdateAbilities (){
            if (!IsInPlay())
            {
                return;
            };
            if (mStateCountdown > 0)
            {
                mStateCountdown--;
            };
            this.UpdateProductionPlant();
            if (mDoSpecialCountdown > 0)
            {
                mDoSpecialCountdown--;
                if (mDoSpecialCountdown == 0)
                {
                };
            };
        }

        public  Sunflower (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
            this.app = app;
            mBoard = theBoard;
            mPlantCol = theGridX;
            mRow = theGridY;
            if (mBoard!=null)
            {
                mX = mBoard.GridToPixelX(theGridX, theGridY);
                mY = mBoard.GridToPixelY(theGridX, theGridY);
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
            mHighlighted = false;
            mWidth = 54;
            mHeight = 54;
            mLaunchRate = 2500;
            mReanimationType = REANIM_SUNFLOWER;
            Reanimation aBodyReanim =app.reanimator().createReanimation(PVZReanims.REANIM_SUNFLOWER );
            aBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            aBodyReanim.animRate(TodCommon.RandRangeFloat(10, 15));
            aBodyReanim.currentTrack("anim_idle");
            mBodyReanimation = aBodyReanim;
            mLaunchCounter = TodCommon.RandRangeInt(150, (mLaunchRate / 2));
        }
    }


