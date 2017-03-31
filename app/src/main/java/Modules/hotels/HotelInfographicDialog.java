package Modules.hotels;

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
import Display.DialogUI.*;
//import flash.utils.*;

    public class HotelInfographicDialog extends GenericDialog
    {

        public  HotelInfographicDialog (MapResource param1)
        {
            super("", "HotelInfographic", GenericDialogView.TYPE_OK, null, "HotelInfographic");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, "assets/hotels/hotelsInfographic.png");
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = super.createAssetDict();
            _loc_1.put("hotelsInfographic",  m_assetDependencies.get("assets/hotels/hotelsInfographic.png"));
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            HotelInfographicDialogView _loc_2 =new HotelInfographicDialogView(param1 ,m_message ,m_title ,m_type );
            return _loc_2;
        }//end

    }



