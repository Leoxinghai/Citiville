package Events;

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

import Classes.*;
//import flash.events.*;

    public class QuestEvent extends Event
    {
        private Quest m_quest =null ;
        private double m_enqueueTimeMSEC ;
        private int m_taskCompleted =-1;
        public static  String COMPLETED ="quest_completed";
        public static  String STARTED ="quest_started";
        public static  String EXPIRED ="quest_expired";
        public static  String PROGRESS ="quest_progress";

        public  QuestEvent (String param1 ,Quest param2 ,double param3 ,boolean param4 =false ,boolean param5 =false )
        {
            super(param1, param4, param5);
            this.m_quest = param2;
            this.m_enqueueTimeMSEC = param3;
            return;
        }//end  

        public Quest  quest ()
        {
            return this.m_quest;
        }//end  

        public double  enqueueTimeMSEC ()
        {
            return this.m_enqueueTimeMSEC;
        }//end  

        public void  taskCompleted (int param1 )
        {
            this.m_taskCompleted = param1;
            return;
        }//end  

        public int  taskCompleted ()
        {
            return this.m_taskCompleted;
        }//end  

    }



