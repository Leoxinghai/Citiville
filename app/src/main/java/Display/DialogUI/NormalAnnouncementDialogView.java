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
import org.aswing.border.*;

    public class NormalAnnouncementDialogView extends GenericPictureDialogView
    {
        private Object m_data ;
        private Sunset m_sunset ;
        private JLabel m_countdownLabel ;
        private Timer m_ticker ;

        public  NormalAnnouncementDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",int param9 =0,int param10 =0,Object param11 =null )
        {
            this.m_data = param11 ? (param11) : ({});
            if (this.m_data.hasOwnProperty("sunsetTheme"))
            {
                this.m_sunset = Global.sunsetManager.getSunsetByThemeName(this.m_data.get("sunsetTheme").get("value"));
            }
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            AssetPane _loc_7 =null ;
            JPanel _loc_8 =null ;
            Sprite _loc_9 =null ;
            JPanel _loc_10 =null ;
            JPanel _loc_11 =null ;
            JPanel _loc_12 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.TOP ));
            textArea = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_3 = ASwingHelper.makeMultilineText(this.m_message,450,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,16,EmbeddedArt.brownTextColor);
            _loc_4 = ASwingHelper.centerElement(_loc_3);
            Array _loc_5 =.get(0 ,10,0,10) ;
            if (Global.localizer.langCode == "ja")
            {
                _loc_5 = .get(10, 10, 5, 10);
            }
            ASwingHelper.setEasyBorder(_loc_4, _loc_5.get(0), _loc_5.get(1), _loc_5.get(2), _loc_5.get(3));
            textArea.append(_loc_4);
            if (this.m_sunset)
            {
                _loc_7 = new AssetPane(m_assetDict.get("timer_exclamation_burst"));
                ASwingHelper.prepare(_loc_7);
                this.m_countdownLabel = new JLabel();
                this.m_countdownLabel.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.advancedFontProps));
                this.m_countdownLabel.setForeground(new ASColor(16777215));
                this.m_countdownLabel.setVerticalAlignment(JLabel.CENTER);
                this.m_countdownLabel.setHorizontalAlignment(JLabel.CENTER);
                this.m_countdownLabel.setTextFilters(EmbeddedArt.darkerOverlayFilter);
                this.m_countdownLabel.setPreferredWidth(350);
                ASwingHelper.setEasyBorder(this.m_countdownLabel, 5, 10, 5, 10);
                this.onCountdownTimerTick(null);
                this.m_ticker = new Timer(500);
                this.m_ticker.addEventListener(TimerEvent.TIMER, this.onCountdownTimerTick, false, 0, true);
                this.m_ticker.start();
                _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_8.append(this.m_countdownLabel);
                ASwingHelper.prepare(_loc_8);
                _loc_9 = new Sprite();
                _loc_9.graphics.beginFill(EmbeddedArt.limeGreenTextColor);
                _loc_9.graphics.drawRoundRect(0, 0, _loc_8.getWidth(), _loc_8.getHeight(), 8, 8);
                _loc_9.graphics.endFill();
                _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_10.append(_loc_8);
                ASwingHelper.setBackground(_loc_8, _loc_9);
                _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                ASwingHelper.setEasyBorder(_loc_11, 10, 0, 10, 0);
                _loc_11.appendAll(_loc_7, ASwingHelper.horizontalStrut(-_loc_7.getWidth() * 0.5), _loc_10);
                _loc_11.swapChildren(_loc_7, _loc_10);
                textArea.append(_loc_11);
            }
            m_imagePanel = makeImagePanel();
            _loc_6 = ASwingHelper.centerElement(m_imagePanel);
            ASwingHelper.setEasyBorder(_loc_6, 0, 20, m_heightOffset, 20 + m_widthOffset);
            textArea.append(_loc_6);
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_12 = createButtonPanel();
                _loc_1.append(_loc_12);
            }
            ASwingHelper.prepare(_loc_1);
            _loc_2.setPreferredWidth(_loc_1.getWidth());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
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
            if (this.m_sunset && this.m_countdownLabel)
            {
                _loc_2 = this.m_sunset.getSunsetTimeRemaining() / 1000;
                _loc_3 = DateUtil.getFormattedDayCounter(_loc_2);
                _loc_4 = int(_loc_3);
                if (_loc_4 > 0)
                {
                    _loc_3 = ZLoc.t("Dialogs", "EventEndsInDays", {days:_loc_4});
                }
                else
                {
                    _loc_3 = ZLoc.t("Dialogs", "EventEndsIn", {time:_loc_3});
                }
                this.m_countdownLabel.setText(_loc_3);
                ASwingHelper.prepare(this);
            }
            return;
        }//end  

    }




