package Display.MarketUI;

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

import Display.aswingui.gridlistui.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class TabbedMarketScrollingList extends MarketScrollingList
    {
        public Class m_gridCellClass ;

        public  TabbedMarketScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2)
        {
            this.m_gridCellClass = param2;
            super(param1, MarketCellFactory, param3, param4, param5);
            return;
        }//end  

         protected GridListCellFactory  createCellFactory ()
        {
            return new GenericGridCellFactory(this.m_gridCellClass);
        }//end  

         protected Insets  getLeftInsets ()
        {
            return new Insets(0, 0, 5, 3);
        }//end  

         protected Insets  getRightInsets ()
        {
            return new Insets(0, 3, 5, 0);
        }//end  

    }



