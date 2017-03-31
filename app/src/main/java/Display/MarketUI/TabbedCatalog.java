package Display.MarketUI;

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

import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Events.*;
import Modules.automation.ui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class TabbedCatalog extends Catalog
    {
        public Array m_types ;
        public static Dictionary assetDict ;

        public  TabbedCatalog (ItemCatalogUI param1 ,Array param2 ,CatalogParams param3 )
        {
            m_assetDependenciesLoaded = 0;
            m_assetDependencies = new Dictionary();
            this.m_types = param2;
            super(param1, param3);
            return;
        }//end

         protected int  getScrollHeight ()
        {
            if (m_ui instanceof AutomationUI)
            {
                return ((AutomationUI)m_ui).getScrollHeight();
            }
            return Catalog.SCROLL_HEIGHT - Catalog.TAB_HEIGHTOFFFSET;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.TABBED_MARKET_ASSETS);
        }//end

         protected void  loadAssets ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.getAssetDependencies().size(); i0++)
            {
            	_loc_1 = this.getAssetDependencies().get(i0);

                Global.delayedAssets.get(_loc_1, this.makeAssets);
            }
            return;
        }//end

         protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            _loc_3 = this.getAssetDependencies ().length ;
            _loc_5 = m_assetDependenciesLoaded+1;
            m_assetDependenciesLoaded = _loc_5;
            m_assetDependencies.put(param2,  param1);
            if (_loc_3 == 1 || param2 == DelayedAssetLoader.MARKET_ASSETS)
            {
                m_comObject = param1;
            }
            if (m_assetDependenciesLoaded >= _loc_3)
            {
                this.setChrome(new Event("loaded"));
            }
            return;
        }//end

         protected void  setChrome (Event event )
        {
            TabbedCatalog.assetDict = new Dictionary(true);
            TabbedCatalog.assetDict.put("bg",  m_comObject.marketBG);
            TabbedCatalog.assetDict.put("btn_left_offstate",  m_comObject.market_prev_up);
            TabbedCatalog.assetDict.put("btn_left_onstate",  m_comObject.market_prev_over);
            TabbedCatalog.assetDict.put("btn_left_downstate",  m_comObject.market_prev_down);
            TabbedCatalog.assetDict.put("btn_right_offstate",  m_comObject.market_next_up);
            TabbedCatalog.assetDict.put("btn_right_onstate",  m_comObject.market_next_over);
            TabbedCatalog.assetDict.put("btn_right_downstate",  m_comObject.market_next_down);
            TabbedCatalog.assetDict.put("card_available_selected",  m_comObject.marketItem);
            TabbedCatalog.assetDict.put("card_limited_item",  m_comObject.limitedMarketItem);
            TabbedCatalog.assetDict.put("card_limited_counter",  m_comObject.limitedMarketItemCounter);
            TabbedCatalog.assetDict.put("card_available_unselected",  m_comObject.marketItem);
            TabbedCatalog.assetDict.put("card_locked",  m_comObject.marketItem_locked);
            TabbedCatalog.assetDict.put("icon_coins",  m_comObject.coin);
            TabbedCatalog.assetDict.put("icon_cash",  m_comObject.cash);
            TabbedCatalog.assetDict.put("icon_goods",  m_comObject.goods);
            TabbedCatalog.assetDict.put("sale_tag",  m_comObject.market_saletag);
            TabbedCatalog.assetDict.put("pop_lock",  m_comObject.lockedArea);
            TabbedCatalog.assetDict.put("pop_neighborlock",  m_comObject.lockedNeighborArea);
            TabbedCatalog.assetDict.put("saleSticker",  m_comObject.franchise_saleSticker);
            TabbedCatalog.assetDict.put("selectedTab",  m_comObject.selectedTab);
            _loc_2 = DelayedAssetLoader.TABBED_MARKET_ASSETS,.TABBED_MARKET_ASSETS]);
            TabbedCatalog.assetDict.put("tab_bg",  _loc_2.factories2_0_bg);
            TabbedCatalog.assetDict.put("card_locked",  _loc_2.factories2_0_card_locked);
            TabbedCatalog.assetDict.put("tab_active",  _loc_2.factories2_0_tab_active);
            TabbedCatalog.assetDict.put("tab_inactive",  _loc_2.factories2_0_tab_inactive);
            TabbedCatalog.assetDict.put("tab_locked",  _loc_2.factories2_0_tab_locked);
            m_content = m_ui;
            CatalogParams _loc_3 =new CatalogParams(m_type );
            _loc_3.itemName = m_itemName;
            _loc_3.exclusiveCategory = m_exclusive;
            _loc_3.overrideTitle = m_overrideTitle;
            asset.init(this, _loc_3);
            m_holder = new Sprite();
            this.addChild(m_holder);
            JWindow _loc_4 =new JWindow(m_holder );
            _loc_4.setContentPane(m_ui);
            ASwingHelper.prepare(_loc_4);
            _loc_4.show();
            m_content.addEventListener(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, showCatToolTip, false, 0, true);
            m_content.addEventListener("hidetooltip", hideCatToolTip, false, 0, true);
            m_categoryTooltip = new ItemCategoryToolTip();
            m_tooltip = new ItemCatalogToolTip();
            m_tooltipHolder = new Sprite();
            this.addChild(m_tooltipHolder);

            m_tooltipHolder.mouseEnabled = false;
            m_tooltipHolder.mouseChildren = false;
            m_tooltipHolder.y = -80;
            jtw = new JWindow(m_tooltipHolder);
            jtw.setContentPane(m_tooltip);
            ASwingHelper.prepare(jtw);
            m_cursorId = UI.setCursor(null);

            this.mouseChildren = false;
            this.mouseEnabled = false;
            if (m_itemName != null)
            {
                asset.goToItem(m_itemName);
            }
            return;
        }//end

         public Array  categoryNames ()
        {
            return this.m_types;
        }//end

    }



