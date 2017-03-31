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
import Classes.effects.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;

//import flash.display.*;
//import flash.utils.*;

    public class Mall extends MechanicMapResource implements IMerchant, ISlottedContainer
    {
        protected MerchantCrowdManager m_crowdManager =null ;
        protected String m_peepState ="PeepStateBlock";
        protected int m_peepsInFlight ;
        protected int m_requiredCustomers =0;
        protected CoinPickEffect m_coinPickEffect =null ;
        protected TourBus m_tourBus ;
        protected Dictionary m_levelToMysteryItemMap ;
public static  String PEEP_STATE_SPAWN ="PeepStateSpawning";
public static  String PEEP_STATE_BLOCK ="PeepStateBlock";
        private static int intTourBusNPCCount =0;

        public  Mall (String param1 )
        {
            super(param1);
            this.m_crowdManager = new MerchantCrowdManager(this);
            m_objectType = WorldObjectTypes.MALL;
            m_isRoadVerifiable = true;
            this.m_peepState = PEEP_STATE_BLOCK;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            ItemInstance _loc_4 =null ;
            super.loadObject(param1);
            mechanicData.put("slots", newArray());
            /*
            if (param1.mechanicData.slots && param1.mechanicData.slots != NOT_INITIALIZED_MECHANIC)
            {
                while (_loc_2 < param1.mechanicData.slots.length())
                {

                    _loc_3 = param1.mechanicData.slots.get(_loc_2) ? (param1.mechanicData.slots.get(_loc_2)) : (null);
                    if (_loc_3)
                    {
                        _loc_4 =(ItemInstance) Global.world.loadObjectInstance(_loc_3);
                        _loc_4.loadObject(_loc_3);
                        ((Array)mechanicData.get("slots")).push(_loc_4);
                    }
                    _loc_2++;
                }
            }
            if (param1.mechanicData.harvestState)
            {
                mechanicData.put("harvestState",  param1.mechanicData.harvestState);
            }
            */

            if (this.isOpen())
            {
                this.updatePeepSpawning();
            }
            return;
        }//end

         public void  sell ()
        {
            if (this.canFinishSell())
            {
                super.sell();
            }
            else
            {
                UI.displayMessage(ZLoc.t("Main", "CannotSellStorageMall", {item:getItemFriendlyName()}));
            }
            return;
        }//end

        private boolean  canFinishSell ()
        {
            _loc_1 =(Array) mechanicData.get("slots");
            return !_loc_1 || _loc_1.length == 0;
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            PositionedObject _loc_5 =null ;
            super.setPosition(param1, param2, param3);
            _loc_4 = this.slots;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_5.setPosition(param1, param2, param3);
            }
            return;
        }//end

        public void  updatePeepSpawning ()
        {
            int _loc_5 =0;
            Object _loc_6 =null ;
            _loc_1 = this.m_peepState==PEEP_STATE_SPAWN;
            _loc_2 = this.m_peepState==PEEP_STATE_BLOCK;
            _loc_3 = this.isAcceptingVisits();
            _loc_4 =             !_loc_3;
            if (_loc_3 && !isNeedingRoad)
            {
                _loc_5 = 0;
                _loc_6 = mechanicData.get("harvestState");
                if (_loc_6.hasOwnProperty("customers") && _loc_6.hasOwnProperty("customersReq") && this.m_requiredCustomers == 0)
                {
                    this.m_requiredCustomers = _loc_6.get("customersReq") - _loc_6.get("customers");
                    _loc_5 = this.m_requiredCustomers;
                }
                Global.world.citySim.npcManager.startSpawningBusinessPeeps(_loc_5);
            }
            if (_loc_4 || isNeedingRoad)
            {
            }
            if (_loc_4 && _loc_1 || isNeedingRoad)
            {
                Global.world.citySim.npcManager.onBusinessClosed();
                this.m_requiredCustomers = 0;
            }
            this.m_peepState = PEEP_STATE_BLOCK;
            if (_loc_3)
            {
                this.m_peepState = PEEP_STATE_SPAWN;
            }
            return;
        }//end

        public Object  visits ()
        {
            Object _loc_2 =null ;
            Object _loc_1 =null ;
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.hasOwnProperty("customers") && _loc_2.hasOwnProperty("customersReq"))
                {
                    _loc_1 = {customers:_loc_2.get("customers"), customersReq:_loc_2.get("customersReq")};
                }
            }
            return _loc_1;
        }//end

         public boolean  isOpen ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.visits;
            if (_loc_2)
            {
                _loc_1 = _loc_2.customers < _loc_2.customersReq;
            }
            return _loc_1;
        }//end

        public boolean  isRouteable ()
        {
            boolean _loc_1 =false ;
            if (this.isOpen() && !isNeedingRoad && this.getPopularity())
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public boolean  hasHotspot ()
        {
            boolean _loc_1 =false ;
            if (getHotspot() != null)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public MapResource  getMapResource ()
        {
            return this;
        }//end

        public void  makePeepEnterTarget (Peep param1 )
        {
            this.m_crowdManager.makeNpcEnterMerchant(param1);
            return;
        }//end

        public MerchantCrowdManager  crowdManager ()
        {
            return this.m_crowdManager;
        }//end

        public boolean  isAcceptingVisits ()
        {
            Object _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            _loc_1 = this.isRouteable();
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_PEEP_SPAWNING);
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3);
            _loc_4 = ExperimentDefinitions.PEEP_SPAWN_PLANNED==_loc_2;
            _loc_5 = ExperimentDefinitions.OPTIMIZE_FRAMERATE_Q3_ENABLED==_loc_3;
            if (_loc_4 && _loc_5)
            {
                _loc_6 = this.visits;
                if (_loc_1 && _loc_6)
                {
                    _loc_7 = _loc_6.customers + this.m_peepsInFlight;
                    _loc_8 = _loc_6.customersReq + Global.gameSettings().getNumber("merchantPeepsInFlightExceedMax", 0);
                    _loc_1 = _loc_7 < _loc_8;
                }
            }
            return _loc_1;
        }//end

        public void  performVisit (Peep param1 )
        {
            return;
        }//end

        public void  planVisit (Peep param1 )
        {
            Object visits ;
            double visitsRequired ;
            double recoveryDelay ;
            double recoveryDelayMs ;
            peep = param1;
            this.m_peepsInFlight++;
            if (!this.isAcceptingVisits())
            {
                this.updatePeepSpawning();
                visits = this.visits;
                visitsRequired;
                if (visits)
                {
                    visitsRequired = visits.customersReq;
                }
                recoveryDelay = Global.gameSettings().getNumber("merchantPeepsInFlightWaitTimeMin", 60);
                recoveryDelay = Math.max(visitsRequired, recoveryDelay);
                recoveryDelayMs = 1000 * recoveryDelay;
                recoveryDelayMs = recoveryDelayMs * Global.gameSettings().getNumber("merchantPeepsInFlightWaitTimeOut", 15);
                TimerUtil .callLater (void  ()
            {
                if (isOpen())
                {
                    m_peepsInFlight = 0;
                    updatePeepSpawning();
                }
                return;
            }//end
            , recoveryDelayMs);
            }
            return;
        }//end

         public int  getPopularity ()
        {
            MapResource _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.slots;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.getPopularity();
            }
            return _loc_1;
        }//end

         public boolean  isVisitorInteractable ()
        {
            _loc_1 = getMechanicData();
            if (isNeedingRoad || isPendingOrder || _loc_1 == null || _loc_1.get("harvestState") == null)
            {
                return false;
            }
            return true;
        }//end

        public Array  slots ()
        {
            _loc_1 = getMechanicData();
            if (_loc_1 == null || _loc_1.get("slots") == null)
            {
                _loc_1.put("slots", newArray());
            }
            return _loc_1.get("slots");
        }//end

        public void  slots (Array param1 )
        {
            _loc_2 = getMechanicData();
            _loc_2.put("slots",  param1);
            return;
        }//end

        public MapResource  mapResource ()
        {
            return (MapResource)this;
        }//end

        public MechanicMapResource  mechanicMapResource ()
        {
            return (MechanicMapResource)this;
        }//end

        public void  onStoreSlotObject (MapResource param1 )
        {
            return;
        }//end

        public void  onRemoveSlotObject (MapResource param1 )
        {
            return;
        }//end

        public Dictionary  visitors ()
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            Dictionary _loc_1 =new Dictionary ();
            _loc_2 = getMechanicData();
            if (_loc_2 == null || _loc_2.get("harvestState") == null)
            {
                return null;
            }
            _loc_3 = _loc_2.get("harvestState").get( "visitors") ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = _loc_4;
                if (_loc_5.indexOf("i") >= 0)
                {
                    _loc_5 = _loc_5.substr(1);
                }
                _loc_1.put(_loc_5,  _loc_3.get(_loc_4));
            }
            return _loc_1;
        }//end

        public void  visitors (Dictionary param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (mechanicData == null || mechanicData.get("harvestState") == null)
            {
                return;
            }
            Object _loc_2 =new Object ();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_4 = param1.get(_loc_3);
                _loc_2.put("i" + _loc_3,  _loc_4);
            }
            mechanicData.get("harvestState").put("visitors",  _loc_2);
            return;
        }//end

         public String  getToolTipStatus ()
        {
            Object _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            String _loc_1 =null ;
            if (isNeedingRoad)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
                return _loc_1;
            }
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.get("customers") != null && _loc_2.get("customersReq") != null && _loc_2.get("resources"))
                {
                    _loc_3 = _loc_2.get("customers");
                    _loc_4 = _loc_2.get("customersReq");
                    _loc_1 = ZLoc.t("Main", "BusinessCustomers", {served:_loc_3, max:_loc_4});
                    return _loc_1;
                }
            }
            return super.getToolTipStatus();
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.reloadImage();
            super.onObjectDrop(param1);
            if (!isNeedingRoad)
            {
                this.updatePeepSpawning();
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.m_crowdManager.onUpdate(param1);
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            this.updatePeepSpawning();
            this.updateEffects();
            return;
        }//end

        public String  getMysteryItemName (int param1 )
        {
            return this.levelToMysteryItemMap.get(param1);
        }//end

        public Dictionary  levelToMysteryItemMap ()
        {
            Item _loc_1 =null ;
            String _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            Item _loc_5 =null ;
            String _loc_6 =null ;
            if (this.m_levelToMysteryItemMap == null)
            {
                this.m_levelToMysteryItemMap = new Dictionary();
                _loc_1 = getItem();
                _loc_2 = Item.findUpgradeRoot(_loc_1);
                _loc_3 = UpgradeDefinition.getFullUpgradeChain(_loc_2);
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length())
                {

                    _loc_5 = _loc_3.get(_loc_4);
                    _loc_6 = _loc_5.mysteryItem;
                    this.m_levelToMysteryItemMap.put(_loc_4,  _loc_6);
                    _loc_4++;
                }
            }
            return this.m_levelToMysteryItemMap;
        }//end

        public boolean  hasMysteryItemInInventory (int param1 )
        {
            _loc_2 = this.getMysteryItemName(param1);
            return Global.player.inventory.getItemCountByName(_loc_2) > 0;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2);
            this.updatePeepSpawning();
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "mall", "upgrade_from", param1.name);
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "mall", "upgrade_to", param2.name);
            return;
        }//end

        public void  onWaveFinished ()
        {
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            transaction = param1;
            superReturn = super.onVisitReplayAction(transaction);
            GameTransactionManager.addTransaction(transaction);
            Global .world .citySim .tourBusManager .addTourBus (this ,void  ()
            {
                return;
            }//end
            );
            return superReturn;
        }//end

        public int  getInitialHarvestBonus (int param1 )
        {
            HarvestBonus _loc_7 =null ;
            _loc_2 = Item.findUpgradeRoot(getItem());
            _loc_3 = UpgradeDefinition.getFullUpgradeChain(_loc_2);
            if (_loc_3 == null || param1 >= _loc_3.length())
            {
                return 0;
            }
            _loc_4 = _loc_3.get(param1);
            _loc_5 = _loc_3.get(param1).harvestBonuses ;
            int _loc_6 =0;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_7 = _loc_5.get(i0);

                _loc_6 = _loc_6 + _loc_7.initialPercentModifier;
            }
            return _loc_6;
        }//end

        protected void  createCoinPickEffect ()
        {
            if (!this.m_coinPickEffect)
            {
                this.m_coinPickEffect =(CoinPickEffect) MapResourceEffectFactory.createEffect(this, EffectType.COIN_PICK);
                this.m_coinPickEffect.setPickType(CoinPickEffect.PICK_1);
            }
            else
            {
                this.m_coinPickEffect.setPickType(CoinPickEffect.PICK_1);
                this.m_coinPickEffect.reattach();
            }
            return;
        }//end

         public void  cleanUp ()
        {
            if (this.m_coinPickEffect)
            {
                this.m_coinPickEffect.cleanUp();
            }
            super.cleanUp();
            return;
        }//end

         protected void  updateArrow ()
        {
            super.updateArrow();
            if (isNeedingRoad || isUxLockedByQuests)
            {
                if (this.m_coinPickEffect)
                {
                    this.m_coinPickEffect.cleanUp();
                    this.m_coinPickEffect = null;
                }
                return;
            }
            if (!Global.isVisiting() && hasValidId())
            {
                this.createCoinPickEffect();
            }
            return;
        }//end

        protected void  updateEffects ()
        {
            if (!isNeedingRoad && !isUxLockedByQuests)
            {
                this.createCoinPickEffect();
                return;
            }
            if (this.m_coinPickEffect)
            {
                this.m_coinPickEffect.cleanUp();
                this.m_coinPickEffect = null;
            }
            return;
        }//end

         public void  replaceContent (DisplayObject param1 )
        {
            super.replaceContent(param1);
            this.updateEffects();
            return;
        }//end

        protected void  unloadTourists ()
        {
            Global.world.citySim.npcManager.createBusinessPeepsWaiting(this, 3);
            return;
        }//end

        public void  performVisitAnimation (Peep param1 )
        {
            if (!isNeedingRoad)
            {
                this.createCoinPickEffect();
                this.m_coinPickEffect.setBounceType(param1.getBusinessEntranceEffects(this));
                this.m_coinPickEffect.bounceToType(CoinPickEffect.PICK_1);
                createStagePickEffect();
            }
            return;
        }//end

    }




