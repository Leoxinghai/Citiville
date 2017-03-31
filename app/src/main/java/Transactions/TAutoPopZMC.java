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

import Engine.Transactions.*;
import Modules.stats.experiments.*;
import Modules.stats.trackers.*;
//import flash.external.*;

    public class TAutoPopZMC extends Transaction
    {

        public  TAutoPopZMC ()
        {
            funName = "UserService.getZEventCount";
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.getZEventCount");
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            /*
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_POPUP_ZMC_EARLY );
            if (_loc_2 == ExperimentDefinitions.POPUP_ZMC_NORMAL)
            {
                if (param1.count > 0)
                {
                    Global.zmcOpenedOnInit = true;
                    ExternalInterface.call("openZMC");
                }
                else
                {
                    StartupSessionTracker.interactive();
                }
            }
            */
            return;
        }//end

    }



