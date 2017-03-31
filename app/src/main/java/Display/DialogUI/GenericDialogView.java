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
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.net.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

import com.xinghai.Debug;

    public class GenericDialogView extends JPanel
    {
        protected double m_titleFontSize =20;
        protected double m_titleSmallCapsFontSize =30;
        protected String m_message ;
        protected String m_titleString ;
        protected int m_type ;
        protected String m_acceptTextName ="Accept";
        protected String m_cancelTextName ="Cancel";
        protected String m_skipTextName ="Skip";
        protected String m_shareTextName ="Share";
        protected Function m_closeCallback ;
        protected Function m_skipCallback ;
        protected DisplayObject m_bgAsset ;
        protected DisplayObject m_bgInterior ;
        protected DisplayObject m_closeBtn ;
        protected DisplayObject m_keystone ;
        protected Dictionary m_assetDict ;
        protected JPanel m_titlePanel ;
        protected JPanel m_interiorHolder ;
        protected JPanel m_textArea ;
        protected String m_align ;
        protected JTextField m_title ;
        protected String m_icon ;
        protected int m_iconPos ;
        protected double m_messagePaddingLeft =45;
        protected double m_messagePaddingRight =40;
        protected String m_customOk ;
        protected boolean m_relativeIcon =true ;
        protected  int character_width =109;
        protected  int character_height =191;
        protected String m_feedShareViralType ="";
        protected Function m_doStatsTrackingFunction =null ;
        protected Function m_getStatsTitleFunction =null ;
        protected boolean m_html =false ;
        protected String m_fbLikeURL ="http://www.zynga.com";
        protected String m_fbLikeTitle ="Zynga";
        protected String m_fbLikeReference ="cityville";
        protected String m_likeButtonString ="Like";
        protected String m_visitButtonString ="Visit";
        protected JPanel m_likeButtonPanel =null ;
        protected Loader m_iconLoader ;
        public static  int TYPE_NONE =-1;
        public static  int TYPE_OK =0;
        public static  int TYPE_ACCEPTCANCEL =1;
        public static  int TYPE_YESNO =2;
        public static  int TYPE_MODAL =3;
        public static  int TYPE_SAVESHARESAVE =4;
        public static  int TYPE_SHARECANCEL =5;
        public static  int TYPE_SENDGIFTS =6;
        public static  int TYPE_CONFIRM =7;
        public static  int TYPE_NOBUTTONS =8;
        public static  int TYPE_VISIT =9;
        public static  int TYPE_ADDNEIGHBOR =10;
        public static  int TYPE_ASKFRIEND =11;
        public static  int TYPE_ASKFRIENDS_BUYPERMITS =12;
        public static  int TYPE_SAVE =13;
        public static  int TYPE_TITLENOCLOSE =14;
        public static  int TYPE_SAVESHARECOINS =15;
        public static  int TYPE_GETCASH =16;
        public static  int TYPE_SHARECOINS =17;
        public static  int TYPE_REMOVECANCEL =18;
        public static  int TYPE_PLAY =19;
        public static  int TYPE_INVITEFRIENDS_BUYNEIGHBORS =19;
        public static  int TYPE_INVITEFRIENDS_BUYFRANCHISEUNLOCKS =20;
        public static  int TYPE_CUSTOM_OK =21;
        public static  int TYPE_YESNO_SHARE =22;
        public static  int TYPE_GETCASH_SHARE =23;
        public static  int TYPE_SAVESHARESAVEREDGREEN =24;
        public static  int TYPE_SHARE =25;
        public static  int TYPE_SAVE_CLOSE =26;
        public static  int TYPE_BUY_SHARE =27;
        public static  int TYPE_OK_WITHOUTCANCEL =28;
        public static  int TYPE_GENERIC_OK_WITHOUTCANCEL =29;
        public static  int YES =1;
        public static  int CANCEL =0;
        public static  int NO =0;
        public static  int SKIP =2;
        public static  int YES_NOMARKET =3;
        public static  int SHARE =4;
        public static  int ICON_POS_LEFT =0;
        public static  int ICON_POS_RIGHT =1;
        public static  int ICON_POS_TOP =2;
        public static  int ICON_POS_BOTTOM =3;
        public static  int BUTTON_MARGIN =20;
        public static  int MAX_TITLE_TEXT_WIDTH =700;

        public  GenericDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true ,boolean param12 =false ,String param13 ="",String param14 ="",String param15 ="")
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.TOP));
            Debug.debug6("GenericDialogView."+param2+";"+param3);
            this.m_icon = param6;
            this.m_iconPos = param7;
            this.m_assetDict = param1;
            this.m_type = param4;
            this.m_customOk = param10;
            this.m_relativeIcon = param11;
            this.m_html = param12;
            if (param5 != null)
            {
                this.m_closeCallback = param5;
            }
            if (param9 != null)
            {
                this.m_skipCallback = param9;
            }
            this.m_titleString = param3;
            this.m_message = param2;
            this.m_feedShareViralType = param8;
            this.m_align = this.m_type == TYPE_ASKFRIEND ? (TextFormatAlign.LEFT) : (TextFormatAlign.CENTER);
            this.m_fbLikeURL = param13;
            this.m_fbLikeReference = param14;
            this.m_fbLikeTitle = param15;
            this.init();
            return;
        }//end

        protected void  init ()
        {
            switch(this.m_type)
            {
                case TYPE_OK:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "OkButton");
                    break;
                }
                case TYPE_SHARE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Share");
                    break;
                }
                case TYPE_CUSTOM_OK:
                {
                    this.m_acceptTextName = this.m_customOk;
                    break;
                }
                case TYPE_YESNO:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Yes");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "No");
                    break;
                }
                case TYPE_ACCEPTCANCEL:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Accept");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Cancel");
                    break;
                }
                case TYPE_SAVESHARESAVEREDGREEN:
                case TYPE_SAVESHARESAVE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "SaveShare");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Save");
                    break;
                }
                case TYPE_SHARECANCEL:
                {
                    if (this.m_feedShareViralType == "")
                    {
                        this.m_acceptTextName = ZLoc.t("Dialogs", "Share");
                    }
                    else
                    {
                        this.m_acceptTextName = Viral.getViralHelperRewardText(this.m_feedShareViralType);
                    }
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Cancel");
                    break;
                }
                case TYPE_SENDGIFTS:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "SendGifts");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Skip");
                    break;
                }
                case TYPE_CONFIRM:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Confirm");
                    break;
                }
                case TYPE_VISIT:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Visit");
                    break;
                }
                case TYPE_ADDNEIGHBOR:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Expand");
                    break;
                }
                case TYPE_ASKFRIEND:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "AskFriends");
                    break;
                }
                case TYPE_ASKFRIENDS_BUYPERMITS:
                {
                    this.m_cancelTextName = ZLoc.t("Dialogs", "PermitDialog_ask");
                    break;
                }
                case TYPE_INVITEFRIENDS_BUYNEIGHBORS:
                {
                    this.m_cancelTextName = ZLoc.t("Dialogs", "NeighborBuyDialog_ask");
                    break;
                }
                case TYPE_SAVE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Save");
                    break;
                }
                case TYPE_SAVE_CLOSE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Save");
                    break;
                }
                case TYPE_SAVESHARECOINS:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "SaveShareCoins");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Save");
                    break;
                }
                case TYPE_GETCASH:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "GetCash");
                    break;
                }
                case TYPE_SHARECOINS:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "ShareCoins");
                    break;
                }
                case TYPE_REMOVECANCEL:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Remove");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "Cancel");
                    break;
                }
                case TYPE_PLAY:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "PlayCityville");
                    break;
                }
                case TYPE_YESNO_SHARE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "Yes");
                    this.m_cancelTextName = ZLoc.t("Dialogs", "No");
                    this.m_shareTextName = ZLoc.t("Dialogs", "AskFriendsPermits");
                    break;
                }
                case TYPE_BUY_SHARE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "UnlockExpansion");
                    this.m_shareTextName = ZLoc.t("Dialogs", "AskFriendsPermits");
                    break;
                }
                case TYPE_GETCASH_SHARE:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "GetCash");
                    this.m_shareTextName = ZLoc.t("Dialogs", "AskFriendsPermits");
                    break;
                }
                case TYPE_GENERIC_OK_WITHOUTCANCEL:
                {
                    this.m_acceptTextName = ZLoc.t("Dialogs", "OkButton");
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.m_bgAsset = this.m_assetDict.get("dialog_bg");
            this.m_closeBtn = this.m_assetDict.get("btn_closemc");
            this.m_likeButtonString = ZLoc.t("Dialogs", "FBLike");
            this.m_visitButtonString = ZLoc.t("Dialogs", "FBVisit");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  conditionalRebuildCenterPanel ()
        {
            this.remove(this.interiorHolder);
            this.makeCenterPanel();
            return;
        }//end

        public Dictionary  assetDict ()
        {
            return this.m_assetDict;
        }//end

        protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (this.m_bgAsset)
            {
                _loc_1 = new MarginBackground(this.m_bgAsset, new Insets(0, 0, 10, 0));
                this.setBackgroundDecorator(_loc_1);
            }
            return;
        }//end

        public void  titlePanel (JPanel param1 )
        {
            this.m_titlePanel = param1;
            return;
        }//end

        public JPanel  titlePanel ()
        {
            return this.m_titlePanel;
        }//end

        public void  title (JTextField param1 )
        {
            this.m_title = param1;
            return;
        }//end

        public JTextField  title ()
        {
            return this.m_title;
        }//end

        public void  interiorHolder (JPanel param1 )
        {
            this.m_interiorHolder = param1;
            return;
        }//end

        public JPanel  interiorHolder ()
        {
            return this.m_interiorHolder;
        }//end

        public void  textArea (JPanel param1 )
        {
            this.m_textArea = param1;
            return;
        }//end

        public JPanel  textArea ()
        {
            return this.m_textArea;
        }//end

        public String  acceptText ()
        {
            return this.m_acceptTextName;
        }//end

        public String  cancelText ()
        {
            return this.m_cancelTextName;
        }//end

        public String  skipText ()
        {
            return this.m_skipTextName;
        }//end

        public void  setStatsTrackingFunction (Function param1 ,Function param2 )
        {
            this.m_doStatsTrackingFunction = param1;
            this.m_getStatsTitleFunction = param2;
            return;
        }//end

        protected void  makeCenterPanel ()
        {
            this.interiorHolder = this.createInteriorHolder();
            this.append(this.interiorHolder);
            Debug.debug6("GenericDialgoView.makeCenterPanel");
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = this.createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            this.textArea = this.createTextArea();
            ASwingHelper.prepare(this.textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (this.m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(this.textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (this.m_type != TYPE_MODAL && this.m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = this.createButtonPanel();
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_titleFontSize = TextFieldUtil.getLocaleFontSize(this.m_titleSmallCapsFontSize, this.m_titleFontSize, [{locale:"ja", size:this.m_titleSmallCapsFontSize}]);
            if (this.m_titleString != "")
            {
                _loc_2 = ZLoc.t("Dialogs", this.m_titleString + "_title", this.getTitleTokens());
                _loc_3 = new TextFormat();
                _loc_3.size = this.m_titleSmallCapsFontSize;
                this.m_titleFontSize = ASwingHelper.shrinkFontSizeToFit(MAX_TITLE_TEXT_WIDTH, _loc_2, EmbeddedArt.titleFont, this.m_titleFontSize, EmbeddedArt.newtitleFilters, null, _loc_3);
                this.title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, this.m_titleFontSize, EmbeddedArt.titleColor, 3);
                this.title.filters = EmbeddedArt.newtitleFilters;
                TextFieldUtil.formatSmallCaps(this.title.getTextField(), _loc_3);
                _loc_1.append(this.title);
                this.title.getTextField().height = this.m_titleFontSize * 1.5;
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        protected Object  getTitleTokens ()
        {
            return null;
        }//end

        protected JPanel  createIconPane ()
        {
            AssetPane iconPane ;
            String iconString ;
            Loader iconLoader ;
            Debug.debug6("GenericDialogView.createIconPane");


            iconPane = new AssetPane();
            if (this.m_relativeIcon)
            {
                iconString = Global.getAssetURL(this.m_icon);
            }
            else
            {
                iconString = this.m_icon;
            }
            iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , this));
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

        protected JPanel  createFBLikeButton ()
        {
            JLabel textPanel ;
            JPanel iconPanel ;
            AssetPane iconPane ;
            String buttonString ;
            Loader buttonLoader ;
            JPanel iconPanel2 ;
            AssetPane iconPane2 ;
            String buttonString2 ;
            Loader buttonLoader2 ;
            boolean _loc_2 =false ;
            Debug.debug6("GenericDialogView.createFBLikeButton");

            fbLikeVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FBLIKEBUTTON);
            if (fbLikeVariant == 0)
            {
                return null;
            }
            if (this.m_fbLikeURL.length > 0)
            {
                this.m_likeButtonPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                if (fbLikeVariant == ExperimentDefinitions.EXPERIMENT_FBLIKEBUTTON_NORMAL)
                {
                    textPanel = ASwingHelper.makeLabel(this.m_likeButtonString, EmbeddedArt.defaultFontNameBold, 11, 1995270);
                    iconPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    iconPane = new AssetPane();
                    buttonString = Global.getAssetURL("assets/fblike/fb_thumb_icon_pad.png");
                    buttonLoader =LoadingManager .load (buttonString ,void  (Event event )
            {
                iconPane.setAsset(event.target.content);
                ASwingHelper.prepare(iconPane);
                return;
            }//end
            );
                    iconPanel.append(ASwingHelper.centerElement(iconPane), BorderLayout.CENTER);
                    this.m_likeButtonPanel.appendAll(iconPanel, textPanel);
                    iconPanel.setBackgroundDecorator(new SolidBackground(new ASColor(13100989)));

                    iconPanel.mouseChildren = false;
                    iconPanel.mouseEnabled = false;
                    this.m_likeButtonPanel.addEventListener(MouseEvent.CLICK, this.onFBLike, false, 0, true);
                }
                else if (fbLikeVariant == ExperimentDefinitions.EXPERIMENT_FBLIKEBUTTON_VISIT)
                {
                    textPanel = ASwingHelper.makeLabel(this.m_visitButtonString, EmbeddedArt.defaultFontNameBold, 11, 1995270);
                    iconPanel2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    iconPane2 = new AssetPane();
                    buttonString2 = Global.getAssetURL("assets/fblike/visit_button_padding.png");
                    buttonLoader2 =LoadingManager .load (buttonString2 ,void  (Event event )
            {
                iconPane2.setAsset(event.target.content);
                ASwingHelper.prepare(iconPane2);
                return;
            }//end
            );
                    iconPanel2.append(ASwingHelper.centerElement(iconPane2), BorderLayout.CENTER);
                    this.m_likeButtonPanel.appendAll(iconPanel2, textPanel);
                    iconPanel2.setBackgroundDecorator(new SolidBackground(new ASColor(13100989)));

                    iconPanel2.mouseChildren = false;
                    iconPanel2.mouseEnabled = false;
                    this.m_likeButtonPanel.addEventListener(MouseEvent.CLICK, this.onFBVisit, false, 0, true);
                }
                this.m_likeButtonPanel.setBorder(new LineBorder(null, new ASColor(10278028), 2));
                textPanel.setBackgroundDecorator(new SolidBackground(new ASColor(13100989)));
                this.m_likeButtonPanel.setBackgroundDecorator(new SolidBackground(new ASColor(13100989)));

                textPanel.mouseChildren = false;
                textPanel.mouseEnabled = false;
                this.m_likeButtonPanel.addEventListener(MouseEvent.MOUSE_OVER, this.onFBLikeEnter, false, 0, true);
                this.m_likeButtonPanel.addEventListener(MouseEvent.MOUSE_OUT, this.onFBLikeLeave, false, 0, true);
            }
            return this.m_likeButtonPanel;
        }//end

        protected JPanel  createTextArea ()
        {
            double _loc_2 =0;
            JPanel _loc_8 =null ;
            JPanel _loc_9 =null ;
            JPanel _loc_10 =null ;
            JPanel _loc_11 =null ;
            Debug.debug6("GenericDialogView.createTextArea");

            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            if (this.m_icon != null && this.m_icon != "")
            {
                _loc_2 = this.setMessageTextWidth(this.m_iconPos == ICON_POS_LEFT || this.m_iconPos == ICON_POS_RIGHT);
                _loc_8 = this.createIconPane();
            }
            else
            {
                _loc_2 = this.setMessageTextWidth(false);
            }
            _loc_3 = this.createTextComponent(_loc_2);
            _loc_4 = this.createTextAreaInnerPane(_loc_3);
            _loc_5 = _loc_8;
            _loc_6 = _loc_4;
            if (this.m_iconPos == ICON_POS_RIGHT || this.m_iconPos == ICON_POS_BOTTOM)
            {
                _loc_5 = _loc_4;
                _loc_6 = _loc_8;
            }
            _loc_1.append(ASwingHelper.horizontalStrut(this.m_messagePaddingLeft));
            if (this.m_iconPos == ICON_POS_LEFT || this.m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_5)
                {
                    _loc_1.append(_loc_5);
                }
                if (_loc_6)
                {
                    _loc_1.append(_loc_6);
                }
            }
            else
            {
                _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 10);
                ASwingHelper.setEasyBorder(_loc_9, 10);
                _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                if (_loc_5)
                {
                    _loc_10.append(_loc_5);
                    ASwingHelper.prepare(_loc_10);
                    _loc_9.append(_loc_10);
                }
                if (_loc_6)
                {
                    _loc_11.append(_loc_6);
                    ASwingHelper.prepare(_loc_11);
                    _loc_9.append(_loc_11);
                }
                ASwingHelper.prepare(_loc_9);
                _loc_1.append(_loc_9);
            }
            _loc_1.append(ASwingHelper.horizontalStrut(this.m_messagePaddingRight));
            _loc_7 = _loc_3.getHeight();
            return _loc_1;
        }//end

        protected double  setMessageTextWidth (boolean param1 =false )
        {
            double _loc_2 =0;
            if (param1 == false)
            {
                _loc_2 = 350;
            }
            else
            {
                _loc_2 = 270;
            }
            return _loc_2;
        }//end

        protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_5 =0;
            Container _loc_6 =null ;
            Debug.debug7("GenericDialogView.createTextComponent " + EmbeddedArt.defaultFontNameBold+";"+this.m_message);

            _loc_3 = ASwingHelper.makeMultilineText(this.m_message,param1,EmbeddedArt.defaultFontNameBold,this.m_align,18,EmbeddedArt.brownTextColor,null,this.m_html);
            double _loc_4 =75;
            if (_loc_3.getHeight() < _loc_4)
            {
                _loc_5 = (_loc_4 - _loc_3.getHeight()) / 2;
                _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_6.append(ASwingHelper.verticalStrut(_loc_5));
                _loc_6.append(_loc_3);
                _loc_6.append(ASwingHelper.verticalStrut(_loc_5));
                _loc_2 = _loc_6;
            }
            else
            {
                _loc_2 = _loc_3;
            }
            return _loc_2;
        }//end

        protected JPanel  createTextAreaInnerPane (Component param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2.append(param1);
            return _loc_2;
        }//end

        protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = this.createCloseButtonPanel();
            _loc_3 = this.createTitlePanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            return _loc_1;
        }//end

        protected JPanel  createHeaderInnerPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.RIGHT ,0,0));
            _loc_2 = this.createCloseButtonPanel();
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  createCloseButtonPanel ()
        {
            JButton _loc_2 =null ;
            Object _loc_3 =null ;
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            if (this.m_type == TYPE_OK || this.m_type == TYPE_SAVE_CLOSE || this.m_type == TYPE_CUSTOM_OK || this.m_type == TYPE_ACCEPTCANCEL || this.m_type == TYPE_MODAL || this.m_type == TYPE_SHARECANCEL || this.m_type == TYPE_CONFIRM || this.m_type == TYPE_ASKFRIENDS_BUYPERMITS || this.m_type == TYPE_INVITEFRIENDS_BUYNEIGHBORS || this.m_type == TYPE_VISIT || this.m_type == TYPE_ADDNEIGHBOR || this.m_type == TYPE_ASKFRIEND || this.m_type == TYPE_NOBUTTONS || this.m_type == TYPE_YESNO || this.m_type == TYPE_YESNO_SHARE || this.m_type == TYPE_BUY_SHARE || this.m_type == TYPE_GETCASH || this.m_type == TYPE_GETCASH_SHARE || this.m_type == TYPE_SHARECOINS || this.m_type == TYPE_PLAY || this.m_type == TYPE_SAVESHARESAVEREDGREEN || this.m_type == TYPE_SHARE)
            {
                _loc_2 = ASwingHelper.makeMarketCloseButton();
                _loc_2.addEventListener(MouseEvent.CLICK, this.onCancelX, false, 0, true);
                _loc_1.append(_loc_2);
                _loc_3 = this.getCloseButtonBorder();
                ASwingHelper.setEasyBorder(_loc_2, _loc_3.get("top"), _loc_3.get("left"), _loc_3.get("bottom"), _loc_3.get("right"));
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(20));
            }
            return _loc_1;
        }//end

        protected Object  getCloseButtonBorder ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put("top",  3);
            _loc_1.put("left",  0);
            _loc_1.put("bottom",  0);
            _loc_1.put("right",  7);
            return _loc_1;
        }//end

        protected JPanel  createButtonPanel ()
        {
            CustomButton _loc_3 =null ;
            CustomButton _loc_4 =null ;
            CustomButton _loc_5 =null ;
            CustomButton _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));

            Debug.debug6("GenericDialogView.createButtonPanel");


            TextFieldUtil.formatSmallCapsString(this.m_cancelTextName);
            if (this.m_type == TYPE_SAVESHARESAVE || this.m_type == TYPE_SAVESHARESAVEREDGREEN)
            {
                _loc_3 = null;
                if (this.m_type == TYPE_SAVESHARESAVE)
                {
                    _loc_3 = new CustomButton(this.m_cancelTextName, null, "GreyButtonUI");
                }
                else
                {
                    _loc_3 = new CustomButton(this.m_cancelTextName, null, "RedButtonUI");
                }
                if (Global.localizer.langCode == "en")
                {
                    _loc_3.setPreferredWidth(100);
                    _loc_3.setMinimumWidth(100);
                    _loc_3.setMaximumWidth(100);
                }
                _loc_3.addActionListener(this.onSkip, 0, true);
                _loc_1.append(_loc_3);
                _loc_1.append(ASwingHelper.horizontalStrut(5));
                this.m_acceptTextName = this.m_acceptTextName;
                this.m_skipTextName = this.m_acceptTextName;
            }
            if (this.m_type != TYPE_SHARECANCEL && this.m_type != TYPE_SAVESHARESAVE && this.m_type != TYPE_SAVESHARESAVEREDGREEN && this.m_type != TYPE_SAVE_CLOSE && this.m_type != TYPE_SAVE && this.m_type != TYPE_VISIT && this.m_type != TYPE_ADDNEIGHBOR && this.m_type != TYPE_ASKFRIEND && this.m_type != TYPE_INVITEFRIENDS_BUYNEIGHBORS && this.m_type != TYPE_ASKFRIENDS_BUYPERMITS && this.m_type != TYPE_OK && this.m_type != TYPE_CUSTOM_OK && this.m_type != TYPE_SHARECOINS && this.m_type != TYPE_GETCASH && this.m_type != TYPE_GETCASH_SHARE && this.m_type != TYPE_BUY_SHARE && this.m_type != TYPE_PLAY && this.m_type != TYPE_SHARE && this.m_type != TYPE_OK_WITHOUTCANCEL && this.m_type != TYPE_GENERIC_OK_WITHOUTCANCEL)
            {
                _loc_4 = new CustomButton(this.m_cancelTextName, null, "RedButtonUI");
                _loc_4.addActionListener(this.onCancel, 0, true);
                _loc_1.append(_loc_4);
            }
            if (this.m_type == TYPE_ASKFRIENDS_BUYPERMITS || this.m_type == TYPE_INVITEFRIENDS_BUYNEIGHBORS)
            {
                _loc_5 = new CustomButton(this.m_cancelTextName, null, "GreenButtonUI");
                _loc_5.addActionListener(this.onSkip, 0, true);
                this.m_skipTextName = this.m_cancelTextName;
                _loc_1.append(_loc_5);
            }
            TextFieldUtil.formatSmallCapsString(this.m_acceptTextName);
            CustomButton _loc_2 =new CustomButton(this.m_acceptTextName ,null ,"GreenButtonUI");
            if (Global.localizer.langCode == "en")
            {
                if (this.m_type == TYPE_SAVESHARESAVE)
                {
                    _loc_2.setPreferredWidth(225);
                    _loc_2.setMinimumWidth(225);
                    _loc_2.setMaximumWidth(225);
                }
                if (this.m_type == TYPE_SAVE)
                {
                    _loc_2.setPreferredWidth(175);
                    _loc_2.setMinimumWidth(175);
                    _loc_2.setMaximumWidth(175);
                }
                if (this.m_type == TYPE_SAVE_CLOSE)
                {
                    _loc_2.setPreferredWidth(175);
                    _loc_2.setMinimumWidth(175);
                    _loc_2.setMaximumWidth(175);
                }
            }
            _loc_2.addActionListener(this.onAccept, 0, true);
            _loc_1.append(_loc_2);
            if (this.m_type == TYPE_YESNO_SHARE || this.m_type == TYPE_GETCASH_SHARE || this.m_type == TYPE_BUY_SHARE)
            {
                _loc_6 = new CustomButton(this.m_shareTextName, null, "GreenButtonUI");
                _loc_6.addActionListener(this.onShare, 0, true);
                _loc_1.append(_loc_6);
            }
            return _loc_1;
        }//end

        protected void  onAccept (AWEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES, true));
            this.close();
            return;
        }//end

        protected void  onCancelX (Object param1)
        {
            this.m_cancelTextName = "";
            this.countDialogViewAction("X");
            this.onCancel(param1);
            return;
        }//end

        protected void  onCancel (Object param1)
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, NO, true));
            this.close();
            return;
        }//end

        protected void  onSkip (Object param1)
        {
            GenericPopupEvent _loc_2 =new GenericPopupEvent(GenericPopupEvent.SELECTED ,SKIP ,true );
            dispatchEvent(_loc_2);
            this.close();
            return;
        }//end

        protected void  onShare (Object param1)
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, SHARE, true));
            this.close();
            return;
        }//end

        protected void  onFBLike (Object param1)
        {
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "fb_like_button", "like_button_click", this.m_fbLikeReference);
            ExternalInterface.call("theFarmOverlay.showFBLikeOverlay", this.m_fbLikeTitle, this.m_fbLikeURL, this.m_fbLikeReference, ZLoc.t("Dialogs", "Close"), ZLoc.t("Dialogs", "FBVisit"));
            return;
        }//end

        protected void  onFBVisit (Object param1)
        {
            event = param1;
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "fb_visit_button", "visit_button_click", this.m_fbLikeReference);
            URLRequest request =new URLRequest(this.m_fbLikeURL );
            try
            {
                navigateToURL(request, "_blank");
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        protected void  onFBLikeEnter (Object param1)
        {
            this.m_likeButtonPanel.setBorder(new LineBorder(null, new ASColor(7188570), 2));
            return;
        }//end

        protected void  onFBLikeLeave (Object param1)
        {
            this.m_likeButtonPanel.setBorder(new LineBorder(null, new ASColor(10278028), 2));
            return;
        }//end

        public void  close ()
        {
            if (this.m_skipCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, this.m_skipCallback);
            }
            this.closeMe();
            return;
        }//end

        protected void  closeMe ()
        {
            dispatchEvent(new Event("close", true));
            return;
        }//end

        public void  setAcceptTextName (String param1 )
        {
            this.m_acceptTextName = param1;
            return;
        }//end

        public void  setCancelTextName (String param1 )
        {
            this.m_cancelTextName = param1;
            return;
        }//end

        public void  countDialogViewAction (String param1 ="view",boolean param2 =true ,int param3 =1,String param4 ="",String param5 ="",String param6 ="")
        {
            if (this.m_doStatsTrackingFunction == null || this.m_doStatsTrackingFunction == true)
            {
                return;
            }
            _loc_7 = this.m_doStatsTrackingFunction();
            if (!_loc_7)
            {
                return;
            }
            _loc_8 = this.m_getStatsTitleFunction();
            _loc_9 = GameUtil.trimBadStatsCharacters(param1);
            if (this.trackingIsSampled())
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_8, _loc_9, param4, param5, param6, param3);
            }
            else
            {
                StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, _loc_8, _loc_9, param4, param5, param6, param3);
            }
            if (param2)
            {
                this.m_acceptTextName = "";
                this.m_cancelTextName = "";
            }
            return;
        }//end

        protected boolean  trackingIsSampled ()
        {
            return true;
        }//end

    }




