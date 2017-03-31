package zasp.Util;

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

    public class SumSampler implements Sampler
    {
        private double cutoffSum_ ;
        private double runningSum_ ;
        private CircularBuffer samples_ ;

        public  SumSampler (double param1 )
        {
            this.samples_ = new CircularBuffer(0);
            this.cutoffSum_ = param1;
            this.runningSum_ = 0;
            return;
        }//end

        public void  sample (double param1 )
        {
            double _loc_2 =0;
            if (this.samples_.length >= this.samples_.capacity)
            {
                this.samples_.capacity = 1 + 1.3 * this.samples_.capacity;
            }
            this.samples_.push(param1);
            this.runningSum_ = this.runningSum_ + param1;
            while (this.samples_.length > 0)
            {

                _loc_2 =(Number) this.samples_.elementAt(0);
                if (this.runningSum_ - _loc_2 < this.cutoffSum_)
                {
                    break;
                }
                this.samples_.pop();
                this.runningSum_ = this.runningSum_ - _loc_2;
            }
            return;
        }//end

        public double  value ()
        {
            return this.runningSum_;
        }//end

        public int  length ()
        {
            return this.samples_.length;
        }//end

        public double  sampleAt (int param1 )
        {
            return this.samples_.elementAt(param1) as Number;
        }//end

        public static void  test ()
        {
            SumSampler _loc_1 =new SumSampler(50);
            if (_loc_1.length != 0)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 0) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(10.1);
            _loc_1.sample(15.2);
            _loc_1.sample(20.3);
            if (_loc_1.length != 3)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 45.6) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(5);
            if (_loc_1.length != 4)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 50.6) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(0);
            _loc_1.sample(0);
            _loc_1.sample(0);
            if (_loc_1.length != 7)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 50.6) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(3.3);
            if (_loc_1.length != 8)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 53.9) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(7);
            if (_loc_1.length != 8)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 50.8) > 0.01)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(0) != 15.2)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(1) != 20.3)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(2) != 5)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(3) != 0)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(4) != 0)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(5) != 0)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(6) != 3.3)
            {
                throw new Error("");
            }
            if (_loc_1.sampleAt(7) != 7)
            {
                throw new Error("");
            }
            _loc_1.sample(123.4);
            if (_loc_1.length != 1)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 123.4) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(49);
            if (_loc_1.length != 2)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 172.4) > 0.01)
            {
                throw new Error("");
            }
            _loc_1.sample(1.1);
            if (_loc_1.length != 2)
            {
                throw new Error("");
            }
            if (Math.abs(_loc_1.value - 50.1) > 0.01)
            {
                throw new Error("");
            }
            return;
        }//end

    }



