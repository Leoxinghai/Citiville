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

    public class FriendValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  FriendValidationUtil ()
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
            this .m_validators.get( "property", boolean  (Object param1 );
            {
                return this.hasOwnProperty(param1.get("property")) && this.get(param1.get("property"));
            }//end
            ;
            this .m_validators.get( "hasHelpRequests") =boolean  (Object param1 =null )
            {
                return ((Friend)this).helpRequests > 0;
            }//end
            ;
            this .m_validators.get( "neighborVisitSortCondition") =boolean  (Object param1 =null )
            {
                if (((Friend)this).uid == Global.getVisiting())
                {
                    return ((Friend)this).helpRequests > 0 && Global.player.visitorEnergy > 0;
                }
                return ((Friend)this).helpRequests > 0 && (this as Friend).energyLeft > 0;
            }//end
            ;
            this .m_validators.get( "noVisitThemedCitySam") =boolean  (Object param1 =null )
            {
                if (((Friend)this).uid == "-1")
                {
                    return !Global.player.hasVisitedCitySam;
                }
                return false;
            }//end
            ;
            return;
        }//end

    }



