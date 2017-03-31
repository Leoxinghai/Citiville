package Events;

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
    public class FriendBarEvent extends Event
    {
        public int delta ;
        public int length ;
        public static  String MOVE ="move";
        public static  String MOVE_END ="stepEnd";
        public static  String LOADED ="loaded";

        public void  FriendBarEvent (String param1 ,int param2 =0,int param3 =0,boolean param4 =false ,boolean param5 =false )
        {
            this.delta = param2;
            this.length = param3;
            super(param1, param4, param5);
            return;
        }//end  

    }



