package scripting;

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

    public class Condition
    {
        protected Function m_eval =null ;
        protected Object m_args =null ;

        public  Condition (Function param1 ,Object param2 )
        {
            this.m_eval = param1;
            this.m_args = param2;
            return;
        }//end

        public void  args (Object param1 )
        {
            this.m_args = param1;
            return;
        }//end

        public boolean  evaluate ()
        {
            return this.m_eval != null && this.m_eval.call(null, this.m_args);
        }//end

    }



