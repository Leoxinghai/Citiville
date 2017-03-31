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

import Engine.Managers.*;
import Engine.Transactions.*;
import Modules.stats.experiments.*;
import Transactions.*;
import com.adobe.utils.*;

    public class GameTransactionManager
    {
        private static Array m_listeners =new Array();

        public  GameTransactionManager ()
        {
            return;
        }//end

        public static void  addListener (ITransactionManagerListener param1 )
        {
            if (!ArrayUtil.arrayContainsValue(m_listeners, param1))
            {
                m_listeners.push(param1);
            }
            return;
        }//end

        public static ITransactionManagerListener  removeListener (ITransactionManagerListener param1 )
        {
            if (ArrayUtil.arrayContainsValue(m_listeners, param1))
            {
                ArrayUtil.removeValueFromArray(m_listeners, param1);
                return param1;
            }
            return null;
        }//end

        public static void  addTransaction (Transaction param1 ,boolean param2 =false ,boolean param3 =false )
        {
            ITransactionManagerListener _loc_4 =null ;
            if (param2 && !param3 && Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_WAITTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_NONUSERWAIT))
            {
                param2 = false;
            }
            for(int i0 = 0; i0 < m_listeners.size(); i0++)
            {
            	_loc_4 = m_listeners.get(i0);

                _loc_4.beforeTransactionAdded(param1, param2);
            }
            TransactionManager.addTransaction(param1, param2, param3);
            if (param1 instanceof TFarmTransaction)
            {
                ((TFarmTransaction)param1).onAdd();
            }
            for(int i0 = 0; i0 < m_listeners.size(); i0++)
            {
            	_loc_4 = m_listeners.get(i0);

                _loc_4.afterTransactionAdded(param1, param2);
            }
            return;
        }//end

    }



