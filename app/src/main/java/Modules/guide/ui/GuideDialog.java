package Modules.guide.ui;

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

import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;

    public class GuideDialog extends GenericPictureDialog
    {
        private MovieClip m_asset ;
        private String m_buttonName ;
        private double m_tweenTime =0.25;
        private int m_position =0;
        private double m_offsetX =0;
        private double m_offsetY =0;
        protected double m_avatarOffsetX =0;
        protected double m_avatarOffsetY =0;
        protected DisplayObject m_advisor ;
        protected JWindow m_avatarWindow ;
        protected int m_assetsLoaded =0;
        protected String m_avatarURL ;
        protected Loader m_avatarContentLoader ;
        protected Loader m_bgContentLoader ;
        protected String m_avatarLocation ;
        public static  int POS_CENTER =0;
        public static  int POS_NW =1;
        public static  int POS_NE =2;
        public static  int POS_SW =3;
        public static  int POS_SE =4;
        public static  int POS_ABS =5;

        public  GuideDialog (String param1 ,String param2 ,String param3 ,boolean param4 =false ,int param5 =0,double param6 =0,double param7 =0,String param8 ="",double param9 =0,double param10 =0,String param11 ="right")
        {
            this.m_avatarURL = param8;
            this.m_buttonName = param3;
            this.m_position = param5;
            this.m_offsetX = param6;
            this.m_offsetY = param7;
            this.m_avatarOffsetX = param9;
            this.m_avatarOffsetY = param10;
            this.m_avatarLocation = param11;
            super(param1, "", 0, null, param2, "", param4);
            return;
        }//end

         public boolean  isLockable ()
        {
            return false;
        }//end

        public void  setTweenTime (double param1 )
        {
            this.m_tweenTime = param1;
            return;
        }//end

        protected void  onStage ()
        {
            this.addListeners();
            return;
        }//end

         protected void  loadAssets ()
        {
            _loc_1 =Global.getAssetURL(this.m_avatarURL );
            this.m_avatarContentLoader = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_2 =Global.getAssetURL("assets/dialogs/TutorialAssets.swf");
            this.m_bgContentLoader = LoadingManager.load(_loc_2, this.onAssetsLoaded);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_assetsLoaded++;
            if (this.m_assetsLoaded < 2)
            {
                return;
            }
            Dictionary _loc_2 =new Dictionary ();
            _loc_3 = this.m_bgContentLoader.content ;
            _loc_2.get("speechBubble") = this.m_avatarLocation == GuideDialogView.ADVISOR_LOCATION_RIGHT ? (new _loc_3.speechBubbleRight()) : (new _loc_3.speechBubbleLeft());
            this.m_advisor = this.m_avatarContentLoader.content;
            m_jpanel = new GuideDialogView(_loc_2, m_message, m_title, m_type, m_callback, this.m_avatarLocation);
            this.finalizeAndShow();
            return;
        }//end

         protected void  finalizeAndShow ()
        {
            Sprite _loc_1 =new Sprite ();
            this.addChildAt(_loc_1, 0);
            this.m_avatarWindow = new JWindow(_loc_1);
            AssetPane _loc_2 =new AssetPane(this.m_advisor );
            this.m_avatarWindow.setContentPane(_loc_2);
            ASwingHelper.prepare(this.m_avatarWindow);
            this.m_avatarWindow.show();
            this.m_avatarWindow.setX(this.m_avatarOffsetX);
            this.m_avatarWindow.setY(this.m_avatarOffsetY);
            ASwingHelper.prepare(m_jpanel);
            m_jwindow.setContentPane(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            m_jwindow.setX(136);
            m_jwindow.show();
            setupDialogStatsTracking();
            onDialogInitialized();
            return;
        }//end

        protected void  addListeners ()
        {
            return;
        }//end

        protected void  removeListeners ()
        {
            return;
        }//end

         protected void  hideTween (Function param1 )
        {
            this.mouseEnabled = false;
            this.mouseChildren = false;
            if (param1 != null)
            {
                TweenLite.to(m_content, this.m_tweenTime, {alpha:0, onComplete:param1});
            }
            else
            {
                TweenLite.to(m_content, this.m_tweenTime, {alpha:0});
            }
            return;
        }//end

         protected void  showTween ()
        {
            this.mouseEnabled = false;
            this.mouseChildren = false;
            _loc_1 = Math.min(Global.ui.width ,Global.ui.screenWidth );
            _loc_2 = Math.min(Global.ui.height ,Global.ui.screenHeight );
            switch(this.m_position)
            {
                case POS_CENTER:
                {
                    this.centerPopup();
                    break;
                }
                case POS_NW:
                {
                    this.x = this.m_offsetX;
                    this.y = this.m_offsetY;
                    break;
                }
                case POS_NE:
                {
                    this.x = _loc_1 - this.width - this.m_offsetX;
                    this.y = this.m_offsetY;
                    break;
                }
                case POS_SW:
                {
                    this.x = this.m_offsetX;
                    this.y = _loc_2 - this.height - this.m_offsetY;
                    break;
                }
                case POS_SE:
                {
                    this.x = _loc_1 - this.width - this.m_offsetX;
                    this.y = _loc_2 - this.height - this.m_offsetY;
                    break;
                }
                case POS_ABS:
                {
                    this.x = this.m_offsetX;
                    this.y = this.m_offsetY;
                    break;
                }
                default:
                {
                    break;
                }
            }
            Point _loc_3 =new Point(m_content.width ,m_content.height );
            Matrix _loc_4 =new Matrix ();
            _loc_4.scale(1, 1);
            m_content.transform.matrix = _loc_4;
            TweenLite.from(m_content, this.m_tweenTime, {alpha:0, onComplete:onShowComplete});
            return;
        }//end

         public void  centerPopup ()
        {
            super.centerPopup();
            this.y = this.y + 20;
            return;
        }//end

        private void  onButtonClick (MouseEvent event )
        {
            close();
            return;
        }//end

    }



