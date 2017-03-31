package zasp.Classes;

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

import zasp.Util.*;
    public class ZaspTrackerDetail
    {
        protected CircularBuffer bufF2F ;
        public static  int BUFFER_LENGTH =45;

        public  ZaspTrackerDetail ()
        {
            this.bufF2F = new CircularBuffer(BUFFER_LENGTH);
            return;
        }//end

        public void  sample (String param1 ,double param2 ,double param3 ,int param4 )
        {
            Object _loc_5 =null ;
            while (this.bufF2F.length >= (this.bufF2F.capacity - 1))
            {

                _loc_5 = this.bufF2F.pop();
            }
            if (_loc_5 == null)
            {
                _loc_5 = new Object();
            }
            _loc_5.gameState = param1;
            _loc_5.gameNumber = param2;
            _loc_5.dt = param3;
            _loc_5.mem = param4;
            this.bufF2F.push(_loc_5);
            return;
        }//end

        public Object  report ()
        {
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            Object _loc_1 =new Object ();
            _loc_1.buf_length = BUFFER_LENGTH;
            _loc_1.buf = new Array();
            int _loc_2 =0;
            while (_loc_2 < this.bufF2F.length())
            {

                _loc_3 = this.bufF2F.elementAt(_loc_2);
                _loc_4 = new Object();
                _loc_4.gameState = _loc_3.gameState;
                _loc_4.gameNumber = _loc_3.gameNumber;
                _loc_4.dt = _loc_3.dt;
                _loc_4.mem = _loc_3.mem;
                _loc_1.buf.put(_loc_2,  _loc_4);
                _loc_2++;
            }
            return _loc_1;
        }//end

        public CircularBuffer  f2fBuffer ()
        {
            return this.bufF2F;
        }//end

        public void  log_report ()
        {
            Object _loc_2 =null ;
            trace("PERFORMANCE REPORT DETAIL");
            trace("  BUFFER_LENGTH: " + BUFFER_LENGTH);
            int _loc_1 =0;
            while (_loc_1 < this.bufF2F.length())
            {

                _loc_2 = this.bufF2F.elementAt(_loc_1);
                trace("  sample:        " + _loc_1);
                trace("    gameState:   " + _loc_2.gameState);
                trace("    gameNumber:  " + _loc_2.gameNumber);
                trace("    dt:          " + _loc_2.dt);
                trace("    mem:         " + _loc_2.mem);
                _loc_1++;
            }
            return;
        }//end

    }



