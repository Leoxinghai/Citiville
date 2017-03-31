package Classes.util;

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

//import flash.utils.*;
    public class MemoizationCache
    {
        private Function m_func ;
        private Dictionary m_dict ;
        private boolean m_gotZeroArgResult =false ;
        private Object m_zeroArgResult =null ;

        public  MemoizationCache (Function param1 )
        {
            this.m_dict = new Dictionary(true);
            this.m_func = param1;
            return;
        }//end

        public  (...args )*
        {
            _loc_5 = null;
            int argslen =args.length ;
            if (argslen == 0)
            {
                if (!this.m_gotZeroArgResult)
                {
                    this.m_zeroArgResult = this.m_func();
                    this.m_gotZeroArgResult = true;
                }
                return this.m_zeroArgResult;
            }
            _loc_3 = this.m_dict ;
            int _loc_4 =0;
            while (true)
            {

                _loc_5 = args.get(_loc_4);
                if (_loc_4 >= (argslen - 1))
                {
                    break;
                }
                if (!(_loc_5 in _loc_3))
                {
                    //_loc_3.put(_loc_5,  new Dictionary(true));
					_loc_3.put(_loc_5,  true);
                }
                //_loc_3 = _loc_3.get(_loc_5);
                _loc_4 = _loc_4 + 1;
            }




            _loc_6 = this.m_func.apply(null ,args );

            return _loc_6;
        }//end

    }



