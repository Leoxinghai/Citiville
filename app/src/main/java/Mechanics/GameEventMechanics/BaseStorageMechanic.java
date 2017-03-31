package Mechanics.GameEventMechanics;

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
import Engine.Classes.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.storage.*;

    public class BaseStorageMechanic implements IActionGameMechanic, IStorage
    {
        protected MechanicMapResource m_owner =null ;
        protected MechanicConfigData m_config =null ;
        protected String m_gameEvent ="";
        protected int m_capacity =0;
        protected int m_maxCapacity =0;
        public static  String MODE_STORE ="store";
        public static  String MODE_PLACE_FROM_STORAGE ="place_from_storage";

        public  BaseStorageMechanic ()
        {
            return;
        }//end

        public int  capacity ()
        {
            return this.m_capacity;
        }//end

        public void  capacity (int param1 )
        {
            if (param1 > this.m_maxCapacity)
            {
                param1 = this.m_maxCapacity;
            }
            this.m_capacity = param1;
            return;
        }//end

        public int  maxCapacity ()
        {
            return this.m_maxCapacity;
        }//end

        public void  maxCapacity (int param1 )
        {
            this.m_maxCapacity = param1;
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            _loc_3 = this.m_owner.getItem ().storageInitCapacity ;
            this.m_capacity = _loc_3;
            _loc_4 = this.m_owner.getItem ().storageMaxCapacity ;
            this.m_maxCapacity = _loc_3;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            this.m_gameEvent = param1;
            return true;
        }//end

        protected boolean  canExecute ()
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true ,false ,null );
            this.m_gameEvent = param1;
            if (!this.canExecute())
            {
                return _loc_3;
            }
            _loc_4 = param2.get("mode") ;
            if (param2.get("mode") == MODE_STORE)
            {
                _loc_3 = this.handleStore(param2);
            }
            else if (_loc_4 == MODE_PLACE_FROM_STORAGE)
            {
                _loc_3 = this.handlePlaceFromStorage(param2);
            }
            this.postStorageAction();
            return _loc_3;
        }//end

        protected MechanicActionResult  handleStore (Array param1 )
        {
            boolean _loc_6 =false ;
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            Object _loc_9 =null ;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true ,false ,null );
            _loc_4 = param1.get("object") ;
            _loc_5 = this.store(_loc_4 );
            if (this.store(_loc_4))
            {
                _loc_6 = true;
                _loc_7 = false;
                _loc_8 = true;
                _loc_9 = {operation:"storeObject", objectId:_loc_4.getId()};
                _loc_3 = new MechanicActionResult(_loc_6, _loc_7, _loc_8, _loc_9);
            }
            return _loc_3;
        }//end

        protected MechanicActionResult  handlePlaceFromStorage (Array param1 )
        {
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_3 = param1.get("slotId") ;
            _loc_4 = param1.get("mapResource") ;
            if (_loc_3 >= _loc_2.length())
            {
                return new MechanicActionResult(false, true, false, null);
            }
            this.handleNpcFlush();
            _loc_2.splice(_loc_3, 1);
            if (this.m_owner instanceof ISlottedContainer)
            {
                (this.m_owner as ISlottedContainer).onRemoveSlotObject(_loc_4);
            }
            _loc_5 = _loc_4.positionX ;
            _loc_6 = _loc_4.positionY ;
            _loc_7 = _loc_4.getDirection ();
            boolean _loc_8 =true ;
            boolean _loc_9 =false ;
            boolean _loc_10 =true ;
            Object _loc_11 ={operation slotId "removeObject",,newX ,newY ,dir };
            return new MechanicActionResult(_loc_8, _loc_9, _loc_10, _loc_11);
        }//end

        protected void  postStorageAction ()
        {
            return;
        }//end

        public int  numOpenSlots ()
        {
            WorldObject _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3)
                {
                    _loc_1++;
                }
            }
            return this.capacity - _loc_1;
        }//end

        public boolean  canStore (MapResource param1 )
        {
            String _loc_3 =null ;
            boolean _loc_2 =false ;
            if (param1 instanceof MapResource)
            {
                _loc_3 = this.m_owner.getItemName();
                if (StorageType.validateStorageItemType(param1, _loc_3))
                {
                    _loc_2 = param1.isStorable();
                }
            }
            return _loc_2;
        }//end

        public boolean  store (Object param1)
        {
            Array _loc_3 =null ;
            boolean _loc_2 =false ;
            if (this.canStore(param1))
            {
                _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type);
                if (_loc_3.length >= this.capacity)
                {
                    _loc_2 = false;
                    if (Config.DEBUG_MODE)
                    {
                        throw new Error("Cannot add world object, exceeds capacity.");
                    }
                }
                param1.detach();
                param1.prepareForStorage(this.m_owner as MapResource);
                param1.onSell(this.m_owner as MapResource);
                ((MapResource)param1).setPosition(this.m_owner.positionX, this.m_owner.positionY, this.m_owner.positionZ);
                _loc_3.push(param1);
                this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, this.m_gameEvent);
                if (this.m_owner instanceof ISlottedContainer)
                {
                    (this.m_owner as ISlottedContainer).onStoreSlotObject(param1);
                }
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        protected void  handleNpcFlush ()
        {
            Global.world.citySim.businessVisitBatchManager.onQueueForceAction();
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



