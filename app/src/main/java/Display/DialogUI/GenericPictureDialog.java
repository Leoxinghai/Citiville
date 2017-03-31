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

    public class GenericPictureDialog extends GenericDialog
    {
        protected String m_acceptTextName ;
        protected String m_imagePath ;
        protected DisplayObject m_bg ;
        protected int m_widthOffset ;
        protected int m_heightOffset ;

        public  GenericPictureDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="SendEmails",String param10 ="",int param11 =0,int param12 =0)
        {
            this.m_acceptTextName = param9;
            this.m_imagePath = param10;
            this.m_widthOffset = param11;
            this.m_heightOffset = param12;
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            _loc_1 = super.getAssetDependencies();
            _loc_1.push(this.m_imagePath);
            return _loc_1;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            if (m_type == GenericDialogView.TYPE_OK_WITHOUTCANCEL)
            {
                _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg_picture_nocancel());
            }
            else
            {
                _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg_picture());
            }
            _loc_1.put("image",  m_assetDependencies.get(this.m_imagePath));
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            GenericPictureDialogView _loc_2 =new GenericPictureDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,this.m_acceptTextName ,this.m_widthOffset ,this.m_heightOffset );
            return _loc_2;
        }//end

    }




