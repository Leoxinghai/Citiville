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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDDailyDripComponent extends HUDComponent implements IPlayerStateObserver
    {
        private DisplayObject m_icon =null ;
        private AssetPane m_iconHolder ;
        private boolean m_locked =false ;
        private boolean m_hidden =false ;
        private boolean m_created =false ;

        public  HUDDailyDripComponent ()
        {
            Global.player.addObserver(this);
            this.reset();
            return;
        }//end

         public void  reset ()
        {
            String _loc_3 =null ;
            if (this.m_hidden)
            {
                return;
            }
            alpha = 0;
            this.m_locked = false;
            int _loc_1 =0;
            if (Global.player.dailyRewards)
            {
                for(int i0 = 0; i0 < Global.player.dailyRewards.size(); i0++) 
                {
                	_loc_3 = Global.player.dailyRewards.get(i0);

                    _loc_1++;
                }
            }
            if (_loc_1 > 0)
            {
                this.m_locked = true;
            }
            if (Global.player.level < 7)
            {
                this.m_locked = true;
            }
            _loc_2 = ExperimentDefinitions.DAILY_EMAIL==Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DAILY_DRIP_EMAIL);
            if (!_loc_2)
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
            this.m_locked = true;
            this.m_hidden = true;
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
            this.m_hidden = true;
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
            LoadingManager.load(Global.getAssetURL("assets/hud/icon_emailReward.png"), this.onIconLoaded);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.horizontalStrut(0));
            m_jWindow.setContentPane(m_jPanel);
            ASwingHelper.prepare(m_jWindow);
            m_jWindow.cacheAsBitmap = true;
            m_jWindow.show();
            this.alpha = 0;
            this.visible = false;
            if (!this.m_locked)
            {
                this.visible = true;
                this.addTooltip();
            }
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            this.m_icon = _loc_2.content;
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
            DailyDripDialog _loc_2 =null ;
            if (!this.m_locked)
            {
                StatsManager.sample(100, StatsCounterType.DAILY_BONUS, "email", "email_hud_clicked");
                _loc_2 = new DailyDripDialog();
                UI.displayPopup(_loc_2, true, "DailyDripDialog", true);
                this.hide();
            }
            return;
        }//end

        private void  addTooltip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Dialogs", "DripHUD_tooltip");
            }//end
            );
            m_toolTip.attachToolTip(m_jPanel);
            m_toolTip.hideCursor = true;
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            _loc_2 = ExperimentDefinitions.DAILY_EMAIL==Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DAILY_DRIP_EMAIL);
            if (!_loc_2)
            {
                return;
            }
            if (!_loc_2)
            {
                this.m_locked = true;
            }
            if (param1 == 7)
            {
                this.m_locked = false;
                if (this.m_created == false)
                {
                    this.create();
                    this.m_created = true;
                }
                this.visible = true;
                Z_TweenLite.to(this, 1, {alpha:1});
                this.addTooltip();
            }
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



