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

import by.blooddy.crypto.*;
import by.blooddy.crypto.image.*;
//import flash.display.*;
//import flash.utils.*;

    public class FreezeManager
    {

        public  FreezeManager ()
        {
            return;
        }//end

        public static BitmapData  getScreenshot ()
        {
            BitmapData bData =new BitmapData(Global.stage.stageWidth ,Global.stage.stageHeight ,false ,0);
            try
            {
                bData.draw(Global.stage);
            }
            catch (error:SecurityError)
            {
                bData;
            }
            return bData;
        }//end

        public static String  exportScreenshot ()
        {
            String _loc_5 =null ;
            String _loc_1 =null ;
            _loc_2 = getScreenshot();
            if (_loc_2 === null)
            {
                return null;
            }
            Shape _loc_3 =new Shape ();
            _loc_3.graphics.beginFill(0, 0.3);
            _loc_3.graphics.drawRect(0, 0, Global.stage.stageWidth, Global.stage.stageHeight);
            _loc_3.graphics.endFill();
            _loc_2.draw(_loc_3);
            _loc_4 = JPEGEncoder.encode(_loc_2,80);
            if (JPEGEncoder.encode(_loc_2, 80))
            {
                _loc_5 = Base64.encode(_loc_4);
                if (_loc_5)
                {
                    _loc_1 = _loc_5;
                }
            }
            return _loc_1;
        }//end

    }



