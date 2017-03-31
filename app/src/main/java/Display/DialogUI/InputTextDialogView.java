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
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class InputTextDialogView extends GenericDialogView
    {
        protected String m_inputLabel ;
        protected String m_inputField ;
        protected JTextField m_textField ;

        public  InputTextDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,Function param10 =null )
        {
            this.m_inputLabel = param4;
            this.m_inputField = param5;
            super(param1, param2, param3, param6, param7, param8, param9, "", param10);
            return;
        }//end

        public TextField  textField ()
        {
            return this.m_textField ? (this.m_textField.getTextField()) : (null);
        }//end

        public String  text ()
        {
            return this.m_textField ? (this.m_textField.getText()) : (null);
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.CENTER ));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = this.createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = createButtonPanel();
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = createCloseButtonPanel();
            _loc_3 = this.createTitlePanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            ASwingHelper.setEasyBorder(_loc_1, 55, 0, 20);
            return _loc_1;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 10, 0));
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredWidth(m_bgAsset.width);
                this.setMinimumWidth(m_bgAsset.width);
                this.setMaximumWidth(m_bgAsset.width);
            }
            return;
        }//end

         protected JPanel  createTextAreaInnerPane (Component param1 )
        {
            _loc_2 = super.createTextAreaInnerPane(param1);
            _loc_3 = this.createTextField();
            _loc_4 = this.createTextFieldLabel();
            _loc_5 = this.createTextFieldContainer(_loc_3);
            _loc_6 = this.createTextFieldContainer(_loc_4);
            _loc_3.addEventListener(TextEvent.TEXT_INPUT, this.textInput);
            this.m_textField = _loc_3;
            _loc_2.append(ASwingHelper.verticalStrut(15));
            _loc_2.append(_loc_6);
            _loc_2.append(_loc_5);
            _loc_2.append(ASwingHelper.verticalStrut(9));
            return _loc_2;
        }//end

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            return 350;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            _loc_2 = ASwingHelper.makeMultilineText(m_message,param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,16,10582366);
            _loc_2.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            return _loc_2;
        }//end

         protected JPanel  createTitlePanel ()
        {
            m_titleFontSize = 28;
            return super.createTitlePanel();
        }//end

        protected JTextField  createTextField ()
        {
            _loc_1 = ASwingHelper.makeTextField(this.m_inputField,EmbeddedArt.defaultSerifFont,16,6710886);
            _loc_1.setEditable(true);
            _loc_1.setFont(new ASFont("_sans", 16, false, false, false));
            _loc_1.getTextField().selectable = true;
            _loc_1.setBackground(ASwingHelper.getWhite());
            _loc_1.setBorder(new LineBorder(null, new ASColor(15128239), 1));
            _loc_1.setPreferredWidth(250);
            _loc_1.getTextField().addEventListener(MouseEvent.MOUSE_UP, this.highlightCurrentText, false, 0, true);
            return _loc_1;
        }//end

        protected Component  createTextFieldContainer (Component param1 )
        {
            JPanel _loc_2 =new JPanel(new FlowLayout(FlowLayout.CENTER ,0,0));
            _loc_2.append(param1);
            return _loc_2;
        }//end

        protected Component  createTextFieldLabel ()
        {
            _loc_1 = ASwingHelper.makeLabel(this.m_inputLabel ,EmbeddedArt.defaultFontNameBold ,16,10582366);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, -2, 0, 0)));
            return _loc_1;
        }//end

        private void  highlightCurrentText (MouseEvent event )
        {
            _loc_2 = (TextField)event.target
            if (_loc_2)
            {
                _loc_2.setSelection(0, _loc_2.text.length());
            }
            return;
        }//end

        protected void  textInput (TextEvent event )
        {
            _loc_2 = Global.gameSettings().getString("inavlidNameCharacters");
            _loc_3 = event.text;
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                if (_loc_3 == _loc_2.substr(_loc_4, 1))
                {
                    event.preventDefault();
                }
                _loc_4++;
            }
            return;
        }//end

    }




