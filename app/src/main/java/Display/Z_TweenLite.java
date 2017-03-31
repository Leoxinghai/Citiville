package Display;

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

import com.greensock.*;
import com.greensock.core.*;
//import flash.utils.*;

    public class Z_TweenLite extends TweenLite
    {
        private static SimpleTimeline m_rootTimeLine =null ;
        private static SimpleTimeline m_rootFrameTimeLine =null ;

        public  Z_TweenLite (Object param1 ,double param2 ,Object param3 )
        {
            if (m_rootTimeLine == null)
            {
                m_rootTimeLine = new Z_SimpleTimeLine(null);
                m_rootFrameTimeLine = new Z_SimpleTimeLine(null);
                m_rootTimeLine.cachedStartTime = getTimer() * 0.001;
                m_rootFrameTimeLine.cachedStartTime = rootFrame;
                m_rootTimeLine.autoRemoveChildren = true;
                m_rootFrameTimeLine.autoRemoveChildren = true;
                TweenLite.rootTimeline = m_rootTimeLine;
                TweenLite.rootFramesTimeline = m_rootFrameTimeLine;
            }
            super(param1, param2, param3);
            return;
        }//end  

        public static Z_TweenLite  to (Object param1 ,double param2 ,Object param3 )
        {
            return new Z_TweenLite(param1, param2, param3);
        }//end  

    }



