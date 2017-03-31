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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import Transactions.*;

//import flash.utils.*;

    public class Neighborhood extends MechanicMapResource implements ISlottedContainer, IPeepHome
    {

        public  Neighborhood (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.NEIGHBORHOOD;
            m_isRoadVerifiable = true;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            ItemInstance _loc_4 =null ;
            super.loadObject(param1);
            mechanicData.put("slots", newArray());

            //add by xinghai

            if (param1.mechanicData.slots && param1.mechanicData.slots != NOT_INITIALIZED_MECHANIC)
            {
                while (_loc_2 < param1.mechanicData.slots.length())
                {

                    _loc_3 = param1.mechanicData.slots.get(_loc_2) ? (param1.mechanicData.slots.get(_loc_2)) : (null);
                    if (_loc_3)
                    {
                        _loc_4 =(ItemInstance) Global.world.loadObjectInstance(_loc_3);
                        ((Residence)_loc_4).loadObject(_loc_3);
                        ((MapResource)_loc_4).pathProvider = this;
                        ((Array)mechanicData.get("slots")).push(_loc_4);
                    }
                    _loc_2++;
                }
            }
            if (param1.mechanicData.harvestState)
            {
                mechanicData.put("harvestState", param1.mechanicData.harvestState);
            }


            return;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.sellPrice;
        }//end

         public void  sell ()
        {
            if (this.canFinishSell())
            {
                super.sell();
            }
            else
            {
                UI.displayMessage(ZLoc.t("Main", "CannotSellStorageNeighborhood", {item:getItemFriendlyName()}));
            }
            return;
        }//end

        private boolean  canFinishSell ()
        {
            _loc_1 = this.slots;
            return !_loc_1 || _loc_1.length == 0;
        }//end

         public boolean  isOpen ()
        {
            boolean _loc_1 =false ;
            return _loc_1;
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

         public boolean  isVisitorInteractable ()
        {
            return false;
        }//end

         public boolean  doesVisitActionCostEnergy ()
        {
            return this.isVisitorInteractable();
        }//end

         public void  onVisitPlayAction ()
        {
            Global.world.citySim.pickupManager.enqueue("NPC_mailman", this);
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            if (!suppressVisitorActions())
            {
                super.onVisitReplayAction(null);
            }
            return 0;
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

        public boolean  isHarvestable ()
        {
            return this.timeLeft <= 0;
        }//end

        public double  timeLeft ()
        {
            _loc_1 = GlobalEngine.getTimer()/1000;
            _loc_2 = this.endTS-_loc_1;
            return _loc_2 >= 0 ? (_loc_2) : (0);
        }//end

        public  harvestState ()*
        {
            if (!mechanicData.hasOwnProperty("harvestState") || mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            return mechanicData.get("harvestState");
        }//end

        public void  harvestState (Object param1)
        {
            if (!mechanicData.hasOwnProperty("harvestState") || mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            mechanicData.put("harvestState",  param1);
            return;
        }//end

        public  resources ()*
        {
            if (!this.harvestState.hasOwnProperty("resources"))
            {
                this.harvestState.put("resources",  new Array());
            }
            return this.harvestState.get("resources");
        }//end

        public void  resources (Object param1)
        {
            if (!this.harvestState.hasOwnProperty("resources"))
            {
                this.harvestState.put("resources",  new Array());
            }
            this.harvestState.put("resources",  param1);
            return;
        }//end

        public Object endTS ()
        {
            if (!this.harvestState.hasOwnProperty("endTS"))
            {
                this.harvestState.put("endTS",  -1);
            }
            return this.harvestState.get("endTS");
        }//end

        public void  endTS (Object param1)
        {
            this.harvestState.put("endTS",  param1);
            return;
        }//end

        public Array  slots ()
        {
            _loc_1 = getMechanicData();
            if (_loc_1 == null || _loc_1.get("slots") == null)
            {
                _loc_1.put("slots",  new Array());
            }
            return _loc_1.get("slots");
        }//end

        public void  slots (Array param1 )
        {
            _loc_2 = getMechanicData();
            _loc_2.put("slots",  param1);
            return;
        }//end

        public MechanicMapResource  mechanicMapResource ()
        {
            return this;
        }//end

         public String  getPopulationType ()
        {
            Residence _loc_6 =null ;
            _loc_1 = Population.MIXED;
            _loc_2 = Population.MIXED;
            Dictionary _loc_3 =new Dictionary ();
            int _loc_4 =0;
            _loc_5 = this.slots;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                _loc_1 = _loc_6.getPopulationType();
                _loc_2 = _loc_1;
                if (!_loc_3.get(_loc_1))
                {
                    _loc_3.put(_loc_1,  1);
                    _loc_4++;
                    if (_loc_4 > 1)
                    {
                        _loc_2 = Population.MIXED;
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

         public int  getPopulationYield ()
        {
            Residence _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.slots;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.getPopulationYield();
            }
            return _loc_1;
        }//end

         public int  getPopulationCapYield ()
        {
            Residence _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.slots;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.getPopulationCapYield();
            }
            return _loc_1;
        }//end

         public int  getPopulationMaxYield ()
        {
            Residence _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.slots ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.getPopulationMaxYield();
            }
            return _loc_1;
        }//end

        public Population  getPopulation ()
        {
            Residence _loc_4 =null ;
            Population _loc_1 =null ;
            Population _loc_2 =new Population(0,Population.MIXED );
            _loc_3 = this.slots;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_1 = _loc_4.getPopulation();
                _loc_2.merge(_loc_1);
            }
            return _loc_2;
        }//end

         public void  addPopulation (int param1 )
        {
            if (Config.DEBUG_MODE)
            {
                throw new Error("Cannot add population to a neighborhood.");
            }
            return;
        }//end

         public void  recomputePopulation ()
        {
            if (Config.DEBUG_MODE)
            {
                throw new Error("Cannot recompute population on a neighborhood.");
            }
            return;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_1 =null ;
            if (isNeedingRoad)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
                return _loc_1;
            }
            if (this.slots == null || this.slots.length <= 0)
            {
                return super.getToolTipStatus();
            }
            if (!this.isHarvestable() && !Global.isVisiting())
            {
                _loc_2 = ZLoc.t("Main", "BareResidence", {time:GameUtil.formatMinutesSeconds(this.timeLeft)});
                _loc_1 = _loc_2;
                return _loc_1;
            }
            return super.getToolTipStatus();
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.reloadImage();
            super.onObjectDrop(param1);
            if (!isNeedingRoad)
            {
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            return;
        }//end

        public String  getMysteryItemName (int param1 )
        {
            return null;
        }//end

        public boolean  hasMysteryItemInInventory (int param1 )
        {
            return false;
        }//end

        public void  onStoreSlotObject (MapResource param1 )
        {
            param1.pathProvider = this;
            return;
        }//end

        public void  onRemoveSlotObject (MapResource param1 )
        {
            param1.pathProvider = null;
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2);
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "neighborhood", "upgrade_from", param1.name);
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "neighborhood", "upgrade_to", param2.name);
            return;
        }//end

        public int  getInitialHarvestBonus (int param1 )
        {
            HarvestBonus _loc_7 =null ;
            if (getItem() == null)
            {
                return 0;
            }
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

         public boolean  canShowAlternativeUpgradeToolTip ()
        {
            return false;
        }//end

        public static double  calculateEndTime (MechanicMapResource param1 ,Array param2 )
        {
            Residence _loc_7 =null ;
            Item _loc_8 =null ;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            if (param1 == null || param2 == null)
            {
                return -1;
            }
            _loc_3 = GlobalEngine.getTimer()/1000;
            double _loc_4 =-1;
            double _loc_5 =-1;
            int _loc_6 =0;
            while (_loc_6 < param2.length())
            {

                _loc_7 =(Residence) param2.get(_loc_6);
                if (_loc_7 == null)
                {
                }
                _loc_8 = _loc_7.getItem();
                if (_loc_8 == null)
                {
                }
                _loc_9 = Math.round(_loc_8.growTime * 23 * 60 * 60);
                _loc_10 = _loc_7.plantTime / 1000;
                _loc_11 = _loc_10 + _loc_9;
                _loc_12 = Math.max(_loc_11 - _loc_3, 0);
                if (_loc_12 > _loc_5)
                {
                    _loc_4 = _loc_11;
                    _loc_5 = _loc_12;
                }
                _loc_6++;
            }
            return _loc_4;
        }//end

        public static int  getCapacityForNeighborhood (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            if (_loc_2 == null)
            {
                return 0;
            }
            int _loc_3 =1;
            _loc_4 = UpgradeDefinition.getFullUpgradeChain(param1);
            if (UpgradeDefinition.getFullUpgradeChain(param1) != null)
            {
                _loc_3 = _loc_4.length;
            }
            int _loc_5 =0;
            if (_loc_2.storageMaxCapacity >= 0)
            {
                _loc_5 = _loc_2.storageMaxCapacity;
            }
            return _loc_3 * _loc_5;
        }//end

        public static String  getResidenceTypeForNeighborhood (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 == null)
            {
                return _loc_2;
            }
            _loc_4 = _loc_3.storables;
            if (_loc_3.storables != null && _loc_4.length > 0)
            {
                _loc_2 = _loc_4.get(0);
            }
            return _loc_2;
        }//end

    }




