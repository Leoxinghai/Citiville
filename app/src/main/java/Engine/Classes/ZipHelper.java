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
//import flash.utils.*;
//import flash.events.*;
import deng.fzip.*;
import Engine.Classes.*;
import Engine.*;
import Engine.Events.*;


    public class ZipHelper
    {
        private Dictionary m_zips ;
        private static ZipHelper s_instance =new ZipHelper ;

        public  ZipHelper ()
        {
            this.m_zips = new Dictionary();
            return;
        }//end

        public boolean  isLoaded (String param1 ,String param2 )
        {
            _loc_3 = this.getZipArchive(param1 );
            if (_loc_3 === null)
            {
                throw new Error("Called close without first calling load");
            }
            return _loc_3.isLoaded(param2);
        }//end

        public void  load (URLRequest param1 ,String param2 ,Object param3 )
        {
            _loc_4 = this.getZipArchive(param1.url );
            if (this.getZipArchive(param1.url) === null)
            {
                _loc_4 = new ZipArchive(param1);
                this.m_zips.put(param1.url,  _loc_4);
            }
            _loc_4.load(param2, param3);
            return;
        }//end

        public void  close (String param1 ,String param2 )
        {
            _loc_3 = this.getZipArchive(param1 );
            if (_loc_3 === null)
            {
                throw new Error("Called close without first calling load");
            }
            _loc_3.close(param2);
            return;
        }//end

        public int  getRetryCount (String param1 )
        {
            _loc_2 = this.getZipArchive(param1 );
            if (_loc_2 !== null)
            {
                return _loc_2.retryCount;
            }
            return 0;
        }//end

        public void  setRetryCount (String param1 ,int param2 )
        {
            _loc_3 = this.getZipArchive(param1 );
            if (_loc_3 !== null)
            {
                _loc_3.retryCount = param2;
            }
            return;
        }//end

        public boolean  getErrorLogged (String param1 )
        {
            _loc_2 = this.getZipArchive(param1 );
            if (_loc_2 !== null)
            {
                return _loc_2.errorLogged;
            }
            return false;
        }//end

        public void  setErrorLogged (String param1 ,boolean param2 )
        {
            _loc_3 = this.getZipArchive(param1 );
            if (_loc_3 !== null)
            {
                _loc_3.errorLogged = param2;
            }
            return;
        }//end

        private ZipArchive  getZipArchive (String param1 )
        {
            if (param1 !== null && this.m_zips.get(param1) !== undefined)
            {
                return this.m_zips.get(param1);
            }
            return null;
        }//end

        public static ZipHelper  instance ()
        {
            return s_instance;
        }//end

    }

import flash.net.*;
import flash.utils.*;
import flash.events.*;
import deng.fzip.*;
import Engine.Classes.*;
import Engine.*;
import Engine.Events.*;

class ZipArchive
    private FZip m_zip ;
    private URLRequest m_request ;
    private Dictionary m_callbacks ;
    private int m_retryCount ;
    private boolean m_errorLogged ;
    private boolean m_checkPreloadCache ;
    private boolean m_zipLoaded ;

     ZipArchive (URLRequest param1 )
    {
        this.m_zip = null;
        this.m_request = param1;
        this.m_callbacks = new Dictionary();
        this.m_retryCount = 0;
        this.m_errorLogged = false;
        this.m_checkPreloadCache = true;
        this.m_zipLoaded = false;
        this.initLoad(true);
        return;
    }//end

    public void  load (String param1 ,Object param2 )
    {
        FZipFile _loc_4 =null ;
        if (this.m_zip === null && this.m_retryCount >= LoaderConstants.MAX_RETRIES)
        {
            throw new Error("Trying to load when already over the retry max");
        }
        if (this.m_zip === null)
        {
            this.initLoad();
        }
        if (this.m_zipLoaded)
        {
            _loc_4 = this.m_zip.getFileByName(param1);
            if (_loc_4 !== null)
            {
                if (param2.onComplete !== null)
                {
                    setTimeout(Utilities.bind(param2.onComplete, null, [new ZipEvent(ZipEvent.FILE_LOADED, _loc_4.content)]), 0);
                }
                return;
            }
        }
        _loc_3 = this.m_callbacks.get(param1) ;
        if (_loc_3 === null)
        {
            _loc_3 = new Vector<Object>();
            this.m_callbacks.put(param1,  _loc_3);
        }
        _loc_3.push(param2);
        return;
    }//end

    public void  close (String param1 )
    {
        if (this.m_callbacks.get(param1) !== null)
        {
            delete this.m_callbacks.get(param1);
        }


            if (this.m_zip !== null)
            {
                this.m_zip.close();
                this.removeEventListeners();
                this.m_zip = null;
            }

        return;
    }//end

    public boolean  isLoaded (String param1 )
    {
        if (this.m_zip !== null && this.m_zip.getFileByName(param1) !== null)
        {
            return true;
        }
        return false;
    }//end

    public int  retryCount ()
    {
        return this.m_retryCount;
    }//end

    public void  retryCount (int param1 )
    {
        return;
    }//end

    public boolean  errorLogged ()
    {
        return this.m_errorLogged;
    }//end

    public void  errorLogged (boolean param1 )
    {
        this.m_errorLogged = param1;
        return;
    }//end

    private void  onComplete (Event event )
    {
        this.m_callbacks = new Dictionary();
        return;
    }//end

    private void  onFileLoaded (FZipEvent event )
    {
        Object _loc_4 =null ;
        _loc_2 = event.file.filename ;
        _loc_3 = this.m_callbacks.get(_loc_2) ;
        if (_loc_3 !== null)
        {
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4.onComplete !== null)
                {
                    _loc_4.onComplete(new ZipEvent(ZipEvent.FILE_LOADED, event.file.content));
                }
            }
            delete this.m_callbacks.get(_loc_2);
        }
        return;
    }//end

    private void  onHttpStatus (HTTPStatusEvent event )
    {
        this.handleCallback("onHttpStatus", event);
        return;
    }//end

    private void  onError (IOErrorEvent event )
    {
        this.onLoadFail();
        this.handleCallback("onError", event);
        return;
    }//end

    private void  onParseError (FZipErrorEvent event )
    {
        this.onError(new IOErrorEvent(event.type, event.bubbles, event.cancelable, event.text));
        return;
    }//end

    private void  onSecurityError (IOErrorEvent event )
    {
        this.onLoadFail();
        this.handleCallback("onSecurityError", event);
        return;
    }//end

    private void  handleCallback (String param1 ,Event param2 )
    {
        Vector _loc_3.<Object >=null ;
        Object _loc_4 =null ;
        for(int i0 = 0; i0 < this.m_callbacks.size(); i0++)
        {
        	_loc_3 = this.m_callbacks.get(i0);

            if (_loc_3 !== null)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    if (_loc_4.hasOwnProperty(param1))
                    {
                        _loc_9 = _loc_4;
                        _loc_9._loc_4.get(param1)(param2.clone());
                    }
                }
            }
        }
        return;
    }//end

    private void  initLoad (boolean param1 =false )
    {
        if (this.m_zip !== null)
        {
            throw new Error("FZip instance already exists");
        }
        if (!param1)
        {
            this.m_retryCount++;
        }
        this.m_zip = new FZip();
        if (this.m_checkPreloadCache && PreloadedAssetCache.instance.isInCache(this.m_request.url))
        {
            PreloadedAssetCache.instance.loadFromCache(this.m_request.url, this.onPreloadCacheLoad, this.onPreloadCacheError);
        }
        else
        {
            this.addEventListeners();
            this.m_zip.load(this.m_request);
            this.m_zipLoaded = true;
        }
        return;
    }//end

    private void  onLoadFail ()
    {
        this.removeEventListeners();
        this.m_zip = null;
        return;
    }//end

    private void  onPreloadCacheLoad (ByteArray param1 )
    {
        this.addEventListeners();
        this.m_zip.loadBytes(param1);
        this.m_zipLoaded = true;
        return;
    }//end

    private void  onPreloadCacheError ()
    {
        this.m_checkPreloadCache = false;
        this.addEventListeners();
        this.m_zip.load(this.m_request);
        this.m_zipLoaded = true;
        return;
    }//end

    private void  addEventListeners ()
    {
        this.m_zip.addEventListener(Event.COMPLETE, this.onComplete);
        this.m_zip.addEventListener(FZipEvent.FILE_LOADED, this.onFileLoaded);
        this.m_zip.addEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHttpStatus);
        this.m_zip.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
        this.m_zip.addEventListener(FZipErrorEvent.PARSE_ERROR, this.onParseError);
        this.m_zip.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
        return;
    }//end

    private void  removeEventListeners ()
    {
        this.m_zip.removeEventListener(Event.COMPLETE, this.onComplete);
        this.m_zip.removeEventListener(FZipEvent.FILE_LOADED, this.onFileLoaded);
        this.m_zip.removeEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHttpStatus);
        this.m_zip.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
        this.m_zip.removeEventListener(FZipErrorEvent.PARSE_ERROR, this.onParseError);
        this.m_zip.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
        return;
    }//end





