package Modules.stats.helpers;

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

import Engine.Managers.*;
import Modules.stats.*;
import Modules.stats.data.*;

//import flash.utils.*;

    public class AbstractStatsHelper
    {
        protected Dictionary m_statsTargets ;

        public  AbstractStatsHelper ()
        {
            this.m_statsTargets = new Dictionary(true);
            return;
        }//end

        public void  register (IStatsTarget param1 ,String param2 )
        {
            this.m_statsTargets.put(param1,  param2);
            return;
        }//end

        public void  init ()
        {
            return;
        }//end

        public void  process (IStatsTarget param1 ,String param2 )
        {
            StatsCountData _loc_4 =null ;
            _loc_3 = param1.getStatsCounterObject ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_4 = _loc_3.get(i0);

                StatsManager.count(param2, _loc_4.ontoloogy.kingdom, _loc_4.ontoloogy.phylum, _loc_4.ontoloogy.zclass, _loc_4.ontoloogy.family, _loc_4.ontoloogy.genus, _loc_4.amount);
            }
            return;
        }//end

    }



