package Engine.Helpers;

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

import java.util.Vector;


public class Vector3
    {
        public double x ;
        public double y ;
        public double z ;
        public static boolean optimizeMemoryUse =false ;
        private static Vector<Vector3> pool =new Vector<Vector3>();
        private static int allocCount =0;
        private static int allocTime =0;

        public  Vector3 (double param1,double param2,double param3)
        {
            this.x = param1;
            this.y = param2;
            this.z = param3;
            return;
        }//end

        public Vector3  clone ()
        {
            return alloc(this.x, this.y, this.z);
        }//end

        public void  set(Vector3 param1 )
        {
            this.x = param1.x;
            this.y = param1.y;
            this.z = param1.z;
            return;
        }//end

        public Vector3  add (Vector3 param1 )
        {
            Vector3 _loc_2 = alloc(this.x+param1.x,this.y+param1.y,this.z+param1.z);
            return _loc_2;
        }//end

        public Vector3  subtract (Vector3 param1 )
        {
            Vector3 _loc_2 = alloc(this.x-param1.x,this.y-param1.y,this.z-param1.z);
            return _loc_2;
        }//end

        public Vector3  scale (double param1 )
        {
            Vector3 _loc_2 = alloc(this.x*param1,this.y*param1,this.z*param1);
            return _loc_2;
        }//end

        public boolean  equals (Vector3 param1 )
        {
            return this.x == param1.x && this.y == param1.y && this.z == param1.z;
        }//end

        public String  toString ()
        {
            return "(x: " + this.x + ", y: " + this.y + ", z: " + this.z + ")";
        }//end

        public Vector2  toVector2 ()
        {
            return new Vector2(this.x, this.y);
        }//end

        public IntVector2  toIntVector2 ()
        {
            return new IntVector2(this.x, this.y);
        }//end

        public boolean  isAxisAligned ()
        {
            return this.x == 0 && this.y == 0 || this.x == 0 && this.z == 0 || this.y == 0 && this.z == 0;
        }//end

        public double  dot (Vector3 param1 )
        {
            return this.x * param1.x + this.y * param1.y + this.z * param1.z;
        }//end

        public Vector3  cross (Vector3 param1 )
        {
            return alloc(this.y * param1.z - this.z * param1.y, this.z * param1.x - this.x * param1.z, this.x * param1.y - this.y * param1.x);
        }//end

        public double  length ()
        {
            return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        }//end

        public Vector3  normalize (double param1 =1)
        {
            double _loc_2 = this.length();
            Vector3 _loc_3 = alloc(this.x/_loc_2*param1,this.y/_loc_2*param1,this.z/_loc_2*param1);
            return _loc_3;
        }//end

        public Vector3  floor ()
        {
            return alloc(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
        }//end

        public Vector3  ceil ()
        {
            return alloc(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
        }//end

        public static Vector3  alloc (double param1 =0,double param2 =0,double param3 =0)
        {
            Vector3 _loc_4 =null ;
            if (optimizeMemoryUse && pool.length > 0)
            {
                _loc_4 = pool.pop();
                _loc_4.x = param1;
                _loc_4.y = param2;
                _loc_4.z = param3;
            }
            else
            {
                _loc_4 = new Vector3(param1, param2, param3);
            }
            return _loc_4;
        }//end

        public static void  free (Vector3 param1 )
        {
            if (optimizeMemoryUse && param1 != null && !isNaN(param1.x) && pool.length < 100)
            {
                param1.x = -1;
                param1.y = -1;
                param1.z = -1;
                pool.push(param1);
            }
            return;
        }//end

        public static double  computeSquareDistance (Vector3 param1 ,Vector3 param2 )
        {
            double _loc_3 = param1.x -param2.x ;
            double _loc_4 = param1.y -param2.y ;
            double _loc_5 = param1.z -param2.z ;
            return _loc_3 * _loc_3 + _loc_4 * _loc_4 + _loc_5 * _loc_5;
        }//end

        public static Vector3  lerp (Vector3 param1 ,Vector3 param2 ,double param3 )
        {
            Vector3 _loc_4 = alloc();
            double _loc_5 = 1-param3;
            _loc_4.x = param1.x * _loc_5 + param2.x * param3;
            _loc_4.y = param1.y * _loc_5 + param2.y * param3;
            _loc_4.z = param1.z * _loc_5 + param2.z * param3;
            return _loc_4;
        }//end

    }



