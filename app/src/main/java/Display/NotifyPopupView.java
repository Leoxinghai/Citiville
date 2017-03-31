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
//import flash.utils.*;
import org.aswing.*;

    public class NotifyPopupView extends GenericDialogView
    {
        protected int m_state ;
        protected AssetPane m_tip ;
        public static  int STATE_LONG =0;
        public static  int STATE_SHORT =1;

        public  NotifyPopupView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            this.m_state = STATE_LONG;
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("bgAsset");
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
                _loc_1 = new MarginBackground(m_bgAsset);
                this.setBackgroundDecorator(_loc_1);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setBackgroundDecorator(new AssetBackground(m_bgAsset));
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.append(ASwingHelper.verticalStrut(5));
            switch(this.m_state)
            {
                case STATE_LONG:
                {
                    _loc_1.append(this.makeLongPanel());
                    break;
                }
                case STATE_SHORT:
                {
                    _loc_1.append(this.makeShortPanel());
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_1.append(ASwingHelper.verticalStrut(10));
            ASwingHelper.prepare(_loc_1);
            this.appendAll(ASwingHelper.horizontalStrut(5), _loc_1, ASwingHelper.horizontalStrut(5));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeLongPanel ()
        {
            return this.makeShortPanel();
        }//end

        protected JPanel  makeShortPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-2);
            this.m_tip = ASwingHelper.makeMultilineText(m_message, m_bgAsset.width, EmbeddedArt.defaultFontNameBold, "left", 10, 7715292);
            _loc_1.appendAll(this.m_tip, ASwingHelper.verticalStrut(22));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        public void  rebuild (int param1 )
        {
            this.m_state = param1;
            this.removeAll();
            this.makeCenterPanel();
            ASwingHelper.prepare(this.parent);
            return;
        }//end

    }



