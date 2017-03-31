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

import Classes.LogicComponents.*;
import Classes.sim.*;
import Engine.Helpers.*;
import Engine.Transactions.*;
import Events.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.bandits.*;
import Modules.stats.types.*;
import Modules.zoo.*;

//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class Municipal extends SimpleHarvestableResource implements IPopulationStateObserver, IPlayerStateObserver, IPeepCapacity
    {
        private  String MUNICIPAL ="municipal";
        protected MunicipalComponentBase m_logic =null ;
        public static  Object COMPONENTS ={hub MunicipalComponentHub ,upgradable ,zoo ,ticket };
        public static  String DEFAULT_MUNICIPAL_IMAGE ="static";

        public  Municipal (String param1 )
        {
            super(param1);
            if (this.m_logic == null)
            {
                this.m_logic = this.getLogicComponent();
            }
            m_objectType = WorldObjectTypes.MUNICIPAL;
            m_typeName = this.MUNICIPAL;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_logic.onLoadObject(param1);
            return;
        }//end

        public PopulationCap  getPopulationCap ()
        {
            _loc_1 = m_item.populationCapYield;
            _loc_2 = m_item.populationType;
            PopulationCap _loc_3 =new PopulationCap(_loc_1 ,_loc_2 );
            return _loc_3;
        }//end

         public void  setId (double param1 )
        {
            super.setId(param1);
            this.m_logic.onSetId(param1);
            return;
        }//end

         public void  setItem (Item param1 )
        {
            super.setItem(param1);
            if (this.m_logic == null)
            {
                this.m_logic = this.getLogicComponent();
            }
            return;
        }//end

         public void  attach ()
        {
            super.attach();
            Global.world.citySim.addObserver(this);
            Global.player.addObserver(this);
            return;
        }//end

         public void  detach ()
        {
            super.detach();
            Global.world.citySim.removeObserver(this);
            Global.player.removeObserver(this);
            return;
        }//end

        public MunicipalComponentBase  getLogicComponent ()
        {
            Class _loc_1 =null ;
            MunicipalComponentBase _loc_2 =null ;
            if (COMPONENTS.hasOwnProperty(m_item.behavior))
            {
                _loc_1 = COMPONENTS.get(m_item.behavior);
                _loc_2 =(MunicipalComponentBase) new _loc_1(this);
                return _loc_2;
            }
            return new MunicipalComponentBase(this);
        }//end

        public void  levelChanged (int param1 )
        {
            this.setState(getState());
            this.m_logic.onLevelChanged(param1);
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  energyChanged (double param1 )
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

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            this.m_logic.onPopulationInit(param1, param2, param3);
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            this.setState(getState());
            this.m_logic.onPopulationChanged(param1, param2, param3, param4);
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            return;
        }//end

        protected boolean  useDisplayMechanics ()
        {
            if (!m_hasActionMechanics)
            {
                return false;
            }
            if (this.m_displayMechanics.length == 0)
            {
                return false;
            }
            if (!hasMechanicAvailable("harvestState"))
            {
                return false;
            }
            return this.m_hasActionMechanics;
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            registerStatsActionName(TrackedActionType.FIRST_PLANT, "grandopening");
            registerStatsActionName(TrackedActionType.HARVEST, "collect");
            registerStatsActionName(TrackedActionType.PLANT, "open");
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            Global.world.citySim.recomputePopulationCap(Global.world);
            _loc_1 = getGrowTimeDelta();
            this.m_plantTime = GlobalEngine.getTimer() - _loc_1;
            this.setState(STATE_PLANTED);
            plantTime = GlobalEngine.getTimer();
            super.onBuildingConstructionCompleted_PreServerUpdate();
            return;
        }//end

         public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            this.m_logic.onBuildingConstructionCompleted();
            MechanicManager.getInstance().handleAction((IMechanicUser)this, "onAddToWorld");
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            Global.world.citySim.recomputePopulationCap(Global.world);
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage(DEFAULT_MUNICIPAL_IMAGE,this,m_direction,m_statePhase);
            return _loc_1;
        }//end

         public boolean  isCurrentImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading(DEFAULT_MUNICIPAL_IMAGE, m_direction, m_statePhase);
        }//end

         public boolean  isDrawImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading(DEFAULT_MUNICIPAL_IMAGE, m_direction, m_statePhase);
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (this.useDisplayMechanics())
            {
                _loc_1 = this.getMechToolTipStatus();
                if (_loc_1 == null)
                {
                    _loc_2 = getQualifiedClassName(Global.world.getTopGameMode());
                    _loc_3 = _loc_2.split("::").pop();
                    if (_loc_3 != "GMMultiPick")
                    {
                        _loc_1 = this.m_logic.getToolTipStatus();
                    }
                }
            }
            else
            {
                _loc_1 = this.m_logic.getToolTipStatus();
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            if (this.useDisplayMechanics())
            {
                _loc_1 = this.getMechToolTipAction();
                if (!_loc_1)
                {
                    _loc_1 = this.m_logic.getToolTipAction();
                }
            }
            else
            {
                _loc_1 = this.m_logic.getToolTipAction();
            }
            return _loc_1;
        }//end

         public Component  getCustomToolTipStatus ()
        {
            double _loc_3 =0;
            _loc_1 = super.getCustomToolTipStatus();
            boolean _loc_2 =false ;
            if (_loc_1)
            {
                if (!Global.isVisiting() && getState() == HarvestableResource.STATE_PLANTED && !isUpgradePossible())
                {
                    _loc_2 = true;
                }
                if (_loc_2)
                {
                    _loc_3 = getToolTipTextWidth(ZLoc.t("Main", "BareResidence", {time:"00:00:00"})) + 5;
                    if (_loc_3 > _loc_1.getPreferredWidth())
                    {
                        _loc_1.setPreferredWidth(_loc_3);
                        _loc_1.setMaximumWidth(_loc_3);
                    }
                }
                else
                {
                    _loc_1.setPreferredSize(null);
                }
            }
            return _loc_1;
        }//end

         public Component  getCustomToolTipLevelBar ()
        {
            return null;
        }//end

         public int  getHighlightColor ()
        {
            _loc_1 = this.isHarvestable()&& Global.world.getTopGameMode() instanceof GMPlay;
            _loc_2 = this.isUpgradePossible()&& Global.world.getTopGameMode() instanceof GMPlay;
            return _loc_1 ? (EmbeddedArt.READY_HIGHLIGHT_COLOR) : (_loc_2 ? (EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR) : (super.getHighlightColor()));
        }//end

         public void  onPlayAction ()
        {
            this.m_logic.handlePlayAction();
            return;
        }//end

         public boolean  harvest ()
        {
            Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
            return super.harvest();
        }//end

         public Object  doHarvestDropOff (boolean param1 =true )
        {
            _loc_2 = super.doHarvestDropOff(param1);
            this.m_logic.doHarvestDropOff(param1);
            return _loc_2;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                return;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            _loc_1 = this.m_logic.getProgressBarEndFunction();
            return _loc_1 != null ? (_loc_1) : (super.getProgressBarEndFunction());
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            return Global.player.processRandomModifiers(harvestingDefinition, this, true, m_secureRands);
        }//end

         protected void  createStagePickEffect ()
        {
            this.m_logic.createStagePickEffect();
            return;
        }//end

        public boolean  upgradeGateKeysMet ()
        {
            if (this.m_logic != null)
            {
                return this.m_logic.upgradeGateKeysMet();
            }
            return false;
        }//end

         protected void  updateArrow ()
        {
            if (this.useDisplayMechanics())
            {
                if (this.m_displayMechanics.length > 0)
                {
                    dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "", true));
                    return;
                }
            }
            if (this.m_logic == null)
            {
                this.m_logic = this.getLogicComponent();
            }
            if (this.m_logic.enableUpdateArrow())
            {
                this.createStagePickEffect();
            }
            else
            {
                removeStagePickEffect();
            }
            return;
        }//end

         public void  lock ()
        {
            super.lock();
            this.updateArrow();
            return;
        }//end

         public void  unlock ()
        {
            super.unlock();
            this.updateArrow();
            return;
        }//end

         public void  upgradeBuildingIfPossible (boolean param1 =true ,Transaction param2 ,boolean param3 =true )
        {
            this.m_logic.upgradeBuildingIfPossible();
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2, param3);
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            this.updateArrow();
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            this.updateArrow();
            return;
        }//end

         public void  replaceContent (DisplayObject param1 )
        {
            super.replaceContent(param1);
            this.updateArrow();
            return;
        }//end

         public void  setState (String param1 )
        {
            super.setState(param1);
            this.updateArrow();
            return;
        }//end

         protected Vector3 Vector  getItemImageHotspots (Item param1 ,String param2 ,int param3 ,String param4 ="").<>
        {
            return param1.getCachedImageHotspots(DEFAULT_MUNICIPAL_IMAGE, m_direction, param4);
        }//end

         public Vector3  getItemImageDimensions ()
        {
            if (m_item)
            {
                return m_item.getCachedImageDimensions(DEFAULT_MUNICIPAL_IMAGE, m_direction);
            }
            return null;
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            super.prepareForStorage(param1);
            Global.world.citySim.recomputePopulationCap(Global.world);
            return;
        }//end

         public void  restoreFromStorage (MapResource param1)
        {
            super.restoreFromStorage(param1);
            Global.world.citySim.recomputePopulationCap(Global.world);
            return;
        }//end

         public boolean  unlimitedVisits ()
        {
            return true;
        }//end

        public int  rollCallTieredDooberValue ()
        {
            Object _loc_4 =null ;
            _loc_1 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(this,"rollCall",MechanicManager.ALL)
            _loc_2 = _loc_1.getCrewState();
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                if (_loc_4.checkedIn)
                {
                    _loc_3++;
                }
            }
            return _loc_3;
        }//end

        public void  rollCallTieredDooberValue (int param1 )
        {
            return;
        }//end

    }



