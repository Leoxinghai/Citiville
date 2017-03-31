package Modules.guide.ui;

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
import Modules.guide.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class WelcomeDialog extends GenericDialog
    {
        protected  int AVATAR_DIALOG_OFFSET_X =20;
        protected  int AVATAR_DIALOG_OFFSET_Y =10;
        protected  int DIALOG_OFFSET_Y =50;
        protected DisplayObject m_welcomeBG ;
        protected DisplayObject m_welcomeInnerBG ;
        protected DisplayObject m_hintInnerBG ;
        protected DisplayObject m_cityHeader ;
        protected DisplayObject m_advisor ;
        protected JWindow m_avatarWindow ;
        protected int m_assetsLoaded =0;
        protected String m_avatarURL ;
        protected String m_hintMessage ;
        protected DisplayObject m_avatarContent ;
        protected Loader m_bgContentLoader ;
        public DisplayObject m_loaderObj ;
        protected DisplayObject m_speech ;
        protected double m_position ;
        protected CustomButton m_button ;
        public static  int POS_CENTER =0;
        public static  int POS_NW =1;
        public static  int POS_NE =2;
        public static  int POS_SW =3;
        public static  int POS_SE =4;

        public  WelcomeDialog (String param1 ,String param2 ="",int param3 =0,int param4 =0,Function param5 =null ,boolean param6 =true ,String param7 ="",String param8 ="",Function param9 =null )
        {
            this.m_avatarURL = param7;
            this.m_hintMessage = param8;
            this.m_position = param4;
            super(param1, param2, param3, param5, param2, "", param6, 0, "", param9);
            return;
        }//end

         protected void  loadAssets ()
        {
            _loc_1 =Global.getAssetURL(this.m_avatarURL );
            Global.delayedAssets.get(this.m_avatarURL, this.makeAssets);
            Global.delayedAssets.get(GuideUtils.TUTORIAL_ASSETS, this.makeAssets);
            return;
        }//end

         public boolean  isLockable ()
        {
            return false;
        }//end

         protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            switch(param2)
            {
                case GuideUtils.TUTORIAL_ASSETS:
                {
                    this.m_loaderObj = param1;
                    break;
                }
                case this.m_avatarURL:
                {
                    this.m_avatarContent = param1;
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.m_assetsLoaded++;
            if (this.m_assetsLoaded < 2)
            {
                return;
            }
            m_loaded = true;
            this.onAssetsLoaded();
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            AssetPane _loc_8 =null ;
            _loc_2 = this.m_loaderObj ;
            this.m_advisor = this.m_avatarContent;
            this.m_speech =(DisplayObject) new _loc_2.speechBubbleLeft();
            m_jpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3.setBackgroundDecorator(new MarginBackground(this.m_speech, new Insets(0, 0, 10, 0)));
            _loc_5 = this.getTfWidth(m_message ,m_dialogTitle );
            if (m_dialogTitle)
            {
                _loc_8 = ASwingHelper.makeMultilineCapsText(m_dialogTitle, _loc_5, EmbeddedArt.titleFont, TextFormatAlign.LEFT, 16, EmbeddedArt.darkBlueTextColor, null, true);
                _loc_4.append(_loc_8);
            }
            _loc_6 = ASwingHelper.makeMultilineText(m_message ,_loc_5 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor ,null ,true );
            _loc_4.appendAll(_loc_6, ASwingHelper.verticalStrut(10));
            _loc_4.setBorder(new EmptyBorder(null, new Insets(10, 50, 0, 25)));
            _loc_7 = this.createButtonPanel ();
            _loc_4.append(_loc_7);
            _loc_3.append(_loc_4);
            ASwingHelper.prepare(_loc_3);
            m_jpanel.append(_loc_3);
            this.finalizeAndShow();
            return;
        }//end

        protected int  getTfWidth (String param1 ,String param2 )
        {
            return Math.max(200, param1.length * 1.5, param2.length * 1.5);
        }//end

        protected JPanel  createButtonPanel ()
        {
            this.m_button = new CustomButton(ZLoc.t("Dialogs", "OkButton"), null, "GreenButtonUI");
            this.m_button.addActionListener(this.onPanelClick, 0, true);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.append(this.m_button);
            return _loc_1;
        }//end

         protected void  showTween ()
        {
            this.mouseEnabled = false;
            this.mouseChildren = false;
            Point _loc_1 =new Point(m_content.width ,m_content.height );
            Matrix _loc_2 =new Matrix ();
            _loc_2.scale(1, 1);
            m_content.transform.matrix = _loc_2;
            TweenLite.from(this, 0.25, {alpha:0, onComplete:onShowComplete});
            return;
        }//end

         protected void  hideTween (Function param1 )
        {
            this.mouseEnabled = false;
            this.mouseChildren = false;
            if (param1 != null)
            {
                TweenLite.to(this, 0.25, {alpha:0, onComplete:param1});
            }
            else
            {
                TweenLite.to(this, 0.25, {alpha:0});
            }
            return;
        }//end

         protected void  finalizeAndShow ()
        {
            Point _loc_4 =null ;
            ASwingHelper.prepare(m_jpanel);
            m_jwindow.setContentPane(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            m_jwindow.show();
            Sprite _loc_1 =new Sprite ();
            this.addChild(_loc_1);
            this.m_avatarWindow = new JWindow(_loc_1);
            if (this.m_advisor.parent && this.m_advisor.parent instanceof Sprite && this.m_advisor instanceof Bitmap)
            {
                this.m_advisor = new Bitmap(((Bitmap)this.m_advisor).bitmapData);
            }
            AssetPane _loc_2 =new AssetPane(this.m_advisor );
            this.m_avatarWindow.setContentPane(_loc_2);
            ASwingHelper.prepare(this.m_avatarWindow);
            this.m_avatarWindow.show();
            _loc_3 = this.m_avatarWindow.width -this.AVATAR_DIALOG_OFFSET_X +m_jwindow.width ;
            if (this.m_position == POS_NW)
            {
                _loc_4 = new Point(Global.ui.screenWidth * 1 / 3 - _loc_3 / 2, Global.ui.screenHeight / 3 - m_jwindow.height / 2);
            }
            else if (this.m_position == POS_NE)
            {
                _loc_4 = new Point(Global.ui.screenWidth * 0.66 - _loc_3 / 2, Global.ui.screenHeight * 0.4 - m_jwindow.height / 2);
            }
            else if (this.m_position == POS_SW)
            {
                _loc_4 = new Point(Global.ui.screenWidth * 1 / 3 - _loc_3 / 2, 2 * Global.ui.screenHeight / 3 - m_jwindow.height / 2);
            }
            else if (this.m_position == POS_SE)
            {
                _loc_4 = new Point(Global.ui.screenWidth * 2 / 3 - _loc_3 / 2, 2 * Global.ui.screenHeight / 3 - m_jwindow.height / 2);
            }
            else
            {
                _loc_4 = new Point(Global.ui.screenWidth / 2 - _loc_3 / 2, Global.ui.screenHeight / 2 - m_jwindow.height / 2);
            }
            m_jwindow.setX(_loc_4.x + this.AVATAR_DIALOG_OFFSET_X + this.m_avatarWindow.getWidth());
            m_jwindow.setY(_loc_4.y - this.DIALOG_OFFSET_Y);
            this.m_avatarWindow.setX(_loc_4.x + this.AVATAR_DIALOG_OFFSET_X);
            this.m_avatarWindow.setY(_loc_4.y - (this.DIALOG_OFFSET_Y + this.AVATAR_DIALOG_OFFSET_Y));
            setupDialogStatsTracking();
            onDialogInitialized();
            return;
        }//end

         public void  centerPopup ()
        {
            _loc_1 = this.m_avatarWindow.width -this.AVATAR_DIALOG_OFFSET_X +m_jwindow.width ;
            Point _loc_2 =new Point(Global.ui.screenWidth /2-_loc_1 /2,Global.ui.screenHeight /2-m_jwindow.height /2);
            m_jwindow.setX(_loc_2.x + this.AVATAR_DIALOG_OFFSET_X + this.m_avatarWindow.getWidth());
            m_jwindow.setY(_loc_2.y - this.DIALOG_OFFSET_Y);
            this.m_avatarWindow.setX(_loc_2.x + this.AVATAR_DIALOG_OFFSET_X);
            this.m_avatarWindow.setY(_loc_2.y - (this.DIALOG_OFFSET_Y + this.AVATAR_DIALOG_OFFSET_Y));
            return;
        }//end

        protected void  onPanelClick (AWEvent event )
        {
            countDialogAction("OK");
            if (m_callback != null)
            {
                m_callback(event);
            }
            this.close();
            dispatchEvent(new Event(Event.CLOSE, true));
            return;
        }//end

    }



