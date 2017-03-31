package Modules.attractions;

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

import Classes.*;
import Display.DialogUI.*;

    public class AttractionInfographicDialog extends GenericPictureDialog
    {
public static  String DIALOG_TITLE_LOC_PACKAGE ="Tutorials";
public static  String DIALOG_TITLE_LOC_KEY ="OpenAttraction_title";
public static  String DIALOG_MESSAGE_LOC_PACKAGE ="Tutorials";
public static  String DIALOG_MESSAGE_LOC_KEY ="OpenAttraction";
public static  String DIALOG_NAME ="AttractionInfographic";
public static  double DIALOG_TYPE =0;
public static  String DIALOG_ICON =null ;
public static  boolean DIALOG_MODAL =true ;
public static  double DIALOG_ICON_POS =0;
public static  String DIALOG_BUTTON_LOC_KEY ="OkButton";
public static  String DIALOG_IMAGE_URL ="assets/attractions/attractions_infographic.png";

        public  AttractionInfographicDialog (MapResource param1)
        {
            super(ZLoc.t(DIALOG_MESSAGE_LOC_PACKAGE, DIALOG_MESSAGE_LOC_KEY), DIALOG_NAME, DIALOG_TYPE, null, ZLoc.t(DIALOG_TITLE_LOC_PACKAGE, DIALOG_TITLE_LOC_KEY), DIALOG_ICON, DIALOG_MODAL, DIALOG_ICON_POS, DIALOG_BUTTON_LOC_KEY, DIALOG_IMAGE_URL);
            return;
        }//end

        protected void  DialogCloseCallback ()
        {
            return;
        }//end

    }



