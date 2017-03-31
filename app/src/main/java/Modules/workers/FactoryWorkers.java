package Modules.workers;

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
    public class FactoryWorkers extends Workers
    {
        public static  String CONTRACT_NAME ="contractName";

        public  FactoryWorkers ()
        {
            return;
        }//end

         public int  maxWorkers ()
        {
            _loc_1 =Global.gameSettings().getItemByName(this.contractName );
            int _loc_2 =0;
            if (_loc_1.workers)
            {
                _loc_2 = _loc_1.workers.members.length;
            }
            return _loc_2;
        }//end

        public String  contractName ()
        {
            return getAttribute(CONTRACT_NAME, "");
        }//end

        public void  contractName (String param1 )
        {
            setAttribute(CONTRACT_NAME, param1);
            return;
        }//end

    }



