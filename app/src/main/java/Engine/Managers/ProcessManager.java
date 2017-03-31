package Engine.Managers;

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

import Engine.Interfaces.*;

//import flash.utils.*;
import Engine.Interfaces.*;

    public class ProcessManager
    {
        private double m_timeScale =1;
        private int m_virtualTime =0;
        private int m_platformTime =0;
        private int m_lastTime =-1;
        private double m_elapsed =0;
        private boolean m_needPurgeEmpty =false ;
        private boolean m_processing =false ;
        private boolean m_active =false ;
        private Vector<FramedProcessObject> m_framedObjects;
        private Vector<TickedProcessObject> m_tickedObjects;
        private Vector<FramedProcessObject> m_deferredFramedObjects;
        private Vector<TickedProcessObject> m_deferredTickedObjects;
        private static ProcessManager m_instance =null ;
        private static boolean m_canInstantiate =false ;
        public static  int TICKS_PER_SECOND =30;
        public static  double TICK_RATE =1/Number(TICKS_PER_SECOND );
        public static  double TICK_RATE_MS =TICK_RATE *1000;
        public static  int MAX_TICKS_PER_FRAME =5;

        public  ProcessManager ()
        {
            this.m_framedObjects = new Vector<FramedProcessObject>();
            this.m_tickedObjects = new Vector<TickedProcessObject>();
            this.m_deferredFramedObjects = new Vector<FramedProcessObject>();
            this.m_deferredTickedObjects = new Vector<TickedProcessObject>();
            if (!m_canInstantiate)
            {
                throw new Error("Cannot call ProcessManager constructor directly - use ProcessManager.instance instead.");
            }
            return;
        }//end

        public double  timeScale ()
        {
            return this.m_timeScale;
        }//end

        public void  timeScale (double param1 )
        {
            this.m_timeScale = param1;
            return;
        }//end

        public double  virtualTime ()
        {
            return this.m_virtualTime;
        }//end

        public double  platformTime ()
        {
            return this.m_platformTime;
        }//end

        public void  activate ()
        {
            if (!this.m_active)
            {
                this.m_lastTime = -1;
                this.m_elapsed = 0;
                this.m_active = true;
            }
            return;
        }//end

        public void  deactivate ()
        {
            if (this.m_active)
            {
                this.m_active = false;
            }
            return;
        }//end

        public boolean  isActive ()
        {
            return this.m_active;
        }//end

        public void  addFramedObject (IFramedObject param1 ,double param2)
        {
            FramedProcessObject _loc_3 =new FramedProcessObject ();
            _loc_3.object = param1;
            _loc_3.priority = param2;
            this.pushFramedObject(_loc_3);
            return;
        }//end

        public void  addTickedObject (ITickedObject param1 ,double param2)
        {
            TickedProcessObject _loc_3 =new TickedProcessObject ();
            _loc_3.object = param1;
            _loc_3.priority = param2;
            this.pushTickedObject(_loc_3);
            return;
        }//end

        public void  removeFramedObject (IFramedObject param1 )
        {
            if (this.objectCount == 1)
            {
                this.deactivate();
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_framedObjects.length())
            {

                if (this.m_framedObjects.get(_loc_2) && this.m_framedObjects.get(_loc_2).object == param1)
                {
                    if (this.m_processing)
                    {
                        this.m_framedObjects.get(_loc_2) = null;
                        this.m_needPurgeEmpty = true;
                    }
                    else
                    {
                        this.m_framedObjects.splice(_loc_2, 1);
                    }
                    if (this.m_framedObjects.length == 0)
                    {
                        this.m_framedObjects = new Vector<FramedProcessObject>();
                    }
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        public void  removeTickedObject (ITickedObject param1 )
        {
            if (this.objectCount == 1)
            {
                this.deactivate();
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_tickedObjects.length())
            {

                if (this.m_tickedObjects.get(_loc_2) && this.m_tickedObjects.get(_loc_2).object == param1)
                {
                    if (this.m_processing)
                    {
                        this.m_tickedObjects.put(_loc_2,  null);
                        this.m_needPurgeEmpty = true;
                    }
                    else
                    {
                        this.m_tickedObjects.splice(_loc_2, 1);
                    }
                    if (this.m_tickedObjects.length == 0)
                    {
                        this.m_tickedObjects = new Vector<TickedProcessObject>();
                    }
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        public void  onFrame ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            if (this.m_active)
            {
                _loc_1 = getTimer();
                if (this.m_lastTime >= 0)
                {
                    _loc_2 = Number(_loc_1 - this.m_lastTime) * this.m_timeScale;
                    this.process(_loc_2);
                }
                this.m_lastTime = _loc_1;
            }
            return;
        }//end

        private int  objectCount ()
        {
            return this.m_tickedObjects.length + this.m_framedObjects.length;
        }//end

        private void  pushFramedObject (FramedProcessObject param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.m_processing)
            {
                this.m_deferredFramedObjects.push(param1);
            }
            else
            {
                if (!this.m_active)
                {
                    this.activate();
                }
                if (this.m_framedObjects.indexOf(param1) == -1)
                {
                    _loc_2 = -1;
                    _loc_3 = 0;
                    while (_loc_3 < this.m_framedObjects.length())
                    {

                        if (this.m_framedObjects.get(_loc_3) && this.m_framedObjects.get(_loc_3).priority < param1.priority)
                        {
                            _loc_2 = _loc_3;
                            break;
                        }
                        _loc_3++;
                    }
                    if (_loc_2 < 0 || _loc_2 >= this.m_framedObjects.length())
                    {
                        this.m_framedObjects.push(param1);
                    }
                    else
                    {
                        this.m_framedObjects.splice(_loc_2, 0, param1);
                    }
                }
            }
            return;
        }//end

        private void  pushTickedObject (TickedProcessObject param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.m_processing)
            {
                this.m_deferredTickedObjects.push(param1);
            }
            else
            {
                if (!this.m_active)
                {
                    this.activate();
                }
                if (this.m_tickedObjects.indexOf(param1) == -1)
                {
                    _loc_2 = -1;
                    _loc_3 = 0;
                    while (_loc_3 < this.m_tickedObjects.length())
                    {

                        if (this.m_tickedObjects.get(_loc_3) && this.m_tickedObjects.get(_loc_3).priority < param1.priority)
                        {
                            _loc_2 = _loc_3;
                            break;
                        }
                        _loc_3++;
                    }
                    if (_loc_2 < 0 || _loc_2 >= this.m_tickedObjects.length())
                    {
                        this.m_tickedObjects.push(param1);
                    }
                    else
                    {
                        this.m_tickedObjects.splice(_loc_2, 0, param1);
                    }
                }
            }
            return;
        }//end

        private void  process (double param1 )
        {
            int _loc_2 =0;
            TickedProcessObject _loc_4 =null ;
            FramedProcessObject _loc_6 =null ;
            int _loc_7 =0;
            this.m_platformTime = getTimer();
            this.m_elapsed = this.m_elapsed + param1;
            int _loc_3 =0;
            while (this.m_elapsed >= TICK_RATE_MS && _loc_3 < MAX_TICKS_PER_FRAME)
            {

                this.processDeferredObjects();
                this.m_processing = true;
                _loc_2 = 0;
                while (_loc_2 < this.m_tickedObjects.length())
                {

                    _loc_4 = this.m_tickedObjects.get(_loc_2);
                    if (_loc_4)
                    {
                        _loc_4.object.onTick(TICK_RATE);
                    }
                    _loc_2 = _loc_2 + 1;
                }
                this.m_processing = false;
                this.m_virtualTime = this.m_virtualTime + TICK_RATE_MS;
                this.m_elapsed = this.m_elapsed - TICK_RATE_MS;
                _loc_3++;
            }
            if (_loc_3 >= MAX_TICKS_PER_FRAME)
            {
                _loc_7 = this.m_elapsed / TICK_RATE_MS;
                this.m_elapsed = this.m_elapsed - TICK_RATE_MS * _loc_7;
            }
            this.m_processing = true;
            _loc_5 = param1/1000;
            _loc_2 = 0;
            while (_loc_2 < this.m_framedObjects.length())
            {

                _loc_6 = this.m_framedObjects.get(_loc_2);
                if (_loc_6)
                {
                    _loc_6.object.onFrame(_loc_5);
                }
                _loc_2 = _loc_2 + 1;
            }
            this.m_processing = false;
            if (this.m_needPurgeEmpty)
            {
                this.purgeEmpty();
            }
            return;
        }//end

        private void  processDeferredObjects ()
        {
            int _loc_1 =0;
            _loc_2 = this.m_deferredFramedObjects ;
            if (_loc_2.length > 0)
            {
                this.m_deferredFramedObjects = new Vector<FramedProcessObject>();
                _loc_1 = 0;
                while (_loc_1 < _loc_2.length())
                {

                    this.pushFramedObject(_loc_2.get(_loc_1));
                    _loc_1 = _loc_1 + 1;
                }
            }
            _loc_3 = this.m_deferredTickedObjects ;
            if (_loc_3.length > 0)
            {
                this.m_deferredTickedObjects = new Vector<TickedProcessObject>();
                _loc_1 = 0;
                while (_loc_1 < _loc_3.length())
                {

                    this.pushTickedObject(_loc_3.get(_loc_1));
                    _loc_1 = _loc_1 + 1;
                }
            }
            return;
        }//end

        private void  purgeEmpty ()
        {
            int _loc_1 =0;
            _loc_1 = 0;
            while (_loc_1 < this.m_framedObjects.length())
            {

                if (!this.m_framedObjects.get(_loc_1))
                {
                    this.m_framedObjects.splice(_loc_1, 1);
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1 = _loc_1 + 1;
            }
            _loc_1 = 0;
            while (_loc_1 < this.m_tickedObjects.length())
            {

                if (!this.m_tickedObjects.get(_loc_1))
                {
                    this.m_tickedObjects.splice(_loc_1, 1);
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1 = _loc_1 + 1;
            }
            if (this.m_framedObjects.length == 0)
            {
                this.m_framedObjects = new Vector<FramedProcessObject>();
            }
            if (this.m_tickedObjects.length == 0)
            {
                this.m_tickedObjects = new Vector<TickedProcessObject>();
            }
            this.m_needPurgeEmpty = false;
            return;
        }//end

        public static ProcessManager  instance ()
        {
            if (m_instance == null)
            {
                m_canInstantiate = true;
                m_instance = new ProcessManager;
                m_canInstantiate = false;
            }
            return m_instance;
        }//end

    }

import Engine.Interfaces.*;
final class FramedProcessObject
    public IFramedObject object =null ;
    public double priority =0;

     FramedProcessObject ()
    {
        return;
    }//end


import Engine.Interfaces.*;
final class TickedProcessObject
    public ITickedObject object =null ;
    public double priority =0;

     TickedProcessObject ()
    {
        return;
    }//end



