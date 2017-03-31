package Modules.quest.Display.TaskFooters;

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







//import flash.events.Event;
import org.aswing.JPanel;
import org.aswing.Component;
import org.aswing.FlowLayout;
import org.aswing.SoftBoxLayout;
import org.aswing.Container;
import Modules.quest.Display.QuestPopupView;
import org.aswing.Insets;
import org.aswing.ASFont;


    public class BasicButtonFooter implements ITaskFooter
    {
        protected String m_type =null ;
        protected GenericDialogView m_dialogView =null ;
        private String m_buttonText ;
        private CustomButton m_button =null ;
        private static  int ACTION_EVENT_PRIORITY =1;
        private static  int CLEANUP_EVENT_PRIORITY =0;

        public  BasicButtonFooter (GenericDialogView param1 ,String param2 ,String param3 )
        {
            this.m_dialogView = param1;
            this.m_buttonText = param3;
            this.m_type = param2;
            return;
        }//end

        public Component  getComponent ()
        {
            Container _loc_1 =null ;
            JPanel _loc_2 =new JPanel(new FlowLayout(FlowLayout.LEFT ));
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_button = this.createButton(this.m_buttonText);
            _loc_2.append(this.m_button);
            _loc_1.append(_loc_2);
            this.m_dialogView.addEventListener(Event.CLOSE, this.onClose, false, CLEANUP_EVENT_PRIORITY);
            return _loc_1;
        }//end

        protected CustomButton  createButton (String param1 )
        {
            CustomButton _loc_2 =new CustomButton(param1 ,null ,"GreenButtonUI");
            _loc_2.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_2.setPreferredHeight(30);
            _loc_2.setMargin(new Insets(2, 10, 0, 10));
            _loc_2.addActionListener(this.onButtonClick);
            return _loc_2;
        }//end

        protected void  onButtonClick (Event event )
        {
            throw new Error("onButtonClick not overridden.");
        }//end

        private void  onClose (Event event )
        {
            this.removeListeners();
            return;
        }//end

        private void  removeListeners ()
        {
            if (this.m_button)
            {
                this.m_button.removeActionListener(this.onButtonClick);
            }
            if (this.m_dialogView)
            {
                this.m_dialogView.removeEventListener(Event.CLOSE, this.onClose);
            }
            _loc_1 =(QuestPopupView) this.m_dialogView;
            if (_loc_1 != null)
            {
                _loc_1.okayButton.removeActionListener(this.onButtonClick);
            }
            return;
        }//end

    }



