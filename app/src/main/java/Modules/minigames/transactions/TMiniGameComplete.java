package Modules.minigames.transactions;

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

import Transactions.*;
    public class TMiniGameComplete extends TFarmTransaction
    {
        protected String m_name ;
        protected Object m_data ;

        public  TMiniGameComplete (String param1 ,Object param2 )
        {
            this.m_name = param1;
            this.m_data = param2;
            return;
        }//end  

        public String  getGameName ()
        {
            return this.m_name;
        }//end  

         public void  perform ()
        {
            signedCall("MiniGameService.onMiniGameComplete", this.m_name, this.m_data);
            return;
        }//end  

    }



