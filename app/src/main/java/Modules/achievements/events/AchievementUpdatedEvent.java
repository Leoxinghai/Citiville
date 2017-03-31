package Modules.achievements.events;

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

//import flash.events.*;
    public class AchievementUpdatedEvent extends Event
    {
        protected String m_groupName ;
        protected String m_achievementName ;
        public static  String UPDATED ="achievement_updated";

        public  AchievementUpdatedEvent (String param1 ,String param2 ,String param3 ,boolean param4 =false ,boolean param5 =false )
        {
            super(param1, param4, param5);
            this.m_groupName = param2;
            this.m_achievementName = param3;
            return;
        }//end  

        public String  groupName ()
        {
            return this.m_groupName;
        }//end  

        public String  achievementName ()
        {
            return this.m_achievementName;
        }//end  

    }



