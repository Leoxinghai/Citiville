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
import Classes.announcers.*;
import Display.*;
import Engine.Helpers.*;
import Modules.bandits.*;
import Modules.friendUGC.*;
import Modules.ships.cruise.*;
import Modules.stats.data.*;

import com.adobe.utils.*;
import Modules.stats.*;

    public class CitySim implements IGameWorldStateObserver, IStatsTarget
    {
        protected int m_lowestYield =-1;
        protected NotificationStateObserver m_notificationStateObserver ;
        protected NPCManager m_npcMgr ;
        protected RoadManager m_roadMgr ;
        protected FriendVisitManager m_friendVisitMgr ;
        protected WaterManager m_waterMgr ;
        protected TrainManager m_trainMgr ;
        protected LotManager m_lotMgr ;
        protected PickupManager m_pickupMgr ;
        protected NeighborNavigationManager m_nghbrNavNgr ;
        protected MiniQuestManager m_miniQuestManager ;
        protected NPCVisitBatchManager m_NPCBatchManager ;
        protected PreyManager m_preyManager ;
        protected CruiseShipManager m_cruiseShipManager ;
        protected ResortManager m_resortManager ;
        protected FriendNPCManager m_friendNPCManager ;
        protected POIManager m_poiMgr ;
        protected AnnouncerManager m_announcerManager ;
        protected TourBusManager m_tourBusManager ;
        protected UnwitherManager m_unwitherManager ;
        protected Population m_population =null ;
        protected PopulationCap m_populationCap =null ;
        protected Population m_populationPotential =null ;
        protected Array m_stateObservers ;
        public static  int HAPPINESS_GOOD =0;
        public static  int HAPPINESS_NEUTRAL =1;
        public static  int HAPPINESS_BAD =2;

        public  CitySim (GameWorld param1 )
        {
            this.m_notificationStateObserver = new NotificationStateObserver(param1);
            this.m_roadMgr = new RoadManager(param1);
            this.m_waterMgr = new WaterManager(param1);
            this.m_lotMgr = new LotManager(param1);
            this.m_npcMgr = new NPCManager(param1);
            this.m_friendNPCManager = new FriendNPCManager(param1);
            this.m_pickupMgr = new PickupManager(param1);
            this.m_nghbrNavNgr = new NeighborNavigationManager(param1);
            this.m_poiMgr = new POIManager();
            this.m_friendVisitMgr = new FriendVisitManager(param1);
            this.m_stateObservers = new Array();
            this.m_miniQuestManager = new MiniQuestManager(param1);
            this.m_NPCBatchManager = new NPCVisitBatchManager(param1);
            this.m_preyManager = new PreyManager(param1);
            this.m_cruiseShipManager = new CruiseShipManager(param1);
            this.m_announcerManager = new AnnouncerManager(param1);
            this.m_resortManager = new ResortManager(param1);
            this.m_tourBusManager = new TourBusManager();
            this.m_unwitherManager = new UnwitherManager(param1);
            this.m_population = new Population(0, Population.MIXED);
            this.m_populationCap = new PopulationCap(0, Population.MIXED);
            this.m_populationPotential = new Population(0, Population.MIXED);
            param1.addObserver(this);
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            IPopulationStateObserver _loc_2 =null ;
            this.m_population.loadObject(param1.population);
            this.m_populationCap.loadObject(param1.populationCap);
            this.m_populationPotential.loadObject(param1.populationPotential);
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            		_loc_2 = this.m_stateObservers.get(i0);

                _loc_2.onPopulationInit(this.getPopulation(), this.getScaledPopulation(), this.getTotalPopulation());
                _loc_2.onPopulationChanged(this.getPopulation(), this.getPopulation(), this.getScaledPopulation(), this.getTotalPopulation());
                _loc_2.onPotentialPopulationChanged(this.getPotentialPopulation(), this.getTotalPopulation());
                _loc_2.onPopulationCapChanged(this.getPopulationCap());
            }
            return;
        }//end

        public StatsCountData Vector  getStatsCounterObject ().<>
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            Vector<StatsCountData> _loc_1=new Vector<StatsCountData>();
            _loc_1.push(new StatsCountData(new StatsOntology("population"), this.getScaledPopulation()));
            _loc_1.push(new StatsCountData(new StatsOntology("population_max"), this.getScaledPopulationCap()));
            _loc_2 =Global.gameSettings().getPopulations ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.get("id");
                if (_loc_4 != null && _loc_4 != "")
                {
                    _loc_1.push(new StatsCountData(new StatsOntology(_loc_4 + "_population"), this.getScaledPopulation(_loc_4)));
                    _loc_1.push(new StatsCountData(new StatsOntology(_loc_4 + "_population_max"), this.getScaledPopulationCap(_loc_4)));
                }
            }
            return _loc_1;
        }//end

        public void  initialize ()
        {
            if (this.m_trainMgr)
            {
                this.m_trainMgr.stopObserving();
            }
            this.m_trainMgr = new TrainManager(Global.world);
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

        public NPCManager  npcManager ()
        {
            return this.m_npcMgr;
        }//end

        public RoadManager  roadManager ()
        {
            return this.m_roadMgr;
        }//end

        public FriendVisitManager  friendVisitManager ()
        {
            return this.m_friendVisitMgr;
        }//end

        public WaterManager  waterManager ()
        {
            return this.m_waterMgr;
        }//end

        public TrainManager  trainManager ()
        {
            return this.m_trainMgr;
        }//end

        public LotManager  lotManager ()
        {
            return this.m_lotMgr;
        }//end

        public PickupManager  pickupManager ()
        {
            return this.m_pickupMgr;
        }//end

        public NeighborNavigationManager  neighborNavigationManager ()
        {
            return this.m_nghbrNavNgr;
        }//end

        public POIManager  poiManager ()
        {
            return this.m_poiMgr;
        }//end

        public MiniQuestManager  miniQuestManager ()
        {
            return this.m_miniQuestManager;
        }//end

        public NPCVisitBatchManager  businessVisitBatchManager ()
        {
            return this.m_NPCBatchManager;
        }//end

        public PreyManager  preyManager ()
        {
            return this.m_preyManager;
        }//end

        public CruiseShipManager  cruiseShipManager ()
        {
            return this.m_cruiseShipManager;
        }//end

        public AnnouncerManager  announcerManager ()
        {
            return this.m_announcerManager;
        }//end

        public TourBusManager  tourBusManager ()
        {
            return this.m_tourBusManager;
        }//end

        public UnwitherManager  unwitherManager ()
        {
            return this.m_unwitherManager;
        }//end

        public ResortManager  resortManager ()
        {
            return this.m_resortManager;
        }//end

        public FriendNPCManager  friendNPCManager ()
        {
            return this.m_friendNPCManager;
        }//end

        public void  addObserver (IPopulationStateObserver param1 )
        {
            if (!ArrayUtil.arrayContainsValue(this.m_stateObservers, param1))
            {
                this.m_stateObservers.push(param1);
            }
            return;
        }//end

        public void  removeObserver (IPopulationStateObserver param1 )
        {
            ArrayUtil.removeValueFromArray(this.m_stateObservers, param1);
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            this.m_npcMgr.updateBuildingCaches();
            if (Global.franchiseManager.placementMode == false)
            {
                Global.player.commodities.updateCapacities();
            }
            return;
        }//end

        public void  toggleDebugRoadOverlay ()
        {
            this.m_roadMgr.showOverlay(!this.m_roadMgr.isShowingOverlay);
            return;
        }//end

        private int  numberObjects (Class param1 )
        {
            _loc_4 = undefined;
            int _loc_2 =0;
            _loc_3 = Global.world.getObjects();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4 instanceof param1)
                {
                    _loc_2++;
                }
            }
            return _loc_2;
        }//end

        public int  getPopulation (String param1)
        {
            _loc_2 = this.m_population.getYield(param1 );
            return _loc_2;
        }//end

        public int  getPopulationCap (String param1)
        {
            _loc_2 = this.m_populationCap.getCapYield(param1);
            _loc_3 = Global.gameSettings().getPopulationCapYieldBase(param1,1);
            _loc_4 = _loc_2+_loc_3 ;
            return _loc_2 + _loc_3;
        }//end

        public int  getScaledPopulation (String param1)
        {
            _loc_2 = Global.gameSettings().getNumber("populationScale",1);
            _loc_3 = this.m_population.getYield(param1);
            return _loc_3 * _loc_2;
        }//end

        public int  getScaledPopulationCap (String param1)
        {
            _loc_2 = Global.gameSettings().getNumber("populationScale",1);
            _loc_3 = this.getPopulationCap(param1);
            return _loc_3 * _loc_2;
        }//end

        public double  applyPopulationScale (double param1 )
        {
            return param1 * Global.gameSettings().getNumber("populationScale", 1);
        }//end

        public int  getRequiredPopulationCap (Item param1 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_2 =0;
            switch(param1.type)
            {
                case "business":
                {
                    _loc_3 = this.getTotalBusinesses() + 1;
                    _loc_4 = Global.gameSettings().getNumber("businessLimitByPopulationMax");
                    _loc_2 = this.applyPopulationScale(Math.ceil(_loc_3 / _loc_4));
                    break;
                }
                default:
                {
                    _loc_5 = param1.populationBase;
                    _loc_6 = this.getTotalPopulation(param1.populationType);
                    _loc_2 = this.applyPopulationScale(_loc_6 + _loc_5);
                    break;
                }
            }
            return _loc_2;
        }//end

        public int  getRequiredNonTotalPopulationCap (Item param1 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_2 =0;
            switch(param1.type)
            {
                case "business":
                {
                    _loc_2 = this.getRequiredPopulationCap(param1);
                    break;
                }
                default:
                {
                    _loc_3 = param1.populationBase;
                    _loc_4 = this.getPopulation(param1.populationType);
                    _loc_2 = this.applyPopulationScale(_loc_4 + _loc_3);
                    break;
                }
            }
            return _loc_2;
        }//end

        public int  getPotentialPopulation (String param1)
        {
            _loc_2 = this.m_populationPotential.getYield(param1 );
            return _loc_2;
        }//end

        public int  getTotalPopulation (String param1)
        {
            _loc_2 = this.m_population.getYield(param1);
            _loc_3 = this.m_populationPotential.getYield(param1);
            return _loc_2 + _loc_3;
        }//end

        public int  getTotalBusinesses ()
        {
            ConstructionSite _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_1 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.BUSINESS));
            _loc_2 = _loc_1.length;
            _loc_3 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.CONSTRUCTION_SITE));
            int _loc_4 =0;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_5 = _loc_3.get(i0);

                if (_loc_5.targetClass == Business)
                {
                    _loc_4++;
                }
            }
            _loc_6 = 0;
            _loc_7 = _loc_2 + _loc_4 + _loc_6;
            return _loc_7;
        }//end

        public void  recomputePopulationCap (GameWorld param1 )
        {
            IPeepCapacity _loc_5 =null ;
            int _loc_6 =0;
            IPopulationStateObserver _loc_7 =null ;
            PopulationCap _loc_2 =null ;
            _loc_3 = param1.getObjectsByClass(IPeepCapacity);
            _loc_4 = this.m_populationCap.getCapYield ();
            this.m_populationCap = new PopulationCap(0, Population.MIXED);
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                _loc_2 = _loc_5.getPopulationCap();
                this.m_populationCap.merge(_loc_2);
            }
            _loc_6 = this.m_populationCap.getCapYield();
            if (_loc_6 != _loc_4)
            {
                for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
                {
                		_loc_7 = this.m_stateObservers.get(i0);

                    _loc_7.onPopulationCapChanged(this.getPopulationCap());
                }
            }
            if (UI.m_catalog)
            {
                UI.m_catalog.updateChangedCells();
            }
            return;
        }//end

        public void  recomputePopulation (GameWorld param1 ,boolean param2 =true )
        {
            IPeepHome _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            IPopulationStateObserver _loc_9 =null ;
            Population _loc_3 =null ;
            _loc_4 = param1.getObjectsByClass(IPeepHome);
            _loc_5 = this.m_population.getYield();
            this.m_population = new Population(0, Population.MIXED);
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_6 = _loc_4.get(i0);

                _loc_3 = _loc_6.getPopulation();
                this.m_population.merge(_loc_3);
            }
            _loc_7 = this.m_population.getYield();
            _loc_8 = _loc_7 - _loc_5;
            if (param2)
            {
                for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
                {
                	_loc_9 = this.m_stateObservers.get(i0);

                    _loc_9.onPopulationChanged(this.getPopulation(), _loc_8, this.getScaledPopulation(), this.getTotalPopulation());
                }
            }
            return;
        }//end

        public void  recomputePotentialPopulation (GameWorld param1 )
        {
            IPeepPotential _loc_5 =null ;
            int _loc_6 =0;
            IPopulationStateObserver _loc_7 =null ;
            Population _loc_2 =null ;
            _loc_3 = param1.getObjectsByClass(IPeepPotential);
            _loc_4 = this.m_populationPotential.getYield();
            this.m_populationPotential = new Population(0, Population.MIXED);
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                _loc_2 = _loc_5.getPopulation();
                this.m_populationPotential.merge(_loc_2);
            }
            _loc_6 = this.m_populationPotential.getYield();
            if (_loc_6 != _loc_4)
            {
                for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
                {
                		_loc_7 = this.m_stateObservers.get(i0);

                    _loc_7.onPotentialPopulationChanged(this.getPotentialPopulation(), this.getTotalPopulation());
                }
            }
            return;
        }//end

        public int  getHappinessState (String param1)
        {
            _loc_2 = Global.gameSettings().getNumber("happyPopulationCutoff",0.65);
            _loc_3 = Global.gameSettings().getNumber("sadPopulationCutoff",0.85);
            _loc_4 = this.getPopulation(param1)/this.getPopulationCap(param1);
            if (this.getPopulation(param1) / this.getPopulationCap(param1) < _loc_2)
            {
                return HAPPINESS_GOOD;
            }
            if (_loc_4 < _loc_3)
            {
                return HAPPINESS_NEUTRAL;
            }
            return HAPPINESS_BAD;
        }//end

        protected void  findLowestPopulationYield ()
        {
            Item _loc_3 =null ;
            _loc_1 = int.MAX_VALUE;
            _loc_2 = Global.gameSettings().getItemsByType("residence");
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3.populationBase < _loc_1)
                {
                    _loc_1 = _loc_3.populationBase;
                }
            }
            if (_loc_1 < int.MAX_VALUE)
            {
                this.m_lowestYield = _loc_1;
            }
            return;
        }//end

    }




