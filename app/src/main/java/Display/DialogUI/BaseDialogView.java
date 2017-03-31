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
import Display.*;
import Display.aswingui.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class BaseDialogView extends GenericDialogView
    {
        protected Object m_data ;
        protected JPanel m_contentPanel ;
        protected Dictionary m_buttons ;
        public static  String BUTTON_RED ="RedButtonUI";
        public static  String BUTTON_GREEN ="GreenButtonUI";
        public static  String BUTTON_GREY ="GrayButtonUI";
        public static  Array LEGAL_BUTTON_STYLES =.get(BUTTON_RED ,BUTTON_GREEN ,BUTTON_GREY) ;

        public  BaseDialogView (Dictionary param1 ,Object param2 )
        {
            this.m_data = param2;
            this.m_buttons = new Dictionary();
            super(param1);
            return;
        }//end

        protected JPanel  createContentPanel ()
        {
            String _loc_1 =null ;
            AssetPane _loc_2 =null ;
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (this.m_data.hasOwnProperty("message"))
            {
                _loc_1 = this.m_data.get("message");
                _loc_2 = ASwingHelper.makeMultilineText(_loc_1, 350, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 18, EmbeddedArt.brownTextColor);
                ASwingHelper.setEasyBorder(this.m_contentPanel, 20, 0, 20);
                this.m_contentPanel.append(_loc_2);
            }
            return this.m_contentPanel;
        }//end

         protected void  makeBackground ()
        {
            Object _loc_1 =null ;
            super.makeBackground();
            if (this.m_data.hasOwnProperty("background"))
            {
                _loc_1 = this.m_data.get("background");
                if (_loc_1.get("fixedWidth"))
                {
                    setPreferredWidth(m_bgAsset.width);
                }
                if (_loc_1.get("fixedHeight"))
                {
                    setPreferredHeight(m_bgAsset.height);
                }
            }
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            TextFormat _loc_2 =null ;
            m_titleString = this.m_data.hasOwnProperty("title") ? (this.m_data.get("title")) : ("");
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(m_titleSmallCapsFontSize, m_titleFontSize, .get({locale:"ja", size:m_titleSmallCapsFontSize}));
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_titleString != "")
            {
                _loc_2 = new TextFormat();
                _loc_2.size = m_titleSmallCapsFontSize;
                m_titleFontSize = ASwingHelper.shrinkFontSizeToFit(MAX_TITLE_TEXT_WIDTH, m_titleString, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.newtitleFilters, null, _loc_2);
                title = ASwingHelper.makeTextField(m_titleString, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
                title.filters = EmbeddedArt.newtitleFilters;
                TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_2);
                _loc_1.append(title);
                title.getTextField().height = m_titleFontSize * 1.5;
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            Object _loc_6 =null ;
            _loc_2 = createHeaderPanel();
            _loc_3 = this.createContentPanel();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_4.appendAll(_loc_2, _loc_3);
            _loc_5 = this.createButtonPanel();
            _loc_1 = new JPanel(new BorderLayout());
            _loc_1.append(_loc_4, BorderLayout.NORTH);
            _loc_1.append(_loc_5, BorderLayout.SOUTH);
            if (this.m_data.hasOwnProperty("background"))
            {
                _loc_6 = this.m_data.get("background");
                if (_loc_6.get("fixedWidth"))
                {
                    _loc_1.setPreferredWidth(m_bgAsset.width);
                }
                if (_loc_6.get("fixedHeight"))
                {
                    _loc_1.setPreferredHeight(m_bgAsset.height);
                }
            }
            return _loc_1;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JButton _loc_2 =null ;
            Object _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical();
            if (this.m_data.hasOwnProperty("hasCloseButton") && this.m_data.get("hasCloseButton") == false)
            {
                _loc_1.append(ASwingHelper.verticalStrut(20));
            }
            else
            {
                _loc_2 = ASwingHelper.makeMarketCloseButton();
                _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
                _loc_1.append(_loc_2);
                _loc_3 = getCloseButtonBorder();
                ASwingHelper.setEasyBorder(_loc_2, _loc_3.get("top"), _loc_3.get("left"), _loc_3.get("bottom"), _loc_3.get("right"));
            }
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            Array _loc_2 =null ;
            Object _loc_3 =null ;
            boolean _loc_4 =false ;
            String _loc_5 =null ;
            CustomButton _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            if (this.m_data.hasOwnProperty("buttons") && this.m_data.get("buttons") instanceof Array)
            {
                _loc_2 =(Array) this.m_data.get("buttons");
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    _loc_4 = _loc_3.hasOwnProperty("isClose") ? (_loc_3.get("isClose")) : (true);
                    _loc_5 = LEGAL_BUTTON_STYLES.indexOf(_loc_3.get("style")) == -1 ? (BUTTON_GREEN) : (_loc_3.get("style"));
                    _loc_6 = new CustomButton(_loc_3.get("label"), null, _loc_5);
                    this.m_buttons.get(_loc_6) = new DialogButtonData(_loc_6, _loc_4, _loc_3.get("callback"));
                    _loc_6.addActionListener(this.onButtonClick, 0, true);
                    _loc_1.append(_loc_6);
                }
            }
            return _loc_1;
        }//end

        protected void  onButtonClick (AWEvent event )
        {
            _loc_2 = this.m_buttons.get(event.currentTarget);
            if (_loc_2)
            {
                if (_loc_2.callback != null)
                {
                    _loc_2.callback();
                }
                if (_loc_2.isClose)
                {
                    close();
                }
            }
            return;
        }//end

    }

import Display.aswingui.*;

class DialogButtonData
    private CustomButton m_button ;
    private Function m_callback ;
    private boolean m_isClose ;

     DialogButtonData (CustomButton param1 ,boolean param2 =true ,Function param3 =null )
    {
        this.m_button = param1;
        this.m_callback = param3;
        this.m_isClose = param2;
        return;
    }//end

    public CustomButton  button ()
    {
        return this.m_button;
    }//end

    public Function  callback ()
    {
        return this.m_callback;
    }//end

    public boolean  isClose ()
    {
        return this.m_isClose;
    }//end






