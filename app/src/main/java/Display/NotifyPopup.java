package Display;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;

    public class NotifyPopup extends GenericDialog
    {
        protected Timer m_disappearTimer =null ;
        protected Vector2 m_appearPos =null ;
        protected int m_timeout =0;
        public static  int DISAPPEAR_TIMEOUT =6000;

        public  NotifyPopup (Function param1 ,String param2 ,int param3 =6000)
        {
            super(param2, "", 0, param1, "", "", false);
            m_centered = false;
            this.m_timeout = param3;
            return;
        }//end

         protected void  init ()
        {
            m_holder = new Sprite();
            this.addChild(m_holder);
            m_jwindow = new JWindow(m_holder);
            m_content = m_holder;
            m_content.addEventListener("close", closeMe, false, 0, true);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.POPULATION_ASSETS, makeAssets);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            m_jpanel = this.createDialogView(this.createAssetDict());
            finalizeAndShow();
            this.m_appearPos = new Vector2(38, 472 - 25);
            this.x = this.m_appearPos.x;
            this.y = this.m_appearPos.y;
            ASwingHelper.prepare(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            this.buttonMode = true;
            this.mouseChildren = false;
            return;
        }//end

         protected boolean  doTrackDialogActions ()
        {
            return false;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new NotifyPopupView(param1, m_message, m_title, m_type, m_callback);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("bgAsset",  new (DisplayObject)m_comObject.neighborBar_populationPopup());
            return _loc_1;
        }//end

        public void  showTimed ()
        {
            if (m_jpanel)
            {
                ((NotifyPopupView)m_jpanel).rebuild(NotifyPopupView.STATE_SHORT);
                ASwingHelper.prepare(m_jwindow);
                super.show();
                this.restartDisappearTimer();
            }
            return;
        }//end

        public void  showOnTimed ()
        {
            if (m_jpanel)
            {
                ASwingHelper.prepare(m_jwindow);
                super.show();
                this.stopDisappearTimer();
            }
            return;
        }//end

         public void  show ()
        {
            if (m_jpanel)
            {
                ((NotifyPopupView)m_jpanel).rebuild(NotifyPopupView.STATE_LONG);
                ASwingHelper.prepare(m_jwindow);
                super.show();
                this.stopDisappearTimer();
            }
            return;
        }//end

         public void  hide ()
        {
            super.hide();
            this.stopDisappearTimer();
            return;
        }//end

        protected void  restartDisappearTimer ()
        {
            if (this.m_disappearTimer == null)
            {
                this.m_disappearTimer = new Timer(this.m_timeout);
                this.m_disappearTimer.start();
                this.m_disappearTimer.addEventListener(TimerEvent.TIMER, this.onTimer, false, 0, true);
            }
            else
            {
                this.m_disappearTimer.reset();
                this.m_disappearTimer.start();
            }
            return;
        }//end

        protected void  stopDisappearTimer ()
        {
            if (this.m_disappearTimer)
            {
                this.m_disappearTimer.stop();
            }
            return;
        }//end

        protected void  onTimer (TimerEvent event )
        {
            if (this.m_disappearTimer)
            {
                this.m_disappearTimer.stop();
            }
            this.hide();
            return;
        }//end

         protected void  hideTween (Function param1 )
        {
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            param1();
            return;
        }//end

         protected void  showTween ()
        {
            boolean _loc_3 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_3;
            Point _loc_1 =new Point(m_content.width ,m_content.height );
            Matrix _loc_2 =new Matrix ();
            _loc_2.translate((-_loc_1.x) / 2, (-_loc_1.y) / 2);
            _loc_2.scale(1, 1);
            _loc_2.translate(_loc_1.x / 2, _loc_1.y / 2);
            m_content.transform.matrix = _loc_2;
            if (m_centered)
            {
                centerPopup();
            }
            this.onShowComplete();
            return;
        }//end

         public void  onShowComplete ()
        {
            this.x = this.m_appearPos.x;
            this.y = this.m_appearPos.y;
            return;
        }//end

        public void  setAppearPos (Vector2 param1 )
        {
            this.m_appearPos = param1;
            return;
        }//end

    }



