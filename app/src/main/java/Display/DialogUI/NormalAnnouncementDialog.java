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

    public class NormalAnnouncementDialog extends GenericPictureDialog
    {
        private Object m_data ;
        private static  String TIMER_EXCLAIM_URL ="assets/dialogs/timer_exclamation_burst.png";

        public  NormalAnnouncementDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="SendEmails",String param10 ="",int param11 =0,int param12 =0,Object param13 =null )
        {
            this.m_data = param13;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         public void  show ()
        {
        	Debug.debug4("NormalAnnouncementDialog.show");
        	hide();
        	return;
        }

         protected Array  getAssetDependencies ()
        {
            _loc_1 = super.getAssetDependencies();
            _loc_1.push(TIMER_EXCLAIM_URL);
            return _loc_1;
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = super.createAssetDict();
            _loc_1.put("timer_exclamation_burst",  (DisplayObject)m_assetDependencies.get(TIMER_EXCLAIM_URL));
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new NormalAnnouncementDialogView(param1, m_message, m_dialogTitle, m_type, m_callback, m_icon, m_iconPos, m_acceptTextName, m_widthOffset, m_heightOffset, this.m_data);
        }//end

    }




