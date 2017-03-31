package Classes.Desires;

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

    public class DesireTypes
    {
        public static  String VISIT_BUSINESS ="DVisitBusiness";
        public static  String GO_HOME ="DGoHome";
        public static  String WANDER_ROADS ="DWanderRoads";
        public static  String GO_HOTEL ="DGoHotel";
        public static  String GO_CRUISE_SHIP ="DGoCruiseShip";
        public static  String VISIT_ATTRACTION ="DVisitAttraction";
        public static  Array DESIRE_TYPES =.get(Desire ,DVisitBusiness ,DGoHome ,DWanderRoads ,DGoHotel ,DGoCruiseShip ,DVisitAttraction) ;

        public  DesireTypes ()
        {
            return;
        }//end

    }



