package Display.DialogUI;

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
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import tool.*;

    public class OffsetDialogView extends GenericDialogView
    {
        private OffsetEditorDialog m_offsetDialog ;
        public JButton capture ;

        public  OffsetDialogView (Dictionary param1 ,OffsetEditorDialog param2 )
        {
            this.capture = new JButton("Capture!");
            super(param1, "click capture and roll over target", "click capture", GenericDialogView.TYPE_CUSTOM_OK, null, "", 0, "", null, "Save to XML");
            this.m_offsetDialog = param2;
            textArea.setLayout(ASwingHelper.softBoxLayoutVertical);
            textArea.setPreferredHeight(-1);
            textArea.setMinimumHeight(20);
            return;
        }//end  

        private void  doCapture (AWEvent event )
        {
            title.setText("Roll over target...");
            textArea.removeAll();
            textArea.append(new JLabel("1st thing you roll onto"));
            textArea.append(new JLabel("with children will capture"));
            textArea.append(ASwingHelper.verticalStrut(30));
            ASwingHelper.prepare(this, this.parent);
            OffsetEditor.captureMode = true;
            return;
        }//end  

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1.append(ASwingHelper.horizontalStrut(30));
            _loc_1.append(this.capture);
            title = ASwingHelper.makeTextField("click capture and roll over target", EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            _loc_1.append(title);
            this.capture.addActionListener(this.doCapture, 0, true);
            return _loc_1;
        }//end  

         protected void  onAccept (AWEvent event )
        {
            this.m_offsetDialog.saveToXML();
            return;
        }//end  

    }


