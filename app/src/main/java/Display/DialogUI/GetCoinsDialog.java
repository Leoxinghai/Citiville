package Display.DialogUI;

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

import Engine.Managers.*;
import Events.*;

    public class GetCoinsDialog extends GenericDialog
    {

        public  GetCoinsDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,boolean param5 =true ,int param6 =0)
        {
            super(param1, param2, param3, param4, "", "", param5);
            return;
        }//end  

         protected void  processSelection (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                StatsManager.count("get_coins", "click_ok");
                GlobalEngine.socialNetwork.redirect("money.php?ref=coinsDialog");
            }
            else if (event.button == GenericDialogView.NO)
            {
                StatsManager.count("get_coins", "click_cancel");
            }
            event.stopPropagation();
            return;
        }//end  

    }


