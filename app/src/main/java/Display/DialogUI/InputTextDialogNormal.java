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
//import flash.display.*;
//import flash.utils.*;

    public class InputTextDialogNormal extends InputTextDialog
    {

        public  InputTextDialogNormal (String param1 ,String param2 ,String param3 ,String param4 ,int param5 =0,int param6 =0,Function param7 =null ,boolean param8 =true ,Function param9 =null ,boolean param10 =false )
        {
            m_lightbox = param10;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            return;
        }//end  

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg", (DisplayObject) new m_comObject.dialog_bg());
            return _loc_1;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            InputTextNormalDialogView _loc_2 =new InputTextNormalDialogView(param1 ,m_message ,m_title ,m_inputLabel ,m_inputField ,m_type ,m_callback ,"",0,m_SkipCallback );
            m_textField = _loc_2.textField;
            return _loc_2;
        }//end  

         protected void  handleShowLightbox ()
        {
            if (m_lightbox)
            {
                Global.ui.displayLightbox(true, UI.MASK_ALL_UI);
            }
            return;
        }//end  

    }


