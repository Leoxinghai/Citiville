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
import Classes.LogicComponents.*;
//import flash.utils.*;
import validation.*;

    public class MapResourceValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  MapResourceValidationUtil ()
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
            this .m_validators.put( "checkState", boolean  (Object param1 );
            {
                _loc_2 = param1.check ;
                _loc_3 = param1.checkNot ;
                _loc_4 =                 !_loc_2 || (this as MapResource).getState() == _loc_2;
                _loc_5 =                 !_loc_3 || (this as MapResource).getState() != _loc_3;
                return _loc_4 && _loc_5;
            }//end
            ;
            this .m_validators.put( "isUpgradable", boolean  (Object param1 );
            {
                MunicipalComponentBase _loc_5 =null ;
                _loc_2 = param1.value != "false";
                _loc_3 = thisas Municipal ;
                _loc_4 = this(as MapResource ).isUpgradePossible ();
                if (_loc_3)
                {
                    _loc_5 = _loc_3.getLogicComponent();
                    _loc_4 = _loc_4 && _loc_5.passesExperimentGate();
                }
                return _loc_4 == _loc_2;
            }//end
            ;
            this .m_validators.put( "isNotItem", boolean  (Object param1 );
            {
                _loc_2 = (String)(param1.itemName );
                boolean _loc_3 =false ;
                if (this is MapResource)
                {
                    if (((MapResource)this).getItemName() != _loc_2)
                    {
                        _loc_3 = true;
                    }
                }
                return _loc_3;
            }//end
            ;
            return;
        }//end

    }



