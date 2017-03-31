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
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.PVZApp;

    public class Squash extends CPlant {

        private static final int SQUASH_RISE_TIME =50;
        private static final int DAMAGE_FREEZE =0;
        private static final int DAMAGE_BYPASSES_SHIELD =2;
        private static final int DAMAGE_DOESNT_CAUSE_FLASH =1;
        private static final int SQUASH_FALL_HEIGHT =120;
        private static final int SQUASH_LOOK_TIME =80;
        private static final int DAMAGE_HITS_SHIELD_AND_BODY =3;
        private static final int SQUASH_FALL_TIME =10;
        private static final int SQUASH_DONE_TIME =100;
        private static final int DAMAGE_DOESNT_LEAVE_BODY =4;
        private static final int SQUASH_PRE_LAUNCH_TIME =45;

        public void  DoSquashDamage (){
            Zombie aZombie ;
            int aRowDiff ;
            Rectangle aZombieRect ;
            int aRectOverlap ;
            int aRange ;
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
                    aZombieRect = aZombie.GetZombieRect();
                    aRectOverlap = mBoard.GetRectOverlap(aAttackRect, aZombieRect);
                    aRange = 0;
                    if (aRectOverlap > aRange)
                    {
                        aZombie.TakeDamage(1800, DAMAGE_DOESNT_LEAVE_BODY);
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
            this.UpdateSquash();
            Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanimColor();
            UpdateReanim();
        }
        public Zombie  FindSquashTarget (){
            Zombie aZombie ;
            int aRowDiff ;
            Rectangle aZombieRect ;
            int aRange ;
            int aDist ;
            int aCheckLeftDistance ;
            Rectangle aAttackRect =GetPlantAttackRect(WEAPON_PRIMARY );
            int aClosestRange =0;
            Zombie aClosestZombie =null;
			for(int i =0; i<mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)mBoard.mZombies.elementAt(i);
                aRowDiff = (aZombie.mRow - mRow);
                if (aRowDiff != 0)
                {
                }
                else
                {
                    if (!aZombie.mHasHead)
                    {
                    }
                    else
                    {
                        aZombieRect = aZombie.GetZombieRect();
                        if (!(((aZombie.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT)) && ((aZombieRect.x < (mX + 20)))))
                        {
                            if ((((aZombie.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT)) || ((aZombie.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT))))
                            {
                                continue;
                            };
                        };
                        aRange = 70;
                        if (aZombie.mIsEating)
                        {
                            aRange = 110;
                        };
                        aDist = -(mBoard.GetRectOverlap(aAttackRect, aZombieRect));
                        if (aDist > aRange)
                        {
                        }
                        else
                        {
                            aCheckLeftDistance = aAttackRect.x;
                            if ((((aZombie.mZombiePhase == PHASE_POLEVAULTER_POST_VAULT)) || ((aZombie.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT))))
                            {
                                aCheckLeftDistance = (aAttackRect.x - 60);
                            };
                            if ((aZombieRect.x + aZombieRect.width()) < aCheckLeftDistance)
                            {
                            }
                            else
                            {
                                if (aZombie == mTargetZombie)
                                {
                                    return (aZombie);
                                };
                                if ( aClosestZombie==null || aDist < aClosestRange)
                                {
                                    aClosestZombie = aZombie;
                                    aClosestRange = aDist;
                                };
                            };
                        };
                    };
                };
            };
            return (aClosestZombie);
        }
        public void  UpdateSquash (){
            Zombie aZombie ;
            int aStartX ;
            int aStartY ;
            int aOffsetY ;
            if (mState == STATE_NOTREADY)
            {
                aZombie = this.FindSquashTarget();
                if (aZombie == null)
                {
                    return;
                };
                mTargetZombie = aZombie;
                mTargetX = (int)(aZombie.ZombieTargetLeadX(0)) - (mWidth / 2);
                mState = STATE_SQUASH_LOOK;
                mStateCountdown = SQUASH_LOOK_TIME;
                if (mTargetX < mX)
                {
                    mBodyReanimation.currentTrack("anim_lookleft");
                    mBodyReanimation.animRate(24);
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                }
                else
                {
                    mBodyReanimation.currentTrack("anim_lookright");
                    mBodyReanimation.animRate(24);
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                };
                app.foleyManager().playFoley(PVZFoleyType.SQUASH_HMM);
                return;
            };
            if (mState == STATE_SQUASH_LOOK)
            {
                if (mStateCountdown == 0)
                {
                    mBodyReanimation.currentTrack("anim_jumpup");
                    mBodyReanimation.animRate(24);
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                    mState = STATE_SQUASH_PRE_LAUNCH;
                    mStateCountdown = SQUASH_PRE_LAUNCH_TIME;
                };
                return;
            };
            if (mState == STATE_SQUASH_PRE_LAUNCH)
            {
                if (mStateCountdown == 0)
                {
                    aZombie = this.FindSquashTarget();
                    if (aZombie!=null)
                    {
                        mTargetX = (int)(aZombie.ZombieTargetLeadX(30)) - (mWidth / 2);
                    };
                    mState = STATE_SQUASH_RISING;
                    mStateCountdown = SQUASH_RISE_TIME;
                };
                return;
            };
            int aTargetCol =mBoard.PixelToGridXKeepOnBoard(mTargetX ,mY );
            int aDestX =mTargetX ;
            int aDestY =(mBoard.GridToPixelY(aTargetCol ,mRow )+8);
            if (mState == STATE_SQUASH_RISING)
            {
                aStartX = mBoard.GridToPixelX(mPlantCol, mStartRow);
                aStartY = mBoard.GridToPixelY(mPlantCol, mStartRow);
                mX = TodCommon.TodAnimateCurve(SQUASH_RISE_TIME, 20, mStateCountdown, aStartX, aDestX, TodCommon.CURVE_EASE_IN_OUT);
                mY = TodCommon.TodAnimateCurve(SQUASH_RISE_TIME, 20, mStateCountdown, aStartY, (aDestY - SQUASH_FALL_HEIGHT), TodCommon.CURVE_EASE_IN_OUT);
                if (mStateCountdown == 0)
                {
                    mBodyReanimation.currentTrack("anim_jumpdown");
                    mBodyReanimation.animRate(24);
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                    mState = STATE_SQUASH_FALLING;
                    mStateCountdown = SQUASH_FALL_TIME;
                };
            }
            else
            {
                if (mState == STATE_SQUASH_FALLING)
                {
                    mY = TodCommon.TodAnimateCurve(SQUASH_FALL_TIME, 0, mStateCountdown, (aDestY - SQUASH_FALL_HEIGHT), aDestY, TodCommon.CURVE_LINEAR);
                    if (mStateCountdown == 5)
                    {
                        this.DoSquashDamage();
                        app.foleyManager().playFoley(PVZFoleyType.SPLAT);
                    };
                    if (mStateCountdown == 0)
                    {
                        mState = STATE_SQUASH_DONE_FALLING;
                        mStateCountdown = SQUASH_DONE_TIME;
                        mBoard.ShakeBoard(1, 4);
                        app.foleyManager().playFoley(PVZFoleyType.THUMP);
                        aOffsetY = 80;
                    };
                }
                else
                {
                    if (mState == STATE_SQUASH_DONE_FALLING)
                    {
                        if (mStateCountdown == 0)
                        {
                            Die();
                        };
                    };
                };
            };
        }

        public  Squash (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
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
            Reanimation aBodyReanim =app.reanimator().createReanimation(PVZReanims.REANIM_SQUASH );
            aBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            aBodyReanim.animRate(TodCommon.RandRangeFloat(10, 15));
            aBodyReanim.currentTrack("anim_idle");
            mBodyReanimation = aBodyReanim;
            mLaunchCounter = TodCommon.RandRangeInt(300, (mLaunchRate / 2));
        }
    }


