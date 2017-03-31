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
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.PVZApp;

    public class Chomper extends CPlant {

        public void  UpdateChomper (){
            Zombie aZombie ;
            if (mState == STATE_READY)
            {
                aZombie = FindTargetZombie(mRow, WEAPON_PRIMARY);
                if (aZombie!=null)
                {
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                    mBodyReanimation.animRate(24);
                    mBodyReanimation.currentTrack("anim_bite");
                    mState = STATE_CHOMPER_BITING;
                    mStateCountdown = 70;
                };
            }
            else
            {
                if (mState == STATE_CHOMPER_BITING)
                {
                    if (mStateCountdown == 0)
                    {
                        app.foleyManager().playFoley(PVZFoleyType.BIGCHOMP);
                        aZombie = FindTargetZombie(mRow, WEAPON_PRIMARY);
                        if ( aZombie!=null && aZombie.mZombiePhase != PHASE_POLEVAULTER_IN_VAULT && aZombie.mZombiePhase != PHASE_POLEVAULTER_PRE_VAULT)
                        {
                            app.foleyManager().playFoley(PVZFoleyType.GULP);
                            aZombie.DieWithLoot();
                            mState = STATE_CHOMPER_BITING_GOT_ONE;
                        }
                        else
                        {
                            mState = STATE_CHOMPER_BITING_MISSED;
                        };
                        mStateCountdown = 40;
                    };
                }
                else
                {
                    if (mState == STATE_CHOMPER_BITING_GOT_ONE)
                    {
                        if (mStateCountdown == 0)
                        {
                            mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                            mBodyReanimation.animRate(15);
                            mBodyReanimation.currentTrack("anim_chew");
                            mState = STATE_CHOMPER_DIGESTING;
                            mStateCountdown = 4000;
                        };
                    }
                    else
                    {
                        if (mState == STATE_CHOMPER_DIGESTING)
                        {
                            if (mStateCountdown == 0)
                            {
                                mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                                mBodyReanimation.animRate(12);
                                mBodyReanimation.currentTrack("anim_swallow");
                                mState = STATE_CHOMPER_SWALLOWING;
                                mStateCountdown = 230;
                            };
                        }
                        else
                        {
                            if ((((mState == STATE_CHOMPER_SWALLOWING)) || ((mState == STATE_CHOMPER_BITING_MISSED))))
                            {
                                if (mStateCountdown == 0)
                                {
                                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                                    mBodyReanimation.animRate(TodCommon.RandRangeFloat(13, 17));
                                    mBodyReanimation.currentTrack("anim_idle");
                                    mState = STATE_READY;
                                };
                            };
                        };
                    };
                };
            };
        }
         public void  Update (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            if (mStateCountdown > 0)
            {
                mStateCountdown--;
            };
            this.UpdateChomper();
            Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanimColor();
            UpdateReanim();
        }

        public  Chomper (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
            this.app = app;
            mBoard = theBoard;
            mPlantCol = theGridX;
            mRow = theGridY;
            if (mBoard!=null)
            {
                mX = (mBoard.GridToPixelX(theGridX, theGridY) + 5);
                mY = mBoard.GridToPixelY(theGridX, theGridY);
            };
            mAnimCounter = 0;
            mAnimPing = true;
            mFrame = 0;
            mShootingCounter = 0;
            mFrameLength = 18;
            mNumFrames = 5;
            mState = STATE_READY;
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
            mLaunchRate = 2500;
            mReanimationType = REANIM_SUNFLOWER;
            Reanimation aBodyReanim =app.reanimator().createReanimation(PVZReanims.REANIM_CHOMPER);
            aBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            aBodyReanim.animRate(TodCommon.RandRangeFloat(13, 17));
            aBodyReanim.currentTrack("anim_idle");
            mBodyReanimation = aBodyReanim;
            mLaunchCounter = TodCommon.RandRangeInt(300, (mLaunchRate / 2));
        }
    }


