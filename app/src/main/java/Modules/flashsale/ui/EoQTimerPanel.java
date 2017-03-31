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

import Classes.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class EoQTimerPanel extends JPanel
    {
        private static int FONT_SIZE =18;

        public  EoQTimerPanel (Date param1 ,DisplayObject param2 )
        {
            super(new BorderLayout());
            this.makeBackground(param2);
            this.createTimerCells(param1);
            return;
        }//end

        private void  makeBackground (DisplayObject param1 )
        {
            MarginBackground _loc_2 =new MarginBackground(param1 ,new Insets(0,0,0,0));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(param1.width, param1.height));
            this.setMinimumSize(new IntDimension(param1.width, param1.height));
            this.setMaximumSize(new IntDimension(param1.width, param1.height));
            return;
        }//end

        private void  createTimerCells (Date param1 )
        {
            EoQTimerCellPanel _loc_2 =new EoQTimerCellPanel(param1 );
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 10);
            this.append(_loc_2, BorderLayout.EAST);
            return;
        }//end

        private void  createLabel ()
        {
            _loc_1 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","FreeGiftSaleTimerMsg"),EmbeddedArt.defaultFontNameBold ,FONT_SIZE ,EmbeddedArt.whiteTextColor ,JLabel.LEFT );
            ASwingHelper.setEasyBorder(_loc_1, 0, 40);
            this.append(_loc_1, BorderLayout.WEST);
            return;
        }//end

    }



