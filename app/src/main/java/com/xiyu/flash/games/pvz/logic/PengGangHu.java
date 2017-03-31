package com.xiyu.flash.games.pvz.logic;

import com.thelikes.thegot2run.R;

import android.media.MediaPlayer;

import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.renderables.PengGangHuRenderable;
import com.xiyu.flash.games.pvz.resources.PVZImages;

public class PengGangHu  extends GameObject {
//		private PVZApp mApp;
//		private Board mBoard;
	
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

    public static final int STATE_SQUASH_RISING =5;
    public static final int STATE_SQUASH_FALLING =6;
    public static final int STATE_SQUASH_DONE_FALLING =7;
    
    private static  String COIN_NONE ="none";
    private static  String COIN_MOTION_FROM_SKY ="from sky";
    private static  String COIN_MOTION_FROM_PLANT ="from plant";
    private static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
    private static  String COIN_SUN ="sun";

    
        public void  draw (Graphics2D g ){

            ImageInst anImg ;
            int aOffsetX =0;
            int aOffsetY =0;
            g.pushState();
            g.drawImage(mPengImage, (mX + aOffsetX), (mY + aOffsetY));
            g.drawCircle(mX, mY, 300);
            g.popState();
        }

        public int mType;
        private ImageInst mPengImage;
        
        private int speed;

        public void  update (){
            mVisible = true;
//            if(app.widgetManager().lastMouseX < 300) {
            	mX = app.widgetManager().lastMouseX;
            	mY = app.widgetManager().lastMouseY;
//            }
            //UpdateShot();
        }
        
        private int lastMouseX;
        private int lastMouseY;
        private int mFadeCount;
        
        public PengCoin  ShootPengCoin (int theX ,int theY ,String theCoinType ,String theCoinMotion ){
        	PengCoin aCoin =new PengCoin ();
            aCoin.CoinInitialize(theX, theY, theCoinType, theCoinMotion, this.app, this.mBoard);
            mBoard.mCoins.push(aCoin);
            mBoard.mRenderManager.add(new PengGangHuRenderable(aCoin, RENDER_LAYER_COIN_BANK));
            return (aCoin);
        }

        
        public void onMouseDown(int x, int y) {
        	System.out.println("PengGangHu.down." + x +":"+y);
//        	if(x >1300 && x <1600) {
//	        	mX = x;
//	        	mY = y-100;
//        	}
        }
        
        public void onMouseMove(int x, int y) {
        	System.out.println("PengGangHu.move." + x +":"+y);
        	if(x >1300 && x <1600) {	        	
//        		mX = x;
//	        	mY = y;
//	        	this.lastMouseX = x;
//	        	this.lastMouseY = y;
        	}
        }
        
        public void onMouseUp(int x, int y) {
        	System.out.println("PengGangHu.up." + x +":"+y);
        	mState = STATE_SQUASH_RISING;
        	ShootPengCoin(x,y,COIN_SUN, COIN_MOTION_FROM_PLANT);
        	MediaPlayer mediaPlayer = MediaPlayer.create(app, R.raw.jump);
        	mediaPlayer.start();
        }
        

        private float mScale = 2;
        public boolean  MouseHitTest (int theX ,int theY ,HitResult theHitResult ){
            boolean aResult = false;
            int aOffsetX = 0;
            int aOffsetY = 0;
            if (theX >= (this.mX + aOffsetX) && theX < (this.mX + mWidth * this.mScale + aOffsetX) && theY >= (this.mY + aOffsetY) && theY < (this.mY + mHeight * this.mScale + aOffsetY))
            {
                aResult = true;
            }
            return aResult;
            
//            theX = theX + 200;
//            theY = theY + 50;
            
//            theX = theX * 2;
//            theY = theY * 2;
            /*
            if (this.mType == COIN_DYNAMITE)
            {
//                return (false);
            };
            if (this.mType == COIN_FINAL_SEED_PACKET)
            {
                aOffsetX = (int)((mWidth - (mWidth * this.mScale)));
                aOffsetY = (int)((mHeight - (mHeight * this.mScale)));
            };
            int aExtraClickSize =0;
            int aExtraClickHeight =0;
            if (this.mType == COIN_SUN)
            {
                aExtraClickSize = 15;
            };
            if (this.mDead)
            {
                aResult = false;
            }
            else
            {
                if (this.mIsBeingCollected)
                {
                    aResult = false;
                }
                else
                {
                    if ((((this.mType == COIN_USABLE_SEED_PACKET && mBoard!= null)) && (!((mBoard.mCursorObject.mCursorType == Board.CURSOR_TYPE_NORMAL)))))
                    {
                        aResult = false;
                    }
                    else
                    {
                        if ((((((((theX >= ((this.mPosX - aExtraClickSize) + aOffsetX))) && ((theX < (((this.mPosX + (mWidth * this.mScale)) + aExtraClickSize) + aOffsetX))))) && ((theY >= ((this.mPosY + aOffsetY) - aExtraClickSize))))) && ((theY < ((((this.mPosY + (mHeight * this.mScale)) + aOffsetY) + aExtraClickSize) + aExtraClickHeight)))))
                        {
                            aResult = true;
                        }
                        else
                        {
                            aResult = false;
                        };
                    };
                };
            };

            if (aResult)
            {
                System.out.println("CoinX.mouseHit " + theX+":"+(this.mPosX - aExtraClickSize + aOffsetX)+":"+(this.mPosX + (mWidth * this.mScale) + aExtraClickSize + aOffsetX) );
                System.out.println("CoinY.mouseHit " + theY+":"+(this.mPosY - aExtraClickSize + aOffsetY)+":"+(this.mPosY + (mHeight * this.mScale) + aExtraClickSize + aOffsetY) );
                theHitResult.mObject = this;
                theHitResult.mObjectType = OBJECT_TYPE_COIN;
                return (true);
            };
            theHitResult.mObject = null;
            theHitResult.mObjectType = OBJECT_TYPE_NONE;
            */
//            return (false);
        }

        int mState;
        int mStateCountdown;
        
        public void  UpdateShot (){
            Zombie aZombie ;
            int aStartX ;
            int aStartY ;
            int aOffsetY ;

            int mTargetX = mX + 200;
            
            int aTargetCol =mBoard.PixelToGridXKeepOnBoard(mTargetX ,mY );
            int aDestX =1500;//mTargetX ;
            int aDestY =600;//(mBoard.GridToPixelY(aTargetCol ,mRow )+8);
            if (mState == STATE_SQUASH_RISING)
            {
                aStartX = mX;
                aStartY = mY;
                mX = TodCommon.TodAnimateCurve(SQUASH_RISE_TIME, 20, mStateCountdown, aStartX, aDestX, TodCommon.CURVE_EASE_IN_OUT);
                mY = TodCommon.TodAnimateCurve(SQUASH_RISE_TIME, 20, mStateCountdown, aStartY, (aDestY - SQUASH_FALL_HEIGHT), TodCommon.CURVE_EASE_IN_OUT);
                if (mStateCountdown == 0)
                {
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
//                        this.DoSquashDamage();
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
                };
            };
        }
        
        
		
		public PengGangHu(PVZApp _app ,Board theBoard ){
	        super();
	        mX = 1500;
	        mY = 400;
	        app = _app;
	        mBoard = theBoard;
	        this.mPengImage = app.imageManager().getImageInst(PVZImages.IMAGE_PENG);
	        this.mWidth = mPengImage.width();
	        this.mHeight = mPengImage.height();
	//        this.mGangImage = app.imageManager().getImageInst("ZOMBIE_BUNGI_HAND");
	
	        
		}
	
}

