package Classes.util;

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

import Display.*;
import Engine.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
//import flash.external.*;
//import flash.geom.*;

    public class FrameManager
    {
        private static Function silentLoadCallback ;
        private static FrameLoaderDialog silentLoadDlg ;
        private static boolean loadInProgress ;
        private static String lastUrl ;
        private static boolean dynamicTrayOpen ;
        private static Array initTrayPreloads ;
        private static  String METHOD_NAVIGATE_TO ="ZYFrameManager.navigateTo";
        private static  String METHOD_TRAY_POPULATE ="ZYFrameManager.dynamicTrayPopulate";
        private static  String METHOD_TRAY_PRELOAD ="ZYFrameManager.dynamicTrayPreload";
        private static  String METHOD_TRAY_CLOSE ="ZYFrameManager.dynamicTrayClose";

        public  FrameManager ()
        {
            return;
        }//end

        public static void  navigateTo (String param1 ,String param2 ,Function param3 =null ,Point param4 =null ,boolean param5 =true )
        {
            _loc_6 = showFrame(param1,METHOD_NAVIGATE_TO,param2,param3,param4,param5);
            return;
        }//end

        public static void  showTray (String param1 ,String param2 ,Function param3 =null ,Point param4 =null ,boolean param5 =false )
        {
            boolean _loc_6 =false ;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS) == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                _loc_6 = showFrame(param1, METHOD_TRAY_POPULATE, param2, param3, param4, param5);
            }
            else
            {
                navigateTo(param1, param2, param3, param4, true);
            }
            if (_loc_6)
            {
                dynamicTrayOpen = true;
            }
            return;
        }//end

        public static void  preloadTray (String param1 ,boolean param2 )
        {
            url = param1;
            forceReload = param2;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS) == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                try
                {
                    if (ExternalInterface.available)
                    {
                        ExternalInterface.call(METHOD_TRAY_PRELOAD, url, forceReload);
                    }
                }
                catch (e:Error)
                {
                    ErrorManager.addError(ZLoc.t("Dialogs", "NavigateToFailed", {url:url}));
                }
            }
            return;
        }//end

        public static void  navigateImmediatelyTo (String param1 )
        {
            url = param1;
            if (loadInProgress)
            {
                return;
            }
            try
            {
                if (ExternalInterface.available)
                {
                    if (Utilities.isFullScreen())
                    {
                        Utilities.setFullScreen(false);
                    }
                    ExternalInterface.call(METHOD_NAVIGATE_TO, url);
                    lastUrl = url;
                }
            }
            catch (e:Error)
            {
                ErrorManager.addError(ZLoc.t("Dialogs", "NavigateToFailed", {url:url}));
            }
            return;
        }//end

        public static void  frameLoadComplete (boolean param1 =true )
        {
            loadInProgress = false;
            if (silentLoadDlg != null)
            {
                silentLoadDlg.close();
                silentLoadDlg = null;
            }
            if (silentLoadCallback != null)
            {
                silentLoadCallback(param1);
                silentLoadCallback = null;
            }
            return;
        }//end

        private static boolean  showFrame (String param1 ,String param2 ,String param3 =null ,Function param4 =null ,Point param5 =null ,boolean param6 =true )
        {
            boolean success ;
            url = param1;
            method = param2;
            loadingText = param3;
            completeCallback = param4;
            targetPos = param5;
            silentLoad = param6;
            if (loadInProgress)
            {
                return false;
            }
            if (dynamicTrayOpen)
            {
                closeTray();
            }
            try
            {
                if (ExternalInterface.available)
                {
                    silentLoadCallback = completeCallback;
                    if (!loadingText)
                    {
                        loadingText = ZLoc.t("Dialogs", "FrameLoading");
                    }
                    silentLoadDlg = new FrameLoaderDialog(loadingText, targetPos);
                    UI.displayPopup(silentLoadDlg, false, "FrameLoading");
                    loadInProgress = true;
                    if (Utilities.isFullScreen())
                    {
                        Utilities.setFullScreen(false);
                    }
                    success = ExternalInterface.call(method, url, silentLoad);
                    if (!success)
                    {
                        frameLoadComplete(false);
                    }
                    lastUrl = url;
                    return success;
                }
            }
            catch (e:Error)
            {
                ErrorManager.addError(ZLoc.t("Dialogs", "NavigateToFailed", {url:url}));
            }
            return false;
        }//end

        public static void  doInitPreloads ()
        {
            XMLList _loc_1 =null ;
            XML _loc_2 =null ;
            String _loc_3 =null ;
            dynamicTrayOpen = false;
            if (!Global.player.isNewPlayer)
            {
                _loc_1 = Global.gameSettings().getInitTrayPreloads();
                initTrayPreloads = new Array();
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_2 = _loc_1.get(i0);

                    _loc_3 = _loc_2.@url;
                    preloadTray(_loc_3, false);
                    initTrayPreloads.push(_loc_3);
                }
            }
            return;
        }//end

        public static void  onDynamicTrayClosed ()
        {
            String _loc_1 =null ;
            dynamicTrayOpen = false;
            for(int i0 = 0; i0 < initTrayPreloads.size(); i0++)
            {
            	_loc_1 = initTrayPreloads.get(i0);

                if (_loc_1 == lastUrl)
                {
                    preloadTray(_loc_1, true);
                    break;
                }
            }
            return;
        }//end

        public static String  getLastUrl ()
        {
            return lastUrl;
        }//end

        public static void  closeTray ()
        {
            frameLoadComplete();
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call(METHOD_TRAY_CLOSE);
                }
            }
            catch (e:Error)
            {
                ErrorManager.addError(ZLoc.t("Dialogs", "CloseTrayFailed", {url:lastUrl}));
            }
            onDynamicTrayClosed();
            return;
        }//end

    }



