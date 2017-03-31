package Display;

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

    public class QueuedIcon
    {
        public String code ;
        public String name ;
        public int priority ;
        public String group ;
        public boolean persists ;
        public String imageUrl ;
        public String marketDest ;
        public boolean hasPopup ;
        public boolean giftRedirect ;
        public boolean giftBox ;

        public  QueuedIcon (XML param1 )
        {
            this.code = param1.@code.toString();
            this.name = param1.@name.toString();
            this.priority = parseInt(param1.priority.toString());
            this.group = param1.group.toString();
            this.persists = parseInt(param1.persists.toString()) != 0;
            this.imageUrl = param1.image.@url.toString();
            this.marketDest = param1.marketDest.toString();
            this.hasPopup = parseInt(param1.hasPopup.toString()) != 0;
            this.giftRedirect = parseInt(param1.sendToGiftPage.toString()) != 0;
            this.giftBox = parseInt(param1.showGiftBox.toString()) != 0;
            return;
        }//end

    }



