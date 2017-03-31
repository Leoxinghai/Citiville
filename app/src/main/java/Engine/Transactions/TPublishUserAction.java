package Engine.Transactions;

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
//import flash.utils.*;

    public class TPublishUserAction extends Transaction
    {
        protected String m_action ;
        protected Object m_bundleData ;
        protected boolean m_oneLine ;
        protected Array m_strTargetIds ;
        protected Function m_closeCallback =null ;
        protected boolean m_askPermissions ;
        protected boolean m_autoPublish =false ;
        protected String m_userMessage ="";
        protected Timer m_autoPublishTimer ;
public static  double FEED_CALLBACK_TIMEOUT =15;
public static  double AUTO_PUBLISH_DELAY =5000;
public static String m_category ="";
public static String m_subcategory ="";
public static String m_creative ="";

        public  TPublishUserAction (String param1 ,Object param2 ,Array param3 ,boolean param4 ,Function param5 =null ,int param6 =0,boolean param7 =false ,boolean param8 =false ,String param9 ="")
        {
            int _loc_10 =0;
            this.m_strTargetIds = new Array();
            this.m_action = param1;
            this.m_bundleData = param2;
            this.m_oneLine = param4;
            this.m_closeCallback = param5;
            this.m_askPermissions = param7;
            if (this.m_closeCallback != null)
            {
                if (param6 <= 0)
                {
                    param6 = FEED_CALLBACK_TIMEOUT;
                }
                if (GlobalEngine.feedCallbackTimer == null)
                {
                    GlobalEngine.feedCallbackTimer = new Timer(param6 * 1000);
                    GlobalEngine.feedCallbackTimer.addEventListener(TimerEvent.TIMER, this.onCloseCallback, false, 0, true);
                    GlobalEngine.feedCallbackTimer.start();
                }
                GlobalEngine.onFeedCloseCallback = this.onCloseCallback;
            }
            if (param3)
            {
                _loc_10 = 0;
                while (_loc_10 < param3.length())
                {

                    this.m_strTargetIds.push(param3.get(_loc_10).toString());
                    _loc_10++;
                }
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.publishUserAction", this.m_action, this.m_bundleData, this.m_strTargetIds, this.m_oneLine);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1.showFeedDialog && this.m_oneLine == false)
            {
                if (this.m_askPermissions && !GlobalEngine.socialNetwork.userHasStreamPermissions())
                {
                    GlobalEngine.socialNetwork.showStreamPermissions(this.onFinishStreamPermissions);
                }
                else
                {
                    m_category = param1.category;
                    m_subcategory = param1.subcategory;
                    m_creative = param1.creative;
                    this.streamPublish();
                }
            }
            return;
        }//end

        protected void  streamPublish ()
        {
            String _loc_1 =null ;
            if (this.m_strTargetIds.length > 0)
            {
                _loc_1 = this.m_strTargetIds.get(0).toString();
            }
            GlobalEngine.socialNetwork.streamPublish(m_rawResult.data.attachment, m_rawResult.data.actionLink, _loc_1, m_rawResult.data.userMessagePrompt, null, this.m_autoPublish, this.m_userMessage);
            return;
        }//end

        protected void  onFinishStreamPermissions ()
        {
            if (GlobalEngine.socialNetwork.userHasStreamPermissions())
            {
                this.m_autoPublishTimer = new Timer(AUTO_PUBLISH_DELAY, 1);
                this.m_autoPublishTimer.addEventListener(TimerEvent.TIMER, this.onPublishDelayComplete);
                this.m_autoPublishTimer.start();
            }
            else
            {
                this.streamPublish();
            }
            return;
        }//end

        protected void  onPublishDelayComplete (TimerEvent event )
        {
            if (this.m_autoPublishTimer != null)
            {
                this.m_autoPublishTimer.removeEventListener(TimerEvent.TIMER, this.onPublishDelayComplete);
                this.m_autoPublishTimer = null;
            }
            this.streamPublish();
            return;
        }//end

        protected void  onCloseCallback (TimerEvent event )
        {
            GlobalEngine.onFeedCloseCallback = null;
            GlobalEngine.socialNetwork.onFeedClosed();
            if (this.m_closeCallback != null)
            {
                this.m_closeCallback();
            }
            return;
        }//end

        public static String  category ()
        {
            return m_category;
        }//end

        public static String  subcategory ()
        {
            return m_subcategory;
        }//end

        public static String  creative ()
        {
            return m_creative;
        }//end

    }



