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
import Classes.actions.*;
import Engine.Helpers.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class PickupManager implements IGameWorldStateObserver
    {
        private  Class MAILMAN_ACTION =ActionRentPickup ;
        private  Class LUMBERJACK_ACTION =ActionClearWilderness ;
        private  Class FARMER_ACTION =ActionCropHarvest ;
        private  Class BUSINESS_ACTION =ActionBusinessPickup ;
        private  Class HOTEL_ACTION =ActionHotelPickup ;
        private GameWorld m_world ;
        private int m_numQueuedPickups ;
        protected Dictionary m_pickupTypes ;

        public  PickupManager (GameWorld param1 )
        {
            this.m_world = param1;
            this.m_numQueuedPickups = 0;
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            XML _loc_1 =null ;
            PickupType _loc_2 =null ;
            this.m_pickupTypes = new Dictionary();
            this.m_numQueuedPickups = 0;
            for(int i0 = 0; i0 < Global.gameSettings().getPickupNpcs().size(); i0++)
            {
            		_loc_1 = Global.gameSettings().getPickupNpcs().get(i0);

                _loc_2 = new PickupType(_loc_1);
                this.m_pickupTypes.put(_loc_2.type,  _loc_2);
            }
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
            this.m_pickupTypes = null;
            this.m_numQueuedPickups = 0;
            return;
        }//end

        public void  enqueue (String param1 ,MapResource param2 )
        {
            Debug.debug5("PickupManager."+"enqueue."+param1);
            NPC _loc_4 =null ;
            if (param2.isLocked())
            {
                return;
            }
            _loc_3 = this.getQueue(param1 );
            if (_loc_3)
            {
                _loc_3.push(param2);
                this.m_numQueuedPickups++;
                param2.lock();
                _loc_4 = Global.world.citySim.npcManager.getPickupNPC(param1);
                if (_loc_4 == null)
                {
                    Global.world.citySim.npcManager.createPickupNPC(param1, this.m_pickupTypes.get(param1).dummy);
                }
            }
            return;
        }//end

        public MapResource  dequeue (String param1 )
        {
            Debug.debug5("PickupManager."+"dequeue."+param1);
            _loc_2 = this.getQueue(param1 );
            if (_loc_2 && _loc_2.length > 0)
            {
                this.m_numQueuedPickups--;
                return this.getQueue(param1).shift();
            }
            return null;
        }//end

        public MapResource  peek (String param1 )
        {
            Debug.debug5("PickupManager."+"peek."+param1);
            _loc_2 = this.getQueue(param1 );
            return _loc_2 ? (this.getQueue(param1).get(0)) : (null);
        }//end

        public void  addPickupAction (NPC param1 ,int param2 =0,MapResource param3 =null ,boolean param4 =true )
        {
            Debug.debug5("PickupManager.addPickupAction."+param1.getItemName()+";"+param3);
            _loc_5 = this.m_pickupTypes.get(param1.getItemName ()) ;
            if (_loc_5)
            {
                param1.getStateMachine().addState(new _loc_5.ActionDef(param1, param2, param3), param4);
            }
            return;
        }//end

        public Array  getQueue (String param1 )
        {
            if (this.m_pickupTypes.get(param1) != null)
            {
                return this.m_pickupTypes.get(param1).destinationQueue;
            }
            return null;
        }//end

        public void  clearQueue (String param1 )
        {
            Debug.debug5("PickupManager.clearQueue"+param1);
            MapResource _loc_3 =null ;
            Function _loc_4 =null ;
            _loc_2 = this.getQueue(param1 );
            if (_loc_2)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_3.unlock();
                    _loc_4 = _loc_3.getProgressBarCancelFunction();
                    if (_loc_4 != null)
                    {
                        _loc_4();
                    }
                }
                this.m_numQueuedPickups = this.m_numQueuedPickups - _loc_2.length;
                _loc_2.splice(0, _loc_2.length());
            }
            Global.world.citySim.npcManager.removePickupNPC(param1);
            return;
        }//end

        public void  clearAllQueues ()
        {
            PickupType _loc_1 =null ;
            Debug.debug5("PickupManager.clearAllQueues");

            for(int i0 = 0; i0 < this.m_pickupTypes.size(); i0++)
            {
            		_loc_1 = this.m_pickupTypes.get(i0);

                this.clearQueue(_loc_1.type);
            }
            this.m_numQueuedPickups = 0;
            return;
        }//end

    }

import Classes.*;
import Classes.actions.*;
import Engine.Helpers.*;
//import flash.utils.*;

class PickupType
    public String type ;
    public boolean dummy ;
    public Class ActionDef ;
    public Array destinationQueue ;

     PickupType (XML param1 )
    {
        this.type = param1.@type;
        this.dummy = param1.@dummy == "true";
        this.ActionDef = (Class)getDefinitionByName("Classes.actions." + param1.@action);
        this.destinationQueue = new Array();
        return;
    }//end




