package Init.PostInit;

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
import Engine.Init.*;
//import flash.events.*;

    public class DoDisplayDialog extends InitializationAction
    {
        protected Dialog m_dialog ;
        public static  String INIT_ID ="DoDialog";

        public  DoDisplayDialog (Dialog param1 )
        {
            super(INIT_ID);
            this.m_dialog = param1;
            return;
        }//end  

         public void  execute ()
        {
            this.m_dialog.addEventListener(Event.CLOSE, this.onDialogClose);
            UI.displayPopup(this.m_dialog);
            return;
        }//end  

        protected void  onDialogClose (Event event )
        {
            this.m_dialog.removeEventListener(Event.CLOSE, this.onDialogClose);
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



