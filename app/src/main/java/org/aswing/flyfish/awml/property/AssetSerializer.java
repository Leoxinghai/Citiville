package org.aswing.flyfish.awml.property;

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
//import flash.net.*;
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.awml.*;
import org.aswing.flyfish.util.*;

    public class AssetSerializer extends Object implements PropertySerializer
    {
        private static int id_counter =0;

        public  AssetSerializer ()
        {
            return;
        }//end

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            _loc_3 = param1+"";
            return decode(_loc_3);
        }//end

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = param1as AssetValue ;
            if (_loc_3)
            {
                return new XML(_loc_3.getText());
            }
            return new XML("");
        }//end

        public Array  getCodeLines (ValueModel param1 ,ProModel param2 )
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            _loc_10 = id_counter+1;
            id_counter = _loc_10;
            _loc_3 = param1as AssetValue ;
            if (_loc_3)
            {
                if (_loc_3.isLinkage())
                {
                    if (_loc_3.getTextValue().indexOf("#") >= 0)
                    {
                        _loc_4 = "loader" + id_counter;
                        _loc_5 = _loc_3.getTextValue().split("#");
                        _loc_6 = _loc_5.get(0);
                        _loc_7 = _loc_5.get(1);
                        return .get("var " + _loc_4 + ":LinkageLazyLoader = new LinkageLazyLoader(\'" + _loc_6 + "\', \'" + _loc_7 + "\');", _loc_4 + ".startLoad();", _loc_4);
                    }
                    return .get("ResourceManager.ins.getAsset(\'" + _loc_3.getTextValue() + "\')");
                }
                else
                {
                    _loc_4 = "loader" + id_counter;
                    _loc_8 = "ResourceManager.ins.getResourceUrl(\'" + _loc_3.getTextValue() + "\')";
                    return .get("var " + _loc_4 + ":AssetLoader = new AssetLoader();", _loc_4 + ".load(new URLRequest(" + _loc_8 + "));", _loc_4);
                }
            }
            return .get("null");
        }//end

        public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            return null;
        }//end

        public static AssetValue  decode (String param1 )
        {
            String _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            LinkageLazyLoader _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            AssetLoader _loc_10 =null ;
            int _loc_11 =0;
            int _loc_12 =0;
            if (param1.charAt(0) == "@")
            {
                _loc_2 = param1.substr(1);
                if (_loc_2.indexOf("#") >= 0)
                {
                    _loc_4 = _loc_2.split("#");
                    _loc_5 = _loc_4.get(0);
                    _loc_6 = _loc_4.get(1);
                    _loc_7 = new LinkageLazyLoader(_loc_5, _loc_6);
                    _loc_7.startLoad();
                    return new AssetValue(param1, _loc_2, _loc_7);
                }
                _loc_3 = ResourceManager.ins.getAsset(_loc_2);
                if (_loc_3 == null)
                {
                    _loc_3 = DisplayUtils.createNoneAssetShape();
                }
                return new AssetValue(param1, _loc_2, _loc_3);
            }
            else
            {
                _loc_8 = param1;
                if (param1.indexOf("url") == 0)
                {
                    _loc_11 = param1.indexOf("(") + 2;
                    _loc_12 = param1.indexOf(")") - 1;
                    _loc_8 = param1.substring(_loc_11, _loc_12);
                }
                _loc_9 = ResourceManager.ins.getResourceUrl(_loc_8);
                _loc_10 = new AssetLoader();
                _loc_10.load(new URLRequest(_loc_9));
                return new AssetValue(param1, _loc_8, _loc_10);
            }
        }//end

    }


