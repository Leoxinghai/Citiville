package Engine.Classes;

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

import com.xiyu.logic.EventDispatcher;
import com.xiyu.logic.IEventDispatcher;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Engine.Interfaces.*;
import Engine.Managers.*;
import root.GlobalEngine;

import com.greensock.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.system.*;
//import flash.utils.*;

import com.xinghai.Debug;
import com.xiyu.util.Event;

public class World extends PositionedObjectContainer implements IEventDispatcher
    {
        protected TileMap m_tileMap =null ;
        protected ITerrainMap m_terrain ;
        protected EventDispatcher m_dispatcher ;
        protected boolean m_initialized ;
        protected double m_lastPerfUpdate =0;
        protected int m_perfFrameCount =0;
        protected boolean m_perfTracking =false ;
        protected boolean m_errorOnNullObject =false ;
        protected CollisionMap m_collisionMap =null ;
        protected ObjectLayer m_defaultLayer =null ;
        protected Dictionary m_layerLookup ;
        protected Object m_objectCounts ;
        private TweenLite m_scrollTween ;
        protected ShardScheduler m_shards ;
        private static  int PERF_PROBABILITY =10000;
        private static  double PERF_UPDATE_INTERVAL =5000;
        private static  String PERF_FPS ="fps";
        private static  String PERF_MEM_USAGE ="mem_usage";
        public static  String DEFAULT_LAYER_NAME ="default";

        private static World m_instance =null ;

        public  World (int param1 ,int param2 )
        {
            super(param1, param2);
            this.m_layerLookup = new Dictionary();
            this.m_objectCounts = new Object();
            this.m_dispatcher = new EventDispatcher(this);
            m_instance = this;
            this.m_shards = new ShardScheduler();
            FreezeManager.getInstance().addEventListener(FreezeEvent.THAWED, this.resetPerformanceTracking);
            return;
        }//end

        public int  defCon ()
        {
            return 0;
        }//end

        public Sharder  getSharder (int param1 )
        {
            return this.m_shards.getSharder(param1);
        }//end

        protected ObjectLayer  createObjectLayer (String param1 )
        {
            return new ObjectLayer(param1);
        }//end

         public void  loadObject (Object param1 )
        {
            WorldObject _loc_3 =null ;
            LoadingManager.startWorldLoad();
            this.cleanUp();
            if (param1.sizeX && param1.sizeY)
            {
                m_size.x = param1.sizeX;
                m_size.y = param1.sizeY;
            }
            this.initialize();
            this.m_defaultLayer = this.createObjectLayer(DEFAULT_LAYER_NAME);
            this.addLayer(this.m_defaultLayer);
            int _loc_2 =0;
            while (_loc_2 < param1.objects.length())
            {

                if (param1.objects.get(_loc_2) == "null" || param1.objects.get(_loc_2) == null)
                {
                    if (this.m_errorOnNullObject)
                    {
                        throw new Error("object data was null");
                    }
                    trace("Null object loaded");
                }
                _loc_3 = this.createObjectInstance(param1.objects.get(_loc_2));
                if (_loc_3)
                {
                    _loc_3.setOuter(this);
                    _loc_3.loadObject(param1.objects.get(_loc_2));
                    _loc_3.attach();
                }
                _loc_2++;
            }
            return;
        }//end

        protected WorldObject createObjectInstance (Object param1 )
        {
            return (WorldObject)SavedObject.getInstance(param1.className);
        }//end

        public void  initialize ()
        {
            this.m_shards.initSharders();
            if (GlobalEngine.engineOptions.tileMapClass == null)
            {
                GlobalEngine.engineOptions.tileMapClass = TileMap;
            }
            this.m_tileMap =(TileMap) new GlobalEngine.engineOptions.tileMapClass(getGridWidth(), getGridHeight());
            this.m_collisionMap = new CollisionMap();
            this.m_collisionMap.initialize(getGridWidth(), getGridHeight());
            this.m_initialized = true;
            GlobalEngine.viewport.clearBitmapData();
            GlobalEngine.viewport.regenerateBitmapData();
            return;
        }//end

        public void  cleanUp ()
        {
            IEngineObject _loc_3 =null ;
            Array _loc_1 = m_children.concat([]);
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(IEngineObject) _loc_1.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_3.detach();
                    _loc_3.cleanUp();
                }
                _loc_2++;
            }
            m_children = new Array();
            this.m_layerLookup = new Dictionary();
            this.m_defaultLayer = null;
            this.m_tileMap = null;
            this.m_collisionMap.dispose();
            this.m_collisionMap = null;
            this.m_initialized = false;
            return;
        }//end

        public TileMap  getTileMap ()
        {
            return this.m_tileMap;
        }//end

        public int  getRotation ()
        {
            return Constants.ROTATION_0;
        }//end

        public Array  getReferencedAssets ()
        {
            WorldObject _loc_3 =null ;
            Array _loc_4 =null ;
            int _loc_5 =0;
            Array _loc_1 =new Array();
            int _loc_2 =0;
            while (_loc_2 < m_children.length())
            {

                _loc_3 =(WorldObject) m_children.get(_loc_2);
                _loc_4 = _loc_3.getReferencedAssets();
                _loc_5 = 0;
                while (_loc_5 < _loc_4.length())
                {

                    if (_loc_1.indexOf(_loc_4.get(_loc_5)) == -1)
                    {
                        _loc_1.push(_loc_4.get(_loc_5));
                    }
                    _loc_5++;
                }
                _loc_2++;
            }
            _loc_1 = _loc_1.concat(this.m_tileMap.getReferencedAssets());
            return _loc_1;
        }//end

        public WorldObject  getObjectById (int param1 ,int param2 =1.67772e +007)
        {
            WorldObject _loc_6 =null ;
            WorldObject _loc_3 =null ;
            Array _loc_4 = getObjects();
            int _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                _loc_6 =(WorldObject) _loc_4.get(_loc_5);
                if (_loc_6.getId() == param1 && (_loc_6.getObjectType() & param2) > 0)
                {
                    _loc_3 = _loc_6;
                    break;
                }
                _loc_5++;
            }
            return _loc_3;
        }//end

        public Array  getObjectsByType (int param1 =1.67772e +007)
        {
            WorldObject _loc_5 =null ;
            Array _loc_2 =new Array();
            Array _loc_3 = getObjects();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(WorldObject) _loc_3.get(_loc_4);
                if ((_loc_5.getObjectType() & param1) > 0)
                {
                    _loc_2.push(_loc_5);
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

        public Array  getObjectsByClass (Class param1)
        {
            WorldObject _loc_5 =null ;
            Array _loc_2 =new Array ();
            Array _loc_3 = getObjects();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(WorldObject) _loc_3.get(_loc_4);
                if (_loc_5 != null && (param1 == null || _loc_5 instanceof param1))
                {
                    _loc_2.push(_loc_5);
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

         public void  conditionallyRedrawAllObjects ()
        {
            WorldObject _loc_3 =null ;
            Array _loc_1 = getObjects();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(WorldObject) _loc_1.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_3.conditionallyRedraw();
                }
                _loc_2++;
            }
            return;
        }//end

        public void  updateWorld ()
        {
            IEngineObject _loc_1 =null ;
            int _loc_2 =0;


            if (this.m_perfTracking)
            {
                this.trackPerf();
            }
            if (this.m_initialized)
            {
                this.m_shards.updateAllSharderTimers();
                _loc_2 = 0;
                while (_loc_2 < m_children.length())
                {
                    _loc_1 =(IEngineObject) m_children.get(_loc_2);
                    if (_loc_1 !=null)
                    {
                        _loc_1.update();
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public void  updateCulling ()
        {
            PositionedObject _loc_3 =null ;
            Array _loc_1 = getObjects();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(PositionedObject) _loc_1.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_3.updateCulling();
                }
                _loc_2++;
            }
            return;
        }//end

        public void  addLayer (ObjectLayer param1 )
        {

            _loc_2 = this.m_layerLookup.get(param1.name) ;
            if (_loc_2 == null)
            {
                this.m_layerLookup.get(param1.name) = param1;
                m_children.push(param1);
                m_children.sortOn("priority", Array.NUMERIC);
                setObjectsDirty();
                param1.attach();
            }
            return;
        }//end

        public void  removeLayer (ObjectLayer param1 )
        {
            param1.cleanUp();
            delete this.m_layerLookup.get(param1.name);
            param1.detach();
            return;
        }//end

        public ObjectLayer  getObjectLayerByName (String param1 )
        {
            return this.m_layerLookup.get(param1);
        }//end

        public void  insertObjectIntoDepthArray (WorldObject param1 ,String param2 )
        {
            if (param2 == null)
            {
                param2 = DEFAULT_LAYER_NAME;
            }
            ObjectLayer _loc_3 = this.getObjectLayerByName(param2 );
            GlobalEngine.assert(_loc_3 != null, "No object layer found with name: " + param2);
            if (_loc_3 != null)
            {
                _loc_3.insertObjectIntoDepthArray(param1);
                this.m_layerLookup.put(param1,  _loc_3);
            }
            return;
        }//end

        public void  updateObjectInDepthArray (WorldObject param1 )
        {
            ObjectLayer _loc_2 =(ObjectLayer) this.m_layerLookup.get(param1);
            if (_loc_2 != null)
            {
                _loc_2.updateObjectInDepthArray(param1);
            }
            else if (param1.isAttached())
            {
                this.insertObjectIntoDepthArray(param1);
            }
            return;
        }//end

        public void  removeObjectFromDepthArray (WorldObject param1 )
        {
            ObjectLayer _loc_2 =(ObjectLayer) this.m_layerLookup.get(param1);
            if (_loc_2 != null)
            {
                delete this.m_layerLookup.get(param1);
                _loc_2.removeObjectFromDepthArray(param1);
            }
            return;
        }//end

        public void  insertObjectIntoCollisionMap (WorldObject param1 )
        {
            this.m_collisionMap.insertObject(param1);
            return;
        }//end

        public void  removeObjectFromCollisionMap (WorldObject param1 )
        {
            this.m_collisionMap.removeObject(param1);
            return;
        }//end

        protected void  buildCollisionMap ()
        {
            this.m_collisionMap.initialize(getGridWidth(), getGridHeight());
            return;
        }//end

        public void  showCollisionMap (boolean param1 )
        {
            if (this.m_collisionMap != null)
            {
                this.m_collisionMap.showCollisionMap(param1);
            }
            return;
        }//end

        public boolean  checkCollision (int param1 ,int param2 ,int param3 ,int param4 ,Array param5 ,Array param6 =null ,int param7 =1.67772e +007,WorldObject param8 =null )
        {
            boolean _loc_9 =false ;
            CollisionLookup _loc_10 =new CollisionLookup ();
            _loc_10.init(param1, param2, param3, param4);
            _loc_10.ignoreObjects = param5;
            _loc_10.colliderTypes = param7;
            _loc_10.collidingObject = param8;
            _loc_10.colliders = param6;
            _loc_9 = this.m_collisionMap.checkCollision(_loc_10);
            return _loc_9;
        }//end

        public CollisionMap  getCollisionMap ()
        {
            return this.m_collisionMap;
        }//end

        public PositionedObject  pickObject (Point param1 ,int param2 =1.67772e +007,int param3 =0)
        {
            int _loc_4 =0;
            PositionedObject _loc_6 =null ;
            PositionedObject _loc_5 =null ;
            _loc_4 = m_children.length() - 1;
            while (_loc_4 >= 0)
            {

                _loc_6 =(PositionedObject) m_children.get(_loc_4);
                _loc_5 =(PositionedObject) _loc_6.pickObject(param1, param2, param3);
                if (_loc_5 != null)
                {
                    break;
                }
                _loc_4 = _loc_4 - 1;
            }
            return _loc_5;
        }//end

        public Array  pickAllObjects (Point param1 ,PositionedObject param2 ,int param3 =1.67772e +007,int param4 =0)
        {
            int _loc_5 =0;
            PositionedObject _loc_6 =null ;
            PositionedObject _loc_8 =null ;
            Array _loc_7 =new Array ();
            _loc_5 = m_children.length - 1;
            while (_loc_5 >= 0)
            {

                _loc_8 =(PositionedObject) m_children.get(_loc_5);
                _loc_6 =(PositionedObject) _loc_8.pickObject(param1, param3, param4);
                if (_loc_6 != null)
                {
                    if (_loc_6 === param2)
                    {
                        break;
                    }
                    _loc_7.push(_loc_6);
                }
                _loc_5 = _loc_5 - 1;
            }
            return _loc_7;
        }//end

        public ITerrainMap  terrainMap ()
        {
            return this.m_terrain;
        }//end

        public void  startPerformanceTracking ()
        {
            if (Math.random() * PERF_PROBABILITY < 1)
            {
                this.m_perfTracking = true;
            }
            return;
        }//end

        private void  resetPerformanceTracking (FreezeEvent event )
        {
            if (this.m_perfTracking == true)
            {
                this.m_lastPerfUpdate = getTimer();
                this.m_perfFrameCount = 0;
            }
            return;
        }//end

        protected void  trackPerf ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_4 =null ;
            double _loc_5 =0;
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_1 = getTimer();
            this.m_perfFrameCount++;
            if (_loc_1 >= this.m_lastPerfUpdate + PERF_UPDATE_INTERVAL)
            {
                _loc_2 = this.m_perfFrameCount / (_loc_1 - this.m_lastPerfUpdate) * 1000;
                if (GlobalEngine)
                {
                    _loc_3 = this.getNumObjects();
                    if (_loc_2 > 30)
                    {
                        _loc_4 = "30-60";
                    }
                    else if (_loc_2 > 20)
                    {
                        _loc_4 = "20-30";
                    }
                    else if (_loc_2 > 10)
                    {
                        _loc_4 = "10-20";
                    }
                    else if (_loc_2 > 5)
                    {
                        _loc_4 = "5-10";
                    }
                    else if (_loc_2 > 1)
                    {
                        _loc_4 = "1-5";
                    }
                    else
                    {
                        _loc_4 = "0-1";
                    }
                    _loc_5 = Number(System.totalMemory / 1024 / 1024);
                    if (_loc_5 < 20)
                    {
                        _loc_6 = "0-20";
                    }
                    else if (_loc_5 < 40)
                    {
                        _loc_6 = "20-40";
                    }
                    else if (_loc_5 < 60)
                    {
                        _loc_6 = "40-60";
                    }
                    else if (_loc_5 < 80)
                    {
                        _loc_6 = "60-80";
                    }
                    else if (_loc_5 < 100)
                    {
                        _loc_6 = "80-100";
                    }
                    else if (_loc_5 < 120)
                    {
                        _loc_6 = "100-120";
                    }
                    else if (_loc_5 < 140)
                    {
                        _loc_6 = "120-140";
                    }
                    else if (_loc_5 < 160)
                    {
                        _loc_6 = "140-160";
                    }
                    else if (_loc_5 < 180)
                    {
                        _loc_6 = "160-180";
                    }
                    else if (_loc_5 < 200)
                    {
                        _loc_6 = "180-200";
                    }
                    else if (_loc_5 < 220)
                    {
                        _loc_6 = "200-220";
                    }
                    else
                    {
                        _loc_6 = "220-inf";
                    }
                    if (_loc_3 < 200)
                    {
                        _loc_7 = " 0To200 objects";
                    }
                    else if (_loc_3 < 400)
                    {
                        _loc_7 = " 200To400 objects";
                    }
                    else if (_loc_3 < 600)
                    {
                        _loc_7 = " 400To600 objects";
                    }
                    else if (_loc_3 < 1000)
                    {
                        _loc_7 = " 600To1000 objects";
                    }
                    else if (_loc_3 < 1500)
                    {
                        _loc_7 = " 1000To1500 objects";
                    }
                    else
                    {
                        _loc_7 = " 1500ToInf objects";
                    }
                    StatsManager.perf(PERF_FPS, "clientPerformance", "", "", "", "", _loc_2.toString());
                    StatsManager.perf(PERF_MEM_USAGE, "clientPerformance", "", "", "", "", _loc_3.toString());
                }
                this.m_lastPerfUpdate = _loc_1;
                this.m_perfFrameCount = 0;
            }
            return;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,int param4 =0,boolean param5 =false )
        {
            this.m_dispatcher.addEventListener(param1, param2, param3, param4);
            return;
        }//end

        public boolean  dispatchEvent (Event event )
        {
            return this.m_dispatcher.dispatchEvent(event);
        }//end

        public boolean  hasEventListener (String param1 )
        {
            return this.m_dispatcher.hasEventListener(param1);
        }//end

        public void  removeEventListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            this.m_dispatcher.removeEventListener(param1, param2, param3);
            return;
        }//end

        public boolean  willTrigger (String param1 )
        {
            return this.m_dispatcher.willTrigger(param1);
        }//end

        public void  errorOnNullObject (boolean param1 )
        {
            this.m_errorOnNullObject = param1;
            return;
        }//end

        public void  incObjectCountMap (String param1 )
        {
            if (this.m_objectCounts.hasOwnProperty(param1))
            {
                _loc_2 = this.m_objectCounts ;
                _loc_3 = param1;
                _loc_4 = this.m_objectCounts.get(param1) +1;
                _loc_2.put(_loc_3,  _loc_4);
            }
            else
            {
                this.m_objectCounts.put(param1,  1);
            }
            return;
        }//end

        public void  decObjectCountMap (String param1 )
        {
            if (this.m_objectCounts.hasOwnProperty(param1))
            {
                _loc_2 = this.m_objectCounts ;
                _loc_3 = param1;
                _loc_4 = this.m_objectCounts.get(param1) -1;
                _loc_2.put(_loc_3,  _loc_4);
            }
            else
            {
                this.m_objectCounts.put(param1,  0);
            }
            return;
        }//end

        public int  getObjectCount (String param1 )
        {
            if (this.m_objectCounts.hasOwnProperty(param1))
            {
                return this.m_objectCounts.get(param1);
            }
            return 0;
        }//end

        public void  centerOnObject (WorldObject param1 ,double param2 =1)
        {
            if (this.m_scrollTween)
            {
                this.m_scrollTween.complete(true);
                this.m_scrollTween = null;
            }
            _loc_3 = param1.getPosition ();
            Object _loc_4 ={startPos GlobalEngine.viewport.getScrollPosition (),alpha 0};
            _loc_5 = (IsoViewport)GlobalEngine.viewport
            (GlobalEngine.viewport as IsoViewport).centerOnTilePos(_loc_3.x, _loc_3.y);
            _loc_4.endPos = GlobalEngine.viewport.getScrollPosition();
            GlobalEngine.viewport.setScrollPosition(_loc_4.startPos);
            this.m_scrollTween = TweenLite.to(_loc_4, param2, {alpha:1, onUpdate:this.onUpdateScrollTween, onUpdateParams:.get(_loc_4), onComplete:this.onCompleteScrollTween});
            return;
        }//end

        protected void  onUpdateScrollTween (Object param1 )
        {
            _loc_2 = Vector2.lerp(param1.startPos,param1.endPos,param1.alpha);
            GlobalEngine.viewport.setScrollPosition(_loc_2);
            return;
        }//end

        protected void  onCompleteScrollTween ()
        {
            this.m_scrollTween = null;
            return;
        }//end

        public static World  getInstance ()
        {
            return m_instance;
        }//end

    }



