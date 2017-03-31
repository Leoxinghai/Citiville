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
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZParticles;

//import flash.geom.Rectangle;

    public class Projectile extends GameObject {

        private static final int BOARD_HEIGHT =405;
        private static final int BOARD_WIDTH =540;
        public static final int MOTION_BACKWARDS =2;
        private static final int LAWN_YMIN =60;
        private static final int DAMAGE_HITS_SHIELD_AND_BODY =1;
        public static final int PROJECTILE_PEA =0;
        private static  double PROJECTILE_SPEED =(3.33*0.67);//2.2311
        public static final int PROJECTILE_PUFF =2;
        private static final int DAMAGE_SPIKE =5;
        private static final int LAWN_YMAX =365;
        public static final int MOTION_STRAIGHT =0;
        private static final int DAMAGE_FREEZE =2;
        private static final int DAMAGE_DOESNT_CAUSE_FLASH =3;
        private static final int LAWN_XMIN =20;
        public static final int MOTION_PUFF =1;
        private static final int DAMAGE_BYPASSES_SHIELD =0;
        private static final int GRIDSIZEY =5;
        public static final int PROJECTILE_SNOWPEA =1;
        private static final int GRIDSIZEX =9;
        private static final int DAMAGE_DOESNT_LEAVE_BODY =4;
        private static final int LAWN_XMAX =513;

        public boolean mDead ;
        private ParticleRenderable mSnowPeaParticlesRenderable ;
        private int renderCounter ;
        private ParticleSystem mSnowPeaParticles ;

        public Zombie  FindCollisionTarget (){
            Zombie aZombie ;
            int aRowDiff ;
            Rectangle aZombieRect ;
            Rectangle aProjectileRect =this.mImpactRect ;
            Zombie aZombieClosest =null;
            int aFarthestLeft =0;
			for(int i =0; i<mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)mBoard.mZombies.elementAt(i);
                aRowDiff = (aZombie.mRow - mRow);
                if (aRowDiff != 0)
                {
                }
                else
                {
                    if (aZombie.IsDeadOrDying())
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
                            if (aZombieRect.left()> this.mImpactRect.right())
                            {
                            }
                            else
                            {
                                if (aZombieRect.right() < this.mImpactRect.left())
                                {
                                }
                                else
                                {
                                    if (((aZombieClosest!=null) && ((aZombie.mX >= aFarthestLeft))))
                                    {
                                    }
                                    else
                                    {
                                        aZombieClosest = aZombie;
                                        aFarthestLeft = aZombie.mX;
                                    };
                                };
                            };
                        };
                    };
                };
            };
            return (aZombieClosest);
        }

        public int mProjectileType ;
        private int mPosX =0;
        private int mPosY =0;
        private Rectangle mImpactRect ;

        public void  DoImpact (Zombie theZombie ){
            String aSplatParticleType ="";
            int zType ;
            int aDamageFlags =0;
            if (this.mProjectileType == PROJECTILE_SNOWPEA)
            {
                aDamageFlags = DAMAGE_FREEZE;
            };
            theZombie.TakeDamage(this.mDamage, aDamageFlags);
            if (theZombie.mHelmHealth > 0)
            {
                zType = theZombie.mZombieType;
                if (theZombie.mZombieType == Zombie.ZOMBIE_TRAFFIC_CONE)
                {
                    app.foleyManager().playFoley(PVZFoleyType.PLASTIC_HIT);
                }
                else
                {
                    if ((((zType == Zombie.ZOMBIE_PAIL)) || ((zType == Zombie.ZOMBIE_DOOR))))
                    {
                        app.foleyManager().playFoley(PVZFoleyType.SHIELD_HIT);
                    };
                };
            };
            app.foleyManager().playFoley(PVZFoleyType.SPLAT);
            double aSplatPosX =(mX +(12*0.675));
            double aSplatPosY =(mY +(12*0.675));
            if (this.mProjectileType == PROJECTILE_PEA)
            {
                aSplatParticleType = PVZParticles.PARTICLE_PEASPLAT;
            }
            else
            {
                if (this.mProjectileType == PROJECTILE_SNOWPEA)
                {
                    aSplatParticleType = PVZParticles.PARTICLE_SNOWPEASPLAT;
                }
                else
                {
                    if (this.mProjectileType == PROJECTILE_PUFF)
                    {
                        aSplatPosX = (aSplatPosX - 5);
                        aSplatParticleType = PVZParticles.PARTICLE_PUFFSPLAT;
                    };
                };
            };
            if (this.mMotionType == MOTION_BACKWARDS)
            {
                aSplatPosX = (aSplatPosX - 30);
            };
            ParticleSystem anEffect =app.particleManager().spawnParticleSystem(aSplatParticleType );
            anEffect.setPosition((int)aSplatPosX, (int)aSplatPosY);
            mBoard.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
            this.Die();
        }

        public int mMotionType ;
        public int mDamageRangeFlags ;

        public void  Die (){
            if (this.mSnowPeaParticles != null)
            {
                if (this.mSnowPeaParticles.mDead == false)
                {
                    this.mSnowPeaParticles.die();
                };
            };
            this.mDead = true;
        }

        public int mProjectileAge ;

        public void  CheckForCollision (){
            if ((((mX > BOARD_WIDTH)) || (((mX + mWidth) < 0))))
            {
                this.Die();
                return;
            };
            Zombie aZombie =this.FindCollisionTarget ();
            if (aZombie != null)
            {
                this.DoImpact(aZombie);
            };
        }
        public void  Draw (Graphics2D g ){
            ImageInst aImage ;
            double aScale ;
            if (this.mProjectileType == PROJECTILE_PEA)
            {
                aImage = app.imageManager().getImageInst(PVZImages.IMAGE_PROJECTILEPEA);
                g.drawImage(aImage, mX, mY);
            }
            else
            {
                if (this.mProjectileType == PROJECTILE_PUFF)
                {
                    aImage = app.imageManager().getImageInst(PVZImages.IMAGE_PUFFSHROOM_PUFF1);
                    aScale = TodCommon.TodAnimateCurveFloat(0, 30, this.mProjectileAge, 0.3, 1, TodCommon.CURVE_LINEAR);
                    g.pushState();
                    g.scale(aScale, aScale);
                    g.drawImage(aImage, mX, mY);
                    g.popState();
                }
                else
                {
                    this.mSnowPeaParticles.draw(g);
                    aImage = app.imageManager().getImageInst(PVZImages.IMAGE_PROJECTILESNOWPEA);
                    g.drawImage(aImage, mX, mY);
                };
            };
        }

        public int mClickBackoffCounter ;

        public void  Update (){
            if ((((((mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)) || ((mBoard.mGameScene == Board.SCENE_AWARD)))) || ((mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                return;
            };
            this.mProjectileAge++;
            int aPopToFrontAge =20;
            if ((((this.mProjectileType == PROJECTILE_PEA)) || ((this.mProjectileType == PROJECTILE_SNOWPEA))))
            {
                aPopToFrontAge = 0;
            };
            int aParticleOffsetX =6;
            int aParticleOffsetY =10;
            if ((((this.mProjectileType == PROJECTILE_SNOWPEA)) && (!((this.mSnowPeaParticles == null)))))
            {
                this.mSnowPeaParticles.update();
                this.mSnowPeaParticles.setPosition((mX + aParticleOffsetX), (mY + aParticleOffsetY));
            };
            if (this.mProjectileAge > aPopToFrontAge)
            {
            };
            double deltaX =0;
            double deltaY =0;
            if (this.mMotionType == MOTION_BACKWARDS)
            {
                deltaX = (deltaX - PROJECTILE_SPEED*Math.cos(-1*mAngle));
				deltaY = (deltaY + PROJECTILE_SPEED*Math.sin(-1*mAngle));
            }
            else
            {
                deltaX = (deltaX + PROJECTILE_SPEED*Math.cos(-1*mAngle));
				deltaY = (deltaY + PROJECTILE_SPEED*Math.sin(-1*mAngle));
            };
            this.mPosX = (int)(this.mPosX + deltaX);
			//this.mPosY = (int)(this.mPosY + deltaY);
            mX = (int)(this.mPosX);
			//mY = (int)(this.mPosY);
            this.mImpactRect.x = mX;
			//this.mImpactRect.y = mY;
            if ((((this.mProjectileType == PROJECTILE_PEA)) || ((this.mProjectileType == PROJECTILE_SNOWPEA))))
            {
                this.mImpactRect.x = (int)(this.mImpactRect.x - (15 * 0.675));
            };
            this.CheckForCollision();
        }
        public void  ProjectileInitialize (int theX ,int theY ,int theRenderOrder ,int theRow ,int theProjectileType ,PVZApp app ,Board theBoard ,int theAngle){
            int aParticleOffsetX ;
            int aParticleOffsetY ;
            this.app = app;
            mBoard = theBoard;
            this.mPosX = theX;
            this.mPosY = theY;
            int aGridX =mBoard.PixelToGridXKeepOnBoard(theX ,theY );
            this.mDamage = 20;
            this.mProjectileType = theProjectileType;
            if (this.mProjectileType == PROJECTILE_PUFF)
            {
                this.mMotionType = MOTION_PUFF;
            }
            else
            {
                this.mMotionType = MOTION_STRAIGHT;
            };
            mRow = theRow;
            this.mDamageRangeFlags = 0;
            this.mDead = false;
            this.mTargetZombieID = null;
            this.mProjectileAge = 0;
            this.mClickBackoffCounter = 0;
            mWidth = (int)((40 * 0.675));
            mHeight = (int)((40 * 0.675));
            if (this.mProjectileType == PROJECTILE_SNOWPEA)
            {
                aParticleOffsetX = (int)(8 * 0.675);
                aParticleOffsetY = (int)(13 * 0.675);
                this.mSnowPeaParticles = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SNOWPEATRAIL);
                this.mSnowPeaParticles.setPosition((mX + aParticleOffsetX), (mY + aParticleOffsetY));
                this.renderCounter = 0;
            };
            mX = (int)(this.mPosX);
            mY = (int)(this.mPosY);
            this.mImpactRect.x = mX;
            this.mImpactRect.y = mY;
            this.mImpactRect.width = mWidth;
            this.mImpactRect.height = mHeight;
			this.mAngle = theAngle;
        }

        public int mDamage ;
        public Zombie mTargetZombieID ;
		public int mAngle ;

        public  Projectile (){
            this.mImpactRect = new Rectangle();
        }
    }


