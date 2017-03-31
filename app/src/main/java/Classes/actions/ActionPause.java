package Classes.actions;

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

import Classes.*;
    public class ActionPause extends BaseAction
    {
        protected double m_pauseTime ;
        protected double m_currTime ;

        public  ActionPause (MapResource param1 ,double param2)
        {
            super(param1);
            this.m_pauseTime = param2;
            this.m_currTime = 0;
            return;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            if (this.m_pauseTime == -1)
            {
                return;
            }
            this.m_currTime = this.m_currTime + param1;
            if (this.m_currTime >= this.m_pauseTime)
            {
                m_mapResource.actionQueue.removeState(this);
            }
            return;
        }//end

    }



