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

import Classes.bonus.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;


    public class ItemBonus
    {
        private String m_field ;
        private double m_radius =0;
        private Array m_allowedTypes ;
        private Array m_allowedQueries ;
        protected double m_percentModifier =0;
        private String m_name ;
        private double m_radiusMin =0;
        private double m_radiusMax =0;
        private boolean m_dynamicModifier ;
        private boolean m_dynamicRadius ;
        private Array m_dynamicModifierArray ;
        private String m_modifierCallbackName ;
        private String m_description ;
        public static  String COIN_YIELD ="coinYield";
        public static  String XP_YIELD ="XP";
        public static  String GOODS_YIELD ="goods";
        public static  String PREMIUM_GOODS_YIELD ="premium_goods";
        public static  String ALL ="all";
        private static ItemInstance m_curObject ;
        private static  double NEARBY =5;

        public  ItemBonus (XML param1 )
        {
            String _loc_2 =null ;
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            String _loc_5 =null ;
            this.m_allowedTypes = new Array();
            this.m_allowedQueries = new Array();
            this.m_dynamicModifierArray = new Array();
            this.m_field = param1.@field;
            this.m_percentModifier = param1.@percentModifier;
            if (isNaN(parseInt(param1.@radius)) && String(param1.@radius).length > 0)
            {
                this.m_dynamicRadius = true;
                this.m_radiusMin = parseInt(param1.dynamicRadius.@min);
                this.m_radiusMax = parseInt(param1.dynamicRadius.@max);
            }
            if (isNaN(parseInt(param1.@percentModifier)) && String(param1.@percentModifier).length > 0)
            {
                this.m_dynamicModifier = true;
                for(int i0 = 0; i0 < param1.dynamicModifier.attributes().size(); i0++)
                {
                	_loc_4 = param1.dynamicModifier.attributes().get(i0);

                    _loc_5 = _loc_4.name();
                    this.m_dynamicModifierArray.put(_loc_5,  parseInt(param1.dynamicModifier.attribute(_loc_5)));
                }
            }
            this.m_radius = param1.@radius;
            this.m_name = param1.@name;
            if (keywordExperimentEnabled())
            {
                for(int i0 = 0; i0 < param1.allowedKeyword.size(); i0++)
                {
                		_loc_3 = param1.allowedKeyword.get(i0);

                    _loc_2 = _loc_3.@type;
                    this.m_allowedQueries.push(_loc_2);
                }
            }
            for(int i0 = 0; i0 < param1.allowedType.size(); i0++)
            {
            	_loc_3 = param1.allowedType.get(i0);

                _loc_2 = _loc_3.@type;
                this.m_allowedTypes.push(_loc_2);
            }
            this.m_description = String(param1.@description);
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  field ()
        {
            return this.m_field;
        }//end

        public String  description ()
        {
            return this.m_description;
        }//end

        public Array  dynamicModifierArray ()
        {
            return this.m_dynamicModifierArray;
        }//end

        public Array  allowedTypes ()
        {
            return this.m_allowedTypes;
        }//end

        public Array  allowedQueries ()
        {
            return this.m_allowedQueries;
        }//end

        public double  getRadius (ItemInstance param1 )
        {
            MapResource _loc_3 =null ;
            _loc_2 = this.m_radius;
            if (this.m_dynamicRadius)
            {
                _loc_3 = MapResource(param1);
                _loc_2 = _loc_3.getRadius(this);
            }
            return _loc_2;
        }//end

        public double  coinModifier ()
        {
            double _loc_1 =0;
            if (this.m_field == "coinYield")
            {
                _loc_1 = this.m_percentModifier;
            }
            return _loc_1;
        }//end

        public double  radiusMin ()
        {
            return this.m_radiusMin;
        }//end

        public double  radiusMax ()
        {
            return this.m_radiusMax;
        }//end

        public double  maxPercentModifier ()
        {
            return this.m_percentModifier;
        }//end

        public String  getMarketPlaceString ()
        {
            if (this.m_dynamicModifier)
            {
                return ZLoc.t("Dialogs", "TT_RangePayout", {min:this.m_dynamicModifierArray.get("base"), max:this.m_dynamicModifierArray.get("max")});
            }
            return ZLoc.t("Dialogs", "TT_Payout", {amount:this.m_percentModifier});
        }//end

        public boolean  withinRadius (ItemInstance param1 ,ItemInstance param2 )
        {
            return ItemBonus.withinNearbyRadius(param1, param2, this.getRadius(param1));
        }//end

        public boolean  isNegative ()
        {
            if (this.m_percentModifier < 0)
            {
                return true;
            }
            return false;
        }//end

        public double  effectiveRadius (ItemInstance param1 )
        {
            _loc_2 = param1.getItem().sizeX;
            _loc_3 = param1.getItem().sizeY;
            _loc_4 = Math.min(_loc_2,_loc_3)*0.5;
            return this.m_radius + _loc_4;
        }//end

        public boolean  bonusAppliesToObject (ItemInstance param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 instanceof LotSite)
            {
                return false;
            }
            _loc_2 = param1.getItem();
            if (_loc_2)
            {
                if (ItemBonus.keywordExperimentEnabled())
                {
                    for(int i0 = 0; i0 < this.m_allowedQueries.size(); i0++)
                    {
                    		_loc_5 = this.m_allowedQueries.get(i0);

                        if (_loc_2.doesKeywordMatch(_loc_5))
                        {
                            return true;
                        }
                    }
                }
                _loc_3 = _loc_2.type;
                for(int i0 = 0; i0 < this.m_allowedTypes.size(); i0++)
                {
                	_loc_4 = this.m_allowedTypes.get(i0);

                    if (_loc_4 == _loc_3)
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public double  getPercentModifier (ItemInstance param1 )
        {
            MapResource _loc_2 =null ;
            if (this.m_dynamicModifier)
            {
                _loc_2 = MapResource(param1);
                return _loc_2.getAOEPercentModifier(this);
            }
            return this.m_percentModifier;
        }//end

        public static boolean  keywordExperimentEnabled ()
        {
            return Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_KEYWORDS2) == ExperimentDefinitions.KEYWORDS2_ENABLED;
        }//end

        public static boolean  withinNearbyRadius (ItemInstance param1 ,ItemInstance param2 ,double param3 ,double param4 =0)
        {
            _loc_5 = param1.getSize();
            _loc_6 = param1.getSize().x;
            _loc_7 = _loc_5.y;
            _loc_8 = param1.getPosition();
            _loc_9 = param1.getPosition().x+_loc_6*0.5;
            _loc_10 = _loc_8.y+_loc_7*0.5;
            _loc_5 = param2.getSize();
            _loc_11 = _loc_5.x;
            _loc_12 = _loc_5.y;
            _loc_8 = param2.getPosition();
            _loc_13 = _loc_8.x+_loc_11*0.5;
            _loc_14 = _loc_8.y+_loc_12*0.5;
            _loc_15 = _loc_9-_loc_13;
            _loc_16 = _loc_10-_loc_14;
            _loc_17 = param3+(_loc_6+_loc_11)/2;
            _loc_18 = param3+(_loc_7+_loc_12)/2;
            if (Math.abs(_loc_15) < _loc_17 && Math.abs(_loc_16) < _loc_18)
            {
                return true;
            }
            return false;
        }//end

        public static double  getDistanceBetweenObjs (MapResource param1 ,MapResource param2 )
        {
            return param1.centerPosition.subtract(param2.centerPosition).length();
        }//end

        public static double  getBonusModifier (ItemInstance param1 ,String param2 )
        {
            Array _loc_3 =new Array ();
            _loc_4 = ItemBonus.getBonuses(param1,_loc_3);
            double _loc_5 =1;
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                if (_loc_3.get(_loc_6) instanceof Headquarter && param1 instanceof Business && param2 == COIN_YIELD)
                {
                    _loc_5 = _loc_5 + ((ItemBonus)_loc_4.get(_loc_6)).coinModifier * ((Headquarter)_loc_3.get(_loc_6)).floorCount / 100;
                }
                else if (((ItemBonus)_loc_4.get(_loc_6)).field == param2 || param2 == ALL)
                {
                    _loc_5 = _loc_5 + ((ItemBonus)_loc_4.get(_loc_6)).getPercentModifier(_loc_3.get(_loc_6)) / 100;
                }
                _loc_6++;
            }
            if (_loc_5 < 0)
            {
                _loc_5 = 0;
            }
            return _loc_5;
        }//end

        public static double  getMaxBonusModifier (ItemInstance param1 ,String param2 )
        {
            Array _loc_3 =new Array ();
            _loc_4 = ItemBonus.getBonuses(param1,_loc_3);
            double _loc_5 =1;
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                if (_loc_3.get(_loc_6) instanceof Headquarter && param1 instanceof Business && param2 == COIN_YIELD)
                {
                    _loc_5 = _loc_5 + ((ItemBonus)_loc_4.get(_loc_6)).coinModifier * ((Headquarter)_loc_3.get(_loc_6)).floorCount / 100;
                }
                else if (((ItemBonus)_loc_4.get(_loc_6)).field == param2 || param2 == ALL)
                {
                    _loc_5 = _loc_5 + ((ItemBonus)_loc_4.get(_loc_6)).maxPercentModifier / 100;
                }
                _loc_6++;
            }
            if (_loc_5 < 0)
            {
                _loc_5 = 0;
            }
            return _loc_5;
        }//end

        public static Array  getBonuses (ItemInstance param1 ,Array param2 )
        {
            ItemInstance _loc_5 =null ;
            Item _loc_6 =null ;
            ItemBonus _loc_7 =null ;
            m_curObject = param1;
            _loc_3 = Global.world.getObjectsByPredicate(matchBonus);
            Array _loc_4 =new Array ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_5 = _loc_3.get(i0);

                if (_loc_5 instanceof ConstructionSite)
                {
                    _loc_6 = ((ConstructionSite)_loc_5).targetItem;
                }
                else
                {
                    _loc_6 = _loc_5.getItem();
                }
                for(int i0 = 0; i0 < _loc_6.bonuses.size(); i0++)
                {
                	_loc_7 = _loc_6.bonuses.get(i0);

                    if (!_loc_7.bonusAppliesToObject(m_curObject))
                    {
                        continue;
                    }
                    if (_loc_7.withinRadius(_loc_5, m_curObject))
                    {
                        _loc_4.push(_loc_7);
                        if (param2 != null)
                        {
                            param2.push(_loc_5);
                        }
                    }
                }
            }
            _loc_4 = _loc_4.concat(getHarvestBonuses(param1));
            return _loc_4;
        }//end

        private static Array  getHarvestBonuses (ItemInstance param1 )
        {
            Vector _loc_4.<HarvestBonus >=null ;
            HarvestBonus _loc_5 =null ;
            Array _loc_2 =new Array();
            _loc_3 = (MapResource)param1
            if (_loc_3)
            {
                _loc_4 = _loc_3.harvestingDefinition.harvestBonuses;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_5.init(_loc_3);
                    _loc_2.push(_loc_5);
                }
            }
            return _loc_2;
        }//end

        public static Array  getNearbyBonuses (ItemInstance param1 ,Array param2 )
        {
            ItemInstance _loc_5 =null ;
            ItemBonus _loc_6 =null ;
            m_curObject = param1;
            _loc_3 = Global.world.getObjectsByPredicate(matchNearbyBonus);
            Array _loc_4 =new Array ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_5 = _loc_3.get(i0);

                for(int i0 = 0; i0 < _loc_5.getItem().bonuses.size(); i0++)
                {
                	_loc_6 = _loc_5.getItem().bonuses.get(i0);

                    if (!_loc_6.bonusAppliesToObject(m_curObject))
                    {
                        continue;
                    }
                    if (ItemBonus.withinNearbyRadius(_loc_5, m_curObject, NEARBY))
                    {
                        _loc_4.push(_loc_6);
                        if (param2 != null)
                        {
                            param2.push(_loc_5);
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public static double  calcFranchiseBonuses (Business param1 )
        {
            Array _loc_2 =new Array ();
            _loc_3 = ItemBonus.getBonuses(param1,_loc_2);
            double _loc_4 =0;
            int _loc_5 =0;
            while (_loc_5 < _loc_3.length())
            {

                if (_loc_2.get(_loc_5) instanceof Headquarter)
                {
                    _loc_4 = _loc_4 + ((ItemBonus)_loc_3.get(_loc_5)).coinModifier * ((Headquarter)_loc_2.get(_loc_5)).builtFloorCount;
                }
                _loc_5++;
            }
            return _loc_4;
        }//end

        public static double  calcIndividualHQBonus (Business param1 ,MapResource param2 )
        {
            MapResource _loc_7 =null ;
            boolean _loc_8 =false ;
            Array _loc_3 =new Array ();
            _loc_4 = ItemBonus.getBonuses(param1,_loc_3);
            double _loc_5 =0;
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                _loc_7 = _loc_3.get(_loc_6);
                if (_loc_7)
                {
                    _loc_8 = _loc_7 instanceof Headquarter || _loc_7 instanceof ConstructionSite && ((ConstructionSite)_loc_7).targetItem.className == "Headquarter";
                    if (_loc_8 && _loc_7.getId() == param2.getId())
                    {
                        _loc_5 = _loc_5 + ((ItemBonus)_loc_4.get(_loc_6)).coinModifier * (param2 instanceof ConstructionSite ? (1) : (((Headquarter)_loc_3.get(_loc_6)).builtFloorCount));
                        break;
                    }
                }
                _loc_6++;
            }
            return _loc_5;
        }//end

        private static boolean  matchBonus (GameObject param1 )
        {
            Item _loc_3 =null ;
            ItemBonus _loc_4 =null ;
            if (!param1 instanceof MapResource)
            {
                return false;
            }
            _loc_2 = MapResource(param1);
            if (m_curObject.getId() == _loc_2.getId())
            {
                return false;
            }
            if (_loc_2 instanceof ConstructionSite)
            {
                _loc_3 = ((ConstructionSite)_loc_2).targetItem;
            }
            else
            {
                _loc_3 = _loc_2.getItem();
            }
            for(int i0 = 0; i0 < _loc_3.bonuses.size(); i0++)
            {
            	_loc_4 = _loc_3.bonuses.get(i0);

                if (!_loc_4.bonusAppliesToObject(m_curObject))
                {
                    continue;
                }
                if (_loc_4.withinRadius(_loc_2, m_curObject))
                {
                    return true;
                }
            }
            return false;
        }//end

        private static boolean  matchNearbyBonus (GameObject param1 )
        {
            ItemBonus _loc_3 =null ;
            if (!param1 instanceof MapResource)
            {
                return false;
            }
            _loc_2 = MapResource(param1);
            if (m_curObject.getId() == _loc_2.getId())
            {
                return false;
            }
            for(int i0 = 0; i0 < _loc_2.getItem().bonuses.size(); i0++)
            {
            		_loc_3 = _loc_2.getItem().bonuses.get(i0);

                if (!_loc_3.bonusAppliesToObject(m_curObject))
                {
                    continue;
                }
                if (ItemBonus.withinNearbyRadius(_loc_2, m_curObject, NEARBY))
                {
                    return true;
                }
            }
            return false;
        }//end

    }




