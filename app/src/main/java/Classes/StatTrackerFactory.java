package Classes;

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
    public class StatTrackerFactory
    {
        private static  Object XML_PARSERS ={"count"StatTrackerFactory.count ,"sample".sample };

        public  StatTrackerFactory ()
        {
            return;
        }//end

        private static String  getAttr (XML param1 ,String param2 ,String param3 )
        {
            _loc_4 = param1.attribute(param2 ).get(0) ;
            return param1.attribute(param2).get(0) != null ? (_loc_4.toString()) : (param3);
        }//end

        public static StatTracker  fromXML (XML param1 )
        {
            _loc_2 = param1.localName ();
            if (_loc_2 == null)
            {
                return null;
            }
            Function _loc_3 =XML_PARSERS.get(_loc_2.toString ()) ;
            return _loc_3 != null ? (_loc_3(param1)) : (null);
        }//end

        public static StatTracker  count (String param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",int param7 =1)
        {
            counter = param1;
            kingdom = param2;
            phylum = param3;
            zclass = param4;
            family = param5;
            genus = param6;
            value = param7;
            func = function()void
            {
                StatsManager.count(counter, kingdom, phylum, zclass, family, genus, value);
                return;
            }//end
            ;
            return new StatTrackerImpl(func);
        }//end

        public static StatTracker  sample (int param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",int param8 =1)
        {
            rate = param1;
            counter = param2;
            kingdom = param3;
            phylum = param4;
            zclass = param5;
            family = param6;
            genus = param7;
            value = param8;
            func = function()void
            {
                StatsManager.sample(rate, counter, kingdom, phylum, zclass, family, genus, value);
                return;
            }//end
            ;
            return new StatTrackerImpl(func);
        }//end

    }

import Classes.*;
class StatTrackerImpl implements StatTracker
    private Function m_func ;

     StatTrackerImpl (Function param1 )
    {
        this.m_func = param1;
        return;
    }//end

    public void  track ()
    {
        if (this.m_func != null)
        {
            this.m_func();
        }
        return;
    }//end





