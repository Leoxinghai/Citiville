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

import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.logic.GridItem;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopQueue;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.PVZApp;

    public class GraveBuster extends CPlant {

         public void  Update (){
            ParticleSystem anEffect ;
            GridItem aGraveStone ;
            System.out.println("GraveBuster.1");
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            System.out.println("GraveBuster.2");
            Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanimColor();
            UpdateReanim();
            if (mStateCountdown > 0)
            {
                mStateCountdown--;
            };
            mY = (mBoard.GridToPixelY(mPlantCol, mRow) - 20);
            int aOffsetY =0;
            if (mState == STATE_GRAVEBUSTER_EATING)
            {
                aOffsetY = (int)TodCommon.TodAnimateCurveFloat(GRAVE_BUSTER_EAT_TIME, 0, mStateCountdown, 0, 20, TodCommon.CURVE_LINEAR);
            };
            mY = (mY + aOffsetY);
            if (mState == STATE_GRAVEBUSTER_LANDING)
            {
                if (mBodyReanimation.currentTrack().equals("anim_idle"))
                {
                    mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                    mBodyReanimation.animRate(12);
                    mStateCountdown = GRAVE_BUSTER_EAT_TIME;
                    mState = STATE_GRAVEBUSTER_EATING;
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_GRAVEBUSTER);
                    anEffect.setPosition((mX + 24), (mY + 40));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_PARTICLE));
                };
            }
            else
            {
                if (mState == STATE_GRAVEBUSTER_EATING)
                {
                    if (mStateCountdown == 0)
                    {
                        aGraveStone = mBoard.GetGraveStoneAt(mPlantCol, mRow);
                        if (aGraveStone!=null)
                        {
                            aGraveStone.GridItemDie();
                            mBoard.mGravesCleared++;
                        };
                        anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_GRAVEBUSTERDIE);
                        anEffect.setPosition((mX + 27), (mY + 54));
                        mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_PARTICLE));
                        Die();
                    };
                };
            };
        }

        public  GraveBuster (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
            super();
            Array anArray ;
            this.app = app;
            mBoard = theBoard;
            mPlantCol = theGridX;
            mRow = theGridY;
            if (mBoard!=null)
            {
                mX = mBoard.GridToPixelX(theGridX, theGridY);
                mY = mBoard.GridToPixelY(theGridX, theGridY);
            };
            int aOffsetX =3;
            int aOffsetY =-20;
            mX = (mX + aOffsetX);
            mY = (mY + aOffsetY);
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
            mReanimationType = REANIM_CHERRYBOMB;
            mBodyReanimation = app.reanimator().createReanimation(PVZReanims.REANIM_GRAVEBUSTER);
            if (IsInPlay())
            {
                mBodyReanimation.currentTrack("anim_land");
                mBodyReanimation.animRate(TodCommon.RandRangeFloat(10, 15));
                anArray = new Array("anim_idle");
                mBodyReanimation.loopType(new ReanimLoopQueue(anArray));
                mState = STATE_GRAVEBUSTER_LANDING;
                app.foleyManager().playFoley(PVZFoleyType.GRAVEBUSTERCHOMP);
            };
        }
    }


