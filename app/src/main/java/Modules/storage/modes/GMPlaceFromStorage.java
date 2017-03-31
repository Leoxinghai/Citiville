package Modules.storage.modes;

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
import GameMode.*;
import Modules.storage.*;
import Modules.storage.transactions.*;
//import flash.events.*;

    public class GMPlaceFromStorage extends GMObjectMove
    {
        protected StorageType m_storageType ;
        protected String m_storageKey ;
        protected BaseStorageUnit m_storageBin ;
        protected String m_itemName ;
        protected Class m_itemClass ;

        public  GMPlaceFromStorage (StorageType param1 ,String param2 ,String param3 ,Class param4 =null )
        {
            this.m_storageType = param1;
            this.m_storageKey = param2;
            this.m_storageBin = Global.player.storageComponent.getStorageUnit(param1, param2);
            this.m_itemName = param3;
            this.m_itemClass = param4;
            super(null, null, null, Constants.INDEX_NONE, true);
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (!m_selectedObject)
            {
                m_highlightedPoint = getMouseTilePos();
                m_selectedObject = this.getItemFromStorage();
                m_selectedObject.setOuter(Global.world);
                resetOnPlaceCollisions();
                registerAllOnPlaceCollisions();
                snapPoint(m_highlightedPoint);
                m_selectedObject.setPosition(m_highlightedPoint.x, m_highlightedPoint.y);
            }
            Global.world.citySim.roadManager.updateResource((MapResource)m_selectedObject);
            m_autoRotate = ((MapResource)m_selectedObject).autoRotate;
            if (m_autoRotate)
            {
                rotateToRoad((MapResource)m_selectedObject);
            }
            _loc_2 = super.onMouseMove(event);
            return _loc_2;
        }//end

         protected void  completeObjectDrop ()
        {
            Object _loc_3 =null ;
            _loc_1 = (MapResource)m_selectedObject
            _loc_2 = _loc_1.isPlacedObjectNonBuilding ();
            m_selectedObject.setPosition(Math.round(m_releasedPoint.x), Math.round(m_releasedPoint.y));
            m_selectedObject.onObjectDropPreTansaction(m_objectStartPos);
            if (m_releasedPoint)
            {
                _loc_3 = new Object();
                _loc_3.x = Math.round(m_releasedPoint.x);
                _loc_3.y = Math.round(m_releasedPoint.y);
                _loc_3.state = _loc_1.getState();
                _loc_3.direction = _loc_1.getDirection();
                _loc_3.storageType = this.m_storageType.type;
                _loc_3.storageKey = this.m_storageKey;
                _loc_3 = _loc_1.addTMoveParams(_loc_3);
                this.m_storageBin.remove((ItemInstance)m_selectedObject);
                GameTransactionManager.addTransaction(new TMoveFromStorage(_loc_1, _loc_3));
                _loc_1.restoreFromStorage(null);
            }
            m_selectedObject.conditionallyReattach();
            m_selectedObject.onObjectDrop(m_objectStartPos);
            m_objectPlacedSuccess = true;
            if (_loc_2)
            {
                Sounds.play("click4");
            }
            else
            {
                Sounds.play("place_building");
            }
            onObjectDropCompleted();
            return;
        }//end

        protected MapResource  getItemFromStorage ()
        {
            ItemInstance _loc_1 =null ;
            if (this.m_storageBin)
            {
                _loc_1 = this.m_storageBin.getItemsByName(this.m_itemName).get(0);
            }
            return (MapResource)_loc_1;
        }//end

        protected void  removeItemFromStorage ()
        {
            this.m_storageBin.remove((ItemInstance)m_selectedObject);
            return;
        }//end

    }



