package Classes.announcers;

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
import Classes.actions.*;
//import flash.utils.*;

    public class AnnouncerActionData
    {
        private String m_actionName ;
        private Dictionary m_args ;

        public  AnnouncerActionData (String param1 ,Dictionary param2 )
        {
            this.m_actionName = param1;
            this.m_args = param2;
            return;
        }//end

        public String  actionName ()
        {
            return this.m_actionName;
        }//end

        public Dictionary  args ()
        {
            return this.m_args;
        }//end

        public NPCAction  getAction (NPC param1 )
        {
            NPCAction _loc_2 =null ;
            switch(this.m_actionName)
            {
                case "ActionNavigateWander":
                {
                    _loc_2 = new ActionNavigateWander(param1);
                    break;
                }
                default:
                {
                    _loc_2 = new NPCAction(param1);
                    break;
                }
            }
            return _loc_2;
        }//end

    }



