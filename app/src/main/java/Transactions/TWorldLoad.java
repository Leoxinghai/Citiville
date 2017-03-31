package Transactions;

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
import Display.*;
import Display.DialogUI.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Events.*;
import Modules.bandits.*;
import Modules.crew.*;
import Modules.matchmaking.*;
import Modules.minigames.*;
import Modules.socialinventory.*;
import Modules.stats.types.*;

    public class TWorldLoad extends TFarmTransaction
    {
        protected String m_userId ;
        protected GenericDialog m_popup ;
        private Object m_mission ;
        private double m_panToObjectId ;

        public  TWorldLoad (String param1 ,double param2 =-1)
        {
            this.m_userId = param1;
            this.m_panToObjectId = param2;
            Global.isTransitioningWorld = true;
            Global.playerPopulation = Global.world.citySim.getPopulation();
            this.m_popup = UI.displayMessage(ZLoc.t("Main", "LoadingFarm"), GenericDialogView.TYPE_NOBUTTONS, null, "", true);
            if (SocialInventoryManager.isFeatureAvailable())
            {
                Global.world.addEventListener(FarmGameWorldEvent.BEGIN_VISIT, SocialInventoryManager.onNeighborVisit, false, 0, true);
            }
            return;
        }//end  

         public void  perform ()
        {
            signedCall("WorldService.loadWorld", this.m_userId.toString());
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            WorldObject _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            Global.player.mutualFriendsDialog = null;
            Sounds.stopAnyLoopingSounds();
            param1.world.ownerId = this.m_userId.toString();
            
            
            Global.world.mostFrequentHelpers = param1.world.mostFrequentHelpers;
            Global.world.vistorCenterText =(String) param1.world.visitorCenterMessage;
            Global.world.loadObject(param1.world);
            if (param1.crews != null)
            {
                Global.crews = new CrewMap();
                Global.crews.loadObject(param1.crews);
            }
            if (param1.featureData && param1.featureData.rollCall)
            {
                Global.rollCallManager.loadObject(param1.featureData.rollCall);
            }
            Global.rollCallManager.init();
            String _loc_2 =null ;
            MiniGame.end();
            Global.world.visitorOrderMgr.cleanUp();
            if (param1.ugc)
            {
                Global.world.friendUGCManager.loadUGCMap(param1.ugc);
            }
            if (Global.player.snUser.uid != this.m_userId)
            {
                _loc_2 = Global.player.getFriendName(this.m_userId);
                if (_loc_2 == null)
                {
                    _loc_2 = ZLoc.t("Main", "Friend");
                }
                if (Global.player.isEligibleForBusinessUpgrades() && !Global.player.getSeenFlag("biz_ups_visit_toaster"))
                {
                    Global.player.setSeenFlag("biz_ups_visit_toaster");
                    Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBusinessUpgradesVisit"), true);
                }
                Global.world.visitorOrderMgr.initializeOrders(param1.orders);
            }
            else
            {
                _loc_2 = Global.player.snUser.firstName;
                Global.ui.m_cityNamePanel.cityName = Global.player.cityName;
                Global.hud.townName = Global.player.cityName;
            }
            if (!Global.isVisiting())
            {
                Global.hud.townName = Global.player.cityName;
                Global.player.commodities.updateCapacities();
                Global.hud.updateCommodities();
                PreyManager.spawnExistingHunters(true);
                PreyManager.resetAllPreySpawned();
                if (SocialInventoryManager.isFeatureAvailable())
                {
                    if (param1.featureData.get("socialInventory"))
                    {
                        SocialInventoryManager.setData(param1.featureData.get("socialInventory"));
                    }
                }
            }
            Global.world.citySim.resortManager.setupFriendSlidePicks();
            Global.hud.conditionallyRefreshHUD(true);
            Global.world.centerCityView(0);
            this.m_popup.close();
            Global.isTransitioningWorld = false;
            if (Global.isVisiting())
            {
                Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.BEGIN_VISIT));
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "visit_start", MatchmakingManager.instance.getNeighborStatsId(this.m_userId), this.m_userId);
            }
            Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.WORLD_LOADED));
            if (this.m_panToObjectId != -1)
            {
                _loc_3 = Global.world.getObjectById(this.m_panToObjectId);
                if (_loc_3 != null)
                {
                    if (_loc_3 is GameObject)
                    {
                        Global.world.centerOnObjectWithCallback(_loc_3, 1, ((GameObject)_loc_3).onPanned);
                    }
                    else
                    {
                        Global.world.centerOnObject(_loc_3);
                    }
                }
            }
            if (Global.isVisiting())
            {
                _loc_4 = Global.world.ownerId;
                _loc_5 = Global.player.getFriendFirstName(_loc_4);
                Global.ui.m_cityNamePanel.cityName = Global.player.getFriendCityName(_loc_4);
            }
            GameTransactionManager.addTransaction(new TUpdateEnergy());
            if (!Global.isVisiting())
            {
                Global.ui.m_friendBar.updateNeighbors(Global.friendbar);
            }
            return;
        }//end  

         protected void  onFault (int param1 ,String param2 )
        {
            this.m_popup.close();
            UI.displayMessage(ZLoc.t("Main", "UnableToLoadFarm"));
            return;
        }//end  

    }



