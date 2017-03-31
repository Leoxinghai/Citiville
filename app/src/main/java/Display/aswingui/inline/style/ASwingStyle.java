package Display.aswingui.inline.style;

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

import org.aswing.*;
    public class ASwingStyle implements IASwingStyle
    {
        private ASwingStyleProperties m_properties ;
        private static Stylizer s_stylizer ;

        public  ASwingStyle (ASwingStyleProperties param1 )
        {
            this.m_properties = param1;
            return;
        }//end

        public void  apply (Component param1 )
        {
            if (!s_stylizer)
            {
                s_stylizer = new Stylizer();
            }
            s_stylizer.apply(this.m_properties, param1);
            return;
        }//end

        public void  inherit (IASwingStyle param1 )
        {
            _loc_2 = param1.properties ;
            if (!this.properties.color)
            {
                this.properties.color = _loc_2.color;
            }
            if (!this.properties.fontFamily)
            {
                this.properties.fontFamily = _loc_2.fontFamily;
            }
            if (isNaN(this.properties.fontSize) && !isNaN(_loc_2.fontSize))
            {
                this.properties.fontSize = _loc_2.fontSize;
            }
            if (!this.properties.fontStyle)
            {
                this.properties.fontStyle = _loc_2.fontStyle;
            }
            if (!this.properties.fontWeight)
            {
                this.properties.fontWeight = _loc_2.fontWeight;
            }
            if (isNaN(this.properties.letterSpacing) && !isNaN(_loc_2.letterSpacing))
            {
                this.properties.letterSpacing = _loc_2.letterSpacing;
            }
            if (isNaN(this.properties.textAlign) && !isNaN(_loc_2.textAlign))
            {
                this.properties.textAlign = _loc_2.textAlign;
            }
            if (!this.properties.textDecoration)
            {
                this.properties.textDecoration = _loc_2.textDecoration;
            }
            if (!this.properties.textTransform)
            {
                this.properties.textTransform = _loc_2.textTransform;
            }
            return;
        }//end

        public ASwingStyleProperties  properties ()
        {
            return this.m_properties;
        }//end

    }


