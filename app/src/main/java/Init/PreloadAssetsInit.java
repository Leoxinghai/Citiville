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

import Engine.Classes.*;
import Engine.Init.*;
//import flash.events.*;

    public class PreloadAssetsInit extends InitializationAction
    {
        private Array m_assetUrls =null ;
        public static  String INIT_ID ="PreloadAssetsInit";

        public  PreloadAssetsInit (String param1 )
        {
            super(INIT_ID);
            addDependency(GameSettingsDownloadInit.INIT_ID);
            if (param1 !== null)
            {
                this.m_assetUrls = param1.split(",");
            }
            return;
        }//end  

         public void  execute ()
        {
            String _loc_1 =null ;
            if (this.m_assetUrls !== null)
            {
                for(int i0 = 0; i0 < this.m_assetUrls.size(); i0++) 
                {
                	_loc_1 = this.m_assetUrls.get(i0);
                    
                    PreloadedAssetCache.instance.addToCache(_loc_1);
                }
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



