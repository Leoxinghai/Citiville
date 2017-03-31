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

import Display.aswingui.*;
import Modules.franchise.display.*;
//import flash.display.*;
import org.aswing.*;

    public class StarRatingRibbon extends Sprite
    {
        private JWindow m_win ;
        private JPanel m_container ;
        protected int m_rating ;
        protected boolean m_useCustomStars ;
        protected String m_assetPack ;
        protected int m_maxRating ;
        protected int m_customGap ;
        protected int m_width ;
        protected int m_height ;
        protected String m_starType ;
        private static  int DEFAULT_MAX_RATING =5;

        public  StarRatingRibbon (int param1 ,int param2 ,double param3 ,boolean param4 =false ,String param5 =null ,int param6 =5,int param7 =-5,String param8 ="fr_star")
        {
            this.m_rating = param3;
            this.m_useCustomStars = param4;
            this.m_assetPack = param5;
            this.m_maxRating = param6;
            this.m_customGap = param7;
            this.m_starType = param8;
            this.m_width = param1;
            this.m_height = param2;
            Global.delayedAssets.get(param5, this.onAssetsLoaded);
            return;
        }//end

        protected void  onAssetsLoaded (DisplayObject param1 ,String param2 )
        {
            _loc_3 = param1as Object ;
            this.m_container = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            MarginBackground _loc_4 =new MarginBackground(new _loc_3.marketcardTop ());
            this.m_container.setBackgroundDecorator(_loc_4);
            StarRatingComponent _loc_5 =new StarRatingComponent(this.m_rating ,this.m_useCustomStars ,this.m_assetPack ,this.m_maxRating ,this.m_customGap ,this.m_starType );
            this.m_container.append(_loc_5);
            _loc_6 = this.m_width /2-_loc_5.width /2;
            ASwingHelper.setEasyBorder(_loc_5, 1, _loc_6, 0, _loc_6);
            this.m_container.setPreferredWidth(this.m_width);
            this.m_container.setPreferredHeight(this.m_height);
            ASwingHelper.prepare(this.m_container);
            this.m_win = new JWindow(this);
            this.m_win.setContentPane(this.m_container);
            this.m_win.show();
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

    }



