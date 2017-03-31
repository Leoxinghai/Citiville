package com.facebook.utils;

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
//import flash.utils.*;

    public class FacebookArrayCollection extends EventDispatcher
    {
        protected Array _source ;
        protected Dictionary hash ;
        protected Class _type ;

        public  FacebookArrayCollection (Array param1 ,Class param2 )
        {
            this.reset();
            this._type = param2;
            this.initilizeSource(param1);
            return;
        }//end

        protected void  verifyIndex (int param1 )
        {
            if (this._source.length < param1)
            {
                throw new RangeError("Index: " + param1 + ", instanceof out of range.");
            }
            return;
        }//end

        public void  addItem (Object param1 )
        {
            this.addItemAt(param1, this.length());
            return;
        }//end

        public int  length ()
        {
            return this._source.length;
        }//end

        public void  addItemAt (Object param1 ,int param2 )
        {
            if (this.hash.get(param1) != null)
            {
                throw new Error("Item already exists.");
            }
            if (this._type !== null && !(param1 instanceof this._type))
            {
                throw new TypeError("This collection requires " + this._type + " as the type.");
            }
            this.hash.put(param1,  true);
            this._source.splice(param2, 0, param1);
            return;
        }//end

        public int  indexOf (Object param1 )
        {
            return this._source.indexOf(param1);
        }//end

        public void  removeItemAt (int param1 )
        {
            this.verifyIndex(param1);
            _loc_2 = this._source.get(param1) ;
            delete this.hash.get(_loc_2);
            this._source.splice(param1, 1);
            return;
        }//end

        public Object  getItemAt (int param1 )
        {
            this.verifyIndex(param1);
            return this._source.get(param1);
        }//end

         public String  toString ()
        {
            return this._source.join(", ");
        }//end

        public void  reset ()
        {
            this.hash = new Dictionary(true);
            this._source = new Array();
            return;
        }//end

        protected void  initilizeSource (Array param1 )
        {
            this._source = new Array();
            if (param1 == null)
            {
                return;
            }
            _loc_2 = param1.length ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                this.addItem(param1.get(_loc_3));
                _loc_3 = _loc_3 + 1;
            }
            return;
        }//end

        public Object  findItemByProperty (String param1 ,Object param2 ,boolean param3 =false )
        {
            Object _loc_4 =null ;
            for(int i0 = 0; i0 < this.hash.size(); i0++)
            {
            		_loc_4 = this.hash.get(i0);

                if (param3 && param1 in _loc_4 && _loc_4.get(param1) === param2)
                {
                    return _loc_4;
                }
                if (!param3 && param1 in _loc_4 && _loc_4.get(param1) == param2)
                {
                    return _loc_4;
                }
            }
            return null;
        }//end

        public Class  type ()
        {
            return this._type;
        }//end

        public Array  source ()
        {
            return this._source;
        }//end

        public Array  toArray ()
        {
            Array _loc_1 =new Array();
            _loc_2 = this.length ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                _loc_1.push(this.getItemAt(_loc_3));
                _loc_3 = _loc_3 + 1;
            }
            return _loc_1;
        }//end

        public boolean  contains (Object param1 )
        {
            return this.hash.get(param1) === true;
        }//end

    }


