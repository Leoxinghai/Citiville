package Classes;

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

    public class SeaVehicle extends Vehicle
    {
        protected Array m_shipWaypoints ;
        private boolean m_isIncoming =false ;

        public  SeaVehicle (String param1 ,boolean param2 ,double param3 =-1)
        {
            super(param1, param2, param3);
            this.m_shipWaypoints = new Array();
            return;
        }//end

         public boolean  isVehicle ()
        {
            return false;
        }//end

         public boolean  isSeaVehicle ()
        {
            return true;
        }//end

        public void  setIsIncoming ()
        {
            this.m_isIncoming = true;
            return;
        }//end

        public boolean  isIncoming ()
        {
            return this.m_isIncoming;
        }//end

    }


