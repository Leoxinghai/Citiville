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

import Classes.*;
import Transactions.*;
//import flash.system.*;
import plugin.*;

    public class ConsoleHelper
    {
        private ConsoleStub console ;

        public  ConsoleHelper (ConsoleStub param1 )
        {
            this.console = param1;
            this.initHotkeys();
            this.initObjects();
            return;
        }//end

        private void  initHotkeys ()
        {
            this .console .bindKey ("x",void  ()
            {
                ItemImageAsset.printStats();
                return;
            }//end
            , null, false, true);
            this .console .bindKey ("r",void  ()
            {
                TFarmTransaction.printRolls();
                return;
            }//end
            , null, false, true);
            this .console .bindKey ("s",void  ()
            {
                TFarmTransaction.printStats();
                return;
            }//end
            , null, false, true);
            return;
        }//end

        private void  initObjects ()
        {
            this.console.store("report_bitmap", ItemImageAsset.printStats);
            return;
        }//end

        public int  flashMemory ()
        {
            return System.totalMemory;
        }//end

        public String  track_asset ()
        {
            this.console.addGraph("pixels", this, "savedMemory");
            return "Assets tracking initialized, hot key = x\n commands = $report_bitmap()";
        }//end

        public static void  activate ()
        {
            _loc_1 = ConsoleStub.getInstance();
            if (_loc_1 != null)
            {
                _loc_1.store("helper", new ConsoleHelper(_loc_1), true);
            }
            return;
        }//end

    }



