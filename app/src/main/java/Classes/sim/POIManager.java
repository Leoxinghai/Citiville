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
import Engine.Helpers.*;
import com.adobe.utils.*;
//import flash.geom.*;
//import flash.utils.*;

    public class POIManager implements IGameWorldStateObserver
    {
        private Dictionary m_pois ;
        private Dictionary m_poiTables ;
        private Dictionary m_resources ;
        private Dictionary m_npcs ;

        public  POIManager ()
        {
            this.m_poiTables = new Dictionary();
            this.m_resources = new Dictionary();
            this.m_pois = new Dictionary();
            this.m_npcs = new Dictionary();
            return;
        }//end

        public void  initialize ()
        {
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

        public void  cleanUp ()
        {
            this.m_poiTables = null;
            this.m_resources = null;
            this.m_pois = null;
            this.m_npcs = null;
            return;
        }//end

        public void  initializePOIs (Array param1 )
        {
            MapResource _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            	_loc_2 = param1.get(i0);

                this.addPOI(_loc_2);
            }
            return;
        }//end

        public void  addPOI (MapResource param1 )
        {
            XML _loc_9 =null ;
            _loc_2 = param1.getItem ();
            if (_loc_2.xml.poiInfo.length() == 0)
            {
                return;
            }
            _loc_3 = _loc_2.xml.poiInfo.get(0) ;
            _loc_4 = _loc_3.@attraction;
            _loc_5 = _loc_3.@influenceRadius;
            _loc_6 = _loc_3.@maxNPCs;
            _loc_7 = _loc_3.@maxLoiterActions;
            POI _loc_8 =new POI(param1 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 );
            for(int i0 = 0; i0 < _loc_3.action.size(); i0++) 
            {
            	_loc_9 = _loc_3.action.get(i0);

                _loc_8.addAction(_loc_9.@name);
            }
            if (this.m_pois == null)
            {
                this.m_pois = new Dictionary(true);
            }
            this.m_pois.put(param1,  _loc_8);
            this.updatePOI(_loc_8.resource);
            return;
        }//end

        public void  removePOI (MapResource param1 )
        {
            if (this.m_pois.get(param1) != null)
            {
                delete this.m_pois.get(param1);
                this.m_poiTables = null;
                this.m_poiTables = new Dictionary();
            }
            if (this.m_resources.get(param1) != null)
            {
                delete this.m_poiTables.get(param1);
            }
            return;
        }//end

        public void  updatePOI (MapResource param1 )
        {
            if (this.m_pois.get(param1) != null && this.m_poiTables)
            {
                this.m_poiTables = null;
                this.m_poiTables = new Dictionary();
            }
            if (this.m_resources.get(param1) != null)
            {
                delete this.m_poiTables.get(param1);
            }
            return;
        }//end

        public POI  selectPOIFromOriginForNPC (MapResource param1 ,NPC param2 )
        {
            POI _loc_6 =null ;
            if (this.m_poiTables.get(param1) == null)
            {
                this.buildPOITable(param1);
                this.m_resources.put(param1,  param1);
            }
            _loc_3 = Math.random ();
            POI _loc_4 =null ;
            double _loc_5 =0;
            for(int i0 = 0; i0 < this.m_poiTables.get(param1).size(); i0++) 
            {
            	_loc_6 = this.m_poiTables.get(param1).get(i0);

                if (_loc_3 <= _loc_6.probability + _loc_5 && this.addNPC(param2, _loc_6))
                {
                    _loc_4 = _loc_6;
                    break;
                }
                _loc_5 = _loc_5 + _loc_6.probability;
            }
            return _loc_4;
        }//end

        public Array  getAllPOIs ()
        {
            return DictionaryUtil.getKeys(this.m_pois);
        }//end

        public Array  getResourcesByPredicate (Function param1 )
        {
            Object _loc_3 =null ;
            POI _loc_4 =null ;
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < this.m_pois.size(); i0++) 
            {
            	_loc_3 = this.m_pois.get(i0);

                _loc_4 = this.m_pois.get(_loc_3);
                if (param1(_loc_4))
                {
                    _loc_2.push(_loc_3);
                }
            }
            return _loc_2;
        }//end

        private void  buildPOITable (MapResource param1 )
        {
            POI _loc_4 =null ;
            Point _loc_5 =null ;
            Point _loc_6 =null ;
            double _loc_7 =0;
            POI _loc_8 =null ;
            Array _loc_2 =new Array ();
            int _loc_3 =0;
            for(int i0 = 0; i0 < this.m_pois.size(); i0++) 
            {
            	_loc_4 = this.m_pois.get(i0);

                if (_loc_4.resource.isActive == false)
                {
                    continue;
                }
                _loc_5 = new Point(param1.positionX, param1.positionY);
                _loc_6 = new Point(_loc_4.resource.positionX, _loc_4.resource.positionY);
                _loc_7 = MathUtil.distance(_loc_5, _loc_6);
                if (_loc_7 > _loc_4.influenceRadius)
                {
                    continue;
                }
                _loc_8 = _loc_4.clone();
                _loc_8.calculateAttraction(_loc_7);
                _loc_3 = _loc_3 + _loc_8.attraction;
                _loc_2.push(_loc_8);
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            	_loc_4 = _loc_2.get(i0);

                _loc_4.setProbability(_loc_3);
            }
            _loc_2.sort(this.sortByProbability);
            this.m_poiTables.put(param1,  _loc_2);
            return;
        }//end

        private int  sortByProbability (POI param1 ,POI param2 )
        {
            return param1.probability - param2.probability;
        }//end

        private boolean  addNPC (NPC param1 ,POI param2 )
        {
            if (this.m_npcs.get(param2.resource.getId()) == null)
            {
                this.m_npcs.put(param2.resource.getId(),  new Array());
                this.m_npcs.get(param2.resource.getId()).push(param1.getId());
                return true;
            }
            if (this.m_npcs.get(param2.resource.getId()).length < param2.maxNPCs)
            {
                this.m_npcs.get(param2.resource.getId()).push(param1.getId());
                return true;
            }
            return false;
        }//end

        public void  removeNPC (NPC param1 ,POI param2 )
        {
            if (this.m_npcs.get(param2.resource.getId()) == null)
            {
                return;
            }
            this.m_npcs.get(param2.resource.getId()).pop();
            return;
        }//end

        public void  clearAllNPCs (MapResource param1 )
        {
            if (this.m_pois.get(param1) != null)
            {
                this.m_npcs.put(param1.getId(),  new Array());
            }
            return;
        }//end

    }



