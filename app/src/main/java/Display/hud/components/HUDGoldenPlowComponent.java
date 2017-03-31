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
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Init.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDGoldenPlowComponent extends HUDXPromoComponent
    {
        private Date m_timeout ;
        private JTextField m_timerText ;
        private double m_lastUpdateTime ;

        public  HUDGoldenPlowComponent ()
        {
            return;
        }//end

         public void  initWithXML (XML param1 )
        {
            String _loc_2 =null ;
            if (param1.timer != null && param1.timer.length() > 0)
            {
                _loc_2 = String(param1.timer.get(0));
                if (_loc_2 != null && _loc_2 != "")
                {
                    this.m_timeout = TimestampEvents.parseDateFromTimestampString(_loc_2);
                }
            }
            super.initWithXML(param1);
            return;
        }//end

        protected int  timeLeftSeconds ()
        {
            _loc_1 = GameSettingsInit.getCurrentTime();
            Date _loc_2 =new Date ();
            _loc_2.setTime(_loc_1);
            _loc_3 = this.m_timeout.getTime ()-_loc_1 ;
            if (_loc_3 < 0)
            {
                _loc_3 = 0;
            }
            return int(_loc_3 / 1000);
        }//end

         protected boolean  lockedCheck ()
        {
            return super.lockedCheck() || this.timeLeftSeconds <= 0;
        }//end

         protected void  create ()
        {
            super.create();
            GlobalEngine.stage.addEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            return;
        }//end

         public void  reset ()
        {
            GlobalEngine.stage.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            super.reset();
            return;
        }//end

        private void  onEnterFrame (...args )
        {
            int argsvalue =GlobalEngine.currentTime ;
            if (argsvalue != this.m_lastUpdateTime)
            {
                this.m_lastUpdateTime = argsvalue;
                this.updateTimer();
            }
            return;
        }//end

         protected void  onIconLoaded (Event event )
        {
            super.onIconLoaded(event);
            TextFormat _loc_2 =new TextFormat(EmbeddedArt.defaultFontNameBold ,10,16777215,true );
            _loc_2.align = TextFormatAlign.CENTER;
            this.m_timerText = ASwingHelper.makeTextField(CardUtil.formatTimeHours(0), EmbeddedArt.defaultFontNameBold, 12, 16777215, 0, _loc_2);
            this.m_timerText.width = m_icon.width;
            this.m_timerText.filters = .get(new GlowFilter(EmbeddedArt.BORDER_MAIN_COLOR, 1, 2, 2, 500, BitmapFilterQuality.HIGH));
            this.m_timerText.setTextFormat(_loc_2);
            this.m_timerText.setDefaultTextFormat(_loc_2);
            this.m_timerText.getTextField().multiline = true;
            this.m_timerText.setWordWrap(true);
            this.updateTimer();
            addChild(this.m_timerText);
            this.m_timerText.y = m_icon.height - this.m_timerText.height;
            m_jPanel.setPreferredSize(new IntDimension(m_icon.width, m_icon.height + 12));
            m_jPanel.setMinimumSize(new IntDimension(m_icon.width, m_icon.height + 12));
            m_jPanel.setMaximumSize(new IntDimension(m_icon.width, m_icon.height + 12));
            ASwingHelper.prepare(m_jWindow);
            return;
        }//end

        protected void  updateTimer ()
        {
            String _loc_1 =null ;
            double _loc_3 =0;
            _loc_2 = this.timeLeftSeconds ;
            if (_loc_2 < 60 * 60)
            {
                _loc_1 = CardUtil.formatTimeHours(_loc_2);
            }
            else
            {
                _loc_3 = _loc_2 / (60 * 60 * 24);
                _loc_1 = CardUtil.localizeTimeRemainingFloored(_loc_3);
            }
            if (this.m_timerText != null)
            {
                this.m_timerText.setText(_loc_1);
            }
            return;
        }//end

         protected void  trackMouseClick ()
        {
            super.trackMouseClick();
            return;
        }//end

         protected void  onMouseClick (Event event )
        {
            super.onMouseClick(event);
            return;
        }//end

    }



