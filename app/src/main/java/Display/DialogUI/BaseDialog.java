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

//import flash.display.*;
//import flash.utils.*;

    public class BaseDialog extends GenericDialog
    {
        protected Object m_data ;
        private Array m_preloadAssetData ;

        public  BaseDialog (Object param1 )
        {
            this.m_data = param1;
            _loc_2 = this.m_data.hasOwnProperty("name")? (this.m_data.get("name")) : ("");
            _loc_3 = this.m_data.hasOwnProperty("statsTrackingName")? (this.m_data.get("statsTrackingName")) : ("");
            _loc_4 = this.m_data.hasOwnProperty("closeCallback")? (this.m_data.get("closeCallback")) : (null);
            super("", _loc_2, 0, _loc_4, "", "", true, 0, "", null, "", true, _loc_3);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_2 =null ;
            Object _loc_3 =null ;
            _loc_1 = super.getAssetDependencies();
            if (this.m_data.hasOwnProperty("background") && this.m_data.get("background").hasOwnProperty("url"))
            {
                _loc_1.push(this.m_data.get("background").get("url"));
            }
            else
            {
                delete this.m_data.get("background");
            }
            if (this.m_data.hasOwnProperty("preloadAssets") && this.m_data.get("preloadAssets") instanceof Array)
            {
                _loc_2 = this.m_data.get("preloadAssets");
                this.m_preloadAssetData = new Array();
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    if (_loc_3.hasOwnProperty("name") && _loc_3.hasOwnProperty("url"))
                    {
                        _loc_1.push(_loc_3.get("url"));
                        this.m_preloadAssetData.push(_loc_3);
                    }
                }
            }
            return _loc_1;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Object _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            Bitmap _loc_4 =null ;
            Class _loc_5 =null ;
            boolean _loc_6 =false ;
            Class _loc_7 =null ;
            Object _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            Object _loc_11 =null ;
            Dictionary _loc_1 =new Dictionary ();
            if (this.m_data.hasOwnProperty("background"))
            {
                _loc_2 = this.m_data.get("background");
                _loc_3 = null;
                if (_loc_2.hasOwnProperty("className"))
                {
                    _loc_5 = m_assetDependencies.get(_loc_2.get("url")).get(_loc_2.get("className"));
                    _loc_3 =(DisplayObject) new _loc_5;
                }
                else
                {
                    _loc_3 =(DisplayObject) m_assetDependencies.get(_loc_2.get("url"));
                }
                int _loc_12 =1;
                _loc_3.scaleY = 1;
                _loc_3.scaleX = _loc_12;
                if (_loc_3 instanceof Bitmap)
                {
                    _loc_4 = new Bitmap(((Bitmap)_loc_3).bitmapData);
                }
                DisplayObject _loc_121 =(_loc_4 !=null ? (_loc_4) : (_loc_3));
                _loc_1.put("dialog_bg",  _loc_121);
                m_assetBG = _loc_121;
            }
            else
            {
                _loc_6 = this.m_data.hasOwnProperty("hasCloseButton") ? (this.m_data.get("hasCloseButton")) : (true);
                _loc_7 = m_comObject.dialog_bg;
                if (this.m_data.hasOwnProperty("title"))
                {
                    _loc_7 = _loc_6 ? (m_comObject.dialog_bg) : (m_comObject.dialog_bg_noclose);
                }
                else
                {
                    _loc_7 = _loc_6 ? (m_comObject.dialog_bg_notitle) : (m_comObject.dialog_bg_notitle_noclose);
                }
                DisplayObject _loc_122 =(DisplayObject)new _loc_7;
                _loc_1.put("dialog_bg",  _loc_122);
                m_assetBG = _loc_122;
            }
            if (this.m_preloadAssetData)
            {
                for(int i0 = 0; i0 < this.m_preloadAssetData.size(); i0++)
                {
                	_loc_8 = this.m_preloadAssetData.get(i0);

                    _loc_9 = _loc_8.get("name");
                    _loc_10 = _loc_8.get("url");
                    _loc_11 = m_assetDependencies.get(_loc_10);
                    if (_loc_8.hasOwnProperty("className"))
                    {
                        _loc_11 = _loc_11.get(_loc_8.get("className"));
                    }
                    _loc_1.put(_loc_9,  _loc_11);
                }
            }
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            _loc_2 = this.m_data.hasOwnProperty("viewClass")? (this.m_data.get("viewClass")) : (BaseDialogView);
            return new _loc_2(param1, this.m_data);
        }//end

    }




