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

import Classes.*;
import Classes.util.*;
import Display.aswingui.*;
import Modules.sunset.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class CharacterTalkDialogView extends BaseDialogView
    {
        private JLabel m_countdownLabel ;
        private Timer m_ticker ;
        private Sunset m_sunset ;

        public  CharacterTalkDialogView (Dictionary param1 ,Object param2 )
        {
            if (param2.hasOwnProperty("sunsetTheme"))
            {
                this.m_sunset = Global.sunsetManager.getSunsetByThemeName(param2.get("sunsetTheme"));
            }
            super(param1, param2);
            return;
        }//end

         protected JPanel  createContentPanel ()
        {
            m_contentPanel = new JPanel(new BorderLayout());
            _loc_1 = this.createCountdownPanel ();
            _loc_2 = this.createContentBody ();
            AssetPane _loc_3 =new AssetPane(new (DisplayObject)m_assetDict.get( "speechTail"));
            ASwingHelper.setEasyBorder(_loc_3, 170);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_5 = _loc_1? (new Insets(30)) : (new Insets());
            ASwingHelper.setBackground(_loc_4, new (DisplayObject)m_assetDict.get("speechBubble"), _loc_5);
            if (_loc_1)
            {
                _loc_4.append(_loc_1);
            }
            _loc_4.append(_loc_2);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel ();
            ASwingHelper.setEasyBorder(_loc_6, 0, 0, 0, 30);
            _loc_6.appendAll(_loc_3, ASwingHelper.horizontalStrut(-5), _loc_4);
            _loc_6.swapChildren(_loc_3, _loc_4);
            m_contentPanel.append(_loc_6, BorderLayout.EAST);
            return m_contentPanel;
        }//end

        protected JPanel  createContentBody ()
        {
            String _loc_2 =null ;
            AssetPane _loc_3 =null ;
            JPanel _loc_4 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_data.hasOwnProperty("message"))
            {
                _loc_2 = m_data.get("message");
                _loc_3 = ASwingHelper.makeMultilineText(_loc_2, 420, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 18, EmbeddedArt.brownTextColor, null, true);
                _loc_4 = ASwingHelper.makeSoftBoxJPanel();
                ASwingHelper.setEasyBorder(_loc_4, 0, 15, 20, 15);
                _loc_4.append(_loc_3);
                _loc_1.append(_loc_4);
            }
            return _loc_1;
        }//end

        private JPanel  createCountdownPanel ()
        {
            JPanel _loc_1 =null ;
            AssetPane _loc_2 =null ;
            JPanel _loc_3 =null ;
            Sprite _loc_4 =null ;
            JPanel _loc_5 =null ;
            if (this.m_sunset && m_assetDict.hasOwnProperty("timerExclamation"))
            {
                _loc_2 = new AssetPane(m_assetDict.get("timerExclamation"));
                ASwingHelper.prepare(_loc_2);
                this.m_countdownLabel = new JLabel();
                this.m_countdownLabel = new JLabel();
                this.m_countdownLabel.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.advancedFontProps));
                this.m_countdownLabel.setForeground(new ASColor(16777215));
                this.m_countdownLabel.setVerticalAlignment(JLabel.CENTER);
                this.m_countdownLabel.setHorizontalAlignment(JLabel.CENTER);
                this.m_countdownLabel.setTextFilters(EmbeddedArt.darkerOverlayFilter);
                this.m_countdownLabel.setPreferredWidth(400);
                ASwingHelper.setEasyBorder(this.m_countdownLabel, 2, 10, 2, 10);
                this.onCountdownTimerTick(null);
                this.m_ticker = new Timer(500);
                this.m_ticker.addEventListener(TimerEvent.TIMER, this.onCountdownTimerTick, false, 0, true);
                this.m_ticker.start();
                _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_3.append(this.m_countdownLabel);
                ASwingHelper.prepare(_loc_3);
                _loc_4 = new Sprite();
                _loc_4.graphics.beginFill(EmbeddedArt.redTextColor);
                _loc_4.graphics.drawRoundRect(0, 0, _loc_3.getWidth(), _loc_3.getHeight(), 8, 8);
                _loc_4.graphics.endFill();
                _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_5.append(_loc_3);
                ASwingHelper.setBackground(_loc_3, _loc_4);
                _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                ASwingHelper.setEasyBorder(_loc_1, 10, 0, 10, 0);
                _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(-_loc_2.getWidth() * 0.5), _loc_5);
                _loc_1.swapChildren(_loc_2, _loc_5);
            }
            return _loc_1;
        }//end

         protected void  makeCenterPanel ()
        {
            super.makeCenterPanel();
            if (m_assetDict.hasOwnProperty("customTitleImage"))
            {
                if (m_assetDict.get("customTitleImage") instanceof DisplayObject)
                {
                    this.addChild((DisplayObject)m_assetDict.get("customTitleImage"));
                }
            }
            return;
        }//end

         protected void  closeMe ()
        {
            if (this.m_ticker)
            {
                this.m_ticker.stop();
                this.m_ticker.removeEventListener(TimerEvent.TIMER, this.onCountdownTimerTick);
            }
            super.closeMe();
            return;
        }//end

        private void  onCountdownTimerTick (TimerEvent event )
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            String _loc_6 =null ;
            if (this.m_sunset && this.m_countdownLabel)
            {
                _loc_2 = this.m_sunset.getSunsetTimeRemaining() / 1000;
                _loc_3 = DateUtil.getFormattedDayCounter(_loc_2);
                _loc_4 = int(_loc_3);
                if (_loc_4 > 0)
                {
                    _loc_5 = m_data.hasOwnProperty("timerDaysLabel") ? (m_data.get("timerDaysLabel")) : ("EventEndsInDays");
                    _loc_3 = ZLoc.t("Dialogs", _loc_5, {days:_loc_4});
                }
                else
                {
                    _loc_6 = m_data.hasOwnProperty("timerLabel") ? (m_data.get("timerLabel")) : ("EventEndsIn");
                    _loc_3 = ZLoc.t("Dialogs", _loc_6, {time:_loc_3});
                }
                this.m_countdownLabel.setText(_loc_3);
                ASwingHelper.prepare(this);
            }
            return;
        }//end

    }



