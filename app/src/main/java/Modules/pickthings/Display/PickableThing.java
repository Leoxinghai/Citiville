package Modules.pickthings.Display;

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
    public class PickableThing extends Sprite
    {
        private Bitmap m_asset ;
        public double row ;
        protected int m_type ;

        public  PickableThing (Bitmap param1 ,int param2 )
        {
            this.m_asset = new Bitmap(param1.bitmapData.clone());
            this.addChild(this.m_asset);
            this.mouseEnabled = true;
            this.mouseChildren = true;
            this.m_type = param2;
            return;
        }//end  

        public int  type ()
        {
            return this.m_type;
        }//end  

        public void  cleanup ()
        {
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            return;
        }//end  

    }



