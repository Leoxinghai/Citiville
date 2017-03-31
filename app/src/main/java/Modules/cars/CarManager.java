package Modules.cars;

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
import Classes.doobers.*;
import Classes.featuredata.*;
import Classes.util.*;
import Engine.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;

    public class CarManager
    {
        protected Array m_carNames ;
        private double m_lastCarSpawn =0;
        private static CarManager m_instance ;

        public  CarManager ()
        {
            this.m_lastCarSpawn = 0;
            return;
        }//end

        public void  processCarHarvest (NPC param1 )
        {
            Array _loc_5 =null ;
            double _loc_2 =0;
            _loc_3 =Global.gameSettings().getNumber("carHarvestBuildingModifier",0);
            _loc_2 = Math.min(_loc_3, param1.pathBuildingScore);
            _loc_2 = _loc_2 * Global.gameSettings().getNumber("carHarvestModifier", 1);
            _loc_2 = _loc_2 * param1.getItem().carTier;
            String _loc_4 ="";
            if (Global.world.dooberManager.isDoobersEnabled() && _loc_2 > 0)
            {
                _loc_5 = new Array();
                if (Global.questManager.isQuestActive("qf_cars2") && !Global.player.isQuestCompleted("qf_cars2"))
                {
                    _loc_4 = "default";
                    _loc_5 = Global.player.processRandomModifiers(param1.getItem(), null, true, param1.secureRandsArray);
                }
                _loc_5.push(.get(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_2), _loc_2));
                Global.world.dooberManager.createBatchDoobers(_loc_5, null, param1.getPosition().x, param1.getPosition().y);
            }
            else
            {
                Global.player.gold = Global.player.gold + _loc_2;
            }
            GameTransactionManager.addTransaction(new THarvestCar(param1.getItem().name, _loc_2), true);
            return;
        }//end

        public String  checkCarSpawn ()
        {
            double _loc_3 =0;
            double _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            Array _loc_7 =null ;
            int _loc_8 =0;
            Object _loc_9 =null ;
            Array _loc_10 =null ;
            int _loc_11 =0;
            int _loc_12 =0;
            String _loc_13 =null ;
            int _loc_14 =0;
            int _loc_15 =0;
            NPC _loc_16 =null ;
            Object _loc_17 =null ;
            int _loc_18 =0;
            int _loc_19 =0;
            int _loc_20 =0;
            _loc_1 = (CarData)FeatureDataManager.instance.getFeatureDataClass("cars")
            String _loc_2 ="none";
            if (_loc_1.carsEnabled)
            {
                if (this.m_carNames == null)
                {
                    this.m_carNames = Global.gameSettings().getString("availableCarTypes", "car").split(",");
                }
                _loc_3 = GlobalEngine.getTimer() / 1000;
                _loc_4 = Global.gameSettings().getNumber("carSpawnDelay", 0);
                if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CARS) == 1 && _loc_3 - this.m_lastCarSpawn > _loc_4)
                {
                    _loc_5 = MathUtil.randomIncl(1, 100);
                    _loc_6 = Global.gameSettings().getInt("carSpawnChance", 1);
                    if (_loc_1.carsToForceSpawn > 0)
                    {
                        _loc_5 = 0;
                        (_loc_1.carsToForceSpawn - 1);
                    }
                    if (_loc_5 < _loc_6)
                    {
                        _loc_7 = Global.world.citySim.npcManager.getAllBusinessCars();
                        _loc_8 = RuntimeVariableManager.getInt("MAXIMUM_ACTIVE_CARS", 10);
                        _loc_9 = _loc_1.getItems();
                        if (_loc_7.length < _loc_8 && _loc_9 != null)
                        {
                            _loc_10 = new Array();
                            _loc_11 = 0;
                            _loc_12 = 0;
                            while (_loc_12 < this.m_carNames.length())
                            {

                                _loc_13 = this.m_carNames.get(_loc_12);
                                if (_loc_9.hasOwnProperty(_loc_13))
                                {
                                    _loc_14 = _loc_9.get(_loc_13);
                                    _loc_15 = 0;
                                    while (_loc_15 < _loc_7.length())
                                    {

                                        _loc_16 =(NPC) _loc_7.get(_loc_15);
                                        if (_loc_16.getItemName() == _loc_13)
                                        {
                                            _loc_14 = _loc_14 - 1;
                                        }
                                        _loc_15++;
                                    }
                                    if (_loc_14 > 0)
                                    {
                                        _loc_11 = _loc_11 + _loc_14;
                                        _loc_17 = new Object();
                                        _loc_17.put("name",  _loc_13);
                                        _loc_17.put("number",  _loc_14);
                                        _loc_10.push(_loc_17);
                                    }
                                }
                                _loc_12++;
                            }
                            if (_loc_11 > 0)
                            {
                                _loc_18 = MathUtil.random(0, _loc_11);
                                _loc_19 = 0;
                                _loc_20 = 0;
                                while (_loc_20 < _loc_10.length())
                                {

                                    _loc_19 = _loc_19 + _loc_10.get(_loc_20).get("number");
                                    if (_loc_18 < _loc_19)
                                    {
                                        _loc_2 = _loc_10.get(_loc_20).get("name");
                                        this.m_lastCarSpawn = GlobalEngine.getTimer() / 1000;
                                        break;
                                    }
                                    _loc_20++;
                                }
                            }
                        }
                    }
                }
            }
            if (_loc_2 != "none")
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "cars", "harvest", _loc_2, "", "", 0);
            }
            return _loc_2;
        }//end

        public static CarManager  instance ()
        {
            if (!CarManager.m_instance)
            {
                CarManager.m_instance = new CarManager;
            }
            return CarManager.m_instance;
        }//end

        public static void  enableCars ()
        {
            Array _loc_1 =null ;
            _loc_2 = (CarData)FeatureDataManager.instance.getFeatureDataClass("cars")
            if (!_loc_2.carsEnabled)
            {
                _loc_1 = Global.world.getObjectsByNames(.get("mun_cargarage"));
                if (_loc_1.length > 0)
                {
                    _loc_2.carsEnabled = true;
                    _loc_2.carsToForceSpawn = 5;
                    _loc_2.updateItemAmount("car_cvbee", 3);
                    _loc_2.updateItemAmount("car_tinycruiser", 5);
                    Global.guide.notify("CarGuide");
                    GameTransactionManager.addTransaction(new TEnableCars(), true);
                }
            }
            return;
        }//end

    }



