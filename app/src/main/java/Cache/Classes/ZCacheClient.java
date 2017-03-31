package Cache.Classes;

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

//import flash.net.*;
//import flash.utils.*;
import Cache.Interfaces.*;

    final public class ZCacheClient implements IZCache
    {
        private Object m_zCache ;

        public  ZCacheClient (Object param1 )
        {
            this.m_zCache = param1;
            return;
        }//end

        public Error  initError ()
        {
            return this.m_zCache.initError;
        }//end

        public Error  lastFlushError ()
        {
            return this.m_zCache.lastFlushError;
        }//end

        public int  inactivityFlushTimeout ()
        {
            return this.m_zCache.inactivityFlushTimeout;
        }//end

        public void  inactivityFlushTimeout (int param1 )
        {
            this.m_zCache.inactivityFlushTimeout = param1;
            return;
        }//end

        public String  namespace ()
        {
            return this.m_zCache.namespace;
        }//end

        public boolean  init (String param1 )
        {
            return this.m_zCache.init(param1);
        }//end

        public boolean  flush ()
        {
            return this.m_zCache.flush();
        }//end

        public boolean  allowed ()
        {
            return this.m_zCache.allowed;
        }//end

        public void  promptForStorage (Function param1 ,Function param2 )
        {
            this.m_zCache.promptForStorage(param1, param2);
            return;
        }//end

        public boolean  put (String param1 ,Object param2 ,int param3 )
        {
            return this.m_zCache.put(param1, param2, param3);
        }//end

        public Object (String param1 )
        {
            return this.m_zCache.get(param1);
        }//end

        public boolean  containsKey (String param1 )
        {
            return this.m_zCache.containsKey(param1);
        }//end

        public boolean  clear ()
        {
            return this.m_zCache.clear();
        }//end

        public Object remove (String param1 )
        {
            return this.m_zCache.remove(param1);
        }//end

        public Object  stats ()
        {
            return this.m_zCache.stats;
        }//end

    }



