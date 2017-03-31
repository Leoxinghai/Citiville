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

//import flash.text.*;
import org.aswing.*;

    public class JText extends JLabel implements ITextFieldLabel
    {
        protected TextField textField ;

        public  JText (String param1 ="",Icon param2 ,int param3 =0)
        {
            super(param1, param2, param3);
            return;
        }//end

        public TextField  getTextField ()
        {
            return this.textField;
        }//end

        public void  setTextField (TextField param1 )
        {
            this.textField = param1;
            return;
        }//end

    }


