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

    public class PlayerNews
    {
        public int ver ;
        public String senderId ;
        public String senderName ;
        public String type ;
        public String object ;
        public double timestampMs ;
        public static  String NEWSTYPE_VISITED ="v";
        public static  String NEWSTYPE_GIFT_SENT ="gs";
        public static  String NEWSTYPE_GIFT_ACCEPTED ="ga";

        public  PlayerNews (Object param1 )
        {
            if (param1.ver == null || int(param1.ver) <= 1)
            {
                this.ver = 1;
                this.senderId = String(param1.senderId);
                this.senderName = String(param1.senderName);
                this.type = String(param1.type);
                this.object = String(param1.object);
                this.timestampMs = Number(param1.timestamp) * 1000;
            }
            return;
        }//end

        public static Array  loadFromObjects (Array param1 )
        {
            defs = param1;
            return defs .map (PlayerNews  (Object param11 ,...args )
            {
                return new PlayerNews(param11);
            }//end
            );
        }//end

    }


