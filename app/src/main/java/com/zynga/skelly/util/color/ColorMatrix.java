package com.zynga.skelly.util.color;

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

//import flash.filters.*;
    public class ColorMatrix
    {
        public Array matrix ;
        private static double r_lum =0.212671;
        private static double g_lum =0.71516;
        private static double b_lum =0.072169;
        private static Array IDENTITY =new Array(1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0);

        public  ColorMatrix (Object param1)
        {
            if (param1 instanceof ColorMatrix)
            {
                this.matrix = param1.matrix.concat();
            }
            else if (param1 instanceof Array)
            {
                this.matrix = param1.concat();
            }
            else
            {
                this.reset();
            }
            return;
        }//end

        public void  reset ()
        {
            this.matrix = IDENTITY.concat();
            return;
        }//end

        public ColorMatrix  clone ()
        {
            return new ColorMatrix(this.matrix);
        }//end

        public void  adjustSaturation (double param1 )
        {
            _loc_2 = param11-;
            _loc_3 = _loc_2*r_lum ;
            _loc_4 = _loc_2*g_lum ;
            _loc_5 = _loc_2*b_lum ;
            Array _loc_6 =new Array(_loc_3 +param1 ,_loc_4 ,_loc_5 ,0,0,_loc_3 ,_loc_4 +param1 ,_loc_5 ,0,0,_loc_3 ,_loc_4 ,_loc_5 +param1 ,0,0,0,0,0,1,0);
            this.concat(_loc_6);
            return;
        }//end

        public void  adjustContrast (double param1 ,double param2 =0,double param3 =0)
        {
            param2 = param2 || param1;
            param3 = param3 || param1;
            param1 = param1 + 1;
            param2 = param2 + 1;
            param3 = param3 + 1;
            Array _loc_4 =new Array(param1 ,0,0,0,128*(1-param1 ),0,param2 ,0,0,128*(1-param2 ),0,0,param3 ,0,128*(1-param3 ),0,0,0,1,0);
            this.concat(_loc_4);
            return;
        }//end

        public void  adjustBrightness (double param1 ,double param2 =0,double param3 =0)
        {
            param2 = param2 || param1;
            param3 = param3 || param1;
            Array _loc_4 =new Array(1,0,0,0,param1 ,0,1,0,0,param2 ,0,0,1,0,param3 ,0,0,0,1,0);
            this.concat(_loc_4);
            return;
        }//end

        public void  adjustHue (double param1 )
        {
            param1 = param1 * (Math.PI / 180);
            _loc_2 = Math.cos(param1 );
            _loc_3 = Math.sin(param1 );
            double _loc_4 =0.213;
            double _loc_5 =0.715;
            double _loc_6 =0.072;
            Array _loc_7 =new Array(_loc_4 +_loc_2 *(1-_loc_4 )+_loc_3 *(-_loc_4 ),_loc_5 +_loc_2 *(-_loc_5 )+_loc_3 *(-_loc_5 ),_loc_6 +_loc_2 *(-_loc_6 )+_loc_3 *(1-_loc_6 ),0,0,_loc_4 +_loc_2 *(-_loc_4 )+_loc_3 *0.143,_loc_5 +_loc_2 *(1-_loc_5 )+_loc_3 *0.14,_loc_6 +_loc_2 *(-_loc_6 )+_loc_3 *-0.283,0,0,_loc_4 +_loc_2 *(-_loc_4 )+_loc_3 *(-(1-_loc_4 )),_loc_5 +_loc_2 *(-_loc_5 )+_loc_3 *_loc_5 ,_loc_6 +_loc_2 *(1-_loc_6 )+_loc_3 *_loc_6 ,0,0,0,0,0,1,0,0,0,0,0,1);
            this.concat(_loc_7);
            return;
        }//end

        public void  colorize (double param1 ,double param2 )
        {
            _loc_3 = param1(>>16& 255) / 255;
            _loc_4 = param1(>>8& 255) / 255;
            _loc_5 = param1(& 255) / 255;
            if (isNaN(param2))
            {
                param2 = 1;
            }
            _loc_6 = param21-;
            Array _loc_7 =new Array(_loc_6 +param2 *_loc_3 *r_lum ,param2 *_loc_3 *g_lum ,param2 *_loc_3 *b_lum ,0,0,param2 *_loc_4 *r_lum ,_loc_6 +param2 *_loc_4 *g_lum ,param2 *_loc_4 *b_lum ,0,0,param2 *_loc_5 *r_lum ,param2 *_loc_5 *g_lum ,_loc_6 +param2 *_loc_5 *b_lum ,0,0,0,0,0,1,0);
            this.concat(_loc_7);
            return;
        }//end

        public void  setAlpha (double param1 )
        {
            Array _loc_2 =new Array(1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,param1 ,0);
            this.concat(_loc_2);
            return;
        }//end

        public void  desaturate ()
        {
            Array _loc_1 =new Array(r_lum ,g_lum ,b_lum ,0,0,r_lum ,g_lum ,b_lum ,0,0,r_lum ,g_lum ,b_lum ,0,0,0,0,0,1,0);
            this.concat(_loc_1);
            return;
        }//end

        public void  invert ()
        {
            Array _loc_1 =new Array (-1,0,0,0,255,0,-1,0,0,255,0,0,-1,0,255,0,0,0,1,0);
            this.concat(_loc_1);
            return;
        }//end

        public void  threshold (double param1 )
        {
            Array _loc_2 =new Array(r_lum *256,g_lum *256,b_lum *256,0,-256*param1 ,r_lum *256,g_lum *256,b_lum *256,0,-256*param1 ,r_lum *256,g_lum *256,b_lum *256,0,-256*param1 ,0,0,0,1,0);
            this.concat(_loc_2);
            return;
        }//end

        public void  randomize (double param1 )
        {
            if (isNaN(param1))
            {
                param1 = 1;
            }
            _loc_2 = param11-;
            _loc_3 = _loc_2+param1 *(Math.random ()-Math.random ());
            _loc_4 = param1*(Math.random ()-Math.random ());
            _loc_5 = param1*(Math.random ()-Math.random ());
            _loc_6 = param1*255*(Math.random ()-Math.random ());
            _loc_7 = param1*(Math.random ()-Math.random ());
            _loc_8 = _loc_2+param1*(Math.random()-Math.random());
            _loc_9 = param1*(Math.random ()-Math.random ());
            _loc_10 = param1*255*(Math.random ()-Math.random ());
            _loc_11 = param1*(Math.random ()-Math.random ());
            _loc_12 = param1*(Math.random ()-Math.random ());
            _loc_13 = _loc_2+param1*(Math.random()-Math.random());
            _loc_14 = param1*255*(Math.random ()-Math.random ());
            Array _loc_15 =new Array(_loc_3 ,_loc_4 ,_loc_5 ,0,_loc_6 ,_loc_7 ,_loc_8 ,_loc_9 ,0,_loc_10 ,_loc_11 ,_loc_12 ,_loc_13 ,0,_loc_14 ,0,0,0,1,0);
            this.concat(_loc_15);
            return;
        }//end

        public void  setChannels (double param1 ,double param2 ,double param3 ,double param4 )
        {
            _loc_5 = param1((& 1) == 1 ? (1) : (0)) + ((param1 & 2) == 2 ? (1) : (0)) + ((param1 & 4) == 4 ? (1) : (0)) + ((param1 & 8) == 8 ? (1) : (0));
            if (((param1 & 1) == 1 ? (1) : (0)) + ((param1 & 2) == 2 ? (1) : (0)) + ((param1 & 4) == 4 ? (1) : (0)) + ((param1 & 8) == 8 ? (1) : (0)) > 0)
            {
                _loc_5 = 1 / _loc_5;
            }
            _loc_6 = param2((& 1) == 1 ? (1) : (0)) + ((param2 & 2) == 2 ? (1) : (0)) + ((param2 & 4) == 4 ? (1) : (0)) + ((param2 & 8) == 8 ? (1) : (0));
            if (((param2 & 1) == 1 ? (1) : (0)) + ((param2 & 2) == 2 ? (1) : (0)) + ((param2 & 4) == 4 ? (1) : (0)) + ((param2 & 8) == 8 ? (1) : (0)) > 0)
            {
                _loc_6 = 1 / _loc_6;
            }
            _loc_7 = param3((& 1) == 1 ? (1) : (0)) + ((param3 & 2) == 2 ? (1) : (0)) + ((param3 & 4) == 4 ? (1) : (0)) + ((param3 & 8) == 8 ? (1) : (0));
            if (((param3 & 1) == 1 ? (1) : (0)) + ((param3 & 2) == 2 ? (1) : (0)) + ((param3 & 4) == 4 ? (1) : (0)) + ((param3 & 8) == 8 ? (1) : (0)) > 0)
            {
                _loc_7 = 1 / _loc_7;
            }
            _loc_8 = param4((& 1) == 1 ? (1) : (0)) + ((param4 & 2) == 2 ? (1) : (0)) + ((param4 & 4) == 4 ? (1) : (0)) + ((param4 & 8) == 8 ? (1) : (0));
            if (((param4 & 1) == 1 ? (1) : (0)) + ((param4 & 2) == 2 ? (1) : (0)) + ((param4 & 4) == 4 ? (1) : (0)) + ((param4 & 8) == 8 ? (1) : (0)) > 0)
            {
                _loc_8 = 1 / _loc_8;
            }
            Array _loc_9 =new Array ((param1 & 1) == 1 ? (_loc_5) : (0), (param1 & 2) == 2 ? (_loc_5) : (0), (param1 & 4) == 4 ? (_loc_5) : (0), (param1 & 8) == 8 ? (_loc_5) : (0), 0, (param2 & 1) == 1 ? (_loc_6) : (0), (param2 & 2) == 2 ? (_loc_6) : (0), (param2 & 4) == 4 ? (_loc_6) : (0), (param2 & 8) == 8 ? (_loc_6) : (0), 0, (param3 & 1) == 1 ? (_loc_7) : (0), (param3 & 2) == 2 ? (_loc_7) : (0), (param3 & 4) == 4 ? (_loc_7) : (0), (param3 & 8) == 8 ? (_loc_7) : (0), 0, (param4 & 1) == 1 ? (_loc_8) : (0), (param4 & 2) == 2 ? (_loc_8) : (0), (param4 & 4) == 4 ? (_loc_8) : (0), (param4 & 8) == 8 ? (_loc_8) : (0), 0);
            this.concat(_loc_9);
            return;
        }//end

        public void  blend (ColorMatrix param1 ,double param2 )
        {
            _loc_3 = param21-;
            double _loc_4 =0;
            while (_loc_4 < 20)
            {

                this.matrix.put(_loc_4,  _loc_3 * this.matrix.get(_loc_4) + param2 * param1.matrix.get(_loc_4));
                _loc_4 = _loc_4 + 1;
            }
            return;
        }//end

        public void  concat (Array param1 )
        {
            double _loc_5 =0;
            Array _loc_2 =new Array ();
            double _loc_3 =0;
            double _loc_4 =0;
            while (_loc_4 < 4)
            {

                _loc_5 = 0;
                while (_loc_5 < 5)
                {

                    _loc_2[_loc_3 + _loc_5] = param1[_loc_3] * this.matrix[_loc_5] + param1[(_loc_3 + 1)] * this.matrix[_loc_5 + 5] + param1[_loc_3 + 2] * this.matrix[_loc_5 + 10] + param1[_loc_3 + 3] * this.matrix.get(_loc_5 + 15) + (_loc_5 == 4 ? (param1.get(_loc_3 + 4)) : (0));
                    _loc_5 = _loc_5 + 1;
                }
                _loc_3 = _loc_3 + 5;
                _loc_4 = _loc_4 + 1;
            }
            this.matrix = _loc_2;
            return;
        }//end

        public ColorMatrixFilter  filter ()
        {
            return new ColorMatrixFilter(this.matrix);
        }//end

    }


