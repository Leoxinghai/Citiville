package Classes.orders;

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

import Classes.orders.Hotel.*;
//import flash.utils.*;

    public class AbstractOrder
    {
        protected Object m_params ;
        public static  String PREFIX ="i";
public static  String SENDER_ID ="senderID";
public static  String RECIPIENT_ID ="recipientID";
public static  String TIME_SENT ="timeSent";
public static  String LAST_TIME_REMINDED ="lastTimeReminded";
public static  String ORDER_TYPE ="orderType";
public static  String ORDER_STATE ="orderState";
public static  String TRANSMISSION_STATUS ="transmissionStatus";

        public  AbstractOrder (String param1 ,String param2 ,String param3 ,String param4 ,Object param5 =null )
        {
            if (param5)
            {
                this.m_params = param5;
            }
            else
            {
                this.m_params = new Object();
            }
            this.setRecipientID(param1);
            this.setSenderID(param2);
            this.setType(param3);
            this.setTransmissionStatus(param4);
            return;
        }//end

        public int  getTimeSent ()
        {
            return this.m_params.get(TIME_SENT);
        }//end

        public int  getLastTimeReminded ()
        {
            return this.m_params.get(LAST_TIME_REMINDED);
        }//end

        public String  getType ()
        {
            return this.m_params.get(ORDER_TYPE);
        }//end

        public String  getState ()
        {
            return this.m_params.get(ORDER_STATE);
        }//end

        public String  getSenderID ()
        {
            return this.m_params.get(SENDER_ID);
        }//end

        public String  getRecipientID ()
        {
            return this.m_params.get(RECIPIENT_ID);
        }//end

        public String  getTransmissionStatus ()
        {
            return this.m_params.get(TRANSMISSION_STATUS);
        }//end

        public Object  getParams ()
        {
            return this.m_params;
        }//end

        public boolean  doWeHaveBadParams (Object param1 )
        {
            return false;
        }//end

        public void  setParams (Object param1 )
        {
            this.m_params = param1;
            return;
        }//end

        public void  updateOrder (Object param1 )
        {
            this.m_params = param1;
            return;
        }//end

        public void  setTimeSent (int param1 )
        {
            this.m_params.put(TIME_SENT,  param1);
            return;
        }//end

        public void  setLastTimeReminded (int param1 )
        {
            this.m_params.put(LAST_TIME_REMINDED,  param1);
            return;
        }//end

        public void  setType (String param1 )
        {
            this.m_params.put(ORDER_TYPE,  param1);
            return;
        }//end

        public void  setState (String param1 )
        {
            this.m_params.put(ORDER_STATE,  param1);
            return;
        }//end

        public void  setSenderID (String param1 )
        {
            if (param1.charAt() == PREFIX)
            {
                this.m_params.put(SENDER_ID,  param1.slice(1));
            }
            else
            {
                this.m_params.put(SENDER_ID,  param1);
            }
            return;
        }//end

        public void  setRecipientID (String param1 )
        {
            if (param1.charAt() == PREFIX)
            {
                this.m_params.put(RECIPIENT_ID,  param1.slice(1));
            }
            else
            {
                this.m_params.put(RECIPIENT_ID,  param1);
            }
            return;
        }//end

        public void  setTransmissionStatus (String param1 )
        {
            this.m_params.put(TRANSMISSION_STATUS,  param1);
            return;
        }//end

        public boolean  equals (AbstractOrder param1 )
        {
            return this.getSenderID() == param1.getSenderID();
        }//end

        public static int  parseOrder (String param1 ,String param2 ,String param3 ,String param4 ,Object param5 ,Dictionary param6 )
        {
            if (param1 == OrderType.HOTEL)
            {
                return HotelOrder.parseOrder(param1, param2, param3, param4, param5, param6);
            }
            _loc_7 = param2==OrderStatus.RECEIVED? (Global.player.snUser.uid) : (param4);
            _loc_8 = param2==OrderStatus.RECEIVED? (param4) : (Global.player.snUser.uid);
            if (Global.isVisiting())
            {
                _loc_7 = param2 == OrderStatus.RECEIVED ? (Global.getVisiting()) : (param4);
                _loc_8 = param2 == OrderStatus.RECEIVED ? (param4) : (Global.getVisiting());
            }
            _loc_9 = OrderType.order(_loc_7,_loc_8,param1,param2,param5.get(param1).get(param2).get(param3).get(param4));
            if (OrderType.order(_loc_7, _loc_8, param1, param2, param5[param1].get(param2).get(param3).get(param4)) == null)
            {
                return 0;
            }
            _loc_9.setState(param3);
            _loc_9.setTransmissionStatus(param2);
            param6.get(param1).get(param2).push(_loc_9);
            return 1;
        }//end

    }



