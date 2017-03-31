package scripting;

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
import Display.MarketUI.*;
import Modules.bandits.*;
import Modules.socialinventory.*;
//import flash.utils.*;
import root.Global;
import validation.util.*;

    public class ScriptingConditions
    {

        public  ScriptingConditions ()
        {
            return;
        }//end

        public boolean  playerInExperiment (Object param1 )
        {
            Array _loc_4 =null ;
            int _loc_5 =0;
            String _loc_6 =null ;
            _loc_2 = param1.get("experimentName") ;
            _loc_3 = param1.get("experimentVariant") ? (param1.get("experimentVariant")) : ("1");
            if (_loc_2 && _loc_3)
            {
                _loc_4 = _loc_3.split(",");
                _loc_5 = Global.experimentManager.getVariant(_loc_2);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_6 = _loc_4.get(i0);

                    if (_loc_5 == parseInt(_loc_6))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public boolean  playerPurchasedStarterPack (Object param1 )
        {
            _loc_2 =Global.experimentManager.getVariant("cv_starter_pack2")>0;
            return _loc_2 && Global.player.getSeenFlag("purchased_starter_pack");
        }//end

        public boolean  playerRequiredLevel (Object param1 )
        {
            return ScriptingConditionsUtil.playerRequiredLevel(param1);
        }//end

        public boolean  playerHasSeen (Object param1 )
        {
            _loc_2 = (String)(param1[ "checkSessionFlags"] )=="1"|| String(param1.get("checkSessionFlags")) == "true" ? (Global.player.getSeenSessionFlag(param1.flag)) : (false);
            return Global.player.getSeenFlag(param1.flag) || _loc_2;
        }//end

        public boolean  playerHasNotSeen (Object param1 )
        {
            if (String(param1.get("checkSessionFlags")) == "1" || String(param1.get("checkSessionFlags")) == "true")
            {
                if (!Global.player.getSeenFlag(param1.flag) && !Global.player.getSeenSessionFlag(param1.flag))
                {
                    return true;
                }
                return false;
            }
            else
            {
            }
            return !Global.player.getSeenFlag(param1.flag);
        }//end

        public boolean  playerFlag (Object param1 )
        {
            if (param1.flag)
            {
                return Global.player.getFlag(param1.flag).value == param1.value;
            }
            return false;
        }//end

        public boolean  playerCompletedQuest (Object param1 )
        {
            _loc_2 = param1.quest ;
            return Global.player.isQuestCompleted(_loc_2);
        }//end

        public boolean  playerNotCompletedQuest (Object param1 )
        {
            _loc_2 = param1.quest ;
            return !Global.player.isQuestCompleted(_loc_2);
        }//end

        public boolean  timeWithinRange (Object param1 )
        {
            _loc_2 = param1.startDate ;
            _loc_3 = param1.endDate ;
            return TimestampEvents.isCurrentTimeBetweeenRange(_loc_2, _loc_3);
        }//end

        public boolean  inGeoCountry (Object param1 )
        {
            return ScriptingConditionsUtil.inGeoCountry(param1);
        }//end

        public boolean  notInGeoCountry (Object param1 )
        {
            return !ScriptingConditionsUtil.inGeoCountry(param1);
        }//end

        public boolean  preferredLanguage (Object param1 )
        {
            _loc_2 = param1.languageCode ;
            _loc_3 =Global.localizer.langCode ;
            return _loc_2 == _loc_3;
        }//end

        public boolean  alwaysTrue (Object param1)
        {
            return true;
        }//end

        public boolean  hasNoConstructionSite (Object param1)
        {
            ConstructionSite _loc_3 =null ;
            _loc_2 =Global.world.getObjectsByClass(ConstructionSite );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.targetName == param1.targetName)
                {
                    return false;
                }
            }
            return true;
        }//end

        public boolean  hasConstructionSite (Object param1)
        {
            ConstructionSite _loc_3 =null ;
            _loc_2 =Global.world.getObjectsByClass(ConstructionSite );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.targetName == param1.targetName)
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  itemRequiredLevelByRoot (Object param1)
        {
            int _loc_6 =0;
            Item _loc_7 =null ;
            String _loc_8 =null ;
            ItemInstance _loc_9 =null ;
            _loc_2 = param1.level ;
            _loc_3 = param1.rootName ;
            _loc_4 =Global.gameSettings().getOrderedUpgradeChainByRoot(_loc_3 );
            _loc_5 =Global.world.getObjectsByNames(_loc_4 );
            if (Global.world.getObjectsByNames(_loc_4).length > 0 && _loc_4.length > 0)
            {
                _loc_6 = 1;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_8 = _loc_4.get(i0);

                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_9 = _loc_5.get(i0);

                        _loc_7 = _loc_9.getItem();
                        if (_loc_7 && _loc_7.name == _loc_8 && _loc_6 >= _loc_2)
                        {
                            return true;
                        }
                    }
                    _loc_6++;
                }
            }
            else
            {
                return false;
            }
            return false;
        }//end

        public boolean  checkPoliceStations (Object param1)
        {
            if (PreyUtil.getHubLevel("copsNBandits") == 1)
            {
                return true;
            }
            return false;
        }//end

        public boolean  checkNoPoliceStations (Object param1)
        {
            ConstructionSite _loc_3 =null ;
            _loc_2 =Global.world.getObjectsByClass(ConstructionSite );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.targetName == "mun_policestation")
                {
                    return false;
                }
            }
            if (PreyUtil.getHubLevel("copsNBandits") == 0)
            {
                return true;
            }
            return false;
        }//end

        public boolean  checkPopulation (Object param1)
        {
            return Global.world.citySim.getPopulation() * Global.gameSettings().getInt("populationScale", 1) >= int(param1.requiredPopulation);
        }//end

        public boolean  hasObjectOnGameBoard (Object param1)
        {
            Class _loc_2 =null ;
            Array _loc_3 =null ;
            if (param1.itemName)
            {
                return Global.world.getObjectsByNames(.get(param1.itemName)).length > 0;
            }
            if (param1.className)
            {
                _loc_2 = Class(getDefinitionByName(param1.className));
                if (_loc_2)
                {
                    _loc_3 = Global.world.getObjectsByClass(_loc_2);
                    if (_loc_3.length())
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public boolean  hasNotObjectOnGameBoard (Object param1)
        {
            return !this.hasObjectOnGameBoard(param1);
        }//end

        public boolean  hasObjectAnywhere (Object param1)
        {
            return !this.hasNotObjectAnywhere(param1);
        }//end

        public boolean  hasNotObjectAnywhere (Object param1)
        {
            _loc_2 = (String)(param1.itemName );
            boolean _loc_3 =false ;
            if (String(param1.ignoreInheritance) == "true")
            {
                _loc_3 = Global.player.inventory.verifySingletonItemIgnoreInheritance(_loc_2);
            }
            else
            {
                _loc_3 = Global.player.inventory.verifySingletonItem(_loc_2);
            }
            return _loc_3;
        }//end

        public boolean  hasOrIsBuildingObjectOnGameBoard (Object param1)
        {
            ConstructionSite _loc_3 =null ;
            Class _loc_4 =null ;
            if (this.hasObjectOnGameBoard(param1))
            {
                return true;
            }
            _loc_2 =Global.world.getObjectsByClass(ConstructionSite );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (param1.itemName)
                {
                    if (_loc_3.targetName == param1.itemName)
                    {
                        return true;
                    }
                    continue;
                }
                if (param1.className)
                {
                    _loc_4 = Class(getDefinitionByName(param1.className));
                    if (_loc_3.targetClass == _loc_4)
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public boolean  doesNotHaveAndIsNotBuildingObjectOnGameBoard (Object param1)
        {
            return !this.hasOrIsBuildingObjectOnGameBoard(param1);
        }//end

        public boolean  canPurchaseItem (Object param1)
        {
            if (param1.itemName)
            {
                return !BuyLogic.isLocked(Global.gameSettings().getItemByName(param1.itemName));
            }
            return false;
        }//end

        public boolean  hasNoObjectOnGameBoard (Object param1)
        {
            if (param1.itemName)
            {
                return Global.world.getObjectsByNames(.get(param1.itemName)).length == 0;
            }
            return false;
        }//end

        public boolean  shouldSeeStarterPack (Object param1)
        {
            _loc_2 = param1.experimentVariant ? (param1.experimentVariant) : (1);
            _loc_3 =Global.experimentManager.getVariant("cv_starter_pack2")==_loc_2 ;
            return _loc_3 === true && Global.player.hasMadeRealPurchase === false;
        }//end

        public boolean  socialInventoryEnabled (Object param1)
        {
            return SocialInventoryManager.isFeatureAvailable();
        }//end

        public boolean  hasNotMadeRealPurchase (Object param1)
        {
            return !Global.player.hasMadePurchase;
        }//end

        public boolean  playerDoesNotOwnTerritory (Object param1)
        {
            _loc_2 = param1.get("x") ;
            _loc_3 = param1.get("y") ;
            return !Global.world.territory.pointInTerritory(_loc_2, _loc_3);
        }//end

    }



