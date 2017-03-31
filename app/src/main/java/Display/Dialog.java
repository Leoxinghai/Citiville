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
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import Engine.*;
import Engine.Events.*;
import Engine.Managers.*;
import com.greensock.*;
import fl.motion.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import com.xinghai.Debug;

    public class Dialog extends MovieClip
    {
        protected DisplayObject m_content =null ;
        protected boolean m_modal =true ;
        protected boolean m_lightbox =false ;
        protected boolean m_shown =false ;
        protected boolean m_loaded =false ;
        protected boolean m_showOnLoad =false ;
        protected JWindow m_jwindow ;
        protected int m_cursorId =0;
        protected boolean m_centered =true ;
        private int m_zaspLoadHandle =0;
        public static ASwingFactory swing ;
        public static ASwingStyleFactory styles ;
        public static  double TWEEN_TIME =0.25;
        public static  boolean INTERNAL =true ;
        public static  boolean EXTERNAL =false ;
        private static int s_modalCount =0;

        public  Dialog (boolean param1 =true ,boolean param2 =false ,DisplayObject param3 =null )
        {
            if (!swing)
            {
                swing = ASwingFactory.getInstance();
            }
            if (!styles)
            {
                styles = ASwingStyleFactory.getInstance();
            }
            if (param3 != null)
            {
                this.m_content = param3;
            }
            this.m_loaded = param2;
            this.m_modal = param1;
            return;
        }//end

        public boolean  isLockable ()
        {
            return false;
        }//end

        final protected void  onDialogInitialized ()
        {
            if (this.m_content)
            {
            }
            dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            this.m_loaded = true;
            if (this.m_showOnLoad)
            {
                this.m_showOnLoad = false;
                this.show();
            }
            return;
        }//end

        protected boolean  useSystemCursor ()
        {
            return true;
        }//end

        public void  show ()
        {
            if (!Global.ui)
            {
                return;
            }
            Debug.debug4("Dialog.show");
            //hide();

            Global.ui.turnOffHighlightedObject();
            if (this.m_loaded)
            {
                this.visible = true;
                this.showTween();
                if (GlobalEngine.zaspManager)
                {
                    GlobalEngine.zaspManager.zaspPopup();
                    if (this.m_zaspLoadHandle > 0)
                    {
                        GlobalEngine.zaspManager.zaspWaitEnd(this.m_zaspLoadHandle);
                        this.m_zaspLoadHandle = 0;
                    }
                }
                this.onShow();
            }
            else
            {
                if (GlobalEngine.zaspManager && !this.m_zaspLoadHandle)
                {
                    this.m_zaspLoadHandle = GlobalEngine.zaspManager.zaspWaitStart("DIALOG", getQualifiedClassName(this));
                }
                this.m_showOnLoad = true;
            }
            return;
        }//end

        public void  hide ()
        {
            this.hideTween(this.onHideComplete);
            this.onHide();
            return;
        }//end

        public void  close ()
        {
            DisplayObject dispObj ;
            Global.ui.swapPopupAd("genericAd", false);
            dispObj = (DisplayObject)this; // new DisplayObject();
            this .hideTween (void  ()
            {
                if (dispObj.parent)
                {
                    dispObj.parent.removeChild(dispObj);
                }
                dispatchEvent(new Event(Event.CLOSE));
                onHideComplete();
                return;
            }//end
            );
            this.onHide();
            return;
        }//end

        protected void  onShow ()
        {
            if (this.m_shown == false)
            {
                if (this.m_modal)
                {
                    if (s_modalCount == 0)
                    {
                        InputManager.disableInput();
                        Global.ui.mouseEnabled = false;
                        Global.ui.mouseChildren = false;
                    }

                    s_modalCount++;
                }
                this.handleShowLightbox();
                if (this.useSystemCursor())
                {
                    this.m_cursorId = UI.setCursor(null);
                }

                this.mouseChildren = true;
                this.mouseEnabled = true;
                this.m_shown = true;
                Global.stage.addEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
                Global.stage.addEventListener(Event.RESIZE, this.onResize);
            }
            else
            {
                this.m_showOnLoad = true;
            }
            if (!Global.guide.isActive())
            {
                Sounds.play("dialogSpawn");
            }
            return;
        }//end

        protected void  handleShowLightbox ()
        {
            if (this.m_lightbox)
            {
                Global.ui.displayLightbox(true);
            }
            return;
        }//end

        protected void  onHide ()
        {
            if (this.m_shown == true)
            {
                if (this.m_modal)
                {
                    _loc_2 = s_modalCount-1;
                    s_modalCount = _loc_2;
                }
                if (s_modalCount == 0)
                {
                    InputManager.enableInput();
                    Global.ui.mouseEnabled = true;
                    Global.ui.mouseChildren = true;
                }
                if (this.m_lightbox)
                {
                    Global.ui.displayLightbox(false);
                }
                this.m_shown = false;
                boolean _loc_1 =false ;
                this.mouseChildren = false;
                this.mouseEnabled = _loc_1;
                if (this.useSystemCursor())
                {
                    UI.removeCursor(this.m_cursorId);
                }
                Global.stage.removeEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
                Global.stage.removeEventListener(Event.RESIZE, this.onResize);
            }
            this.m_showOnLoad = false;
            return;
        }//end

        protected void  showTween ()
        {
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;

            this.m_content.scaleY = 0;
            this.m_content.scaleX = 0;
            TweenLite.to(this.m_content, TWEEN_TIME, {scaleX:1, scaleY:1, ease:Back.easeOut, onUpdate:this.showTweenOnUpdate, onComplete:this.onShowComplete});
            return;
        }//end

        protected void  hideTween (Function param1 )
        {
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            if (this.m_content)
            {
                TweenLite.to(this.m_content, TWEEN_TIME, {scaleX:0, scaleY:0, ease:Back.easeIn, onUpdate:this.hideTweenOnUpdate, onComplete:param1});
            }
            else if (param1 == null)
            {
                param1();
            }
            Sounds.play("dialogClose");
            return;
        }//end

        protected void  showTweenOnUpdate ()
        {
            if (this.m_centered)
            {
                this.centerPopup();
            }
            return;
        }//end

        protected void  hideTweenOnUpdate ()
        {
            if (this.m_centered)
            {
                this.centerPopup();
            }
            return;
        }//end

        protected void  onHideComplete ()
        {
            this.visible = false;
            return;
        }//end

        public void  onShowComplete ()
        {
            boolean _loc_1 =true ;
            this.mouseChildren = true;
            this.mouseEnabled = true;
            return;
        }//end

        protected void  onFullScreen (FullScreenEvent event )
        {
            this.centerPopup();
            return;
        }//end

        protected void  onResize (Event event )
        {
            this.centerPopup();
            return;
        }//end

        public Point  getDialogOffset ()
        {
            return new Point(0, 0);
        }//end

        public void  centerPopup ()
        {
            Point _loc_1 =null ;
            double _loc_2 =0;
            double _loc_3 =0;
            if (this.m_jwindow)
            {
                this.m_centered = true;
                _loc_1 = this.getDialogOffset();
                _loc_2 = this.m_jwindow.getWidth() * this.m_content.scaleX;
                _loc_3 = this.m_jwindow.getHeight() * this.m_content.scaleY;
                x = (Global.ui.screenWidth * Global.ui.screenScale - _loc_2) * 0.5 + _loc_1.x;
                y = (Global.ui.screenHeight - _loc_3) * 0.5 + _loc_1.y;
                scaleX = Global.ui.screenScale;
                scaleY = Global.ui.screenScale;
            }
            return;
        }//end

        public Rectangle  getVisibleBounds (DisplayObject param1 )
        {
            BitmapData _loc_2 =new BitmapData ((Math.ceil(param1.width )+1),(Math.ceil(param1.height )+1),true ,0);
            _loc_2.draw(param1);
            Rectangle _loc_3 =_loc_2.getColorBoundsRect(4294967295,0,false );
            _loc_2.dispose();
            return _loc_3;
        }//end

        public Point  setIsoPosition (int param1 ,int param2 )
        {
            this.m_centered = false;
            _loc_3 = IsoMath.tilePosToPixelPos(param1,param2);
            _loc_3 = IsoMath.viewportToStage(_loc_3);
            this.x = _loc_3.x;
            this.y = _loc_3.y;
            return _loc_3;
        }//end

        public void  setStagePosition (int param1 ,int param2 )
        {
            this.m_centered = false;
            this.x = param1;
            this.y = param2;
            return;
        }//end

        public boolean  isModal ()
        {
            return this.m_modal;
        }//end

        public boolean  isShown ()
        {
            return this.m_shown;
        }//end

    }




