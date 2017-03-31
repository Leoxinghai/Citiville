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

import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZFoleyType;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.resources.PVZReanims;

    public class LawnMower {

        private static final int PHASE_ZOMBIE_BURNED =2;
        public static final int MOWER_ROLLING_IN =0;
        private static final int PHASE_POLEVAULTER_POST_VAULT =6;
        private static final int PHASE_ZOMBIE_MOWERED =3;
        private static final int PHASE_POLEVAULTER_IN_VAULT =5;
        public static final int LAWNMOWER_LAWN =0;
        public static final int MOWER_TRIGGERED =2;
        private static final int PHASE_ZOMBIE_DYING =1;
        public static final int MOWER_SQUISHED =3;
        public static final int MOWER_READY =1;
        private static final int BOARD_WIDTH =540;
        public static final int CHOMP_TIME =50;
        private static final int PHASE_POLEVAULTER_PRE_VAULT =4;
        private static final int PHASE_ZOMBIE_NORMAL =0;

        public int mChompCounter ;
        public boolean mVisible ;

        public void  Draw (Graphics2D g ){
            int aShadowImageIndex ;
            int aScaleX ;
            int aScaleY ;
            int aShadowX ;
            int aShadowY ;
            if (!this.mVisible)
            {
                return;
            };
            if (this.mMowerState != MOWER_SQUISHED)
            {
                aShadowImageIndex = 0;
                aScaleX = 1;
                aScaleY = 1;
                aShadowX = (this.mPosX - 5);
                aShadowY = ((this.mPosY - this.mAltitude) + 30);
            };
            g.pushState();
            this.mReanim.x((this.mPosX + 4));
            this.mReanim.y((this.mPosY - this.mAltitude));
            this.mReanim.draw(g);
            g.popState();
        }

        public int mSquishedCounter ;
        public int mLastPortalX ;
        public int mPosX ;
        public int mPosY ;
        public Reanimation mReanim ;
        public int mMowerHeight ;
        public int mRollingInCounter ;

        public void  Die (){
            this.mDead = true;
        }

        public PVZApp app ;

        public void  StartMower (){
            if (this.mMowerState == MOWER_TRIGGERED)
            {
                return;
            };
            this.mReanim.animRate(70);
            this.app.foleyManager().playFoley(PVZFoleyType.LAWNMOWER);
            this.mBoard.mWaveRowGotLawnMowered.add(this.mRow,this.mBoard.mCurrentWave);
            this.mBoard.mTriggeredLawnMowers++;
            this.mMowerState = MOWER_TRIGGERED;
        }

        public int mAnimTicksPerFrame ;
        public int mRenderOrder ;

        public Rectangle  GetLawnMowerAttackRect (){
            Rectangle aRect =new Rectangle(this.mPosX ,this.mPosY ,50,54);
            return (aRect);
        }

        public int mRow ;
        public int mMowerType ;

        public void  Update (){
            Zombie aZombie ;
            int LAWN_MOWER_ROLL_IN_TIME ;
            int aRowDiff ;
            Rectangle aZombieRect ;
            int aOverlap ;
            int aRange ;
            if (this.mMowerState == MOWER_SQUISHED)
            {
                this.mSquishedCounter--;
                if (this.mSquishedCounter <= 0)
                {
                    this.Die();
                };
                return;
            };
            if (this.mMowerState == MOWER_ROLLING_IN)
            {
                this.mRollingInCounter++;
                LAWN_MOWER_ROLL_IN_TIME = 100;
                this.mPosX = (int)TodCommon.TodAnimateCurveFloat(0, LAWN_MOWER_ROLL_IN_TIME, this.mRollingInCounter, -160, -21, TodCommon.CURVE_EASE_IN_OUT);
                if (this.mRollingInCounter == LAWN_MOWER_ROLL_IN_TIME)
                {
                    this.mMowerState = MOWER_READY;
                };
                return;
            };
            if (this.mBoard.mGameScene != PVZApp.SCENE_PLAYING)
            {
                return;
            };
            Rectangle aAttackRect =this.GetLawnMowerAttackRect ();
			for(int i =0; i<this.mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mBoard.mZombies.elementAt(i);
                aRowDiff = (aZombie.mRow - this.mRow);
                if (aRowDiff != 0)
                {
                }
                else
                {
                    if (aZombie.mZombiePhase == PHASE_ZOMBIE_MOWERED)
                    {
                    }
                    else
                    {
                        aZombieRect = aZombie.GetZombieRect();
                        aOverlap = this.mBoard.GetRectOverlap(aAttackRect, aZombieRect);
                        aRange = 0;
                        if (aOverlap <= aRange)
                        {
                        }
                        else
                        {
                            if (this.mMowerState == MOWER_READY)
                            {
                                if (!aZombie.mHasHead)
                                {
                                    continue;
                                };
                            };
                            this.MowZombie(aZombie);
                        };
                    };
                };
            };
            if (((!((this.mMowerState == MOWER_TRIGGERED))) && (!((this.mMowerState == MOWER_SQUISHED)))))
            {
                return;
            };
            double aSpeed =2.33;
            if (this.mChompCounter > 0)
            {
                this.mChompCounter--;
                aSpeed = TodCommon.TodAnimateCurveFloat(CHOMP_TIME, 0, this.mChompCounter, aSpeed, 1, TodCommon.CURVE_BOUNCE_SLOW_MIDDLE);
            };
            this.mPosX = (int)(this.mPosX + aSpeed);
            this.mPosY = (this.mBoard.GetPosYBasedOnRow((this.mPosX + 28), this.mRow) + 16);
            if (this.mPosX > BOARD_WIDTH)
            {
                this.Die();
            };
            this.mReanim.update();
        }

        public boolean mDead ;
        public int mAltitude ;
        public Board mBoard ;

        public void  MowZombie (Zombie theZombie ){
            if (this.mMowerState == MOWER_READY)
            {
                this.StartMower();
                this.mChompCounter = (CHOMP_TIME / 2);
            }
            else
            {
                if (this.mMowerState == MOWER_TRIGGERED)
                {
                    this.mChompCounter = CHOMP_TIME;
                };
            };
            this.app.foleyManager().playFoley(PVZFoleyType.SPLAT);
            theZombie.MowDown();
        }

        public int mMowerState ;

        public  LawnMower (PVZApp app ,Board theBoard ,int theRow ){
            this.app = app;
            this.mBoard = theBoard;
            this.mRow = theRow;
            this.mPosX = 20;//-108;
            this.mPosY = (this.mBoard.GetPosYBasedOnRow((this.mPosX + 28), theRow) + 10);

//            this.mPosX = this.mPosX / 2; 
            this.mPosY = this.mPosY;
            
            this.mDead = false;
            this.mMowerState = MOWER_READY;
            this.mVisible = true;
            this.mChompCounter = 0;
            this.mRollingInCounter = 0;
            this.mSquishedCounter = 0;
            this.mLastPortalX = -1;
            this.mMowerType = LAWNMOWER_LAWN;
            this.mAltitude = 0;
            this.mReanim = app.reanimator().createReanimation(PVZReanims.REANIM_LAWNMOWER);
            this.mReanim.animRate(0);
            this.mReanim.x(this.mPosX);
            this.mReanim.y(this.mPosY);
            this.mReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            this.mReanim.currentTrack("anim_normal");
        }
    }


