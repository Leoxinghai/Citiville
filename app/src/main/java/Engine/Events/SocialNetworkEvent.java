package Engine.Events;

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
    public class SocialNetworkEvent extends Event
    {
        public static  String GET_USER_COMPLETE ="getUserComplete";
        public static  String GET_FRIENDS_COMPLETE ="getFriendsComplete";
        public static  String GET_APP_FRIENDS_COMPLETE ="getAppFriendsComplete";
        public static  String LOGIN_COMPLETE ="loginComplete";

        public  SocialNetworkEvent (String param1 ,boolean param2 =false ,boolean param3 =false )
        {
            super(param1, param2, param3);
            return;
        }//end  

    }



