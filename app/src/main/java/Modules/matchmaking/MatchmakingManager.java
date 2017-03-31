package Modules.matchmaking;

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
import Classes.gates.*;
import Classes.js.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.Toaster.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.errors.*;
//import flash.events.*;

    public class MatchmakingManager
    {
        private Array m_buildingBuddies ;
        private Array m_newBuddies ;
        private MatchmakingConfig m_config ;
        private ThrottlingHelper m_sendTS ;
        private ThrottlingHelper m_giftTS ;
        private JavaScript m_javaScript ;
        private IJavaScriptCallback m_addFriendCallback ;
        private IJavaScriptCallback m_getFriendIdCallback ;
        private String m_friendId ;
        private DisplayObject m_crewIcon ;
        private boolean m_neighborsLoaded ;
        public static  String REQUEST_CREW ="crew";
        public static  String REQUEST_ITEM ="buildable";
        private static  int FREEZE_KEY =3;
        private static MatchmakingManager s_instance ;

        public  MatchmakingManager ()
        {
            this.m_buildingBuddies = new Array();
            this.m_newBuddies = new Array();
            if (s_instance)
            {
                throw new IllegalOperationError("Cannot instantiate a new instance of singleton class");
            }
            this.m_sendTS = new ThrottlingHelper(this.config.sendThrottle, {});
            this.m_giftTS = new ThrottlingHelper(this.config.giftThrottle, {});
            this.m_javaScript = new JavaScript();
            this.m_addFriendCallback = this.m_javaScript.callback("onFBAddFriendDialogClosed", this.onFBAddFriendDialogClosed);
            this.m_getFriendIdCallback = this.m_javaScript.callback("onGetFriendId", this.onGetFriendId);
            Global.delayedAssets.get(DelayedAssetLoader.MATCHMAKING_ASSETS, this.assetsLoaded);
            return;
        }//end

        private void  assetsLoaded (Object param1 ,String param2 )
        {
            _loc_3 =(Class) param1.buildingBuddy_cellPic;
            this.m_crewIcon = new _loc_3;
            if (this.m_neighborsLoaded)
            {
                this.popNewBuildingBuddies(this.m_newBuddies);
            }
            return;
        }//end

        private MatchmakingConfig  config ()
        {
            if (!this.m_config)
            {
                this.m_config = Global.gameSettings().getMatchmakingConfig();
            }
            return this.m_config;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            if ("buildingBuddies" in param1)
            {
                for(int i0 = 0; i0 < param1.get("buildingBuddies").size(); i0++)
                {
                		_loc_2 = param1.get("buildingBuddies").get(i0);

                    this.m_buildingBuddies.push(_loc_2.substr(1));
                    if (param1.get("buildingBuddies").get(_loc_2) == "new")
                    {
                        this.m_newBuddies.push(_loc_2.substr(1));
                    }
                }
            }
            this.m_sendTS = new ThrottlingHelper(this.config.sendThrottle, param1.sendTS);
            this.m_giftTS = new ThrottlingHelper(this.config.giftThrottle, param1.giftTS);
            return;
        }//end

        public void  onNeighborsLoad (Event event )
        {
            this.m_neighborsLoaded = true;
            (event.target as IEventDispatcher).removeEventListener(Event.COMPLETE, this.onNeighborsLoad);
            if (this.m_crewIcon)
            {
                this.popNewBuildingBuddies(this.m_newBuddies);
            }
            return;
        }//end

        public void  popNewBuildingBuddies (Array param1 )
        {
            LocalizationName tnPlayer ;
            LocalizationObjectToken tkFriend ;
            String title ;
            String body ;
            newBuddies = param1;
            try
            {
                if (newBuddies.length > 1)
                {
                    tnPlayer = ZLoc.tn(Global.player.getFriendName(newBuddies.get(0)), Global.player.getFriendGender(newBuddies.get(0)));
                    tkFriend = ZLoc.tk("Gifts", "Friend", "", (newBuddies.length - 1));
                    title = ZLoc.t("Dialogs", "BuildingBuddyNewBuddyToaster_title");
                    body = ZLoc.t("Dialogs", "BuildingBuddyNewBuddyToaster_body_plural", {user:tnPlayer, num:(newBuddies.length - 1), friend:tkFriend});
                    Global.ui.toaster.show(new ItemToaster(title, body, this.m_crewIcon, null, -1));
                }
                else if (newBuddies.length > 0)
                {
                    tnPlayer = ZLoc.tn(Global.player.getFriendName(newBuddies.get(0)), Global.player.getFriendGender(newBuddies.get(0)));
                    title = ZLoc.t("Dialogs", "BuildingBuddyNewBuddyToaster_title");
                    body = ZLoc.t("Dialogs", "BuildingBuddyNewBuddyToaster_body_single", {user:tnPlayer});
                    Global.ui.toaster.show(new ItemToaster(title, body, this.m_crewIcon, null, -1));
                }
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        public void  addBuildingBuddy (String param1 )
        {
            this.m_buildingBuddies.push(param1);
            return;
        }//end

        public Array  getBuildingBuddies ()
        {
            return this.m_buildingBuddies ? (this.m_buildingBuddies.concat()) : ([]);
        }//end

        public Array  getNewBuddies ()
        {
            return this.m_newBuddies ? (this.m_newBuddies.concat()) : ([]);
        }//end

        public boolean  isBuildingBuddy (String param1 )
        {
            return this.m_buildingBuddies ? (this.m_buildingBuddies.indexOf(param1) > -1) : (false);
        }//end

        public Array  getGatedWorldObjects ()
        {
            _loc_1 =Global.world.getObjects ();
            return _loc_1.filter(this.isObjectBuildableOrCrewable, null);
        }//end

        private boolean  isObjectBuildableOrCrewable (MapResource param1 ,int param2 ,Array param3 )
        {
            ConstructionSite _loc_4 =null ;
            AbstractGate _loc_5 =null ;
            int _loc_6 =0;
            CompositeGate _loc_7 =null ;
            if (param1 instanceof ConstructionSite)
            {
                _loc_4 =(ConstructionSite) param1;
                _loc_5 =(AbstractGate) _loc_4.getGate(ConstructionSite.BUILD_GATE);
                _loc_6 = 0;
                if (_loc_5)
                {
                    if (_loc_5 instanceof CompositeGate)
                    {
                        _loc_7 =(CompositeGate) _loc_5;
                        _loc_5 =(AbstractGate) _loc_7.getGate(param1, "buildables");
                    }
                    if (_loc_5)
                    {
                        _loc_6 = _loc_5.amountNeeded;
                    }
                }
            }
            return _loc_6 > 0;
        }//end

        public boolean  sendFreeGiftEnabled (String param1 )
        {
            _loc_2 =Global.gameSettings().getString("matchmakingGift","mysterygift_v1");
            return Global.world.viralMgr.canSendRequest(RequestType.GIFT_REQUEST, [param1], {item:_loc_2});
        }//end

        public boolean  askForBuildableEnabled ()
        {
            return Global.world.viralMgr.canSendRequest(RequestType.ITEM_REQUEST, this.m_buildingBuddies, {item:"material_ribbon", viralType:"municipal_material_ribbon"});
        }//end

        public double  askForBuildableEnabledTime ()
        {
            return this.m_sendTS.throttleEndTime;
        }//end

        public boolean  askForCrewEnabled ()
        {
            return Global.world.viralMgr.canSendRequest(RequestType.ITEM_REQUEST, this.m_buildingBuddies, {item:"crew_bonus", viralType:"request_crew_bonus", message:"BuildingBuddiesCrew"});
        }//end

        public double  askForCrewEnabledTime ()
        {
            return this.m_sendTS.throttleEndTime;
        }//end

        public void  sendFreeGift (String param1 ,Function param2 )
        {
            String giftName ;
            uid = param1;
            callback = param2;
            giftName = Global.gameSettings().getString("matchmakingGift", "mysterygift_v1");
            Object ontology ;
            Global .world .viralMgr .sendRequest (RequestType .GIFT_REQUEST ,.get(uid) ,{giftName item },void  (boolean param1 ,Array param2 )
            {
                String _loc_3 =null ;
                if (param1 && param2 && param2.length > 0)
                {
                    _loc_3 = param2.get(int(Math.random() * (param2.length - 1)));
                    showFreeGiftToaster(_loc_3, giftName);
                }
                if (callback != null)
                {
                    callback(param1, param2);
                }
                return;
            }//end
            , ontology);
            return;
        }//end

        public void  askForBuildable (String param1 ,String param2 ,Function param3 =null ,String param4 ="gate",boolean param5 =true )
        {
            itemName = param1;
            viralType = param2;
            callback = param3;
            statSource = param4;
            showToaster = param5;
            viralType = viralType ? (viralType) : ("municipal_");
            Global .world .viralMgr .sendRequest (RequestType .ITEM_REQUEST ,this .m_buildingBuddies ,{itemName item ,viralType viralType +itemName },void  (boolean param1 ,Array param2 )
            {
                String _loc_3 =null ;
                if (showToaster && param1 && param2 && param2.length > 0)
                {
                    _loc_3 = param2.get(int(Math.random() * (param2.length - 1)));
                    showBuildableRequestToaster(itemName, (param2.length - 1), _loc_3);
                }
                if (callback != null)
                {
                    callback(param1, param2);
                }
                return;
            }//end
            );
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, StatsKingdomType.ASK_BUILDING_BUDDIES, StatsPhylumType.ASK_FOR_BUILDABLES, statSource);
            return;
        }//end

        private void  showBuildableRequestToaster (String param1 ,int param2 ,String param3 )
        {
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            _loc_5 = ZLoc.tn(Global.player.getFriendFirstName(param3 ),Global.player.getFriendGender(param3 ));
            _loc_6 = ZLoc.t("Dialogs","BuildingBuddyRequestItemToaster_title");
            _loc_7 = param2==0? (ZLoc.t("Dialogs", "BuildingBuddyRequestItemToaster_message_solo", {user:_loc_5, item:_loc_4.localizedName})) : (ZLoc.t("Dialogs", "BuildingBuddyRequestItemToaster_message", {user:_loc_5, item:_loc_4.localizedName, count:param2, buddies:ZLoc.tk("Dialogs", "buildingBuddies", "", param2)}));
            ItemToaster _loc_8 =new ItemToaster(_loc_6 ,_loc_7 ,_loc_4.getImageByName("icon"),null ,-1);
            Global.ui.toaster.show(_loc_8);
            return;
        }//end

        public void  askForCrew (boolean param1 =true ,Function param2 ,String param3 ="gate",Item param4 =null )
        {
            showToaster = param1;
            callback = param2;
            statSource = param3;
            targetItem = param4;
            Global .world .viralMgr .sendRequest (RequestType .ITEM_REQUEST ,this .m_buildingBuddies ,{"crew_bonus"item ,"request_crew_bonus"viralType ,"BuildingBuddiesCrew"message },void  (boolean param1 ,Array param2 )
            {
                String _loc_3 =null ;
                String _loc_4 =null ;
                if (showToaster && param1 && param2 && param2.length > 0)
                {
                    _loc_3 = param2.get(int(Math.random() * (param2.length - 1)));
                    _loc_4 = targetItem ? (targetItem.localizedName) : (null);
                    showCrewRequestToaster((param2.length - 1), _loc_3, _loc_4);
                }
                if (callback != null)
                {
                    callback(param1, param2);
                }
                return;
            }//end
            );
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, StatsKingdomType.ASK_BUILDING_BUDDIES, StatsPhylumType.ASK_FOR_CREW, statSource);
            return;
        }//end

        private void  showCrewRequestToaster (int param1 ,String param2 ,String param3 )
        {
            _loc_4 = ZLoc.tn(Global.player.getFriendFirstName(param2 ),Global.player.getFriendGender(param2 ));
            _loc_5 = ZLoc.t("Dialogs","BuildingBuddyRequestCrewToaster_title");
            _loc_6 = param1==0? (ZLoc.t("Dialogs", "BuildingBuddyRequestCrewToaster_message_solo", {user:_loc_4, itemName:param3})) : (ZLoc.t("Dialogs", "BuildingBuddyRequestCrewToaster_message", {user:_loc_4, count:param1, buddies:ZLoc.tk("Dialogs", "buildingBuddies", "", param1), itemName:param3}));
            ItemToaster _loc_7 =new ItemToaster(_loc_5 ,_loc_6 ,this.m_crewIcon ,null ,-1);
            Global.ui.toaster.show(_loc_7);
            return;
        }//end

        private void  showFreeGiftToaster (String param1 ,String param2 )
        {
            _loc_3 = ZLoc.tn(Global.player.getFriendFirstName(param1 ),Global.player.getFriendGender(param1 ));
            _loc_4 =Global.gameSettings().getItemByName("mysterygift_v1");
            _loc_5 = ZLoc.t("Dialogs","BuildingBuddyFreeGiftToaster_title");
            _loc_6 = ZLoc.t("Dialogs","BuildingBuddyFreeGiftToaster_message",{user _loc_3 ,itemName.localizedName });
            ItemToaster _loc_7 =new ItemToaster(_loc_5 ,_loc_6 ,_loc_4.icon ,null ,-1);
            Global.ui.toaster.show(_loc_7);
            return;
        }//end

        public void  addSentGift (String param1 )
        {
            this.m_giftTS.add("i" + param1);
            return;
        }//end

        public boolean  canSendGift (String param1 )
        {
            return !this.m_giftTS.isThrottled("i" + param1);
        }//end

        public void  addHelpRequest (String param1 )
        {
            this.m_sendTS.add("i" + param1);
            return;
        }//end

        public boolean  canSendHelpRequest (String param1 )
        {
            return !this.m_sendTS.isThrottled("i" + param1);
        }//end

        public String  getNeighborStatsId (String param1 )
        {
            return this.isBuildingBuddy(param1) ? ("matchmaking") : ("friend");
        }//end

        public void  addFriend (String param1 )
        {
            UI.freezeScreen(false, true, null, FREEZE_KEY);
            this.m_friendId = param1;
            this.m_javaScript.execute("getFacebookId", param1, Global.player.snUser.snid, this.m_getFriendIdCallback);
            return;
        }//end

        private void  onGetFriendId (Object param1 )
        {
            param1.get(_loc_2 = this.m_friendId) ;
            this.m_javaScript.execute("FB.ui", {method:"friends.add", id:_loc_2}, this.m_addFriendCallback);
            return;
        }//end

        private void  onFBAddFriendDialogClosed (Object param1 )
        {
            if (param1 && param1.action == true)
            {
                StatsManager.count(StatsCounterType.GAME_ACTIONS, StatsKingdomType.FRIEND_ADDED, this.m_friendId);
            }
            UI.thawScreen(FREEZE_KEY);
            return;
        }//end

        public static MatchmakingManager  instance ()
        {
            if (!s_instance)
            {
                s_instance = new MatchmakingManager;
            }
            return s_instance;
        }//end

    }



