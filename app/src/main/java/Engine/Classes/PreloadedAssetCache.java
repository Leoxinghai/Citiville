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
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class PreloadedAssetCache
    {
        private Dictionary m_pendingLoads =null ;
        private Dictionary m_completedLoads =null ;
        private static PreloadedAssetCache s_instance =new PreloadedAssetCache ;

        public  PreloadedAssetCache ()
        {
            this.m_pendingLoads = new Dictionary();
            this.m_completedLoads = new Dictionary();
            return;
        }//end

        public void  addToCache (String param1 )
        {
            URLLoader _loc_2 =new URLLoader ();
            _loc_2.dataFormat = URLLoaderDataFormat.BINARY;
            _loc_2.addEventListener(Event.COMPLETE, Utilities.bind(this.onComplete, null, [param1]));
            _loc_2.addEventListener(IOErrorEvent.IO_ERROR, Utilities.bind(this.onError, null, [param1]));
            _loc_2.addEventListener(SecurityErrorEvent.SECURITY_ERROR, Utilities.bind(this.onError, null, [param1]));
            _loc_2.load(new URLRequest(param1));
            this.m_pendingLoads.put(param1,  new Array());
            return;
        }//end

        public boolean  isInCache (String param1 )
        {
            if (this.m_pendingLoads.hasOwnProperty(param1) || this.m_completedLoads.hasOwnProperty(param1))
            {
                return true;
            }
            return false;
        }//end

        public void  loadFromCache (String param1 ,Function param2 ,Function param3 )
        {
            if (this.m_completedLoads.hasOwnProperty(param1))
            {
                setTimeout(Utilities.bind(this.onDataAvailable, null, [param2, this.m_completedLoads[param1]]), 0);
            }
            else if (this.m_pendingLoads.hasOwnProperty(param1))
            {
                this.m_pendingLoads.get(param1).push({completeCallback:param2, failureCallback:param3});
            }
            else
            {
                param3();
            }
            return;
        }//end

        private void  onComplete (String param1 ,Event param2 )
        {
            ByteArray _loc_3 =null ;
            Object _loc_4 =null ;
            if (this.m_pendingLoads.hasOwnProperty(param1))
            {
                _loc_3 = param2.target.data;
                for(int i0 = 0; i0 < this.m_pendingLoads.get(param1).size(); i0++)
                {
                	_loc_4 = this.m_pendingLoads.get(param1).get(i0);

                    this.onDataAvailable(_loc_4.completeCallback, _loc_3);
                }
                this.clearPending(param1);
                _loc_3.position = 0;
                this.m_completedLoads.put(param1,  _loc_3);
            }
            return;
        }//end

        private void  onError (String param1 ,Event param2 )
        {
            Object _loc_3 =null ;
            if (this.m_pendingLoads.hasOwnProperty(param1))
            {
                for(int i0 = 0; i0 < this.m_pendingLoads.get(param1).size(); i0++)
                {
                	_loc_3 = this.m_pendingLoads.get(param1).get(i0);

                    _loc_3.failureCallback();
                }
                this.clearPending(param1);
            }
            return;
        }//end

        private void  onDataAvailable (Function param1 ,ByteArray param2 )
        {
            param2.position = 0;
            param1(param2);
            return;
        }//end

        private void  clearPending (String param1 )
        {
            this.m_pendingLoads.put(param1,  null);
            delete this.m_pendingLoads.get(param1);
            return;
        }//end

        public static PreloadedAssetCache  instance ()
        {
            return s_instance;
        }//end

    }



