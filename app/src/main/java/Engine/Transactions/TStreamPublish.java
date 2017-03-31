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

import Engine.Classes.*;
    public class TStreamPublish extends Transaction
    {
        protected String m_type ;
        protected String m_subtype ;
        protected Object m_data ;
        protected String m_targetId ;
        protected Object m_clickthorugh_params ;

        public  TStreamPublish (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null )
        {
            this.m_type = param1;
            this.m_data = param2;
            this.m_targetId = param3;
            this.m_clickthorugh_params = param4;
            this.m_subtype = param5;
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.streamPublish", this.m_type, this.m_data, this.m_targetId, this.m_clickthorugh_params, this.m_subtype);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (GlobalEngine.socialNetwork instanceof SNAPISocialNetwork)
            {
                (GlobalEngine.socialNetwork as SNAPISocialNetwork).snapiStreamPublish(param1);
            }
            else
            {
                GlobalEngine.socialNetwork.streamPublish(param1.attachment, param1.action_link, param1.target_id, param1.user_message_prompt);
            }
            return;
        }//end

    }



