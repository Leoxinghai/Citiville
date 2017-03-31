package Transactions;

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

import Classes.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;

    public class TRefreshUser extends TInitUser
    {
        private GenericDialog m_dialog =null ;
        private boolean m_causedByError =false ;
        private static int m_numRefreshes =0;
        private static int MAX_REFRESHES =1;

        public  TRefreshUser (String param1 ,boolean param2 =false )
        {
            MAX_REFRESHES = RuntimeVariableManager.getInt("REFRESH_USER_LIMIT", 1);
            this.m_causedByError = param2;
            UI.flushDialogs();
            this.m_dialog = UI.displayMessage(ZLoc.t("Main", "ResolvingIssues"), GenericDialogView.TYPE_NOBUTTONS, null, "", true);
            Sounds.stopAnyLoopingSounds();
            Global.setVisiting(null);
            Global.ui.updateVisitMode(false);
            TransactionManager.initialize();
            ConnectionStatus.stopTimeout();
            super(param1);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 != null)
            {
                loadWorld(param1);
                if (this.m_causedByError)
                {
                    _loc_3 = m_numRefreshes+1;
                    m_numRefreshes = _loc_3;
                }
                StatsManager.count("refresh", "user_state");
            }
            else
            {
                GameUtil.reloadApp(true, "fail_refresh_user");
            }
            if (this.m_dialog)
            {
                this.m_dialog.close();
            }
            return;
        }//end

         protected void  onFault (int param1 ,String param2 )
        {
            GameUtil.reloadApp(true, "amf_fault_refresh_user");
            return;
        }//end

        public static boolean  canCall (boolean param1 )
        {
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REFRESH_USER );
            _loc_3 = _loc_2==ExperimentDefinitions.REFRESH_USER ;
            if (param1 !=null)
            {
                _loc_3 = _loc_3 && m_numRefreshes < MAX_REFRESHES;
            }
            return _loc_3;
        }//end

    }



