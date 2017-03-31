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
import Engine.*;
import Modules.stats.experiments.*;
import com.adobe.utils.*;
//import flash.utils.*;

    public class RoadGraph
    {
        public Array defs ;
        public Dictionary closestRoad ;
        public int debugColor ;
        public boolean m_hasRoads =false ;
        public boolean m_hasSidewalks =false ;
        public boolean m_hasParkingLots =false ;
        private boolean m_useAStarPathFinding =false ;
        private static  Array DEBUG_COLORS =.get(16711680 ,65280,255,4210752) ;
        public static int nextColorIndex =0;

        public  RoadGraph ()
        {
            this.defs = new Array();
            this.debugColor = DEBUG_COLORS.get(nextColorIndex);
            nextColorIndex = (nextColorIndex + 1) % DEBUG_COLORS.length;
            this.m_useAStarPathFinding = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FINDPATH);
            return;
        }//end

        public int  size ()
        {
            return this.defs.length;
        }//end

        public boolean  hasRoads ()
        {
            return this.m_hasRoads;
        }//end

        public boolean  hasSidewalks ()
        {
            return this.m_hasSidewalks;
        }//end

        private boolean  useAStarPathFinding ()
        {
            return this.m_useAStarPathFinding;
        }//end

        public MapResource  findRandomResource (Class param1 ,MapResource param2 ,int param3 =0)
        {
            MapResource _loc_6 =null ;
            _loc_4 = DictionaryUtil.getKeys(this.closestRoad);
            Array _loc_5 =new Array();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_6 = _loc_4.get(i0);

                if (_loc_6 != param2 && !(_loc_6.getObjectType() & param3) && (param1 == null || _loc_6 instanceof param1))
                {
                    _loc_5.push(_loc_6);
                }
            }
            return _loc_5.length > 0 ? ((MapResource)MathUtil.randomElement(_loc_5)) : (null);
        }//end

        public Array  findPath (RoadDef param1 ,RoadDef param2 )
        {
            if (this.useAStarPathFinding)
            {
                return this.findPath_astar(param1, param2);
            }
            return this.findPath_dijkstra(param1, param2);
        }//end

        private Array  findPath_dijkstra (RoadDef param1 ,RoadDef param2 )
        {
            RoadDef def ;
            Array unvisited ;
            Function reverseSortFn ;
            boolean found ;
            RoadDef current ;
            double currentX ;
            double currentY ;
            RoadDef other ;
            double otherX ;
            double otherY ;
            double dx ;
            double dy ;
            double deltaDist ;
            RoadDef next ;
            source = param1;
            target = param2;
            reverseSortFn =int  (RoadDef param1 ,RoadDef param2 )
            {
                return param2.distance - param1.distance;
            }//end
            ;
            if (this.defs.indexOf(source) == -1 || this.defs.indexOf(target) == -1)
            {
                return null;
            }
            int _loc_4 =0;
            _loc_5 = this.defs ;
            for(int i0 = 0; i0 < this.defs.size(); i0++)
            {
            	def = this.defs.get(i0);


                def.distance = int.MAX_VALUE;
                def.backPtr = null;
            }
            source.distance = 0;
            unvisited = ArrayUtil.copyArray(this.defs);
            ArrayUtil.removeValueFromArray(unvisited, source);
            unvisited.push(source);
            found;
            while (unvisited.length > 0)
            {

                current = unvisited.pop();
                currentX = current.x + (current.isSidewalk ? (RoadManager.SIDEWALK_WIDTH / 2) : (RoadManager.ROAD_WIDTH / 2));
                currentY = current.y + (current.isSidewalk ? (RoadManager.SIDEWALK_HEIGHT / 2) : (RoadManager.ROAD_HEIGHT / 2));


                for(int i0 = 0; i0 < current.roads.size(); i0++)
                {
                	other = current.roads.get(i0);


                    otherX = other.x + (other.isSidewalk ? (RoadManager.SIDEWALK_WIDTH / 2) : (RoadManager.ROAD_WIDTH / 2));
                    otherY = other.y + (other.isSidewalk ? (RoadManager.SIDEWALK_HEIGHT / 2) : (RoadManager.ROAD_HEIGHT / 2));
                    dx = currentX - otherX;
                    dy = currentY - otherY;
                    deltaDist = Math.sqrt(dx * dx + dy * dy);
                    if (other.distance > current.distance)
                    {
                        other.distance = current.distance + deltaDist;
                        other.backPtr = current;
                    }
                    if (other == target)
                    {
                        break;
                    }
                }
                unvisited.sort(reverseSortFn);
            }
            if (target.distance == int.MAX_VALUE)
            {
                return null;
            }
            Array path =new Array();
            current = target;
            while (current.backPtr != null)
            {

                path.unshift(current);
                next = current.backPtr;
                current.backPtr = null;
                current = next;
            }
            path.unshift(current);
            return path;
        }//end

        private Array  findPath_astar (RoadDef param1 ,RoadDef param2 )
        {
            RoadDef _loc_5 =null ;
            RoadDef _loc_6 =null ;
            Array _loc_7 =null ;
            RoadDef _loc_8 =null ;
            double _loc_9 =0;
            boolean _loc_10 =false ;
            if (this.defs.indexOf(param1) == -1 || this.defs.indexOf(param2) == -1)
            {
                return null;
            }
            if (param1 == param2)
            {
                return .get(param2);
            }
            Array _loc_3 =new Array();
            Array _loc_4 =new Array();
            _loc_3.push(param1);
            param1.gScore = 0;
            param1.hScore = this.distanceBetween(param1.x - param2.x, param1.y - param2.y);
            param1.fScore = param1.hScore;
            while (_loc_4.length > 0)
            {

                _loc_5 = _loc_4.shift();
                if (_loc_5 == param2)
                {
                    _loc_7 = new Array();
                    _loc_8 = param2;
                    while (_loc_8.backPtr != param1)
                    {

                        _loc_7.unshift(_loc_8);
                        _loc_8 = _loc_8.backPtr;
                    }
                    _loc_7.unshift(param1);
                    return _loc_7;
                }
                _loc_3.push(_loc_5);
                for(int i0 = 0; i0 < _loc_5.roads.size(); i0++)
                {
                	_loc_6 = _loc_5.roads.get(i0);

                    if (_loc_3.indexOf(_loc_6) != -1)
                    {
                        continue;
                    }
                    _loc_9 = _loc_5.gScore + this.distanceBetween(_loc_5.x - _loc_6.x, _loc_5.y - _loc_6.y);
                    if (_loc_4.indexOf(_loc_6) == -1)
                    {
                        _loc_4.push(_loc_6);
                        _loc_10 = true;
                    }
                    else if (_loc_9 < _loc_6.gScore)
                    {
                        _loc_10 = true;
                    }
                    else
                    {
                        _loc_10 = false;
                    }
                    if (_loc_10)
                    {
                        _loc_6.backPtr = _loc_5;
                        _loc_6.gScore = _loc_9;
                        _loc_6.hScore = this.distanceBetween(_loc_6.x - param2.x, _loc_6.y - param2.y);
                        _loc_6.fScore = _loc_6.gScore + _loc_6.hScore;
                        _loc_4.sortOn("fScore", Array.NUMERIC);
                    }
                }
            }
            return _loc_3;
        }//end

        private double  distanceBetween (double param1 ,double param2 )
        {
            return Math.sqrt(param1 * param1 + param2 * param2);
        }//end

        public void  add (RoadDef param1 )
        {
            this.defs.push(param1);
            param1.graph = this;
            if (param1.isSidewalk)
            {
                this.m_hasSidewalks = true;
            }
            else if (param1.road instanceof ParkingLot)
            {
                this.m_hasParkingLots = true;
            }
            else
            {
                this.m_hasRoads = true;
            }
            return;
        }//end

        public void  setClosestRoad (MapResource param1 ,RoadDef param2 )
        {
            _loc_3 =(RoadDef) this.closestRoad.get(param1);
            if (_loc_3 != null)
            {
                ArrayUtil.removeValueFromArray(_loc_3.buildings, param1);
            }
            this.closestRoad.put(param1,  param2);
            param2.buildings.push(param1);
            return;
        }//end

        public RoadDef  getClosestRoad (MapResource param1 )
        {
            return (RoadDef)this.closestRoad.get(param1);
        }//end

        public boolean  hasClosestRoad (MapResource param1 )
        {
            return this.closestRoad.get(param1) != null;
        }//end

    }


