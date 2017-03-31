package Init;

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

import Engine.Init.*;
import Modules.stats.helpers.*;
import Modules.stats.types.*;
//import flash.events.*;

    public class StatsInit extends InitializationAction
    {
        private LoginStatsHelper m_loginStatsHelper ;
        public static  String INIT_ID ="StatsInit";

        public  StatsInit (InitializationManager param1 )
        {
            super(INIT_ID);
            this.m_loginStatsHelper = new LoginStatsHelper(param1);
            addDependency(TransactionsInit.INIT_ID);
            addDependency(UIInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            this.m_loginStatsHelper.register(Global.player, StatsCounterType.PLAYER_COUNTER);
            this.m_loginStatsHelper.register(Global.franchiseManager, StatsCounterType.FRANCHISES);
            this.m_loginStatsHelper.register(Global.world, StatsCounterType.PLAYER_COUNTER);
            this.m_loginStatsHelper.register(Global.world.citySim, StatsCounterType.PLAYER_COUNTER);
            this.m_loginStatsHelper.init();
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



