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

    public class HotelsGuestDialog extends GenericDialog
    {
        protected MechanicMapResource m_owner ;
        protected Function m_upgradeCallback ;
        protected int numAssetsLeft =0;
        protected Dictionary m_loaderDictionary ;
        protected HotelsGuestDialogView dialogView =null ;
        private String m_skin ;
public static Dictionary m_contentDictionary =new Dictionary ();
public static Array m_loadedAssets =new Array ();
        public static  String urlbackOrig ="assets/hotels/rewards/background.png";
        public static  String urlbackOrig2 ="assets/hotels/rewards/background2.png";

        public  HotelsGuestDialog (MechanicMapResource param1 ,Function param2 )
        {
            this.m_loaderDictionary = new Dictionary();
            this.m_owner = param1;
            this.m_upgradeCallback = param2;
            this.m_skin = this.m_owner.getItem().UI_skin;
            _loc_3 = param1.getCustomName ();
            super("", "VisitorDialog", 0, null, _loc_3);
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

         protected void  loadAssets ()
        {
            HotelDooberData _loc_8 =null ;
            String _loc_9 =null ;
            Loader _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            Loader _loc_13 =null ;
            _loc_1 =Global.getAssetURL(urlbackOrig );
            _loc_2 = LoadingManager.load(_loc_1,this.onLoadPictureAsset,LoadingManager.PRIORITY_HIGH);
            this.m_loaderDictionary.put(_loc_2,  urlbackOrig);
            m_loadedAssets.push(urlbackOrig);
            _loc_3 =Global.getAssetURL(urlbackOrig2 );
            _loc_4 = LoadingManager.load(_loc_3,this.onLoadPictureAsset,LoadingManager.PRIORITY_HIGH);
            this.m_loaderDictionary.put(_loc_4,  urlbackOrig2);
            m_loadedAssets.push(urlbackOrig2);
            this.numAssetsLeft = 2;
            int _loc_5 =0;
            while (_loc_5 <= this.m_owner.getItem().hotel.maxFloors)
            {

                _loc_9 = this.m_owner.getItem().hotel.getFloor(_loc_5).imageURL;
                _loc_9 = Global.getAssetURL(_loc_9);
                this.numAssetsLeft++;
                _loc_10 = LoadingManager.load(_loc_9, this.onLoadPictureAsset, LoadingManager.PRIORITY_HIGH);
                this.m_loaderDictionary.put(_loc_10,  this.m_owner.getItem().hotel.getFloor(_loc_5).imageURL);
                m_loadedAssets.push(this.m_owner.getItem().hotel.getFloor(_loc_5).imageURL);
                _loc_5++;
            }
            _loc_6 =(ICheckInHandler) this.m_owner;
            _loc_7 = this(.m_owner as ICheckInHandler ).getAllRewardDooberData ();
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            		_loc_8 = _loc_7.get(i0);

                if (m_loadedAssets.indexOf(_loc_8.pictureURL) > -1)
                {
                    continue;
                }
                _loc_11 = Global.getAssetURL(_loc_8.pictureURL);
                _loc_12 = _loc_11;
                this.numAssetsLeft++;
                _loc_13 = LoadingManager.load(_loc_11, this.onLoadPictureAsset, LoadingManager.PRIORITY_HIGH);
                this.m_loaderDictionary.put(_loc_13,  _loc_8.pictureURL);
                m_loadedAssets.push(_loc_8.pictureURL);
            }
            return;
        }//end

        protected void  onLoadPictureAsset (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            _loc_3 = _loc_2.content ;
            _loc_4 = this.m_loaderDictionary.get(_loc_2) ;
            if (this.m_loaderDictionary.get(_loc_2).length > 0)
            {
                m_contentDictionary.put(_loc_4,  _loc_3);
            }
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  checkAssetsLoaded ()
        {
             this.numAssetsLeft--;
            if (--this.numAssetsLeft == 0)
            {
                super.loadAssets();
            }
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.dialogView = new HotelsGuestDialogView(param1, this.m_owner, this.m_upgradeCallback, m_message, m_title, m_type, m_callback, m_icon, m_iconPos, m_feedShareViralType, m_SkipCallback, m_customOk, m_relativeIcon);
            return this.dialogView;
        }//end

         protected Dictionary  createAssetDict ()
        {
            String _loc_4 =null ;
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
            _loc_1.put("hotels_dialogFrame",  this.getAsset("dialogFrame"));
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
            _loc_1.put("hotels_npc_visitor",  this.getAsset("npc_visitor"));
            _loc_1.put("hotels_penthouseStar",  this.getAsset("penthouseStar"));
            _loc_1.put("hotels_podium",  this.getAsset("podium"));
            _loc_1.put("hotels_previewBackground",  this.getAsset("previewBackground"));
            _loc_1.put("hotels_previewBoxes",  this.getAsset("previewBoxes"));
            _loc_1.put("hotels_previewBoxesGradient",  this.getAsset("previewBoxesGradient"));
            _loc_1.put("hotels_scrollAreaTop",  this.getAsset("scrollAreaTop"));
            _loc_1.put("hotels_scrollAreaTop2",  this.getAsset("scrollAreaTop2"));
            _loc_1.put("hotels_timerBackground",  this.getAsset("timerBackground"));
            _loc_1.put("hotels_topFloor",  this.getAsset("topFloor"));
            _loc_1.put("hotels_upgradeBackground",  this.getAsset("upgradeBackground"));
            _loc_1.put("hotels_welcomeSign",  this.getAsset("welcomeSign"));
            _loc_1.put("hotels_whiteTabLeft",  this.getAsset("whiteTabLeft"));
            _loc_1.put("hotels_whiteTabRight",  this.getAsset("whiteTabRight"));
            _loc_1.put("hotels_hotels_15",  this.getAsset("hotels_15"));
            if (this.m_owner.getItem().getUI_skin_param("bonus_image_override"))
            {
                _loc_1.put("hotels_bonusImage",  this.getAsset("bonusImage"));
            }
            _loc_2 = this.getAsset("dialogFrame");
            _loc_1.put("dialog_bg", (DisplayObject) new _loc_2);
            m_assetBG = _loc_1.get("dialog_bg");
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            _loc_1.put("payroll_checkin_confirmCheckmark",  _loc_3.payroll_checkin_confirmCheckmark);
            for(int i0 = 0; i0 < m_loadedAssets.size(); i0++)
            {
            		_loc_4 = m_loadedAssets.get(i0);

                _loc_1.put(_loc_4,  m_contentDictionary.get(_loc_4));
            }
            return _loc_1;
        }//end

         protected void  finalizeAndShow ()
        {
            this.dialogView.initGivenReward();
            super.finalizeAndShow();
            return;
        }//end

    }



