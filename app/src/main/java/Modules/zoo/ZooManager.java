package Modules.zoo;

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

    public class ZooManager
    {
        public static  String MECHANIC_SLOTS ="slots";
        public static  String MECHANIC_STORAGE ="storage";
        public static  String MECHANIC_DIALOG_GENERATOR ="dialogGenerator";
        public static  String MECHANIC_REWARDS_DIALOG ="rewardsDialog";
        public static  String COMMON ="common";
        public static  String RARE ="rare";
        public static  String UNCOMMON ="uncommon";
        public static  String ZOO_UNLOCK ="zooUnlock_";

        public  ZooManager ()
        {
            return;
        }//end

        public Array  getAllAnimals (String param1 ="")
        {
            Array _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = Global.gameSettings().getItemsByKeywords(.get(param1));
            }
            else
            {
                _loc_2 = Global.gameSettings().getItemsByKeywords(.get("zoo_animal"));
            }
            return _loc_2;
        }//end

        public Dictionary  getAllAnimalsByRarity (String param1 )
        {
            Item _loc_4 =null ;
            _loc_2 = this.getAllAnimals(param1 );
            Dictionary _loc_3 =new Dictionary ();
            _loc_3.put(ZooManager.COMMON,  new Array());
            _loc_3.put(ZooManager.UNCOMMON,  new Array());
            _loc_3.put(ZooManager.RARE,  new Array());
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                switch(_loc_4.rarity)
                {
                    case ZooManager.COMMON:
                    {
                        _loc_3.get(ZooManager.COMMON).push(_loc_4.name);
                        break;
                    }
                    case ZooManager.UNCOMMON:
                    {
                        _loc_3.get(ZooManager.UNCOMMON).push(_loc_4.name);
                        break;
                    }
                    case ZooManager.RARE:
                    {
                        _loc_3.get(ZooManager.RARE).push(_loc_4.name);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_3;
        }//end

        public boolean  isAnimal (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            return _loc_2 && _loc_2.itemHasKeyword("zoo_animal");
        }//end

    }



