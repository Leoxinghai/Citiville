package Classes.orders.Hotel;

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
//import flash.utils.*;

    public class HotelOrder extends AbstractOrder
    {
        private double m_hotelID ;
        private String m_hotelType ;
        private HotelGuest m_guestData ;
        protected Object m_HotelParams ;
        private static  String GUESTS ="guests";
        public static  String HOTEL_ID ="hotelId";
        private static  String FLOOR_ID ="floorId";
        private static  String TIMESTAMP ="timestamp";
        private static  String VIP_STATUS ="vipStatus";
        private static  String GOT_GIFT ="gotGift";
        private static  String REPORTED ="reported";
        private static  String GUEST_DATA ="guest_data";
        private static  String UGC_ID ="ugcId";

        public  HotelOrder (String param1 ,String param2 )
        {
            super(param1, param2, OrderType.HOTEL, null, null);
            return;
        }//end

         public boolean  doWeHaveBadParams (Object param1 )
        {
            if (!param1.hasOwnProperty(HOTEL_ID))
            {
                return true;
            }
            return false;
        }//end

         public void  setParams (Object param1 )
        {
            this.m_HotelParams = param1;
            _loc_2 = (int)(param1.get(FLOOR_ID) );
            _loc_3 =(double)(param1.get(TIMESTAMP) );
            _loc_4 = (int)(param1.get(VIP_STATUS) );
            int _loc_5 =-1;
            if (param1.get(GOT_GIFT).toString() == "true")
            {
                _loc_5 = 0;
            }
            else
            {
                _loc_5 = int(param1.get(GOT_GIFT));
            }
            this.m_hotelID = param1.get(HOTEL_ID);
            _loc_6 = param1.get(UGC_ID);
            _loc_7 = (String)Global.world.friendUGCManager.getUGCObject(_loc_6 );
            _loc_8 = param1.get(REPORTED);
            HotelGuest _loc_9 =new HotelGuest(getSenderID (),_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 ,_loc_7 ,_loc_8 ,this ,this.m_hotelID );
            this.m_guestData = _loc_9;
            return;
        }//end

        public void  overrideParams (int param1 ,int param2 ,double param3 ,int param4 ,int param5 ,String param6 )
        {
            this.m_hotelID = param1;
            _loc_7 = param2;
            _loc_8 = param3;
            _loc_9 = param4;
            _loc_10 = param5;
            _loc_11 = param6;
            Array _loc_12 =new Array ();
            HotelGuest _loc_13 =new HotelGuest(getSenderID (),_loc_7 ,_loc_8 ,_loc_9 ,_loc_10 ,_loc_11 ,_loc_12 ,this ,param1 );
            this.m_guestData = _loc_13;
            return;
        }//end

        public double  hotelID ()
        {
            return this.m_hotelID;
        }//end

        public void  hotelID (double param1 )
        {
            this.m_hotelID = param1;
            return;
        }//end

        public String  getHotelType ()
        {
            return this.m_hotelType;
        }//end

        public HotelGuest  guestData ()
        {
            return this.m_guestData;
        }//end

        public void  guestData (HotelGuest param1 )
        {
            this.m_guestData = param1;
            return;
        }//end

        public boolean  equalityCheck (String param1 ,int param2 ,String param3 )
        {
            if (getRecipientID() == param1 && this.m_hotelID == param2 && getSenderID() == param3)
            {
                return true;
            }
            return false;
        }//end

        public static int  parseOrder (String param1 ,String param2 ,String param3 ,String param4 ,Object param5 ,Dictionary param6 )
        {
            String _loc_10 =null ;
            AbstractOrder _loc_11 =null ;
            HotelOrder _loc_12 =null ;
            _loc_7 = Global.player.snUser.uid;
            _loc_8 = param4;
            int _loc_9 =0;
            for(int i0 = 0; i0 < param5.get(param1).get(param2).get(param3).get(param4).size(); i0++)
            {
            		_loc_10 = param5.get(param1).get(param2).get(param3).get(param4).get(i0);

                if (Global.isVisiting())
                {
                    _loc_7 = Global.getVisiting();
                }
                _loc_11 = OrderType.order(_loc_7, _loc_10, param1, param2, param5.get(param1).get(param2).get(param3).get(param4).get(_loc_10));
                if (_loc_11 == null)
                {
                    return 0;
                }
                _loc_12 =(HotelOrder) _loc_11;
                _loc_12.hotelID = Number(_loc_8.slice(1));
                _loc_11.setState(param3);
                _loc_11.setTransmissionStatus(param2);
                param6.get(param1).get(param2).push(_loc_11);
                _loc_9++;
            }
            return _loc_9;
        }//end

    }



