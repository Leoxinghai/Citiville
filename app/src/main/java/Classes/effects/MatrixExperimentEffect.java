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
import com.zynga.skelly.util.color.*;
//import flash.utils.*;

    public class MatrixExperimentEffect extends ColorMatrixEffect
    {
        private static ColorMatrix m_matrix =new ColorMatrix ();
        private static double m_brightness =0;
        private static double m_contrast =0;
        private static double m_saturation =1;
        private static Dictionary m_bitmapDictionary =new Dictionary(true );

        public  MatrixExperimentEffect (MapResource param1 )
        {
            super(param1, 0);
            return;
        }//end

         protected Dictionary  bitmapDictionary ()
        {
            return m_bitmapDictionary;
        }//end

         protected ColorMatrix  colorMatrix ()
        {
            return MatrixExperimentEffect.m_matrix.clone();
        }//end

        public static void  reset ()
        {
            m_brightness = 0;
            m_contrast = 0;
            m_saturation = 1;
            update();
            return;
        }//end

        public static void  adjustBrightness (double param1 )
        {
            m_brightness = m_brightness + param1;
            update();
            return;
        }//end

        public static void  adjustContrast (double param1 )
        {
            m_contrast = m_contrast + param1;
            update();
            return;
        }//end

        public static void  adjustSaturation (double param1 )
        {
            m_saturation = m_saturation + param1;
            update();
            return;
        }//end

        private static void  update ()
        {
            MapResource _loc_1 =null ;
            m_matrix = new ColorMatrix();
            m_matrix.adjustBrightness(m_brightness, m_brightness, m_brightness);
            m_matrix.adjustContrast(m_contrast, m_contrast, m_contrast);
            m_matrix.adjustSaturation(m_saturation);
            m_bitmapDictionary = new Dictionary(true);
            for(int i0 = 0; i0 < Global.world.getObjectsByClass(MapResource).size(); i0++)
            {
            		_loc_1 = Global.world.getObjectsByClass(MapResource).get(i0);

                if (!(_loc_1 instanceof Road || _loc_1 instanceof Decoration))
                {
                    _loc_1.removeAnimatedEffects();
                    _loc_1.addAnimatedEffect(EffectType.MATRIXTEST);
                }
            }
            return;
        }//end

    }



