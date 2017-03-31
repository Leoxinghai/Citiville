package Modules.dataservices;

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

    public class DataServicesResult
    {
        protected String m_queryName ;
        protected Array m_queryArgs ;
        protected Object m_results ;
        protected int m_status ;
        public static  int PENDING =0;
        public static  int COMPLETE =1;

        public  DataServicesResult (String param1 ,Array param2 )
        {
            this.m_queryName = param1;
            this.m_queryArgs = param2;
            this.m_results = null;
            this.m_status = PENDING;
            return;
        }//end

        public void  onComplete (Object param1 )
        {
            this.m_results = param1;
            this.m_status = COMPLETE;
            return;
        }//end

        public String  getQueryName ()
        {
            return this.m_queryName;
        }//end

        public Array  getQueryArgs ()
        {
            return this.m_queryArgs;
        }//end

        public boolean  isComplete ()
        {
            return this.m_status == COMPLETE;
        }//end

        public boolean  isValid ()
        {
            return this.m_status == COMPLETE && this.m_results != null;
        }//end

        public Object  ()
        {
            return this.m_results;
        }//end

    }



