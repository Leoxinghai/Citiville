package Modules.hotels;

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
import Classes.util.*;
import Display.DialogUI.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class HotelsDialog extends GenericDialog
    {
        protected MechanicMapResource m_owner ;
        protected int m_tab ;
        protected String m_skin ;
        public static  int TAB_GUESTS =0;
        public static  int TAB_UPGRADE =1;
        public static int DEFAULT_TAB =0;
        public static  String INFOGRAPHIC_URL ="assets/hotels/hotelsInfographic.png";

        public  HotelsDialog (MechanicMapResource param1 ,Item param2 ,int param3 =0,String param4 ="",Function param5 =null )
        {
            this.m_owner = param1;
            this.m_tab = param3;
            this.m_skin = this.m_owner.getItem().UI_skin;
            super("", "HotelDialog", 0, param5, "HotelDialog");
            return;
        }//end

        protected String  skinAssetName ()
        {
            return DelayedAssetLoader.DIALOG_ASSETS_PATH + this.m_skin + "Assets.swf";
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =.get(DelayedAssetLoader.HOTEL_ASSETS ,DelayedAssetLoader.GENERIC_DIALOG_ASSETS ,DelayedAssetLoader.PAYROLL_ASSETS ,DelayedAssetLoader.TABBED_ASSETS) ;
            if (this.m_skin && this.m_skin != "default")
            {
                _loc_1.push(this.skinAssetName);
            }
            return _loc_1;
        }//end

        protected Class  getAsset (String param1 )
        {
            Class _loc_2 =null ;
            if (this.m_skin && this.m_skin != "default" && m_assetDependencies.get(this.skinAssetName) && m_assetDependencies.get(this.skinAssetName).hasOwnProperty(this.m_skin + "_" + param1))
            {
                _loc_2 = m_assetDependencies.get(this.skinAssetName).get(this.m_skin + "_" + param1);
            }
            else
            {
                _loc_2 = m_assetDependencies.get(DelayedAssetLoader.HOTEL_ASSETS).get("hotels_" + param1);
            }
            return _loc_2;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            HotelsDialogView dialogView ;
            assetDict = param1;
            assetDict.put("spawner",  this.m_owner);
            dialogView = new HotelsDialogView(assetDict, this.m_tab, m_message, m_dialogTitle, m_type, m_callback, m_icon, m_iconPos, m_feedShareViralType, m_SkipCallback, m_customOk, m_relativeIcon);
            LoadingManager .load (Global .getAssetURL (INFOGRAPHIC_URL ),void  (Event event )
            {
                dialogView.setInfographic(event.target.content);
                return;
            }//end
            );
            return dialogView;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("btn_up_normal",  m_comObject.gridlist_nav_up_normal);
            _loc_1.put("btn_up_over",  m_comObject.gridlist_nav_up_over);
            _loc_1.put("btn_up_down",  m_comObject.gridlist_nav_up_down);
            _loc_1.put("btn_down_normal",  m_comObject.gridlist_nav_down_normal);
            _loc_1.put("btn_down_over",  m_comObject.gridlist_nav_down_over);
            _loc_1.put("btn_down_down",  m_comObject.gridlist_nav_down_down);
            _loc_1.put("pic_checkGreen",  m_comObject.checkmark_green);
            _loc_1.put("pic_dot",  m_comObject.gridList_page_dot);
            _loc_1.put("dialog_div",  m_comObject.dialog_rename_divider);
            _loc_1.put("vertical_scrollBar_border",  m_comObject.vertical_scrollBar_border);
            _loc_1.put("hotels_blueTabLeft",  this.getAsset("blueTabLeft"));
            _loc_1.put("hotels_blueTabRight",  this.getAsset("blueTabRight"));
            _loc_1.put("hotels_bottomFloor",  this.getAsset("bottomFloor"));
            _loc_1.put("hotels_deleteMessageX",  this.getAsset("deleteMessageX"));
            _loc_2 = this.getAsset("dialogFrame");
            _loc_1.put("dialog_bg", (DisplayObject) new _loc_2);
            _loc_1.put("hotels_floorLock",  this.getAsset("floorLock"));
            _loc_1.put("hotels_floorLocked",  this.getAsset("floorLocked"));
            _loc_1.put("hotels_floorSelector",  this.getAsset("floorSelector"));
            _loc_1.put("hotels_floorUnlocked",  this.getAsset("floorUnlocked"));
            _loc_1.put("hotels_friendMessage",  this.getAsset("friendMessage"));
            _loc_1.put("hotels_goalsBackground",  this.getAsset("goalsBackground"));
            _loc_1.put("hotels_guestSign",  this.getAsset("guestSign"));
            _loc_1.put("hotels_mainTabPanel",  this.getAsset("mainTabPanel"));
            _loc_1.put("hotels_midFloors",  this.getAsset("midFloors"));
            _loc_1.put("hotels_npcMessageBubble",  this.getAsset("npcMessageBubble"));
            _loc_1.put("hotels_npc_host",  this.getAsset("npc_host"));
            _loc_1.put("hotels_penthouseStar",  this.getAsset("penthouseStar"));
            _loc_1.put("hotels_podium",  this.getAsset("podium"));
            _loc_1.put("hotels_previewBackground",  this.getAsset("previewBackground"));
            _loc_1.put("hotels_previewBoxes",  this.getAsset("previewBoxes"));
            _loc_1.put("hotels_scrollAreaTop",  this.getAsset("scrollAreaTop"));
            _loc_1.put("hotels_scrollAreaTop2",  this.getAsset("scrollAreaTop2"));
            _loc_1.put("hotels_timerBackground",  this.getAsset("timerBackground"));
            _loc_1.put("hotels_topFloor",  this.getAsset("topFloor"));
            _loc_1.put("hotels_upgradeBackground",  this.getAsset("upgradeBackground"));
            _loc_1.put("hotels_welcomeSign",  this.getAsset("welcomeSign"));
            _loc_1.put("hotels_whiteTabLeft",  this.getAsset("whiteTabLeft"));
            _loc_1.put("hotels_whiteTabRight",  this.getAsset("whiteTabRight"));
            _loc_1.put("hotels_hotels_15",  this.getAsset("hotels_15"));
            _loc_1.put("hotels_questionButton",  this.getAsset("questionButton"));
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            _loc_1.put("payroll_checkin_confirmCheckmark",  _loc_3.payroll_checkin_confirmCheckmark);
            _loc_1.put("payroll_icon_clock",  _loc_3.payroll_icon_clock);
            _loc_1.put("payroll_icon_responses",  _loc_3.payroll_icon_responses);
            _loc_1.put("payroll_reward_coin",  _loc_3.payroll_reward_coin);
            _loc_1.put("payroll_reward_energy",  _loc_3.payroll_reward_energy);
            _loc_1.put("payroll_reward_xp",  _loc_3.payroll_reward_xp);
            _loc_4 = m_assetDependencies.get(DelayedAssetLoader.TABBED_ASSETS) ;
            _loc_1.put("tab_unselected",  _loc_4.police_tab_unselected);
            _loc_1.put("tab_selected",  _loc_4.police_tab_selected);
            _loc_1.put("list_bg",  _loc_4.conventionCenter_innerArea_flatTop);
            m_assetBG = _loc_1.get("dialog_bg");
            return _loc_1;
        }//end

    }



