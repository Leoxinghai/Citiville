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

    public class RemoveItemEvent extends GenericPopupEvent
    {
        public String itemName ;
        public int itemCount ;

        public  RemoveItemEvent (String param1 ,int param2 ,String param3 ,int param4 ,boolean param5 =false )
        {
            super(param1, param2, param5);
            this.itemName = param3;
            this.itemCount = param4;
            return;
        }//end  

    }



