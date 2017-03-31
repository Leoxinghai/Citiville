package zasp.Display;

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

//import flash.display.*;
//import flash.events.*;
import zasp.Util.*;

    public class FunkySpinner extends Sprite implements Sampler
    {
        private Array slices ;
        private CircularBuffer samplesMillis =null ;
        private static  double PHI =(1+Math.sqrt(5))/2;
        private static  double goldenSpiralC =1/Math.pow(PHI ,4);
        private static  double silverSpiralC =1/Math.pow(PHI ,2);
        private static  double copperSpiralC =1/PHI ;
        private static  double C =silverSpiralC ;
        private static  double millisPerCycle =2000;
        private static  int slicesPerCycle =60;
        private static  int totalNumSlices =150;
        private static  Array colors =.get(0 ,16711680,65280,255,8355711,16744319,8388479,8355839,12566463,16760767,12582847,12566527,14671839,16768991,14680031,14671871,16777215) ;

        public  FunkySpinner (double param1 ,double param2 ,double param3 )
        {
            ColorPoly _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            double _loc_13 =0;
            double _loc_14 =0;
            double _loc_15 =0;
            double _loc_16 =0;
            double _loc_17 =0;
            double _loc_18 =0;
            double _loc_19 =0;
            double _loc_20 =0;
            double _loc_21 =0;
            double _loc_22 =0;
            double _loc_23 =0;
            double _loc_24 =0;
            this.slices = new Array();
            this.samplesMillis = new CircularBuffer((colors.length - 1));
            int _loc_4 =0;
            while (_loc_4 < totalNumSlices)
            {

                _loc_5 = new ColorPoly();
                this.slices.push(_loc_5);
                addChild(_loc_5);
                _loc_6 = Math.PI;
                _loc_7 = 2 * _loc_6 * (_loc_4 + 0) / slicesPerCycle - _loc_6 / 2;
                _loc_8 = 2 * _loc_6 * (_loc_4 + 1) / slicesPerCycle - _loc_6 / 2;
                _loc_9 = Math.cos(_loc_7);
                _loc_10 = Math.sin(_loc_7);
                _loc_11 = Math.cos(_loc_8);
                _loc_12 = Math.sin(_loc_8);
                _loc_13 = param3 * Math.pow(C, (_loc_4 + 0) / slicesPerCycle);
                _loc_14 = _loc_13 * C;
                _loc_15 = param3 * Math.pow(C, (_loc_4 + 1) / slicesPerCycle);
                _loc_16 = _loc_15 * C;
                _loc_17 = param1 + _loc_13 * _loc_9;
                _loc_18 = param2 + _loc_13 * _loc_10;
                _loc_19 = param1 + _loc_14 * _loc_9;
                _loc_20 = param2 + _loc_14 * _loc_10;
                _loc_21 = param1 + _loc_15 * _loc_11;
                _loc_22 = param2 + _loc_15 * _loc_12;
                _loc_23 = param1 + _loc_16 * _loc_11;
                _loc_24 = param2 + _loc_16 * _loc_12;
                _loc_5.addPt(_loc_17, _loc_18);
                _loc_5.addPt(_loc_21, _loc_22);
                _loc_5.addPt(_loc_23, _loc_24);
                _loc_5.addPt(_loc_19, _loc_20);
                _loc_5.addPt(_loc_17, _loc_18);
                _loc_4++;
            }
            this.addListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            return;
        }//end

        public void  sample (double param1 )
        {
            while (this.samplesMillis.length >= (this.samplesMillis.capacity - 1))
            {

                this.samplesMillis.pop();
            }
            this.samplesMillis.push(param1);
            return;
        }//end

        private void  addListener (String param1 ,Function param2 )
        {
            addEventListener(param1, param2, false, 0, true);
            return;
        }//end

        private void  removeListener (String param1 ,Function param2 )
        {
            removeEventListener(param1, param2, false);
            return;
        }//end

        private void  added_to_stage (Event event )
        {
            this.removeListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            this.addListener(Event.ENTER_FRAME, this.enter_frame);
            this.addListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage);
            return;
        }//end

        private void  removed_from_stage (Event event )
        {
            this.addListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            this.removeListener(Event.ENTER_FRAME, this.enter_frame);
            this.removeListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage);
            return;
        }//end

        private void  enter_frame (Event event )
        {
            ColorPoly _loc_8 =null ;
            _loc_2 = millisPerCycle/slicesPerCycle ;
            _loc_3 = Math.min(colors.length,this.samplesMillis.length());
            int _loc_4 =0;
            int _loc_5 =0;
            double _loc_6 =0;
            int _loc_7 =0;
            if (this.samplesMillis.length > 0)
            {
                _loc_7 = this.samplesMillis.length - 1;
                _loc_6 =(Number) this.samplesMillis.elementAt(_loc_7);
            }
            while (_loc_5 < _loc_3 && _loc_4 < totalNumSlices)
            {

                _loc_8 =(ColorPoly) this.slices.get(_loc_4);
                _loc_8.changeColor(colors.get(_loc_5));
                _loc_4++;
                while (_loc_2 * _loc_4 > _loc_6 && _loc_5 < _loc_3)
                {

                    _loc_7 = this.samplesMillis.length - _loc_5 - 1;
                    _loc_6 = _loc_6 + (this.samplesMillis.elementAt(_loc_7) as Number);
                    _loc_5++;
                }
            }
            while (_loc_4 < totalNumSlices)
            {

                _loc_8 =(ColorPoly) this.slices.get(_loc_4);
                _loc_8.changeColor(colors.get((colors.length - 1)));
                _loc_4++;
            }
            return;
        }//end

    }




