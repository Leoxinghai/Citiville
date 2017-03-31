package Display.aswingui;

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
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class ASwingHelper
    {
        private static SoftBoxLayout _sbl ;
        private static SoftBoxLayout _vbl ;
        private static DecorateBorder _dlb ;
        private static Dictionary cache =new Dictionary(true );
        public static boolean _debugOff =true ;

        public  ASwingHelper ()
        {
            return;
        }//end

        public static boolean  debugOff ()
        {
            return false;
        }//end

        public static void  setDebug (Object param1)
        {
            param1.setBorder(new EmptyBorder(ASwingHelper.debugLB));
            return;
        }//end

        public static void  setDebugAll (Object param1)
        {
            int _loc_2 =0;
            setDebug(param1);
            if (param1.hasOwnProperty("getComponentCount"))
            {
                _loc_2 = 0;
                while (_loc_2 < param1.getComponentCount())
                {

                    if (param1.getComponentCount() > 0)
                    {
                        ASwingHelper.setDebugAll(param1.getComponent(_loc_2));
                    }
                    else
                    {
                        setDebug(param1);
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static Array  makeItemData (XML param1 )
        {
            XML _loc_4 =null ;
            Dictionary _loc_5 =null ;
            _loc_6 = undefined;
            XMLList _loc_2 =new XMLList(param1.dummyData );
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                _loc_5 = new Dictionary();
                for(int i0 = 0; i0 < _loc_4.attributes().size(); i0++)
                {
                	_loc_6 = _loc_4.attributes().get(i0);

                    _loc_5.put(_loc_6.name().toString(),  new String(_loc_6));
                }
                _loc_3.push(_loc_5);
            }
            return _loc_3;
        }//end

        public static JLabel  makeLabel (String param1 ,String param2 ,int param3 ,int param4 =16777215,int param5 =0)
        {
            JLabel _loc_6 =new JLabel(param1 ,null ,param5 );
            _loc_6.setFont(new ASFont(param2, param3, false, false, false, new ASFontAdvProperties(EmbeddedArt.isEmbedFont(param2), AntiAliasType.ADVANCED, GridFitType.SUBPIXEL)));
            _loc_6.setForeground(new ASColor(param4));
            ASwingHelper.prepare(_loc_6);
            return _loc_6;
        }//end


        public static JTextField  makeTextField (String param1 ,String param2 ,int param3 ,int param4 =16777215,double param5 =0,TextFormat param6 =null )
        {
            int _loc_8 =0;
            TextFormat _loc_9 =null ;
            JTextField _loc_7 =new JTextField(param1 );
            _loc_7.setEditable(false);
            if (Global.localizer.langCode != "en")
            {
                if (param3 >= 20)
                {
                    _loc_8 = int(param3 * 0.85);
                }
                else
                {
                    _loc_8 = param3;
                }
            }
            else
            {
                _loc_8 = param3;
            }


            _loc_7.setFont(new ASFont(param2, param3, false, false, false, new ASFontAdvProperties(false, AntiAliasType.ADVANCED, GridFitType.SUBPIXEL)));


            Debug.debug7("ASwingHelper.setForeground"+";"+param4);


            _loc_7.setForeground(new ASColor(param4));
            _loc_7.setOpaque(false);
            //_loc_7.setBackgroundDecorator(null);
            //_loc_7.setBackground(null);
            _loc_7.getTextField().selectable = false;
            if (param6 != null)
            {
                _loc_7.setTextFormat(param6);
            }
            else
            {
                _loc_9 = _loc_7.getTextFormat();
                _loc_9.kerning = true;
                _loc_9.letterSpacing = param5;
                _loc_7.setTextFormat(_loc_9);
            }
            ASwingHelper.prepare(_loc_7);
            return _loc_7;
        }//end

        public static ASFont  getStandardFont (int param1 )
        {
            ASFont _loc_2 =new ASFont(EmbeddedArt.defaultFontName ,param1 ,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            return _loc_2;
        }//end

        public static ASFont  getBoldFont (int param1 )
        {
            ASFont _loc_2 =new ASFont(EmbeddedArt.defaultFontNameBold ,param1 ,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            return _loc_2;
        }//end

        public static JPanel  centerElement (Component param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2.append(param1);
            return _loc_2;
        }//end

        public static JPanel  leftAlignElement (Component param1 )
        {
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_2.append(param1);
            return _loc_2;
        }//end

        public static JPanel  rightAlignElement (Component param1 )
        {
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT );
            _loc_2.append(param1);
            return _loc_2;
        }//end

        public static JPanel  bottomAlignElement (Component param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_2.append(param1);
            return _loc_2;
        }//end

        public static JPanel  topAlignElement (Component param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2.append(param1);
            return _loc_2;
        }//end

        public static ASFont  getTitleFont (int param1 )
        {
            ASFont _loc_2 =new ASFont(EmbeddedArt.titleFont ,param1 ,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            return _loc_2;
        }//end

        public static void  setEasyBorder (Component param1 ,double param2 ,double param3 =0,double param4 =0,double param5 =0)
        {
            param1.setBorder(new EmptyBorder(null, new Insets(param2, param3, param4, param5)));
            return;
        }//end

        public static void  setBackground (Component param1 ,DisplayObject param2 ,Insets param3 =null )
        {
            if (param3 == null)
            {
                param1.setBackgroundDecorator(new AssetBackground(param2));
            }
            else
            {
                param1.setBackgroundDecorator(new MarginBackground(param2, param3));
            }
            return;
        }//end

        public static void  setSizedBackground (JPanel param1 ,DisplayObject param2 ,Insets param3 =null )
        {
            double _loc_4 =0;
            double _loc_5 =0;
            if (param3 == null)
            {
                param1.setBackgroundDecorator(new AssetBackground(param2));
                param1.setPreferredSize(new IntDimension(param2.width, param2.height));
                param1.setMaximumSize(new IntDimension(param2.width, param2.height));
                param1.setMinimumSize(new IntDimension(param2.width, param2.height));
            }
            else
            {
                _loc_4 = param3.left + param3.right + param2.width;
                _loc_5 = param3.top + param3.bottom + param2.height;
                param1.setBackgroundDecorator(new MarginBackground(param2, param3));
                param1.setPreferredSize(new IntDimension(_loc_4, _loc_5));
                param1.setMaximumSize(new IntDimension(_loc_4, _loc_5));
                param1.setMinimumSize(new IntDimension(_loc_4, _loc_5));
            }
            return;
        }//end

        public static JButton  makeMarketCloseButton ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.mkt_close_up ()as DisplayObject ;
            DisplayObject _loc_2 =new EmbeddedArt.mkt_close_over ()as DisplayObject ;
            DisplayObject _loc_3 =new EmbeddedArt.mkt_close_down ()as DisplayObject ;
            SimpleButton _loc_4 =new SimpleButton(_loc_1 ,_loc_2 ,_loc_3 ,_loc_1 );
            JButton _loc_5 =new JButton ();
            _loc_5.wrapSimpleButton(_loc_4);
            return _loc_5;
        }//end

        public static JButton  makeLittleCloseButton ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.littleClose_up ()as DisplayObject ;
            DisplayObject _loc_2 =new EmbeddedArt.littleClose_over ()as DisplayObject ;
            DisplayObject _loc_3 =new EmbeddedArt.littleClose_down ()as DisplayObject ;
            SimpleButton _loc_4 =new SimpleButton(_loc_1 ,_loc_2 ,_loc_3 ,_loc_1 );
            JButton _loc_5 =new JButton ();
            _loc_5.wrapSimpleButton(_loc_4);
            return _loc_5;
        }//end

        public static JPanel  makeCloseButtonOffset ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.mkt_close_up ()as DisplayObject ;
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_2.append(ASwingHelper.horizontalStrut(_loc_1.width));
            return _loc_2;
        }//end

        public static JButton  makeCloseButton ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.dia_close_on ()as DisplayObject ;
            DisplayObject _loc_2 =new EmbeddedArt.dia_close_over ()as DisplayObject ;
            SimpleButton _loc_3 =new SimpleButton(_loc_1 ,_loc_2 ,_loc_1 ,_loc_1 );
            JButton _loc_4 =new JButton ();
            _loc_4.wrapSimpleButton(_loc_3);
            return _loc_4;
        }//end

        public static ASColor  getColor (int param1 )
        {
            ASColor _loc_2 =new ASColor(param1 ,1);
            return _loc_2;
        }//end

        public static ASColor  getWhite ()
        {
            ASColor _loc_1 =new ASColor(16777215,1);
            return _loc_1;
        }//end

        public static AssetPane  makeMultilineText (String param1 ,int param2 ,String param3 ,String param4 ,int param5 =12,int param6 =0,Array param7 =null ,boolean param8 =false )
        {
            /*
            TextField _loc_9 =new TextField ();
            if (param2 == -1)
            {
                _loc_9.multiline = false;
                _loc_9.wordWrap = false;
                _loc_9.text = param1;
                _loc_9.width = _loc_9.textWidth;
            }
            else
            {
                _loc_9.width = param2;
                _loc_9.multiline = true;
                _loc_9.wordWrap = true;
                _loc_9.text = param1;
            }
            _loc_9.height = 0;
            _loc_9.selectable = false;
            _loc_9.embedFonts = EmbeddedArt.isEmbedFont(param3);
            _loc_9.antiAliasType = AntiAliasType.ADVANCED;
            _loc_9.gridFitType = GridFitType.SUBPIXEL;
            _loc_9.autoSize = TextFieldAutoSize.CENTER;
            TextFormat _loc_10 =new TextFormat ();
            _loc_10.font = param3;
            _loc_10.color = param6;
            _loc_10.align = param4;
            _loc_10.size = param5;
            if (param8)
            {
                _loc_9.defaultTextFormat = _loc_10;
                _loc_9.htmlText = param1;
            }
            else
            {
                _loc_9.setTextFormat(_loc_10);
            }
            _loc_9.filters = param7;
            AssetPane _loc_11 =new AssetPane(_loc_9 );
            _loc_11.setPreferredWidth(param2 + 10);
            ASwingHelper.prepare(_loc_11);
            return _loc_11;
            */


	      TextField test =new TextField ();
	      TextFormat myFormat =new TextFormat ();
	      myFormat.font = "SimplifiedChineseRegular";
	      test.defaultTextFormat = myFormat;
	      test.text= "??";
              AssetPane _loc_11 =new AssetPane(test );
              _loc_11.setPreferredWidth(param2 + 10);
              ASwingHelper.prepare(_loc_11);
             return _loc_11;


        }//end

        public static AssetPane  makeMultilineCapsText (String param1 ,int param2 ,String param3 ,String param4 ,int param5 =12,int param6 =0,Array param7 =null ,boolean param8 =false ,int param9 =0)
        {
            TextField _loc_10 =new TextField ();
            if (param2 == -1)
            {
                _loc_10.multiline = false;
                _loc_10.wordWrap = false;
                _loc_10.text = param1;
                _loc_10.width = _loc_10.textWidth;
            }
            else
            {
                _loc_10.width = param2;
                _loc_10.multiline = true;
                _loc_10.wordWrap = true;
                _loc_10.text = param1;
            }
            if (param8 == true)
            {
                _loc_10.htmlText = param1;
            }
            _loc_10.height = param9;
            if (param9 == 0)
            {
                _loc_10.autoSize = TextFieldAutoSize.CENTER;
            }
            _loc_10.selectable = false;
            _loc_10.embedFonts = EmbeddedArt.isEmbedFont(param3);
            _loc_10.antiAliasType = AntiAliasType.ADVANCED;
            _loc_10.gridFitType = GridFitType.SUBPIXEL;
            TextFormat _loc_11 =new TextFormat ();
            _loc_11.font = param3;
            _loc_11.color = param6;
            _loc_11.align = param4;
            _loc_11.size = param5;
            _loc_11.leading = (-param5) / 4;
            _loc_10.filters = param7;
            TextFieldUtil.formatSmallCaps(_loc_10, new TextFormat(param3, param5 + 4));
            _loc_10.setTextFormat(_loc_11);
            AssetPane _loc_12 =new AssetPane(_loc_10 );
            ASwingHelper.prepare(_loc_12);
            return _loc_12;
        }//end

        public static ASFont  makeFont (String param1 ,int param2 )
        {
            _loc_3 = Fonts.ensureFont(param1);
            if (_loc_3)
            {

                return new ASFont(_loc_3, param2, false, false, false, new ASFontAdvProperties());

            }

            return new ASFont(param1, param2, false, false, false, new ASFontAdvProperties());
        }//end

        public static void  prepare (Object ...args )
        {
            int argslen =0;
            Component _loc_3 =null ;
            if (args != null)
            {
                argslen = 0;
                while (argslen < args.length())
                {

                    _loc_3 =(Component) args.get(argslen);
                    if (_loc_3)
                    {
                        _loc_3.invalidatePreferSizeCaches();
                        _loc_3.pack();
                        _loc_3.revalidate();
                    }
                    argslen++;
                }
            }
            return;
        }//end

        public static JPanel  makeFlowJPanel (int param1 =1,int param2 =0,int param3 =0)
        {
            JPanel _loc_4 =new JPanel(new FlowLayout(param1 ,param2 ,param3 ,false ));
            return new JPanel(new FlowLayout(param1, param2, param3, false));
        }//end

        public static JPanel  makeSoftBoxJPanel (int param1 =1,int param2)
        {
            JPanel _loc_3 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,param2 ,param1 ));
            return _loc_3;
        }//end

        public static JPanel  makeSoftBoxJPanelVertical (int param1 =1,int param2)
        {
            JPanel _loc_3 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,param2 ,param1 ));
            return _loc_3;
        }//end

        public static SoftBoxLayout  softBoxLayout ()
        {
            if (!_sbl)
            {
                _sbl = new SoftBoxLayout(SoftBoxLayout.X_AXIS, 1);
            }
            return _sbl;
        }//end

        public static SoftBoxLayout  softBoxLayoutVertical ()
        {
            if (!_vbl)
            {
                _vbl = new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 1);
            }
            return _vbl;
        }//end

        public static DecorateBorder  debugLB ()
        {
            if (debugOff)
            {
                return null;
            }
            _dlb = new LineBorder(null, new ASColor(16777215 * Math.random()), 0.5, 0);
            return _dlb;
        }//end

        public static DecorateBorder  debug (String param1 )
        {
            return debugLB;
        }//end

        public static DecorateBorder  debugOnce ()
        {
            return debugLB;
        }//end

        public static JPanel  verticalStrut (int param1 ,boolean param2 =false )
        {
            JPanel _loc_3 =new JPanel ();
            _loc_3.setPreferredSize(new IntDimension(1, param1));
            if (param2 == true)
            {
                _loc_3.setBorder(debugLB);
            }
            return _loc_3;
        }//end

        public static JPanel  horizontalStrut (int param1 ,boolean param2 =false )
        {
            JPanel _loc_3 =new JPanel ();
            _loc_3.setPreferredSize(new IntDimension(param1, 1));
            if (param2 == true)
            {
                _loc_3.setBorder(debugLB);
            }
            return _loc_3;
        }//end

        public static boolean  setTextSpacing (DisplayObjectContainer param1 ,double param2 ,int param3 =-1)
        {
            _loc_7 = undefined;
            TextFormat _loc_8 =null ;
            boolean _loc_4 =false ;
            _loc_5 = param1.numChildren;
            int _loc_6 =0;
            while (_loc_6 < _loc_5)
            {

                _loc_7 = param1.getChildAt(_loc_6);
                if (_loc_7 instanceof TextField)
                {
                    _loc_8 = ((TextField)_loc_7).getTextFormat();
                    _loc_8.kerning = true;
                    _loc_8.underline = true;
                    _loc_8.letterSpacing = param2;
                    ((TextField)_loc_7).text = "blah";
                }
                else if (_loc_7 instanceof DisplayObjectContainer)
                {
                    if (param3 < 1000)
                    {
                        _loc_4 = setTextSpacing(_loc_7, param2, (param3 + 1));
                    }
                    else
                    {
                        return false;
                    }
                }
                if (_loc_4)
                {
                    return true;
                }
                _loc_6++;
            }
            return false;
        }//end

        public static ColorMatrixFilter  makeDisabledFilter ()
        {
            Array _loc_1 =.get(0 .3,0.59,0.11,0,0,0.3,0.59,0.11,0,0,0.3,0.59,0.11,0,0,0,0,0,1,0) ;
            return new ColorMatrixFilter(_loc_1);
        }//end

        public static Array  getTokenIndices (String param1 ,String param2 ,int param3 )
        {
            if (!param3)
            {
                return null;
            }
            Array _loc_4 =new Array ();
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_5 = 0;
            while (_loc_5 < param3)
            {

                _loc_4.put(_loc_5, newArray());
                _loc_4.get(_loc_5).put(0,  param1.indexOf("{", _loc_6) - _loc_7);
                _loc_4.get(_loc_5).put(1,  param2.indexOf(" ", _loc_4.get(_loc_5).get(0)));
                if (_loc_4.get(_loc_5).get(1) == -1)
                {
                    _loc_4.get(_loc_5).put(1,  param2.length - 1);
                }
                _loc_6 = _loc_4.get(_loc_5).get(1);
                _loc_7 = _loc_4.get(_loc_5).get(1) - _loc_4.get(_loc_5).get(0);
                _loc_5++;
            }
            return _loc_4;
        }//end

        public static JTextField  multicolorize (JTextField param1 ,Array param2 ,Array param3 )
        {
            int _loc_7 =0;
            int _loc_8 =0;
            _loc_4 = param1;
            _loc_5 = param1.getText();
            TextFormat _loc_6 =new TextFormat ();
            for(int i0 = 0; i0 < param3.size(); i0++)
            {
            	_loc_7 = param3.get(i0);

                _loc_6.color = _loc_7;
            }
            _loc_8 = 0;
            while (_loc_8 < param3.length())
            {

                _loc_6.color = param3.get(_loc_8);
                _loc_4.setTextFormat(_loc_6, param2.get(_loc_8).get(0), param2.get(_loc_8).get(1));
                _loc_8++;
            }
            return _loc_4;
        }//end

        public static String  colorize (String param1 ,double param2 )
        {
            _loc_3 = param2"<font color=\'#"+.toString(16)+"\'>"+param1+"</font>";
            return _loc_3;
        }//end

        public static String  applyFont (String param1 ,String param2 )
        {
            _loc_3 = param2"<font face=\'"++"\'>"+param1+"</font>";
            return _loc_3;
        }//end

        public static void  setForcedSize (Component param1 ,IntDimension param2 )
        {
            param1.setPreferredSize(param2);
            param1.setMinimumSize(param2);
            param1.setMaximumSize(param2);
            return;
        }//end

        public static void  setForcedWidth (Component param1 ,int param2 )
        {
            param1.setPreferredWidth(param2);
            param1.setMinimumWidth(param2);
            param1.setMaximumWidth(param2);
            return;
        }//end

        public static void  setForcedHeight (Component param1 ,int param2 )
        {
            param1.setPreferredHeight(param2);
            param1.setMinimumHeight(param2);
            param1.setMaximumHeight(param2);
            return;
        }//end

        public static double  scaleToFit (DisplayObject param1 ,Component param2 )
        {
            double _loc_3 =1;
            double _loc_4 =1;
            int _loc_5 = param2.getPreferredHeight();
            int _loc_6 = param2.getPreferredWidth();
            if (param1.width > _loc_6)
            {
                _loc_3 = _loc_6 / param1.width;
            }
            if (param1.height > _loc_5)
            {
                _loc_4 = _loc_5 / param1.height;
            }
            return Math.min(_loc_3, _loc_4);
        }//end

        public static void  scaleBitmapTo (Bitmap param1 ,double param2 ,double param3 )
        {
            if (param1 !=null)
            {
                param1.smoothing = true;
                param1.width = param2;
                param1.height = param3;
            }
            return;
        }//end

        public static JPanel  makeFriendImageFromZid (String param1 )
        {
            DisplayObject asset ;
            Sprite image ;
            String url ;
            zid = param1;
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            asset =(DisplayObject) new EmbeddedArt.hud_no_profile_pic();
            image = new Sprite();
            if (zid.substr(0, 1) == "-")
            {
                url = Global.player.findFriendById("-1").snUser.picture;
            }
            else if (zid == Global.player.uid)
            {
                url = Global.player.snUser.picture;
            }
            else if (Global.player.findFriendById(zid))
            {
                url = Global.player.findFriendById(zid).snUser.picture;
            }
            image.addChild(asset);
            AssetPane imageContainer =new AssetPane(image );
            ASwingHelper.setEasyBorder(imageContainer, 0, 0, 0, 0);
            ASwingHelper.prepare(imageContainer);
            if (url)
            {
void                 LoadingManager .loadFromUrl (url ,{LoadingManager priority .PRIORITY_HIGH , completeCallback (Event event )
            {
                image.removeChild(asset);
                asset =(DisplayObject) event.target.content;
                image.addChild(asset);
                return;
            }//end
            });
            }
            container.append(imageContainer);
            ASwingHelper.prepare(container);
            return container;
        }//end

        public static TextField  makeText (String param1 ,int param2 ,String param3 ,int param4 =12,int param5 =16777215,String param6 ="center")
        {
            TextField _loc_7 =new TextField ();
            _loc_7.width = param2;
            _loc_7.multiline = true;
            _loc_7.wordWrap = true;
            _loc_7.selectable = false;
            _loc_7.embedFonts = EmbeddedArt.isEmbedFont(param3);
            _loc_7.antiAliasType = AntiAliasType.ADVANCED;
            _loc_7.gridFitType = GridFitType.PIXEL;
            _loc_7.autoSize = TextFieldAutoSize.CENTER;
            TextFormat _loc_8 =new TextFormat ();
            _loc_8.font = param3;
            _loc_8.color = param5;
            _loc_8.align = param6;
            _loc_8.size = param4;
            _loc_7.defaultTextFormat = _loc_8;
            _loc_7.setTextFormat(_loc_8);
            _loc_7.text = param1;
            return _loc_7;
        }//end

        public static TextField  makeOneLineText (String param1 ,String param2 ,int param3 =12,int param4 =16777215,String param5 ="center")
        {
            TextField _loc_6 =new TextField ();
            _loc_6.multiline = false;
            _loc_6.wordWrap = false;
            _loc_6.text = param1;
            _loc_6.selectable = false;
            _loc_6.embedFonts = EmbeddedArt.isEmbedFont(param2);
            _loc_6.antiAliasType = AntiAliasType.ADVANCED;
            _loc_6.gridFitType = GridFitType.PIXEL;
            _loc_6.autoSize = TextFieldAutoSize.CENTER;
            TextFormat _loc_7 =new TextFormat ();
            _loc_7.font = param2;
            _loc_7.color = param4;
            _loc_7.align = param5;
            _loc_7.size = param3;
            _loc_6.defaultTextFormat = _loc_7;
            _loc_6.setTextFormat(_loc_7);
            return _loc_6;
        }//end

        public static void  normalizeComponentSizes (Array param1 ,boolean param2 ,boolean param3 )
        {
            Component _loc_6 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_6 = param1.get(i0);

                if (param2 && _loc_6.getPreferredWidth() > _loc_4)
                {
                    _loc_4 = _loc_6.getPreferredWidth();
                }
                if (param3 && _loc_6.getPreferredHeight() > _loc_5)
                {
                    _loc_5 = _loc_6.getPreferredHeight();
                }
            }
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_6 = param1.get(i0);

                if (param2)
                {
                    _loc_6.setPreferredWidth(_loc_4);
                    _loc_6.width = _loc_4;
                }
                if (param3)
                {
                    _loc_6.setPreferredHeight(_loc_5);
                    _loc_6.height = _loc_5;
                }
            }
            return;
        }//end

        public static void  createImageCardCell (JPanel param1 ,String param2 ,double param3 =1)
        {
            int cardHeight ;
            AssetPane ap ;
            DisplayObject icon ;
            cardPanel = param1;
            iconUrl = param2;
            scale = param3;
            cardWidth = cardPanel.getPreferredWidth();
            cardHeight = cardPanel.getPreferredHeight();
            ap = new AssetPane();
            icon;
            LoadingManager .load (iconUrl ,void  (Event event )
            {
                icon = event.target.content;
                BitmapData _loc_2 =new BitmapData(icon.width ,icon.height ,true ,16777215);
                _loc_2.draw(icon);
                BitmapData _loc_3 =new BitmapData(icon.width *scale ,icon.height *scale ,true ,16777215);
                Matrix _loc_4 =new Matrix ();
                _loc_4.scale(scale, scale);
                _loc_3.draw(_loc_2, _loc_4, null, null, null, true);
                Bitmap _loc_5 =new Bitmap(_loc_3 ,"auto",true );
                _loc_6 = cardHeight(-_loc_5.height)/2;
                ap.setAsset(_loc_5);
                ASwingHelper.setEasyBorder(ap, _loc_6);
                return;
            }//end
            );
            cardPanel.append(ap);
            return;
        }//end

        public static int  shrinkFontSizeToFit (int param1 ,String param2 ,String param3 ,int param4 ,Array param5 =null ,TextFormat param6 =null ,TextFormat param7 =null ,int param8 =12)
        {
            JTextField _loc_9 =null ;
            if (param2 == null)
            {
                return param8;
            }
            _loc_10 = param4;
            do
            {

                _loc_9 = ASwingHelper.makeTextField(param2, param3, _loc_10, 0, 0, param6);
                if (param5)
                {
                    _loc_9.filters = param5;
                }
                if (param7)
                {
                    TextFieldUtil.formatSmallCaps(_loc_9.getTextField(), param7);
                }
                _loc_10 = _loc_10 - 1;
            }while (_loc_9.getTextField().textWidth > param1 && _loc_10 >= param8)
            return (_loc_10 + 1);
        }//end

        public static void  setProperFont (Component param1 ,String param2 )
        {
            _loc_3 = param1.getFont();
            ASFont _loc_4 =new ASFont(param2 ,_loc_3.getSize (),_loc_3.isBold (),_loc_3.isItalic (),_loc_3.isUnderline (),EmbeddedArt.advancedFontProps );
            param1.setFont(_loc_4);
            return;
        }//end

    }





