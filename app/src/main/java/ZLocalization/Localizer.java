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

import Engine.Interfaces.*;
import ZLocalization.Substituters.*;

    public class Localizer implements ILocalizer
    {
        public  String MASC ="masc";
        public  String FEM ="fem";
        private  String IMAGE_PACKAGE ="LocalizedImages";
        protected  String LOCALE_DELIMITER ="_";
        private  int NO_COUNT =-1;
        private  String DEFAULT_GENDER ="masc";
        protected SubstituterSimple m_substituter ;
        protected Object m_raw ;
        protected Object m_cached ;
        protected String m_locale ;
        public IFontMapper m_fontMapper ;

        public static Object substituterMap ={es SubstituterEs ,en_UD ,en_PI ,it ,pt ,fr ,de };

        public  Localizer (Object param1 )
        {
            return;
        }//end

        protected void  setSubstituter ()
        {
            if (substituterMap.hasOwnProperty(this.m_locale))
            {
                this.m_substituter = new substituterMap.get(this.m_locale);
            }
            else if (substituterMap.hasOwnProperty(this.langCode))
            {
                this.m_substituter = new substituterMap.get(this.langCode);
            }
            else
            {
                this.m_substituter = new SubstituterSimple();
            }
            return;
        }//end

        public String  langCode ()
        {
            return this.m_locale.split(this.LOCALE_DELIMITER).get(0);
        }//end

        public String  localeCode ()
        {
            //add by xinghai
            return "zh_CN";

            //return this.m_locale;
        }//end

        public void  setAsGlobalInstance ()
        {
            ZLoc.instance = this;
            return;
        }//end

        public String  t (String param1 ,String param2 ,Object param3 =null )
        {
            return this.translate(param1, param2, param3);
        }//end

        public LocalizationObjectToken  tk (String param1 ,String param2 ,String param3 ="",int param4 =-1)
        {
            return this.createToken(param1, param2, param3, param4);
        }//end

        public LocalizationName  tn (String param1 ,String param2 ="masc")
        {
            return this.createName(param1, param2);
        }//end

        public LocalizationObjectToken  createToken (String param1 ,String param2 ,String param3 ="",int param4 =-1)
        {
            return new LocalizationObjectToken(this.getString(param1, param2), param3, param4);
        }//end

        public LocalizationName  createName (String param1 ,String param2 ="masc")
        {
            return new LocalizationName(param1, param2);
        }//end

        public String  translate (String param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = this.getString(param1 ,param2 );
            if (this.getString(param1, param2) == null)
            {
                return "Cannot find string " + param2 + " in package " + param1 + ".";
            }
            return _loc_4.toString(param3 || {}, this.m_substituter);
        }//end

        public String  translateImagePath (String param1 )
        {
            _loc_2 = this.getString(this.IMAGE_PACKAGE ,param1 );
            if (_loc_2 == null)
            {
                return "Cannot find path " + param1;
            }
            return _loc_2.getVariation(LocalizedString.ORIGINAL);
        }//end

        public LocalizedString  getString (String param1 ,String param2 )
        {
            return null;
        }//end

        public IFontMapper  getFontMapper ()
        {
            return this.m_fontMapper;
        }//end

        public boolean  exists (String param1 ,String param2 )
        {
            _loc_3 = this.getString(param1 ,param2 );
            return _loc_3 != null;
        }//end

    }


