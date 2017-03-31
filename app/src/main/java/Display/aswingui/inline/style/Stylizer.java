package Display.aswingui.inline.style;

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

import Display.*;
import Display.aswingui.*;
import Display.aswingui.inline.util.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.text.*;
//import flash.text.TextField;
//import flash.text.TextFormat;
//import flash.text.TextFormatAlign
import org.aswing.Border;
import org.aswing.Component;
import org.aswing.JLabel;
import org.aswing.JButton;
import org.aswing.border.*;
import org.aswing.Insets;
import org.aswing.GroundDecorator;
import org.aswing.AssetBackground;
import org.aswing.JTextField;
import org.aswing.ASFont;


    public class Stylizer
    {
        private static  int DEFAULT_FONT_SIZE =12;
        private static  Array NO_FILTERS =new Array();

        public  Stylizer ()
        {
            return;
        }//end

        public void  apply (ASwingStyleProperties param1 ,Component param2 )
        {
            if (param2 instanceof JLabel)
            {
                this.styleText(param1, (JLabel)param2);
            }
            else if (param2 instanceof JButton)
            {
                this.styleButton(param1, (JButton)param2);
            }
            this.applyBorder(param1, param2);
            this.applyBackground(param1, param2);
            this.applyFont(param1, param2);
            return;
        }//end

        public void  applyFont (ASwingStyleProperties param1 ,Component param2 )
        {
            TextFormat _loc_8 =null ;
            Localizer _loc_9 =null ;
            String _loc_10 =null ;
            ASFont _loc_11 =null ;
            JLabel _loc_12 =null ;
            String _loc_13 =null ;
            _loc_3 =             !isNaN(param1.fontSize) || param1.fontFamily;
            _loc_4 = param2.getFont ();
            _loc_5 = DEFAULT_FONT_SIZE;
            if (!isNaN(param1.fontSize))
            {
                _loc_5 = param1.fontSize;
            }
            else
            {
                _loc_5 = _loc_4.getSize();
            }
            _loc_6 = param1.fontFamily ;
            if (!param1.fontFamily)
            {
                _loc_6 = _loc_4.getName();
            }
            if (param1.fontSizeVariants)
            {
                _loc_9 = ZLoc.instance;
                _loc_10 = _loc_9.langCode;
                if (_loc_10 != "en")
                {
                    _loc_5 = param1.fontSizeVariants.hasOwnProperty(_loc_10) ? (param1.fontSizeVariants.get(_loc_10) as int) : (param1.fontSizeVariants.get("other") as int);
                }
            }
            if (_loc_3)
            {
                _loc_11 = ASwingFont.create(_loc_6, _loc_5, param1.fontWeight, param1.fontStyle, param1.textDecoration);
                param2.setFont(_loc_11);
            }
            if (!isNaN(param1.color))
            {
                param2.setForeground(ASwingColor.create(param1.color));
            }
            _loc_7 = this.getTextField(param2 );
            if (this.getTextField(param2))
            {
                _loc_8 = _loc_7.getTextFormat();
                _loc_7.embedFonts = ASwingFont.isEmbed(param1.fontFamily);
            }
            if (_loc_8)
            {
                _loc_8.letterSpacing = param1.letterSpacing;
                _loc_8.align = param1.textAlign == FontStyle.ALIGN_CENTER ? (TextFormatAlign.CENTER) : (param1.textAlign == FontStyle.ALIGN_RIGHT ? (TextFormatAlign.RIGHT) : (TextFormatAlign.LEFT));
                _loc_7.setTextFormat(_loc_8);
            }
            else if (param2 instanceof JLabel)
            {
                _loc_12 =(JLabel) param2;
                if (!isNaN(param1.textAlign))
                {
                    _loc_12.setHorizontalAlignment(param1.textAlign);
                }
            }
            if (param1.textTransform)
            {
                _loc_13 = this.getText(param2);
                if (_loc_13)
                {
                    switch(param1.textTransform)
                    {
                        case FontStyle.SMALLCAPS:
                        {
                            if (_loc_7)
                            {
                                TextFieldUtil.formatSmallCaps(_loc_7, new TextFormat(_loc_6, _loc_5 + 4, null, null, null, null, null, null, _loc_8.align));
                            }
                            else
                            {
                                _loc_13 = TextFieldUtil.formatSmallCapsString(_loc_13);
                            }
                            break;
                        }
                        case FontStyle.UPPERCASE:
                        {
                            _loc_13 = _loc_13.toUpperCase();
                            break;
                        }
                        case FontStyle.LOWERCASE:
                        {
                            _loc_13 = _loc_13.toLowerCase();
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    this.setText(param2, _loc_13);
                }
            }
            if (param1.filters && param1.filters.length())
            {
                this.setFilters(param2, param1.filters);
            }
            return;
        }//end

        public void  applyBorder (ASwingStyleProperties param1 ,Component param2 )
        {
            Border _loc_4 =null ;
            Insets _loc_5 =null ;
            _loc_3 = param1.borderStyle ;
            if (_loc_3)
            {
                switch(_loc_3)
                {
                    case BorderStyle.LINE:
                    {
                        _loc_4 = new LineBorder(null, ASwingColor.get(param1.borderColor), Math.max(1, param1.borderThickness), param1.cornerRadius);
                        break;
                    }
                    default:
                    {
                        _loc_5 = ASwingUnits.inset(param1.paddingTop, param1.paddingLeft, param1.paddingBottom, param1.paddingRight);
                        _loc_4 = new EmptyBorder(null, _loc_5);
                        break;
                        break;
                    }
                }
                if (_loc_4)
                {
                    param2.setBorder(_loc_4);
                }
            }
            return;
        }//end

        public void  applyBackground (ASwingStyleProperties param1 ,Component param2 )
        {
            GroundDecorator _loc_3 =null ;
            DisplayObject _loc_6 =null ;
            Insets _loc_7 =null ;
            int _loc_8 =0;
            int _loc_9 =0;
            Shape _loc_10 =null ;
            double _loc_11 =0;
            _loc_4 = param1.backgroundColor ;
            _loc_5 = param1.backgroundImage ;
            if (param1.backgroundImage)
            {
                if (_loc_5 instanceof DisplayObject)
                {
                    _loc_6 =(DisplayObject) _loc_5;
                }
                else if (_loc_5 instanceof Class)
                {
                    _loc_6 =(DisplayObject) new _loc_5;
                }
                else if (_loc_5 instanceof String)
                {
                }
                if (_loc_6)
                {
                    _loc_7 = ASwingUnits.inset(param1.paddingTop, param1.paddingLeft, param1.paddingBottom, param1.paddingRight);
                    _loc_3 = new MarginBackground(_loc_6, _loc_7.clone());
                }
            }
            else if (!isNaN(_loc_4))
            {
                _loc_8 = param1.backgroundWidth;
                if (!_loc_8)
                {
                    _loc_8 = param2.getPreferredWidth();
                }
                _loc_9 = param1.backgroundHeight;
                if (!_loc_9)
                {
                    _loc_9 = param2.getPreferredHeight();
                }
                _loc_10 = new Shape();
                _loc_11 = !isNaN(param1.cornerRadius) ? (param1.cornerRadius) : (0);
                _loc_10.graphics.clear();
                _loc_10.graphics.beginFill(param1.backgroundColor);
                _loc_10.graphics.drawRoundRect(0, 0, _loc_8 - param1.paddingLeft - param1.paddingRight, _loc_9 - param1.paddingTop - param1.paddingBottom, _loc_11, _loc_11);
                _loc_3 = new AssetBackground(_loc_10);
            }
            if (_loc_3)
            {
                param2.setBackgroundDecorator(_loc_3);
            }
            else if (param2 instanceof JLabel || param2 instanceof JTextField)
            {
                param2.setBackground(null);
                param2.setBackgroundDecorator(null);
            }
            return;
        }//end

        private void  styleText (ASwingStyleProperties param1 ,JLabel param2 )
        {
            if (param1.icon || param1.textGap)
            {
                param2.setIcon(param1.icon);
                param2.setIconTextGap(param1.textGap);
            }
            return;
        }//end

        private void  styleButton (ASwingStyleProperties param1 ,JButton param2 )
        {
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            SimpleButton _loc_6 =null ;
            if (!param1.skinClass)
            {
                if (param1.upSkin)
                {
                    DisplayObject _loc_7 =new param1.upSkin ()as DisplayObject ;
                    _loc_3 =(DisplayObject) new param1.upSkin();

                    _loc_4 = _loc_7;
                    _loc_5 = _loc_7;
                }
                if (param1.overSkin)
                {
                    _loc_4 =(DisplayObject) new param1.overSkin();
                }
                if (param1.downSkin)
                {
                    _loc_5 =(DisplayObject) new param1.downSkin();
                }
                if (_loc_3 && _loc_4 && _loc_5)
                {
                    _loc_6 = new SimpleButton(_loc_3, _loc_4, _loc_5, _loc_3);
                    param2.wrapSimpleButton(_loc_6);
                }
            }
            return;
        }//end

        private String  getText (Component param1 )
        {
            String _loc_2 =null ;
            if (param1 instanceof JLabel)
            {
                _loc_2 = ((JLabel)param1).getText();
            }
            else if (param1 instanceof JTextField)
            {
                _loc_2 = ((JTextField)param1).getText();
            }
            else if (param1 instanceof JButton)
            {
                _loc_2 = ((JButton)param1).getText();
            }
            return _loc_2;
        }//end

        private TextField  getTextField (Component param1 )
        {
            TextField _loc_2 =null ;
            if (param1 instanceof JTextField)
            {
                _loc_2 = ((JTextField)param1).getTextField();
            }
            return _loc_2;
        }//end

        private void  setText (Component param1 ,String param2 )
        {
            if (param1 instanceof JLabel)
            {
                ((JLabel)param1).setText(param2);
            }
            else if (param1 instanceof JTextField)
            {
                ((JTextField)param1).setText(param2);
            }
            else if (param1 instanceof JButton)
            {
                ((JButton)param1).setText(param2);
            }
            return;
        }//end

        private void  setFilters (Component param1 ,Array param2 )
        {
            if (param1 instanceof JLabel)
            {
                ((JLabel)param1).setTextFilters(param2);
            }
            else if (param1 instanceof JTextField)
            {
                ((JTextField)param1).getTextField().filters = param2;
            }
            else if (param1 instanceof JButton)
            {
                ((JButton)param1).setTextFilters(param2);
            }
            return;
        }//end

    }


