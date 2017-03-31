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

    public class OverlayAssetRenderer extends MultiAssetRendererBase
    {

        public  OverlayAssetRenderer (boolean param1 =false )
        {
            super(param1);
            return;
        }//end  

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end  

         protected void  forEachDisplayLocationDirection (Function param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < config.rawXMLConfig.displayLocation.size(); i0++) 
            {
            		_loc_2 = config.rawXMLConfig.displayLocation.get(i0);
                
                _loc_3 = _loc_2.@direction.toString();
                param1(_loc_3, _loc_2);
            }
            return;
        }//end  

         protected void  forEachDisplayLocationWithinDirection (Function param1 ,XML param2 )
        {
            _loc_3 = parseFloat(param2.@mapX.toString());
            _loc_4 = parseFloat(param2.@mapY.toString());
            param1(0, _loc_3, _loc_4);
            return;
        }//end  

        private void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.obj == config.type)
            {
                onSourceDataChanged();
            }
            return;
        }//end  

         protected Array  getSourceData ()
        {
            return .get(owner.getDataForMechanic(config.type));
        }//end  

    }



