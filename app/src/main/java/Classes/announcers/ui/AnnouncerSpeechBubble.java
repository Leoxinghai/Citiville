package Classes.announcers.ui;

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
//import flash.text.*;
import org.aswing.*;

    public class AnnouncerSpeechBubble extends Sprite
    {
        private Sprite m_holder ;
        private JWindow m_win ;
        private JPanel m_container ;
        private JPanel m_body ;
        private JPanel m_mainTxtContainer ;

        public  AnnouncerSpeechBubble ()
        {
            this.m_holder = new Sprite();
            addChild(this.m_holder);
            this.m_mainTxtContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_body = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_body.appendAll(this.m_mainTxtContainer);
            ASwingHelper.setEasyBorder(this.m_body, 10, 10, 16, 10);
            MarginBackground _loc_1 =new MarginBackground(new EmbeddedArt.npcSpeechBubble ()as DisplayObject ,new Insets(0,0,0,0));
            this.m_container = ASwingHelper.makeSoftBoxJPanel();
            this.m_container.setBackgroundDecorator(_loc_1);
            this.m_container.append(this.m_body);
            this.m_win = new JWindow(this.m_holder);
            this.m_win.setContentPane(this.m_container);
            this.m_win.show();
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        public void  setMessage (String param1 )
        {
            this.m_mainTxtContainer.removeAll();
            _loc_2 = ASwingHelper.makeMultilineText(param1 ,150,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,12,3355443);
            this.m_mainTxtContainer.append(_loc_2);
            ASwingHelper.prepare(this.m_win);
            this.m_holder.x = -this.m_holder.width;
            this.m_holder.y = -this.m_holder.height;
            return;
        }//end

    }



