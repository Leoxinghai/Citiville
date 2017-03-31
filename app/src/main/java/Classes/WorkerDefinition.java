package Classes;

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

    public class WorkerDefinition
    {
        private String m_name ;

        public  WorkerDefinition ()
        {
            this.m_name = null;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public void  loadObject (XML param1 )
        {
            _loc_2 = param1.name.@locPkg;
            _loc_3 = param1.name.@locKey;
            this.m_name = ZLoc.t(_loc_2, _loc_3);
            return;
        }//end

    }


