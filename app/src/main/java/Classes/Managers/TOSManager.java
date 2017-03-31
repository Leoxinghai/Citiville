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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Transactions.*;
//import flash.events.*;
//import flash.external.*;

    public class TOSManager
    {
        private Object m_permissions ;
        private static TOSManager m_instance ;
        private static Object SINGLETON_ENFORCER ={};

        public  TOSManager (Object param1 ,Object param2 )
        {
            if (param1 != SINGLETON_ENFORCER)
            {
                throw new Error("TOSManager instanceof a singleton");
            }
            this.m_permissions = param2;
            return;
        }//end

        public void  showTOSDialog ()
        {
            UI.displayPopup(new GenericDialog(ZLoc.t("Dialogs", "termsOfService_message", {tosStartTag:"<a href=\'http://www.zynga.com/about/terms-of-service.php\' target=\'_blank\'><font color=\'#0000FF\'><u>", ppStartTag:"<a href=\'http://www.zynga.com/about/privacy-policy.php\' target=\'_blank\'><font color=\'#0000FF\'><u>", endTag:"</u></font></a>"}), "Terms of Service", GenericDialogView.TYPE_GENERIC_OK_WITHOUTCANCEL, this.onTOSAccepted, "", "", true, 0, "", null, "", true, "", true));
            return;
        }//end

        private boolean  hasPermissions (...args )
        {
            argsvalue = null;
            for(int i0 = 0; i0 < args.size(); i0++)
            {
            		argsvalue = args.get(i0);

                if (!parseInt(this.m_permissions.get(argsvalue)))
                {
                    return false;
                }
            }
            return true;
        }//end

        private void  onTOSAccepted (Event event )
        {
            GameTransactionManager.addTransaction(new TAcceptTOS(), true);
            if (ExternalInterface.available && !this.hasPermissions("publish_actions", "user_games_activity", "friends_games_activity", "manage_notifications"))
            {
                ExternalInterface.addCallback("onShowPostTOSPermissionsClose", this.onShowPostTOSPermissionsClose);
                ExternalInterface.call("showPostTOSPermissions");
            }
            return;
        }//end

        private void  onShowPostTOSPermissionsClose ()
        {
            Global.player.setSeenFlag("trackPermissions");
            GameTransactionManager.addTransaction(new TTrackPermissions(), true);
            return;
        }//end

        public static void  init (Object param1 )
        {
            if (!m_instance)
            {
                m_instance = new TOSManager(SINGLETON_ENFORCER, param1);
            }
            return;
        }//end

        public static TOSManager  instance ()
        {
            return m_instance;
        }//end

    }



