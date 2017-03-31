package Transactions;

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

import Classes.util.*;
import Display.*;
import Engine.Transactions.*;

    public class TRenameCity extends Transaction
    {
        private String m_cityName ;

        public  TRenameCity (String param1 )
        {
            this.m_cityName = param1;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.setCityName", "renameCity", this.m_cityName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1.hasOwnProperty("name"))
            {
                UI.setCityName(param1.get("name"));
                if (param1.get("result") == INVALID_DATA)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "InvalidCityName"));
                    return;
                }
                GameTransactionManager.addTransaction(new TOnValidCityName(), true);
            }
            return;
        }//end  

    }



