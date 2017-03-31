package Classes.Managers;

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
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
import com.xinghai.Debug;

    public class ZPreloaderManager
    {
        private static  String COUNTER_ERRORS ="errors";
        private static  String COUNTER_ZPRELOADER ="preloader";
        private static  String COUNTER_ZCACHE ="zcache";
        private static Loader m_loader =null ;
        private static boolean m_enabled =false ;
        private static String m_url =null ;
        private static String m_initString =null ;
        private static boolean m_loaded =false ;

        public  ZPreloaderManager ()
        {
            return;
        }//end

        public static void  init ()
        {
            Debug.debug4("Preloader.init");
            if (m_loaded)
            {
                return;
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ZPRELOADER) == ExperimentDefinitions.ZPRELOADER_ENABLED)
            {
                m_url =(String) GlobalEngine.getFlashVar("zpreloader_url");
                m_initString =(String) GlobalEngine.getFlashVar("zpreloader_init");
                m_enabled = true;
                if (m_enabled && m_url && m_initString)
                {
                    load();
                    m_loaded = true;
                }
            }
            return;
        }//end

        private static void  load ()
        {
            Debug.debug4("Preloader.load"+m_url);
            if (!m_loader)
            {
                m_loader = new Loader();
                m_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onLoaderComplete);
                m_loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, onLoaderIOError);
                try
                {
                    m_loader.load(new URLRequest(m_url), new LoaderContext());
                }
                catch (e:Error)
                {
                    StatsManager.count(COUNTER_ERRORS, COUNTER_ZCACHE, COUNTER_ZPRELOADER, "load_error: " + e);
                    ErrorManager.addError("ZPreloader load error: " + e);
                    removeLoaderEventListeners();
                }
            }
            return;
        }//end

        private static void  onLoaderComplete (Event event )
        {
            event = event;
            Debug.debug4("Preloader.loaderComplete");
            removeLoaderEventListeners();
            if (m_loader.contentLoaderInfo.childAllowsParent)
            {
                try
                {
                    (m_loader.content).init(m_initString);
                    StatsManager.count(COUNTER_ZCACHE, COUNTER_ZPRELOADER, "status", "enabled");
                }
                catch (e:Error)
                {
                    StatsManager.count(COUNTER_ERRORS, COUNTER_ZCACHE, COUNTER_ZPRELOADER, "init_error: " + e);
                    ErrorManager.addError("ZPreloader init error: " + e);
                }
            }
            else
            {
                StatsManager.count(COUNTER_ERRORS, COUNTER_ZCACHE, COUNTER_ZPRELOADER, "permission_error", "childAllowsParent");
                ErrorManager.addError("ZPreloader childAllowsParent error");
            }
            Debug.debug4("Preloader.loaderComplete.2");
            return;
        }//end

        private static void  onLoaderIOError (IOErrorEvent event )
        {
            Debug.debug4("Preloader.onLoaderIOError");
            removeLoaderEventListeners();
            return;
        }//end

        private static void  removeLoaderEventListeners ()
        {
            m_loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, onLoaderComplete);
            m_loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, onLoaderIOError);
            return;
        }//end

    }


