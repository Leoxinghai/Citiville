﻿package Display.DialogUI;

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

//import flash.utils.*;
    public class GenericDialogWithBold extends GenericDialog
    {
        private String strBoldMessage ="";

        public  GenericDialogWithBold (String param1 ,String param2 ,String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",String param7 ="",boolean param8 =true ,int param9 =0,String param10 ="",Function param11 =null )
        {
            this.strBoldMessage = param2;
            super(param1, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            GenericDialogWithBoldView _loc_2 =new GenericDialogWithBoldView(param1 ,m_message ,this.strBoldMessage ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback );
            return _loc_2;
        }//end  

    }


