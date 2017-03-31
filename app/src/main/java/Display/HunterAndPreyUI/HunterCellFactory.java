package Display.HunterAndPreyUI;

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
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class HunterCellFactory extends CrewCellFactory
    {
        private int color =0;

        public  HunterCellFactory (Dictionary param1 ,IntDimension param2 )
        {
            super(param1, param2);
            return;
        }//end  

         public GridListCell  createNewGridListCell ()
        {
            this.color++;
            return new HunterCell(this, this.color);
        }//end  

    }


