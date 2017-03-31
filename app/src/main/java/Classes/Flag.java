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

import Classes.util.*;
import Transactions.*;

    public class Flag
    {
        protected  int SECOND =1000;
        protected  int MINUTE =60000;
        protected  int HOUR =3.6e +006;
        protected  int DAY =8.64e +007;
        protected  int WEEK =6.048e +008;
        public double lastModifiedGlobalEngineTime ;
        public String name ;
        public double m_value ;

        public  Flag (String param1 )
        {
            this.name = param1;
            this.m_value = 0;
            return;
        }//end

        public void  value (double param1 )
        {
            this.lastModifiedGlobalEngineTime = GlobalEngine.getTimer();
            this.m_value = param1;
            return;
        }//end

        public double  value ()
        {
            return this.m_value;
        }//end

        public double  getLastModifiedGlobalEngineTime ()
        {
            return this.lastModifiedGlobalEngineTime;
        }//end

        public boolean  hasSecondsElapsed (int param1 )
        {
            if (!this.lastModifiedGlobalEngineTime)
            {
                return true;
            }
            _loc_2 = GlobalEngine.getTimer ()-this.lastModifiedGlobalEngineTime ;
            return _loc_2 > this.SECOND * param1;
        }//end

        public boolean  hasDaysElapsed (int param1 )
        {
            if (!this.lastModifiedGlobalEngineTime)
            {
                return true;
            }
            _loc_2 = GlobalEngine.getTimer ()-this.lastModifiedGlobalEngineTime ;
            return _loc_2 > this.DAY * param1;
        }//end

        public void  setAndSave (double param1 )
        {
            this.value = param1;
            GameTransactionManager.addTransaction(new TFlag(this.name, param1));
            return;
        }//end

        public static Flag  buildFromServerObject (Object param1 )
        {
            Flag _loc_2 =new Flag(param1.name );
            _loc_2.value = param1.m_value;
            _loc_2.lastModifiedGlobalEngineTime = param1.lastModifiedGlobalEngineTime;
            return _loc_2;
        }//end

    }


