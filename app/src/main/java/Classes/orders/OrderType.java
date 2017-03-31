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
import Classes.orders.Valentines2011.*;
import Engine.Managers.*;

    public class OrderType
    {
        public static  String TRAIN ="order_train";
        public static  String LOT ="order_lot";
        public static  String VISITOR_HELP ="order_visitor_help";
        public static  String VALENTINE_2011 ="order_valentine";
        public static  String HOTEL ="order_hotel";

        public  OrderType ()
        {
            return;
        }//end

        public static AbstractOrder  order (String param1 ,String param2 ,String param3 ,String param4 ,Object param5 )
        {
            boolean _loc_7 =false ;
            AbstractOrder _loc_6 =null ;
            switch(param3)
            {
                case TRAIN:
                {
                    _loc_6 = new TrainOrder(param1, param2);
                    break;
                }
                case LOT:
                {
                    _loc_6 = new LotOrder(param1, param2);
                    break;
                }
                case VISITOR_HELP:
                {
                    _loc_6 = new VisitorHelpOrder(param1, param2);
                    break;
                }
                case VALENTINE_2011:
                {
                    _loc_6 = new ValentineOrder(param1, param2);
                    break;
                }
                case HOTEL:
                {
                    _loc_6 = new HotelOrder(param1, param2);
                    break;
                }
                default:
                {
                    _loc_6 = null;
                    break;
                    break;
                }
            }
            if (_loc_6)
            {
                _loc_7 = _loc_6.doWeHaveBadParams(param5);
                if (_loc_7)
                {
                    StatsManager.count("bad_order", param3);
                    return null;
                }
                _loc_6.setParams(param5);
            }
            return _loc_6;
        }//end

    }


