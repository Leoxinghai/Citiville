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
import Managers.*;
//import flash.events.*;

    public class QuestInit extends InitializationAction
    {
        public static  String INIT_ID ="ZQuestInit";

        public  QuestInit ()
        {
            super(INIT_ID);
            addDependency(QuestSettingsInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            QuestManager.getInstance().initialize();
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



