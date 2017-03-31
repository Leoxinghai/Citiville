package Display.aswingui;

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

//import flash.display.*;
//import flash.system.*;
import org.aswing.zynga.AssetButtonBackground;

    public class CustomButtonBackground extends AssetButtonBackground
    {
        private Class m_upImage ;
        private Class m_overImage ;
        private Class m_pressedImage ;

        public  CustomButtonBackground (Class param1 ,Class param2 ,Class param3 =null )
        {
            this.m_upImage = param1;
            this.m_overImage = param2;
            this.m_pressedImage = param3;
            super(null, null);
            this.reflectAssets(null, null);
            return;
        }//end

         public void  reflectAssets (String param1 ,ApplicationDomain param2 )
        {
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            DisplayObject _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            if (this.m_upImage)
            {
                _loc_7 =(DisplayObject) new this.m_upImage();
                _loc_8 =(DisplayObject) new this.m_upImage();
                _loc_9 =(DisplayObject) new this.m_upImage();
                _loc_10 =(DisplayObject) new this.m_upImage();
            }
            if (this.m_overImage)
            {
                _loc_3 =(DisplayObject) new this.m_overImage();
                _loc_4 =(DisplayObject) new this.m_overImage();
            }
            else
            {
                _loc_3 =(DisplayObject) new this.m_upImage();
                _loc_4 =(DisplayObject) new this.m_upImage();
            }
            if (this.m_pressedImage)
            {
                _loc_5 = createInstance(param1 + "down", param2);
                _loc_6 = createInstance(param1 + "downSelected", param2);
            }
            else
            {
                _loc_5 =(DisplayObject) new this.m_upImage();
                _loc_6 =(DisplayObject) new this.m_upImage();
            }
            stateAsset.setDefaultButtonImage(_loc_7);
            stateAsset.setDefaultImage(_loc_7);
            stateAsset.setPressedImage(_loc_5);
            stateAsset.setPressedSelectedImage(_loc_6);
            stateAsset.setDisabledImage(_loc_8);
            stateAsset.setSelectedImage(_loc_9);
            stateAsset.setDisabledSelectedImage(_loc_10);
            stateAsset.setRolloverImage(_loc_3);
            stateAsset.setRolloverSelectedImage(_loc_4);
            return;
        }//end

    }


