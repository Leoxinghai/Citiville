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
import org.aswing.*;

    public class TimerPanel extends JPanel
    {
        private static int FONT_SIZE =18;

        public  TimerPanel (Date param1 )
        {
            super(new BorderLayout());
            this.makeBackground();
            this.createLabel();
            this.createTimerCells(param1);
            return;
        }//end

        private void  makeBackground ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.timer_bg ()as DisplayObject ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,0,0));
            this.setBackgroundDecorator(_loc_2);
            ASwingHelper.setEasyBorder(this, 0, 80, 0, 80);
            this.setHeight(_loc_1.height);
            return;
        }//end

        private void  createTimerCells (Date param1 )
        {
            TimerCellPanel _loc_2 =new TimerCellPanel(param1 );
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 10);
            this.append(_loc_2, BorderLayout.EAST);
            return;
        }//end

        private void  createLabel ()
        {
            _loc_1 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","FreeGiftSaleTimerMsg"),EmbeddedArt.defaultFontNameBold ,TimerPanel.FONT_SIZE ,EmbeddedArt.whiteTextColor ,JLabel.LEFT );
            ASwingHelper.setEasyBorder(_loc_1, 0, 40);
            this.append(_loc_1, BorderLayout.WEST);
            return;
        }//end

    }



