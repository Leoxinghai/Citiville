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

import com.xiyu.flash.games.pvz.logic.GameObject;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.resources.reanimator.ReanimLoopType;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class CPlant extends GameObject {

        public static final int RENDER_LAYER_LAWN =300000;
        public static final int SEED_PUFFSHROOM =8;
        public static final int STATE_SQUASH_RISING =5;
        public static final int STATE_CHOMPER_BITING =8;
        public static final int PHASE_ZOMBIE_BURNED =2;
        public static final int SEED_PEASHOOTER =0;
        public static final int STATE_BOWLING_DOWN =14;
        public static final int REANIM_CHERRYBOMB =3;
        public static final int PHASE_ZOMBIE_DYING =1;
        public static final int RENDER_LAYER_SCREEN_FADE =900000;
        public static final int PROJECTILE_PUFF =2;
        public static final int STATE_SUNSHROOM_GROWING =16;
        public static final int DAMAGE_HITS_SHIELD_AND_BODY =1;
        public static final int REANIM_SQUASH =4;
        public static final int SEED_GRAVEBUSTER =11;
        public static final int RENDER_LAYER_FOG =500000;
        public static final int STATE_BOWLING_UP =13;
        public static final int DAMAGE_SPIKE =5;
        public static final int STATE_CHOMPER_SWALLOWING =12;
        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int STATE_READY =1;
        public static final int SEED_SUNFLOWER =1;
        private static final int HELMTYPE_PAIL =2;
        public static final int RENDER_LAYER_PROJECTILE =(RENDER_LAYER_LAWN +5000);//305000
        public static final int PHASE_ZOMBIE_NORMAL =0;
        public static final int SEED_NONE =-1;
        public static final int SEED_CHERRYBOMB =2;
        public static final int STATE_CHOMPER_DIGESTING =11;
        public static final int SEED_CHOMPER =6;
        public static final int PROJECTILE_SNOWPEA =1;
        public static final int RENDER_LAYER_GROUND =200000;
        public static final int DAMAGE_FREEZE =2;
        public static final int STATE_DOINGSPECIAL =2;
        public static final int DAMAGE_BYPASSES_SHIELD =0;
        public static final int TICKS_PER_SECOND =100;
        public static final int SEED_SUNSHROOM =9;
        public static final int RENDER_LAYER_PLANT =(RENDER_LAYER_LAWN +2000);//302000
        public static final int SEED_WALLNUT =3;
        public static final int PHASE_POLEVAULTER_IN_VAULT =5;
        public static final int RENDER_LAYER_PARTICLE =(RENDER_LAYER_LAWN +7000);//307000
        private static final int HELMTYPE_NONE =0;
        public static final int STATE_SQUASH_PRE_LAUNCH =4;
        public static final int PROJECTILE_PEA =0;
        private static final int HELMTYPE_TRAFFIC_CONE =1;
        public static final int RENDER_LAYER_ABOVE_UI =800000;
        public static final int SEED_REPEATER =7;
        public static final int STATE_SUNSHROOM_BIG =17;
        public static final int STATE_GRAVEBUSTER_LANDING =18;
        public static final int STATE_GRAVEBUSTER_EATING =19;
        public static final int STATE_CHOMPER_BITING_MISSED =10;
        public static final int SEED_FUMESHROOM =10;
        public static final int SEED_SNOWPEA =5;
        public static final int STATE_CHOMPER_BITING_GOT_ONE =9;
        public static final int STATE_NOTREADY =0;
        public static final int WEAPON_PRIMARY =0;
        public static final int PHASE_POLEVAULTER_PRE_VAULT =4;
        public static final int RENDER_LAYER_TOP =400000;
        public static final int RENDER_LAYER_LAWN_MOWER =(RENDER_LAYER_LAWN +6000);//306000
        public static final int DAMAGE_DOESNT_LEAVE_BODY =4;
        public static final int REANIM_SUNFLOWER =2;
        public static final int SEED_SQUASH =4;
        public static final int PHASE_POLEVAULTER_POST_VAULT =6;
        public static final int STATE_SQUASH_LOOK =3;
        public static final int REANIM_NONE =-1;
        public static final int REANIM_PEASHOOTER =0;
        public static final int STATE_SQUASH_DONE_FALLING =7;
        public static final int RENDER_LAYER_UI_TOP =700000;
        public static final int PHASE_ZOMBIE_MOWERED =3;
        public static final int REANIM_WALLNUT =1;
        public static final int SEED_LEFTPEATER =49;
        public static final int RENDER_LAYER_COIN_BANK =600000;
        public static final int RENDER_LAYER_GRAVE_STONE =(RENDER_LAYER_LAWN +1000);//301000
        public static final int STATE_SQUASH_FALLING =6;
        public static final int STATE_SUNSHROOM_SMALL =15;
        public static final int GRAVE_BUSTER_EAT_TIME =400;
        public static final int DAMAGE_DOESNT_CAUSE_FLASH =3;
        public static final int RENDER_LAYER_ZOMBIE =(RENDER_LAYER_LAWN +3000);//303000
        public static final int RENDER_LAYER_UI_BOTTOM =100000;

        public Rectangle  GetPlantAttackRect (int thePlantWeapon ){
            Rectangle aRect ;
            if (mBoard.IsWallnutBowlingLevel())
            {
                aRect = new Rectangle((mX + 0), mY, (mWidth - 14), mHeight);
            }
            else
            {
                if (this.mSeedType == SEED_SQUASH)
                {
                    aRect = new Rectangle((mX + 14), mY, (mWidth - 23), mHeight);
                }
                else
                {
                    if (this.mSeedType == SEED_CHOMPER)
                    {
                        aRect = new Rectangle((mX + 54), mY, 28, mHeight);
                    }
                    else
                    {
                        if (this.mSeedType == SEED_PUFFSHROOM)
                        {
                            aRect = new Rectangle((mX + 40), mY, 155, mHeight);
                        }
                        else
                        {
                            if (this.mSeedType == SEED_FUMESHROOM)
                            {
                                aRect = new Rectangle((mX + 40), mY, 230, mHeight);
                            }
                            else
                            {
                                if (this.mSeedType == SEED_LEFTPEATER)
                                {
                                    aRect = new Rectangle(0, mY, mX, mHeight);
                                }
                                else
                                {
                                    aRect = new Rectangle((mX + 41), mY, 540, mHeight);
                                };
                            };
                        };
                    };
                };
            };
            return (aRect);
        }

        public int mPlantMaxHealth ;
        public int mFrameLength ;
        public int mFrame ;
        public Rectangle mPlantRect ;

        public void  UpdateReanim (){
            this.mBodyReanimation.update();
            if (this.mHeadReanimation!=null)
            {
            };
        }
        public void  DoSpecial (){
        }

        public int mLaunchRate ;

        public Zombie  FindTargetZombie (int theRow ,int thePlantWeapon ){
            Zombie aZombie ;
            int aRowDiff ;
            int aRange ;
            Rectangle aZombieRect ;
            int aOverlap ;
            int aPickWeight ;
            Rectangle aAttackRect =this.GetPlantAttackRect(thePlantWeapon );
            int aHighestWeight =0;
            Zombie aBestZombie =null;

			for(int i =0; i<mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)mBoard.mZombies.elementAt(i);
                aRowDiff = (aZombie.mRow - theRow);
                aRange = 0;
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
                        if (aZombie.mFromWave == Zombie.ZOMBIE_WAVE_CUTSCENE)
                        {
                        }
                        else
                        {
                            if (this.mSeedType == SEED_CHOMPER)
                            {
                                if (((aZombie.IsDeadOrDying()) || (!(aZombie.mHasHead))))
                                {
                                    continue;
                                };
                                if (((aZombie.mIsEating) || ((this.mState == STATE_CHOMPER_BITING))))
                                {
                                    aRange = 60;
                                };
                            };
                            if (this.mSeedType == SEED_EXPLODE_O_NUT)
                            {
                                if (aZombie.mZombiePhase == PHASE_POLEVAULTER_IN_VAULT)
                                {
                                    continue;
                                };
                            };
                            aZombieRect = aZombie.GetZombieRect();
                            aOverlap = mBoard.GetRectOverlap(aAttackRect, aZombieRect);
//                            System.out.println("FindZombie.2 " +aOverlap+":"+ (-(aRange)));
                            if (aOverlap < -(aRange))
                            {
//                            	System.out.println("FindZombie.2.2 ");
                            }
                            else
                            {
                                //aPickWeight = -(aZombieRect.x);
                            	aPickWeight = aZombieRect.x;
//                                System.out.println("FindZombie.3"+aPickWeight+":"+aHighestWeight+":"+aZombie);
                                if ( aBestZombie!=null || aPickWeight > aHighestWeight)
                                {
//                                	System.out.println("FindZombie.3.2 ");
                                	aHighestWeight = aPickWeight;
                                    aBestZombie = aZombie;
                                };
                            };
                        };
                    };
                };
            };
            return (aBestZombie);
        }

        public Reanimation mBodyReanimation ;

        public void  PlayBodyReanim (String theTrackName ,ReanimLoopType theLoopType ,int theBlendTime ,int theAnimRate ){
        }
        public void  SetSleeping (boolean theIsAsleep ){
            double aAnimTime ;
            if (this.mIsAsleep == theIsAsleep)
            {
                return;
            };
            this.mIsAsleep = theIsAsleep;
            if (theIsAsleep)
            {
                aAnimTime = this.mBodyReanimation.animTime();
                this.mBodyReanimation.currentTrack("anim_sleep");
                this.mBodyReanimation.animTime(aAnimTime);
            }
            else
            {
                aAnimTime = this.mBodyReanimation.animTime();
                this.mBodyReanimation.currentTrack("anim_idle");
                this.mBodyReanimation.animTime(aAnimTime);
            };
        }
        public void  UpdateBowling (){
            int aPosX ;
            int aPosY ;
            ParticleSystem anEffect ;
            double aSpeed =this.mBodyReanimation.getTrackVelocity("_ground",true);
            mX = (int)(mX - aSpeed);
            if (mX > 540)
            {
                this.Die();
            };
            int aRowSpeed =2;
            if (this.mState == STATE_BOWLING_UP)
            {
                mY = (mY - aRowSpeed);
            }
            else
            {
                if (this.mState == STATE_BOWLING_DOWN)
                {
                    mY = (mY + aRowSpeed);
                };
            };
            int aDiffY =(mBoard.GridToPixelY(0,mRow )-mY );
            if ((((aDiffY > 2)) || ((aDiffY < -2))))
            {
                return;
            };
            int aNewState =this.mState ;
            if ((((aNewState == STATE_BOWLING_UP)) && ((mRow == 0))))
            {
                aNewState = STATE_BOWLING_DOWN;
            }
            else
            {
                if ((((aNewState == STATE_BOWLING_DOWN)) && ((mRow == 4))))
                {
                    aNewState = STATE_BOWLING_UP;
                };
            };
            Zombie aZombie =this.FindTargetZombie(mRow ,WEAPON_PRIMARY );
            if (aZombie!=null)
            {
                aPosX = (mX + (mWidth / 2));
                aPosY = (mY + (mHeight / 2));
                if (this.mSeedType == SEED_EXPLODE_O_NUT)
                {
                    app.foleyManager().playFoley(PVZFoleyType.CHERRYBOMB);
                    app.foleyManager().playFoley(PVZFoleyType.BOWLINGIMPACT2);
                    mBoard.KillAllZombiesInRadius(mRow, aPosX, aPosY, 90, 1, true);
                    anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_POWIE);
                    anEffect.setPosition(aPosX, aPosY);
                    mBoard.mRenderManager.add(new ParticleRenderable(anEffect,0));
                    mBoard.ShakeBoard(2, -4);
                    this.Die();
                    return;
                };
                app.foleyManager().playFoley(PVZFoleyType.BOWLINGIMPACT);
                if (aZombie.mHelmType != HELMTYPE_NONE)
                {
                    if (aZombie.mHelmType == HELMTYPE_PAIL)
                    {
                        app.foleyManager().playFoley(PVZFoleyType.SHIELD_HIT);
                    }
                    else
                    {
                        if (aZombie.mHelmType == HELMTYPE_TRAFFIC_CONE)
                        {
                            app.foleyManager().playFoley(PVZFoleyType.PLASTIC_HIT);
                        };
                    };
                    aZombie.TakeHelmDamage(900, 0);
                }
                else
                {
                    aZombie.TakeDamage(1800, 0);
                };
                if ((((mRow == 4)) || ((this.mState == STATE_BOWLING_DOWN))))
                {
                    aNewState = STATE_BOWLING_UP;
                }
                else
                {
                    if ((((mRow == 0)) || ((this.mState == STATE_BOWLING_UP))))
                    {
                        aNewState = STATE_BOWLING_DOWN;
                    }
                    else
                    {
                        if ((Math.random() * 2) == 0)
                        {
                            aNewState = STATE_BOWLING_DOWN;
                        }
                        else
                        {
                            aNewState = STATE_BOWLING_UP;
                        };
                    };
                };
            };
            if (aNewState == STATE_BOWLING_UP)
            {
                this.mState = STATE_BOWLING_UP;
                mRow--;
            }
            else
            {
                if (aNewState == STATE_BOWLING_DOWN)
                {
                    this.mState = STATE_BOWLING_DOWN;
                    mRow++;
                };
            };
        }

        public int mBlinkCountdown ;
        public int mDisappearCountdown ;
        public Reanimation mHeadReanimation ;

        public boolean  IsOnBoard (){
            return (true);
        }
        public void  DoBlink (){
        }

        public int mPlantCol ;
        public int mState ;
        public int mStartRow ;
        public Zombie mTargetZombie ;
        public boolean mHighlighted ;
        public int mWakeUpCounter ;
        public boolean mSquished ;

        public boolean  IsNocturnal (int theSeedtype ){
            if (theSeedtype == SEED_PUFFSHROOM || theSeedtype == SEED_SUNSHROOM || theSeedtype == SEED_FUMESHROOM)
            {
                return (true);
            };
            return (false);
        }

        public int mTargetX ;
        public int mTargetY ;
        public boolean mAnimPing ;

        public void  Animate (){
        }

        public Reanimation mBlinkReanimation ;
        public int mSubclass ;

        public int  getRow (){
            return (mRow);
        }
        public void  MouseDown (int x ,int y ){
        }

        public int mRecentlyEatenCountdown ;

        public int  getCol (){
            return (this.mPlantCol);
        }

        public int mEatenFlashCountdown ;

        public void  UpdateReanimColor (){
            int aFade ;
            if (this.mSeedType == SEED_EXPLODE_O_NUT)
            {
                this.mBodyReanimation.useColor = true;
                this.mBodyReanimation.overrideColor.alpha = 1;
                this.mBodyReanimation.overrideColor.red = 1;
                this.mBodyReanimation.overrideColor.green = (int)0.25;
                this.mBodyReanimation.overrideColor.blue = (int)0.25;
            };
            if (this.mHighlighted)
            {
                this.mBodyReanimation.useColor = true;
                this.mBodyReanimation.overrideColor.alpha = 1;
                this.mBodyReanimation.overrideColor.red = 1;
                this.mBodyReanimation.overrideColor.green = 1;
                this.mBodyReanimation.overrideColor.blue = 1;
                this.mBodyReanimation.additiveColor.alpha = (50 / 0xFF);
                this.mBodyReanimation.additiveColor.red = (int)0.25;
                this.mBodyReanimation.additiveColor.green = (int)0.25;
                this.mBodyReanimation.additiveColor.blue = (int)0.25;
            }
            else
            {
                if (this.mEatenFlashCountdown > 0)
                {
                    aFade = TodCommon.ClampInt((this.mEatenFlashCountdown * 3), 0, 0xFF);
                    aFade = (aFade / 0xFF);
                    this.mBodyReanimation.useColor = true;
                    this.mBodyReanimation.additiveColor.alpha = 0;
                    this.mBodyReanimation.additiveColor.red = aFade;
                    this.mBodyReanimation.additiveColor.green = aFade;
                    this.mBodyReanimation.additiveColor.blue = aFade;
                }
                else
                {
                    this.mBodyReanimation.useColor = false;
                };
            };
        }

        public int mSeedType ;

        public Rectangle  GetPlantRect (){
            Rectangle aRect ;
            aRect = new Rectangle((mX + 10), mY, (mWidth - 8), mHeight);
            if (this.mSeedType == SEED_LEFTPEATER)
            {
                aRect.x = (aRect.x + 20);
            };
            return (aRect);
        }

        public int mLaunchCounter ;
        public int mReanimationType ;
        public int mNumFrames ;

        public boolean  IsInPlay (){
            return (true);
        }
        public void  Draw (Graphics2D g ){
            int aImageIndex ;
            if ((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD))))
            {
            };
            int aImageRow ;
            int aOffsetX =0;
            int aOffsetY =0;
            
            this.mBodyReanimation.x(mX);
            this.mBodyReanimation.y(mY);
            if (mBoard.IsWallnutBowlingLevel())
            {
                this.mBodyReanimation.drawLerp(g,null,0);
            }
            else
            {
                this.mBodyReanimation.draw(g);
            };
            if (this.mHeadReanimation !=null)
            {
            };
        }
        public void  EndBlink (){
        }
        public void  Update (){
        }

        public Rectangle mPlantAttackRect ;
        public int mShootingCounter ;
        public boolean mIsAsleep ;
        public int mDoSpecialCountdown ;
        public boolean mDead ;

        public boolean  getDead (){
            return (this.mDead);
        }
        public void  UpdateBlink (){
        }

        public int mStateCountdown ;

        public void  Die (){
            this.mDead = true;
        }
        public void  PlayIdleAnim (int theRate ){
        }

        public int mPlantHealth ;
        public int mAnimCounter ;

    }


