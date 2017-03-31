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
import Engine.Transactions.*;

    public class TCityStreamPublish extends TStreamPublish
    {
        protected String m_userMessage ;
        protected Function m_callback ;
        protected boolean m_autoPublish ;

        public  TCityStreamPublish (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,String param6 =null ,Function param7 =null ,boolean param8 =false )
        {
            this.m_userMessage = param6;
            this.m_autoPublish = param8;
            this.m_callback = param7;
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.streamPublish", m_type, m_data, m_targetId, m_clickthorugh_params, m_subtype, this.m_autoPublish, this.m_userMessage);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            int _loc_2 =0;
            if (GlobalEngine.socialNetwork instanceof SNAPISocialNetwork)
            {
                (GlobalEngine.socialNetwork as SNAPISocialNetwork).snapiStreamPublish(param1);
            }
            else
            {
                GlobalEngine.socialNetwork.streamPublish(param1.attachment, param1.action_link, param1.target_id, param1.user_message_prompt, null, param1.auto_publish, param1.message);
            }
            if (this.m_callback != null)
            {
                _loc_2 = param1.auto_publish ? (ViralManager.STREAM_PUBLISH_AUTO) : (ViralManager.STREAM_PUBLISH_DEFAULT);
                this.m_callback(_loc_2);
            }
            return;
        }//end

        public String  getViralType ()
        {
            return m_type;
        }//end

    }


