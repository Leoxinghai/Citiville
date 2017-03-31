package Classes.Managers;

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

    public class FlashMFSRecipient
    {
        public double zid ;
        public String name ;
        public String portraitURL ;
        public boolean selected ;

        public  FlashMFSRecipient (double param1 ,String param2 ,String param3 ,boolean param4 =false )
        {
            this.zid = param1;
            this.name = param2;
            this.portraitURL = param3;
            this.selected = param4;
            return;
        }//end

    }


