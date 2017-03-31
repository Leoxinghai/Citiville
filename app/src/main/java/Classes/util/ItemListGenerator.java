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

import Classes.*;
import Modules.remodel.*;

//import flash.utils.*;

    public class ItemListGenerator
    {

        public  ItemListGenerator ()
        {
            return;
        }//end

        public static Array  generate (String param1 ,Dictionary param2 )
        {
            Class _loc_4 =null ;
            Function _loc_5 =null ;
            Array _loc_3 =new Array();
            if (param1 && Global.gameSettings)
            {
             //   _loc_4 =(Class) ;
                _loc_5 =(Function) _loc_4.get(param1);
                if (_loc_5 != null)
                {
                    _loc_3 = _loc_5(param2);
                }
            }
            return _loc_3;
        }//end

        private static Array  allOfType (Dictionary param1 )
        {
            _loc_2 = (String)(param1.get( "type") );
            return Global.gameSettings().getItemsByType(_loc_2);
        }//end

        private static Array  remodelSkins (Dictionary param1 )
        {
            Array _loc_4 =null ;
            Vector _loc_5.<RemodelDefinition >=null ;
            RemodelDefinition _loc_6 =null ;
            _loc_2 = (String)(param1.get( "itemName") );
            _loc_3 =Global.gameSettings().getItemByName(_loc_2 );
            if (_loc_3)
            {
                _loc_4 = new Array();
                _loc_5 = _loc_3.getAllRemodelDefinitions();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_4.push(Global.gameSettings().getItemByName(_loc_6.remodelItemName));
                }
                _loc_4.sort(RemodelManager.compareRemodelSkins, Array.DESCENDING);
            }
            return _loc_4;
        }//end

    }



