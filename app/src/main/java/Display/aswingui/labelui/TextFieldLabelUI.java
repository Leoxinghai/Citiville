package Display.aswingui.labelui;

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
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.basic.*;

    public class TextFieldLabelUI extends BasicLabelUI
    {

        public  TextFieldLabelUI ()
        {
            return;
        }//end

         protected void  installComponents (JLabel param1 )
        {
            ITextFieldLabel _loc_2 =null ;
            super.installComponents(param1);
            if (param1 instanceof ITextFieldLabel)
            {
                _loc_2 =(ITextFieldLabel) param1;
                _loc_2.setTextField(textField);
            }
            return;
        }//end

         protected void  uninstallComponents (JLabel param1 )
        {
            ITextFieldLabel _loc_2 =null ;
            super.uninstallComponents(param1);
            if (param1 instanceof ITextFieldLabel)
            {
                _loc_2 =(ITextFieldLabel) param1;
                _loc_2.setTextField(null);
            }
            return;
        }//end

         public void  paint (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            if (param1 instanceof ITextFieldLabel)
            {
                super.paint(param1, param2, param3);
            }
            else
            {
                super.paint(param1, param2, param3);
            }
            return;
        }//end

    }


