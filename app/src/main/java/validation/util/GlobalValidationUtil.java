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

import Classes.util.*;
import Init.*;
//import flash.utils.*;
import validation.*;

    public class GlobalValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  GlobalValidationUtil ()
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
            this .m_validators.put( "isVisiting", boolean  (Object param1 );
            {
                _loc_2 = param1.check !== "false";
                return Global.isVisiting() == _loc_2;
            }//end
            ;
            this .m_validators.put( "isLevelAtLeast", boolean  (Object param1 );
            {
                _loc_2 = (int)(param1.level );
                return Global.player.level >= _loc_2;
            }//end
            ;
            this .m_validators.put( "isBeforeDate", boolean  (Object param1 );
            {
                double _loc_3 =0;
                _loc_2 = param1.get("date") ;
                if (_loc_2)
                {
                    _loc_3 = DateFormatter.parseTimeString(param1.get("date"));
                    return GameSettingsInit.getCurrentTime() < _loc_3;
                }
                return false;
            }//end
            ;
            this .m_validators.put( "playerHasCoupon", boolean  (Object param1 );
            {
                if (param1.couponName)
                {
                    return Global.player.hasCoupon(String(param1.couponName));
                }
                return false;
            }//end
            ;
            this .m_validators.put( "playerHasNotFlag", boolean  (Object param1 );
            {
                if (param1.flag)
                {
                    return Global.player.getFlag(String(param1.flag)).value <= 0;
                }
                return false;
            }//end
            ;
            return;
        }//end

    }



