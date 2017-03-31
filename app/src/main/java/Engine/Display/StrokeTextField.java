package Engine.Display;

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
//import flash.filters.*;
//import flash.text.*;

    public class StrokeTextField extends TextField
    {
        protected  int STROKE_BLUR =3;
        protected  int STROKE_STRENGTH =200;
        public GlowFilter strokeFilter ;

        public  StrokeTextField (int param1 =0,double param2 =0.8)
        {
            this.strokeFilter = new GlowFilter(param1, alpha, this.STROKE_BLUR, this.STROKE_BLUR, this.STROKE_STRENGTH);
            this.filters = .get(this.strokeFilter);
            this.selectable = false;
            this.blendMode = BlendMode.LAYER;
            this.cacheAsBitmap = true;
            this.autoSize = TextFieldAutoSize.LEFT;
            this.mouseEnabled = false;
            this.mouseWheelEnabled = false;
            return;
        }//end

        public void  toolTipText (String param1 )
        {
            if (param1 !=null)
            {
                this.htmlText = param1;
            }
            return;
        }//end

    }



