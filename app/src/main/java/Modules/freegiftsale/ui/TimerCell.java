package Modules.freegiftsale.ui;

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
import Display.aswingui.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class TimerCell extends JPanel
    {
        private JTextField textField ;
        public static int FONT_SIZE =22;

        public  TimerCell ()
        {
            super(new FlowLayout(FlowLayout.CENTER, 0, 0));
            this.makeBackground();
            this.makeTextField();
            return;
        }//end  

        public void  digit (int param1 )
        {
            this.textField.setText(new String(param1));
            this.repaint();
            return;
        }//end  

        private void  makeBackground ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.timer_slot ()as DisplayObject ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,0,0));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
            this.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
            this.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            return;
        }//end  

        private void  makeTextField ()
        {
            TextFormat _loc_1 =new TextFormat ();
            _loc_1.align = TextFormatAlign.CENTER;
            this.textField = ASwingHelper.makeTextField("0", EmbeddedArt.TITLE_FONT, TimerCell.FONT_SIZE, EmbeddedArt.lightOrangeTextColor, 3, _loc_1);
            ASwingHelper.setEasyBorder(this.textField, 0, -2, 0);
            this.append(this.textField);
            return;
        }//end  

    }



