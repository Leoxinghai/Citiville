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
import org.aswing.flyfish.awml.*;

    public class AssetValue extends Object implements ValueModel
    {
        private String text ;
        private String textValue ;
        private DisplayObject asset ;

        public  AssetValue (String param1 ="",String param2 ="",DisplayObject param3 =null )
        {
            this.text = param1;
            this.textValue = param2;
            this.asset = param3;
            return;
        }//end

        public String  getText ()
        {
            return this.text;
        }//end

        public String  getTextValue ()
        {
            return this.textValue;
        }//end

        public boolean  isLinkage ()
        {
            return this.text.length > 0 && this.text.charAt(0) == "@";
        }//end

        public boolean  isNull ()
        {
            return this.text == "" || this.text == "@";
        }//end

        public Object getValue ()
        {
            return this.asset;
        }//end

    }


