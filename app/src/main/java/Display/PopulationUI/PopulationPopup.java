package Display.PopulationUI;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class PopulationPopup extends NotifyPopup
    {
        protected boolean m_bShowing =false ;
        protected int m_state ;
        private Timer m_removeTimer ;
public static PopulationPopup m_instance ;

        public  PopulationPopup (Function param1)
        {
            super(param1);
            this.m_removeTimer = new Timer(1000, 1);
            return;
        }//end

        public boolean  showing ()
        {
            return this.m_bShowing;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            m_jpanel = this.createDialogView(this.createAssetDict());
            m_appearPos = new Vector2(38, 472 - 150);
            ASwingHelper.prepare(m_jpanel);
            this.x = m_appearPos.x;
            this.y = m_appearPos.y;
            finalizeAndShow();
            Global.ui.bottomUI.addChild(this);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.POPULATION_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("bgAsset",  new m_comObject.neighborBar_populationPopup());
            return _loc_1;
        }//end

         protected void  onShow ()
        {
            super.onShow();
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_1;
            return;
        }//end

         public void  show ()
        {
            m_appearPos = new Vector2(36, 472 - 147);
            if (Global.isVisiting())
            {
                m_appearPos.y = m_appearPos.y + 25;
            }
            this.x = m_appearPos.x;
            this.y = m_appearPos.y;
            if (m_jpanel)
            {
                ((NotifyPopupView)m_jpanel).rebuild(NotifyPopupView.STATE_LONG);
                ASwingHelper.prepare(m_jwindow);
                super.show();
                stopDisappearTimer();
            }
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new PopulationPopupView(param1, m_message, m_title, m_type, m_callback, this);
        }//end

         protected void  showTween ()
        {
            Point startSize ;
            Object matParams ;
            Matrix mat ;
            this.m_bShowing = true;
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            startSize = new Point(m_content.width, m_content.height);
            matParams = new Object();
            if (m_centered)
            {
                centerPopup();
            }
            mat = new Matrix();
            mat.translate((-startSize.x) / 2, (-startSize.y) / 2);
            mat.scale(matParams.scale, matParams.scale);
            mat.translate(startSize.x / 2, startSize.y / 2);
            m_content.transform.matrix = mat;
void             TweenLite .to (matParams ,TWEEN_TIME ,{1scale ,Back ease .easeOut , onUpdate ()
            {
                mat = new Matrix();
                mat.translate((-startSize.x) / 2, (-startSize.y) / 2);
                mat.scale(matParams.scale, matParams.scale);
                mat.translate(startSize.x / 2, startSize.y / 2);
                m_content.transform.matrix = mat;
                this.visible = true;
                return;
            }//end
void             , onComplete ()
            {
                m_bShowing = false;
                onShowComplete();
                return;
            }//end
            });
            return;
        }//end

        public static PopulationPopup  getInstance ()
        {
            if (!m_instance)
            {
                m_instance = new PopulationPopup;
            }
            return m_instance;
        }//end

    }



