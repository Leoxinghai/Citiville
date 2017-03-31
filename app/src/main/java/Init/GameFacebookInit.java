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

import Classes.util.*;
//import flash.events.*;
//import flash.utils.*;

    public class GameFacebookInit extends FacebookInit
    {
        protected Timer m_friendsTimer ;
public static  int SETUP_FRIENDS_TIMER_DELAY =200;
public static  boolean IS_DEBUG_TIMER_DELAY =false ;

        public  GameFacebookInit (boolean param1 )
        {
            super(param1, GameFacebookUtil);
            return;
        }//end  

         protected void  setupOnFriendsComplete ()
        {
            this.setupPlayerFriends(null);
            return;
        }//end  

        private void  setupPlayerFriends (Event event )
        {
            _loc_2 =             !IS_DEBUG_TIMER_DELAY || event != null;
            if (Global.player && _loc_2)
            {
                if (this.m_friendsTimer)
                {
                    this.m_friendsTimer.stop();
                    this.m_friendsTimer = null;
                }
                Global.player.setFriends(GlobalEngine.socialNetwork.getFriendUsers());
            }
            else if (this.m_friendsTimer == null)
            {
                this.m_friendsTimer = new Timer(SETUP_FRIENDS_TIMER_DELAY);
                this.m_friendsTimer.addEventListener(TimerEvent.TIMER, this.setupPlayerFriends);
                this.m_friendsTimer.start();
            }
            return;
        }//end  

    }



