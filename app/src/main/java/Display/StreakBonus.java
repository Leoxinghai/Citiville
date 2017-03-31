package Display;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.xiyu.util.Dictionary;

import Classes.*;
import Classes.util.*;
import Transactions.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;

    public class StreakBonus extends GameSprite
    {
        protected XML m_xml ;
        protected DisplayObject m_progressBarBack ;
        protected DisplayObject m_progressBar ;
        protected int m_originalProgressBarWidth ;
        protected double m_expireTime ;
        protected double m_warningTime ;
        protected double m_activeElapsedTime ;
        protected boolean m_isActive ;
        protected boolean m_isDisplayUpdating ;
        protected boolean m_inWarningPhase ;
        protected Array m_streakMsgs ;
        protected TextField m_rewardMsg ;
        protected int m_maxesReached ;
        protected int m_tierReached ;
        protected int m_maxTiers ;
        protected double m_rewardMultiplier ;
        protected Bitmap m_warningBitmap ;
        protected BitmapData m_warningBitmapData ;
        protected boolean m_soundLoaded =false ;
        protected int m_streakSounds =6;
        protected double m_current_bonus =0;
        protected double m_current_progress =0;
        protected double m_displayed_progress =0;
        protected Array m_rewardAmounts ;
        private Array m_bonusAmountsQueue ;
        private boolean m_progressMaxed =false ;
        protected  int STREAKBONUS_REWARDTEXT_TIME =2000;
        protected  double STREAKBONUS_REWARD_DELAY =300;
        protected  double STREAKBONUS_FONT_SIZE =16;
        protected  double STREAKBONUS_REWARD_FONT_SIZE =24;
        public static  int STREAKBONUS_REWARDTEXT_XOFFSET =50;
        public static  int STREAKBONUS_XOFFSET =0;
        public static  int STREAKBONUS_YOFFSET =55;

        public  StreakBonus (XML param1 )
        {
            DisplayObject _loc_3 =null ;
            this.m_streakMsgs = new Array();
            this.m_warningBitmap = new Bitmap();
            this.m_rewardAmounts = new Array();
            this.m_bonusAmountsQueue = new Array();
            this.m_xml = param1;
            this.m_progressBarBack = new EmbeddedArt.streakFrame();
            this.m_progressBar = new EmbeddedArt.streakBar();
            this.m_progressBar.x = 8;
            this.m_progressBar.y = 5;
            this.m_originalProgressBarWidth = this.m_progressBar.width;
            _loc_2 = param1.tier ;
            this.m_maxTiers = _loc_2.length();
            this.m_expireTime = param1.@expire_time;
            this.m_warningTime = param1.@warning_time;
            this.m_rewardMultiplier = param1.@multiplier;
            this.initStreakMessages();
            this.addChild(this.m_warningBitmap);
            this.addChild(this.m_progressBarBack);
            this.addChild(this.m_progressBar);
            this.hitTestObject(this.m_progressBarBack);
            for(int i0 = 0; i0 < this.m_streakMsgs.size(); i0++)
            {
            	_loc_3 = this.m_streakMsgs.get(i0);

                this.addChild(_loc_3);
            }
            this.m_progressBar.width = 0;
            this.reset();
            return;
        }//end

        protected void  initStreakMessages ()
        {
            TextField _loc_2 =null ;
            int _loc_1 =1;
            while (_loc_1 <= this.m_maxTiers)
            {

                _loc_2 = this.createStreakMessage(ZLoc.t("Dialogs", "StreakMessage" + _loc_1), true, this.STREAKBONUS_FONT_SIZE);
                this.m_streakMsgs.put((_loc_1 - 1),  _loc_2);
                _loc_1++;
            }
            return;
        }//end

        private TextField  createStreakMessage (String param1 ,boolean param2 ,double param3 )
        {
            TextField _loc_4 =new TextField ();
            _loc_4.text = param1;
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.font = EmbeddedArt.titleFont;
            _loc_5.size = param3;
            _loc_5.color = 2193847;
            _loc_4.setTextFormat(_loc_5);
            _loc_4.filters = .get(new GlowFilter(16777215, 1, 5, 5, 15));
            _loc_4.embedFonts = EmbeddedArt.titleFontEmbed;
            _loc_4.selectable = false;
            _loc_4.mouseEnabled = false;
            _loc_4.cacheAsBitmap = param2;
            _loc_4.autoSize = TextFieldAutoSize.LEFT;
            _loc_4.alpha = 0;
            _loc_4.scaleX = 0;
            _loc_4.scaleY = 0;
            return _loc_4;
        }//end

        protected void  turnOnStreakMessage (DisplayObject param1 ,double param2 =0,double param3 =0)
        {
            DisplayObject _loc_4 =null ;
            int _loc_5 =0;
            if (!param1)
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_streakMsgs.size(); i0++)
            {
            	_loc_4 = this.m_streakMsgs.get(i0);

                if (_loc_4.alpha)
                {
                    this.turnOffStreakMessage(_loc_4);
                }
            }
            param1.scaleX = 1;
            param1.scaleY = 1;
            param1.x = this.m_progressBarBack.width / 2 + param2;
            param1.y = 10 + param3;
            _loc_5 = (-param1.width) / 2;
            param1.scaleX = 0;
            param1.scaleY = 0;
            TweenLite.to(param1, 0.2, {scaleX:1, scaleY:1, x:_loc_5 + this.m_progressBarBack.width / 2 + param2, alpha:1});
            return;
        }//end

        protected void  turnOffStreakMessage (DisplayObject param1 ,boolean param2 =false )
        {
            streakMsg = param1;
            removeMsg = param2;
void             TweenLite .to (streakMsg ,0.2,{0alpha , onComplete ()
            {
                streakMsg.scaleX = 0;
                streakMsg.scaleY = 0;
                if (removeMsg && streakMsg.parent)
                {
                    streakMsg.parent.removeChild(streakMsg);
                    streakMsg = null;
                }
                return;
            }//end
            });
            return;
        }//end

        protected void  warningTimeReached ()
        {
            GlowFilter _loc_1 =null ;
            if (!this.m_inWarningPhase)
            {
                this.m_inWarningPhase = true;
                this.m_warningBitmapData = new BitmapData(this.m_progressBarBack.width, this.m_progressBarBack.height, true, 0);
                this.m_warningBitmapData.draw(this);
                this.m_warningBitmap.bitmapData = this.m_warningBitmapData;
                _loc_1 = new GlowFilter(16711680, 0.8, 15, 15, 5);
                this.m_warningBitmap.filters = .get(_loc_1);
                this.toggleBlink();
            }
            return;
        }//end

        protected void  toggleBlink ()
        {
            if (!this.m_inWarningPhase)
            {
                this.m_warningBitmap.alpha = 0;
                return;
            }
            if (this.m_warningBitmap.alpha)
            {
void                 TweenLite .to (this .m_warningBitmap ,0.2,{0alpha , onComplete ()
            {
                toggleBlink();
                return;
            }//end
            });
            }
            else
            {
void                 TweenLite .to (this .m_warningBitmap ,0.2,{0.8alpha , onComplete ()
            {
                toggleBlink();
                return;
            }//end
            });
            }
            return;
        }//end

        protected void  reset ()
        {
            this.alpha = 0;
            this.visible = false;
            this.mouseEnabled = false;
            this.mouseChildren = false;
            this.m_current_bonus = 0;
            this.m_current_progress = 0;
            this.m_displayed_progress = 0;
            this.m_maxesReached = 0;
            this.m_tierReached = 0;
            this.m_progressMaxed = false;
            this.m_activeElapsedTime = 0;
            this.m_isActive = false;
            this.m_isDisplayUpdating = false;
            this.m_warningBitmap.alpha = 0;
            this.turnOnStreakMessage(this.m_streakMsgs.get(0));
            this.m_inWarningPhase = false;
            this.m_rewardAmounts.splice(0, this.m_rewardAmounts.length());
            return;
        }//end

        protected void  expireTimeReached ()
        {
            this.m_isActive = false;
            if (this.m_maxesReached > 0)
            {
                this.rewardStreakBonus();
                Sounds.play("doober_coin_click");
            }
            else
            {
void                 TweenLite .to (this ,0.2,{0alpha , onComplete ()
            {
                reset();
                return;
            }//end
            });
            }
            return;
        }//end

        protected void  resetExpireTimeAndShow ()
        {
            this.m_inWarningPhase = false;
            this.m_isActive = true;
            this.m_activeElapsedTime = 0;
            TweenLite.to(this, 0.5, {alpha:1});
            return;
        }//end

        protected void  stopWarning ()
        {
            this.m_inWarningPhase = false;
            return;
        }//end

        public void  incProgress (double param1 )
        {
            this.visible = true;
            this.mouseEnabled = true;
            this.enableDisplayUpdating();
            this.resetExpireTimeAndShow();
            if (this.m_rewardMsg)
            {
                this.turnOffStreakMessage(this.m_rewardMsg, true);
            }
            if (!this.m_progressMaxed && this.m_tierReached < this.m_maxTiers)
            {
                this.m_current_bonus = this.m_current_bonus + param1;
                this.m_current_progress = this.m_current_progress + this.bonusPerClick;
                if (this.m_current_progress >= this.calculateMaxProgress())
                {
                    (this.m_tierReached + 1);
                    this.m_progressMaxed = true;
                }
            }
            else
            {
                this.m_bonusAmountsQueue.push(param1);
            }
            return;
        }//end

        protected void  maxProgressReached ()
        {
            double _loc_1 =0;
            (this.m_maxesReached + 1);
            this.m_current_bonus = 0;
            this.m_current_progress = 0;
            this.m_displayed_progress = 0;
            if (this.m_maxesReached <= this.m_streakSounds)
            {
                Sounds.play("bonus_ramp_" + this.m_maxesReached);
            }
            else if (this.m_maxesReached > this.m_streakSounds)
            {
                Sounds.play("bonus_ramp_" + (this.m_maxesReached - this.m_streakSounds));
            }
            if (this.m_maxesReached >= this.m_maxTiers)
            {
                this.rewardStreakBonus();
            }
            else
            {
                this.turnOnStreakMessage(this.m_streakMsgs.get(this.m_maxesReached));
            }
            this.m_progressMaxed = false;
            while (this.m_bonusAmountsQueue.length > 0 && !this.m_progressMaxed && this.m_tierReached < this.m_maxTiers)
            {

                _loc_1 = this.m_bonusAmountsQueue.shift();
                this.m_current_bonus = this.m_current_bonus + _loc_1;
                this.m_current_progress = this.m_current_progress + this.bonusPerClick;
                if (this.m_current_progress >= this.calculateMaxProgress())
                {
                    (this.m_tierReached + 1);
                    this.m_progressMaxed = true;
                    break;
                }
            }
            return;
        }//end

        public void  rewardStreakBonus ()
        {
            double timeDelay ;
            totalReward = this.totalBonus;
            this.m_rewardMsg = this.createStreakMessage(ZLoc.t("Dialogs", "StreakRewardCoin", {amount:totalReward}), false, this.STREAKBONUS_REWARD_FONT_SIZE);
            this.stopWarning();
            Global.hud.addChild(this.m_rewardMsg);
            this.turnOnStreakMessage(this.m_rewardMsg, this.x - this.width + STREAKBONUS_REWARDTEXT_XOFFSET, this.y);
            this.addRewardToPlayer(totalReward);
            GameTransactionManager.addTransaction(new TStreakBonus(totalReward, this.m_tierReached));
            this.reset();
            TimerUtil .callLater (void  ()
            {
void                 TweenLite .to (this ,0.2,{0alpha , onComplete ()
                {
                    turnOffStreakMessage(m_rewardMsg, true);
                    return;
                }//end
                });
                return;
            }//end
            , this.STREAKBONUS_REWARDTEXT_TIME);
            return;
        }//end

        protected void  animateRewardTextFlyout (double param1 ,double param2 ,boolean param3 =false ,TextField param4 =null )
        {
            amount = param1;
            timeDelay = param2;
            resetBar = param3;
            rewardMsg = param4;
            TimerUtil .callLater (void  ()
            {
                Sprite spr ;
                spr = new Sprite();
                StrokeTextField statusText =new StrokeTextField(2105376,0.8);
                statusText.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                TextFormat resource_fmt =new TextFormat(EmbeddedArt.defaultFontNameBold ,STREAKBONUS_FONT_SIZE ,null ,true );
                resource_fmt.color = 4106655;
                statusText.defaultTextFormat = resource_fmt;
                statusText.toolTipText = "+" + amount;
                spr.addChild(statusText);


                Global.hud.addChild(spr);
                Point coinsHolderPoint =new Point(Global.hud.getComponent.get( "coins").x ,Global.hud.getComponent.get( "coins").y );
                bezCtrlPointX = spr(.x-coinsHolderPoint.x)/2+coinsHolderPoint.x;
                bezCtrlPointY = spr.y+200;
void                 TweenLite .to (spr ,2,{.get( bezier {bezCtrlPointX x ,bezCtrlPointY y },{coinsHolderPoint x .x ,coinsHolderPoint y .y }) ,1.5scaleX ,1.5scaleY ,Quart ease .easeOut ,false orientToBezier , onComplete ()
                {
                    if (spr.parent)
                    {
                        spr.parent.removeChild(spr);
                    }
                    addRewardToPlayer(amount);
                    if (resetBar)
                    {
                        if (rewardMsg)
                        {
                            turnOffStreakMessage(rewardMsg, true);
                        }
void                         TweenLite .to (this ,0.2,{0alpha , onComplete ()
                    {
                        reset();
                        return;
                    }//end
                    });
                    }
                    return;
                }//end
                });
                return;
            }//end
            , timeDelay);
            return;
        }//end

        protected void  addRewardToPlayer (double param1 )
        {
            Global.player.gold = Global.player.gold + param1;
            return;
        }//end

        protected double  calculateMaxProgress ()
        {
            return Number(this.m_xml.tier.get(this.m_maxesReached).@threshold);
        }//end

        protected double  bonusPerClick ()
        {
            return Number(this.m_xml.tier.get(this.m_maxesReached).@bonus_per_click);
        }//end

        protected double  calculateBonusForCurrentTier ()
        {
            _loc_1 = this.m_xml.tier.get(this.m_maxesReached).@reward_value;
            return Math.ceil(Global.player.level * this.m_rewardMultiplier * _loc_1);
        }//end

        public double  totalBonus ()
        {
            _loc_1 = this.m_xml.tier.get((this.m_tierReached -1)).@reward_value;
            return Math.ceil(Global.player.level * this.m_rewardMultiplier * _loc_1);
        }//end

        protected void  enableDisplayUpdating ()
        {
            this.m_isDisplayUpdating = true;
            return;
        }//end

        protected void  disableDisplayUpdating ()
        {
            this.m_isDisplayUpdating = false;
            return;
        }//end

        protected void  updateStreakBonus ()
        {
            if (this.m_displayed_progress >= this.m_current_progress)
            {
                this.disableDisplayUpdating();
            }
            this.m_displayed_progress = this.m_displayed_progress + this.calculateMaxProgress() * 2 / 100;
            this.updateFrame();
            return;
        }//end

        public void  updateFrame ()
        {
            if (this.alpha == 0)
            {
                TweenLite.to(this, 0.2, {alpha:1});
            }
            this.m_progressBar.width = this.m_displayed_progress / this.calculateMaxProgress() * this.m_originalProgressBarWidth;
            _loc_1 = this.calculateMaxProgress ();
            if (this.m_displayed_progress >= _loc_1)
            {
                this.maxProgressReached();
            }
            return;
        }//end

        public void  onUpdate (double param1 )
        {
            if (this.m_isActive && !UI.isModalDialogOpen)
            {
                this.m_activeElapsedTime = this.m_activeElapsedTime + param1;
                if (this.m_isDisplayUpdating)
                {
                    this.updateStreakBonus();
                }
                if (this.m_activeElapsedTime >= this.m_warningTime)
                {
                    this.warningTimeReached();
                }
                if (this.m_activeElapsedTime >= this.m_expireTime)
                {
                    this.expireTimeReached();
                }
            }
            return;
        }//end

        public double  midPointX ()
        {
            return this.x + this.m_progressBarBack.width / 2;
        }//end

        public double  midPointY ()
        {
            return this.y + this.m_progressBarBack.height / 2;
        }//end

    }



