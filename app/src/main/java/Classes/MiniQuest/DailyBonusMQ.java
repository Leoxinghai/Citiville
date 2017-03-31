package Classes.MiniQuest;

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

import Display.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.events.*;

    public class DailyBonusMQ extends MiniQuest
    {
        public static  String QUEST_NAME ="dailyBonusMQ";
        private static boolean m_showLoading =true ;

        public  DailyBonusMQ ()
        {
            super(QUEST_NAME);
            m_recurrenceTime = 4;
            m_showLoading = !Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_WMODE_DEFAULT);
            return;
        }//end  

         protected void  initQuest ()
        {
            StatsManager.count(StatsCounterType.DAILY_BONUS, "iconTriggered");
            super.initQuest();
            return;
        }//end  

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            doDailyBonus();
            m_recurrenceTime = 0;
            return;
        }//end  

        public static void  doDailyBonus ()
        {
            Global.player.dailyBonusManager.collectDailyBonus();
            if (Global.player.dailyBonusManager.isLastDay())
            {
                UI.freezeScreen(m_showLoading);
            }
            else
            {
                Global.player.dailyBonusManager.doBonusEndTasks();
            }
            return;
        }//end  

    }

