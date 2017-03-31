package Modules.achievements;

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
import Engine.Managers.*;
import Modules.achievements.data.*;
import Modules.achievements.events.*;
import Modules.achievements.transactions.*;
import Modules.stats.types.*;
//import flash.events.*;
//import flash.utils.*;

    public class AchievementManager extends EventDispatcher
    {
        protected boolean m_isUpdated ;
        protected Dictionary m_groups ;

        public  AchievementManager ()
        {
            this.m_groups = new Dictionary();
            return;
        }//end

        public void  loadServerData (Object param1 ,Object param2 ,boolean param3 )
        {
            String _loc_4 =null ;
            Object _loc_5 =null ;
            Object _loc_6 =null ;
            XML _loc_7 =null ;
            if (param1 == null)
            {
                return;
            }
            this.m_isUpdated = this.m_isUpdated || param3;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_4 = param1.get(i0);

                _loc_5 = param1.get(_loc_4);
                _loc_6 = param2 && param2.get(_loc_4) ? (param2.get(_loc_4)) : ({});
                if (this.m_groups.get(_loc_4) == null)
                {
                    _loc_7 = Global.gameSettings().getAchievementGroupByName(_loc_4);
                    if (_loc_7 != null)
                    {
                        this.m_groups.put(_loc_4,  new AchievementGroup(_loc_7));
                    }
                }
                if (this.m_groups.get(_loc_4) != null)
                {
                    (this.m_groups.get(_loc_4) as AchievementGroup).update(_loc_5, _loc_6);
                }
            }
            dispatchEvent(new AchievementGroupsUpdatedEvent(AchievementGroupsUpdatedEvent.UPDATED));
            return;
        }//end

        public boolean  isUpdated ()
        {
            return this.m_isUpdated;
        }//end

        public boolean  purchaseFinish (String param1 ,String param2 )
        {
            _loc_3 = this.getAchievement(param1 ,param2 );
            if (_loc_3 == null)
            {
                return false;
            }
            _loc_4 = _loc_3.state ==Achievement.UNINIT || _loc_3.state == Achievement.STARTED;
            if (!(_loc_3.state == Achievement.UNINIT || _loc_3.state == Achievement.STARTED) || Global.player.cash < _loc_3.cashcost)
            {
                return false;
            }
            GameTransactionManager.addTransaction(new TAchievementsPurchaseFinish(param1, param2));
            Global.player.cash = Global.player.cash - _loc_3.cashcost;
            this.setAchievementState(param1, param2, Achievement.FINISHED);
            StatsManager.count(StatsCounterType.ACHIEVEMENTS, StatsKingdomType.ACH_PURCHASE_FINISH, param1, param2);
            return true;
        }//end

        public boolean  claimReward (String param1 ,String param2 )
        {
            _loc_3 = this.getAchievement(param1 ,param2 );
            if (_loc_3 == null || _loc_3.state != Achievement.FINISHED)
            {
                return false;
            }
            GameTransactionManager.addTransaction(new TAchievementsReward(param1, param2));
            Global.player.giveRewards(_loc_3.rewards, true);
            this.setAchievementState(param1, param2, Achievement.REWARDED);
            StatsManager.count(StatsCounterType.ACHIEVEMENTS, StatsKingdomType.ACH_CLAIM_REWARD, param1, param2);
            return true;
        }//end

        protected void  setAchievementState (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = this.m_groups.get(param1) ;
            if (this.m_groups.get(param1) == null)
            {
                return;
            }
            _loc_5 = _loc_4.getAchievement(param2 );
            if (_loc_4.getAchievement(param2) == null)
            {
                return;
            }
            _loc_5.state = param3;
            _loc_5.justUpdated = true;
            dispatchEvent(new AchievementUpdatedEvent(AchievementUpdatedEvent.UPDATED, param1, param2));
            return;
        }//end

        public void  startFullUpdate ()
        {
            GameTransactionManager.addTransaction(new TAchievementsUpdate(""));
            return;
        }//end

        public void  startGroupUpdate (String param1 )
        {
            GameTransactionManager.addTransaction(new TAchievementsUpdate(param1));
            return;
        }//end

        public Dictionary  getAchievementGroups ()
        {
            return this.m_groups;
        }//end

        public AchievementGroup  getAchievementGroup (String param1 )
        {
            return this.m_groups.get(param1);
        }//end

        public Achievement  getAchievement (String param1 ,String param2 )
        {
            return this.m_groups.get(param1) == null ? (null) : ((this.m_groups.get(param1) as AchievementGroup).getAchievement(param2));
        }//end

    }




