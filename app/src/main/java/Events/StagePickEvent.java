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

//import flash.events.*;
    public class StagePickEvent extends Event
    {
        protected String m_pickName ;
        public static  String HIDE ="hideStagePick";
        public static  String SHOW ="showStagePick";

        public  StagePickEvent (String param1 ,String param2 ,boolean param3 =false ,boolean param4 =false )
        {
            this.m_pickName = param2;
            super(param1, param3, param4);
            return;
        }//end

        public String  pickName ()
        {
            return this.m_pickName;
        }//end

        public boolean  allPicks ()
        {
            return this.m_pickName == null;
        }//end

    }



