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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class MysteryCrateDialogView extends GenericDialogView
    {
        private AutoAnimatedBitmap m_animatedSpinner ;
        private String m_animateRelative ;
        private JPanel m_iconPane ;
        private JPanel m_winningPane ;
        private Component m_textComponent ;
        private JPanel m_largeIconPane ;
        private JPanel m_buttonPane ;
        private JPanel m_buttonStrut ;
        private Component m_textContainer ;

        public  MysteryCrateDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            super.makeCenterPanel();
            this.m_buttonPane.visible = false;
            this.m_buttonStrut = ASwingHelper.verticalStrut(35);
            interiorHolder.append(this.m_buttonStrut);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            JPanel _loc_1 =null ;
            double _loc_2 =0;
            _loc_1 = ASwingHelper.makeFlowJPanel(SoftBoxLayout.CENTER, 0);
            this.m_largeIconPane = this.iconLoaderPane("assets/dialogs/citysam_celebrate.png");
            _loc_3 = Catalog.assetDict.get("card_available_unselected");
            DisplayObject _loc_4 =(DisplayObject)new _loc_3;
            double _loc_5 =10;
            m_messagePaddingLeft = 8;
            m_messagePaddingRight = 20;
            if (m_icon != null && m_icon != "")
            {
                _loc_2 = setMessageTextWidth(m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT);
                this.m_animateRelative = "assets/dialogs/mystery_crate_anim70x74.png";
                this.m_iconPane = this.animatedPane(this.m_animateRelative, _loc_4);
            }
            this.m_largeIconPane.visible = false;
            this.m_textContainer = this.createTextComponent(_loc_2);
            _loc_6 = createTextAreaInnerPane(this.m_textContainer);
            _loc_7 = this.m_iconPane;
            _loc_8 = _loc_6;
            _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingLeft));
            _loc_1.append(this.m_largeIconPane);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_5));
            if (m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_7)
                {
                    _loc_1.append(_loc_7);
                }
                _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingRight));
                if (_loc_8)
                {
                    _loc_1.append(_loc_8);
                }
            }
            _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingRight));
            _loc_9 = this.m_textContainer.getHeight();
            return _loc_1;
        }//end

        public void  showWinnings (Item param1 ,boolean param2 )
        {
            TextFormat _loc_6 =null ;
            _loc_3 = Catalog.assetDict.get("card_available_unselected");
            DisplayObject _loc_4 =(DisplayObject)new _loc_3;
            _loc_5 = textArea.getIndex(this.m_iconPane);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            ASwingHelper.setSizedBackground(_loc_7, _loc_4);
            this.m_winningPane = this.iconLoaderPane(param1.iconRelative, _loc_4);
            _loc_7.append(this.m_winningPane);
            textArea.remove(this.m_iconPane);
            textArea.insert(_loc_5, _loc_7);
            _loc_6 = TextField(AssetPane(this.m_textComponent).getAsset()).getTextFormat();
            _loc_8 = param2? (ZLoc.t("Dialogs", "Mystery_Crate_message", {itemName:param1.localizedName})) : (ZLoc.t("Dialogs", "Mystery_Crate_gateFail", {itemName:param1.localizedName}));
            TextField(AssetPane(this.m_textComponent).getAsset()).text = _loc_8;
            TextField(AssetPane(this.m_textComponent).getAsset()).setTextFormat(_loc_6);
            double _loc_9 =95;
            ASwingHelper.prepare(this.m_textContainer);
            this.m_textContainer.setPreferredHeight(_loc_9);
            this.m_textComponent.setPreferredHeight(_loc_9);
            ASwingHelper.prepare(this.m_textContainer);
            ASwingHelper.prepare(textArea);
            this.m_largeIconPane.visible = true;
            this.m_buttonPane.visible = param2;
            this.m_buttonStrut.visible = !param2;
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  animatedPane (String param1 ,DisplayObject param2 )
        {
            AssetPane iconPane ;
            JPanel iconInnerPane ;
            String iconString ;
            Loader iconLoader ;
            double offy ;
            double offx ;
            iconRelativePath = param1;
            bkgAsset = param2;
            iconPane = new AssetPane();
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            if (!Catalog.assetDict.get(iconRelativePath))
            {
                iconString = Global.getAssetURL(iconRelativePath);
                iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                _loc_3 = undefined;
                _loc_4 = undefined;
                _loc_5 = undefined;
                if (iconLoader.content instanceof Bitmap)
                {
                    _loc_3 =(Bitmap) iconLoader.content;
                    m_animatedSpinner = new AutoAnimatedBitmap(_loc_3.bitmapData, 2, 70, 74, 12);
                    Catalog.assetDict.put(iconRelativePath,  _loc_3);
                }
                iconPane.setAsset(m_animatedSpinner);
                ASwingHelper.prepare(param11);
                if (bkgAsset)
                {
                    _loc_4 = Math.abs(bkgAsset.height - iconLoader.content.height) / 2;
                    _loc_5 = Math.abs(bkgAsset.width - iconLoader.content.width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, _loc_4, _loc_5, _loc_4, _loc_5);
                }
                Catalog.assetDict.put(iconRelativePath,  iconLoader.content);
                param11.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , iconInnerPane));
            }
            else
            {
                this.m_animatedSpinner = new AutoAnimatedBitmap(Catalog.assetDict.get(iconRelativePath).bitmapData, 2, 70, 74, 12);
                iconPane.setAsset(this.m_animatedSpinner);
                ASwingHelper.prepare(this.m_animatedSpinner);
                if (bkgAsset)
                {
                    offy = Math.abs(bkgAsset.height - Catalog.assetDict.get(iconRelativePath).height) / 2;
                    offx = Math.abs(bkgAsset.width - Catalog.assetDict.get(iconRelativePath).width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, offy, offx, offy, offx);
                }
            }
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

        protected JPanel  iconLoaderPane (String param1 ,DisplayObject param2 )
        {
            AssetPane iconPane ;
            JPanel iconInnerPane ;
            Loader iconLoader ;
            iconRelativePath = param1;
            bkgAsset = param2;
            iconPane = new AssetPane();
            iconString = Global.getAssetURL(iconRelativePath);
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                _loc_3 = undefined;
                _loc_4 = undefined;
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param11);
                if (bkgAsset)
                {
                    _loc_3 = Math.abs(bkgAsset.height - iconLoader.content.height) / 2;
                    _loc_4 = Math.abs(bkgAsset.width - iconLoader.content.width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, _loc_3, _loc_4, _loc_3, _loc_4);
                }
                param11.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , iconInnerPane));
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(3));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                this.m_buttonPane = createButtonPanel();
                _loc_1.append(this.m_buttonPane);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_4 =0;
            Container _loc_5 =null ;
            m_align = TextFormatAlign.LEFT;
            this.m_textComponent = ASwingHelper.makeMultilineText(m_message, param1, EmbeddedArt.defaultFontNameBold, m_align, 18, EmbeddedArt.blueTextColor);
            double _loc_3 =75;
            if (this.m_textComponent.getHeight() < _loc_3)
            {
                _loc_4 = (_loc_3 - this.m_textComponent.getHeight()) / 2;
                _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_5.append(ASwingHelper.verticalStrut(_loc_4));
                _loc_5.append(this.m_textComponent);
                _loc_5.append(ASwingHelper.verticalStrut(_loc_4));
                _loc_2 = _loc_5;
            }
            else
            {
                _loc_2 = this.m_textComponent;
            }
            return _loc_2;
        }//end

    }




