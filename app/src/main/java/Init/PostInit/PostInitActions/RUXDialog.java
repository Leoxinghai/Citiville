package Init.PostInit.PostInitActions;

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
    public class RUXDialog extends StartupDialogBase
    {
public static  String DIALOG_IMAGE_URL ="assets/announcements/welcomeback.png";

        public  RUXDialog ()
        {
            _loc_1 = ZLoc.t("Dialogs","rux_dialog_msg");
            String _loc_2 ="RUX Welcome Back Dialog";
            _loc_3 = GenericDialogView.TYPE_OK;
            Function _loc_4 =null ;
            _loc_5 = ZLoc.t("Dialogs","rux_dialog_title");
            String _loc_6 =null ;
            boolean _loc_7 =true ;
            int _loc_8 =0;
            String _loc_9 ="OkButton";
            _loc_10 = DIALOG_IMAGE_URL;
            GenericPictureDialog _loc_11 =new GenericPictureDialog(_loc_1 ,_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 ,_loc_9 ,_loc_10 );
            super(_loc_11, _loc_2);
            return;
        }//end

    }



