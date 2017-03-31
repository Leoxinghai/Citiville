package GameMode;

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
import Engine.Helpers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;

    public class GMMechanicPlaceFromStorage extends GMObjectMove
    {
        private IMechanicUser m_storage ;
        private int m_slotId ;
        private ItemInstance m_itemInstance ;

        public  GMMechanicPlaceFromStorage (IMechanicUser param1 ,int param2 ,ItemInstance param3 )
        {
            this.m_storage = param1;
            this.m_slotId = param2;
            this.m_itemInstance = param3;
            super(null, null, null, Constants.INDEX_NONE, true);
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (this.m_itemInstance == null)
            {
                return super.onMouseMove(event);
            }
            if (!m_selectedObject)
            {
                m_highlightedPoint = getMouseTilePos();
                m_selectedObject =(MapResource) this.m_itemInstance;
                m_selectedObject.setOuter(Global.world);
                ((MapResource)m_selectedObject).pathProvider = null;
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

         public void  enableMode ()
        {
            super.enableMode();
            if (m_selectedObject instanceof MapResource)
            {
                ((MapResource)m_selectedObject).pathProvider = null;
            }
            return;
        }//end

         public void  disableMode ()
        {
            MapResource _loc_1 =null ;
            Vector3 _loc_2 =null ;
            super.disableMode();
            if (!m_objectPlacedSuccess)
            {
                if (m_selectedObject)
                {
                    _loc_1 =(MapResource) this.m_storage;
                    ((MapResource)m_selectedObject).pathProvider = _loc_1;
                    _loc_2 = _loc_1.getPosition();
                    m_selectedObject.setPosition(_loc_2.x, _loc_2.y, _loc_2.z);
                }
            }
            return;
        }//end

         protected void  completeObjectDrop ()
        {
            _loc_1 = (MapResource)m_selectedObject
            _loc_2 = _loc_1.isPlacedObjectNonBuilding ();
            _loc_1.setPosition(Math.round(m_releasedPoint.x), Math.round(m_releasedPoint.y));
            this.removeItemFromStorage();
            _loc_1.onObjectDropPreTansaction(m_objectStartPos);
            _loc_1.restoreFromStorage(this.m_storage as MapResource);
            _loc_1.conditionallyReattach();
            _loc_1.onObjectDrop(m_objectStartPos);
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

        protected void  removeItemFromStorage ()
        {
            Array _loc_1 =new Array ();
            _loc_1.put("mode",  BaseStorageMechanic.MODE_PLACE_FROM_STORAGE);
            _loc_1.put("slotId",  this.m_slotId);
            _loc_1.put("mapResource",  (MapResource)m_selectedObject);
            MechanicManager.getInstance().handleAction(this.m_storage, "GMMechanicStore", _loc_1);
            Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.OBJECT_PLACED_FROM_STORAGE, null));
            return;
        }//end

         public void  removeMode ()
        {
            super.removeMode();
            Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.OBJECT_PLACED_FROM_STORAGE, null));
            return;
        }//end

    }



