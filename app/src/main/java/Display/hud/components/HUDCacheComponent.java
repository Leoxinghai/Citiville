package Display.hud.components;

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

import Cache.Classes.UI.*;
import Cache.Interfaces.*;
import Cache.Util.*;
import Classes.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import GameMode.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;


    public class HUDCacheComponent extends HUDComponent
    {
        private DisplayObject m_icon =null ;
        private AssetPane m_iconHolder ;
        private boolean m_locked =false ;
        private boolean m_hidden =false ;
        private boolean m_created =false ;
        private XML m_xml ;
        private Loader m_zCacheDialogBkgLoader =null ;
        private DisplayObject m_zCacheDialogBkgButton =null ;
        private ZCacheUI m_zCacheUI ;
        private JTextField cacheTitle ;
        private Timer loadTimer ;
        private Timer delayTimer ;

        public  HUDCacheComponent ()
        {
            this.name = "HUDCacheComponent";
            this.loadTimer = new Timer(60000, 1);
            this.loadTimer.addEventListener(TimerEvent.TIMER_COMPLETE, this.onTimerCompleteShow, false, 0, true);
            return;
        }//end

         public void  initWithXML (XML param1 )
        {
            this.m_xml = param1;
            this.reset();
            return;
        }//end

         public void  reset ()
        {
            boolean _loc_3 =false ;
            if (this.m_hidden)
            {
                return;
            }
            this.m_locked = false;
            _loc_1 = this.m_xml.@experiment;
            if (_loc_1 != "")
            {
                _loc_3 = Global.experimentManager.getVariant(_loc_1) > 0;
                if (!_loc_3)
                {
                    this.m_locked = true;
                }
            }
            _loc_2 = this.m_xml.@langcode;
            if (_loc_2 != "")
            {
                if (_loc_2 != Global.localizer.langCode)
                {
                    this.m_locked = true;
                }
            }
            if (!this.m_locked && !this.m_created && Global.player.countFlags() > 0)
            {
                this.loadTimer.stop();
                this.loadTimer.reset();
                this.loadTimer.start();
                this.m_created = true;
            }
            return;
        }//end

        protected void  onTimerCompleteShow (TimerEvent event )
        {
            this.create();
            this.loadTimer.reset();
            this.loadTimer.removeEventListener(TimerEvent.TIMER_COMPLETE, this.onTimerCompleteShow);
            return;
        }//end

         public boolean  isVisible ()
        {
            return this.m_created && !this.m_locked;
        }//end

        private void  create ()
        {
            int _loc_1 =0;
            boolean _loc_2 =false ;
            if (Global.player.countFlags() > 0 && Global.player.level > 5 && !Global.player.cacheButtonShown)
            {
                m_jWindow = new JWindow(this);
                m_jPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                this.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
                _loc_1 = Global.player.getLastActivationTime("shownCacheIngameComponent");
                _loc_2 = Global.player.getSeenFlag("cache_ingame");
                if (_loc_2 != 1)
                {
                    Global.player.setLastActivationTime("shownCacheIngameComponent", GlobalEngine.getTimer() / 1000);
                    Global.player.setSeenFlag("cache_ingame");
                    this.loadIcon();
                }
                else if (_loc_1 > 0 && !this.stale(_loc_1, 14))
                {
                    this.loadIcon();
                }
                Global.player.cacheButtonShown = true;
                this.delayTimer = new Timer(60000, 1);
                this.delayTimer.addEventListener(TimerEvent.TIMER_COMPLETE, this.onTimerCompleteHide, false, 0, true);
                this.delayTimer.start();
            }
            return;
        }//end

        private void  loadIcon ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            if (!this.m_locked)
            {
                _loc_1 = this.m_xml.@icon;
                _loc_2 = Global.getAssetURL(_loc_1);
                LoadingManager.load(_loc_2, this.onIconLoaded);
                m_jWindow.setContentPane(m_jPanel);
                ASwingHelper.prepare(m_jWindow);
                m_jWindow.cacheAsBitmap = true;
                m_jWindow.show();
                this.visible = true;
                StatsManager.count("zcache", "hud_icon_home", "shown");
                this.alpha = 1;
                Z_TweenLite.to(this, 1, {alpha:2});
                this.addTooltip();
            }
            return;
        }//end

        protected void  onTimerCompleteHide (TimerEvent event )
        {
            this.hide();
            return;
        }//end

        protected void  hide ()
        {
            removeEventListener(MouseEvent.CLICK, this.onMouseClick, false);
            Z_TweenLite.to(this, 2, {alpha:0, onComplete:this.doHide});
            this.m_hidden = true;
            this.m_locked = true;
            return;
        }//end

        protected void  doHide ()
        {
            if (m_jWindow)
            {
                m_jWindow.visible = false;
                m_jWindow.buttonMode = false;
            }
            this.m_locked = true;
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            Loader _loc_3 =null ;
            Bitmap _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            AutoAnimatedBitmap _loc_9 =null ;
            boolean _loc_10 =false ;
            _loc_2 = GlobalZCache.instance;
            if (_loc_2)
            {
                if (!_loc_2.allowed && Util.isZCacheCompatible())
                {
                    _loc_3 =(Loader) event.target.loader;
                    this.m_icon = _loc_3.content;
                    _loc_4 =(Bitmap) this.m_icon;
                    if (this.m_xml.@animated == 1 && _loc_4)
                    {
                        _loc_5 = this.m_xml.@animFrames;
                        _loc_6 = this.m_xml.@animWidth;
                        _loc_7 = this.m_xml.@animHeight;
                        _loc_8 = this.m_xml.@animFPS;
                        _loc_9 = new AutoAnimatedBitmap(_loc_4.bitmapData, _loc_5, _loc_6, _loc_7, _loc_8);
                        _loc_9.x = 0;
                        _loc_9.y = 0;
                        _loc_10 = true;
                        if (_loc_10)
                        {
                            _loc_9.play();
                        }
                        else
                        {
                            _loc_9.stop();
                        }
                        this.m_icon = _loc_9;
                    }
                    addChildAt(this.m_icon, 0);
                    m_jPanel.setPreferredSize(new IntDimension(this.m_icon.width, this.m_icon.height));
                    ASwingHelper.prepare(m_jWindow);
                    m_jWindow.useHandCursor = true;
                    m_jWindow.buttonMode = true;
                    m_jWindow.addEventListener(MouseEvent.MOUSE_DOWN, this.blockCityName, false, 0, true);
                    m_jWindow.addEventListener(MouseEvent.MOUSE_MOVE, this.blockCityName, false, 0, true);
                    alpha = 0;
                    Z_TweenLite.to(this, 2, {alpha:1});
                }
            }
            return;
        }//end

        protected void  blockCityName (MouseEvent event )
        {
            GMDefault _loc_3 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            if (_loc_2 instanceof GMDefault)
            {
                _loc_3 =(GMDefault) _loc_2;
                _loc_3.turnOffObject();
            }
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            _loc_2 =Global.player.level ;
            if (!this.m_locked)
            {
                this.m_zCacheDialogBkgLoader = LoadingManager.load(Global.getAssetURL("assets/preloader/adobeStorageLarge_bg2.png"), this.onPermissionButtonLoaded);
            }
            StatsManager.count("zcache", "hud_icon_home", "clicked");
            this.hide();
            return;
        }//end

        private void  onPermissionButtonLoaded (Event event )
        {
            event = event;
            this.m_zCacheDialogBkgButton = this.m_zCacheDialogBkgLoader.content;
            if (GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN)
            {
                this.m_zCacheDialogBkgButton.x = 150;
                this.m_zCacheDialogBkgButton.y = 445;
            }
            else
            {
                this.m_zCacheDialogBkgButton.x = 150;
                this.m_zCacheDialogBkgButton.y = 145;
            }
            Global.ui.addChild(this.m_zCacheDialogBkgButton);
            this.cacheTitle = ASwingHelper.makeTextField(ZLoc.t("Main", "LoadCityFaster"), EmbeddedArt.titleFont, 21, EmbeddedArt.HIGHLIGHT_ORANGE, 0);
            this.cacheTitle.x = this.m_zCacheDialogBkgButton.x + 80;
            this.cacheTitle.y = this.m_zCacheDialogBkgButton.y + 45;
            Global.ui.addChild(this.cacheTitle);
            StatsManager.count("zcache", "flash_permission", "shown");
            zCache = GlobalZCache.instance;
            if (zCache)
            {
                    zCache .onSuccess =void  ()
            {
                StatsManager.count("zcache", "flash_permission", "accepted");
                m_zCacheDialogBkgButton.visible = false;
                cacheTitle.visible = false;
                return;
            }//end
            ;
                    zCache .onFailure =void  ()
            {
                StatsManager.count("zcache", "flash_permission", "denied");
                m_zCacheDialogBkgButton.visible = false;
                cacheTitle.visible = false;
                return;
            }//end
            ;
                GlobalZCache .instance .promptForStorage (void  ()
            {
                StatsManager.count("zcache", "flash_permission", "accepted");
                m_zCacheDialogBkgButton.visible = false;
                cacheTitle.visible = false;
                return;
            }//end
            ,void  ()
            {
                StatsManager.count("zcache", "flash_permission", "denied");
                m_zCacheDialogBkgButton.visible = false;
                cacheTitle.visible = false;
                return;
            }//end
            );
            }
            return;
        }//end

        private void  hideDlgs (boolean param1 )
        {
            this.m_zCacheDialogBkgButton.visible = false;
            this.cacheTitle.visible = false;
            return;
        }//end

        private void  addTooltip ()
        {
            String toolTip ;
            toolTip = this.m_xml.@toolTip;
            if (toolTip == "")
            {
                return;
            }
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Dialogs", toolTip);
            }//end
            );
            m_toolTip.attachToolTip(m_jPanel);
            m_toolTip.hideCursor = true;
            return;
        }//end

        private void  onAddedToStage (Event event )
        {
            this.hideAfterXSecs(30);
            return;
        }//end

        private void  hideAfterXSecs (int param1 )
        {
            param1 = param1 * 1000;
            Timer _loc_2 =new Timer(param1 ,1);
            _loc_2.addEventListener(TimerEvent.TIMER, this.onTimerCompleteHide);
            _loc_2.start();
            return;
        }//end

        private boolean  stale (int param1 ,int param2 )
        {
            return GlobalEngine.getTimer() / 1000 - param1 > 60 * 60 * 24 * param2 ? (true) : (false);
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

    }



