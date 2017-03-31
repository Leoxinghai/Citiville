package Init;

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

import com.xiyu.logic.XML;
import com.xiyu.logic.XMLList;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Classes.*;
import Classes.announcements.*;
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.NeighborUI.config.*;
import Display.NeighborUI.helpers.*;
import Engine.*;
import Engine.Init.*;
import Engine.Managers.*;
import Modules.matchmaking.*;
import Modules.quest.Managers.*;
import Modules.remodel.*;
import Modules.stats.experiments.*;
import Modules.sunset.*;

//import flash.events.*;
//import flash.utils.*;
import root.Config;
import root.Global;
import root.GlobalEngine;
import root.ZLoc;
import scripting.*;
import validation.*;
import com.xinghai.Debug;
import com.xiyu.util.Event;
import java.util.Vector;


public class GameSettingsInit extends InitializationAction
    {
        private XMLList m_xml ;
        private XML m_rawXml ;
        public XML m_effectsConfigXml ;
        private Dictionary m_itemsXML =null ;
        private XMLList m_cachedItemsXMLList =null ;
        private int m_lastItemXMLIndex =0;
        private Dictionary m_items ;
        private Dictionary m_themeItems ;
        private Array m_newItems ;
        private Dictionary m_itemsByType =null ;
        private Dictionary m_attributes ;
        private Dictionary m_questTiers ;
        private Dictionary m_minigameConfigs ;
        private Dictionary m_keywords ;
        private int m_numKeywords =0;
        private boolean m_optimizeItemsXML =false ;
        private GameSettingsDownloadInit m_downloadInit =null ;
        private Object m_hubGroupMap =null ;
        private double m_inGameDaySeconds ;
        private double m_growMultiplier ;
        private double m_witherMultiplier ;
        private double m_boostGrowMultiplier ;
        private double m_boostGrowInstantHourLimit ;
        private double m_dailyCollectCycleTimeSeconds ;
        private double m_buildMultiplier ;
        private double m_buildingSpeedupCostMultiplier ;
        private double m_buildingSpeedupTimeMultiplier ;
        private double m_buildingSpeedupMaxCount ;
        private Dictionary m_sets ;
        private boolean m_lootSingleClick ;
        private int m_minZoom ;
        private int m_maxZoom ;
        private Dictionary m_parsedData ;
        private boolean m_optimizeRandomModifiers =false ;
        private XML m_randomModifierTables =null ;
        private Dictionary m_randomModifierTableCache ;
        private Dictionary m_memoizers ;
        private Object m_populations ;
        private Array m_populationsForDisplay ;
        private int m_hasMultiplePopulation =-1;
        private Array m_validExpansionSquares ;
        private int m_keywordErrorCount =0;
        private String m_keywordErrorString ="";
        private int m_missingKeywordCount =0;
        private String m_missingKeywordString ="";
        private Dictionary m_grafts =null ;
        public static  String INIT_ID ="GameSettingsInit";
        private static  int MOTD_ICON_BACKLOG_MAX =3;

        public  GameSettingsInit (GameSettingsDownloadInit param1 )
        {
            super(INIT_ID);
            this.m_items = new Dictionary();
            this.m_themeItems = new Dictionary();
            this.m_newItems = new Array();
            this.m_attributes = new Dictionary();
            this.m_questTiers = new Dictionary();
            this.m_minigameConfigs = new Dictionary();
            this.m_keywords = new Dictionary();
            this.m_parsedData = new Dictionary();
            this.m_memoizers = new Dictionary();
            this.m_validExpansionSquares = new Array();
            this.m_downloadInit = param1;
            addDependency(GameSettingsDownloadInit.INIT_ID);
            addDependency(ExperimentsInit.INIT_ID);
            addDependency(RuntimeVariableInit.INIT_ID);
            addDependency(LoadingInit.INIT_ID);
            GlobalEngine.zaspManager.trackTimingStart("GAME_SETTINGS_INIT");
            Global.gameSettings(this);
            return;
        }//end

         public void  execute ()
        {
            Dictionary _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 = this.m_downloadInit.data ;
            this.m_downloadInit.data = null;
            this.m_optimizeItemsXML = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_ITEMS_XML);
            GlobalEngine.log("XMLSettingsInit", "success: loaded " + _loc_1.length + " bytes");
            _loc_2 = XML(_loc_1);
            this.m_rawXml = _loc_2;
            this.m_xml = _loc_2..farming;
            if (this.m_xml.length > 0)
            {
                this.m_xml = this.m_xml.get(0);
            }
            this.populateVariables();
            if (this.m_rawXml.assetIndex && this.m_rawXml.assetIndex.length() > 0)
            {
                _loc_3 = new Dictionary();
                for(int i0 = 0; i0 < Config.ASSET_PATHS.size(); i0++)
                {
                	_loc_4 = Config.ASSET_PATHS.get(i0);

                    _loc_3.put(_loc_4,  _loc_4 + this.m_rawXml.assetIndex.@hashedPrefix);
                }
                new AssetUrlManager(_loc_3, this.m_rawXml.assetIndex.toString(), this.m_rawXml.assetPackIndex.toString());
            }
            else
            {
                new AssetUrlManager(null, null, null);
            }
            this.m_inGameDaySeconds = Number(this.getAttribute("inGameDaySeconds"));
            this.m_growMultiplier = Number(this.getAttribute("growMultiplier"));
            this.m_witherMultiplier = Number(this.getAttribute("witherMultiplier"));
            this.m_boostGrowMultiplier = Number(this.getAttribute("boostGrowMultiplier"));
            this.m_boostGrowInstantHourLimit = Number(this.getAttribute("boostGrowInstantHourLimit"));
            this.m_dailyCollectCycleTimeSeconds = Number(this.getAttribute("dailyCollectCycleTimeSeconds"));
            this.m_buildMultiplier = Number(this.getAttribute("buildMultiplier"));
            this.m_buildingSpeedupCostMultiplier = Number(this.getAttribute("buildingSpeedupCostMultiplier"));
            this.m_buildingSpeedupTimeMultiplier = Number(this.getAttribute("buildingSpeedupTimeMultiplier"));
            this.m_buildingSpeedupMaxCount = Number(this.getAttribute("buildingSpeedupMaxCount"));
            this.m_minZoom = this.getInt("minZoom", 2);
            this.m_maxZoom = this.getInt("maxZoom", 4);
            this.m_lootSingleClick = true;
            this.initSets();
            this.parseKeywordSyntax(this.m_rawXml.keyword_syntax.get(0).itemType);
            this.parseValidExpansionSquares(this.m_rawXml.validExpansions.get(0).squares);
            this.validateXml();
            this.optimizeRandomModifiers = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMORY_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_DOOBER_SPAWN);
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("GAME_SETTINGS_INIT");
            return;
        }//end

        private boolean  optimizeItemsXML ()
        {
            return this.m_optimizeItemsXML;
        }//end

        private void  initSets ()
        {
            XML _loc_1 =null ;
            ItemGroup _loc_2 =null ;
            this.m_sets = new Dictionary();
            for(int i0 = 0; i0 < this.m_rawXml.sets.set.size(); i0++)
            {
            	_loc_1 = this.m_rawXml.sets.set.get(i0);

                _loc_2 = new ItemGroup(_loc_1);
                this.m_sets.put(_loc_2.getName(),  _loc_2);
            }
            return;
        }//end

        private void  validateXml ()
        {
            if (Config.DEBUG_MODE)
            {
                this.validateXmlItems();
            }
            return;
        }//end

        private void  validateXmlItems ()
        {
            XML _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_rawXml.items.item.size(); i0++)
            {
            	_loc_1 = this.m_rawXml.items.item.get(i0);

                if (!_loc_1.hasOwnProperty("@name") || !_loc_1.hasOwnProperty("@type"))
                {
                    continue;
                }
                if (_loc_1.@type == "business")
                {
                    this.validateXmlBusinessItem(_loc_1);
                }
                this.validateItemKeywords(_loc_1);
            }
            return;
        }//end

        private void  validateXmlBusinessItem (XML param1 )
        {
            XML _loc_6 =null ;
            String _loc_7 =null ;
            XML _loc_8 =null ;
            String _loc_9 =null ;
            if (!param1.hasOwnProperty("canFranchise"))
            {
                return;
            }
            if (param1.canFranchise != "true")
            {
                return;
            }
            boolean _loc_2 =false ;
            Dictionary _loc_3 =new Dictionary ();
            if (param1.hasOwnProperty("randomModifierGroups"))
            {
                for(int i0 = 0; i0 < param1.randomModifierGroups.group.size(); i0++)
                {
                	_loc_6 = param1.randomModifierGroups.group.get(i0);

                    _loc_7 = Business.MODIFIER_GROUP_DEFAULT;
                    if (_loc_6.hasOwnProperty("@name"))
                    {
                        _loc_7 = _loc_6.@name;
                    }
                    _loc_3.put(_loc_7,  _loc_6);
                    _loc_2 = true;
                }
            }
            boolean _loc_4 =false ;
            Dictionary _loc_5 =new Dictionary ();
            if (param1.hasOwnProperty("randomModifiers"))
            {
                for(int i0 = 0; i0 < param1.randomModifiers.size(); i0++)
                {
                	_loc_8 = param1.randomModifiers.get(i0);

                    _loc_9 = Business.MODIFIER_GROUP_DEFAULT;
                    if (_loc_8.hasOwnProperty("@name"))
                    {
                        _loc_9 = _loc_8.@name;
                    }
                    _loc_5.put(_loc_9,  _loc_8);
                    _loc_4 = true;
                }
            }
            if (!_loc_5.get(Business.MODIFIER_GROUP_DEFAULT))
            {
                ErrorManager.addError("GameSettingsInit: franchise (" + param1.@name + ") " + "missing random modifiers: " + Business.MODIFIER_GROUP_DEFAULT);
            }
            if (!_loc_5.get(Business.MODIFIER_GROUP_FRANCHISE))
            {
                ErrorManager.addError("GameSettingsInit: franchise (" + param1.@name + ") " + "missing random modifiers: " + Business.MODIFIER_GROUP_FRANCHISE);
            }
            if (_loc_4 && !_loc_2)
            {
                ErrorManager.addError("GameSettingsInit: franchise (" + param1.@name + ") " + "has random modifiers without random modifier groups.");
            }
            if (!_loc_3.get(Business.MODIFIER_GROUP_DEFAULT))
            {
                ErrorManager.addError("GameSettingsInit: franchise (" + param1.@name + ") " + "missing random modifier group: " + Business.MODIFIER_GROUP_DEFAULT);
            }
            if (!_loc_3.get(Business.MODIFIER_GROUP_FRANCHISE))
            {
                ErrorManager.addError("GameSettingsInit: franchise (" + param1.@name + ") " + "missing random modifier group: " + Business.MODIFIER_GROUP_FRANCHISE);
            }
            return;
        }//end

        public ItemGroup  getSetByName (String param1 )
        {
            return this.m_sets.get(param1);
        }//end

        public void  populateVariables ()
        {
            return;
        }//end

        public Object getAttribute (String param1 ,Object param2 )
        {
            Object _loc_3 = null;
            if (this.m_attributes.get(param1) != null && this.m_attributes.get(param1) != undefined)
            {
                _loc_3 = this.m_attributes.get(param1);
            }
            else
            {
                if (this.m_xml.get("@" + param1) != null && this.m_xml.get("@" + param1) != undefined)
                {
                    _loc_3 = this.m_xml.get("@" + param1);
                }
                else
                {
                    _loc_3 = param2;
                }
                this.m_attributes.put(param1,  _loc_3);
            }
            return _loc_3;
        }//end

        public double  getInstantReadyCropMultiplier (int param1 )
        {
            return Math.max(Number(this.getAttribute("instantReadyCropCostConstant" + param1)), 0.1);
        }//end

        public double  getInstantReadyResidenceMultiplier (int param1 )
        {
            return Math.max(Number(this.getAttribute("instantReadyResidenceCostConstant" + param1)), 0.1);
        }//end

        public String  getString (String param1 ,String param2)
        {
            return this.getAttribute(param1, param2).toString();
        }//end

        public int  getInt (String param1 ,int param2)
        {
            return parseInt(this.getAttribute(param1, param2));
        }//end

        public double  getNumber (String param1 ,double param2)
        {
            return Double.parseDouble((String)this.getAttribute(param1, param2));
        }//end

        public Array  newItems ()
        {
            return this.m_newItems;
        }//end

        public double  inGameDaySeconds ()
        {
            return this.m_inGameDaySeconds;
        }//end

        public double  growMultiplier ()
        {
            return this.m_growMultiplier;
        }//end

        public double  witherMultiplier ()
        {
            return this.m_witherMultiplier;
        }//end

        public double  boostGrowMultiplier ()
        {
            return this.m_boostGrowMultiplier;
        }//end

        public double  boostGrowInstantHourLimit ()
        {
            return this.m_boostGrowInstantHourLimit;
        }//end

        public double  dailyCollectCycleTimeSeconds ()
        {
            return this.m_dailyCollectCycleTimeSeconds;
        }//end

        public double  buildMultiplier ()
        {
            return this.m_buildMultiplier;
        }//end

        public double  buildingSpeedupCostMultiplier ()
        {
            return this.m_buildingSpeedupCostMultiplier;
        }//end

        public double  buildingSpeedupTimeMultiplier ()
        {
            return this.m_buildingSpeedupTimeMultiplier;
        }//end

        public double  buildingSpeedupMaxCount ()
        {
            return this.m_buildingSpeedupMaxCount;
        }//end

        public boolean  lootSingleClick ()
        {
            return this.m_lootSingleClick;
        }//end

        public int  minZoom ()
        {
            return this.m_minZoom;
        }//end

        public int  maxZoom ()
        {
            return this.m_maxZoom;
        }//end

        public void  setAttribute (String param1 ,String param2 )
        {
            this.m_xml.put("@" + param1,  param2);
            return;
        }//end

        public Array  getAttributes ()
        {
            XMLList _loc_2 =null ;
            int _loc_3 =0;
            Array _loc_1 =new Array();
            if (this.m_xml)
            {
                _loc_2 = this.m_xml.@*;
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    _loc_1.push({name:_loc_2.get(_loc_3).name(), value:_loc_2.get(_loc_3).toString()});
                    _loc_3++;
                }
            }
            return _loc_1;
        }//end

        public XML  getXML ()
        {
            return this.m_rawXml;
        }//end

        public XMLList  getPickupNpcs ()
        {
            return this.m_rawXml.pickupNpcs.pickupNpc;
        }//end

        public XMLList  getGameNews ()
        {
            return this.m_rawXml.gameNews.item;
        }//end

        public XMLList  getAchievementGroups ()
        {
            return this.m_rawXml.achievements;
        }//end

        public XML  getAchievementGroupByName (String param1 )
        {
            XMLList def ;
            String group = param1;
            int _loc_4 =0;
            XMLList _loc_5 = this.m_rawXml.achievements.group ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);
                with (_loc_6)
                {
                    if (@id == group)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            def = _loc_3;
            return def.length() > 0 ? (def.get(0)) : (null);
        }//end

        public XMLList  getTickets ()
        {
            return this.m_rawXml.tickets;
        }//end

        public XMLList  getItems ()
        {
            Array items ;
            Array itemsObjects ;
            int length ;
            Object itemObject ;
            if (this.optimizeItemsXML)
            {
                if (this.m_cachedItemsXMLList == null)
                {
                    this.m_cachedItemsXMLList = new XMLList();
                    items = new Array();
                    int _loc_2 =0;
                    _loc_3 = this.m_itemsXML ;
                    for(int i0 = 0; i0 < this.m_itemsXML.size(); i0++)
                    {
                    	itemsObjects = this.m_itemsXML.get(i0);


                        for(int i0 = 0; i0 < itemsObjects.size(); i0++)
                        {
                        	itemObject = itemsObjects.get(i0);

                            items.push(itemObject);
                        }
                    }
                    items .sort (int  (Object param1 ,Object param2 )
            {
                return param1.index - param2.index;
            }//end
            );
                    length;
                    int _loc_21 =0;
                    _loc_31 = items;
                    for(int i0 = 0; i0 < items.size(); i0++)
                    {
                    	itemObject = items.get(i0);

                        length = (length + 1);

                        this.m_cachedItemsXMLList.put(length,  itemObject.xml);
                    }
                }
                return this.m_cachedItemsXMLList;
            }
            else
            {
            }
            return this.m_rawXml.items.item;
        }//end

        public XMLList  getCollections ()
        {
            return this.m_rawXml.collections.collection;
        }//end

        public XMLList  getWildernessRegions ()
        {
            return this.m_rawXml.wilderness.region;
        }//end

        public XMLList getWildernessHeatMap ()
        {
            return this.m_rawXml.heatMapPoints.point;
        }//end

        public Dictionary  getSets ()
        {
            return this.m_sets;
        }//end

        public XML getRandomModifierPackModifiers (String param1 )
        {
            XMLList packsXmlList ;
            XML packXml ;
            XML result ;
            XMLList children ;
            String a_packId = param1;
            packsXmlList = this.m_rawXml.randomModifierPacks.randomModifiers;
            int _loc_4 =0;
            XMLList _loc_5 = packsXmlList;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                with (_loc_6)
                {
                    if (attribute("id") == a_packId)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            packXml = (XML)_loc_3.get(0);
            result = null;
            if (packXml && packXml.length())
            {
                children = packXml.children();
                children = Global.experimentManager.filterXmlByExperiment(children);
                if (children && children.length())
                {
                    packXml.setChildren(children);
                    result = (XML)packXml.get(0);
                }
            }
            return result;
        }//end

        public XML  getRandomModifierTables ()
        {
            XML _loc_1 = this.m_rawXml.randomModifierTables.get(0) ;
            return _loc_1;
        }//end

        public XML  getRandomModifierTable (String param1 ,String param2 )
        {
            XMLList tablesXml ;
            XML result ;
            String tableType = param1;
            String tableName = param2;
            tablesXml = this.getRandomModifierTables().children();
            int _loc_5 =0;
            XMLList _loc_6 = tablesXml;
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);
                with (_loc_7)
                {
                    if (attribute("type") == tableType && attribute("name") == tableName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            result = _loc_4.get(0);
            return result;
        }//end

        public XML  getSpecificRandomModifierTable (String param1 )
        {
            XML modifierTables ;
            XMLList modifierTable ;
            String tableType = param1;
            if (!this.m_optimizeRandomModifiers || !this.m_randomModifierTableCache)
            {
                modifierTables = this.getRandomModifierTables();
                int _loc_4 =0;
                XMLList _loc_5 = modifierTables.randomModifierTable;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == tableType)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                modifierTable = _loc_3;
                return modifierTable.get(0);
            }
            else
            {
            }
            return this.m_randomModifierTableCache.get(tableType);
        }//end

        public void  optimizeRandomModifiers (boolean param1 )
        {
            XML _loc_2 =null ;
            this.m_optimizeRandomModifiers = param1;
            if (this.m_optimizeRandomModifiers)
            {
                this.m_randomModifierTables = this.getRandomModifierTables();
                this.m_randomModifierTableCache = new Dictionary();
                for(int i0 = 0; i0 < this.m_randomModifierTables.randomModifierTable.size(); i0++)
                {
                	_loc_2 = this.m_randomModifierTables.randomModifierTable.get(i0);

                    this.m_randomModifierTableCache.put(String(_loc_2.@name),  _loc_2);
                }
            }
            return;
        }//end

        public XMLList  getLimitedItemRequests ()
        {
            return this.m_rawXml.limitedItemRequests.request;
        }//end

        public XMLList  getInitTrayPreloads ()
        {
            return this.m_rawXml.initTrayPreloads.preload;
        }//end

        public XMLList  getItemsByTypeXML (String param1 ,String param2)
        {
            XML _loc_6 =null ;
            int _loc_7 =0;
            Array _loc_8 =null ;
            XMLList _loc_3 =new XMLList();
            XMLList _loc_4 = this.getItems ();
            int _loc_5 =0;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_6 = _loc_4.get(i0);

                if (_loc_6.@type == param1 || _loc_6.@category == param1)
                {
                    if (param2.toLowerCase() == "all" || param2 == "" || param2 == _loc_6.@subtype)
                    {
                        _loc_3.put(++_loc_5,  _loc_6);
                    }
                }
                if (_loc_6.tag.length() > 0)
                {
                    _loc_7 = 0;
                    while (_loc_7 < _loc_6.tag.length())
                    {

                        _loc_8 = _loc_6.tag.get(_loc_7).split(":");
                        if (_loc_8.get(0) == param1)
                        {
                            if (param2.toLowerCase() == "all" || param2 == "" || param2 == _loc_8.get(1))
                            {
                                _loc_3.put(_loc_5++,  _loc_6);
                                break;
                            }
                        }
                        _loc_7++;
                    }
                }
            }
            return _loc_3;
        }//end

        public Array  getItemsByType (String param1 ,String param2)
        {
            XMLList itemsXML ;
            XML itemXML ;
            Item item ;
            Item bundleItem ;
            XMLList rowXml ;
            int i ;
            int permitBundleVariant ;
            Item permitItem ;
            String type = param1;
            String subType = param2;
            if (this.m_itemsByType == null)
            {
                this.m_itemsByType = new Dictionary();
                itemsXML = this.getItems();
                int _loc_4 =0;
                XMLList _loc_5 = itemsXML;
                for(int i0 = 0; i0 < itemsXML.size(); i0++)
                {
                	itemXML = itemsXML.get(i0);


                    item = this.getItemByName(itemXML.@name);
                    if (this.m_itemsByType.get(item.type) == null)
                    {
                        this.m_itemsByType.put(item.type,  new Array());
                    }
                    this.m_itemsByType.get(item.type).push(item);
                }
            }
            Array items ;
            if (this.m_itemsByType.get(type) != null)
            {
                items = this.m_itemsByType.get(type);
            }
            if (subType != "")
            {
                items =items .filter (boolean  (Item param11 ,...args )
            {
                return subType == "" || subType == param11.subtype;
            }//end
            );
            }
            if (this.m_itemsByType.get(type + "_bundles"))
            {
                bundleItem =(Item) this.m_itemsByType.get(type + "_bundles").get(0);
                rowXml = bundleItem.xml.children();
                i = 0;
                while (i < rowXml.length())
                {

                    items = items.concat(Global.gameSettings().getItemByName(String(rowXml.get(i).@name)) as Item);
                    i = (i + 1);
                }
                items.reverse();
            }
            if (type == "expansion")
            {
                permitBundleVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PERMIT_BUNDLES);
                if (permitBundleVariant >= ExperimentDefinitions.SHOW_PERMIT_BUNDLES)
                {
                    permitItem =(Item) this.m_itemsByType.get("permit_pack" + permitBundleVariant).get(0);
                    if (permitItem.name == "permit_pack" + permitBundleVariant + "_1")
                    {
                        items = this.m_itemsByType.get("permit_pack" + permitBundleVariant).reverse().concat(items).reverse();
                    }
                    else
                    {
                        items = this.m_itemsByType.get("permit_pack" + permitBundleVariant).concat(items).reverse();
                    }
                }
            }
            variant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STARTER_PACK2);
            inExperiment = variant>0&& Global.player.hasMadeRealPurchase == false;
            if (inExperiment && (type == "energy" || type == "decorRoads"))
            {
                items = this.m_itemsByType.get("starter_pack2_" + variant).concat(items);
            }
            return items;
        }//end

        public Array  getItemsByKeywords (Array param1 )
        {
            Item _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_items.size(); i0++)
            {
            	_loc_3 = this.m_items.get(i0);

                for(int i1 = 0; i1 < param1.size(); i1++)
                {
                	_loc_4 = param1.get(i1);

                    if (_loc_3.itemHasKeyword(_loc_4))
                    {
                        _loc_2.push(_loc_3);
                    }
                }
            }
            return _loc_2;
        }//end

        public Array  getItemsByKeywordMatch (String param1 )
        {
            Item _loc_4 =null ;
            boolean _loc_5 =false ;
            String _loc_6 =null ;
            _loc_2 = Item.processKeywordQuery(param1);
            Array _loc_3 =new Array();
            if (_loc_2.union)
            {
                for(int i0 = 0; i0 < this.m_items.size(); i0++)
                {
                	_loc_4 = this.m_items.get(i0);

                    _loc_5 = true;
                    for(int i0 = 0; i0 < _loc_2.keywords.size(); i0++)
                    {
                    	_loc_6 = _loc_2.keywords.get(i0);

                        if (!_loc_4.itemHasKeyword(_loc_6))
                        {
                            _loc_5 = false;
                            break;
                        }
                    }
                    if (_loc_5)
                    {
                        _loc_3.push(_loc_4);
                    }
                }
            }
            else
            {
                _loc_3 = this.getItemsByKeywords(_loc_2.keywords);
            }
            return _loc_3;
        }//end

        public Array  getXGameGiftItemsByGameId (int param1 )
        {
            XMLList items ;
            XMLList narrowed ;
            Array ret ;
            XML itemXML ;
            gameId = param1;
            items = this.getItems();
            int _loc_4 =0;
            XMLList _loc_5 = items;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);
                with (_loc_6)
                {
                    if (child("xGameInfo").child("gameId") == gameId)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            narrowed = _loc_3;
            ret = new Array();
            int _loc_31 =0;
            _loc_41 = narrowed;
            for(int i0 = 0; i0 < narrowed.size(); i0++)
            {
            	itemXML = narrowed.get(i0);
                ret.push(this.getItemByName(itemXML.attribute("name")));
            }
            return ret;
        }//end

        public Array  getAllReceivableXGameGiftItemsByGameId (int param1 )
        {
            XMLList items ;
            Array ret ;
            XML item ;
            int gameId = param1;
            int _loc_4 =0;
            XMLList _loc_5 = this.m_rawXml.child("xGameGiftingReceivableItems").child("game");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("id") == gameId)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            items = _loc_3.child("item");
            //ret;
            int _loc_31 =0;
            XMLList _loc_41 = items;
            for(int i0 = 0; i0 < items.size(); i0++)
            {
            	item = items.get(i0);


                ret.push({name:item.attribute("name").toString(), level:item.attribute("level").length() > 0 ? (parseInt(item.attribute("level").toString())) : (1)});
            }
            return ret;
        }//end

        public XML  getReceivableXGameGiftDefinitionByItemName (String param1 )
        {
            XML ret ;
            XMLList result ;
            String itemName = param1;
            int _loc_4 =0;
            XMLList _loc_5 = this.m_rawXml.child("xGameGiftingReceivableItems").child("game").child("item");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);
                with (_loc_6)
                {
                    if (attribute("name") == itemName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            result = _loc_3;
            if (result.length() > 0)
            {
                ret = result.get(0);
            }
            return ret;
        }//end

        public void  setupSaleAndNewItems ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            boolean _loc_4 =false ;
            Item _loc_5 =null ;
            boolean _loc_6 =false ;
            String _loc_7 =null ;
            int _loc_8 =0;
            XMLList _loc_1 = this.m_rawXml.newitems.item ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_5 = this.getItemByName(_loc_2.@name);
                _loc_6 = true;
                if (_loc_5.experiments)
                {
                    for(int i1 = 0; i1 < _loc_5.experiments.size(); i1++)
                    {
                    	_loc_7 = _loc_5.experiments.get(i1);

                        _loc_8 = Global.experimentManager.getVariant(_loc_7);
                        if (((Array)_loc_5.experiments.get(_loc_7)).indexOf(_loc_8) < 0)
                        {
                            _loc_6 = false;
                            break;
                        }
                    }
                }
                if (_loc_6)
                {
                    this.m_newItems.push(_loc_5);
                }
            }
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STARTER_PACK2);
            _loc_4 = _loc_3 > 0 && Global.player.hasMadeRealPurchase == false;
            if (_loc_4 == true)
            {
                Array _loc_9 = (Array)this.m_itemsByType.get( "starter_pack2_"+_loc_3) ;
                if(_loc_9 != null) {
                	this.m_newItems = _loc_9.concat(this.m_newItems);
                }
            }
            return;
        }//end

        public Array  getPlaceableItems (String param1 )
        {
            type = param1;
            return this .getItemsByType (type ).filter (boolean  (Item param11 ,...args )
            {
                return param11.placeable;
            }//end
            );
        }//end

        public Array  getMarketItems ()
        {
            XML _loc_3 =null ;
            XMLList _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            Item _loc_7 =null ;
            XML _loc_8 =null ;
            Item _loc_9 =null ;
            String _loc_10 =null ;
            Array _loc_11 =null ;
            int _loc_12 =0;
            Array _loc_1 =new Array ();
            _loc_2 = this.m_rawXml.market.marketItems.category ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.item;
                _loc_5 = _loc_3.@name;
                if (_loc_5 == "expansion")
                {
                    _loc_6 = this.getItemsByType("expansion");
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    	_loc_7 = _loc_6.get(i0);

                        _loc_7.category.push(_loc_5);
                        _loc_1.push(_loc_7);
                    }
                    continue;
                }
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_8 = _loc_4.get(i0);

                    _loc_9 = this.getItemByName(_loc_8.@name);
                    if (_loc_9.buyable)
                    {
                        _loc_9.category.push(_loc_5);
                        _loc_10 = _loc_8.@keywords;
                        _loc_11 = _loc_10.split(",");
                        if (_loc_3.@name == "specials")
                        {
                            _loc_9.isNew = true;
                        }
                        _loc_12 = 0;
                        while (_loc_12 < _loc_11.length())
                        {

                            if (_loc_11.get(_loc_12) != "plot_contract" && _loc_11.get(_loc_12) != "ship_contract" && _loc_11.get(_loc_12) != "")
                            {
                                _loc_11.put(_loc_12,  ZLoc.t("Market", _loc_11.get(_loc_12)));
                            }
                            _loc_12++;
                        }
                        _loc_9.marketKeywords = _loc_11;
                        _loc_1.push(_loc_9);
                    }
                }
            }
            return _loc_1;
        }//end

        public Array  getBuyableItems (String param1 )
        {
            type = param1;
            return this .getItemsByType (type ).filter (boolean  (Item param11 ,...args )
            {
                return param11.buyable;
            }//end
            );
        }//end

        public XML  getMarketPromoXML ()
        {
            return this.m_rawXml.market.marketPromos.get(0);
        }//end

        public Array  getCurrentBuyableItems (String param1 ,boolean param2 =false )
        {
            Item item ;
            String type = param1;
            boolean ignoreExcludeExperiments = param2;
            Array param3 =this.getItemsByType(type);

            if(param3 == null) {
            	param3 = new Array();
            }

            initArray = param3.filter(function(Item param10,...args)
            {
                String argsvalue ="";
                _loc_4 = null;


                if (!param10.buyable)
                {
                    return false;
                }

               /*
                if (param10.noMarket)
                {
                    return false;
                }

                if (!param10.buyable)
                {
                    return false;
                }



                if (!ignoreExcludeExperiments && param10.excludeExperiments)
                {
                    for(int i0 = 0; i0 < param10.excludeExperiments.size(); i0++)
                    {
                    	argsvalue = param10.excludeExperiments.get(i0);

                        _loc_4 = Global.experimentManager.getVariant(argsvalue);
                        if (((Array)param10.excludeExperiments.get(argsvalue)).indexOf(_loc_4) >= 0)
                        {
                            return false;
                        }
                    }
                }

                if (param10.experiments)
                {
                    for(int i0 = 0; i0 < param10.experiments.size(); i0++)
                    {
                    	argsvalue = param10.experiments.get(i0);

                        _loc_4 = Global.experimentManager.getVariant(argsvalue);
                        if (((Array)param10.experiments.get(argsvalue)).indexOf(_loc_4) < 0)
                        {
                            return false;
                        }
                    }
                }
                */
                return true;
            }//end
            );

            Debug.debug6("GameSettingsInit.getCurrentBuyableItems.3. "+initArray.length());

            Array nonLimitedArray =new Array ();
            int itemIdx ;

            while (itemIdx < initArray.length())
            {

                item = initArray.get(itemIdx);
                //if (this.isCurrentItem(item) && this.notExceedingCap(item))
                //{
                   item.m_buyable = true;
                   item.m_noMarket = false;

                    nonLimitedArray.push(item);
                //}
                Debug.debug6("getCurrentBuyableItems.4 " + item.name);
                itemIdx = (itemIdx + 1);
            }
            Debug.debug6("GameSettingsInit.getCurrentBuyableItems.5. "+nonLimitedArray.length());

            return nonLimitedArray;
        }//end

        public boolean  notExceedingCap (Item param1 )
        {
            if (param1.buildingCap == 0)
            {
                return true;
            }
            _loc_2 =Global.world.getObjectsByNames(.get(param1.name) );
            return param1.buildingCap > _loc_2.length;
        }//end

        public boolean  isCurrentItem (Item param1 )
        {
            boolean _loc_2 =true ;
            _loc_3 = GlobalEngine.getTimer ();
            if (isNaN(param1.startDate) && isNaN(param1.endDate))
            {
                return true;
            }
            if (param1.startDate && !isNaN(param1.startDate) && param1.startDate.toString() != "")
            {
                if (_loc_3 < Number(param1.startDate))
                {
                    _loc_2 = false;
                }
            }
            if (!isNaN(param1.endDate) && getCurrentTime() >= Number(param1.endDate))
            {
                _loc_2 = false;
            }
            return _loc_2;
        }//end

        public XMLList  getSpecialItems ()
        {
            XML _loc_4 =null ;
            XMLList _loc_1 =new XMLList ();
            int _loc_2 =0;
            _loc_3 = this.getItems ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (this.isSpecialItem(_loc_4))
                {
                    _loc_1.put(_loc_2++,  _loc_4);
                }
            }
            return _loc_1;
        }//end

        public XMLList  getAutomationMenuItems ()
        {
            XMLList _loc_1 = this.m_rawXml.child("automationMenu").child("automation");
            return _loc_1;
        }//end

        public Array  getRegenerableResources ()
        {
            XML _loc_3 =null ;
            Array _loc_1 =new Array();
            _loc_2 = this.m_rawXml.regenerableResources.regenerableResource ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1.push(this.getRegenerableResourceByName(_loc_3.attribute("name").toString()));
            }
            return _loc_1;
        }//end

        public RegenerableResource  getRegenerableResourceByName (String param1 )
        {
            RegenerableResource result ;
            XMLList resources ;
            XML resource ;
            String resourceName = param1;
            result = null;
            int _loc_4 =0;
            XMLList _loc_5 = this.m_rawXml.regenerableResources.regenerableResource ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);
                with (_loc_6)
                {
                    if (attribute("name") == resourceName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            resources = _loc_3;
            if (resources.length() > 0)
            {
                resource = resources.get(0);
                result = new RegenerableResource();
                result.name = resource.attribute("name").toString();
                result.regenAmount = parseInt(resource.attribute("regenAmount"));
                result.regenInterval = parseInt(resource.attribute("regenInterval"));
                result.regenCap = parseInt(resource.attribute("regenCap"));
                result.softCap = parseInt(resource.attribute("softCap"));
            }
            return result;
        }//end

        public boolean  isSpecialItem (XML param1 )
        {
            boolean _loc_2 =false ;
            if (param1.@buyable && param1.@buyable.toString() == "true")
            {
                if (param1.lootTable && param1.lootTable.toString() != "")
                {
                    _loc_2 = true;
                }
                else if (param1.limitedStart && param1.limitedStart.toString() != "")
                {
                    _loc_2 = true;
                }
                else if (param1.@marketSpecial && param1.@marketSpecial.toString() != "")
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public XMLList  getMenuItems ()
        {
            XMLList _loc_1 = this.m_rawXml.marketmenu.menuitem ;
            return _loc_1;
        }//end

        public XMLList  getMarketCategories ()
        {
            XMLList _loc_1 = this.m_rawXml.market.marketItems.category ;
            return _loc_1;
        }//end

        public Array  getBuyableCategories (String param1 )
        {
            XML _loc_4 =null ;
            String _loc_5 =null ;
            XMLList _loc_2 = this.m_rawXml.marketmenu.menuitem ;
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                if (_loc_4.@type == param1)
                {
                    _loc_5 = _loc_4.@alsoBuys;
                }
            }
            if (_loc_5 && _loc_5 != "")
            {
                _loc_3 = _loc_5.split(",");
            }


            return _loc_3;
        }//end

        public XMLList  getLimitedIcons ()
        {
            XMLList _loc_1 = this.m_rawXml.marketmenu.limitedIcons ;
            return _loc_1;
        }//end

        public XMLList  getMarketNewIcons ()
        {
            XMLList _loc_1 = this.m_rawXml.marketmenu.overrideNewIcons ;
            return _loc_1;
        }//end

        public XMLList  getPlayerClasses ()
        {
            return this.m_rawXml.playerClasses.playerClass;
        }//end

        public XMLList  getSubMenuItemsByMenuType (String param1 )
        {
            XMLList results ;
            String menuType = param1;
            int _loc_4 =0;
            XMLList _loc_5 = this.m_rawXml.marketmenu.menuitem ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == menuType)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            results = _loc_3.submenuitem;
            return results;
        }//end

        public XMLList  getMissionByType (String param1 )
        {
            String missionType = param1;
            int _loc_4 =0;
            XMLList v_loc_5 = this.m_rawXml.missions.mission ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == missionType)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            return _loc_3;
        }//end

        public IThemeDefinition  getThemeByNames (Vector param1 .<String >)
        {
            Vector _loc_2.<XML >=null ;
            String _loc_3 =null ;
            if (this.m_themeItems.get(this.getThemesKey(param1)) == null)
            {
                _loc_2 = new Vector<XML>();
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_3 = param1.get(i0);

                    _loc_2.push(this.getThemeXMLByName(_loc_3));
                }
                this.m_themeItems.put(this.getThemesKey(param1),  new ThemeListDefinition(_loc_2));
            }
            return this.m_themeItems.get(this.getThemesKey(param1));
        }//end

        public Item  getItemByNameAndThemes (String param1 ,Vector<Theme > param2)
        {
            Theme _loc_4 =null ;
            IThemeDefinition _loc_5 =null ;
            Item _loc_6 =null ;
            Vector<String> _loc_3 =new Vector<String>();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_4 = param2.get(i0);

                _loc_3.push(_loc_4.name);
            }
            _loc_5 = this.getThemeByNames(_loc_3);
            _loc_6 = _loc_5.getThemeItemByName(param1);
            return _loc_6;
        }//end

        private String  getThemesKey (Vector param1 .<String >)
        {
            return param1.toString();
        }//end

        private XML  getThemeXMLByName (String param1 )
        {
            XML _loc_3 =null ;
            XML _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_rawXml.themes.theme.size(); i0++)
            {
            	_loc_3 = this.m_rawXml.themes.theme.get(i0);

                if (_loc_3.@name == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            return _loc_2;
        }//end

        public void  parseAllSunsets ()
        {
            XML _loc_1 =null ;
            String _loc_2 =null ;
            double _loc_3 =0;
            double _loc_4 =0;
            Sunset _loc_5 =null ;
            for(int i0 = 0; i0 < this.m_rawXml.sunsets.sunset.size(); i0++)
            {
            	_loc_1 = this.m_rawXml.sunsets.sunset.get(i0);

                _loc_2 = _loc_1.@theme;
                _loc_3 = DateFormatter.parseTimeString(_loc_1.endDate);
                _loc_4 = DateFormatter.parseTimeString(_loc_1.startDate);
                _loc_5 = new Sunset(_loc_2, _loc_3, _loc_4);
                if (_loc_5)
                {
                    Global.sunsetManager.addSunset(_loc_5);
                }
            }
            return;
        }//end

        public void  parseAllGlobalValidators ()
        {
            XML _loc_1 =null ;
            GenericValidationScript _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_rawXml.validators.validate.size(); i0++)
            {
            	_loc_1 = this.m_rawXml.validators.validate.get(i0);

                _loc_2 = this.parseValidateTag(_loc_1);
                if (_loc_2)
                {
                    Global.validationManager.addValidator(_loc_2);
                }
            }
            return;
        }//end

        public void  parseAllItems ()
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;

            this.m_itemsXML = new Dictionary();
            if (this.optimizeItemsXML)
            {
                this.m_lastItemXMLIndex = 0;
                for(int i0 = 0; i0 < this.m_rawXml.items.item.size(); i0++)
                {
                	_loc_2 = this.m_rawXml.items.item.get(i0);

                    _loc_3 = String(_loc_2.@name);
                    if (this.m_itemsXML.hasOwnProperty(_loc_3))
                    {
                        this.m_itemsXML.get(_loc_3).push({xml:_loc_2, index:this.m_lastItemXMLIndex});
                    }
                    else
                    {
                        this.m_itemsXML.put(_loc_3,  .get({xml:_loc_2, index:this.m_lastItemXMLIndex}));
                    }
                    this.m_lastItemXMLIndex++;
                }
                delete this.m_rawXml.items;
            }
            _loc_1 = this.getItems ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = String(_loc_2.@name);
                _loc_4 = String(_loc_2.@type);

                if (!this.m_items.hasOwnProperty(_loc_3))
                {
			if(_loc_4 == "decoration") {
				Debug.debug7("Decoration. " +_loc_3);
			}

			if(_loc_3 == "NPC_farmer") {
				Debug.debug4("GameSettingsInit.parseAllItems.NPC_mailman"+_loc_2);
				this.m_items.put(_loc_3,  new Item(_loc_2,true));

			} else {
                    		this.m_items.put(_loc_3,  new Item(_loc_2));
                    	}
                }
            }
            return;
        }//end

        public Item  getItemByName (String param1 )
        {
            XML _loc_3 =null ;
            _loc_2 = this.m_items.get(param1) ;
            if (_loc_2 == null)
            {
                _loc_3 = this.getItemXMLByName(param1);
                if (_loc_3 != null)
                {
			if(param1 == "NPC_mailman") {
			    Debug.debug4("GameSettingsInit.getItemByName.NPC_mailman"+_loc_3);
	                    _loc_2 = new Item(_loc_3,true);

			} else {
                    		_loc_2 = new Item(_loc_3);
                    	}
                    this.m_items.put(param1,  _loc_2);
                }
            }
            return _loc_2;
        }//end

        public Array  getItemList (XMLList param1 ,Object param2 )
        {
            Dictionary _loc_4 =null ;
            XML _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_3 =null ;
            if (param1 && param1.length() > 0)
            {
                _loc_4 = new Dictionary();
                for(int i0 = 0; i0 < param1.attributes().size(); i0++)
                {
                	_loc_5 = param1.attributes().get(i0);

                    _loc_4.put(String(_loc_5.name()),  String(_loc_5));
                }
                if (param2)
                {
                    for(int i0 = 0; i0 < param2.size(); i0++)
                    {
                    	_loc_6 = param2.get(i0);

                        _loc_4.put(_loc_6,  param2.put(_loc_6, _loc_4.put(_loc_6,  param2.get(_loc_6)));
                    }
                }
                _loc_3 = ItemListGenerator.generate(String(param1.@generator), _loc_4);
            }
            return _loc_3;
        }//end

        public XML  getEffectByName (String param1 )
        {
            XMLList list ;
            XML result ;
            effectName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.effects.effect ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            Debug.debug5("GameSettingsInit.getEffectByName"+param1);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                with (_loc_6)
                {
                    if (@name == effectName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            if (list.length() > 0)
            {
                result = list.get(0);
            }
            return result;
        }//end

        public XMLList  getScaffoldSetByName (String param1 )
        {
            XMLList list ;
            XMLList result ;
            scaffoldName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.scaffolds.scaffold ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == scaffoldName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.image;
            }
            return result;
        }//end

        public XMLList  getRibbonSetByName (String param1 )
        {
            XMLList list ;
            XMLList result ;
            ribbonName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.ribbons.ribbon ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == ribbonName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.image;
            }
            return result;
        }//end

        public RemodelDefinition Vector  getAllRemodelDefinitionsByItem (String param1 ).<>
        {
            XML _loc_4 =null ;
            RemodelDefinition _loc_5 =null ;
            Vector<RemodelDefinition> _loc_2 =new Vector<RemodelDefinition>();
            _loc_3 = this.getItemXMLByName(param1 );
            if (_loc_3)
            {
                for(int i0 = 0; i0 < _loc_3..remodel.size(); i0++)
                {
                	_loc_4 = _loc_3..remodel.get(i0);

                    _loc_5 = RemodelDefinition.parse(_loc_4, param1);
                    if (_loc_5)
                    {
                        _loc_2.push(_loc_5);
                    }
                }
            }
            return _loc_2;
        }//end

        public RemodelDefinition  getRemodelDefinitionByName (String param1 ,String param2 )
        {
            RemodelDefinition def ;
            XMLList remodels ;
            XMLList matches ;
            XML remodelXml ;
            itemName = param1;
            remodelItemName = param2;
            itemXml = this.getItemXMLByName(itemName);
            if (itemXml)
            {
                remodels = itemXml..remodel;
                int _loc_5 =0;
                _loc_6 = remodels;
                XMLList _loc_4 =new XMLList("");
                Object _loc_7;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);


                    with (_loc_7)
                    {
                        if (attribute("item") == remodelItemName)
                        {
                            _loc_4.put(_loc_5++,  _loc_7);
                        }
                    }
                }
                matches = _loc_4;


                for(int i0 = 0; i0 < matches.size(); i0++)
                {
                	remodelXml = matches.get(i0);


                    def = RemodelDefinition.parse(remodelXml, itemName);
                }
            }
            return def;
        }//end

        public MatchmakingConfig  getMatchmakingConfig ()
        {
            MatchmakingConfig _loc_1 =null ;
            _loc_2 = this.m_rawXml.matchmakingConfig ;
            if (_loc_2.length() > 0)
            {
                _loc_1 = new MatchmakingConfig(_loc_2.get(0));
            }
            return _loc_1;
        }//end

        public GenericValidationScript  parseValidateTag (XML param1 )
        {
            GenericValidationScript _loc_3 =null ;
            GenericValidationConditionSet _loc_4 =null ;
            XML _loc_5 =null ;
            _loc_2 = param1.@name;
            if (_loc_2)
            {
                _loc_3 = new GenericValidationScript(_loc_2);
                _loc_4 = new GenericValidationConditionSet(GenericValidationConditionSet.EVALUATE_OR);
                for(int i0 = 0; i0 < param1.children().size(); i0++)
                {
                	_loc_5 = param1.children().get(i0);

                    this.parseValidateTagCondition(_loc_5, _loc_4);
                }
                _loc_3.conditionSet = _loc_4;
                return _loc_3;
            }
            return null;
        }//end

        protected void  parseValidateTagCondition (XML param1 ,GenericValidationConditionSet param2 )
        {
            GenericValidationConditionSet _loc_4 =null ;
            XML _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            Object _loc_8 =null ;
            XML _loc_9 =null ;
            GenericValidationCondition _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_3 =param1.name ();
            if (_loc_3 == "func")
            {
                _loc_8 = {};
                for(int i0 = 0; i0 < param1.attributes().size(); i0++)
                {
                	_loc_9 = param1.attributes().get(i0);

                    _loc_11 = _loc_9.name();
                    _loc_12 = param1.attribute(_loc_11);
                    switch(_loc_11)
                    {
                        case "_class":
                        {

                            _loc_6 = _loc_12;
                            break;
                        }
                        case "_name":
                        {

                            _loc_7 = _loc_12;
                            break;
                        }
                        default:
                        {

                            _loc_8.put(_loc_11,  _loc_12);
                            break;
                        }
                    }
                }
                _loc_10 = new GenericValidationCondition(_loc_6, _loc_7, _loc_8);
                param2.add(_loc_10);
                return;
            }


            switch(_loc_3)
            {
                case "anyOf":
                {
                    _loc_4 = new GenericValidationConditionSet(GenericValidationConditionSet.EVALUATE_OR);
                    break;
                }
                case "allOf":
                {
                    _loc_4 = new GenericValidationConditionSet(GenericValidationConditionSet.EVALUATE_AND);
                    break;
                }
                default:
                {
                    break;
                }
            }
            for(int i0 = 0; i0 < param1.children().size(); i0++)
            {
            	_loc_5 = param1.children().get(i0);

                this.parseValidateTagCondition(_loc_5, _loc_4);
            }
            if (_loc_4)
            {
                param2.add(_loc_4);
            }
            return;
        }//end

        public Script  parseScriptTag (XML param1 ,IScriptingContext param2 )
        {
            XML _loc_4 =null ;
            XMLList _loc_5 =null ;
            String _loc_6 =null ;
            Function _loc_7 =null ;
            Object _loc_8 =null ;
            XML _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            Script _loc_3 =new Script(param2 );
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.condition.size(); i0++)
                {
                	_loc_4 = param1.condition.get(i0);

                    _loc_5 = _loc_4.attributes();
                    _loc_6 = _loc_4.name().localName;
                    switch(_loc_6)
                    {
                        case "condition":
                        {
                            _loc_7 = Global.scriptingManager.getConditionFunction(_loc_4.@eval);
                            if (_loc_7 == null)
                            {
                                break;
                            }
                            _loc_8 = {};
                            for(int i0 = 0; i0 < _loc_5.size(); i0++)
                            {
                            	_loc_9 = _loc_5.get(i0);

                                _loc_10 = _loc_9.name();
                                _loc_11 = _loc_4.attribute(_loc_10);
                                if (_loc_10 != "eval")
                                {
                                    _loc_8.put(_loc_10,  _loc_11);
                                }
                            }
                            _loc_3.addCondition(_loc_4.@eval, _loc_8);
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public XMLList  getReactivate ()
        {
            XMLList _loc_1 =null ;
            if (this.m_rawXml.hasOwnProperty("reactivate"))
            {
                _loc_1 = this.m_rawXml.reactivate;
            }
            return _loc_1;
        }//end

        public XMLList  getReactivateGroups ()
        {
            XMLList _loc_1 =null ;
            if (this.m_rawXml.hasOwnProperty("reactivate"))
            {
                if (this.m_rawXml.reactivate.hasOwnProperty("groups"))
                {
                    _loc_1 = this.m_rawXml.reactivate.groups;
                }
            }
            return _loc_1;
        }//end

        public XMLList  getReactivateAllowedActions ()
        {
            XMLList _loc_2 =null ;
            XMLList _loc_1 =null ;
            if (this.m_rawXml.hasOwnProperty("reactivate"))
            {
                if (this.m_rawXml.reactivate.hasOwnProperty("actions"))
                {
                    _loc_1 = Global.experimentManager.filterXmlByExperiment(this.m_rawXml.reactivate.actions);
                    if (_loc_1.children().length() != 0)
                    {
                        _loc_2 = Global.experimentManager.filterXmlByExperiment(_loc_1.children());
                        _loc_1.setChildren(_loc_2);
                    }
                }
            }
            return _loc_1;
        }//end

        public XMLList  getAnnouncers ()
        {
            return this.m_rawXml.child("announcers").child("announcer");
        }//end

        public Array  getAnnouncements ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            Object _loc_4 =null ;
            XML _loc_5 =null ;
            XML _loc_6 =null ;
            XML _loc_7 =null ;
            XML _loc_8 =null ;
            Script _loc_9 =null ;
            AnnouncementData _loc_10 =null ;
            XML _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_13 =null ;
            Object _loc_14 =null ;
            XML _loc_15 =null ;
            XMLList _loc_16 =null ;
            Object _loc_17 =null ;
            XML _loc_18 =null ;
            Object _loc_19 =null ;
            XML _loc_20 =null ;
            String _loc_21 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_rawXml.announcements.announcement.size(); i0++)
            {
            	_loc_2 = this.m_rawXml.announcements.announcement.get(i0);

                _loc_3 = _loc_2.dialog.get(0);
                _loc_4 = {};
                for(int i0 = 0; i0 < _loc_3.attributes().size(); i0++)
                {
                	_loc_5 = _loc_3.attributes().get(i0);

                    _loc_12 = _loc_5.name();
                    _loc_13 = _loc_5;
                    _loc_4.put(_loc_12,  _loc_13);
                }
                if (_loc_3.closeCallback.length() > 0)
                {
                    _loc_14 = new Object();
                    _loc_14.name = String(_loc_3.closeCallback.@name);
                    _loc_14.params = new Array();
                    for(int i0 = 0; i0 < _loc_3.closeCallback.param.size(); i0++)
                    {
                    	_loc_15 = _loc_3.closeCallback.param.get(i0);

                        _loc_14.params.push(String(_loc_15));
                    }
                    _loc_4.put("closeCallback",  _loc_14);
                }
                if (_loc_3.child("params").child("param").length() > 0)
                {
                    _loc_16 = _loc_3.child("params").child("param");
                    _loc_17 = {};
                    for(int i0 = 0; i0 < _loc_16.size(); i0++)
                    {
                    	_loc_18 = _loc_16.get(i0);

                        _loc_19 = {};
                        for(int i0 = 0; i0 < _loc_18.attributes().size(); i0++)
                        {
                        	_loc_20 = _loc_18.attributes().get(i0);

                            _loc_19.put(_loc_20.name().toString(),  _loc_20.toString());
                        }
                        _loc_17.put(_loc_18.attribute("name"),  _loc_19);
                    }
                    _loc_4.put("params",  _loc_17);
                }
                _loc_7 = _loc_3.viewTracking.get(0);
                if (_loc_7 != null)
                {
                    _loc_6 = _loc_7.children().get(0);
                    if (_loc_6 != null)
                    {
                        _loc_4.put("viewTracking",  StatTrackerFactory.fromXML(_loc_6));
                    }
                }
                _loc_8 = _loc_3.closeTracking.get(0);
                if (_loc_8 != null)
                {
                    _loc_6 = _loc_8.children().get(0);
                    if (_loc_6 != null)
                    {
                        _loc_4.put("closeTracking",  StatTrackerFactory.fromXML(_loc_6));
                    }
                }
                _loc_9 = null;
                _loc_10 = new AnnouncementData(_loc_2.@id, _loc_4, _loc_2.@priority);
                if (_loc_2.attribute("validate").length() > 0)
                {
                    _loc_9 = new Script(_loc_10);
                    _loc_21 = _loc_2.attribute("validate").toString();
                    _loc_9.addValidator(_loc_21);
                    _loc_10.attachScript(_loc_9);
                }
                for(int i0 = 0; i0 < _loc_2.script.size(); i0++)
                {
                	_loc_11 = _loc_2.script.get(i0);

                    _loc_9 = this.parseScriptTag(_loc_11, _loc_10);
                    _loc_10.attachScript(_loc_9);
                }
                _loc_1.push(_loc_10);
            }
            return _loc_1;
        }//end

        public Array  getSaleDataByExperiment (String param1 )
        {
            Array result ;
            XMLList allSalesXml ;
            XMLList typeXml ;
            XML saleXml ;
            Object sale ;
            XML attr ;
            Array featuredSales ;
            XML pkgXml ;
            String prop ;
            String val ;
            Object pkgData ;
            saleType = param1;
            result = new Array();
            allSalesXml = Global.experimentManager.filterXmlByExperiment(this.m_rawXml.sales.sale);
            int _loc_4 =0;
            _loc_5 = allSalesXml;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == saleType)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            typeXml = _loc_3;
            int _loc_31 =0;
            _loc_41 = typeXml;
            for(int i0 = 0; i0 < typeXml.size(); i0++)
            {
            	saleXml = typeXml.get(i0);


                sale = new Object();

                for(int i0 = 0; i0 < saleXml.attributes().size(); i0++)
                {
                	attr = saleXml.attributes().get(i0);


                    prop = attr.name();
                    val = attr;
                    sale.put(prop,  val);
                }
                if (!sale.hasOwnProperty("priority"))
                {
                    sale.priority = 0;
                }
                sale.packageSetId = parseInt(saleXml.pkgSetId.get(0).toString());
                featuredSales = new Array();

                for(int i0 = 0; i0 < saleXml.featuredSaleData.pkg.size(); i0++)
                {
                	pkgXml = saleXml.featuredSaleData.pkg.get(i0);


                    pkgData = new Object();

                    for(int i0 = 0; i0 < pkgXml.attributes().size(); i0++)
                    {
                    	attr = pkgXml.attributes().get(i0);


                        prop = attr.name();
                        val = attr;
                        pkgData.put(prop,  val);
                    }
                    featuredSales.push(pkgData);
                }
                sale.featuredSales = featuredSales;
                result.push(sale);
            }
            return result;
        }//end

        public XMLList  saleSettingsXML ()
        {
            return this.m_rawXml.sales.saleSettings;
        }//end

        protected XMLList  getValidFriendBarConfigsXML ()
        {
            XMLList _loc_1 =new XMLList ();
            if (this.m_rawXml && this.m_rawXml.friendBarConfig)
            {
                _loc_1 = Global.experimentManager.filterXmlByExperiment(this.m_rawXml.friendBarConfig.config);
            }
            return _loc_1;
        }//end

        public FriendBarSortHelper  getFriendBarSortHelperByType (String param1 )
        {
            FriendBarSortHelper helper ;
            XML configXml ;
            XMLList sortConfigs ;
            XML sortConfig ;
            int maxResults ;
            String validateOn ;
            XML sortHelperXml ;
            Object args ;
            int priority ;
            XML attr ;
            typeName = param1;
            configs = this.getValidFriendBarConfigsXML();
            int _loc_3 =0;
            _loc_4 = configs;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	configXml = _loc_4.get(i0);


                int _loc_6 =0;
                _loc_7 = configXml.sort;
                XMLList _loc_5 =new XMLList("");
                Object _loc_8;
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                	_loc_8 = _loc_7.get(i0);


                    with (_loc_8)
                    {
                        if (attribute("type") == typeName)
                        {
                            _loc_5.put(_loc_6++,  _loc_8);
                        }
                    }
                }
                sortConfigs = _loc_5;
                if (sortConfigs.length() > 0)
                {
                    sortConfig = sortConfigs.get(0);
                    maxResults = this.getXmlAttribute(sortConfig, "maxResults", -1);
                    validateOn = this.getXmlAttribute(sortConfig, "validateOn", null);
                    if (!helper)
                    {
                        helper = new FriendBarSortHelper(maxResults, validateOn);
                    }
                    int _loc_51 =0;
                    _loc_61 = sortConfig.sortHelper;
                    for(int i0 = 0; i0 < _loc_61.size(); i0++)
                    {
                    	sortHelperXml = _loc_61.get(i0);


                        if (sortHelperXml.attribute("name"))
                        {
                            args = new Object();
                            priority = this.getXmlAttribute(sortHelperXml, "priority", 0);


                            for(int i0 = 0; i0 < sortHelperXml.attributes().size(); i0++)
                            {
                            	attr = sortHelperXml.attributes().get(i0);


                                args.put(String(attr.name),  String(attr));
                            }
                            helper.addCompareHelper(sortHelperXml.@name, priority, args);
                        }
                    }
                }
            }
            return helper;
        }//end

        public Array  getFriendBadgesByPriority ()
        {
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            Array _loc_1 =new Array ();
            _loc_2 = this.getValidFriendBarConfigsXML ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                for(int i0 = 0; i0 < _loc_3.badge.size(); i0++)
                {
                	_loc_4 = _loc_3.badge.get(i0);

                    if (_loc_4.attribute("name"))
                    {
                        _loc_5 = this.getXmlAttribute(_loc_4, "asset", null);
                        _loc_6 = this.getXmlAttribute(_loc_4, "priority", 0);
                        _loc_7 = this.getXmlAttribute(_loc_4, "validateOn", null);
                        _loc_1.push(new FriendBadgeConfig(_loc_4.@type, _loc_5, _loc_6, _loc_7));
                    }
                }
                if (_loc_1.length > 1)
                {
                    _loc_1.sortOn("priority", Array.NUMERIC | Array.DESCENDING);
                }
            }
            return _loc_1;
        }//end

        public int  getCitySamVersion ()
        {
            XML _loc_2 =null ;
            _loc_1 = this.getValidFriendBarConfigsXML ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (this.getXmlAttribute(_loc_2, "name") == "themed_citysam")
                {
                    return this.getXmlAttribute(_loc_2, "version", -1);
                }
            }
            return -1;
        }//end

        public String  getCitySamNeighborCard ()
        {
            XML _loc_2 =null ;
            _loc_1 = this.getValidFriendBarConfigsXML ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (this.getXmlAttribute(_loc_2, "name") == "themed_citysam")
                {
                    return _loc_2.child("card").attribute("asset");
                }
            }
            return null;
        }//end

        public String  getCitySamPanToObjectId ()
        {
            XML _loc_2 =null ;
            _loc_1 = this.getValidFriendBarConfigsXML ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (this.getXmlAttribute(_loc_2, "name") == "themed_citysam")
                {
                    return _loc_2.child("card").attribute("panto");
                }
            }
            return null;
        }//end

        public Array  getXpromos ()
        {
            XML _loc_2 =null ;
            Object _loc_3 =null ;
            XML _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_1 =new Array();
            for(int i0 = 0; i0 < this.m_rawXml.inGameXpromos.xpromo.size(); i0++)
            {
            	_loc_2 = this.m_rawXml.inGameXpromos.xpromo.get(i0);

                _loc_3 = {};
                for(int i0 = 0; i0 < _loc_2.attributes().size(); i0++)
                {
                	_loc_4 = _loc_2.attributes().get(i0);

                    _loc_5 = String(_loc_4.name());
                    _loc_6 = String(_loc_4);
                    _loc_3.put(_loc_5,  _loc_6);
                }
                _loc_1.push(_loc_3);
            }
            return _loc_1;
        }//end

        public XML  getCollectionXMLByName (String param1 )
        {
            XML result ;
            XMLList list ;
            collectionName = param1;
            result;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.collections.collection ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(collectionName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            if (list.length())
            {
                result = list.get(0);
            }
            return result;
        }//end

        public XML  getItemXMLByCode (String param1 )
        {
            XMLList itemsXML ;
            XML itemXML ;
            String itemCode ;
            XMLList list ;
            code = param1;
  	    int _loc_4 ;
	   XMLList _loc_3 ;

            if (this.optimizeItemsXML)
            {
                itemsXML = this.getItems();


                for(int i0 = 0; i0 < itemsXML.size(); i0++)
                {
                	itemXML = itemsXML.get(i0);


                    itemCode = String(itemXML.@code);
                    if (itemCode === code)
                    {
                        return itemXML;
                    }
                }
            }
            else
            {
                _loc_4 = 0;
                _loc_5 = this.m_rawXml.items.item ;
                XMLList _loc_31 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@code == code)
                        {
                            _loc_31.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                list = _loc_31;
                if (list.length())
                {
                    return list.get(0);
                }
            }
            return null;
        }//end

        public Item  getItemByCode (String param1 )
        {
            Item _loc_2 =null ;
            XML _loc_3 =null ;
            if (param1 != null)
            {
                _loc_3 = this.getItemXMLByCode(param1);
                if (_loc_3 != null)
                {
                    _loc_2 = new Item(_loc_3);
                }
            }
            return _loc_2;
        }//end

        private String  getDescendentNameFromItemXML (XML param1 )
        {
            _loc_2 = param1.child("upgrade").attribute("item");
            return _loc_2.length() > 0 ? (_loc_2.get(0).toString()) : (null);
        }//end

        public String  getItemProgenitorName (String param1 )
        {
            XMLList itemsXML ;
            XML itemXML ;
            String descendantName ;
            XMLList ancestorNames ;
            String ancestorName ;
            itemName = param1;
		int _loc_4 =0;
		XMLList _loc_3 =itemsXML ;

            if (this.optimizeItemsXML)
            {
                itemsXML = this.getItems();


                for(int i0 = 0; i0 < itemsXML.size(); i0++)
                {
                	itemXML = itemsXML.get(i0);


                    descendantName = this.getDescendentNameFromItemXML(itemXML);
                    if (descendantName === itemName)
                    {
                        return this.getItemProgenitorName(itemXML.@name);
                    }
                }
            }
            else
            {
                _loc_4 = 0;
                _loc_5 = this.m_rawXml.items.item ;
                XMLList _loc_31 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (child("upgrade").attribute("item") == itemName)
                        {
                            _loc_31.put(_loc_4++,  _loc_6);
                        }
                    }

                }
                ancestorNames = _loc_31.attribute("name");
                if (ancestorNames.length() > 0)
                {
                    ancestorName = ancestorNames.get(0).toString();
                    return this.getItemProgenitorName(ancestorName);
                }
            }
            return itemName;
        }//end

        public Array  getItemNamesInUpgradeHeirarchy (String param1 )
        {
            XML _loc_4 =null ;
            _loc_2 = this.getItemProgenitorName(param1 );
            Array _loc_3 =new Array();
            do
            {

                _loc_3.push(_loc_2);
                _loc_4 = this.getItemXMLByName(_loc_2);
                if (_loc_4 != null)
                {
                    _loc_2 = this.getDescendentNameFromItemXML(_loc_4);
                    continue;
                }
                _loc_2 = null;
            }while (_loc_2)
            return _loc_3;
        }//end

        public Array  getOrderedUpgradeChainByRoot (String param1 )
        {
            Array _loc_4 =null ;
            _loc_2 = this.getItemByName(param1 );
            int _loc_3 =1;
            if (_loc_2)
            {
                _loc_4 = new Array();
                do
                {

                    _loc_4.push(_loc_2.name);
                    _loc_3++;
                    _loc_2 = _loc_2.itemUpgradeDescendantName ? (this.getItemByName(_loc_2.itemUpgradeDescendantName)) : (null);
                }while (_loc_2)
            }
            else
            {
                return null;
            }
            return _loc_4;
        }//end

        public XMLList  getXpromoXML ()
        {
            return this.m_rawXml.xpromos.xpromo;
        }//end

        public XML  getXpromoXMLByName (String param1 )
        {
            XMLList list ;
            source = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.xpromos.xpromo ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@src == source)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            return list.length() == 1 ? (list.get(0)) : (null);
        }//end

        public XMLList  getCommodityXML ()
        {
            return this.m_rawXml.commodities.commodity;
        }//end

        public int  getCommodityMax (String param1 )
        {
            _loc_2 = this.getCommodityXMLByName(param1 );
            return _loc_2 != null ? (int(_loc_2.@max)) : (0);
        }//end

        public int  getCommodityMinLevel (String param1 )
        {
            _loc_2 = this.getCommodityXMLByName(param1 );
            return _loc_2 != null ? (int(_loc_2.@minLevel)) : (0);
        }//end

        public int  getCommodityBonus (String param1 )
        {
            _loc_2 = this.getCommodityXMLByName(param1 );
            return _loc_2 != null ? (int(_loc_2.@bonus)) : (0);
        }//end

        public XML  getCommodityXMLByName (String param1 )
        {
            XMLList list ;
            nameStr = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.commodities.commodity ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == nameStr)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            return list.length() > 0 ? (list.get(0)) : (null);
        }//end

        public XML  getCommodityXMLDefault ()
        {
            XMLList list ;
            int _loc_3 =0;
            _loc_4 = this.m_rawXml.commodities.commodity ;
            XMLList _loc_2 =new XMLList("");
            Object _loc_5;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);


                with (_loc_5)
                {
                    if (@default == "1")
                    {
                        _loc_2.put(_loc_3++,  _loc_5);
                    }
                }
            }
            list = _loc_2;
            return list.length() > 0 ? (list.get(0)) : (null);
        }//end

        public XML  getExpansionXMLByNum (double param1 )
        {
            num = param1;
            XML retVal =this.m_rawXml.expansions.expansion.get(num) ;








            return retVal;

            //return !(_loc_5 in _loc_4) ? (retVal) : (var _loc_4:int = 0, var _loc_5:* = this.m_rawXml.expansions.expansion, var _loc_3:XMLList = new XMLList(""), // Jump to 127, // label, var _loc_6:* = _loc_5[_loc_4], var _loc_7:* = _loc_5[_loc_4], with (_loc_5.get(_loc_4)), if (!(@num == "MAX")) goto 122, _loc_3.put(_loc_4,  _loc_6, // end with, if (_loc_5 in _loc_4) goto 87, _loc_3.get(0)));
        }//end

        public XMLList  getExpansionXML ()
        {
            return this.m_rawXml.expansions.expansion;
        }//end

        public XMLList  getViralXML ()
        {
            return this.m_rawXml.virals.viral;
        }//end

        public XML  getViralXMLByName (String param1 )
        {
            XMLList list ;
            nameStr = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.virals.viral ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == nameStr)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            return list.length() > 0 ? (list.get(0)) : (null);
        }//end

        public XML  getReputationLevelXML (int param1 )
        {
            XMLList list ;
            XML result ;
            level = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.reputation.level ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@num == level)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.get(0);
            }
            return result;
        }//end

        public XML  getLevelXML (int param1 )
        {
            XMLList list ;
            XML result ;
            level = param1;
            int _loc_4 =0;
            _loc_5 = this.getLevelsXML ().level ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@num == level)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.get(0);
            }
            return result;
        }//end

        public XMLList  getLevelsXML ()
        {
            _loc_1 =Global.gameSettings().getInt("levelsCvLevelTop");
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_LEVEL_REGRADE );
            if (_loc_2)
            {
                return this.m_rawXml.child("levels_cv_level_regrade_var_" + _loc_1.toString());
            }
            return this.m_rawXml.levels_cv_level_regrade_var_0;
        }//end

        public XML  getItemXMLByName (String param1 )
        {
            XMLList results ;
            itemName = param1;
            if(param1 == "NPC_mailman") {
            	Debug.debug4("getItemXMLByName."+param1);
            }
            if (this.optimizeItemsXML)
            {
                if (this.m_itemsXML.hasOwnProperty(itemName))
                {
                    return this.m_itemsXML.get(itemName).get(0).xml;
                }
                return null;
            }
            else
            {
                int _loc_4 =0;
                _loc_5 = this.m_rawXml.items.item ;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == itemName)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }

                results = _loc_3;
            }

            return results.length() > 0 ? (results.get(0) as XML) : (null);
        }//end

        public String  getItemFriendlyName (String param1 )
        {
            _loc_2 = ZLoc.t("Main","BadItemName");
            _loc_3 = this.getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.localizedName;
            }
            return _loc_2;
        }//end

        public String  getItemCellView (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemXMLByName(param1 );
            if (_loc_2 && _loc_2.attribute("viewClass"))
            {
                return _loc_2.@viewClass;
            }
            return null;
        }//end

        public String  getImageByName (String param1 ,String param2 )
        {
            XMLList images ;
            XML outerXML ;
            itemName = param1;
            imageName = param2;
            iteminst = this.getItemByName(itemName);
            item = iteminst!= null ? (iteminst.xml) : (null);
            String result ;
            if (item)
            {
                int _loc_5 =0;
                _loc_6 = item.image;
                XMLList _loc_4 =new XMLList("");
                Object _loc_7;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);


                    with (_loc_7)
                    {
                        if (@name == imageName)
                        {
                            _loc_4.put(_loc_5++,  _loc_7);
                        }
                    }
                }
                images = _loc_4;
                if (images.length())
                {
                    outerXML =(XML) images.get(0);
                    if (images.get(0).children().length() > 0)
                    {
                        result = Global.getAssetURL(outerXML.children().get(0).@url);
                    }
                    else
                    {
                        result = Global.getAssetURL(outerXML.@url);
                    }
                }
            }
            return result;
        }//end

        public String  getImageByNameRelativeUrl (String param1 ,String param2 )
        {
            XMLList images ;
            XML outerXML ;
            itemName = param1;
            imageName = param2;
            iteminst = this.getItemByName(itemName);
            item = iteminst!= null ? (iteminst.xml) : (null);
            String result ;
            if (item)
            {
                int _loc_5 =0;
                _loc_6 = item.image;
                XMLList _loc_4 =new XMLList("");
                Object _loc_7;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);


                    with (_loc_7)
                    {
                        if (@name == imageName)
                        {
                            _loc_4.put(_loc_5++,  _loc_7);
                        }
                    }
                }
                images = _loc_4;
                if (images.length())
                {
                    outerXML =(XML) images.get(0);
                    if (images.get(0).children().length() > 0)
                    {
                        result = outerXML.children().get(0).@url;
                    }
                    else
                    {
                        result = outerXML.@url;
                    }
                }
            }
            return result;
        }//end

        public Dictionary  getDisplayedStatsByName (String param1 )
        {
            _loc_2 = this.getItemXMLByName(param1 );
            _loc_3 = _loc_2.displayedStats ;
            return this.parseDisplayedStats(param1, _loc_3);
        }//end

        public Dictionary  parseDisplayedStats (String param1 ,XMLList param2 ,String param3 =null )
        {
            XML _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            GenericValidationScript _loc_9 =null ;
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            Dictionary _loc_5 =new Dictionary ();
            if (_loc_4 && param2)
            {
                for(int i0 = 0; i0 < param2.children().size(); i0++)
                {
                	_loc_6 = param2.children().get(i0);

                    _loc_7 = _loc_6.@validate;
                    if (_loc_7 && _loc_7.length > 0)
                    {
                        _loc_9 = _loc_4.getValidation(_loc_7);
                        if (_loc_9 && !_loc_9.validate(_loc_4))
                        {
                            continue;
                        }
                    }
                    _loc_8 = String(_loc_6.@type);
                    switch(_loc_8)
                    {
                        case "coin":
                        case "xp":
                        case "goods":
                        case "premium_goods":
                        case "energy":
                        {
                            if (_loc_6.@value.length() > 0)
                            {
                                _loc_5.put(_loc_8,  int(_loc_6.@value));
                            }
                            else
                            {
                                _loc_5.put(_loc_8,  Global.player.GetDooberMinimums(_loc_4, _loc_8, param3));
                            }
                            break;
                        }
                        case "population":
                        {
                            if (_loc_6.@value.length() > 0)
                            {
                                _loc_5.put(_loc_8,  int(_loc_6.@value));
                            }
                            else
                            {
                                _loc_5.put(_loc_8,  _loc_4.populationCapYield * Global.gameSettings().getNumber("populationScale", 10));
                            }
                            break;
                        }
                        case "bonus":
                        {
                            if (_loc_5.get(_loc_8) == null)
                            {
                                _loc_5.put(_loc_8,  new Array());
                            }
                            ((Array)_loc_5.get(_loc_8)).push(String(_loc_6.@value));
                            break;
                        }
                        case "picture":
                        {
                            _loc_5.put(_loc_8,  String(_loc_6.@value));
                            break;
                        }
                        case "item":
                        case "collectable":
                        {
                            if (_loc_5.get(_loc_8) == null)
                            {
                                _loc_5.put(_loc_8,  new Object());
                            }
                            _loc_5.get(_loc_8).name = String(_loc_6.@value);
                            _loc_5.get(_loc_8).count = int(_loc_6.@count);
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
            return _loc_5;
        }//end

        public QueuedIcon  getIconByCode (String param1 )
        {
            QueuedIcon _loc_2 =null ;
            _loc_3 = this.getIconXMLByCode(param1 );
            if (_loc_3)
            {
                _loc_2 = new QueuedIcon(_loc_3);
            }
            return _loc_2;
        }//end

        public QueuedIcon  getIconByName (String param1 )
        {
            _loc_2 = this.getIconXMLByName(param1 );
            _loc_3 = _loc_2? (new QueuedIcon(_loc_2)) : (null);
            return _loc_3;
        }//end

        public XML  getIconXMLByCode (String param1 )
        {
            XMLList list ;
            XML result ;
            code = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.icons.icon ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@code == code)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.get(0);
            }
            return result;
        }//end

        public XML  getIconXMLByName (String param1 )
        {
            XMLList list ;
            XML result ;
            iconName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.icons.icon ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == iconName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            result;
            if (list.length())
            {
                result = list.get(0);
            }
            return result;
        }//end

        public Object  getCurrentMOTD ()
        {
            XML _loc_4 =null ;
            Object _loc_1 =null ;
            XMLList _loc_2 =new XMLList ();
            int _loc_3 =0;
            while (_loc_3 < this.m_rawXml.motd.motdItem.length())
            {

                _loc_4 = this.m_rawXml.motd.motdItem.get(_loc_3);
                if (checkDateGate(_loc_4))
                {
                    _loc_4.put("version",  _loc_4.get("@version"));
                    _loc_1 = _loc_4;
                    break;
                }
                _loc_3++;
            }
            return _loc_1;
        }//end

        public boolean  checkDate (String param1 ,String param2 )
        {
            Date _loc_3 =new Date(param1 );
            Date _loc_4 =new Date(param2 );
            Date _loc_5 =new Date ();
            boolean _loc_6 =false ;
            if (_loc_3 < _loc_5 && _loc_5 < _loc_4)
            {
                _loc_6 = true;
            }
            return _loc_6;
        }//end

        private XML  getCollectionXMLByCollectableName (String param1 )
        {
            Object _loc_3 =null ;
            XMLList _loc_4 =null ;
            XML _loc_5 =null ;
            _loc_2 = this.getCollections ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_2.get(_loc_3).collectables.children();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    if (_loc_5.@name == param1)
                    {
                        return _loc_2.get(_loc_3);
                    }
                }
            }
            return null;
        }//end

        public Collection  getCollectionByCollectableName (String param1 )
        {
            _loc_2 = this.getCollectionXMLByCollectableName(param1 );
            if (!_loc_2)
            {
                return null;
            }
            Collection _loc_3 =new Collection(_loc_2 );
            return _loc_3;
        }//end

        public String  getDooberFromType (String param1 ,int param2 ,String param3 =null )
        {
            XML denom ;
            int denomAmount ;
            dooberType = param1;
            amount = param2;
            specialTable = param3;
            XML result ;
            XMLList list ;
            if (specialTable != null)
            {
                list = this.getXMLWithAttribute(this.m_rawXml.dooberDisplayTables.dooberDisplayTable, "special", specialTable).children();
            }
            if (list == null || list.length() <= 0)
            {
                int _loc_6 =0;
                _loc_7 = this.m_rawXml.dooberDisplayTables.dooberDisplayTable ;
                XMLList _loc_51 =new XMLList("");
                Object _loc_8;
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                	_loc_8 = _loc_7.get(i0);


                    with (_loc_8)
                    {
                        if (@type == dooberType)
                        {
                            _loc_51.put(_loc_6++,  _loc_8);
                        }
                    }
                }
                list = _loc_51.children();
            }
            if (list == null || list.length() <= 0)
            {
                return dooberType;
            }
            int _loc_52 =0;
            _loc_62 = list;
            for(int i0 = 0; i0 < list.size(); i0++)
            {
            	denom = list.get(i0);


                denomAmount = parseInt(denom.@amount);
                if (amount >= denomAmount)
                {
                    return denom.@name;
                }
            }
            return dooberType;
        }//end

        public String  getDooberTypeFromItemName (String param1 )
        {
            _loc_2 = this.getItemByName(param1 );
            switch(_loc_2.type)
            {
                case Doober.DOOBER_XP:
                case Doober.DOOBER_COIN:
                case Doober.DOOBER_CASH:
                case Doober.DOOBER_ENERGY:
                case Doober.DOOBER_GOODS:
                case Doober.DOOBER_PREMIUM_GOODS:
                case Doober.DOOBER_REP:
                case Doober.DOOBER_POPULATION:
                case Doober.DOOBER_COLLECTABLE:
                {
                    return _loc_2.type;
                }
                default:
                {
                    return Doober.DOOBER_ITEM;
                    break;
                }
            }
        }//end

        protected XML  getXMLWithAttribute (XMLList param1 ,String param2 ,String param3 )
        {
            XML _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_4 = param1.get(i0);

                if (_loc_4.attribute(param2).length() > 0 && _loc_4.attribute(param2).get(0).toString() == param3)
                {
                    return _loc_4;
                }
            }
            return null;
        }//end

        public int  getTieredInt (String param1 ,int param2 )
        {
            _loc_3 = this.getTieredString(param1 ,param2 );
            return _loc_3 ? (int(_loc_3)) : (0);
        }//end

        public String  getTieredString (String param1 ,int param2 )
        {
            XMLList list ;
            XML tierResult ;
            XML tier ;
            tableName = param1;
            num = param2;
            int _loc_5 =0;
            _loc_6 = this.m_rawXml.tieredValues.tieredValue ;
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (@name == tableName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            list = _loc_4;
            tierResult = list.children().get(0);


            for(int i0 = 0; i0 < list.children().size(); i0++)
            {
            	tier = list.children().get(i0);


                if (num < tier.@num)
                {
                    break;
                    continue;
                }
                tierResult = tier;
            }
            return tierResult.@amount;
        }//end

        public XMLList  getTierXml (String param1 )
        {
            tableName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.tieredValues.tieredValue ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == tableName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            return _loc_3;
        }//end

        public int  getGoldContainerValueByName (String param1 )
        {
            int result ;
            XMLList list ;
            a_name = param1;
            result;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.goldcontainers.goldcontainer ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == a_name)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            if (list.length())
            {
                result = list.@coins;
            }
            return result;
        }//end

        public String  getGoldContainerURLByName (String param1 )
        {
            String result ;
            XMLList list ;
            a_name = param1;
            result;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.goldcontainers.goldcontainer ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == a_name)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            list = _loc_3;
            if (list.length())
            {
                result = list.@url;
            }
            return Global.getAssetURL(result);
        }//end

        public Collection  getCollectionByName (String param1 )
        {
            Collection _loc_2 =new Collection(this.getCollectionXMLByName(param1 ));
            return _loc_2;
        }//end

        public XML  getDailyBonus (int param1 )
        {
            XML xml ;
            consecutiveDay = param1;
            int _loc_4 =0;
            _loc_5 = this.m_rawXml.dailyBonus.consecutiveDay ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@number == String(consecutiveDay))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            xml = _loc_3.get(0);
            return xml;
        }//end

        public int  getDailyBonusMaxRecoveryDays ()
        {
            _loc_1 = this.m_rawXml.dailyBonus.get(0) ;
            _loc_2 = (int)(Number(_loc_1.@maxRecoveryDays));
            return _loc_2;
        }//end

        public int  getDailyBonusMaxStreak ()
        {
            _loc_1 = this.m_rawXml.dailyBonus.consecutiveDay ;
            _loc_2 = _loc_1.length ();
            return _loc_2;
        }//end

        public Dictionary  getValentinesAssets ()
        {
            XML _loc_3 =null ;
            Dictionary _loc_1 =new Dictionary(true );
            _loc_2 = this.m_rawXml.valentines ;
            for(int i0 = 0; i0 < _loc_2.children().size(); i0++)
            {
            	_loc_3 = _loc_2.children().get(i0);

                _loc_1.put(_loc_3.name(),  _loc_3.image);
            }
            return _loc_1;
        }//end

        public Object  getXmlData (String param1 )
        {
            if (this.m_parsedData.get(param1) == null)
            {
                this.m_parsedData.put(param1,  XMLParser.parseXMLElementRecursively(this.m_rawXml.child(param1)));
            }
            return this.m_parsedData.get(param1);
        }//end

        public Object  getTicketMQConfig ()
        {
            return this.getXmlData("ticketMQ");
        }//end

        public Object  getTicketMQConfigForTheme (String param1 )
        {
            Object _loc_2 =null ;
            _loc_3 = this.getTicketMQConfig ();
            if (_loc_3)
            {
                _loc_2 = _loc_3.get(param1);
            }
            return _loc_2;
        }//end

        public Object  getHubQueueInfo (String param1 )
        {
            _loc_2 = this.getXmlData("preyGroups");
            return _loc_2.get(param1).get("queueInfo");
        }//end

        public Object  getPreyGroupSettings (String param1 )
        {
            _loc_2 = this.getXmlData("preyGroups");
            return _loc_2.get(param1);
        }//end

        public Object  getAllPreyGroups ()
        {
            return this.getXmlData("preyGroups");
        }//end

        public Object  getHunterPreyModes (String param1 )
        {
            _loc_2 = this.getXmlData("preyGroups");
            return _loc_2.get(param1).get("modes");
        }//end

        public Object  getPreyData (String param1 ,int param2 )
        {
            _loc_3 = this.getXmlData("preyGroups");
            return _loc_3.get(param1).get("preys").get(param2);
        }//end

        public Object  getPreys (String param1 )
        {
            _loc_2 = this.getXmlData("preyGroups");
            return _loc_2.get(param1).get("preys");
        }//end

        public Array  getHubNPCS (String param1 ,int param2 )
        {
            Object _loc_6 =null ;
            _loc_3 = this.getXmlData("preyGroups");
            _loc_4 = .get("hubs").get(param2).get("npcs");
            Array _loc_5 =new Array ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_6 = _loc_4.get(i0);

                _loc_5.push(_loc_6);
            }
            return _loc_5;
        }//end

        public Dictionary  getPreyGroupDialogAssetURLs (String param1 )
        {
            String _loc_4 =null ;
            _loc_2 = this.getXmlData("preyGroups");
            Dictionary _loc_3 =new Dictionary ();
            for(int i0 = 0; i0 < _loc_2.get(param1).get("dialogInfo").size(); i0++)
            {
            	_loc_4 = _loc_2.get(param1).get("dialogInfo").get(i0);

                _loc_3.put(_loc_4,  _loc_2.get(param1).get("dialogInfo").get(_loc_4));
            }
            return _loc_3;
        }//end

        public String  getHubCaptureNpc (String param1 ,int param2 )
        {
            _loc_3 = this.getXmlData("preyGroups");
            return _loc_3.get(param1).get("hubs").get(param2).get("captureNpc");
        }//end

        public int  getHubLevel (String param1 ,String param2 )
        {
            keyword = param1;
            stationName = param2;
            if (!this.m_memoizers.hasOwnProperty("getHubLevel"))
            {
                this .m_memoizers.put( "getHubLevel", new MemoizationCache (int  (String param11 ,String param21 );
            {
                _loc_4 = null;
                _loc_3 = getXmlData("preyGroups");
                for(int i0 = 0; i0 < _loc_3.get(param11).get("hubs").size(); i0++)
                {
                	_loc_4 = _loc_3.get(param11).get("hubs").get(i0);

                    if (_loc_4.get("name") == param21)
                    {
                        return int(_loc_4.get("level"));
                    }
                }
                return 0;
            }//end
            );
            }
            return this.m_memoizers.get("getHubLevel").get(keyword, stationName);
        }//end

        public int  getHubSlots (String param1 ,int param2 )
        {
            _loc_3 = this.getXmlData("preyGroups");
            return _loc_3.get(param1).get("hubs").get(param2).get("maxHunters");
        }//end

        public String  getHubName (String param1 ,int param2 )
        {
            _loc_3 = this.getXmlData("preyGroups");
            return _loc_3.get(param1).get("hubs").get(param2).get("name");
        }//end

        public Array  getHubNames (String param1 )
        {
            keyword = param1;
            if (!this.m_memoizers.hasOwnProperty("getHubNames"))
            {
                this .m_memoizers.put( "getHubNames", new MemoizationCache (Array  (String param11 );
            {
                _loc_4 = null;
                _loc_2 = getXmlData("preyGroups");
                Array _loc_3 =new Array ();
                for(int i0 = 0; i0 < _loc_2.get(param11).get("hubs").size(); i0++)
                {
                	_loc_4 = _loc_2.get(param11).get("hubs").get(i0);

                    _loc_3.push(_loc_4.get("name"));
                }
                return _loc_3;
            }//end
            );
            }
            return this.m_memoizers.get("getHubNames").get(keyword);
        }//end

        public String  getHubGroupIdForItemName (String param1 )
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            Object _loc_4 =null ;
            Object _loc_5 =null ;
            if (!this.m_hubGroupMap)
            {
                this.m_hubGroupMap = new Object();
                _loc_2 = this.getXmlData("preyGroups");
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    if (_loc_3)
                    {
                        _loc_4 = _loc_2.get(_loc_3);
                        for(int i0 = 0; i0 < _loc_4.get("hubs").size(); i0++)
                        {
                        	_loc_5 = _loc_4.get("hubs").get(i0);

                            this.m_hubGroupMap.get(String(_loc_5.put("name")),  _loc_3);
                        }
                    }
                }
            }
            return this.m_hubGroupMap.get(param1);
        }//end

        public Object  getHubByLevel (String param1 ,int param2 )
        {
            _loc_3 = this.getXmlData("preyGroups");
            return _loc_3.get(param1).get("hubs").get(param2);
        }//end

        public Array  getAllHubGroupIds ()
        {
            Object _loc_3 =null ;
            Array _loc_1 =new Array();
            _loc_2 = this.getXmlData("preyGroups");
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1.push(_loc_3.get("id"));
            }
            return _loc_1;
        }//end

        public XMLList  getPopulationTriggersXML ()
        {
            return this.m_rawXml.populationTriggers.trigger;
        }//end

        public Object  getFeatureDataClasses ()
        {
            if (!this.m_memoizers.hasOwnProperty("getFeatureDataClasses"))
            {
                this .m_memoizers.put( "getFeatureDataClasses", new MemoizationCache (Object  ();
            {
                _loc_1 = getXmlData("featureData").get("feature");
                _loc_2 ={};
                _loc_3 =0;
                while (_loc_3 < _loc_1.length())
                {

                    _loc_2.get(_loc_1.put(_loc_3).name,  _loc_1.get(_loc_3));
                    _loc_3 = _loc_3 + 1;
                }
                return _loc_2;
            }//end
            );
            }
            return this.m_memoizers.get("getFeatureDataClasses").get();
        }//end

        public void  testAllItemAssets ()
        {
            String _loc_3 =null ;
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            Item _loc_6 =null ;
            Array _loc_7 =null ;
            String _loc_8 =null ;
            _loc_9 = undefined;
            ItemImageInstance _loc_10 =null ;
            if (!Config.DEBUG_MODE)
            {
                return;
            }
            this.getItemsByType("test");
            ErrorManager.addError("Now loading all assets.");
            int _loc_1 =0;
            Array _loc_2 =.get(Constants.DIRECTION_SW ,Constants.DIRECTION_SE ,Constants.DIRECTION_NE ,Constants.DIRECTION_NW) ;
            for(int i0 = 0; i0 < this.m_itemsByType.size(); i0++)
            {
            	_loc_3 = this.m_itemsByType.get(i0);

                _loc_4 = new Array();
                switch(_loc_3)
                {
                    case "residence":
                    {
                        _loc_4 = .get("planted", "grown");
                        break;
                    }
                    case "decoration":
                    case "municipal":
                    case "landmark":
                    case "ZooEnclosure":
                    case "garden":
                    case "business":
                    {
                        _loc_4 = .get("static");
                        break;
                    }
                    case "plot":
                    case "ship":
                    {
                        _loc_4 = .get("plowed", "planted", "grown", "withered");
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
                _loc_5 = this.m_itemsByType.get(_loc_3);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);

                    _loc_7 = _loc_4;
                    switch(_loc_6.className)
                    {
                        case "Headquarter":
                        {
                            _loc_7 = .get("base", "floor", "rooftop", "penthouse", "billboard");
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    for(int i0 = 0; i0 < _loc_7.size(); i0++)
                    {
                    	_loc_8 = _loc_7.get(i0);

                        for(int i0 = 0; i0 < _loc_2.size(); i0++)
                        {
                        	_loc_9 = _loc_2.get(i0);

                            _loc_10 = _loc_6.getCachedImage(_loc_8, _loc_9);
                            if (_loc_10 == null && !_loc_6.isCachedImageLoading(_loc_8, _loc_9))
                            {
                                ErrorManager.addError("ERROR missing asset for item " + _loc_6.name + " (" + _loc_6.localizedName + "), state " + _loc_8 + ", dir " + _loc_9);
                                _loc_1++;
                            }
                        }
                    }
                }
            }
            ErrorManager.addError("Asset test finished: " + _loc_1 + " errors found");
            return;
        }//end

        public void  addOrReplaceItemXMLByName (XML param1 )
        {
            String itemName ;
            int index ;
            XMLList list ;
            itemXML = param1;
            Object _loc_6;
            if (this.optimizeItemsXML)
            {
                itemName = String(itemXML.@name);
                if (this.m_itemsXML.hasOwnProperty(itemName))
                {
                    index = this.m_itemsXML.get(itemName).get(0).index;
                    this.m_itemsXML.get(itemName).put(0,  {xml:itemXML, index:index});
                }
                else
                {
             this.m_lastItemXMLIndex++;
                    this.m_itemsXML.put(itemName,  .get({xml:itemXML, index:this.m_lastItemXMLIndex++}));
                }
                this.m_cachedItemsXMLList = null;
            }
            else
            {
                int _loc_4 =0;
                _loc_5 = this.m_rawXml.items.item ;
                XMLList _loc_3 =new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == itemXML.@name)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                list = _loc_3;
                if (list.length())
                {
                    list.put(0,  itemXML);
                }
                else
                {
                    this.m_rawXml.items.appendChild(itemXML);
                }
            }
            return;
        }//end

        public QuestTierConfig  getQuestTierConfig (String param1 )
        {
            XML xml ;
            tierName = param1;
            tierConfig = this.m_questTiers.get(tierName);
            Object _loc_6;
            if (tierConfig == null)
            {
                int _loc_4 =0;
                _loc_5 = this.m_rawXml.questTierConfigs.tierConfig ;
                XMLList _loc_3 =new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == tierName)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                xml = _loc_3.get(0);
                if (xml)
                {
                    tierConfig = new QuestTierConfig(xml);
                    this.m_questTiers.put(tierName,  tierConfig);
                }
            }
            return tierConfig;
        }//end

        public Dictionary  getPickThingsConfig (String param1 )
        {
            XML xml ;
            Array rewards ;
            Dictionary distribution ;
            Array rarities ;
            Array multiply ;
            XML reward ;
            Array pieces ;
            boardName = param1;
            config = this.m_minigameConfigs.get(boardName);
            Object _loc_6;
            if (config == null)
            {
                int _loc_4 =0;
                _loc_5 = this.m_rawXml.pickThingsGames.pickThingsGame ;
                XMLList _loc_3 =new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == boardName)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                xml = _loc_3.get(0);
                if (xml)
                {
                    config = new Dictionary();
                    rewards = new Array();
                    distribution = new Dictionary();
                    rarities = new Array();
                    multiply = new Array();
                    int _loc_31 =0;
                    _loc_41 = xml.rewards.children();
                    for(int i0 = 0; i0 < xml.rewards.children().size(); i0++)
                    {
                    	reward = xml.rewards.children().get(i0);


                        rewards.push(reward.toString());
                        rarities.push(reward.@rarity);
                        multiply.push(reward.@multiply.toString() ? (reward.@multiply.toString()) : ("1"));
                        distribution.put(reward.toString(),  reward.@count.get(0));
                    }
                    config.put("rewards",  rewards);
                    config.put("rarities",  rarities);
                    config.put("multiply",  multiply);
                    config.put("distribution",  distribution);
                    config.put("replayCost",  xml.@replayCost.get(0));
                    config.put("assetPrefix",  xml.@assetPrefix.get(0));
                    config.put("experiment",  xml.@experiment.get(0));
                    config.put("pieceCount",  xml.pieces.piece.length());
                    pieces = new Array();
                    this.m_minigameConfigs.put(boardName,  config);
                }
            }
            return config;
        }//end

        public XMLList  getSagas ()
        {
            return this.m_rawXml.sagas.saga;
        }//end

        public XML  mergeParentXmlIntoItem (XML param1 )
        {
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            XML _loc_5 =null ;
            String _loc_6 =null ;
            XMLList _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            String _loc_10 =null ;
            XMLList _loc_11 =null ;
            _loc_2 = param1.attribute("derivesFrom").toString ();
            if (_loc_2 != "")
            {
                //_loc_3 = Global.gameSettings().getItemByName(_loc_2).xml.copy();
                Item loc_3 =Global.gameSettings().getItemByName(_loc_2 );
                if(loc_3 == null) return null;
                _loc_3 = loc_3.xml;

                //param1 = mergeParentMechanicsIntoChild(param1, _loc_3);
                //for each (_loc_4 in param1.children())
                _loc_5 = mergeParentMechanicsIntoChild(param1, _loc_3);
                for(int i0 = 0; i0 < _loc_5.children().size(); i0++)
                {
                	_loc_4 = _loc_5.children().get(i0);

                    _loc_6 = _loc_4.name().localName;
                    _loc_7 = _loc_3.child(_loc_6);
                    if (_loc_7.length() == 0)
                    {
                        continue;
                    }
                    delete _loc_3.child(_loc_6).get(0);
                }
                for(int i0 = 0; i0 < param1.children().size(); i0++)
                {
                	_loc_4 = param1.children().get(i0);

                    _loc_3.appendChild(_loc_4);
                }
                for(int i0 = 0; i0 < param1.attributes().size(); i0++)
                {
                	_loc_5 = param1.attributes().get(i0);

                    _loc_8 = _loc_5.name().localName;
                    if (_loc_8 != "name" && _loc_3.attribute(_loc_8).length() != 0)
                    {
                        delete _loc_3.get(_loc_8);
                    }
                }
                for(int i0 = 0; i0 < param1.attributes().size(); i0++)
                {
                	_loc_5 = param1.attributes().get(i0);

                    _loc_8 = _loc_5.name().localName;
                    if (_loc_8 != "className" && _loc_8 != "type" && _loc_8 != "code")
                    {
                        _loc_3.put(_loc_8,  _loc_5.toString());
                    }
                }
                if (param1.attribute("doNotInherit").length() > 0)
                {
                    _loc_9 = String(param1.@doNotInherit).split(",");
                    for(int i0 = 0; i0 < _loc_9.size(); i0++)
                    {
                    	_loc_10 = _loc_9.get(i0);

                        _loc_11 = _loc_3.child(_loc_10);
                        if (_loc_11.length() == 0)
                        {
                            continue;
                        }
                        delete _loc_3.child(_loc_10).get(0);
                    }
                }
                param1 = _loc_3;
                return param1;
            }
            return null;
        }//end

        public XML  mergeMechanicPackXmlIntoItem (XML param1 )
        {
            _loc_2 = param1.attribute("mechanicPack").toString ();
            if (_loc_2.length > 0)
            {
                param1 = this.mergeMechanicPackXmlHelper(_loc_2, param1);
                return param1;
            }
            return null;
        }//end

        private XML  mergeMechanicPackXmlHelper (String param1 ,XML param2 )
        {
            XMLList mechanicPackXml ;
            mechPackName = param1;
            itemXml = param2;
            int _loc_5 =0;
            _loc_6 = this.m_rawXml.mechanicPacks.mechanicPack ;
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (@name == mechPackName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            mechanicPackXml = _loc_4;
            if (mechanicPackXml.length() > 0)
            {
                itemXml = mergeParentMechanicsIntoChild(itemXml, mechanicPackXml.get(0));
            }
            return itemXml;
        }//end

        public XML  loadAndReplaceMechanicPackXmlIntoItem (XML param1 ,String param2 )
        {
            param1 = removeMechanicsXML(param1);
            param1 = this.mergeMechanicPackXmlHelper(param2, param1);
            return param1;
        }//end

        private Object getXmlAttribute (XML param1 ,String param2 ,*param3 =null )
        {
            return param1.attribute(param2).toString().length > 0 ? (param1.attribute(param2)) : (param3);
        }//end

        private void  parseKeywordSyntax (XMLList param1 )
        {
            XML _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                this.parseKeywords(_loc_2.keyword);
            }
            return;
        }//end

        public int  keywordID (String param1 )
        {
            if (!this.m_keywords.hasOwnProperty(param1))
            {
                return 0;
            }
            _loc_2 = this.m_keywords.get(param1) ;
            if (_loc_2)
            {
                return _loc_2.keywordID;
            }
            return 0;
        }//end

        public boolean  keywordVisible (String param1 )
        {
            if (!this.m_keywords.hasOwnProperty(param1))
            {
                return false;
            }
            _loc_2 = this.m_keywords.get(param1) ;
            if (_loc_2)
            {
                return _loc_2.visible;
            }
            return false;
        }//end

        public boolean  keywordMarketVisible (String param1 )
        {
            if (!this.m_keywords.hasOwnProperty(param1))
            {
                return false;
            }
            _loc_2 = this.m_keywords.get(param1) ;
            if (_loc_2)
            {
                return _loc_2.marketVisible;
            }
            return false;
        }//end

        public String  keywordType (String param1 )
        {
            if (!this.m_keywords.hasOwnProperty(param1))
            {
                return "";
            }
            _loc_2 = this.m_keywords.get(param1) ;
            if (_loc_2)
            {
                return _loc_2.type;
            }
            return "";
        }//end

        public XML  uiTheme ()
        {
            return Global.experimentManager.filterXmlByExperiment(this.m_rawXml.uiThemes.theme).get(0);
        }//end

        private void  addKeywords (String param1 ,String param2 ,boolean param3 ,boolean param4 )
        {
            String _loc_6 =null ;
            Object _loc_7 =null ;
            _loc_5 = param1.split(",");
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                if (this.keywordID(_loc_6) != 0)
                {
                    if (param2 != this.keywordType(_loc_6) || param3 != this.keywordVisible(_loc_6))
                    {
                        ErrorManager.addError("ERROR - GameSettingsInit - in <keyword_syntax> Keyword (" + param1 + ") instanceof defined multiple times with different type and/or visibility values.");
                    }
                    continue;
                }
                _loc_7 = new Object();
                this.m_numKeywords++;
                _loc_7.keywordID = this.m_numKeywords;
                _loc_7.keyword = _loc_6;
                _loc_7.type = param2;
                _loc_7.visible = param3;
                _loc_7.marketVisible = param4;
                this.m_keywords.put(_loc_6,  _loc_7);
            }
            return;
        }//end

        private void  parseKeywords (XMLList param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            String _loc_7 =null ;
            boolean _loc_8 =false ;
            String _loc_9 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                _loc_3 = _loc_2.@required;
                if (_loc_3 == "")
                {
                }
                _loc_4 = _loc_2.@required == "1";
                _loc_3 = _loc_2.@visible;
                if (_loc_3 == "")
                {
                }
                _loc_5 = _loc_2.@visible == "1";
                _loc_6 = _loc_5;
                if (_loc_2.@marketVisible.length() > 0)
                {
                    _loc_6 = _loc_2.@marketVisible;
                }
                _loc_3 = _loc_2.@type;
                if (_loc_3 == "")
                {
                }
                _loc_7 = _loc_2.@type;
                String _loc_12 ="1";
                _loc_2.@oneof = "1";
                _loc_8 = true;
                _loc_3 = _loc_2.@value;
                if (_loc_3 == "" && !_loc_8)
                {
                }
                _loc_9 = _loc_2.@value;
                if (_loc_9 != "")
                {
                    this.addKeywords(_loc_9, _loc_7, _loc_5, _loc_6);
                }
                this.parseKeywords(_loc_2.keyword);
            }
            return;
        }//end

        private boolean  validateItemKeywords (XML param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            if (!Config.DEBUG_MODE)
            {
                return true;
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.keyword.size(); i0++)
            {
            	_loc_3 = param1.keyword.get(i0);

                _loc_2.push(String(_loc_3));
            }
            _loc_4 = param1.@type;
            this.m_keywordErrorString = "";
            _loc_5 = this.validateKeywords(_loc_2, _loc_4);
            if (!_loc_5)
            {
                this.m_keywordErrorCount++;
                if (this.m_keywordErrorCount < 5)
                {
                    ErrorManager.addError("GameSettingsInit: invalid keywords for item (" + param1.@name + ") \n " + this.m_keywordErrorString);
                }
                else if (this.m_keywordErrorCount == 5)
                {
                    ErrorManager.addError("GameSettingsInit: keywords: more errors not listed here.");
                }
                if (this.m_keywordErrorCount >= 5)
                {
                }
            }
            this.m_missingKeywordString = "";
            boolean _loc_6 =false ;
            if (_loc_2.length == 0)
            {
                _loc_6 = true;
            }
            if (!_loc_6 && this.missingKeywords(_loc_2, _loc_4))
            {
                this.m_missingKeywordCount++;
                if (this.m_missingKeywordCount < 5)
                {
                    ErrorManager.addError("GameSettingsInit: missing required keywords for item (" + param1.@name + ") \n " + this.m_missingKeywordString);
                }
                else if (this.m_missingKeywordCount == 5)
                {
                    ErrorManager.addError("GameSettingsInit: keywords: more missing keywords not listed here.");
                }
                if (this.m_missingKeywordCount >= 5)
                {
                }
            }
            return _loc_5;
        }//end

        private boolean  missingKeywords (Array param1 ,String param2 )
        {
            XML _loc_4 =null ;
            String _loc_5 =null ;
            boolean _loc_3 =false ;
            for(int i0 = 0; i0 < this.m_rawXml.keyword_syntax.get(0).itemType.size(); i0++)
            {
            	_loc_4 = this.m_rawXml.keyword_syntax.get(0).itemType.get(i0);

                _loc_5 = _loc_4.@name;
                if (_loc_5 != "all" && _loc_5 != param2)
                {
                    continue;
                }
                if (this.missingItemsKeyword(param1, _loc_4.keyword))
                {
                    _loc_3 = true;
                }
            }
            return _loc_3;
        }//end

        private boolean  missingItemsKeyword (Array param1 ,XMLList param2 )
        {
            XML _loc_4 =null ;
            String _loc_5 =null ;
            boolean _loc_6 =false ;
            boolean _loc_7 =false ;
            String _loc_8 =null ;
            boolean _loc_9 =false ;
            boolean _loc_3 =false ;
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_4 = param2.get(i0);

                _loc_5 = _loc_4.@value;
                _loc_6 = _loc_4.@required == "1";
                _loc_7 = false;
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_8 = param1.get(i0);

                    if (_loc_5.indexOf(_loc_8) >= 0)
                    {
                        _loc_7 = true;
                    }
                }
                if (_loc_6 && !_loc_7)
                {
                    this.m_missingKeywordString = this.m_missingKeywordString + ("Required Keyword \'" + _loc_5 + "\' instanceof missing.\n");
                    _loc_3 = true;
                }
                if (_loc_7)
                {
                    _loc_9 = this.missingItemsKeyword(param1, _loc_4.keyword);
                    if (_loc_9)
                    {
                        _loc_3 = true;
                    }
                }
            }
            return _loc_3;
        }//end

        private boolean  validateKeywords (Array param1 ,String param2 )
        {
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            XML _loc_6 =null ;
            String _loc_7 =null ;
            boolean _loc_3 =true ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_4 = param1.get(i0);

                if (this.keywordID(_loc_4) == 0)
                {
                    this.m_keywordErrorString = this.m_keywordErrorString + ("Invalid keyword - \'" + _loc_4 + "\' doesn\'t exist in keyword_syntax. \n");
                    _loc_3 = false;
                    continue;
                }
                _loc_5 = false;
                for(int i0 = 0; i0 < this.m_rawXml.keyword_syntax.get(0).itemType.size(); i0++)
                {
                	_loc_6 = this.m_rawXml.keyword_syntax.get(0).itemType.get(i0);

                    _loc_7 = _loc_6.@name;
                    if (_loc_7 != "all" && _loc_7 != param2)
                    {
                        continue;
                    }
                    if (this.validateItemKeyword(_loc_4, param1, _loc_6.keyword))
                    {
                        _loc_5 = true;
                    }
                }
                if (!_loc_5)
                {
                    this.m_keywordErrorString = this.m_keywordErrorString + (_loc_4 + " not valid for type " + param2);
                    _loc_3 = false;
                }
            }
            return _loc_3;
        }//end

        private boolean  validateItemKeyword (String param1 ,Array param2 ,XMLList param3 ,boolean param4 =false )
        {
            String _loc_7 =null ;
            XML _loc_11 =null ;
            String _loc_12 =null ;
            boolean _loc_13 =false ;
            boolean _loc_14 =false ;
            boolean _loc_15 =false ;
            boolean _loc_16 =false ;
            boolean _loc_17 =false ;
            if (param2.indexOf(param1) >= 0)
            {
                param2 = param2.slice();
                param2.splice(param2.indexOf(param1), 1);
            }
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            boolean _loc_8 =false ;
            String _loc_9 ="";
            String _loc_10 ="";
            for(int i0 = 0; i0 < param3.size(); i0++)
            {
            	_loc_11 = param3.get(i0);

                _loc_12 = _loc_11.@value;
                _loc_13 = _loc_11.@required == "1";
                _loc_14 = _loc_12.indexOf(param1) >= 0;
                _loc_15 = false;
                if (_loc_14)
                {
                    _loc_5 = true;
                }
                for(int i0 = 0; i0 < param2.size(); i0++)
                {
                	_loc_7 = param2.get(i0);

                    if (_loc_12.indexOf(_loc_7) >= 0)
                    {
                        _loc_15 = true;
                        _loc_8 = true;
                        _loc_9 = _loc_7;
                    }
                }
                if (_loc_14 && _loc_15 && param4)
                {
                    this.m_keywordErrorString = this.m_keywordErrorString + ("Keywords \'" + param1 + "\' and \'" + _loc_9 + "\' both occur, only one may be used.\n");
                    return false;
                }
                _loc_16 = _loc_11.@oneof == "1";
                if (!_loc_14 && _loc_15)
                {
                    _loc_17 = this.validateItemKeyword(param1, param2, _loc_11.keyword, _loc_16);
                    if (_loc_17)
                    {
                        _loc_6 = true;
                        break;
                    }
                }
                if (_loc_15)
                {
                    _loc_8 = true;
                }
            }
            if (param4 && _loc_5 && _loc_8)
            {
                this.m_keywordErrorString = this.m_keywordErrorString + ("Keywords \'" + param1 + "\' and \'" + _loc_9 + "\' both occur, only one may be used.\n");
                return false;
            }
            return _loc_5 || _loc_6;
        }//end

        private void  parseValidExpansionSquares (XMLList param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            String _loc_10 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                _loc_3 = null;
                _loc_4 = null;
                if (_loc_2.@experiment.length() > 0)
                {
                    _loc_3 = _loc_2.@experiment;
                    _loc_4 = new Array();
                    if (_loc_2.attribute("variants").length() > 0)
                    {
                        _loc_9 = _loc_2.attribute("variants").toString().split(",");
                        for(int i0 = 0; i0 < _loc_9.size(); i0++)
                        {
                        	_loc_10 = _loc_9.get(i0);

                            _loc_4.push(parseInt(_loc_10));
                        }
                    }
                    else
                    {
                        _loc_4.push(1);
                    }
                }
                _loc_5 = new Object();
                _loc_5.experiment = _loc_3;
                _loc_5.experimentVariants = _loc_4;
                _loc_6 = String(_loc_2);
                _loc_7 = _loc_6.split(",");
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                	_loc_8 = _loc_7.get(i0);

                    this.m_validExpansionSquares.put(_loc_8,  _loc_5);
                }
            }
            return;
        }//end

        public Object  getExpansionSquare (int param1 ,int param2 )
        {
            _loc_3 = param1+"|"+param2 ;
            return this.m_validExpansionSquares.get(_loc_3);
        }//end

        public Object  getPopulations ()
        {
            XMLList _loc_1 =null ;
            XMLList _loc_2 =null ;
            if (this.m_populations == null)
            {
                _loc_1 = Global.experimentManager.filterXmlByExperiment(this.m_rawXml.citysim.populations.population);
                _loc_2 = this.m_rawXml.citysim.populations;
                _loc_2.setChildren(_loc_1);
                this.m_populations = XMLParser.parseXMLElementRecursively(_loc_2);
            }
            return this.m_populations;
        }//end

        public int  getPopulationCapYieldBase (String param1 ,int param2 )
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            _loc_3 = param2;
            _loc_4 = this.getPopulations ();
            if (param1 !=null)
            {
                if (_loc_4.hasOwnProperty("baseCap"))
                {
                    _loc_3 = _loc_4.baseCap;
                }
                if (_loc_4.hasOwnProperty(param1))
                {
                    _loc_5 = _loc_4.get(param1);
                    if (_loc_5.hasOwnProperty("baseCap"))
                    {
                        _loc_3 = _loc_5.baseCap;
                    }
                }
            }
            else
            {
                _loc_3 = 0;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_6 = _loc_4.get(i0);

                    _loc_3 = _loc_3 + this.getPopulationCapYieldBase(_loc_6, param2);
                }
            }
            return _loc_3;
        }//end

        public Array  getPopulationsForDisplay ()
        {
            Object _loc_1 =null ;
            String _loc_2 =null ;
            Object _loc_3 =null ;
            if (this.m_populationsForDisplay == null)
            {
                this.m_populationsForDisplay = new Array();
                _loc_1 = this.getPopulations();
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_2 = _loc_1.get(i0);

                    _loc_3 = _loc_1.get(_loc_2);
                    if (_loc_3.hasOwnProperty("displayPriority") && _loc_3.get("displayPriority") != "-1")
                    {
                        this.m_populationsForDisplay.push(_loc_3);
                    }
                }
            }
            this.m_populationsForDisplay.sortOn("displayPriority");
            return this.m_populationsForDisplay;
        }//end

        public boolean  hasMultiplePopulations ()
        {
            Array _loc_1 =null ;
            if (this.m_hasMultiplePopulation == -1)
            {
                this.m_hasMultiplePopulation = 0;
                _loc_1 = this.getPopulationsForDisplay();
                if (_loc_1.length > 1)
                {
                    this.m_hasMultiplePopulation = 1;
                }
            }
            return Boolean(this.m_hasMultiplePopulation);
        }//end

        public Array  getMechanicGraftsByType (String param1 )
        {
            XML graft ;
            String graftType ;
            XMLList mechanics ;
            XML attribs ;
            String event ;
            XML mechanic ;
            XML attrib ;
            type = param1;
            Object _loc_8;

            if (!this.m_grafts)
            {
                this.m_grafts = new Dictionary();
                if (this.m_rawXml.mechanicGrafts)
                {
                    int _loc_3 =0;
                    _loc_4 = this.m_rawXml.mechanicGrafts.graft ;
                    for(int i0 = 0; i0 < this.m_rawXml.mechanicGrafts.graft.size(); i0++)
                    {
                    	graft = this.m_rawXml.mechanicGrafts.graft.get(i0);


                        graftType = graft.@graftType.toString();
                        int _loc_6 =0;
                        _loc_7 = this.m_rawXml.mechanicPacks.mechanicPack ;
                        XMLList _loc_5 =new XMLList("");
                        for(int i0 = 0; i0 < _loc_7.size(); i0++)
                        {
                        	_loc_8 = _loc_7.get(i0);


                            with (_loc_8)
                            {
                                if (@name == graft.@pack.toString())
                                {
                                    _loc_5.put(_loc_6++,  _loc_8);
                                }
                            }

                        }
                        mechanics = _loc_5.get(0).mechanics.copy();
                        int _loc_51 =0;
                        _loc_61 = graft.attribs;
                        for(int i0 = 0; i0 < graft.attribs.size(); i0++)
                        {
                        	attribs = graft.attribs.get(i0);


                            event = attribs.@event;
                            type = attribs.@type;
                            int _loc_81 =0;
                            int _loc_11 =0;
                            _loc_121 = mechanics.gameEventMechanics;
                            XMLList _loc_101 =new XMLList("");
                            Object _loc_131;
                            for(int i0 = 0; i0 < _loc_121.size(); i0++)
                            {
                            	_loc_131 = _loc_121.get(i0);

                                with (_loc_131)
                                {
                                    if (@gameMode == event)
                                    {
                                        _loc_101.put(_loc_11++,  _loc_131);
                                    }
                                }
                            }
                            _loc_91 = _loc_101.mechanic;
                            XMLList _loc_71 =new XMLList("");
                            for(int i0 = 0; i0 < _loc_91.size(); i0++)
                            {
                            	_loc_131 = _loc_91.get(i0);


                                with (_loc_131)
                                {
                                    if (@type == type)
                                    {
                                        _loc_71.put(_loc_81++,  _loc_131);
                                    }
                                }
                            }
                            mechanic = _loc_71.get(0);
                            if (!mechanic)
                            {
                                continue;
                            }
                            int _loc_72 =0;
                            _loc_82 = attribs.attrib;
                            for(int i0 = 0; i0 < attribs.attrib.size(); i0++)
                            {
                            	attrib = attribs.attrib.get(i0);


                                mechanic.get(0).put(attrib.@name.toString(),  attrib.@value.toString());
                            }
                        }
                        if (graftType in this.m_grafts)
                        {
                            ((Array)this.m_grafts.get(graftType)).push(mechanics);
                            continue;
                        }
                        this.m_grafts.put(graftType,  new Array(mechanics));
                    }
                }
            }
            if (type in this.m_grafts)
            {
                return this.m_grafts.get(type);
            }
            return new Array();
        }//end

        public static double  getCurrentTime ()
        {
            _loc_1 =             !isNaN(Global.player.debugTime) ? (Global.player.debugTime * 1000) : (GlobalEngine.getTimer());
            return _loc_1;
        }//end

        public static boolean  checkDateGate (XML param1 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            boolean _loc_2 =true ;
            if (param1.limitedStart && param1.limitedStart.toString() != "")
            {
                _loc_3 = GameUtil.synchronizedDateToNumber(param1.limitedStart.toString());
                if (param1.limitedEnd && param1.limitedEnd.toString() != "")
                {
                    _loc_4 = GameUtil.synchronizedDateToNumber(param1.limitedEnd.toString());
                    if (_loc_3 <= GlobalEngine.getTimer() && _loc_4 >= GlobalEngine.getTimer())
                    {
                        _loc_2 = true;
                    }
                    else
                    {
                        _loc_2 = false;
                    }
                }
            }
            return _loc_2;
        }//end

        private static XML  mergeParentMechanicsIntoChild (XML param1 ,XML param2 )
        {
            XML parentMechanicTypeBlock ;
            XML mechanicXml ;
            String typeName ;
            String gameEvent ;
            XMLList childMechanicTypeBlock ;
            XML childXml = param1;
            XML parentXml = param2;
            XML childXmlCopy = childXml.copy();
            XML childMechanicXml = childXmlCopy..mechanics.get(0);
            XML parentMechanicXml = parentXml..mechanics.get(0);
            if (parentMechanicXml != null)
            {
                if (childMechanicXml != null)
                {
                    int _loc_4 =0;
                    _loc_5 = parentMechanicXml.gameEventMechanics;
                    for(int i0 = 0; i0 < parentMechanicXml.gameEventMechanics.size(); i0++)
                    {
                    	parentMechanicTypeBlock = parentMechanicXml.gameEventMechanics.get(i0);


                        gameEvent = parentMechanicTypeBlock.attribute("gameMode").toString();
                        int _loc_7 =0;
                        _loc_8 = childMechanicXml.gameEventMechanics;
                        XMLList _loc_6 =new XMLList("");
                        Object _loc_9;
                        for(int i0 = 0; i0 < _loc_8.size(); i0++)
                        {
                        	_loc_9 = _loc_8.get(i0);


                            with (_loc_9)
                            {
                                if (attribute("gameMode") == gameEvent)
                                {
                                    _loc_6.put(_loc_7++,  _loc_9);
                                }
                            }

                        }
                        childMechanicTypeBlock = _loc_6;
                        if (childMechanicTypeBlock.length() > 0)
                        {
                            int _loc_61 =0;
                            _loc_71 = parentMechanicTypeBlock.mechanic;
                            for(int i0 = 0; i0 < parentMechanicTypeBlock.mechanic.size(); i0++)
                            {
                            	mechanicXml = parentMechanicTypeBlock.mechanic.get(i0);


                                typeName = mechanicXml.attribute("type");
                                int _loc_91 =0;
                                _loc_101 = childMechanicTypeBlock.mechanic;
                                XMLList _loc_81 =new XMLList("");
                                Object _loc_111;
                                for(int i0 = 0; i0 < _loc_101.size(); i0++)
                                {
                                	_loc_111 = _loc_101.get(i0);


                                    with (_loc_111)
                                    {
                                        if (attribute("type") == typeName)
                                        {
                                            _loc_81.put(_loc_91++,  _loc_111);
                                        }
                                    }
                                }
                                if (_loc_81.length() == 0)
                                {
                                    childMechanicTypeBlock.appendChild(mechanicXml.copy());
                                }
                            }
                            continue;
                        }
                        childMechanicXml.appendChild(parentMechanicTypeBlock.copy());
                    }
                    _loc_4 = 0;
                    _loc_5 = parentMechanicXml.clientDisplayMechanics;
                    for(int i0 = 0; i0 < parentMechanicXml.clientDisplayMechanics.size(); i0++)
                    {
                    	parentMechanicTypeBlock = parentMechanicXml.clientDisplayMechanics.get(i0);


                        if (childMechanicXml.clientDisplayMechanics.length() > 0)
                        {
                            int _loc_62 =0;
                            _loc_72 = parentMechanicTypeBlock.mechanic ;
                            for(int i0 = 0; i0 < parentMechanicTypeBlock.mechanic.size(); i0++)
                            {
                            	mechanicXml = parentMechanicTypeBlock.mechanic.get(i0);


                                typeName = mechanicXml.attribute("type");
                                int _loc_92 =0;
                                _loc_102 = childMechanicXml.clientDisplayMechanics.mechanic;
                                XMLList _loc_82 =new XMLList("");
                                Object _loc_112;
                                for(int i0 = 0; i0 < _loc_102.size(); i0++)
                                {
                                	_loc_112 = _loc_102.get(i0);


                                    with (_loc_112)
                                    {
                                        if (attribute("type") == typeName)
                                        {
                                            _loc_82.put(_loc_92++,  _loc_112);
                                        }
                                    }
                                }
                                if (_loc_82.length() == 0)
                                {
                                    childMechanicXml.clientDisplayMechanics.appendChild(mechanicXml.copy());
                                }
                            }
                            continue;
                        }
                        childMechanicXml.appendChild(parentMechanicTypeBlock.copy());
                    }
                }
                else
                {
                    childXmlCopy.appendChild(parentXml.mechanics.copy());
                }
            }
            return childXmlCopy;
        }//end

        private static XML  removeMechanicsXML (XML param1 )
        {
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            XML _loc_5 =null ;
            boolean _loc_6 =false ;
            _loc_2 = param1.mechanics.get(0) ;
            if (_loc_2 != null)
            {
                _loc_3 = _loc_2.copy();
                delete param1.mechanics;
                for(int i0 = 0; i0 < _loc_3.gameEventMechanics.size(); i0++)
                {
                	_loc_4 = _loc_3.gameEventMechanics.get(i0);

                    for(int i0 = 0; i0 < _loc_4.mechanic.size(); i0++)
                    {
                    	_loc_5 = _loc_4.mechanic.get(i0);

                        _loc_6 = _loc_5.attribute("nonRemovable").toString() == "true";
                        if (_loc_6)
                        {
                            continue;
                        }
                        delete _loc_4.children().get(_loc_5.childIndex());
                    }
                    if (_loc_4.children().length() <= 0)
                    {
                        delete _loc_3.children().get(_loc_4.childIndex());
                    }
                }
                for(int i0 = 0; i0 < _loc_3.clientDisplayMechanics.size(); i0++)
                {
                	_loc_4 = _loc_3.clientDisplayMechanics.get(i0);

                    for(int i0 = 0; i0 < _loc_4.mechanic.size(); i0++)
                    {
                    	_loc_5 = _loc_4.mechanic.get(i0);

                        _loc_6 = _loc_5.attribute("nonRemovable").toString() == "true";
                        if (_loc_6)
                        {
                            continue;
                        }
                        delete _loc_4.children().get(_loc_5.childIndex());
                    }
                    if (_loc_4.children().length() <= 0)
                    {
                        delete _loc_3.children().get(_loc_4.childIndex());
                    }
                }
                if (_loc_3.children().length() > 0)
                {
                    param1.appendChild(_loc_3);
                }
            }
            return param1;
        }//end

    }



