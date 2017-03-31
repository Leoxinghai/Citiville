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

import Classes.*;
import Engine.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;

    public class SWFDialog extends Dialog
    {
        protected String m_dialogAsset =null ;
        protected Loader m_loader ;

        public  SWFDialog (boolean param1 =true )
        {
            super(param1);
            if (this.m_dialogAsset)
            {
                this.m_loader = LoadingManager.load(Global.getAssetURL(this.m_dialogAsset), this.onLoaderEvent);
                this.visible = false;
            }
            return;
        }//end

        private void  onLoaderEvent (Event event )
        {
            if (this.m_loader && this.m_loader.content)
            {
                m_content = this.m_loader.content;
            }
            this.onLoadComplete();
            onDialogInitialized();
            return;
        }//end

        protected void  onLoadComplete ()
        {
            return;
        }//end

        protected void  addIcon (String param1 ,DisplayObjectContainer param2 ,boolean param3 =false ,double param4 =60,double param5 =60)
        {
            int _loc_7 =0;
            DisplayObject _loc_8 =null ;
            _loc_6 = LoadingManager.load(param1,Delegate.create(this,this.iconLoaded,param2,param3,param4,param5));
            if (param2.parent)
            {
                _loc_7 = param2.numChildren;
                while (_loc_7 > 0)
                {

                    _loc_8 = param2.getChildAt((_loc_7 - 1));
                    TweenLite.killTweensOf(_loc_8);
                    if (_loc_8 instanceof Loader)
                    {
                        LoadingManager.cancelLoad(Loader(_loc_8));
                    }
                    param2.removeChildAt((_loc_7 - 1));
                    _loc_7 = _loc_7 - 1;
                }
            }
            param2.addChild(_loc_6);
            return;
        }//end

        protected void  iconLoaded (Event event ,DisplayObjectContainer param2 ,boolean param3 =false ,double param4 =60,double param5 =60)
        {
            _loc_6 = (LoaderInfo)event.target
            if (!(event.target as LoaderInfo && _loc_6.content))
            {
                return;
            }
            if (_loc_6.loader.parent != param2)
            {
                return;
            }
            if (param2.parent)
            {
                Utilities.removeAllChildren(param2);
            }
            _loc_7 = Utilities.getSmoothableBitmap(_loc_6.content);
            Utilities.getSmoothableBitmap(_loc_6.content).width = param4;
            _loc_7.height = param5;
            if (param3)
            {
                _loc_7.x = (-param4) / 2;
                _loc_7.y = (-param5) / 2;
            }
            _loc_7.alpha = 0;
            TweenLite.to(_loc_7, 0.1, {alpha:1});
            param2.addChild(_loc_7);
            return;
        }//end

    }



