package Mechanics;

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

    public class MechanicActionResult
    {
        protected boolean m_success ;
        protected boolean m_shouldProceed ;
        protected boolean m_sendTransaction ;
        protected Object m_transactionParams ;

        public  MechanicActionResult (boolean param1 ,boolean param2 ,boolean param3 =true ,Object param4 =null )
        {
            this.m_success = param1;
            this.m_shouldProceed = param2;
            this.m_sendTransaction = param3;
            this.m_transactionParams = param4;
            return;
        }//end

        public boolean  actionSuccess ()
        {
            return this.m_success;
        }//end

        public boolean  continueActionExecution ()
        {
            return this.m_shouldProceed;
        }//end

        public boolean  sendTransaction ()
        {
            return this.m_sendTransaction;
        }//end

        public Object  transactionParams ()
        {
            return this.m_transactionParams;
        }//end

    }



