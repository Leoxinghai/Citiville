package Display.Toaster;

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
import Display.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class TickerToaster extends Toaster
    {
        private String m_message ;
        private JTextField m_messageText ;
        private JButton m_closeButton ;
        private JWindow m_window ;

        public  TickerToaster (String param1 )
        {
            m_duration = 5000;
            this.m_message = param1;
            this.draw();
            return;
        }//end

         public double  displayHeight ()
        {
            return 30;
        }//end

        protected void  draw ()
        {
            this.m_window = new JWindow(this);
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.RIGHT ));
            DisplayObject _loc_2 =new EmbeddedArt.hud_nghbr_notification_ticker ()as DisplayObject ;
            ASwingHelper.setSizedBackground(_loc_1, _loc_2);
            _loc_3 = ASwingHelper.makeFlowJPanel ();
            _loc_4 = TextFieldUtil.getLocaleFontSize(14,11,.get({localesize"ja",12)});
            this.m_closeButton = ASwingHelper.makeLittleCloseButton();
            ASwingHelper.prepare(this.m_closeButton);
            this.m_messageText = ASwingHelper.makeTextField("", EmbeddedArt.defaultFontNameBold, _loc_4, EmbeddedArt.brownTextColor, 0);
            this.m_messageText.setPreferredWidth(_loc_2.width - this.m_closeButton.width - 10);
            this.m_messageText.setText(this.m_message);
            this.m_closeButton.addEventListener(MouseEvent.MOUSE_UP, this.closeButtonHandler, false, 0, true);
            _loc_3.appendAll(this.m_messageText, this.m_closeButton);
            ASwingHelper.prepare(_loc_3);
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            this.m_window.setContentPane(_loc_1);
            this.m_window.show();
            ASwingHelper.prepare(this.m_window);
            return;
        }//end

        private void  closeButtonHandler (MouseEvent event )
        {
            close();
            return;
        }//end

    }



