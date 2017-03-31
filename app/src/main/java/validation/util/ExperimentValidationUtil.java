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

//import flash.utils.*;
import validation.*;

    public class ExperimentValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  ExperimentValidationUtil ()
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
            this .m_validators.put( "isVariantGreaterThan", boolean  (Object param1 );
            {
                _loc_2 =(double)(param1.variant );
                _loc_3 = param1.experiment ;
                _loc_4 =Global.experimentManager.getVariant(_loc_3 );
                return Global.experimentManager.getVariant(_loc_3) > _loc_2;
            }//end
            ;
            this .m_validators.put( "isInSimpleExperiment", boolean  (Object param1 );
            {
                _loc_2 = param1.experiment ;
                _loc_3 =Global.experimentManager.getVariant(_loc_2 );
                return Global.experimentManager.getVariant(_loc_2) == true;
            }//end
            ;
            this .m_validators.put( "isNotInSimpleExperiment", boolean  (Object param1 );
            {
                _loc_2 = param1.experiment ;
                _loc_3 =Global.experimentManager.getVariant(_loc_2 );
                return Global.experimentManager.getVariant(_loc_2) == false;
            }//end
            ;
            return;
        }//end

    }



