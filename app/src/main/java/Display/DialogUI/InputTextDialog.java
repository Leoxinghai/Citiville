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
import Engine.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;

    public class InputTextDialog extends GenericDialog
    {
        protected String m_inputLabel ;
        protected String m_inputField ;
        protected int m_maxLength ;
        protected TextField m_textField ;

        public  InputTextDialog (String param1 ,String param2 ,String param3 ,String param4 ,int param5 =0,int param6 =0,Function param7 =null ,boolean param8 =true ,Function param9 =null )
        {
            if (Utilities.isFullScreen())
            {
                Utilities.toggleFullScreen();
            }
            this.m_inputLabel = param3;
            this.m_inputField = param4;
            this.m_maxLength = param5;
            super(param1, param2, param6, param7, param2, "", param8, 0, "", param9);
            this.addEventListener(Event.ADDED_TO_STAGE, this.setFocus, false, 0, true);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_name_city());
            return _loc_1;
        }//end

        public TextField  textField ()
        {
            return this.m_textField;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            InputTextDialogView _loc_2 =new InputTextDialogView(param1 ,m_message ,m_title ,this.m_inputLabel ,this.m_inputField ,m_type ,m_callback ,"",0,m_SkipCallback );
            this.m_textField = _loc_2.textField;
            return _loc_2;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            _loc_2 = this.createAssetDict();
            m_jpanel = this.createDialogView(_loc_2);
            this.addEventListener(Event.ADDED_TO_STAGE, this.setFocus, false, 0, true);
            finalizeAndShow();
            return;
        }//end

        protected void  setFocus (Event event )
        {
            m_jwindow.removeEventListener(Event.ADDED_TO_STAGE, this.setFocus);
            _loc_2 = (InputTextDialogView)m_jpanel
            _loc_3 = _loc_2.textField;
            _loc_3.maxChars = this.m_maxLength;
            Global.stage.focus = _loc_3;
            setTimeout(_loc_3.setSelection, 250, 0, _loc_3.text.length());
            ASwingHelper.prepare(m_jwindow);
            return;
        }//end

    }




