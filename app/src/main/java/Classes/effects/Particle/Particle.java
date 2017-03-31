package Classes.effects.Particle;

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

import Engine.Classes.*;
import Engine.Helpers.*;
//import flash.display.*;

    public class Particle
    {
        public boolean done ;
        public Vector2 vel ;
        public double deltaRotation ;
        public double startScale ;
        public double endScale ;
        public double activeTime ;
        public double duration ;
        public DisplayObject displayObject ;
        public AnimatedBitmap animation ;
        public int lastFrame ;

        public  Particle ()
        {
            this.done = false;
            this.vel = new Vector2();
            this.deltaRotation = 0;
            this.startScale = 1;
            this.endScale = 1;
            this.activeTime = 0;
            this.duration = 0;
            this.lastFrame = 0;
            this.animation = null;
            this.displayObject = null;
            return;
        }//end

    }



