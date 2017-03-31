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

import Classes.sim.*;
import Classes.util.*;
import Engine.Helpers.*;
import Events.*;
import Mechanics.*;
import Transactions.*;
//import flash.display.*;

    public class Attraction extends MechanicMapResource implements IMerchant
    {
        protected MerchantCrowdManager m_crowdManager =null ;
        private String m_stateMaintain ;
        private String m_stateOpen ;

        public  Attraction (String param1 )
        {
            super(param1);
            this.m_crowdManager = new MerchantCrowdManager(this);
            m_objectType = WorldObjectTypes.ATTRACTION;
            m_isRoadVerifiable = true;
            addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.subType != "NPCEnterAction")
            {
                this.verifySpawning();
            }
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (param1 && param1.mechanicData && param1.mechanicData.harvestState)
            {
                this.harvestState = param1.mechanicData.harvestState;
            }
            if (param1 && param1.mechanicData && param1.mechanicData.sleepState)
            {
                this.sleepState = param1.mechanicData.sleepState;
            }
            return;
        }//end

        public int  numCustomers ()
        {
            return mechanicData.get("harvestState") && mechanicData.get("harvestState").get("customers") ? (mechanicData.get("harvestState").get("customers")) : (0);
        }//end

        public int  maxCustomers ()
        {
            return mechanicData.get("harvestState") && mechanicData.get("harvestState").get("customersReq") ? (mechanicData.get("harvestState").get("customersReq")) : (0);
        }//end

        public boolean  hasReachedMaxCustomers ()
        {
            return this.maxCustomers != 0 && this.numCustomers >= this.maxCustomers;
        }//end

        public void  harvestState (Object param1)
        {
            mechanicData.put("harvestState", param1);
            return;
        }//end

        public  harvestState ()*
        {
            return mechanicData.get("harvestState");
        }//end

        public void  openTS (double param1 )
        {
            if (mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState", newObject());
            }
            mechanicData.get("harvestState").put("openTS",  param1);
            return;
        }//end

        public double  openTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("harvestState") && mechanicData.get("harvestState") != null && mechanicData.get("harvestState").hasOwnProperty("openTS"))
            {
                return mechanicData.get("harvestState").get("openTS");
            }
            return -1;
        }//end

        public void  closeTS (double param1 )
        {
            if (mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            mechanicData.get("harvestState").put("closeTS",  param1);
            return;
        }//end

        public double  closeTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("harvestState") && mechanicData.get("harvestState") != null && mechanicData.get("harvestState").hasOwnProperty("closeTS"))
            {
                return mechanicData.get("harvestState").get("closeTS");
            }
            return -1;
        }//end

        public void  sleepState (Object param1)
        {
            mechanicData.put("sleepState", param1);
            return;
        }//end

        public  sleepState ()*
        {
            return mechanicData.get("sleepState");
        }//end

        public void  sleepTS (double param1 )
        {
            if (mechanicData.get("sleepState") == null)
            {
                mechanicData.put("sleepState", newObject());
            }
            mechanicData.get("sleepState").put("sleepTS",  param1);
            return;
        }//end

        public double  sleepTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("sleepTS"))
            {
                return mechanicData.get("sleepState").get("sleepTS");
            }
            return -1;
        }//end

        public void  wakeTS (double param1 )
        {
            if (mechanicData.get("sleepState") == null)
            {
                mechanicData.put("sleepState",  new Object());
            }
            mechanicData.get("sleepState").put("wakeTS",  param1);
            return;
        }//end

        public double  wakeTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("wakeTS"))
            {
                return mechanicData.get("sleepState").get("wakeTS");
            }
            return -1;
        }//end

        public double  openDuration ()
        {
            _loc_1 = getMechanicConfig("openTS","GMPlay");
            return _loc_1.params.get("totalTime");
        }//end

        public double  openTimeLeft ()
        {
            if (this.openTS < 0 || this.closeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.closeTS;
            _loc_2 = GlobalEngine.getTimer()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

        public double  sleepDuration ()
        {
            _loc_1 = getMechanicConfig("harvestState","GMPlay");
            return _loc_1.params.get("totalTime");
        }//end

        public double  sleepTimeLeft ()
        {
            if (this.sleepTS < 0 || this.wakeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.wakeTS;
            _loc_2 = GlobalEngine.getTimer()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

        public void  consumables (int param1 )
        {
            if (mechanicData.get("sleepState") == null)
            {
                mechanicData.put("sleepState", newObject());
            }
            mechanicData.get("sleepState").put("consumables",  param1);
            return;
        }//end

        public int  consumables ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("consumables"))
            {
                return mechanicData.get("sleepState").get("consumables");
            }
            return 0;
        }//end

        public void  maintenance (int param1 )
        {
            if (mechanicData.get("sleepState") == null)
            {
                mechanicData.put("sleepState", newObject());
            }
            mechanicData.get("sleepState").put("maintenance",  param1);
            return;
        }//end

        public int  maintenance ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("maintenance"))
            {
                return mechanicData.get("sleepState").get("maintenance");
            }
            return 0;
        }//end

         public boolean  isOpen ()
        {
            return this.openTS > 0 && this.openTimeLeft > 0;
        }//end

        public boolean  isSleeping ()
        {
            return this.sleepTS > 0 && this.sleepTimeLeft > 0;
        }//end

        public boolean  isRouteable ()
        {
            return !isNeedingRoad && this.isOpen() && !this.hasReachedMaxCustomers();
        }//end

        public MapResource  getMapResource ()
        {
            return this;
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

        public void  makePeepEnterTarget (Peep param1 )
        {
            this.m_crowdManager.makeNpcEnterMerchant(param1);
            return;
        }//end

        public MerchantCrowdManager  crowdManager ()
        {
            return this.m_crowdManager;
        }//end

        public void  updatePeepSpawning ()
        {
            this.verifySpawning();
            return;
        }//end

        public void  onWaveFinished ()
        {
            return;
        }//end

        public boolean  isAcceptingVisits ()
        {
            return this.isRouteable();
        }//end

        public void  planVisit (Peep param1 )
        {
            return;
        }//end

        public void  performVisit (Peep param1 )
        {
            return;
        }//end

        public void  performVisitAnimation (Peep param1 )
        {
            return;
        }//end

        public Array  states ()
        {
            Array _loc_2 =null ;
            _loc_1 = m_item.xml;
            if (this.isOpen())
            {
                if (!this.m_stateOpen)
                {
                    this.m_stateOpen = m_item.xml.states.@open;
                }
                _loc_2 = .get(this.m_stateOpen);
            }
            else
            {
                if (!this.m_stateMaintain)
                {
                    this.m_stateMaintain = m_item.xml.states.@maintain;
                }
                _loc_2 = .get(this.m_stateMaintain);
            }
            return _loc_2;
        }//end

         public void  sell ()
        {
            return super.sell();
        }//end

         public String  getToolTipAction ()
        {
            Object _loc_2 =null ;
            _loc_1 = ZLoc.t("Main","ClickToOpen");
            if (isNeedingRoad)
            {
                return super.getToolTipAction();
            }
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (this.isOpen())
                {
                    _loc_1 = ZLoc.t("Main", "AcceptingCustomers");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "ClickToCollect");
                }
                return _loc_1;
            }
            return super.getToolTipAction();
        }//end

         public String  getToolTipStatus ()
        {
            Object _loc_2 =null ;
            int _loc_3 =0;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_1 =null ;
            if (isNeedingRoad)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
                return _loc_1;
            }
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                _loc_3 = int(_loc_2.get("customers"));
                _loc_4 = ZLoc.t("Main", "CustomersNum", {num:_loc_3});
                if (this.isOpen())
                {
                    _loc_5 = ZLoc.t("Main", "OpenBuilding", {time:GameUtil.formatMinutesSeconds(this.openTimeLeft)});
                    _loc_4 = ZLoc.t("Main", "CustomersNum", {num:_loc_2.get("customers")});
                    _loc_1 = _loc_5;
                }
                else
                {
                    _loc_6 = ZLoc.t("Main", "StatusClosed");
                    _loc_1 = _loc_6;
                }
                _loc_1 = _loc_1 + ("\n" + _loc_4);
                return _loc_1;
            }
            else if (this.isSleeping() && !Global.isVisiting())
            {
                _loc_7 = ZLoc.t("Main", "ClickToReopen");
                _loc_8 = ZLoc.t("Main", "MaintenanceMode", {time:GameUtil.formatMinutesSeconds(this.sleepTimeLeft)});
                _loc_1 = _loc_7;
                _loc_1 = _loc_1 + ("\n" + _loc_8);
                return _loc_1;
            }
            return super.getToolTipStatus();
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            this.verifySpawning();
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            this.verifySpawning();
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2, param3);
            return;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            return;
        }//end

         public void  replaceContent (DisplayObject param1 )
        {
            super.replaceContent(param1);
            return;
        }//end

        public void  verifySpawning ()
        {
            if (this.isOpen() && !isNeedingRoad)
            {
                this.activatePeepSpawning();
            }
            else
            {
                this.crowdManager.stopCollecting();
                Global.world.citySim.npcManager.onAttractionsClosed();
            }
            return;
        }//end

        public void  activatePeepSpawning ()
        {
            Global.world.citySim.npcManager.startSpawningAttractionsPeeps();
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

         public boolean  isVisitorInteractable ()
        {
            return !isNeedingRoad && !isPendingOrder;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.sellPrice;
        }//end

    }





