package org.aswing.flyfish.css.property;

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

//import flash.text.*;
import org.aswing.*;

    public class FontDecoder extends Object implements ValueDecoder
    {

        public  FontDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            return null;
        }//end

        public boolean  mutabble ()
        {
            return false;
        }//end

        public Object aggregate (Array param1 )
        {
            ASColor _loc_10 =null ;
            _loc_2 = null;
            if (param1.get(2) != null)
            {
                _loc_10 =(ASColor) param1.get(2);
                _loc_2 = _loc_10.getRGB();
            }
            _loc_3 = new TextFormat(param1[0] ,param1[1] ,_loc_2 ,param1[3] ,param1[4] ,param1[5] ,param1[6] ,param1.get(7) ,param1.get(8) ,param1.get(9) ,param1.get(10) ,param1.get(11) ,param1.get(12) );
            boolean _loc_4 =false ;
            String _loc_5 ="normal";
            String _loc_6 ="pixel";
            double _loc_7 =0;
            double _loc_8 =0;
            if (param1.get(13) != null)
            {
                _loc_4 = param1.get(13);
            }
            if (param1.get(14) != null)
            {
                _loc_5 = param1.get(14);
            }
            if (param1.get(15) != null)
            {
                _loc_6 = param1.get(15);
            }
            if (param1.get(16) != null)
            {
                _loc_7 = param1.get(16);
            }
            if (param1.get(17) != null)
            {
                _loc_8 = param1.get(17);
            }
            _loc_9 = new ASFontAdvProperties(_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 );
            return new ASFont(_loc_3, 0, false, false, false, _loc_9);
        }//end

    }


