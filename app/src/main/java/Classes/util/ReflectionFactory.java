package Classes.util;

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


    public class ReflectionFactory
    {
        private Class m_defaultClass ;
        private Vector<Class> m_allowedClasses;

        public  ReflectionFactory ()
        {
            this.m_defaultClass = null;
            this.m_allowedClasses = new Vector<Class>();
            return;
        }//end

        public void  register (Class param1 )
        {
            if (this.m_allowedClasses.indexOf(param1) < 0)
            {
                this.m_allowedClasses.push(param1);
            }
            return;
        }//end

        public Object  createObject (String param1 )
        {
            Object _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = this.createObjectFromClassName(param1);
            }
            else
            {
                _loc_2 = new this.m_defaultClass();
            }
            return _loc_2;
        }//end

        public Class  defaultClass ()
        {
            return this.m_defaultClass;
        }//end

        public void  defaultClass (Class param1 )
        {
            this.m_defaultClass = param1;
            return;
        }//end

        private Object  createObjectFromClassName (String param1 )
        {
            Object _loc_2 =null ;
            _loc_3 = this.m_defaultClass ;
            if (param1 !=null)
            {
                _loc_3 = this.getAllowedClassByName(param1);
            }
            if (_loc_3)
            {
                _loc_2 = new _loc_3;
            }
            return _loc_2;
        }//end

        private Class  getAllowedClassByName (String param1 )
        {
            Class _loc_3 =null ;
            Class _loc_2 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < this.m_allowedClasses.size(); i0++)
                {
                	_loc_3 = this.m_allowedClasses.get(i0);

                    if (param1 == Global.getClassName(_loc_3))
                    {
                        _loc_2 = _loc_3;
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

    }



