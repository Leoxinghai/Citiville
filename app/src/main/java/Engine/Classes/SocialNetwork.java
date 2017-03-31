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
import Engine.Managers.*;
import Engine.Transactions.*;
//import flash.events.*;

    public class SocialNetwork extends EventDispatcher
    {
        protected double m_sigTime =0;
        private double m_lastFeedTime =0;
        protected boolean m_hasPublishPermissions =false ;
        protected boolean m_hasEmailPermission =false ;
        protected Function m_closePermissionDialogCallback ;
        protected Function m_closeEmailPermissionDialogCallback ;
public static  double SHORT_STORY_TIMEOUT =2000;

        public  SocialNetwork ()
        {
            return;
        }//end

        public String  getLoggedInUserId ()
        {
            return null;
        }//end

        public SocialNetworkUser  getLoggedInUser ()
        {
            return null;
        }//end

        public void  redirect (String param1 ,Object param2 ,String param3 ="_parent",boolean param4 =true )
        {
            if (param4)
            {
                param1 = Config.SN_APP_URL + param1;
            }
            Utilities.launchURL(param1, param3, param2);
            return;
        }//end

        public Array  getFriendUsers ()
        {
            return new Array();
        }//end

        public Array  getAppFriendsIds ()
        {
            return new Array();
        }//end

        public double  getSigTime ()
        {
            return this.m_sigTime;
        }//end

        public void  sendNotification (String param1 ,Array param2 ,Array param3 )
        {
            TransactionManager.addTransaction(new TSendNotification(param1, param2, param3));
            return;
        }//end

        public void  publishFeedStory (String param1 ,Object param2 ,Array param3 ,boolean param4 ,Function param5 =null ,int param6 =0,boolean param7 =false ,boolean param8 =false ,String param9 ="")
        {
            TransactionManager.addTransaction(new TPublishUserAction(param1, param2, param3, param4, param5, param6, param7), true, true);
            return;
        }//end

        public Array  getDefaultFeedImages ()
        {
            return new Array();
        }//end

        public void  showFeedDialog (String param1 ,Object param2 ,Array param3 ,Function param4 =null )
        {
            return;
        }//end

        public void  streamPublish (Object param1 ,Object param2 ,String param3 ,String param4 =null ,Function param5 =null ,boolean param6 =false ,String param7 ="")
        {
            return;
        }//end

        public void  showBookmarkDialog ()
        {
            return;
        }//end

        public void  onFeedClosed ()
        {
            this.m_lastFeedTime = GlobalEngine.getTimer();
            if (GlobalEngine.feedCallbackTimer)
            {
                GlobalEngine.feedCallbackTimer.stop();
                GlobalEngine.feedCallbackTimer = null;
            }
            if (GlobalEngine.onFeedCloseCallback != null)
            {
                GlobalEngine.onFeedCloseCallback(null);
                GlobalEngine.onFeedCloseCallback = null;
            }
            return;
        }//end

        public void  showStreamPermissions (Function param1)
        {
            this.m_closePermissionDialogCallback = param1;
            return;
        }//end

        public void  onPermissionDialogClosed (boolean param1 )
        {
            this.m_hasPublishPermissions = param1;
            if (this.m_closePermissionDialogCallback != null)
            {
                this.m_closePermissionDialogCallback();
                this.m_closePermissionDialogCallback = null;
            }
            return;
        }//end

        public boolean  userHasStreamPermissions ()
        {
            return this.m_hasPublishPermissions;
        }//end

        public void  showEmailPermission (Function param1)
        {
            this.m_closeEmailPermissionDialogCallback = param1;
            return;
        }//end

        public void  onEmailPermissionDialogClosed (boolean param1 )
        {
            this.m_hasEmailPermission = param1;
            if (this.m_closeEmailPermissionDialogCallback != null)
            {
                this.m_closeEmailPermissionDialogCallback(param1);
                this.m_closeEmailPermissionDialogCallback = null;
            }
            return;
        }//end

        public void  haveEmailPermission (boolean param1 )
        {
            this.m_hasEmailPermission = param1;
            return;
        }//end

        public boolean  userHasEmailPermission ()
        {
            return this.m_hasEmailPermission;
        }//end

    }


