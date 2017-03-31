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
//import flash.display.*;
//import flash.external.*;

    public class SNAPISocialNetwork extends SocialNetwork
    {
        protected Object m_snapiUser ;
        protected Array m_friends ;
        protected Array m_appFriends ;
        protected boolean m_offlineMode ;
        protected double m_offlineModeId ;

        public  SNAPISocialNetwork (String param1 ,String param2 )
        {
            double _loc_6 =0;
            String _loc_3 =null ;
            _loc_4 = GlobalEngine.getFlashVars ();
            boolean _loc_5 =false ;
            if (_loc_4.decouple_from_facebook)
            {
                GlobalEngine.log("SNAPI", "Attempting to decouple from SNAPI");
                _loc_6 = parseFloat(_loc_4.decoupled_facebook_id);
                if (isNaN(_loc_6))
                {
                    GlobalEngine.log("SNAPI", "Can\'t decouple from SNAPI, invalid ID");
                    _loc_5 = false;
                }
                else
                {
                    this.m_offlineModeId = _loc_6;
                    _loc_5 = true;
                    GlobalEngine.log("SNAPI", "Succesfully decoupled from SNAPI.  OfflineId: " + this.m_offlineModeId);
                }
            }
            if (_loc_5)
            {
                this.m_offlineMode = true;
            }
            return;
        }//end

        public void  getUser ()
        {
            Object userData ;
            String key ;
            try
            {
                userData = ExternalInterface.call("getUserInfo");
                if (userData != null && userData.hasOwnProperty("zid"))
                {
                    this.m_snapiUser = new Object();
                    int _loc_2 =0;
                    Object _loc_3 =userData ;
                    this.m_snapiUser = userData;

                    for(int i0 = 0; i0 < userData.size(); i0++)
                    {
                    		key = userData.get(i0);

                    //    this.m_snapiUser.put(key,  userData.get(key));
                    //}
                }
            }
            catch (err:Error)
            {
            }
            if (this.m_snapiUser != null && this.m_snapiUser.zid == 0)
            {
                this.m_snapiUser = null;
            }
            if (this.m_snapiUser == null && !this.m_offlineMode)
            {
                GlobalEngine.error("SNAPI", "Unable to load SNAPI user info from JS.");
                ErrorManager.addError("Unable to load SNAPI user info from JS.", ErrorManager.ERROR_FAILED_TO_LOAD);
            }
            dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_USER_COMPLETE));
            return;
        }//end

        protected void  getExtendedPermissions ()
        {
            return;
        }//end

         public String  getLoggedInUserId ()
        {
            String _loc_1 =null ;
            if (this.m_snapiUser)
            {
                _loc_1 = this.m_snapiUser.zid.toString();
            }
            else if (this.m_offlineMode)
            {
                _loc_1 = this.m_offlineModeId.toString();
            }
            return _loc_1;
        }//end

         public SocialNetworkUser  getLoggedInUser ()
        {
            return this.toSocialNetworkUser(this.m_snapiUser);
        }//end

        public void  getFriendList ()
        {
            try
            {
                this.m_friends = ExternalInterface.call("getFriendData");
            }
            catch (err:Error)
            {
            }
            if (this.m_friends == null && this.m_offlineMode == false)
            {
                GlobalEngine.error("SNAPI", "Unable to load friend data from JS");
                ErrorManager.addError("Unable to load SNAPI friend data from JS.", ErrorManager.ERROR_FAILED_TO_LOAD);
            }
            dispatchEvent(new SocialNetworkEvent(SocialNetworkEvent.GET_FRIENDS_COMPLETE));
            return;
        }//end

        public void  getAppFriends ()
        {
            try
            {
                this.m_appFriends = ExternalInterface.call("getAppFriendIds");
            }
            catch (err:Error)
            {
            }
            if (this.m_appFriends == null && this.m_offlineMode == false)
            {
                GlobalEngine.log("SNAPI", "Unable to load appFriendIds from JS");
                ErrorManager.addError("Unable to load SNAPI appFriendIds from JS", ErrorManager.ERROR_FAILED_TO_LOAD);
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
            if (this.m_friends)
            {
                _loc_2 = this.m_friends;
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
            return this.m_appFriends;
        }//end

         public void  streamPublish (Object param1 ,Object param2 ,String param3 ,String param4 =null ,Function param5 =null ,boolean param6 =false ,String param7 ="")
        {
            throw new Error("Cannot use streamPublish with SNAPI, please use snapiStreamPublish");
        }//end

        public void  snapiStreamPublish (Object param1 )
        {
            data = param1;
            try
            {
                if (ExternalInterface.available)
                {
                    if (GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN)
                    {
                        GlobalEngine.stage.displayState = StageDisplayState.NORMAL;
                    }
                    ExternalInterface.call("snapiStreamPublish", data);
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
            }
            return;
        }//end

        public void  snapiAwardXp (double param1 )
        {
            amount = param1;
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call("snapiAwardXp", amount);
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
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
                _loc_2 = new SocialNetworkUser(param1.zid);
                _loc_2.firstName = param1.first_name;
                _loc_2.name = param1.name;
                _loc_2.picture = param1.pic_square;
                _loc_2.gender = param1.sex;
                _loc_2.locale = param1.locale;
            }
            return _loc_2;
        }//end

    }



