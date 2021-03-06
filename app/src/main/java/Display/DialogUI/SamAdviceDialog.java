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

//import flash.display.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class SamAdviceDialog extends GenericDialog
    {
        private String m_acceptTextName ;

        public  SamAdviceDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="Accept")
        {
            this.m_acceptTextName = param9;
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            this.m_acceptTextName = param9;
            return;
        }//end  

         public void  show ()
        {
        	Debug.debug4("SamAdviceDialog.show");
        	hide();
        	return;
        }

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg", (DisplayObject) new m_comObject.dialog_offline_bg());
            return _loc_1;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            SamAdviceDialogView _loc_2 =new SamAdviceDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,this.m_acceptTextName );
            return _loc_2;
        }//end  

    }


