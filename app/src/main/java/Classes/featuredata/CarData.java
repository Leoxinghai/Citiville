package Classes.featuredata;

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

    public class CarData implements FeatureDataInventory
    {
        private Object m_featureData ;
        private static RegExp carRegexp =/^(\d+)\*(\w+)""^(\d+)\*(\w+)/;

        public  CarData (Object param1 )
        {
            this.m_featureData = param1;
            if (this.m_featureData == null)
            {
                this.m_featureData = new Object();
                m_featureData.put("inventory", newObject());
            }
            return;
        }//end

        public Object featureData ()
        {
            return this.m_featureData;
        }//end

        public void  featureData (Object param1)
        {
            this.m_featureData = param1;
            return;
        }//end

        public boolean  carsEnabled ()
        {
            boolean _loc_1 =false ;
            if (this.m_featureData && this.m_featureData.hasOwnProperty("carsenabled"))
            {
                _loc_1 = this.m_featureData.get("carsenabled");
            }
            return _loc_1;
        }//end

        public void  carsEnabled (boolean param1 )
        {
            if (this.m_featureData)
            {
                this.m_featureData.put("carsenabled",  param1);
            }
            return;
        }//end

        public double  carsToForceSpawn ()
        {
            double _loc_1 =0;
            if (this.m_featureData && this.m_featureData.hasOwnProperty("carstoforcespawn"))
            {
                _loc_1 = this.m_featureData.get("carstoforcespawn");
            }
            return _loc_1;
        }//end

        public void  carsToForceSpawn (double param1 )
        {
            if (this.m_featureData)
            {
                this.m_featureData.put("carstoforcespawn",  param1);
            }
            return;
        }//end

        public Object  getItems ()
        {
            return this.m_featureData.get("inventory");
        }//end

        public boolean  updateItemAmount (String param1 ,int param2 )
        {
            _loc_3 = carRegexp.exec(param1);
            if (_loc_3 && _loc_3.length == 3)
            {
                param1 = String(_loc_3.get(2));
                param2 = int(_loc_3.get(1));
            }
            if (this.m_featureData.get("inventory").hasOwnProperty(param1))
            {
                this.m_featureData.get("inventory").put(param1,  this.m_featureData.get("inventory").get(param1) + param2);
            }
            else
            {
                this.m_featureData.get("inventory").put(param1,  param2);
            }
            this.m_featureData.get("inventory").put(param1,  Math.max(this.m_featureData.get("inventory").get(param1), 0));
            return true;
        }//end

    }



