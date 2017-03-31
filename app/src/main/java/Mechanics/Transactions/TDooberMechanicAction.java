package Mechanics.Transactions;

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
import Mechanics.*;

    public class TDooberMechanicAction extends TMechanicAction
    {

        public  TDooberMechanicAction (MechanicMapResource param1 ,String param2 ,Object param3 =null )
        {
            super(param1, param2, MechanicManager.ALL, param3);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (m_ownerObject && param1 && param1.data)
            {
                m_ownerObject.parseAndCheckDooberResults(param1.data);
            }
            return;
        }//end  

    }



