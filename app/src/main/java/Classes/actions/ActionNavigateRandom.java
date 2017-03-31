package Classes.actions;

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
import Classes.sim.*;
import Engine.*;
import Engine.Helpers.*;

import com.xinghai.Debug;

    public class ActionNavigateRandom extends ActionNavigate
    {
        protected double m_elapsed =0;
        protected double m_timeout =-1;
        public static  int MAX_LOOKAHEAD =5;

        public  ActionNavigateRandom (NPC param1 )
        {
            Road _loc_3 =null ;
            Vector3 _loc_4 =null ;
            Vector3 _loc_5 =null ;
            super(param1, null, null);
            _loc_2 = param1.getPosition ();

            if (_loc_2 == null || _loc_2.x == 0 && _loc_2.y == 0)
            {
                _loc_3 = Global.world.citySim.roadManager.findRandomRoad();
                if (_loc_3 != null)
                {
                    _loc_2 = _loc_3.getPosition();
                    m_npc.setPosition(_loc_2.x, _loc_2.y);
                    this.updatePath(false);
                    if (m_path == null || m_path.length == 0)
                    {
                        _loc_4 = _loc_3.getHotspot();
                        m_npc.setPosition(_loc_4.x, _loc_4.y);
                    }
                    else
                    {
                        _loc_5 = ((PathElement)m_path.get(0)).offsetPosition;
                        m_npc.setPosition(_loc_5.x, _loc_5.y);
                    }
                }
            }
            return;
        }//end

        public ActionNavigateRandom  setTimeout (double param1 )
        {
            this.m_timeout = param1;
            return this;
        }//end

         public int  getInterrupt ()
        {
            return FULL_INTERRUPT;
        }//end

         public void  enter ()
        {
            this.m_elapsed = 0;
            return;
        }//end

         public void  reenter ()
        {
            this.m_elapsed = 0;
            return;
        }//end

         public void  update (double param1 )
        {
            if (m_path == null || m_path.length < MAX_LOOKAHEAD)
            {
                this.updatePath(true);
            }
            super.update(param1);
            this.m_elapsed = this.m_elapsed + param1;
            if (this.m_timeout >= 0 && this.m_elapsed > this.m_timeout)
            {
                this.m_npc.actionQueue.removeState(this);
            }
            return;
        }//end

         protected double  navigationVelocity ()
        {
            return m_npc.velocityWalk;
        }//end

         protected void  updatePath (boolean param1 )
        {
            Road _loc_3 =null ;
            Road _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Vector3 _loc_6 =null ;
            Array _loc_7 =null ;
            if (m_path && m_path.length >= MAX_LOOKAHEAD)
            {
                return;
            }
            _loc_2 = m_npc.getPosition();
            if (isNaN(_loc_2.x) || isNaN(_loc_2.y))
            {
                _loc_3 = Global.world.citySim.roadManager.findRandomRoad();
                if (_loc_3 != null)
                {
                    _loc_2 = _loc_3.getPosition();
                    m_npc.setPosition(_loc_2.x, _loc_2.y);
                }
                else
                {
                    m_npc.setPosition(0, 0);
                }
            }
            if (!m_path || m_path.length == 0)
            {
                _loc_4 = Global.world.citySim.roadManager.findClosestRoad(_loc_2);
                _loc_5 = _loc_4 != null ? (_loc_4.getHotspot()) : (_loc_2);
                _loc_6 = this.getAxisAlignedDirection(_loc_2, _loc_5);
                _loc_7 = .get(_loc_5);
            }
            else
            {
                _loc_6 = this.getAxisAlignedDirection(_loc_2, (PathElement)(m_path.get(0)).offsetPosition);
                _loc_7 = m_path.map(PathElement.extractBasePosition);
            }
            this.extendRoadPositions(_loc_7);
            _loc_7 = _loc_7.map(PathElement.createInstance);
            if (Global.world.citySim.roadManager.isShowingOverlay)
            {
                m_npc.m_navDbgPath2 = _loc_7;
                m_npc.m_navRandom = true;
            }
            m_path = makeOffsetPath(_loc_7, _loc_6, false);
            debugPrintPath();
            return;
        }//end

        protected void  extendRoadPositions (Array param1 )
        {
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            Vector3 _loc_4 =null ;
            while (param1.length < MAX_LOOKAHEAD)
            {

                _loc_2 = param1.length > 1 ? (param1.get(param1.length - 2)) : (null);
                _loc_3 = param1.get((param1.length - 1));
                _loc_4 = this.findNextRoadPosition(_loc_2, _loc_3);
                param1.push(_loc_4 != null ? (_loc_4) : (_loc_3));
            }
            return;
        }//end

        protected Vector3  findNextRoadPosition (Vector3 param1 ,Vector3 param2 )
        {
            Vector3 _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Vector3 _loc_6 =null ;
            Vector3 _loc_7 =null ;
            Road _loc_10 =null ;
            Vector3 _loc_11 =null ;
            Vector3 _loc_12 =null ;
            Vector3 _loc_13 =null ;
            Vector3 _loc_14 =null ;
            double _loc_15 =0;
            double _loc_16 =0;
            _loc_3 =Global.world.citySim.roadManager.findClosestRoadNeighbors(param2 );
            _loc_8 = this.getAxisAlignedDirection(param1 ,param2 );
            _loc_9 = this.getAxisAlignedDirection(param1 ,param2 ).cross(V_UP ).normalize ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_10 = _loc_3.get(i0);

                if (_loc_10 == null)
                {
                    continue;
                }
                _loc_13 = _loc_10.getHotspot();
                _loc_14 = _loc_13.subtract(param2);
                _loc_15 = _loc_8.dot(_loc_14);
                if (_loc_15 > 0)
                {
                    _loc_6 = _loc_13;
                    continue;
                }
                if (_loc_15 < 0)
                {
                    _loc_7 = _loc_13;
                    continue;
                }
                _loc_16 = _loc_9.dot(_loc_14);
                if (_loc_16 > 0)
                {
                    _loc_4 = _loc_13;
                    continue;
                }
                if (_loc_16 < 0)
                {
                    _loc_5 = _loc_13;
                }
            }
            _loc_11 = new Vector3(-m_roadSize, -m_roadSize);
            _loc_12 = this.chooseNextTile(_loc_4, _loc_5, _loc_6, _loc_7);
            return _loc_12;
        }//end

        protected Vector3  getAxisAlignedDirection (Vector3 param1 ,Vector3 param2 )
        {
            _loc_3 = param1!= null && param2 != null ? (param2.subtract(param1)) : (new Vector3(1, 0));
            if (Math.abs(_loc_3.x) > Math.abs(_loc_3.y))
            {
                _loc_3.y = 0;
            }
            else
            {
                _loc_3.x = 0;
            }
            if (_loc_3.length() > 0)
            {
                return _loc_3.normalize();
            }
            return _loc_3;
        }//end

        protected Vector3  findRoadAt (Vector3 param1 ,double param2 =0.1)
        {
            _loc_5 = null;
            _loc_3 = param1.floor ();
            _loc_4 =Global.world.getCollisionMap ().getObjectsByPosition(_loc_3.x ,_loc_3.y );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (_loc_5 instanceof Road)
                {
                    return ((Road)_loc_5).getPosition();
                }
            }
            return null;
        }//end

        protected Vector3  chooseNextTile (Vector3 param1 ,Vector3 param2 ,Vector3 param3 ,Vector3 param4 )
        {
            Array _loc_5 =new Array();
            Array _loc_6 =new Array();
            _loc_7 = param1!= null && param2 != null && param3 != null && param4 != null;
            if (param1 !=null)
            {
                _loc_5.push(param1);
                _loc_6.push(1);
            }
            if (param3)
            {
                _loc_5.push(param3);
                _loc_6.push(_loc_7 ? (1) : (0.5));
            }
            if (param2)
            {
                _loc_5.push(param2);
                _loc_6.push(_loc_7 ? (1) : (0.5));
            }
            if (param4)
            {
                _loc_5.push(param4);
                _loc_6.push(1e-005);
            }
            _loc_8 = MathUtil.randomIndexWeighed(_loc_6);
            return (Vector3)_loc_5.get(_loc_8);
        }//end

    }



