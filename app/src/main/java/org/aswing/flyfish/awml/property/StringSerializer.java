﻿package org.aswing.flyfish.awml.property;

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

import org.aswing.flyfish.awml.*;
    public class StringSerializer extends Object implements PropertySerializer, DefaultValueHelper
    {

        public  StringSerializer ()
        {
            return;
        }//end  

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            return new SimpleValue(param1 + "");
        }//end  

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = XML(param1.getValue());
            return _loc_3;
        }//end  

        public Array  getCodeLines (ValueModel param1 ,ProModel param2 )
        {
            return null;
        }//end  

        public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            if (param2.getDef().getEditorParam() == "no-generate-code")
            {
                return null;
            }
            return "\"" + param1 + "\"";
        }//end  

        public ValueModel  getDefaultValue (String param1 ,Model param2 )
        {
            if (param2 is ComModel && param1 == ComModel.ID_NAME)
            {
                return new SimpleValue(ComModel(param2).getID());
            }
            throw new Error("It is not default value helped!");
        }//end  

        public boolean  isNeedHelp (String param1 ,Model param2 )
        {
            if (param2 is ComModel && param1 == ComModel.ID_NAME)
            {
                return true;
            }
            return false;
        }//end  

    }


