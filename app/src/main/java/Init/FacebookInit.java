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
//import flash.utils.*;

    public class FacebookInit extends SocialNetworkInit
    {
        protected FacebookUtil m_facebookUtil ;
        protected Class m_facebookUtilClass =null ;
public static  String FB_API_KEY ="";
public static  String FB_SECRET ="";

        public  FacebookInit (boolean param1 ,Class param2 )
        {
            if (param2 == null)
            {
                param2 = FacebookUtil;
            }
            this.m_facebookUtilClass = param2;
            super(param1);
            return;
        }//end

         public void  execute ()
        {
            Timer _loc_1 =null ;
            if (m_skipSN)
            {
                super.execute();
            }
            else
            {
                _loc_2 = new this.m_facebookUtilClass(this.getApiKey (),this.getSecret ());
                this.m_facebookUtil = new this.m_facebookUtilClass(this.getApiKey(), this.getSecret());

                GlobalEngine.socialNetwork = _loc_2;
                GlobalEngine.socialNetwork = _loc_2;
                if (this.m_facebookUtil.needsLogin)
                {
                    this.addEventListener(MouseEvent.CLICK, this.onLoginClick);
                    this.m_facebookUtil.addEventListener(SocialNetworkEvent.LOGIN_COMPLETE, this.onLoginComplete);
                    _loc_1 = new Timer(4000);
                    _loc_1.addEventListener(TimerEvent.TIMER, this.onLoginClick);
                    _loc_1.start();
                }
                else
                {
                    this.onLoginComplete(null);
                }
            }
            return;
        }//end

        private void  onLoginComplete (Event event )
        {
            this.m_facebookUtil.removeEventListener(SocialNetworkEvent.LOGIN_COMPLETE, this.onLoginComplete);
            this.m_facebookUtil.addEventListener(SocialNetworkEvent.GET_USER_COMPLETE, this.onGetUserComplete);
            this.m_facebookUtil.getUser();
            return;
        }//end

        private void  onGetUserComplete (SocialNetworkEvent event )
        {
            this.m_facebookUtil.removeEventListener(SocialNetworkEvent.GET_USER_COMPLETE, this.onGetUserComplete);
            dispatchEvent(new Event(Event.COMPLETE));
            this.m_facebookUtil.addEventListener(SocialNetworkEvent.GET_FRIENDS_COMPLETE, this.onGetFriendsComplete);
            this.m_facebookUtil.getFriendList();
            return;
        }//end

        private void  onGetFriendsComplete (SocialNetworkEvent event )
        {
            this.m_facebookUtil.removeEventListener(SocialNetworkEvent.GET_FRIENDS_COMPLETE, this.onGetFriendsComplete);
            this.m_facebookUtil.addEventListener(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE, this.onGetAppFriendsComplete);
            this.m_facebookUtil.getAppFriends();
            return;
        }//end

        private void  onGetAppFriendsComplete (SocialNetworkEvent event )
        {
            this.m_facebookUtil.removeEventListener(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE, this.onGetAppFriendsComplete);
            this.setupOnFriendsComplete();
            return;
        }//end

        protected void  setupOnFriendsComplete ()
        {
            return;
        }//end

        private void  onLoginClick (Event event =null )
        {
            this.m_facebookUtil.validateLogin();
            return;
        }//end

        protected String  getApiKey ()
        {
            return (String)GlobalEngine.getFlashVar("fb_sig_api_key");
        }//end

        protected String  getSecret ()
        {
            return (String)GlobalEngine.getFlashVar("fb_sig_api_secret");
        }//end

    }



