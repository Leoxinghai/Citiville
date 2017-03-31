package pixelShader;

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
import com.xiyu.util.BitmapData;
import com.xiyu.util.Dictionary;

import Classes.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class AnimatedShaderBitmap extends AnimatedBitmapOptimized
    {
        private BitmapData m_shaderTexture ;
        private static Dictionary s_shadedSpriteSheets =new Dictionary(true );
        private static Dictionary s_textures =new Dictionary(true );
        private static Dictionary s_shaded =new Dictionary(true );
        private static int s_keys =0;


        public  AnimatedShaderBitmap (BitmapData param1 ,BitmapData param2 ,int param3 ,int param4 ,int param5 ,double param6 ,boolean param7 )
        {
            param1 = this.applyTexture(param1, param2);
            super(param1, param3, param4, param5, param6, param7);
            this.draw();
            return;
        }//end

        public BitmapData  applyTexture (BitmapData param1 ,BitmapData param2 )
        {
            BitmapData _loc_5 =null ;

            this.m_shaderTexture = param2;
            if (!s_textures.get(param2))
            {
                s_textures.put(param2,  s_keys + 1);
            }
            BitmapData _loc_3 =param1 ;
            if (!s_shadedSpriteSheets.get(param1))
            {
                s_shadedSpriteSheets.put(param1,  s_keys + 1);
                s_keys++;
            }
            _loc_4 = ""+ s_textures.get(param2) +"/"+s_shadedSpriteSheets.get(param1) ;

            if (s_shaded.get(_loc_4))
            {
                _loc_3 = s_shaded.get(_loc_4);
            }
            else
            {
                _loc_5 = Processor.instance.doShaderJob(param1, this.m_shaderTexture);
                _loc_5.applyFilter(_loc_5, _loc_5.rect, new Point(), new BlurFilter(1.1, 1.1, 2));
                _loc_3 = _loc_5;
                s_shaded.put(_loc_4,  _loc_5);
            }

            return _loc_3;
        }//end

        public BitmapData texture ()
        {
            return this.m_shaderTexture;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            return;
        }//end

         protected void  draw ()
        {
            if (!this.m_shaderTexture)
            {
                return;
            }
            super.draw();
            return;
        }//end

    }



