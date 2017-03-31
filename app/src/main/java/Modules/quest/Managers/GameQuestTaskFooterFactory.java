package Modules.quest.Managers;

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

import Display.DialogUI.*;
import Modules.quest.Display.TaskFooters.*;
//import flash.utils.*;

    public class GameQuestTaskFooterFactory
    {
        private Dictionary m_registeredTypes ;

        public  GameQuestTaskFooterFactory ()
        {
            this.m_registeredTypes = new Dictionary();
            return;
        }//end

        public void  register (String param1 ,Class param2 )
        {
            this.m_registeredTypes.put(param1,  param2);
            return;
        }//end

        public ITaskFooter  createTaskFooter (String param1 ,String param2 ,GenericDialogView param3 )
        {
            _loc_4 =(Class) this.m_registeredTypes.get(param1);
            return new (this.m_registeredTypes.get(param1) as Class)()(param3, param2) as ITaskFooter;
        }//end

    }



