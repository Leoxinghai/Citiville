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
import Display.*;
import Display.MarketUI.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import GameMode.*;
import Modules.stats.experiments.*;
import Transactions.*;

import com.adobe.crypto.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.net.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class WildernessSim implements IGameWorldStateObserver
    {
        private  int MAX_DISTANCE =9999;
        private  int WIDTH =2;
        private  int HEIGHT =2;
        private  int MAX_DRAWS_PER_FRAME =10;
        private int m_rollCount ;
        private XMLList m_regions ;
        private Array m_heatMapPoints ;
        private Array m_trees ;
        private Dictionary m_drawObjs ;
        private int m_treesDrawn =0;
        private int m_w ;
        private int m_z ;
        private URLLoader m_loader =null ;
        private String m_wildernessData ;
        private  int NUM_WILDERNESS_DATA =20;
        private int m_dataNdx =0;
        private int m_retryCount =0;
        private  int MAX_RETRY =3;
        private boolean m_needGeneration =false ;
        private boolean m_markersEnabled =true ;
        private Vector<Prop> m_markers;
        private static  String EXPANSION_MARKER_ITEM_NAME ="prop_expansion_marker";
        private static boolean m_initialized =false ;
        private static boolean m_heatMapInitialized =false ;

        public  WildernessSim ()
        {
            this.m_markers = new Vector<Prop>(0);
            this.m_rollCount = 0;
            this.m_w = int(Global.world.ownerId);
            this.m_z = Number("0x" + MD5.hash(Global.world.ownerId).substr(0, 8));
            if (!m_heatMapInitialized)
            {
                this.initializeHeatMap();
                m_heatMapInitialized = true;
            }
            this.m_dataNdx = Number(Global.player.uid) % this.NUM_WILDERNESS_DATA;
            this.loadWildernessData(this.m_dataNdx);
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            this.hideAllExpansionMarkers(false);
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        protected void  loadWildernessData (int param1 )
        {
            if (param1 < 0)
            {
                param1 = 0;
            }
            if (param1 >= this.NUM_WILDERNESS_DATA)
            {
                param1 = this.NUM_WILDERNESS_DATA - 1;
            }
            String _loc_2 ="assets/wilderness/WildernessData/WildernessClientData"+(param1 +1)+".txt";
            _loc_2 = Global.getAssetURL(_loc_2);
            String _loc_3 =AssetUrlManager.instance.lookUpUrl(_loc_2 );
            URLRequest _loc_4 =new URLRequest(_loc_3 );
            this.m_loader = new URLLoader();
            this.m_loader.addEventListener(IOErrorEvent.IO_ERROR, this.onDataError);
            this.m_loader.addEventListener(Event.COMPLETE, this.onDataLoaded);
            this.m_loader.load(_loc_4);
            return;
        }//end

        private void  onDataLoaded (Event event )
        {
            if (this.m_loader)
            {
                this.m_loader.removeEventListener(IOErrorEvent.IO_ERROR, this.onDataError);
                this.m_loader.removeEventListener(Event.COMPLETE, this.onDataLoaded);
            }
            this.m_wildernessData =(String) this.m_loader.data;
            this.m_loader = null;
            return;
        }//end

        private void  onDataError (Event event )
        {
            if (this.m_loader)
            {
                this.m_loader.removeEventListener(IOErrorEvent.IO_ERROR, this.onDataError);
                this.m_loader.removeEventListener(Event.COMPLETE, this.onDataLoaded);
            }
            this.m_retryCount++;
            if (this.m_retryCount <= this.MAX_RETRY)
            {
                this.loadWildernessData(this.m_dataNdx);
            }
            return;
        }//end

        public void  dbgReloadData (int param1 )
        {
            this.m_dataNdx = param1;
            this.m_wildernessData = null;
            this.loadWildernessData(this.m_dataNdx);
            m_initialized = false;
            Global.world.createOverlayBackground();
            return;
        }//end

        public int  getNextRandomNumber (int param1 ,int param2 )
        {
            this.m_z = 36969 * (this.m_z & 65535) + (this.m_z >> 16);
            this.m_w = 18000 * (this.m_w & 65535) + (this.m_w >> 16);
            _loc_3 = this(.m_z <<16)+this.m_w ;
            _loc_4 = param2-param1 ;
            _loc_3 = Math.abs(_loc_3) % _loc_4;
            _loc_3 = _loc_3 + param1;
            this.m_rollCount++;
            return _loc_3;
        }//end

        private Object  getWildernessRect ()
        {
            Object _loc_1 =new Object ();
            _loc_2 =Global.world.getWorldRect ();
            int _loc_3 =Global.gameSettings().getAttribute("farmSize");
            _loc_4 =Global.gameSettings().getAttribute("maxFarmSize");
            int _loc_5 =Global.gameSettings().getAttribute("wildernessLength");
            _loc_1.put("minX", -_loc_5);
            _loc_1.put("minY", -_loc_5);
            _loc_1.put("maxX", _loc_3+_loc_5);
            _loc_1.put("maxY", _loc_3+_loc_5);
            int _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1 );
            if (_loc_6 > 0)
            {
                _loc_1.put("minY",  _loc_1.get("minY") - 20);
                _loc_1.put("maxX",  _loc_1.get("maxX") + 40);
            }
            return _loc_1;
        }//end

        public void  initializeHeatMap ()
        {
            XML _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            Object _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            this.m_regions = Global.gameSettings().getWildernessRegions();
            this.m_heatMapPoints = new Array();
            _loc_1 = Global.gameSettings().getWildernessHeatMap();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                this.m_heatMapPoints.push({x:parseInt(_loc_2.@x), y:parseInt(_loc_2.@y)});
            }
            _loc_3 = Global.gameSettings().getAttribute("wildernessHeatMapMinRandomPoints");
            _loc_4 = Global.gameSettings().getAttribute("wildernessHeatMapMaxRandomPoints");
            _loc_5 = this.getNextRandomNumber(_loc_3, _loc_4);
            _loc_6 = this.getWildernessRect();
            _loc_7 = 0;
            while (_loc_7 < _loc_5)
            {

                _loc_8 = this.getNextRandomNumber(_loc_6.minX, _loc_6.maxX);
                _loc_9 = this.getNextRandomNumber(_loc_6.minY, _loc_6.maxY);
                this.m_heatMapPoints.push({x:_loc_8, y:_loc_9});
                _loc_7++;
            }
            return;
        }//end

        public Array  getTreesInRect (Rectangle param1 )
        {
            double _loc_5 =0;
            double _loc_6 =0;
            Array _loc_2 =new Array ();
            Rectangle _loc_3 =new Rectangle ();
            _loc_3.width = this.WIDTH;
            _loc_3.height = this.HEIGHT;
            int _loc_4 =0;
            while (_loc_4 < this.m_trees.length())
            {

                _loc_5 = this.m_trees.get(_loc_4).x;
                _loc_6 = this.m_trees.get(_loc_4).y;
                _loc_3.x = _loc_5;
                _loc_3.y = _loc_6;
                if (_loc_3.intersects(param1))
                {
                    _loc_2.push(this.m_trees.get(_loc_4));
                    this.m_trees.splice(_loc_4, 1);
                    _loc_4 = _loc_4 - 1;
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

        public boolean  isTreePositionInBounds (Array param1 ,double param2 ,double param3 )
        {
            Rectangle _loc_4 =null ;
            if (param1 && param1.length())
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_4 = param1.get(i0);

                    if (this.isTreePositionInRect(_loc_4, param2, param3))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public boolean  isTreePositionInRect (Rectangle param1 ,double param2 ,double param3 )
        {
            Rectangle _loc_4 =new Rectangle ();
            _loc_4.x = param2;
            _loc_4.y = param3;
            _loc_4.width = this.WIDTH;
            _loc_4.height = this.HEIGHT;
            boolean result =_loc_4.intersects(param1 );


            return result;
        }//end

        public void  removeTreesInRect (Rectangle param1 )
        {
            double _loc_5 =0;
            double _loc_6 =0;
            _loc_2 = this.m_trees.length ;
            Rectangle _loc_3 =new Rectangle ();
            _loc_3.width = this.WIDTH;
            _loc_3.height = this.HEIGHT;
            int _loc_4 =0;
            while (_loc_4 < this.m_trees.length())
            {

                _loc_5 = this.m_trees.get(_loc_4).x;
                _loc_6 = this.m_trees.get(_loc_4).y;
                _loc_3.x = _loc_5;
                _loc_3.y = _loc_6;
                if (_loc_3.intersects(param1))
                {
                    this.m_trees.splice(_loc_4, 1);
                    _loc_4 = _loc_4 - 1;
                }
                _loc_4++;
            }
            if (_loc_2 != this.m_trees.length())
            {
                this.m_treesDrawn = 0;
                Global.world.createOverlayBackground();
            }
            return;
        }//end

        public void  removeTreesInUnionedBounds (Array param1 )
        {
            Rectangle _loc_2 =null ;
            Rectangle _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                if (_loc_3 && _loc_3 instanceof Rectangle)
                {
                    if (!_loc_2)
                    {
                        _loc_2 = _loc_3;
                        continue;
                    }
                    _loc_2 = _loc_2.union(_loc_3);
                }
            }
            this.removeTreesInRect(_loc_2);
            return;
        }//end

        public Array  getBridgeBounds ()
        {
            Rectangle _loc_2 =null ;
            GameObject _loc_3 =null ;
            MapResource _loc_4 =null ;
            Array _loc_1 =new Array();
            _loc_5 = Global.world.getObjectsByClass(BridgePart).concat(Global.world.getObjectsByClass(Bridge));
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_3 = _loc_5.get(i0);

                if (_loc_3)
                {
                    _loc_4 =(MapResource) _loc_3;
                    _loc_2 = new Rectangle(_loc_4.positionX, _loc_4.positionY, _loc_4.sizeX, _loc_4.sizeY);
                    _loc_2.y = _loc_2.y - 3;
                    _loc_2.height = _loc_2.height + 4;
                    _loc_2.x = _loc_2.x - 3;
                    _loc_2.width = _loc_2.width + 4;
                    _loc_1.push(_loc_2);
                }
            }
            return _loc_1;
        }//end

        public Array  processExpansionsOnObjectPlace (MapResource param1 )
        {
            String _loc_4 =null ;
            Array _loc_2 =new Array();
            _loc_3 = param1.getItem();
            if (this.hasExpansionGrantInfo(_loc_3))
            {
                _loc_4 = this.getGrantedExpansionsOnPlace(_loc_3);
                if (_loc_4)
                {
                    _loc_2 = this.onProcessGrantedExpansions(param1, _loc_4, true, param1.notifyUserForExpansionsOnPlace);
                }
            }
            return _loc_2;
        }//end

        public Array  processExpansionsOnConstructionComplete (ConstructionSite param1 )
        {
            String _loc_4 =null ;
            Array _loc_2 =new Array();
            _loc_3 = param1.targetItem;
            if (this.hasExpansionGrantInfo(_loc_3))
            {
                _loc_4 = this.getGrantedExpansionsOnFinish(_loc_3);
                if (_loc_4)
                {
                    _loc_2 = this.onProcessGrantedExpansions(param1, _loc_4, false);
                }
            }
            return _loc_2;
        }//end

        public boolean  hasExpansionGrantInfo (Item param1 )
        {
            return param1 && (param1.bridge || param1.grantedExpansionType);
        }//end

        public String  getGrantedExpansionsOnFinish (Item param1 )
        {
            String _loc_2 =null ;
            if (param1.bridge && param1.bridge.grantedExpansionsOnFinish)
            {
                _loc_2 = param1.bridge.grantedExpansionsOnFinish;
            }
            else if (param1.grantedExpansionsOnFinish)
            {
                _loc_2 = param1.grantedExpansionsOnFinish;
            }
            return _loc_2;
        }//end

        public String  getGrantedExpansionsOnPlace (Item param1 )
        {
            String _loc_2 =null ;
            if (param1.bridge && param1.bridge.grantedExpansionsOnPlace)
            {
                _loc_2 = param1.bridge.grantedExpansionsOnPlace;
            }
            else if (param1.grantedExpansionsOnPlace)
            {
                _loc_2 = param1.grantedExpansionsOnPlace;
            }
            return _loc_2;
        }//end

        protected Array  onProcessGrantedExpansions (MapResource param1 ,String param2 ,boolean param3 =true ,Function param4 =null )
        {
            Rectangle _loc_13 =null ;
            Rectangle _loc_14 =null ;
            Array _loc_15 =null ;
            int _loc_16 =0;
            Object _loc_17 =null ;
            Wilderness _loc_18 =null ;
            int _loc_19 =0;
            Array _loc_5 =new Array();
            _loc_6 = param1isConstructionSite? (((ConstructionSite)param1).targetItem) : (param1.getItem());
            _loc_7 = param1(is ConstructionSite ? (((ConstructionSite)param1).targetItem) : (param1.getItem())).bridge.grantedExpansionType;
            _loc_8 = Global.gameSettings().getItemByName(_loc_7);
            Vector2 _loc_9 =new Vector2(param1.positionX ,param1.positionY );
            if (param3)
            {
                if (param1 instanceof Bridge)
                {
                    _loc_13 = new Rectangle(_loc_9.x, _loc_9.y - _loc_6.bridge.bridgeBoundary.y, _loc_6.bridge.bridgeBoundary.x, _loc_6.bridge.bridgeBoundary.y);
                }
                else
                {
                    _loc_13 = new Rectangle(_loc_9.x, _loc_9.y, param1.sizeX, param1.sizeY);
                }
                Global.world.wildernessSim.removeTreesInRect(_loc_13);
            }
            if (!param2)
            {
                ErrorManager.addError("No expansions coordinates");
                return _loc_5;
            }
            _loc_10 = param2.split("|");
            if (param2.split("|").length < 2 || _loc_10.length % 2 != 0)
            {
                ErrorManager.addError("Incorrect coordinate sequence for expansion");
                return _loc_5;
            }
            boolean _loc_11 =false ;
            int _loc_12 =0;
            while (_loc_12 < _loc_10.length())
            {

                _loc_14 = new Rectangle(_loc_10.get(_loc_12), _loc_10.get((_loc_12 + 1)), _loc_8.expansionWidth, _loc_8.expansionHeight);
                if (!Global.world.rectIntersectsTerritory(_loc_14))
                {
                    Global.world.expandMap(_loc_14);
                    _loc_11 = true;
                    if (param4 != null)
                    {
                        param4(_loc_14);
                    }
                    _loc_15 = Global.world.wildernessSim.getTreesInRect(_loc_14);
                    _loc_16 = _loc_15.length - 1;
                    while (_loc_16 >= 0)
                    {

                        _loc_17 = _loc_15.get(_loc_16);
                        if (!param3 || !Global.world.wildernessSim.isTreePositionInRect(_loc_13, _loc_17.x, _loc_17.y))
                        {
                            _loc_18 = new Wilderness(_loc_17.itemName);
                            _loc_19 = TWorldState.getNextTempID();
                            _loc_18.setId(_loc_19);
                            _loc_17.id = _loc_19;
                            _loc_18.setState("static");
                            _loc_18.setOuter(Global.world);
                            _loc_18.setDirection(_loc_17.dir);
                            _loc_18.setPosition(_loc_17.x, _loc_17.y);
                            _loc_18.isMoveLocked = true;
                            _loc_18.attach();
                            _loc_17.wildernessObj = _loc_18;
                        }
                        else
                        {
                            _loc_15.splice(_loc_16, 1);
                        }
                        _loc_16 = _loc_16 - 1;
                    }
                    _loc_5.push(_loc_15);
                }
                else
                {
                    _loc_5.push(new Array());
                }
                _loc_12 = _loc_12 + 2;
            }
            if (_loc_11)
            {
                Global.world.createOverlayBackground();
                Global.world.citySim.roadManager.onGameLoaded(null);
            }
            return _loc_5;
        }//end

        public boolean  gotTrees (double param1 )
        {
            if (param1 <= 0)
            {
                param1 = 0;
            }
            if (param1 > 1)
            {
                param1 = 1;
            }
            return this.m_treesDrawn > 0 && this.m_treesDrawn >= this.m_trees.length * param1;
        }//end

        public void  update ()
        {
            if (this.m_needGeneration)
            {
                this.generateWilderness();
                return;
            }
            if (this.m_trees == null)
            {
                return;
            }
            if (this.m_treesDrawn >= this.m_trees.length())
            {
                return;
            }
            this.drawTreesInGrassBitmap();
            return;
        }//end

        private void  drawTreesInGrassBitmap ()
        {
            return;

            double _loc_4 =0;
            double _loc_5 =0;
            int _loc_6 =0;
            String _loc_7 =null ;
            Wilderness _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            ItemImageInstance _loc_12 =null ;
            Bitmap _loc_13 =null ;
            CompositeItemImage _loc_14 =null ;
            if (!Global.world.canDrawInGrass() || !this.allTreeAssetsLoaded())
            {
                return;
            }
            int _loc_1 =0;
            _loc_2 = IsoMath.tilePosToPixelPos(0,0);
            int _loc_3 =0;
            while (_loc_3 < this.m_trees.length())
            {

                if (_loc_3 < this.m_treesDrawn)
                {
                }
                else
                {
                    this.m_treesDrawn++;
                    _loc_1++;
                    _loc_4 = this.m_trees.get(_loc_3).x;
                    _loc_5 = this.m_trees.get(_loc_3).y;
                    _loc_6 = this.m_trees.get(_loc_3).dir;
                    _loc_7 = this.m_trees.get(_loc_3).itemName;
                    _loc_8 = this.m_drawObjs.get(_loc_7);
                    if (_loc_8 == null)
                    {
                        break;
                    }
                    _loc_9 = _loc_8.getDisplayObject();
                    if (_loc_9 == null)
                    {
                        _loc_9 = _loc_8.createDisplayObject();
                        _loc_8.drawDisplayObject();
                        _loc_8.updateDisplayObjectTransform();
                    }
                    _loc_10 = _loc_8.getScreenPosition().x - _loc_2.x;
                    _loc_11 = _loc_8.getScreenPosition().y - _loc_2.y;
                    _loc_12 = _loc_8.getItem().getCachedImage("static");
                    if (_loc_12 != null)
                    {
                        _loc_13 = null;
                        if (_loc_12.image instanceof Bitmap)
                        {
                            _loc_13 =(Bitmap) _loc_12.image;
                        }
                        else if (_loc_12.image instanceof CompositeItemImage)
                        {
                            _loc_14 =(CompositeItemImage) _loc_12.image;
                            if (_loc_14.getBaseImage() instanceof Bitmap)
                            {
                                _loc_13 =(Bitmap) _loc_14.getBaseImage();
                            }
                        }
                        Global.world.drawInGrass(_loc_13, _loc_4, _loc_5, _loc_10, _loc_11);
                    }
                    if (_loc_1 > this.MAX_DRAWS_PER_FRAME)
                    {
                        break;
                    }
                }
                _loc_3++;
            }
            return;
        }//end

        public void  generateWilderness ()
        {
            Rectangle _loc_2 =null ;
            GameObject _loc_3 =null ;
            MapResource _loc_4 =null ;
            Array _loc_7 =null ;
            Road _loc_8 =null ;
            Rectangle _loc_9 =null ;
            Rectangle _loc_10 =null ;
            TrainTracks _loc_21 =null ;
            TrainStation _loc_22 =null ;
            int _loc_23 =0;
            int _loc_24 =0;
            boolean _loc_25 =false ;
            int _loc_26 =0;
            int _loc_27 =0;
            boolean _loc_28 =false ;
            XML _loc_29 =null ;
            int _loc_30 =0;
            Object _loc_31 =null ;
            XML _loc_32 =null ;
            String _loc_33 =null ;
            int _loc_34 =0;
            if (m_initialized)
            {
                this.m_treesDrawn = 0;
                return;
            }
            if (this.m_wildernessData == null)
            {
                this.m_needGeneration = true;
                return;
            }
            this.m_needGeneration = false;
            Array _loc_1 =new Array ();
            _loc_5 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
            _loc_1.concat(this.getBridgeBounds());
            _loc_6 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.TRAIN_STATION));
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_3 = _loc_6.get(i0);

                _loc_21 =(TrainTracks) _loc_3;
                _loc_22 =(TrainStation) _loc_3;
                _loc_4 =(MapResource) _loc_3;
                if (_loc_21 == null && _loc_22 == null)
                {
                    continue;
                }
                _loc_2 = new Rectangle(_loc_4.positionX, _loc_4.positionY, _loc_4.sizeX, _loc_4.sizeY);
                _loc_2.y = _loc_2.y - 3;
                _loc_2.height = _loc_2.height + 4;
                if (_loc_5 > 0)
                {
                    _loc_2.height = _loc_2.height + 4;
                }
                _loc_2.x = _loc_2.x - 3;
                _loc_2.width = _loc_2.width + 4;
                if (_loc_5 > 0)
                {
                    _loc_2.width = _loc_2.width + 4;
                }
                _loc_1.push(_loc_2);
            }
            _loc_7 = Global.world.getObjectsByClass(Road);
            _loc_9 = new Rectangle();
            _loc_10 = null;
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            	_loc_8 = _loc_7.get(i0);

                _loc_9.x = _loc_8.positionX;
                _loc_9.y = _loc_8.positionY;
                _loc_9.width = _loc_8.sizeX;
                _loc_9.height = _loc_8.sizeY;
                if (!Global.world.rectInTerritory(_loc_9))
                {
                    if (_loc_10 == null)
                    {
                        _loc_10 = _loc_9;
                        continue;
                    }
                    _loc_10 = _loc_10.union(_loc_9);
                }
            }
            if (_loc_10 != null)
            {
                _loc_10.y = _loc_10.y - 3;
                _loc_10.height = _loc_10.height + 4;
                if (_loc_5 > 0)
                {
                    _loc_10.height = _loc_10.height + 4;
                }
                _loc_10.x = _loc_10.x - 3;
                _loc_10.width = _loc_10.width + 4;
                if (_loc_5 > 0)
                {
                    _loc_10.width = _loc_10.width + 4;
                }
                _loc_1.push(_loc_10);
            }
            this.m_trees = new Array();
            this.m_treesDrawn = 0;
            _loc_11 = this.getWildernessRect();
            Rectangle _loc_12 =new Rectangle ();
            int _loc_13 =0;
            _loc_14 = charCodeAt" ".();
            int _loc_15 =0;
            int _loc_16 =0;
            Rectangle _loc_17 =new Rectangle(_loc_11.minX ,33,_loc_11.maxX -_loc_11.minX ,_loc_11.maxY -33);
            _loc_18 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TREE_REMOVAL);
            _loc_19 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TREE_REMOVAL)==ExperimentDefinitions.TREE_REMOVAL_THIRTY? (30) : (_loc_18 == ExperimentDefinitions.TREE_REMOVAL_FOURTY ? (40) : (_loc_18 == ExperimentDefinitions.TREE_REMOVAL_FIFTY ? (50) : (_loc_18 == ExperimentDefinitions.TREE_REMOVAL_SIXTY ? (60) : (_loc_18 == ExperimentDefinitions.TREE_REMOVAL_SEVENTY ? (70) : (0)))));
            int _loc_20 =_loc_11.maxX -this.HEIGHT ;
            while (_loc_20 >= _loc_11.minX)
            {

                _loc_23 = _loc_11.maxY - this.HEIGHT;
                while (_loc_23 >= _loc_11.minY)
                {

                    _loc_24 = this.m_wildernessData.charCodeAt(_loc_13);
                    _loc_13++;
                    if (_loc_24 < 32)
                    {
                        _loc_24 = this.m_wildernessData.charCodeAt(_loc_13);
                        _loc_13++;
                    }
                    _loc_25 = _loc_24 > _loc_14;
                    if (!_loc_25)
                    {
                    }
                    else
                    {
                        _loc_24 = _loc_24 - (_loc_14 + 1);
                        _loc_26 = _loc_24 & 3;
                        _loc_27 = _loc_24 >> 2 & 15;
                        if (!Global.world.tileInGroundmap(_loc_20, _loc_23))
                        {
                        }
                        else
                        {
                            _loc_12.x = _loc_20;
                            _loc_12.y = _loc_23;
                            _loc_12.width = this.WIDTH;
                            _loc_12.height = this.HEIGHT;
                            if (!Global.world.citySim.waterManager.positionValidForTree(_loc_12))
                            {
                            }
                            else
                            {
                                _loc_28 = false;
                                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                                {
                                	_loc_2 = _loc_1.get(i0);

                                    if (_loc_12.intersects(_loc_2))
                                    {
                                        _loc_28 = true;
                                    }
                                }
                                if (_loc_28)
                                {
                                }
                                else
                                {
                                    if (_loc_19 > 0 && _loc_12.intersects(_loc_17))
                                    {
                                        _loc_34 = this.getNextRandomNumber(1, 100);




                                    }
                                    _loc_12.width = 1;
                                    _loc_12.height = 1;
                                    _loc_12.x = _loc_12.x + 2;
                                    if (Global.world.territory.rectInTerritory(_loc_12))
                                    {
                                    }
                                    else
                                    {
                                        _loc_12.x = _loc_12.x - 2;
                                        _loc_12.y = _loc_12.y + 2;
                                        if (Global.world.territory.rectInTerritory(_loc_12))
                                        {
                                        }
                                        else if (_loc_26 < 0 || _loc_26 >= this.m_regions.length())
                                        {
                                        }
                                        else
                                        {
                                            _loc_29 = this.m_regions.get(_loc_26);
                                            _loc_30 = _loc_29.asset.length();
                                            if (_loc_27 >= _loc_30)
                                            {
                                                _loc_27 = 0;
                                            }
                                            _loc_32 = _loc_29.asset.get(_loc_27);
                                            _loc_33 = _loc_32.@name;

                                            _loc_31 = {x:_loc_20, y:_loc_23, dir:0, itemName:_loc_33};
                                            this.m_trees.push(_loc_31);
                                            this.loadTreeAsset(_loc_31);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _loc_23 = _loc_23 - this.HEIGHT;
                }
                _loc_20 = _loc_20 - this.WIDTH;
            }
            m_initialized = true;
            return;
        }//end

        public void  saveWilderness (int param1 )
        {
            int _loc_10 =0;
            Vector3 _loc_11 =null ;
            double _loc_12 =0;
            int _loc_13 =0;
            double _loc_14 =0;
            double _loc_15 =0;
            int _loc_16 =0;
            int _loc_17 =0;
            XML _loc_18 =null ;
            String _loc_19 =null ;
            double _loc_20 =0;
            XMLList _loc_21 =null ;
            int _loc_22 =0;
            boolean _loc_23 =false ;
            Vector3 _loc_24 =null ;
            double _loc_25 =0;
            XML _loc_26 =null ;
            int _loc_27 =0;
            int _loc_2 =1;
            this.m_rollCount = param1 * 255;
            _loc_3 = this.getWildernessRect();
            Rectangle _loc_4 =new Rectangle ();
            _loc_5 = charCodeAt" ".();
            int _loc_6 =0;
            String _loc_7 ="";
            _loc_8 = _loc_3.maxX-this.HEIGHT;
            while (_loc_8 >= _loc_3.minX)
            {

                _loc_10 = _loc_3.maxY - this.HEIGHT;
                while (_loc_10 >= _loc_3.minY)
                {

                    _loc_11 = new Vector3(_loc_8, _loc_10);
                    _loc_6++;
                    if (_loc_6 > 80)
                    {
                        _loc_7 = _loc_7 + "\n";
                        _loc_6 = 0;
                    }
                    _loc_12 = this.MAX_DISTANCE;
                    _loc_13 = 0;
                    while (_loc_13 < this.m_heatMapPoints.length())
                    {

                        _loc_24 = _loc_11.subtract(new Vector3(this.m_heatMapPoints.get(_loc_13).x, this.m_heatMapPoints.get(_loc_13).y));
                        _loc_2 = Math.sqrt(_loc_24.dot(_loc_24));
                        if (_loc_25 < _loc_12)
                        {
                            _loc_12 = _loc_25;
                            if (_loc_12 < this.WIDTH || _loc_12 < this.HEIGHT)
                            {
                                break;
                            }
                        }
                        _loc_13++;
                    }
                    _loc_14 = this.getNextRandomNumber(0, 100) / 100;
                    _loc_15 = this.getNextRandomNumber(0, 100) / 100;
                    _loc_16 = this.getNextRandomNumber(0, 3);
                    _loc_17 = this.findRegionNdx(_loc_12);
                    if (_loc_17 < 0 || _loc_17 >= this.m_regions.length())
                    {
                        _loc_7 = _loc_7 + " ";
                    }
                    else
                    {
                        _loc_18 = this.m_regions.get(_loc_17);
                        if (!_loc_18)
                        {
                            _loc_7 = _loc_7 + " ";
                        }
                        else
                        {
                            _loc_19 = _loc_18.@density;
                            if (_loc_14 > parseFloat(_loc_19))
                            {
                                _loc_7 = _loc_7 + " ";
                            }
                            else
                            {
                                _loc_20 = 0;
                                _loc_21 = _loc_18.asset;
                                _loc_22 = 0;
                                _loc_23 = false;
                                _loc_22 = 0;
                                while (_loc_22 < _loc_21.length())
                                {

                                    _loc_26 = _loc_21.get(_loc_22);
                                    if (_loc_15 <= _loc_20 + parseFloat(_loc_26.@probability))
                                    {
                                        _loc_23 = true;
                                        break;
                                    }
                                    else
                                    {
                                        _loc_20 = _loc_20 + parseFloat(_loc_26.@probability);
                                    }
                                    _loc_22++;
                                }
                                if (_loc_23)
                                {
                                    _loc_27 = (_loc_5 + 1) + _loc_17 + 4 * _loc_22;
                                    _loc_7 = _loc_7 + String.fromCharCode(_loc_27);
                                }
                                else
                                {
                                    _loc_7 = _loc_7 + " ";
                                }
                            }
                        }
                    }
                    _loc_10 = _loc_10 - this.HEIGHT;
                }
                _loc_8 = _loc_8 - this.WIDTH;
            }
            FileReference _loc_9 =new FileReference ();
            _loc_9.save(_loc_7, "WildernessClientData" + (param1 + 1) + ".txt");
            return;
        }//end

        public XML  findRegion (double param1 )
        {
            XML _loc_3 =null ;
            double _loc_4 =0;
            XML _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_regions.size(); i0++)
            {
            	_loc_3 = this.m_regions.get(i0);

                _loc_4 = _loc_3.@distance;
                if (param1 < _loc_3.@distance)
                {
                    return _loc_2;
                }
                _loc_2 = _loc_3;
            }
            return _loc_2;
        }//end

        public int  findRegionNdx (double param1 )
        {
            XML _loc_4 =null ;
            double _loc_5 =0;
            int _loc_2 =-1;
            XML _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_regions.size(); i0++)
            {
            	_loc_4 = this.m_regions.get(i0);

                _loc_5 = _loc_4.@distance;
                if (param1 < _loc_4.@distance)
                {
                    return _loc_2;
                }
                _loc_3 = _loc_4;
                _loc_2++;
            }
            return _loc_2;
        }//end

        public Wilderness  placeWilderness (XML param1 ,int param2 ,int param3 ,double param4 ,double param5 ,int param6 ,Shape param7 )
        {
            XML _loc_10 =null ;
            Object _loc_11 =null ;
            String _loc_12 =null ;
            Point _loc_13 =null ;
            if (param4 > parseFloat(param1.@density))
            {
                return null;
            }
            double _loc_8 =0;
            _loc_9 = param1.asset ;
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            	_loc_10 = _loc_9.get(i0);

                if (param5 <= _loc_8 + parseFloat(_loc_10.@probability))
                {
                    if (param7 != null)
                    {
                        _loc_13 = IsoMath.tilePosToPixelPos(param2, param3);
                        param7.graphics.moveTo((_loc_13.x - 1), _loc_13.y);
                        param7.graphics.lineTo((_loc_13.x + 1), _loc_13.y);
                        param7.graphics.moveTo(_loc_13.x, (_loc_13.y - 1));
                        param7.graphics.lineTo(_loc_13.x, (_loc_13.y + 1));
                    }
                    _loc_12 = _loc_10.@name;
                    _loc_11 = {x:param2, y:param3, dir:param6, itemName:_loc_12};
                    this.m_trees.push(_loc_11);
                    this.loadTreeAsset(_loc_11);
                    return null;
                    continue;
                }
                _loc_8 = _loc_8 + parseFloat(_loc_10.@probability);
            }
            return null;
        }//end

        public void  loadTreeAsset (Object param1 )
        {
            Wilderness _loc_4 =null ;
            if (this.m_drawObjs == null)
            {
                this.m_drawObjs = new Dictionary();
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_drawObjs.length())
            {

                _loc_4 =(Wilderness) this.m_drawObjs.get(_loc_2);
                if (param1.itemName == _loc_4.getItemName())
                {
                    return;
                }
                _loc_2++;
            }
            Wilderness _loc_3 =new Wilderness(param1.itemName );
            _loc_3.setState("static");
            _loc_3.setDirection(0);
            _loc_3.setPosition(0, 0);
            this.m_drawObjs.put(_loc_3.getItemName(),  _loc_3);
            _loc_3.loadCurrentImage();
            return;
        }//end

        public boolean  allTreeAssetsLoaded ()
        {
            Wilderness _loc_2 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_drawObjs.length())
            {

                _loc_2 =(Wilderness) this.m_drawObjs.get(_loc_1);
                if (_loc_2.isDrawImageLoading())
                {
                    return false;
                }
                _loc_1++;
            }
            return true;
        }//end

        public void  trySellExpansion (int param1 ,int param2 ,boolean param3 )
        {
            this.hideAllExpansionMarkers(true);
            CatalogParams _loc_4 =new CatalogParams("expansion","expand_12_12");
            UI.displayCatalog(_loc_4);
            Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBuyExpansion"));
            _loc_5 = Math.floor(param1/12);
            _loc_6 = Math.floor(param2/12);
            StatsManager.count("game_actions_unsampled", "click_on_expanded_area", param3 ? ("on_sale") : ("normal"), String(_loc_5), String(_loc_6));
            return;
        }//end

        public void  onTerritoryRedraw (TerritoryMap param1 )
        {
            Object _loc_4 =null ;
            Prop _loc_5 =null ;
            this.hideAllExpansionMarkers(false);
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ONBOARD_SALE);
            if (!this.m_markersEnabled || Global.isVisiting() || Global.player.level < 8 || _loc_2 != 3)
            {
                return;
            }
            _loc_3 = this.findAllBorderLocations(param1);
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_5 = new Prop(EXPANSION_MARKER_ITEM_NAME);
                _loc_5.setState("static");
                _loc_5.setPosition(_loc_4.x, _loc_4.y);
                _loc_5.setDirection(_loc_4.dir);
                _loc_5.setOuter(Global.world);
                _loc_5.attach();
                this.m_markers.push(_loc_5);
            }
            return;
        }//end

        public void  hideAllExpansionMarkers (boolean param1 )
        {
            Prop _loc_2 =null ;
            while (this.m_markers.length > 0)
            {

                _loc_2 = this.m_markers.pop();
                _loc_2.detach();
                _loc_2.cleanUp();
            }
            if (param1 !=null)
            {
                this.m_markersEnabled = false;
            }
            return;
        }//end

        private Array  findAllBorderLocations (TerritoryMap param1 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            int _loc_10 =0;
            Array _loc_2 =new Array();
            _loc_3 = param1.gridDelta ;
            _loc_4 = _loc_3*10;
            Rectangle _loc_5 =new Rectangle(_loc_6 ,_loc_7 ,_loc_3 ,_loc_3 );
            _loc_6 = -_loc_4;
            while (_loc_6 <= _loc_4)
            {

                _loc_7 = -_loc_4;
                while (_loc_7 <= _loc_4)
                {

                    _loc_5.x = _loc_6;
                    _loc_5.y = _loc_7;
                    if (param1.pointInTerritory(_loc_6, _loc_7))
                    {
                    }
                    else
                    {
                        _loc_8 = Global.world.citySim.waterManager.positionValidForExpansion(_loc_5);
                        if (!_loc_8)
                        {
                        }
                        else
                        {
                            _loc_9 = GMExpand.isValidExpansionSquare(_loc_5);
                            if (!_loc_9)
                            {
                            }
                            else
                            {
                                _loc_10 = this.getOwnedNeighbor(param1, _loc_6, _loc_7, _loc_3);
                                if (_loc_10 >= 0)
                                {
                                    _loc_2.push({x:_loc_6, y:_loc_7, dir:_loc_10});
                                }
                            }
                        }
                    }
                    _loc_7 = _loc_7 + _loc_3;
                }
                _loc_6 = _loc_6 + _loc_3;
            }
            return _loc_2;
        }//end

        private int  getOwnedNeighbor (TerritoryMap param1 ,int param2 ,int param3 ,int param4 )
        {
            if (param1.pointInTerritory(param2 - param4, param3))
            {
                return Constants.DIRECTION_NE;
            }
            if (param1.pointInTerritory(param2 + param4, param3))
            {
                return Constants.DIRECTION_SW;
            }
            if (param1.pointInTerritory(param2, param3 - param4))
            {
                return Constants.DIRECTION_NW;
            }
            if (param1.pointInTerritory(param2, param3 + param4))
            {
                return Constants.DIRECTION_SE;
            }
            return -1;
        }//end

    }




