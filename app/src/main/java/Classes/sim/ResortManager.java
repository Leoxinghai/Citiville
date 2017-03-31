package Classes.sim;

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
import Classes.effects.*;
import Classes.orders.*;
import Classes.orders.Hotel.*;
import Classes.util.*;
import Display.FactoryUI.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
import com.zynga.skelly.util.*;
//import flash.events.*;
//import flash.utils.*;

    public class ResortManager implements IGameWorldStateObserver
    {
        protected Dictionary m_resorts ;
        protected NPC m_focusNPC =null ;
        protected Array m_npcGuestData ;
        protected Function m_tutorialTimerHandler ;
        public static  int MAX_CHECKINS_PER_HOTEL =50;
        public static  String NPC_CHECK_IN_TUTORIAL ="npc_check_in_tutorial";
        public static  String NPC_CANNOT_CHECK_IN_TUTORIAL ="npc_cannot_check_in_tutorial";
        public static  String NPC_CONSUMER_TUTORIAL ="npc_consumer_tutorial";
public static  int GUEST_NPC_MAX =5;
        private static Array m_hotelOrders ;
        private static boolean m_hotelDialogVisible ;

        public  ResortManager (GameWorld param1 )
        {
            this.m_npcGuestData = new Array();
            param1.addObserver(this);
            m_hotelDialogVisible = false;
            return;
        }//end

        public NPC  getFocusNPC ()
        {
            return this.m_focusNPC;
        }//end

        public void  clearFocusNPC ()
        {
            this.m_focusNPC = null;
            return;
        }//end

        public void  hotelDialogActive ()
        {
            m_hotelDialogVisible = true;
            return;
        }//end

        public void  hotelDialogClosed (GenericPopupEvent event )
        {
            m_hotelDialogVisible = false;
            return;
        }//end

        public double  getNumberOfHotels ()
        {
            _loc_1 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL) );
            return _loc_1.length;
        }//end

        public double  getNumberOfBusinessesForHotel (double param1 )
        {
            Hotel _loc_4 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL) );
            double _loc_3 =0;
            if (this.m_resorts == null)
            {
                this.parseResorts();
            }
            if (param1 < _loc_2.length())
            {
                _loc_4 = _loc_2.get(param1);
                if (this.m_resorts.get(_loc_4.getId()) != null)
                {
                    _loc_3 = ((Array)this.m_resorts.get(_loc_4.getId())).length;
                }
            }
            return _loc_3;
        }//end

        public Array  getBusinessesForHotel (int param1 )
        {
            return this.m_resorts.get(param1);
        }//end

        public boolean  checkNPCCheckInTutorial (NPC param1 )
        {
            boolean _loc_2 =false ;
            if (!Global.player.getSeenFlag(NPC_CHECK_IN_TUTORIAL) && this.m_focusNPC == null && !m_hotelDialogVisible)
            {
                this.m_focusNPC = param1;
                this.m_focusNPC.addAnimatedEffect(EffectType.FOCUS_GLOW);
                Global.guide.notify("NPCCheckInGuide");
                Global.player.setSeenFlag(NPC_CHECK_IN_TUTORIAL);
                this.startTutorialWave(param1);
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  checkNPCCannotCheckInTutorial (NPC param1 )
        {
            boolean _loc_2 =false ;
            if (!Global.player.getSeenFlag(NPC_CANNOT_CHECK_IN_TUTORIAL) && this.m_focusNPC == null && !m_hotelDialogVisible)
            {
                this.m_focusNPC = param1;
                this.m_focusNPC.addAnimatedEffect(EffectType.FOCUS_GLOW);
                Global.guide.notify("NPCCannotCheckInGuide");
                Global.player.setSeenFlag(NPC_CANNOT_CHECK_IN_TUTORIAL);
                this.startTutorialWave(param1);
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  checkNPCConsumerTutorial (NPC param1 )
        {
            boolean _loc_2 =false ;
            if (!Global.player.getSeenFlag(NPC_CONSUMER_TUTORIAL) && this.m_focusNPC == null && !m_hotelDialogVisible)
            {
                this.m_focusNPC = param1;
                this.m_focusNPC.addAnimatedEffect(EffectType.FOCUS_GLOW);
                Global.guide.notify("NPCConsumerGuide");
                Global.player.setSeenFlag(NPC_CONSUMER_TUTORIAL);
                this.startTutorialWave(param1);
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        protected void  startTutorialWave (NPC param1 )
        {
            param1.pauseAndWave();
            Timer _loc_2 =new Timer(100);
            this.m_tutorialTimerHandler = Curry.curry(this.onTutorialTimer, param1);
            _loc_2.addEventListener(TimerEvent.TIMER, this.m_tutorialTimerHandler);
            _loc_2.start();
            return;
        }//end

        protected void  onTutorialTimer (NPC param1 ,TimerEvent param2 )
        {
            if (!Global.guide.isActive())
            {
                param1.unpause();
                if (param2.target instanceof Timer)
                {
                    ((Timer)param2.target).stop();
                    ((Timer)param2.target).removeEventListener(TimerEvent.TIMER, this.m_tutorialTimerHandler);
                }
            }
            return;
        }//end

        public void  parseResorts ()
        {
            MapResource _loc_4 =null ;
            if (this.m_resorts == null)
            {
                this.m_resorts = new Dictionary();
            }
            _loc_1 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.BUSINESS) );
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.SOCIAL_BUSINESS) );
            _loc_1 = _loc_1.concat(_loc_2);
            Hotel _loc_3 =null ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_4 = _loc_1.get(i0);

                _loc_3 = this.getClosestHotelInResortRange(_loc_4);
                if (_loc_3 != null)
                {
                    if (this.m_resorts.get(_loc_3.getId()) == null)
                    {
                        this.m_resorts.put(_loc_3.getId(),  new Array());
                    }
                    if (((Array)this.m_resorts.get(_loc_3.getId())).indexOf(_loc_4.getId()) == -1)
                    {
                        ((Array)this.m_resorts.get(_loc_3.getId())).push(_loc_4.getId());
                    }
                }
            }
            return;
        }//end

        public void  resetAndParse ()
        {
            this.m_resorts = null;
            this.parseResorts();
            return;
        }//end

        public void  updateAllBusinesses ()
        {
            MapResource _loc_3 =null ;
            _loc_1 =Global.world.getObjectsByClass(Business );
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.SOCIAL_BUSINESS) );
            _loc_1 = _loc_1.concat(_loc_2);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                this.updateBusiness(_loc_3);
            }
            return;
        }//end

        public void  removeBusiness (MapResource param1 )
        {
            Array _loc_2 =null ;
            double _loc_3 =0;
            Hotel _loc_4 =null ;
            double _loc_5 =0;
            if (this.m_resorts != null)
            {
                _loc_2 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL));
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    _loc_4 = _loc_2.get(_loc_3);
                    if (this.m_resorts.get(_loc_4.getId()) != null)
                    {
                        _loc_5 = ((Array)this.m_resorts.get(_loc_4.getId())).indexOf(param1.getId());
                        if (_loc_5 != -1)
                        {
                            ((Array)this.m_resorts.get(_loc_4.getId())).splice(_loc_5, 1);
                        }
                    }
                    _loc_3 = _loc_3 + 1;
                }
            }
            return;
        }//end

        public void  updateBusiness (MapResource param1 )
        {
            Hotel _loc_2 =null ;
            if (this.m_resorts == null)
            {
                this.parseResorts();
            }
            else
            {
                this.removeBusiness(param1);
                _loc_2 = this.getClosestHotelInResortRange(param1);
                if (_loc_2 != null)
                {
                    if (this.m_resorts.get(_loc_2.getId()) == null)
                    {
                        this.m_resorts.put(_loc_2.getId(),  new Array());
                    }
                    if (((Array)this.m_resorts.get(_loc_2.getId())).indexOf(param1.getId()) == -1)
                    {
                        ((Array)this.m_resorts.get(_loc_2.getId())).push(param1.getId());
                    }
                }
            }
            return;
        }//end

        public double  getHotelForResourceId (double param1 )
        {
            Array _loc_3 =null ;
            String _loc_4 =null ;
            Hotel _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_resorts.size(); i0++)
            {
            		_loc_4 = this.m_resorts.get(i0);

                _loc_3 = this.m_resorts.get(int(_loc_4));
                if (_loc_3.indexOf(param1) != -1)
                {
                    return int(_loc_4);
                }
            }
            return 0;
        }//end

        public boolean  businessInResort (MapResource param1 )
        {
            Array _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_2 =false ;
            for(int i0 = 0; i0 < this.m_resorts.size(); i0++)
            {
            	_loc_4 = this.m_resorts.get(i0);

                _loc_3 = this.m_resorts.get(int(_loc_4));
                if (_loc_3.indexOf(param1.getId()) != -1)
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  businessInResortById (double param1 )
        {
            Array _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_2 =false ;
            if (this.m_resorts == null)
            {
                this.parseResorts();
            }
            for(int i0 = 0; i0 < this.m_resorts.size(); i0++)
            {
            	_loc_4 = this.m_resorts.get(i0);

                _loc_3 = this.m_resorts.get(int(_loc_4));
                if (_loc_3.indexOf(param1) != -1)
                {
                    return true;
                }
            }
            return false;
        }//end

        private Hotel  getClosestHotel (MapResource param1 )
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Hotel _loc_6 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL) );
            Hotel _loc_3 =null ;
            if (_loc_2.length > 0)
            {
                _loc_3 = _loc_2.get(0);
                _loc_4 = 1000;
                _loc_5 = 0;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_6 = _loc_2.get(i0);

                    _loc_5 = param1.centerPosition.subtract(_loc_6.centerPosition).length();
                    if (_loc_5 < _loc_4)
                    {
                        _loc_4 = _loc_5;
                        _loc_3 = _loc_6;
                    }
                }
            }
            return _loc_3;
        }//end

        private Hotel  getClosestHotelInResortRange (MapResource param1 )
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Hotel _loc_6 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL) );
            Hotel _loc_3 =null ;
            if (_loc_2.length > 0)
            {
                _loc_4 = 1000;
                _loc_5 = 0;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_6 = _loc_2.get(i0);

                    _loc_5 = param1.centerPosition.subtract(_loc_6.centerPosition).length();
                    if (_loc_5 < _loc_4 && _loc_5 <= _loc_6.resortRadius)
                    {
                        _loc_4 = _loc_5;
                        _loc_3 = _loc_6;
                    }
                }
            }
            return _loc_3;
        }//end

        public void  initialize ()
        {
            this.clearGuestNPCs();
            this.setupFriendSlidePicks();
            return;
        }//end

        public void  setupFriendSlidePicks ()
        {
            HotelOrder _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            Object _loc_5 =null ;
            _loc_1 = hotelVisitorOrders;
            this.m_npcGuestData = new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                this.setupFriendSlidePick(_loc_2);
            }
            _loc_3 = Global.gameSettings().getInt("maxHotelGuestNPC", GUEST_NPC_MAX);
            this.m_npcGuestData.sortOn("timestamp", Array.DESCENDING);
            _loc_4 = 0;
            while (_loc_4 < this.m_npcGuestData.length && this.m_npcGuestData.length > _loc_3)
            {

                if (!this.m_npcGuestData.get(_loc_4).get(FriendNPCSlidePick.HAS_UGC_MESSAGE) || (_loc_4 + 1) > _loc_3)
                {
                    this.m_npcGuestData.splice(_loc_4, 1);
                    _loc_4 = _loc_4 - 1;
                }
                _loc_4++;
            }
            if (Global.world.citySim.friendNPCManager)
            {
                for(int i0 = 0; i0 < this.m_npcGuestData.size(); i0++)
                {
                		_loc_5 = this.m_npcGuestData.get(i0);

                    Global.world.citySim.friendNPCManager.registerFriendData(_loc_5.payload, _loc_5.source);
                }
            }
            return;
        }//end

        public void  setupFriendSlidePick (HotelOrder param1 ,boolean param2 =false )
        {
            Array _loc_12 =null ;
            ICheckInHandler _loc_13 =null ;
            Object _loc_3 =new Object ();
            _loc_4 = param1.guestData ;
            _loc_5 = param1.guestData.timestamp ;
            Date _loc_6 =new Date ();
            _loc_7 = Math.round(_loc_6.getTime ()/1000);
            _loc_8 = Math.round(_loc_6.getTime ()/1000)-_loc_5 ;
            _loc_9 = (MapResource)Global.world.getObjectById(_loc_4.hotelID ) ;
            if (!((MapResource)Global.world.getObjectById(_loc_4.hotelID)))
            {
                _loc_12 = Global.world.getObjectsByClass(Hotel);
                if (_loc_12.length())
                {
                    _loc_9 =(Hotel) _loc_12.get(int(Math.random() * _loc_12.length()));
                }
            }
            if (!_loc_9)
            {
                return;
            }
            _loc_3.put(FriendNPCSlidePick.FRIEND_ID,  param1.getSenderID());
            _loc_3.put(FriendNPCSlidePick.CHECK_IN_TIMESTAMP,  _loc_8);
            if (_loc_4.message == null || _loc_4.message == "")
            {
                _loc_13 =(ICheckInHandler) _loc_9;
                _loc_3.put(FriendNPCSlidePick.UGC_MESSAGE,  _loc_13.getDefaultUGCCheckinMessage(_loc_4.floor));
                _loc_3.put(FriendNPCSlidePick.HAS_UGC_MESSAGE,  false);
            }
            else
            {
                _loc_3.put(FriendNPCSlidePick.UGC_MESSAGE,  _loc_4.message);
                _loc_3.put(FriendNPCSlidePick.HAS_UGC_MESSAGE,  true);
            }
            _loc_3.put(FriendNPCSlidePick.UGC_MESSAGE_REPORTED,  _loc_4.reported);
            _loc_3.put("hotelId",  _loc_4.hotelID);
            _loc_10 = DateUtil.calculateTimeDifference(_loc_8*1000);
            _loc_11 = getCheckInTime(_loc_10);
            _loc_3.put(FriendNPCSlidePick.CHECK_IN_TIME,  _loc_11);
            _loc_3.thankYouCallback = this.sendThankYouFeed;
            this.m_npcGuestData.push({payload:_loc_3, source:_loc_9, timestamp:_loc_4.timestamp});
            if (param2)
            {
                Global.world.citySim.friendNPCManager.registerFriendData(_loc_3, _loc_9);
            }
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  clearGuestNPCs ()
        {
            Object _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_npcGuestData.size(); i0++)
            {
            		_loc_1 = this.m_npcGuestData.get(i0);

                Global.world.citySim.friendNPCManager.removeFriendNPC(_loc_1.source);
            }
            this.m_npcGuestData = new Array();
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            this.parseResorts();
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  sendThankYouFeed (Object param1 )
        {
            int _loc_3 =0;
            MapResource _loc_4 =null ;
            String _loc_2 ="hotel";
            if (param1.friendData && param1.friendData.hotelId)
            {
                _loc_3 = int(param1.friendData.hotelId);
                _loc_4 =(MapResource) Global.world.getObjectById(_loc_3);
                if (_loc_4)
                {
                    _loc_2 = _loc_4.getItemName();
                }
            }
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_2, "NPC_dialog", "thank_guest");
            Global.world.viralMgr.sendHotelThankYouFeed(param1.guestId);
            StatsManager.social(StatsCounterType.SOCIAL_VERB_GIVE_THANKS, param1.guestId, _loc_2, TrackedActionType.SEND_THANKS, "", "", 1);
            return;
        }//end

        public static Array  hotelVisitorOrders ()
        {
            Array _loc_1 =new Array();
            if (Global.isVisiting())
            {
                _loc_1 = Global.world.visitorOrderMgr.getOrders(OrderType.HOTEL, OrderStatus.RECEIVED, OrderStates.ACCEPTED);
            }
            else
            {
                _loc_1 = Global.world.orderMgr.getOrders(OrderType.HOTEL, OrderStatus.RECEIVED, OrderStates.ACCEPTED);
            }
            return _loc_1;
        }//end

        public static String  getCheckInTime (Object param1 )
        {
            String _loc_2 =null ;
            if (Number(param1.days) + Number(param1.hours) / 24 > 3)
            {
                return ZLoc.t("Dialogs", "TimeInfo_timePastOver", {timeType:ZLoc.t("Dialogs", "Days"), timeValue:3});
            }
            if (param1.days == 0 && param1.hours == 0)
            {
                return ZLoc.t("Dialogs", "TimeInfo_timePastUnder", {timeType:ZLoc.t("Dialogs", "Hour"), timeValue:1});
            }
            _loc_2 = "";
            if (param1.days > 0)
            {
                _loc_2 = _loc_2 + ("" + param1.days + " ");
                if (param1.days == 1)
                {
                    _loc_2 = _loc_2 + ZLoc.t("Dialogs", "Day");
                }
                else
                {
                    _loc_2 = _loc_2 + ZLoc.t("Dialogs", "Days");
                }
            }
            if (param1.hours > 0)
            {
                if (param1.days > 0)
                {
                    _loc_2 = _loc_2 + ", ";
                }
                _loc_2 = _loc_2 + ("" + param1.hours + " ");
                if (param1.hours == 1)
                {
                    _loc_2 = _loc_2 + ZLoc.t("Dialogs", "Hour");
                }
                else
                {
                    _loc_2 = _loc_2 + ZLoc.t("Dialogs", "Hours");
                }
            }
            _loc_2 = _loc_2 + (" " + ZLoc.t("Dialogs", "TimeInfo_timeCustomAgo"));
            return _loc_2;
        }//end

    }



