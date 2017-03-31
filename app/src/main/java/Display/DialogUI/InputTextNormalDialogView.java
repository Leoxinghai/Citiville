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

import Display.aswingui.*;
//import flash.utils.*;
import org.aswing.*;

    public class InputTextNormalDialogView extends InputTextDialogView
    {

        public  InputTextNormalDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,Function param10 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end  

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = createCloseButtonPanel();
            _loc_3 = createTitlePanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, 20);
            return _loc_1;
        }//end  

    }



