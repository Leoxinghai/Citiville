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
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.PVZApp;

    public class ExplodoNut extends CPlant {

         public void  Update (){
            if (mStateCountdown > 0)
            {
                mStateCountdown--;
            };
            if (mBoard.IsWallnutBowlingLevel())
            {
                UpdateBowling();
            };
            this.AnimateNuts();
            Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanim();
        }
        public void  AnimateNuts (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            if (mRecentlyEatenCountdown > 0)
            {
                mRecentlyEatenCountdown--;
            };
            if (mEatenFlashCountdown > 0)
            {
                mEatenFlashCountdown--;
            };
            if (mPlantHealth < (mPlantMaxHealth / 3))
            {
                mBodyReanimation.currentTrack("anim_idle3");
            }
            else
            {
                if (mPlantHealth < ((mPlantMaxHealth * 2) / 3))
                {
                    mBodyReanimation.currentTrack("anim_idle2");
                };
            };
            if (mRecentlyEatenCountdown > 0)
            {
                mBodyReanimation.animRate(0.1);
            }
            else
            {
                mBodyReanimation.animRate(TodCommon.RandRangeFloat(10, 15));
            };
        }

        public  ExplodoNut (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
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
            mState = STATE_NOTREADY;
            mDead = false;
            mSquished = false;
            mSeedType = theSeedType;
            mPlantHealth = 4000;
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
            mLaunchRate = 2500;
            mReanimationType = REANIM_WALLNUT;
            Reanimation aBodyReanim =app.reanimator().createReanimation(PVZReanims.REANIM_WALLNUT );
            if (mBoard.IsWallnutBowlingLevel())
            {
                aBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                aBodyReanim.animRate(TodCommon.RandRangeFloat(12, 18));
                aBodyReanim.currentTrack("_ground");
                app.foleyManager().playFoley(PVZFoleyType.BOWLING);
            }
            else
            {
                aBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                aBodyReanim.animRate(TodCommon.RandRangeFloat(10, 15));
                aBodyReanim.currentTrack("anim_idle1");
            };
            aBodyReanim.useColor = true;
            aBodyReanim.overrideColor = Color.RGB(1, (64 / 0xFF), (64 / 0xFF));
            mBodyReanimation = aBodyReanim;
            mLaunchCounter = TodCommon.RandRangeInt(300, (mLaunchRate / 2));
            mPlantMaxHealth = mPlantHealth;
        }
    }


