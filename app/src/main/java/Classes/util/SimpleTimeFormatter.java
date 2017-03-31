package Classes.util;

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

//import flash.text.*;
    public class SimpleTimeFormatter implements ITimeFormatter
    {
        private TextFormat m_digitTextFormat ;
        private TextFormat m_delimiterTextFormat ;
        private static  double MILLIS_PER_SECOND =0.001;
        private static  double MILLIS_PER_MINUTE =1.66667e -005;
        private static  double MILLIS_PER_HOUR =2.77778e -007;
        private static  String DELIMITER =":";
        private static  int DIGIT_FONT_SIZE =22;
        private static  int DELIMITER_FONT_SIZE =24;

        public  SimpleTimeFormatter ()
        {
            this.m_digitTextFormat = new TextFormat();
            this.m_delimiterTextFormat = new TextFormat();
            return;
        }//end

        public void  setDigitFont (int param1 ,double param2 =NaN )
        {
            this.m_digitTextFormat.size = param1;
            if (!isNaN(param2))
            {
                this.m_digitTextFormat.color = param2;
            }
            return;
        }//end

        public void  setDelimiterFont (int param1 ,double param2 =NaN )
        {
            this.m_delimiterTextFormat.size = param1;
            if (!isNaN(param2))
            {
                this.m_delimiterTextFormat.color = param2;
            }
            return;
        }//end

        public String  formatTime (double param1 )
        {
            _loc_2 = param1*MILLIS_PER_HOUR ;
            param1 = param1 - _loc_2 / MILLIS_PER_HOUR;
            _loc_3 = param1*MILLIS_PER_MINUTE ;
            param1 = param1 - _loc_3 / MILLIS_PER_MINUTE;
            _loc_4 = param1*MILLIS_PER_SECOND ;
            _loc_2 = Math.min(99, _loc_2);
            _loc_5 = _loc_2<10? ("0" + _loc_2.toString()) : (_loc_2.toString());
            _loc_6 = _loc_3<10? ("0" + _loc_3.toString()) : (_loc_3.toString());
            _loc_7 = _loc_4<10? ("0" + _loc_4.toString()) : (_loc_4.toString());
            return _loc_5 + DELIMITER + _loc_6 + DELIMITER + _loc_7;
        }//end

        public void  styleTime (TextField param1 )
        {
            _loc_2 = param1.getTextFormat ();
            this.inheritFormat(_loc_2, this.m_digitTextFormat);
            this.inheritFormat(_loc_2, this.m_delimiterTextFormat);
            param1.setTextFormat(this.m_digitTextFormat, 0, 2);
            param1.setTextFormat(this.m_delimiterTextFormat, 3, 4);
            param1.setTextFormat(this.m_digitTextFormat, 4, 6);
            param1.setTextFormat(this.m_delimiterTextFormat, 6, 7);
            param1.setTextFormat(this.m_digitTextFormat, 7);
            return;
        }//end

        private void  inheritFormat (TextFormat param1 ,TextFormat param2 )
        {
            param2.align = param1.align;
            param2.bold = param1.bold;
            param2.font = param1.font;
            param2.italic = param1.italic;
            param2.letterSpacing = param1.letterSpacing;
            param2.underline = param1.underline;
            if (!param2.color)
            {
                param2.color = param1.color;
            }
            if (!param2.size)
            {
                param2.size = param1.size;
            }
            return;
        }//end

    }


