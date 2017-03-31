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
    public class ShipBonus extends HarvestBonus
    {
        private String m_experiment ;
        private int m_variant ;

        public  ShipBonus (XML param1 )
        {
            this.m_variant = parseInt(param1.@variant);
            this.m_experiment = param1.@experiment;
            super(param1);
            return;
        }//end

         public void  init (MapResource param1 )
        {
            if (this.m_experiment && Global.experimentManager.getVariant(this.m_experiment) != this.m_variant)
            {
                return;
            }
            _loc_2 = param1as Ship ;
            _loc_3 = m_percentModifier;
            m_percentModifier = 0;
            if (_loc_2)
            {
                m_percentModifier = _loc_2.getItem().harvestMultiplier;
            }
            return;
        }//end

         public double  maxPercentModifier ()
        {
            return m_percentModifier;
        }//end

    }



