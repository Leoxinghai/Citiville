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

import Modules.sale.*;
import Modules.stats.experiments.*;
import ZLocalization.*;

    public class ExpansionManager
    {
        private static ExpansionManager m_instance ;

        public  ExpansionManager ()
        {
            return;
        }//end

        public Object  getExpansionDataByLevel (int param1 )
        {
            XML xml ;
            XMLList expansions ;
            String searchNum ;
            Object expansionData ;
            XML attribute ;
            level = param1;
            xml = Global.gameSettings().getXML();
            expansions = xml.expansions.expansion;
            searchNum = level < 10 ? ("0" + level) : (level.toString());
            int _loc_4 =0;
            _loc_5 = expansions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("num") == searchNum)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            desiredExpansion = );
            if (!desiredExpansion)
            {
                _loc_4 = 0;
                _loc_5 = expansions;
                _loc_3 = new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (attribute("num") == "MAX")
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                desiredExpansion = _loc_3.get(0);
            }
            expansionData = new Object();
            int _loc31 =0;
            _loc41 = desiredExpansion.attributes();
            for(int i0 = 0; i0 < desiredExpansion.attributes().size(); i0++)
            {
            	attribute = desiredExpansion.attributes().get(i0);


                expansionData.put(attribute.name().localName,  attribute.toXMLString());
            }
            return expansionData;
        }//end

        public int  getBehaviorExperimentVariant ()
        {
            return Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_RETUNE);
        }//end

        public int  getNextExpansionLevelRequirement ()
        {
            _loc_1 = this.getExpansionDataByLevel((Global.player.expansionsPurchased+1));
            return parseInt(_loc_1.get("level"));
        }//end

        public int  getNextExpansionPermitRequirement ()
        {
            _loc_1 = this.getExpansionDataByLevel((Global.player.expansionsPurchased+1));
            int _loc_2 =0;
            switch(this.getBehaviorExperimentVariant())
            {
                case ExperimentDefinitions.EXPANSION_RETUNE_TEST:
                {
                    _loc_2 = parseInt(_loc_1.get("permits3"));
                    break;
                }
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_1:
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_2:
                {
                }
                default:
                {
                    _loc_2 = parseInt(_loc_1.get("permits2"));
                    break;
                }
            }
            return _loc_2;
        }//end

        public int  getNextExpansionNumPermitPurchaseToComplete ()
        {
            _loc_1 = this.getNextExpansionPermitRequirement();
            _loc_2 = Math.max(0,_loc_1-Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM));
            return _loc_2;
        }//end

        public int  getNextExpansionPermitCostToComplete ()
        {
            _loc_1 = this.getNextExpansionNumPermitPurchaseToComplete()*Global.gameSettings().getItemByName(Item.PERMIT_ITEM).cash;
            return _loc_1;
        }//end

        public int  getNextExpansionPopulationRequirement ()
        {
            int _loc_3 =0;
            Object _loc_4 =null ;
            _loc_1 = this.getExpansionDataByLevel((Global.player.expansionsPurchased+1));
            int _loc_2 =0;
            switch(this.getBehaviorExperimentVariant())
            {
                case ExperimentDefinitions.EXPANSION_RETUNE_TEST:
                {
                    _loc_2 = parseInt(_loc_1.get("population3"));
                    break;
                }
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_1:
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_2:
                {
                }
                default:
                {
                    _loc_2 = parseInt(_loc_1.get("population"));
                    break;
                    break;
                }
            }
            if (Global.player.expansionsPurchased > 0)
            {
                _loc_3 = Global.player.populationObservedOnLastExpansion + parseInt(_loc_1.get("popIncrement"));
                _loc_4 = this.getExpansionDataByLevel(Global.player.expansionsPurchased);
                if (_loc_3 < _loc_2 && Global.player.populationObservedOnLastExpansion < parseInt(_loc_4.get("population")))
                {
                    _loc_2 = _loc_3;
                }
            }
            return _loc_2;
        }//end

        public int  getExpansionCost (int param1 )
        {
            Object _loc_3 =null ;
            int _loc_2 =1;
            switch(this.getBehaviorExperimentVariant())
            {
                case ExperimentDefinitions.EXPANSION_RETUNE_TEST:
                {
                    _loc_3 = this.getExpansionDataByLevel(Global.player.expansionCostLevel);
                    _loc_2 = parseInt(_loc_3.get("expansionCost"));
                    break;
                }
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_1:
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_2:
                {
                }
                default:
                {
                    _loc_2 = param1;
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

        public int  getNextExpansionLockOverrideCost (Item param1 )
        {
            _loc_2 = this.getExpansionDataByLevel((Global.player.expansionsPurchased+1));
            int _loc_3 =0;
            switch(this.getBehaviorExperimentVariant())
            {
                case ExperimentDefinitions.EXPANSION_RETUNE_TEST:
                {
                    _loc_3 = parseInt(_loc_2.get("cost3"));
                    break;
                }
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_1:
                case ExperimentDefinitions.EXPANSION_RETUNE_CONTROL_2:
                {
                }
                default:
                {
                    _loc_3 = parseInt(_loc_2.get("cost2"));
                    break;
                    break;
                }
            }
            if (Global.marketSaleManager.isItemOnSale(param1, MarketSaleManager.EXPANSION_LOCK_OVERRIDE_COST))
            {
                _loc_3 = _loc_3 - _loc_3 * (Global.marketSaleManager.getDiscountPercent(param1) / 100);
            }
            return _loc_3;
        }//end

        public boolean  hasEnoughPermits ()
        {
            _loc_1 = this.getNextExpansionPermitRequirement();
            _loc_2 = Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM);
            return _loc_2 >= _loc_1;
        }//end

        public boolean  hasEnoughPopulation ()
        {
            return Global.player.checkPopulation(this.getNextExpansionPopulationRequirement());
        }//end

        public boolean  hasPassedExpansionGate ()
        {
            return this.hasEnoughPopulation() && this.hasEnoughPermits();
        }//end

        public String  getUnlockLabel ()
        {
            LocalizationObjectToken _loc_2 =null ;
            String _loc_1 ="";
            if (!this.hasEnoughPermits())
            {
                _loc_2 = ZLoc.tk("Dialogs", "permit", "", this.getNextExpansionPermitRequirement());
                _loc_1 = ZLoc.t("Dialogs", "permit_text", {amount2:this.getNextExpansionPermitRequirement(), permit:_loc_2});
            }
            else if (!this.hasEnoughPopulation())
            {
                _loc_1 = ZLoc.t("Dialogs", "population_text1", {amount2:this.getNextExpansionPopulationRequirement() * Global.gameSettings().getNumber("populationScale", 1)});
            }
            return _loc_1;
        }//end

        public String  getPermitsLabel ()
        {
            LocalizationObjectToken _loc_2 =null ;
            String _loc_1 ="";
            if (!this.hasEnoughPermits())
            {
                _loc_2 = ZLoc.tk("Dialogs", "permit", "", this.getNextExpansionPermitRequirement());
                _loc_1 = ZLoc.t("Dialogs", "permit_text", {amount2:this.getNextExpansionPermitRequirement(), permit:_loc_2});
            }
            return _loc_1;
        }//end

        public String  getPopulationLabel ()
        {
            String _loc_1 ="";
            if (!this.hasEnoughPopulation())
            {
                _loc_1 = ZLoc.t("Dialogs", "population_text1", {amount2:this.getNextExpansionPopulationRequirement() * Global.gameSettings().getNumber("populationScale", 1)});
            }
            return _loc_1;
        }//end

        public String  getNeededPopulationLabel ()
        {
            _loc_1 = this.getNextExpansionPopulationRequirement();
            _loc_2 = Global.world.citySim.getPopulation();
            _loc_3 = _loc_1-_loc_2 ;
            if (_loc_3 > 0)
            {
                return _loc_3 * 10 + " " + ZLoc.t("Market", "population");
            }
            return "";
        }//end

        public String  getNeededPermitsLabel ()
        {
            _loc_1 = this.getNeededPermits ();
            if (_loc_1 > 0)
            {
                return _loc_1 + " " + ZLoc.t("Dialogs", "Permits");
            }
            return "";
        }//end

        public int  getNeededPermits ()
        {
            _loc_1 = this.getNextExpansionPermitRequirement();
            _loc_2 = Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM);
            return Math.max(0, _loc_1 - _loc_2);
        }//end

        public static ExpansionManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new ExpansionManager;
            }
            return m_instance;
        }//end

    }



