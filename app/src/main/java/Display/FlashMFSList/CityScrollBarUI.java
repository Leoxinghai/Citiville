package Display.FlashMFSList;

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
//import flash.display.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.plaf.basic.*;

    public class CityScrollBarUI extends BasicScrollBarUI
    {

        public  CityScrollBarUI ()
        {
            return;
        }//end  

         protected Icon  createArrowIcon (double param1 )
        {
            DisplayObject _loc_2 =new EmbeddedArt.scrollbarArrowUp ()as DisplayObject ;
            Matrix _loc_3 =new Matrix ();
            _loc_3.translate((-_loc_2.width) * 0.5, (-_loc_2.height) * 0.5);
            _loc_3.rotate(param1 + Math.PI * 0.5);
            _loc_3.translate(_loc_2.width * 0.5, _loc_2.height * 0.5);
            //BitmapData _loc_4 =new BitmapData(_loc_2.width ,_loc_2.height ,true ,0);
            BitmapData _loc_4 =new BitmapData(20,20,true ,0);
            //_loc_4.draw(_loc_2, _loc_3);
            AssetIcon _loc_5 =new AssetIcon(new Bitmap(_loc_4 ));
            return _loc_5;
        }//end  

         protected JButton  createArrowButton ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.graphics.beginFill(16777215, 0);
            _loc_1.graphics.drawRect(0, 0, scrollBarWidth, scrollBarWidth);
            SimpleButton _loc_2 =new SimpleButton(_loc_1 ,_loc_1 ,_loc_1 ,_loc_1 );
            JButton _loc_3 =new JButton ();
            _loc_3.wrapSimpleButton(_loc_2);
            _loc_3.setFocusable(false);
            _loc_3.setBackground(null);
            _loc_3.setForeground(null);



            _loc_3.setPreferredSize(new IntDimension(scrollBarWidth, scrollBarWidth));
            return _loc_3;
        }//end  

    }


