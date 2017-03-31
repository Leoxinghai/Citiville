package Modules.guide.actions;

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

    public class GAAllowQuests extends GuideAction
    {
        protected boolean m_value =false ;

        public  GAAllowQuests ()
        {
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"allowquests");
            if (!_loc_2)
            {
                return false;
            }
            this.m_value = Boolean(_loc_2.@value);
            return true;
        }//end  

         public void  update (double param1 )
        {
            super.update(param1);
            if (Global.player != null)
            {
                Global.player.setAllowQuests(this.m_value);
            }
            if (this.m_value)
            {
                Global.hud.setTutorial(false);
                Global.questManager.refreshActiveIconQuests();
            }
            removeState(this);
            return;
        }//end  

    }



