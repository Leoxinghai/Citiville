package Classes;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import Transactions.*;

    public class DailyBonusManager
    {
        protected int m_currentBonusDay ;
        protected int m_previousBonusDay ;
        protected int m_maxRecoveryDays ;
        protected int m_maxStreak ;
        protected String m_randomReward ;

        public  DailyBonusManager (Object param1 )
        {
            this.m_currentBonusDay = param1.currentBonusDay;
            this.m_previousBonusDay = param1.previousBonusDay;
            this.m_maxRecoveryDays = Global.gameSettings().getDailyBonusMaxRecoveryDays();
            this.m_maxStreak = Global.gameSettings().getDailyBonusMaxStreak();
            _loc_2 = this.m_currentBonusDay -this.m_previousBonusDay ;
            if (_loc_2 > (this.m_maxRecoveryDays + 1))
            {
                this.m_currentBonusDay = 1;
                this.m_previousBonusDay = 0;
            }
            return;
        }//end

        public int  currentBonusDay ()
        {
            return this.m_currentBonusDay;
        }//end

        public int  previousBonusDay ()
        {
            return this.m_previousBonusDay;
        }//end

        public int  maxStreak ()
        {
            return this.m_maxStreak;
        }//end

        public String  randomReward ()
        {
            return this.m_randomReward;
        }//end

        public void  randomReward (String param1 )
        {
            this.m_randomReward = param1;
            return;
        }//end

        public boolean  isBonusAvailable ()
        {
            _loc_1 = this.m_currentBonusDay-this.m_previousBonusDay;
            _loc_2 = this.m_currentBonusDay<=0;
            _loc_3 = this.m_currentBonusDay>this.m_maxStreak;
            _loc_4 = _loc_1>(this.m_maxRecoveryDays +1);
            _loc_5 = this.m_currentBonusDay<this.m_previousBonusDay;
            _loc_6 = _loc_1==0;
            boolean _loc_7 =true ;
            if (_loc_2 || _loc_3 || _loc_4 || _loc_5 || _loc_6)
            {
                _loc_7 = false;
            }
            return _loc_7;
        }//end

        public boolean  isRecoveryNeeded ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this(.m_currentBonusDay-1)-this.m_previousBonusDay;
            if (_loc_2 > 0 && _loc_2 <= this.m_maxRecoveryDays)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public int  extraCashNeededForRecovery ()
        {
            DailyBonus _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_1 =0;
            if (this.isRecoveryNeeded)
            {
                _loc_2 = this.getDailyBonus(this.m_currentBonusDay);
                _loc_3 = _loc_2.recoveryCash;
                _loc_4 = Global.player.cash;
                _loc_1 = _loc_3 - _loc_4;
                if (_loc_1 < 0)
                {
                    _loc_1 = 0;
                }
            }
            return _loc_1;
        }//end

        public void  collectDailyBonus ()
        {
            if (!this.isBonusAvailable || this.extraCashNeededForRecovery > 0)
            {
                return;
            }
            GameTransactionManager.addTransaction(new TCollectDailyBonus(this.isRecoveryNeeded));
            StatsManager.count(StatsCounterType.DAILY_BONUS, "daysClaimed", "", "", "", "", this.m_currentBonusDay);
            StatsManager.count(StatsCounterType.DAILY_BONUS, "totalClaims");
            return;
        }//end

        public DailyBonus  getDailyBonus (int param1 )
        {
            _loc_2 = Global.gameSettings().getDailyBonus(param1);
            DailyBonus _loc_3 =new DailyBonus(_loc_2 );
            return _loc_3;
        }//end

        public Array  getDailyBonusRange (int param1 ,int param2 )
        {
            DailyBonus _loc_5 =null ;
            param1 = param1 > 0 ? (param1) : (1);
            Array _loc_3 =new Array();
            _loc_4 = param1;
            while (_loc_4 <= param2)
            {

                if (_loc_4 <= this.m_maxStreak)
                {
                    _loc_5 = this.getDailyBonus(_loc_4);
                    _loc_3.put(_loc_3.length,  _loc_5);
                }
                else
                {
                    break;
                }
                _loc_4++;
            }
            return _loc_3;
        }//end

        public boolean  isLastDay ()
        {
            return this.m_currentBonusDay == this.m_maxStreak;
        }//end

        public void  doBonusEndTasks ()
        {
            if (this.isLastDay())
            {
                UI.thawScreen();
            }
            DailyBonusDialog _loc_1 =new DailyBonusDialog("","DailyBonusRewards",0,null ,"DailyBonusRewards");
            UI.displayPopup(_loc_1);
            this.grantBonus();
            return;
        }//end

        public void  grantBonus ()
        {
            _loc_1 = this.getDailyBonus(this.m_currentBonusDay);
            this.m_previousBonusDay = this.m_currentBonusDay;
            if (this.m_currentBonusDay == Global.gameSettings().getDailyBonusMaxStreak())
            {
                if (Global.player.dailyBonusManager.randomReward)
                {
                    Global.player.inventory.addItems(Global.player.dailyBonusManager.randomReward, 1, true);
                    StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "item", _loc_1.item, "", "", 1);
                }
                else
                {
                    if (_loc_1.coin)
                    {
                        Global.player.gold = Global.player.gold + _loc_1.coin;
                        StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "coins", "", "", "", _loc_1.coin);
                    }
                    if (_loc_1.energy)
                    {
                        Global.player.updateEnergy(_loc_1.energy, new Array("energy", "earnings", "rewards", ""));
                        StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "energy", "", "", "", _loc_1.energy);
                    }
                    if (_loc_1.goods)
                    {
                        Global.player.commodities.add("goods", _loc_1.goods);
                        StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "goods", "", "", "", _loc_1.goods);
                    }
                    if (_loc_1.xp)
                    {
                        Global.player.xp = Global.player.xp + _loc_1.xp;
                        StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "xp", "", "", "", _loc_1.xp);
                    }
                }
                return;
            }
            if (_loc_1.coin)
            {
                Global.player.gold = Global.player.gold + _loc_1.coin;
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "coins", "", "", "", _loc_1.coin);
            }
            if (_loc_1.energy)
            {
                Global.player.updateEnergy(_loc_1.energy, new Array("energy", "earnings", "rewards", ""));
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "energy", "", "", "", _loc_1.energy);
            }
            if (_loc_1.food)
            {
                Global.player.commodities.add("food", _loc_1.food);
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "food", "", "", "", _loc_1.food);
            }
            if (_loc_1.goods)
            {
                Global.player.commodities.add("goods", _loc_1.goods);
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "goods", "", "", "", _loc_1.goods);
            }
            if (_loc_1.item)
            {
                Global.player.inventory.addItems(_loc_1.item, 1);
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "item", _loc_1.item, "", "", 1);
            }
            if (_loc_1.xp)
            {
                Global.player.xp = Global.player.xp + _loc_1.xp;
                StatsManager.count(StatsCounterType.DAILY_BONUS, StatsKingdomType.BONUS_AWARDED, "xp", "", "", "", _loc_1.xp);
            }
            return;
        }//end

    }




