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
import Engine.Helpers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
import de.polygonal.ds.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class WonderShockwave extends ConsumerShockwave implements IGameWorldUpdateObserver, IGameWorldStateObserver
    {
        private boolean m_growing =false ;
        private Timer m_startupTimer =null ;
        private boolean m_onlyHarvestSparkles =false ;
        private PriorityQueue m_bonusQueue ;
        private Array m_harvestables ;
        private Array m_nonharvestables ;
        private Dictionary m_functionCurries ;

        public  WonderShockwave (MapResource param1 ,double param2 ,boolean param3 =false ,int param4 =16755200)
        {
            MapResource _loc_7 =null ;
            Item _loc_8 =null ;
            ItemBonus _loc_9 =null ;
            int _loc_10 =0;
            super(param1, 11, param2, null, null, false, param4);
            this.m_functionCurries = new Dictionary();
            this.m_harvestables = new Array();
            this.m_nonharvestables = new Array();
            _loc_5 = m_origin.getPosition();
            _loc_6 = m_origin.getSize();
            this.m_center = new Vector3(_loc_5.x + _loc_6.x / 2, _loc_5.y + _loc_6.y / 2);
            this.m_bonusQueue = new PriorityQueue(Global.world.getNumObjects() * 2);
            for(int i0 = 0; i0 < Global.world.getObjects().size(); i0++)
            {
            	_loc_7 = Global.world.getObjects().get(i0);

                _loc_8 = _loc_7.getItem();
                for(int i0 = 0; i0 < m_origin.getItem().bonuses.size(); i0++)
                {
                	_loc_9 = m_origin.getItem().bonuses.get(i0);

                    if (_loc_9.bonusAppliesToObject((ItemInstance)_loc_7))
                    {
                        _loc_10 = int(_loc_7.getPositionNoClone().subtract(m_center).length());
                        this.m_bonusQueue.enqueue(new PQueueResource(-_loc_10, _loc_7));
                        this.followObject(_loc_7);
                        break;
                    }
                }
            }
            if (param3)
            {
                this.m_tileRadius = this.m_maxRadius;
                this.m_onlyHarvestSparkles = true;
                this.updateQueue();
            }
            Global.world.addObserver(this);
            return;
        }//end

        private void  hideNonHarvestables (TimerEvent event )
        {
            MapResource _loc_2 =null ;
            this.m_onlyHarvestSparkles = true;
            for(int i0 = 0; i0 < this.m_nonharvestables.size(); i0++)
            {
            	_loc_2 = this.m_nonharvestables.get(i0);

                _loc_2.killSparkle(m_origin.getItemName());
            }
            this.m_nonharvestables = new Array();
            return;
        }//end

        public boolean  growing ()
        {
            return this.m_growing;
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
            return;
        }//end

        public void  followObject (MapResource param1 )
        {
            Function _loc_2 =null ;
            if (!(param1 in this.m_functionCurries))
            {
                _loc_2 = Curry.curry(this.handleHarvestChange, param1);
                this.m_functionCurries.put(param1,  _loc_2);
                param1.addEventListener(GameObjectEvent.STATE_CHANGE, _loc_2);
                param1.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, _loc_2);
            }
            return;
        }//end

        public void  ignoreObject (MapResource param1 )
        {
            Function _loc_2 =null ;
            if (param1 in this.m_functionCurries)
            {
                _loc_2 = this.m_functionCurries.get(param1);
                param1.removeEventListener(GameObjectEvent.STATE_CHANGE, _loc_2);
                param1.removeEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, _loc_2);
                delete this.m_functionCurries.get(param1);
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            ItemBonus _loc_4 =null ;
            int _loc_5 =0;
            Iterator _loc_6 =null ;
            if (param1.getId() == m_origin.getId())
            {
                return;
            }
            if (param2 == null)
            {
                for(int i0 = 0; i0 < m_origin.getItem().bonuses.size(); i0++)
                {
                	_loc_4 = m_origin.getItem().bonuses.get(i0);

                    if (_loc_4.bonusAppliesToObject((ItemInstance)param1))
                    {
                        _loc_5 = int(param3.subtract(m_center).length());
                        this.m_bonusQueue.enqueue(new PQueueResource(-_loc_5, param1));
                        this.followObject(param1);
                        this.updateQueue();
                        break;
                    }
                }
            }
            else if (param3 == null)
            {
                this.ignoreObject(param1);
                if (this.m_bonusQueue.size > 0)
                {
                    _loc_6 = this.m_bonusQueue.getIterator();
                    do
                    {

                        if (((PQueueResource)_loc_6.data).obj == param1)
                        {
                            this.m_bonusQueue.remove((PQueueResource)_loc_6.data);
                        }
                        _loc_6 = _loc_6.next();
                    }while (_loc_6.hasNext())
                }
            }
            else
            {
                this.handleHarvestChange(param1);
            }
            return;
        }//end

        public void  handleHarvestChange (MapResource param1 ,Event param2 )
        {
            int _loc_4 =0;
            if (!param1)
            {
                return;
            }
            _loc_3 = this.m_harvestables.indexOf(param1);
            param1.killSparkle(m_origin.getItemName());
            if (WonderShockwave.isHarvestable(param1))
            {
                _loc_4 = int(param1.getPositionNoClone().subtract(m_center).length());
                if (_loc_4 < m_maxRadius)
                {
                    param1.makeSparkle(m_origin.getItemName());
                    if (_loc_3 < 0)
                    {
                        this.m_harvestables.push(param1);
                    }
                }
                else if (_loc_3 > 0)
                {
                    this.m_harvestables.splice(_loc_3, 1);
                }
            }
            else if (_loc_3 > 0)
            {
                this.m_harvestables.splice(_loc_3, 1);
            }
            return;
        }//end

        public void  update (double param1 )
        {
            if (this.m_growing)
            {
                if (!this.m_startupTimer)
                {
                    this.m_startupTimer = new Timer(4000);
                    this.m_startupTimer.addEventListener(TimerEvent.TIMER, this.hideNonHarvestables);
                    this.m_startupTimer.start();
                }
                if (m_origin == null)
                {
                    this.stop();
                    return;
                }
                updateVisuals();
                m_animation.alpha = (30 - Math.min(30, m_tileRadius)) / 30;
                if (m_animation.alpha == 0)
                {
                    m_animation.visible = false;
                }
                if (m_tileRadius >= m_maxRadius)
                {
                    if (m_endCallback != null)
                    {
                        m_endCallback();
                    }
                    else
                    {
                        this.stop();
                    }
                }
                this.updateQueue();
            }
            return;
        }//end

        public void  updateQueue ()
        {
            MapResource _loc_1 =null ;
            while (this.m_bonusQueue.front && this.m_bonusQueue.front.priority > -m_tileRadius)
            {

                _loc_1 = ((PQueueResource)this.m_bonusQueue.dequeue()).obj;
                if (WonderShockwave.isHarvestable(_loc_1))
                {
                    this.m_harvestables.push(_loc_1);
                    _loc_1.makeSparkle(m_origin.getItemName());
                    continue;
                }
                if (!this.m_onlyHarvestSparkles)
                {
                    this.m_nonharvestables.push(_loc_1);
                    _loc_1.makeSparkle(m_origin.getItemName());
                }
            }
            return;
        }//end

        public void  destroy ()
        {
            Object _loc_1 =null ;
            MapResource _loc_2 =null ;
            Global.world.removeObserver(this);
            if (this.m_growing)
            {
                this.stop();
            }
            for(int i0 = 0; i0 < this.m_functionCurries.size(); i0++)
            {
            	_loc_1 = this.m_functionCurries.get(i0);

                this.ignoreObject((MapResource)_loc_1);
            }
            for(int i0 = 0; i0 < this.m_harvestables.size(); i0++)
            {
            	_loc_2 = this.m_harvestables.get(i0);

                _loc_2.killSparkle(m_origin.getItemName());
            }
            this.m_functionCurries = null;
            this.m_harvestables = null;
            this.m_nonharvestables = null;
            this.m_bonusQueue = null;
            m_animation = null;
            m_seenNpcs = null;
            m_origin = null;
            m_npcConsumer = null;
            m_endCallback = null;
            m_seenNpcs = null;
            return;
        }//end

         public void  start ()
        {
            m_startTime = getTimer();
            this.m_growing = true;
            createWave();
            return;
        }//end

         public void  stop ()
        {
            MapResource res ;
            this.m_growing = false;
            this.updateQueue();
            remover = Curry.curry(function(param1DisplayObject)
            {
                if (param1.parent != null)
                {
                    param1.parent.removeChild(param1);
                }
                return;
            }//end
            , m_animation);
            this.m_onlyHarvestSparkles = true;
            fadeout = Global.gameSettings().getNumber("bizWaveFade",1);
            TweenLite.to(m_animation, fadeout, {alpha:0, onComplete:remover});


            for(int i0 = 0; i0 < this.m_nonharvestables.size(); i0++)
            {
            	res = this.m_nonharvestables.get(i0);


                res.killSparkle(m_origin.getItemName());
            }
            this.m_nonharvestables = new Array();
            this.m_bonusQueue.clear();
            return;
        }//end

        public static boolean  isHarvestable (MapResource param1 )
        {
            HarvestableResource _loc_2 =null ;
            MechanicMapResource _loc_3 =null ;
            Dictionary _loc_4 =null ;
            boolean _loc_5 =false ;
            String _loc_6 =null ;
            Dictionary _loc_7 =null ;
            MechanicConfigData _loc_8 =null ;
            IActionGameMechanic _loc_9 =null ;
            if (param1 instanceof HarvestableResource)
            {
                _loc_2 =(HarvestableResource) param1;
                if (_loc_2.isHarvestable())
                {
                    return true;
                }
                return false;
            }
            else if (param1 instanceof MechanicMapResource)
            {
                _loc_3 =(MechanicMapResource) param1;
                _loc_4 = _loc_3.actionMechanics;
                _loc_5 = false;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_6 = _loc_4.get(i0);

                    _loc_7 = _loc_4.get(_loc_6);
                    for(int i0 = 0; i0 < _loc_7.size(); i0++)
                    {
                    	_loc_8 = _loc_7.get(i0);

                        if (_loc_8.params.get("stopNonEssentialInstantiation") != "true")
                        {
                            _loc_9 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_3, _loc_8.type, _loc_6);
                            if (_loc_9 instanceof BaseStateHarvestMechanic)
                            {
                                if (((BaseStateHarvestMechanic)_loc_9).isHarvestReady() && (!param1.isNeedingRoad || param1.isAdjacentToAnyRoad))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            return false;
        }//end

    }




