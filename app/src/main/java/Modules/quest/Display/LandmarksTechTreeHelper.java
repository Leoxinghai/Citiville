package Modules.quest.Display;

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
import Classes.util.*;
import Modules.landmarks.*;
import Transactions.*;

    public class LandmarksTechTreeHelper extends LandmarksTechTreeDialog
    {

        public  LandmarksTechTreeHelper (Object param1 ,boolean param2 =false )
        {
            MapResource _loc_3 =null ;
            String _loc_4 =null ;
            Object _loc_6 =null ;
            _loc_5 = param1.hasOwnProperty("rewards")? (param1.rewards) : (null);
            if (param1.hasOwnProperty("rewards") ? (param1.rewards) : (null))
            {
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    if (_loc_6.resource == "item")
                    {
                        _loc_4 = _loc_6.itemName;
                        break;
                    }
                }
            }
            GameTransactionManager.addTransaction(new TSeenQuest(param1.name), true);
            super(Global.gameSettings().getItemByName(_loc_4));
            return;
        }//end

    }



