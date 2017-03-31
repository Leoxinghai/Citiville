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
    public class CustomDialog extends GenericDialog
    {
        protected boolean m_finalized ;
        protected Array m_buttons ;

        public  CustomDialog (String param1 ="",String param2 ="",String param3 ="",String param4 ="",int param5 =0,boolean param6 =true )
        {
            this.m_finalized = false;
            this.m_buttons = new Array();
            super(param1, param2, 0, null, param3, param4, param6, param5, "", null, "");
            return;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            CustomDialogView _loc_2 =new CustomDialogView(param1 ,m_message ,m_dialogTitle ,m_icon ,m_iconPos ,this.m_buttons );
            return _loc_2;
        }//end  

         protected void  init ()
        {
            return;
        }//end  

         protected void  loadAssets ()
        {
            return;
        }//end  

        public void  buildCustomDialog ()
        {
            if (!this.m_finalized)
            {
                super.init();
                super.loadAssets();
                this.m_finalized = true;
            }
            return;
        }//end  

        public void  addButton (String param1 ,Function param2 )
        {
            this.m_buttons.push({label:param1, callback:param2});
            return;
        }//end  

    }


