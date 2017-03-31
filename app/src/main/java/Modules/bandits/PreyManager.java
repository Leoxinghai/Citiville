package Modules.bandits;

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
import Classes.actions.*;
import Classes.effects.*;
import Classes.gates.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.HunterAndPreyUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.bandits.transactions.*;
import Modules.workers.*;
import Transactions.*;

import com.adobe.utils.*;
import com.zynga.skelly.util.*;
//import flash.geom.*;
//import flash.utils.*;

    public class PreyManager implements IGameWorldStateObserver
    {
        private IGate m_gate ;
        private GateFactory m_gateFactory ;
        private Dictionary m_gateUnlockLevel ;
        private Dictionary m_hubUnlockLevels ;
        public Array workerManagers ;
        public static  String BASE_ASSET ="mun_policestation";
        public static Array m_scenes =new Array();
        public static Dictionary m_prey =new Dictionary ();
        public static Dictionary m_preySpawned =new Dictionary ();
        public static Dictionary m_preyData =new Dictionary ();
        public static Dictionary m_hunters =new Dictionary ();
        public static Dictionary m_specialNPCs =new Dictionary ();
        public static  String NPC_BANDIT ="npc_bandit";
        public static  String NPC_COP ="npc_cop";
        public static  String NPC_POLICECRUISER ="policecruiser";
        public static  String NPC_POLICEVAN ="swatvan";
        public static  String NPC_POLICESPORTCRUISER ="policesportcruiser";
        public static  String NPC_POLICEHELICOPTER ="policehelicopter";
        public static  String NPC_PIGEONSWARM ="prop_pigeon_swarm";
        public static  String NPC_SUPERHERO ="superhero";
        public static  int NUM_SWAT_COPS =3;
        public static  double BANDIT_NUDGE =0.9;
        public static  double COPTER_FLY_HEIGHT =10;
        public static  int MAX_PATROL_HUNTERS_DEFAULT =5;
        public static  String SECURE_RAND_FEATURE_NAME ="copsNBandits";
        public static  String ACTION_PATROL ="cop_patrol";
        public static  String ACTION_MAKE_AND_PATROL ="cop_make_patrol";
        public static  String ACTION_NO_BUSINESSES ="cop_no_bus";
        public static  String ACTION_SWITCH_AND_PATROL ="cop_switch_patrol";
        public static  String ACTION_REPLACE_AND_PATROL ="cop_replace_patrol";
        public static  int HELICOPTER_LEVEL =6;
        public static  int SUPERHERO_LEVEL =7;
        public static  String MODE_DEFAULT ="default";
        public static  String MODE_SUPERHERO ="superhero";
        public static Dictionary m_numPreyCaptured =new Dictionary ();
        public static Dictionary m_typesPreyCaptured =new Dictionary ();

        public  PreyManager (GameWorld param1 )
        {
            this.m_gateUnlockLevel = new Dictionary();
            param1.addObserver(this);
            this.m_gateFactory = new GateFactory();
            this.m_gateFactory.register(GateType.INVENTORY, InventoryGate);
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
            return;
        }//end

        public boolean  areHubsLocked (String param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            if (param1 !=null)
            {
                _loc_2 = PreyUtil.getHubLevel(param1);
                _loc_3 = PreyManager.getHunterPreyMode(param1);
                if (_loc_2 < int(_loc_3.get("levelRequired")))
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  areHubsGated (String param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            if (param1 !=null)
            {
                _loc_2 = PreyUtil.getHubLevel(param1);
                _loc_3 = PreyManager.getHunterPreyMode(param1);
                if (_loc_2 >= int(_loc_3.get("levelRequired")))
                {
                    if (_loc_3.get("gateName"))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public int  getHubUnlockLevel (String param1 )
        {
            if (this.m_gateUnlockLevel.get(param1) != null)
            {
                return this.m_gateUnlockLevel.get(param1);
            }
            return 0;
        }//end

        public void  setHubUnlockLevel (int param1 ,String param2 )
        {
            this.m_gateUnlockLevel.put(param2,  param1);
            return;
        }//end

        public boolean  doesHubNeedResource (String param1 )
        {
            _loc_2 = PreyManager.getHunterPreyMode(param1);
            if (int(_loc_2.get("huntersRetired")) == 0)
            {
                return PreyManager.getNumSleepingHunters(param1) > 0 && Global.world.viralMgr.canPost(param1 + "_getResource");
            }
            return false;
        }//end

        public void  showHubGate (String param1 ,Municipal param2 )
        {
            if (!PreyUtil.isHub(param1, param2))
            {
                throw new Error("Invalid hub for gating: " + param2.getItem().name);
            }
            _loc_3 = PreyManager.getHunterPreyMode(param1);
            String _loc_4 ="pre_upgrade";
            if (_loc_3.get("gateName"))
            {
                _loc_4 = String(_loc_3.get("gateName"));
            }
            if (this.m_gate == null)
            {
                this.m_gate = this.m_gateFactory.loadGateFromXML(param2.getItem(), param2, _loc_4, Curry.curry(this.onUpgradeGateSuccess, param1, param2));
            }
            if (this.m_gate == null)
            {
                throw new Error("Failed to initialize gate for item " + param2.getItem().name);
            }
            this.m_gate.displayGate();
            return;
        }//end

        public void  onUpgradeGateSuccess (String param1 ,Municipal param2 )
        {
            GameTransactionManager.addTransaction(new TUnlockHubGate(param2));
            PreyUtil.logGameActionStats("municipal", "unlock_gate", param2.getItem().name);
            this.setHubUnlockLevel(PreyUtil.getHubLevel(param1), param1);
            PreyUtil.refreshHubAppearance(param1);
            this.showHubUnlockedFTUEIfNecessary(param1);
            return;
        }//end

        public void  showHubUnlockedFTUEIfNecessary (String param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            Object _loc_6 =null ;
            CharacterDialog _loc_7 =null ;
            _loc_2 = PreyUtil.getHubLevel(param1);
            _loc_3 = getHunterPreyMode(param1);
            if (int(_loc_3.get("levelRequired")) == _loc_2)
            {
                _loc_4 = ZLoc.t("Dialogs", param1 + "_finishedHub_message");
                _loc_5 = param1 + "_finishedHub";
                _loc_6 = Global.gameSettings().getHubQueueInfo(param1);
                _loc_7 = new CharacterDialog(_loc_4, _loc_5, GenericDialogView.TYPE_OK, null, null, true, _loc_6.get("catchPreyFTUEAsset"), "Okay");
                UI.displayPopup(_loc_7, true, "Finished Hub", false);
            }
            return;
        }//end

        public void  parseServerData (Object param1 )
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = String(_loc_2.preyId);
                m_prey.put(_loc_3,  _loc_2.preys);
                m_numPreyCaptured.put(_loc_3,  _loc_2.numPreysCaptured);
                m_typesPreyCaptured.put(_loc_3,  _loc_2.typesPreysCaptured);
                this.m_gateUnlockLevel.put(_loc_3,  _loc_2.gateUnlockLevel || 1);
                this.setHubUnlockLevel(_loc_2.gateUnlockLevel || 1, _loc_3);
            }
            return;
        }//end

        public WorkerManager  getWorkerManagerByGroup (String param1 )
        {
            WorkerManager _loc_2 =null ;
            for(int i0 = 0; i0 < this.workerManagers.size(); i0++)
            {
            		_loc_2 = this.workerManagers.get(i0);

                if (_loc_2.featureName == param1)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public static Array  getPrey (String param1 )
        {
            if (m_prey.get(param1))
            {
                return m_prey.get(param1);
            }
            return new Array();
        }//end

        public static PreyData  getPreyData (String param1 ,Object param2 )
        {
            PreyData _loc_3 =null ;
            if (param1 && param2 && int(param2.id) >= 0)
            {
                if (m_preyData.get(param1))
                {
                    _loc_3 = m_preyData.get(param1).get(int(param2.id));
                }
                else
                {
                    m_preyData.put(param1,  new Array());
                }
                if (!_loc_3)
                {
                    _loc_3 = new PreyData(param1, param2);
                    m_preyData.get(param1).put(int(param2.id),  _loc_3);
                }
            }
            return _loc_3;
        }//end

        public static Array  getPreySpawned (String param1 )
        {
            if (m_preySpawned.get(param1))
            {
                return m_preySpawned.get(param1);
            }
            return new Array();
        }//end

        public static void  resetAllPreySpawned ()
        {
            m_preySpawned = new Dictionary();
            return;
        }//end

        public static double  getNumPreyCaptured (String param1 )
        {
            if (m_numPreyCaptured.get(param1) != null)
            {
                return m_numPreyCaptured.get(param1);
            }
            return -1;
        }//end

        public static void  setNumPreyCaptured (double param1 ,String param2 )
        {
            m_numPreyCaptured.put(param2,  param1);
            return;
        }//end

        public static Object  getTypesPreyCaptured (String param1 )
        {
            if (m_typesPreyCaptured.get(param1))
            {
                return m_typesPreyCaptured.get(param1);
            }
            return null;
        }//end

        public static void  addHunter (NPC param1 ,String param2 )
        {
            if (!m_hunters.get(param2))
            {
                m_hunters.put(param2,  new Array());
            }
            m_hunters.get(param2).push(param1);
            return;
        }//end

        public static Array  getHunters (String param1 )
        {
            if (param1 !=null)
            {
                if (!m_hunters.get(param1))
                {
                    m_hunters.put(param1,  new Array());
                }
                return m_hunters.get(param1);
            }
            return null;
        }//end

        public static int  getIndexOfHunter (NPC param1 ,String param2 )
        {
            if (!getHunters(param2))
            {
                return -1;
            }
            return getHunters(param2).indexOf(param1);
        }//end

        public static int  getNumActiveHuntersByGroup (String param1 )
        {
            _loc_2 = getHunters(param1);
            return _loc_2 ? (_loc_2.length()) : (0);
        }//end

        public static int  getMaxActiveHuntersByGroup (String param1 )
        {
            _loc_2 = getHunterPreyMode(param1);
            return _loc_2.get("performanceHunterLimit") ? (int(_loc_2.get("performanceHunterLimit"))) : (MAX_PATROL_HUNTERS_DEFAULT);
        }//end

        public static int  getNumActiveHunters ()
        {
            Array _loc_3 =null ;
            _loc_1 = DictionaryUtil.getValues(m_hunters);
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                _loc_2 = _loc_2 + _loc_3.length;
            }
            return _loc_2;
        }//end

        public static Object  getHunterPreyMode (String param1 )
        {
            Object _loc_6 =null ;
            _loc_2 =Global.gameSettings().getHunterPreyModes(param1 );
            _loc_3 = PreyUtil.getHubLevel(param1);
            _loc_4 = _loc_2.get("default") ? (_loc_2.get("default")) : ({});
            int _loc_5 =-1;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_6 = _loc_2.get(i0);

                if (_loc_3 >= int(_loc_6.get("levelRequired")) && int(_loc_6.get("levelRequired")) > _loc_5)
                {
                    _loc_4 = _loc_6;
                    _loc_5 = int(_loc_6.get("levelRequired"));
                }
            }
            return _loc_4;
        }//end

        public static boolean  isUsingResource (String param1 )
        {
            return !areHuntersRetired(param1);
        }//end

        public static boolean  areHuntersRetired (String param1 )
        {
            _loc_2 = getHunterPreyMode(param1);
            if (int(_loc_2.get("huntersRetired")) == 1 || String(_loc_2.get("huntersRetired")) == "true")
            {
                return true;
            }
            return false;
        }//end

        public static String  getHubResourceToolTipAction (String param1 )
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_2 ="";
            if (param1 !=null)
            {
                _loc_3 = Global.gameSettings().getPreyGroupSettings(param1);
                _loc_4 = _loc_3.get("resourceInfo").get("toolTipActionPackage");
                _loc_5 = _loc_3.get("resourceInfo").get("toolTipActionKey");
                _loc_2 = ZLoc.t(_loc_4, _loc_5);
            }
            return _loc_2;
        }//end

        public static String  getHubResourceStagePick (String param1 )
        {
            _loc_2 =Global.gameSettings().getPreyGroupSettings(param1 );
            return _loc_2.get("resourceInfo").get("stagePickEffect");
        }//end

        public static void  onSpecialNPCChange (String param1 ,boolean param2 =false )
        {
            if (param2)
            {
                PreyManager.sunsetSpecialNPC(param1);
            }
            PreyManager.spawnSpecialLevelNPC(param1);
            if (areHuntersRetired(param1))
            {
                clearHunterNPCs(param1);
            }
            return;
        }//end

        public static void  celebrateHubUpgrade (String param1 ,Municipal param2 )
        {
            int numEffect ;
            int interval ;
            int effectsInstantiated ;
            groupId = param1;
            municipal = param2;


                numEffect = 100;
                interval = 10;
                MapResourceEffectFactory.createEffect(municipal, EffectType.FIREWORK_BALLOONS);
                Sounds.play("cruise_fireworks");
                effectsInstantiated = 0;
                while (effectsInstantiated <= numEffect)
                {

                    TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(municipal, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , effectsInstantiated * interval);
                    effectsInstantiated = (effectsInstantiated + 1);
                }

            return;
        }//end

        public static double  sortOnRequiredPoliceLevel (PreyData param1 ,PreyData param2 )
        {
            _loc_3 = param1.requiredHubLevel ;
            _loc_4 = param2.requiredHubLevel ;
            if (_loc_3 > _loc_4)
            {
                return 1;
            }
            if (_loc_3 < _loc_4)
            {
                return -1;
            }
            if (param1.id > param2.id)
            {
                return 1;
            }
            if (param1.id < param2.id)
            {
                return -1;
            }
            return 0;
        }//end

        public static Array  getPreyDefinitions (String param1 )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            Object _loc_7 =null ;
            _loc_2 =Global.gameSettings().getPreys(param1 );
            _loc_3 = new Array ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_5 = String(_loc_4.get("experimentName"));
                _loc_6 = int(_loc_4.get("experimentVariant"));
                if (_loc_5 == "" || Global.experimentManager.getVariant(_loc_5) == _loc_6)
                {
                    _loc_7 = new Object();
                    _loc_7.id = _loc_4.get("id");
                    _loc_3.push(PreyManager.getPreyData(param1, _loc_7));
                }
            }
            _loc_3.sort(sortOnRequiredPoliceLevel);
            return _loc_3;
        }//end

        public static void  processHarvest (MapResource param1 )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            PreyData _loc_9 =null ;
            _loc_2 = param1.getItem ();
            _loc_3 =Global.gameSettings().getAllPreyGroups ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = _loc_4.get("spawnsFrom").get("name");
                _loc_6 = _loc_5.split(",");
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_7 = _loc_6.get(i0);

                    if (_loc_2.getItemKeywords().indexOf(_loc_7) > -1 || _loc_7 == _loc_2.type)
                    {
                        _loc_8 = _loc_4.get("id");
                        _loc_9 = getPreyForHarvest(param1, _loc_8);
                        if (_loc_9 == null)
                        {
                            return;
                        }
                        addPreyWalker(_loc_9, param1);
                    }
                }
            }
            return;
        }//end

        public static PreyData  getPreyForHarvest (MapResource param1 ,String param2 )
        {
            Object _loc_5 =null ;
            if (!m_prey.get(param2) || !shouldSpawnPrey(param1, param2))
            {
                return null;
            }
            PreyData _loc_3 =null ;
            _loc_4 = m_preySpawned.get(param2)? (m_preySpawned.get(param2).length()) : (0);
            if (m_prey.get(param2) && m_prey.get(param2).get(_loc_4) != null)
            {
                _loc_5 = new Object();
                _loc_5.id = m_prey.get(param2).get(_loc_4);
                if (m_preySpawned.get(param2) == null)
                {
                    m_preySpawned.put(param2,  new Array());
                }
                m_preySpawned.get(param2).push(_loc_5.id);
                _loc_3 = PreyManager.getPreyData(param2, _loc_5);
            }
            return _loc_3;
        }//end

        public static boolean  shouldSpawnPrey (MapResource param1 ,String param2 )
        {
            if (Global.world.citySim.preyManager.areHubsLocked(param2))
            {
                return false;
            }
            _loc_3 =Global.gameSettings().getHubQueueInfo(param2 );
            _loc_4 = _loc_3.get("spawnChance") ;
            int _loc_5 =99;
            _loc_6 = param1.getItemName ()+" prey spawn";
            _loc_7 = SecureRand.randPerFeature(0,_loc_5,param2,_loc_6);
            if (SecureRand.randPerFeature(0, _loc_5, param2, _loc_6) < _loc_4)
            {
                return true;
            }
            return false;
        }//end

        public static void  addPreyWalker (PreyData param1 ,MapResource param2 )
        {
            Vector3 pos ;
            NPC prey ;
            Vector3 direction ;
            def = param1;
            building = param2;
            road = Global.world.citySim.roadManager.findClosestRoad(building.getPosition());
            if (road == null)
            {
                return;
            }
            hotspots = building.getHotspots();
            if (hotspots.length > 1)
            {
                direction = building.getHotspots().get(0).subtract(building.getHotspots().get(1)).normalize();
                pos = building.getHotspots().get(0).add(direction.scale(PreyManager.BANDIT_NUDGE));
            }
            else
            {
                pos = road.getHotspot();
            }
            prey = Global.world.citySim.npcManager.createWalkerByNameAtPosition(def.itemName, pos, false);
            prey.animation = "idle";
            prey.actionSelection = new BanditActionSelection(prey);
            prey .actionQueue .addActions (new ActionAnimationEffect (prey ,EffectType .POOF ),new ActionFn (prey ,void  ()
            {
                Sounds.play(def.spawnSound);
                return;
            }//end
            ),new ActionPause (prey ,2),new ActionFn (prey ,Curry .curry (void  (PreyData param11 ,NPC param21 )
            {
                _loc_3 = null;
                _loc_4 = null;
                _loc_5 = null;
                _loc_6 = null;
                if (!Global.player.getSeenFlag(def.groupId + "_Appeared"))
                {
                    _loc_3 = ZLoc.t("Dialogs", def.groupId + "_Appeared_message");
                    _loc_4 = def.groupId + "_Appeared";
                    _loc_5 = Global.gameSettings().getHubQueueInfo(def.groupId);
                    _loc_6 = new CharacterDialog(_loc_3, _loc_4, GenericDialogView.TYPE_OK, null, null, true, _loc_5.get("catchPreyFTUEAsset"), "Okay");
                    UI.displayPopup(_loc_6, true, "Prey Seen", false);
                    Global.player.setSeenFlag(def.groupId + "_Appeared");
                }
                prey.animation = "static";
                return;
            }//end
            , def, prey)));
            prey.slidePick = new PreySlidePick(prey, def);
            prey.showSlidePick();
            prey .playActionCallback =Curry .curry (void  (NPC param12 )
            {
                (prey.slidePick as PreySlidePick).preyClicked();
                return;
            }//end
            , prey);
            PreyUtil.logGameActionStats("active_building", def.groupId, "prey_npc_spawn", def);
            return;
        }//end

        public static NPC  addPatrol (String param1 ,MapResource param2 ,String param3 =null ,int param4 =-1)
        {
            NPC npc ;
            Array npcList ;
            int numNPCs ;
            Municipal startingStation ;
            Road roadStart ;
            Road roadEnd ;
            SoundObject loopsound ;
            groupId = param1;
            target = param2;
            npcType = param3;
            crewSlot = param4;
            stationLevel = PreyUtil.getHubLevel(groupId);
            mode = PreyManager.getHunterPreyMode(groupId);
            requiredLevel =(int)(mode.get( "levelRequired") );
            npc;
            HunterPreyWorkers workers ;
            HunterData hunterdata ;
            if (areHuntersRetired(groupId))
            {
                return null;
            }
            if (getNumActiveHuntersByGroup(groupId) >= getMaxActiveHuntersByGroup(groupId))
            {
                return null;
            }
            if (stationLevel >= requiredLevel && stationLevel > 0)
            {
                npcList = Global.gameSettings().getHubNPCS(groupId, stationLevel);
                numNPCs = npcList.length;
                workers =(HunterPreyWorkers) Global.world.citySim.preyManager.getWorkerManagerByGroup(groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET);
                hunterdata = workers.getHunterData(crewSlot);
                if (numNPCs > 0 || npcType != null)
                {
                    npcType = npcType ? (npcType) : (npcList.get(int(Math.random() * numNPCs)).get("id"));
                    startingStation = getBestHub(groupId);
                    switch(npcType)
                    {
                        case PreyManager.NPC_COP:
                        case "npc_dogcatcher":
                        {
                            npc = Global.world.citySim.npcManager.createWalkerByNameAtPosition(npcType, startingStation.getHotspot(), false);
                            npc.clearStates();
                            npc.getStateMachine().addActions(new ActionAnimationEffect(npc, EffectType.POOF));
                            npc.actionSelection = new CopNPCActionSelection(npc, target);
                            break;
                        }
                        case PreyManager.NPC_POLICECRUISER:
                        case PreyManager.NPC_POLICESPORTCRUISER:
                        case PreyManager.NPC_POLICEVAN:
                        {
                            npc = new Vehicle(npcType, false);
                            roadStart = Global.world.citySim.roadManager.findClosestRoad(startingStation.getPosition());
                            if (target)
                            {
                                roadEnd = Global.world.citySim.roadManager.findClosestRoad(target.getPosition());
                            }
                            else
                            {
                                roadEnd = Global.world.citySim.roadManager.findRandomRoadNearEdges(roadStart);
                            }
                            if (!roadStart && !roadEnd)
                            {
                                GlobalEngine.info("PreyManager", "Darn, no road(s) available for the cop cruiser");
                                npc.cleanUp();
                                return addPatrol(groupId, target, PreyManager.NPC_COP, crewSlot);
                            }
                            if (!Global.world.citySim.roadManager.findPath(roadStart, roadEnd, RoadManager.PATH_ROAD_ONLY))
                            {
                                roadStart = Global.world.citySim.roadManager.findRandomRoadNearEdges(roadEnd);
                            }
                            if (!roadStart)
                            {
                                npc.cleanUp();
                                return addPatrol(groupId, target, PreyManager.NPC_COP, crewSlot);
                            }
                            npc.setPosition(roadStart.getHotspot().x, roadStart.getHotspot().y);
                            Global.world.citySim.npcManager.addVehicle((Vehicle)npc);
                            npc.clearStates();
                            npc .getStateMachine ().addActions (new ActionAnimationEffect (npc ,EffectType .VEHICLE_POOF ),new ActionFn (npc ,void  ()
            {
                Sounds.play("siren");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (npc ,roadEnd ,null ),new ActionFn (npc ,void  ()
            {
                Sounds.stop(loopsound);
                return;
            }//end
            ), new ActionFn(npc, Curry.curry(PreyManager.swapCarForHunter, PreyManager.NPC_COP, (Vehicle)npc, target, groupId)));
                            break;
                        }
                        default:
                        {
                            GlobalEngine.warning("PreyManager", "Unknown npc type: " + npcType);
                            break;
                            break;
                        }
                    }
                }
                else
                {
                    GlobalEngine.warning("PreyManager", "Patrol requested but no npc available or specified");
                }
            }
            else
            {
                GlobalEngine.warning("PreyManager", "Patrol requested when no hubs available");
            }
            if (npc)
            {
                addHunter(npc, groupId);
                npc .playActionCallback =Curry .curry (void  ()
            {
                return;
            }//end
            );
                if (hunterdata != null)
                {
                    npc.slidePick = new HunterSlidePick(groupId, npc, hunterdata);
                    npc .highlightCallback =void  (boolean param1 ,boolean param2 ,boolean param3 =true )
            {
                received = param1;
                stored = param2;
                showText = param3;
                if (received && !npc.isSlidePickShown())
                {
                    npc.showSlidePick();
                    if (showText)
                    {
                        (npc.slidePick as HunterSlidePick).showText();
                    }
                    else
                    {
                        (npc.slidePick as HunterSlidePick).hideText();
                    }
                    TimerUtil .callLater (void  ()
                {
                    npc.hideSlidePick(0.5);
                    return;
                }//end
                , 5000);
                }
                return;
            }//end
            ;
                    npc .imageLoadedCallback =Curry .curry (void  (NPC param11 )
            {
                param11.highlightCallback(true, false, false);
                param11.imageLoadedCallback = null;
                return;
            }//end
            , npc);
                }
                if (crewSlot != -1 && workers)
                {
                    hunterdata.setCopReference(npc);
                    workers.setHunterData(hunterdata);
                }
            }
            return npc;
        }//end

        public static NPC  swapCarForHunter (String param1 ,Vehicle param2 ,MapResource param3 ,String param4 )
        {
            Array _loc_11 =null ;
            _loc_5 = getIndexOfHunter(param2,param4);
            _loc_6 = param2.getPosition ();
            _loc_7 = param2.getItem().name;
            _loc_8 =Global.world.citySim.npcManager.createWalkerByNameAtPosition(param1 ,_loc_6 ,false );
            Global.world.citySim.npcManager.createWalkerByNameAtPosition(param1, _loc_6, false).clearStates();
            _loc_8.getStateMachine().addActions(new ActionAnimationEffect(_loc_8, EffectType.POOF));
            _loc_8.actionSelection = new CopNPCActionSelection(_loc_8, param3);
            _loc_8.slidePick = param2.slidePick;
            _loc_8.highlightCallback = param2.highlightCallback;
            if (_loc_8.slidePick)
            {
                (_loc_8.slidePick as HunterSlidePick).owner = _loc_8;
            }
            _loc_9 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param4 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            _loc_10 =Global(.world.citySim.preyManager.getWorkerManagerByGroup(param4 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ).getNpcOwner(param2 );
            if ((Global.world.citySim.preyManager.getWorkerManagerByGroup(param4).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET) as HunterPreyWorkers).getNpcOwner(param2))
            {
                _loc_9.setNpcOwner(_loc_10.getPosition(), _loc_8);
            }
            Global.world.citySim.npcManager.removeVehicle(param2);
            if (_loc_5 > -1)
            {
                ((Array)m_hunters.get(param4)).splice(_loc_5, 1, _loc_8);
                _loc_11 = new Array();
            }
            return _loc_8;
        }//end

        public static NPC  getSpecialNPC (String param1 )
        {
            NPC _loc_2 =null ;
            if (m_specialNPCs && param1 != null)
            {
                _loc_2 = m_specialNPCs.get(param1);
            }
            return _loc_2;
        }//end

        public static void  setSpecialNPC (String param1 ,NPC param2 )
        {
            if (!m_specialNPCs)
            {
                m_specialNPCs = new Dictionary();
            }
            m_specialNPCs.put(param1,  param2);
            return;
        }//end

        public static void  spawnSpecialLevelNPC (String param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            String _loc_4 =null ;
            if (!getSpecialNPC(param1))
            {
                _loc_2 = PreyUtil.getHubLevel(param1);
                _loc_3 = Global.gameSettings().getHubByLevel(param1, _loc_2);
                if (_loc_3)
                {
                    _loc_4 = _loc_3.get("specialNpc");
                    switch(_loc_4)
                    {
                        case NPC_POLICEHELICOPTER:
                        {
                            setSpecialNPC(param1, createHelicopter(param1, false, true));
                            break;
                        }
                        case NPC_SUPERHERO:
                        {
                            break;
                        }
                        default:
                        {
                            return;
                            break;
                        }
                    }
                }
            }
            return;
        }//end

        public static NPC  createHelicopter (String param1 ,boolean param2 ,boolean param3 =false )
        {
            Vector3 _loc_6 =null ;
            Municipal _loc_7 =null ;
            _loc_4 = getSpecialNPC(param1);
            if (getSpecialNPC(param1) && _loc_4 instanceof Helicopter)
            {
                return (Helicopter)_loc_4;
            }
            _loc_5 = new Helicopter(PreyManager.NPC_POLICEHELICOPTER ,false );
            (new Helicopter(PreyManager.NPC_POLICEHELICOPTER, false).actionSelection as CopHelicopterActionSelection).groupId = param1;
            if (param3)
            {
                _loc_7 = PreyUtil.getHubStationClosestToCenter(param1);
                if (_loc_7)
                {
                    _loc_6 = new Vector3(_loc_7.positionX + 15, _loc_7.positionY - 15, 10);
                }
                else
                {
                    GlobalEngine.warning("PreyManager", "Created helicopter with no pads available");
                }
            }
            else
            {
                _loc_6 = getOffScreenFlyPosition();
            }
            _loc_5.setPosition(_loc_6.x, _loc_6.y, _loc_6.z);
            Global.world.citySim.npcManager.addVehicle(_loc_5);
            return _loc_5;
        }//end

        public static NPC  createSuperHero (String param1 ,boolean param2 ,boolean param3 =true )
        {
            NPC _loc_5 =null ;
            SuperHeroActionSelection _loc_6 =null ;
            if (getSpecialNPC(param1))
            {
                return getSpecialNPC(param1);
            }
            _loc_4 = PreyUtil.getHubStationClosestToCenter(param1);
            if (PreyUtil.getHubStationClosestToCenter(param1))
            {
                _loc_5 = Global.world.citySim.npcManager.createWalkerByNameAtPosition(NPC_PIGEONSWARM, new Vector3(0, 0, 0), false);
                _loc_6 = new SuperHeroActionSelection(_loc_5);
                _loc_6.groupId = param1;
                _loc_5.actionSelection = _loc_6;
                _loc_5.alpha = 0;
            }
            return _loc_5;
        }//end

        public static void  sunsetSpecialNPC (String param1 )
        {
            _loc_2 = getSpecialNPC(param1);
            if (_loc_2)
            {
                if (_loc_2.actionSelection instanceof CopHelicopterActionSelection)
                {
                    (_loc_2.actionSelection as CopHelicopterActionSelection).forceSunset();
                }
                else
                {
                    _loc_2.clearStates();
                    _loc_2.getStateMachine().addActions(new ActionDie(_loc_2));
                }
                cleanUpSpecialNPC(param1, _loc_2);
            }
            return;
        }//end

        public static void  cleanUpSpecialNPC (String param1 ,NPC param2 )
        {
            if (getSpecialNPC(param1) == param2)
            {
                setSpecialNPC(param1, null);
            }
            return;
        }//end

        public static boolean  isActiveSpecialNPC (String param1 ,NPC param2 )
        {
            if (param1 !=null)
            {
                return param2 == getSpecialNPC(param1);
            }
            return false;
        }//end

        public static void  sendHelicopter (String param1 ,Vector3 param2 ,boolean param3 )
        {
            _loc_4 = createHelicopter(param1,param3);
            if (createHelicopter(param1, param3))
            {
                if (!param3)
                {
                    setSpecialNPC(param1, _loc_4);
                }
                if (_loc_4 instanceof Helicopter)
                {
                    ((Helicopter)_loc_4).addScene(param2);
                }
                else if (_loc_4.actionSelection instanceof CopHelicopterActionSelection)
                {
                    (_loc_4.actionSelection as CopHelicopterActionSelection).addTargetScene(param2);
                    (_loc_4.actionSelection as CopHelicopterActionSelection).groupId = param1;
                }
            }
            return;
        }//end

        public static void  sendSuperHero (String param1 ,Vector3 param2 )
        {
            _loc_3 = createSuperHero(param1,false,true);
            setSpecialNPC(param1, _loc_3);
            (_loc_3.actionSelection as SuperHeroActionSelection).addTargetScene(param2);
            (_loc_3.actionSelection as SuperHeroActionSelection).groupId = param1;
            return;
        }//end

        public static NPC  pullHunterForCapture (String param1 ,String param2 ,Vector3 param3 =null ,boolean param4 =false )
        {
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            NPC _loc_11 =null ;
            HunterPreyWorkers _loc_12 =null ;
            Array _loc_13 =null ;
            double _loc_14 =0;
            HunterData _loc_15 =null ;
            NPC _loc_5 =null ;
            _loc_6 = getHunters(param1);
            if (param4 || !isUsingResource(param1))
            {
                _loc_5 = Global.world.citySim.npcManager.createWalkerByNameAtPosition(param2, param3, false);
                return _loc_5;
            }
            if (param3)
            {
                _loc_7 = 0;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_11 = _loc_6.get(i0);

                    if (_loc_11)
                    {
                        _loc_8 = _loc_11.positionX - param3.x;
                        _loc_9 = _loc_11.positionY - param3.y;
                        _loc_10 = _loc_8 * _loc_8 + _loc_9 * _loc_9;
                        if (_loc_10 < _loc_7 || _loc_5 == null)
                        {
                            _loc_5 = _loc_11;
                            _loc_7 = _loc_10;
                        }
                    }
                }
            }
            else if (_loc_6)
            {
                _loc_5 = _loc_6.get(int(Math.random() * _loc_6.length()));
            }
            if (_loc_5 instanceof Vehicle)
            {
                _loc_5 = PreyManager.swapCarForHunter(param2, (Vehicle)_loc_5, null, param1);
            }
            ArrayUtil.removeValueFromArray(_loc_6, _loc_5);
            if (!_loc_5)
            {
                _loc_5 = Global.world.citySim.npcManager.createWalkerByNameAtPosition(param2, param3, false);
                _loc_12 =(HunterPreyWorkers) Global.world.citySim.preyManager.getWorkerManagerByGroup(param1).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET);
                _loc_13 = _loc_12.getAllCopData();
                _loc_14 = getHunterSleepTime(param1);
                for(int i0 = 0; i0 < _loc_13.size(); i0++)
                {
                		_loc_15 = _loc_13.get(i0);

                    if ((_loc_15.getState() == HunterData.STATE_PATROLLING || _loc_15.getState() == HunterData.STATE_SLEEPING && _loc_15.getTimestamp() + _loc_14 <= Math.floor(GlobalEngine.getTimer() / 1000)) && _loc_15.getCopReference() == null)
                    {
                        _loc_12.setNpcOwner(_loc_15.getPosition(), _loc_5);
                        break;
                    }
                }
            }
            if (_loc_5)
            {
                _loc_5.clearStates();
            }
            else
            {
                throw new Error("Attempted to pull null NPC for capture");
            }
            return _loc_5;
        }//end

        public static void  clearHunterNPCs (String param1 )
        {
            NPC _loc_2 =null ;
            if (param1 && m_hunters.get(param1))
            {
                for(int i0 = 0; i0 < m_hunters.get(param1).size(); i0++)
                {
                		_loc_2 = m_hunters.get(param1).get(i0);

                    if (_loc_2)
                    {
                        _loc_2.clearStates();
                        _loc_2.animation = "idle";
                        _loc_2.getStateMachine().addActions(new ActionDie(_loc_2));
                    }
                }
                m_hunters.put(param1,  new Array());
            }
            return;
        }//end

        public static void  clearAllHunterNPCs ()
        {
            String _loc_2 =null ;
            _loc_1 = DictionaryUtil.getKeys(m_hunters);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                clearHunterNPCs(_loc_2);
            }
            return;
        }//end

        public static CaptureScene  createCaptureScene (PreyData param1 ,Vector3 param2 ,NPC param3 =null ,boolean param4 =false )
        {
            CaptureScene _loc_7 =null ;
            _loc_5 = param1.captureScene ;
            _loc_6 = (Class)getDefinitionByName(_loc_5)
            if (getDefinitionByName(_loc_5) as Class)
            {
                _loc_7 =(CaptureScene) new _loc_6(param1, param2, param4);
            }
            else
            {
                _loc_7 = new ArrestScene(param1, param2, param4);
            }
            _loc_7.target = param3;
            _loc_7.setOuter(Global.world);
            _loc_7.attach();
            _loc_7.init();
            m_scenes.push(_loc_7);
            return _loc_7;
        }//end

        public static void  removeCaptureScene (CaptureScene param1 )
        {
            if (!ArrayUtil.arrayContainsValue(m_scenes, param1))
            {
                return;
            }
            ArrayUtil.removeValueFromArray(m_scenes, param1);
            param1.shutdown();
            param1.detach();
            param1.cleanUp();
            return;
        }//end

        public static Array  scenes ()
        {
            return m_scenes;
        }//end

        public static int  getNumPatrollingCops (String param1 )
        {
            HunterData _loc_5 =null ;
            int _loc_2 =0;
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param1 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            _loc_4 = _loc_3.getAllCopData ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (_loc_5.getState() == HunterData.STATE_PATROLLING)
                {
                    _loc_2++;
                }
            }
            return _loc_2;
        }//end

        public static int  getNumWalkerCops (String param1 )
        {
            if (m_hunters.get(param1))
            {
                return m_hunters.get(param1).length;
            }
            return 0;
        }//end

        public static int  getNumSleepingHunters (String param1 )
        {
            Array _loc_4 =null ;
            HunterData _loc_5 =null ;
            int _loc_2 =0;
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param1 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            if (_loc_3)
            {
                _loc_4 = _loc_3.getAllCopData();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    if (_loc_5.getState() == HunterData.STATE_SLEEPING)
                    {
                        _loc_2++;
                    }
                }
            }
            return _loc_2;
        }//end

        public static int  getNumUsableCops (String param1 )
        {
            Array _loc_5 =null ;
            HunterData _loc_6 =null ;
            int _loc_2 =0;
            _loc_3 = getHunterSleepTime(param1);
            _loc_4 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param1 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            if (Global.world.citySim.preyManager.getWorkerManagerByGroup(param1).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET) as HunterPreyWorkers)
            {
                _loc_5 = _loc_4.getAllCopData();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    if (_loc_6.getState() == HunterData.STATE_PATROLLING || _loc_6.getState() == HunterData.STATE_SLEEPING && _loc_6.getTimestamp() + _loc_3 <= Math.floor(GlobalEngine.getTimer() / 1000))
                    {
                        _loc_2++;
                    }
                }
            }
            return _loc_2;
        }//end

        public static Municipal  getBestHub (String param1 )
        {
            Array _loc_5 =null ;
            Municipal _loc_6 =null ;
            RoadGraph _loc_7 =null ;
            Road _loc_8 =null ;
            double _loc_9 =0;
            Municipal _loc_2 =null ;
            double _loc_3 =0;
            _loc_4 = PreyUtil.getHubLevel(param1);
            if (PreyUtil.getHubLevel(param1) > 0)
            {
                _loc_5 = PreyUtil.getHubsByLevel(param1, _loc_4);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 = Global.world.citySim.roadManager.getConnectedRoadGraph(_loc_6);
                    _loc_8 = Global.world.citySim.roadManager.findClosestRoad(_loc_6.getPosition());
                    if (_loc_7)
                    {
                        return _loc_6;
                    }
                    if (_loc_8)
                    {
                        _loc_9 = MathUtil.distance(new Point(_loc_8.positionX, _loc_8.positionY), new Point(_loc_6.positionX, _loc_6.positionY));
                        if (_loc_9 < _loc_3 || _loc_2 == null)
                        {
                            _loc_3 = _loc_9;
                            _loc_2 = _loc_6;
                        }
                    }
                }
                if (!_loc_2)
                {
                    _loc_2 = _loc_5.get(0);
                }
            }
            return _loc_2;
        }//end

        public static Vector3  getOffScreenFlyPosition ()
        {
            _loc_1 =Global.stage.y ;
            _loc_2 =Global.ui.screenHeight /2;
            _loc_3 =Global.stage.x ;
            _loc_4 =Global.ui.screenWidth ;
            _loc_5 =Global.ui.screenWidth +_loc_3 +20;
            _loc_6 = _loc_1+_loc_2;
            _loc_7 = IsoMath.screenPosToTilePos(_loc_5 ,_loc_6 );
            return new Vector3(_loc_7.x, _loc_7.y, 10);
        }//end

        public static boolean  upgradeAllHubs (String param1 ,int param2 =0,String param3 ="")
        {
            Municipal _loc_5 =null ;
            Array _loc_6 =null ;
            Array _loc_7 =null ;
            if (param2 == 0 && !canUpgradeAllHubs(param1))
            {
                return false;
            }
            _loc_4 = PreyUtil.getHubsByGroup(param1);
            if (param2 == 0)
            {
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_5.upgradeBuildingIfPossible();
                }
            }
            else
            {
                _loc_6 = new Array();
                _loc_7 = new Array();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_6.push(_loc_5);
                    if (param3 != "")
                    {
                        _loc_7.push(param3);
                        continue;
                    }
                    _loc_7.push(_loc_5.getItem().upgrade.newItemName);
                }
                GameTransactionManager.addTransaction(new TBuyUpgradeBuildings(_loc_6, _loc_7, param2));
            }
            Global.world.citySim.preyManager.setHubUnlockLevel(PreyUtil.getHubLevel(param1), param1);
            return true;
        }//end

        public static void  upgradeCatchUp (String param1 ,Municipal param2 )
        {
            _loc_3 = Math.max(PreyUtil.getHubLevel(param1 ),Global.world.citySim.preyManager.getHubUnlockLevel(param1 ));
            _loc_4 = PreyUtil.getHubInstanceLevel(param1,param2);
            if (_loc_3 > 0 && _loc_4 < _loc_3)
            {
                GameTransactionManager.addTransaction(new TUpgradeCatchUp(param2));
            }
            return;
        }//end

        public static boolean  canUpgradeAllHubs (String param1 )
        {
            Municipal _loc_3 =null ;
            _loc_2 = PreyUtil.getHubsByGroup(param1);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (!_loc_3.isUpgradePossible())
                {
                    return false;
                }
            }
            return true;
        }//end

        public static boolean  canUpgradeAllHubsOfGroup (String param1 )
        {
            return canUpgradeAllHubs(param1);
        }//end

        public static double  getHunterSleepTime (String param1 )
        {
            _loc_2 =Global.gameSettings().getHubQueueInfo(param1 );
            return _loc_2.get("hunterSleepTime");
        }//end

        public static void  spawnExistingHunters (boolean param1 =false ,String param2 )
        {
            Array _loc_3 =null ;
            String _loc_4 =null ;
            if (param2 != null)
            {
                spawnExistingHunterForGroup(param2, param1);
            }
            else
            {
                _loc_3 = Global.gameSettings().getAllHubGroupIds();
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    spawnExistingHunterForGroup(_loc_4, param1);
                }
            }
            return;
        }//end

        private static void  spawnExistingHunterForGroup (String param1 ,boolean param2 =false )
        {
            Array _loc_4 =null ;
            HunterData _loc_5 =null ;
            if (param2)
            {
                m_hunters.put(param1,  new Array());
                setSpecialNPC(param1, null);
            }
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param1 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            if (_loc_3)
            {
                _loc_4 = _loc_3.getAllCopData();
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    if (param2)
                    {
                        _loc_5.setCopReference(null);
                    }
                    if (getNumPatrollingCops(param1) > getNumWalkerCops(param1))
                    {
                        if ((_loc_5.getState() == HunterData.STATE_PATROLLING || _loc_5.getState() == HunterData.STATE_SLEEPING && _loc_5.getTimestamp() + getHunterSleepTime(param1) < Math.floor(GlobalEngine.getTimer() / 1000)) && _loc_5.getCopReference() == null)
                        {
                            PreyManager.addPatrol(param1, null, null, _loc_5.m_position);
                        }
                        continue;
                    }
                    break;
                }
            }
            onSpecialNPCChange(param1, false);
            return;
        }//end

        public static int  getNumPreyUntilUpgrade (String param1 )
        {
            UpgradeDefinition _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 = PreyUtil.getHubLevel(param1);
            if (_loc_2 > 0)
            {
                _loc_3 = UpgradeDefinition.getNextUpgrade(Global.gameSettings().getHubName(param1, _loc_2));
                if (_loc_3)
                {
                    _loc_4 = int(_loc_3.requirements.getRequirementValue(Requirements.REQUIREMENT_SPECIAL_PREY));
                    return Math.max(0, _loc_4 - m_numPreyCaptured.get(param1));
                }
                return -1;
            }
            return -1;
        }//end

        public static int  findResourceRequiredHunter (String param1 )
        {
            HunterData _loc_5 =null ;
            int _loc_2 =-1;
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param1 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            _loc_4 = _loc_3.getAllCopData ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (_loc_5.getState() == HunterData.STATE_SLEEPING)
                {
                    _loc_2 = _loc_5.getPosition();
                    break;
                }
            }
            return _loc_2;
        }//end

        public static String  giveResourceAndWake (int param1 ,String param2 )
        {
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(param2 ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            _loc_4 = _loc_3.getHunterData(param1 );
            _loc_3.getHunterData(param1).setState(HunterData.STATE_PATROLLING);
            _loc_4.setTimestamp(Math.floor(GlobalEngine.getTimer() / 1000));
            _loc_3.setHunterData(_loc_4);
            return _loc_4.getState();
        }//end

    }


