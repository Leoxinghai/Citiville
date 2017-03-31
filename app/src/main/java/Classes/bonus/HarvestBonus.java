package Classes.bonus;

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

import Classes.*;
    public class HarvestBonus extends ItemBonus
    {
        private int m_initialPercentModifier ;

        public  HarvestBonus (XML param1 )
        {
            super(param1);
            this.m_initialPercentModifier = m_percentModifier;
            return;
        }//end  

        public void  init (MapResource param1 )
        {
            return;
        }//end  

        public int  initialPercentModifier ()
        {
            return this.m_initialPercentModifier;
        }//end  

    }



