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

import Classes.*;
import Engine.Transactions.*;

    public class TPurchaseRandomGardenFlower extends Transaction
    {
        protected MechanicMapResource m_target ;
        private Function m_completeFunction ;

        public  TPurchaseRandomGardenFlower (MechanicMapResource param1 ,Function param2 )
        {
            this.m_target = param1;
            this.m_completeFunction = param2;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("GameMechanicService.performMechanicAction", this.m_target.getId(), "loot", "all", {});
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1 && this.m_completeFunction is Function)
            {
                this.m_completeFunction(param1);
            }
            return;
        }//end  

    }



