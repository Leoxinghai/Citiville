package Display.InventoryUI;

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
import Engine.*;
import Engine.Helpers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class InventoryView extends UICatalog
    {
        protected boolean m_forceStayOpen =false ;
        protected boolean m_refreshEntireInventory =false ;
        public static Dictionary assetDict ;
        public static  int SCROLL_HEIGHT =550;

        public  InventoryView (String param1)
        {
            InventoryCatalogUI _loc_2 =new InventoryCatalogUI ();
            super(_loc_2, new CatalogParams().setItemName(param1));
            return;
        }//end

        public boolean  forceStayOpen ()
        {
            return this.m_forceStayOpen;
        }//end

        public boolean  doRefreshInventory ()
        {
            return this.m_refreshEntireInventory;
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

         protected void  setChrome (Event event )
        {
            InventoryView.assetDict = new Dictionary(true);
            InventoryView.assetDict.put("btn_prev_up",  m_comObject.btn_prev_up);
            InventoryView.assetDict.put("btn_prev_over",  m_comObject.btn_prev_over);
            InventoryView.assetDict.put("btn_prev_down",  m_comObject.btn_prev_down);
            InventoryView.assetDict.put("btn_next_up",  m_comObject.btn_next_up);
            InventoryView.assetDict.put("btn_next_over",  m_comObject.btn_next_over);
            InventoryView.assetDict.put("btn_next_down",  m_comObject.btn_next_down);
            InventoryView.assetDict.put("btn_close_big_up",  m_comObject.btn_close_big_up);
            InventoryView.assetDict.put("btn_close_big_over",  m_comObject.btn_close_big_over);
            InventoryView.assetDict.put("btn_close_big_down",  m_comObject.btn_close_big_down);
            InventoryView.assetDict.put("btn_close_small_up",  m_comObject.btn_close_small_up);
            InventoryView.assetDict.put("btn_close_small_over",  m_comObject.btn_close_small_over);
            InventoryView.assetDict.put("btn_close_small_down",  m_comObject.btn_close_small_down);
            InventoryView.assetDict.put("verticalRule",  m_comObject.verticalRule);
            InventoryView.assetDict.put("collectionFlyout",  m_comObject.collectionFlyout);
            InventoryView.assetDict.put("collections_inventory_item",  m_comObject.inventoryCollectionsItem);
            InventoryView.assetDict.put("collections_item_empty",  m_comObject.collectionsItemEmpty);
            InventoryView.assetDict.put("wishlistItem",  m_comObject.wishlistItem);
            InventoryView.assetDict.put("countBG",  m_comObject.countBG);
            InventoryView.assetDict.put("countMaxBG",  m_comObject.countMaxBG);
            InventoryView.assetDict.put("gift_down",  m_comObject.btn_gift_down);
            InventoryView.assetDict.put("gift_over",  m_comObject.btn_gift_over);
            InventoryView.assetDict.put("gift_up",  m_comObject.btn_gift_up);
            InventoryView.assetDict.put("wishlist_icon",  m_comObject.wishlistIcon);
            InventoryView.assetDict.put("inventory_card",  m_comObject.inventoryCard);
            InventoryView.assetDict.put("add_to_wishlist",  m_comObject.addtoWishList);
            InventoryView.assetDict.put("inventory_card_locked",  m_comObject.inventoryCardLocked);
            InventoryView.assetDict.put("collectionsCard_ready",  m_comObject.collectionsCardReady);
            asset.init(this, new CatalogParams(m_type));
            m_cursorId = UI.setCursor(null);
            boolean _loc_2 =true ;
            this.mouseChildren = true;
            this.mouseEnabled = _loc_2;
            if (m_itemName != null)
            {
                asset.goToItem(m_itemName);
            }
            return;
        }//end

         public boolean  canBuy (Item param1 )
        {
            return true;
        }//end

         public void  close ()
        {
            super.close();
            return;
        }//end

        public void  allowClose ()
        {
            this.m_forceStayOpen = false;
            return;
        }//end

         public void  onBuy (Item param1 ,Object param2 )
        {
            Array _loc_5 =null ;
            int _loc_6 =0;
            Item _loc_7 =null ;
            this.m_forceStayOpen = false;
            if (!param1)
            {
                return;
            }
            Market_loc_3 = event.GENERIC ;
            boolean _loc_4 =true ;
            switch(param1.type)
            {
                case "tool":
                {
                    _loc_3 = MarketEvent.EQUIPMENT;
                    break;
                }
                case "contract":
                {
                    _loc_3 = MarketEvent.CONTRACT;
                    break;
                }
                case "residence":
                {
                    _loc_3 = MarketEvent.RESIDENCE;
                    break;
                }
                case "business":
                {
                    _loc_3 = MarketEvent.BUSINESS;
                    break;
                }
                case "plot_contract":
                {
                    _loc_3 = MarketEvent.CONTRACT;
                    break;
                }
                case "ship_contract":
                {
                    _loc_3 = MarketEvent.CONTRACT;
                    break;
                }
                case "expansion":
                {
                    _loc_3 = MarketEvent.EXPAND_FARM;
                    break;
                }
                case "bridge":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.BRIDGE, param1.name, false, false, MarketEvent.SOURCE_INVENTORY));
                    break;
                }
                case "energy":
                {
                    _loc_5 = Global.player.inventory.getItems();
                    _loc_6 = 0;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_7 = _loc_5.get(i0);

                        if (_loc_7 && _loc_7.energyRewards > 0 && Global.player.inventory.getItemCount(_loc_7) > 0)
                        {
                            _loc_6 = _loc_6 + Global.player.inventory.getItemCount(_loc_7);
                        }
                    }
                    if (_loc_6 <= 1)
                    {
                        this.m_refreshEntireInventory = true;
                    }
                    this.m_forceStayOpen = true;
                    if (Global.player.inventory.getItemCount(param1) > 0)
                    {
                        _loc_3 = MarketEvent.EXTRA;
                    }
                    else
                    {
                        _loc_4 = false;
                    }
                    break;
                }
                case "gas":
                {
                    _loc_3 = MarketEvent.GAS;
                    break;
                }
                case "theme_collection":
                {
                    _loc_3 = MarketEvent.THEME_COLLECTION;
                    break;
                }
                case "goods":
                {
                    _loc_3 = MarketEvent.GOODS;
                    break;
                }
                case "hotel":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.HOTEL, param1.name, false, false, MarketEvent.SOURCE_INVENTORY));
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_4)
            {
                dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, _loc_3, param1.name, false, false, MarketEvent.SOURCE_INVENTORY));
            }
            if (!this.m_forceStayOpen)
            {
                if (m_closeMarket == true || UI.AUTO_CLOSE_MARKET)
                {
                    onCloseClick(true);
                }
            }
            return;
        }//end

         public Vector2  getExpandedMainMenuPosition ()
        {
            return new Vector2(665, 120);
        }//end

         protected int  getScrollHeight ()
        {
            if (Utilities.isFullScreen())
            {
                return Global.ui.screenHeight / 2 + m_ui.height / 2;
            }
            return SCROLL_HEIGHT;
        }//end

         public void  show ()
        {
            super.show();
            this.refreshInventory();
            return;
        }//end

        public void  refreshInventory ()
        {
            this.asset.refreshShelf();
            return;
        }//end

        public void  refreshInventoryLimit ()
        {
            (this.asset as InventoryCatalogUI).refreshInventoryLimit();
            return;
        }//end

        public static void  setupAssets (DisplayObject param1 ,String param2 )
        {
            InventoryView _loc_3 =new InventoryView ;
            _loc_3.makeAssets(param1, param2);
            return;
        }//end

    }



