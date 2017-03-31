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
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class SpecialsCongratsDialogView extends GenericDialogView
    {
        private JPanel m_iconPane ;
        private Component m_textComponent ;
        private Item m_specialsItem ;
        private DisplayObject m_burstBg ;

        public  SpecialsCongratsDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true ,Item param12 =null )
        {
            this.m_specialsItem = param12;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            StatsManager.count("bogo", "congrats_dialog", "close");
            closeMe();
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            UI.displayInventory(this.m_specialsItem.name);
            StatsManager.count("bogo", "congrats_dialog", "click");
            close();
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(textArea);
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = createButtonPanel();
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createIconPane ()
        {
            String _loc_9 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = m_assetDict.get("extra_dialog_bg") ;
            MarginBackground _loc_3 =new MarginBackground(_loc_2 ,new Insets(0,0,0,0));
            _loc_1.setBackgroundDecorator(_loc_3);
            _loc_1.setPreferredSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_1.setMinimumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_1.setMaximumSize(new IntDimension(_loc_2.width, _loc_2.height));
            this.m_burstBg = m_assetDict.get("reward_burst");
            MarginBackground _loc_4 =new MarginBackground(this.m_burstBg ,new Insets(0,10,0,0));
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_5.setBackgroundDecorator(_loc_4);
            _loc_5.setPreferredSize(new IntDimension(this.m_burstBg.width, this.m_burstBg.height));
            _loc_5.setMinimumSize(new IntDimension(this.m_burstBg.width, this.m_burstBg.height));
            _loc_5.setMaximumSize(new IntDimension(this.m_burstBg.width, this.m_burstBg.height));
            _loc_5.append(this.iconLoaderPane(this.m_specialsItem.iconRelative));
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6.appendAll(ASwingHelper.horizontalStrut(180), _loc_5);
            _loc_7 = Specials.getInstance().getSpecialDataByName(this.m_specialsItem.name);
            Object _loc_8 ={};
            _loc_8.put("n",  _loc_7.get("m"));
            _loc_8.put("itemName",  ZLoc.t("Items", this.m_specialsItem.name + "_friendlyName"));
            if (_loc_8.get("n") > 1)
            {
                _loc_9 = "specials_bogo_plural_body";
            }
            else
            {
                _loc_9 = "specials_bogo_body";
            }
            _loc_10 = ZLoc.t("Dialogs",_loc_9,_loc_8);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_12 = ASwingHelper.makeMultilineText(_loc_10,250,EmbeddedArt.defaultFontNameBold,m_align,18,EmbeddedArt.brownTextColor);
            _loc_11.append(_loc_12);
            _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_13.appendAll(ASwingHelper.horizontalStrut(170), _loc_11);
            _loc_14 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_14.appendAll(ASwingHelper.verticalStrut(25), _loc_6, ASwingHelper.verticalStrut(10), _loc_13);
            _loc_1.appendAll(_loc_14);
            return _loc_1;
        }//end

        protected JPanel  iconLoaderPane (String param1 ,DisplayObject param2 )
        {
            AssetPane iconPane ;
            Loader iconLoader ;
            iconRelativePath = param1;
            bkgAsset = param2;
            iconPane = new AssetPane();
            iconString = Global.getAssetURL(iconRelativePath);
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param11);
                param11.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , iconInnerPane));
            iconPane.setBorder(new EmptyBorder(null, new Insets(0, 35, 0, 0)));
            iconInnerPane.append(iconPane);
            return iconInnerPane;
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

         protected JPanel  createTextArea ()
        {
            m_messagePaddingLeft = 0;
            m_messagePaddingRight = 0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            _loc_2 = this.createIconPane();
            _loc_1.append(ASwingHelper.horizontalStrut(5));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(5));
            return _loc_1;
        }//end

    }




