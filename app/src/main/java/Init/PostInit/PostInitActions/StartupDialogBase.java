package Init.PostInit.PostInitActions;

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
import Display.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;

    public class StartupDialogBase
    {
        protected Function m_callback =null ;
        protected DisplayObject m_dialog ;
        protected boolean m_shouldQueue =false ;
        protected String m_loggingName ;
        private boolean m_onStartUp ;
        protected StatTracker m_viewTracker ;
        protected StatTracker m_closeTracker ;
        protected StatTracker m_okTracker ;
        protected StatTracker m_cancelTracker ;

        public  StartupDialogBase (DisplayObject param1 ,String param2 ,boolean param3 =false ,Function param4 =null ,boolean param5 =true ,StatTracker param6 =null ,StatTracker param7 =null )
        {
            this.m_dialog = param1;
            this.m_shouldQueue = param3;
            this.m_callback = param4;
            this.m_loggingName = param2;
            this.m_dialog.addEventListener(Event.CLOSE, this.onClose);
            this.m_onStartUp = param5;
            this.m_viewTracker = param6;
            this.m_closeTracker = param7;
            return;
        }//end

        public boolean  shouldQueue ()
        {
            return this.m_shouldQueue;
        }//end

        protected boolean  isEligible ()
        {
            return this.m_dialog != null;
        }//end

        final public void  show ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ANNOUNCEMENT, "view", this.m_loggingName);
            if (this.m_viewTracker != null)
            {
                this.m_viewTracker.track();
            }
            if (this.isEligible())
            {
                UI.displayPopup(this.m_dialog, this.m_shouldQueue, this.m_dialog.name, true);
            }
            else
            {
                this.m_callback = null;
                this.onClose();
            }
            return;
        }//end

        final public void  showStandalone ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ANNOUNCEMENT, "view", this.m_loggingName);
            if (this.m_viewTracker != null)
            {
                this.m_viewTracker.track();
            }
            this.m_dialog.removeEventListener(Event.CLOSE, this.onClose);
            UI.displayPopup(this.m_dialog, this.m_shouldQueue, this.m_dialog.name, true);
            return;
        }//end

        final public void  onClose (Event event =null )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ANNOUNCEMENT, "close", this.m_loggingName);
            if (this.m_closeTracker != null)
            {
                this.m_closeTracker.track();
            }
            this.m_dialog.removeEventListener(Event.CLOSE, this.onClose);
            if (this.m_callback != null)
            {
                this.m_callback();
            }
            if (this.m_onStartUp)
            {
                StartUpDialogManager.pumpDialogQueue();
            }
            this.m_dialog = null;
            this.m_callback = null;
            return;
        }//end

    }



