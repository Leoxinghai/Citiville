package Mechanics.ClientDisplayMechanics;

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

import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class MultiAssetRenderer extends MultiAssetRendererBase
    {
        private String m_dataSourceType ;
        public static  String DEFAULT_IMAGE_NAME ="static";

        public  MultiAssetRenderer ()
        {
            return;
        }//end  

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            if (!config.rawXMLConfig.hasOwnProperty("displayLocations"))
            {
                return;
            }
            this.m_dataSourceType = config.params.get("dataSourceType");
            owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end  

         protected void  forEachDisplayLocationDirection (Function param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < config.rawXMLConfig.displayLocations.size(); i0++) 
            {
            	_loc_2 = config.rawXMLConfig.displayLocations.get(i0);
                
                if (_loc_2.hasOwnProperty("location"))
                {
                    _loc_3 = _loc_2.@direction.toString();
                    param1(_loc_3, _loc_2);
                }
            }
            return;
        }//end  

         protected void  forEachDisplayLocationWithinDirection (Function param1 ,XML param2 )
        {
            XML _loc_3 =null ;
            int _loc_4 =0;
            double _loc_5 =0;
            double _loc_6 =0;
            for(int i0 = 0; i0 < param2.location.size(); i0++) 
            {
            	_loc_3 = param2.location.get(i0);
                
                _loc_4 = parseInt(_loc_3.@id.toString());
                _loc_5 = parseFloat(_loc_3.@mapX.toString());
                _loc_6 = parseFloat(_loc_3.@mapY.toString());
                param1(_loc_4, _loc_5, _loc_6);
            }
            return;
        }//end  

        private void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.obj == this.m_dataSourceType)
            {
                onSourceDataChanged();
            }
            return;
        }//end  

         protected Array  getSourceData ()
        {
            return owner.getDataForMechanic(this.m_dataSourceType);
        }//end  

    }




