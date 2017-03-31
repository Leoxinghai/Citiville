package Transactions;

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

import Engine.Managers.*;
import Engine.Transactions.*;
import Modules.stats.experiments.*;

    public class TStreakBonus extends Transaction
    {
        protected Array m_data ;

        public  TStreakBonus (int param1 ,int param2 )
        {
            this.m_data = new Array();
            this.m_data.put("amount",  param1);
            this.m_data.put("maxesReached",  param2);
            _loc_3 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DOOBER_TRACKING );
            if (_loc_3)
            {
                StatsManager.count("doobers", "streak_bonus_sent", "client", param1.toString(), param2.toString(), Global.player.level.toString());
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.streakBonus", this.m_data);
            return;
        }//end

    }



