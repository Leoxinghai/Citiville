package Modules.hotels;

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
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class SocialBusinessGuestDialogView extends HotelsGuestDialogView
    {

        public  SocialBusinessGuestDialogView (Dictionary param1 ,MechanicMapResource param2 ,Function param3 ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="",boolean param13 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13);
            return;
        }//end  

         protected void  drawSquareWindow (Rectangle param1 )
        {
            boolean _loc_2 =false ;
            if (!m_litWindow)
            {
                m_litWindow = new Sprite();
                _loc_2 = true;
            }
            m_litWindow.x = param1.x;
            m_litWindow.y = param1.y;
            m_litWindow.width = param1.width;
            m_litWindow.height = param1.height;
            m_litWindow.addChild(m_assetDict.get("social_business_bonusImage"));
            if (_loc_2)
            {
                this.addChild(m_litWindow);
            }
            return;
        }//end  

    }



