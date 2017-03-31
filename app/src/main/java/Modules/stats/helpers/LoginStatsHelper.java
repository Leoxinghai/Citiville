package Modules.stats.helpers;

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

import Engine.Init.*;
import Modules.stats.*;
//import flash.events.*;

    public class LoginStatsHelper extends AbstractStatsHelper
    {
        private InitializationManager m_initializationManager ;

        public  LoginStatsHelper (InitializationManager param1 )
        {
            this.m_initializationManager = param1;
            return;
        }//end

         public void  init ()
        {
            if (this.m_initializationManager.haveAllCompleted())
            {
                this.processInitializationComplete();
            }
            else
            {
                this.m_initializationManager.addEventListener(Event.COMPLETE, this.initializationCompleteHandler);
            }
            return;
        }//end

        private void  processInitializationComplete ()
        {
            Object _loc_1 =null ;
            IStatsTarget _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < m_statsTargets.size(); i0++) 
            {
            		_loc_1 = m_statsTargets.get(i0);

                _loc_2 =(IStatsTarget) _loc_1;
                _loc_3 =(String) m_statsTargets.get(_loc_2);
                //process(_loc_2, _loc_3);
            }
            return;
        }//end

        private void  initializationCompleteHandler (Event event )
        {
            _loc_2 =(InitializationManager) event.target;
            _loc_2.removeEventListener(Event.COMPLETE, this.initializationCompleteHandler);
            this.processInitializationComplete();
            return;
        }//end

    }



