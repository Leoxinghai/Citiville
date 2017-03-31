package Classes.util;

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
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Engine.*;
import Engine.Managers.*;
import Modules.stats.trackers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;
import org.aswing.flyfish.*;

import com.xinghai.Debug;

    public class DelayedAssetLoader extends EventDispatcher
    {
        protected Array m_binaryToPreload ;
        protected Array m_assetsToPreload ;
        protected Dictionary m_cache ;
        protected Dictionary m_listeners ;
        protected int m_count ;
        protected Dictionary m_cacheNameIndex ;
        protected ApplicationDomain m_applicationDomain ;
        public static  int LOADTYPE_RESOURCE =0;
        public static  int LOADTYPE_BINARY =1;
        public static  int LOADTYPE_TEXT =2;
        public static  Object THEME_MAP =new Object ();
        public static  String DIALOG_ASSETS_PATH ="assets/dialogs/";
        public static  String GENERIC_DIALOG_ASSETS ="assets/dialogs/ASwingAssets.swf";
        public static  String QUEST_ASSETS ="assets/dialogs/QuestAssets.swf";
        public static  String NEW_QUEST_ASSETS ="assets/dialogs/NewQuestLayoutAssets.swf";
        public static  String TRAIN_ASSETS ="assets/dialogs/TrainDialogAssets.swf";
        public static  String INVENTORY_ASSETS ="assets/dialogs/InventoryAssets.swf";
        public static  String MARKET_ASSETS ="assets/dialogs/MarketAssets.swf";
        public static  String HALLOWEEN_MARKET_ASSETS ="assets/dialogs/MarketAssets.swf";
        public static  String MATCHMAKING_ASSETS ="assets/dialogs/MatchmakingAssets.swf";
        public static  String TABBED_MARKET_ASSETS ="assets/dialogs/TabbedMarketAssets.swf";
        public static  String POPULATION_ASSETS ="assets/dialogs/PopulationAssets.swf";
        public static  String MULTI_POPULATION_ASSETS ="assets/dialogs/MultiPopulationAssets.swf";
        public static  String BUILDABLE_ASSETS ="assets/dialogs/BuildablesAssets.swf";
        public static  String DAILY_BONUS_ASSETS ="assets/dialogs/DailyBonusAssets.swf";
        public static  String FOOD_BARBERPOLE ="assets/hud/foodPole_19x132.png";
        public static  String GOODS_BARBERPOLE ="assets/hud/goodsPole_19x132.png";
        public static  String ENERGY_ASSETS ="assets/dialogs/EnergyAssets.swf";
        public static  String FRANCHISE_ASSETS ="assets/franchise/FranchiseAssets.swf";
        public static  String VALENTINES_ASSETS ="assets/dialogs/ValentinesAssets.swf";
        public static  String FRAME_MANAGER_ASSETS ="assets/dialogs/FrameManagerAssets.swf";
        public static  String TABBED_ASSETS ="assets/dialogs/TabbedAssets.swf";
        public static  String FLASHSALE_ASSETS ="assets/dialogs/FlashSaleAssets.swf";
        public static  String EOQSALE_ASSETS ="assets/dialogs/EoQFlashSaleAssets.swf";
        public static  String UPGRADES_ASSETS ="assets/dialogs/UpgradesAssets.swf";
        public static  String BIZ_UPGRADES_ASSETS ="assets/dialogs/BusinessUpgradesAssets.swf";
        public static  String ZOO_ASSETS ="assets/zoo/ZooAssets.swf";
        public static  String SLOTTED_CONTAINER_ASSETS ="assets/dialogs/SlottedContainerAssets.swf";
        public static  String SAGA_ASSETS ="assets/dialogs/SagaAssets.swf";
        public static  String HALLOWEENSAGA_SAGA_ASSETS ="assets/dialogs/HalloweenSagaAssets.swf";
        public static  String MALL_ASSETS ="assets/malls/MallAssets.swf";
        public static  String PAYROLL_ASSETS ="assets/dialogs/PayrollAssets.swf";
        public static  String MASTERY_ASSETS ="assets/dialogs/MasteryAssets.swf";
        public static  String XGAMEGIFTING_ASSETS ="assets/dialogs/XGameGiftingAssets.swf";
        public static  String STREET_FAIR_ASSETS ="assets/dialogs/StreetCarnivalAssets.swf";
        public static  String STREET_FAIR_ASSETS_SHELL ="assets/dialogs/ShellCarnivalAssets.swf";
        public static  String HOTEL_ASSETS ="assets/dialogs/HotelsAssets.swf";
        public static  String ATTRACTIONS_ASSETS ="assets/dialogs/AttractionsAssets.swf";
        public static  String LANDMARKS_ASSETS ="assets/dialogs/LandmarksAssets.swf";
        public static  String GARDEN_ASSETS ="assets/dialogs/GardenAssets.swf";
        public static  String PAYER_CAMPAIGN ="assets/dialogs/PayerCampaign.swf";
        public static  String UNIVERSITY_ASSETS ="assets/dialogs/UniversityAssets.swf";
        public static  String DOWN_TOWN_CENTER_ASSETS ="assets/dialogs/DownTownCenterAssets.swf";
        public static  String PIER_UPGRADE_ASSETS ="assets/dialogs/PierUpgradeAssets.swf";
        public static  String BUTTON_ASSETS ="assets/dialogs/ButtonAssets.swf";
        public static  String CSS ="assets/css/main.css";
        public static  String REQUESTS2_ASSETS ="assets/dialogs/Requests2Assets.swf";
        public static  String IN_GAME_MFS_ASSETS ="assets/dialogs/InGameMFSAssets.swf";
        public static String m_theme ;

        public  DelayedAssetLoader ()
        {
            this.m_binaryToPreload = .get(CSS, BUTTON_ASSETS);
            this.m_assetsToPreload = .get(GENERIC_DIALOG_ASSETS, MARKET_ASSETS, QUEST_ASSETS, NEW_QUEST_ASSETS, FRAME_MANAGER_ASSETS, MASTERY_ASSETS, TRAIN_ASSETS, INVENTORY_ASSETS, BUILDABLE_ASSETS, POPULATION_ASSETS, MULTI_POPULATION_ASSETS, DAILY_BONUS_ASSETS, GOODS_BARBERPOLE, ENERGY_ASSETS, FRANCHISE_ASSETS, VALENTINES_ASSETS, TABBED_ASSETS, FLASHSALE_ASSETS, EOQSALE_ASSETS, UPGRADES_ASSETS, BIZ_UPGRADES_ASSETS, ZOO_ASSETS, SAGA_ASSETS, TABBED_MARKET_ASSETS, SLOTTED_CONTAINER_ASSETS, PAYROLL_ASSETS, XGAMEGIFTING_ASSETS, STREET_FAIR_ASSETS, ATTRACTIONS_ASSETS, STREET_FAIR_ASSETS_SHELL, PAYER_CAMPAIGN, UNIVERSITY_ASSETS, DOWN_TOWN_CENTER_ASSETS, PIER_UPGRADE_ASSETS, MATCHMAKING_ASSETS, "assets/NPC/Car_yehaul/car_haultruck_N.png", "assets/NPC/Car_yehaul/car_haultruck_NNE.png", "assets/NPC/Car_yehaul/car_haultruck_NE.png", "assets/NPC/Car_yehaul/car_haultruck_NEE.png", "assets/NPC/Car_yehaul/car_haultruck_E.png", "assets/NPC/Car_yehaul/car_haultruck_SEE.png", "assets/NPC/Car_yehaul/car_haultruck_SE.png", "assets/NPC/Car_yehaul/car_haultruck_SSE.png", "assets/NPC/Car_yehaul/car_haultruck_S.png", "assets/NPC/Car_yehaul/car_haultruck_SSW.png", "assets/NPC/Car_yehaul/car_haultruck_SW.png", "assets/NPC/Car_yehaul/car_haultruck_SWW.png", "assets/NPC/Car_yehaul/car_haultruck_W.png", "assets/NPC/Car_yehaul/car_haultruck_NWW.png", "assets/NPC/Car_yehaul/car_haultruck_NW.png", "assets/NPC/Car_yehaul/car_haultruck_NNW.png", "assets/NPC/Car_tourismbus/tour-bus_N.png", "assets/NPC/Car_tourismbus/tour-bus_NNE.png", "assets/NPC/Car_tourismbus/tour-bus_NE.png", "assets/NPC/Car_tourismbus/tour-bus_NEE.png", "assets/NPC/Car_tourismbus/tour-bus_E.png", "assets/NPC/Car_tourismbus/tour-bus_SEE.png", "assets/NPC/Car_tourismbus/tour-bus_SE.png", "assets/NPC/Car_tourismbus/tour-bus_SSE.png", "assets/NPC/Car_tourismbus/tour-bus_S.png", "assets/NPC/Car_tourismbus/tour-bus_SSW.png", "assets/NPC/Car_tourismbus/tour-bus_SW.png", "assets/NPC/Car_tourismbus/tour-bus_SWW.png", "assets/NPC/Car_tourismbus/tour-bus_W.png", "assets/NPC/Car_tourismbus/tour-bus_NWW.png", "assets/NPC/Car_tourismbus/tour-bus_NW.png", "assets/NPC/Car_tourismbus/tour-bus_NNW.png", HOTEL_ASSETS);
            this.m_cacheNameIndex = new Dictionary(true);
            this.m_cache = new Dictionary();
            this.m_listeners = new Dictionary();
            return;
        }//end

        public Dictionary  cache ()
        {
            return this.m_cache;
        }//end

        public Dictionary  nameCache ()
        {
            return this.m_cacheNameIndex;
        }//end

        public ApplicationDomain  appDomain ()
        {
            return this.m_applicationDomain;
        }//end

        public void  startPostloading ()
        {
            Global.runspace = new CityRunSpace();
            Global.runspace.loadLibs(this.onLibsLoaded);
            this.m_count = this.m_assetsToPreload.length + this.m_binaryToPreload.length;
            this.startPreloadingUrls(this.m_assetsToPreload, LoadingManager.PRIORITY_LOW, LOADTYPE_RESOURCE, false);
            this.startPreloadingUrls(this.m_binaryToPreload, LoadingManager.PRIORITY_LOW, LOADTYPE_BINARY, false);
            return;
        }//end

        private void  onLibsLoaded ()
        {
            FlyFish.initialise(Global.runspace);
            return;
        }//end

        public void  startPreloadingUrls (Array param1 ,int param2 =2,int param3 =0,boolean param4 =false )
        {
            String _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_5 = param1.get(i0);

                if (this.m_cache.get(_loc_5) == null)
                {
                    _loc_6 = _loc_5.split("/");
                    _loc_7 = _loc_5.split("/").get((_loc_6.length - 1));
                    _loc_8 = _loc_7.split(".").get(0);
                    this.m_cacheNameIndex.put(_loc_8,  _loc_5);
                    if (param3 == LOADTYPE_RESOURCE)
                    {
                        this.startLoading(_loc_5, param2, this.onAssetLoaded, param4);
                        continue;
                    }
                    this.startLoadingBinary(_loc_5, param2, this.onBinaryLoaded, param4);
                }
            }
            LoadingManager.setWorldAssetsRequested();
            return;
        }//end

        public boolean  isAssetLoaded (String param1 )
        {
            return this.m_cache.get(param1) != null;
        }//end

        protected String  getAbsoluteURL (String param1 ,boolean param2 =false )
        {
            param1 = this.getActualURL(param1);
            return param2 ? (param1) : (Global.getAssetURL(param1));
        }//end

        protected String  getActualURL (String param1 )
        {
            String _loc_2 =null ;
            if (THEME_MAP.get(param1))
            {
                if (!m_theme)
                {
                    m_theme = Global.gameSettings().uiTheme().@name;
                }
                _loc_2 = THEME_MAP.get(param1).get(m_theme);
            }
            return _loc_2 ? (_loc_2) : (param1);
        }//end

        protected void  startLoading (String param1 ,int param2 ,Function param3 ,boolean param4 =false )
        {
            Loader loader ;
            url = param1;
            priority = param2;
            callback = param3;
            urlIsAbsolute = param4;
            absoluteUrl = this.getAbsoluteURL(url,urlIsAbsolute);
            loader =LoadingManager .load (absoluteUrl ,void  (Event event )
            {
                callback(url, loader);
                return;
            }//end
            , priority);
            this.m_applicationDomain = loader.contentLoaderInfo.applicationDomain;
            return;
        }//end

        protected void  startLoadingBinary (String param1 ,int param2 ,Function param3 ,boolean param4 =false )
        {
            URLLoader loader ;
            String assetUrl ;
            url = param1;
            priority = param2;
            callback = param3;
            urlIsAbsolute = param4;
            absoluteUrl = this.getAbsoluteURL(url,urlIsAbsolute);
            if (absoluteUrl)
            {
                loader = new URLLoader();
                loader.dataFormat = URLLoaderDataFormat.BINARY;
                loader .addEventListener (Event .COMPLETE ,void  (Event event )
            {
                callback(url, loader);
                return;
            }//end
            );
                loader.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
                loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
                assetUrl = AssetUrlManager.instance.lookUpUrl(absoluteUrl);
                loader.load(new URLRequest(assetUrl));
            }
            else
            {
                ErrorManager.addError("Bad url in DelayedAssetLoader: " + url);
            }
            return;
        }//end

        protected void  onAssetLoaded (String param1 ,Loader param2 )
        {
            _loc_3 =(DisplayObject) param2.content;
            if (_loc_3 == null)
            {
                ErrorManager.addError("Failed to load delayed asset: " + param1);
            }
            this.postProcessLoadedData(param1, _loc_3);
            return;
        }//end

        protected void  onBinaryLoaded (String param1 ,URLLoader param2 )
        {
            _loc_3 = param2(as URLLoader ).data ;
            if (!_loc_3)
            {
                ErrorManager.addError("Failed to load delayed binary data: " + param1);
            }
            if (param1.indexOf(".xml.z") > 0)
            {
                _loc_3 = Utilities.uncompress(_loc_3);
            }
            this.postProcessLoadedData(param1, _loc_3);
            param2.removeEventListener(Event.COMPLETE, this.onBinaryLoaded);
            param2.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            param2.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
            return;
        }//end

        protected void  onError (Event event )
        {
            if (event instanceof TextEvent)
            {
                ErrorManager.addError("Failed to load delayed data: " + ((TextEvent)event).text);
            }
            else
            {
                ErrorManager.addError("Failed to load delayed data: " + event.toString());
            }
            return;
        }//end

        protected void  postProcessLoadedData (String param1 , Object param2)
        {
            Function _loc_4 =null ;
            this.m_cache.put(param1,  param2);
            this.populateGlobalVariables(param2, param1);
            _loc_3 =(Array) this.m_listeners.get(param1);
            if (_loc_3 != null)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    _loc_4(param2, param1);
                }
            }
            delete this.m_listeners.get(param1);
            this.m_count--;
            if (this.m_count == 0)
            {
                StartupSessionTracker.perf(StatsKingdomType.LOADCOMPLETE);
                dispatchEvent(new Event(Event.COMPLETE));
            }
            return;
        }//end

        protected void  populateGlobalVariables (Object param1 ,String param2 )
        {
            switch(param2)
            {
                case DelayedAssetLoader.MARKET_ASSETS:
                {
                    Catalog.setupAssets(param1, param2);
                    break;
                }
                case DelayedAssetLoader.INVENTORY_ASSETS:
                {
                    InventoryView.setupAssets(param1, param2);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public boolean  (String param1 ,Function param2 ,int param3 =0,boolean param4 =false )
        {
            _loc_5 = this.m_cache.get(param1) ;
            if (this.m_cache.get(param1) != null)
            {
                param2(_loc_5, param1);
                return true;
            }
            if (this.m_listeners.get(param1) == null)
            {
                this.m_listeners.put(param1,  .get(param2));
                this.startPreloadingUrls(.get(param1), LoadingManager.PRIORITY_LOW, param3, param4);
            }
            else
            {
                ((Array)this.m_listeners.get(param1)).push(param2);
            }
            return false;
        }//end

        public DisplayObject  getAsset (String param1 )
        {
            if (this.m_cache.get(param1))
            {
                return (DisplayObject)this.m_cache.get(param1);
            }
            return makeAssetLoader(param1, this);
        }//end

        public static DisplayObject  makeAssetLoader (String param1 ,DelayedAssetLoader param2 )
        {
            Sprite container ;
            url = param1;
            loader = param2;
            container = new Sprite();
            loader .(url ,void  (DisplayObject param1 ,String param2 )
            {
                container.addChild(param1);
                return;
            }//end
            );
            return container;
        }//end

    }



