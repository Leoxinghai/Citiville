package Classes.inventory;

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
    public class BaseItemInventory extends BaseInventory
    {

        public  BaseItemInventory (Object param1 )
        {
            super(param1);
            return;
        }//end

         public boolean  isValidName (String param1 )
        {
            return Global.gameSettings().getItemByName(param1) != null;
        }//end

         public int  getCapacity (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            return _loc_2.inventoryLimit;
        }//end

    }



