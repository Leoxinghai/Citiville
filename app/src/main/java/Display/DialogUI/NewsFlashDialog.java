package Display.DialogUI;

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

import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class NewsFlashDialog extends GenericDialog
    {
        protected String m_imageURL ;
        protected int m_imageCount =0;
        protected Object m_itemObject =null ;
        protected String m_subhead ="";
public static String m_NewsTitle ="";
public static String m_NewsSubhead ="";
public static String m_NewsMessage ="";

        public  NewsFlashDialog (String param1 ,String param2 ="",String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,boolean param7 =true ,int param8 =0)
        {
            this.m_subhead = param3;
            this.m_imageURL = param4;
            _loc_9 = GenericDialogView.TYPE_SHARECANCEL;
            super(param1, param2, _loc_9, param6, param2, "", param7);
            m_NewsTitle = param2;
            m_NewsSubhead = this.m_subhead;
            m_NewsMessage = param1;
            return;
        }//end

         protected void  loadAssets ()
        {
            this.m_itemObject = new Object();
            _loc_1 = Global.getAssetURL(this.m_imageURL);
            this.m_itemObject.image = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_2 = Global.getAssetURL("assets/newsFlash/newspaper_bg.png");
            this.m_itemObject.bg = LoadingManager.load(_loc_2, this.onAssetsLoaded);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
             this.m_imageCount++;
            if (++this.m_imageCount < 2)
            {
                return;
            }
            Dictionary _loc_2 =new Dictionary(true );
            _loc_2.put("bg",  (Bitmap)this.m_itemObject.bg.content);
            _loc_2.put("imageURL",  (Bitmap)this.m_itemObject.image.content);
            m_jpanel = new NewsFlashDialogView(_loc_2, m_message, m_title, this.m_subhead, m_type, m_callback);
            this.finalizeAndShow();
            return;
        }//end

         protected void  finalizeAndShow ()
        {
            ASwingHelper.prepare(m_jpanel);
            m_jwindow.setContentPane(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            _loc_1 = m_jwindow.getWidth();
            _loc_2 = m_jwindow.getHeight();
            m_jwindow.setX((-_loc_1) / 2);
            m_jwindow.setY((-_loc_2) / 2);
            m_jwindow.show();
            m_holder.width = _loc_1;
            m_holder.height = _loc_2;
            setupDialogStatsTracking();
            onDialogInitialized();
            return;
        }//end

         public Point  getDialogOffset ()
        {
            return new Point(m_jwindow.getWidth() / 2, m_jwindow.getHeight() / 2);
        }//end

         protected void  hideTween (Function param1 )
        {
            Object matParams ;
            Point startSize ;
            callback = param1;
            boolean _loc_3 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_3;
            if (m_content == null)
            {
                if (callback != null)
                {
                    param1();
                }
            }
            else
            {
                //matParams =;
                startSize = new Point(0, 0);
void                 TweenLite .to (matParams ,TWEEN_TIME ,{0scale ,Back ease .easeIn , onUpdate ()
            {
                Matrix _loc_1 =new Matrix ();
                _loc_1.translate((-startSize.x) / 2, (-startSize.y) / 2);
                _loc_1.scale(matParams.scale, matParams.scale);
                _loc_1.translate(startSize.x / 2, startSize.y / 2);
                m_content.transform.matrix = _loc_1;
                return;
            }//end
            , onComplete:callback});
            }
            return;
        }//end

         protected void  showTween ()
        {
            this.mouseEnabled = false;
            this.mouseChildren = false;
            if (m_centered)
            {
                centerPopup();
            }
            Point _loc_1 =new Point(m_content.width ,m_content.height );
            Matrix _loc_2 =new Matrix ();
            _loc_2.scale(0.25, 0.25);
            m_content.transform.matrix = _loc_2;
            m_content.alpha = 0;
            TweenLite.to(m_content, 0.5, {alpha:1, scaleX:1, scaleY:1, rotation:720, onComplete:onShowComplete});
            return;
        }//end

        public static String  GetLastNewsTitle ()
        {
            return m_NewsTitle;
        }//end

        public static String  GetLastNewsSubhead ()
        {
            return m_NewsSubhead;
        }//end

        public static String  GetLastNewsMessage ()
        {
            return m_NewsMessage;
        }//end

    }




