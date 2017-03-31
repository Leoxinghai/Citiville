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

import com.xiyu.logic.Sprite;
import com.xiyu.logic.TextField;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Classes.doobers.*;
import Classes.effects.*;
import Classes.orders.*;
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.CollectionsUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Engine.Init.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.friendUGC.*;
import Modules.quest.Managers.*;
import Modules.stats.data.*;
import Modules.stats.experiments.*;
import Modules.stats.trackers.*;
import Transactions.*;

import com.adobe.utils.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import Modules.stats.*;
import com.xinghai.Debug;
import com.xiyu.util.Event;
import com.xiyu.util.Rectangle;
import root.*;
import java.util.Vector;


public class GameWorld extends World implements IStatsTarget
    {
        public  int GIFTBOX_ID =-1;
        public  int INVENTORY_ID =-2;
        private int m_gameInitStatus =0;
        protected String m_ownerId =null ;
        public double lotOutlineColor =3829545;
        protected Array m_activeModes ;
        protected Sprite m_lotSprite =null ;
        protected int m_modeLock =0;
        protected Array m_neighborText ;
        protected String m_pendingVisitUserId ;
        private int m_currentDefconLevel =0;
        private int m_maxDefcon =4;
        private int m_frameCount ;
        private int m_giftRange ;
        public Object mostFrequentHelpers ;
        public String vistorCenterText ;
        private boolean m_hasSeenGenericAd =false ;
        private boolean m_isAddNeighborShown ;
        private int m_lastAddNeighborTime ;
        private int m_lastGenericAdTime ;
        private int m_lastGiftMissionTime ;
        private int m_lastIdleMissionTime ;
        private Array m_ownedCells ;
        private TerritoryMap m_territory ;
        protected WildernessSim m_wildernessSim ;
        protected  Vector2 UP =new Vector2(0,1);
        protected  Vector2 RIGHT =new Vector2(1,0);
        protected  Vector2 DOWN =new Vector2(0,-1);
        protected  Vector2 LEFT =new Vector2 (-1,0);


        private double m_lastPromoShowTime =-1;
        private double m_lastUpdateTime =0;
        private int m_lastUpdate ;
        private double m_promoDelay =-1;
        private TweenLite m_scrollTween ;
        private TweenLite m_zoomTween ;
        private int m_secondsAboveDefconThreshold =0;
        private int m_secondsBelowDefconThreshold =0;
        private boolean m_showingGiftMissionDialog =false ;
        private boolean m_zoomEnabled =true ;
        private boolean m_unlockingMove =false ;
        protected CitySim m_citySim ;
        protected Array m_stateObservers ;
        protected WaterFeature m_river ;
        private Bitmap m_grassBitmap =null ;
        private boolean m_grassBitmapInitialized =false ;
        private Point m_groundmapOrigin ;
        private double m_groundmapScale ;
        private Rectangle m_initialRoadsRect ;
        protected Rectangle m_overlayBackgroundRect ;
        protected Loader m_backgroundLoader ;
        private Sprite m_backgroundSprite ;
        protected DooberManager m_dooberManager ;
        protected OrderManager m_orderManager ;
        protected OrderManager m_visitorOrderManager ;
        protected ViralManager m_viralManager ;
        protected Vector<Theme> m_currentThemes;
        protected Vector<String> m_currentThemeCollections;
        protected boolean m_printPerformanceData =false ;
        private Dictionary m_nameCache =null ;
        private Dictionary m_typeCache =null ;
        private Dictionary m_idCache =null ;
        private Dictionary m_classCache ;
        private int m_invalidQuestElapsed =0;
        protected FriendUGCManager m_friendUGCManager ;
        private Sprite m_alignSprite ;
        private static  int GAME_INIT_INCOMPLETE =0;
        private static  int GAME_INIT_COMPLETE =1;
        public static  int DEFCON_LEVEL1 =0;
        public static  int DEFCON_LEVEL2 =1;
        public static  int DEFCON_LEVEL3 =2;
        public static  int DEFCON_LEVEL4 =3;
        public static  int DEFCON_LEVEL5 =4;
        public static  int DEFCON_NORMAL_HIGHEST_LEVEL =4;
        public static  int DEFCON_LEVEL6 =5;
        public static  int DEFCON_LEVEL7 =6;
        public static  int DEFCON_LEVEL8 =7;
        public static  int DEFCON_LEVEL9 =8;
        public static  int DEFCON_LEVEL10 =9;
        public static  int DEFCON_LOWEST_LEVEL =0;
        public static  int DEFCON_HIGHEST_LEVEL =9;
        private static  int FPS_DEFCON_DOWN_THRESHOLD =25;
        private static  int FPS_DEFCON_UP_THRESHOLD =15;
        private static  double FPS_UPDATE_INTERVAL =1000;
        private static  int PERF_PROBABILITY =10000;
        private static  String OVERRIDE_LAYER_NAME ="default";
        private static  double PERF_UPDATE_INTERVAL =5000;
        private static int SEC_ABOVE_DEFCON_THRESHOLD =15;
        private static int SEC_BELOW_DEFCON_THRESHOLD =10;
        public static  String CITY_SAM_OWNER_ID ="-1";
        public static  int CULL_ZOOM_IN =0;
        public static  int CULL_ZOOM_OUT =1;
        public static  int CULL_CHECK_ALL =2;
        private static  int INVALID_QUEST_UPDATE =2000;
        private static double idleDelayFromSettings =Global.gameSettings().getNumber("idleMissionTime");
        private static double giftDelayFromSettings =Global.gameSettings().getNumber("giftMissionTime");
        private static double addNeighborDelayFromSettings =Global.gameSettings().getNumber("addNeighborTime");
        private static  double RIVER_TILE_SCALE =1.25;


        private static boolean m_npcDepthSorting =true ;

        private static boolean m_enableUpdateSharding =false ;

        private static boolean m_enableUpdateCulling =true ;

        private static boolean m_cacheObjectNamesAndTypes =false ;
        private static boolean m_defconHide =false ;
        private static boolean m_cacheObjectIds =false ;
        private static boolean m_skipEngineObjectUpdates =false ;

        public void  GameWorld ()
        {
            this.m_activeModes = new Array();
            this.m_neighborText = new Array();

            this.m_stateObservers = new Array();
            this.m_classCache = new Dictionary();
            _loc_1 =Global.gameSettings().getInt("farmSize",80);
            super(_loc_1, _loc_1);
            this.m_citySim = new CitySim(this);
            this.m_dooberManager = new DooberManager(this);
            this.m_orderManager = new OrderManager();
            this.m_visitorOrderManager = new OrderManager();
            this.m_viralManager = new ViralManager();
            this.m_currentThemes = new Vector<Theme>();
            this.m_currentThemeCollections = new Vector<String>();
            this.m_friendUGCManager = new FriendUGCManager();
            this.m_lastUpdate = getTimer();
            this.m_frameCount = 0;


            m_cacheObjectNamesAndTypes = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_GET_OBJECTS_BY_NAMES_AND_TYPES);
            m_cacheObjectIds = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_GET_OBJECT_BY_ID);
            m_skipEngineObjectUpdates = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_ONUPDATE_EVENTS);
            m_enableUpdateSharding = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_ONUPDATE_SHARDING);


            m_defconHide = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_DEFCON_HIDE);
            Vector3.optimizeMemoryUse = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMORY_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_VECTOR3_MEMORY);
            MapResourceEffectFactory.loadOptimization = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMORY_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_EFFECT_IMAGE_LOADING);
            ItemImage.disposeSpriteSheets = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMORY_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_DISPOSE_SPRITE_SHEETS);
            this.m_maxDefcon = DEFCON_NORMAL_HIGHEST_LEVEL;
            if (Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_DEFCON_LIMIT))
            {
                this.m_maxDefcon = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DEFCON_LIMIT) - 1;
                if (this.m_maxDefcon < DEFCON_NORMAL_HIGHEST_LEVEL)
                {
                    this.m_maxDefcon = DEFCON_NORMAL_HIGHEST_LEVEL;
                }
                if (this.m_maxDefcon > DEFCON_HIGHEST_LEVEL)
                {
                    this.m_maxDefcon = DEFCON_HIGHEST_LEVEL;
                }
            }
            if (m_enableUpdateSharding || m_enableUpdateCulling)
            {
                SEC_ABOVE_DEFCON_THRESHOLD = 10;
                SEC_BELOW_DEFCON_THRESHOLD = 5;
            }
            return;
        }//end

        public int  gameInitStatus ()
        {
            return this.m_gameInitStatus;
        }//end

        public void  gameInitStatus (int param1 )
        {
            this.m_gameInitStatus = param1;
            return;
        }//end

        public Rectangle  overlayBackgroundRect ()
        {
            return this.m_overlayBackgroundRect;
        }//end

        public WildernessSim  wildernessSim ()
        {
            return this.m_wildernessSim;
        }//end

        public OrderManager  orderMgr ()
        {
            return this.m_orderManager;
        }//end

        public OrderManager  visitorOrderMgr ()
        {
            return this.m_visitorOrderManager;
        }//end

        public ViralManager  viralMgr ()
        {
            return this.m_viralManager;
        }//end

         public int  defCon ()
        {
            return this.m_currentDefconLevel;
        }//end

        public void  addObserver (IGameWorldStateObserver param1 )
        {
            if (!ArrayUtil.arrayContainsValue(this.m_stateObservers, param1))
            {
                this.m_stateObservers.push(param1);
            }
            return;
        }//end

        public void  removeObserver (IGameWorldStateObserver param1 )
        {
            ArrayUtil.removeValueFromArray(this.m_stateObservers, param1);
            return;
        }//end

        public CitySim  citySim ()
        {
            return this.m_citySim;
        }//end

        public DooberManager  dooberManager ()
        {
            return this.m_dooberManager;
        }//end

        public FriendUGCManager  friendUGCManager ()
        {
            return this.m_friendUGCManager;
        }//end

        public WaterFeature  river ()
        {
            return this.m_river;
        }//end

        public boolean  isEditMode ()
        {
            _loc_1 = this.getTopGameMode();
            return _loc_1 != null && _loc_1.supportsEditing;
        }//end

        public boolean  isUnlockingMove ()
        {
            return this.m_unlockingMove;
        }//end

        public void  isUnlockingMove (boolean param1 )
        {
            this.m_unlockingMove = param1;
            return;
        }//end

        public void  enableZoom (boolean param1 )
        {
            this.m_zoomEnabled = param1;
            return;
        }//end

        public boolean  isZoomEnabled ()
        {
            return this.m_zoomEnabled;
        }//end

        public void  printPerformanceData (boolean param1 )
        {
            this.m_printPerformanceData = param1;
            return;
        }//end

        public TerritoryMap  territory ()
        {
            return this.m_territory;
        }//end

        public StatsCountData Vector  getStatsCounterObject ().<>
        {
            Vector<StatsCountData> _loc_1 =new Vector<StatsCountData>();
            _loc_2 = this.getObjectsByTypes(.get(WorldObjectTypes.BUSINESS));
            _loc_3 = this.getObjectsByTypes(.get(WorldObjectTypes.RESIDENCE));
            _loc_4 = this.getObjectsByTypes(.get(WorldObjectTypes.MUNICIPAL));
            _loc_5 = this.getObjectsByTypes(.get(WorldObjectTypes.LANDMARK));
            _loc_6 = this.getObjectsByTypes(.get(WorldObjectTypes.PLOT));
            _loc_7 = this.getObjectsByTypes(.get(WorldObjectTypes.SIDEWALK));
            _loc_8 = this.getObjectsByTypes(.get(WorldObjectTypes.ROAD));
            _loc_9 = this.getObjectsByTypes(.get(WorldObjectTypes.DECORATION));
            _loc_10 = Global.player.expansionsPurchased;
            _loc_11 = this.getObjectsByTypes(.get(WorldObjectTypes.SHIP));
            _loc_1.push(new StatsCountData(new StatsOntology("num_businesses"), _loc_2.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_residences"), _loc_3.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_municipals"), _loc_4.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_plots"), _loc_6.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_sidewalks"), _loc_7.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_roads"), _loc_8.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_decorations"), _loc_9.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_landmarks"), _loc_5.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_ships"), _loc_11.length()));
            _loc_1.push(new StatsCountData(new StatsOntology("num_expansions"), _loc_10));
            return _loc_1;
        }//end

        public void  addGameMode (GameMode param1 ,boolean param2 =true )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            GameMode _loc_5 =null ;
            if (this.isModeLocked() == false)
            {
                _loc_3 = getQualifiedClassName(param1);
                _loc_4 = 0;
                while (_loc_4 < this.m_activeModes.length())
                {

                    _loc_5 =(GameMode) this.m_activeModes.get(_loc_4);
                    _loc_5.disableMode();
                    if (param2)
                    {
                        _loc_5.removeMode();
                    }
                    if (getQualifiedClassName(_loc_5) == _loc_3)
                    {
                        this.m_activeModes.splice(_loc_4, 1);
                        _loc_4 = _loc_4 - 1;
                    }
                    _loc_4++;
                }
                if (param2)
                {
                    this.m_activeModes = new Array(param1);
                }
                else
                {
                    this.m_activeModes.push(param1);
                }
                if (param2)
                {
                    UI.removeAllCursors();
                }
                param1.enableMode();
            }
            return;
        }//end

        public void  centerCityView (double param1 =1)
        {
            _loc_2 = Global.gameSettings().getInt("startCameraX");
            _loc_3 = Global.gameSettings().getInt("startCameraY");
            this.centerOnIsoPosition(new Vector3(_loc_2, _loc_3), param1);
            return;
        }//end

         public void  centerOnObject (WorldObject param1 ,double param2 =1)
        {
            _loc_3 = param1.getPosition();
            this.centerOnIsoPosition(_loc_3, param2);
            return;
        }//end

        public void  centerOnObjectWithCallback (WorldObject param1 ,double param2 =1,Function param3 =null )
        {
            _loc_4 = param1.getPosition();
            this.centerOnIsoPosition(_loc_4, param2, param3);
            return;
        }//end

        public void  centerOnIsoPosition (Vector3 param1 ,double param2 =1,Function param3 =null )
        {
            if (this.m_scrollTween)
            {
                this.m_scrollTween.complete(true);
                this.m_scrollTween = null;
            }
            Object _loc_4 ={startPos GlobalEngine.viewport.getScrollPosition (),alpha 0};
            _loc_5 = (IsoViewport)GlobalEngine.viewport
            _loc_5.centerOnTilePos(param1.x, param1.y);
            _loc_4.endPos = _loc_5.getScrollPosition();
            _loc_5.setScrollPosition(_loc_4.startPos);
            this.m_scrollTween = TweenLite.to(_loc_4, param2, {alpha:1, onUpdate:this.onUpdateScrollTween, onUpdateParams:.get(_loc_4), onComplete:this.onCompleteScrollTweenWithCallback, onCompleteParams:.get(param3)});
            return;
        }//end

        public void  zoom (double param1 ,double param2 ,double param3 =1)
        {
            if (this.m_zoomTween)
            {
                this.m_zoomTween.complete(true);
                this.m_zoomTween = null;
            }
            _loc_4 = (IsoViewport)GlobalEngine.viewport
            _loc_4.setZoom(param1);
            Object _loc_5 ={startZoom param1 ,alpha 0endZoom ,};
            //{startZoom:param1, alpha:0}.endZoom = param2;
            this.m_zoomTween = TweenLite.to(_loc_5, param3, {alpha:1, onUpdate:this.onUpdateZoomTween, onUpdateParams:.get(_loc_5), onComplete:this.onCompleteZoomTween});
            return;
        }//end

        public boolean  zoomFinished ()
        {
            if (this.m_zoomTween && this.m_zoomTween.active)
            {
                return false;
            }
            if (this.m_scrollTween && this.m_scrollTween.active)
            {
                return false;
            }
            return true;
        }//end

        protected void  onCompleteZoomTween ()
        {
            this.m_zoomTween = null;
            return;
        }//end

        protected void  onUpdateZoomTween (Object param1 )
        {
            _loc_2 = param1.endZoom*param1.alpha+param1.startZoom*(1-param1.alpha);
            GlobalEngine.viewport.setZoom(_loc_2);
            return;
        }//end

         public void  cleanUp ()
        {
            IGameWorldStateObserver _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            	_loc_1 = this.m_stateObservers.get(i0);

                _loc_1.cleanUp();
            }
            while (this.m_activeModes.length())
            {

                this.popGameMode();
            }
            if (this.m_lotSprite != null && this.m_lotSprite.parent != null)
            {
                this.m_lotSprite.parent.removeChild(this.m_lotSprite);
                this.m_lotSprite = null;
            }
            Global.ui.cleanUpToolTip();
            super.cleanUp();
            return;
        }//end

        public Array  getActiveGameModes ()
        {
            return this.m_activeModes;
        }//end

        public int  getAllCountByName (String param1 )
        {
            ConstructionSite _loc_4 =null ;
            Array _loc_5 =null ;
            Mall _loc_6 =null ;
            MapResource _loc_7 =null ;
            int _loc_2 =0;
            _loc_2 = _loc_2 + Global.player.inventory.getItemCountByName(param1);
            _loc_2 = _loc_2 + Global.player.storageComponent.getItemsByName(param1).length;
            _loc_2 = _loc_2 + Global.world.getObjectsByNames(.get(param1)).length;
            _loc_3 = Global.world.getObjectsByClass(ConstructionSite);
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4.targetName == param1)
                {
                    _loc_2 = _loc_2 + 1;
                }
            }
            _loc_5 = Global.world.getObjectsByClass(Mall);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                for(int i0 = 0; i0 < _loc_6.slots.size(); i0++)
                {
                	_loc_7 = _loc_6.slots.get(i0);

                    if (_loc_7)
                    {
                        if (_loc_7.getItem().name == param1)
                        {
                            _loc_2 = _loc_2 + 1;
                        }
                    }
                }
            }
            return _loc_2;
        }//end

        public Array  getObjectsByNames (Array param1 ,boolean param2 =true )
        {
            Array _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            MapResource _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_10 =0;
            Array _loc_3 =new Array ();
            if (cacheObjectNamesAndTypes && param2)
            {
                _loc_5 = 0;
                while (_loc_5 < param1.length())
                {

                    _loc_4 = this.m_nameCache.get(param1.get(_loc_5));
                    if (_loc_4 != null)
                    {
                        _loc_6 = 0;
                        while (_loc_6 < _loc_4.length())
                        {

                            _loc_3.push(_loc_4.get(_loc_6));
                            _loc_6++;
                        }
                    }
                    _loc_5++;
                }
            }
            else
            {
                _loc_4 = getObjects();
                _loc_7 = 0;
                while (_loc_7 < _loc_4.length())
                {

                    _loc_8 =(MapResource) _loc_4.get(_loc_7);
                    if (_loc_8 != null && _loc_8.getItem() != null)
                    {
                        _loc_9 = _loc_8.getItem().name;
                        _loc_10 = 0;
                        while (_loc_10 < param1.length())
                        {

                            if (_loc_9 == param1.get(_loc_10))
                            {
                                _loc_3.push(_loc_8);
                            }
                            _loc_10++;
                        }
                    }
                    _loc_7++;
                }
            }
            return _loc_3;
        }//end

        public Array  getObjectsByTypes (Array param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            MapResource _loc_7 =null ;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            Array _loc_2 =new Array ();
            if (cacheObjectNamesAndTypes)
            {
                _loc_4 = 0;
                while (_loc_4 < param1.length())
                {

                    _loc_3 = this.m_typeCache.get(param1.get(_loc_4));
                    if (_loc_3 != null)
                    {
                        _loc_5 = 0;
                        while (_loc_5 < _loc_3.length())
                        {

                            _loc_2.push(_loc_3.get(_loc_5));
                            _loc_5++;
                        }
                    }
                    _loc_4++;
                }
            }
            else
            {
                _loc_3 = getObjects();
                _loc_6 = 0;
                while (_loc_6 < _loc_3.length())
                {

                    _loc_7 =(MapResource) _loc_3.get(_loc_6);
                    if (_loc_7 != null && _loc_7.getItem() != null)
                    {
                        _loc_8 = _loc_7.getObjectType();
                        _loc_9 = 0;
                        while (_loc_9 < param1.length())
                        {

                            _loc_10 = param1.get(_loc_9);
                            if (_loc_8 == _loc_10)
                            {
                                _loc_2.push(_loc_7);
                            }
                            _loc_9++;
                        }
                    }
                    _loc_6++;
                }
            }
            return _loc_2;
        }//end

         public Array  getObjectsByType (int param1 =0)
        {
            return this.getObjectsByTypes(.get(param1));
        }//end

        public Array  getObjectsByClassAt (Class param1 ,Vector3 param2 )
        {
            WorldObject _loc_5 =null ;
            Array _loc_3 =new Array ();
            if (param1 == null || param2 == null)
            {
                return _loc_3;
            }
            Array _loc_4 = Global.world.getCollisionMap().getObjectsByPosition(param2.x,param2.y);
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = (WorldObject)_loc_4.get(i0);

                if (_loc_5 instanceof param1)
                {
                    _loc_3.push(_loc_5);
                }
            }
            return _loc_3;
        }//end

         public Array  getObjectsByClass (Class param1)
        {
            if (param1 != MapResource)
            {
                if (!(param1 in this.m_classCache))
                {
                    this.m_classCache.put(param1,  super.getObjectsByClass(param1));
                }
                return this.m_classCache.get(param1);
            }
            else
            {
                return super.getObjectsByClass(param1);
            }
        }//end

        public Array  getObjectsByPredicate (Function param1 )
        {
            ObjectLayer _loc_3 =null ;
            WorldObject _loc_4 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < m_children.size(); i0++)
            {
            	_loc_3 = m_children.get(i0);

                for(int i0 = 0; i0 < _loc_3.children.size(); i0++)
                {
                		_loc_4 = _loc_3.children.get(i0);

                    if (param1(_loc_4))
                    {
                        _loc_2.push(_loc_4);
                    }
                }
            }
            return _loc_2;
        }//end

        public Array  getObjectsByTargetName (String param1 )
        {
            MapResource _loc_5 =null ;
            Array _loc_2 =new Array();
            Array _loc_3 = getObjects();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(MapResource) _loc_3.get(_loc_4);
                if (_loc_5 != null && _loc_5.getItem() != null)
                {
                    if (_loc_5 instanceof ConstructionSite)
                    {
                        if (_loc_5.getActionTargetName() == param1)
                        {
                            _loc_2.push(_loc_5);
                        }
                    }
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

        public Array  getObjectsByKeywords (...args )
        {
            MapResource _loc_5 =null ;
            boolean _loc_6 =false ;
            Item _loc_7 =null ;
            String _loc_8 =null ;
            Array args1 =new Array();
            Array _loc_3 = getObjects();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(MapResource) _loc_3.get(_loc_4);
                if (_loc_5)
                {
                    _loc_6 = true;
                    _loc_7 = _loc_5.getItem();
                    for(int i0 = 0; i0 < args.size(); i0++)
                    {
                    	_loc_8 = args.get(i0);

                        if (!_loc_7 || !_loc_7.itemHasKeyword(_loc_8))
                        {
                            _loc_6 = false;
                            break;
                        }
                    }
                    if (_loc_6)
                    {
                        args1.push(_loc_5);
                    }
                }
                _loc_4++;
            }
            return args1;
        }//end

        public Array  getObjectsByRegEx (String param1 )
        {
            MapResource _loc_5 =null ;
            RegExp _loc_6 =null ;
            Array _loc_7 =null ;
            Array _loc_2 =new Array();
            Array _loc_3 = getObjects();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(MapResource) _loc_3.get(_loc_4);
                if (_loc_5 && _loc_5.getItemName())
                {
                    _loc_6 = new RegExp(param1);
                    _loc_7 = _loc_6.exec(_loc_5.getItemName());
                    if (_loc_7 && _loc_7.length > 0)
                    {
                        _loc_2.push(_loc_5);
                    }
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

        public Array  findConnectingMapResources (int param1 ,int param2 ,int param3 ,int param4 ,Array param5 =null ,boolean param6 =true ,Class param7 =null )
        {
            WorldObject _loc_16 =null ;
            double _loc_17 =0;
            boolean _loc_18 =false ;
            WorldObject _loc_19 =null ;
            int _loc_8 = param1-1;
            int _loc_9 = param1+param3 +1;
            int _loc_10 = param2-1;
            int _loc_11 = param2+param4+1;
            Array _loc_12 =new Array();
            Object _loc_13 ={};
            Array _loc_14 =new Array();
            _loc_15 = Global.world.checkCollision((_loc_8+1),(_loc_11-1),(_loc_9-1),(_loc_11-1),[],_loc_14);
            _loc_12 = _loc_14.splice(0);
            if (!_loc_15 || param6 || param7)
            {
                _loc_15 = Global.world.checkCollision((_loc_8 + 1), _loc_10, (_loc_9 - 1), _loc_10, [], _loc_14);
                _loc_12 = _loc_12.concat(_loc_14.splice(0));
            }
            if (!_loc_15 || param6 || param7)
            {
                _loc_15 = Global.world.checkCollision(_loc_8, (_loc_10 + 1), _loc_8, (_loc_11 - 1), [], _loc_14);
                _loc_12 = _loc_12.concat(_loc_14.splice(0));
            }
            if (!_loc_15 || param6 || param7)
            {
                _loc_15 = Global.world.checkCollision((_loc_9 - 1), (_loc_10 + 1), (_loc_9 - 1), (_loc_11 - 1), [], _loc_14);
                _loc_12 = _loc_12.concat(_loc_14.splice(0));
            }
            for(int i0 = 0; i0 < _loc_12.size(); i0++)
            {
            	_loc_16 = _loc_12.get(i0);

                _loc_17 = _loc_16.getId();
                _loc_13.put(_loc_17,  _loc_16);
            }
            _loc_12 = new Array();
            for(int i0 = 0; i0 < _loc_13.size(); i0++)
            {
            	_loc_16 = _loc_13.get(i0);

                if (param5 && param5.length())
                {
                    _loc_18 = false;
                    for(int i0 = 0; i0 < param5.size(); i0++)
                    {
                    	_loc_19 = param5.get(i0);

                        if (_loc_19 == _loc_16)
                        {
                            _loc_18 = true;
                            break;
                        }
                    }
                    if (!_loc_18 && (!param7 || _loc_16 instanceof param7))
                    {
                        _loc_12.put(_loc_12.length,  _loc_16);
                    }
                    continue;
                }
                if (!param7 || _loc_16 instanceof param7)
                {
                    _loc_12.put(_loc_12.length,  _loc_16);
                }
            }
            return _loc_12;
        }//end

        public GameMode  getTopGameMode ()
        {
            GameMode _loc_1 =null ;
            if (this.m_activeModes.length())
            {
                _loc_1 = this.m_activeModes.get((this.m_activeModes.length - 1));
            }
            return _loc_1;
        }//end

         public void  initialize ()
        {
            IGameWorldStateObserver _loc_2 =null ;
            Rectangle _loc_3 =null ;
            super.initialize();
            m_shards.setAllNumShards(1);
            this.m_lastUpdateTime = getTimer();
            if (this.m_territory)
            {
                _loc_3 = this.m_territory.occupiedBoundingRect;
                if (this.m_initialRoadsRect)
                {
                    _loc_3 = _loc_3.union(this.m_initialRoadsRect);
                }
                m_collisionMap.dispose();
                m_collisionMap = new GameCollisionMap();
                (GameCollisionMap)m_collisionMap.initRect(_loc_3.x, _loc_3.y, _loc_3.width, _loc_3.height);
            }


            if (Game.m_blitting)
            {
                addLayer(new BlitGameObjectLayer("road", -2));
                addLayer(new BlitGameObjectLayer("roadOverlay", -1));
                addLayer(new BlitGameObjectLayer("backship", -1));
                addLayer(new BlitGameObjectLayer("frontship", 1));
                addLayer(new BlitGameObjectLayer("doober", 8));
                addLayer(new BlitGameObjectLayer("clouds", 10));
            }
            else if (npcDepthSorting || m_enableUpdateCulling)
            {
                addLayer(new RoadLayer("road", -2));
                addLayer(new GameObjectLayer("roadOverlay", -1));
                addLayer(new GameObjectLayer("backship", -1));
                addLayer(new GameObjectLayer("frontship", 1));
                addLayer(new GameObjectLayer("doober", 8));
                addLayer(new GameObjectLayer("clouds", 10));
            }
            else
            {
                addLayer(new RoadLayer("road", -2));
                addLayer(new ObjectLayer("roadOverlay", -1));
                addLayer(new ObjectLayer("backship", -1));
                addLayer(new ObjectLayer("frontship", 1));
                addLayer(new ObjectLayer("doober", 8));
                addLayer(new ObjectLayer("clouds", 10));
            }
            InitializationManager.getInstance().addEventListener(Event.COMPLETE, this.onGameInitComplete);
            this.setDefaultGameMode();
            if (GlobalEngine.socialNetwork)
            {
                GlobalEngine.socialNetwork.addEventListener(SocialNetworkEvent.GET_FRIENDS_COMPLETE, this.onGetFriends);
            }
            if (this.m_lotSprite == null)
            {
                this.createLotBG(true);
            }
            _loc_1 = Global.gameSettings().getNumber("giftMissionTimeRange");
            this.m_giftRange = Math.round(Math.random() * _loc_1);
            this.m_isAddNeighborShown = false;
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            		_loc_2 = this.m_stateObservers.get(i0);

                _loc_2.initialize();
            }
            this.m_nameCache = new Dictionary();
            this.m_typeCache = new Dictionary();
            this.m_idCache = new Dictionary();
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        private void  createObjects (Object param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            WorldObject _loc_5 =null ;
            Object _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            Vector<DistanceObject> _loc_9=null ;
            Vector3 _loc_10 =null ;
            int _loc_11 =0;
            this.cleanUp();
            if (param1.sizeX && param1.sizeY)
            {
                m_size.x = param1.sizeX;
                m_size.y = param1.sizeY;
            }
            Rectangle _loc_2 =new Rectangle ();
            this.m_initialRoadsRect = null;
            for(int i0 = 0; i0 < param1.objects.size(); i0++)
            {
                _loc_3 = param1.objects.get(i0);
                _loc_6 = param1.objects.get(_loc_3);
                if (_loc_6.className == "Road")
                {
                    _loc_2.x = _loc_6.position.x;
                    _loc_2.y = _loc_6.position.y;
                    _loc_2.width = Road.SIZE_X;
                    _loc_2.height = Road.SIZE_Y;
                    if (this.m_initialRoadsRect == null)
                    {
                        this.m_initialRoadsRect = _loc_2;
                        continue;
                    }
                    this.m_initialRoadsRect = this.m_initialRoadsRect.union(_loc_2);
                }
            }
            this.initialize();
            if (Game.m_blitting)
            {
                m_defaultLayer = new BlitGameObjectLayer(OVERRIDE_LAYER_NAME);
            }
            else
            {
                m_defaultLayer = this.createObjectLayer(OVERRIDE_LAYER_NAME);
            }
            addLayer(m_defaultLayer);
            if (Game.m_blitting)
            {
                addLayer(new BlitGameObjectLayer("npc", 1));
            }
            if (Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_CENTERED_LOADING))
            {
                _loc_7 = Global.gameSettings().getInt("startCameraX");
                _loc_8 = Global.gameSettings().getInt("startCameraY");
                _loc_9 = new Vector<DistanceObject>();
                for(int i0 = 0; i0 < param1.objects.size(); i0++)
                {
                	_loc_4 = param1.objects.get(i0);

                    _loc_5 = this.createObjectInstance(param1.objects.get(_loc_4));
                    if (_loc_5)
                    {
                        _loc_5.loadObject(param1.objects.get(_loc_4));
                        _loc_10 = _loc_5.getPositionNoClone();
                        _loc_11 = (_loc_10.x - _loc_7) * (_loc_10.x - _loc_7) + (_loc_10.y - _loc_8) * (_loc_10.y - _loc_8);
                        _loc_9.push(new DistanceObject(_loc_11, _loc_5));
                    }
                }
                _loc_9.sort(this.compareFunction);
                for(int i0 = 0; i0 < _loc_9.size(); i0++)
                {
                	_loc_4 = _loc_9.get(i0);

                    _loc_5 = _loc_9.get(_loc_4).obj;
                    _loc_5.setOuter(this);
                    _loc_5.attach();
                }
            }
            else
            {
                for(int i0 = 0; i0 < param1.objects.size(); i0++)
                {
                	_loc_4 = param1.objects.get(i0);

                    _loc_5 = this.createObjectInstance(param1.objects.get(_loc_4));
                    if (_loc_5)
                    {
                        _loc_5.loadObject(param1.objects.get(_loc_4));
                        _loc_5.setOuter(this);
                        _loc_5.attach();
                    }
                }
            }
            return;
        }//end

        private double  compareFunction (DistanceObject param1 ,DistanceObject param2 )
        {
            return param1.distance - param2.distance;
        }//end

        public boolean  isModeLocked ()
        {
            return this.m_modeLock > 0;
        }//end

        private void  createWaterFeatures (Object param1 )
        {
            Debug.debug4("GameWorld"+"createWaterFeatures");
            if (!param1.waterFeatures)
            {
                return;
            }
            if (param1.waterFeatures.length < 1)
            {
                return;
            }
            WaterFeature _loc_2 =new WaterFeature ();
            _loc_2.loadObject(param1.waterFeatures.get(0));
            this.m_river = _loc_2;
            return;
        }//end

        protected void  loadTerritory (Object param1 )
        {
            int _loc_2 =0;
            Rectangle _loc_3 =null ;
            Debug.debug4("GameWorld"+"loadTerritory");
            if (param1.mapRects && param1.mapRects.length())
            {
                this.m_territory = new TerritoryMap(12);
                _loc_2 = 0;
                while (_loc_2 < param1.mapRects.length())
                {

                    _loc_3 = new Rectangle(param1.mapRects.get(_loc_2).x, param1.mapRects.get(_loc_2).y, param1.mapRects.get(_loc_2).width, param1.mapRects.get(_loc_2).height);
                    this.m_territory.addTerritory(_loc_3);
                    _loc_2++;
                }
            }
            return;
        }//end

        public void  rebuildCollisionMap ()
        {
            ObjectLayer _loc_2 =null ;
            WorldObject _loc_3 =null ;
            m_collisionMap.dispose();
            Debug.debug4("GameWorld"+"rebuildCollisionMap");
            _loc_1 = this.m_territory.occupiedBoundingRect;
            if (this.m_initialRoadsRect)
            {
                _loc_1 = _loc_1.union(this.m_initialRoadsRect);
            }
            ((GameCollisionMap)m_collisionMap).initRect(_loc_1.x, _loc_1.y, _loc_1.width, _loc_1.height);
            ((GameCollisionMap)m_collisionMap).setOwnedArea(this.m_territory.occupiedRects);
            for(int i0 = 0; i0 < m_children.size(); i0++)
            {
            		_loc_2 = m_children.get(i0);

                for(int i0 = 0; i0 < _loc_2.children.size(); i0++)
                {
                		_loc_3 = _loc_2.children.get(i0);

                    m_collisionMap.insertObject(_loc_3);
                }
            }
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            IGameWorldStateObserver _loc_2 =null ;
            this.m_ownerId = param1.ownerId;
            this.loadTerritory(param1);
            this.populateThemes(param1.currentThemes, param1.currentThemeCollections);
            this.createObjects(param1);
            this.createWaterFeatures(param1);
            this.citySim.loadObject(param1.citySim);
            if (this.m_territory)
            {
                ((GameCollisionMap)m_collisionMap).setOwnedArea(this.m_territory.occupiedRects);
            }
            this.setDefaultGameMode();
            if (Global.isVisiting() == false)
            {
                this.resetIdleMissionTime();
                this.resetLastPromoTime();
            }
            if (this.m_wildernessSim == null)
            {
                this.m_wildernessSim = new WildernessSim();
                this.addObserver(this.m_wildernessSim);
                if (Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_CENTERED_LOADING))
                {
                    this.allocateGrassBitmap();
                }
            }
            else
            {
                this.createOverlayBackground();
            }
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            		_loc_2 = this.m_stateObservers.get(i0);

                _loc_2.onGameLoaded(param1);
            }
            return;
        }//end

         public void  insertObjectIntoDepthArray (WorldObject param1 ,String param2 )
        {
            _loc_3 = getObjectLayerByName(param2);

            if (param2 == null || _loc_3 == null)
            {
                param2 = DEFAULT_LAYER_NAME;
            }
            super.insertObjectIntoDepthArray(param1, param2);
            return;
        }//end

        public void  lockGameMode ()
        {
            this.m_modeLock++;
            return;
        }//end

        public void  popGameMode ()
        {
            GameMode _loc_1 =null ;
            if (this.m_activeModes.length && this.isModeLocked() == false)
            {
                _loc_1 =(GameMode) this.m_activeModes.pop();
                _loc_1.disableMode();
                if (this.m_activeModes.length())
                {
                    this.m_activeModes.get((this.m_activeModes.length - 1)).enableMode();
                }
            }
            return;
        }//end

        public void  removeGameMode (GameMode param1 )
        {
            int _loc_2 =0;
            GameMode _loc_3 =null ;
            if (this.m_activeModes.length && this.isModeLocked() == false)
            {
                _loc_2 = 0;
                while (_loc_2 < this.m_activeModes.length())
                {

                    _loc_3 =(GameMode) this.m_activeModes.get(_loc_2);
                    if (_loc_3 == param1)
                    {
                        param1.disableMode();
                        this.m_activeModes.splice(_loc_2, 1);
                        if (_loc_2 == this.m_activeModes.length && this.m_activeModes.length > 0)
                        {
                            this.m_activeModes.get((this.m_activeModes.length - 1)).enableMode();
                        }
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

         public WorldObject  getObjectById (int param1 ,int param2 =1.67772e +007)
        {
            Array _loc_4 =null ;
            ObjectLayer _loc_5 =null ;
            _loc_6 = undefined;
            WorldObject _loc_3 =null ;
            if (cacheObjectIds)
            {
                _loc_4 = this.m_idCache.get(param1);
                if (_loc_4 != null && _loc_4.length > 0)
                {
                    _loc_3 =(WorldObject) _loc_4.get(0);
                    if ((_loc_3 != null && _loc_3.getObjectType() & param2) > 0)
                    {
                        return _loc_3;
                    }
                }
            }
            else
            {
                for(int i0 = 0; i0 < m_children.size(); i0++)
                {
                	_loc_5 = m_children.get(i0);

                    for(int i0 = 0; i0 < _loc_5.children.size(); i0++)
                    {
                    	_loc_6 = _loc_5.children.get(i0);

                        _loc_3 =(WorldObject) _loc_6;
                        if (_loc_3 != null && _loc_3.getId() == param1 && (_loc_3.getObjectType() & param2) > 0)
                        {
                            return _loc_3;
                        }
                    }
                }
            }
            return null;
        }//end

        public void  resetAddNeighborTime ()
        {
            this.m_lastAddNeighborTime = getTimer();
            return;
        }//end

        public void  resetGenericAdTime ()
        {
            this.m_lastGenericAdTime = getTimer();
            return;
        }//end

        public void  resetGiftMissionTime ()
        {
            this.m_lastGiftMissionTime = getTimer();
            return;
        }//end

        public void  resetIdleMissionTime ()
        {
            this.m_lastIdleMissionTime = getTimer();
            return;
        }//end

        public void  resetLastPromoTime ()
        {
            this.m_lastPromoShowTime = GlobalEngine.getTimer();
            return;
        }//end

        public boolean  isHome ()
        {
            return Global.isVisiting() == false && this.m_ownerId == GlobalEngine.socialNetwork.getLoggedInUserId();
        }//end

        public String  ownerId ()
        {
            return this.m_ownerId;
        }//end

        public boolean  isOwnerCitySam ()
        {
            _loc_1 = boolean(this.m_ownerId==CITY_SAM_OWNER_ID);
            return _loc_1;
        }//end

        public void  setDefaultGameMode ()
        {
            if (Global.isVisiting())
            {
                if (Global.player.canEnterGMVisitBuy())
                {
                    this.addGameMode(new GMVisitBuy(Global.getVisiting()));
                }
                else
                {
                    this.addGameMode(new GMVisit(Global.getVisiting()));
                }
            }
            else
            {
                this.addGameMode(new GMPlay());
            }
            return;
        }//end

        public boolean  isThemeEnabled (String param1 )
        {
            Theme _loc_3 =null ;
            boolean _loc_2 =false ;
            for(int i0 = 0; i0 < this.currentThemes.size(); i0++)
            {
            	_loc_3 = this.currentThemes.get(i0);

                if (_loc_3 != null && _loc_3.name == param1)
                {
                    _loc_2 = true;
                    break;
                }
            }
            return _loc_2;
        }//end

        public boolean  isThemeCollectionEnabled (Item param1 )
        {
            return this.m_currentThemeCollections.indexOf(param1.name) >= 0;
        }//end

        public Theme Vector  currentThemes ().<>
        {
            return this.m_currentThemes;
        }//end

        private Theme Vector  convertCurrentThemes (Array param1 ).<>
        {
            Theme _loc_3 =null ;
            Vector<Theme> _loc_2 =new Vector<Theme>();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

        private String Vector<>  convertCurrentThemeCollections (Array param1)
        {
            String _loc_3 =null ;
            Vector<String> _loc_2 =new Vector<String>();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

        private void  populateThemes (Array param1 ,Array param2 )
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            Vector<Theme> _loc_5 =null ;
            this.m_currentThemes = new Vector<Theme>();
            this.m_currentThemeCollections = new Vector<String>();
            this.m_currentThemeCollections = this.convertCurrentThemeCollections(param2);
            for(int i0 = 0; i0 < this.m_currentThemeCollections.size(); i0++)
            {
            	_loc_3 = this.m_currentThemeCollections.get(i0);

                _loc_4 = Global.gameSettings().getItemByName(_loc_3);
                if (_loc_4)
                {
                    _loc_5 = this.convertCurrentThemes(_loc_4.getThemeCollection());
                    this.insertThemes(_loc_5);
                }
            }
            return;
        }//end

        private void  insertThemes (Vector param1 .<Theme >)
        {
            Theme _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.insertTheme(_loc_2);
            }
            return;
        }//end

        private void  insertTheme (Theme param1 )
        {
            Theme _loc_4 =null ;
            int _loc_2 =0;
            boolean _loc_3 =false ;
            for(int i0 = 0; i0 < this.m_currentThemes.size(); i0++)
            {
            	_loc_4 = this.m_currentThemes.get(i0);

                if (_loc_4.priority > param1.priority)
                {
                    this.m_currentThemes.splice(_loc_2, 0, param1);
                    _loc_3 = true;
                    break;
                }
                _loc_2++;
            }
            if (!_loc_3)
            {
                this.m_currentThemes.push(param1);
            }
            return;
        }//end

        public void  reload ()
        {
            GameTransactionManager.addTransaction(new TWorldLoad(this.m_ownerId), true, true);
            TransactionManager.sendAllTransactions(true);
            return;
        }//end

        public boolean  isDefaultGameMode ()
        {
            _loc_1 = this.getTopGameMode();
            boolean _loc_2 =false ;
            if (Global.isVisiting())
            {
                _loc_2 = _loc_1 instanceof GMVisit;
            }
            else
            {
                _loc_2 = _loc_1 instanceof GMPlay;
            }
            return _loc_2;
        }//end

        public void  unlockGameMode ()
        {
            if (this.m_modeLock > 0)
            {
                this.m_modeLock--;
            }
            return;
        }//end

        public void  updateNeighborText ()
        {
            this.createLotBG(false);
            return;
        }//end

         public void  updateWorld ()
        {


            int ii =0;
            for(;ii<Global.stage.numChildren;ii++) {

            }

	    ii = Global.stage.numChildren;


            IGameWorldStateObserver _loc_4 =null ;
            Quest _loc_6 =null ;
            Vector _loc_7.<Quest >=null ;
            super.updateWorld();
            _loc_1 = getTimer();
            this.monitorPerformance(_loc_1);
            IdleDialogManager.doIdleDialogTimerCheck();
            ViralManager.doViralManagerThawCheck();

            Global.player.update();
            if (this.m_wildernessSim)
            {
                this.m_wildernessSim.update();
            }
            _loc_2 = _loc_1(-this.m_lastUpdateTime)/1000;
            _loc_3 = _loc_1-this.m_lastUpdateTime ;
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            	_loc_4 = this.m_stateObservers.get(i0);

                if (_loc_4 instanceof IGameWorldUpdateObserver)
                {
                    ((IGameWorldUpdateObserver)_loc_4).update(_loc_2);
                }
            }

            Global.weiboManager.update(_loc_1);

            Global.ui.updateToolTip(_loc_2);
            Global.world.citySim.friendVisitManager.update((_loc_1 - this.m_lastUpdateTime) / 1000);
            Global.guide.update();
            this.m_invalidQuestElapsed = this.m_invalidQuestElapsed + _loc_3;
			/*
            if (this.m_invalidQuestElapsed > INVALID_QUEST_UPDATE)
            {
                this.m_invalidQuestElapsed = this.m_invalidQuestElapsed - INVALID_QUEST_UPDATE;
                _loc_7 = Global.questManager.getAndRemoveInvalidQuests();
                if (_loc_7.length > 0)
                {
                    GameTransactionManager.addTransaction(new TCheckInvalidQuests(_loc_7));
                }
            }
            _loc_5 = Global.questManager.getActiveQuests();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                if (_loc_6 instanceof GameQuest)
                {
                    ((GameQuest)_loc_6).updateGuide();
                }
            }
			*/
            //Global.hud.streakBonus.onUpdate(_loc_2);

            CollectionPanelPopout.getInstance().onUpdate(_loc_2);
            this.m_lastUpdateTime = _loc_1;


            return;
        }//end

        public Array  getPickObjects (Point param1 ,int param2 =1.67772e +007,int param3 =0)
        {
            int _loc_5 =0;
            ObjectLayer _loc_6 =null ;
            int _loc_7 =0;
            PositionedObject _loc_8 =null ;
            PositionedObject _loc_9 =null ;
            Array _loc_10 =null ;
            int _loc_11 =0;
            Array _loc_4 =new Array();
            _loc_5 = m_children.length - 1;
            while (_loc_5 >= 0)
            {

                _loc_6 =(ObjectLayer) m_children.get(_loc_5);
                _loc_9 = null;
                _loc_10 = _loc_6.children;
                _loc_11 = _loc_10.length;
                if (GameObjectLayer.useCulling())
                {
                    _loc_7 = _loc_11 - 1;
                    while (_loc_7 >= 0)
                    {

                        _loc_8 =(PositionedObject) _loc_10.get(_loc_7);
                        if (_loc_8.isVisible())
                        {
                            _loc_9 =(PositionedObject) _loc_8.pickObject(param1, param2, param3);
                            if (_loc_9 != null)
                            {
                                _loc_4.put(_loc_4.length,  _loc_9);
                            }
                        }
                        _loc_7 = _loc_7 - 1;
                    }
                }
                else
                {
                    _loc_7 = _loc_11 - 1;
                    while (_loc_7 >= 0)
                    {

                        _loc_8 =(PositionedObject) _loc_10.get(_loc_7);
                        _loc_9 =(PositionedObject) _loc_8.pickObject(param1, param2, param3);
                        if (_loc_9 != null)
                        {
                            _loc_4.put(_loc_4.length,  _loc_9);
                        }
                        _loc_7 = _loc_7 - 1;
                    }
                }
                _loc_5 = _loc_5 - 1;
            }
            return _loc_4;
        }//end

        protected void  checkPerformance (int param1 )
        {
            if (DEFCON_LOWEST_LEVEL == this.m_currentDefconLevel && param1 <= FPS_DEFCON_UP_THRESHOLD)
            {
                this.m_secondsBelowDefconThreshold++;
            }
            else if (this.m_maxDefcon == this.m_currentDefconLevel && param1 > FPS_DEFCON_DOWN_THRESHOLD)
            {
                this.m_secondsAboveDefconThreshold++;
            }
            else if (DEFCON_LOWEST_LEVEL != this.m_currentDefconLevel && this.m_maxDefcon != this.m_currentDefconLevel)
            {
                if (param1 <= FPS_DEFCON_UP_THRESHOLD)
                {
                    this.m_secondsBelowDefconThreshold++;
                    this.m_secondsAboveDefconThreshold = 0;
                }
                else
                {
                    this.m_secondsAboveDefconThreshold++;
                    this.m_secondsBelowDefconThreshold = 0;
                }
            }
            else
            {
                this.m_secondsAboveDefconThreshold = 0;
                this.m_secondsBelowDefconThreshold = 0;
            }
            if (this.m_secondsBelowDefconThreshold >= SEC_BELOW_DEFCON_THRESHOLD)
            {
                this.toggleDefconLevel((this.m_currentDefconLevel + 1));
            }
            else if (this.m_secondsAboveDefconThreshold >= SEC_ABOVE_DEFCON_THRESHOLD)
            {
                this.toggleDefconLevel((this.m_currentDefconLevel - 1));
            }
            return;
        }//end

        public Object  logGetProfilingData (int param1 ,boolean param2 )
        {
            WorldObject _loc_9 =null ;
            NPC _loc_10 =null ;
            MapResource _loc_11 =null ;
            Object _loc_12 =null ;
            _loc_3 = Config.DEBUG_MODE&& this.m_printPerformanceData;
            if (!_loc_3 && !param2)
            {
                return 0;
            }
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            if (!Global.world.getObjectLayerByName("default"))
            {
                return null;
            }
            _loc_8 = Global.world.getObjectLayerByName("default").children;
            for(int i0 = 0; i0 < _loc_8.size(); i0++)
            {
            	_loc_9 = _loc_8.get(i0);

                if (_loc_9 instanceof NPC)
                {
                    _loc_10 =(NPC) _loc_9;
                    _loc_4++;
                    if (_loc_10.isVisible())
                    {
                        _loc_5++;
                    }
                    continue;
                }
                if (_loc_9 instanceof MapResource)
                {
                    _loc_11 =(MapResource) _loc_9;
                    _loc_6++;
                    if (_loc_11.isVisible())
                    {
                        _loc_7++;
                    }
                }
            }
            if (param2)
            {
                _loc_12 = new Object();
                _loc_12.put("npc", _loc_4);
                _loc_12.put("npcv", _loc_5);
                _loc_12.put("build", _loc_6);
                _loc_12.put("buildv", _loc_7);
                return _loc_12;
            }
            return null;
        }//end

        public Object  getProfilingGameState ()
        {
            Object _loc_1 ={};
            _loc_1.put("frozen", UI.isScreenFrozen());
            _loc_1.put("edit", Global.world.getTopGameMode()isGMEdit);
            _loc_1.put("visit", Global.isVisiting());
            _loc_1.put("fullscreen", Utilities.isFullScreen());
            _loc_1.put("defcon", this.defCon);
            _loc_2 = Utilities.isFullScreen()? ("f") : ("w");
            _loc_3 = GlobalEngine.zaspManager.zaspIsGameActive()? ("a") : ("i");
            _loc_4 = UI.isScreenFrozen()? ("fr") : ("no");
            _loc_5 = Global.isVisiting()? ("visit") : (Global.world.getTopGameMode() instanceof GMEdit ? ("edit") : ("none"));
            if (Wonder.getFirstActiveWonder())
            {
                _loc_5 = "wonder";
            }
            _loc_6 = _loc_2+"-"+_loc_3 +"-"+_loc_4 +"-"+_loc_5 +"-"+this.defCon ;
            _loc_1.put("description",  _loc_6);
            return _loc_1;
        }//end

        protected void  createTerritoryBG ()
        {
            Rectangle _loc_1 =null ;
            Vector2 _loc_2 =null ;
            Debug.debug4("GameWorld"+"createTerritoryBG");

            if (this.m_territory)
            {
                if (this.m_lotSprite != null)
                {
                    this.m_lotSprite.graphics.clear();
                    GlobalEngine.viewport.overlayBase.removeChild(this.m_lotSprite);
                }
                else
                {
                    this.m_lotSprite = new Sprite();
                }
                this.m_lotSprite.graphics.beginFill(0, 0.3);
                for(int i0 = 0; i0 < this.m_territory.occupiedRects.size(); i0++)
                {
                	_loc_1 = this.m_territory.occupiedRects.get(i0);

                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y);
                    this.m_lotSprite.graphics.moveTo(_loc_2.x, _loc_2.y);
                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y + _loc_1.height);
                    this.m_lotSprite.graphics.lineTo(_loc_2.x, _loc_2.y);
                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x + _loc_1.width, _loc_1.y + _loc_1.height);
                    this.m_lotSprite.graphics.lineTo(_loc_2.x, _loc_2.y);
                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x + _loc_1.width, _loc_1.y);
                    this.m_lotSprite.graphics.lineTo(_loc_2.x, _loc_2.y);
                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y);
                    this.m_lotSprite.graphics.lineTo(_loc_2.x, _loc_2.y);
                }
                this.m_lotSprite.graphics.drawRect(-10000, -10000, 20000, 20000);
                this.m_lotSprite.graphics.endFill();
                GlobalEngine.viewport.overlayBase.cacheAsBitmap = true;
                GlobalEngine.viewport.overlayBase.addChildAt(this.m_lotSprite, 1);
            }
            return;
        }//end

        public void  drawAlignmentGrid (GameObject param1 )
        {
            if (this.m_alignSprite != null)
            {
                GlobalEngine.viewport.overlayBase.removeChild(this.m_alignSprite);
            }
            this.m_alignSprite = new Sprite();
            this.m_alignSprite.graphics.lineStyle(0.5, 16777215);
            Box3D _loc_2 = param1.getBoundingBox();
            int _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x,_loc_2.y);
            int _loc_4 = IsoMath.tilePosToPixelPos(_loc_2.x+_loc_2.width,_loc_2.y);
            int _loc_5 = IsoMath.tilePosToPixelPos(_loc_2.x,_loc_2.y+_loc_2.height);
            int _loc_6 = IsoMath.tilePosToPixelPos(_loc_2.x+_loc_2.width,_loc_2.y+_loc_2.height);
            this.m_alignSprite.graphics.moveTo(_loc_3.x, _loc_3.y);
            this.m_alignSprite.graphics.lineTo(_loc_4.x, _loc_4.y);
            this.m_alignSprite.graphics.lineTo(_loc_6.x, _loc_6.y);
            this.m_alignSprite.graphics.lineTo(_loc_5.x, _loc_5.y);
            this.m_alignSprite.graphics.lineTo(_loc_3.x, _loc_3.y);
            GlobalEngine.viewport.overlayBase.addChildAt(this.m_alignSprite, GlobalEngine.viewport.overlayBase.numChildren);
            return;
        }//end

        protected void  createLotBG (boolean param1 =true )
        {
            TextField _loc_13 =null ;
            if (this.gameInitStatus == GAME_INIT_COMPLETE)
            {
                this.createTerritoryBG();
            }
            return;
            this.m_neighborText = new Array();
            Vector2 _loc_11 = _loc_4.cloneVec2();
            _loc_4.cloneVec2().normalize(_loc_9);
            _loc_12 = Vector2.lerp(_loc_6,_loc_8,0.5);
            this.renderLotText(_loc_12.add(_loc_11), _loc_2, true, this.getNeighborName(0));
            Vector2 _loc_11 = _loc_3.cloneVec2();
            _loc_11.normalize(_loc_9);
            _loc_12 = Vector2.lerp(_loc_8, _loc_7, 0.5);
            this.renderLotText(_loc_12.subtract(_loc_11), _loc_2, false, this.getNeighborName(1));
            _loc_12 = Vector2.lerp(_loc_5, _loc_7, 0.5);
            this.renderLotText(_loc_12, _loc_2, true, this.getNeighborName(2));
            _loc_12 = Vector2.lerp(_loc_6, _loc_5, 0.5);
            this.renderLotText(_loc_12, _loc_2, false, this.getNeighborName(3));
            Vector2 _loc_11 = _loc_4.cloneVec2();
            _loc_11.normalize(_loc_9);
            _loc_12 = Vector2.lerp(_loc_6, _loc_8, 0.5);
            _loc_12.x = _loc_12.x - _loc_3.x * getGridWidth() * 0.7;
            _loc_12.y = _loc_12.y - _loc_3.y * getGridWidth() * 0.7;
            this.renderLotText(_loc_12.add(_loc_11), _loc_2, true, this.getNeighborName(4));
            Vector2 _loc_11 = _loc_4.cloneVec2();
            _loc_11.normalize(_loc_9);
            _loc_12 = Vector2.lerp(_loc_6, _loc_8, 0.5);
            _loc_12.x = _loc_12.x + _loc_3.x * getGridWidth() * 0.8;
            _loc_12.y = _loc_12.y + _loc_3.y * getGridWidth() * 0.8;
            this.renderLotText(_loc_12.add(_loc_11), _loc_2, true, this.getNeighborName(5));
            _loc_12 = Vector2.lerp(_loc_5, _loc_7, 0.5);
            _loc_12.x = _loc_12.x - _loc_3.x * getGridWidth() * 0.7;
            _loc_12.y = _loc_12.y - _loc_3.y * getGridWidth() * 0.7;
            this.renderLotText(_loc_12, _loc_2, true, this.getNeighborName(6));
            _loc_12 = Vector2.lerp(_loc_5, _loc_7, 0.5);
            _loc_12.x = _loc_12.x + _loc_3.x * getGridWidth() * 0.8;
            _loc_12.y = _loc_12.y + _loc_3.y * getGridWidth() * 0.8;
            this.renderLotText(_loc_12, _loc_2, true, this.getNeighborName(7));
            return;
        }//end

        protected void  renderLotLine (Sprite param1 ,Point param2 ,Vector2 param3 ,int param4 ,boolean param5 )
        {
            Point _loc_6 =new Point(param3.x *getGridHeight ()*0.5,param3.y *getGridHeight ()*0.5);
            _loc_7 = param2.add(_loc_6);
            _loc_8 = param2.subtract(_loc_6).subtract(_loc_6);
            _loc_9 = param2.subtract(_loc_6).subtract(_loc_6).subtract(_loc_6);
            double _loc_10 =2;
            _loc_11 = param5? (Math.PI * (-26 / 180)) : (Math.PI * (26 / 180));
            Matrix _loc_12 =new Matrix ();
            _loc_12.createGradientBox(_loc_6.length, _loc_6.length, _loc_11, _loc_7.x, _loc_7.y);
            param1.graphics.lineStyle(_loc_10, param4, 0);
            param1.graphics.lineGradientStyle("linear", [param4, param4], [0, 100], [20, 235], _loc_12);
            param1.graphics.moveTo(_loc_7.x, _loc_7.y);
            param1.graphics.lineTo(param2.x, param2.y);
            param1.graphics.lineGradientStyle("linear", [param4, param4], [100, 100], [20, 235]);
            param1.graphics.lineTo(_loc_8.x, _loc_8.y);
            _loc_12.createGradientBox(_loc_6.length, _loc_6.length, _loc_11, _loc_8.x, _loc_8.y);
            param1.graphics.lineGradientStyle("linear", [param4, param4], [100, 0], [20, 235], _loc_12);
            param1.graphics.lineTo(_loc_9.x, _loc_9.y);
            return;
        }//end

        protected void  renderLotText (Point param1 ,int param2 ,boolean param3 ,String param4 =null )
        {
            return;
        }//end

        protected void  onNeighborTextClick (MouseEvent event )
        {
            return;
        }//end

        public WorldObject  loadObjectInstance (Object param1 )
        {
            String className ;
            String itemName ;
            Class SavedObjectClassType ;
            WorldObject instance ;
            data = param1;
            try
            {
                className = data.className;
                itemName = data.itemName;
                SavedObjectClassType =(Class) getDefinitionByName("Classes." + className);
                if (itemName != null)
                {
                    instance = new SavedObjectClassType(itemName);
                }
                else
                {
                    instance = new SavedObjectClassType;
                }
            }
            catch (e:ReferenceError)
            {
                ErrorManager.addError("Type was not found " + className, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
            }
            return instance;
        }//end

         protected WorldObject  createObjectInstance (Object param1 )
        {
            return this.loadObjectInstance(param1);
        }//end

        protected void  enableAnimations (boolean param1 )
        {
            Global.playAnimations = param1;
            _loc_2 = Global.world.getObjects();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                ((GameObject)_loc_2.get(_loc_3)).conditionallyRedraw(true);
                _loc_3++;
            }
            return;
        }//end

        protected String  getNeighborName (int param1 )
        {
            Player _loc_3 =null ;
            String _loc_2 =null ;
            param1 = Global.player.neighbors.length - param1 - 1;
            if (Global.player && param1 >= 0)
            {
                _loc_3 = Global.player.findFriendById(Global.player.neighbors.get(param1));
                if (_loc_3)
                {
                    _loc_2 = _loc_3.firstName;
                }
            }
            return _loc_2;
        }//end

        protected void  giftIdleMissionHandler (GenericPopupEvent event )
        {
            _loc_2 = event.button==GenericPopup.YES;
            if (_loc_2 == true)
            {
                GlobalEngine.socialNetwork.redirect("gifts.php?ref=giftMissionDialog");
            }
            this.m_showingGiftMissionDialog = false;
            return;
        }//end

        protected boolean  checkAppHidden (int param1 )
        {
            return !Global.appActive && param1 <= 2;
        }//end

        protected void  monitorPerformance (int param1 )
        {
            this.m_frameCount++;
            if (param1 >= this.m_lastUpdate + FPS_UPDATE_INTERVAL)
            {
                this.m_lastUpdate = param1;
                if (!defconHide || !this.checkAppHidden(this.m_frameCount))
                {
                    this.checkPerformance(this.m_frameCount);
                }
                this.logGetProfilingData(this.m_frameCount, false);
                this.m_frameCount = 0;
            }
            return;
        }//end

        protected void  onCompleteScrollTweenWithCallback (Function param1)
        {
            this.m_scrollTween = null;
            if (param1 != null)
            {
                param1();
            }
            return;
        }//end

        protected void  onConfirmVisit (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                UI.visitNeighbor(this.m_pendingVisitUserId);
            }
            return;
        }//end

        protected void  onGameInitComplete (Event event )
        {
            boolean _loc_3 =false ;
            this.gameInitStatus = GAME_INIT_COMPLETE;
            _loc_2 =             !Global.player.isNewPlayer && !Global.disableGamePopups && !Global.disableLoadPopups;
            if (_loc_2)
            {
                GameTransactionManager.addTransaction(new TAutoPopZMC(), true);
            }
            else
            {
                StartupSessionTracker.interactive();
            }
            if (Global.player.compensationFlag != Player.NOT_COMPENSATED)
            {
                _loc_3 = Global.player.compensationFlag == Player.COMPENSATED_GIFT_CARD ? (true) : (false);
                UI.displayCreditBasic(_loc_3);
            }
            this.createLotBG();
            Global.player.commodities.updateCapacities();
            Global.hud.updateCommodities();
            if (!Global.mission.missionType)
            {
                Global.postInitActions.execute();
            }
            Global.disableLoadPopups = false;
            return;
        }//end

        protected void  onGetFriends (Event event )
        {
            this.updateNeighborText();
            return;
        }//end

         protected void  onUpdateScrollTween (Object param1 )
        {
            _loc_2 = Vector2.lerp(param1.startPos,param1.endPos,param1.alpha);
            GlobalEngine.viewport.setScrollPosition(_loc_2);
            return;
        }//end

        public void  forceDefcon (int param1 )
        {
            this.toggleDefconLevel(param1);
            this.m_secondsAboveDefconThreshold = -360;
            this.m_secondsBelowDefconThreshold = -360;
            return;
        }//end

        protected void  toggleDefconLevel (int param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            this.m_secondsAboveDefconThreshold = 0;
            this.m_secondsBelowDefconThreshold = 0;
            if (Global.guide && Global.guide.isActive())
            {
                if (Global.playAnimations == false)
                {
                    this.enableAnimations(true);
                }
                Global.stage.quality = StageQuality.HIGH;
            }
            else if (param1 != this.m_currentDefconLevel)
            {
                this.m_currentDefconLevel = param1;
                if (param1 >= DEFCON_LEVEL4 && Global.playAnimations)
                {
                    this.enableAnimations(false);
                }
                else if (!Global.playAnimations)
                {
                    this.enableAnimations(true);
                }
                switch(param1)
                {
                    case DEFCON_LEVEL1:
                    {
                        if (!Global.player.options.graphicsLowQuality)
                        {
                            Global.stage.quality = Game.m_blitting ? (StageQuality.LOW) : (StageQuality.HIGH);
                        }

                        break;
                    }
                    case DEFCON_LEVEL2:
                    {
                        Global.stage.quality = StageQuality.LOW;

                        break;
                    }
                    case DEFCON_LEVEL3:
                    {
                        break;
                    }
                    case DEFCON_LEVEL4:
                    {
                        break;
                    }
                    case DEFCON_LEVEL5:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                if (GameWorld.enableUpdateShardingOrCulling)
                {
                    _loc_2 = MathUtil.clamp(Math.pow(4, param1), 1, 16);
                    this.getSharder(ShardScheduler.CATEGORY_LOW_PRIORITY).setNumShards(_loc_2);
                    _loc_3 = param1 + 1;
                    if (param1 >= DEFCON_NORMAL_HIGHEST_LEVEL)
                    {
                        _loc_3 = 8;
                    }
                    this.getSharder(ShardScheduler.CATEGORY_HIGH_PRIORITY).setNumShards(_loc_3);
                    this.recomputeUpdateTimes();
                }
            }
            return;
        }//end

        private int  getMapExpansionSize ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            if (_loc_1)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.background.@expandsize;
               Debug.debug4("GameWorld"+"getMapExpansionSize" + _loc_3);
                return _loc_3;
            }
            return 0;
        }//end

        private int  getXOverlayBackgroundRectableAdjustment ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            if (_loc_1)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.background.@xOverlayOffset;
                return _loc_3;
            }
            return 0;
        }//end

        private void  allocateGrassBitmap ()
        {

            _loc_1 = Global.gameSettings().getXML();
            int _loc_2 =_loc_1.background.@minBound;
            int _loc_3 =_loc_1.background.@maxBound;
            _loc_4 = _loc_1.background.@bgColor;

            _loc_4 = //"0x009999";
            this.m_groundmapScale = 1 / Global.gameSettings().getNumber("maxZoom", 1);
            this.m_groundmapOrigin = IsoMath.tilePosToPixelPos(0, 0);
            if (this.m_overlayBackgroundRect == null)
            {
                this.m_overlayBackgroundRect = new Rectangle();
            }

            this.m_overlayBackgroundRect.width = _loc_3 - _loc_2 + this.getMapExpansionSize();
            this.m_overlayBackgroundRect.height = _loc_3 - _loc_2 + this.getMapExpansionSize();
            this.m_overlayBackgroundRect.x = this.m_groundmapOrigin.x - this.m_overlayBackgroundRect.width / 2 + this.getXOverlayBackgroundRectableAdjustment();
            this.m_overlayBackgroundRect.y = this.m_groundmapOrigin.y - this.m_overlayBackgroundRect.height / 2;


            this.m_overlayBackgroundRect.width = 1000;
            this.m_overlayBackgroundRect.height = 700;
            this.m_overlayBackgroundRect.x = -200;
            this.m_overlayBackgroundRect.y = -100;
*/

            Debug.debug4("allocateGrassBitmap."+"width"+this.m_overlayBackgroundRect.width);
            Debug.debug4("allocateGrassBitmap."+"height"+this.m_overlayBackgroundRect.height);
            Debug.debug4("allocateGrassBitmap."+"x"+this.m_overlayBackgroundRect.x);
            Debug.debug4("allocateGrassBitmap."+"y"+this.m_overlayBackgroundRect.y);
            this.m_grassBitmapInitialized = false;
            if (this.m_grassBitmap)
            {
                this.m_grassBitmap.bitmapData.fillRect(new Rectangle(0, 0, this.m_grassBitmap.width, this.m_grassBitmap.height), _loc_4);
                return;
            }
            double _loc_5 =this.m_groundmapScale ;

            Debug.debug4("_loc_6."+"width "+(_loc_3 - _loc_2 + this.getMapExpansionSize()) * (1 / _loc_5));

            Debug.debug4("_loc_6."+"height "+(_loc_3 - _loc_2) * (1 / _loc_5));

            BitmapData _loc_6 =new BitmapData ((_loc_3 -_loc_2 +this.getMapExpansionSize ())*(1/_loc_5 ),(_loc_3 -_loc_2 )*(1/_loc_5 ),false ,_loc_4 );



            Debug.debug4("m_groundmapScale."+_loc_5);


            Debug.debug4("_loc_6."+"width "+_loc_6.width);
            Debug.debug4("_loc_6."+"height "+_loc_6.height);




            this.m_grassBitmap = new Bitmap(_loc_6, "auto", true);
            Debug.debug4("m_grassBitmap.1 "+"width:"+this.m_grassBitmap.width+";height"+this.m_grassBitmap.height+";x."+this.m_grassBitmap.x+";y."+this.m_grassBitmap.y);
            return;
        }//end

        public boolean  tileInGroundmap (int param1 ,int param2 )
        {
            if (this.m_grassBitmap == null)
            {
                return false;
            }
            _loc_3 = IsoMath.tilePosToPixelPos(param1+0.5,param2+0.5);
            _loc_3.x = _loc_3.x - (this.m_groundmapOrigin.x + this.getXOverlayBackgroundRectableAdjustment());
            _loc_3.y = _loc_3.y - this.m_groundmapOrigin.y;
            int _loc_4 =5;
            if (_loc_3.x > this.m_groundmapScale * this.m_grassBitmap.bitmapData.width / 2 - _loc_4)
            {
                return false;
            }
            if (_loc_3.y > this.m_groundmapScale * this.m_grassBitmap.bitmapData.height / 2 - _loc_4)
            {
                return false;
            }
            if (_loc_3.x < -(this.m_groundmapScale * this.m_grassBitmap.bitmapData.width / 2 - _loc_4))
            {
                return false;
            }
            if (_loc_3.y < -(this.m_groundmapScale * this.m_grassBitmap.bitmapData.height / 2 - _loc_4))
            {
                return false;
            }
            return true;
        }//end

        public void  createOverlayBackground ()
        {
            Array images ;
            Array items ;
            Array states ;
            boolean initialized ;
            XML layer ;
            Item targetItem ;
            String targetState ;
            this.allocateGrassBitmap();
            this.m_wildernessSim.generateWilderness();
            this.m_wildernessSim.onTerritoryRedraw(this.m_territory);
            xml = Global.gameSettings().getXML();
            Array loaders =new Array ();
            images = new Array();
            items = new Array();
            states = new Array();
            initialized = false;
            int i ;
            int cachedCount ;


            for(int i0 = 0; i0 < xml.background.layer .size(); i0++)
            {
            	layer = xml.background.layer .get(i0);

		Debug.debug4("createOverlayBackground."+layer.item.@name);
                targetItem = Global.gameSettings().getItemByName(layer.item.@name);
                items.put(i,  targetItem);
                targetState = layer.item.@state;
                states.put(i,  targetState);
                if (this.addBitmapToArray(targetItem, targetState, images, i))
                {
                    cachedCount = (cachedCount + 1);
                }
                else
                {
                    targetItem .addEventListener (LoaderEvent .LOADED ,void  (Event event )
			    {
				Item _loc_3 =null ;
				int _loc_2 =0;
				for(int i0 = 0; i0 < items.size(); i0++)
				{
					_loc_3 = items.get(i0);

				    if (_loc_3.getCachedImage(states.get(_loc_2)) == null)
				    {
					return;
				    }
				    addBitmapToArray(_loc_3, states.get(_loc_2), images, _loc_2);
				    _loc_2++;
				}
				if (!initialized)
				{
				    grassitize(images);
				}
		//                initialized = true;
				return;
			    }//end
			    );
                }
                i = (i + 1);
            }
            if (cachedCount == i)
            {
                if (!initialized)
                {
                    this.grassitize(images);
                }
                initialized = true;
            }
            return;
        }//end

        private boolean  addBitmapToArray (Item param1 ,String param2 ,Array param3 ,int param4 )
        {
            MovieClip _loc_7 =null ;
            int _loc_8 =0;
            Bitmap _loc_9 =null ;
            boolean _loc_5 =false ;
            ItemImageInstance _loc_6 =param1.getCachedImage(param2 );


            if (param1.getCachedImage(param2))
            {
                _loc_7 =(MovieClip) _loc_6.image;
                _loc_8 = 0;
                while (_loc_8 < _loc_7.numChildren)
                {

                    _loc_9 =(Bitmap) _loc_7.getChildAt(_loc_8);
                    if (_loc_9)
                    {
                        param3.put(param4,  _loc_9);
                        _loc_5 = true;
                        break;
                    }
                    _loc_8++;
                }
            }
            return _loc_5;
        }//end

        public boolean  canDrawInGrass ()
        {
            return this.m_grassBitmap != null && this.m_grassBitmapInitialized;
        }//end

        private int  getGrassXOffsetForExpansion ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            if (_loc_1 > 0)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.background.@xGrassOffset;
                return _loc_3;
            }
            return 0;
        }//end

        public void  drawInGrass (Bitmap param1 ,double param2 ,double param3 ,int param4 =0,int param5 =0)
        {
            if (this.m_grassBitmap == null)
            {
                return;
            }
            Point _loc_6 =IsoMath.tilePosToPixelPos(0,0);
            int _loc_7 =_loc_6.x ;
            int _loc_8 =_loc_6.y ;
            _loc_6 = IsoMath.tilePosToPixelPos(param2, param3);
            _loc_7 = _loc_6.x - _loc_7;
            _loc_8 = _loc_6.y - _loc_8;
            _loc_7 = _loc_7 + param4;
            _loc_8 = _loc_8 + param5;
            Point _loc_9 =new Point ();
            _loc_9.x = 4 * _loc_7 + this.m_grassBitmap.bitmapData.width * 0.5 + this.getGrassXOffsetForExpansion();
            _loc_9.y = 4 * _loc_8 + this.m_grassBitmap.bitmapData.height * 0.5;
            Rectangle _loc_10 =new Rectangle(param2 ,param3 ,2,2);
            if (Global.world.rectInTerritory(_loc_10) == false)
            {

                this.m_grassBitmap.bitmapData.copyPixels(param1.bitmapData, new Rectangle(0, 0, param1.width, param1.height), _loc_9);
            }
            return;
        }//end

        private void  tileRivers (BitmapData param1 ,Bitmap param2 ,XML param3 )
        {
            Matrix _loc_4 =new Matrix ();
            double _loc_5 =RIVER_TILE_SCALE ;
            int _loc_6 =param3.x ;
            int _loc_7 =param3.y ;
            _loc_6 = _loc_6 + this.getRiverOffsetForXExpansion();
            _loc_4.identity();
            _loc_4.translate(_loc_6, _loc_7);
            _loc_4.scale(_loc_5, _loc_5);
            _loc_4.translate(param1.width * 0.5, param1.height * 0.5);

            Debug.debug4("GameWorld"+"tileRivers" + _loc_6+";"+_loc_7);

            param1.draw(param2.bitmapData, _loc_4);

            return;
        }//end

        private void  tileOpenWater (BitmapData param1 ,Bitmap param2 ,Bitmap param3 ,XML param4 ,int param5 )
        {
            Debug.debug4("GameWorld"+"tileOpenWater");
            double _loc_15 =0;
            double _loc_16 =0;
            double _loc_17 =0;
            double _loc_18 =0;
            Matrix _loc_6 =new Matrix ();
            double _loc_7 =RIVER_TILE_SCALE ;
            _loc_8 = param4.x;
            _loc_9 = param4.y;
            _loc_10 = param4.fillBeneath;
            _loc_11 = param4.leftOverlap;
            _loc_12 = param4.rightOverlap;
            _loc_13 = param4.topOverlap;
            _loc_14 = param4.bottomOverlap;
            if (param3 && _loc_10 != 0)
            {
                _loc_6.identity();
                _loc_9 = _loc_9 + (param2.bitmapData.height - _loc_13);
                if (param5 > _loc_9 * _loc_7)
                {
                    _loc_15 = param5 - _loc_9 * _loc_7;
                    _loc_15 = _loc_15 + _loc_14;
                    _loc_16 = param2.bitmapData.width * _loc_7;
                    if (param4.fillWidth != 0)
                    {
                        _loc_16 = param4.fillWidth * _loc_7;
                    }
                    _loc_16 = _loc_16 + (_loc_11 + _loc_12);
                    _loc_8 = _loc_8 - _loc_11;
                    _loc_17 = _loc_16 / param3.bitmapData.width;
                    _loc_18 = _loc_15 / param3.bitmapData.height;
                    _loc_6.scale(_loc_17, _loc_18);
                    _loc_6.translate(_loc_8 * _loc_7 + this.getOceanOffsetForXExpansion(), _loc_9 * _loc_7);
                    _loc_6.translate(param1.width * 0.5, param1.height * 0.5);
                    param1.draw(param3.bitmapData, _loc_6);
                }
            }
            return;
        }//end

        private double  getOceanOffsetForXExpansion ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            Debug.debug4("GameWorld"+"getOceanOffsetForXExpansion.1");
            if (_loc_1 > 0)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.background.@xOceanOffset;
                Debug.debug4("GameWorld"+"getOceanOffsetForXExpansion" + _loc_3);
                return _loc_3;
            }
            return 0;
        }//end

        private double  getRiverOffsetForXExpansion ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            if (_loc_1 > 0)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.background.@xRiverOffset;
                return _loc_3;
            }
            return 0;
        }//end

        private void  placeRiverTiles (Array param1 ,int param2 ,XMLList param3 ,BitmapData param4 )
        {
            Bitmap _loc_5 =null ;
            XML _loc_8 =null ;
            int _loc_9 =0;
            Bitmap _loc_10 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            Debug.debug4("GameWorld"+"placeRiverTiles");
            for(int i0 = 0; i0 < param3.size(); i0++)
            {
            	_loc_8 = param3.get(i0);

                if (_loc_7 < param2)
                {
                    _loc_7++;
                    continue;
                }
                _loc_9 = _loc_8.isWaterTile;
                _loc_10 =(Bitmap) param1.get(_loc_7);
                _loc_7++;
                if (_loc_9)
                {
                    _loc_6 = _loc_8.tileYLimit;
                    _loc_5 = _loc_10;
                    continue;
                }
                this.tileOpenWater(param4, _loc_10, _loc_5, _loc_8, _loc_6);
            }
            _loc_7 = 0;
            for(int i0 = 0; i0 < param3.size(); i0++)
            {
            	_loc_8 = param3.get(i0);

                if (_loc_7 < param2)
                {
                    _loc_7++;
                    continue;
                }
                _loc_9 = _loc_8.isWaterTile;
                _loc_10 =(Bitmap) param1.get(_loc_7);
                _loc_7++;
                if (_loc_9)
                {
                    continue;
                }
                this.tileRivers(param4, _loc_10, _loc_8);
            }
            return;
        }//end

        private void  grassitize (Array param1 )
        {
            XML _loc_9 =null ;
            Bitmap _loc_10 =null ;
            Matrix _loc_11 =null ;
            Rectangle _loc_12 =null ;
            int _loc_13 =0;
            int _loc_14 =0;
            int _loc_15 =0;
            int _loc_16 =0;
            int _loc_17 =0;
            int _loc_18 =0;
            int _loc_19 =0;

            GameUtil.srand(0);
            XML _loc_2 =Global.gameSettings().getXML ();
            Matrix _loc_3 =new Matrix ();
            double _loc_4 =this.m_groundmapScale ;
            _loc_3.scale(_loc_4, _loc_4);
            BitmapData _loc_5 =this.m_grassBitmap.bitmapData ;
            _loc_6 = _loc_2.background.@bgColor;

            Debug.debug4("grassitize.m_groundmapScale."+_loc_4);




            int _loc_7 =0;
            Point _loc_8 =new Point ();
            for(int i0 = 0; i0 < _loc_2.background.layer.size(); i0++)
            {
            		_loc_9 = _loc_2.background.layer.get(i0);

                _loc_10 =(Bitmap) param1.get(_loc_7);
                _loc_11 = new Matrix();
                _loc_12 = _loc_10.bitmapData.rect;
                _loc_13 = _loc_9.tile;
                _loc_14 = _loc_9.river;
                if (_loc_14)
                {
                    this.placeRiverTiles(param1, _loc_7, _loc_2.background.layer, _loc_5);
                    break;
                }
                else if (_loc_13)
                {
                    _loc_15 = _loc_5.width / _loc_12.width;
                    _loc_16 = _loc_5.height / _loc_12.height;
                    _loc_17 = 0;
                    while (_loc_17 <= _loc_15)
                    {

                        _loc_18 = 0;
                        while (_loc_18 <= _loc_16)
                        {

                            _loc_8.x = _loc_17 * _loc_12.width;
                            _loc_8.y = _loc_18 * _loc_12.height;


                            _loc_18++;
                        }
                        _loc_17++;
                    }
                }
                else
                {
                    _loc_19 = 0;
                    while (_loc_19 < _loc_9.instances)
                    {

                        _loc_8.x = GameUtil.rand(0, _loc_5.width);
                        _loc_8.y = GameUtil.rand(0, _loc_5.height);


                        _loc_19++;
                    }
                }
                _loc_7++;
            }

            _loc_3.translate((-this.m_grassBitmap.width) / 2 + this.m_groundmapOrigin.x + this.getXOverlayBackgroundRectableAdjustment(), (-this.m_grassBitmap.height) / 2 + this.m_groundmapOrigin.y);
            this.m_grassBitmap.transform.matrix = _loc_3;
            this.m_grassBitmapInitialized = true;
            if (this.m_grassBitmap.parent == null)
            {
                GlobalEngine.viewport.overlayBase.addChildAt(this.m_grassBitmap, 0);

            }





            return;
        }//end

        public double  rand (double param1 ,double param2)
        {
            return Math.random() * (param1 - param2) + param2;
        }//end

        private void  onAvatarFeedClosed ()
        {
            Global.startTutorial();
            return;
        }//end

        public boolean  rectIntersectsTerritory (Rectangle param1 )
        {
            Rectangle _loc_4 =null ;
            boolean _loc_2 =false ;
            _loc_3 = this.m_territory.occupiedRects;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (param1.intersects(_loc_4))
                {
                    _loc_2 = true;
                    break;
                }
            }
            return _loc_2;
        }//end

        public boolean  rectInTerritory (Rectangle param1 )
        {
            _loc_2 = this.m_territory.rectInTerritory(param1);
            return _loc_2;
        }//end

        public Rectangle  getWorldRect ()
        {
            Rectangle _loc_1 =null ;
            if (this.m_territory)
            {
                _loc_1 = this.m_territory.occupiedBoundingRect;
            }
            else
            {
                _loc_1 = new Rectangle(0, 0, getGridWidth(), getGridHeight());
            }
            return _loc_1;
        }//end

        public void  expandMap (Rectangle param1 )
        {
            this.m_territory.addTerritory(param1);
            this.rebuildCollisionMap();
            this.createTerritoryBG();
            Global.world.wildernessSim.onTerritoryRedraw(this.m_territory);
            Debug.debug4("GameWorld"+"expandMap");
            return;
        }//end

         protected ObjectLayer  createObjectLayer (String param1 )
        {
            if (m_enableUpdateCulling || m_npcDepthSorting)
            {
                return new GameObjectLayer(param1);
            }
            return new ObjectLayer(param1);
        }//end

        public void  updateWorldObjectCulling (int param1 )
        {
            WorldObject _loc_3 =null ;
            _loc_2 = Global.world.getObjects();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (param1 == CULL_ZOOM_IN && !_loc_3.isVisible())
                {
                    continue;
                }
                if (param1 == CULL_ZOOM_OUT && _loc_3.isVisible())
                {
                    continue;
                }
                _loc_3.updateCulling();
            }
            this.recomputeUpdateTimes();
            return;
        }//end

        private void  recomputeUpdateTimes ()
        {
            ObjectLayer _loc_1 =null ;
            GameObjectLayer _loc_2 =null ;
            for(int i0 = 0; i0 < m_children.size(); i0++)
            {
            	_loc_1 = m_children.get(i0);

                _loc_2 =(GameObjectLayer) _loc_1;
                if (_loc_2)
                {
                    _loc_2.recomputeNextUpdateTime = true;
                }
            }
            return;
        }//end

         public int  getNumObjects ()
        {
            ObjectLayer _loc_2 =null ;
            int _loc_1 =0;
            for(int i0 = 0; i0 < m_children.size(); i0++)
            {
            		_loc_2 = m_children.get(i0);

                _loc_1 = _loc_1 + _loc_2.children.length;
            }
            return _loc_1;
        }//end

         public int  getNumVisibleObjects ()
        {
            ObjectLayer _loc_2 =null ;
            Array _loc_3 =null ;
            PositionedObject _loc_4 =null ;
            int _loc_1 =0;
            for(int i0 = 0; i0 < m_children.size(); i0++)
            {
            	_loc_2 = m_children.get(i0);

                _loc_3 = _loc_2.children;
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    if (_loc_4.isVisible())
                    {
                        _loc_1++;
                    }
                }
            }
            return _loc_1;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            IGameWorldStateObserver _loc_4 =null ;
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            	_loc_4 = this.m_stateObservers.get(i0);

                _loc_4.onResourceChange(param1, param2, param3);
            }
            return;
        }//end

        protected boolean  predicateSelectDockHouseLinkedObjects (WorldObject param1 )
        {
            boolean _loc_2 =false ;
            if (param1 instanceof Dock || param1 instanceof HarvestableShip)
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public Array  getLinkedObjects (MapResource param1 )
        {
            Array _loc_2 =new Array ();
            switch(param1.getObjectType())
            {
                case WorldObjectTypes.DOCK_HOUSE:
                {
                    _loc_2 = this.getObjectsByPredicate(this.predicateSelectDockHouseLinkedObjects);
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

        public void  addObjectToCaches (MapResource param1 )
        {
            this.addObjectToNameCache(param1);
            this.addObjectToTypeCache(param1);
            this.addObjectToIdCache(param1);
            this.addObjectToClassCache(param1);
            return;
        }//end

        public void  delObjectFromCaches (MapResource param1 )
        {
            this.delObjectFromNameCache(param1);
            this.delObjectFromTypeCache(param1);
            this.delObjectFromIdCache(param1);
            this.delObjectFromClassCache(param1);
            return;
        }//end

        private void  addObjectToClassCache (MapResource param1 )
        {
            Object _loc_2 =null ;
            Class _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_classCache.size(); i0++)
            {
            	_loc_2 = this.m_classCache.get(i0);

                _loc_3 =(Class) _loc_2;
                if (param1 instanceof _loc_3)
                {
                    this.m_classCache.get(_loc_2).push(param1);
                }
            }
            return;
        }//end

        private void  delObjectFromClassCache (MapResource param1 )
        {
            Object _loc_2 =null ;
            Class _loc_3 =null ;
            int _loc_4 =0;
            for(int i0 = 0; i0 < this.m_classCache.size(); i0++)
            {
            	_loc_2 = this.m_classCache.get(i0);

                _loc_3 =(Class) _loc_2;
                if (param1 instanceof _loc_3)
                {
                    _loc_4 = this.m_classCache.get(_loc_2).indexOf(param1);
                    if (_loc_4 != -1)
                    {
                        this.m_classCache.get(_loc_2).splice(_loc_4, 1);
                    }
                }
            }
            return;
        }//end

        private void  addObjectToNameCache (MapResource param1 )
        {
            _loc_2 = param1.getItemName();
            _loc_3 = this.m_nameCache.get(_loc_2);
            if (_loc_3 == null)
            {
                _loc_3 = new Array();
                this.m_nameCache.put(_loc_2,  _loc_3);
            }
            _loc_3.push(param1);
            return;
        }//end

        private void  delObjectFromNameCache (MapResource param1 )
        {
            int _loc_4 =0;
            _loc_2 = param1.getItemName();
            _loc_3 = this.m_nameCache.get(_loc_2);
            if (_loc_3 != null)
            {
                _loc_4 = _loc_3.indexOf(param1);
                if (_loc_4 != -1)
                {
                    _loc_3.splice(_loc_4, 1);
                }
            }
            return;
        }//end

        private void  addObjectToTypeCache (MapResource param1 )
        {
            _loc_2 = param1.getObjectType();
            _loc_3 = this.m_typeCache.get(_loc_2);
            if (_loc_3 == null)
            {
                _loc_3 = new Array();
                m_typeCache.put(_loc_2, _loc_3);
            }
            _loc_3.push(param1);
            return;
        }//end

        private void  delObjectFromTypeCache (MapResource param1 )
        {
            _loc_2 = param1.getObjectType();
            _loc_3 = this.m_typeCache.get(_loc_2);
            if (_loc_3 == null)
            {
                return;
            }
            _loc_4 = _loc_3.indexOf(param1);
            if (_loc_3.indexOf(param1) > -1)
            {
                _loc_3.splice(_loc_4, 1);
            }
            return;
        }//end

        public void  addObjectToIdCache (WorldObject param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 = param1.getId();
            if (_loc_2 != 0)
            {
                _loc_3 = this.m_idCache.get(_loc_2);
                if (_loc_3 == null)
                {
                    _loc_3 = new Array();
                    this.m_idCache.put(_loc_2,  _loc_3);
                }
                _loc_4 = _loc_3.indexOf(param1);
                if (_loc_4 == -1)
                {
                    _loc_3.push(param1);
                }
            }
            return;
        }//end

        public void  delObjectFromIdCache (WorldObject param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 = param1.getId();
            if (_loc_2 != 0)
            {
                _loc_3 = this.m_idCache.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_4 = _loc_3.indexOf(param1);
                    if (_loc_4 != -1)
                    {
                        _loc_3.splice(_loc_4, 1);
                    }
                }
            }
            return;
        }//end

        public static boolean  npcDepthSorting ()
        {
            return m_npcDepthSorting;
        }//end

        public static boolean  cacheObjectNamesAndTypes ()
        {
            return m_cacheObjectNamesAndTypes;
        }//end

        public static boolean  cacheObjectIds ()
        {
            return m_cacheObjectIds;
        }//end

        public static boolean  skipEngineObjectUpdates ()
        {
            return m_skipEngineObjectUpdates;
        }//end

        public static boolean  enableUpdateShardingOrCulling ()
        {
            return m_enableUpdateSharding || m_enableUpdateCulling;
        }//end

        public static boolean  defconHide ()
        {
            return m_defconHide;
        }//end

    }





