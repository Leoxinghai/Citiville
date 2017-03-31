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
    public class StartupMessage extends StartupDialogBase
    {

        public  StartupMessage (String param1 ,String param2 ,String param3 ,Function param4 =null ,String param5 ="",int param6 =0,boolean param7 =true ,String param8 ="")
        {
            super(new GenericDialog(param2, param1, param6, param4, param1, param5, true, 0, "", null, param8, param7), param3, false, null);
            return;
        }//end  

    }



