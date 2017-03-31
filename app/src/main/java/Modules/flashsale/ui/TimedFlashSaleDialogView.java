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

import Display.aswingui.*;
import Modules.freegiftsale.ui.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class TimedFlashSaleDialogView extends FlashSaleDialogView
    {

        public  TimedFlashSaleDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,Object param8 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end  

         protected JPanel  createTextArea ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_1.setPreferredSize(new IntDimension(this.width - 50, this.height - 50));
            _loc_1.append(createTopPanel(), BorderLayout.NORTH);
            JPanel _loc_2 =new JPanel(new SoftBoxLayout(AsWingConstants.VERTICAL ));
            Date _loc_3 =new Date(m_endDate *1000);
            _loc_2.append(ASwingHelper.verticalStrut(80));
            _loc_2.append(new TimerPanel(_loc_3));
            _loc_1.append(_loc_2, BorderLayout.CENTER);
            _loc_1.append(createBottomPanel(), BorderLayout.SOUTH);
            return _loc_1;
        }//end  

    }



