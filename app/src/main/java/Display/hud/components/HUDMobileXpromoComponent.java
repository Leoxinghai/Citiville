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

import Classes.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;
import Classes.sim.*;

    public class HUDMobileXpromoComponent extends HUDComponent implements IPlayerStateObserver
    {
        private DisplayObject m_icon =null ;
        private AssetPane m_iconHolder ;
        private boolean m_locked =false ;
        private boolean m_hidden =false ;
        private boolean m_created =false ;
        private XML m_xml ;
        private  String FLAG_PREFIX ="xpromo_anim_";

        public  HUDMobileXpromoComponent ()
        {
            Global.player.addObserver(this);
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
            boolean _loc_4 =false ;
            if (this.m_hidden)
            {
                return;
            }
            alpha = 0;
            this.m_locked = false;
            _loc_1 = this.m_xml.@playerRequiredLevel;
            if (_loc_1 > 0 && Global.player.level < _loc_1)
            {
                this.m_locked = true;
            }
            _loc_2 = this.m_xml.@experiment;
            if (_loc_2 != "")
            {
                _loc_4 = Global.experimentManager.getVariant(_loc_2) > 0;
                if (!_loc_4)
                {
                    this.m_locked = true;
                }
            }
            _loc_3 =Global.player.getSeenFlag("mobile_xpromo");
            if (_loc_3)
            {
                this.m_locked = true;
            }
            if (!this.m_locked && !this.m_created)
            {
                this.create();
                this.m_created = true;
            }
            return;
        }//end

         public boolean  isVisible ()
        {
            return this.m_created && !this.m_locked;
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

        private void  create ()
        {
            m_jWindow = new JWindow(this);
            m_jPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.defaultFontNameBold ,18,16777215,true );
            _loc_1.align = TextFormatAlign.RIGHT;
            this.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            m_jPanel.append(ASwingHelper.verticalStrut(15));
            _loc_2 = this.m_xml.@icon;
            _loc_3 =Global.getAssetURL(_loc_2 );
            LoadingManager.load(_loc_3, this.onIconLoaded);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4.append(ASwingHelper.horizontalStrut(0));
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            this.alpha = 0;
            this.visible = false;
            if (!this.m_locked)
            {
                this.visible = true;
                this.alpha = 1;
                Z_TweenLite.to(this, 1, {alpha:2});
                this.addTooltip();
            }
            return;
        }//end

        private String  animSeenFlag ()
        {
            return this.FLAG_PREFIX + this.m_xml.@type;
        }//end

        private void  onIconLoaded (Event event )
        {
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            AutoAnimatedBitmap _loc_8 =null ;
            boolean _loc_9 =false ;
            _loc_2 =(Loader) event.target.loader;
            this.m_icon = _loc_2.content;
            _loc_3 =(Bitmap) this.m_icon;
            if (this.m_xml.@animated == 1 && _loc_3)
            {
                _loc_4 = this.m_xml.@animFrames;
                _loc_5 = this.m_xml.@animWidth;
                _loc_6 = this.m_xml.@animHeight;
                _loc_7 = this.m_xml.@animFPS;
                _loc_8 = new AutoAnimatedBitmap(_loc_3.bitmapData, _loc_4, _loc_5, _loc_6, _loc_7);
                _loc_8.x = 0;
                _loc_8.y = 0;
                _loc_9 = true;
                if (this.m_xml.@animateOnce == 1)
                {
                    _loc_9 = !Global.player.getSeenFlag(this.animSeenFlag);
                    if (_loc_9)
                    {
                        Global.player.setSeenFlag(this.animSeenFlag);
                    }
                }
                if (_loc_9)
                {
                    _loc_8.play();
                }
                else
                {
                    _loc_8.stop();
                }
                this.m_icon = _loc_8;
            }
            addChildAt(this.m_icon, 0);
            m_jPanel.setPreferredSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            m_jPanel.setMinimumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            m_jPanel.setMaximumSize(new IntDimension(this.m_icon.width, this.m_icon.height));
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.useHandCursor = true;
            m_jWindow.buttonMode = true;
            alpha = 0;
            Z_TweenLite.to(this, 2, {alpha:1});
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            String _loc_2 =null ;
            if (!this.m_locked)
            {
                StatsManager.count("xpromo", "hometown", "hud_icon_clicks");
                _loc_2 = this.m_xml.@announcement;
                StartUpDialogManager.displayAnnouncement(_loc_2);
                this.hide();
            }
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


