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
//import flash.utils.*;

    public class SlottedContainerBonus extends HarvestBonus
    {
        private String m_featureName ;
        protected double m_visitBoost =0;

        public  SlottedContainerBonus (XML param1 )
        {
            super(param1);
            this.m_featureName = param1.@featureName;
            this.m_visitBoost = param1.@visitPercent;
            return;
        }//end

         public void  init (MapResource param1 )
        {
            Dictionary _loc_4 =null ;
            Object _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            int _loc_2 =0;
            Object _loc_3 =null ;
            if (param1 instanceof MechanicMapResource)
            {
                _loc_4 = ((MechanicMapResource)param1).getMechanicData();
                _loc_3 = _loc_4.get("harvestState");
                if (_loc_3 && _loc_3.get("visitors") != null)
                {
                    _loc_5 = _loc_3.get("visitors");
                    _loc_6 = 0;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_7 = _loc_5.get(i0);

                        _loc_6++;
                    }
                    _loc_2 = _loc_6 * this.m_visitBoost;
                }
            }
            m_percentModifier = initialPercentModifier + _loc_2;
            return;
        }//end

         public double  maxPercentModifier ()
        {
            return m_percentModifier;
        }//end

    }



