package Mechanics.ClientDisplayMechanics;

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
import Engine.Events.*;
import Engine.Interfaces.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import com.adobe.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class MultiAssetRendererBase implements IClientGameMechanic
    {
        private MechanicMapResource m_owner ;
        private String m_type ;
        private MechanicConfigData m_config ;
        private Sprite m_ownerDisplayObj ;
        private Array m_displayLocations ;
        private Dictionary m_displayLocationMap ;
        private Dictionary m_displayContentMap ;
        private boolean m_isDirty =true ;
        private boolean m_rotateable ;
        private int m_currentRotation ;
        private boolean m_useOrigin =false ;
        private boolean m_forceRefreshOnDataChange =false ;
        public static  String DEFAULT_IMAGE_NAME ="static";
        private static  Array m_knownDirections =new Array("SW","SE","NE","NW");

        public  MultiAssetRendererBase (boolean param1 =false )
        {
            this.m_displayLocations = new Array();
            this.m_displayContentMap = new Dictionary();
            this.m_displayLocationMap = new Dictionary();
            this.m_rotateable = param1;
            return;
        }//end

        public MechanicMapResource  owner ()
        {
            return this.m_owner;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public MechanicConfigData  config ()
        {
            return this.m_config;
        }//end

        public void  detachDisplayObject ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            Object _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            IDynamicDisplayObject _loc_5 =null ;
            if (this.m_ownerDisplayObj != this.m_owner.getDisplayObject())
            {
                this.m_ownerDisplayObj = null;
                this.m_isDirty = true;
            }
            boolean _loc_2 =false ;
            do
            {

                if (this.m_isDirty)
                {
                    _loc_2 = true;
                    break;
                }
                if (MechanicManager.getForceDisplayRefresh(this.m_type, this.m_owner.getId()))
                {
                    MechanicManager.setForceDisplayRefresh(this.m_type, this.m_owner.getId(), false);
                    _loc_2 = true;
                    break;
                }
            }while (0)
            if (_loc_2)
            {
                this.m_isDirty = false;
                this.refreshDisplayData();
            }
            for(int i0 = 0; i0 < this.m_displayLocations.size(); i0++)
            {
            	_loc_3 = this.m_displayLocations.get(i0);

                if (!this.m_displayContentMap.get(_loc_3.id))
                {
                    continue;
                }
                _loc_4 = this.m_displayContentMap.get(_loc_3.id);
                if (!this.m_ownerDisplayObj.contains(_loc_4))
                {
                    this.m_ownerDisplayObj.addChild(_loc_4);
                }
                if (this.m_rotateable && this.m_currentRotation != this.m_owner.getDirection())
                {
                    this.m_isDirty = true;
                    this.m_currentRotation = this.m_owner.getDirection();
                }
                if (_loc_4 instanceof IDynamicDisplayObject)
                {
                    _loc_5 =(IDynamicDisplayObject) _loc_4;
                    _loc_5.onUpdate(param1);
                }
            }
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            this.m_type = param2.type;
            this.m_ownerDisplayObj =(Sprite) this.m_owner.getDisplayObject();
            _loc_3 = param2.rawXMLConfig.@rotateable.get(0);
            if (_loc_3 != null)
            {
                this.m_rotateable = _loc_3.toString() == "true";
            }
            if (this.m_rotateable)
            {
                this.m_currentRotation = this.m_owner.getDirection();
            }
            this.m_useOrigin = param2.rawXMLConfig.@useOrigin != undefined ? (param2.rawXMLConfig.@useOrigin == "true") : (false);
            this.m_forceRefreshOnDataChange = param2.rawXMLConfig.@forceRefreshOnDataChange != undefined ? (param2.rawXMLConfig.@forceRefreshOnDataChange == "true") : (false);
            this.initDisplayLocations();
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            this.m_owner.getItem().addEventListener(LoaderEvent.LOADED, this.onOwnerItemImageLoaded, false, 1, true);
            this.m_owner.addEventListener(GameObjectEvent.STATE_CHANGE, this.onStateChanged, false, 0, true);
            return;
        }//end

        protected void  forEachDisplayLocationDirection (Function param1 )
        {
            return;
        }//end

        protected void  forEachDisplayLocationWithinDirection (Function param1 ,XML param2 )
        {
            return;
        }//end

        private void  initDisplayLocations ()
        {
            this .forEachDisplayLocationDirection (void  (String param1 ,XML param2 )
            {
                Array locations ;
                direction = param1;
                data = param2;
                if (direction == null || direction.length == 0)
                {
                    direction;
                }
                locations = new Array();
                forEachDisplayLocationWithinDirection (void  (int param1 ,double param2 ,double param3 )
                {
                    Object _loc_4 ={id param1 ,x ,y };
                    locations.push(_loc_4);
                    return;
                }//end
                , data);
                m_displayLocationMap.put(direction,  locations);
                if (!m_displayLocationMap.get("ALL"))
                {
                    m_displayLocationMap.put("ALL",  locations);
                }
                return;
            }//end
            );
            this.m_displayLocations = this.m_displayLocationMap.get("ALL");
            return;
        }//end

        protected void  onSourceDataChanged ()
        {
            this.m_isDirty = true;
            return;
        }//end

        private void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (this.m_forceRefreshOnDataChange && event.subType != "NPCEnterAction")
            {
                this.m_isDirty = true;
            }
            return;
        }//end

        protected void  onStateChanged (GameObjectEvent event )
        {
            this.m_ownerDisplayObj =(Sprite) this.m_owner.getDisplayObject();
            this.refreshDisplayObjects();
            return;
        }//end

        protected void  onOwnerItemImageLoaded (Event event )
        {
            this.m_ownerDisplayObj =(Sprite) this.m_owner.getDisplayObject();
            this.refreshDisplayObjects();
            return;
        }//end

        private void  refreshDisplayObjects ()
        {
            int _loc_1 =0;
            for(int i0 = 0; i0 < DictionaryUtil.getKeys(this.m_displayContentMap).size(); i0++)
            {
            	_loc_1 = DictionaryUtil.getKeys(this.m_displayContentMap).get(i0);

                if (this.m_displayContentMap.get(_loc_1) && this.m_ownerDisplayObj)
                {
                    if (this.m_ownerDisplayObj.contains(this.m_displayContentMap.get(_loc_1)))
                    {
                        this.m_ownerDisplayObj.removeChild(this.m_displayContentMap.get(_loc_1));
                    }
                    this.m_ownerDisplayObj.addChild(this.m_displayContentMap.get(_loc_1));
                    this.setDisplayPosition(this.m_displayContentMap.get(_loc_1), _loc_1);
                }
            }
            return;
        }//end

        protected void  onItemImageLoaded (Event event )
        {
            _loc_2 =(Item) event.target;
            if (!_loc_2)
            {
                return;
            }
            _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
            this.refreshDisplayData();
            return;
        }//end

        protected ItemImageInstance  getItemImage (Item param1 ,int param2 )
        {
            return param1.getCachedImage(DEFAULT_IMAGE_NAME, null, param2);
        }//end

        protected boolean  addToDisplay (int param1 ,String param2 )
        {
            Object _loc_4 =null ;
            ItemImageInstance _loc_6 =null ;
            MechanicConfigData _loc_7 =null ;
            String _loc_9 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            DisplayObject _loc_12 =null ;
            boolean _loc_3 =false ;
            this.m_displayLocations = this.getDisplayLocationsForRotation(this.m_owner.getDirection());
            for(int i0 = 0; i0 < this.m_displayLocations.size(); i0++)
            {
            	_loc_4 = this.m_displayLocations.get(i0);

                if (_loc_4.id == param1)
                {
                    _loc_3 = true;
                    break;
                }
            }
            if (!_loc_3)
            {
                return false;
            }
            _loc_5 =Global.gameSettings().getItemByName(param2 );
            if (!Global.gameSettings().getItemByName(param2))
            {
                return false;
            }
            boolean _loc_8 =false ;
            Function loc_6 ;
            if (this.m_owner)
            {
                _loc_9 = this.m_config.params.get("imageNameSelector");
                _loc_10 = Item.DEFAULT_DIRECTION;
                if (this.m_rotateable)
                {
                    _loc_10 = this.m_owner.getDirection();
                }
                loc_6 =(Function) this.m_owner.get(_loc_9);
                if (_loc_9 && this.m_owner.get(_loc_9) instanceof Function)
                {
                    _loc_6 = loc_6(_loc_5, param1, _loc_10);
                    _loc_8 = true;
                }
                else if (_loc_9 && this.get(_loc_9) instanceof Function)
                {
                    _loc_6 = loc_6(_loc_5, param1, _loc_10);
                    _loc_8 = true;
                }
            }
            if (!_loc_8)
            {
                _loc_11 = this.m_rotateable ? (this.m_owner.getDirection()) : (Item.DEFAULT_DIRECTION);
                _loc_6 = this.getItemImage(_loc_5, _loc_11);
            }
            if (!_loc_6)
            {
                _loc_5.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 1);
            }
            else
            {
                _loc_12 =(DisplayObject) _loc_6.image;
                if (!this.m_ownerDisplayObj)
                {
                    this.m_ownerDisplayObj =(Sprite) this.m_owner.getDisplayObject();
                }
                if (this.m_ownerDisplayObj)
                {
                    this.m_ownerDisplayObj.addChild(_loc_12);
                    this.setDisplayPosition(_loc_12, param1);
                }
                else
                {
                    this.m_isDirty = true;
                }
                this.m_displayContentMap.put(param1,  _loc_12);
            }
            return true;
        }//end

        protected Array  getSourceData ()
        {
            return null;
        }//end

        protected void  refreshDisplayData ()
        {
            int _loc_2 =0;
            _loc_1 = this.getSourceData ();
            if (_loc_1)
            {
                this.clearAddedDisplayObjects();
                _loc_2 = 0;
                while (_loc_2 < _loc_1.length())
                {

                    if (!_loc_1.get(_loc_2))
                    {
                    }
                    else
                    {
                        this.addToDisplay(_loc_2, _loc_1.get(_loc_2));
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        protected void  clearAddedDisplayObjects ()
        {
            int _loc_1 =0;
            DisplayObject _loc_2 =null ;
            for(int i0 = 0; i0 < DictionaryUtil.getKeys(this.m_displayContentMap).size(); i0++)
            {
            	_loc_1 = DictionaryUtil.getKeys(this.m_displayContentMap).get(i0);

                _loc_2 = this.m_displayContentMap.get(_loc_1);
                if (_loc_2 && this.m_ownerDisplayObj && this.m_ownerDisplayObj.contains(_loc_2))
                {
                    this.m_ownerDisplayObj.removeChild(_loc_2);
                }
                delete this.m_displayContentMap.get(_loc_1);
            }
            return;
        }//end

        private Array  getDisplayLocationsForRotation (int param1 =0)
        {
            if (param1 < 0 || param1 >= m_knownDirections.length())
            {
                return this.m_displayLocationMap.get("ALL");
            }
            _loc_2 = m_knownDirections.get(param1);
            if (this.m_displayLocationMap.get(_loc_2))
            {
                return this.m_displayLocationMap.get(_loc_2);
            }
            return this.m_displayLocationMap.get("ALL");
        }//end

        private void  setDisplayPosition (DisplayObject param1 ,int param2 )
        {
            if (!param1)
            {
                return;
            }
            if (this.m_useOrigin)
            {
                return;
            }
            this.m_displayLocations = this.getDisplayLocationsForRotation(this.m_owner.getDirection());
            param1.x = this.m_displayLocations.get(param2).x;
            param1.y = this.m_displayLocations.get(param2).y - param1.height;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return true;
        }//end

    }




