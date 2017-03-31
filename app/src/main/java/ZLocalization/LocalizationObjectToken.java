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

    public class LocalizationObjectToken extends LocalizationToken
    {
        private  int NO_COUNT =-1;
        private  String DEFAULT_GENDER ="masc";
        private  String SINGULAR ="singular";
        private  String PLURAL ="plural";
        private  String ATTRIBUTE_DELIM =",";
        protected LocalizedString m_locString ;
        public static  Object GENDERS ={masc fem 0,1neuter ,2};
public static Array VALID_ATTRIUBTES =.get( "plural","singular","count","indefinite","definite","masc","fem","common","neuter") ;
public static Array LOOKUP_ATTRIBUTES =.get(.get( "indefinite","definite") ,.get( "singular","plural") ,.get( "accusative")) ;

        public  LocalizationObjectToken (LocalizedString param1 ,String param2 ="",int param3 =-1)
        {
            this.m_locString = param1;
            if (param2 != "")
            {
                m_attributes = m_attributes.concat(param2.split(this.ATTRIBUTE_DELIM));
            }
            if (param3 != this.NO_COUNT)
            {
                m_attributes.push(param3 == 1 ? (this.SINGULAR) : (this.PLURAL));
            }
            if (this.m_locString && this.m_locString.gender != null)
            {
                m_attributes.push(this.m_locString.gender);
            }
            return;
        }//end

         public String  getString ()
        {
            Array _loc_3 =null ;
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            while (_loc_2 < LOOKUP_ATTRIBUTES.length())
            {

                _loc_3 = Utilities.arrayIntersect(m_attributes, LOOKUP_ATTRIBUTES.get(_loc_2));
                if (_loc_3.length > 0)
                {
                    if (_loc_3.length == 1)
                    {
                        _loc_1.push(_loc_3.get(0));
                    }
                    else
                    {
                        throw new Error("Token attributes lead to an ambiguous variant.");
                    }
                }
                _loc_2++;
            }
            return this.m_locString.getVariation(_loc_1.join("_"));
        }//end

    }


