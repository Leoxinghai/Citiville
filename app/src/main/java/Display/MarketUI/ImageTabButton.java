package Display.MarketUI;

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
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class ImageTabButton extends JButton
    {
        private DisplayObject m_upImg ;
        private DisplayObject m_overImg ;
        private Object m_descriptorObject ;

        public  ImageTabButton (String param1 ,String param2 ,Object param3 =null )
        {
            this.m_descriptorObject = param3;
            LoadingManager.load(Global.getAssetURL(param1), this.onUpImgLoaded, LoadingManager.PRIORITY_HIGH);
            LoadingManager.load(Global.getAssetURL(param2), this.onOverImgLoaded, LoadingManager.PRIORITY_HIGH);
            return;
        }//end  

        public boolean  isFullyLoaded ()
        {
            return this.m_upImg && this.m_overImg;
        }//end  

        private void  init ()
        {
            SimpleButton _loc_1 =null ;
            if (this.m_upImg && this.m_overImg)
            {
                _loc_1 = new SimpleButton(this.m_upImg, this.m_overImg, this.m_overImg, this.m_upImg);
                this.m_descriptorObject.put("button",  _loc_1);
                wrapSimpleButton(_loc_1);
                ASwingHelper.prepare(this);
            }
            return;
        }//end  

        private void  onUpImgLoaded (Event event )
        {
            this.m_upImg = (event.currentTarget as LoaderInfo).content;
            this.m_descriptorObject.put("image",  this.m_upImg);
            this.init();
            return;
        }//end  

        private void  onOverImgLoaded (Event event )
        {
            this.m_overImg = (event.currentTarget as LoaderInfo).content;
            this.init();
            return;
        }//end  

    }



