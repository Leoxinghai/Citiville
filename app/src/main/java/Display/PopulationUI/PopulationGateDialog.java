package Display.PopulationUI;

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
//import flash.utils.*;

    public class PopulationGateDialog extends GenericDialog
    {

        public  PopulationGateDialog (String param1 ,String param2)
        {
            super(param1, "", 0, null, param2);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PopulationGateDialogView _loc_2 =new PopulationGateDialogView(param1 ,m_message ,m_title ,m_type ,m_callback );
            return _loc_2;
        }//end

    }



