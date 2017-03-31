package Modules.crew.ui;

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

import Display.DialogUI.*;
//import flash.utils.*;

    public class GovernorsRunFailDialog extends GenericDialog
    {

        public  GovernorsRunFailDialog (String param1 ,Function param2 )
        {
            String _loc_3 ="assets/rollCall/payroll_sam.png";
            _loc_4 = ZLoc.t("Dialogs","RollCallFail_"+param1 +"_text");
            _loc_5 = ZLoc.t("Dialogs","RollCall_pigeonRewards_button");
            super(_loc_4, "roll_call_" + param1, GenericDialogView.TYPE_CUSTOM_OK, param2, "RollCallFail_" + param1, _loc_3, true, 0, "", null, _loc_5);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            GovernorsRunFailDialogView _loc_2 =new GovernorsRunFailDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,"",null ,m_customOk );
            return _loc_2;
        }//end

    }



