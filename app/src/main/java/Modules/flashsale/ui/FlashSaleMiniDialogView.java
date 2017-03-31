package Modules.flashsale.ui;

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
//import flash.utils.*;

    public class FlashSaleMiniDialogView extends FlashSaleDialogView
    {

        public  FlashSaleMiniDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,Object param8 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end  

         protected int  HEADING_TOP_PAD ()
        {
            return 0;
        }//end  

         protected int  HEADING_LEFT_PAD ()
        {
            return 10;
        }//end  

         protected int  HEADING_WIDTH ()
        {
            return 400;
        }//end  

         protected int  BODYA_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(48, 1, [{locale:"ja", ratio:0.8}]);
        }//end  

         protected int  BODYB_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(16, 1, [{locale:"ja", ratio:0.8}]);
        }//end  

         protected int  BODYB_TOP_PAD ()
        {
            return -5;
        }//end  

         protected Class  CELL_CLASS ()
        {
            return PackageSaleMiniCell;
        }//end  

    }


