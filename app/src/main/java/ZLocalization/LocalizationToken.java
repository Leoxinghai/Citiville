package ZLocalization;

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

    public class LocalizationToken
    {
        protected Array m_attributes ;

        public  LocalizationToken ()
        {
            this.m_attributes = new Array();
            return;
        }//end

        public Array  filterIndexes (Array param1 )
        {
            Array _loc_4 =null ;
            Array _loc_2 =new Array ();
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_4 = Utilities.arrayDiff(this.m_attributes, param1.get(_loc_3));
                if (_loc_4.length == 0)
                {
                    _loc_2.push(_loc_3);
                }
                else if (_loc_4.length == 1 && LocalizationObjectToken.GENDERS.hasOwnProperty(_loc_4.get(0)) && !(this instanceof LocalizationName))
                {
                    _loc_2.push(_loc_3);
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        public void  addAttributes (Array param1 )
        {
            this.m_attributes = this.m_attributes.concat(param1);
            return;
        }//end

        public String  getString ()
        {
            throw new Error("Must override getString function when extending LocalizationToken");
        }//end

    }


