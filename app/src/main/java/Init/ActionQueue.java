package Init;

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
import com.xiyu.util.Event;

import Engine.Init.*;
//import flash.events.*;

    public class ActionQueue
    {
        protected Array m_theQ ;

        public  ActionQueue ()
        {
            this.m_theQ = new Array();
            return;
        }//end

        public void  add (InitializationAction param1 )
        {
            this.m_theQ.push(param1);
            return;
        }//end

        public void  execute ()
        {
            this.doNextAction();
            return;
        }//end

        public void  cancel ()
        {
            this.m_theQ = new Array();
            return;
        }//end

        protected void  onActionCompleted (Event event =null )
        {
            this.doNextAction();
            return;
        }//end

        protected void  doNextAction ()
        {
            InitializationAction _loc_1 =null ;
            if (this.m_theQ.length() > 0)
            {
                _loc_1 = (InitializationAction)this.m_theQ.pop();
                _loc_1.addEventListener(Event.COMPLETE, this.onActionCompleted);
                _loc_1.execute();
            }
            return;
        }//end

    }



