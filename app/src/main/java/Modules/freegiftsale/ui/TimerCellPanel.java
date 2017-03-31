package Modules.freegiftsale.ui;

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
import Display.aswingui.*;
//import flash.errors.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class TimerCellPanel extends JPanel
    {
        private TimerCell hoursPanel1 ;
        private TimerCell hoursPanel2 ;
        private TimerCell minutesPanel1 ;
        private TimerCell minutesPanel2 ;
        private TimerCell secondsPanel1 ;
        private TimerCell secondsPanel2 ;
        private Date m_countdownDate ;
        private static int CELL_SEPARATOR_FONT_SIZE =24;
        private static int MILLIS_PER_HOUR =3600000;
        private static int MILLIS_PER_MINUTE =60000;
        private static int MILLIS_PER_SECOND =1000;
        private static int RENDER_INTERVAL =100;

        public  TimerCellPanel (Date param1 )
        {
            super(new FlowLayout(FlowLayout.LEFT, 1));
            this.m_countdownDate = param1;
            this.hoursPanel1 = new TimerCell();
            this.hoursPanel2 = new TimerCell();
            this.minutesPanel1 = new TimerCell();
            this.minutesPanel2 = new TimerCell();
            this.secondsPanel1 = new TimerCell();
            this.secondsPanel2 = new TimerCell();
            this.appendAll(this.hoursPanel1, this.hoursPanel2, this.createTimerCellSeparator(), this.minutesPanel1, this.minutesPanel2, this.createTimerCellSeparator(), this.secondsPanel1, this.secondsPanel2);
            this.renderTimerCells(null);
            Timer _loc_2 =new Timer(RENDER_INTERVAL );
            _loc_2.addEventListener(TimerEvent.TIMER, this.renderTimerCells);
            _loc_2.start();
            return;
        }//end

        private JTextField  createTimerCellSeparator ()
        {
            TextFormat _loc_1 =new TextFormat ();
            _loc_1.align = TextFormatAlign.LEFT;
            _loc_2 = ASwingHelper.makeTextField(":",EmbeddedArt.TITLE_FONT ,TimerCellPanel.CELL_SEPARATOR_FONT_SIZE ,EmbeddedArt.lightishBlueTextColor ,3,_loc_1 );
            ASwingHelper.setEasyBorder(_loc_2, -2, -2, 0);
            return _loc_2;
        }//end

        private void  renderTimerCells (TimerEvent event )
        {
            int _loc_4 =0;
            double _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            Date _loc_2 =new Date ();
            _loc_3 = this.m_countdownDate.getTime ()-_loc_2.getTime ();
            if (_loc_3 < 0)
            {
                this.setDisplay(0, 0, 0);
            }
            else
            {
                if (_loc_3 >= 99 * MILLIS_PER_HOUR + 99 * MILLIS_PER_MINUTE)
                {
                    throw new IllegalOperationError("Max of 99 hours ahead FREE_GIFT_OFFER_END_DATE RuntimeVar instanceof over 99 hours ahead!");
                }
                _loc_4 = _loc_3 / TimerCellPanel.MILLIS_PER_HOUR;
                _loc_5 = _loc_3 - _loc_4 * TimerCellPanel.MILLIS_PER_HOUR;
                _loc_6 = _loc_5 / TimerCellPanel.MILLIS_PER_MINUTE;
                _loc_5 = _loc_5 - _loc_6 * TimerCellPanel.MILLIS_PER_MINUTE;
                _loc_7 = _loc_5 / TimerCellPanel.MILLIS_PER_SECOND;
                this.setDisplay(_loc_4, _loc_6, _loc_7);
            }
            return;
        }//end

        private void  setDisplay (int param1 ,int param2 ,int param3 )
        {
            this.setDigitStrings(param1, this.hoursPanel1, this.hoursPanel2);
            this.setDigitStrings(param2, this.minutesPanel1, this.minutesPanel2);
            this.setDigitStrings(param3, this.secondsPanel1, this.secondsPanel2);
            return;
        }//end

        private void  setDigitStrings (int param1 ,TimerCell param2 ,TimerCell param3 )
        {
            String _loc_4 =null ;
            if (param1 > 99)
            {
                return;
            }
            if (param1 < 0)
            {
                return;
            }
            if (param1 >= 0 && param1 < 10)
            {
                param2.digit = 0;
                param3.digit = param1;
            }
            else
            {
                _loc_4 = String(param1);
                param2.digit = parseInt(_loc_4.charAt(0));
                param3.digit = parseInt(_loc_4.charAt(1));
            }
            return;
        }//end

    }



