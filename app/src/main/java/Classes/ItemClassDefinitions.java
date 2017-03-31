package Classes;

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
    public class ItemClassDefinitions
    {
        private static  Object typeHashMap ={business Business ,decoration ,npc ,pier ,plot ,ship ,harvest_ship ,residence ,road ,sidewalk ,parking_lot ,bridge ,bridgepart ,storage ,train_station ,wilderness ,municipal ,landmark ,itemStorage ,factory ,dockHouse ,dock ,zooEnclosure ,garden ,neighborhood ,mall ,municipalcenter ,hotel ,socialbusiness ,powerstation ,attraction ,wonder ,energyToggle ,university };
        private static  Class defaultClass =Decoration ;

        public  ItemClassDefinitions ()
        {
            return;
        }//end

        public static Class  getClassByType (String param1 ,String param2)
        {
            Class _loc_3 =null ;
            if (param2)
            {
                _loc_3 =(Class) getDefinitionByName("Classes." + param2);
            }
            else
            {
                _loc_3 = typeHashMap.get(param1);
            }
            if (!_loc_3)
            {
                _loc_3 = defaultClass;
            }
            return _loc_3;
        }//end

        public static Class  getClassByItemName (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            _loc_3 = getClassByItem(_loc_2);
            return _loc_3;
        }//end

        public static Class  getClassByItem (Item param1 )
        {
            _loc_2 = getClassByType(param1.type,param1.className);
            return _loc_2;
        }//end

        public static String  getTypeByClass (Class param1 )
        {
            String _loc_3 =null ;
            Class _loc_4 =null ;
            String _loc_2 =null ;
            for(int i0 = 0; i0 < typeHashMap.size(); i0++)
            {
            		_loc_3 = typeHashMap.get(i0);

                _loc_4 = typeHashMap.get(_loc_3);
                if (_loc_4 == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            return _loc_2;
        }//end

    }




