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

    public class Box3D
    {
        protected Vector3 m_size ;
        protected Vector3 m_position ;

        public  Box3D (Vector3 param1 ,Vector3 param2 )
        {
            this.m_size = new Vector3();
            this.m_position = new Vector3();
            if (param1 != null)
            {
                this.m_position = param1.clone();
            }
            if (param2 != null)
            {
                this.m_size = param2.clone();
            }
            return;
        }//end

        public Vector3  min ()
        {
            return this.m_position.clone();
        }//end

        public Vector3  max ()
        {
            return this.m_position.add(this.m_size);
        }//end

        public Vector3  position ()
        {
            return this.m_position.clone();
        }//end

        public Vector3  size ()
        {
            return this.m_size.clone();
        }//end

        public double  x ()
        {
            return this.m_position.x;
        }//end

        public double  y ()
        {
            return this.m_position.y;
        }//end

        public double  z ()
        {
            return this.m_position.z;
        }//end

        public double  width ()
        {
            return this.m_size.x;
        }//end

        public double  height ()
        {
            return this.m_size.y;
        }//end

        public double  length ()
        {
            return this.m_size.z;
        }//end

        public void  position (Vector3 param1 )
        {
            this.m_position = param1.clone();
            return;
        }//end

        public void  size (Vector3 param1 )
        {
            this.m_size = param1.clone();
            return;
        }//end

        public void  x (double param1 )
        {
            this.m_position.x = param1;
            return;
        }//end

        public void  y (double param1 )
        {
            this.m_position.y = param1;
            return;
        }//end

        public void  z (double param1 )
        {
            this.m_position.z = param1;
            return;
        }//end

        public void  width (double param1 )
        {
            this.m_size.x = param1;
            return;
        }//end

        public void  height (double param1 )
        {
            this.m_size.y = param1;
            return;
        }//end

        public void  length (double param1 )
        {
            this.m_size.z = param1;
            return;
        }//end

        public boolean  isPointInside (Vector3 param1 ,boolean param2 =true )
        {
            _loc_3 = this.max ;
            if (param2)
            {
                return this.m_position.x <= param1.x && _loc_3.x >= param1.x && this.m_position.y <= param1.y && _loc_3.y >= param1.y && this.m_position.z <= param1.z && _loc_3.z >= param1.z;
            }
            return this.m_position.x < param1.x && _loc_3.x > param1.x && this.m_position.y < param1.y && _loc_3.y > param1.y && this.m_position.z < param1.z && _loc_3.z > param1.z;
        }//end

        public boolean  isBoxIntersecting (Box3D param1 ,boolean param2 =true )
        {
            return false;
        }//end

    }



