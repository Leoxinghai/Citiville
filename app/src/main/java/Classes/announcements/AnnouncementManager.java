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

    public class AnnouncementManager
    {
        protected Array m_announcements ;

        public  AnnouncementManager ()
        {
            this.m_announcements = Global.gameSettings().getAnnouncements();
            return;
        }//end

        public AnnouncementData  getNextAnnouncement ()
        {
            AnnouncementData _loc_1 =null ;
            AnnouncementData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcements.size(); i0++)
            {
            		_loc_2 = this.m_announcements.get(i0);

                if (_loc_2.hasNotSeen && _loc_2.validate())
                {
                    if (_loc_1 && _loc_1.priority >= _loc_2.priority)
                    {
                        continue;
                    }
                    _loc_1 = _loc_2;
                }
            }
            return _loc_1;
        }//end

        public AnnouncementData  getAnnouncementById (String param1 )
        {
            AnnouncementData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcements.size(); i0++)
            {
            		_loc_2 = this.m_announcements.get(i0);

                if (_loc_2.id != param1)
                {
                    continue;
                }
                if (_loc_2.hasNotSeen && _loc_2.validate())
                {
                    return _loc_2;
                }
                break;
            }
            return null;
        }//end

        public AnnouncementData  getAnnouncementByIdUnconditionally (String param1 )
        {
            AnnouncementData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_announcements.size(); i0++)
            {
            		_loc_2 = this.m_announcements.get(i0);

                if (_loc_2.id == param1)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

    }



