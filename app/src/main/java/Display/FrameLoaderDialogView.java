package Display;

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
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class FrameLoaderDialogView extends GenericDialogView
    {
        protected boolean m_showBeak ;

        public  FrameLoaderDialogView (Dictionary param1 ,String param2 ="",boolean param3 =false )
        {
            this.m_showBeak = param3;
            super(param1, param2);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset =(DisplayObject) new m_assetDict.get("frameLoader_bg");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 17, 0));
                this.setBackgroundDecorator(_loc_1);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.appendAll(ASwingHelper.verticalStrut(10), this.makeTextPanel(), this.makeWaitingPanel(), ASwingHelper.verticalStrut(9), this.makeBeakPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeTextPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeLabel(m_message,EmbeddedArt.titleFont,16,EmbeddedArt.orangeTextColor,JLabel.CENTER);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(10), _loc_2, ASwingHelper.horizontalStrut(10));
            return _loc_1;
        }//end

        protected JPanel  makeWaitingPanel ()
        {
            Bitmap _loc_3 =null ;
            AutoAnimatedBitmap _loc_4 =null ;
            AssetPane _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "frameLoader_waiting");
            if (_loc_2 && _loc_2 instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2;
                _loc_4 = new AutoAnimatedBitmap(_loc_3.bitmapData, 12, 20, 20, 15);
                _loc_5 = new AssetPane(_loc_4);
                _loc_1.append(_loc_5);
            }
            return _loc_1;
        }//end

        protected JPanel  makeBeakPanel ()
        {
            DisplayObject _loc_2 =null ;
            AssetPane _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (this.m_showBeak)
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("frameLoader_beak");
                _loc_3 = new AssetPane(_loc_2);
                _loc_1.append(_loc_3);
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(20));
            }
            return _loc_1;
        }//end

    }




