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

import Classes.orders.*;
import Classes.orders.Hotel.*;
import Classes.sim.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.hotels.*;

    public class CheckinMechanicResource extends MechanicMapResource implements ICheckInHandler
    {

        public  CheckinMechanicResource (String param1 )
        {
            super(param1);
            return;
        }//end

        public void  harvestState (Object param1)
        {
            _loc_2 = mechanicData.get("harvestState") ;
            mechanicData.put("harvestState", param1);
            if (param1 == null && param1 != _loc_2)
            {
                this.emptyOfGuests();
            }
            return;
        }//end

        public Object harvestState ()
        {
            return mechanicData.get("harvestState");
        }//end

        public void  emptyOfGuests ()
        {
            Global.world.citySim.resortManager.clearGuestNPCs();
            this.clearPersonalGuestData();
            return;
        }//end

        protected void  clearPersonalGuestData ()
        {
            Array _loc_1 =null ;
            HotelOrder _loc_2 =null ;
            if (!Global.isVisiting())
            {
                _loc_1 = Global.world.orderMgr.getOrders(OrderType.HOTEL, OrderStatus.RECEIVED, OrderStates.ACCEPTED);
                if (_loc_1 && _loc_1.length())
                {
                    for(int i0 = 0; i0 < _loc_1.size(); i0++)
                    {
                    	_loc_2 = _loc_1.get(i0);

                        if (_loc_2 && _loc_2.hotelID == this.getId())
                        {
                            Global.world.orderMgr.removeOrder(_loc_2);
                        }
                    }
                }
            }
            return;
        }//end

        public boolean  isClosed ()
        {
            if (getState() == Hotel.STATE_CLOSED)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isNPCFull ()
        {
            if (getState() == Hotel.STATE_HARVESTABLE)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isFriendFull ()
        {
            if (!this.isRoomOnFloor(1) && !this.hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid))
            {
                return true;
            }
            if (this.isNPCFull())
            {
                return true;
            }
            return false;
        }//end

        public boolean  isVIPRequested ()
        {
            if (getState() == Hotel.STATE_OPEN && this.hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && this.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_REQUESTED)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isVIP ()
        {
            if (getState() == Hotel.STATE_OPEN && this.hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && (this.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_GRANTED || this.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_ACCEPTED))
            {
                return true;
            }
            return false;
        }//end

        public boolean  isCheckedIn ()
        {
            if (getState() == Hotel.STATE_OPEN && this.hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && this.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_NOTREQUESTED)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isOpenForNPC ()
        {
            if (getState() == Hotel.STATE_OPEN)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isOpenForFriend ()
        {
            _loc_1 = this.visitorOrdersForHotel().length<ResortManager.MAX_CHECKINS_PER_HOTEL;
            if (_loc_1 && getState() == Hotel.STATE_OPEN && !this.hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && this.isRoomOnFloor(1))
            {
                return true;
            }
            return false;
        }//end

        public boolean  hasVisitorCheckedIntoDefaultFloor (String param1 ,String param2 )
        {
            _loc_3 = this.defaultCheckInFloor;
            return this.visitorCheckedIntoHotelFloor(param1, param2) == _loc_3;
        }//end

        public boolean  hasVisitorCheckedIntoHotel (String param1 ,String param2 )
        {
            HotelOrder _loc_5 =null ;
            boolean _loc_3 =false ;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_3 = true;
                    break;
                }
            }
            return _loc_3;
        }//end

        public int  visitorCheckedIntoHotelFloor (String param1 ,String param2 )
        {
            HotelOrder _loc_5 =null ;
            int _loc_3 =-1;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_3 = _loc_5.guestData.floor;
                    break;
                }
            }
            return _loc_3;
        }//end

        public boolean  giveGuestRewards (int param1 )
        {
            _loc_2 = getItem().hotel.getFloor(param1);
            _loc_3 = _loc_2.randomModifierTable;
            _loc_4 = (RewardMechanic)MechanicManager.getInstance().getMechanicInstance(this,"rewards",MechanicManager.ALL)
            Array _loc_5 =new Array ();
            _loc_5.put("randomModifiers", _loc_3);
            _loc_5.put("floorId", param1);
            _loc_6 = _loc_4.executeOverrideForGameEvent(MechanicManager.ALL,_loc_5);
            return _loc_4.executeOverrideForGameEvent(MechanicManager.ALL, _loc_5).actionSuccess;
        }//end

        public int  getGuestGiftIndex (String param1 ,String param2 )
        {
            HotelOrder _loc_5 =null ;
            int _loc_3 =-1;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_3 = _loc_5.guestData.gotGift;
                    break;
                }
            }
            return _loc_3;
        }//end

        public void  setGuestVIPStatus (String param1 ,String param2 ,int param3 )
        {
            HotelOrder _loc_5 =null ;
            HotelGuest _loc_6 =null ;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_6 = _loc_5.guestData;
                    _loc_6.VIPStatus = param3;
                    _loc_5.guestData = _loc_6;
                    break;
                }
            }
            return;
        }//end

        public int  getGuestVIPStatus (String param1 ,String param2 )
        {
            HotelOrder _loc_5 =null ;
            _loc_3 = HotelGuest.VIP_NOTREQUESTED;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_3 = _loc_5.guestData.VIPStatus;
                    break;
                }
            }
            return _loc_3;
        }//end

        public boolean  isRoomOnFloor (int param1 )
        {
            _loc_2 = this.getHotelVisitorCountForFloor(param1);
            _loc_3 = getItem().hotel.getFloor(param1).roomCount;
            if (_loc_2 >= _loc_3)
            {
                return false;
            }
            return true;
        }//end

        public int  getHotelVisitorCountForFloor (int param1 )
        {
            HotelOrder _loc_4 =null ;
            int _loc_2 =0;
            _loc_3 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4.guestData.hotelID == getId() && _loc_4.guestData.floor == param1)
                {
                    _loc_2++;
                }
            }
            return _loc_2;
        }//end

        public int  getTotalRooms ()
        {
            ItemDefinitionHotelFloor _loc_3 =null ;
            _loc_1 = m_item.hotel.floors;
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                _loc_2 = _loc_2 + _loc_3.roomCount;
            }
            return _loc_2;
        }//end

        public Array  visitorOrdersForHotel ()
        {
            HotelOrder _loc_3 =null ;
            Array _loc_1 =new Array ();
            _loc_2 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3.hotelID == getId())
                {
                    _loc_1.push(_loc_3);
                }
            }
            return _loc_1;
        }//end

        public int  defaultCheckInFloor ()
        {
            ItemDefinitionHotelFloor _loc_2 =null ;
            int _loc_1 =1;
            if (m_item == null)
            {
                return _loc_1;
            }
            for(int i0 = 0; i0 < m_item.hotel.floors.size(); i0++)
            {
            	_loc_2 = m_item.hotel.floors.get(i0);

                if (_loc_2.upgradeCost == 0 && _loc_2.upgradeCash == 0)
                {
                    _loc_1 = _loc_2.floorID;
                }
            }
            return _loc_1;
        }//end

        public int  getPeepMax ()
        {
            Object _loc_2 =null ;
            int _loc_1 =0;
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.hasOwnProperty("customersReq"))
                {
                    _loc_1 = _loc_2.get("customersReq");
                }
            }
            else
            {
                _loc_1 = m_item.commodityReq;
            }
            return _loc_1;
        }//end

        public int  getPeepCount ()
        {
            Object _loc_2 =null ;
            int _loc_1 =0;
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.hasOwnProperty("customers"))
                {
                    _loc_1 = _loc_2.get("customers");
                }
            }
            return _loc_1;
        }//end

        public void  setPeepCount (int param1 )
        {
            Object _loc_2 =null ;
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.hasOwnProperty("customers"))
                {
                    _loc_2.put("customers",  param1);
                }
            }
            return;
        }//end

        public void  updatePeepCount (int param1 )
        {
            _loc_2 = this.getPeepCount();
            _loc_2 = _loc_2 + param1;
            this.setPeepCount(_loc_2);
            return;
        }//end

        public ItemDefinitionHotelFloor  getFloor (int param1 )
        {
            return m_item.hotel.getFloor(param1);
        }//end

        public Array  getAllRewardDooberData ()
        {
            Array _loc_3 =null ;
            HotelDooberData _loc_4 =null ;
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            while (_loc_2 <= this.getItem().hotel.maxFloors)
            {

                _loc_3 = this.getAllRewardDooberDataForFloor(_loc_2);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    if (_loc_1.indexOf(_loc_4) > -1)
                    {
                        continue;
                    }
                    _loc_1.push(_loc_4);
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        public Array  getAllRewardDooberDataForFloor (int param1 )
        {
            XML _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            XML _loc_8 =null ;
            XML _loc_9 =null ;
            XML _loc_10 =null ;
            String _loc_11 =null ;
            double _loc_12 =0;
            String _loc_13 =null ;
            HotelDooberData _loc_14 =null ;
            Array _loc_2 =new Array ();
            if (this.getItem().hotel.getFloor(param1) == null)
            {
                return _loc_2;
            }
            _loc_3 = this.getItem().hotel.getFloor(param1).randomModifierTable;
            if (_loc_3.length == 0)
            {
                return _loc_2;
            }
            _loc_4 = this.getItem().getRandomModifiersXmlByName(_loc_3);
            for(int i0 = 0; i0 < _loc_4.modifier.size(); i0++)
            {
            	_loc_5 = _loc_4.modifier.get(i0);

                _loc_6 = String(_loc_5.@tableName);
                _loc_7 = String(_loc_5.@type);
                if (_loc_7 == "mixed" && _loc_6.length > 0)
                {
                    _loc_8 = Global.gameSettings().getSpecificRandomModifierTable(_loc_6);
                    for(int i0 = 0; i0 < _loc_8.roll.size(); i0++)
                    {
                    	_loc_9 = _loc_8.roll.get(i0);

                        if (_loc_9.length == 0)
                        {
                            continue;
                        }
                        for(int i0 = 0; i0 < _loc_9.children().size(); i0++)
                        {
                        	_loc_10 = _loc_9.children().get(i0);

                            _loc_11 = _loc_10.localName();
                            _loc_12 = Number(parseFloat(_loc_10.@amount));
                            _loc_13 = String(_loc_10.@name);
                            _loc_14 = new HotelDooberData(_loc_11, _loc_12, _loc_13);
                            _loc_2.push(_loc_14);
                            break;
                        }
                    }
                }
            }
            return _loc_2;
        }//end

        public HotelDooberData  getRewardDooberDataByFloorAndByIndex (int param1 ,int param2 )
        {
            XML _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            XML _loc_9 =null ;
            XML _loc_10 =null ;
            XML _loc_11 =null ;
            String _loc_12 =null ;
            double _loc_13 =0;
            String _loc_14 =null ;
            HotelDooberData _loc_15 =null ;
            _loc_3 = this.getItem().hotel.getFloor(param1).randomModifierTable;
            if (_loc_3.length == 0)
            {
                return null;
            }
            _loc_4 = this.getItem().getRandomModifiersXmlByName(_loc_3);
            _loc_5 = param2;
            for(int i0 = 0; i0 < _loc_4.modifier.size(); i0++)
            {
            	_loc_6 = _loc_4.modifier.get(i0);

                _loc_7 = String(_loc_6.@tableName);
                _loc_8 = String(_loc_6.@type);
                if (_loc_8 == "mixed" && _loc_7.length > 0)
                {
                    _loc_9 = Global.gameSettings().getSpecificRandomModifierTable(_loc_7);
                    for(int i0 = 0; i0 < _loc_9.roll.size(); i0++)
                    {
                    	_loc_10 = _loc_9.roll.get(i0);

                        if (_loc_10.length == 0)
                        {
                            continue;
                        }
                        for(int i0 = 0; i0 < _loc_10.children().size(); i0++)
                        {
                        	_loc_11 = _loc_10.children().get(i0);

                            _loc_12 = _loc_11.localName();
                            _loc_13 = Number(parseFloat(_loc_11.@amount));
                            _loc_14 = String(_loc_11.@name);
                            _loc_15 = new HotelDooberData(_loc_12, _loc_13, _loc_14);
                            if (_loc_5 == 0)
                            {
                                return _loc_15;
                            }
                            _loc_5 = _loc_5 - 1;
                            break;
                        }
                    }
                }
            }
            return null;
        }//end

        public void  onMechanicDataChanged (GenericObjectEvent event )
        {
            return;
        }//end

        public void  updateSparkleEffect ()
        {
            if (Global.isVisiting())
            {
                this.onMechanicDataChanged(null);
            }
            return;
        }//end

        public Array  hotelVisitorOrders ()
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

        public HotelOrder  getVisitorHotelOrder (String param1 ,String param2 )
        {
            HotelOrder _loc_5 =null ;
            HotelOrder _loc_3 =null ;
            _loc_4 = this.hotelVisitorOrders;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.equalityCheck(param1, getId(), param2))
                {
                    _loc_3 = _loc_5;
                    break;
                }
            }
            return _loc_3;
        }//end

        public String  closedFlyoutText ()
        {
            return "";
        }//end

        public String  fullFlyoutText ()
        {
            return "";
        }//end

        public String  getDefaultUGCCheckinMessage (int param1 )
        {
            _loc_2 = String(param1);
            if (param1 == 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "HotelDialog_floorVIPSingle");
            }
            return ZLoc.t("Dialogs", "FriendNPC_tooltip_noUgcDefaultMsg", {floor:_loc_2});
        }//end

        public String  getFloorCaption (int param1 )
        {
            _loc_2 = ZLoc.t("Dialogs","HotelDialog_floorText",{floorIDparam1});
            if (param1 == 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "HotelDialog_floorVIPSingle");
            }
            return _loc_2;
        }//end

    }





