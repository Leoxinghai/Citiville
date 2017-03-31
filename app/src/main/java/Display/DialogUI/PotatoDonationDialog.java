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

//import flash.utils.*;
    public class PotatoDonationDialog extends GenericPictureDialog
    {
        protected int m_cashCost ;

        public  PotatoDonationDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="SendEmails",String param10 ="",int param11 =0)
        {
            this.m_cashCost = param11;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PotatoDonationDialogView _loc_2 =new PotatoDonationDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_acceptTextName ,this.m_cashCost );
            return _loc_2;
        }//end  

    }


