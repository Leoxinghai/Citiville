package Modules.storage.ui;

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
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.storage.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class StorageView extends UICatalog
    {
        protected ItemStorage m_storageItem ;
        public static Dictionary assetDict ;
        public static  int SCROLL_HEIGHT =550;

        public  StorageView (ItemStorage param1 ,String param2 ,String param3 ="")
        {
            StorageCatalogUI _loc_4 =new StorageCatalogUI ();
            this.m_storageItem = param1;
            super(_loc_4, new CatalogParams().setItemName(param3));
            return;
        }//end

        public ItemStorage  storageItem ()
        {
            return this.m_storageItem;
        }//end

        public StorageType  storageType ()
        {
            return this.m_storageItem.getStorageType();
        }//end

        public String  storageKey ()
        {
            return this.m_storageItem.getStorageKey();
        }//end

         protected void  loadAssets ()
        {
            m_content = m_ui;
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, this.makeAssets);
            Sprite _loc_1 =new Sprite ();
            this.addChild(_loc_1);
            JWindow _loc_2 =new JWindow(_loc_1 );
            _loc_2.setContentPane(m_ui);
            ASwingHelper.prepare(_loc_2);
            _loc_2.show();
            return;
        }//end

         protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            m_comObject = param1;
            this.setChrome(null);
            return;
        }//end

         protected int  getScrollHeight ()
        {
            return SCROLL_HEIGHT;
        }//end

        public void  refresh ()
        {
            asset.switchType(getStorageCatalogType(this.storageType, this.storageKey));
            return;
        }//end

         protected void  setChrome (Event event )
        {
            StorageView.assetDict = new Dictionary(true);
            StorageView.assetDict.put("btn_prev_up",  m_comObject.btn_prev_up);
            StorageView.assetDict.put("btn_prev_over",  m_comObject.btn_prev_over);
            StorageView.assetDict.put("btn_prev_down",  m_comObject.btn_prev_down);
            StorageView.assetDict.put("btn_next_up",  m_comObject.btn_next_up);
            StorageView.assetDict.put("btn_next_over",  m_comObject.btn_next_over);
            StorageView.assetDict.put("btn_next_down",  m_comObject.btn_next_down);
            StorageView.assetDict.put("btn_close_big_up",  m_comObject.btn_close_big_up);
            StorageView.assetDict.put("btn_close_big_over",  m_comObject.btn_close_big_over);
            StorageView.assetDict.put("btn_close_big_down",  m_comObject.btn_close_big_down);
            StorageView.assetDict.put("btn_close_small_up",  m_comObject.btn_close_small_up);
            StorageView.assetDict.put("btn_close_small_over",  m_comObject.btn_close_small_over);
            StorageView.assetDict.put("btn_close_small_down",  m_comObject.btn_close_small_down);
            StorageView.assetDict.put("verticalRule",  m_comObject.verticalRule);
            StorageView.assetDict.put("collectionFlyout",  m_comObject.collectionFlyout);
            StorageView.assetDict.put("collections_inventory_item",  m_comObject.inventoryCollectionsItem);
            StorageView.assetDict.put("collections_item_empty",  m_comObject.collectionsItemEmpty);
            StorageView.assetDict.put("wishlistItem",  m_comObject.wishlistItem);
            StorageView.assetDict.put("countBG",  m_comObject.countBG);
            StorageView.assetDict.put("countMaxBG",  m_comObject.countMaxBG);
            StorageView.assetDict.put("gift_down",  m_comObject.btn_gift_down);
            StorageView.assetDict.put("gift_over",  m_comObject.btn_gift_over);
            StorageView.assetDict.put("gift_up",  m_comObject.btn_gift_up);
            StorageView.assetDict.put("wishlist_icon",  m_comObject.wishlistIcon);
            StorageView.assetDict.put("inventory_card",  m_comObject.inventoryCard);
            StorageView.assetDict.put("inventory_card_locked",  m_comObject.inventoryCardLocked);
            StorageView.assetDict.put("add_to_wishlist",  m_comObject.addtoWishList);
            StorageView.assetDict.put("inventory_card_locked",  m_comObject.inventoryCardLocked);
            StorageView.assetDict.put("collectionsCard_ready",  m_comObject.collectionsCardReady);
            CatalogParams _loc_2 =new CatalogParams(getStorageCatalogType(this.storageType ,this.storageKey ));
            asset.init(this, _loc_2);
            m_cursorId = UI.setCursor(null);
            boolean _loc_3 =true ;
            this.mouseChildren = true;
            this.mouseEnabled = _loc_3;
            return;
        }//end

         public boolean  canBuy (Item param1 )
        {
            return true;
        }//end

         public void  onBuy (Item param1 ,Object param2 )
        {
            if (!param1)
            {
                return;
            }
            Market_loc_3 = event.build(MarketEvent.MARKET_BUY ,param1 ,MarketEvent.SOURCE_STORAGE );
            if (_loc_3)
            {
                doItemActions(param1, param2);
                dispatchEvent(_loc_3);
            }
            onCloseClick(true);
            return;
        }//end

        public static String  getStorageCatalogType (StorageType param1 ,String param2 )
        {
            return param1.type + "|" + param2;
        }//end

        public static StorageType  getStorageTypeFromCatalogType (String param1 )
        {
            return StorageType.createEnum(param1.split("|", 2).get(0));
        }//end

        public static String  getStorageKeyFromCatalogType (String param1 )
        {
            return param1.split("|", 2).get(1);
        }//end

    }



