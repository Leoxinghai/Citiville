package com.xiyu.flash.games.pvz.logic.Zombies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
import com.xiyu.flash.games.pvz.logic.GameObject;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.logic.Plants.CPlant;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.MessageWidget;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.renderables.ReanimationRenderable;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.resources.images.ImageData;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
//import flash.geom.Point;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopQueue;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.resources.reanimator.ReanimLoopType;
import com.xiyu.flash.framework.resources.particles.ParticleEmitter;
import com.xiyu.flash.framework.resources.reanimator.ReanimTrack;

import java.lang.Math;

import com.pgh.mahjong.*;
import com.pgh.mahjong.resource.MahJongImages;


    public class Zombie extends GameObject {

        private static final int GRIDSIZEX =9;
        public static final int SEED_PUFFSHROOM =8;
        private static  String COIN_MOTION_COIN ="coin";
        private static final int HEIGHT_ZOMBIE_NORMAL =0;
        private static final int PHASE_ZOMBIE_BURNED =2;
        public static final int SEED_PEASHOOTER =0;
        private static final int DAMAGE_HITS_SHIELD_AND_BODY =1;
        private static double CLIP_HEIGHT_LIMIT =-67.5;
        private static final int ATTACKTYPE_VAULT =1;

        public static ZombieDrawPosition mScratchPosition =new ZombieDrawPosition ();

        private static final int PHASE_ZOMBIE_DYING =1;
        private static final int DAMAGE_SPIKE =5;
        public static final int ZOMBIE_NORMAL =0;
        public static final int ZOMBIE_WAVE_DEBUG =-1;
        public static final int ZOMBIE_DOOR =6;
        private static final int LAWN_XMIN =40;
        private static final int SCENE_CHALLENGE =7;
        public static final int SEED_EXPLODE_O_NUT =50;
        private static  String COIN_NONE ="none";
        public static final int SEED_SUNFLOWER =1;
        private static final int SCENE_PLAYING =3;
        private static final int PHASE_ZOMBIE_NORMAL =0;
        private static final int LAWN_XMAX =760;
        private static final int SCENE_CREDIT =6;
        public static final int SEED_NONE =-1;
        private static final int SCENE_LOADING =0;
        public static final int SEED_CHOMPER =6;
        public static final int ZOMBIE_PAIL =4;
        private static final int HEIGHT_FALLING =1;
        public static final int SEED_CHERRYBOMB =2;
        private static final int SHIELDTYPE_DOOR =1;
        private static final int SCENE_MENU =1;
        private static  String COIN_SHOVEL ="shovel";
        private static final int TICKS_BETWEEN_EATS =4;
        private static final int HELMTYPE_PAIL =2;
        private static final int SCENE_LEVEL_INTRO =2;
        private static final int HELMTYPE_FOOTBALL =3;
        private static final int SHIELDTYPE_NEWSPAPER =2;
        public static final int ZOMBIE_FOOTBALL =7;
        private static  Color gChilledColor =Color.ARGB(1,(int)0.29,(int)0.29,1);
        private static final int DAMAGE_BYPASSES_SHIELD =0;
        private static final int DAMAGE_FREEZE =2;
        private static  String COIN_MOTION_FROM_PLANT ="from plant";
        private static final int ATTACKTYPE_CHEW =0;
        private static  String COIN_FINAL_SEED_PACKET ="finalseedpacket";
        public static final int ZOMBIE_WAVE_UI =-3;
        private static double CHILLED_SPEED_FACTOR =0.4;
        private static int CLIP_HEIGHT_OFF =-135;
        private static  String COIN_PRESENT_PLANT ="presentplant";
        private static final int PHASE_NEWSPAPER_MAD =9;
        public static final int SEED_SUNSHROOM =9;
        private static final int LAWN_YMIN =80;
        private static final int PHASE_POLEVAULTER_IN_VAULT =5;
        public static final int SEED_WALLNUT =3;
        public static final int SEED_REPEATER =7;
        public static final int ZOMBIE_NEWSPAPER =5;
        public static final int SEED_FUMESHROOM =10;
        private static final int HELMTYPE_NONE =0;
        private static  String COIN_MOTION_FROM_SKY ="from sky";
        private static final int SCENE_AWARD =5;
        private static  String COIN_SUN ="sun";
        public static final int ZOMBIE_WAVE_WINNER =-4;
        private static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
        private static final int HELMTYPE_TRAFFIC_CONE =1;
        public static final int ZOMBIE_WAVE_CUTSCENE =-2;
        private static final int DAMAGE_DOESNT_LEAVE_BODY =4;
        public static final int SEED_SNOWPEA =5;
        private static  String COIN_USABLE_SEED_PACKET ="usableseedpacket";
        public static final int ZOMBIE_INVALID =-1;
        private static final int PHASE_POLEVAULTER_PRE_VAULT =4;
        private static final int PHASE_NEWSPAPER_READING =7;
        public static final int ZOMBIE_TRAFFIC_CONE =2;
        private static final int BOARD_HEIGHT = 800;//405;
        private static final int LAWN_YMAX =620;
        public static final int SEED_SQUASH =4;
        private static final int PHASE_POLEVAULTER_POST_VAULT =6;
        private static final int SCENE_ZOMBIES_WON =4;
        private static final int PHASE_ZOMBIE_MOWERED =3;
        private static final int PHASE_NEWSPAPER_MADDENING =8;
        private static  String COIN_NOTE ="note";
        private static final int SHIELDTYPE_NONE =0;
        public static final int ZOMBIE_FLAG =1;
        private static final int PHASE_RISING_FROM_GRAVE =10;
        public static final int SEED_LEFTPEATER =49;
        private static final int BOARD_WIDTH = 1500;//540;
        public static final int ZOMBIE_POLEVAULTER =3;
        private static final int DAMAGE_DOESNT_CAUSE_FLASH =3;
        private static final int GRIDSIZEY =5;
        public static final int SEED_GRAVEBUSTER =11;

        public int mZombieHeight;
        
        public boolean isMasked = false;
        public int maskCount = 0;
        public ImageInst mMjImage;
        public ImageInst mMaskImage;
        

        public void  CheckForBoardEdge (){
            double velocity ;
            int aWinPos =-50;
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                aWinPos = -75;
            }
            else
            {
                if (this.mZombieType == ZOMBIE_FOOTBALL)
                {
                    aWinPos = -100;
                };
            };
            if (mX <= aWinPos && this.mHasHead)
            {
                velocity = this.mBodyReanim.getTrackVelocity("_ground", true);
                this.mPosX = -95;
                mBoard.ZombiesWon(this);
            };
            if ((((mX <= (aWinPos + 35))) && (!(this.mHasHead))))
            {
                this.TakeDamage(1800, DAMAGE_DOESNT_CAUSE_FLASH);
            };
        }

        public int mAnimCounter ;

        public void  UpdateZombieWalking (){
            double aSpeed =0;
            ParticleSystem anEffect ;

            if (this.ZombieNotWalking())
            {
                return;
            };
            if (this.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT)
            {
                aSpeed = this.mVelX;
            }
            else
            {
                if (this.mBodyReanim != null)
                {
                    aSpeed = (double)this.mBodyReanim.getTrackVelocity("_ground",false);
                };
            };
            this.mPosX = (this.mPosX - aSpeed);
            if (this.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT)
            {
                if (this.mBodyReanim.shouldTriggerTimedEvent(0.16))
                {
                };
                if (this.mBodyReanim.shouldTriggerTimedEvent(0.67))
                {
                };
            };
            if ((((this.mZombieType == ZOMBIE_FOOTBALL)) && (!((this.mFromWave == ZOMBIE_WAVE_WINNER)))))
            {
                if (this.mBodyReanim.shouldTriggerTimedEvent(0.03))
                {
                };
                if (this.mBodyReanim.shouldTriggerTimedEvent(0.61))
                {
                };
            };
        }
        public boolean  ZombieNotWalking (){
            if (this.mIsEating)
            {
                return (true);
            };
            if (this.mZombiePhase == PHASE_NEWSPAPER_MADDENING)
            {
                return (true);
            };
            return (false);
        }

        public int mZombieFade ;

        public void  EatPlant (CPlant thePlant ){
            if ((((thePlant.mSeedType == CPlant.SEED_CHERRYBOMB)) || ((thePlant.mSeedType == CPlant.SEED_SQUASH))))
            {
                return;
            };
            this.StartEating();
            boolean aTriggerSpecial =false;
            if (aTriggerSpecial)
            {
                thePlant.DoSpecial();
                return;
            };
            if ((((this.mChilledCounter > 0)) && (((this.mZombieAge % 2) == 1))))
            {
                this.mBodyReanim.animRate ((this.mBodyReanim.animRate() * 0.5));
                return;
            };
            thePlant.mPlantHealth = (thePlant.mPlantHealth - TICKS_BETWEEN_EATS);
            thePlant.mRecentlyEatenCountdown = 50;
            if (thePlant.mPlantHealth <= 0)
            {
                app.foleyManager().playFoley(PVZFoleyType.GULP);
                thePlant.Die();
                if ((((((((((mBoard.mLevel >= 2)) && ((mBoard.mLevel <= 4)))) && ((thePlant.mPlantCol > 4)))) && ((mBoard.mPlants.length() < 15)))) && ((thePlant.mSeedType == SEED_PEASHOOTER))))
                {
                    mBoard.DisplayAdvice("[ADVICE_PEASHOOTER_DIED]", MessageWidget.MESSAGE_STYLE_HINT_TALL_FAST, Board.ADVICE_PEASHOOTER_DIED);
                };
            };
        }

        public boolean mOnHighGround ;
        private ZombieDrawPosition scratchDrawPos ;
        public boolean mFlatTires ;

        public void  AttachShield (){
            String aAttachTrack ="";
            if (this.mShieldType == SHIELDTYPE_DOOR)
            {
                this.ShowDoorArms(true);
                this.mBodyReanim.setTrackVisible("Zombie_outerarm_screendoor", true);
                aAttachTrack = "anim_screendoor1";
            }
            else
            {
                if (this.mShieldType == SHIELDTYPE_NEWSPAPER)
                {
                    this.mBodyReanim.setTrackVisible("Zombie_paper_hands", true);
                    this.mBodyReanim.setTrackVisible("Zombie_paper_paper2", false);
                    this.mBodyReanim.setTrackVisible("Zombie_paper_paper3", false);
                    aAttachTrack = "Zombie_paper_paper1";
                };
            };
            this.mBodyReanim.setTrackVisible(aAttachTrack, true);
        }

        public int mAltitude ;
        public int mButteredCounter ;
        public int mShieldHealth ;
        public boolean mHasArm;

        public void  ApplyBurn (){
            int aCharredPosX ;
            int aCharredPosY ;
            Reanimation aCharredReanim ;
            if (((this.mDead) || ((this.mZombiePhase == PHASE_ZOMBIE_BURNED))))
            {
                return;
            };
            if ((((this.mZombieType == ZOMBIE_FOOTBALL)) && ((this.mHelmHealth >= 1800))))
            {
                this.TakeDamage(1800, DAMAGE_HITS_SHIELD_AND_BODY);
                return;
            };
            if ((((this.mZombiePhase == PHASE_ZOMBIE_DYING)) || ((this.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT))))
            {
                this.DieWithLoot();
            }
            else
            {
                aCharredPosX = (int)this.mPosX;
                aCharredPosY = (int)(this.mPosY - 8);
                aCharredReanim = app.reanimator().createReanimation("REANIM_ZOMBIE_CHARRED");
                aCharredReanim.currentTrack("anim_crumble");
                aCharredReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                aCharredReanim.x(aCharredPosX);
                aCharredReanim.y(aCharredPosY);
                aCharredReanim.animRate(15);
                mBoard.mRenderManager.add(new ReanimationRenderable(aCharredReanim, (RENDER_LAYER_ZOMBIE + mRow),false));
                this.DieWithLoot();
            };
        }

        public int mTargetCol ;

        public CPlant  FindPlantTarget (int theAttackType ){
            CPlant aPlant ;
            Rectangle aPlantRect ;
            int aOverlap ;
            Rectangle aAttackRect =this.GetZombieAttackRect ();
			for(int i =0; i<mBoard.mPlants.length();i++)
			{
				aPlant = (CPlant)mBoard.mPlants.elementAt(i);
                if (mRow != aPlant.mRow)
                {
                }
                else
                {
                    aPlantRect = aPlant.GetPlantRect();
                    aOverlap = mBoard.GetRectOverlap(aAttackRect, aPlantRect);
                    if (aOverlap < 20)
                    {
                    }
                    else
                    {
                        if (!this.CanTargetPlant(aPlant, theAttackType))
                        {
                        }
                        else
                        {
                            return (aPlant);
                        };
                    };
                };
            };
            return (null);
        }

        public int mZombiePhase ;

        public boolean  IsImmobilizied (){
            return (false);
        }
        public void  StopEating (){
            if (!this.mIsEating)
            {
                return;
            };
            this.mIsEating = false;
            this.StartWalkAnim(20);
            this.UpdateAnimSpeed();
        }
        public boolean  SetupDrawZombieWon (Graphics2D g ){
            if (this.mFromWave != ZOMBIE_WAVE_WINNER)
            {
                return (true);
            };
            if (!mBoard.mShowZombieWalking)
            {
                return (false);
            };
            g.setClipRect((-85 - mX), (0 - mY), 540, 405,true);
            return (true);
        }
        public void  PickRandomSpeed (){
            this.mVelX = TodCommon.RandRangeFloat(0.23, 0.37);
            if (this.mZombiePhase == PHASE_NEWSPAPER_MAD)
            {
                this.mVelX = TodCommon.RandRangeFloat(0.89, 0.91);
            }
            else
            {
                if (this.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT)
                {
                    this.mVelX = TodCommon.RandRangeFloat(0.66, 0.68);
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_FOOTBALL)
                    {
                        this.mVelX = TodCommon.RandRangeFloat(0.66, 0.68);
                    }
                    else
                    {
                        if (this.mZombieType == ZOMBIE_FLAG)
                        {
                            this.mVelX = 0.45;
                        }
                        else
                        {
                            if (this.mVelX < 0.3)
                            {
                                this.mAnimTicksPerFrame = 12;
                            }
                            else
                            {
                                this.mAnimTicksPerFrame = 15;
                            };
                        };
                    };
                };
            };
            this.UpdateAnimSpeed();
        }
        public int  TakeHelmDamage (int theDamage ,int theDamageFlags ){
            if (theDamageFlags != DAMAGE_DOESNT_CAUSE_FLASH)
            {
                this.mJustGotShotCounter = 25;
            };
            int aOldIndex =this.GetHelmDamageIndex ();
            int aDamageApplied =Math.min(this.mHelmHealth ,theDamage );
            int aDamageRemaining =(theDamage -aDamageApplied );
            this.mHelmHealth = (this.mHelmHealth - aDamageApplied);
            if (theDamageFlags == DAMAGE_FREEZE)
            {
                this.ApplyChill(false);
            };
            if (this.mHelmHealth == 0)
            {
                this.DropHelm(theDamageFlags);
                return (aDamageRemaining);
            };
            int aNewIndex =this.GetHelmDamageIndex ();
            if (aOldIndex != aNewIndex)
            {
                if (this.mHelmType == HELMTYPE_TRAFFIC_CONE && aNewIndex == 1)
                {
                    this.mBodyReanim.setTrackVisible("anim_cone1", false);
                    this.mBodyReanim.setTrackVisible("anim_cone2", true);
                }
                else
                {
                    if (this.mHelmType == HELMTYPE_TRAFFIC_CONE && aNewIndex == 2)
                    {
                        this.mBodyReanim.setTrackVisible("anim_cone2", false);
                        this.mBodyReanim.setTrackVisible("anim_cone3", true);
                    }
                    else
                    {
                        if (this.mHelmType == HELMTYPE_PAIL && aNewIndex == 1)
                        {
                            this.mBodyReanim.setTrackVisible("anim_bucket1", false);
                            this.mBodyReanim.setTrackVisible("anim_bucket2", true);
                        }
                        else
                        {
                            if (this.mHelmType == HELMTYPE_PAIL && aNewIndex == 2)
                            {
                                this.mBodyReanim.setTrackVisible("anim_bucket2", false);
                                this.mBodyReanim.setTrackVisible("anim_bucket3", true);
                            }
                            else
                            {
                                if (this.mHelmType == HELMTYPE_FOOTBALL && aNewIndex == 1)
                                {
                                    this.mBodyReanim.setTrackVisible("zombie_football_helmet1", false);
                                    this.mBodyReanim.setTrackVisible("zombie_football_helmet2", true);
                                }
                                else
                                {
                                    if (this.mHelmType == HELMTYPE_FOOTBALL && aNewIndex == 2)
                                    {
                                        this.mBodyReanim.setTrackVisible("zombie_football_helmet2", false);
                                        this.mBodyReanim.setTrackVisible("zombie_football_helmet3", true);
                                    };
                                };
                            };
                        };
                    };
                };
            };
            return (aDamageRemaining);
        }

        private ParticleSystem mArmParticle ;

        public void  CheckIfPreyCaught (){
            CPlant aPlant ;
            if (this.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT || this.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT || this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
            {
                return;
            };
            if (!this.mHasHead)
            {
                return;
            };
            int aTicksPerBite =TICKS_BETWEEN_EATS ;
            if (this.mChilledCounter > 0)
            {
                aTicksPerBite = (aTicksPerBite * 2);
            };
            if ((this.mZombieAge % aTicksPerBite) != 0)
            {
                return;
            };
            if (!this.mMindControlled)
            {
                aPlant = this.FindPlantTarget(ATTACKTYPE_CHEW);
                if (aPlant!=null)
                {
                    this.EatPlant(aPlant);
                    return;
                };
            };
            if (this.mIsEating)
            {
                this.StopEating();
            };
        }

        public int mShieldJustGotShotCounter ;

        public void  GetDrawPos (ZombieDrawPosition theDrawPos ){
        	/*
            int aHeightLimit ;
            theDrawPos.mImageOffsetX = (this.mPosX - mX);
            theDrawPos.mImageOffsetY = (this.mPosY - mY);
            if (this.mIsEating)
            {
                theDrawPos.mHeadX = 31;
                theDrawPos.mHeadY = 3;
            }
            else
            {
                if (this.mFrame == 0)
                {
                    theDrawPos.mHeadX = 33;
                    theDrawPos.mHeadY = 2;
                }
                else
                {
                    if (this.mFrame == 1)
                    {
                        theDrawPos.mHeadX = 33;
                        theDrawPos.mHeadY = 1;
                    }
                    else
                    {
                        if (this.mFrame == 2)
                        {
                            theDrawPos.mHeadX = 33;
                            theDrawPos.mHeadY = 2;
                        }
                        else
                        {
                            if (this.mFrame == 3)
                            {
                                theDrawPos.mHeadX = 32;
                                theDrawPos.mHeadY = 3;
                            }
                            else
                            {
                                if (this.mFrame == 4)
                                {
                                    theDrawPos.mHeadX = 32;
                                    theDrawPos.mHeadY = 4;
                                }
                                else
                                {
                                    if (this.mFrame == 5)
                                    {
                                        theDrawPos.mHeadX = 32;
                                        theDrawPos.mHeadY = 3;
                                    }
                                    else
                                    {
                                        if (this.mFrame == 6)
                                        {
                                            theDrawPos.mHeadX = 32;
                                            theDrawPos.mHeadY = 2;
                                        }
                                        else
                                        {
                                            if (this.mFrame == 7)
                                            {
                                                theDrawPos.mHeadX = 33;
                                                theDrawPos.mHeadY = 1;
                                            }
                                            else
                                            {
                                                if (this.mFrame == 8)
                                                {
                                                    theDrawPos.mHeadX = 33;
                                                    theDrawPos.mHeadY = 2;
                                                }
                                                else
                                                {
                                                    if (this.mFrame == 9)
                                                    {
                                                        theDrawPos.mHeadX = 33;
                                                        theDrawPos.mHeadY = 3;
                                                    }
                                                    else
                                                    {
                                                        if (this.mFrame == 10)
                                                        {
                                                            theDrawPos.mHeadX = 33;
                                                            theDrawPos.mHeadY = 4;
                                                        }
                                                        else
                                                        {
                                                            theDrawPos.mHeadX = 33;
                                                            theDrawPos.mHeadY = 3;
                                                        };
                                                    };
                                                };
                                            };
                                        };
                                    };
                                };
                            };
                        };
                    };
                };
            };
            theDrawPos.mArmY = (theDrawPos.mHeadY / 2);
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                theDrawPos.mImageOffsetX = (theDrawPos.mImageOffsetX + -6);
                theDrawPos.mImageOffsetY = (theDrawPos.mImageOffsetY + -11);
            };
            if (this.mZombieType == ZOMBIE_FOOTBALL)
            {
                theDrawPos.mImageOffsetY = (theDrawPos.mImageOffsetY + -16);
            };
            if (this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
            {
                theDrawPos.mBodyY = -(this.mAltitude);
                aHeightLimit = Math.min(this.mPhaseCounter, 40);
                theDrawPos.mClipHeight = (theDrawPos.mBodyY + aHeightLimit);
            };
            theDrawPos.mBodyY = -(this.mAltitude);
            theDrawPos.mClipHeight = CLIP_HEIGHT_OFF;
            */
        }
        public boolean  IsDeadOrDying (){
            if (((((((this.mDead) || ((this.mZombiePhase == PHASE_ZOMBIE_DYING)))) || ((this.mZombiePhase == PHASE_ZOMBIE_BURNED)))) || ((this.mZombiePhase == PHASE_ZOMBIE_MOWERED))))
            {
                return (true);
            };
            return (false);
        }
        public void  UpdateBurn (){
            this.mPhaseCounter--;
            if (this.mPhaseCounter == 0)
            {
                this.DieWithLoot();
            };
        }
        public void  DropLoot (){
            if (!this.IsOnBoard())
            {
                return;
            };
            this.TrySpawnLevelAward();
            if (this.mDroppedLoot)
            {
                return;
            };
            if (mBoard.HasLevelAwardDropped())
            {
                return;
            };
            if (!mBoard.CanDropLoot())
            {
                return;
            };
            this.mDroppedLoot = true;
        }

        public double mVelX ;
        public int mIceTrapCounter ;
        public int mVelZ ;
        public int mAnimFrames ;

        public void  StartEating (){
            if (this.mIsEating)
            {
                return;
            };
            this.mIsEating = true;
            if (this.mZombiePhase == PHASE_NEWSPAPER_MAD)
            {
                this.mBodyReanim.currentTrack ("anim_eat_nopaper");
            }
            else
            {
                this.mBodyReanim.currentTrack ("anim_eat");
            };
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                this.mBodyReanim.animRate(20);
            }
            else
            {
                this.mBodyReanim.animRate(36);
            };
        }

        public boolean mHasHead ;
        public int mSummonCounter ;

        public int  GetHelmDamageIndex (){
            if (this.mHelmHealth < (this.mHelmMaxHealth / 3))
            {
                return (2);
            };
            if (this.mHelmHealth < ((this.mHelmMaxHealth * 2) / 3))
            {
                return (1);
            };
            return (0);
        }

        public boolean mDead ;
        
        public int mjType = 0;
        public CardType mjCard;

        public void  ZombieInitialize (CardType _mjCard, int theRow ,int theType ,boolean theVariant ,Zombie theParentZombie ,int theFromWave ,PVZApp app ,Board theBoard ){
            double aIdlePermuation ;
            this.app = app;
            mBoard = theBoard;
            mRow = theRow;
            this.mFromWave = theFromWave;
            this.mPosX = (int)((BOARD_WIDTH - 13) + (Math.random() * 27));
            this.mPosY = this.GetPosYBasedOnRow(theRow);
            
            
            mjCard = _mjCard;
            if(theType == 0)
            	mjType = 0;
            else 
            	mjType = 1;
            
            
            mWidth = 81;
            mHeight = 81;
            this.mVelX = 0;
            this.mVelZ = 0;
            this.mFrame = 0;
            this.mPrevFrame = 0;
            
            //add by xinghai
            theType = ZOMBIE_NORMAL;
            
            this.mZombieType = theType;
            this.mVariant = theVariant;
            this.mIsEating = false;
            this.mJustGotShotCounter = 0;
            this.mShieldJustGotShotCounter = 0;
            this.mShieldRecoilCounter = 0;
            this.mChilledCounter = 0;
            this.mIceTrapCounter = 0;
            this.mButteredCounter = 0;
            this.mMindControlled = false;
            this.mBlowingAway = false;
            this.mHasHead = true;
            this.mHasArm = true;
            this.mHasObject = false;
            this.mInPool = false;
            this.mOnHighGround = false;
            this.mHelmType = HELMTYPE_NONE;
            this.mShieldType = SHIELDTYPE_NONE;
            this.mYuckyFace = false;
            this.mAnimCounter = 0;
            this.mGroanCounter = TodCommon.RandRangeInt(400, 500);
            this.mAnimTicksPerFrame = 12;
            this.mAnimFrames = 12;
            this.mZombieAge = 0;
            this.mTargetCol = -1;
            this.mZombiePhase = PHASE_ZOMBIE_NORMAL;
            this.mZombieHeight = HEIGHT_ZOMBIE_NORMAL;
            this.mPhaseCounter = 0;
            this.mHitUmbrella = false;
            this.mDroppedLoot = false;
            this.mZombieRect = new Rectangle(25, 0, 28, 77);
            this.mZombieAttackRect = new Rectangle(40, 0, 14, 77);
            this.mPlayingSong = false;
            this.mZombieFade = -1;
            this.mFlatTires = false;
            this.mUseLadderCol = -1;
            this.mShieldHealth = 0;
            this.mHelmHealth = 0;
            this.mFlyingHealth = 0;
            this.mSummonCounter = 0;
            this.mScaleZombie = 1;
            this.mAltitude = 0;
            this.mOrginalAnimRate = 0;
            this.mBodyHealth = 270;
            
            switch (theType)
            {
                case ZOMBIE_NORMAL:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    
					this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    break;
                case ZOMBIE_TRAFFIC_CONE:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    this.mHelmType = HELMTYPE_TRAFFIC_CONE;
                    this.mHelmHealth = 370;
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    break;
                case ZOMBIE_PAIL:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    this.mHelmType = HELMTYPE_PAIL;
                    this.mHelmHealth = 1100;
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    break;
                case ZOMBIE_FLAG:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    this.mHasObject = true;
                    this.mPosX = BOARD_WIDTH;
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    break;
                case ZOMBIE_DOOR:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    this.mHasObject = true;
                    this.mShieldType = SHIELDTYPE_DOOR;
                    this.mShieldHealth = 1100;
                    this.mPosX = BOARD_WIDTH;
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    break;
                case ZOMBIE_NEWSPAPER:
                    this.mBodyReanim = app.reanimator().createReanimation(PVZReanims.REANIM_ZOMBIE_PAPER);
                    this.mHasObject = true;
                    this.mShieldType = SHIELDTYPE_NEWSPAPER;
                    this.mZombiePhase = PHASE_NEWSPAPER_READING;
                    this.mShieldHealth = 150;
                    this.mPosX = BOARD_WIDTH;
                    this.mVariant = false;
                    this.AttachShield();
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    if (this.IsOnBoard())
                    {
                        this.mBodyReanim.currentTrack("anim_walk");
                        this.mBodyReanim.loopType (Reanimation.LOOP_TYPE_ALWAYS);
                        this.PickRandomSpeed();
                    }
                    else
                    {
                        this.mBodyReanim.currentTrack("anim_idle");
                        this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                        this.mBodyReanim.animTime ((Math.random() * 0.99));
                        this.mBodyReanim.animRate (TodCommon.RandRangeFloat(12, 24));
                    };
                    break;
                case ZOMBIE_POLEVAULTER:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE_POLEVAULTER");
                    this.mHasObject = true;
                    this.mZombiePhase = PHASE_POLEVAULTER_PRE_VAULT;
                    this.mBodyHealth = 500;
                    this.mVariant = false;
                    this.mPosX = (int)((BOARD_WIDTH + 50) + (Math.random() * 7));
                    if (this.IsOnBoard())
                    {
                        this.mBodyReanim.currentTrack ("anim_run");
                        this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                        this.PickRandomSpeed();
                    }
                    else
                    {
                        this.mBodyReanim.currentTrack("anim_idle");
                        this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                        this.mBodyReanim.animTime ((Math.random() * 0.99));
                        this.mBodyReanim.animRate (TodCommon.RandRangeFloat(12, 24));
                    };
                    if (mBoard.IsWallnutBowlingLevel())
                    {
                        this.mZombieAttackRect = new Rectangle(-154, 0, 182, 77);
                    }
                    else
                    {
                        this.mZombieAttackRect = new Rectangle(-20, 0, 47, 77);
                    };
                    break;
                case ZOMBIE_FOOTBALL:
                    this.mBodyReanim = app.reanimator().createReanimation(PVZReanims.REANIM_ZOMBIE_FOOTBALL);
                    this.mZombieRect = new Rectangle(34, 0, 38, 77);
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                    this.mHelmType = HELMTYPE_FOOTBALL;
                    this.mHelmHealth = 2800;
                    this.mAnimTicksPerFrame = 6;
                    this.mVariant = false;
                    if (this.IsOnBoard())
                    {
                        this.mBodyReanim.setTrackVisible("anim_hair", false);
                        this.mBodyReanim.setTrackVisible("zombie_football_helmet2", false);
                        this.mBodyReanim.setTrackVisible("zombie_football_helmet3", false);
                        this.mBodyReanim.currentTrack("anim_walk");
                        this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                        this.PickRandomSpeed();
                    }
                    else
                    {
                        this.mBodyReanim.setTrackVisible("anim_hair", false);
                        this.mBodyReanim.currentTrack("anim_idle");
                        this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                        this.mBodyReanim.animTime((Math.random() * 0.99));
                        this.mBodyReanim.animRate(TodCommon.RandRangeFloat(12, 24));
                    };
                    break;
                default:
                    this.mBodyReanim = app.reanimator().createReanimation("REANIM_ZOMBIE");
                    this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
            };
            
            
            if (((mBoard!=null) && (mBoard.IsFlagWave(this.mFromWave))))
            {
                this.mPosX = (this.mPosX + 27);
            };
            if (theType == ZOMBIE_NORMAL || theType == ZOMBIE_TRAFFIC_CONE || theType == ZOMBIE_FLAG || theType == ZOMBIE_PAIL || theType == ZOMBIE_DOOR)
            {
                this.mBodyReanim.setTrackVisible("anim_hair", false);
                this.mBodyReanim.setTrackVisible("anim_cone", false);
                this.mBodyReanim.setTrackVisible("anim_cone2", false);
                this.mBodyReanim.setTrackVisible("anim_cone3", false);
                this.mBodyReanim.setTrackVisible("anim_bucket", false);
                this.mBodyReanim.setTrackVisible("anim_bucket2", false);
                this.mBodyReanim.setTrackVisible("anim_bucket3", false);
                this.mBodyReanim.setTrackVisible("anim_screendoor", false);
                this.mBodyReanim.setTrackVisible("anim_screendoor2", false);
                this.mBodyReanim.setTrackVisible("anim_screendoor3", false);
                this.mBodyReanim.setTrackVisible("Zombie_flaghand", false);
                this.mBodyReanim.setTrackVisible("Zombie_duckytube", false);
                this.mBodyReanim.setTrackVisible("anim_tongue", false);
                this.mBodyReanim.setTrackVisible("Zombie_mustache", false);
                this.mBodyReanim.setTrackVisible("Zombie_innerarm_screendoor", false);
                this.mBodyReanim.setTrackVisible("Zombie_innerarm_screendoor_hand", false);
                this.mBodyReanim.setTrackVisible("Zombie_outerarm_screendoor", false);
                this.mBodyReanim.setTrackVisible("anim_hair", false);
                switch (theType)
                {
                    case ZOMBIE_NORMAL:
                        this.mBodyReanim.setTrackVisible("anim_hair", false);
                        break;
                    case ZOMBIE_TRAFFIC_CONE:
                        this.mBodyReanim.setTrackVisible("anim_cone", true);
                        break;
                    case ZOMBIE_PAIL:
                        this.mBodyReanim.setTrackVisible("anim_bucket", true);
                        break;
                    case ZOMBIE_FLAG:
                        this.mBodyReanim.setTrackVisible("Zombie_flaghand", true);
                        this.mBodyReanim.setTrackVisible("anim_hair", true);
                        this.mBodyReanim.setTrackVisible("anim_innerarm1", false);
                        this.mBodyReanim.setTrackVisible("anim_innerarm2", false);
                        this.mBodyReanim.setTrackVisible("anim_innerarm3", false);
                        this.mBodyReanim.setTrackVisible("Zombie_innerarm_screendoor", true);
                        break;
                    case ZOMBIE_DOOR:
                        this.mBodyReanim.setTrackVisible("anim_hair", true);
                        this.mBodyReanim.setTrackVisible("anim_innerarm1", false);
                        this.mBodyReanim.setTrackVisible("anim_innerarm2", false);
                        this.mBodyReanim.setTrackVisible("anim_innerarm3", false);
                        this.AttachShield();
                        this.SetupDoorArms(true);
                };
                if (!this.IsOnBoard())
                {
                    aIdlePermuation = (Math.random() * 3);
                    if ((((aIdlePermuation > 1)) && (!((theType == ZOMBIE_POLEVAULTER)))))
                    {
                        this.mBodyReanim.animRate(TodCommon.RandRangeFloat(12, 24));
                        this.mBodyReanim.currentTrack("anim_idle2");
                    }
                    else
                    {
                        this.mBodyReanim.animRate(TodCommon.RandRangeFloat(12, 18));
                        this.mBodyReanim.currentTrack("anim_idle");
                    };
                    this.mBodyReanim.animTime((Math.random() * 0.99));
                }
                else
                {
                    this.StartWalkAnim(0);
                };
                this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                this.PickRandomSpeed();
            };
            this.UpdateAnimSpeed();
            if (this.mVariant)
            {
                this.mBodyReanim.setTrackVisible("anim_tongue", false);
            };
            this.mBodyMaxHealth = this.mBodyHealth;
            this.mHelmMaxHealth = this.mHelmHealth;
            this.mShieldMaxHealth = this.mShieldHealth;
            this.mDead = false;
            mX = (int)(this.mPosX);
            mY = (int)(this.mPosY);
            if (this.IsOnBoard())
            {
            };
            mMjImage = new ImageInst(new ImageData(new BitmapData(MahJongImages.getMahJongImage(mjCard.name.toUpperCase()))));
            mMaskImage = new ImageInst(new ImageData(new BitmapData(MahJongImages.getMahJongMaskImage(mjCard.name.toUpperCase()))));
            
//            if(this.mjType == 0) {
            	this.mBodyReanim.overrideImage("anim_head1", mMaskImage);
//            } else {
            	this.mBodyReanim.overrideImage("anim_head2", mMjImage);
//            }
            this.mBodyReanim.setTrackVisible("anim_head2", false);
            
            this.UpdateReanim();
        }
        public Rectangle  GetZombieAttackRect (){
            Rectangle aRect =this.mZombieAttackRect.clone ();
            ZombieDrawPosition aDrawPos =this.scratchDrawPos ;
            this.GetDrawPos(aDrawPos);
            aRect.offset(mX, (mY + aDrawPos.mBodyY));
            if (aDrawPos.mClipHeight > CLIP_HEIGHT_LIMIT)
            {
                aRect.height = (aRect.height() - aDrawPos.mClipHeight);
            };
            return (aRect);
        }
        
        public void showMjHead() {
        	this.isMasked = false;
        	this.maskCount = 1000;
//        	this.mBodyReanim.overrideImage("anim_head1", mMjImage);
        	this.mBodyReanim.setTrackVisible("anim_head1", false);
        	this.mBodyReanim.setTrackVisible("anim_head2", true);
        	
        	this.UpdateReanim();
        }
        

        private boolean mHitRectUpdated =false ;

        public void  ApplyChill (boolean theIsIceTrap ){
            if (!this.CanBeChilled())
            {
                return;
            };
            if (this.mChilledCounter == 0)
            {
                app.foleyManager().playFoley(PVZFoleyType.FROZEN);
            };
            int aTime =1000;
            if (theIsIceTrap)
            {
                aTime = 2000;
            };
            this.mChilledCounter = Math.max(aTime, this.mChilledCounter);
            this.UpdateAnimSpeed();
        }
        public int  TakeShieldDamage (int theDamage ,int theDamageFlags ){
            if (theDamageFlags != DAMAGE_DOESNT_CAUSE_FLASH || this.mZombieType == ZOMBIE_DOOR || this.mZombieType == ZOMBIE_NEWSPAPER)
            {
                this.mShieldJustGotShotCounter = 25;
                if (this.mJustGotShotCounter < 0)
                {
                    this.mJustGotShotCounter = 0;
                };
            };
            if (theDamageFlags != DAMAGE_DOESNT_CAUSE_FLASH && theDamageFlags != DAMAGE_HITS_SHIELD_AND_BODY)
            {
                this.mShieldRecoilCounter = 12;
                if (this.mShieldType == SHIELDTYPE_DOOR)
                {
                    app.foleyManager().playFoley(PVZFoleyType.SHIELD_HIT);
                };
            };
            int aOldIndex =this.GetShieldDamageIndex ();
            int aDamageApplied =Math.min(this.mShieldHealth ,theDamage );
            int aDamageRemaining =(theDamage -aDamageApplied );
            this.mShieldHealth = (this.mShieldHealth - aDamageApplied);
            if (this.mShieldHealth == 0)
            {
                this.DropShield(theDamageFlags);
                return (aDamageRemaining);
            };
            int aNewIndex =this.GetShieldDamageIndex ();
            if (aOldIndex != aNewIndex)
            {
                if (this.mShieldType == SHIELDTYPE_DOOR && aNewIndex == 1)
                {
                    this.mBodyReanim.setTrackVisible("anim_screendoor1", false);
                    this.mBodyReanim.setTrackVisible("anim_screendoor2", true);
                }
                else
                {
                    if (this.mShieldType == SHIELDTYPE_DOOR && aNewIndex == 2)
                    {
                        this.mBodyReanim.setTrackVisible("anim_screendoor2", false);
                        this.mBodyReanim.setTrackVisible("anim_screendoor3", true);
                    }
                    else
                    {
                        if (this.mShieldType == SHIELDTYPE_NEWSPAPER && aNewIndex == 1)
                        {
                            this.mBodyReanim.setTrackVisible("Zombie_paper_paper1", false);
                            this.mBodyReanim.setTrackVisible("Zombie_paper_paper2", true);
                        }
                        else
                        {
                            if (this.mShieldType == SHIELDTYPE_NEWSPAPER && aNewIndex == 2)
                            {
                                this.mBodyReanim.setTrackVisible("Zombie_paper_paper2", false);
                                this.mBodyReanim.setTrackVisible("Zombie_paper_paper3", true);
                            };
                        };
                    };
                };
            };
            return (aDamageRemaining);
        }

        public boolean mYuckyFace ;

        public void  DieNoLoot (){
            if (((this.IsOnBoard()) && (!(this.mDead))))
            {
                app.mTotalZombiesKilled++;
                app.adAPI.setScore(app.mTotalZombiesKilled);
//                trace(app.mTotalZombiesKilled);
            };
            this.TrySpawnLevelAward();
            this.mDead = true;
        }

        public boolean mHasObject ;
        public double mPosX ;
        public double mPosY ;
        public int mParticleOffsetY ;
        public int mShieldType ;
        public int mParticleOffsetX ;

        public void  ShowDoorArms (boolean theShow ){
            if (this.mBodyReanim!=null)
            {
                this.SetupDoorArms(theShow);
                if (!this.mHasArm)
                {
                    this.mBodyReanim.setTrackVisible("Zombie_outerarm_lower", false);
                    this.mBodyReanim.setTrackVisible("Zombie_outerarm_hand", false);
                };
            };
        }
        public void  UpdateZombiePosition (){
            int aDesiredY ;
            if (this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
            {
                return;
            };
            this.UpdateZombieWalking();
            /*
            if (this.mBlowingAway)
            {
                this.mPosX = (this.mPosX + 10);
                if (mX > 850)
                {
                    this.DieWithLoot();
                    return;
                };
            };
            if (this.mZombieHeight == HEIGHT_ZOMBIE_NORMAL)
            {
                aDesiredY = this.GetPosYBasedOnRow(mRow);
                if (this.mPosY < aDesiredY)
                {
                    this.mPosY = (this.mPosY + Math.min(1, (aDesiredY - this.mPosY)));
                }
                else
                {
                    if (this.mPosY > aDesiredY)
                    {
                        this.mPosY = (this.mPosY - Math.min(1, (this.mPosY - aDesiredY)));
                    };
                };
            };
            */
        }
        public void  WalkIntoHouse (){
            this.mFromWave = ZOMBIE_WAVE_WINNER;
            if (this.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT)
            {
                this.mZombiePhase = PHASE_POLEVAULTER_POST_VAULT;
                this.StartWalkAnim(0);
            };
            this.mPosY = 196;
        }
        public void  Animate (){
            int aEatingTicksPerFrame ;
            double aLeftHandTime ;
            double aRightHandTime ;
            this.mPrevFrame = this.mFrame;
            if (this.IsImmobilizied())
            {
                return;
            };
            this.mAnimCounter++;
            if (this.mIsEating && this.mHasHead)
            {
                aEatingTicksPerFrame = 6;
                if (this.mChilledCounter > 0)
                {
                    aEatingTicksPerFrame = 12;
                };
                if (this.mAnimCounter >= (this.mAnimFrames * aEatingTicksPerFrame))
                {
                    this.mAnimCounter = aEatingTicksPerFrame;
                };
                this.mFrame = (this.mAnimCounter / aEatingTicksPerFrame);
                aLeftHandTime = 0.14;
                aRightHandTime = 0.68;
                if (this.mZombieType == ZOMBIE_POLEVAULTER)
                {
                    aLeftHandTime = 0.38;
                    aRightHandTime = 0.8;
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_NEWSPAPER)
                    {
                        aLeftHandTime = 0.42;
                        aRightHandTime = 0.42;
                    };
                };
                if (this.mBodyReanim.shouldTriggerTimedEvent(aLeftHandTime) || this.mBodyReanim.shouldTriggerTimedEvent(aRightHandTime))
                {
                    this.AnimateChewSound();
                    this.AnimateChewEffect();
                };
                return;
            };
            if (this.mAnimCounter >= (this.mAnimFrames * this.mAnimTicksPerFrame))
            {
                this.mAnimCounter = 0;
            };
            this.mFrame = (this.mAnimCounter / this.mAnimTicksPerFrame);
        }
        public int  GetPosYBasedOnRow (int theRow ){
            if (!this.IsOnBoard())
            {
                return (0);
            };
            int aPosY =(mBoard.GetPosYBasedOnRow ((int)(this.mPosX +40),theRow )-30);
            return (aPosY);
        }

        public int mZombieType ;
        public int mJustGotShotCounter ;

        public boolean  EffectedByDamage (){
            if (this.IsDeadOrDying())
            {
                return (false);
            };
            if (this.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT || this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
            {
                return (false);
            };
            return (true);
        }

        public int mFlyingHealth ;
        public boolean mIsEating ;
        public int mFlyingMaxHealth ;

        public void  DropArm (int theDamageFlags ){
            ImageInst anImage ;
            if (!this.CanLoseBodyParts())
            {
                return;
            };
            if (!this.mHasArm)
            {
                return;
            };
            if (this.mShieldType == SHIELDTYPE_DOOR || this.mShieldType == SHIELDTYPE_NEWSPAPER)
            {
                return;
            };
            if (this.mZombiePhase == PHASE_NEWSPAPER_READING)
            {
                return;
            };
            this.mHasArm = false;
            if (this.mZombieType == ZOMBIE_NEWSPAPER)
            {
                this.mBodyReanim.setTrackVisible("Zombie_paper_hands", false);
                this.mBodyReanim.setTrackVisible("Zombie_paper_leftarm_lower", false);
            }
            else
            {
                if (this.mZombieType == ZOMBIE_POLEVAULTER)
                {
                    this.mBodyReanim.setTrackVisible("Zombie_polevaulter_outerarm_lower", false);
                    this.mBodyReanim.setTrackVisible("Zombie_outerarm_hand", false);
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_FOOTBALL)
                    {
                        this.mBodyReanim.setTrackVisible("Zombie_football_rightarm_lower", false);
                        this.mBodyReanim.setTrackVisible("Zombie_football_rightarm_hand", false);
                    }
                    else
                    {
                        this.mBodyReanim.setTrackVisible("Zombie_outerarm_lower", false);
                        this.mBodyReanim.setTrackVisible("Zombie_outerarm_hand", false);
                    };
                };
            };
            if (this.mZombieType != ZOMBIE_NORMAL)
            {
                return;
            };
            String anEffect =PVZParticles.PARTICLE_ZOMBIEARM ;
            ZombieDrawPosition aDrawPos =this.scratchDrawPos ;
            this.GetDrawPos(aDrawPos);
            int aPosX =(int)((this.mPosX +aDrawPos.mImageOffsetX ) + 20);
            int aPosY =(int)(((this.mPosY +aDrawPos.mImageOffsetY ) + aDrawPos.mBodyY ) + (78*0.675));
            Point aPoint =new Point(0,0);
            if (this.mZombiePhase == PHASE_ZOMBIE_MOWERED)
            {
                anEffect = PVZParticles.PARTICLE_MOWEREDZOMBIEARM;
            };
            aPosX = (aPosX + aPoint.x);
            aPosY = (aPosY + aPoint.y);
            this.mArmParticle = app.particleManager().spawnParticleSystem(anEffect);
            this.mArmParticle.setPosition(aPosX, aPosY);
            if (this.mChilledCounter > 0)
            {
            	System.out.println("droparm."+this.mArmParticle.mEmitterList);
            	//((ParticleEmitter)this.mArmParticle.mEmitterList.elementAt(0)).mColorOverride=gChilledColor;
            };
            app.foleyManager().playFoley(PVZFoleyType.LIMBS_POP);
        }
        public void  Update (){
            this.mHitRectUpdated = false;
            this.mZombieAge++;
            
            if(!isMasked) {
            	if(maskCount == 1000) {
            		this.mBodyReanim.setTrackVisible("anim_head1", false);
            		this.mBodyReanim.setTrackVisible("anim_head2", true);
            	} else if(maskCount == 600) {
//            		this.mBodyReanim.setTrackVisible("anim_head1", true);
            	} else if(maskCount == 1) {
            		this.mBodyReanim.setTrackVisible("anim_head1", true);
            		this.mBodyReanim.setTrackVisible("anim_head2", false);
            		isMasked = true;
//            		this.UpdateReanim();
            	}
            	maskCount--;
            }
            
            if (mBoard.mGameScene != SCENE_PLAYING && this.IsOnBoard() && this.mFromWave != ZOMBIE_WAVE_WINNER)
            {
                return;
            };
            if (this.mZombiePhase == PHASE_ZOMBIE_BURNED)
            {
                this.UpdateBurn();
            }
            else
            {
                if (this.mZombiePhase == PHASE_ZOMBIE_MOWERED)
                {
                    this.UpdateMowered();
                }
                else
                {
                    if (this.mZombiePhase == PHASE_ZOMBIE_DYING)
                    {
                        this.UpdateDeath();
                        this.UpdateZombieWalking();
                    }
                    else
                    {
                        if (this.mPhaseCounter > 0 && !this.IsImmobilizied())
                        {
                            this.mPhaseCounter--;
                        };
                        if (mBoard.mGameScene == SCENE_ZOMBIES_WON)
                        {
                            if (mBoard.mShowZombieWalking)
                            {
                                this.UpdateZombieWalking();
                            };
                        }
                        else
                        {
                            if (this.IsOnBoard())
                            {
                                this.UpdatePlaying();
                            };
                        };
                        this.Animate();
                    };
                };
            };
            this.mJustGotShotCounter--;
            if (this.mShieldJustGotShotCounter > 0)
            {
                this.mShieldJustGotShotCounter--;
            };
            if (this.mZombieFade > 0)
            {
                this.mZombieFade--;
                if (this.mZombieFade == 0)
                {
                    this.DieNoLoot();
                };
            };
            mX = (int)(this.mPosX);
            mY = (int)(this.mPosY);
            this.UpdateReanim();
            if (this.mHeadParticle != null)
            {
                if (this.mHeadParticle.mDead)
                {
                };
            };
            if (this.mArmParticle != null)
            {
                this.mArmParticle.update();
                if (this.mArmParticle.mDead)
                {
                    this.mArmParticle = null;
                };
            };
            if (this.mHelmParticle != null)
            {
                this.mHelmParticle.update();
                if (this.mHelmParticle.mDead)
                {
                    this.mHelmParticle = null;
                };
            };
            if (this.mFlagParticle != null)
            {
                this.mFlagParticle.update();
                if (this.mFlagParticle.mDead)
                {
                    this.mFlagParticle = null;
                };
            };
        }
        public void  UpdateZombieNewspaper (){
            ImageInst anImage ;
            if (this.mZombiePhase == PHASE_NEWSPAPER_MADDENING)
            {
                if (this.mBodyReanim.currentTrack().equals("anim_walk_nopaper"))
                {
                    this.mZombiePhase = PHASE_NEWSPAPER_MAD;
                    app.foleyManager().playFoley(PVZFoleyType.NEWSPAPER_RARRGH);
                    this.StartWalkAnim(20);
                    anImage = app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_ZOMBIE_PAPER_MADHEAD);
                    this.mBodyReanim.overrideImage("anim_head1", anImage);
                };
            };
        }

        public int mBodyHealth;

        public void  DropHelm (int theDamageFlags ){
            Point aPoint = new Point();
            String anEffect ="";
            if (this.mHelmType == HELMTYPE_NONE)
            {
                return;
            };
            ZombieDrawPosition aDrawPos =this.scratchDrawPos;
            this.GetDrawPos(aDrawPos);
            int aPosX =(int)(((this.mPosX +aDrawPos.mImageOffsetX )+aDrawPos.mHeadX )+10);
            int aPosY =(int)((((this.mPosY +aDrawPos.mImageOffsetY )+aDrawPos.mHeadY )+aDrawPos.mBodyY )+13);
            if (this.mHelmType == HELMTYPE_TRAFFIC_CONE)
            {
                aPoint = this.mBodyReanim.getTrackPosition("anim_cone");
                this.mBodyReanim.setTrackVisible("anim_cone1", false);
                this.mBodyReanim.setTrackVisible("anim_cone2", false);
                this.mBodyReanim.setTrackVisible("anim_cone3", false);
                this.mBodyReanim.setTrackVisible("anim_hair", true);
                anEffect = PVZParticles.PARTICLE_ZOMBIETRAFFICCONE;
            }
            else
            {
                if (this.mHelmType == HELMTYPE_PAIL)
                {
                    aPoint = this.mBodyReanim.getTrackPosition("anim_bucket");
                    this.mBodyReanim.setTrackVisible("anim_bucket1", false);
                    this.mBodyReanim.setTrackVisible("anim_bucket2", false);
                    this.mBodyReanim.setTrackVisible("anim_bucket3", false);
                    this.mBodyReanim.setTrackVisible("anim_hair", true);
                    anEffect = PVZParticles.PARTICLE_ZOMBIEPAIL;
                }
                else
                {
                    if (this.mHelmType == HELMTYPE_FOOTBALL)
                    {
                        aPoint = this.mBodyReanim.getTrackPosition("zombie_football_helmet3");
                        this.mBodyReanim.setTrackVisible("zombie_football_helmet1", false);
                        this.mBodyReanim.setTrackVisible("zombie_football_helmet2", false);
                        this.mBodyReanim.setTrackVisible("zombie_football_helmet3", false);
                        this.mBodyReanim.setTrackVisible("anim_hair", true);
                        anEffect = PVZParticles.PARTICLE_ZOMBIEHELMET;
                    };
                };
            };
            this.mHelmParticle = app.particleManager().spawnParticleSystem(anEffect);
            this.mHelmParticle.setPosition((aPosX + aPoint.x), (aPosY + aPoint.y));
            this.mHelmType = HELMTYPE_NONE;
        }
        public int  GetShieldDamageIndex (){
            if (this.mShieldHealth < (this.mShieldMaxHealth / 3))
            {
                return (2);
            };
            if (this.mShieldHealth < ((this.mShieldMaxHealth * 2) / 3))
            {
                return (1);
            };
            return (0);
        }
        public void  TakeDamage (int theDamage ,int theDamageFlags ){
            if (this.IsDeadOrDying())
            {
                return;
            };
            int aDamageRemaining =theDamage ;
            if (aDamageRemaining > 0 && this.mShieldType != SHIELDTYPE_NONE)
            {
                aDamageRemaining = this.TakeShieldDamage(theDamage, theDamageFlags);
                if (theDamageFlags == DAMAGE_HITS_SHIELD_AND_BODY)
                {
                    aDamageRemaining = theDamage;
                };
            };
            if (aDamageRemaining > 0 && this.mHelmType != HELMTYPE_NONE)
            {
                aDamageRemaining = this.TakeHelmDamage(theDamage, theDamageFlags);
            };
            if (aDamageRemaining > 0)
            {
                this.TakeBodyDamage(aDamageRemaining, theDamageFlags);
            };
        }

        private ParticleSystem mHelmParticle ;

        public boolean  CanLoseBodyParts (){
            return (true);
        }
        public void  DetachShield (){
            if (this.mBodyReanim==null)
            {
                this.mShieldType = SHIELDTYPE_NONE;
                this.mShieldHealth = 0;
                return;
            };
            if (this.mShieldType == SHIELDTYPE_DOOR)
            {
                this.mBodyReanim.setTrackVisible("anim_screendoor3", false);
                this.ShowDoorArms(false);
            }
            else
            {
                if (this.mShieldType == SHIELDTYPE_NEWSPAPER)
                {
                    this.mBodyReanim.setTrackVisible("Zombie_paper_hands", true);
                };
            };
            this.mShieldType = SHIELDTYPE_NONE;
            this.mShieldHealth = 0;
        }

        public int mFrame ;

        public void  UpdatePlaying (){
            boolean aBleeding ;
            int aBleedRate ;
            this.mGroanCounter--;
            int aZombiesOnScreen =mBoard.mZombies.length();
            if (this.mGroanCounter == 0 && Math.random() * aZombiesOnScreen == 0 && this.mHasHead && !mBoard.HasLevelAwardDropped())
            {
                if (this.mVariant)
                {
                    app.foleyManager().playFoley(PVZFoleyType.BRAINS);
                }
                else
                {
                    app.foleyManager().playFoley(PVZFoleyType.GROAN);
                };
                this.mGroanCounter = (int)((Math.random() * 1000) + 500);
            };
            if (this.mChilledCounter > 0)
            {
                this.mChilledCounter--;
                if (this.mChilledCounter == 0)
                {
                    this.UpdateAnimSpeed();
                };
            };
            if (this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
            {
                this.UpdateZombieRiseFromGrave();
                return;
            };
            if (!this.IsImmobilizied())
            {
                this.UpdateActions();
                this.UpdateZombiePosition();
                this.CheckIfPreyCaught();
                this.CheckForBoardEdge();
            };
            if (!this.IsDeadOrDying() && this.mFromWave != ZOMBIE_WAVE_WINNER)
            {
                aBleeding = !(this.mHasHead);
                if (aBleeding)
                {
                    aBleedRate = 1;
                    if (this.mBodyMaxHealth >= 500)
                    {
                        aBleedRate = 3;
                    };
                    if ((int)((Math.random() * 4)) == 0)
                    {
                        this.TakeDamage(aBleedRate, DAMAGE_DOESNT_CAUSE_FLASH);
                    };
                };
            };
        }
        public void  StopZombieSound (){
        }
        public void  UpdateDeath (){
            if (this.mBodyReanim.mIsDead)
            {
                this.DieNoLoot();
                return;
            };
            if (this.mZombieHeight == HEIGHT_FALLING)
            {
                this.UpdateZombieFalling();
            };
            double aFallTime =-1;
            if (this.mZombieType == ZOMBIE_NORMAL || this.mZombieType == ZOMBIE_FLAG || this.mZombieType == ZOMBIE_TRAFFIC_CONE || this.mZombieType == ZOMBIE_PAIL)
            {
                if (this.mBodyReanim.currentTrack() == "anim_superlongdeath")
                {
                    aFallTime = 0.78;
                }
                else
                {
                    if (this.mBodyReanim.currentTrack() == "anim_death2")
                    {
                        aFallTime = 0.71;
                    }
                    else
                    {
                        aFallTime = 0.77;
                    };
                };
            }
            else
            {
                if (this.mZombieType == ZOMBIE_POLEVAULTER)
                {
                    aFallTime = 0.68;
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_NEWSPAPER)
                    {
                        aFallTime = 0.63;
                    }
                    else
                    {
                        if (this.mZombieType == ZOMBIE_FOOTBALL)
                        {
                            aFallTime = 0.52;
                        };
                    };
                };
            };
            if (aFallTime > 0 && this.mBodyReanim.shouldTriggerTimedEvent(aFallTime))
            {
                app.foleyManager().playFoley(PVZFoleyType.ZOMBIE_FALLING);
            };
            if (this.mZombieFade == -1 && this.mBodyReanim.animTime() == 1)
            {
                this.mZombieFade = 100;
            };
        }

        public int mZombieAge ;
        public int mPrevFrame ;
        public int mPhaseCounter ;

        public Rectangle  GetZombieRect (){
            ZombieDrawPosition aDrawPos ;
            if (this.mHitRectUpdated == false)
            {
            	this.mZombieHitRect.x = this.mZombieRect.x;
                this.mZombieHitRect.y = this.mZombieRect.y;
                this.mZombieHitRect.width = this.mZombieRect.width();
                this.mZombieHitRect.height = this.mZombieRect.height();
                aDrawPos = mScratchPosition;
                this.GetDrawPos(aDrawPos);
                this.mZombieHitRect.offset(mX, (int)(mY + aDrawPos.mBodyY));
                if (aDrawPos.mClipHeight > CLIP_HEIGHT_LIMIT)
                {
                    this.mZombieHitRect.height = (this.mZombieHitRect.height() - (int)aDrawPos.mClipHeight);
                };
                this.mHitRectUpdated = true;
            };
            return (this.mZombieHitRect);
        }
        public void  UpdateReanim (){
            this.mBodyReanim.update();
        }
        public void  DieWithLoot (){
            this.DieNoLoot();
            this.DropLoot();
        }
        public void  UpdateZombieFalling (){
            this.mAltitude--;
        }

        public int mHelmType ;

        public void  DropFlag (){
            if (this.mZombieType != ZOMBIE_FLAG || !this.mHasObject)
            {
                return;
            };
            this.mHasObject = false;
            Point aPoint =this.mBodyReanim.getTrackPosition("Zombie_flaghand");
            int aFlagPosX =(mX -10);
            int aFlagPosY =(mY -10);
            this.mFlagParticle = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_ZOMBIEFLAG);
            this.mFlagParticle.setPosition(aFlagPosX, aFlagPosY);
        }

        private ParticleSystem mFlagParticle ;

        public boolean  CanBeChilled (){
            if (((this.IsDeadOrDying()) || ((this.mZombiePhase == PHASE_RISING_FROM_GRAVE))))
            {
                return (false);
            };
            return (true);
        }
        public void  MowDown (){
            if (this.mDead || this.mZombiePhase == PHASE_ZOMBIE_MOWERED)
            {
                return;
            };
            ParticleSystem anEffect =app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_MOWERCLOUD );
            anEffect.setPosition((int)(this.mPosX + 20), (int)(this.mPosY + 20));
            mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
            this.DropHead(0);
            this.DropArm(0);
            this.DropHelm(0);
            this.DropShield(0);
            this.DieWithLoot();
        }

        public int mUseLadderCol ;

        public int  ZombieTargetLeadX (int theTime ){
            int aSpeed = (int)this.mVelX ;
            if (this.mChilledCounter > 0)
            {
                aSpeed = (int)(aSpeed * CHILLED_SPEED_FACTOR);
            };
            if (this.ZombieNotWalking())
            {
                aSpeed = 0;
            };
            int aLeadAmount =(aSpeed *theTime );
            Rectangle aZombieRect =this.GetZombieRect ();
            int aCenter =(aZombieRect.x +(aZombieRect.width() /2));
            return ((aCenter - aLeadAmount));
        }

        public boolean mHitUmbrella ;
        public boolean mMindControlled ;

        public void  DrawReanim (Graphics2D g ,ZombieDrawPosition theDrawPos ){
            int aDrawHeight ;
            int aFade ;
            int anOffsetX =(int)theDrawPos.mImageOffsetX ;
            int anOffsetY =(int)((theDrawPos.mImageOffsetY +theDrawPos.mBodyY )-52);
            if (theDrawPos.mClipHeight > CLIP_HEIGHT_LIMIT)
            {
                aDrawHeight = ((120 - theDrawPos.mClipHeight) + 71);
                g.setClipRect((anOffsetX - 200), anOffsetY, (120 + 400), aDrawHeight,true);
            };
            int aAlpha =1;
            if (this.mZombieFade >= 0)
            {
                aAlpha = TodCommon.ClampInt((this.mZombieFade / 10), 0, 1);
            };
            Color aColorOverride =this.mOverrideColor ;
            aColorOverride.alpha = aAlpha;
            aColorOverride.red = 1;
            aColorOverride.green = 1;
            aColorOverride.blue = 1;
            Color aExtraAdditiveColor =this.mAdditiveColor ;
            aExtraAdditiveColor.alpha = 0;
            aExtraAdditiveColor.red = 0;
            aExtraAdditiveColor.green = 0;
            aExtraAdditiveColor.blue = 0;
            boolean aEnableExtraAdditiveDraw =false;
            if (this.mZombiePhase == PHASE_ZOMBIE_BURNED)
            {
                aColorOverride.red = 0;
                aColorOverride.green = 0;
                aColorOverride.blue = 0;
                aEnableExtraAdditiveDraw = false;
            }
            else
            {
                if (this.mChilledCounter > 0 || this.mIceTrapCounter > 0)
                {
                    aColorOverride.red = gChilledColor.red;
                    aColorOverride.green = gChilledColor.green;
                    aColorOverride.blue = gChilledColor.blue;
                    aExtraAdditiveColor.red = aColorOverride.red;
                    aExtraAdditiveColor.green = aColorOverride.green;
                    aExtraAdditiveColor.blue = aColorOverride.blue;
                    aEnableExtraAdditiveDraw = true;
                };
            };
            if (this.mJustGotShotCounter > 0)
            {
                aFade = ((this.mJustGotShotCounter * 10) / 0xFF);
                aExtraAdditiveColor.red = (aExtraAdditiveColor.red + aFade);
                aExtraAdditiveColor.red = (((aExtraAdditiveColor.red)>1) ? 1 : aExtraAdditiveColor.red);
                aExtraAdditiveColor.green = (aExtraAdditiveColor.green + aFade);
                aExtraAdditiveColor.green = (((aExtraAdditiveColor.green)>1) ? 1 : aExtraAdditiveColor.green);
                aExtraAdditiveColor.blue = (aExtraAdditiveColor.blue + aFade);
                aExtraAdditiveColor.blue = (((aExtraAdditiveColor.blue)>1) ? 1 : aExtraAdditiveColor.blue);
                aEnableExtraAdditiveDraw = true;
            };
            this.mBodyReanim.useColor = aEnableExtraAdditiveDraw;
            this.mBodyReanim.overrideColor = aColorOverride;
            this.mBodyReanim.additiveColor = aExtraAdditiveColor;
            if (!this.IsOnBoard())
            {
                this.mBodyReanim.x(mX);
                this.mBodyReanim.y(mY);
                this.mBodyReanim.draw(g);
            }
            else
            {
                if (this.mZombiePhase == PHASE_RISING_FROM_GRAVE)
                {
                    this.mBodyReanim.x(mX);
                    this.mBodyReanim.y((mY - this.mAltitude));
                    g.setClipRect(mX, mY, mHeight, mWidth,true);
                    this.mBodyReanim.drawLerp(g, this.mScratchMatrix,1);
                }
                else
                {
                    this.mBodyReanim.x(mX);
                    this.mBodyReanim.y(mY);
                    this.mBodyReanim.draw(g);
                };
            };
            g.clearClipRect();
        }

        public int mFromWave ;
        public int mShieldMaxHealth ;
        public Reanimation mBodyReanim ;
        public boolean mVariant ;

        public void  DropShield (int theDamageFlags ){
            Point aPoint ;
            ParticleSystem anEffect ;
            Array anArray ;
            if (this.mShieldType == SHIELDTYPE_NONE)
            {
                return;
            };
            ZombieDrawPosition aDrawPos =mScratchPosition ;
            this.GetDrawPos(aDrawPos);
            if (this.mShieldType == SHIELDTYPE_DOOR)
            {
                aPoint = this.mBodyReanim.getTrackPosition("anim_screendoor1");
                this.DetachShield();
                if (theDamageFlags != DAMAGE_DOESNT_LEAVE_BODY)
                {
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_ZOMBIEDOOR);
                    anEffect.setPosition((aPoint.x + mX), (aPoint.y + mY));
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
                };
            }
            else
            {
                if (this.mShieldType == SHIELDTYPE_NEWSPAPER)
                {
                    aPoint = this.mBodyReanim.getTrackPosition("Zombie_paper_paper3");
                    this.StopEating();
                    this.mZombiePhase = PHASE_NEWSPAPER_MADDENING;
                    anArray = new Array("anim_walk_nopaper");
                    this.PlayZombieReanim("anim_gasp", new ReanimLoopQueue(anArray), 8);
                    this.DetachShield();
                    if (theDamageFlags != DAMAGE_DOESNT_LEAVE_BODY)
                    {
                        anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_ZOMBIENEWSPAPER);
                        anEffect.setPosition((aPoint.x + mX), (aPoint.y + mY));
                        mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
                    };
                    if (((!((theDamageFlags == DAMAGE_DOESNT_LEAVE_BODY))) && (!((theDamageFlags == DAMAGE_BYPASSES_SHIELD)))))
                    {
                        app.foleyManager().playFoley(PVZFoleyType.NEWSPAPER_RIP);
                    };
                };
            };
            this.mShieldType = SHIELDTYPE_NONE;
        }

        public boolean mBlowingAway ;
        public int mScaleZombie ;

        public void  Draw (Graphics2D g ){
            this.GetDrawPos(this.scratchDrawPos);
            if (mBoard.mGameScene == SCENE_ZOMBIES_WON)
            {
                if (!this.SetupDrawZombieWon(g))
                {
                    return;
                };
            };
            this.DrawReanim(g, this.scratchDrawPos);
            g.clearClipRect();
            if (this.mHeadParticle != null)
            {
            };
            if (this.mArmParticle != null)
            {
                this.mArmParticle.draw(g);
            };
            if (this.mHelmParticle != null)
            {
                this.mHelmParticle.draw(g);
            };
            if (this.mFlagParticle != null)
            {
                this.mFlagParticle.draw(g);
            };
        }
        public void  DropPole (){
            if (this.mZombieType != ZOMBIE_POLEVAULTER)
            {
                return;
            };
            this.mBodyReanim.setTrackVisible("Zombie_polevaulter_innerarm_upper", false);
            this.mBodyReanim.setTrackVisible("Zombie_polevaulter_innerarm_lower", false);
            this.mBodyReanim.setTrackVisible("Zombie_polevaulter_innerhand", false);
            this.mBodyReanim.setTrackVisible("Zombie_polevaulter_pole", false);
            this.mBodyReanim.setTrackVisible("Zombie_polevaulter_pole2", false);
        }

        public int mAnimTicksPerFrame ;
        public Rectangle mZombieRect ;
        public int mOrginalAnimRate ;
        public Reanimation mAttachment ;

        public void  UpdateZombieRiseFromGrave (){
            this.mAltitude = TodCommon.TodAnimateCurve(50, 0, this.mPhaseCounter, -200, 0, TodCommon.CURVE_LINEAR);
            if (this.mPhaseCounter == 0)
            {
                this.mZombiePhase = PHASE_ZOMBIE_NORMAL;
            };
        }

        public int mChilledCounter ;
        public int mHelmHealth ;
        private Color mOverrideColor ;

        public int  GetBodyDamageIndex (){
            if (this.mBodyHealth < (this.mBodyMaxHealth / 3))
            {
                return (2);
            };
            if (this.mBodyHealth < ((this.mBodyMaxHealth * 2) / 3))
            {
                return (1);
            };
            return (0);
        }
        public void  DropHead (int theDamageFlags ){
            if (!this.CanLoseBodyParts())
            {
                return;
            };
            if (!this.mHasHead)
            {
                return;
            };
            this.mHasHead = false;
            this.mBodyReanim.setTrackVisible("anim_head1", false);
            this.mBodyReanim.setTrackVisible("anim_head2", false);
            this.mBodyReanim.setTrackVisible("anim_hair", false);
            this.mBodyReanim.setTrackVisible("anim_tongue", false);
            if (theDamageFlags == DAMAGE_DOESNT_LEAVE_BODY)
            {
                return;
            };
            if (this.mZombieType == ZOMBIE_NEWSPAPER)
            {
                this.mBodyReanim.setTrackVisible("anim_hairpiece", false);
                this.mBodyReanim.setTrackVisible("anim_head_jaw", false);
                this.mBodyReanim.setTrackVisible("anim_head_glasses", false);
            };
            ZombieDrawPosition aDrawPos =this.scratchDrawPos ;
            this.GetDrawPos(aDrawPos);
            int aPosX =(int)((this.mPosX +aDrawPos.mHeadX )+aDrawPos.mImageOffsetX );
            int aPosY =(int)((this.mPosY +aDrawPos.mHeadY )+aDrawPos.mImageOffsetY );
            Point aPoint =this.mBodyReanim.getTrackPosition("anim_head1");
            aPosX = (aPosX + aPoint.x);
            aPosY = (aPosY + aPoint.y);
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                aPosX = (aPosX + 0);
                aPosY = (aPosY + 40);
            }
            else
            {
                if (this.mZombieType == ZOMBIE_NEWSPAPER)
                {
                    aPosX = (aPosX + -2);
                    aPosY = (aPosY + 22);
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_FOOTBALL)
                    {
                        aPosX = (aPosX + 4);
                        aPosY = (aPosY + 40);
                    }
                    else
                    {
                        aPosX = (aPosX + -15);
                        aPosY = (aPosY + 15);
                    };
                };
            };
            String anEffect =PVZParticles.PARTICLE_ZOMBIEHEAD ;
            if (this.mZombiePhase == PHASE_ZOMBIE_MOWERED)
            {
                anEffect = PVZParticles.PARTICLE_MOWEREDZOMBIEHEAD;
            }
            else
            {
                if (this.mZombieType == ZOMBIE_FOOTBALL)
                {
                    anEffect = PVZParticles.PARTICLE_ZOMBIEFOOTBALLHEAD;
                }
                else
                {
                    if (this.mZombieType == ZOMBIE_NEWSPAPER)
                    {
                        anEffect = PVZParticles.PARTICLE_ZOMBIENEWSPAPERHEAD;
                    }
                    else
                    {
                        if (this.mZombieType == ZOMBIE_POLEVAULTER)
                        {
                            anEffect = PVZParticles.PARTICLE_ZOMBIEPOLEVALUTHEAD;
                        };
                    };
                };
            };
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                this.DropPole();
            }
            else
            {
                if (this.mZombieType == ZOMBIE_FLAG)
                {
                    this.DropFlag();
                };
            };
            this.mHeadParticle = app.particleManager().spawnParticleSystem(anEffect);
            this.mHeadParticle.setPosition(aPosX, aPosY);
            if (this.mChilledCounter > 0)
            {
            	System.out.println("Drophead"+mHeadParticle.mEmitterList);
            	//((ParticleEmitter)this.mHeadParticle.mEmitterList.elementAt(0)).mColorOverride=gChilledColor;
            };
            mBoard.mRenderManager.add(new ParticleRenderable(this.mHeadParticle, (RENDER_LAYER_ZOMBIE + mRow)));
            app.foleyManager().playFoley(PVZFoleyType.LIMBS_POP);
        }
        public void  UpdateMowered (){
        }

        private Color mAdditiveColor ;

        public void  RiseFromGrave (int theCol ,int theRow ){
			System.out.println("RiseFromGrave."+theCol+":"+theRow);
            this.mPosX = mBoard.GridToPixelX(theCol, mRow);
            this.mPosY = this.GetPosYBasedOnRow(theRow);
            mRow = theRow;
            mX = (int)(this.mPosX);
            mY = (int)(this.mPosY);
            this.mZombiePhase = PHASE_RISING_FROM_GRAVE;
            this.mPhaseCounter = 150;
            this.mAltitude = -200;
            int aParticleX = (int)(this.mPosX+20);
            int aParticleY = (int)(this.mPosY +85);
            ParticleSystem anEffect =app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_ZOMBIERISE );
            anEffect.setPosition(aParticleX, aParticleY);
            mBoard.mRenderManager.add(new ParticleRenderable(anEffect, (RENDER_LAYER_ZOMBIE - 1)));
            app.foleyManager().playFoley(PVZFoleyType.GRAVESTONE_RUMBLE);
        }

        private ParticleSystem mHeadParticle ;

        public boolean  CanTargetPlant (CPlant thePlant ,int theAttackType ){
            if (((mBoard.IsWallnutBowlingLevel()) && (!((theAttackType == ATTACKTYPE_VAULT)))))
            {
                return (false);
            };
            if (theAttackType == ATTACKTYPE_CHEW)
            {
            };
            if (theAttackType == ATTACKTYPE_VAULT)
            {
            };
            return (true);
        }
        public boolean  IsOnBoard (){
            if (this.mFromWave == -2)
            {
                return (false);
            };
            return (true);
        }

        public Rectangle mZombieAttackRect ;
        private Rectangle mZombieHitRect ;
        public boolean mInPool ;

        public void  PlayDeathAnim (int theDamageFlags ){
            double aDeathAnimRate ;
            if ((((((this.mZombiePhase == PHASE_ZOMBIE_DYING)) || ((this.mZombiePhase == PHASE_ZOMBIE_BURNED)))) || ((this.mZombiePhase == PHASE_ZOMBIE_MOWERED))))
            {
                return;
            };
            if (theDamageFlags == DAMAGE_DOESNT_LEAVE_BODY)
            {
                this.DieNoLoot();
                return;
            };
            this.StopEating();
            this.mZombiePhase = PHASE_ZOMBIE_DYING;
            this.mVelX = 0;
            if (this.mZombieType == ZOMBIE_FOOTBALL)
            {
                aDeathAnimRate = 24;
            }
            else
            {
                aDeathAnimRate = TodCommon.RandRangeFloat(24, 30);
            };
            this.mBodyReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
            this.mBodyReanim.currentTrack("anim_death");
            this.mBodyReanim.animRate(aDeathAnimRate);
        }
        public void  AnimateChewSound (){
            CPlant aPlant =this.FindPlantTarget(ATTACKTYPE_CHEW );
            if (aPlant == null)
            {
                return;
            };
            if (aPlant != null)
            {
                if (aPlant.mSeedType == SEED_WALLNUT)
                {
                    app.foleyManager().playFoley(PVZFoleyType.CHOMP_SOFT);
                }
                else
                {
                    app.foleyManager().playFoley(PVZFoleyType.CHOMP);
                };
            };
        }
        public void  UpdateZombiePolevaulter (){
            CPlant aPlant =null;
            double aAnimDuration ;
            int aJumpDistance ;
            boolean aLanded ;
            int aOldPosX ;
            if (this.mZombiePhase == PHASE_POLEVAULTER_PRE_VAULT && this.mHasHead && this.mZombieHeight == HEIGHT_ZOMBIE_NORMAL)
            {
                aPlant = this.FindPlantTarget(ATTACKTYPE_VAULT);
                if (aPlant != null)
                {
                    this.mZombiePhase = PHASE_POLEVAULTER_IN_VAULT;
                    this.PlayZombieReanim("anim_jump", Reanimation.LOOP_TYPE_ONCE_AND_HOLD, 24);
                    aAnimDuration = ((this.mBodyReanim.frameCount() / this.mBodyReanim.animRate()) * 100);
                    aJumpDistance = ((mX - aPlant.mX) - 54);
                    if (mBoard.IsWallnutBowlingLevel())
                    {
                        aJumpDistance = 0;
                    };
                    this.mVelX = aJumpDistance / aAnimDuration;
                    this.mHasObject = false;
                };
            }
            else
            {
                if (this.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT)
                {
                    aLanded = false;
                    if (this.mBodyReanim.animTime() == 1)
                    {
                        this.mBodyReanim.animRate(24);
                        this.mBodyReanim.currentTrack("anim_walk");
                        this.mBodyReanim.loopType (Reanimation.LOOP_TYPE_ALWAYS);
                        aLanded = true;
                        this.mPosX = (this.mPosX - 101);
                    };
                    if (this.mBodyReanim.shouldTriggerTimedEvent(0.2))
                    {
                    };
                    if (this.mBodyReanim.shouldTriggerTimedEvent(0.4))
                    {
                        app.foleyManager().playFoley(PVZFoleyType.POLEVAULT);
                    };
                    if (aLanded)
                    {
                        mX = (int)this.mPosX;
                        this.mZombiePhase = PHASE_POLEVAULTER_POST_VAULT;
                        this.mZombieAttackRect = new Rectangle(10, 0, 33, 77);
                        this.StartWalkAnim(0);
                    }
                    else
                    {
                        aOldPosX = (int)this.mPosX;
                        this.mPosX = (this.mPosX - (150 * this.mBodyReanim.animTime()));
                        this.mPosY = this.GetPosYBasedOnRow(mRow);
                        this.mPosX = aOldPosX;
                    };
                };
            };
        }

        public boolean mPlayingSong ;
        public int mGroanCounter ;
        public int mShieldRecoilCounter ;

        public void  TakeBodyDamage (int theDamage ,int theDamageFlags ){
            if (theDamageFlags != DAMAGE_DOESNT_CAUSE_FLASH)
            {
                this.mJustGotShotCounter = 25;
            };
            if (theDamageFlags == DAMAGE_FREEZE)
            {
                this.ApplyChill(false);
            };
            int aOldHealth =this.mBodyHealth ;
            int oldIndex =this.GetBodyDamageIndex ();
            this.mBodyHealth = (this.mBodyHealth - theDamage);
            int newIndex =this.GetBodyDamageIndex ();
            this.UpdateDamageStates(theDamageFlags);
            if (this.mBodyHealth <= 0)
            {
                this.mBodyHealth = 0;
                this.PlayDeathAnim(theDamageFlags);
                this.DropLoot();
            };
        }
        public void  UpdateAnimSpeed (){
            int aTrackIndex ;
            if (!this.IsOnBoard())
            {
                return;
            };
            if (this.IsDeadOrDying())
            {
                this.mBodyReanim.animRate(this.mOrginalAnimRate);
                return;
            };
            if (this.mIsEating)
            {
                if (this.mZombieType == ZOMBIE_POLEVAULTER)
                {
                    if (this.mChilledCounter > 0)
                    {
                        this.mBodyReanim.animRate(10);
                    }
                    else
                    {
                        this.mBodyReanim.animRate(20);
                    };
                }
                else
                {
                    if (this.mChilledCounter > 0)
                    {
                        this.mBodyReanim.animRate(18);
                    }
                    else
                    {
                        this.mBodyReanim.animRate(36);
                    };
                };
                return;
            };
            if (this.ZombieNotWalking())
            {
                this.mBodyReanim.animRate(this.mOrginalAnimRate);
                return;
            };
            int i =0;
            while (i < this.mBodyReanim.definition().tracks.length())
            {
            	if(((ReanimTrack)this.mBodyReanim.definition().tracks.elementAt(i)).name=="_ground")
                {
                    aTrackIndex = i;
                    break;
                };
                i++;
            };
            double aDistance =this.mBodyReanim.getTrackVelocity("_ground",true );
            if ( aDistance < TodCommon.EPSILON)
            {
                return;
            };
            double aFramesPerPixel =(this.mBodyReanim.frameCount() /aDistance );
            double aSpeed =(((this.mVelX *aFramesPerPixel )*31)/this.mScaleZombie );

            this.mBodyReanim.animRate(aSpeed);
            if (this.mChilledCounter > 0)
            {
                this.mBodyReanim.animRate((this.mBodyReanim.animRate() * 0.5));
            };
        }
        public void  UpdateActions (){
            if (this.mZombieHeight == HEIGHT_FALLING)
            {
                this.UpdateZombieFalling();
            };
            if (this.mZombieType == ZOMBIE_POLEVAULTER)
            {
                this.UpdateZombiePolevaulter();
            };
            if (this.mZombieType == ZOMBIE_NEWSPAPER)
            {
                this.UpdateZombieNewspaper();
            };
        }

        public int mHelmMaxHealth ;

        public void  UpdateDamageStates (int theDamageFlags ){
            if (!this.CanLoseBodyParts())
            {
                return;
            };
            int aHealthLeft =(this.mBodyHealth /this.mBodyMaxHealth );
            if (this.mHasArm && aHealthLeft <= 0.66 && this.mBodyHealth > 0)
            {
                this.DropArm(theDamageFlags);
            };
            if (this.mHasHead && aHealthLeft <= 0.33)
            {
                this.DropHead(theDamageFlags);
                this.DropLoot();
                this.StopZombieSound();
                if (mBoard.HasLevelAwardDropped())
                {
                    this.PlayDeathAnim(theDamageFlags);
                };
            };
        }

        public boolean mDroppedLoot ;

        public void  StartWalkAnim (int theBlendTime ){
            if (this.mZombiePhase == PHASE_NEWSPAPER_MAD)
            {
                this.PickRandomSpeed();
                this.PlayZombieReanim("anim_walk_nopaper", Reanimation.LOOP_TYPE_ALWAYS, 0);
                return;
            };
            double aWalkPermuation =(Math.random ()*2);
            if (this.mZombieType == ZOMBIE_FLAG)
            {
                aWalkPermuation = 0;
            };
            if (aWalkPermuation == 0)
            {
                if (this.mZombieType != ZOMBIE_POLEVAULTER && this.mZombieType != ZOMBIE_NEWSPAPER && this.mZombieType != ZOMBIE_FOOTBALL)
                {
                    this.mBodyReanim.currentTrack("anim_walk2");
                    return;
                };
            };
            this.mBodyReanim.currentTrack("anim_walk");
            this.PickRandomSpeed();
        }
        public void  AnimateChewEffect (){
            ZombieDrawPosition aDrawPos ;
            int aPosX ;
            int aPosY ;
            ParticleSystem anEffect ;
            CPlant aPlant =this.FindPlantTarget(ATTACKTYPE_CHEW );
            if (aPlant == null)
            {
                return;
            };
            if (aPlant != null)
            {
                if (aPlant.mSeedType == SEED_WALLNUT)
                {
                    aDrawPos = this.scratchDrawPos;
                    this.GetDrawPos(aDrawPos);
                    aPosX = (int)(this.mPosX + 37);
                    aPosY = (int)((this.mPosY + 40) + aDrawPos.mBodyY);
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_WALLNUTEATSMALL);
                    anEffect.setPosition(aPosX, aPosY);
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
                };
            };
            aPlant.mEatenFlashCountdown = Math.max(aPlant.mEatenFlashCountdown, 25);
        }

        private Matrix mScratchMatrix ;

        public void  SetupDoorArms (boolean theShow ){
            boolean aRenderArms =true ;
            boolean aRenderDoor =false;
            if (theShow)
            {
                aRenderArms = false;
                aRenderDoor = true;
            };
            this.mBodyReanim.setTrackVisible("anim_innerarm1", aRenderArms);
            this.mBodyReanim.setTrackVisible("anim_innerarm2", aRenderArms);
            this.mBodyReanim.setTrackVisible("anim_innerarm3", aRenderArms);
            this.mBodyReanim.setTrackVisible("Zombie_outerarm_lower", aRenderArms);
            this.mBodyReanim.setTrackVisible("Zombie_outerarm_hand", aRenderArms);
            this.mBodyReanim.setTrackVisible("Zombie_outerarm_upper", aRenderArms);
            this.mBodyReanim.setTrackVisible("anim_innerarm", aRenderArms);
            this.mBodyReanim.setTrackVisible("Zombie_outerarm_screendoor", aRenderDoor);
            this.mBodyReanim.setTrackVisible("Zombie_innerarm_screendoor", aRenderDoor);
            this.mBodyReanim.setTrackVisible("Zombie_innerarm_screendoor_hand", aRenderDoor);
        }

        public int mBodyMaxHealth ;

        public boolean  TrySpawnLevelAward (){
            String aCoinType ;
            int aGridX ;
            if (!this.IsOnBoard())
            {
                return (false);
            };
            if (mBoard.HasLevelAwardDropped())
            {
                return (false);
            };
            if (this.mDroppedLoot)
            {
                return (false);
            };
            if (app.IsScaryPotterLevel())
            {
                if (!mBoard.mChallenge.ScaryPotterIsCompleted())
                {
                    return (false);
                };
            }
            else
            {
                if (mBoard.mCurrentWave < mBoard.mNumWaves)
                {
                    return (false);
                };
                if (mBoard.AreEnemyZombiesOnScreen())
                {
                    return (false);
                };
            };
            mBoard.mLevelAwardSpawned = true;
            app.mBoardResult = PVZApp.BOARDRESULT_WON;
            Rectangle aZombieRect =this.GetZombieRect ();
            int aCenterX =(aZombieRect.x +(aZombieRect.width() /2));
            int aCenterY =(aZombieRect.y +(aZombieRect.height() /4));
            mBoard.RemoveAllZombies();
            if (mBoard.mLevel == 14)
            {
                aCoinType = COIN_NONE;
                mBoard.FadeOutLevel();
            }
            else
            {
                if (app.IsScaryPotterLevel())
                {
                    aCoinType = COIN_NONE;
                    aGridX = mBoard.PixelToGridXKeepOnBoard((int)(this.mPosX + 75), (int)this.mPosY);
                    mBoard.mChallenge.PuzzlePhaseComplete(aGridX, mRow);
                }
                else
                {
                    if (app.IsAdventureMode() && mBoard.mLevel < 14)
                    {
                        if (mBoard.mLevel == 9)
                        {
                            aCoinType = COIN_NOTE;
                        }
                        else
                        {
                            if (mBoard.mLevel == 4)
                            {
                                aCoinType = COIN_SHOVEL;
                            }
                            else
                            {
                                aCoinType = COIN_FINAL_SEED_PACKET;
                            };
                        };
                    }
                    else
                    {
                        if (app.IsSurvivalMode())
                        {
                            aCoinType = COIN_NONE;
                            mBoard.FadeOutLevel();
                        }
                        else
                        {
                            aCoinType = COIN_NONE;
                        };
                    };
                };
            };
            String aMotion =COIN_MOTION_COIN ;
            app.foleyManager().playFoley(PVZFoleyType.SPAWN_SUN);
            mBoard.AddCoin(aCenterX, aCenterY, aCoinType, aMotion);
            this.mDroppedLoot = true;
            return (true);
        }
        public void  PlayZombieReanim (String theTrackName ,ReanimLoopType theLoopType ,int theAnimRate ){
            if (this.mBodyReanim == null)
            {
                return;
            };
            this.mBodyReanim.currentTrack(theTrackName);
            this.mBodyReanim.loopType(theLoopType);
            this.mBodyReanim.animRate(theAnimRate);
            if (theAnimRate != 0)
            {
                this.mOrginalAnimRate = theAnimRate;
            };
            this.UpdateAnimSpeed();
        }

        public  Zombie (){
            super();
            this.mOverrideColor = Color.ARGB(1, 1, 1, 1);
            this.mAdditiveColor = Color.ARGB(1, 0, 0, 0);
            this.scratchDrawPos = new ZombieDrawPosition();
            this.mScratchMatrix = new Matrix();
            this.mZombieHitRect = new Rectangle();
        }
    }


