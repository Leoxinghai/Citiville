package Modules.quest.Display;

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
import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
import com.greensock.easing.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.utils.*;
import org.aswing.*;

    public class QuestIndicatorSprite extends Sprite
    {
        private  int EXPIRE =5000;
        private  double BOUNCE_DURATION =0.5;
        private  int BOUNCE_DIST =15;
        private  int BOUNCE_START_X =70;
        private  int BOUNCE_Y =35;
        private  int ICON_RIGHT_MARGIN =10;
        private String m_name ;
        private String m_type ;
        private boolean m_persist ;
        private Timer m_timer ;
        protected DisplayObject m_bgAsset ;
        protected JWindow m_jw ;
        protected JPanel m_mainPanel ;
        protected TimelineLite m_bouncyTween ;
        protected DisplayObject m_icon ;
        private Object m_optionalLocale ;

        public  QuestIndicatorSprite (String param1 ,boolean param2 ,String param3 ,Object param4 =null )
        {
            this.m_name = param3;
            this.m_type = param1;
            this.m_persist = param2;
            this.m_optionalLocale = param4;
            this.m_icon = null;
            this.init();
            this.doBounce();
            this.startExpireTimer();
            return;
        }//end

        public Object  definition ()
        {
            return {name:this.m_name, type:this.m_type, persist:this.m_persist, optionalLocale:this.m_optionalLocale};
        }//end

        protected void  init ()
        {
            this.m_jw = new JWindow(this);
            this.m_mainPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_bgAsset =(DisplayObject) new EmbeddedArt.questNotifications_bg();
            this.m_mainPanel.setBackgroundDecorator(new AssetBackground(this.m_bgAsset));
            this.m_mainPanel.setPreferredHeight(this.m_bgAsset.height);
            this.m_mainPanel.setMinimumHeight(this.m_bgAsset.height);
            this.m_mainPanel.setMaximumHeight(this.m_bgAsset.height);
            _loc_1 =Global.gameSettings().getIconXMLByName("questNotification_"+this.m_type );
            if (_loc_1)
            {
                LoadingManager.load(Global.getAssetURL(_loc_1.image.get(0).asset.@url), this.onIconLoaded);
            }
            else
            {
                this.prepareWindow();
            }
            this.x = this.BOUNCE_START_X;
            this.y = this.BOUNCE_Y - this.m_bgAsset.height / 2;
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            this.m_icon =(DisplayObject) _loc_2;
            this.prepareWindow();
            return;
        }//end

        protected void  prepareWindow ()
        {
            this.m_mainPanel.appendAll(ASwingHelper.verticalStrut(2), this.makeTextPanel());
            this.m_jw.setContentPane(this.m_mainPanel);
            ASwingHelper.prepare(this.m_mainPanel);
            ASwingHelper.prepare(this.m_jw);
            ASwingHelper.prepare(this);
            this.m_jw.show();
            if (this.m_icon)
            {
                this.addChild(this.m_icon);
                this.m_icon.x = this.m_jw.width - this.m_icon.width - this.ICON_RIGHT_MARGIN;
                this.m_icon.y = (-(this.m_icon.height - this.m_jw.height)) / 2;
            }
            return;
        }//end

        protected JPanel  makeTextPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_2 = ZLoc.t("Quest","Notification_"+this.m_type ,this.m_optionalLocale );
            _loc_3 = TextFieldUtil.getLocaleFontSize(24,18,null);
            _loc_4 = ASwingHelper.makeLabel(_loc_2 ,EmbeddedArt.titleFont ,_loc_3 ,EmbeddedArt.whiteTextColor );
            _loc_4.setTextFilters(.get(new DropShadowFilter(4, 45, EmbeddedArt.blackTextColor, 0.4, 2, 2)));
            if (Global.localizer.langCode != "en")
            {
                _loc_1.appendAll(ASwingHelper.verticalStrut(35));
                _loc_1.appendAll(ASwingHelper.horizontalStrut(20), _loc_4, ASwingHelper.horizontalStrut(10));
            }
            else
            {
                _loc_1.appendAll(ASwingHelper.horizontalStrut(35), _loc_4, ASwingHelper.horizontalStrut(20));
            }
            if (this.m_icon)
            {
                _loc_1.append(ASwingHelper.horizontalStrut(this.m_icon.width));
            }
            return _loc_1;
        }//end

        protected void  startExpireTimer ()
        {
            if (!this.m_persist)
            {
                this.m_timer = new Timer(this.EXPIRE, 0);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.expireTimeReached);
                this.m_timer.start();
            }
            return;
        }//end

        public void  remove ()
        {
            this.cleanUp(false);
            TweenLite .to (this ,0.5,{0alpha ,Curry onComplete .curry (void  (QuestIndicatorSprite param1 )
            {
                removeDisplayObject();
                Global.hud.cleanUpBanner(param1);
                return;
            }//end
            , this)});
            return;
        }//end

        public void  cleanUp (boolean param1 =true )
        {
            if (this.m_timer)
            {
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.expireTimeReached);
            }
            if (this.m_bouncyTween)
            {
                this.m_bouncyTween.clear();
                this.m_bouncyTween.kill();
                this.m_bouncyTween = null;
            }
            TweenLite.killTweensOf(this);
            if (param1 !=null)
            {
                this.removeDisplayObject();
            }
            return;
        }//end

        private void  removeDisplayObject ()
        {
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            return;
        }//end

        protected void  expireTimeReached (TimerEvent event )
        {
            Global.hud.removeBanner(this.m_name);
            return;
        }//end

        protected void  doBounce (...args )
        {
            if (this.m_bouncyTween != null)
            {
                this.m_bouncyTween.clear();
                this.m_bouncyTween.kill();
                this.m_bouncyTween = null;
            }
            if (Global.world.defCon > GameWorld.DEFCON_LEVEL1)
            {
                return;
            }
            this.m_bouncyTween = new TimelineLite({onComplete:this.doBounce});
            this.m_bouncyTween.appendMultiple(.get(new Z_TweenLite(this, this.BOUNCE_DURATION, {x:this.BOUNCE_START_X + this.BOUNCE_DIST, ease:Quad.easeInOut}), new Z_TweenLite(this, this.BOUNCE_DURATION, {x:this.BOUNCE_START_X, ease:Quad.easeInOut})), 0, TweenAlign.SEQUENCE);
            this.m_bouncyTween.gotoAndPlay(0);
            return;
        }//end

        public boolean  persists ()
        {
            return this.m_persist;
        }//end

    }



