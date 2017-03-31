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

import Engine.Events.*;
import Engine.Init.*;
import Engine.Managers.*;
//import flash.events.*;

    public class AssetLoadingInit extends InitializationAction
    {
        public static  String INIT_ID ="AssetLoadingInit";

        public  AssetLoadingInit ()
        {
            super(INIT_ID);
            addDependency(UIInit.INIT_ID);
            addDependency(GlobalsInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            this.onAssetsLoaded(null);
            GlobalEngine.zaspManager.trackTimingStart("ASSET_LOADING_INIT");
            return;
        }//end  

        protected void  onAssetsLoaded (Event event )
        {
            LoadingManager.getInstance().removeEventListener(LoaderEvent.ALL_HIGH_PRIORITY_LOADED, this.onAssetsLoaded);
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("ASSET_LOADING_INIT");
            return;
        }//end  

    }




