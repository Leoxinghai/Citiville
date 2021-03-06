package Classes;

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

import Init.*;
//import flash.utils.*;
import Engine.Interfaces.*;

    public class QuestComponent implements IEngineComponent
    {
        private QuestComponentOptions m_options ;

        public  QuestComponent (QuestComponentOptions param1)
        {
            if (param1 == null)
            {
                param1 = new QuestComponentOptions();
            }
            this.m_options = param1;
            return;
        }//end

        public void  initialize ()
        {
            _loc_1 = Config.BASE_PATH+"questSettings.xml";
            if (GlobalEngine.getFlashVar("quest_url"))
            {
                _loc_1 =(String) GlobalEngine.getFlashVar("quest_url");
            }
            _loc_2 = (ByteArray)GlobalEngine.getFlashVar("QuestSettings")
            if (_loc_2)
            {
                GlobalEngine.setFlashVar("QuestSettings", null);
            }
            GlobalEngine.initializationManager.add(new QuestSettingsInit(_loc_1, this.m_options.questClass, this.m_options.questUtility, _loc_2));
            GlobalEngine.initializationManager.add(new QuestInit());
            return;
        }//end

    }



