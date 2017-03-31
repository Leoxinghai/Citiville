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

import Engine.Classes.*;
import Engine.Events.*;
//import flash.events.*;

    public class SNAPIInit extends SocialNetworkInit
    {
        protected SNAPISocialNetwork m_sn ;
        protected Class m_snClass =null ;

        public  SNAPIInit (boolean param1 ,Class param2 )
        {
            if (param2 == null)
            {
                param2 = SNAPISocialNetwork;
            }
            this.m_snClass = param2;
            super(param1);
            return;
        }//end

         public void  execute ()
        {
            if (m_skipSN)
            {
                super.execute();
            }
            else
            {
                _loc_1 = new this.m_snClass ();
                this.m_sn = _loc_1;

                GlobalEngine.socialNetwork = _loc_1;
                GlobalEngine.socialNetwork = _loc_1;
                this.m_sn.addEventListener(SocialNetworkEvent.GET_USER_COMPLETE, this.onGetUserComplete);
                this.m_sn.getUser();
                GlobalEngine.zaspManager.trackTimingStart("SN_INIT");
            }
            return;
        }//end

        private void  onGetUserComplete (SocialNetworkEvent event )
        {
            this.m_sn.removeEventListener(SocialNetworkEvent.GET_USER_COMPLETE, this.onGetUserComplete);
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("SN_INIT");
            this.m_sn.addEventListener(SocialNetworkEvent.GET_FRIENDS_COMPLETE, this.onGetFriendsComplete);
            this.m_sn.getFriendList();
            return;
        }//end

        private void  onGetFriendsComplete (SocialNetworkEvent event )
        {
            this.m_sn.removeEventListener(SocialNetworkEvent.GET_FRIENDS_COMPLETE, this.onGetFriendsComplete);
            this.m_sn.addEventListener(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE, this.onGetAppFriendsComplete);
            this.m_sn.getAppFriends();
            return;
        }//end

        private void  onGetAppFriendsComplete (SocialNetworkEvent event )
        {
            this.m_sn.removeEventListener(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE, this.onGetAppFriendsComplete);
            this.setupOnFriendsComplete();
            return;
        }//end

        protected void  setupOnFriendsComplete ()
        {
            return;
        }//end

    }



