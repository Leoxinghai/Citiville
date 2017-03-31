package Engine.Classes;

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


    public class Sharder
    {
        protected int m_currentShard =0;
        protected int m_currentNumShards =2;
        protected int m_maxNumShards =16;
        protected Vector m_lastShardTimeVector<Number >=null ;
        protected Vector m_deltaShardTimeVector<Number >=null ;

        public  Sharder ()
        {
            return;
        }//end

        public void  initShards ()
        {
            this.m_maxNumShards = GlobalEngine.engineOptions.numShards;
            this.m_lastShardTimeVector = new Vector<Number>(this.m_maxNumShards, true);
            this.m_deltaShardTimeVector = new Vector<Number>(this.m_maxNumShards, true);
            int _loc_1 =0;
            while (_loc_1 < this.m_maxNumShards)
            {

                this.m_lastShardTimeVector.put(_loc_1,  GlobalEngine.currentTime);
                this.m_deltaShardTimeVector.put(_loc_1,  0);
                _loc_1++;
            }
            return;
        }//end

        public void  setNumShards (int param1 )
        {
            if (param1 <= this.m_maxNumShards)
            {
                this.m_currentNumShards = param1;
                if (this.m_currentShard > this.m_currentNumShards)
                {
                    this.m_currentShard = 0;
                }
            }
            return;
        }//end

        public int  getRandomShard ()
        {
            return Math.floor(Math.random() * this.m_maxNumShards);
        }//end

        public double  getShardDelta (int param1 )
        {
            return param1 % this.m_currentNumShards != this.m_currentShard ? (-1) : (this.m_deltaShardTimeVector.get(param1));
        }//end

    }



