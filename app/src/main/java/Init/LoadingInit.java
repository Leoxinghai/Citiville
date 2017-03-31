package Init;

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
import Engine.Init.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
//import flash.events.*;

    public class LoadingInit extends InitializationAction
    {
        public static  String INIT_ID ="LoadingInit";

        public  LoadingInit ()
        {
            super(INIT_ID);
            addDependency(ExperimentsInit.INIT_ID);
            addDependency(RuntimeVariableInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            LoadingManager.logSampleRate = RuntimeVariableManager.getInt("ASSET_LOG_SAMPLE_RATE", 0);
            LoadingManager.perDomainLoadQueues = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOAD_QUEUES);
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }


