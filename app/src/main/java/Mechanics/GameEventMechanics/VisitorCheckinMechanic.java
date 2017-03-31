package Mechanics.GameEventMechanics;

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
import Classes.orders.*;
import Classes.orders.Hotel.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.quest.Helpers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;

    public class VisitorCheckinMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {
        protected String m_gameEvent ="all";
        protected ICheckInHandler m_checkInHandler =null ;
        protected int m_ugcID =-1;

        public  VisitorCheckinMechanic ()
        {
            return;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            this.m_checkInHandler =(ICheckInHandler) m_owner;
            return;
        }//end

         public String  getPick ()
        {
            if (this.m_checkInHandler.isClosed())
            {
                return "";
            }
            if (this.m_checkInHandler.isFriendFull())
            {
                return "full";
            }
            if (this.m_checkInHandler.isVIPRequested())
            {
                return "requestvip";
            }
            if (this.m_checkInHandler.isVIP())
            {
                return "vip";
            }
            if (this.m_checkInHandler.isCheckedIn())
            {
                return "checkedin";
            }
            if (this.m_checkInHandler.isOpenForFriend())
            {
                return "checkin";
            }
            return "";
        }//end

         public Array  getPicksToHide ()
        {
            if (this.m_checkInHandler.isClosed())
            {
                return new Array("full", "requestvip", "vip", "checkedin", "checkin");
            }
            if (this.m_checkInHandler.isFriendFull())
            {
                return new Array("requestvip", "vip", "checkedin", "checkin");
            }
            if (this.m_checkInHandler.isVIPRequested())
            {
                return new Array("full", "vip", "checkedin", "checkin");
            }
            if (this.m_checkInHandler.isVIP())
            {
                return new Array("full", "requestvip", "checkedin", "checkin");
            }
            if (this.m_checkInHandler.isCheckedIn())
            {
                return new Array("full", "requestvip", "vip", "checkin");
            }
            if (this.m_checkInHandler.isOpenForFriend())
            {
                return new Array("full", "requestvip", "vip", "checkedin");
            }
            return null;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_checkInHandler.isClosed() || this.m_checkInHandler.isFriendFull() || this.m_checkInHandler.isVIPRequested() || this.m_checkInHandler.isVIP() || this.m_checkInHandler.isCheckedIn() || this.m_checkInHandler.isOpenForFriend())
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =null ;
            if (this.m_checkInHandler.isClosed())
            {
                _loc_3 = this.handleClosed(param1, param2);
            }
            else if (this.m_checkInHandler.isFriendFull())
            {
                _loc_3 = this.handleFull(param1, param2);
            }
            else if (this.m_checkInHandler.isVIPRequested())
            {
                _loc_3 = this.handleVIPRequest(param1, param2);
            }
            else if (this.m_checkInHandler.isVIP())
            {
                _loc_3 = this.handleVIP(param1, param2);
            }
            else if (this.m_checkInHandler.isCheckedIn())
            {
                _loc_3 = this.handleCheckedIn(param1, param2);
            }
            else if (this.m_checkInHandler.isOpenForFriend())
            {
                _loc_3 = this.handleOpen(param1, param2);
            }
            return _loc_3;
        }//end

        protected MechanicActionResult  handleClosed (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 ={operation "hotelVisitClosed"};
            m_owner.displayStatus(this.m_checkInHandler.closedFlyoutText);
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        protected MechanicActionResult  handleFull (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 ={operation "hotelVisitFull"};
            m_owner.displayStatus(this.m_checkInHandler.fullFlyoutText);
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        protected MechanicActionResult  handleVIPRequest (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 ={operation "hotelVisitRequestVIP"};
            this.displayDialog();
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        protected MechanicActionResult  handleVIP (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 ={operation "hotelVisitVIP"};
            this.displayDialog();
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        protected MechanicActionResult  handleCheckedIn (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 ={operation "hotelVisitCheckedIn"};
            this.displayDialog();
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        protected MechanicActionResult  handleOpen (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            this.m_gameEvent = param1;
            _loc_5 = ZLoc.t("Dialogs","hotelCheckIn_message",{item m_owner.getCustomName (),username.player.getFriendFirstName(Global.world.ownerId )});
            _loc_6 = ZLoc.t("Dialogs","hotelCheckIn_inputLabel");
            _loc_7 =Global.gameSettings().getInt("maxCheckInMessageLength",140);
            InputTextDialog _loc_8 =new InputTextDialog(_loc_5 ,"hotelCheckIn",_loc_6 ,"",_loc_7 ,GenericDialogView.TYPE_SAVESHARESAVEREDGREEN ,this.onNameDialogComplete ,true ,this.onNameDialogComplete );
            UI.displayPopup(_loc_8, false);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "view", m_owner.getItemName(), "checkin_dialog", "leave_message");
            return new MechanicActionResult(false, true, false, null);
        }//end

        protected void  onNameDialogComplete (GenericPopupEvent event )
        {
            TextField _loc_2 =null ;
            String _loc_3 =null ;
            if (event.button == GenericDialogView.YES || event.button == GenericDialogView.SKIP || event.button == GenericDialogView.CANCEL)
            {
                _loc_2 =(TextField) event.target.textField;
                _loc_3 = "";
                if (_loc_2)
                {
                    _loc_3 = _loc_2.text;
                }
                if (_loc_3 != "")
                {
                    if (event.button == GenericDialogView.SKIP)
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", m_owner.getItemName(), "checkin_dialog", "leave_message");
                    }
                    else if (event.button == GenericDialogView.CANCEL)
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", m_owner.getItemName(), "checkin_dialog", "click_x");
                        _loc_3 = "";
                    }
                    else
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", m_owner.getItemName(), "checkin_dialog", "cc_message");
                    }
                    if (_loc_3 != "")
                    {
                        m_owner.trackSocialAction(StatsCounterType.SOCIAL_VERB_VISIT_NEIGHBOR, m_owner.getItemName(), TrackedActionType.LEAVE_MESSAGE);
                    }
                }
                else
                {
                    StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", m_owner.getItemName(), "checkin_dialog", "no_message");
                }
                if (_loc_3 != "")
                {
                    this.m_ugcID = Global.world.friendUGCManager.createUGC(Global.player.uid, Global.getVisiting(), _loc_3);
                }
                TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, this.m_gameEvent, {operation:"hotelVisitOpen", ownerId:Global.getVisiting(), ugcID:this.m_ugcID, ugcMessage:_loc_3}));
                m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, m_config.type, true, this.m_gameEvent));
                this.finishGuestCheckIn();
            }
            if (event.button == GenericDialogView.YES && _loc_3 != "")
            {
                Global.world.viralMgr.sendHotelGuestCheckInFeed(_loc_3);
            }
            return;
        }//end

        protected void  finishGuestCheckIn ()
        {
            m_owner.trackSocialAction(StatsCounterType.SOCIAL_VERB_VISIT_NEIGHBOR, m_owner.getItemName(), TrackedActionType.HOTEL_CHECKIN, "from_game");
            _loc_1 =Global.gameSettings().getInt("friendVisitBusinessRepGain",1);
            _loc_2 =Global.gameSettings().getNumber("friendHelpDefaultCoinReward",10);
            m_owner.finalizeAndAwardVisitorHelp(VisitorHelpType.HOTEL_CHECKIN, _loc_1, _loc_2);
            HotelOrder _loc_3 =new HotelOrder(Global.getVisiting (),Global.player.uid );
            _loc_3.hotelID = m_owner.getId();
            _loc_3.setState(OrderStates.ACCEPTED);
            _loc_3.setTransmissionStatus(OrderStatus.RECEIVED);
            Date _loc_4 =new Date ();
            _loc_5 = Math.round(_loc_4.getTime ()/1000);
            _loc_6 = (String)Global.world.friendUGCManager.getUGCObject(this.m_ugcID );
            _loc_3.overrideParams(m_owner.getId(), 1, _loc_5, 0, -1, _loc_6);
            Global.world.visitorOrderMgr.placeOrder(_loc_3);
            Global.world.citySim.resortManager.setupFriendSlidePick(_loc_3, true);
            m_owner.displayStatus(ZLoc.t("Main", "HotelCheckIn"));
            this.m_checkInHandler.updatePeepCount(m_owner.getItem().hotel.getFloor(this.m_checkInHandler.defaultCheckInFloor).guestUpgradeBonus);
            this.displayDialog();
            return;
        }//end

        protected void  displayDialog ()
        {
            _loc_1 = getDefinitionByName(m_config.params.get( "dialogToPop") )as Class ;
            _loc_2 = new _loc_1(m_owner ,this.upgradeProcess );
            UI.displayPopup(_loc_2, true, "VisitorDialog", true);
            return;
        }//end

        protected boolean  doGuestUpgrade (MechanicMapResource param1 ,String param2 ,String param3 ,int param4 ,int param5 )
        {
            _loc_6 = param1as ICheckInHandler ;
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", m_owner.getItemName(), "visitor_panel_ui", "upgrade_" + param4);
            _loc_7 = _loc_6.getVisitorHotelOrder(param2,param3);
            _loc_8 = param1.getItem ().hotel.getFloor(_loc_7.guestData.floor ).guestUpgradeBonus ;
            _loc_9 = param1.getItem ().hotel.getFloor(param4 ).guestUpgradeBonus ;
            _loc_9 = param1.getItem().hotel.getFloor(param4).guestUpgradeBonus - _loc_8;
            if (_loc_7 == null)
            {
                return false;
            }
            _loc_7.guestData.floor = param4;
            _loc_7.guestData.gotGift = param5;
            this.guestVisitorUpgrade(param1, param4, param5);
            _loc_6.updatePeepCount(_loc_9);
            return true;
        }//end

        public void  guestVisitorUpgrade (MechanicMapResource param1 ,int param2 ,int param3 )
        {
            int _loc_7 =0;
            int _loc_8 =0;
            _loc_4 = param1as ICheckInHandler ;
            boolean _loc_5 =true ;
            _loc_6 = _loc_4.getGuestVIPStatus(Global.getVisiting (),Global.player.uid );
            if (param2 == 0 && _loc_6 == HotelGuest.VIP_ACCEPTED)
            {
                _loc_5 = false;
            }
            if (_loc_5)
            {
                _loc_7 = param1.getItem().hotel.getFloor(param2).upgradeCost;
                Global.player.gold = Global.player.gold - _loc_7;
                _loc_8 = param1.getItem().hotel.getFloor(param2).upgradeCash;
                Global.player.cash = Global.player.cash - _loc_8;
            }
            TransactionManager.addTransaction(new TMechanicAction(param1, "hotelVisitClosed", "GMVisit", {operation:"upgradeGuest", ownerId:Global.getVisiting(), hotelId:param1.getId(), floorId:param2, giftIndex:param3}));
            param1.trackSocialAction(StatsCounterType.SOCIAL_VERB_VISIT_NEIGHBOR, param1.getItemName(), TrackedActionType.HOTEL_UPGRADE, "", param2);
            return;
        }//end

        public boolean  upgradeProcess (MechanicMapResource param1 ,String param2 ,String param3 ,int param4 ,int param5 =0)
        {
            _loc_6 = param1as ICheckInHandler ;
            _loc_7 = param1(as ICheckInHandler ).hasVisitorCheckedIntoHotel(param2 ,param3 );
            if (((ICheckInHandler)param1).hasVisitorCheckedIntoHotel(param2, param3))
            {
                return this.doGuestUpgrade(param1, param2, param3, param4, param5);
            }
            return false;
        }//end

    }



