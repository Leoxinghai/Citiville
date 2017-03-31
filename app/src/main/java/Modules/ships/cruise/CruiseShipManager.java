package Modules.ships.cruise;

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
import Engine.Helpers.*;
import Classes.sim.*;

    public class CruiseShipManager implements IGameWorldUpdateObserver
    {
        protected Array m_cheeringNpcs ;
        protected double m_lastSpawnTime =0;
        protected Array m_leftToSpawn ;
public static  int CHEERING_NPC_COUNT =10;
public static  int TOURIST_SPAWN_DELAY =200;
public static  int MAX_DOOBERS =10;

        public  CruiseShipManager (GameWorld param1 )
        {
            this.m_cheeringNpcs = new Array();
            this.m_leftToSpawn = new Array();
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            this.removeCheeringScene();
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            HarvestableShip _loc_7 =null ;
            if (!param1.hasOwnProperty("featureData") || !param1.featureData.hasOwnProperty("cruiseShips"))
            {
                return;
            }
            _loc_2 = param1.featureData.cruiseShips ;
            _loc_3 = _loc_2.tourists ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = int(_loc_3.get(_loc_4));
                _loc_6 = int(String(_loc_4).substr(1));
                _loc_7 =(HarvestableShip) Global.world.getObjectById(_loc_6);
                if (_loc_7 != null)
                {
                    if (_loc_7.getState() != HarvestableResource.STATE_GROWN)
                    {
                        this.releasePeeps(_loc_5, _loc_7);
                    }
                }
            }
            this.checkCheeringScenes();
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  update (double param1 )
        {
            if (this.m_leftToSpawn.length > 0)
            {
                param1 = GlobalEngine.serverTime - this.m_lastSpawnTime;
                if (param1 > TOURIST_SPAWN_DELAY)
                {
                    this.m_lastSpawnTime = GlobalEngine.serverTime;
                    this.spawnNextTourist();
                }
            }
            return;
        }//end

        public void  checkCheeringScenes ()
        {
            return;
        }//end

        public void  createCheeringScene ()
        {
            return;
        }//end

        public boolean  isCheeringSceneActive ()
        {
            return this.m_cheeringNpcs.length > 0;
        }//end

        public void  removeCheeringScene ()
        {
            Peep _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_cheeringNpcs.size(); i0++) 
            {
            		_loc_1 = this.m_cheeringNpcs.get(i0);

                _loc_1.actionQueue.removeAllStates();
                _loc_1.actionQueue.addActions(new ActionPlayAnimation(_loc_1, "cheer", Math.random()), new ActionEnableFreedom(_loc_1, true, true));
            }
            this.m_cheeringNpcs = new Array();
            return;
        }//end

        protected void  createCheeringSceneAroundDockBuilding (MapResource param1 )
        {
            Vector3 _loc_3 =null ;
            Peep _loc_4 =null ;
            int _loc_2 =0;
            while (_loc_2 < CHEERING_NPC_COUNT)
            {

                _loc_3 = this.createPointAroundDockEntrance(param1);
                _loc_4 = Global.world.citySim.npcManager.createPeepWalkerAtPosition(_loc_3, false);
                _loc_4.spawnSource = Peep.SOURCE_TOURBUS;
                _loc_4.isFreeAgent = false;
                _loc_4.actionQueue.addActions(new ActionPlayAnimation(_loc_4, "cheer", 10000));
                this.m_cheeringNpcs.push(_loc_4);
                _loc_2++;
            }
            return;
        }//end

        protected Vector3  createPointAroundDockEntrance (MapResource param1 )
        {
            _loc_2 = CruiseShipUtil.getRoadsideDockEntrance(param1);
            Vector3 _loc_3 =new Vector3(_loc_2.x +3+Math.random ()*2,_loc_2.y +(Math.random ()-0.5)*5);
            return _loc_3;
        }//end

        public void  releasePeeps (int param1 ,HarvestableShip param2 )
        {
            int _loc_3 =0;
            Array _loc_4 =new Array(param1 );
            _loc_5 = param2!= null ? (param2.getItem()) : (null);
            _loc_3 = 0;
            while (_loc_3 < param1)
            {

                _loc_4.put(_loc_3,  new TouristDef(param2));
                _loc_3++;
            }
            if (_loc_5 != null && _loc_5.getRandomSpawnNpc() != null)
            {
                _loc_3 = 0;
                while (_loc_3 < param1)
                {

                    (_loc_4.get(_loc_3) as TouristDef).name = _loc_5.getRandomSpawnNpc();
                    _loc_3++;
                }
            }
            this.m_leftToSpawn = this.m_leftToSpawn.concat(_loc_4);
            return;
        }//end

        protected void  spawnNextTourist ()
        {
            DesirePeep tourist ;
            def = this.m_leftToSpawn.shift();
            building = CruiseShipUtil.getDockBuilding();
            entrance = CruiseShipUtil.getRoadsideDockEntrance(building);
            Array desires ;
            tourist = Global.world.citySim.npcManager.createDesireWalkerAtPosition(entrance, desires, def.name, true);
            tourist.spawnSource = Peep.SOURCE_CRUISESHIP;
            tourist.setResistThresholdByType(DesireTypes.GO_CRUISE_SHIP, Math.max(2, Math.ceil(Math.random() * 4)));
            pos = this.createPointAroundDockEntrance(building);
            tourist .getStateMachine ().addActions (new ActionFn (tourist ,void  ()
            {
                _loc_1 =Global.getAssetURL("assets/citysim/looking_to_spend_npc_bubble.png");
                tourist.queueFeedbackBubble(_loc_1, 8);
                return;
            }//end
            ));
            tourist.actionQueue.addActions(new ActionNavigateBeeline(tourist, pos));
            road = Global.world.citySim.roadManager.findClosestRoad(pos);
            if (road != null)
            {
                tourist.actionQueue.addActions(new ActionNavigate(tourist, road, null).setFallbackAction(new ActionNavigateBeeline(tourist, road.getPosition())));
            }
            return;
        }//end

    }
import Classes.*;
import Engine.Helpers.*;
class TouristDef
    public Vector3 shipLocation ;
    public String name ;

     TouristDef (HarvestableShip param1 )
    {
        this.shipLocation = param1.getPosition();
        return;
    }//end





