package Classes;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.utils.*;

    public class ConnectionStatus
    {
        public static  String TIMEOUT_SAVE_GAME ="timeout_save_game";
        public static  String TIMEOUT_CONNECTION_LOST ="timeout_connection_lost";
        public static String m_currentTimeoutType ;
        public static Timer m_timeoutTimer ;
        public static GenericDialog m_timeoutDialog ;
        public static Function m_onTimeout ;
        private static int m_zaspLoadHandle =0;

        public  ConnectionStatus ()
        {
            return;
        }//end

        public static void  startTimeout (String param1 ,int param2 ,boolean param3 =true ,Function param4 =null )
        {
            String _loc_5 =null ;
            if (m_currentTimeoutType != null)
            {
                return;
            }
            m_currentTimeoutType = param1;
            m_timeoutTimer = TimerUtil.callLater(onTimeout, param2);
            m_onTimeout = param4;
            if (param3)
            {
                StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.TRANSACTIONS + "_start", m_currentTimeoutType);
                switch(m_currentTimeoutType)
                {
                    case TIMEOUT_SAVE_GAME:
                    {
                        _loc_5 = ZLoc.t("Main", "SavingFarm");
                        break;
                    }
                    case TIMEOUT_CONNECTION_LOST:
                    {
                        _loc_5 = ZLoc.t("Main", "RetryingConnection");
                        break;
                    }
                    default:
                    {
                        _loc_5 = ZLoc.t("Main", "SavingFarm");
                        break;
                        break;
                    }
                }
                m_timeoutDialog = UI.displayMessage(_loc_5, GenericDialogView.TYPE_NOBUTTONS, null, "", true);
                if (GlobalEngine.zaspManager)
                {
                    m_zaspLoadHandle = GlobalEngine.zaspManager.zaspWaitStart("TRANSACTION_QUEUE", m_currentTimeoutType);
                }
            }
            return;
        }//end

        public static void  stopTimeout ()
        {
            m_currentTimeoutType = null;
            m_onTimeout = null;
            if (m_timeoutDialog != null)
            {
                m_timeoutDialog.close();
                m_timeoutDialog = null;
            }
            if (m_zaspLoadHandle > 0)
            {
                GlobalEngine.zaspManager.zaspWaitEnd(m_zaspLoadHandle);
                m_zaspLoadHandle = 0;
            }
            if (m_timeoutTimer != null)
            {
                m_timeoutTimer.stop();
                m_timeoutTimer = null;
            }
            return;
        }//end

        public static void  onTimeout ()
        {
            String _loc_1 =null ;
            StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.TRANSACTIONS, m_currentTimeoutType);
            if (m_onTimeout != null)
            {
                m_onTimeout(m_currentTimeoutType);
            }
            switch(m_currentTimeoutType)
            {
                case TIMEOUT_SAVE_GAME:
                {
                    _loc_1 = ZLoc.t("Main", "SaveGameTimeout");
                    break;
                }
                case TIMEOUT_CONNECTION_LOST:
                {
                    _loc_1 = ZLoc.t("Main", "ConnectionLostTimeout");
                    break;
                }
                default:
                {
                    _loc_1 = ZLoc.t("Main", "RefreshGame");
                    break;
                    break;
                }
            }
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, GameUtil.redirectHome, "", true);
            return;
        }//end


    }



