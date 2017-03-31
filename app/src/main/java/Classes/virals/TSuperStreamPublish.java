package Classes.virals;

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
    public class TSuperStreamPublish extends TCityStreamPublish
    {

        public  TSuperStreamPublish (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,String param6 =null ,Function param7 =null ,boolean param8 =false )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.superStreamPublish", m_type, m_data, m_targetId, m_clickthorugh_params, m_subtype, m_userMessage);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            int _loc_2 =0;
            if (param1.get("lastSuperFeedPostError") != Global.player.lastSuperFeedPostError)
            {
                Global.player.lastSuperFeedPostError = param1.get("lastSuperFeedPostError");
            }
            if (Global.player.lastSuperFeedPostError + Global.player.SUPER_FEED_DELAY > GlobalEngine.getTimer() / 1000)
            {
                ViralManager.resetFreezeTimer("FrameLoading_Viral");
                if (GlobalEngine.socialNetwork instanceof SNAPISocialNetwork)
                {
                    (GlobalEngine.socialNetwork as SNAPISocialNetwork).snapiStreamPublish(param1);
                }
                else
                {
                    GlobalEngine.socialNetwork.streamPublish(param1.attachment, param1.action_link, param1.target_id, param1.user_message_prompt, null, param1.auto_publish, param1.message);
                }
            }
            if (m_callback != null)
            {
                _loc_2 = param1.auto_publish ? (ViralManager.STREAM_PUBLISH_AUTO) : (ViralManager.STREAM_PUBLISH_DEFAULT);
                m_callback(_loc_2);
            }
            return;
        }//end

    }



