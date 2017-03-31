package zasp.Display;

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

//import flash.display.*;
//import flash.events.*;

    public class FullRedrawForcer extends Sprite
    {

        public  FullRedrawForcer ()
        {
            this.addListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            return;
        }//end  

        private void  addListener (String param1 ,Function param2 )
        {
            addEventListener(param1, param2, false, 0, true);
            return;
        }//end  

        private void  removeListener (String param1 ,Function param2 )
        {
            removeEventListener(param1, param2, false);
            return;
        }//end  

        private void  added_to_stage (Event event )
        {
            this.removeListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            this.addListener(Event.ENTER_FRAME, this.enter_frame);
            this.addListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage);
            return;
        }//end  

        private void  removed_from_stage (Event event )
        {
            this.addListener(Event.ADDED_TO_STAGE, this.added_to_stage);
            this.removeListener(Event.ENTER_FRAME, this.enter_frame);
            this.removeListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage);
            return;
        }//end  

        private void  enter_frame (Event event )
        {
            int _loc_2 =160;
            _loc_3 = stage.stageWidth;
            _loc_4 = stage.stageHeight;
            int _loc_5 =2;
            render(_loc_2, _loc_3, _loc_4, _loc_5, graphics);
            return;
        }//end  

        private static void  render (int param1 ,int param2 ,int param3 ,int param4 ,Object param5 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            param5.clear();
            param5.lineStyle(1, 16777215);
            param5.beginFill(16777215);
            param5.drawRect(2, 2, 10, 10);
            param5.endFill();
            param5.lineStyle(2, 11468800);
            param5.moveTo(5, 5);
            param5.lineTo(10, 10);
            param5.moveTo(5, 10);
            param5.lineTo(10, 5);
            _loc_6 = 0;
            while (_loc_6 < param2)
            {
                
                _loc_7 = 0;
                while (_loc_7 < param3)
                {
                    
                    param5.drawCircle(_loc_6, _loc_7, param4);
                    _loc_7 = _loc_7 + param1;
                }
                _loc_6 = _loc_6 + param1;
            }
            _loc_7 = 0;
            while (_loc_7 < param3)
            {
                
                _loc_6 = param2 - 1;
                param5.drawCircle(_loc_6, _loc_7, param4);
                _loc_7 = _loc_7 + param1;
            }
            _loc_6 = 0;
            while (_loc_6 < param2)
            {
                
                _loc_7 = param3 - 1;
                param5.drawCircle(_loc_6, _loc_7, param4);
                _loc_6 = _loc_6 + param1;
            }
            _loc_6 = param2 - 1;
            _loc_7 = param3 - 1;
            param5.drawCircle(_loc_6, _loc_7, param4);
            return;
        }//end  

    }




