package Modules.quest.Display;

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

import Modules.guide.ui.*;
//import flash.events.*;

    public class QuestIntroDialog extends WelcomeDialog
    {

        public  QuestIntroDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,boolean param5 =true ,String param6 ="",String param7 ="")
        {
            super(param1, param2, param3, WelcomeDialog.POS_CENTER, param4, param5, param6, param7);
            return;
        }//end  

         protected void  loadAssets ()
        {
            showLoadingDialog();
            super.loadAssets();
            return;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            closeLoadingDialog();
            super.onAssetsLoaded(event);
            return;
        }//end  

    }



