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

import Classes.*;
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.guide.ui.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class Catalog extends Sprite
    {
        protected String m_type ;
        private String m_subType ;
        protected Array m_customItems ;
        protected JPanel m_content =null ;
        protected boolean m_shown =false ;
        protected int m_assetDependenciesLoaded ;
        protected Dictionary m_assetDependencies ;
        protected String m_itemName ;
        protected int m_cursorId =0;
        protected JWindow jtw ;
        protected ItemCatalogToolTip m_tooltip ;
        protected JWindow jctw ;
        protected ItemCategoryToolTip m_categoryTooltip ;
        protected ItemCatalogUI m_ui ;
        protected Sprite m_holder ;
        protected boolean m_exclusive ;
        protected boolean m_closeMarket ;
        protected Object m_comObject ;
        protected String m_overrideTitle ;
        protected Sprite m_cattooltipHolder ;
        protected Sprite m_tooltipHolder ;
        protected GuideArrow m_arrow ;
        private boolean m_ignoreExcludeExperiments ;
        private double m_firstDataMultiplier =1;
        public static Dictionary assetDict ;
        public static  int TOOLTIP_WIDTH =177;
        public static  int CARD_WIDTH =88;
        public static  int CARD_HEIGHT =94;
        public static  int CARD_OFFSET =2;
        public static  int SCROLL_WIDTH =750;
        public static  int SCROLL_HEIGHT =250;
        public static  int TAB_HEIGHTOFFFSET =17;
        public static  int CATALOG_TWEEN_OFFSET =25;
        public static  int TOOLTIP_HOLDER_OFFSET_Y =-110;

        public  Catalog (ItemCatalogUI param1 ,CatalogParams param2 )
        {
            this.m_ui = param1;
            this.m_closeMarket = param2.closeMarket;
            this.m_overrideTitle = param2.overrideTitle;
            this.m_type = param2.type;
            this.m_subType = param2.subType;
            this.m_customItems = param2.customItems;
            this.m_itemName = param2.itemName;
            this.m_exclusive = param2.exclusiveCategory;
            this.m_ignoreExcludeExperiments = param2.ignoreExcludeExperiments;
            this.m_assetDependencies = new Dictionary(true);
            this.m_assetDependenciesLoaded = 0;
            this.loadAssets();
            return;
        }//end

        protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS);
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
            this.m_assetDependenciesLoaded++;
            this.m_assetDependencies.put(param2,  param1);
            if (_loc_3 == 1 || param2 == DelayedAssetLoader.GENERIC_DIALOG_ASSETS)
            {
                this.m_comObject = param1;
            }
            if (this.m_assetDependenciesLoaded >= _loc_3)
            {
                this.setChrome(new Event("loaded"));
            }
            return;
        }//end

        protected void  setChrome (Event event )
        {
            buildDictionary(this.m_assetDependencies.get(DelayedAssetLoader.MARKET_ASSETS));
            Catalog.assetDict.put("btn_up_normal",  this.m_comObject.gridlist_nav_up_normal);
            Catalog.assetDict.put("btn_up_over",  this.m_comObject.gridlist_nav_up_over);
            Catalog.assetDict.put("btn_up_down",  this.m_comObject.gridlist_nav_up_down);
            Catalog.assetDict.put("btn_down_normal",  this.m_comObject.gridlist_nav_down_normal);
            Catalog.assetDict.put("btn_down_over",  this.m_comObject.gridlist_nav_down_over);
            Catalog.assetDict.put("btn_down_down",  this.m_comObject.gridlist_nav_down_down);
            Catalog.assetDict.put("pic_checkGreen",  this.m_comObject.checkmark_green);
            Catalog.assetDict.put("pic_dot",  this.m_comObject.gridList_page_dot);
            Catalog.assetDict.put("dialog_div",  this.m_comObject.dialog_rename_divider);
            Catalog.assetDict.put("vertical_scrollBar_border",  this.m_comObject.vertical_scrollBar_border);
            this.m_content = this.m_ui;
            CatalogParams _loc_2 =new CatalogParams(this.m_type );
            _loc_2.subType = this.m_subType;
            _loc_2.itemName = this.m_itemName;
            _loc_2.exclusiveCategory = this.m_exclusive;
            _loc_2.overrideTitle = this.m_overrideTitle;
            _loc_2.customItems = this.m_customItems;
            _loc_2.ignoreExcludeExperiments = this.m_ignoreExcludeExperiments;
            this.asset.init(this, _loc_2);
            this.m_holder = new Sprite();
            this.addChild(this.m_holder);
            JWindow _loc_3 =new JWindow(this.m_holder );
            _loc_3.setContentPane(this.m_ui);
            ASwingHelper.prepare(_loc_3);
            _loc_3.show();
            this.m_content.addEventListener(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, this.showCatToolTip, false, 0, true);
            this.m_content.addEventListener("hidetooltip", this.hideCatToolTip, false, 0, true);
            this.setupToolTip();
            this.m_cursorId = UI.setCursor(null);
            boolean _loc_4 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_4;
            if (this.m_itemName != null)
            {
                this.asset.goToItem(this.m_itemName);
            }
            return;
        }//end

        protected void  setupToolTip ()
        {
            this.m_categoryTooltip = new ItemCategoryToolTip();
            this.m_tooltip = new ItemCatalogToolTip();
            this.m_tooltipHolder = new Sprite();
            this.addChild(this.m_tooltipHolder);
            boolean _loc_1 =false ;
            this.m_tooltipHolder.mouseEnabled = false;
            this.m_tooltipHolder.mouseChildren = _loc_1;
            this.m_tooltipHolder.y = -80;
            this.jtw = new JWindow(this.m_tooltipHolder);
            this.jtw.setContentPane(this.m_tooltip);
            ASwingHelper.prepare(this.jtw);
            return;
        }//end

        public Array  categoryNames ()
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_1 =new Array ();
            _loc_2 =Global.gameSettings().getMenuItems ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.@type;
                _loc_1.push(_loc_4);
            }
            return _loc_1;
        }//end

        public String  type ()
        {
            return this.asset.type;
        }//end

        public String  subType ()
        {
            return this.asset.subType;
        }//end

        public void  type (String param1 )
        {
            this.asset.switchType(param1);
            return;
        }//end

        public void  subType (String param1 )
        {
            this.asset.switchSubType(param1);
            return;
        }//end

        public void  items (Array param1 )
        {
            this.m_customItems = param1;
            this.asset.switchItems(param1, this.type);
            return;
        }//end

        public void  ignoreExcludeExperiments (boolean param1 )
        {
            this.m_ignoreExcludeExperiments = param1;
            this.asset.setIgnoreExcludeExperiments(param1);
            return;
        }//end

        public Array  items ()
        {
            return this.asset.items;
        }//end

        public void  item (String param1 )
        {
            this.asset.goToItem(param1);
            return;
        }//end

        protected int  getScrollHeight ()
        {
            return Catalog.SCROLL_HEIGHT;
        }//end

        protected void  onFullScreen (FullScreenEvent event =null )
        {
            Rectangle _loc_2 =null ;
            Point _loc_3 =null ;
            if (parent)
            {
                _loc_2 = Global.hud.getRect(parent);
                _loc_3 = new Point(4, Global.ui.screenHeight + CATALOG_TWEEN_OFFSET - this.getScrollHeight());
                this.x = _loc_3.x;
                this.y = _loc_2.y + _loc_3.y;
            }
            return;
        }//end

        protected void  onResize (Event event )
        {
            this.onFullScreen();
            return;
        }//end

        protected void  reparentTooltipHolder (boolean param1 )
        {
            Point _loc_2 =null ;
            DisplayObjectContainer _loc_3 =null ;
            if (param1 && this.m_tooltipHolder.parent != Global.stage)
            {
                _loc_2 = this.localToGlobal(new Point(this.m_tooltipHolder.x, this.m_tooltipHolder.y));
                _loc_2.y = Global.ui.screenHeight + CATALOG_TWEEN_OFFSET - this.getScrollHeight() + TOOLTIP_HOLDER_OFFSET_Y;
                _loc_3 = Global.stage;
            }
            else if (!param1 && this.m_tooltipHolder.parent == Global.stage)
            {
                _loc_2 = new Point(0, TOOLTIP_HOLDER_OFFSET_Y);
                _loc_3 = this;
            }
            if (_loc_3 != null)
            {
                this.m_tooltipHolder.parent.removeChild(this.m_tooltipHolder);
                _loc_3.addChild(this.m_tooltipHolder);
                this.m_tooltipHolder.x = _loc_2.x;
                this.m_tooltipHolder.y = _loc_2.y;
            }
            return;
        }//end

        protected void  showCatToolTip (GenericObjectEvent event )
        {
            _loc_2 =Global(.ui.screenWidth -SCROLL_WIDTH )/2;
            _loc_3 = event.obj.get( "xOffset") != null ? (event.obj.get("xOffset")) : (0);
            _loc_4 = event.obj.get( "yOffset") != null ? (event.obj.get("yOffset")) : (0);
            this.m_tooltipHolder.addChild(this.m_categoryTooltip);
            this.m_categoryTooltip.changeInfo(event.obj.get("category"));
            this.m_categoryTooltip.x = event.obj.get("posX") - this.m_categoryTooltip.width * 0.5 - _loc_2 + 30 + _loc_3;
            if (this.m_categoryTooltip.x < 17)
            {
                this.m_categoryTooltip.x = event.obj.get("posX") - this.m_categoryTooltip.width * 0.4 - _loc_2 + 30 + _loc_3;
            }
            this.m_categoryTooltip.y = 120 - this.m_categoryTooltip.height + _loc_4;
            Sounds.play("UI_mouseover");
            return;
        }//end

        protected void  showToolTip (DataItemEvent event )
        {
            this.reparentTooltipHolder(Global.guide.isActive());
            _loc_2 = event.item ;
            _loc_3 = event.getParams ();
            if (_loc_2 && _loc_2.type == "ship_contract")
            {
                if (!_loc_3)
                {
                    _loc_3 = {};
                }
                _loc_3.firstDataMultiplier = this.m_firstDataMultiplier;
            }
            this.m_tooltip.changeInfo(_loc_2, _loc_3);
            ASwingHelper.prepare(this.m_tooltip);
            this.jtw.show();
            Point _loc_4 =new Point ();
            _loc_5 = event.getOffset();
            if (event.getOffset())
            {
                _loc_4.x = _loc_4.x + _loc_5.x;
                _loc_4.y = _loc_4.y + _loc_5.y;
            }
            double _loc_6 =0;
            if (UI.m_catalog && UI.m_catalog instanceof Catalog)
            {
                _loc_6 = _loc_6 + UI.m_catalog.height;
            }
            _loc_7 =Global(.ui.screenWidth -SCROLL_WIDTH )/2;
            _loc_8 =Global.ui.screenHeight -this.height ;
            this.jtw.setX(Math.min(event.pt.x + CARD_WIDTH / 2 - this.m_tooltip.getWidth() / 2 + CARD_OFFSET - _loc_7 + _loc_4.x, Global.ui.screenWidth - this.m_tooltip.getWidth() - CARD_OFFSET - 10));
            _loc_9 = this.globalToLocal(event.pt );
            this.jtw.setY(_loc_9.y - this.m_tooltip.getHeight() + 82);
            Sounds.play("UI_mouseover");
            return;
        }//end

        protected void  hideCatToolTip (Event event )
        {
            if (this.m_categoryTooltip.parent)
            {
                this.m_categoryTooltip.parent.removeChild(this.m_categoryTooltip);
            }
            return;
        }//end

        protected void  hideToolTip (Event event =null )
        {
            this.jtw.hide();
            return;
        }//end

        protected void  onShow ()
        {
            if (this.asset.type == "expansion")
            {
                this.asset.switchType("expansion");
            }
            this.m_cursorId = UI.setCursor(null);
            if (this.m_ui != null)
            {
                this.m_ui.addEventListener(DataItemEvent.SHOW_TOOLTIP, this.showToolTip, false, 0, true);
                this.m_ui.addEventListener("turnOffToolTip", this.hideToolTip, false, 0, true);
            }
            Global.stage.addEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
            Global.stage.addEventListener(Event.RESIZE, this.onResize);
            this.m_shown = true;
            Sounds.play("dialogSpawn");
            Global.ui.mouseEnabled = true;
            Global.ui.mouseChildren = true;
            UI.popupLock();
            return;
        }//end

        public void  centerPopup ()
        {
            Rectangle _loc_1 =null ;
            Point _loc_2 =null ;
            if (parent)
            {
                _loc_1 = Global.hud.getRect(parent);
                _loc_2 = new Point(4, Global.ui.screenHeight);
                this.x = _loc_2.x;
                this.y = _loc_1.y + _loc_2.y;
            }
            return;
        }//end

        protected int  getYPos ()
        {
            _loc_1 =Global.hud.getRect(parent );
            _loc_2 = _loc_1.y +Global.ui.screenHeight +CATALOG_TWEEN_OFFSET -this.getScrollHeight ();
            return _loc_2;
        }//end

        protected void  showTween ()
        {
            Sprite me ;
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            this.visible = true;
            this.centerPopup();
            newY = this.getYPos();
            me = new Sprite();
void             TweenLite .to (this ,0.5,{newY y , onComplete ()
            {
                boolean _loc_1 =true ;
                me.mouseEnabled = true;
                me.mouseChildren = _loc_1;
                return;
            }//end
            });
            this.asset.onTweenIn();
            return;
        }//end

        protected void  hideTween (Function param1 )
        {
            Rectangle _loc_2 =null ;
            int _loc_3 =0;
            boolean _loc_4 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            this.asset.onTweenOut();
            if (this.m_content == null)
            {
                if (param1 != null)
                {
                    param1();
                }
            }
            else
            {
                _loc_2 = Global.hud.getRect(parent);
                _loc_3 = _loc_2.y + Global.ui.screenHeight;
                TweenLite.to(this, 0.5, {y:_loc_3, onComplete:param1});
            }
            Sounds.play("dialogClose");
            return;
        }//end

        public ICatalogUI  asset ()
        {
            return this.m_content as ICatalogUI;
        }//end

        public void  closeMarket (boolean param1 )
        {
            this.m_closeMarket = param1;
            return;
        }//end

        public void  overrideTitle (String param1 )
        {
            this.asset.overrideTitle = param1;
            return;
        }//end

        public void  exclusiveCategory (boolean param1 )
        {
            this.asset.exclusive = param1;
            return;
        }//end

        public boolean  exclusiveCategory ()
        {
            return this.asset.exclusive;
        }//end

        public boolean  allowsExclusivity (String param1 )
        {
            return param1 == "plot_contract" || param1 == "ship_contract" || param1 == "factory_contract" || param1 == "train" || param1 == "energy" || param1.indexOf("skins") != -1;
        }//end

        protected void  onHide ()
        {
            if (this.m_shown == true)
            {
                this.m_shown = false;
                boolean _loc_1 =false ;
                this.mouseChildren = false;
                this.mouseEnabled = false;
                Global.stage.removeEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
                Global.stage.removeEventListener(Event.RESIZE, this.onResize);
                this.m_ui.removeEventListener(DataItemEvent.SHOW_TOOLTIP, this.showToolTip);
                this.m_ui.removeEventListener("turnOffToolTip", this.hideToolTip);
            }
            if (this.m_tooltip)
            {
                this.hideToolTip();
            }
            UI.popupUnlock();
            return;
        }//end

        public boolean  canBuy (Item param1 )
        {
            int _loc_3 =0;
            String _loc_4 =null ;
            GenericDialog _loc_5 =null ;
            boolean _loc_2 =true ;

	    return _loc_2;

            if (!Global.franchiseManager.isFranchiseItem(param1))
            {
                _loc_2 = Global.player.checkGate(param1.unlock, param1.name);
            }
            if (_loc_2 && param1.buildingCap > 0)
            {
                _loc_3 = Global.world.getAllCountByName(param1.name);
                _loc_2 = param1.buildingCap > _loc_3;
                if (!_loc_2)
                {
                    _loc_4 = ZLoc.t("Dialogs", "BuildingCapReached");
                    _loc_5 = new CharacterResponseDialog(_loc_4, "RollCall_callFail", 0, null, "BuildingCap", "assets/dialogs/citysam_ftue_shh.png", true, 0, "", null, "", true);
                    UI.displayPopup(_loc_5);
                }
            }

            Debug.debug6("Catalog.canBuy."+param1.name+";"+_loc_2);


            return _loc_2;
        }//end

        public void  onBuy (Item param1 ,Object param2 )
        {
            boolean _loc_8 =false ;
            MarketCell _loc_9 =null ;
            MarketCell _loc_10 =null ;
            MarketCell _loc_11 =null ;
            if (!param1)
            {
                return;
            }
            Global.marketSessionTracker.trackItem();
            boolean _loc_3 =false ;
            if (param1.goods > 0)
            {
                _loc_3 = Global.player.canBuyCommoditiy(param1.GetItemSalePrice(), Commodities.GOODS_COMMODITY, false);
                if (!_loc_3)
                {
                    UI.displayOutOfGoodsDialog();
                    return;
                }
            }
            else if (param1.cash > 0)
            {
                _loc_3 = Global.player.canBuyCash(param1.GetItemSalePrice(), false);
            }
            else if (param1.fbc > 0)
            {
                _loc_3 = true;
            }
            else
            {
                _loc_3 = Global.player.canBuy(param1.GetItemSalePrice(), true);
            }

			//add by xinghai
			_loc_3 = true;

            if (!_loc_3)
            {
                if (param1.GetItemSalePrice() > 0)
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH, null, param1);
                    if (Global.world.getTopGameMode() instanceof GMPlant)
                    {
                        Global.world.addGameMode(new GMPlay());
                    }
                    StatsManager.count("fv_cash_buy_fail", "view_popup");
                }
                return;
            }
            _loc_4 = MarketEvent.GENERIC;
            String _loc_5 ="";
            switch(param1.type)
            {
                case "tool":
                {
                    _loc_4 = MarketEvent.EQUIPMENT;
                    break;
                }
                case "contract":
                {
                    _loc_4 = MarketEvent.CONTRACT;
                    break;
                }
                case "goods":
                {
                    _loc_4 = MarketEvent.GOODS;
                    break;
                }
                case "residence":
                {
                    _loc_4 = MarketEvent.RESIDENCE;
                    break;
                }
                case "business":
                {
                    _loc_4 = MarketEvent.BUSINESS;
                    _loc_8 = param1.className == "LotSite";
                    if (_loc_8)
                    {
                        if (param2 instanceof MarketCell)
                        {
                            _loc_9 = MarketCell(param2);
                            MarketCell.addLockCell(_loc_9);
                        }
                    }
                    break;
                }
                case "starter_pack2_1":
                case "starter_pack2_2":
                case "starter_pack2_3":
                case "starter_pack2_4":
                {
                    _loc_4 = MarketEvent.FBC_PURCHASE;
                    _loc_5 = this.asset.type;
                    break;
                }
                case "plot_contract":
                case "factory_contract":
                {
                    _loc_4 = MarketEvent.CONTRACT;
                    if (Plot.IsMarketClickedPlotSet)
                    {
                        Plot.SetContactToMarketClickedPlot(param1.name);
                    }
                    break;
                }
                case "ship_contract":
                {
                    _loc_4 = MarketEvent.CONTRACT;
                    if (Ship.IsMarketClickedShipSet)
                    {
                        Ship.SetContactToMarketClickedShip(param1.name);
                    }
                    break;
                }
                case "expansion":
                {
                    _loc_4 = MarketEvent.EXPAND_FARM;
                    if (param2 instanceof MarketCell)
                    {
                        _loc_10 = MarketCell(param2);
                        MarketCell.addLockCell(_loc_10);
                    }
                    break;
                }
                case "permit_pack2":
                case "permit_pack3":
                case "permit_pack4":
                {
                    _loc_4 = MarketEvent.PERMIT_BUNDLE;
                    if (param2 instanceof MarketCell)
                    {
                        _loc_11 = MarketCell(param2);
                        MarketCell.addLockCell(_loc_11);
                    }
                    break;
                }
                case "genericBundle":
                {
                    _loc_4 = MarketEvent.GENERIC_BUNDLE;
                    break;
                }
                case "energy":
                {
                    _loc_4 = MarketEvent.EXTRA;
                    break;
                }
                case "gas":
                {
                    _loc_4 = MarketEvent.GAS;
                }
                case "wonder":
                {
                    _loc_4 = MarketEvent.WONDER;
                    break;
                }
                case "vehicle":
                {
                    _loc_4 = MarketEvent.VEHICLE;
                    break;
                }
                case "theme_collection":
                {
                    _loc_4 = MarketEvent.THEME_COLLECTION;
                    break;
                }
                case "train":
                {
                    _loc_4 = MarketEvent.TRAIN;
                    break;
                }
                case "mystery_crate":
                {
                    _loc_4 = MarketEvent.MYSTERY_CRATE;
                    break;
                }
                case "neighborhood":
                {
                    _loc_4 = MarketEvent.NEIGHBORHOOD;
                    break;
                }
                case "themed_bundle":
                {
                    _loc_4 = MarketEvent.THEMED_BUNDLE;
                    break;
                }
                case "play":
                {
                    _loc_4 = MarketEvent.PLAY;
                    Global.ui.startPickThings(param1.minigame);
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (param1.isRemodelSkin() && param2)
            {
                if (param2.hasOwnProperty("remodel") && param2.remodel == "skin")
                {
                    _loc_4 = MarketEvent.ITEM_SKIN;
                }
                else if (param2.hasOwnProperty("remodel") && param2.remodel == "combined")
                {
                    _loc_4 = MarketEvent.ITEM_SKIN_COMBINED;
                }
            }
            _loc_6 =Global.world.getTopGameMode ();
            _loc_7 =Global.world.getTopGameMode ().getCustomMarketEvent(param1 );
            if (Global.world.getTopGameMode().getCustomMarketEvent(param1) > 0)
            {
                _loc_4 = _loc_7;
            }
            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, _loc_4, param1.name, false, false, _loc_5));
            if (this.m_closeMarket == true || UI.AUTO_CLOSE_MARKET)
            {
                this.onCloseClick(true);
            }
            return;
        }//end

        public void  show ()
        {
            this.showHelper();
            return;
        }//end

        protected void  showHelper ()
        {
            this.showTween();
            this.onShow();
            return;
        }//end

        protected void  onCloseClick (boolean param1 )
        {
            this.close();
            Plot.ResetMarketClickedPlotSet();
            Ship.ResetMarketClickedShipSet();
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE, this.m_closeMarket));
            return;
        }//end

        public void  close ()
        {
            DisplayObject dispObj ;
            Global.ui.swapPopupAd("genericAd", false);
            UI.showNeighbors();
            dispObj = (DisplayObject)this; // new DisplayObject();

            this .hideTween (void  ()
            {
                if (dispObj.parent)
                {
                    dispObj.parent.removeChild(dispObj);
                }
                dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
                return;
            }//end
            );
            this.onHide();
            return;
        }//end

        public void  updateCoinsCash (int param1 ,int param2 )
        {
            return;
        }//end

        public void  updateChangedCells ()
        {
            this.asset.updateElements();
            return;
        }//end

        public void  setFirstDataMultiplier (double param1 )
        {
            this.m_firstDataMultiplier = param1;
            return;
        }//end

        public void  updatePriceMultiplier (double param1 )
        {
            this.asset.updatePriceMultiplier(param1);
            return;
        }//end

        public void  updateCellsByItemNames (Array param1 )
        {
            this.asset.updateElementsByItemNames(param1);
            return;
        }//end

        public Vector2  getExpandedMainMenuPosition ()
        {
            return new Vector2(632, 80);
        }//end

        public boolean  isShown ()
        {
            return this.m_shown;
        }//end

        public void  showArrow ()
        {
            _loc_1 = this.asset.shelf.dataList.getCellByIndex(1).getCellComponent ();
            _loc_2 = _loc_1.getGlobalLocation ();
            this.m_arrow = new GuideArrow(Global.getAssetURL("assets/hud/tutorialArrow.swf"), 175, 400, 100, 100, GuideArrow.ARROW_DOWN);
            this.addEventListener(MouseEvent.CLICK, this.killArrow, false, 0, true);
            return;
        }//end

        protected void  killArrow (MouseEvent event )
        {
            this.removeEventListener(MouseEvent.CLICK, this.killArrow);
            if (this.m_arrow)
            {
                this.m_arrow.release();
            }
            return;
        }//end

        protected void  doItemActions (Item param1 ,Object param2 )
        {
            boolean _loc_3 =false ;
            MarketCell _loc_4 =null ;
            MarketCell _loc_5 =null ;
            switch(param1.type)
            {
                case "business":
                {
                    _loc_3 = param1.className == "LotSite";
                    if (_loc_3)
                    {
                        if (param2 instanceof MarketCell)
                        {
                            _loc_4 = MarketCell(param2);
                            MarketCell.addLockCell(_loc_4);
                        }
                    }
                    break;
                }
                case "plot_contract":
                case "factory_contract":
                {
                    if (Plot.IsMarketClickedPlotSet)
                    {
                        Plot.SetContactToMarketClickedPlot(param1.name);
                    }
                    break;
                }
                case "ship_contract":
                {
                    if (Ship.IsMarketClickedShipSet)
                    {
                        Ship.SetContactToMarketClickedShip(param1.name);
                    }
                    break;
                }
                case "expansion":
                {
                    if (param2 instanceof MarketCell)
                    {
                        _loc_5 = MarketCell(param2);
                        MarketCell.addLockCell(_loc_5);
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public static void  setupAssets (DisplayObject param1 ,String param2 )
        {
            buildDictionary(param1);
            return;
        }//end


        public static void  buildDictionary (Object param1)
        {
            Catalog.assetDict = new Dictionary(true);
            Catalog.assetDict.put("bg",  param1.marketBG);
            Catalog.assetDict.put("btn_left_offstate",  param1.market_prev_up);
            Catalog.assetDict.put("btn_left_onstate",  param1.market_prev_over);
            Catalog.assetDict.put("btn_left_downstate",  param1.market_prev_down);
            Catalog.assetDict.put("btn_right_offstate",  param1.market_next_up);
            Catalog.assetDict.put("btn_right_onstate",  param1.market_next_over);
            Catalog.assetDict.put("btn_right_downstate",  param1.market_next_down);
            Catalog.assetDict.put("card_available_selected",  param1.marketItem);
            Catalog.assetDict.put("card_limited_item",  param1.limitedMarketItem);
            Catalog.assetDict.put("card_limited_counter",  param1.limitedMarketItemCounter);
            Catalog.assetDict.put("card_available_unselected",  param1.marketItem);
            Catalog.assetDict.put("card_locked",  param1.marketItem_locked);
            Catalog.assetDict.put("icon_coins",  param1.coin);
            Catalog.assetDict.put("icon_cash",  param1.cash);
            Catalog.assetDict.put("icon_goods",  param1.goods);
            Catalog.assetDict.put("sale_tag",  param1.market_saletag);
            Catalog.assetDict.put("pop_lock",  param1.lockedArea);
            Catalog.assetDict.put("pop_neighborlock",  param1.lockedNeighborArea);
            Catalog.assetDict.put("tab_icon_new_onstate",  param1.new_big);
            Catalog.assetDict.put("tab_icon_new_offstate",  param1.new_small);
            Catalog.assetDict.put("tab_icon_decorRoads_onstate",  param1.fountain_big);
            Catalog.assetDict.put("tab_icon_decorRoads_offstate",  param1.fountain_small);
            Catalog.assetDict.put("tab_icon_business_onstate",  param1.shirt_big);
            Catalog.assetDict.put("tab_icon_business_offstate",  param1.shirt_small);
            Catalog.assetDict.put("tab_icon_farming_onstate",  param1.farming_on);
            Catalog.assetDict.put("tab_icon_farming_offstate",  param1.farming_off);
            Catalog.assetDict.put("tab_icon_residence_onstate",  param1.house_big);
            Catalog.assetDict.put("tab_icon_residence_offstate",  param1.house_small);
            Catalog.assetDict.put("tab_icon_expansion_onstate",  param1.expand_big2);
            Catalog.assetDict.put("tab_icon_expansion_offstate",  param1.expand_big);
            Catalog.assetDict.put("tab_icon_extras_onstate",  param1.energy_big);
            Catalog.assetDict.put("tab_icon_extras_offstate",  param1.energy_small);
            Catalog.assetDict.put("tab_icon_ships_onstate",  param1.ship_big);
            Catalog.assetDict.put("tab_icon_ships_offstate",  param1.ship_small);
            Catalog.assetDict.put("tab_icon_municipal_onstate",  param1.tab_icon_municipal_onstate);
            Catalog.assetDict.put("tab_icon_municipal_offstate",  param1.tab_icon_municipal_offstate);
            Catalog.assetDict.put("tab_icon_wonders_onstate",  param1.wonders_big);
            Catalog.assetDict.put("tab_icon_wonders_offstate",  param1.wonders_small);
            Catalog.assetDict.put("tab_icon_landmark_onstate",  param1.landmark_big);
            Catalog.assetDict.put("tab_icon_landmark_offstate",  param1.landmark_small);
            Catalog.assetDict.put("saleSticker",  param1.franchise_saleSticker);
            Catalog.assetDict.put("specialsBgSticker",  EmbeddedArt.bogo_marketcard_bg);
            Catalog.assetDict.put("selectedTab",  param1.selectedTab);
            Catalog.assetDict.put("marketTitleRule",  param1.marketTitleRule);
            Catalog.assetDict.put("comingSoonStars",  param1.market_comingSoonStars);
            Catalog.assetDict.put("tower",  param1.item_tower);
            Catalog.assetDict.put("coliseum",  param1.item_coliseum);
            Catalog.assetDict.put("tractor",  param1.item_tractor);
            Catalog.assetDict.put("truck",  param1.item_truck);
            Catalog.assetDict.put("comingSoonBg",  param1.comingSoonBg);
            Catalog.assetDict.put("market_BG_Blank",  param1.market_BG_Blank);
            Catalog.assetDict.put("market2_leCard",  param1.market2_leCard);
            Catalog.assetDict.put("market2_leCard_divider",  param1.market2_leCard_divider);
            Catalog.assetDict.put("market2_leCardRO",  param1.market2_leCardRO);
            Catalog.assetDict.put("market2_leCardRO_arrow",  param1.market2_leCardRO_arrow);
            Catalog.assetDict.put("market2_marketCard",  param1.market2_marketCard);
            Catalog.assetDict.put("market2_marketCard_divider",  param1.market2_marketCard_divider);
            Catalog.assetDict.put("market2_marketCardRO",  param1.market2_marketCardRO);
            Catalog.assetDict.put("market2_marketCardRO_arrow",  param1.market2_marketCardRO_arrow);
            Catalog.assetDict.put("market2_newCard",  param1.market2_newCard);
            Catalog.assetDict.put("market2_newCard_divider",  param1.market2_newCard_divider);
            Catalog.assetDict.put("market2_newCardRO",  param1.market2_newCardRO);
            Catalog.assetDict.put("market2_newCardRO_arrow",  param1.market2_newCardRO_arrow);
            Catalog.assetDict.put("market2_tabPress",  param1.market2_tabPress);
            Catalog.assetDict.put("market2_tabUp",  param1.market2_tabUp);
            Catalog.assetDict.put("market2_timer",  param1.market2_timer);
            Catalog.assetDict.put("market2_lockedCard",  param1.market2_lockedCard);
            Catalog.assetDict.put("market2_lockedCard_divider",  param1.market2_lockedCard_divider);
            Catalog.assetDict.put("market2_lockedCardRO",  param1.market2_lockedCardRO);
            Catalog.assetDict.put("market2_lockedCardRO_arrow",  param1.market2_lockedCardRO_arrow);
            Catalog.assetDict.put("market2_lockedCard_lockIcon",  param1.market2_lockedCard_lockIcon);
            Catalog.assetDict.put("market2_sortDropDownPress",  param1.sortDropDownPress);
            Catalog.assetDict.put("market2_sortIcon",  param1.sortIcon);
            Catalog.assetDict.put("bestSellers_bg",  param1.bestSellers_bg);
            Catalog.assetDict.put("getCash_Up",  param1.getCash_Up);
            Catalog.assetDict.put("getCash_Press",  param1.getCash_Press);
            Catalog.assetDict.put("coinsIcon",  param1.coinsIcon);
            Catalog.assetDict.put("bestSellers_inset",  param1.bestSellers_inset);
            Catalog.assetDict.put("marketIcons_business_over",  param1.marketIcons_businesses_over);
            Catalog.assetDict.put("marketIcons_business_up",  param1.marketIcons_businesses_up);
            Catalog.assetDict.put("marketIcons_municipal_over",  param1.marketIcons_comBuildings_over);
            Catalog.assetDict.put("marketIcons_municipal_up",  param1.marketIcons_comBuildings_up);
            Catalog.assetDict.put("marketIcons_decorRoads_over",  param1.marketIcons_decorations_over);
            Catalog.assetDict.put("marketIcons_decorRoads_up",  param1.marketIcons_decorations_up);
            Catalog.assetDict.put("marketIcons_extras_over",  param1.marketIcons_energy_over);
            Catalog.assetDict.put("marketIcons_extras_up",  param1.marketIcons_energy_up);
            Catalog.assetDict.put("marketIcons_expansion_over",  param1.marketIcons_expansions_over);
            Catalog.assetDict.put("marketIcons_expansion_up",  param1.marketIcons_expansions_up);
            Catalog.assetDict.put("marketIcons_goods_over",  param1.marketIcons_goods_over);
            Catalog.assetDict.put("marketIcons_goods_up",  param1.marketIcons_goods_up);
            Catalog.assetDict.put("marketIcons_residence_over",  param1.marketIcons_residences_over);
            Catalog.assetDict.put("marketIcons_residence_up",  param1.marketIcons_residences_up);
            Catalog.assetDict.put("marketIcons_specials_over",  param1.marketIcons_specials_over);
            Catalog.assetDict.put("marketIcons_specials_up",  param1.marketIcons_specials_up);
            Catalog.assetDict.put("marketIcons_themes_over",  param1.marketIcons_themes_over);
            Catalog.assetDict.put("marketIcons_themes_up",  param1.marketIcons_themes_up);
            Catalog.assetDict.put("marketIcons_wonders_over",  param1.marketIcons_wonders_over);
            Catalog.assetDict.put("marketIcons_wonders_up",  param1.marketIcons_wonders_up);
            Catalog.assetDict.put("automation_lock",  param1.automation_lock);
            Catalog.assetDict.put("automation_clock",  param1.automation_clock);
            Catalog.assetDict.put("no_scroll_bg",  param1.no_scroll_bg);
            Catalog.assetDict.put("blue_box",  param1.blue_box);
            Catalog.assetDict.put("blue_divider_vertical",  param1.blue_divider_vertical);
            Catalog.assetDict.put("blue_divider_horizontal",  param1.blue_divider_horizontal);
            Catalog.assetDict.put("gas_icon",  param1.gas_icon);
            Catalog.assetDict.put("energy_icon",  param1.energy_icon);
            Catalog.assetDict.put("market2_locked_icon",  param1.market2_locked_icon);
            Catalog.assetDict.put("market2_remodel_icon",  param1.market2_remodel_icon);
            return;
        }//end


    }



