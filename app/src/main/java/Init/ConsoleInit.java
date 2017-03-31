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
//import flash.events.*;
import plugin.*;

    public class ConsoleInit extends InitializationAction
    {
        public static  String INIT_ID ="ConsoleInit";

        public  ConsoleInit ()
        {
            super(INIT_ID);
            addDependency(UIInit.INIT_ID);
            addDependency(BootstrapInit.INIT_ID);
            return;
        }//end

         public void  execute ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            String _loc_4 =null ;
            PluginLoader _loc_5 =null ;
            _loc_1 =Global.bootstrap.setting ;
            if (Config.DEBUG_MODE && _loc_1.bootstrap.length() > 0)
            {
                _loc_2 = _loc_1.bootstrap.get(0);
                if (_loc_2.plugins.length() > 0)
                {
                    for(int i0 = 0; i0 < _loc_2.plugins.plugin.size(); i0++)
                    {
                    	_loc_3 = _loc_2.plugins.plugin.get(i0);

                        if (_loc_3.attribute("type").toString() == "console")
                        {
                            _loc_4 = _loc_3.attribute("locator").toString();
                            _loc_5 = new PluginLoader(_loc_4);
                            _loc_5.load(this.onConsoleLoaded);
                            return;
                        }
                    }
                }
            }
            this.onConsoleLoaded(null);
            return;
        }//end

        private void  onConsoleLoaded (Event event )
        {
            if (!(event instanceof IOErrorEvent))
            {
                if (event && event.target.content)
                {
                    ConsoleStub.install(event.target.content);
                    ConsoleHelper.activate();
                }
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



