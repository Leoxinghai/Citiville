package Modules.flashsale.ui;

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

    public class MiniEoQTimerPanel extends JPanel
    {
        private static int FONT_SIZE =12;

        public  MiniEoQTimerPanel (Date param1 )
        {
            super(new FlowLayout());
            this.createTimerCells(param1);
            return;
        }//end  

        private void  makeBackground ()
        {
            return;
        }//end  

        private void  createTimerCells (Date param1 )
        {
            MiniEoQTimerCellPanel _loc_2 =new MiniEoQTimerCellPanel(param1 );
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 0);
            this.append(_loc_2, FlowLayout.LEFT);
            return;
        }//end  

        private void  createLabel ()
        {
            return;
        }//end  

    }



