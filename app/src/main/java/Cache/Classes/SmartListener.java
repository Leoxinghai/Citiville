package Cache.Classes;

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

//import flash.events.*;
    public class SmartListener
    {
        private IEventDispatcher m_eventDispatcher ;
        private Object m_listeners ;

        public  SmartListener (IEventDispatcher param1 )
        {
            if (param1 !=null)
            {
                this.m_eventDispatcher = param1;
                this.m_listeners = new Object();
            }
            else
            {
                throw new Error("Cannot listen smartly on a null event dispatcher!");
            }
            return;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,boolean param4 =false ,Array param5 =null )
        {
            Object _loc_6 =null ;
            if (param2 != null)
            {
                _loc_6 = {callback:param2, onetime:param3, removeAll:param4, callbackArgs:param5};
                if (this.m_listeners.hasOwnProperty(param1))
                {
                    this.m_listeners.get(param1).push(_loc_6);
                }
                else
                {
                    this.m_listeners.put(param1,  .get(_loc_6));
                    this.m_eventDispatcher.addEventListener(param1, this.onEvent);
                }
            }
            return;
        }//end

        private void  onEvent (Event event )
        {
            boolean _loc_2 =false ;
            Array _loc_3 =null ;
            Object _loc_4 =null ;
            Array _loc_5 =null ;
            if (this.m_listeners.hasOwnProperty(event.type))
            {
                _loc_2 = false;
                _loc_3 = new Array();
                for(int i0 = 0; i0 < this.m_listeners.get(event.type).size(); i0++)
                {
                	_loc_4 = this.m_listeners.get(event.type).get(i0);

                    _loc_5 = _loc_4.callbackArgs ? (_loc_4.callbackArgs) : (new Array());
                    _loc_5.unshift(event);
                    _loc_4.callback.apply(null, _loc_5);
                    if (!_loc_4.onetime)
                    {
                        _loc_3.push(_loc_4);
                    }
                    _loc_2 = _loc_2 || _loc_4.removeAll;
                }
                if (_loc_2)
                {
                    this.removeAllListeners();
                }
                else if (_loc_3.length == 0)
                {
                    delete this.m_listeners.get(event.type);
                    this.m_eventDispatcher.removeEventListener(event.type, this.onEvent);
                }
                else
                {
                    this.m_listeners.put(event.type,  _loc_3);
                }
            }
            return;
        }//end

        public void  removeAllListeners ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_listeners.size(); i0++)
            {
            	_loc_1 = this.m_listeners.get(i0);

                this.m_eventDispatcher.removeEventListener(_loc_1, this.onEvent);
            }
            this.m_listeners = new Object();
            return;
        }//end

    }




