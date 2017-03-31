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

import Classes.actions.*;
import Classes.doobers.*;
import Classes.effects.*;
import Classes.gates.*;
import Classes.sim.*;
import Classes.util.*;
import Display.PopulationUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.franchise.transactions.*;
import Modules.quest.Helpers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class ConstructionSite extends MechanicMapResource implements IPeepPotential
    {
        private  String CONSTRUCTION_SITE ="construction_site";
        private int m_currentState =5;
        private Array m_displayStage ;
        private Array m_displayStageNPCOffset ;
        private int m_stage =0;
        private int m_numberOfStages =0;
        private int m_buildsPerStage ;
        private int m_energyPerBuild ;
        private int m_energyStart ;
        private int m_timePerBuild ;
        private double m_buildStartTime ;
        private int m_builds =0;
        private int m_finishedBuilds =0;
        private int m_totalNumberOfBuilds ;
        private String m_targetBuildingName ;
        private String m_targetBuildingFriendlyName ;
        private Class m_targetBuildingClass ;
        private Array m_buildQueue ;
        private Dictionary m_gates ;
        private GateFactory m_gateFactory ;
        private boolean m_isAccelerating =false ;
        private double m_accelerateStartTime =0;
        private double m_buildTimeLeftAtAccelStart =0;
        private boolean m_isAccelerated =false ;
        private double m_accelerationEnergyCost ;
        private ScaffoldEffect m_scaffoldEffect ;
        private int m_numBoosts ;
        private String m_visitingId ;
        protected String m_mouseOverMessage =null ;
        protected boolean m_alreadyComplete =false ;
        private int m_refund =-1;
        public static  String BUILD_GATE ="build";
        public static  String PRE_BUILD_GATE ="pre_build";
        private static  Array ALL_GATE_IDS =new Array(BUILD_GATE ,PRE_BUILD_GATE );
        private static  String FIRST_TIME_BUILD_COUPON ="first_time_build";
        private static  String FIRST_TIME_BUILD_MODIFIER_GROUP ="first_time_build";
        public static  int STATE_IDLE =0;
        public static  int STATE_BUILDING =1;
        public static  int STATE_AT_GATE =2;
        public static  int STATE_CAN_BE_FINISHED =3;
        public static  int STATE_FINISHED =4;
        public static  int STATE_PRE_GATE =5;
        public static  boolean IS_QUEUE_ALL_BUILDS =false ;

        public  ConstructionSite (String param1 )
        {
            LotSite _loc_2 =null ;
            String _loc_3 =null ;
            _loc_5 = undefined;
            Vector2 _loc_6 =null ;
            this.m_displayStage = new Array();
            this.m_displayStageNPCOffset = new Array();
            this.m_buildQueue = new Array();
            this.m_gates = new Dictionary();
            super(param1);
            m_objectType = WorldObjectTypes.CONSTRUCTION_SITE;
            m_typeName = this.CONSTRUCTION_SITE;
            _loc_4 = m_item.rawImageXml;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_4.get(_loc_5).@type == "displayStage")
                {
                    if (_loc_3 != _loc_4.get(_loc_5).@name)
                    {
                        this.m_displayStage.push(_loc_4.get(_loc_5).@name);
                        _loc_6 = new Vector2(0, 0);
                        if (_loc_4.get(_loc_5).@npcOffsetX)
                        {
                            _loc_6.x = int(_loc_4.get(_loc_5).@npcOffsetX);
                        }
                        if (_loc_4.get(_loc_5).@npcOffsetY)
                        {
                            _loc_6.y = int(_loc_4.get(_loc_5).@npcOffsetY);
                        }
                        this.m_displayStageNPCOffset.push(_loc_6);
                        this.m_numberOfStages++;
                        _loc_3 = _loc_4.get(_loc_5).@name;
                    }
                }
            }
            this.m_numberOfStages--;
            setState(this.m_displayStage.get(this.m_stage));
            this.m_buildsPerStage = m_item.xml.buildsPerStage;
            this.m_energyStart = m_item.xml.energyStart;
            this.m_energyPerBuild = m_item.xml.energyCostPerBuild;
            this.m_timePerBuild = m_item.xml.timePerBuild;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUILD_TEST) == ExperimentDefinitions.BUILD_TEST_ENABLED || RuntimeVariableManager.getBoolean("IS_TEST_ENVIRONMENT", false))
            {
                this.m_timePerBuild = m_item.xml.timePerBuild / 10;
            }
            else
            {
                this.m_timePerBuild = m_item.xml.timePerBuild;
            }
            this.m_totalNumberOfBuilds = this.m_buildsPerStage * this.m_numberOfStages;
            this.m_numBoosts = 0;
            this.m_gateFactory = new GateFactory();
            if (this.m_numberOfStages != int(m_item.xml.numberOfStages))
            {
                ErrorManager.addError("Error processing " + param1 + ": unexpected number of build stages!");
            }
            if (this.m_numberOfStages == 0)
            {
                this.m_currentState = STATE_AT_GATE;
            }
            return;
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            m_statsName = ItemClassDefinitions.getTypeByClass(this.m_targetBuildingClass);
            m_statsItemName = this.m_targetBuildingName;
            return;
        }//end

        protected String  getPreviewImageName (Item param1 )
        {
            switch(param1.type)
            {
                case "landmark":
                case "municipal":
                case "business":
                {
                    return "static";
                }
                default:
                {
                    return "grown";
                    break;
                }
            }
        }//end

         protected ItemImageInstance  getCurrentImageForMask ()
        {
            Item _loc_1 =null ;
            String _loc_2 =null ;
            if (this.m_currentState == STATE_AT_GATE)
            {
                _loc_1 = this.targetItem;
                _loc_2 = this.getPreviewImageName(_loc_1);
                return _loc_1.getCachedImage(_loc_2, this, m_direction, m_statePhase);
            }
            return super.getCurrentImageForMask();
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            if (this.m_currentState == STATE_AT_GATE && m_content instanceof Bitmap)
            {
                m_maskBitmap =(Bitmap) content;
            }
            return super.isPixelInside(param1);
        }//end

        public String  targetName ()
        {
            return this.m_targetBuildingName;
        }//end

        public int  currentState ()
        {
            return this.m_currentState;
        }//end

        public Class  targetClass ()
        {
            return this.m_targetBuildingClass;
        }//end

        public Item  targetItem ()
        {
            return Global.gameSettings().getItemByName(this.m_targetBuildingName);
        }//end

        public double  timePerBuild ()
        {
            _loc_1 = Global.gameSettings().getInt("constructionVisitBoost")*3600000;
            _loc_2 = this.m_totalNumberOfBuilds*m_item.xml.timePerBuild;
            _loc_3 = this1-.m_numBoosts*_loc_1/_loc_2;
            return m_item.xml.timePerBuild * _loc_3;
        }//end

        public void  initializeSite (Item param1 ,Class param2 )
        {
            String _loc_3 =null ;
            Function _loc_4 =null ;
            this.m_targetBuildingName = param1.name;
            this.m_targetBuildingClass = param2;
            this.m_targetBuildingFriendlyName = this.targetItem.localizedName;
            this.m_refund = this.calculateRefund();
            for(int i0 = 0; i0 < ALL_GATE_IDS.size(); i0++)
            {
            	_loc_3 = ALL_GATE_IDS.get(i0);

                _loc_4 = this.getGateCompleteCallback(_loc_3);
                this.m_gates.put(_loc_3,  this.m_gateFactory.loadGateFromXML(param1, this, _loc_3, _loc_4));
            }
            this.setMouseOverMessage(param1);
            this.statsInit();
            return;
        }//end

         public String  getItemFriendlyName ()
        {
            _loc_1 = Global.gameSettings().getItemByName(this.targetName);
            _loc_2 = _loc_1.className=="Headquarter"? (Global.franchiseManager.getHeadquartersFriendlyName(_loc_1.name)) : (_loc_1.localizedName);
            return _loc_2;
        }//end

         public void  loadObject (Object param1 )
        {
            int _loc_3 =0;
            super.loadObject(param1);
            this.m_builds = param1.builds;
            this.m_finishedBuilds = param1.finishedBuilds;
            this.m_currentState = param1.currentState;
            this.m_buildStartTime = param1.buildStartTime;
            this.m_targetBuildingName = param1.targetBuildingName;
            this.m_targetBuildingClass =(Class) getDefinitionByName("Classes." + param1.targetBuildingClass);
            this.m_targetBuildingFriendlyName = this.targetItem.localizedName;
            this.m_isAccelerated = param1.isAccelerated;
            this.m_numBoosts = param1.numBoosts;
            this.m_refund = param1.hasOwnProperty("refund") && param1.refund != null ? (param1.refund) : (-1);
            _loc_2 = this.targetItem;
            this.loadGatesFromObject(param1, _loc_2);
            if (this.m_numberOfStages == 0)
            {
                this.m_currentState = STATE_AT_GATE;
            }
            else if (this.m_isAccelerated)
            {
                _loc_4 = this.m_totalNumberOfBuilds;
                this.m_finishedBuilds = this.m_totalNumberOfBuilds;
                this.m_builds = _loc_4;
                this.m_stage = this.m_numberOfStages - 1;
                if (param1.gates)
                {
                    this.m_currentState = STATE_AT_GATE;
                }
            }
            else
            {
                this.m_stage = param1.stage;
            }
            setState(this.m_displayStage.get(this.m_stage));
            this.setDisplayObjectDirty(true);
            if (this.m_currentState == STATE_BUILDING)
            {
                _loc_3 = 0;
                while (_loc_3 < (param1.buildsQueued.length - 1))
                {

                    this.m_buildQueue.push(true);
                    _loc_3++;
                }
                if (this.m_buildQueue.length())
                {
                    _loc_3 = 0;
                    while (_loc_3 < (this.m_buildQueue.length - 1))
                    {

                        this.createNPCs();
                        _loc_3++;
                    }
                }
            }
            this.statsInit();
            this.setMouseOverMessage(_loc_2);
            return;
        }//end

        protected void  loadGatesFromObject (Object param1 ,Item param2 )
        {
            Object _loc_4 =null ;
            Function _loc_5 =null ;
            IGate _loc_3 =null ;
            for(int i0 = 0; i0 < param1.gates.size(); i0++)
            {
            	_loc_4 = param1.gates.get(i0);

                if (_loc_4.name == null || _loc_4.name == "")
                {
                    _loc_4.name = BUILD_GATE;
                }
                _loc_5 = this.getGateCompleteCallback(_loc_4.name);
                _loc_3 = this.m_gateFactory.loadGateFromXML(param2, this, _loc_4.name, _loc_5);
                if (_loc_3 && _loc_3.loadType == AbstractGate.LOAD_TYPE_DYNAMIC)
                {
                    this.m_gates.put(_loc_4.name,  _loc_3);
                    continue;
                }
                this.m_gates.put(_loc_4.name,  this.m_gateFactory.loadGateFromObject(param1, param2, this, _loc_5));
            }
            return;
        }//end

        protected Function  getGateCompleteCallback (String param1 )
        {
            Function _loc_2 =null ;
            switch(param1)
            {
                case BUILD_GATE:
                {
                    _loc_2 = this.completeBuilding;
                    break;
                }
                case PRE_BUILD_GATE:
                {
                    _loc_2 = this.completePreGate;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_2;
        }//end

         protected void  onStateChanged (String param1 ,String param2 )
        {
            this.updateArrow();
            this.updateScaffold();
            if (this.m_currentState == STATE_IDLE)
            {
                Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
            }
            return;
        }//end

         public void  drawDisplayObject ()
        {
            super.drawDisplayObject();
            this.updateScaffold();
            return;
        }//end

        private void  updateScaffold ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            String _loc_5 =null ;
            if (this.m_targetBuildingName != null && this.m_item.showGirders > 0)
            {
                _loc_1 = (!this.m_isAccelerating && this.m_buildQueue.length ? (1) : (0)) + (this.m_isAccelerating && this.m_currentState != STATE_AT_GATE ? (2) : (0));
                _loc_2 = this.m_finishedBuilds + _loc_1;
                _loc_3 = this.m_finishedBuilds + _loc_1;
                _loc_4 = this.m_numberOfStages;
                _loc_5 = this.m_targetBuildingName;
                if (this.m_scaffoldEffect)
                {
                    this.m_scaffoldEffect.startStage = _loc_2;
                    this.m_scaffoldEffect.endStage = _loc_3;
                    this.m_scaffoldEffect.maxStage = _loc_4;
                    this.m_scaffoldEffect.targetBuildingName = _loc_5;
                    this.m_scaffoldEffect.reattach();
                }
                else
                {
                    this.m_scaffoldEffect = new ScaffoldEffect(this, _loc_2, _loc_3, _loc_4, _loc_5);
                }
            }
            return;
        }//end

        protected void  doPlayAction ()
        {
            boolean _loc_1 =false ;
            switch(this.m_currentState)
            {
                case STATE_PRE_GATE:
                {
                    if (Global.isVisiting())
                    {
                        return;
                    }
                    if (this.m_gates.get(PRE_BUILD_GATE) && this.m_gates.get(PRE_BUILD_GATE).keyCount > 0)
                    {
                        this.m_gates.get(PRE_BUILD_GATE).displayGate(null, ZLoc.t("Dialogs", "PreGateDialogTitle", {item:this.m_targetBuildingFriendlyName}));
                        break;
                    }
                    this.completePreGate();
                    this.doConstruction();
                    break;
                }
                case STATE_IDLE:
                {
                    this.doConstruction();
                    break;
                }
                case STATE_BUILDING:
                {
                    _loc_1 = true;
                    this.doConstruction(_loc_1);
                    break;
                }
                case STATE_AT_GATE:
                {
                    if (Global.isVisiting())
                    {
                        return;
                    }
                    if (this.m_gates.get(BUILD_GATE) && this.m_gates.get(BUILD_GATE).keyCount > 0)
                    {
                        this.m_gates.get(BUILD_GATE).displayGate();
                        break;
                    }
                    this.completeBuilding();
                    break;
                }
                default:
                {
                    break;
                }
            }
            m_actionMode = NO_ACTION;
            return;
        }//end

         public void  onPlayAction ()
        {
            _loc_1 = boolean(m_actionBar&& m_actionBar.visible);
            _loc_2 = boolean(m_visitReplayLock>0);
            if (_loc_1 || _loc_2 || m_item.blockedByExperiments)
            {
                return;
            }
            m_actionMode = PLAY_ACTION;
            this.doPlayAction();
            return;
        }//end

         public void  onVisitPlayAction ()
        {
            m_actionMode = VISIT_PLAY_ACTION;
            this.doVisitAction();
            m_actionMode = NO_ACTION;
            trackVisitAction(TrackedActionType.HELP_BUILD);
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            _loc_2 = super.onVisitReplayAction(param1);
            GameTransactionManager.addTransaction(param1);
            this.doVisitAction();
            return _loc_2;
        }//end

        private void  doVisitAction ()
        {
            switch(this.m_currentState)
            {
                case STATE_IDLE:
                {
                    this.doConstruction();
                    break;
                }
                case STATE_BUILDING:
                {
                    this.visitBoost();
                    break;
                }
                case STATE_AT_GATE:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         public void  updateAlpha ()
        {
            if (m_actionBar && m_actionBar.visible || this.m_isAccelerating)
            {
                this.alpha = 1;
            }
            else
            {
                super.updateAlpha();
            }
            return;
        }//end

         protected void  updateActionBarPosition ()
        {
            Vector2 _loc_1 =null ;
            Point _loc_2 =null ;
            if (m_actionBar && m_actionBar.visible)
            {
                _loc_1 = this.getWorkerOffsetForCurrentStage();
                _loc_2 = IsoMath.tilePosToPixelPos(m_position.x + _loc_1.x, m_position.y + _loc_1.y);
                _loc_2.y = _loc_2.y;
                _loc_2 = IsoMath.viewportToStage(_loc_2);
                m_actionBar.x = _loc_2.x - ACTIONBAR_WIDTH / 2;
                m_actionBar.y = _loc_2.y - ACTIONBAR_HEIGHT - 60;
            }
            return;
        }//end

         public Point  getConstructionNPCOffset ()
        {
            return new Point(this.getWorkerOffsetForCurrentStage().x, this.getWorkerOffsetForCurrentStage().y);
        }//end

         public boolean  isVisitorInteractable ()
        {
            switch(this.m_currentState)
            {
                case STATE_IDLE:
                {
                    return true;
                }
                case STATE_BUILDING:
                {
                    return true;
                }
                case STATE_AT_GATE:
                {
                    return false;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

         public void  onObjectDrag (Vector3 param1 )
        {
            Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
            super.onObjectDrag(param1);
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            int _loc_2 =0;
            if (this.m_buildQueue.length())
            {
                _loc_2 = 0;
                while (_loc_2 < (this.m_buildQueue.length - 1))
                {

                    this.createNPCs();
                    _loc_2++;
                }
            }
            if (this.m_scaffoldEffect)
            {
                this.m_scaffoldEffect.cleanUp();
                this.m_scaffoldEffect = null;
                this.updateScaffold();
            }
            super.onObjectDrop(param1);
            return;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         public String  getToolTipHeader ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (m_actionBar && m_actionBar.visible)
            {
                return null;
            }
            if (this.m_targetBuildingName.slice(0, 2) == "hq")
            {
                _loc_1 = Global.franchiseManager.getFranchiseTypeFromHeadquarters(this.m_targetBuildingName);
                _loc_2 = Global.franchiseManager.worldOwnerModel.getFranchiseName(_loc_1);
                _loc_3 = ZLoc.t("Items", "headquarters", {item:_loc_2});
                return _loc_3;
            }
            return this.m_targetBuildingFriendlyName;
        }//end

         public void  setId (double param1 )
        {
            super.setId(param1);
            if (this.m_gates.get(BUILD_GATE))
            {
                this.m_gates.get(BUILD_GATE).targetObjectId = getId();
            }
            return;
        }//end

         public String  getToolTipStatus ()
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (m_actionBar && m_actionBar.visible && !this.m_isAccelerating)
            {
                return null;
            }
            String _loc_1 =null ;
            if (!IS_QUEUE_ALL_BUILDS)
            {
                if (!isLocked() && !Global.isVisiting() && !Global.world.isEditMode && this.m_currentState != STATE_BUILDING && this.m_currentState != STATE_FINISHED)
                {
                    if (!this.m_gates.get(PRE_BUILD_GATE) || this.m_gates.get(PRE_BUILD_GATE).keyCount == 0 || this.m_currentState != STATE_PRE_GATE)
                    {
                        if (this.m_numberOfStages != 0)
                        {
                            _loc_1 = ZLoc.t("Main", "BuildInfo", {current:this.m_builds, required:this.m_numberOfStages * this.m_buildsPerStage});
                        }
                    }
                }
            }
            else if (this.m_currentState == STATE_BUILDING)
            {
                if (this.m_isAccelerating)
                {
                    _loc_2 = this.getAcceleratedBuildTimeLeft();
                    if (_loc_2 >= 0)
                    {
                        _loc_3 = GameUtil.formatMinutesSeconds(_loc_2);
                        _loc_1 = ZLoc.t("Main", "BuildingAccelerating");
                    }
                }
                else
                {
                    _loc_4 = GameUtil.formatMinutesSeconds(this.getQueuedBuildTimeLeft());
                    _loc_1 = ZLoc.t("Main", "BuildingReadyIn", {time:_loc_4});
                }
            }
            return _loc_1;
        }//end

        public void  forceUpdateArrow ()
        {
            this.updateArrow();
            return;
        }//end

         protected void  updateArrow ()
        {
            this.createStagePickEffect();
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            String _loc_1 =null ;
            if (!Global.guide.isActive() && !Global.isVisiting())
            {
                if (this.m_stage >= this.m_numberOfStages && this.m_gates.get(BUILD_GATE) != null && this.m_gates.get(BUILD_GATE).checkForKeys())
                {
                    _loc_1 = StagePickEffect.PICK_UPGRADE_HAMMER;
                }
            }
            if (_loc_1)
            {
                m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.STAGE_PICK);
                m_stagePickEffect.setPickType(_loc_1);
                m_stagePickEffect.float();
            }
            else
            {
                removeStagePickEffect();
            }
            return;
        }//end

        public int  getQueuedBuildTimeLeft ()
        {
            _loc_1 = GlobalEngine.getTimer();
            _loc_2 = this.m_totalNumberOfBuilds-this.m_finishedBuilds;
            _loc_3 = this.timePerBuild*_loc_2;
            _loc_4 = this(.m_buildStartTime+_loc_3-_loc_1)/1000;
            if ((this.m_buildStartTime + _loc_3 - _loc_1) / 1000 < 0)
            {
                _loc_4 = 0;
            }
            return _loc_4;
        }//end

        public int  getAcceleratedBuildTimeLeft ()
        {
            _loc_1 = GlobalEngine.getTimer();
            _loc_2 = this.m_accelerateStartTime+this.m_timePerBuild;
            _loc_3 = _loc_2(-_loc_1)/(_loc_2-this.m_accelerateStartTime)*this.m_buildTimeLeftAtAccelStart/1000;
            return _loc_3;
        }//end

        protected double  getBuildCost ()
        {
            double _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            if (Global.isVisiting() || m_actionMode == VISIT_REPLAY_ACTION)
            {
                _loc_1 = 0;
            }
            else if (this.m_isAccelerating && this.m_accelerationEnergyCost)
            {
                _loc_1 = this.m_accelerationEnergyCost;
            }
            else if (this.m_builds <= 0)
            {
                _loc_1 = this.m_energyStart;
            }
            else if (!IS_QUEUE_ALL_BUILDS)
            {
                _loc_1 = this.m_energyPerBuild;
            }
            else
            {
                _loc_2 = this.m_finishedBuilds > 0 ? (this.m_finishedBuilds) : (1);
                _loc_3 = this.m_totalNumberOfBuilds - _loc_2;
                _loc_1 = _loc_3 * this.m_energyPerBuild;
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            double _loc_2 =0;
            boolean _loc_3 =false ;
            if (m_actionBar && m_actionBar.visible)
            {
                return null;
            }
            _loc_1 = getGameModeToolTipAction();
            if (!_loc_1)
            {
                if (!isLocked() && !Global.isVisiting() && !Global.world.isEditMode && (IS_QUEUE_ALL_BUILDS || this.m_currentState != STATE_BUILDING) && this.m_currentState != STATE_FINISHED)
                {
                    _loc_2 = this.getBuildCost();
                    switch(this.m_currentState)
                    {
                        case STATE_IDLE:
                        case STATE_BUILDING:
                        case STATE_PRE_GATE:
                        {
                            if (IS_QUEUE_ALL_BUILDS && this.m_builds > 0)
                            {
                                if (_loc_2 >= 0)
                                {
                                    _loc_3 = Global.player.checkEnergy(-_loc_2, false);
                                    if (_loc_3)
                                    {
                                        if (!this.m_isAccelerating)
                                        {
                                            _loc_1 = ZLoc.t("Main", "InstantFinishNoEnergy", {energy:_loc_2});
                                        }
                                    }
                                    else
                                    {
                                        _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                                    }
                                }
                                else
                                {
                                    _loc_1 = ZLoc.t("Main", "ClickToFinish");
                                }
                            }
                            else if (Global.player.checkEnergy(-_loc_2, false))
                            {
                                _loc_1 = ZLoc.t("Main", "ClickToBuild");
                            }
                            else
                            {
                                _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                            }
                            break;
                        }
                        case STATE_AT_GATE:
                        {
                            if (Global.player.checkEnergy(-_loc_2, false))
                            {
                                if (this.m_numberOfStages != 0)
                                {
                                    _loc_1 = ZLoc.t("Main", "ClickToFinish");
                                }
                                else
                                {
                                    _loc_1 = ZLoc.t("Main", "ClickForBuildInfo");
                                }
                            }
                            else
                            {
                                _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                            }
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
                if (Global.isVisiting())
                {
                    _loc_2 = this.getBuildCost();
                    switch(this.m_currentState)
                    {
                        case STATE_IDLE:
                        case STATE_BUILDING:
                        {
                            if (IS_QUEUE_ALL_BUILDS && this.m_builds > 0)
                            {
                                if (_loc_2 >= 0)
                                {
                                    _loc_3 = Global.player.checkEnergy(-_loc_2, false);
                                    if (_loc_3)
                                    {
                                        _loc_1 = ZLoc.t("Main", "ClickToBoost", {energy:_loc_2});
                                    }
                                    else
                                    {
                                        _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                                    }
                                }
                            }
                            else if (Global.player.checkEnergy(-_loc_2, false))
                            {
                                _loc_1 = ZLoc.t("Main", "ClickToBuild");
                            }
                            else
                            {
                                _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                            }
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        private void  doConstruction (boolean param1 =false )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_2 =1;
            _loc_3 = this.m_totalNumberOfBuilds-this.m_builds;
            if (IS_QUEUE_ALL_BUILDS)
            {
                _loc_2 = _loc_3;
            }
            _loc_4 = this.getBuildCost();
            boolean _loc_5 =false ;
            if (Global.isVisiting())
            {
                _loc_5 = true;
            }
            else
            {
                if (m_actionMode == VISIT_REPLAY_ACTION)
                {
                    _loc_5 = true;
                }
                else
                {
                    _loc_5 = Global.player.checkEnergy(-_loc_4, false);
                }
                if (this.inPopulationGate(m_actionMode != VISIT_REPLAY_ACTION))
                {
                    _loc_5 = false;
                }
            }
            if (this.m_builds >= this.m_totalNumberOfBuilds)
            {
                if (param1 && IS_QUEUE_ALL_BUILDS && !this.m_isAccelerating && !this.m_isAccelerated && _loc_5)
                {
                    this.startAcceleration();
                }
                else
                {
                    displayStatus(ZLoc.t("Main", "UnderConstruction"));
                }
            }
            else
            {
                if (Global.isVisiting())
                {
                    _loc_10 = m_numVisitorInteractions+1;
                    m_numVisitorInteractions = _loc_10;
                }
                if (_loc_5)
                {
                    _loc_6 = _loc_2;
                    if (!(param1 && _loc_2 > 0))
                    {
                        this.buildSite();
                        _loc_6 = _loc_6 - 1;
                    }
                    if (_loc_6 > 0)
                    {
                        while (_loc_6--)
                        {

                            this.m_buildQueue.push(true);
                        }
                    }
                    this.createNPCs();
                    this.updateScaffold();
                    if (_loc_2 > 0)
                    {
                        this.m_builds = this.m_builds + _loc_2;
                    }
                    Sounds.playFromSet(Sounds.SET_BUILDING_CONSTRUCTION);
                    if (m_actionMode != VISIT_REPLAY_ACTION)
                    {
                        if (Global.isVisiting())
                        {
                            _loc_7 = Global.gameSettings().getInt("friendVisitConstructionRepGain", 1);
                            _loc_8 = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                            finalizeAndAwardVisitorHelp(VisitorHelpType.CONSTRUCTIONSITE_CONSTRUCT, _loc_7, _loc_8);
                            if (this.m_builds == this.m_totalNumberOfBuilds)
                            {
                                NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_FINISHEDBUILDING);
                            }
                        }
                        else
                        {
                            doEnergyChanges(-_loc_4, this.getEnergyTrackingInfo(this.targetItem));
                            Global.player.heldEnergy = Global.player.heldEnergy + _loc_4;
                            this.m_visitingId = Global.getVisiting();
                        }
                    }
                    m_actionQueue.removeAllStates();
                    m_actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Building"), this.m_timePerBuild / 1000));
                }
                else if (!Global.player.checkEnergy(-_loc_4))
                {
                    displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
            }
            return;
        }//end

        private Array  getEnergyTrackingInfo (Item param1 )
        {
            _loc_2 = param1.name;
            Array _loc_3 =new Array("energy","expenditures","build_unknown",_loc_2 );
            if (_loc_2.indexOf("res_") >= 0)
            {
                _loc_3.put(2,  "build_house");
            }
            else if (_loc_2.indexOf("mun_") >= 0)
            {
                _loc_3.put(2,  "build_municipal");
            }
            else if (_loc_2.indexOf("bus_") >= 0)
            {
                _loc_3.put(2,  "build_business");
            }
            else if (_loc_2.indexOf("storage_") >= 0)
            {
                _loc_3.put(2,  "build_storage");
            }
            else if (_loc_2.indexOf("goods_pier") >= 0)
            {
                _loc_3.put(2,  "build_pier");
            }
            else
            {
                _loc_3.put(2,  "other");
            }
            return _loc_3;
        }//end

        private void  visitBoost ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            this.m_numBoosts++;
            this.buildSite();
            this.updateScaffold();
            if (Global.isVisiting())
            {
                _loc_1 = Global.gameSettings().getInt("friendVisitConstructionRepGain", 1);
                _loc_2 = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                finalizeAndAwardVisitorHelp(VisitorHelpType.CONSTRUCTIONSITE_ACCEL, _loc_1, _loc_2, 0);
                if (this.m_builds == this.m_totalNumberOfBuilds)
                {
                    NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_FINISHEDBUILDING);
                }
            }
            _loc_4 = m_numVisitorInteractions+1;
            m_numVisitorInteractions = _loc_4;
            m_actionQueue.removeAllStates();
            m_actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Boosting")));
            return;
        }//end

        private void  startAcceleration ()
        {
            this.m_isAccelerating = true;
            this.m_accelerateStartTime = GlobalEngine.getTimer();
            _loc_1 = this.m_totalNumberOfBuilds-this.m_finishedBuilds;
            _loc_2 = this.timePerBuild*_loc_1;
            this.m_buildTimeLeftAtAccelStart = this.m_buildStartTime + _loc_2 - this.m_accelerateStartTime;
            this.m_accelerationEnergyCost = this.getBuildCost();
            this.m_timePerBuild = 2000;
            _loc_3 = this.m_totalNumberOfBuilds-this.m_builds;
            this.enqueueBuilds(_loc_3);
            m_actionQueue.removeAllStates();
            Sounds.play("speedup");
            Sounds.playFromSet(Sounds.SET_BUILDING_CONSTRUCTION);
            m_actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Finishing"), this.m_timePerBuild / 1000));
            return;
        }//end

        private void  enqueueBuilds (int param1 )
        {
            _loc_2 = param1;
            if (_loc_2 > 0)
            {
                while (_loc_2--)
                {

                    this.m_buildQueue.push(true);
                }
            }
            if (param1 > 0)
            {
                this.m_builds = this.m_builds + param1;
            }
            this.buildSite();
            this.updateScaffold();
            return;
        }//end

        private void  setToGatedStage ()
        {
            _loc_1 = this.m_totalNumberOfBuilds;
            this.m_finishedBuilds = this.m_totalNumberOfBuilds;
            this.m_builds = _loc_1;
            this.m_stage = this.m_numberOfStages - 1;
            this.m_currentState = STATE_AT_GATE;
            setState(this.m_displayStage.get(this.m_stage));
            return;
        }//end

        private void  buildSite ()
        {
            this.m_currentState = STATE_BUILDING;
            return;
        }//end

        private boolean  checkBuildQueue ()
        {
            if (this.m_buildQueue.length >= this.m_totalNumberOfBuilds)
            {
                return false;
            }
            _loc_1 = this.m_builds+1;
            if (_loc_1 >= this.m_totalNumberOfBuilds)
            {
                return false;
            }
            return true;
        }//end

        protected Vector2  getWorkerOffsetForCurrentStage ()
        {
            Vector2 _loc_1 =new Vector2(0,0);
            if (this.m_stage <= this.m_numberOfStages)
            {
                if (this.m_displayStageNPCOffset.get(this.m_stage))
                {
                    _loc_1 =(Vector2) this.m_displayStageNPCOffset.get(this.m_stage);
                }
            }
            return _loc_1;
        }//end

        protected void  createNPCs ()
        {
            Object _loc_2 =null ;
            Array _loc_1 =new Array();
            _loc_2 = {};
            _loc_2.pos = new Vector3(0, 0);
            _loc_2.isTempWorker = false;
            _loc_1.put(_loc_1.length,  _loc_2);
            Global.world.citySim.npcManager.addConstructionWorkers(this, _loc_1);
            return;
        }//end

        private void  onBuildComplete ()
        {


             Global.world.citySim.trainManager.purchaseWelcomeTrain();


            this.m_currentState = STATE_IDLE;
            this.m_finishedBuilds++;
            if (Global.world.getObjectById(getId()) == null)
            {
                return;
            }
            this.updateBuildingProgress();
            if (this.m_buildQueue.length > 0)
            {
                this.m_buildQueue.pop();
                this.buildSite();
                return;
            }
            return;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            _loc_2 = Global.player.processRandomModifiers(this.targetItem,this,true,m_secureRands);
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                if (_loc_4.get(0) == Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_4.get(1)))
                {
                    _loc_3.push(_loc_4);
                    continue;
                }
                _loc_5 = _loc_4.get(0);
                _loc_6 = Global.gameSettings().getDooberTypeFromItemName(_loc_5);
                if (Doober.DOOBER_ITEM == _loc_6)
                {
                    _loc_3.push(_loc_4);
                }
            }
            return _loc_3;
        }//end

        public void  completeBuilding ()
        {
            ConstructionSite _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            if (Global.isTransitioningWorld)
            {
                return;
            }
            if (Global.isVisiting())
            {
                return;
            }
            if (this.inPopulationGate(m_actionMode != VISIT_REPLAY_ACTION))
            {
                return;
            }
            if (this.m_alreadyComplete)
            {
                return;
            }
            Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
            _loc_1 = ConstructionSite.FIRST_TIME_BUILD_COUPON+"_"+this.m_targetBuildingName;
            if (Global.player.hasCoupon(_loc_1))
            {
                Global.player.useCoupon(_loc_1);
                _loc_5 = ConstructionSite.FIRST_TIME_BUILD_MODIFIER_GROUP;
                m_doobersArray = Global.player.processRandomModifiers(this.targetItem, this, true, m_secureRands, _loc_5);
            }
            else
            {
                m_doobersArray = Global.player.processRandomModifiers(m_item, this, true, m_secureRands);
            }
            spawnDoobers();
            _loc_2 = newthis.m_targetBuildingClass(this.m_targetBuildingName);
            this.m_alreadyComplete = true;
            this.m_currentState = STATE_FINISHED;
            trackAction(TrackedActionType.CONSTRUCTION_COMPLETE);
            this.detach();
            this.cleanUp();
            _loc_2.setOuter(Global.world);
            _loc_2.setPosition(this.getPosition().x, this.getPosition().y);
            _loc_2.setId(this.getId());
            _loc_2.itemOwner = this.itemOwner;
            _loc_2.attach();
            _loc_2.setActive(true);
            _loc_2.onBuildingConstructionCompleted_PreServerUpdate();
            _loc_2.rotateToDirection(this.getDirection());
            Global.world.citySim.recomputePotentialPopulation(Global.world);
            Global.world.citySim.roadManager.updateRoads(_loc_2);
            if (!Global.isVisiting())
            {
                Global.player.commodities.updateCapacities();
                Global.hud.updateCommodities();
                _loc_6 = Global.world.wildernessSim.processExpansionsOnConstructionComplete(this);
                GameTransactionManager.addTransaction(new TConstructionFinish(this, _loc_6));
            }
            else
            {
                GameTransactionManager.addTransaction(new TFranchiseRefresh());
            }
            _loc_3 = Global.world.getObjectsByClass(ConstructionSite);
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_4.forceUpdateArrow();
            }
            _loc_2.onBuildingConstructionCompleted_PostServerUpdate();
            Global.world.onResourceChange(_loc_2, null, _loc_2.getPosition());
            if (_loc_2.getItem().hasFireworks)
            {
                _loc_2.fireZeMissiles();
            }
            return;
        }//end

         public Class  getCursor ()
        {
            _loc_1 = super.getCursor();
            if (this.m_currentState != STATE_FINISHED && this.m_builds < this.m_totalNumberOfBuilds)
            {
                _loc_1 = EmbeddedArt.hud_act_construction;
            }
            else if (IS_QUEUE_ALL_BUILDS)
            {
                _loc_1 = EmbeddedArt.hud_act_zap;
            }
            return _loc_1;
        }//end

         public Object  getCustomCursor ()
        {
            ASFont _loc_2 =null ;
            GlowFilter _loc_3 =null ;
            MarginBackground _loc_4 =null ;
            if (!m_customCursor)
            {
                m_customCursor = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                m_customCursorLabel = new JLabel("");
                m_customCursorHolder = new Sprite();
                Global.ui.addChild(m_customCursorHolder);
                m_customCursorWindow = new JWindow(m_customCursorHolder);
                _loc_2 = ASwingHelper.getBoldFont(16);
                _loc_3 = new GlowFilter(0, 1, 1.2, 1.2, 20, BitmapFilterQuality.HIGH);
                m_customCursorLabel.setForeground(new ASColor(16777215));
                m_customCursorLabel.setFont(_loc_2);
                m_customCursorLabel.setTextFilters(.get(_loc_3));
            }
            DisplayObject _loc_1 =null ;
            if (this.m_currentState != STATE_FINISHED && this.m_builds < this.m_totalNumberOfBuilds)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_construction();
                m_customCursorLabel.setText(this.getBuildCost().toString());
            }
            else
            {
                return null;
            }
            if (_loc_1)
            {
                _loc_4 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                m_customCursor.setBackgroundDecorator(_loc_4);
                m_customCursor.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            }
            m_customCursor.append(m_customCursorLabel);
            m_customCursorWindow.setContentPane(m_customCursor);
            ASwingHelper.prepare(m_customCursorWindow);
            m_customCursorWindow.show();
            return m_customCursorWindow;
        }//end

        private void  updateBuildingProgress ()
        {
            if (this.m_finishedBuilds % this.m_buildsPerStage == 0 && this.m_finishedBuilds != 0)
            {
                this.m_stage++;
                setState(this.m_displayStage.get(this.m_stage));
                setDisplayObjectDirty(true);
                conditionallyRedraw(true);
                if (this.m_stage >= this.m_numberOfStages)
                {
                    if (this.m_gates.get(BUILD_GATE) && this.m_gates.get(BUILD_GATE).keyCount > 0)
                    {
                        this.m_currentState = STATE_AT_GATE;
                        if (this.m_isAccelerating)
                        {
                            GameTransactionManager.addTransaction(new TConstructionFinish(this));
                            this.setToGatedStage();
                            this.updateScaffold();
                            this.m_isAccelerating = false;
                        }
                    }
                    else
                    {
                        this.completeBuilding();
                    }
                }
            }
            return;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return Curry .curry (void  (ConstructionSite param1 )
            {
                _loc_2 = null;
                if (!Global.isVisiting())
                {
                    if (param1.m_visitingId != Global.getVisiting())
                    {
                        return;
                    }
                    if (Global.world.getObjectById(param1.getId()) == null)
                    {
                        return;
                    }
                    if (m_actionMode != VISIT_REPLAY_ACTION)
                    {
                        GameTransactionManager.addTransaction(new TConstructionBuild(param1));
                    }
                    _loc_2 = param1.getBuildCost();
                    Global.player.heldEnergy = Global.player.heldEnergy - _loc_2;
                    param1.trackAction(TrackedActionType.BUILD);
                    if (param1.m_builds != param1.m_totalNumberOfBuilds)
                    {
                        param1.m_doobersArray = param1.makeDoobers();
                        param1.spawnDoobers();
                    }
                }
                param1.onBuildComplete();
                return;
            }//end
            , this);
        }//end

        public IGate  getGate (String param1 )
        {
            return this.m_gates.get(param1);
        }//end

        protected boolean  inPopulationGate (boolean param1 )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            Object _loc_8 =null ;
            int _loc_9 =0;
            _loc_2 = Global.gameSettings().getItemByName(this.targetName);
            _loc_3 = _loc_2.populationBase;
            if (_loc_3)
            {
                _loc_4 = _loc_2.populationType;
                _loc_5 = Global.world.citySim.getPopulation(_loc_4) + _loc_3;
                _loc_6 = Global.world.citySim.getPopulationCap(_loc_4);
                if (_loc_5 > _loc_6)
                {
                    if (param1 !=null)
                    {
                        _loc_7 = Global.world.citySim.getRequiredNonTotalPopulationCap(_loc_2);
                        _loc_8 = {capType:_loc_4, capNeeded:_loc_7};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE, false, null, _loc_8);
                    }
                    return true;
                }
            }
            else if (this.targetClass == Business)
            {
                _loc_9 = Math.floor(Global.world.citySim.getPopulationCap() * Global.gameSettings().getNumber("businessLimitByPopulationMax"));
                if (Global.world.citySim.getTotalBusinesses() > _loc_9)
                {
                    if (param1 !=null)
                    {
                        _loc_8 = {capType:Population.MIXED, capNeeded:Global.world.citySim.getRequiredPopulationCap(_loc_2)};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_BUSINESS, false, null, _loc_8);
                    }
                    return true;
                }
            }
            return false;
        }//end

         public void  cleanUp ()
        {
            if (this.m_scaffoldEffect)
            {
                this.m_scaffoldEffect.cleanUp();
            }
            if (this.m_gateFactory)
            {
                this.m_gateFactory.cleanUp();
            }
            this.m_gateFactory = null;
            Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
            super.cleanUp();
            return;
        }//end

         public void  sell ()
        {
            Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
            super.sell();
            return;
        }//end

         public boolean  sellSendsToInventory ()
        {
            _loc_1 = this.targetItem;
            if (_loc_1)
            {
                return _loc_1.sellSendsToInventory;
            }
            return false;
        }//end

         public void  sendToInventory ()
        {
            boolean _loc_2 =false ;
            _loc_1 = this.targetItem;
            if (_loc_1)
            {
                _loc_2 = Global.player.inventory.addItems(this.targetName, 1);
                if (_loc_2)
                {
                    Global.world.citySim.npcManager.removeAllConstructionWorkers(this);
                    GameTransactionManager.addTransaction(new TSendToInventory(this));
                    detach();
                    cleanup();
                    this.onSell();
                }
            }
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            Global.world.citySim.recomputePotentialPopulation(Global.world);
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.world.citySim.recomputePotentialPopulation(Global.world);
            return;
        }//end

        private int  calculateRefund ()
        {
            _loc_1 = this.targetItem.cost;
            double _loc_2 =0;
            if (Global.marketSaleManager.isItemOnSale(this.targetItem))
            {
                _loc_2 = Global.marketSaleManager.getDiscountPercent(this.targetItem) / 100;
            }
            return Math.ceil(_loc_1 - _loc_1 * _loc_2);
        }//end

         protected int  getSellPrice ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            if (this.targetItem.goods)
            {
                _loc_1 = Global.gameSettings().getNumber("sellBackRatio");
                _loc_2 = Global.gameSettings().getNumber("goodsToCoinRatio");
                return Math.ceil(this.targetItem.goods * (1 / _loc_2) * _loc_1);
            }
            if (this.m_refund >= 0)
            {
                return this.m_refund;
            }
            return this.calculateRefund();
        }//end

         public boolean  canBeDragged ()
        {
            _loc_1 =             !(this.targetClass == Bridge || this.targetClass == BridgePart);
            if (this.targetName == "mun_dam")
            {
                _loc_1 = false;
            }
            return _loc_1 && !(this.m_isAccelerating || this.m_currentState == STATE_BUILDING);
        }//end

         public boolean  canBeRotated ()
        {
            _loc_1 =             !(this.targetClass == Bridge || this.targetClass == BridgePart);
            if (this.targetName == "mun_dam")
            {
                _loc_1 = false;
            }
            return _loc_1 && super.canBeRotated();
        }//end

         public boolean  isNeedingRoad ()
        {
            boolean _loc_1 =false ;
            switch(this.targetClass)
            {
                case Business:
                case Mall:
                case Attraction:
                case Hotel:
                case Residence:
                case Neighborhood:
                {
                    _loc_1 = !isAdjacentToAnyRoad;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

        public boolean  isAccelerating ()
        {
            return this.m_isAccelerating;
        }//end

         public String  getActionTargetName ()
        {
            return this.m_targetBuildingName;
        }//end

         public String  getVisitReplayEquivalentActionString ()
        {
            return "build";
        }//end

         public boolean  checkPlacementRequirements (int param1 ,int param2 )
        {
            _loc_3 = new this.m_targetBuildingClass(this.m_targetBuildingName )as MapResource ;
            return _loc_3.checkPlacementRequirements(param1, param2);
        }//end

        private void  completePreGate ()
        {
            this.m_currentState = STATE_IDLE;
            return;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            if (this.m_mouseOverMessage != null && this.m_mouseOverMessage != "")
            {
                Global.ui.showTickerMessage(this.m_mouseOverMessage, false);
            }
            return;
        }//end

        protected void  setMouseOverMessage (Item param1 )
        {
            _loc_2 = param1.constructionXml;
            String _loc_3 ="";
            if (_loc_2 && _loc_2.attribute("mouseOverText").length() > 0)
            {
                _loc_3 = _loc_2.attribute("mouseOverText").get(0);
            }
            if (_loc_3 != "")
            {
                this.m_mouseOverMessage = ZLoc.t("Main", _loc_3);
            }
            return;
        }//end

         public String  getPopulationType ()
        {
            Item _loc_2 =null ;
            _loc_1 = Population.CITIZEN;
            if (this.targetClass == Residence)
            {
                _loc_2 = Global.gameSettings().getItemByName(this.targetName);
                _loc_1 = _loc_2.populationType;
            }
            return _loc_1;
        }//end

         public int  getPopulationYield ()
        {
            Item _loc_2 =null ;
            int _loc_1 =0;
            if (this.targetClass == Residence)
            {
                _loc_2 = Global.gameSettings().getItemByName(this.targetName);
                _loc_1 = _loc_2.populationBase;
            }
            return _loc_1;
        }//end

        public Population  getPopulation ()
        {
            Item _loc_2 =null ;
            String _loc_3 =null ;
            int _loc_4 =0;
            Population _loc_1 =null ;
            if (this.targetClass == Residence)
            {
                _loc_2 = Global.gameSettings().getItemByName(this.targetName);
                _loc_3 = _loc_2.populationType;
                _loc_4 = _loc_2.populationBase;
                _loc_1 = new Population(_loc_4, _loc_3);
            }
            return _loc_1;
        }//end

        public boolean  isBranded ()
        {
            return this.targetItem.isBrandedBusiness;
        }//end

        public static ConstructionSite  createConstructionSite (String param1 ,Class param2 )
        {
            _loc_3 = Global.gameSettings().getItemByName(param1);
            param2 = param2 || ItemClassDefinitions.getClassByItem(_loc_3);
            ConstructionSite _loc_4 =new ConstructionSite(_loc_3.construction );
            _loc_4.initializeSite(_loc_3, param2);
            _loc_4.itemOwner = Global.player.uid;
            _loc_4.setOuter(Global.world);
            return _loc_4;
        }//end

    }




