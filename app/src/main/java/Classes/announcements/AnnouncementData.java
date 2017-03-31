package Classes.announcements;

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
import scripting.*;

    public class AnnouncementData extends EventDispatcher implements IScriptingContext
    {
        protected String m_id ;
        protected Vector<Script> m_validations;
        protected Object m_view ;
        protected int m_priority ;
        private static  String FLAG_PREFIX ="announcements_";

        public  AnnouncementData (String param1 ,Object param2 ,int param3 =0)
        {
            this.m_validations = new Vector<Script>();
            this.m_id = param1;
            this.m_view = param2;
            this.m_priority = param3;
            return;
        }//end

        public Object  view ()
        {
            return this.m_view;
        }//end

        public String  id ()
        {
            return this.m_id;
        }//end

        public int  priority ()
        {
            return this.m_priority;
        }//end

        public String  seenFlag ()
        {
            return FLAG_PREFIX + this.m_id;
        }//end

        public boolean  hasNotSeen ()
        {
            return !Global.player.getSeenFlag(this.seenFlag);
        }//end

        public void  attachScript (Script param1 )
        {
            this.m_validations.push(param1);
            return;
        }//end

        public boolean  validate ()
        {
            Script _loc_2 =null ;
            boolean _loc_1 =true ;
            for(int i0 = 0; i0 < this.m_validations.size(); i0++)
            {
            		_loc_2 = this.m_validations.get(i0);

                _loc_1 = false;
                if (_loc_2.validates())
                {
                    return true;
                }
            }
            return _loc_1;
        }//end

        public void  onValidate ()
        {
            return;
        }//end

        public void  setAsSeen ()
        {
            Global.player.setSeenFlag(this.seenFlag);
            return;
        }//end

    }



