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
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.PVZApp;

    public class CherryBomb extends CPlant {

         public void  Update (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            Animate();
            if (mPlantHealth < 0)
            {
                Die();
            };
            UpdateReanimColor();
            UpdateReanim();
            if (mDoSpecialCountdown > 0)
            {
                mDoSpecialCountdown--;
                if (mDoSpecialCountdown == 0)
                {
                    this.DoSpecial();
                };
            };
        }
         public void  DoSpecial (){
            int aPosX =(mX +(mWidth /2));
            int aPosY =(mY +(mHeight /2));
            app.foleyManager().playFoley(PVZFoleyType.CHERRYBOMB);
            app.foleyManager().playFoley(PVZFoleyType.JUICY);
            mBoard.KillAllZombiesInRadius(mRow, aPosX, aPosY, 115, 1, true);
            ParticleSystem anEffect =app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_POWIE );
            anEffect.setPosition(aPosX, aPosY);
            mBoard.mRenderManager.add(new ParticleRenderable(anEffect, 0));
            mBoard.ShakeBoard(10, -15);
            Die();
        }

        public  CherryBomb (int theGridX ,int theGridY ,int theSeedType ,PVZApp app ,Board theBoard ){
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
            mReanimationType = REANIM_CHERRYBOMB;
            mBodyReanimation = app.reanimator().createReanimation(PVZReanims.REANIM_CHERRYBOMB);
            if (IsInPlay())
            {
                mDoSpecialCountdown = 100;
                mBodyReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                mBodyReanimation.currentTrack("anim_explode");
                mBodyReanimation.animRate(TodCommon.RandRangeFloat(10, 15));
                app.foleyManager().playFoley(PVZFoleyType.REVERSE_EXPLOSION);
            };
        }
    }


