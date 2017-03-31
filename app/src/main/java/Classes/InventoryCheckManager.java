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

import Classes.featuredata.*;
import Modules.garden.*;
//import flash.utils.*;

    public class InventoryCheckManager
    {
        public static  String RESULT_SUCCESS_HALT ="succeed_halt";
        public static  String RESULT_FAIL_HALT ="fail_halt";
        public static  String RESULT_PROCEED ="proceed";
        private static InventoryCheckManager s_instance =null ;

        public  InventoryCheckManager ()
        {
            if (InventoryCheckManager.s_instance != null)
            {
                throw new Error("Attempting to instantiate more than one InventoryCheckManager");
            }
            return;
        }//end

        public boolean  hasInventoryChecks (Item param1 )
        {
            XMLList _loc_3 =null ;
            if (param1 == null)
            {
                return false;
            }
            boolean _loc_2 =false ;
            if (param1.xml.inventoryChecks && param1.xml.inventoryChecks.length() > 0)
            {
                _loc_3 = param1.xml.inventoryChecks;
                if (_loc_3.inventoryCheck.length() > 0)
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public String  onItemAdd (Item param1 ,int param2 ,Dictionary param3 )
        {
            _loc_4 = int(param3.get("quantity"));
            _loc_5 = (FeatureDataInventory)FeatureDataManager.instance.getFeatureDataClass(param3.get("featureDataKey"))
            if (((FeatureDataInventory)FeatureDataManager.instance.getFeatureDataClass(param3.get("featureDataKey"))).updateItemAmount(param1.name, _loc_4))
            {
                return RESULT_SUCCESS_HALT;
            }
            return RESULT_FAIL_HALT;
        }//end

        public String  onTicketAdd (Item param1 ,int param2 ,Dictionary param3 )
        {
            Object _loc_9 =null ;
            double _loc_10 =0;
            _loc_4 = param3.get("theme") ;
            _loc_5 = int(param3.get("quantity"));
            _loc_6 = .put("showFeedPopup",  null ? (param3.get("showFeedPopup")) : (false));
            _loc_7 = ["ignoreCapacity"]!= null ? (param3.get("ignoreCapacity") == "true") : (false);
            _loc_8 = ["showMQIcon"]!= null ? (param3.get("showMQIcon") == "true") : (false);
            if (param3.get("showMQIcon") != null ? (param3.get("showMQIcon") == "true") : (false))
            {
                Global.world.citySim.miniQuestManager.showTicketMQIcon(_loc_4);
            }
            if (Global.ticketManager.canAddTicket(_loc_4, _loc_5) || _loc_7)
            {
                Global.ticketManager.updateCount(_loc_4, _loc_5, _loc_6);
                return RESULT_SUCCESS_HALT;
            }
            _loc_9 = Global.ticketManager.getTicket(_loc_4);
            _loc_10 = Global.ticketManager.getTicketCountByLevel(_loc_4);
            if (_loc_9 && _loc_9.hasOwnProperty("count") && _loc_9.count && _loc_10)
            {
                Global.ticketManager.showTicketPopup(_loc_4, true);
            }
            return RESULT_FAIL_HALT;
        }//end

        public String  onFlowerAdd (Item param1 ,int param2 ,Dictionary param3 )
        {
            _loc_4 = .get("item")? (param3.get("item")) : (String(param1.name));
            _loc_5 = Global.gameSettings().getItemByName(_loc_4);
            _loc_6 = String(_loc_5.gardenType);
            _loc_7 = int(param3.get("quantity"));
            _loc_8 = param3.get("showfeed") =="true";
            if (_loc_6 && _loc_4)
            {
                if (GardenManager.instance.canAddFlower(_loc_6, _loc_4, _loc_7))
                {
                    if (_loc_8)
                    {
                        GardenManager.instance.postSendSeedFeed(_loc_6, _loc_4, false);
                    }
                    GardenManager.instance.addFlower(_loc_6, _loc_4, _loc_7);
                    GardenManager.instance.refreshFeatureData();
                    return RESULT_SUCCESS_HALT;
                }
                else if (_loc_8 && _loc_7 > 0)
                {
                    GardenManager.instance.postSendSeedFeed(_loc_6, _loc_4, true);
                }
            }
            return RESULT_FAIL_HALT;
        }//end

        public String  onGiftBasketAdd (Item param1 ,int param2 ,Dictionary param3 )
        {
            _loc_4 = int(param3.get("coins"));
            _loc_5 = int(param3.get("goods"));
            if (_loc_4 < 0 || _loc_5 < 0)
            {
                return RESULT_FAIL_HALT;
            }
            Global.player.gold = Global.player.gold + _loc_4;
            Global.player.commodities.add("goods", _loc_5);
            return RESULT_SUCCESS_HALT;
        }//end

        public String  onRegenerableResourceAdd (Item param1 ,int param2 ,Dictionary param3 )
        {
            _loc_4 = .get("regenerableResource");
            _loc_5 = int(param3.get("quantity"));
            if (Global.player.updateRegenerableResource(_loc_4, _loc_5))
            {
                return RESULT_SUCCESS_HALT;
            }
            return RESULT_FAIL_HALT;
        }//end

        public String  onTicketRemove (Item param1 ,int param2 ,Dictionary param3 )
        {
            return RESULT_SUCCESS_HALT;
        }//end

        public static InventoryCheckManager  getInstance ()
        {
            if (!InventoryCheckManager.s_instance)
            {
                InventoryCheckManager.s_instance = new InventoryCheckManager;
            }
            return s_instance;
        }//end

    }



