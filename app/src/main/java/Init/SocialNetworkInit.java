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

import Classes.*;
import Engine.Init.*;
//import flash.events.*;

    public class SocialNetworkInit extends InitializationAction
    {
        protected boolean m_skipSN =false ;
        public static  String INIT_ID ="SocialNetworkInit";

        public  SocialNetworkInit (boolean param1 )
        {
            super(INIT_ID);
            this.m_skipSN = param1;
            addDependency(GameSettingsInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            if (this.m_skipSN)
            {
                Global.player = new Player(null);
                dispatchEvent(new Event(Event.COMPLETE));
            }
            return;
        }//end  

    }



