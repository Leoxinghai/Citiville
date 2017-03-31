package Classes.sim;

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
import Classes.util.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;

import com.adobe.utils.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class RoadManager implements IGameWorldStateObserver
    {
        protected  int MIN_RANDOM_ROAD_GRAPH_LEN =5;
        protected int m_endpointInfluence ;
        protected Vector3 m_roadSize ;
        protected Vector3 m_sidewalkSize ;
        protected Array m_roads ;
        protected Array m_graphs ;
        protected GameWorld m_world ;
        protected int m_x ;
        protected int m_y ;
        protected int m_width ;
        protected int m_height ;
        protected Sprite m_roadOverlay ;
        protected Shape m_dbgShape ;
        protected boolean m_isStatSent =false ;
        private boolean m_usePathFindingCache =false ;
        private LRUCache m_findPathCache =null ;
        public static  int PATH_ROAD_ONLY =0;
        public static  int PATH_TO_FRONT_ENTRANCE =1;
        public static  int PATH_FULL =2;
        public static  double SIDEWALK_WIDTH =1;
        public static  double SIDEWALK_HEIGHT =1;
        public static  double ROAD_WIDTH =3;
        public static  double ROAD_HEIGHT =3;
        private static  int MAX_CACHE_SIZE =500;

        public  RoadManager (GameWorld param1 )
        {
            this.m_world = param1;
            _loc_2 = this.m_world.getWorldRect ();
            this.m_x = _loc_2.x;
            this.m_y = _loc_2.y;
            this.m_width = _loc_2.width;
            this.m_height = _loc_2.height;
            this.m_endpointInfluence = Global.gameSettings().getAttribute("carPathEndpointInfluence");
            param1.addObserver(this);
            this.m_usePathFindingCache = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMOIZED_FINDPATH);
            if (this.usePathFindingCache)
            {
                this.m_findPathCache = new LRUCache(MAX_CACHE_SIZE);
            }
            return;
        }//end

        private boolean  usePathFindingCache ()
        {
            return this.m_usePathFindingCache;
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
            this.updateAllRoadTiles();
            this.regenerateRoads();
            this.regenerateNeighborhoods();
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            if (this.usePathFindingCache)
            {
                this.m_findPathCache = new LRUCache(MAX_CACHE_SIZE);
            }
            return;
        }//end

        protected void  dbgVerifyConnectivity ()
        {
            MapResource _loc_3 =null ;
            _loc_1 =Global.world.getObjects ();
            int _loc_2 =0;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                if (_loc_3 == null)
                {
                    continue;
                }
                if (!_loc_3.isRoadVerifiable)
                {
                    continue;
                }
                if (_loc_3.isAdjacentToAnyRoad == _loc_3.svrIsAdjacentToAnyRoad)
                {
                    _loc_2++;
                    continue;
                }
                if (_loc_3.isAdjacentToAnyRoad)
                {
                    continue;
                }
            }
            return;
        }//end

        public Vector3  getRoadSize ()
        {
            if (this.m_roadSize)
            {
                return this.m_roadSize.clone();
            }
            return null;
        }//end

        public Vector3  getSidewalkSize ()
        {
            if (this.m_sidewalkSize)
            {
                return this.m_sidewalkSize.clone();
            }
            return null;
        }//end

        public Vector3  getPathSize (RoadDef param1 )
        {
            if (param1.isSidewalk)
            {
                return this.getSidewalkSize();
            }
            return this.getRoadSize();
        }//end

        public void  updateRoads (MapResource param1 )
        {
            if (param1 instanceof Road || param1 instanceof Sidewalk)
            {
                this.regenerateRoads();
            }
            this.regenerateNeighborhoods();
            if (this.isShowingOverlay)
            {
                this.showOverlay(false);
                this.showOverlay(true);
            }
            return;
        }//end

        public void  updateRoadTiles (Array param1 )
        {
            double _loc_2 =0;
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                param1.get(_loc_2).clearAdjacent();
                _loc_2 = _loc_2 + 1;
            }
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                param1.get(_loc_2).calculateRoadConnectivity1();
                _loc_2 = _loc_2 + 1;
            }
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                param1.get(_loc_2).calculateRoadConnectivity2();
                _loc_2 = _loc_2 + 1;
            }
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                param1.get(_loc_2).calculateRoadConnectivity3();
                _loc_2 = _loc_2 + 1;
            }
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                param1.get(_loc_2).calculateRoadImage();
                param1.get(_loc_2).setState(param1.get(_loc_2).getState());
                _loc_2 = _loc_2 + 1;
            }
            return;
        }//end

        public void  updateAllRoadTiles ()
        {
            Global.world.citySim.trainManager.generateCrossings();
            _loc_1 =Global.world.getObjectsByClass(Road );
            this.updateRoadTiles(_loc_1);
            return;
        }//end

        public Road  findRandomRoad ()
        {
            RoadDef _loc_3 =null ;
            RoadDef _loc_4 =null ;
            _loc_1 = this.findRandomRoadGraph ();
            if (_loc_1 == null)
            {
                return null;
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < _loc_1.defs.size(); i0++)
            {
            	_loc_3 = _loc_1.defs.get(i0);

                if (_loc_3.road != null)
                {
                    _loc_2.push(_loc_3);
                }
            }
            _loc_4 = MathUtil.randomElement(_loc_2);
            return _loc_4 != null ? (_loc_4.road) : (null);
        }//end

        public Road  findRandomRoadNearEdges (Road param1)
        {
            RoadDef _loc_7 =null ;
            int _loc_8 =0;
            RoadGraph _loc_9 =null ;
            RoadDef _loc_10 =null ;
            double _loc_11 =0;
            _loc_2 =Global.world.getWorldRect ();
            _loc_3 = new Vector3(_loc_2.width /2+_loc_2.left ,_loc_2.height /2+_loc_2.top );
            Array _loc_4 =null ;
            RoadDef _loc_5 =null ;
            if (param1 !=null)
            {
                if (param1.positionX < this.m_x || param1.positionY < this.m_y || param1.positionX >= this.m_x + this.m_width || param1.positionY >= this.m_y + this.m_height)
                {
                    return null;
                }
                _loc_5 = this.m_roads.get(param1.positionX).get(param1.positionY);
                _loc_4 = _loc_5.graph.defs;
            }
            else
            {
                _loc_4 = new Array();
                for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
                {
                		_loc_9 = this.m_graphs.get(i0);

                    for(int i0 = 0; i0 < _loc_9.defs.size(); i0++)
                    {
                    		_loc_10 = _loc_9.defs.get(i0);

                        if (_loc_10.road && !_loc_10.road.isBeingMoved())
                        {
                            _loc_4.push(_loc_10);
                        }
                    }
                }
            }
            if (!_loc_4 || _loc_4.length <= 0)
            {
                return null;
            }
            _loc_6 = new Array ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_7 = _loc_4.get(i0);

                _loc_11 = 0;
                if (_loc_7.road != null)
                {
                    _loc_11 = this.getWeight(_loc_7, _loc_5, _loc_3);
                    if (_loc_7.graph.size < this.MIN_RANDOM_ROAD_GRAPH_LEN || _loc_7 == _loc_5)
                    {
                        _loc_11 = 0;
                    }
                }
                _loc_6.push(_loc_11);
            }
            _loc_8 = MathUtil.randomIndexWeighed(_loc_6);
            if (_loc_8 < 0 || _loc_8 >= _loc_4.length())
            {
                return null;
            }
            return _loc_4.get(_loc_8).road;
        }//end

        public MapResource  findRandomWalkableTile (MapResource param1)
        {
            RoadGraph _loc_5 =null ;
            RoadDef _loc_6 =null ;
            Array _loc_2 =null ;
            RoadDef _loc_3 =null ;
            if (param1 !=null)
            {
                if (param1.positionX < this.m_x || param1.positionY < this.m_y || param1.positionX >= this.m_x + this.m_width || param1.positionY >= this.m_y + this.m_height)
                {
                    return param1;
                }
                _loc_3 = this.m_roads.get(param1.positionX).get(param1.positionY);
                _loc_2 = _loc_3.graph.defs;
            }
            else
            {
                _loc_2 = new Array();
                for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
                {
                	_loc_5 = this.m_graphs.get(i0);

                    for(int i0 = 0; i0 < _loc_5.defs.size(); i0++)
                    {
                    	_loc_6 = _loc_5.defs.get(i0);

                        if (_loc_6.tile && !_loc_6.tile.isBeingMoved())
                        {
                            _loc_2.push(_loc_6);
                        }
                    }
                }
            }
            if (!_loc_2 || _loc_2.length <= 0)
            {
                return param1;
            }
            _loc_4 = MathUtil.randomElement(_loc_2);
            return MathUtil.randomElement(_loc_2) != null ? (_loc_4.tile) : (param1);
        }//end

        public MapResource  findRandomWalkableTileNearEdges (MapResource param1)
        {
            Vector3 _loc_6 =null ;
            RoadDef _loc_8 =null ;
            int _loc_9 =0;
            RoadGraph _loc_10 =null ;
            RoadDef _loc_11 =null ;
            double _loc_12 =0;
            _loc_2 =Global.world.getWorldRect ();
            _loc_3 = new Vector3(_loc_2.width /2+_loc_2.left ,_loc_2.height /2+_loc_2.top );
            Array _loc_4 =null ;
            RoadDef _loc_5 =null ;
            if (param1 && (param1 instanceof Road || param1 instanceof Sidewalk))
            {
                _loc_6 = param1.getPosition();
                if (_loc_6.x < this.m_x || _loc_6.y < this.m_y || _loc_6.x >= this.m_x + this.m_width || _loc_6.y >= this.m_y + this.m_height)
                {
                    return null;
                }
                _loc_5 = this.m_roads.get(_loc_6.x).get(_loc_6.y);
                _loc_4 = _loc_5.graph.defs;
            }
            else
            {
                _loc_4 = new Array();
                for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
                {
                	_loc_10 = this.m_graphs.get(i0);

                    for(int i0 = 0; i0 < _loc_10.defs.size(); i0++)
                    {
                    	_loc_11 = _loc_10.defs.get(i0);

                        if (_loc_11.tile && !_loc_11.tile.isBeingMoved())
                        {
                            _loc_4.push(_loc_11);
                        }
                    }
                }
            }
            if (!_loc_4 || _loc_4.length <= 0)
            {
                return null;
            }
            _loc_7 = new Array ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_8 = _loc_4.get(i0);

                _loc_12 = 0;
                if (_loc_8.tile)
                {
                    _loc_12 = this.getWeight(_loc_8, _loc_5, _loc_3);
                    if (_loc_8.graph.size < this.MIN_RANDOM_ROAD_GRAPH_LEN || _loc_8 == _loc_5)
                    {
                        _loc_12 = 0;
                    }
                }
                _loc_7.push(_loc_12);
            }
            _loc_9 = MathUtil.randomIndexWeighed(_loc_7);
            if (_loc_9 < 0 || _loc_9 >= _loc_4.length())
            {
                return null;
            }
            return _loc_4.get(_loc_9).tile;
        }//end

        protected double  getWeight (RoadDef param1 ,RoadDef param2 ,Vector3 param3 )
        {
            _loc_8 = null;
            Vector3 _loc_9 =null ;
            _loc_4 = param1.tile.getPositionNoClone ().subtract(param3 );
            _loc_5 = param1.tile.getPositionNoClone ().subtract(param3 ).dot(param1.tile.getPositionNoClone ().subtract(param3 ));
            Vector3.free(_loc_4);
            if (param2)
            {
                _loc_9 = param1.tile.getPositionNoClone().subtract(param2.tile.getPositionNoClone());
                _loc_5 = _loc_5 + _loc_9.dot(_loc_9);
                Vector3.free(_loc_9);
            }
            int _loc_6 =0;
            _loc_7 = param1.roads ;
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            	_loc_8 = _loc_7.get(i0);

                if (_loc_8)
                {
                    _loc_6++;
                }
            }
            _loc_5 = _loc_5 + Math.pow(this.m_endpointInfluence, 4 - _loc_6);
            return _loc_5;
        }//end

        public Road  findClosestRoad (Vector3 param1 )
        {
            _loc_2 = this.findClosestRoadDef(param1 ,true );
            return _loc_2 != null ? (_loc_2.road) : (null);
        }//end

        public MapResource  findClosestWalkableTile (Vector3 param1 ,Function param2 )
        {
            _loc_3 = this.findClosestRoadDef(param1 ,false ,param2 );
            return _loc_3.road ? (_loc_3.road) : (_loc_3.sidewalk);
        }//end

        public MapResource  findClosestWalkableTileInGraph (Vector3 param1 ,RoadGraph param2 )
        {
            RoadDef _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            Vector3 _loc_8 =null ;
            double _loc_9 =0;
            RoadDef _loc_3 =null ;
            _loc_4 =(double).POSITIVE_INFINITY ;
            for(int i0 = 0; i0 < param2.defs.size(); i0++)
            {
            		_loc_5 = param2.defs.get(i0);

                _loc_6 = _loc_5.isSidewalk ? (SIDEWALK_WIDTH / 2) : (this.m_roadSize.x / 2);
                _loc_7 = _loc_5.isSidewalk ? (SIDEWALK_HEIGHT / 2) : (this.m_roadSize.y / 2);
                _loc_8 = new Vector3(_loc_5.x + _loc_6, _loc_5.y + _loc_7);
                _loc_9 = param1.subtract(_loc_8).length();
                if (_loc_9 < _loc_4)
                {
                    _loc_3 = _loc_5;
                    _loc_4 = _loc_9;
                }
            }
            return _loc_3 ? (_loc_3.road ? (_loc_3.road) : (_loc_3.sidewalk)) : (null);
        }//end

        public Array  findAllGraphsForWalkableTile (MapResource param1 )
        {
            RoadGraph _loc_3 =null ;
            RoadDef _loc_4 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            	_loc_3 = this.m_graphs.get(i0);

                for(int i0 = 0; i0 < _loc_3.defs.size(); i0++)
                {
                	_loc_4 = _loc_3.defs.get(i0);

                    if (_loc_4.road == param1 || _loc_4.sidewalk == param1)
                    {
                        _loc_2.push(_loc_3);
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

        public Array  findClosestRoadNeighbors (Vector3 param1 )
        {
            _loc_2 = this.findClosestRoadDef(param1 ,true );
            if (_loc_2 == null)
            {
                return null;
            }
            return _loc_2.roads.map(RoadDef.extractRoad);
        }//end

        public RoadDef  findClosestRoadDef (Vector3 param1 ,boolean param2 ,Function param3 =null )
        {
            RoadGraph _loc_6 =null ;
            RoadDef _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            Vector3 _loc_10 =null ;
            double _loc_11 =0;
            RoadDef _loc_4 =null ;
            _loc_5 =(double).POSITIVE_INFINITY ;
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_6 = this.m_graphs.get(i0);

                for(int i0 = 0; i0 < _loc_6.defs.size(); i0++)
                {
                	_loc_7 = _loc_6.defs.get(i0);

                    if (param2 && _loc_7.road == null)
                    {
                        continue;
                    }
                    if (param3 != null)
                    {
                        if (!param3(_loc_7))
                        {
                            continue;
                        }
                    }
                    _loc_8 = _loc_7.isSidewalk ? (SIDEWALK_WIDTH / 2) : (this.m_roadSize.x / 2);
                    _loc_9 = _loc_7.isSidewalk ? (SIDEWALK_HEIGHT / 2) : (this.m_roadSize.y / 2);
                    _loc_10 = new Vector3(_loc_7.x + _loc_8, _loc_7.y + _loc_9);
                    _loc_11 = param1.subtract(_loc_10).length();
                    if (_loc_11 < _loc_5)
                    {
                        _loc_4 = _loc_7;
                        _loc_5 = _loc_11;
                    }
                }
            }
            return _loc_4;
        }//end

        public boolean  hasWalkableRoad ()
        {
            RoadGraph _loc_3 =null ;
            if (this.m_graphs.length == 0)
            {
                return false;
            }
            Array _loc_1 =new Array();
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_3 = this.m_graphs.get(i0);

                if (!_loc_3.hasRoads)
                {
                    continue;
                }
                if (_loc_3.size > 1)
                {
                    return true;
                }
            }
            return false;
        }//end

        protected RoadGraph  findRandomRoadGraph ()
        {
            RoadGraph _loc_3 =null ;
            int _loc_4 =0;
            if (this.m_graphs.length == 0)
            {
                return null;
            }
            Array _loc_1 =new Array();
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_3 = this.m_graphs.get(i0);

                if (!_loc_3.hasRoads)
                {
                    continue;
                }
                _loc_1.push(_loc_3);
                _loc_2.push(_loc_3.size);
            }
            _loc_4 = MathUtil.randomIndexWeighed(_loc_2);
            return (RoadGraph)_loc_1.get(_loc_4);
        }//end

        public RoadGraph  getConnectedRoadGraph (MapResource param1 )
        {
            RoadGraph _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_2 = this.m_graphs.get(i0);

                if (_loc_2.hasClosestRoad(param1))
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public Object  findPath (MapResource param1 ,MapResource param2 ,int param3 ,boolean param4 =false )
        {
            RoadDef _loc_9 =null ;
            RoadDef _loc_13 =null ;
            boolean _loc_14 =false ;
            RoadDef _loc_15 =null ;
            double _loc_16 =0;
            double _loc_17 =0;
            int _loc_18 =0;
            PathElement _loc_19 =null ;
            Array _loc_20 =null ;
            Vector3 _loc_21 =null ;
            PathElement _loc_22 =null ;
            Vector3 _loc_23 =null ;
            _loc_5 = this.findNearestRoads(param1 );
            _loc_6 = this.findNearestRoads(param2 );
            if (_loc_5.length == 0 || _loc_6.length == 0)
            {
                return null;
            }
            RoadDef _loc_7 =null ;
            RoadDef _loc_8 =null ;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_9 = _loc_5.get(i0);

                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_15 = _loc_6.get(i0);

                    if (_loc_9.graph == _loc_15.graph)
                    {
                        _loc_7 = _loc_9;
                        _loc_8 = _loc_15;
                        break;
                    }
                }
            }
            if (_loc_7 == null || _loc_8 == null || _loc_7.graph != _loc_8.graph)
            {
                return null;
            }
            if (!_loc_7.graph)
            {
                return null;
            }
            Array _loc_10 =null ;
            if (0)
            {
                _loc_10 = this.memoizedFindPath(_loc_7, _loc_8);
            }
            else
            {
                _loc_10 = _loc_7.graph.findPath(_loc_7, _loc_8);
            }
            if (_loc_10 == null)
            {
                return null;
            }
            double _loc_11 =0;
            Array _loc_12 =.get(new PathElement(param1.getHotspot (),null ,PathElement.TYPE_NONROAD )) ;
            for(int i0 = 0; i0 < _loc_10.size(); i0++)
            {
            	_loc_13 = _loc_10.get(i0);

                if (_loc_13 == null)
                {
                    continue;
                }
                if (param3 == PATH_ROAD_ONLY && _loc_13.isSidewalk)
                {
                    break;
                }
                _loc_16 = _loc_13.isSidewalk ? (SIDEWALK_WIDTH / 2) : (this.m_roadSize.x / 2);
                _loc_17 = _loc_13.isSidewalk ? (SIDEWALK_HEIGHT / 2) : (this.m_roadSize.y / 2);
                _loc_18 = _loc_13.isSidewalk ? (PathElement.TYPE_NONROAD) : (PathElement.TYPE_ROAD);
                _loc_12.push(new PathElement(new Vector3(_loc_13.x + _loc_16, _loc_13.y + _loc_17), null, _loc_18));
                if (param4)
                {
                    _loc_11 = _loc_11 + _loc_13.ComputeBuildingScore();
                }
            }
            _loc_14 = param2 instanceof HarvestableResource;
            if (param3 != PATH_ROAD_ONLY && _loc_14)
            {
                _loc_19 = _loc_12.get((_loc_12.length - 1));
                _loc_20 = this.findPathFromRoadToEntrance(_loc_19.basePosition, param2);
                for(int i0 = 0; i0 < _loc_20.size(); i0++)
                {
                		_loc_21 = _loc_20.get(i0);

                    _loc_12.push(new PathElement(_loc_21, null, PathElement.TYPE_NONROAD));
                }
            }
            _loc_14 = param1 instanceof HarvestableResource;
            if (param3 != PATH_ROAD_ONLY && _loc_14 && _loc_12.length > 1)
            {
                _loc_22 = _loc_12.get(1);
                _loc_20 = this.findPathFromRoadToEntrance(_loc_22.basePosition, param1);
                if (_loc_20.length > 0)
                {
                    _loc_12.splice(0, 1);
                    for(int i0 = 0; i0 < _loc_20.size(); i0++)
                    {
                    		_loc_21 = _loc_20.get(i0);

                        _loc_12.splice(0, 0, new PathElement(_loc_21, null, PathElement.TYPE_NONROAD));
                    }
                }
            }
            switch(param3)
            {
                case PATH_TO_FRONT_ENTRANCE:
                {
                    _loc_12.push(new PathElement(param2.getHotspot(), null, PathElement.TYPE_NONROAD));
                    break;
                }
                case PATH_FULL:
                {
                    for(int i0 = 0; i0 < param2.getHotspots().size(); i0++)
                    {
                    		_loc_23 = param2.getHotspots().get(i0);

                        _loc_12.push(new PathElement(_loc_23, null, PathElement.TYPE_NONROAD));
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return {path:_loc_12, graph:_loc_7.graph, buildingScore:_loc_11};
        }//end

        private Array  memoizedFindPath (RoadDef param1 ,RoadDef param2 )
        {
            String _loc_6 =null ;
            if (param1.graph.defs.indexOf(param1) == -1 || param1.graph.defs.indexOf(param2) == -1)
            {
                return null;
            }
            if (param1 == param2)
            {
                return .get(param2);
            }
            _loc_3 = param1.tile.getId ();
            _loc_4 = param2.tile.getId ();
            boolean _loc_5 =false ;
            if (_loc_3 > _loc_4)
            {
                _loc_6 = "" + _loc_4 + ":" + _loc_3;
                _loc_5 = true;
            }
            else
            {
                _loc_6 = "" + _loc_3 + ":" + _loc_4;
            }
            Array _loc_7 =null ;
            _loc_7 =(Array) this.m_findPathCache.getItem(_loc_6);
            if (!_loc_7)
            {
                if (_loc_5)
                {
                    _loc_7 = param1.graph.findPath(param2, param1);
                }
                else
                {
                    _loc_7 = param1.graph.findPath(param1, param2);
                }
                this.m_findPathCache.putItem(_loc_6, _loc_7);
            }
            if (_loc_5)
            {
                _loc_7.reverse();
            }
            return _loc_7;
        }//end

        public Object  createInvisiblePeepPath (MapResource param1 ,MapResource param2 )
        {
            if (param1 == null || param2 == null)
            {
                return null;
            }
            _loc_3 = param1.getHotspot ();
            _loc_4 = param2.getHotspot ();
            if (_loc_3 == null || _loc_4 == null)
            {
                return null;
            }
            _loc_5 = new Vector3(_loc_3.x ,_loc_4.y ,_loc_4.z );
            Array _loc_6 =.get(new PathElement(_loc_3 ,null ,PathElement.TYPE_NONROAD ),new PathElement(_loc_5 ,null ,PathElement.TYPE_NONROAD ),new PathElement(_loc_4 ,null ,PathElement.TYPE_NONROAD )) ;
            return {path:_loc_6, graph:null};
        }//end

        protected Array  findPathFromRoadToEntrance (Vector3 param1 ,MapResource param2 )
        {
            Vector3 _loc_6 =null ;
            Array _loc_7 =null ;
            Vector3 _loc_8 =null ;
            double _loc_9 =0;
            double _loc_10 =0;
            _loc_3 = param2.getPosition ();
            _loc_4 = param2.getSizeNoClone ();
            Array _loc_5 =.get(new Vector3(0,0),new Vector3(1,0),new Vector3(1,1),new Vector3(0,1)) ;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_6.x = _loc_3.x + _loc_6.x * _loc_4.x;
                _loc_6.y = _loc_3.y + _loc_6.y * _loc_4.y;
            }
            _loc_7 = new Array();
            _loc_8 = param2.getHotspot();
            _loc_9 = _loc_8.x - (_loc_3.x + _loc_4.x * 0.5);
            _loc_10 = _loc_8.y - (_loc_3.y + _loc_4.y * 0.5);
            if (Math.abs(_loc_9) > Math.abs(_loc_10))
            {
                if (_loc_9 > 0)
                {
                    if (param1.x > _loc_3.x + _loc_4.x)
                    {
                        return _loc_7;
                    }
                }
                else if (param1.x < _loc_3.x)
                {
                    return _loc_7;
                }
            }
            else if (_loc_10 > 0)
            {
                if (param1.y > _loc_3.y + _loc_4.y)
                {
                    return _loc_7;
                }
            }
            else if (param1.y < _loc_3.y)
            {
                return _loc_7;
            }
            _loc_11 = this.findClosestWaypoint(_loc_8 ,param1 ,_loc_5 );
            if (this.findClosestWaypoint(_loc_8, param1, _loc_5) == null)
            {
                return new Array();
            }
            ArrayUtil.removeValueFromArray(_loc_5, _loc_11);
            _loc_12 = this.findClosestWaypoint(_loc_8 ,_loc_11 ,_loc_5 );
            if (this.findClosestWaypoint(_loc_8, _loc_11, _loc_5) == null)
            {
                return .get(_loc_11);
            }
            return .get(_loc_11, _loc_12);
        }//end

        protected Vector3  findClosestWaypoint (Vector3 param1 ,Vector3 param2 ,Array param3 )
        {
            Vector3 _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            Vector3 _loc_4 =null ;
            _loc_5 = int.MAX_VALUE;
            _loc_6 = param1.subtract(param2 ).length ();
            for(int i0 = 0; i0 < param3.size(); i0++)
            {
            	_loc_7 = param3.get(i0);

                _loc_8 = _loc_7.subtract(param2).length();
                _loc_9 = _loc_7.subtract(param1).length();
                _loc_10 = _loc_8 + _loc_9;
                if (_loc_8 < _loc_6 && (_loc_4 == null || _loc_10 <= _loc_5))
                {
                    _loc_4 = _loc_7;
                    _loc_5 = _loc_10;
                }
            }
            return _loc_4;
        }//end

        public void  walkRoadXSteps (RoadDef param1 ,double param2 )
        {
            RoadDef _loc_3 =null ;
            if (param2 > 0)
            {
                for(int i0 = 0; i0 < param1.roads.size(); i0++)
                {
                		_loc_3 = param1.roads.get(i0);

                    this.walkRoadXSteps(_loc_3, (param2 - 1));
                }
            }
            param1.road.m_heatMap = true;
            return;
        }//end

        public void  roadAlgorithm (MapResource param1 )
        {
            RoadDef _loc_4 =null ;
            _loc_2 = this.findNearestRoads(param1 );
            double _loc_3 =5;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                this.walkRoadXSteps(_loc_4, (_loc_3 - 1));
            }
            return;
        }//end

        protected Array  findNearestRoads (MapResource param1 )
        {
            RoadGraph _loc_3 =null ;
            Vector3 _loc_4 =null ;
            RoadDef _loc_5 =null ;
            RoadDef _loc_6 =null ;
            if (param1 instanceof Road || param1 instanceof Sidewalk)
            {
                _loc_4 = param1.getPositionNoClone();
                _loc_5 =(RoadDef) this.m_roads.get(_loc_4.x).get(_loc_4.y);
                if (_loc_5 != null)
                {
                    return .get(_loc_5);
                }
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_3 = this.m_graphs.get(i0);

                _loc_6 = _loc_3.getClosestRoad(param1);
                if (_loc_6 != null)
                {
                    _loc_2.push(_loc_6);
                }
            }
            return _loc_2;
        }//end

        protected void  regenerateRoads ()
        {
            _loc_1 = this.m_world.getWorldRect ();
            this.m_x = _loc_1.x;
            this.m_y = _loc_1.y;
            this.m_width = _loc_1.width;
            this.m_height = _loc_1.height;
            this.clearRoadMatrix();
            this.fillRoadMatrix();
            this.makeGraphs();
            return;
        }//end

        protected void  regenerateNeighborhoods ()
        {
            this.clearBuildings();
            this.updateBuildings();
            return;
        }//end

        protected void  fillRoadMatrix ()
        {
            Road _loc_2 =null ;
            int _loc_3 =0;
            Sidewalk _loc_5 =null ;
            Vector3 _loc_6 =null ;
            int _loc_7 =0;
            Road _loc_8 =null ;
            Vector3 _loc_9 =null ;
            double _loc_10 =0;
            int _loc_11 =0;
            _loc_1 = this.m_world.getObjectsByClass(Road );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_6 = _loc_2.getPosition();
                if (!this.m_roads.get(_loc_6.x) && !this.m_isStatSent && !LoadingManager.worldLoaded)
                {
                    StatsManager.count("user_stuck", "outside_expansion", "road");
                    StatsManager.sendStats(true);
                    this.m_isStatSent = true;
                }
                this.m_roads.get(_loc_6.x).put(_loc_6.y,  new RoadDef(_loc_2, _loc_6.x, _loc_6.y, false));
                if (_loc_2.adjacentRoads == null)
                {
                    continue;
                }
                _loc_7 = 0;
                while (_loc_7 < _loc_2.adjacentRoads.length())
                {

                    _loc_8 = _loc_2.adjacentRoads.get(_loc_7);
                    if (_loc_8 != null && _loc_2.shouldAdjust(_loc_8, _loc_7))
                    {
                        _loc_9 = _loc_8.getPosition();
                        _loc_10 = Math.abs(_loc_9.x - _loc_6.x);
                        if (_loc_10 > 0.9 && _loc_10 < 1.1)
                        {
                            if (this.m_roads.get(_loc_9.x).get(_loc_6.y) == null)
                            {
                                this.m_roads.get(_loc_9.x).put(_loc_6.y,  new RoadDef(_loc_2, _loc_9.x, _loc_6.y, false));
                            }
                        }
                        _loc_10 = Math.abs(_loc_6.y - _loc_9.y);
                        if (_loc_10 > 0.9 && _loc_10 < 1.1)
                        {
                            if (this.m_roads.get(_loc_6.x).get(_loc_9.y) == null)
                            {
                                this.m_roads.get(_loc_6.x).put(_loc_9.y,  new RoadDef(_loc_2, _loc_6.x, _loc_9.y, false));
                            }
                        }
                    }
                    _loc_7++;
                }
            }
            this.m_roadSize = new Vector3(ROAD_WIDTH, ROAD_HEIGHT);
            this.m_sidewalkSize = new Vector3(SIDEWALK_WIDTH, SIDEWALK_HEIGHT);
            _loc_3 = this.m_x;
            while (_loc_3 < this.m_x + this.m_width)
            {

                _loc_11 = this.m_y;
                while (_loc_11 < this.m_y + this.m_height)
                {

                    this.updateRoad(_loc_3, _loc_11);
                    _loc_11++;
                }
                _loc_3++;
            }
            _loc_4 = this.m_world.getObjectsByClass(Sidewalk );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                _loc_6 = _loc_5.getPosition();
                if (!this.m_roads.get(_loc_6.x) && !this.m_isStatSent && !LoadingManager.worldLoaded)
                {
                    StatsManager.count("user_stuck", "outside_expansion", "sidewalk");
                    StatsManager.sendStats(true);
                    this.m_isStatSent = true;
                }
                this.m_roads.get(_loc_6.x).put(_loc_6.y,  new RoadDef(null, _loc_6.x, _loc_6.y, true, _loc_5));
            }
            _loc_3 = this.m_x;
            while (_loc_3 < this.m_x + this.m_width)
            {

                _loc_11 = this.m_y;
                while (_loc_11 < this.m_y + this.m_height)
                {

                    this.updateSidewalk(_loc_3, _loc_11);
                    _loc_11++;
                }
                _loc_3++;
            }
            _loc_3 = this.m_x;
            while (_loc_3 < this.m_x + this.m_width)
            {

                _loc_11 = this.m_y;
                while (_loc_11 < this.m_y + this.m_height)
                {

                    this.updateRoadToSidewalks(_loc_3, _loc_11);
                    _loc_11++;
                }
                _loc_3++;
            }
            return;
        }//end

        protected void  clearRoadMatrix ()
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            this.m_roads = new Array(this.m_width);
            _loc_1 = this.m_x ;
            while (_loc_1 < this.m_x + this.m_width)
            {

                _loc_2 = new Array(this.m_height);
                _loc_3 = this.m_y;
                while (_loc_3 < this.m_y + this.m_height)
                {

                    _loc_2.put(_loc_3,  null);
                    _loc_3++;
                }
                this.m_roads.put(_loc_1,  _loc_2);
                _loc_1++;
            }
            return;
        }//end

        protected boolean  processNeighbor (RoadDef param1 ,RoadDef param2 )
        {
            boolean _loc_3 =false ;
            Road _loc_4 =null ;
            if (param2 != null)
            {
                _loc_3 = param1.road == param2.road;
                for(int i0 = 0; i0 < param1.road.adjacentRoads.size(); i0++)
                {
                		_loc_4 = param1.road.adjacentRoads.get(i0);

                    if (_loc_4 == param2.road)
                    {
                        _loc_3 = true;
                    }
                }
                if (_loc_3)
                {
                    param1.roads.push(param2);
                }
                return true;
            }
            return false;
        }//end

        protected void  updateSidewalk (int param1 ,int param2 )
        {
            Array _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            RoadDef _loc_8 =null ;
            _loc_3 =(RoadDef) this.m_roads.get(param1).get(param2);
            if (_loc_3 == null)
            {
                return;
            }
            if (!_loc_3.isSidewalk)
            {
                return;
            }
            Array _loc_4 =.get(.get( -1,0) ,.get(0 ,-1) ,.get(1 ,0) ,.get(0 ,1)) ;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                _loc_6 = param1 + _loc_5.get(0);
                _loc_7 = param2 + _loc_5.get(1);
                if (_loc_6 < this.m_x || _loc_6 >= this.m_x + this.m_width || _loc_7 < this.m_y || _loc_7 >= this.m_y + this.m_height)
                {
                    continue;
                }
                _loc_8 =(RoadDef) this.m_roads.get(_loc_6).get(_loc_7);
                if (_loc_8 != null && _loc_8.isSidewalk)
                {
                    _loc_3.roads.push(_loc_8);
                }
            }
            return;
        }//end

        protected void  updateRoad (int param1 ,int param2 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_3 =(RoadDef) this.m_roads.get(param1).get(param2);
            if (_loc_3 == null)
            {
                return;
            }
            int _loc_4 =1;
            while (_loc_4 <= (this.m_roadSize.x + 1))
            {

                _loc_5 = param1 + _loc_4;
                _loc_6 = param2;
                if (_loc_5 < this.m_x || _loc_5 >= this.m_x + this.m_width || _loc_6 < this.m_y || _loc_6 >= this.m_y + this.m_height)
                {
                }
                else if (this.processNeighbor(_loc_3, (RoadDef)this.m_roads.get(_loc_5).get(_loc_6)))
                {
                    break;
                }
                _loc_4++;
            }
            _loc_4 = 1;
            while (_loc_4 <= (this.m_roadSize.x + 1))
            {

                _loc_5 = param1 - _loc_4;
                _loc_6 = param2;
                if (_loc_5 < this.m_x || _loc_5 >= this.m_x + this.m_width || _loc_6 < this.m_y || _loc_6 >= this.m_y + this.m_height)
                {
                }
                else if (this.processNeighbor(_loc_3, (RoadDef)this.m_roads.get(_loc_5).get(_loc_6)))
                {
                    break;
                }
                _loc_4++;
            }
            _loc_4 = 1;
            while (_loc_4 <= (this.m_roadSize.y + 1))
            {

                _loc_5 = param1;
                _loc_6 = param2 + _loc_4;
                if (_loc_5 < this.m_x || _loc_5 >= this.m_x + this.m_width || _loc_6 < this.m_y || _loc_6 >= this.m_y + this.m_height)
                {
                }
                else if (this.processNeighbor(_loc_3, (RoadDef)this.m_roads.get(_loc_5).get(_loc_6)))
                {
                    break;
                }
                _loc_4++;
            }
            _loc_4 = 1;
            while (_loc_4 <= (this.m_roadSize.y + 1))
            {

                _loc_5 = param1;
                _loc_6 = param2 - _loc_4;
                if (_loc_5 < this.m_x || _loc_5 >= this.m_x + this.m_width || _loc_6 < this.m_y || _loc_6 >= this.m_y + this.m_height)
                {
                }
                else if (this.processNeighbor(_loc_3, (RoadDef)this.m_roads.get(_loc_5).get(_loc_6)))
                {
                    break;
                }
                _loc_4++;
            }
            return;
        }//end

        protected void  updateRoadToSidewalks (int param1 ,int param2 )
        {
            int _loc_15 =0;
            RoadDef _loc_16 =null ;
            double _loc_17 =0;
            _loc_3 =(RoadDef) this.m_roads.get(param1).get(param2);
            if (_loc_3 == null || _loc_3.isSidewalk)
            {
                return;
            }
            _loc_4 = this.m_roadSize.x /2;
            _loc_5 = this.m_roadSize.y /2;
            double _loc_6 =0.5;
            double _loc_7 =0.5;
            _loc_8 = _loc_3.road.getSize();
            _loc_9 = _loc_3.road.getPosition();
            _loc_10 = new Vector3(_loc_3.x +_loc_4 ,_loc_3.y +_loc_5 );
            double _loc_11 =99999;
            RoadDef _loc_12 =null ;
            _loc_13 = newVector3();
            _loc_14 = _loc_9.x;
            while (_loc_14 < _loc_9.x + _loc_8.x)
            {

                _loc_15 = _loc_9.y + _loc_8.y;
                if (_loc_14 < this.m_x || _loc_14 >= this.m_x + this.m_width || _loc_15 < this.m_y || _loc_15 >= this.m_y + this.m_height)
                {
                }
                else
                {
                    _loc_16 =(RoadDef) this.m_roads.get(_loc_14).get(_loc_15);
                    if (_loc_16 == null || !_loc_16.isSidewalk)
                    {
                    }
                    else
                    {
                        _loc_13.x = _loc_16.x + _loc_6 - _loc_10.x;
                        _loc_13.y = _loc_16.y + _loc_7 - _loc_10.y;
                        _loc_17 = _loc_13.length();
                        if (_loc_12 == null || _loc_17 < _loc_11)
                        {
                            _loc_12 = _loc_16;
                            _loc_11 = _loc_17;
                        }
                    }
                }
                _loc_14++;
            }
            if (_loc_12 != null)
            {
                _loc_3.roads.push(_loc_12);
                _loc_12.roads.push(_loc_3);
            }
            _loc_11 = 99999;
            _loc_12 = null;
            _loc_14 = _loc_9.x;
            while (_loc_14 < _loc_9.x + _loc_8.x)
            {

                _loc_15 = _loc_9.y - 1;
                if (_loc_14 < this.m_x || _loc_14 >= this.m_x + this.m_width || _loc_15 < this.m_y || _loc_15 >= this.m_y + this.m_height)
                {
                }
                else
                {
                    _loc_16 =(RoadDef) this.m_roads.get(_loc_14).get(_loc_15);
                    if (_loc_16 == null || !_loc_16.isSidewalk)
                    {
                    }
                    else
                    {
                        _loc_13.x = _loc_16.x + _loc_6 - _loc_10.x;
                        _loc_13.y = _loc_16.y + _loc_7 - _loc_10.y;
                        _loc_17 = _loc_13.length();
                        if (_loc_12 == null || _loc_17 < _loc_11)
                        {
                            _loc_12 = _loc_16;
                            _loc_11 = _loc_17;
                        }
                    }
                }
                _loc_14++;
            }
            if (_loc_12 != null)
            {
                _loc_3.roads.push(_loc_12);
                _loc_12.roads.push(_loc_3);
                _loc_11 = 99999;
                _loc_12 = null;
            }
            _loc_15 = _loc_9.y;
            while (_loc_15 < _loc_9.y + _loc_8.y)
            {

                if (--_loc_9.x < this.m_x || --_loc_9.x >= this.m_x + this.m_width || _loc_15 < this.m_y || _loc_15 >= this.m_y + this.m_height)
                {
                }
                else
                {
                    _loc_16 =(RoadDef) this.m_roads.get(_loc_14).get(_loc_15);
                    if (_loc_16 == null || !_loc_16.isSidewalk)
                    {
                    }
                    else
                    {
                        _loc_13.x = _loc_16.x + _loc_6 - _loc_10.x;
                        _loc_13.y = _loc_16.y + _loc_7 - _loc_10.y;
                        _loc_17 = _loc_13.length();
                        if (_loc_12 == null || _loc_17 < _loc_11)
                        {
                            _loc_12 = _loc_16;
                            _loc_11 = _loc_17;
                        }
                    }
                }
                _loc_15++;
            }
            if (_loc_12 != null)
            {
                _loc_3.roads.push(_loc_12);
                _loc_12.roads.push(_loc_3);
                _loc_11 = 99999;
                _loc_12 = null;
            }
            _loc_15 = _loc_9.y;
            while (_loc_15 < _loc_9.y + _loc_8.y)
            {

                _loc_14 = _loc_9.x + _loc_8.x;
                if (_loc_14 < this.m_x || _loc_14 >= this.m_x + this.m_width || _loc_15 < this.m_y || _loc_15 >= this.m_y + this.m_height)
                {
                }
                else
                {
                    _loc_16 =(RoadDef) this.m_roads.get(_loc_14).get(_loc_15);
                    if (_loc_16 == null || !_loc_16.isSidewalk)
                    {
                    }
                    else
                    {
                        _loc_13.x = _loc_16.x + _loc_6 - _loc_10.x;
                        _loc_13.y = _loc_16.y + _loc_7 - _loc_10.y;
                        _loc_17 = _loc_13.length();
                        if (_loc_12 == null || _loc_17 < _loc_11)
                        {
                            _loc_12 = _loc_16;
                            _loc_11 = _loc_17;
                        }
                    }
                }
                _loc_15++;
            }
            if (_loc_12 != null)
            {
                _loc_3.roads.push(_loc_12);
                _loc_12.roads.push(_loc_3);
            }
            return;
        }//end

        protected void  makeGraphs ()
        {
            int _loc_2 =0;
            RoadDef _loc_3 =null ;
            Array _loc_4 =null ;
            RoadGraph _loc_5 =null ;
            RoadDef _loc_6 =null ;
            this.m_graphs = new Array();
            RoadGraph.nextColorIndex = 0;
            _loc_1 = this.m_x ;
            while (_loc_1 < this.m_x + this.m_width)
            {

                _loc_2 = this.m_y;
                while (_loc_2 < this.m_y + this.m_height)
                {

                    _loc_3 = this.m_roads.get(_loc_1).get(_loc_2);
                    if (_loc_3 != null && _loc_3.graph == null)
                    {
                        _loc_4 = this.getAllRoads(_loc_3);
                        _loc_5 = new RoadGraph();
                        for(int i0 = 0; i0 < _loc_4.size(); i0++)
                        {
                        		_loc_6 = _loc_4.get(i0);

                            _loc_5.add(_loc_6);
                        }
                        this.m_graphs.push(_loc_5);
                    }
                    _loc_2++;
                }
                _loc_1++;
            }
            return;
        }//end

        protected Array  getAllRoads (RoadDef param1 )
        {
            RoadDef _loc_4 =null ;
            RoadDef _loc_5 =null ;
            _loc_2 = new Dictionary ();
            Array _loc_3 =.get(param1) ;
            while (_loc_3.length > 0)
            {

                _loc_4 =(RoadDef) _loc_3.shift();
                if (_loc_4.graph == null && _loc_2.get(_loc_4) == null)
                {
                    _loc_2.put(_loc_4,  true);
                    for(int i0 = 0; i0 < _loc_4.roads.size(); i0++)
                    {
                    		_loc_5 = _loc_4.roads.get(i0);

                        _loc_3.push(_loc_5);
                    }
                }
            }
            return DictionaryUtil.getKeys(_loc_2);
        }//end

        protected void  clearBuildings ()
        {
            RoadGraph _loc_1 =null ;
            RoadDef _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            	_loc_1 = this.m_graphs.get(i0);

                _loc_1.closestRoad = new Dictionary();
                for(int i0 = 0; i0 < _loc_1.defs.size(); i0++)
                {
                	_loc_2 = _loc_1.defs.get(i0);

                    _loc_2.buildings = new Array();
                }
            }
            return;
        }//end

        protected void  updateBuildings ()
        {
            MapResource _loc_5 =null ;
            RoadGraph _loc_6 =null ;
            RoadDef _loc_7 =null ;
            Box3D _loc_8 =null ;
            Array _loc_9 =null ;
            WorldObject _loc_10 =null ;
            _loc_1 =Global.world.getCollisionMap ();
            int _loc_2 =1;
            _loc_3 = new Vector3(this.m_roadSize.x +_loc_2 *2,this.m_roadSize.y +_loc_2 *2);
            _loc_4 =Global.world.getObjectsByClass(MapResource );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_5.clearRoadSideFlags();
            }
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_6 = this.m_graphs.get(i0);

                for(int i0 = 0; i0 < _loc_6.defs.size(); i0++)
                {
                		_loc_7 = _loc_6.defs.get(i0);

                    _loc_8 = new Box3D(new Vector3((_loc_7.x - 1), (_loc_7.y - 1)), _loc_3);
                    _loc_9 = _loc_1.getIntersectingObjects(_loc_8);
                    for(int i0 = 0; i0 < _loc_9.size(); i0++)
                    {
                    		_loc_10 = _loc_9.get(i0);

                        this.maybeAddResource(_loc_10, _loc_7, _loc_6);
                    }
                }
            }
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_5.updateRoadState();
            }
            return;
        }//end

        public void  updateResource (MapResource param1 )
        {
            RoadGraph _loc_2 =null ;
            RoadDef _loc_3 =null ;
            param1.clearRoadSideFlags();
            for(int i0 = 0; i0 < this.m_graphs.size(); i0++)
            {
            		_loc_2 = this.m_graphs.get(i0);

                for(int i0 = 0; i0 < _loc_2.defs.size(); i0++)
                {
                		_loc_3 = _loc_2.defs.get(i0);

                    this.maybeAddResource(param1, _loc_3, _loc_2);
                }
            }
            param1.updateRoadState();
            return;
        }//end

        protected void  maybeAddResource (WorldObject param1 ,RoadDef param2 ,RoadGraph param3 )
        {
            _loc_4 = param1as MapResource ;
            if ((MapResource)param1 == null || _loc_4 instanceof Road || _loc_4 instanceof Sidewalk || _loc_4 instanceof Wilderness || _loc_4 instanceof TrainTracks)
            {
                return;
            }
            _loc_5 = _loc_4.getHotspot ();
            _loc_6 = this.getPathSize(param2 ).x ;
            _loc_7 = this.getPathSize(param2 ).y ;
            _loc_8 = param1.getBoundingBox ();
            _loc_9 = Math.min(_loc_8.x +_loc_8.width ,param2.x +_loc_6 )-Math.max(_loc_8.x ,param2.x );
            _loc_10 = Math.min(_loc_8.y +_loc_8.height ,param2.y +_loc_7 )-Math.max(_loc_8.y ,param2.y );
            _loc_11 = _loc_9==0&& _loc_10 > 0 || _loc_10 == 0 && _loc_9 > 0;
            if (!(_loc_9 == 0 && _loc_10 > 0 || _loc_10 == 0 && _loc_9 > 0))
            {
                return;
            }
            if (_loc_9 == 0)
            {
                if (_loc_8.x + _loc_8.width == param2.x)
                {
                    _loc_4.setRoadSideFlag(_loc_4.RIGHT_SIDE);
                }
                else if (_loc_8.x == param2.x + _loc_6)
                {
                    _loc_4.setRoadSideFlag(_loc_4.LEFT_SIDE);
                }
            }
            else if (_loc_10 == 0)
            {
                if (_loc_8.y + _loc_8.height == param2.y)
                {
                    _loc_4.setRoadSideFlag(_loc_4.TOP_SIDE);
                }
                else if (_loc_8.y == param2.y + _loc_7)
                {
                    _loc_4.setRoadSideFlag(_loc_4.BOTTOM_SIDE);
                }
            }
            if (param3.hasRoads)
            {
                _loc_4.setConnectsRoadFlag(true);
            }
            _loc_12 = param3.getClosestRoad(_loc_4);
            if (param3.getClosestRoad(_loc_4) == null)
            {
                param3.setClosestRoad(_loc_4, param2);
                return;
            }
            _loc_13 = newVector3(_loc_12.x+this.m_roadSize.x/2,_loc_12.y+this.m_roadSize.y/2);
            _loc_14 = newVector3(_loc_12.x+this.m_roadSize.x/2,_loc_12.y+this.m_roadSize.y/2).subtract(_loc_5).length();
            _loc_15 = newVector3(param2.x+_loc_6/2,param2.y+_loc_7/2);
            _loc_16 = newVector3(param2.x+_loc_6/2,param2.y+_loc_7/2).subtract(_loc_5).length();
            if (new Vector3(param2.x + _loc_6 / 2, param2.y + _loc_7 / 2).subtract(_loc_5).length() < _loc_14)
            {
                param3.setClosestRoad(_loc_4, param2);
            }
            return;
        }//end

        public void  showOverlay (boolean param1 )
        {
            if (this.m_roadOverlay == null && param1)
            {
                this.m_roadOverlay = new Sprite();
                this.drawOverlay(this.m_roadOverlay);
                GlobalEngine.viewport.objectBase.addChild(this.m_roadOverlay);
            }
            else if (this.m_roadOverlay != null && !param1)
            {
                if (this.m_roadOverlay.parent)
                {
                    this.m_roadOverlay.parent.removeChild(this.m_roadOverlay);
                }
                this.m_roadOverlay = null;
            }
            return;
        }//end

        public boolean  isShowingOverlay ()
        {
            return this.m_roadOverlay != null;
        }//end

        public Road  findRandomRoadOnScreen (Road param1 )
        {
            RoadDef _loc_6 =null ;
            int _loc_7 =0;
            double _loc_8 =0;
            _loc_2 = param1.getPosition ();
            Array _loc_3 =null ;
            RoadDef _loc_4 =null ;
            if (param1.positionX < this.m_x || param1.positionY < this.m_y || param1.positionX >= this.m_x + this.m_width || param1.positionY >= this.m_y + this.m_height)
            {
                return param1;
            }
            _loc_4 = this.m_roads.get(param1.positionX).get(param1.positionY);
            _loc_3 = _loc_4.graph.defs;
            if (!_loc_3 || _loc_3.length <= 0)
            {
                return param1;
            }
            _loc_5 = new Array ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_6 = _loc_3.get(i0);

                _loc_8 = 0;
                if (_loc_6.road != null)
                {
                    _loc_8 = this.getOnScreenWeight(_loc_6, _loc_4);
                }
                if (_loc_6.graph.size < this.MIN_RANDOM_ROAD_GRAPH_LEN || _loc_6 == _loc_4)
                {
                    _loc_8 = 0;
                }
                _loc_5.push(_loc_8);
            }
            _loc_7 = MathUtil.randomIndexWeighed(_loc_5);
            if (_loc_7 < 0 || _loc_7 >= _loc_3.length())
            {
                return param1;
            }
            return _loc_3.get(_loc_7).road;
        }//end

        protected double  getOnScreenWeight (RoadDef param1 ,RoadDef param2 )
        {
            int _loc_3 =20;
            double _loc_4 =20;
            _loc_5 = IsoMath.tilePosToPixelPos(param1.x +RoadManager.ROAD_WIDTH /2,param1.y +RoadManager.ROAD_HEIGHT /2);
            _loc_5 = IsoMath.viewportToStage(_loc_5);
            if (_loc_5.x < _loc_3 || _loc_5.y < _loc_3 || _loc_5.x + _loc_3 > GlobalEngine.viewport.stage.stageWidth || _loc_5.y + _loc_3 > GlobalEngine.viewport.stage.stageHeight)
            {
                return 0;
            }
            _loc_6 = param1.road.getPositionNoClone ().subtract(param2.road.getPositionNoClone ());
            _loc_7 = param1.road.getPositionNoClone ().subtract(param2.road.getPositionNoClone ()).dot(param1.road.getPositionNoClone ().subtract(param2.road.getPositionNoClone ()));
            if (param1.road.getPositionNoClone().subtract(param2.road.getPositionNoClone()).dot(param1.road.getPositionNoClone().subtract(param2.road.getPositionNoClone())) > _loc_4 * _loc_4)
            {
                _loc_7 = _loc_4 * _loc_4 / (1 + _loc_7 - _loc_4 * _loc_4);
            }
            return _loc_7;
        }//end

        private void  drawOverlay (Sprite param1 )
        {
            int _loc_8 =0;
            RoadDef _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            Point _loc_12 =null ;
            RoadDef _loc_13 =null ;
            MapResource _loc_14 =null ;
            double _loc_15 =0;
            double _loc_16 =0;
            Point _loc_17 =null ;
            Vector _loc_18.<Vector3 >=null ;
            Vector3 _loc_19 =null ;
            _loc_2 = this.m_roadSize.x /2;
            _loc_3 = this.m_roadSize.y /2;
            _loc_4 = SIDEWALK_WIDTH/2;
            _loc_5 = SIDEWALK_HEIGHT/2;
            _loc_6 = param1.graphics ;
            param1.graphics.clear();
            _loc_7 = this.m_x ;
            while (_loc_7 < this.m_x + this.m_width)
            {

                _loc_8 = this.m_y;
                while (_loc_8 < this.m_y + this.m_height)
                {

                    _loc_9 =(RoadDef) this.m_roads.get(_loc_7).get(_loc_8);
                    if (_loc_9 == null)
                    {
                    }
                    else
                    {
                        _loc_10 = _loc_9.isSidewalk ? (_loc_4) : (_loc_2);
                        _loc_11 = _loc_9.isSidewalk ? (_loc_5) : (_loc_3);
                        _loc_6.lineStyle(1, _loc_9.graph.debugColor);
                        _loc_12 = IsoMath.tilePosToPixelPos(_loc_9.x + _loc_10, _loc_9.y + _loc_11);
                        for(int i0 = 0; i0 < _loc_9.roads.size(); i0++)
                        {
                        	_loc_13 = _loc_9.roads.get(i0);

                            _loc_15 = _loc_13.isSidewalk ? (_loc_4) : (_loc_2);
                            _loc_16 = _loc_13.isSidewalk ? (_loc_5) : (_loc_3);
                            _loc_17 = IsoMath.tilePosToPixelPos(_loc_13.x + _loc_15, _loc_13.y + _loc_16);
                            _loc_6.moveTo(_loc_12.x, _loc_12.y);
                            _loc_6.lineTo(_loc_17.x, _loc_17.y);
                        }
                        _loc_6.moveTo(_loc_12.x, _loc_12.y);
                        _loc_6.lineTo((_loc_12.x + 1), _loc_12.y);
                        _loc_6.lineStyle(1, _loc_9.graph.debugColor, 0.5);
                        for(int i0 = 0; i0 < _loc_9.buildings.size(); i0++)
                        {
                        	_loc_14 = _loc_9.buildings.get(i0);

                            _loc_6.moveTo(_loc_12.x, _loc_12.y);
                            _loc_18 = _loc_14.getHotspots();
                            for(int i0 = 0; i0 < _loc_18.size(); i0++)
                            {
                            	_loc_19 = _loc_18.get(i0);

                                _loc_17 = IsoMath.tilePosToPixelPos(_loc_19.x, _loc_19.y);
                                _loc_6.lineTo(_loc_17.x, _loc_17.y);
                            }
                        }
                    }
                    _loc_8++;
                }
                _loc_7++;
            }
            return;
        }//end

    }


