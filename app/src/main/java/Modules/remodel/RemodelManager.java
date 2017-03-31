package Modules.remodel;

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
import Display.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;

import Classes.sim.*;

    public class RemodelManager implements IGameWorldStateObserver
    {
        protected boolean m_enableFlag ;
        private static RemodelManager s_instance =getInstance ();
        public static  String MECHANIC_TYPE ="remodel";
        public static  Array CONSTRUCTION_MUN_NAMES =.get( "mun_constructioncompany","mun_constructioncompany_2","mun_constructioncompany_3") ;
        public static  String TOASTER_ICON ="assets/remodel/remodel_citySam_toaster.png";
        public static  String INTRO_GUIDE_TRIGGER ="guideToToolMenu";
        private static boolean s_experimentFlag =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REMODEL_V1 )==ExperimentDefinitions.REMODEL_V1_FEATURE ;

        public  RemodelManager ()
        {
            if (!s_instance)
            {
                s_instance = this;
                Global.world.addObserver(this);
            }
            else
            {
                throw new Error("You cannot instantiate more than one RemodelManager!!");
            }
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            String _loc_5 =null ;
            boolean _loc_4 =false ;
            for(int i0 = 0; i0 < CONSTRUCTION_MUN_NAMES.size(); i0++)
            {
            		_loc_5 = CONSTRUCTION_MUN_NAMES.get(i0);

                if (param1.getItemName() == _loc_5)
                {
                    _loc_4 = true;
                    break;
                }
            }
            if (_loc_4 && param2 == null)
            {
                Global.guide.notify(INTRO_GUIDE_TRIGGER);
                this.m_enableFlag = true;
                Global.world.removeObserver(this);
            }
            return;
        }//end

        public static RemodelManager  getInstance ()
        {
            if (!s_instance)
            {
                s_instance = new RemodelManager;
            }
            return s_instance;
        }//end

        public static boolean  isFeatureAvailable (boolean param1 =false )
        {
            return s_experimentFlag && Global.player.level >= Global.gameSettings().getInt("remodelingRequiredLevel", 15) && (param1 || getInstance().m_enableFlag);
        }//end

        public static Array  getConstructionCompanyObjects ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array();
            for(int i0 = 0; i0 < CONSTRUCTION_MUN_NAMES.size(); i0++)
            {
            		_loc_2 = CONSTRUCTION_MUN_NAMES.get(i0);

                _loc_1.push(Global.gameSettings().getItemByName(_loc_2));
            }
            return _loc_1;
        }//end

        public static void  loadFeatureData (Object param1)
        {
            _loc_2 = param1&& param1.hasOwnProperty("enabled") && param1.get("enabled") as Boolean;
            getInstance().m_enableFlag = _loc_2;
            if (_loc_2)
            {
                Global.world.removeObserver(getInstance());
            }
            return;
        }//end

        public static boolean  isRemodelPossible (MapResource param1 )
        {
            Vector _loc_4.<RemodelDefinition >=null ;
            RemodelDefinition _loc_5 =null ;
            if (!isFeatureAvailable())
            {
                return false;
            }
            _loc_2 = param1as MechanicMapResource ;
            if (!_loc_2 || !_loc_2.hasMechanicAvailable(MECHANIC_TYPE))
            {
                return false;
            }
            _loc_3 = param1.getItem ();
            if (_loc_3)
            {
                _loc_4 = _loc_3.getAllRemodelDefinitions();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    if (!_loc_5.requirements || _loc_5.requirements.checkRequirements(param1))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public static RemodelDefinition  getRemodelDefinition (String param1 )
        {
            RemodelDefinition _loc_2 =null ;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.getRemodelDefinitionByName(param1);
            }
            return _loc_2;
        }//end

        public static Item  getRemodelBaseItem (String param1 )
        {
            Item _loc_2 =null ;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3 && _loc_3.derivedItemName)
            {
                _loc_2 = Global.gameSettings().getItemByName(_loc_3.derivedItemName);
            }
            return _loc_2;
        }//end

        public static boolean  canPurchaseSkin (MapResource param1 ,String param2 )
        {
            boolean _loc_3 =false ;
            _loc_4 = getRemodelDefinition(param2);
            if (isFeatureAvailable() && _loc_4 && param1)
            {
                _loc_3 = _loc_4.requirements ? (_loc_4.requirements.checkRequirements(param1)) : (true);
            }
            return _loc_3;
        }//end

        public static int  compareRemodelSkins (Item param1 ,Item param2 )
        {
            if (param1.cash > param2.cash)
            {
                return 1;
            }
            if (param1.cash < param2.cash)
            {
                return -1;
            }
            if (param1.cost > param2.cost)
            {
                return 1;
            }
            if (param1.cost < param2.cost)
            {
                return -1;
            }
            return 0;
        }//end

        public static boolean  hasRemodelEligibleResidence (Item param1 )
        {
            Residence _loc_2 =null ;
            Object _loc_3 =null ;
            for(int i0 = 0; i0 < Global.world.getObjectsByTypes(.get(WorldObjectTypes.RESIDENCE)).size(); i0++)
            {
            		_loc_2 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.RESIDENCE)).get(i0);

                if (_loc_2.getItem().name != param1.name && (_loc_2.getItem().name == param1.derivedItemName || _loc_2.getItem().derivedItemName == param1.derivedItemName))
                {
                    _loc_3 = _loc_2.getDataForMechanic("remodel");
                    if (_loc_3 && _loc_3.hasOwnProperty("itemName") && _loc_3.hasOwnProperty("gate"))
                    {
                        continue;
                    }
                    return true;
                }
            }
            return false;
        }//end

        public static boolean  isConstructionSitePending ()
        {
            ConstructionSite _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 =Global.world.getObjectsByNames(.get( "construction_4x4_10stage") );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                for(int i0 = 0; i0 < CONSTRUCTION_MUN_NAMES.size(); i0++)
                {
                		_loc_3 = CONSTRUCTION_MUN_NAMES.get(i0);

                    if (_loc_3 == _loc_2.targetName)
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public static void  runIntroFlow ()
        {
            Array _loc_1 =null ;
            Array _loc_2 =null ;
            String _loc_3 =null ;
            Item _loc_4 =null ;
            Global.ui.turnOffHighlightedObject();
            if (!getInstance().m_enableFlag)
            {
                _loc_1 = getConstructionCompanyObjects();
                if (_loc_1)
                {
                    _loc_2 = Global.world.getObjectsByNames(CONSTRUCTION_MUN_NAMES);
                    if (_loc_2.length == 0)
                    {
                        _loc_3 = null;
                        for(int i0 = 0; i0 < _loc_1.size(); i0++)
                        {
                        		_loc_4 = _loc_1.get(i0);

                            _loc_2 = _loc_2.concat(Global.world.getObjectsByTargetName(_loc_4.name));
                            if (Global.player.inventory.getItemCountByName(_loc_4.name) > 0)
                            {
                                _loc_3 = _loc_4.name;
                            }
                        }
                        if (_loc_2.length > 0)
                        {
                            Global.world.setDefaultGameMode();
                            Global.world.centerOnObject(_loc_2.get(0));
                        }
                        else if (_loc_3)
                        {
                            UI.displayInventory(_loc_3);
                        }
                    }
                }
            }
            return;
        }//end

    }



