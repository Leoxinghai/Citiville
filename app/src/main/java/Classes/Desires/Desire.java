package Classes.Desires;

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
import Classes.sim.*;

    public class Desire
    {
        protected String m_name ;
        protected DesirePeep m_peep ;
        protected String m_state ;
        protected int m_resistThreshold =0;
        protected int m_resists =0;
        public static  String STATE_NOT_STARTED ="not started";
        public static  String STATE_STARTED ="started";
        public static  String STATE_FINISHED ="finished";

        public  Desire (DesirePeep param1 )
        {
            this.m_peep = param1;
            this.setState(STATE_NOT_STARTED);
            return;
        }//end

        public boolean  isActionable ()
        {
            _loc_1 = this.getSelection ();
            if (_loc_1 != null && _loc_1.actions != null && _loc_1.actions.length > 0)
            {
                return true;
            }
            return false;
        }//end

        public SelectionResult  getSelection ()
        {
            return SelectionResult.FAIL;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  getState ()
        {
            return this.m_state;
        }//end

        public void  setState (String param1 )
        {
            this.m_state = param1;
            return;
        }//end

        public void  resistThreshold (int param1 )
        {
            this.m_resistThreshold = param1;
            return;
        }//end

        public int  resistThreshold ()
        {
            return this.m_resistThreshold;
        }//end

    }



