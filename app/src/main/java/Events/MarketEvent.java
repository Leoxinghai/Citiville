package Events;

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
//import flash.events.*;
//import flash.utils.*;

    public class MarketEvent extends Event
    {
        public String item ;
        public int eventType ;
        public String eventSource ;
public static Dictionary m_itemTypeMap =null ;
        public static  int GENERIC =1;
        public static  int CONTRACT =2;
        public static  int RESIDENCE =3;
        public static  int BUSINESS =4;
        public static  int EXPAND_FARM =5;
        public static  int EXTRA =6;
        public static  int WONDER =7;
        public static  int VEHICLE =8;
        public static  int GOODS =9;
        public static  int THEME_COLLECTION =10;
        public static  int TRAIN =11;
        public static  int FBC_PURCHASE =12;
        public static  int MYSTERY_CRATE =13;
        public static  int PERMIT_BUNDLE =14;
        public static  int GENERIC_BUNDLE =15;
        public static  int ITEM_SKIN =16;
        public static  int ITEM_SKIN_COMBINED =17;
        public static  int NEIGHBORHOOD =18;
        public static  int THEMED_BUNDLE =19;
        public static  int HOTEL =24;
        public static  int PLAY =25;
        public static  int BRIDGE =48;
        public static  int GAS =49;
        public static  int MECHANIC =50;
        public static  String SOURCE_INVENTORY ="fromInventory";
        public static  String SOURCE_STORAGE ="fromStorage";
        public static  int EQUIPMENT =12;
        public static  String MARKET_BUY ="marketbuy";
        public static  String MARKET_GIFT ="marketgift";
        public static  String MARKET_PRESENT ="marketpresent";

        public  MarketEvent (String param1 ,int param2 ,String param3 ,boolean param4 =false ,boolean param5 =false ,String param6 ="")
        {
            this.eventType = param2;
            this.item = param3;
            this.eventSource = param6;
            super(param1, param4, param5);
            return;
        }//end

        public static MarketEvent  build (String param1 ,Item param2 ,String param3 ="",boolean param4 =false ,boolean param5 =false )
        {
            _loc_6 = param2.name ;
            _loc_7 = param2.type;
            if (!m_itemTypeMap)
            {
                initItemTypeMap();
            }
            if (m_itemTypeMap.get(_loc_7))
            {
                return new MarketEvent(param1, m_itemTypeMap.get(_loc_7), _loc_6, param4, param5, param3);
            }
            return new MarketEvent(param1, MarketEvent.GENERIC, _loc_6, param4, param5, param3);
        }//end

        public static void  initItemTypeMap ()
        {
            m_itemTypeMap = new Dictionary();
            m_itemTypeMap.put("tool",  MarketEvent.EQUIPMENT);
            m_itemTypeMap.put("contract",  MarketEvent.CONTRACT);
            m_itemTypeMap.put("residence",  MarketEvent.RESIDENCE);
            m_itemTypeMap.put("business",  MarketEvent.BUSINESS);
            m_itemTypeMap.put("plot_contract",  MarketEvent.CONTRACT);
            m_itemTypeMap.put("storage",  MarketEvent.GENERIC);
            m_itemTypeMap.put("plot",  MarketEvent.GENERIC);
            m_itemTypeMap.put("factory",  MarketEvent.GENERIC);
            m_itemTypeMap.put("sidewalk",  MarketEvent.GENERIC);
            m_itemTypeMap.put("parking_lot",  MarketEvent.GENERIC);
            m_itemTypeMap.put("decoration",  MarketEvent.GENERIC);
            m_itemTypeMap.put("road",  MarketEvent.GENERIC);
            m_itemTypeMap.put("pier",  MarketEvent.GENERIC);
            m_itemTypeMap.put("ship",  MarketEvent.GENERIC);
            m_itemTypeMap.put("harvest_ship",  MarketEvent.GENERIC);
            m_itemTypeMap.put("ship_contract",  MarketEvent.CONTRACT);
            m_itemTypeMap.put("expansion",  MarketEvent.EXPAND_FARM);
            m_itemTypeMap.put("energy",  MarketEvent.EXTRA);
            m_itemTypeMap.put("gas",  MarketEvent.GAS);
            m_itemTypeMap.put("theme_collection",  MarketEvent.THEME_COLLECTION);
            m_itemTypeMap.put("goods",  MarketEvent.GOODS);
            m_itemTypeMap.put("municipal",  MarketEvent.GENERIC);
            m_itemTypeMap.put("holidaytree",  MarketEvent.GENERIC);
            m_itemTypeMap.put("landmark",  MarketEvent.GENERIC);
            m_itemTypeMap.put("itemStorage",  MarketEvent.GENERIC);
            m_itemTypeMap.put("starter_pack2_1",  MarketEvent.FBC_PURCHASE);
            m_itemTypeMap.put("starter_pack2_2",  MarketEvent.FBC_PURCHASE);
            m_itemTypeMap.put("starter_pack2_3",  MarketEvent.FBC_PURCHASE);
            m_itemTypeMap.put("starter_pack2_4",  MarketEvent.FBC_PURCHASE);
            m_itemTypeMap.put("factory_contract",  MarketEvent.CONTRACT);
            m_itemTypeMap.put("wonder",  MarketEvent.WONDER);
            m_itemTypeMap.put("vehicle",  MarketEvent.VEHICLE);
            m_itemTypeMap.put("train",  MarketEvent.TRAIN);
            m_itemTypeMap.put("bridge",  MarketEvent.BRIDGE);
            m_itemTypeMap.put("zooEnclosure",  MarketEvent.GENERIC);
            m_itemTypeMap.put("garden",  MarketEvent.GENERIC);
            m_itemTypeMap.put("permit_pack2",  MarketEvent.PERMIT_BUNDLE);
            m_itemTypeMap.put("permit_pack3",  MarketEvent.PERMIT_BUNDLE);
            m_itemTypeMap.put("permit_pack4",  MarketEvent.PERMIT_BUNDLE);
            m_itemTypeMap.put("genericBundle",  MarketEvent.GENERIC_BUNDLE);
            m_itemTypeMap.put("themed_bundle",  MarketEvent.THEMED_BUNDLE);
            m_itemTypeMap.put("play",  MarketEvent.THEMED_BUNDLE);
            return;
        }//end


    }



