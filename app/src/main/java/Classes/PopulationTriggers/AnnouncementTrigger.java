package Classes.PopulationTriggers;

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
import Classes.announcements.*;
import Init.PostInit.PostInitActions.*;
import com.zynga.skelly.util.*;

    public class AnnouncementTrigger implements IPopulationTrigger
    {
        protected boolean m_hasTriggered =false ;
        protected String m_announcementId ;

        public  AnnouncementTrigger (XML param1 )
        {
            this.m_announcementId = String(param1.@id);
            return;
        }//end

        public void  trigger ()
        {
            StartUpDialogHelper _loc_4 =null ;
            this.m_hasTriggered = true;
            _loc_1 = Global.announcementManager.getAnnouncementById(this.m_announcementId);
            if (!_loc_1)
            {
                return;
            }
            Function _loc_2 =null ;
            if (_loc_1.view.closeCallback)
            {
                _loc_4 = new StartUpDialogHelper();
                _loc_2 = Curry.curry(_loc_4.getCallback(_loc_1.view.closeCallback.name), _loc_1.view.closeCallback.params);
            }
            AnnouncementDialog _loc_3 =new AnnouncementDialog(_loc_1 ,_loc_2 ,false ,true );
            _loc_3.show();
            return;
        }//end

        public boolean  hasTriggered ()
        {
            return this.m_hasTriggered;
        }//end

    }



