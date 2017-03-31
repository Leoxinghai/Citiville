package Modules.cars;

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

import Transactions.*;
    public class THarvestCar extends TFarmTransaction
    {
        private double m_harvestValue ;
        private String m_name ;

        public  THarvestCar (String param1 ,double param2 )
        {
            this.m_name = param1;
            this.m_harvestValue = param2;
            return;
        }//end  

         public void  perform ()
        {
            String _loc_1 ="";
            if (Global.questManager.isQuestActive("qf_cars2"))
            {
                _loc_1 = "special";
            }
            signedCall("CarService.harvestCar", this.m_name, this.m_harvestValue, _loc_1);
            return;
        }//end  

    }



