package Classes.actions;

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
import Classes.sim.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
import root.Global;

import com.xinghai.Debug;

    public class ActionNavigate extends NPCAction
    {
        protected double m_roadSize ;
        protected MapResource m_source ;
        protected MapResource m_target ;
        protected Array m_path ;
        protected boolean m_startRightTurn =false ;
        protected boolean m_endRightTurn =false ;
        protected double m_offset ;
        protected RoadGraph m_graph ;
        protected BaseAction m_fallbackAction ;
        protected boolean m_teleportOnFailure =false ;
        protected boolean m_navigationFailed =false ;
        protected double m_pathTimeRemaining =0;
        protected double m_velocityWalk =0;
        protected int m_pathType =1;
        public static  Vector3 V_UP =new Vector3(0,0,1);
        private static int m_experimentNPCThinning =-1;

        public  ActionNavigate (NPC param1 ,MapResource param2 ,MapResource param3 )
        {
            super(param1);
            this.m_source = param3;
            this.m_target = param2;

            this.m_roadSize = Global.world.citySim.roadManager.getRoadSize().x;
            this.m_offset = param1.roadOffset * this.m_roadSize * (param1.isRightSideNav ? (1) : (-1));
            if (param1 instanceof Vehicle)
            {
                this.m_pathType = RoadManager.PATH_ROAD_ONLY;
            }
            this.m_velocityWalk = param1.velocityWalk;
            if (m_experimentNPCThinning < 0)
            {
                m_experimentNPCThinning = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_NPC_THINNING);
            }
            return;
        }//end

        public ActionNavigate  setFallbackAction (BaseAction param1 )
        {
            this.m_fallbackAction = param1;
            return this;
        }//end

        public ActionNavigate  setTeleportOnFailure (boolean param1 )
        {
            this.m_teleportOnFailure = param1;
            return this;
        }//end

        public ActionNavigate  setPathType (int param1 )
        {
            this.m_pathType = param1;
            return this;
        }//end

        protected double  navigationVelocity ()
        {
            return this.m_velocityWalk;
        }//end

        public RoadGraph  graph ()
        {
            return this.m_graph;
        }//end

        public MapResource  target ()
        {
            return this.m_target;
        }//end

         public void  update (double param1 )
        {
            PathElement _loc_8 =null ;
            Vector3 _loc_9 =null ;
            if (!m_experimentNPCThinning)
            {
                if (param1 > 0.5)
                {
                    param1 = 0.5;
                }
            }
            if (!this.m_path || this.m_path.length == 0)
            {
                if (this.m_navigationFailed)
                {
                    this.doNavigationFailed();
                }
                m_npc.getStateMachine().removeState(this);
                return;
            }
            if (Global.world.citySim.roadManager.isShowingOverlay)
            {
                m_npc.m_navDbgPath = this.m_path;
            }
            else
            {
                m_npc.m_navDbgPath = null;
            }
            this.m_pathTimeRemaining = this.m_pathTimeRemaining - param1;
            if (m_experimentNPCThinning && m_npc.alwaysCulled)
            {
                if (this.m_pathTimeRemaining <= 0)
                {
                    m_npc.getStateMachine().removeState(this);
                }
                return;
            }
            _loc_2 = param1*this.navigationVelocity ;
            boolean _loc_3 =false ;
            Vector3 _loc_4 =null ;
            do
            {

                Vector3.free(_loc_4);
                _loc_8 = this.m_path.get(0);
                _loc_9 = _loc_8.offsetPosition;
                _loc_4 = _loc_9.subtract(m_npc.getPositionNoClone());
                _loc_3 = _loc_4.length() > _loc_2;
                if (!_loc_3)
                {
                    m_npc.setPosition(_loc_9.x, _loc_9.y, _loc_9.z);
                    _loc_2 = _loc_2 - _loc_4.length();
                    this.m_path.shift();
                    this.maybePerformAmbientAction();
                }
                if (this.m_path.length == 0)
                {
                    _loc_3 = true;
                }
            }while (!_loc_3)
            _loc_5 = _loc_4.length()>0? (_loc_4.normalize(_loc_2)) : (Vector3.alloc());
            _loc_6 = m_npc.getPositionNoClone().add(_loc_5);
            m_npc.setPosition(_loc_6.x, _loc_6.y, _loc_6.z);
            _loc_7 = getMovementDirection(_loc_4);
            if (getMovementDirection(_loc_4) != m_npc.getDirection())
            {
                if (m_npc instanceof Vehicle)
                {
                    ((Vehicle)m_npc).startTurnAnimation(_loc_7);
                }
                else
                {
                    m_npc.setDirection(_loc_7);
                    m_npc.setState(m_npc.getState());
                }
            }
            m_npc.conditionallyReattach();
            Vector3.free(_loc_4);
            Vector3.free(_loc_5);
            Vector3.free(_loc_6);
            return;
        }//end

        protected void  updatePath (boolean param1 )
        {
            Array _loc_4 =null ;
            WorldObject _loc_5 =null ;
            Box3D _loc_6 =null ;
            boolean _loc_7 =false ;
            _loc_2 = m_npc.getPositionNoClone();
            if (this.m_source == null || param1)
            {
                _loc_4 = Global.world.getCollisionMap().getObjectsByPosition(_loc_2.x, _loc_2.y);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    if (_loc_5 instanceof MapResource)
                    {
                        this.m_source =(MapResource) _loc_5;
                        break;
                    }
                }
            }
            if (this.m_source == null)
            {
                _loc_6 = new Box3D(new Vector3((_loc_2.x - 1), (_loc_2.y - 1)), new Vector3(2, 2));
                _loc_4 = Global.world.getCollisionMap().getIntersectingObjects(_loc_6);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    if (_loc_5 instanceof MapResource)
                    {
                        this.m_source =(MapResource) _loc_5;
                        break;
                    }
                }
            }
            Object _loc_3 =null ;
            if (m_npc.alwaysCulled)
            {
                if (Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_INVISIBLE_NPCS))
                {
                    _loc_3 = Global.world.citySim.roadManager.createInvisiblePeepPath(this.m_source, this.m_target);
                }
            }
            if (_loc_3 == null)
            {
                _loc_7 = m_npc instanceof Vehicle;
                _loc_3 = Global.world.citySim.roadManager.findPath(this.m_source, this.m_target, m_npc.getPathTypeToBusiness(), _loc_7);
            }
            if (_loc_3 == null)
            {
                _loc_3 = {path:.get(new PathElement(m_npc.getPosition())), graph:null, buildingScore:m_npc.pathBuildingScore};
                this.m_navigationFailed = true;
            }
            m_npc.pathBuildingScore = _loc_3.get("buildingScore");
            m_npc.m_navRandom = false;
            m_npc.m_navDbgPath2 = null;
            this.m_graph = _loc_3.graph;
            this.m_path = this.makeOffsetPath(_loc_3.path, null, !m_npc.isVehicle);
            this.debugPrintPath();
            this.doPathCleanup();
            this.computeTimeToDestination();
            return;
        }//end

        private Vector3  adjustEndpoints (boolean param1 ,Vector3 param2 ,Vector3 param3 ,Vector3 param4 )
        {
            _loc_5 = this.m_roadSize;
            if (param1 !=null)
            {
                if (param3.x == param4.x)
                {
                    if (Math.abs(param3.y - param2.y) < _loc_5)
                    {
                        param3.y = param2.y;
                    }
                }
                else if (param3.y == param4.y)
                {
                    if (Math.abs(param3.x - param2.x) < _loc_5)
                    {
                        param3.x = param2.x;
                    }
                }
            }
            else if (param3.x == param2.x)
            {
                if (Math.abs(param3.y - param4.y) < _loc_5)
                {
                    param3.y = param4.y;
                }
            }
            else if (param3.y == param2.y)
            {
                if (Math.abs(param3.x - param4.x) < _loc_5)
                {
                    param3.x = param4.x;
                }
            }
            return param3;
        }//end

        private void  checkEndpointTurns (Array param1 )
        {
            Vector3 _loc_9 =null ;
            PathElement _loc_10 =null ;
            Vector3 _loc_11 =null ;
            this.m_startRightTurn = false;
            this.m_endRightTurn = false;
            int _loc_2 =0;
            _loc_3 = (PathElement)]
            _loc_4 = _loc_3.basePosition;
            _loc_5 = _loc_3.basePosition;
            _loc_6 = _loc_4;
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            while (_loc_2 < (param1.length - 1))
            {

                _loc_10 =(PathElement) param1.get((_loc_2 + 1));
                _loc_6 = _loc_10.basePosition;
                _loc_11 = _loc_6.subtract(_loc_4);
                if (!_loc_8 && _loc_7 && _loc_3.type == PathElement.TYPE_ROAD)
                {
                    _loc_9 = _loc_4.subtract(_loc_5).cross(_loc_6.subtract(_loc_4));
                    this.m_startRightTurn = _loc_9.z < 0;
                    _loc_8 = true;
                }
                if (_loc_8 && (_loc_2 == param1.length - 2 || _loc_10.type == PathElement.TYPE_NONROAD) && _loc_7)
                {
                    _loc_9 = _loc_4.subtract(_loc_5).cross(_loc_6.subtract(_loc_4));
                    this.m_endRightTurn = _loc_9.z < 0;
                    break;
                }
                if (_loc_4 != _loc_6)
                {
                    _loc_5 = _loc_4;
                    _loc_7 = true;
                    _loc_4 = _loc_6;
                    _loc_3 = _loc_10;
                }
                _loc_2++;
            }
            return;
        }//end

        protected Array  makeOffsetPath (Array param1 ,Vector3 param2 ,boolean param3 )
        {
            PathElement _loc_12 =null ;
            Vector3 _loc_13 =null ;
            Vector3 _loc_14 =null ;
            double _loc_15 =0;
            Vector3 _loc_16 =null ;
            boolean _loc_17 =false ;
            Array _loc_4 =new Array();
            if (param1.length < 3)
            {
                return param1;
            }
            _loc_5 = param2;
            if (param2 == null)
            {
                _loc_5 = ((PathElement)param1.get(1)).basePosition.subtract(((PathElement)param1.get(0)).basePosition);
            }
            if (param3)
            {
                this.checkEndpointTurns(param1);
                if (this.m_offset < 0 && this.m_startRightTurn)
                {
                    this.m_offset = this.m_offset * -1;
                }
                if (this.m_offset > 0 && !this.m_startRightTurn)
                {
                    this.m_offset = this.m_offset * -1;
                }
            }
            int _loc_6 =0;
            _loc_7 = (PathElement)param1.get(_loc_6);
            _loc_8 = ((PathElement)param1.get(_loc_6)).basePosition ;
            _loc_9 = ((PathElement)param1.get(_loc_6)).basePosition ;
            _loc_10 = _loc_5;
            _loc_11 = param3;
            while (_loc_6 < (param1.length - 1))
            {

                _loc_12 =(PathElement) param1.get((_loc_6 + 1));
                _loc_13 = _loc_12.basePosition;
                _loc_14 = _loc_13.subtract(_loc_8);
                if (_loc_14.length() > 0)
                {
                    if (_loc_5.length() == 0)
                    {
                        _loc_5 = _loc_14;
                    }
                    if ((_loc_4.length == 1 || _loc_6 == param1.length - 2) && param3)
                    {
                        _loc_8 = this.adjustEndpoints(_loc_4.length == 1, _loc_9, _loc_8, _loc_13);
                    }
                    _loc_15 = _loc_5.dot(_loc_14);
                    if (_loc_6 == 0 && param3)
                    {
                        _loc_4.push(_loc_7);
                    }
                    else if (_loc_7.type == PathElement.TYPE_NONROAD)
                    {
                        _loc_4.push(_loc_7);
                    }
                    else if (_loc_4.length == 1 && param3)
                    {
                        _loc_4.push(this.getOffsetPoint(_loc_8, _loc_14, this.m_offset, 0));
                    }
                    else if (_loc_12.type == PathElement.TYPE_NONROAD && !m_npc.isVehicle)
                    {
                        _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, 0));
                    }
                    else if (_loc_15 != 0)
                    {
                        if (_loc_15 > 0)
                        {
                            if (_loc_11)
                            {
                                _loc_4.push(this.getOffsetPoint(_loc_8, _loc_14, this.m_offset, 0));
                            }
                            else
                            {
                                _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, 0));
                            }
                            if (param3 && this.m_startRightTurn != this.m_endRightTurn)
                            {
                                this.m_offset = this.m_offset * -1;
                                if (_loc_11)
                                {
                                    _loc_4.push(this.getOffsetPoint(_loc_8, _loc_14, this.m_offset, 0));
                                }
                                else
                                {
                                    _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, 0));
                                }
                                this.m_startRightTurn = this.m_endRightTurn;
                            }
                        }
                        else
                        {
                            _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, this.m_offset));
                            _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5.scale(-1), this.m_offset, -this.m_offset));
                        }
                    }
                    else
                    {
                        _loc_16 = _loc_5.cross(_loc_14);
                        _loc_17 = _loc_16.z > 0;
                        _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, _loc_17 ? (this.m_offset) : (-this.m_offset)));
                    }
                    _loc_11 = _loc_7.type == PathElement.TYPE_NONROAD;
                    _loc_10 = _loc_5;
                    _loc_9 = _loc_8;
                    _loc_7 = _loc_12;
                    _loc_8 = _loc_13;
                    _loc_5 = _loc_14;
                }
                _loc_6++;
            }
            if (!m_npc.isVehicle)
            {
                _loc_4.push((PathElement)param1.get((param1.length - 1)));
            }
            else
            {
                _loc_4.push(this.getOffsetPoint(_loc_8, _loc_5, this.m_offset, 0));
            }
            return _loc_4;
        }//end

        protected void  doPathCleanup ()
        {
            int _loc_1 =0;
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            int _loc_4 =0;
            Vector3 _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            Vector3 _loc_8 =null ;
            Vector3 _loc_9 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            Vector3 _loc_12 =null ;
            int _loc_13 =0;
            if (this.m_path.length >= 2)
            {
                _loc_1 = 0;
                _loc_2 = m_npc.getPositionNoClone();
                _loc_3 = ((PathElement)this.m_path.get(0)).offsetPosition.subtract(_loc_2);
                _loc_4 = 1;
                while (_loc_4 < this.m_path.length())
                {

                    _loc_5 = ((PathElement)this.m_path.get(_loc_4)).offsetPosition.subtract(_loc_2);
                    if (_loc_3.length() > _loc_5.length())
                    {
                        _loc_1 = _loc_4;
                    }
                    _loc_4++;
                }
                if (_loc_1 > 0)
                {
                    this.m_path.splice(0, _loc_1);
                }
            }
            if (this.m_path.length >= 2)
            {
                _loc_6 = this.m_path.length - 1;
                _loc_7 = this.m_path.length - 2;
                _loc_8 = ((PathElement)this.m_path.get(_loc_6)).offsetPosition;
                _loc_9 = ((PathElement)this.m_path.get(_loc_7)).offsetPosition.subtract(_loc_8);
                _loc_10 = 0;
                while (_loc_10 < (_loc_6 - 1))
                {

                    _loc_12 = ((PathElement)this.m_path.get(_loc_10)).offsetPosition.subtract(_loc_8);
                    if (_loc_12.length() < _loc_9.length())
                    {
                        _loc_7 = _loc_10;
                    }
                    _loc_10++;
                }
                if (_loc_7++ < _loc_6)
                {
                    _loc_13 = _loc_6 - _loc_7++;
                    this.m_path.splice(_loc_11, _loc_13);
                }
            }
            return;
        }//end

        protected PathElement  getOffsetPoint (Vector3 param1 ,Vector3 param2 ,double param3 ,double param4 ,int param5 =0)
        {
            _loc_6 = param2.cross(V_UP).normalize(param3);
            _loc_7 = param1.add(_loc_6);
            if (param4 != 0)
            {
                _loc_7 = _loc_7.add(param2.normalize(param4));
            }
            return new PathElement(param1, _loc_7, param5);
        }//end

        protected void  doNavigationFailed ()
        {
            Vector3 _loc_1 =null ;
            if (this.m_teleportOnFailure)
            {
                _loc_1 = this.m_target.getHotspot();
                m_npc.setPosition(_loc_1.x, _loc_1.y);
            }
            if (this.m_fallbackAction)
            {
                m_npc.getStateMachine().removeAllStates();
                m_npc.getStateMachine().addActions(this.m_fallbackAction);
            }
            return;
        }//end

        protected void  maybePerformAmbientAction ()
        {
            _loc_1 = GlobalEngine.getTimer();
            if (m_npc.performsAmbientActions && m_npc.nextAmbientActionStart < _loc_1)
            {
                m_npc.getStateMachine().addState(this.makeAmbientAction());
                m_npc.randomizeNextAmbientActionStart();
            }
            return;
        }//end

        protected NPCAction  makeAmbientAction ()
        {
            return new ActionPlayAnimation(m_npc, "idle", 3);
        }//end

        protected void  debugPrintPath ()
        {
            return;
        }//end

         public void  enter ()
        {
            PathElement _loc_1 =null ;
            Vector3 _loc_2 =null ;
            int _loc_3 =0;
            Vehicle _loc_4 =null ;
            super.enter();
            this.updatePath(false);
            if (m_npc instanceof Vehicle && this.m_path && this.m_path.length > 1 && this.m_path.get(1) != null)
            {
                _loc_1 =(PathElement) this.m_path.get(1);
                _loc_2 = _loc_1.basePosition.subtract(m_npc.getPositionNoClone());
                _loc_3 = getMovementDirection(_loc_2);
                m_npc.setDirection(_loc_3);
                m_npc.setState(m_npc.getState());
                _loc_4 =(Vehicle) m_npc;
                _loc_4.startTurnAnimation(_loc_3);
            }
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.updatePath(true);
            return;
        }//end

        protected void  computeTimeToDestination ()
        {
            PathElement _loc_1 =null ;
            PathElement _loc_2 =null ;
            double _loc_3 =0;
            if (m_experimentNPCThinning)
            {
                this.m_pathTimeRemaining = 0;
                if (this.m_path && this.m_path.length > 0)
                {
                    _loc_1 = this.m_path.get(0);
                    _loc_2 = this.m_path.get((this.m_path.length - 1));
                    _loc_3 = Math.abs(_loc_2.offsetPosition.x - _loc_1.basePosition.x) + Math.abs(_loc_2.offsetPosition.y - _loc_1.basePosition.y);
                    this.m_pathTimeRemaining = _loc_3 / this.navigationVelocity;
                }
            }
            else
            {
                this.m_pathTimeRemaining = Global.world.citySim.roadManager.getRoadSize().x * (this.m_path.length - 1) / this.navigationVelocity;
            }
            return;
        }//end

        public double  getSecondsLeft ()
        {
            return this.m_pathTimeRemaining;
        }//end

        public static int  getMovementDirection (Vector3 param1 ,double param2)
        {
            if (Math.abs(param1.x) > Math.abs(param1.y))
            {
                if (param1.x > param2)
                {
                    return Constants.DIRECTION_NE;
                }
                if (param1.x < -param2)
                {
                    return Constants.DIRECTION_SW;
                }
            }
            else
            {
                if (param1.y > param2)
                {
                    return Constants.DIRECTION_NW;
                }
                if (param1.y < -param2)
                {
                    return Constants.DIRECTION_SE;
                }
            }
            return Constants.DIRECTION_MAX;
        }//end

    }




