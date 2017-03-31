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

//import flash.display.*;
    public class ContainerSlot extends MovieClip
    {
        public int position =0;
        public boolean available =true ;

        public  ContainerSlot ()
        {
            alpha = 0;
            return;
        }//end  

        public void  reset ()
        {
            int _loc_1 =0;
            while (_loc_1 < numChildren)
            {
                
                removeChildAt(0);
                _loc_1 = _loc_1 + 1;
            }
            alpha = 0;
            this.available = true;
            this.position = 0;
            x = 0;
            y = 0;
            return;
        }//end  

    }



