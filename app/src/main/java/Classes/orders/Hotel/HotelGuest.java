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

    public class HotelGuest
    {
        private String m_guestID ;
        private int m_floor ;
        private double m_timestamp ;
        private int m_vipStatus ;
        private int m_gotGift ;
        private String m_msg ;
        private String m_dataString ;
        private int m_hotelID ;
        private HotelOrder m_order ;
        private Array m_reported ;
        public static  int VIP_NOTREQUESTED =0;
        public static  int VIP_REQUESTED =1;
        public static  int VIP_GRANTED =2;
        public static  int VIP_ACCEPTED =3;

        public  HotelGuest (String param1 ,int param2 ,double param3 ,int param4 ,int param5 ,String param6 ,Array param7 ,HotelOrder param8 ,int param9 )
        {
            this.m_reported = new Array();
            this.m_guestID = param1;
            this.m_floor = param2;
            this.m_timestamp = param3;
            this.m_vipStatus = param4;
            this.m_gotGift = param5;
            this.m_msg = param6;
            this.m_reported = param7;
            this.m_order = param8;
            this.m_hotelID = param9;
            return;
        }//end

        public String  dataString ()
        {
            return this.m_dataString;
        }//end

        public String  guestID ()
        {
            return this.m_guestID;
        }//end

        public int  floor ()
        {
            return this.m_floor;
        }//end

        public void  floor (int param1 )
        {
            this.m_floor = param1;
            return;
        }//end

        public double  timestamp ()
        {
            return this.m_timestamp;
        }//end

        public int  VIPStatus ()
        {
            return this.m_vipStatus;
        }//end

        public void  VIPStatus (int param1 )
        {
            this.m_vipStatus = param1;
            return;
        }//end

        public boolean  hasNotRequestedVIPStatus ()
        {
            return this.m_vipStatus == HotelGuest.VIP_NOTREQUESTED;
        }//end

        public boolean  hasRequestedVIPStatus ()
        {
            return this.m_vipStatus == HotelGuest.VIP_REQUESTED;
        }//end

        public boolean  isVIP ()
        {
            return this.m_vipStatus == HotelGuest.VIP_GRANTED;
        }//end

        public int  gotGift ()
        {
            return this.m_gotGift;
        }//end

        public void  gotGift (int param1 )
        {
            this.m_gotGift = param1;
            return;
        }//end

        public String  message ()
        {
            return this.m_msg;
        }//end

        public void  message (String param1 )
        {
            this.m_msg = param1;
            return;
        }//end

        public Array  reported ()
        {
            return this.m_reported;
        }//end

        public int  hotelID ()
        {
            return this.m_hotelID;
        }//end

        public void  hotelID (int param1 )
        {
            this.m_hotelID = param1;
            return;
        }//end

    }


