package Display.DialogUI;

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

import Display.*;
//import flash.geom.*;

    public class UIGenericDialog extends GenericDialog
    {
        private double m_initialWidth =0;

        public  UIGenericDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end  

         public void  centerPopup ()
        {
            Point _loc_1 =null ;
            if (parent)
            {
                if (this.m_initialWidth == 0)
                {
                    this.m_initialWidth = m_content.width;
                }
                _loc_1 = new Point((UI.DEFAULT_WIDTH - this.m_initialWidth) / 2, (Global.ui.screenHeight - m_jwindow.getHeight()) / 2);
                this.x = _loc_1.x;
                this.y = _loc_1.y;
            }
            return;
        }//end  

    }


