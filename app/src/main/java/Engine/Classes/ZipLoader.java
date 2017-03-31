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

import Engine.Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;

    public class ZipLoader extends Loader implements IArchiveLoader
    {
        private String m_filename ;
        private String m_url ;
        private LoaderContext m_context ;
        private boolean m_loading ;

        public  ZipLoader (String param1 )
        {
            this.m_filename = param1;
            this.m_url = null;
            this.m_context = null;
            this.m_loading = false;
            return;
        }//end

        public int  retryCount ()
        {
            return ZipHelper.instance.getRetryCount(this.m_url);
        }//end

        public void  retryCount (int param1 )
        {
            ZipHelper.instance.setRetryCount(this.m_url, param1);
            return;
        }//end

        public boolean  errorLogged ()
        {
            return ZipHelper.instance.getErrorLogged(this.m_url);
        }//end

        public void  errorLogged (boolean param1 )
        {
            ZipHelper.instance.setErrorLogged(this.m_url, param1);
            return;
        }//end

         public void  load (URLRequest param1 ,LoaderContext param2 )
        {
            if (this.m_loading)
            {
                this.close();
            }
            this.m_url = param1.url;
            this.m_context = param2;
            this.m_loading = true;
            ZipHelper.instance.load(param1, this.m_filename, {onComplete:this.onComplete, onHttpStatus:this.defaultHandler, onError:this.defaultHandler, onSecurityError:this.defaultHandler});
            return;
        }//end

         public void  close ()
        {
            if (!this.m_loading)
            {
                throw new Error("Called close without first calling load");
            }
            if (ZipHelper.instance.isLoaded(this.m_url, this.m_filename))
            {
                super.close();
            }
            else
            {
                ZipHelper.instance.close(this.m_url, this.m_filename);
            }
            this.m_loading = false;
            return;
        }//end

        private void  onComplete (ZipEvent event )
        {
            LoaderContext _loc_2 =null ;
            if (this.m_context != null)
            {
                _loc_2 = new LoaderContext();
                _loc_2.applicationDomain = this.m_context.applicationDomain;
            }
            super.loadBytes(event.data, _loc_2);
            return;
        }//end

        private void  defaultHandler (Event event )
        {
            super.dispatchEvent(event.clone());
            return;
        }//end

    }



