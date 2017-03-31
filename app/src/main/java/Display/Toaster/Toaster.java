package Display.Toaster;

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

import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;

    public class Toaster extends Sprite
    {
        protected int m_duration ;
        protected String m_statName ;
        protected Function m_mouseClickHandler ;

        public  Toaster (String param1 ,Function param2 )
        {
            this.m_mouseClickHandler = param2;
            this.m_statName = param1;
            this.m_duration = 1000;
            addEventListener(MouseEvent.MOUSE_MOVE, this.mouseCaptureHandler, false, 0, true);
            addEventListener(MouseEvent.CLICK, this.mouseClickHandlerWrapper, false, 0, true);
            return;
        }//end

        public int  duration ()
        {
            return this.m_duration;
        }//end

        public void  duration (int param1 )
        {
            this.m_duration = param1;
            return;
        }//end

        public double  displayHeight ()
        {
            return height;
        }//end

        public String  statName ()
        {
            return this.m_statName;
        }//end

        private void  mouseCaptureHandler (MouseEvent event )
        {
            event.stopPropagation();
            return;
        }//end

        private void  mouseClickHandlerWrapper (MouseEvent event )
        {
            event.stopPropagation();
            if (this.m_mouseClickHandler != null)
            {
                this.m_mouseClickHandler(event);
            }
            this.countClick();
            return;
        }//end

        protected void  close ()
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

        public void  countView ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_MINITOASTER, this.m_statName, "view");
            return;
        }//end

        public void  countAutoClose ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_MINITOASTER, this.m_statName, "auto_close");
            return;
        }//end

        public void  countForcedClose ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_MINITOASTER, this.m_statName, "manual_close");
            return;
        }//end

        public void  countClick ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_MINITOASTER, this.m_statName, "click");
            return;
        }//end

    }



