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

import Classes.gates.*;
import Engine.Transactions.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Transactions.*;
//import flash.geom.*;
//import flash.utils.*;

    public class MechanicMapResource extends MapResource implements IMechanicUser
    {
        protected Array m_displayMechanics ;
        protected Array m_displayMechanicConfigs ;
        public  String NOT_INITIALIZED_MECHANIC ="Data for this mechanic has not been initialized";
        protected Dictionary m_actionMechanics ;
        protected boolean m_hasActionMechanics =false ;
        protected Dictionary mechanicData ;
        protected GateFactory m_mechanicGateFactory ;
        protected IGate m_mechanicGate ;
        private String m_stateUpgrade ;
        protected String m_preferredLayer =null ;
        public  String STATE_STATIC ="static";

        public  MechanicMapResource (String param1 )
        {
            this.m_displayMechanics = new Array();
            this.m_displayMechanicConfigs = new Array();
            this.m_actionMechanics = new Dictionary();
            this.mechanicData = new Dictionary();
            this.m_stateUpgrade = Item.UPGRADE_STATE_NONE;
            super(param1);
            this.m_mechanicGate = null;
            this.m_mechanicGateFactory = new GateFactory();
            this.initializeMechanics();
            setState(this.STATE_STATIC);
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            super.loadObject(param1);
            if (param1.mechanicData)
            {
                for(int i0 = 0; i0 < param1.mechanicData.size(); i0++)
                {
                		_loc_2 = param1.mechanicData.get(i0);

                    if (param1.mechanicData.get(_loc_2) != this.NOT_INITIALIZED_MECHANIC)
                    {
                        this.mechanicData.put(_loc_2,  param1.mechanicData.get(_loc_2));
                    }
                }
                dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "load", true));
                MechanicManager.getInstance().handleAction(this, "load", null);
            }
            return;
        }//end

         public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PostServerUpdate();
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "load", true));
            MechanicManager.getInstance().handleAction((IMechanicUser)this, "onAddToWorld");
            return;
        }//end

         protected String  getLayerName ()
        {
            return this.m_preferredLayer;
        }//end

        public void  overrideLayerName (String param1 )
        {
            this.m_preferredLayer = param1;
            return;
        }//end

        public String  upgradeState ()
        {
            return this.m_stateUpgrade;
        }//end

        public void  upgradeState (String param1 )
        {
            _loc_2 = this.m_stateUpgrade;
            this.m_stateUpgrade = param1;
            if (param1 != _loc_2)
            {
                dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "load", true));
            }
            return;
        }//end

         public void  upgradeBuildingIfPossible (boolean param1 =true ,Transaction param2 ,boolean param3 =true )
        {
            super.upgradeBuildingIfPossible(param1, param2, param3);
            MechanicManager.getInstance().handleAction(this, MechanicManager.ON_UPGRADE);
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2, param3);
            this.initializeMechanics();
            return;
        }//end

         public void  onAddCrewMember ()
        {
            super.onAddCrewMember();
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "load", true));
            return;
        }//end

        public Array  displayMechanics ()
        {
            return this.m_displayMechanics;
        }//end

        public Dictionary  actionMechanics ()
        {
            return this.m_actionMechanics;
        }//end

        public String  state ()
        {
            return m_state;
        }//end

         public boolean  isSellable ()
        {
            return m_ownable;
        }//end

        protected void  initializeMechanics ()
        {
            IClientGameMechanic _loc_1 =null ;
            XMLList _loc_2 =null ;
            XMLList _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_displayMechanics.size(); i0++)
            {
            	_loc_1 = this.m_displayMechanics.get(i0);

                _loc_1.detachDisplayObject();
            }
            this.m_displayMechanics = new Array();
            this.m_actionMechanics = new Dictionary();
            this.m_hasActionMechanics = false;
            for(int i0 = 0; i0 < Global.gameSettings().getMechanicGraftsByType(m_item.type).size(); i0++)
            {
            	_loc_2 = Global.gameSettings().getMechanicGraftsByType(m_item.type).get(i0);

                this.addMechanics(_loc_2);
            }
            _loc_3 = m_item.mechanicsXml;
            this.addMechanics(_loc_3);
            return;
        }//end

        protected void  addMechanics (XMLList param1 )
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            MechanicConfigData _loc_4 =null ;
            XML _loc_5 =null ;
            MechanicConfigData _loc_6 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.gameEventMechanics.size(); i0++)
                {
                	_loc_2 = param1.gameEventMechanics.get(i0);

                    for(int i0 = 0; i0 < _loc_2.mechanic.size(); i0++)
                    {
                    	_loc_3 = _loc_2.mechanic.get(i0);

                        _loc_4 = new MechanicConfigData(_loc_3);
                        this.addActionMechanic(_loc_4, _loc_2.@gameMode);
                        this.m_hasActionMechanics = true;
                    }
                }
                if (param1.clientDisplayMechanics.length() > 0)
                {
                    for(int i0 = 0; i0 < param1.clientDisplayMechanics.mechanic.size(); i0++)
                    {
                    	_loc_5 = param1.clientDisplayMechanics.mechanic.get(i0);

                        _loc_6 = new MechanicConfigData(_loc_5);
                        this.addDisplayMechanic(_loc_6);
                    }
                }
            }
            return;
        }//end

        public void  reinitializeMechanics ()
        {
            this.m_displayMechanicConfigs = new Array();
            this.initializeMechanics();
            return;
        }//end

        protected void  addActionMechanic (MechanicConfigData param1 ,String param2 )
        {
            this.m_actionMechanics.put(param2,  this.addMechanic(this.m_actionMechanics.get(param2), param1));
            return;
        }//end

        protected void  addDisplayMechanic (MechanicConfigData param1 )
        {
            this.m_displayMechanics.push(MechanicManager.getInstance().createDisplayMechanicInstance(this, param1));
            this.m_displayMechanicConfigs.push(param1);
            return;
        }//end

        protected Dictionary  addMechanic (Dictionary param1 ,MechanicConfigData param2 )
        {
            Dictionary _loc_3 =new Dictionary ();
            if (param1 != null)
            {
                _loc_3 = param1;
            }
            _loc_3.put(param2.type,  param2);
            return _loc_3;
        }//end

        public MechanicConfigData  getMechanicConfig (String param1 ,String param2 ="all")
        {
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            Dictionary _loc_6 =null ;
            MechanicConfigData _loc_7 =null ;
            MechanicConfigData _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_actionMechanics.size(); i0++)
            {
            		_loc_4 = this.m_actionMechanics.get(i0);

                _loc_5 = _loc_4 == param2 || param2 == "all";
                if (_loc_5 && this.m_actionMechanics.get(_loc_4) != null)
                {
                    _loc_6 = this.m_actionMechanics.get(_loc_4);
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    	_loc_7 = _loc_6.get(i0);

                        if (_loc_7.type == param1)
                        {
                            _loc_3 = _loc_7;
                            break;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public MechanicConfigData  getDisplayMechanicConfig (String param1 )
        {
            MechanicConfigData _loc_3 =null ;
            MechanicConfigData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_displayMechanicConfigs.size(); i0++)
            {
            		_loc_3 = this.m_displayMechanicConfigs.get(i0);

                if (_loc_3.type == param1)
                {
                    return _loc_3;
                }
            }
            return _loc_2;
        }//end

        public Array  getPrioritizedMechanicsForGameEvent (String param1 )
        {
            Dictionary _loc_3 =null ;
            Array _loc_4 =null ;
            MechanicConfigData _loc_5 =null ;
            MechanicConfigData _loc_6 =null ;
            Array _loc_2 =new Array();
            if (this.m_actionMechanics.get(param1) != null)
            {
                _loc_3 = this.m_actionMechanics.get(param1);
                _loc_4 = new Array();
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_5 = _loc_3.get(i0);

                    _loc_4.push(_loc_5);
                }
                _loc_4.sortOn("priority", Array.NUMERIC | Array.DESCENDING);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_6 = _loc_4.get(i0);

                    _loc_2.push(_loc_6.type);
                }
            }
            return _loc_2;
        }//end

        public int  getMechanicPriority (String param1 ,String param2 )
        {
            Dictionary _loc_3 =null ;
            if (this.m_actionMechanics.get(param2) != null)
            {
                _loc_3 = this.m_actionMechanics.get(param2);
                if (_loc_3.get(param1))
                {
                    return _loc_3.get(param1).priority;
                }
                return -1;
            }
            return -1;
        }//end

        public Object getDataForMechanic (String param1 )
        {
            _loc_2 = null;
            if (this.hasOwnProperty(param1))
            {
                _loc_2 = this.get(param1);
            }
            else
            {
                _loc_2 = this.mechanicData.get(param1);
            }
            return _loc_2;
        }//end

        public void  setDataForMechanic (String param1 ,Object param2 ,String param3 )
        {
            if (this.hasOwnProperty(param1))
            {
                this.put(param1,  param2);
            }
            else
            {
                this.mechanicData.put(param1,  param2);
            }
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, param1, true, param3));
            return;
        }//end

        public Dictionary  getMechanicData ()
        {
            return this.mechanicData;
        }//end

        public boolean  mechanicExperimentEnabled (MechanicConfigData param1 )
        {
            String _loc_2 =null ;
            double _loc_3 =0;
            if (param1.params.hasOwnProperty("experiment"))
            {
                _loc_2 = param1.params.get("experiment");
                _loc_3 = Global.experimentManager.getVariant(_loc_2);
                if (param1.params.hasOwnProperty("variants"))
                {
                    return ((String)param1.params.get("variants")).split(",").indexOf(_loc_3.toString()) >= 0;
                }
                return _loc_3 != 0;
            }
            if (param1.params.hasOwnProperty("not_experiment"))
            {
                _loc_2 = param1.params.get("not_experiment");
                if (Global.experimentManager.getVariant(_loc_2) == 0)
                {
                    return true;
                }
                return false;
            }
            return true;
        }//end

        public boolean  hasMechanicAvailable (String param1 )
        {
            return this.getMechanicConfig(param1) != null;
        }//end

         public void  onUpdate (double param1 )
        {
            IClientGameMechanic _loc_2 =null ;
            super.onUpdate(param1);
            for(int i0 = 0; i0 < this.m_displayMechanics.size(); i0++)
            {
            		_loc_2 = this.m_displayMechanics.get(i0);

                _loc_2.updateDisplayObject(param1);
            }
            return;
        }//end

         public boolean  isVisitorInteractable ()
        {
            _loc_1 = super.isVisitorInteractable();
            return _loc_1 || this.m_actionMechanics.get("GMVisit") != null;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            MechanicManager.getInstance().handleAction(this, VISIT_REPLAY_ACTION, null);
            return super.onVisitReplayAction(param1);
        }//end

         protected void  sellConfirmationHandler (GenericPopupEvent event )
        {
            super.sellConfirmationHandler(event);
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            MechanicManager.getInstance().handleAction(this, "sell", null);
            return;
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            IClientGameMechanic _loc_2 =null ;
            if (m_displayObject && this.m_displayMechanics.length())
            {
                if (m_displayObject.getRect(m_displayObject.parent).containsPoint(param1))
                {
                    for(int i0 = 0; i0 < this.m_displayMechanics.size(); i0++)
                    {
                    		_loc_2 = this.m_displayMechanics.get(i0);

                        if (_loc_2 instanceof IEdgeModifier)
                        {
                            if (((IEdgeModifier)_loc_2).isPixelInside(param1))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            return super.isPixelInside(param1);
        }//end

         public int  getToolTipFloatOffset ()
        {
            IClientGameMechanic _loc_2 =null ;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.m_displayMechanics.size(); i0++)
            {
            	_loc_2 = this.m_displayMechanics.get(i0);

                if (_loc_2 instanceof IEdgeModifier)
                {
                    _loc_1 = _loc_1 + ((IEdgeModifier)_loc_2).getFloatOffset();
                }
            }
            return super.getToolTipFloatOffset() + _loc_1;
        }//end

        protected String  getMechToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            IActionGameMechanic _loc_6 =null ;
            _loc_1 = super.getToolTipStatus();
            if (this.m_hasActionMechanics)
            {
                _loc_2 = getQualifiedClassName(Global.world.getTopGameMode());
                _loc_3 = _loc_2.split("::").pop();
                _loc_4 =(Array) this.getPrioritizedMechanicsForGameEvent(_loc_3);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_6 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(this, _loc_5, _loc_3);
                    if (_loc_6 instanceof IToolTipModifier)
                    {
                        if (_loc_6.hasOverrideForGameAction(_loc_3))
                        {
                            _loc_1 = ((IToolTipModifier)_loc_6).getToolTipStatus();
                            break;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            return this.getMechToolTipStatus();
        }//end

        protected String  getMechToolTipAction ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            IActionGameMechanic _loc_6 =null ;
            _loc_1 = super.getToolTipAction();
            if (this.m_hasActionMechanics)
            {
                _loc_2 = getQualifiedClassName(Global.world.getTopGameMode());
                _loc_3 = _loc_2.split("::").pop();
                _loc_4 =(Array) this.getPrioritizedMechanicsForGameEvent(_loc_3);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_6 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(this, _loc_5, _loc_3);
                    if (_loc_6 instanceof IToolTipModifier)
                    {
                        if (_loc_6.hasOverrideForGameAction(_loc_3))
                        {
                            _loc_1 = ((IToolTipModifier)_loc_6).getToolTipAction();
                            break;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            return this.getMechToolTipAction();
        }//end

        public String  getCustomName ()
        {
            MechanicConfigData _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            _loc_1 = getItem().localizedName;
            _loc_2 = this.getMechanicData();
            if (_loc_2.get("name"))
            {
                _loc_1 = _loc_2.get("name");
            }
            else
            {
                _loc_3 = this.getMechanicConfig("name");
                _loc_4 = _loc_3.params.get("defaultNameLoc");
                if (_loc_4 != null && _loc_4 != "")
                {
                    if (Global.isVisiting())
                    {
                        _loc_5 = Global.getVisiting();
                        _loc_6 = Global.player.getFriendFirstName(_loc_5);
                        _loc_7 = Global.player.getFriendGender(_loc_5) == "M" ? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
                        _loc_1 = ZLoc.t("Items", _loc_4, {user:ZLoc.tn(_loc_6, _loc_7)});
                    }
                    else
                    {
                        _loc_8 = Global.player.snUser.gender == "M" ? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
                        _loc_1 = ZLoc.t("Items", _loc_4, {user:ZLoc.tn(Global.player.snUser.firstName, _loc_8)});
                    }
                }
            }
            return _loc_1;
        }//end

         public void  setItem (Item param1 )
        {
            super.setItem(param1);
            return;
        }//end

         public void  detach ()
        {
            IClientGameMechanic _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_displayMechanics.size(); i0++)
            {
            		_loc_1 = this.m_displayMechanics.get(i0);

                _loc_1.detachDisplayObject();
            }
            super.detach();
            return;
        }//end

         public void  switchItem (Item param1 )
        {
            this.detach();
            this.setItem(param1);
            this.attach();
            this.initializeMechanics();
            setState(getState());
            return;
        }//end

         public void  trackVisitAction (String param1 ,int param2 =1,String param3 ="",String param4 ="")
        {
            Object _loc_5 =null ;
            if (!param3)
            {
                _loc_5 = this.getDataForMechanic("socialInventory");
                if (_loc_5)
                {
                    if (_loc_5.get("heart"))
                    {
                        param3 = "helped_hearted_item";
                    }
                }
            }
            if (!param4)
            {
                param4 = getItemName();
            }
            super.trackVisitAction(param1, param2, param3, param4);
            return;
        }//end

         protected boolean  suppressVisitorActions ()
        {
            return this.hasPendingRemodel();
        }//end

        protected boolean  hasPendingRemodel ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.getDataForMechanic("remodel");
            if (_loc_2 && _loc_2.hasOwnProperty(RemodelMechanic.ITEMNAME_KEY))
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public IGate  getMechanicGate (String param1 ,Object param2 )
        {
            if (!this.m_mechanicGate || this.m_mechanicGate.name != param1)
            {
                this.m_mechanicGate = this.m_mechanicGateFactory.loadGateFromXML(getItem(), this, param1, null);
            }
            if (this.m_mechanicGate && param2)
            {
                this.m_mechanicGate.loadFromObject(param2);
            }
            return this.m_mechanicGate;
        }//end

        public void  clearMechanicGate ()
        {
            this.m_mechanicGate = null;
            return;
        }//end

        public void  onMechanicPackSwap ()
        {
            return;
        }//end

    }




