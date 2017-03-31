package Modules.bandits;

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

    public class PreyModeData
    {
        public String m_name ;
        public Array m_doobers ;
        public Array m_rewardItems ;
        public Array m_rewardDooberTotals ;
        public int m_requiredHunters =1;

        public  PreyModeData ()
        {
            this.m_doobers = new Array();
            this.m_rewardItems = new Array();
            this.m_rewardDooberTotals = new Array();
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public int  requiredHunters ()
        {
            return this.m_requiredHunters;
        }//end

        public Array  doobers ()
        {
            return this.m_doobers;
        }//end

        public Array  rewardItems ()
        {
            return this.m_rewardItems;
        }//end

        public Array  rewardDooberTotals ()
        {
            return this.m_rewardDooberTotals;
        }//end

    }


