package Modules.flashsale.ui;

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

    public class PackageSaleMiniCell extends PackageSaleCell
    {

        public  PackageSaleMiniCell ()
        {
            return;
        }//end  

         protected String  CELL_BG_CLASS ()
        {
            return "dialog_flashSaleMini_packageSaleCard";
        }//end  

         protected int  HEADING_FONTSIZE ()
        {
            return 18;
        }//end  

         protected int  BODYA_FONTSIZE ()
        {
            return 32;
        }//end  

         protected int  BODYB_FONTSIZE ()
        {
            return 16;
        }//end  

         protected int  BODYC_FONTSIZE ()
        {
            return 20;
        }//end  

         protected int  BODY_BOTTOM_PAD ()
        {
            return 0;
        }//end  

        public static int  CELL_WIDTH ()
        {
            return 199;
        }//end  

        public static int  CELL_HEIGHT ()
        {
            return 174;
        }//end  

    }



