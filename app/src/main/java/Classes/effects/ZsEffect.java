package Classes.effects;

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
    public class ZsEffect extends AnimationEffect
    {
        private  double OFFSET_PERCENT_Y =0.19;

        public  ZsEffect (MapResource param1 ,String param2 )
        {
            super(param1, param2, true, true);
            return;
        }//end  

         public void  reattach ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            super.reattach();
            if (m_effectImage)
            {
                _loc_1 = m_mapResource.content.width >> 1;
                _loc_2 = m_mapResource.content.height * this.OFFSET_PERCENT_Y;
                _loc_3 = -(m_effectImage.width >> 1);
                _loc_4 = -(m_effectImage.height >> 1);
                m_effectImage.x = _loc_1 + _loc_3 + m_itemImage.offsetX;
                m_effectImage.y = _loc_2 + _loc_4 + m_itemImage.offsetY;
            }
            return;
        }//end  

    }


