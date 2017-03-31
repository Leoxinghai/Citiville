package validation.util;

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
import validation.*;

    public class WonderValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  WonderValidationUtil ()
        {
            this.loadValidators();
            return;
        }//end

        public Function  getValidationCallback (String param1 )
        {
            return this.m_validators.get(param1);
        }//end

        protected void  loadValidators ()
        {
            this.m_validators = new Dictionary();
            this .m_validators.put( "isThisWonderActive", boolean  (Object param1 );
            {
                double _loc_5 =0;
                double _loc_6 =0;
                double _loc_7 =0;
                double _loc_8 =0;
                _loc_2 = (String)(param1.mechanicData );
                _loc_3 = (String)(param1.timerDuration );
                if (!_loc_2 || !_loc_3)
                {
                    return false;
                }
                _loc_4 = parseFloat(_loc_3);
                if (this is MechanicMapResource)
                {
                    _loc_5 = ((MechanicMapResource)this).getDataForMechanic(_loc_2);
                    _loc_6 = _loc_5 + _loc_4;
                    _loc_7 = GlobalEngine.getTimer() / 1000;
                    _loc_8 = _loc_6 - _loc_7 >= 0 ? (_loc_6 - _loc_7) : (0);
                    if (_loc_5 > 0 && _loc_8 > 0)
                    {
                        return true;
                    }
                }
                return false;
            }//end
            ;
            return;
        }//end

    }



