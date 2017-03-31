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
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class LevelUpDialogView extends GenericDialogView
    {
        protected int m_level ;
        protected Array m_buyableItems ;
        protected Array m_specialBuyableItems ;
        protected Array m_items ;
        protected JWindow m_itemWindow ;
        protected Array m_giftableItems ;
        protected Loader m_pic ;
        protected Object m_levelIconPane ;
        protected Dictionary m_panelDict ;

        public  LevelUpDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,int param6 =0,Array param7 =null ,Array param8 =null ,Array param9 =null )
        {
            this.m_level = param6;
            this.m_buyableItems = param7;
            this.m_giftableItems = param8;
            this.m_specialBuyableItems = param9;
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, SoftBoxLayout.TOP));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(textArea);
            if (m_type != TYPE_MODAL)
            {
                _loc_3 = createButtonPanel();
                _loc_1.append(ASwingHelper.verticalStrut(17));
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            String _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ZLoc.t("Dialogs", "levelUp_title", {amount:this.m_level});
            m_acceptTextName = ZLoc.t("Dialogs", "ShareExperience");
            title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 2);
            return _loc_1;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 10, 0));
            }
            return;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JButton _loc_2 =null ;
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            if (this.m_type != (TYPE_OK || TYPE_MODAL || TYPE_SHARECANCEL))
            {
                _loc_2 = ASwingHelper.makeMarketCloseButton();
                _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
                _loc_1.append(_loc_2);
                ASwingHelper.setEasyBorder(_loc_2, 5, 0, 20, 5);
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(20));
            }
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            String _loc_16 =null ;
            Component _loc_19 =null ;
            JPanel _loc_20 =null ;
            JPanel _loc_30 =null ;
            JTextField _loc_31 =null ;
            String _loc_32 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,-5);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,-3);
            ASwingHelper.setEasyBorder(_loc_3, 0, 100);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,-10);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_7 = ZLoc.t("Dialogs","NewLevel",{cityNameGlobal.player.cityName});
            _loc_8 = ZLoc.t("Levels","Level"+String(this.m_level));
            _loc_9 = ASwingHelper.makeTextField(_loc_7,EmbeddedArt.titleFont,18,2925509);
            TextFieldUtil.formatSmallCaps(_loc_9.getTextField(), new TextFormat(EmbeddedArt.titleFont, 22));
            _loc_10 = ASwingHelper.makeTextField(_loc_8,EmbeddedArt.titleFont,18,2925509);
            TextFieldUtil.formatSmallCaps(_loc_10.getTextField(), new TextFormat(EmbeddedArt.titleFont, 22));
            _loc_5.append(_loc_9);
            _loc_6.append(_loc_10);
            _loc_4.appendAll(ASwingHelper.verticalStrut(20), _loc_5, _loc_6, ASwingHelper.verticalStrut(20));
            DisplayObject _loc_11 =(DisplayObject)new m_assetDict.get( "dialog_horizontalRule");
            _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_12.append(new AssetPane(_loc_11));
            _loc_3.append(_loc_4);
            _loc_3.append(_loc_12);
            _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,-20);
            if (this.m_buyableItems.length != 0)
            {
                _loc_30 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_31 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "Unlock"), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                _loc_30.append(_loc_31);
                _loc_13.append(_loc_30);
            }
            _loc_14 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_15 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,5);
            this.m_panelDict = new Dictionary(true);
            this.m_itemWindow =(JWindow) this.parent;
            this.m_items = new Array();
            Array _loc_17 =new Array ();
            int _loc_18 =0;
            for(int i0 = 0; i0 < this.m_specialBuyableItems.size(); i0++)
            {
            	_loc_16 = this.m_specialBuyableItems.get(i0);

                _loc_18++;
                if (_loc_18 > 4)
                {
                    break;
                }
                _loc_17.unshift(this.makeItem(_loc_16, true));
            }
            for(int i0 = 0; i0 < this.m_buyableItems.size(); i0++)
            {
            	_loc_16 = this.m_buyableItems.get(i0);

                _loc_18++;
                if (_loc_18 > 4)
                {
                    break;
                }
                _loc_17.unshift(this.makeItem(_loc_16, false));
            }
            for(int i0 = 0; i0 < _loc_17.size(); i0++)
            {
            	_loc_20 = _loc_17.get(i0);

                _loc_20.setPreferredHeight(131);
                _loc_20.setMinimumHeight(131);
                _loc_20.setMaximumHeight(131);
                ASwingHelper.prepare(_loc_20);
                _loc_15.append(_loc_20);
            }
            if (this.m_buyableItems.length == 0)
            {
                _loc_15.append(ASwingHelper.verticalStrut(131));
            }
            _loc_14.append(_loc_15);
            _loc_13.append(_loc_14);
            _loc_3.append(_loc_13);
            TextFormat _loc_21 =new TextFormat ();
            _loc_21.align = TextFormatAlign.CENTER;
            _loc_2.append(_loc_3);
            _loc_22 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_23 = Global.gameSettings().getLevelXML(this.m_level);
            _loc_24 = Global.gameSettings().getLevelXML(this.m_level).@icon;
            _loc_25 = Global.getAssetURL(_loc_24);
            if (_loc_24)
            {
                this.m_pic = LoadingManager.load(_loc_25, this.fillLevelAsset, LoadingManager.PRIORITY_LOW);
            }
            _loc_26 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,30);
            _loc_22.setPreferredHeight(99);
            this.m_levelIconPane = new AssetPane();
            ASwingHelper.setEasyBorder(_loc_26, 30);
            _loc_27 = _loc_23.@message;
            _loc_28 = _loc_23.@message.split(":");
            if (_loc_23.@message.split(":").length > 0)
            {
                _loc_32 = ZLoc.t(_loc_28.get(0), _loc_28.get(1));
            }
            else
            {
                _loc_32 = "You grew your city!";
            }
            _loc_29 = ASwingHelper.makeMultilineText(_loc_32,300,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,TextFieldUtil.getLocaleFontSize(20,20,.get({localesize"ja",16)}),9463372);
            _loc_26.appendAll(this.m_levelIconPane, _loc_29);
            _loc_22.append(_loc_26);
            _loc_2.appendAll(_loc_22);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected Component  makeItem (String param1 ,boolean param2 )
        {
            DisplayObject itemBG ;
            AssetPane unlockText ;
            str = param1;
            special = param2;
            AssetPane ap =new AssetPane ();
            ap.name = str;
            this.m_items.push(ap);
            itemBG =(DisplayObject) new m_assetDict.get("dialog_item");
            DisplayObject specialBG =(DisplayObject)new m_assetDict.get( "dialog_item_special");
            verticalPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            iconPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setSizedBackground(iconPanel, itemBG);
            iconPanel.setPreferredSize(new IntDimension(itemBG.width, itemBG.height));
            iconPanel.setMinimumSize(new IntDimension(itemBG.width, itemBG.height));
            iconPanel.setMaximumSize(new IntDimension(itemBG.width, itemBG.height));
            iconPanel.append(ap);
            specialPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            centeringPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (special)
            {
                ASwingHelper.setSizedBackground(specialPanel, specialBG);
                unlockText = ASwingHelper.makeMultilineText("SPECIAL UNLOCK", specialBG.width - 5, EmbeddedArt.defaultFontNameBold, "center", 12, 16777215);
                ASwingHelper.prepare(unlockText);
                specialPanel.append(unlockText);
            }
            centeringPanel.append(iconPanel);
            specialPanel.append(centeringPanel);
            if (!special)
            {
                specialPanel.append(ASwingHelper.verticalStrut(7));
            }
            verticalPanel.append(specialPanel);
            iconString = Global.gameSettings().getImageByName(str,"icon");
            icon = LoadingManager.load(iconString,function(eventEvent)
            {
                _loc_2 = undefined;
                _loc_4 = undefined;
                _loc_5 = undefined;
                _loc_6 = undefined;
                _loc_7 = undefined;
                _loc_3 = (String)(m_panelDict.get(event.currentTarget.loader) );
                for(int i0 = 0; i0 < m_items.size(); i0++)
                {
                	_loc_4 = m_items.get(i0);

                    if (_loc_4.name == _loc_3)
                    {
                        _loc_2 = _loc_4;
                    }
                }
                _loc_5 = event.currentTarget.content;
                _loc_6 = (itemBG.height - _loc_5.height) / 2;
                _loc_7 = (itemBG.width - _loc_5.width) / 2;
                ASwingHelper.setEasyBorder(_loc_2, _loc_6, _loc_7);
                _loc_2.setAsset(_loc_5);
                ASwingHelper.prepare(m_itemWindow);
                return;
            }//end
            );
            this.m_panelDict.put(icon,  str);
            return verticalPanel;
        }//end

        private void  fillLevelAsset (Event event )
        {
            _loc_2 = this.m_pic.content ;
            this.m_levelIconPane.setAsset(_loc_2);
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES, true));
            Global.world.viralMgr.sendLevelUpFeed(Global.player, this.m_level);
            if (m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_closeCallback);
            }
            closeMe();
            return;
        }//end

    }




