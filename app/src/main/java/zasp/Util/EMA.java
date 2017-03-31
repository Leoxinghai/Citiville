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

    public class EMA implements Sampler
    {
        private double alpha_ =0;
        private double value_ =0;
        private boolean hasValue_ =false ;

        public  EMA (double param1 )
        {
            if (param1 < 0 || param1 > 1)
            {
                throw new Error("alpha " + param1 + " not in .get(0..1)");
            }
            this.alpha_ = param1;
            this.value_ = 0;
            this.hasValue_ = false;
            return;
        }//end

        public void  sample (double param1 )
        {
            if (!this.hasValue)
            {
                this.value_ = param1;
                this.hasValue_ = true;
            }
            else
            {
                this.value_ = this.alpha() * param1 + (1 - this.alpha()) * this.value_;
            }
            return;
        }//end

        public boolean  hasValue ()
        {
            return this.hasValue_;
        }//end

        public double  value ()
        {
            if (!this.hasValue_)
            {
                return 0;
            }
            return this.value_;
        }//end

        public double  alpha ()
        {
            return this.alpha_;
        }//end

    }



