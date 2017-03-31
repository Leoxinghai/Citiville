package Cache.Init;

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

import Engine.Managers.*;
//import flash.system.*;
import Engine.Interfaces.*;

    public class ZCacheComponent implements IEngineComponent
    {
        protected Class m_initClass ;
        protected String m_namespace ;
        public static  String ZCACHE_URL_FLASHVAR ="zcache_url";
        public static  String EVICT_KEYS_FLASHVAR ="zcache_evict_keys";

        public  ZCacheComponent (String param1 ,Class param2 )
        {
            this.m_namespace = param1;
            this.m_initClass = param2 ? (param2) : (ZCacheInit);
            return;
        }//end

        public void  initialize ()
        {
            Array evictKeys ;
            String evictKeysStr ;
            zCacheUrl = (String)GlobalEngine.getFlashVar(ZCACHE_URL_FLASHVAR)
            if (zCacheUrl)
            {
                evictKeys;
                evictKeysStr =(String) GlobalEngine.getFlashVar(EVICT_KEYS_FLASHVAR);
                if (evictKeysStr && evictKeysStr != "")
                {
                    try
                    {
                        if (evictKeysStr.indexOf(",") > 0)
                        {
                            evictKeys = evictKeysStr.split(",");
                        }
                        else
                        {
                            evictKeys;
                        }
                    }
                    catch (e:Error)
                    {
                        GlobalEngine.error("ZCache", "Invalid zcache_evict_keys .get(value=\"" + evictKeysStr + "\")");
                        StatsManager.count("errors", "zcache", "parse_evict_keys", evictKeysStr);
                    }
                }
                Security.allowDomain("zcache.zgncdn.com");
                GlobalEngine.log("ZCache", "Adding " + this.m_initClass + "[zCacheUrl=" + zCacheUrl + ", namespace=" + this.m_namespace + "]");
                GlobalEngine.initializationManager.add(new this.m_initClass(zCacheUrl, this.m_namespace, evictKeys));
            }
            return;
        }//end

    }




