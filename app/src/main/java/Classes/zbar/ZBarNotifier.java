package Classes.zbar;

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

import Engine.Classes.*;
    public class ZBarNotifier
    {
        private static boolean m_queueNotifications =false ;
        private static boolean m_playerInitialized =false ;
        private static Array m_queuedNotifications =new Array ();

        public  ZBarNotifier ()
        {
            return;
        }//end

        public static void  awardXP (double param1 )
        {
            if (param1 <= 0 || m_playerInitialized == false)
            {
                return;
            }
            if (m_queueNotifications)
            {
                m_queuedNotifications.push(param1);
            }
            else
            {
                snapiAwardXP(param1);
            }
            return;
        }//end

        public static void  playerInitialized (boolean param1 )
        {
            m_playerInitialized = param1;
            return;
        }//end

        public static void  queueNotifications (boolean param1 )
        {
            m_queueNotifications = param1;
            if (param1 == false)
            {
                flushNotifications();
            }
            return;
        }//end

        private static void  flushNotifications ()
        {
            double _loc_1 =0;
            while (m_queuedNotifications.length > 0)
            {

                _loc_1 = m_queuedNotifications.pop();
                snapiAwardXP(_loc_1);
            }
            return;
        }//end

        private static void  snapiAwardXP (double param1 )
        {
            if (GlobalEngine.socialNetwork instanceof SNAPISocialNetwork)
            {
                (GlobalEngine.socialNetwork as SNAPISocialNetwork).snapiAwardXp(param1);
            }
            return;
        }//end

    }


