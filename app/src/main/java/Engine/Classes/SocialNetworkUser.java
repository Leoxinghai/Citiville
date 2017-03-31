package Engine.Classes;

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

    public class SocialNetworkUser
    {
        public double snuid =0;
        public double snid =0;
        public String name ="";
        public String firstName ="";
        public String picture ="";
        public String gender ="";
        public String locale ="";

        public  SocialNetworkUser (double param1 ,double param2)
        {
            this.snuid = param1;
            this.snid = param2;
            return;
        }//end

        public String  uid ()
        {
            String _loc_1 =null ;
            if (this.snid == 0)
            {
                _loc_1 = this.snuid.toString();
            }
            else
            {
                _loc_1 = this.snid + ":" + this.snuid;
            }
            return _loc_1;
        }//end

    }



