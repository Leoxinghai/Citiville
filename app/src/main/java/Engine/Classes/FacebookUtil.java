package Engine.Classes;

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

import Engine.*;
import Engine.Events.*;
import Engine.Managers.*;
import com.facebook.*;
import com.facebook.commands.friends.*;
import com.facebook.commands.users.*;
import com.facebook.data.*;
import com.facebook.data.friends.*;
import com.facebook.data.users.*;
import com.facebook.events.*;
import com.facebook.net.*;
import com.facebook.session.*;
//import flash.display.*;
//import flash.external.*;

    public class FacebookUtil extends SocialNetwork
    {
        protected IFacebookSession m_facebookSession ;
        protected Facebook m_facebook ;
        protected boolean m_waitingForLogin =false ;
        protected FacebookUser m_facebookUser ;
        protected Array m_facebookFriends ;
        protected Array m_facebookAppFriends ;
        protected boolean m_needsLogin =false ;
        protected boolean m_offlineMode ;
        protected double m_offlineModeId ;

        public  FacebookUtil (String param1 ,String param2 )
        {
            boolean _loc_5 =false ;
            double _loc_6 =0;
            String _loc_3 =null ;
            _loc_4 = GlobalEngine.getFlashVars ();
            if (GlobalEngine.getFlashVars().fb_sig_session_key != null)
            {
                _loc_3 = _loc_4.fb_sig_session_key;
            }
            if (_loc_4.fb_sig_ss && _loc_4.fb_sig_api_key && _loc_4.fb_sig_session_key)
            {
                this.m_facebookSession = new WebSession(_loc_4.fb_sig_api_key, _loc_4.fb_sig_ss, _loc_4.fb_sig_session_key);
                //facebook_internal::_uid = _loc_4.fb_sig_user;
                m_facebookUser.uid = _loc_4.fb_sig_user;
            }
            else if (_loc_4.as_app_name)
            {
                this.m_facebookSession = new JSSession(param1, _loc_4.as_app_name);
            }
            else
            {
                this.m_facebookSession = new DesktopSession(param1, param2);
            }
            this.m_facebookSession.session_key = _loc_3;
            this.m_facebook = new Facebook();
            this.m_facebook.addEventListener(FacebookEvent.WAITING_FOR_LOGIN, this.onWaitingForLogin);
            this.m_facebook.addEventListener(FacebookEvent.CONNECT, this.onConnect);
            this.m_facebook.startSession(this.m_facebookSession);
            if (_loc_4.fb_sig_session_key)
            {
                this.m_facebookSession.verifySession();
                this.m_needsLogin = false;
                m_sigTime = _loc_4.fb_sig_time;
            }
            else
            {
                _loc_5 = false;
                if (_loc_4.decouple_from_facebook)
                {
                    GlobalEngine.log("Facebook", "Attempting to decouple from facebook");
                    _loc_6 = parseFloat(_loc_4.decoupled_facebook_id);
                    if (isNaN(_loc_6))
                    {
                        GlobalEngine.log("Facebook", "Can\'t decouple from facebook, invalid ID");
                        _loc_5 = false;
                    }
                    else
                    {
                        this.m_offlineModeId = _loc_6;
                        _loc_5 = true;
                        GlobalEngine.log("Facebook", "Succesfully decoupled from facebook.  OfflineId: " + this.m_offlineModeId);
                    }
                }
                if (_loc_5)
                {
                    this.m_needsLogin = false;
                    this.m_offlineMode = true;
                }
                else
                {
                    this.m_facebook.addEventListener(FacebookEvent.WAITING_FOR_LOGIN, this.onWaitingForLogin);
                    this.m_facebook.addEventListener(FacebookEvent.CONNECT, this.onConnect);
                    this.m_facebook.login(true);
                    this.m_needsLogin = true;
                }
            }
            return;
        }//end

        public boolean  needsLogin ()
        {
            return this.m_needsLogin;
        }//end

        public Facebook  facebook ()
        {
            return this.m_facebook;
        }//end

        private void  onWaitingForLogin (FacebookEvent event )
        {
            this.m_waitingForLogin = true;
            this.m_facebook.removeEventListener(FacebookEvent.WAITING_FOR_LOGIN, this.onWaitingForLogin);
            return;
        }//end

        public void  validateLogin ()
        {
            if (this.m_waitingForLogin)
            {
                this.m_facebookSession.refreshSession();
            }
            return;
        }//end

        private void  onConnect (FacebookEvent event )
        {
            if (event && event.data)
            {
                this.m_facebook.removeEventListener(FacebookEvent.CONNECT, this.onConnect);
                dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.LOGIN_COMPLETE));
            }
            return;
        }//end

        public void  getUser ()
        {
            Object facebookData ;
            String key ;
            FacebookCall getUserCall ;
            try
            {
                facebookData = ExternalInterface.call("getUserInfo");
                if (facebookData != null && facebookData.hasOwnProperty("uid"))
                {
                    this.m_facebookUser = new FacebookUser();
                    facebookData.put("uid",  facebookData.get("snuid"));


                    for(int i0 = 0; i0 < facebookData.size(); i0++)
                    {
                    	key = facebookData.get(i0);


                        if (key != "snuid")
                        {
                            this.m_facebookUser.put(key,  facebookData.get(key));
                        }
                    }
                }
            }
            catch (err:Error)
            {
                if (err)
                {
                    GlobalEngine.log("Facebook", "getUserInfo threw exception: " + err.message);
                }
            }
            if (this.m_facebookUser != null && this.m_facebookUser.uid == "")
            {
                this.m_facebookUser = null;
            }
            if (this.m_facebookUser == null && !this.m_offlineMode)
            {
                GlobalEngine.log("Facebook", "Unable to load user info from JS, using REST...");
                if (this.m_facebook.uid != null)
                {
                    getUserCall = this.m_facebook.post(new GetInfo([this.m_facebook.uid], ["uid", "name", "first_name", "pic_square", "sex"]));
                    getUserCall.addEventListener(FacebookEvent.COMPLETE, this.onGetUser);
                }
                else
                {
                    ErrorManager.addError("Facebook uid instanceof null in getUser", ErrorManager.ERROR_FAILED_TO_LOAD);
                }
            }
            else
            {
                dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_USER_COMPLETE));
            }
            this.getExtendedPermissions();
            return;
        }//end

        protected void  getExtendedPermissions ()
        {
            _loc_1 = this.m_facebook.post(new HasAppPermission("publish_stream",this.m_facebook.uid ));
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onGetAppPermissionComplete);
            _loc_2 = this.m_facebook.post(new HasAppPermission("email",this.m_facebook.uid ));
            _loc_2.addEventListener(FacebookEvent.COMPLETE, this.onGetAppEmailPermissionComplete);
            return;
        }//end

        private void  onGetAppPermissionComplete (FacebookEvent event )
        {
            if (event && event.success && event.data)
            {
                m_hasPublishPermissions = (event.data as BooleanResultData).value;
            }
            return;
        }//end

        private void  onGetAppEmailPermissionComplete (FacebookEvent event )
        {
            if (event && event.success && event.data)
            {
                m_hasEmailPermission = (event.data as BooleanResultData).value;
            }
            return;
        }//end

        private void  onGetUser (FacebookEvent event )
        {
            if (event && event.data && GetInfoData(event.data).userCollection.getItemAt(0) != null)
            {
                this.m_facebookUser = FacebookUser(GetInfoData(event.data).userCollection.getItemAt(0));
                dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_USER_COMPLETE));
            }
            return;
        }//end

         public String  getLoggedInUserId ()
        {
            String _loc_1 =null ;
            if (this.m_facebook)
            {
                _loc_1 = this.m_facebook.uid;
            }
            else if (this.m_offlineMode)
            {
                _loc_1 = this.m_offlineModeId.toString();
            }
            return _loc_1;
        }//end

         public SocialNetworkUser  getLoggedInUser ()
        {
            return this.toSocialNetworkUser(this.m_facebookUser);
        }//end

        public void  getFriendList ()
        {
            FacebookCall getFriendListCall ;
            try
            {
                this.m_facebookFriends = ExternalInterface.call("getFriendData");
            }
            catch (err:Error)
            {
                if (err)
                {
                    GlobalEngine.log("Facebook", "getFriendData threw exception: " + err.message);
                }
            }
            if (this.m_facebookFriends == null && this.m_offlineMode == false)
            {
                GlobalEngine.log("Facebook", "Unable to load friend data from JS, using REST...");
                getFriendListCall = this.m_facebook.post(new GetFriends());
                getFriendListCall.addEventListener(FacebookEvent.COMPLETE, this.onGetFriendList);
            }
            else
            {
                dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_FRIENDS_COMPLETE));
            }
            return;
        }//end

        private void  onGetFriendList (FacebookEvent event )
        {
            int _loc_4 =0;
            _loc_2 = GetFriendsData(event.data);
            Array _loc_3 =new Array();
            if (_loc_2 != null)
            {
                _loc_4 = 0;
                while (_loc_4 < _loc_2.friends.length())
                {

                    _loc_3.push(_loc_2.friends.getItemAt(_loc_4).uid);
                    _loc_4++;
                }
                this.getFriends(_loc_3);
            }
            return;
        }//end

        protected void  getFriends (Array param1 )
        {
            _loc_2 = this.m_facebook.post(new GetInfo(param1 ,.get( "uid","name","first_name","pic_square","sex") ));
            _loc_2.addEventListener(FacebookEvent.COMPLETE, this.onGetFriends);
            return;
        }//end

        private void  onGetFriends (FacebookEvent event )
        {
            int _loc_3 =0;
            FacebookUser _loc_4 =null ;
            this.m_facebookFriends = new Array();
            _loc_2 = GetInfoData(event.data);
            if (_loc_2 != null)
            {
                _loc_3 = 0;
                while (_loc_3 < _loc_2.userCollection.length())
                {

                    _loc_4 =(FacebookUser) _loc_2.userCollection.source.get(_loc_3);
                    this.m_facebookFriends.push(_loc_4);
                    _loc_3++;
                }
            }
            dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_FRIENDS_COMPLETE));
            return;
        }//end

        public void  getAppFriends ()
        {
            FacebookCall getAppUsers ;
            try
            {
                this.m_facebookAppFriends = ExternalInterface.call("getAppFriendIds");
            }
            catch (err:Error)
            {
                if (err)
                {
                    GlobalEngine.log("Facebook", "getAppFriendIds threw exception: " + err.message);
                }
            }
            if (this.m_facebookAppFriends == null && this.m_offlineMode == false)
            {
                GlobalEngine.log("Facebook", "Unable to load appFriendIds from JS, using REST...");
                getAppUsers = this.m_facebook.post(new GetAppUsers());
                getAppUsers.addEventListener(FacebookEvent.COMPLETE, this.onGetAppFriends);
            }
            else
            {
                dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE));
            }
            return;
        }//end

        protected void  onGetAppFriends (FacebookEvent event )
        {
            _loc_2 = GetAppUserData(event.data);
            if (_loc_2 != null)
            {
                this.m_facebookAppFriends = _loc_2.uids;
            }
            dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_APP_FRIENDS_COMPLETE));
            return;
        }//end

         public Array  getFriendUsers ()
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            SocialNetworkUser _loc_4 =null ;
            Array _loc_1 =new Array();
            if (this.m_facebookFriends)
            {
                _loc_2 = this.m_facebookFriends;
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    _loc_4 = this.toSocialNetworkUser(_loc_2.get(_loc_3));
                    _loc_1.push(_loc_4);
                    _loc_3++;
                }
            }
            return _loc_1;
        }//end

         public Array  getAppFriendsIds ()
        {
            return this.m_facebookAppFriends;
        }//end

         public void  showFeedDialog (String param1 ,Object param2 ,Array param3 ,Function param4 =null )
        {
            bundleId = param1;
            bundleData = param2;
            targetIds = param3;
            closeCallback = param4;
            try
            {
                if (ExternalInterface.available)
                {
                    if (GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN)
                    {
                        GlobalEngine.stage.displayState = StageDisplayState.NORMAL;
                    }
                    ExternalInterface.call("showFeedDialog", bundleId, bundleData, targetIds, closeCallback);
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
                if (closeCallback != null)
                {
                    this.closeCallback();
                }
            }
            return;
        }//end

         public void  streamPublish (Object param1 ,Object param2 ,String param3 ,String param4 =null ,Function param5 =null ,boolean param6 =false ,String param7 ="")
        {
            attachment = param1;
            actionLink = param2;
            targetId = param3;
            userMessagePrompt = param4;
            closeCallback = param5;
            autoPublish = param6;
            userMessage = param7;
            try
            {
                if (ExternalInterface.available)
                {
                    if (GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN)
                    {
                        GlobalEngine.stage.displayState = StageDisplayState.NORMAL;
                    }
                    ExternalInterface.call("streamPublish", attachment, actionLink, targetId, userMessagePrompt, userHasStreamPermissions());
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
                if (closeCallback != null)
                {
                    this.closeCallback();
                }
            }
            return;
        }//end

         public void  showStreamPermissions (Function param1)
        {
            callback = param1;
            super.showStreamPermissions(callback);
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call("showStreamPermissions");
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
            }
            return;
        }//end

         public void  showBookmarkDialog ()
        {
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call("showBookmarkDialog");
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
            }
            return;
        }//end

         public void  showEmailPermission (Function param1)
        {
            callback = param1;
            super.showEmailPermission(callback);
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call("showEmailPermission");
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
            }
            return;
        }//end

        protected SocialNetworkUser  toSocialNetworkUser (Object param1 )
        {
            SocialNetworkUser _loc_2 =null ;
            if (this.m_offlineMode)
            {
                _loc_2 = new SocialNetworkUser(this.m_offlineModeId, Constants.NO_NETWORK);
                _loc_2.firstName = "Decoupled";
                _loc_2.name = "Decoupled";
                _loc_2.picture = "";
            }
            else
            {
                if (param1 instanceof FacebookUser)
                {
                    _loc_2 = new SocialNetworkUser(param1.uid, Constants.FACEBOOK_NETWORK);
                }
                else
                {
                    _loc_2 = new SocialNetworkUser(param1.snuid, Constants.FACEBOOK_NETWORK);
                }
                _loc_2.firstName = param1.first_name;
                _loc_2.name = param1.name;
                _loc_2.picture = param1.pic_square;
                _loc_2.gender = param1.sex;
                _loc_2.locale = param1.locale;
            }
            return _loc_2;
        }//end

    }



