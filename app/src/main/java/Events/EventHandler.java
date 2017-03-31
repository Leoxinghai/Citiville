package Events;

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

    final public class EventHandler
    {
        private Object _source ;
        private boolean _enabled =true ;
        private boolean _useWeakReferences =true ;
        private int _priority =0;
        private Object handlerMap ;

        public  EventHandler ()
        {
            this.handlerMap = {};
            return;
        }//end

        public void  destroy ()
        {
            String _loc_1 =null ;
            Array _loc_2 =null ;
            this.source = null;
            if (this.handlerMap)
            {
                for(int i0 = 0; i0 < this.handlerMap.size(); i0++)
                {
                	_loc_1 = this.handlerMap.get(i0);

                    _loc_2 =(Array) this.handlerMap.get(_loc_1);
                    _loc_2.length = 0;
                    delete this.handlerMap.get(_loc_1);
                }
                this.handlerMap = null;
            }
            return;
        }//end

        public int  priority ()
        {
            return this._priority;
        }//end

        public void  priority (int param1 )
        {
            _loc_2 = this._enabled ;
            this.enabled = false;
            this._priority = param1;
            this.enabled = _loc_2;
            return;
        }//end

        public boolean  useWeakReferences ()
        {
            return this._useWeakReferences;
        }//end

        public void  useWeakReferences (boolean param1 )
        {
            _loc_2 = this._enabled ;
            this.enabled = false;
            this._useWeakReferences = param1;
            this.enabled = _loc_2;
            return;
        }//end

        public Object  source ()
        {
            return this._source;
        }//end

        public void  source (Object param1 )
        {
            _loc_2 = this._enabled ;
            this.enabled = false;
            this._source = param1;
            this.enabled = _loc_2;
            return;
        }//end

        public boolean  enabled ()
        {
            return this._enabled;
        }//end

        public void  enabled (boolean param1 )
        {
            Array _loc_2 =null ;
            if (param1 != this._enabled)
            {
                this._enabled = param1;
                if (this._source)
                {
                    _loc_2 = this.toKeyArray(this.handlerMap);
                    if (param1 !=null)
                    {
                        this.addHandlers(_loc_2);
                    }
                    else
                    {
                        this.removeHandlers(_loc_2);
                    }
                }
            }
            return;
        }//end

        public EventHandler  addHandler (String param1 ,Function param2 )
        {
            _loc_3 =(Array) this.handlerMap.get(param1);
            if (!_loc_3)
            {
                _loc_3 = new Array();
                this.handlerMap.put(param1,  _loc_3);
            }
            _loc_3.push(param2);
            if (this._source && this._enabled)
            {
                this._source.addEventListener(param1, param2, false, this._priority, this._useWeakReferences);
            }
            return this;
        }//end

        private void  addHandlers (Array param1 )
        {
            String _loc_2 =null ;
            Array _loc_3 =null ;
            Function _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                _loc_3 =(Array) this.handlerMap.get(_loc_2);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    this._source.addEventListener(_loc_2, _loc_4, false, this._priority, this._useWeakReferences);
                }
            }
            return;
        }//end

        public EventHandler  removeHandler (String param1 ,Function param2 )
        {
            _loc_3 =(Array) this.handlerMap.get(param1);
            if (_loc_3 && _loc_3.indexOf(param2) >= 0)
            {
                _loc_3.splice(_loc_3.indexOf(param2), 1);
                if (this._source && this._enabled)
                {
                    this._source.removeEventListener(param1, param2, false);
                }
            }
            return this;
        }//end

        private void  removeHandlers (Array param1 )
        {
            String _loc_2 =null ;
            Array _loc_3 =null ;
            Function _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                _loc_3 =(Array) this.handlerMap.get(_loc_2);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    this._source.removeEventListener(_loc_2, _loc_4, false);
                }
            }
            return;
        }//end

        private Array  toKeyArray (Object param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

    }



