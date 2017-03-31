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

    public class TSendNotification extends Transaction
    {
        protected Array m_targetUserIds ;
        protected Array m_params ;
        protected String m_action ;

        public  TSendNotification (String param1 ,Array param2 ,Array param3 )
        {
            this.m_targetUserIds = new Array();
            this.m_params = new Array();
            this.m_action = param1;
            this.m_params = param2.concat();
            this.m_targetUserIds = param3.concat();
            return;
        }//end

         public void  perform ()
        {
            int _loc_2 =0;
            Array _loc_1 =new Array ();
            if (this.m_targetUserIds)
            {
                _loc_2 = 0;
                while (_loc_2 < this.m_targetUserIds.length())
                {

                    _loc_1.push(this.m_targetUserIds.get(_loc_2).toString());
                    _loc_2++;
                }
            }
            signedCall("UserService.sendUserNotification", this.m_action, this.m_params, _loc_1);
            return;
        }//end

    }



