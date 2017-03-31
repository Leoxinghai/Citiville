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

import java.util.Vector;


public class ShardScheduler
    {
        protected Vector<Sharder> m_sharders;
        public static  int CATEGORY_HIGH_PRIORITY =0;
        public static  int CATEGORY_MEDIUM_PRIORITY =1;
        public static  int CATEGORY_LOW_PRIORITY =2;
        public static  int COUNT_ALL_CATEGORIES =3;

        public  ShardScheduler ()
        {
            return;
        }//end

        public int  categoryCount ()
        {
            return COUNT_ALL_CATEGORIES;
        }//end

        public void  initSharders ()
        {
            this.m_sharders = new Vector<Sharder>(this.categoryCount());
            int _loc_1 =0;
            while (_loc_1 < this.categoryCount())
            {

                this.m_sharders.put(_loc_1,  new Sharder());
                this.m_sharders.get(_loc_1).initShards();
                _loc_1++;
            }
            return;
        }//end

        public Sharder  getSharder (int param1 )
        {
            return this.m_sharders.get(param1);
        }//end

        public void  updateAllSharderTimers ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.categoryCount())
            {

                //this.m_sharders.get(_loc_1).updateShardTimers();
                _loc_1++;
            }
            return;
        }//end

        public void  setAllNumShards (int param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.categoryCount())
            {

                this.m_sharders.get(_loc_2).setNumShards(param1);
                _loc_2++;
            }
            return;
        }//end

    }


