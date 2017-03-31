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
import Modules.stats.experiments.*;
//import flash.utils.*;

    public class LargeCatalogModel
    {
        private Array m_items ;
        private Array m_saleItems ;
        private Array m_bogoItems ;
        private Object m_categories ;
        protected Dictionary m_subCategoryCache ;
        private static LargeCatalogModel s_instance ;
        private static  Object SINGLETON_ENFORCER ={};

        public  LargeCatalogModel (Object param1 )
        {
            if (param1 != SINGLETON_ENFORCER)
            {
                throw new Error("Large Catalog Model instanceof a singleton, and can only be instantiated by itself.");
            }
            this.m_subCategoryCache = new Dictionary(true);
            this.onLoad();
            return;
        }//end

        public Array  getCategoryItems (String param1 )
        {
            _loc_2 = this.m_categories.get(param1) ;
            return _loc_2 != null ? (_loc_2.items) : (null);
        }//end

        public Array  getSubCategoryItems (String param1 ,String param2 )
        {
            type = param1;
            subType = param2;
            if (subType == "bogo" && this.m_bogoItems.length > 0)
            {
                return this.m_bogoItems;
            }
            category = this.m_categories.get(type);
            if (category == null)
            {
                return null;
            }
            items = category.items;
            if (items == null)
            {
                return null;
            }
            if (subType == "all")
            {
                return items;
            }
            return items .filter (boolean  (Item param11 ,int param21 ,Array param31 )
            {
                return param11.marketKeywords != null && param11.marketKeywords.indexOf(subType) >= 0;
            }//end
            );
        }//end

        public boolean  areAnyItemsInSubCategory (String param1 ,String param2 )
        {
            Item _loc_5 =null ;
            _loc_3 = this.m_categories.get(param1) ;
            if (_loc_3 == null)
            {
                return false;
            }
            if (param2 == "bogo")
            {
                if (this.m_bogoItems.length > 0)
                {
                    return true;
                }
                return false;
            }
            _loc_4 = _loc_3.items ;
            if (_loc_3.items == null || _loc_4.length == 0)
            {
                return false;
            }
            if (param2 == "all")
            {
                return true;
            }
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.marketKeywords != null && _loc_5.marketKeywords.indexOf(param2) >= 0)
                {
                    return true;
                }
            }
            return false;
        }//end

        public Array  getCategorySubCategories (String param1 )
        {
            type = param1;
            category = this.m_categories.get(type);
            if (category == null)
            {
                return null;
            }
            subCategories = category.subCategories;
            if (subCategories == null)
            {
                return null;
            }
            return subCategories .filter (boolean  (String param11 ,int param2 ,Array param3 )
            {
                return param11 == "sale" || areAnyItemsInSubCategory(type, param11);
            }//end
            );
        }//end

        public Array  getCategorySortIds (String param1 )
        {
            _loc_2 = this.m_categories.get(param1) ;
            return _loc_2 != null ? (_loc_2.sortIds) : (null);
        }//end

        public Array  search (String param1 )
        {
            searchStr = param1;
            if (searchStr == "")
            {
                return new Array();
            }
            return this .m_items .filter (boolean  (Item param11 ,int param2 ,Array param3 )
            {
                _loc_6 = null;
                _loc_7 = null;
                _loc_8 = null;
                _loc_9 = undefined;
                _loc_10 = undefined;
                _loc_4 = param11.localizedName.toLowerCase();
                if (param11.localizedName.toLowerCase() == searchStr.toLowerCase())
                {
                    return true;
                }
                RegExp _loc_5 =new RegExp("^"+searchStr ,"i");
                for(int i0 = 0; i0 < _loc_4.split(/\s+""\s+/).size(); i0++)
                {
                	_loc_6 = _loc_4.split(/\s+""\s+/).get(i0);

                    _loc_8 = _loc_6.match(_loc_5);
                    if (_loc_8 != null && _loc_8.length > 0)
                    {
                        return true;
                    }
                }
                for(int i0 = 0; i0 < param11.marketKeywords.size(); i0++)
                {
                	_loc_7 = param11.marketKeywords.get(i0);

                    _loc_9 = ZLoc.t("Market", _loc_7);
                    _loc_10 = _loc_9.match(_loc_5);
                    if (_loc_10 != null && _loc_10.length > 0)
                    {
                        return true;
                    }
                }
                return false;
            }//end
            );
        }//end

        public Array  getPromoItems ()
        {
            int count ;
            count;
            return this .m_items .filter (boolean  (Item param1 ,int param2 ,Array param3 )
            {
                if (count > 3 || BuyLogic.isLocked(param1))
                {
                    return false;
                }
                _loc_5 = count+1;
                count = _loc_5;
                return true;
            }//end
            );
        }//end

        public Array  getSaleItems ()
        {
            return this.m_saleItems;
        }//end

        public Array  getBogoSpecialItems ()
        {
            return this.m_bogoItems;
        }//end

        public Array  getSkinnableResidences ()
        {
            totalItems = this.m_items.filter(function(param1Item,param2,param3)
            {
                return param1.hasRemodel();
            }//end
            );
            totalItems.push(Global.gameSettings().getItemByName("res_simpsonmegabrick"));
            return totalItems;
        }//end

        private void  onLoad ()
        {
            XML categoryXML ;
            String categoryName ;
            Array categoryItems ;
            Array expansionItems ;
            Item expansionItem ;
            XMLList itemXMLList ;
            XML itemXML ;
            String itemName ;
            Item item ;
            Item bogoItem ;
            int i ;
            Array newItems =new Array ();
            Object newCategories =new Object ();
            Dictionary itemsAdded =new Dictionary ();
            categoryXMLList = Global.gameSettings().getMarketCategories();
            bogoSpecials = Specials.getInstance().getAllSpecials(true);
            int _loc_2 =0;
            _loc_3 = categoryXMLList;
            int _loc_4 =0;
            XMLList _loc_5 ;

            for(int i0 = 0; i0 < categoryXMLList.size(); i0++)
            {
            	categoryXML = categoryXMLList.get(i0);


                categoryName = categoryXML.@name;
                if (categoryName == null || categoryName.length == 0)
                {
                    continue;
                }
                categoryItems = new Array();
                newCategories.put(categoryName,  {items:categoryItems, subCategories:getSubCategoriesFromCategoryXML(categoryXML), sortIds:getSortIdsFromCategoryXML(categoryXML)});
                if (categoryName == "expansion")
                {
                    expansionItems = Global.gameSettings().getItemsByType("expansion");


                    for(int i0 = 0; i0 < expansionItems.size(); i0++)
                    {
                    	expansionItem = expansionItems.get(i0);


                        if (!shouldExcludeItem(expansionItem))
                        {
                            addCategoryToItem(expansionItem, categoryName);
                            addItem(expansionItem, newItems, itemsAdded);
                            categoryItems.push(expansionItem);
                        }
                    }
                    continue;
                }
                itemXMLList = categoryXML.item;


                for(int i0 = 0; i0 < itemXMLList.size(); i0++)
                {
                	itemXML = itemXMLList.get(i0);


                    itemName = itemXML.@name;
                    if (itemName == null || itemName.length == 0)
                    {
                        continue;
                    }
                    item = Global.gameSettings().getItemByName(itemName);
                    if (item == null || shouldExcludeItem(item))
                    {
                        continue;
                    }
                    if (categoryName == "specials" || bogoSpecials.indexOf(item.name) != -1)
                    {
                        item.isNew = true;
                    }
                    initItemKeywords(item, itemXML);
                    addCategoryToItem(item, categoryName);
                    addItem(item, newItems, itemsAdded);
                    categoryItems.push(item);
                }
            }
            this .m_saleItems =newItems .filter (boolean  (Item param1 ,int param2 ,Array param3 )
            {
                return Global.marketSaleManager.isItemOnSale(param1);
            }//end
            );
            if (bogoSpecials.length > 0)
            {
                this.m_bogoItems = new Array();
                i;
                while (i < bogoSpecials.length())
                {

                    bogoItem = Global.gameSettings().getItemByName(bogoSpecials.get(i));
                    if (bogoItem == null || shouldExcludeItem(bogoItem))
                    {
                    }
                    else
                    {
                        this.m_bogoItems.push(bogoItem);
                        newCategories.get("specials").items.push(bogoItem);
                    }
                    i = (i + 1);
                }
            }
            else
            {
                this.m_bogoItems = new Array();
            }
            this.m_items = newItems;
            this.m_categories = newCategories;
            return;
        }//end

        public static LargeCatalogModel  instance ()
        {
            if (!s_instance)
            {
                s_instance = new LargeCatalogModel(SINGLETON_ENFORCER);
            }
            return s_instance;
        }//end

        private static void  addItem (Item param1 ,Array param2 ,Dictionary param3 )
        {
            if (param1 != null && param3.get(param1) == null)
            {
                param2.push(param1);
                param3.put(param1,  param1);
            }
            return;
        }//end

        private static void  addCategoryToItem (Item param1 ,String param2 )
        {
            _loc_3 = param1.category ;
            if (_loc_3.indexOf(param2) < 0)
            {
                _loc_3.push(param2);
            }
            return;
        }//end

        private static void  initItemKeywords (Item param1 ,XML param2 )
        {
            _loc_3 = param2.@keywords.get(0);
            if (_loc_3 == null)
            {
                return;
            }
            _loc_4 = _loc_3.toString ();
            if (_loc_3.toString() == null)
            {
                return;
            }
            param1.marketKeywords = _loc_4.split(",");
            return;
        }//end

        private static Array  getSubCategoriesFromCategoryXML (XML param1 )
        {
            _loc_2 = param1.@subitems.get(0);
            if (_loc_2 == null)
            {
                return new Array();
            }
            _loc_3 = _loc_2.toString ();
            if (_loc_3 == null)
            {
                return new Array();
            }
            return _loc_3.split(",");
        }//end

        private static Array  getSortIdsFromCategoryXML (XML param1 )
        {
            _loc_2 = param1.@sort.get(0);
            if (_loc_2 == null)
            {
                return new Array();
            }
            _loc_3 = _loc_2.toString ();
            if (_loc_3 == null)
            {
                return new Array();
            }
            return _loc_3.split(",");
        }//end

        private static boolean  shouldExcludeItem (Item param1 )
        {
            return !param1.buyable && param1.type != "wonder" || isItemOutOfSchedule(param1) || isItemExcludedByExperiment(param1);
        }//end

        public static boolean  isItemOutOfSchedule (Item param1 )
        {
            if (!param1.startDate || !param1.endDate)
            {
                return false;
            }
            int _loc_2 =new Date ().getTime ();
            return _loc_2 < param1.startDate || _loc_2 >= param1.endDate;
        }//end

        private static boolean  isItemExcludedByExperiment (Item param1 )
        {
            Dictionary _loc_2 =null ;
            Array _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            _loc_2 = param1.excludeExperiments;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_4 = _loc_2.get(i0);

                    _loc_3 =(Array) _loc_2.get(_loc_4);
                    if (_loc_3 == null || _loc_3.length == 0)
                    {
                        continue;
                    }
                    _loc_5 = Global.experimentManager.getVariant(_loc_4);
                    if (_loc_3.indexOf(_loc_5) >= 0)
                    {
                        return true;
                    }
                }
            }
            _loc_2 = param1.experiments;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_4 = _loc_2.get(i0);

                    _loc_3 =(Array) _loc_2.get(_loc_4);
                    if (_loc_3 == null || _loc_3.length == 0)
                    {
                        continue;
                    }
                    _loc_5 = Global.experimentManager.getVariant(_loc_4);
                    if (_loc_3.indexOf(_loc_5) < 0)
                    {
                        return true;
                    }
                }
            }
            _loc_6 = param1.type ;
            int _loc_7 =0;
            if (_loc_6 != null && _loc_6.substr(0, "starter_pack2".length()) == "starter_pack2")
            {
                if (Global.player.hasMadeRealPurchase)
                {
                    return true;
                }
                _loc_7 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STARTER_PACK2);
                if (_loc_6 != "starter_pack2_" + _loc_7)
                {
                    return true;
                }
            }
            return false;
        }//end

    }



