package Modules.quest.Display;

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

import Display.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;

    public class ButtonManager
    {
public static Array m_registeredTooltips =new Array();
public static Array m_registeredButtons =new Array();
public static Array m_buttonGroups =new Array();
        public static XML m_effectConfig ;

        public  ButtonManager ()
        {
            return;
        }//end

        public static boolean  isCustomButton (Object param1)
        {
            MovieClip _loc_3 =null ;
            boolean _loc_2 =false ;
            if (param1 instanceof MovieClip)
            {
                _loc_3 =(MovieClip) param1;
                if (_loc_3.framesLoaded == 4)
                {
                    _loc_2 = true;
                }
                else
                {
                    _loc_2 = false;
                }
            }
            return _loc_2;
        }//end

        public static void  buttonize (Object param1 ,String param2 ,String param3 =null ,boolean param4 =false )
        {
            //registerButton(param1, param2, param3, param4);
            if (param1 instanceof Sprite)
            {
                param1.buttonMode = true;
                param1.useHandCursor = true;

                param1.addEventListener(MouseEvent.MOUSE_OVER, onMouseOver);
                param1.addEventListener(MouseEvent.MOUSE_OUT, onMouseOut);
                param1.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
                param1.addEventListener(MouseEvent.MOUSE_UP, onMouseUp);
*/

            }
            return;
        }//end

        public static void  enable (Object param1)
        {
            if (param1 instanceof Sprite)
            {

                if (!param1.hasEventListener(MouseEvent.MOUSE_OVER))
                {
                    param1.addEventListener(MouseEvent.MOUSE_OVER, onMouseOver);
                }
                if (!param1.hasEventListener(MouseEvent.MOUSE_OUT))
                {
                    param1.addEventListener(MouseEvent.MOUSE_OUT, onMouseOut);
                }
                if (!param1.hasEventListener(MouseEvent.MOUSE_DOWN))
                {
                    param1.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
                }
                if (!param1.hasEventListener(MouseEvent.MOUSE_UP))
                {
                    param1.addEventListener(MouseEvent.MOUSE_UP, onMouseUp);
                }
*/
            }
            if (!(param1 instanceof SimpleButton))
            {
                param1.buttonMode = true;
            }
            param1.useHandCursor = true;
            param1.filters = new Array();
            return;
        }//end

        public static void  disable (Object param1)
        {
            if (param1 instanceof Sprite)
            {

                if (param1.hasEventListener(MouseEvent.MOUSE_OVER))
                {
                    param1.removeEventListener(MouseEvent.MOUSE_OVER, onMouseOver);
                }
                if (param1.hasEventListener(MouseEvent.MOUSE_OUT))
                {
                    param1.removeEventListener(MouseEvent.MOUSE_OUT, onMouseOut);
                }
                if (param1.hasEventListener(MouseEvent.MOUSE_DOWN))
                {
                    param1.removeEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
                }
                if (param1.hasEventListener(MouseEvent.MOUSE_UP))
                {
                    param1.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
                }
*/
            }
            if (!(param1 instanceof SimpleButton))
            {
                param1.buttonMode = false;
            }
            param1.useHandCursor = false;
            param1.filters = .get(getDisableFilter());
            return;
        }//end

        public static ColorMatrixFilter  getDisableFilter ()
        {
            double _loc_1 =0.01;
            ColorMatrixFilter _loc_2 =new ColorMatrixFilter ();
            Array _loc_3 =.get(1 ,0,0,0,0) ;
            Array _loc_4 =.get(0 ,1,0,0,0) ;
            Array _loc_5 =.get(0 ,0,1,0,0) ;
            Array _loc_6 =.get(0 ,0,0,1,0) ;
            Array _loc_7 =.get(0 .3,0.59,0.11,0,0) ;
            Array _loc_8 =new Array ();
            _loc_8 = new Array().concat(interpolateArrays(_loc_7, _loc_3, _loc_1));
            _loc_8 = _loc_8.concat(interpolateArrays(_loc_7, _loc_4, _loc_1));
            _loc_8 = _loc_8.concat(interpolateArrays(_loc_7, _loc_5, _loc_1));
            _loc_8 = _loc_8.concat(_loc_6);
            _loc_2.matrix = _loc_8;
            return _loc_2;
        }//end

        private static Object  interpolateArrays (Array param1 ,Array param2 ,double param3 )
        {
            _loc_4 = param1.length >=param2.length ? (param1.slice()) : (param2.slice());
            _loc_5 = param1(.length >=param2.length ? (param1.slice()) : (param2.slice())).length;
            while (_loc_5--)
            {

                _loc_4.put(_loc_5,  param1.get(_loc_5) + (param2.get(_loc_5) - param1.get(_loc_5)) * param3);
            }
            return _loc_4;
        }//end

        public static void  forceSelected (Object param1)
        {

            return;
        }//end

        public static void  applyToolTip (Sprite param1 ,String param2 )
        {

            return;
        }//end

        public static void  removeToolTip (Sprite param1 )
        {
            Object _loc_2 =null ;
            for(int i0 = 0; i0 < m_registeredTooltips.size(); i0++)
            {
            		_loc_2 = m_registeredTooltips.get(i0);

                if (_loc_2.image == param1)
                {
                    m_registeredTooltips.splice(m_registeredTooltips.indexOf(_loc_2), 1);
                }
            }
            return;
        }//end

    }



