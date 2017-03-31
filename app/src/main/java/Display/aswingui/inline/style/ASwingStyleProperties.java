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
    public class ASwingStyleProperties
    {
        public double backgroundColor ;
        public Object backgroundImage ;
        public int backgroundRepeat ;
        public int backgroundWidth ;
        public int backgroundHeight ;
        public int borderColor ;
        public String borderStyle =null ;
        public int borderThickness =1;
        public double cornerRadius ;
        public double color ;
        public String fontFamily ;
        public double fontSize ;
        public Object fontSizeVariants ;
        public String fontStyle ;
        public String fontWeight ;
        public double letterSpacing ;
        public double textAlign ;
        public String textDecoration ;
        public String textTransform ;
        public Array filters ;
        private int m_padding ;
        public int paddingLeft ;
        public int paddingRight ;
        public int paddingTop ;
        public int paddingBottom ;
        public String skinClass ;
        public Icon icon ;
        public Class upSkin ;
        public Class overSkin ;
        public Class downSkin ;
        public Class disabledSkin ;
        public int textGap ;
        private static Stylizer stylizer ;

        public  ASwingStyleProperties ()
        {
            return;
        }//end

        public int  padding ()
        {
            return this.m_padding;
        }//end

        public void  padding (int param1 )
        {
            this.m_padding = param1;
            this.paddingLeft = this.paddingLeft ? (this.paddingLeft) : (param1);
            this.paddingRight = this.paddingRight ? (this.paddingRight) : (param1);
            this.paddingBottom = this.paddingBottom ? (this.paddingBottom) : (param1);
            this.paddingTop = this.paddingTop ? (this.paddingTop) : (param1);
            return;
        }//end

    }


