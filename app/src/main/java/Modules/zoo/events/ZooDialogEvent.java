package Modules.zoo.events;

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

//import flash.events.*;
    public class ZooDialogEvent extends Event
    {
        public String animalItemName ;
        public String hotspotLabel ;
        public int slotIndex ;
        public String enclosureName ;
        public static  String REMOVE_ANIMAL_FROM_DISPLAY ="zdeRemoveAnimalFromDisplay";
        public static  String ADD_ANIMAL_TO_DISPLAY ="zdeAddAnimalToDisplay";
        public static  String MOUSE_OVER_HOTSPOT ="zdeMouseOverHotspot";
        public static  String DISPLAY_UPDATE ="zdeDisplayUpdate";
        public static  String BUY_NEW_ANIMAL ="zdeBuyAnimal";
        public static  String UNLOCK_ENCLOSURE ="zdeUnlockEnclosure";

        public  ZooDialogEvent (String param1 ,String param2 ="",boolean param3 =false ,boolean param4 =false )
        {
            switch(param1)
            {
                case BUY_NEW_ANIMAL:
                case ADD_ANIMAL_TO_DISPLAY:
                {
                    this.animalItemName = param2;
                    break;
                }
                case UNLOCK_ENCLOSURE:
                {
                    this.enclosureName = param2;
                    break;
                }
                default:
                {
                    break;
                }
            }
            super(param1, param3, param4);
            return;
        }//end  

    }



