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

//import flash.net.*;
    public class PackedURLResourceLoader extends URLResourceLoader
    {
        private String m_assetUrl ;

        public void  PackedURLResourceLoader (String param1 ,String param2 ,int param3 =0,Function param4 =null ,Function param5 =null )
        {
            this.m_assetUrl = param2;
            super(param1, param3, param4, param5);
            return;
        }//end  

         public boolean  isPackedResource ()
        {
            return true;
        }//end  

         protected double  timeoutLength ()
        {
            return LoaderConstants.PACKED_TIMEOUT_LENGTH;
        }//end  

         protected int  retryCount ()
        {
            return archiveLoader.retryCount;
        }//end  

         protected void  retryCount (int param1 )
        {
            this.archiveLoader.retryCount = param1;
            return;
        }//end  

         public String  getURL ()
        {
            return this.m_assetUrl;
        }//end  

         protected void  makeLoader ()
        {
            m_loader = ArchiveLoaderFactory.createURLLoader(m_url, this.m_assetUrl);
            return;
        }//end  

         public int  getbytesTotal ()
        {
            return ((URLLoader)m_loader).bytesTotal;
        }//end  

         protected void  chooseLoad ()
        {
            ((URLLoader)m_loader).load(m_urlRequest);
            return;
        }//end  

         protected void  chooseClose ()
        {
            ((URLLoader)m_loader).close();
            return;
        }//end  

         protected void  logError (String param1 ,int param2 ,Object param3 )
        {
            if (!this.archiveLoader.errorLogged)
            {
                this.archiveLoader.errorLogged = true;
                super.logError(param1, param2, param3);
            }
            return;
        }//end  

        private IArchiveLoader  archiveLoader ()
        {
            return (IArchiveLoader)m_loader;
        }//end  

    }



