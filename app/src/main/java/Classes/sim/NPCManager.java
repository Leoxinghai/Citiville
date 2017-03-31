package Classes.sim;

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
import Classes.Desires.*;
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.cars.*;
import Modules.stats.experiments.*;

import com.adobe.utils.*;
import com.zynga.skelly.util.*;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class NPCManager implements IGameWorldUpdateObserver, IPlayerStateObserver
    {
        protected GameWorld m_world ;
        protected Array m_walkers ;
        protected Array m_cars ;
        protected Array m_businesscars ;
        protected Array m_ships ;
        protected Array m_clouds ;
        protected Dictionary m_pickupPeeps ;
        protected Dictionary m_deliveryPeeps ;
        protected Dictionary m_builders ;
        protected int m_shipsMin ;
        protected Array m_npcsNames ;
        protected Array m_validNpcNames ;
        protected Array m_shipNames ;
        protected int m_maxVisibleWalkers ;
        protected int m_maxTotalWalkers ;
        protected Timer m_timer ;
        protected Array m_cacheResidential ;
        protected Array m_cacheDecorations ;
        protected Array m_cacheBusinesses ;
        protected boolean m_debugPathing =false ;
        protected double m_stragglerFraction ;
        protected double m_stragglerSpawnDelay ;
        protected double m_timeSinceLastWalkerUpdate =0;
        protected int m_numberOfBusinessPeepsToSpawn =0;
        private boolean m_experimentNPCThinning =true ;
        private double m_timeSinceLastThinningSweep =0;
        private Vector<NPC> m_thinningSweepVec;
        protected double m_timeSinceLastPeepTireOut =0;
        protected double m_timeSinceLastBusinessPeepSpawn =0;
        protected double m_peepTireOutDelay ;
        protected double m_peepTireOutFraction ;
        protected double m_businessPeepSpawnDelay ;
        protected double m_businessPeepSpawnFraction ;
        protected double m_businessPeepSpawnThreshold ;
        protected boolean m_spawnBusinessPeeps =false ;
        protected boolean m_spawnAttractionsPeeps =false ;
        protected Array m_housesOpen ;
        protected Array m_housesClosed ;
        protected Dictionary m_hotelSpawn ;
        protected double m_timeSinceLastVacationerPeepSpawn =0;
        protected double m_vacationerPeepSpawnDelay =0.5;
        private static  double m_thinningSweepRate =0.7;



        public  NPCManager (GameWorld param1 )
        {
            this.m_walkers = new Array();
            this.m_cars = new Array();
            this.m_businesscars = new Array();
            this.m_ships = new Array();
            this.m_clouds = new Array();
            this.m_pickupPeeps = new Dictionary();
            this.m_deliveryPeeps = new Dictionary(true);
            this.m_builders = new Dictionary();
            this.m_thinningSweepVec = new Vector<NPC>();
            this.m_world = param1;
            param1.addObserver(this);
            Global.player.addObserver(this);
            this.m_experimentNPCThinning = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_NPC_THINNING) ? (true) : (false);
            return;
        }//end

        public void  initialize ()
        {
            this.m_walkers = new Array();

            this.m_cars = new Array();
            this.m_businesscars = new Array();
            this.m_ships = new Array();
            this.m_clouds = new Array();
            this.m_housesOpen = new Array();
            this.m_housesClosed = new Array();
            this.m_deliveryPeeps = new Dictionary(true);
            this.m_builders = new Dictionary();
            this.m_pickupPeeps = new Dictionary();
            this.m_hotelSpawn = new Dictionary();
            this.m_npcsNames = Global.gameSettings().getString("npcTypes", "walking_guy").split(",");
            this.m_stragglerFraction = Global.gameSettings().getNumber("npcStragglerFraction", 0.1);
            this.m_stragglerSpawnDelay = Global.gameSettings().getNumber("npcStragglerSpawnDelay", 1);
            this.m_peepTireOutDelay = Global.gameSettings().getNumber("npcPeepTireOutDelay", 10);
            this.m_peepTireOutFraction = Global.gameSettings().getNumber("npcPeepTimeOutFraction", 0.1);
            this.m_businessPeepSpawnFraction = Global.gameSettings().getNumber("npcBusinessPeepSpawnFraction", 0.1);

            //add by xinghai
            //this.m_businessPeepSpawnThreshold = Global.gameSettings().getNumber("npcBusinessPeepSpawnThreshold", 7);
            //this.m_businessPeepSpawnThreshold = Global.gameSettings().getNumber("npcBusinessPeepSpawnThreshold", 1);
            this.m_businessPeepSpawnThreshold = 300;

            this.m_businessPeepSpawnDelay = Global.gameSettings().getNumber("npcBusinessPeepSpawnDelay", 1);
            this.m_shipsMin = Global.gameSettings().getInt("minShips");
            this.m_shipNames = Global.gameSettings().getString("shipTypes", "ship").split(",");
            this.m_maxVisibleWalkers = Global.gameSettings().getInt("maxVisibleWalkers", 75);
            this.m_maxTotalWalkers = Global.gameSettings().getInt("maxTotalWalkers", 350);
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MAX_NUM_OF_WALKERS );
            if (_loc_1)
            {
                this.m_maxVisibleWalkers = Math.min(this.m_maxVisibleWalkers, 50);
                this.m_maxTotalWalkers = Math.min(this.m_maxTotalWalkers, 100);
            }
            this.updateBuildingCaches();
            this.m_timer = new Timer(500);
            this.m_timer.addEventListener(TimerEvent.TIMER, this.onTimer);
            return;
        }//end

        public void  cheatToggleNpcVisibility ()
        {
            NPC _loc_2 =null ;
            _loc_1 = this.getAllWalkers ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_2.alwaysCulled = true;
            }
            return;
        }//end

        public void  cheatNpcRenderTest (boolean param1 )
        {
            int _loc_2 =0;
            this.m_shipsMin = 0;
            this.m_npcsNames = Global.gameSettings().getString(param1 ? ("cheatNpcBenderTypes") : ("cheatNpcNormalTypes"), "").split(",");
            while (_loc_2 < 30)
            {

                this.createPeepWalker(this.getRandomWalkerStartingPoint(), false);
                _loc_2++;
            }
            return;
        }//end

        public void  cheatNpcEverybodyIdle ()
        {
            NPC _loc_2 =null ;
            _loc_1 =Global.gameSettings().getString("cheatNpcEverybodyAction","");
            for(int i0 = 0; i0 < this.m_walkers.size(); i0++)
            {
            	_loc_2 = this.m_walkers.get(i0);

                _loc_2.getStateMachine().addState(new ActionPlayAnimation(_loc_2, _loc_1, 5));
            }
            return;
        }//end

        public void  cleanUp ()
        {
            this.m_walkers = null;
            this.m_cars = null;
            this.m_businesscars = null;
            this.m_ships = null;
            this.m_clouds = null;
            this.m_housesOpen = null;
            this.m_housesClosed = null;
            this.m_deliveryPeeps = null;
            this.m_builders = null;
            this.m_pickupPeeps = new Dictionary();
            this.m_cacheResidential = null;
            this.m_cacheDecorations = null;
            this.m_cacheBusinesses = null;
            this.m_timer.stop();
            this.m_timer.removeEventListener(TimerEvent.TIMER, this.onTimer);
            this.m_timer = null;
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            this.updateBuildingCaches();
            this.initOpenHouses();
            this.m_timer.start();
            return;
        }//end

        protected int  emptyCityStragglerCount ()
        {
            return Math.min(this.m_businessPeepSpawnThreshold, Global.world.citySim.getPopulation());
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  update (double param1 )
        {
            String _loc_2 =null ;
            MapResource _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            NPC _loc_6 =null ;
            int _loc_7 =0;
            this.m_timeSinceLastWalkerUpdate = this.m_timeSinceLastWalkerUpdate + param1;
            this.m_timeSinceLastPeepTireOut = this.m_timeSinceLastPeepTireOut + param1;
            this.m_timeSinceLastBusinessPeepSpawn = this.m_timeSinceLastBusinessPeepSpawn + param1;
            this.m_timeSinceLastVacationerPeepSpawn = this.m_timeSinceLastVacationerPeepSpawn + param1;
            this.m_timeSinceLastThinningSweep = this.m_timeSinceLastThinningSweep + param1;
            if (this.m_spawnBusinessPeeps == false)
            {
                this.m_numberOfBusinessPeepsToSpawn = 0;
            }


            if (this.m_timeSinceLastWalkerUpdate > this.m_stragglerSpawnDelay && !this.m_spawnBusinessPeeps)
            {
                this.updateWalkers();
            }
            if (this.m_timeSinceLastPeepTireOut > this.m_peepTireOutDelay)
            {
                this.tireOutPeeps(this.m_peepTireOutFraction);
                this.m_timeSinceLastPeepTireOut = 0;
            }

            if (this.m_timeSinceLastBusinessPeepSpawn > this.m_businessPeepSpawnDelay && (this.m_spawnBusinessPeeps || this.m_spawnAttractionsPeeps))
            {
                if (Math.random() <= 0.2 && this.m_spawnAttractionsPeeps)
                {
                    this.createAttractionDesireWalker(1);
                }
                else if (this.m_numberOfBusinessPeepsToSpawn > 0)
                {
                    this.createBusinessDesireWalker(1, true);
                }
                this.m_timeSinceLastBusinessPeepSpawn = 0;
            }


            if (this.m_timeSinceLastVacationerPeepSpawn > this.m_vacationerPeepSpawnDelay)
            {



                for(int i0 = 0; i0 < this.m_hotelSpawn.size(); i0++)
                {
                		_loc_2 = this.m_hotelSpawn.get(i0);



                    _loc_3 =(MapResource) Global.world.getObjectById(int(_loc_2));
                    this.createVacationersFromSource(_loc_3, 1);
                    _loc_10 = this.m_hotelSpawn ;
                    _loc_11 = _loc_2;
                    _loc_12 = this.m_hotelSpawn.get(_loc_2) -1;
                    _loc_10.put(_loc_11,  _loc_12);
                    if (this.m_hotelSpawn.get(_loc_2) == 0)
                    {
                        this.stopSpawningVacationersFromResource(_loc_3);
                    }
                }
                this.m_timeSinceLastVacationerPeepSpawn = 0;
            }
            if (this.m_experimentNPCThinning && this.m_timeSinceLastThinningSweep > m_thinningSweepRate)
            {
                this.m_timeSinceLastThinningSweep = 0;
                this.m_thinningSweepVec.length = 0;
                _loc_4 = this.m_walkers.length;
                _loc_5 = 0;
                while (_loc_5 < _loc_4)
                {

                    _loc_6 = this.m_walkers.get(_loc_5);
                    if (_loc_6.isVisible() && _loc_6.canBeThinned())
                    {
                        this.m_thinningSweepVec.push(_loc_6);
                    }
                    _loc_5 = _loc_5 + 1;
                }
                if (this.m_thinningSweepVec.length > this.m_maxVisibleWalkers)
                {
                    _loc_7 = this.m_thinningSweepVec.length - this.m_maxVisibleWalkers;
                    while (_loc_7--)
                    {

                        _loc_6 = this.m_thinningSweepVec.get(int(Math.random() * this.m_thinningSweepVec.length()));
                        _loc_6.alwaysCulled = true;
                    }
                }
            }
            return;
        }//end

        public void  updateWalkers ()
        {
            Array _loc_2 =null ;
            Peep _loc_3 =null ;
            _loc_1 = Math.max(0,this.emptyCityStragglerCount -this.m_walkers.length );


            if(this.m_walkers.length >=94) {
                Debug.debug7("NPCManager.updateWalkers>=94");
            }


            if (_loc_1 > 0)
            {
                _loc_2 = this.createBusinessDesireWalker(_loc_1, false);

                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                        _loc_3.getStateMachine().removeAllStates();
			_loc_3.getStateMachine().addActions(

			new ActionFn (_loc_3 ,void  ()
            {
                _loc_3.animation = "static";
                return;
            }//end
            ), //new ActionPause(_loc_3,30),new ActionDie(_loc_3));
*/
			new ActionNavigateBeeline(_loc_3, new Vector3(30,-30,0)), new ActionPause(_loc_3,30), new ActionDie(_loc_3));

			_loc_3.isStraggler = true;
                }
            }
            this.m_timeSinceLastWalkerUpdate = 0;
            return;
        }//end

        public int  walkerCount ()
        {
            return this.m_walkers.length;
        }//end

        public int  getVisibleWalkerCount ()
        {
            NPC _loc_2 =null ;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.m_walkers.size(); i0++)
            {
            	_loc_2 = this.m_walkers.get(i0);

                if (_loc_2.isVisible())
                {
                    _loc_1++;
                }
            }
            return _loc_1;
        }//end

        public void  startSpawningBusinessPeeps (int param1 =0)
        {
            this.m_spawnBusinessPeeps = true;
            this.m_numberOfBusinessPeepsToSpawn = this.m_numberOfBusinessPeepsToSpawn + param1;
            return;
        }//end

        public void  stopSpawningBusinessPeeps ()
        {
            this.m_spawnBusinessPeeps = false;
            return;
        }//end

        public void  startSpawningAttractionsPeeps ()
        {
            this.m_spawnAttractionsPeeps = true;
            return;
        }//end

        public void  stopSpawningAttractionsPeeps ()
        {
            this.m_spawnAttractionsPeeps = false;
            return;
        }//end

        public void  updateBuildingCaches ()
        {
            WorldObject _loc_2 =null ;
            Array _loc_3 =null ;
            Residence _loc_4 =null ;
            this.m_cacheResidential = new Array();
            this.m_cacheDecorations = new Array();
            this.m_cacheBusinesses = new Array();
            _loc_1 =Global.world.getObjects ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2 instanceof Residence)
                {
                    this.m_cacheResidential.push(_loc_2);
                }
                if (_loc_2 instanceof Decoration)
                {
                    this.m_cacheDecorations.push(_loc_2);
                }
                if (_loc_2 instanceof Business)
                {
                    this.m_cacheBusinesses.push(_loc_2);
                }
                if (_loc_2 instanceof Neighborhood)
                {
                    _loc_3 = ((Neighborhood)_loc_2).slots;
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    	_loc_4 = _loc_3.get(i0);

                        _loc_4.pathProvider =(MapResource) _loc_2;
                        this.m_cacheResidential.push(_loc_4);
                    }
                }
            }
            return;
        }//end

        protected void  initOpenHouses ()
        {
            Residence _loc_1 =null ;

            for(int i0 = 0; i0 < this.m_cacheResidential.size(); i0++)
            {
            	_loc_1 = this.m_cacheResidential.get(i0);
                    this.m_housesOpen.push(_loc_1);

            }
            return;
        }//end

        public void  addNewResidence (Residence param1 )
        {
            this.m_housesOpen.push(param1);
            return;
        }//end

        public void  removeResidence (Residence param1 )
        {
            _loc_2 = param1.hasOccupants ()? (this.m_housesOpen) : (this.m_housesClosed);
            _loc_3 = _loc_2.indexOf(param1 );
            if (_loc_3 != -1)
            {
                _loc_2.splice(_loc_3, 1);
            }
            return;
        }//end

        public Array  cachedDecorations ()
        {
            return this.m_cacheDecorations;
        }//end

        public Array  cachedResidences ()
        {
            return this.m_cacheResidential;
        }//end

        public void  onBusinessClosed ()
        {
            _loc_1 =Global.world.getObjectsByPredicate(this.predicateBusinessIsOpen );
            if (_loc_1.length == 0)
            {
                this.stopSpawningBusinessPeeps();
            }
            return;
        }//end

        public void  onAttractionsClosed ()
        {
            _loc_1 =Global.world.getObjectsByPredicate(this.predicateAttractionIsOpen );
            if (_loc_1.length == 0)
            {
                this.stopSpawningAttractionsPeeps();
            }
            return;
        }//end

        protected boolean  predicateBusinessIsOpen (Object param1 )
        {
            return param1 instanceof Business && ((Business)param1).isOpen() && !((Business)param1).isNeedingRoad;
        }//end

        protected boolean  predicateAttractionIsOpen (Object param1 )
        {
            return param1 instanceof Attraction && ((IMerchant)param1).isRouteable();
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

        public Dictionary  constructionWorkers ()
        {
            return this.m_builders;
        }//end

        public void  setConstructionWorkers (MapResource param1 ,Array param2 )
        {
            _loc_3 = this.m_builders.get(param1) != null ? (((Array)this.m_builders.get(param1)).length()) : (0);
            if (param2.length < _loc_3)
            {
                this.removeAllConstructionWorkers(param1);
                _loc_3 = 0;
            }
            this.addConstructionWorkers(param1, param2);
            return;
        }//end

        public void  addConstructionWorkers (MapResource param1 ,Array param2 )
        {
            Object _loc_3 =null ;
            NPC _loc_4 =null ;
            if (this.m_builders.get(param1) == null)
            {
                this.m_builders.put(param1,  new Array());
            }
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_3 = param2.get(i0);

                _loc_4 = new NPC("walking_guy", false);
                this.setBuilderAnimation(_loc_4, param1, _loc_3);
                _loc_4.setOuter(this.m_world);
                _loc_4.attach();
                this.m_builders.get(param1).push(_loc_4);
            }
            return;
        }//end

        public void  removeAllConstructionWorkers (MapResource param1 )
        {
            NPC _loc_2 =null ;
            if (!this.m_builders || this.m_builders.get(param1) == null)
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_builders.get(param1).size(); i0++)
            {
            	_loc_2 = this.m_builders.get(param1).get(i0);

                _loc_2.detach();
                _loc_2.cleanUp();
            }
            delete this.m_builders.get(param1);
            return;
        }//end

        private void  setBuilderAnimation (NPC param1 ,MapResource param2 ,Object param3 )
        {
            _loc_4 = param2.getPosition ();
            param2.getPosition().x = param2.getPosition().x + param2.getConstructionNPCOffset().x;
            _loc_4.y = _loc_4.y + param2.getConstructionNPCOffset().y;
            _loc_5 = param2.getItem ().sizeX -1;
            _loc_6 = param2.getItem ().sizeY -2;
            _loc_7 = _loc_4.x +0.33;
            _loc_8 = _loc_4.y-0.33;
            _loc_9 = Math.random ()<_loc_5 /(_loc_5 +_loc_6 );
            _loc_10 = this.m_builders.get(param2) != null ? (this.m_builders.get(param2).length()) : (0);
            _loc_11 = this.m_builders.get(param2) != null ? (this.m_builders.get(param2).length()) : (0);
            param1.setDirection(_loc_9 ? (Constants.DIRECTION_NW) : (Constants.DIRECTION_NE));
            if (_loc_9)
            {
                if (_loc_11 > _loc_5)
                {
                    _loc_11 = Math.floor(Math.random() * _loc_5);
                }
                _loc_7 = _loc_4.x + _loc_11 + 1;
                param1.setPosition(_loc_7, _loc_8);
            }
            else
            {
                if (_loc_11 > _loc_6)
                {
                    _loc_11 = Math.floor(Math.random() * _loc_6);
                }
                _loc_8 = _loc_4.y + _loc_11 + 1;
                param1.setPosition(_loc_7, _loc_8);
            }
            String _loc_12 ="hammer";
            if (param3.isTempWorker)
            {
                param1.getStateMachine().addState(new ActionPlayAnimation(param1, _loc_12, 2), true);
                param1.getStateMachine().addState(new ActionPlayAnimation(param1, "cloud", 0.5), true);
                param1.getStateMachine().addState(new ActionDie(param1), true);
            }
            else
            {
                param1.getStateMachine().addState(new ActionPlayAnimation(param1, "cloud", 0.5));
                param1.getStateMachine().addState(new ActionPlayAnimation(param1, _loc_12, Number.POSITIVE_INFINITY), true);
            }
            return;
        }//end

        public NPC  getPickupNPC (String param1 )
        {
            return this.m_pickupPeeps.get(param1) ? (this.m_pickupPeeps.get(param1)) : (null);
        }//end

        public void  createPickupNPC (String param1 ,boolean param2 )
        {


            _loc_3 = param2? (DummyNPC) : (NPC);
            _loc_4 = new _loc_3(param1 ,false );
            _loc_4.setOuter(this.m_world);
            _loc_4.attach();
            this.m_pickupPeeps.put(param1,  _loc_4);
            UI.popupLock();
            Global.world.citySim.pickupManager.addPickupAction(_loc_4);
            return;
        }//end

        public void  removePickupNPC (String param1 )
        {
            if (this.m_pickupPeeps.get(param1))
            {
                this.m_pickupPeeps.get(param1).detach();
                this.m_pickupPeeps.get(param1).cleanUp();
                delete this.m_pickupPeeps.get(param1);
                UI.popupUnlock();
            }
            return;
        }//end

        protected Array  createRandomStragglers (int param1 )
        {
            Peep _loc_3 =null ;
            Road _loc_4 =null ;
            _loc_2 = this.createBusinessPeepWalkers(param1 );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_3.isStraggler = true;
                _loc_4 = Global.world.citySim.roadManager.findRandomRoad();
                if (_loc_4 != null)
                {
                    _loc_3.setPosition(_loc_4.getPosition().x, _loc_4.getPosition().y);
                }
            }
            return _loc_2;
        }//end

        public Array  createBusinessPeepWalkers (int param1 )
        {
            return new Array();

            int _loc_5 =0;
            Residence _loc_6 =null ;
            boolean _loc_7 =false ;
            _loc_2 = Math.max(0,this.m_maxTotalWalkers -this.m_walkers.length );
            param1 = Math.min(param1, _loc_2);
            Array _loc_3 =new Array();

            if (this.m_housesOpen == null || this.m_housesOpen.length == 0)
            {
                return _loc_3;
            }



            int _loc_4 =0;
            while (_loc_4 < param1)
            {

                if (this.m_housesOpen.length > 0)
                {
                    _loc_5 = MathUtil.randomIndex(this.m_housesOpen);
                    _loc_6 = this.m_housesOpen.get(_loc_5);
                    _loc_7 = _loc_6.takeResident();
                    _loc_3.push(this.createPeepWalker(_loc_6, false));
                    if (_loc_7)
                    {
                        this.m_housesOpen.splice(_loc_5, 1);
                        this.m_housesClosed.push(_loc_6);
                    }
                }
                _loc_4++;
            }
            return _loc_3;
        }//end

        public void  removeBusinessPeepWalkers (int param1 )
        {
            int _loc_4 =0;
            Residence _loc_5 =null ;
            _loc_2 = this.findAllPeeps ();
            int _loc_3 =0;
            while (_loc_3 < param1)
            {

                this.removeNpc(MathUtil.randomElement(_loc_2));
                if (this.m_housesClosed != null && this.m_housesClosed.length > 0)
                {
                    _loc_4 = MathUtil.randomIndex(this.m_housesClosed);
                    _loc_5 = this.m_housesClosed.get(_loc_4);
                    _loc_5.returnResident();
                    this.m_housesClosed.splice(_loc_4, 1);
                    this.m_housesOpen.push(_loc_5);
                }
                _loc_3++;
            }
            return;
        }//end

        public void  onResidentReturnHome (Residence param1 )
        {
            param1.returnResident();
            if (this.m_housesClosed == null || this.m_housesClosed.length == 0)
            {
                return;
            }
            _loc_2 = this.m_housesClosed.indexOf(param1 );
            if (_loc_2 != -1)
            {
                this.m_housesClosed.splice(_loc_2, 1);
                this.m_housesOpen.push(param1);
            }
            return;
        }//end

        public Residence  getRandomReturnHome ()
        {
            if (this.m_housesClosed != null && this.m_housesClosed.length > 0)
            {
                return MathUtil.randomElement(this.m_housesClosed);
            }
            return null;
        }//end

        public void  tireOutPeeps (int param1 =-1)
        {
            Peep peep ;
            Array desires ;
            Desire lastDesire ;
            Desire desire ;
            Array actions ;
            count = param1;
            peeps = this.findAllPeepsByPredicate(function(param11Peep)
            {
                _loc_2 = false;
                if (!param11.isFranchiseFreebie)
                {
                    if (param11 instanceof DesirePeep)
                    {
                        _loc_2 = !((DesirePeep)param11).wanderer;
                    }
                }
                return _loc_2;
            }//end
            );
            count = count == -1 ? (peeps.length()) : (count);
            int i =0;
            while (i < count)
            {

                peep = MathUtil.randomElement(peeps);
                peep.isTired = true;
                if (peep instanceof DesirePeep)
                {
                    desires = ((DesirePeep)peep).getDesires();
                    lastDesire = desires.pop();
                    int _loc_3 =0;
                    _loc_4 = desires;
                    for(int i0 = 0; i0 < desires.size(); i0++)
                    {
                    	desire = desires.get(i0);


                        desire.setState(Desire.STATE_FINISHED);
                    }
                    desires.push(lastDesire);
                    ((DesirePeep)peep).setDesires(desires);
                    if (lastDesire.getState() != Desire.STATE_NOT_STARTED)
                    {
                        actions = lastDesire.getSelection().actions;
                        peep.getStateMachine().removeAllStates();
                        if (actions && actions.length > 0)
                        {
                            peep.getStateMachine().addActions(actions);
                        }
                        else
                        {
                            peep.getStateMachine().addActions(new ActionDie(peep));
                        }
                    }
                }
                i = (i + 1);
            }
            return;
        }//end

        public Peep  createPeepWalker (IPeepTarget param1 ,boolean param2 )
        {
            Debug.debug5("NPCManager.createPeepWalker" + IPeepTarget);
            Peep peep ;
            source = param1;
            freebie = param2;
            position = source.getHotspot();
            resource = source.getMapResource();
            peep =(Peep) this.addWalker(this.getNpcName(resource), true, position, Peep);
            peep.animation = "cheer";
            peep .getStateMachine ().addActions (new ActionAnimationEffect (peep ,EffectType .FLASH ),new ActionFn (peep ,void  ()
            {
                peep.animation = "static";
                return;
            }//end
            ));
            if (resource instanceof Residence)
            {
                peep.setHome((Residence)resource);
            }
            if (!this.m_experimentNPCThinning)
            {
                if (!freebie && this.m_walkers.length > this.m_maxVisibleWalkers)
                {
                    if (this.getVisibleWalkerCount() > this.m_maxVisibleWalkers)
                    {
                        peep.alwaysCulled = true;
                    }
                }
            }
            return peep;
        }//end

        public Peep  createPeepWalkerAtPosition (Vector3 param1 ,boolean param2 ,String param3 =null )
        {
            Peep peep ;
            source = param1;
            freebie = param2;
            name = param3;
            peepName = name!= null ? (name) : (this.getNpcName(null));
            peep =(Peep) this.addWalker(peepName, true, source, Peep);
            peep.animation = "cheer";
            peep .getStateMachine ().addActions (new ActionAnimationEffect (peep ,EffectType .FLASH ),new ActionFn (peep ,void  ()
            {
                peep.animation = "static";
                return;
            }//end
            ));
            return peep;
        }//end

        public Array  findAllPeeps ()
        {
            return this .m_walkers .filter (boolean  (NPC param1 ,...args )
            {
                return param1 instanceof Peep;
            }//end
            );
        }//end

        public Array  findAllPeepsByPredicate (Function param1 )
        {
            predicate = param1;
            return this .m_walkers .filter (boolean  (NPC param11 ,...args )
            {
                boolean argsvalue =false ;
                if (param11 instanceof Peep)
                {
                    argsvalue = predicate((Peep)param11);
                }
                return argsvalue;
            }//end
            );
        }//end

        public Array  getAllNpcNames ()
        {
            return this.m_npcsNames;
        }//end

        protected String  getNpcName (MapResource param1 ,int param2 =-1)
        {
            _loc_3 = param1!= null ? (param1.npcNames) : (null);
            if (_loc_3 == null || _loc_3.length == 0)
            {
                _loc_3 = this.m_npcsNames;
            }
            if (param2 >= 0)
            {
                param2 = param2 % _loc_3.length;
                return _loc_3.get(param2);
            }
            return MathUtil.randomElement(_loc_3);
        }//end

        public NPC  createWalker (MapResource param1 ,boolean param2 )
        {
            _loc_3 = param1.getHotspot ();
            return this.addWalker(this.getNpcName(param1), param2, _loc_3, NPC);
        }//end

        public NPC  createWalkerAtPosition (Vector3 param1 ,boolean param2 )
        {
            return this.addWalker(this.getNpcName(null), param2, param1, NPC);
        }//end

        public NPC  createWalkerByNameAtPosition (String param1 ,Vector3 param2 ,boolean param3 )
        {
            return this.addWalker(param1, param3, param2, NPC);
        }//end

        public NPC  createWalkerByClass (Class param1 ,String param2 ,Vector3 param3 ,boolean param4 )
        {
            return this.addWalker(param2, param4, param3, param1);
        }//end

        public NPC  createMayorAtPosition (Vector3 param1 )
        {
            return this.addWalker("NPC_mayor", false, param1, NPC);
        }//end

        public Vehicle  createVehicleByName (Road param1 ,String param2 ,boolean param3 )
        {
            return this.addVehicleByName(param2, param1, param3);
        }//end

        public SeaVehicle  createShipByNameAtPosition (String param1 ,Vector3 param2 ,Array param3 ,boolean param4 ,double param5 =-1)
        {
            return this.addShipByName(param1, param2, param3, param4, param5);
        }//end

        public Cloud  createCloudByNameAtPosition (String param1 ,Vector3 param2 ,Array param3 ,boolean param4 )
        {
            return this.addCloudByName(param1, param2, param3, param4);
        }//end

        public void  addVehicle (Vehicle param1 )
        {
            param1.setOuter(this.m_world);
            param1.attach();
            this.m_cars.push(param1);
            return;
        }//end

        public void  removeWalker (NPC param1 )
        {
            this.removeTrackedNpc(param1, this.m_walkers);
            return;
        }//end

        public void  removeVehicle (Vehicle param1 )
        {
            this.removeTrackedNpc(param1, this.m_cars);
            return;
        }//end

        public void  removeShip (SeaVehicle param1 )
        {
            this.removeTrackedNpc(param1, this.m_ships);
            return;
        }//end

        public void  removeCloud (Cloud param1 )
        {
            this.removeTrackedNpc(param1, this.m_clouds);
            return;
        }//end

        private void  removeTrackedNpc (NPC param1 ,Array param2 )
        {
            if (param2.indexOf(param1) == -1)
            {
                return;
            }
            ArrayUtil.removeValueFromArray(param2, param1);
            if (param1 instanceof Vehicle)
            {
                if (this.m_businesscars.indexOf(param1) != -1)
                {
                    ArrayUtil.removeValueFromArray(this.m_businesscars, param1);
                }
            }
            param1.detach();
            param1.cleanUp();
            return;
        }//end

        public void  removeNpc (NPC param1 )
        {
            if (param1 instanceof SeaVehicle)
            {
                this.removeShip((SeaVehicle)param1);
            }
            else if (param1 instanceof Vehicle)
            {
                this.removeVehicle((Vehicle)param1);
            }
            else
            {
                this.removeWalker(param1);
            }
            return;
        }//end

        public boolean  isNpcTracked (NPC param1 )
        {
            if (param1 instanceof SeaVehicle)
            {
                return this.m_ships.indexOf(param1) != -1;
            }
            if (param1 instanceof Vehicle)
            {
                return this.m_cars.indexOf(param1) != -1;
            }
            return this.m_walkers.indexOf(param1) != -1;
        }//end

        public Array  getFreeAgentWalkers (MapResource param1 )
        {
            NPC _loc_4 =null ;
            ActionNavigate _loc_5 =null ;
            _loc_2 = this.m_world.citySim.roadManager.getConnectedRoadGraph(param1 );
            if (_loc_2 == null)
            {
                return new Array();
            }
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < this.m_walkers.size(); i0++)
            {
            	_loc_4 = this.m_walkers.get(i0);

                if (_loc_4.isFreeAgent)
                {
                    _loc_5 =(ActionNavigate) _loc_4.getStateMachine().getState();
                    if (_loc_5 != null && _loc_5.graph == _loc_2)
                    {
                        _loc_3.push(_loc_4);
                    }
                }
            }
            return _loc_3;
        }//end

        public Array  createMoverPeeps (Residence param1 ,int param2 )
        {
            Debug.debug5("NPCManager.createMoverPeeps");

            Peep peep ;
            boolean empty ;
            house = param1;
            count = param2;
            Array results =new Array ();
            int i ;
            peep;
            i = 0;
            while (i < count)
            {

                empty = house.takeResident();
                peep = this.createPeepWalker(house, false);
                peep.setHome(house);
                peep.getStateMachine().removeAllStates();
                peep .getStateMachine ().addActions (new ActionNavigateBeeline (peep ,house .getHotspot (),null ),new ActionPause (peep ,1.5),new ActionFn (peep ,void  ()
            {
                peep.animation = "static";
                return;
            }//end
            ), new ActionEnableFreedom(peep, true, true));
                results.push(peep);
                i = (i + 1);
            }
            return results;
        }//end

        public Array  createBusinessPeepsWaiting (IMerchant param1 ,int param2 )
        {

            Debug.debug5("NPCManager.createBusinessPeepsWaiting");

            Array _loc_3 =new Array();
            int _loc_4 =0;
            Peep _loc_5 =null ;
            _loc_6 = param1.getMapResource ();
            if (Global.isVisiting())
            {
                _loc_4 = 0;
                while (_loc_4 < param2)
                {

                    _loc_5 = this.createPeepWalker(param1, true);
                    _loc_5.spawnSource = Peep.SOURCE_TOURBUS;
                    _loc_5.getStateMachine().removeAllStates();
                    _loc_5.getStateMachine().addActions(new ActionNavigate(_loc_5, _loc_6, null).setPathType(RoadManager.PATH_TO_FRONT_ENTRANCE), new ActionBusinessPacing(_loc_5, _loc_6));
                    _loc_3.push(_loc_5);
                    _loc_4++;
                }
            }
            else
            {
                _loc_4 = 0;
                while (_loc_4 < param2)
                {

                    _loc_5 = this.createPeepWalker(param1, true);
                    _loc_5.spawnSource = Peep.SOURCE_TOURBUS;
                    _loc_5.getStateMachine().removeAllStates();
                    _loc_5.getStateMachine().addActions(new ActionNavigate(_loc_5, _loc_6, null).setPathType(RoadManager.PATH_TO_FRONT_ENTRANCE), new ActionPause(_loc_5, 3), new ActionMerchantEnter(_loc_5, param1));
                    _loc_3.push(_loc_5);
                    _loc_4++;
                }
            }
            return _loc_3;
        }//end

        public Array  createFranchiseFreebiePeeps (Business param1 ,int param2 )
        {
            Debug.debug5("NPCManager.createFranchiseFreebiePeeps");

            Array _loc_3 =new Array ();
            Peep _loc_4 =null ;
            int _loc_5 =0;
            while (_loc_5 < param2)
            {

                _loc_4 = this.createPeepWalker(param1, true);
                _loc_4.isFranchiseFreebie = true;
                _loc_4.getStateMachine().removeAllStates();
                _loc_4.getStateMachine().addActions(new ActionNavigate(_loc_4, param1, null).setPathType(RoadManager.PATH_TO_FRONT_ENTRANCE), new ActionBusinessPacing(_loc_4, param1));
                _loc_3.push(_loc_4);
                _loc_5++;
            }
            return _loc_3;
        }//end

        public Array  getAllBusinessCars ()
        {
            return ArrayUtil.copyArray(this.m_businesscars);
        }//end

        protected Residence  getRandomWalkerStartingPoint ()
        {
            Array _loc_2 =null ;
            Residence _loc_3 =null ;
            if (this.m_debugPathing && Global.world.citySim.roadManager.isShowingOverlay)
            {
                _loc_2 = this.m_world.getObjectsByClass(Residence);
                _loc_3 = _loc_2.length > 1 ? (_loc_2.get(1)) : (null);
                return _loc_3;
            }
            _loc_1 = MathUtil.randomElement(this.m_world.getObjectsByClass(Residence));
            return _loc_1;
        }//end

        public MapResource  getRandomWalkerOrigin (MapResource param1 ,int param2)
        {
            _loc_3 = this.m_world.citySim.roadManager.getConnectedRoadGraph(param1 );
            if (_loc_3 == null)
            {
                return param1;
            }
            _loc_4 = _loc_3.findRandomResource(Residence ,param1 ,param2 );
            if (_loc_3.findRandomResource(Residence, param1, param2) != null)
            {
                return _loc_4;
            }
            _loc_5 = _loc_3.findRandomResource(null ,param1 ,param2 );
            return _loc_3.findRandomResource(null, param1, param2) != null ? (_loc_5) : (param1);
        }//end

        public MapResource  getRandomWalkerTarget (MapResource param1 ,int param2)
        {
            _loc_3 = this.m_world.citySim.roadManager.getConnectedRoadGraph(param1 );
            if (_loc_3 == null)
            {
                return param1;
            }
            _loc_4 = _loc_3.findRandomResource(null ,param1 ,param2 );
            return _loc_3.findRandomResource(null, param1, param2) != null ? (_loc_4) : (param1);
        }//end

        public NPC  addWalker (String param1 ,boolean param2 ,Vector3 param3 ,Class param4 )
        {
            Debug.debug5("NPCManager.addWalker");

            _loc_5 = new param4(param1 ,param2 );
            if (param3 != null)
            {
                _loc_5.setPosition(param3.x, param3.y);
            }
            _loc_5.setOuter(this.m_world);
            _loc_5.attach();
            if (_loc_5 instanceof Vehicle)
            {
                this.m_cars.push(_loc_5);
                this.m_businesscars.push(_loc_5);
                StatsManager.sample(100, "npcs", "cars", "spawn", _loc_5.getItem().name);
            }
            else
            {
                this.m_walkers.push(_loc_5);
            }
            return _loc_5;
        }//end

        private Vehicle  addVehicleByName (String param1 ,Road param2 ,boolean param3 )
        {
            Debug.debug5("NPCManager.addVehicleByName");

            Road _loc_4 =null ;
            Vehicle _loc_5 =null ;
            if (!param3)
            {
                _loc_4 = Global.world.citySim.roadManager.findRandomRoadNearEdges(param2);
                if (param2 == null || _loc_4 == null || param2.isBeingMoved() || _loc_4.isBeingMoved() || param2 == _loc_4)
                {
                    return null;
                }
                _loc_5 = new Vehicle(param1, false);
                _loc_5.setPosition(param2.getHotspot().x, param2.getHotspot().y);
                _loc_5.getStateMachine().addActions(new ActionNavigate(_loc_5, _loc_4, param2), new ActionDie(_loc_5));
            }
            else
            {
                _loc_5.getStateMachine().addState(new ActionNavigateRandom(_loc_5));
            }
            _loc_5.setOuter(this.m_world);
            _loc_5.attach();
            this.m_cars.push(_loc_5);
            return _loc_5;
        }//end

        private SeaVehicle  addShipByName (String param1 ,Vector3 param2 ,Array param3 ,boolean param4 ,double param5 =-1)
        {
            Debug.debug5("NPCManager.addShipByName");

            SeaVehicle _loc_6 =null ;
            Vector3 _loc_7 =null ;
            Vector3 _loc_8 =null ;
            if (!param4)
            {
                _loc_6 = new SeaVehicle(param1, false, param5);
                _loc_6.setPosition(param2.x, param2.y);
                _loc_7 = param2;
                for(int i0 = 0; i0 < param3.size(); i0++)
                {
                	_loc_8 = param3.get(i0);

                    _loc_6.getStateMachine().addActions(new ActionNavigateBeeline(_loc_6, _loc_8, _loc_7));
                    _loc_7 = _loc_8;
                }
                _loc_6.getStateMachine().addActions(new ActionDie(_loc_6));
            }
            else
            {
                _loc_6.getStateMachine().addState(new ActionNavigateRandom(_loc_6));
            }
            _loc_6.setOuter(this.m_world);
            _loc_6.attach();
            this.m_ships.push(_loc_6);
            return _loc_6;
        }//end

        public Vehicle  addFlyerByName (Vehicle param1 ,Vector3 param2 ,Array param3 )
        {
            param1.setPosition(param2.x, param2.y);
            param1.getStateMachine().addActionsArray(param3);
            param1.setOuter(this.m_world);
            param1.attach();
            this.m_clouds.push(param1);
            return param1;
        }//end

        private Cloud  addCloudByName (String param1 ,Vector3 param2 ,Array param3 ,boolean param4 )
        {
            Cloud _loc_5 =null ;
            Vector3 _loc_6 =null ;
            Vector3 _loc_7 =null ;
            if (!param4)
            {
                _loc_5 = new Cloud(param1, false);
                _loc_5.setPosition(param2.x, param2.y);
                _loc_6 = param2;
                for(int i0 = 0; i0 < param3.size(); i0++)
                {
                	_loc_7 = param3.get(i0);

                    _loc_5.getStateMachine().addActions(new ActionNavigateBeeline(_loc_5, _loc_7, _loc_6));
                    _loc_6 = _loc_7;
                }
                _loc_5.getStateMachine().addActions(new ActionDie(_loc_5));
            }
            else
            {
                _loc_5.getStateMachine().addState(new ActionNavigateRandom(_loc_5));
            }
            _loc_5.setOuter(this.m_world);
            _loc_5.attach();
            this.m_clouds.push(_loc_5);
            return _loc_5;
        }//end

        private void  onTimer (TimerEvent event )
        {
            if (this.m_debugPathing)
            {
                Global.world.citySim.roadManager.showOverlay(true);
            }
            return;
        }//end

        public Array  createDesireWalker (MapResource param1 ,Array param2 ,int param3 =1,boolean param4 =true ,boolean param5 =false )
        {

            Debug.debug5("NPCManager.createDesireWalker");

            DesirePeep _loc_8 =null ;
            Array _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            Class _loc_12 =null ;
            Array _loc_6 =new Array();
            int _loc_7 =0;
            while (_loc_7 < param3)
            {

                _loc_8 =(DesirePeep) this.addWalker(this.getNpcName(param1), param4, param1.getHotspot(), DesirePeep);
                _loc_9 = new Array();
                for(int i0 = 0; i0 < param2.size(); i0++)
                {
                		_loc_10 = param2.get(i0);

                    _loc_11 = "Classes.Desires." + _loc_10;
                    _loc_12 =(Class) getDefinitionByName(_loc_11);
                    _loc_9.push(new _loc_12(_loc_8));
                }
                _loc_8.setDesires(_loc_9);
                _loc_6.push(_loc_8);
                if (!this.m_experimentNPCThinning)
                {
                    if (!param5 && this.m_walkers.length > this.m_maxVisibleWalkers)
                    {
                        if (this.getVisibleWalkerCount() > this.m_maxVisibleWalkers)
                        {
                            _loc_8.alwaysCulled = true;
                        }
                    }
                }
                _loc_7++;
            }
            return _loc_6;
        }//end

        public DesirePeep  createDesireWalkerAtPosition (Vector3 param1 ,Array param2 ,String param3 =null ,boolean param4 =true ,boolean param5 =false )
        {
            String _loc_9 =null ;
            String _loc_10 =null ;
            Class _loc_11 =null ;
            _loc_6 = param3!= null ? (param3) : (this.getNpcName(null));
            _loc_7 = this.addWalker(_loc_6 ,param4 ,param1 ,DesirePeep )as DesirePeep ;
            Array _loc_8 =new Array();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_9 = param2.get(i0);

                _loc_10 = "Classes.Desires." + _loc_9;
                _loc_11 =(Class) getDefinitionByName(_loc_10);
                _loc_8.push(new _loc_11(_loc_7));
            }
            _loc_7.setDesires(_loc_8);
            if (!this.m_experimentNPCThinning)
            {
                if (!param5 && this.m_walkers.length > this.m_maxVisibleWalkers)
                {
                    if (this.getVisibleWalkerCount() > this.m_maxVisibleWalkers)
                    {
                        _loc_7.alwaysCulled = true;
                    }
                }
            }
            return _loc_7;
        }//end

        public Array  createDesireWalkerFromHouses (Array param1 ,int param2 =1,boolean param3 =false ,boolean param4 =true )
        {

            return new Array();

            int idx ;
            Residence house ;
            boolean empty ;
            DesirePeep walker ;
            String carToSpawn ;
            int walkerValue ;
            Array desires =new Array ();
            String desireType ;
            Road startRoad ;
            String className ;
            Class desireClass ;
            int maxActiveCars ;
            desireTypes = param1;
            count = param2;
            applyBusinessSpawnLimit = param3;
            allowVehicles = param4;
            Array results =new Array ();



            //if(count>1) return results;
            count = 1;

            if (this.m_housesOpen == null || this.m_housesOpen.length == 0)
            {

                return results;
            }
            int i ;
            while (i < count)
            {

                if (this.m_housesOpen.length > 0)
                {
                    idx = MathUtil.randomIndex(this.m_housesOpen);
                    house = this.m_housesOpen.get(idx);
                    empty = house.takeResident();
                    carToSpawn = CarManager.instance.checkCarSpawn();
                    walkerValue = 0;
                    if (allowVehicles && carToSpawn != "none")
                    {
                        startRoad = Global.world.citySim.roadManager.findClosestRoad(house.getHotspot());
                        walker =(DesirePeep) this.addWalker(carToSpawn, true, startRoad.getHotspot(), Vehicle);
                        ((Vehicle)walker).preloadImages(16);
                        walker.forceUpdateArrowWithCustomIcon("coin1supersmall");
                        walker .playActionCallback =Curry .curry (void  (NPC param11 )
            {
                param11.hideStagePickEffect();
                param11.playActionCallback = null;
                CarManager.instance.processCarHarvest(param11);
                return;
            }//end
            , walker);
                    }
                    else
                    {
                        walker =(DesirePeep) this.addWalker(this.getNpcName(house), true, house.getHotspot(), DesirePeep);
                    }
                    if (applyBusinessSpawnLimit)
                    {
                        walkerValue = walker.getItem().npcValue;
                        this.m_numberOfBusinessPeepsToSpawn = this.m_numberOfBusinessPeepsToSpawn - walkerValue;
                        if (this.m_numberOfBusinessPeepsToSpawn < 0)
                        {
                            this.m_numberOfBusinessPeepsToSpawn = 0;
                        }
                    }
                    walker.setHome(house);
                    //desires;
                    int _loc_6 =0;
                    _loc_7 = desireTypes;
                    for(int i0 = 0; i0 < desireTypes.size(); i0++)
                    {
                    	desireType = desireTypes.get(i0);


                        className = "Classes.Desires." + desireType;
                        desireClass =(Class) getDefinitionByName(className);
                        desires.push(new desireClass(walker));
                    }
                    walker.setDesires(desires);
                    results.push(walker);
                    if (!this.m_experimentNPCThinning)
                    {
                        maxActiveCars = RuntimeVariableManager.getInt("MAXIMUM_ACTIVE_CARS", 10);
                        if (this.m_walkers.length > this.m_maxVisibleWalkers - maxActiveCars)
                        {
                            if (this.getVisibleWalkerCount() > this.m_maxVisibleWalkers && !(walker instanceof Vehicle))
                            {
                                walker.alwaysCulled = true;
                            }
                        }
                    }
                    if (empty)
                    {
                        this.m_housesOpen.splice(idx, 1);
                        this.m_housesClosed.push(house);
                    }
                }
                i = (i + 1);
            }
            return results;
        }//end

        public Array  createBusinessDesireWalker (int param1 ,boolean param2 )
        {
            DesirePeep _loc_5 =null ;
            int _loc_6 =0;
            //Array _loc_3 =.get(DesireTypes.VISIT_BUSINESS ,DesireTypes.GO_HOME) ;
            Array _loc_3 =.get(DesireTypes.VISIT_ATTRACTION) ;
            _loc_4 = this.createDesireWalkerFromHouses(_loc_3 ,param1 ,true ,param2 );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                _loc_6 = Math.max(2, Math.ceil(Math.random() * 4));
                _loc_5.setResistThresholdByType(DesireTypes.GO_HOME, _loc_6);
            }
            return _loc_4;
        }//end

        public Array  createAttractionDesireWalker (int param1 )
        {
            Array _loc_2 =.get(DesireTypes.VISIT_ATTRACTION ,DesireTypes.VISIT_BUSINESS ,DesireTypes.GO_HOME) ;
            _loc_3 = this.createDesireWalkerFromHouses(_loc_2 ,param1 ,false ,true );
            return _loc_3;
        }//end

        public Array  createVacationersFromSource (MapResource param1 ,int param2 )
        {
            DesirePeep peep ;
            int threshold ;
            source = param1;
            count = param2;
            Array desires ;
            results = this.createDesireWalker(source,desires,count,true,true);
            int _loc_4 =0;
            _loc_5 = results;
            for(int i0 = 0; i0 < results.size(); i0++)
            {
            	peep = results.get(i0);


                peep.vacationer = true;
                peep.spawnSource = Peep.SOURCE_HOTEL;
                threshold = Math.max(2, Math.ceil(Math.random() * 4));
                peep.setResistThresholdByType(DesireTypes.GO_HOME, threshold);
                peep .getStateMachine ().addActions (new ActionFn (peep ,void  ()
            {
                _loc_1 =Global.getAssetURL("assets/citysim/looking_to_spend_npc_bubble.png");
                peep.queueFeedbackBubble(_loc_1, 8);
                return;
            }//end
            ));
                Global.world.citySim.resortManager.checkNPCConsumerTutorial(peep);
            }
            return results;
        }//end

        public void  startSpawningVacationersFromResource (MapResource param1 ,int param2 =1)
        {
            this.m_hotelSpawn.put(param1.getId(),  param2);
            return;
        }//end

        public void  stopSpawningVacationersFromResource (MapResource param1 )
        {
            delete this.m_hotelSpawn.get(param1.getId());
            return;
        }//end

	public Array  getAllWalkers ()
	{
	    return ArrayUtil.copyArray(this.m_walkers);
	}//end


    }



