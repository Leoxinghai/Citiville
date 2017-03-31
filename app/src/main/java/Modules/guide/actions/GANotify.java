package Modules.guide.actions;

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

    public class GANotify extends GuideAction
    {
        protected String m_notify ;

        public  GANotify ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"notify");
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = _loc_2.@name;
            if (_loc_3.length > 0)
            {
                this.m_notify = _loc_3;
            }
            else
            {
                return false;
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            m_guide.notify(this.m_notify);
            removeState(this);
            return;
        }//end

        public void  notify (String param1 )
        {
            this.m_notify = param1;
            return;
        }//end

    }



