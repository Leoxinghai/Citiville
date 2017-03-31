package Display.hud;

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
import Classes.sim.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.remodel.*;
import Modules.socialinventory.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.event.*;
import com.xinghai.Debug;

    public class ExpandedMainMenu extends ExpandedMenu
    {
        protected JButton m_autoButton ;
        protected JButton m_moveButton ;
        protected JButton m_rotateButton ;
        protected JButton m_removeButton ;
        protected JButton m_storeButton ;
        protected JButton m_heartsButton ;
        protected JButton m_remodelButton ;
        protected JButton m_cursorButton ;
        protected JPanel m_leftIconPanel ;
        protected JPanel m_rightIconPanel ;
        protected boolean m_isDoubleWide =false ;
        protected Array m_buttons ;
        protected Array m_buttonsInRightColumn ;
        public static  int SINGLE_COLUMN_THRESHOLD =5;
        public static  int MINIMUM_COLUMN_ROW_COUNT =4;

        public  ExpandedMainMenu (Object param1 ,boolean param2 =false )
        {
            super(param1, param2);
            return;
        }//end

         protected void  makeContent ()
        {
            int _loc_7 =0;
            Array _loc_8 =null ;
            Array _loc_9 =null ;

            this.m_cursorButton = new JButton();
            attachButtonHandlers(this.m_cursorButton, this.onCursorMenuClick);
            this.makeButton(EmbeddedArt.hud_act_cursor, this.m_cursorButton, new JLabel(ZLoc.t("Main", "Tools")));
            this.m_removeButton = new JButton();
            attachButtonHandlers(this.m_removeButton, this.onRemoveMenuClick);
            this.makeButton(EmbeddedArt.hud_actMenu_remove, this.m_removeButton, new JLabel(ZLoc.t("Main", "Remove")));
            this.m_rotateButton = new JButton();
            attachButtonHandlers(this.m_rotateButton, this.onRotateMenuClick);
            this.makeButton(EmbeddedArt.hud_actMenu_rotate, this.m_rotateButton, new JLabel(ZLoc.t("Main", "Rotate")));
            this.m_moveButton = new JButton();
            attachButtonHandlers(this.m_moveButton, this.onMoveMenuClick);
            this.makeButton(EmbeddedArt.hud_actMenu_move, this.m_moveButton, new JLabel(ZLoc.t("Main", "Move")));
            if (ActionAutomationManager.instance.isEligibleForFeature)
            {
                this.m_autoButton = new JButton();
                attachButtonHandlers(this.m_autoButton, this.onAutoMenuClick);
                this.makeButton(EmbeddedArt.hud_actMenu_auto, this.m_autoButton, new JLabel(ZLoc.t("Main", "Auto")));
            }
            if (Global.player.storageComponent.getAllStorageUnits().length > 0)
            {
                this.m_storeButton = new JButton();
                attachButtonHandlers(this.m_storeButton, this.onStoreMenuClick);
                this.makeButton(EmbeddedArt.hud_actMenu_store, this.m_storeButton, new JLabel(ZLoc.t("Main", "Store")));
            }
            if (SocialInventoryManager.isFeatureAvailable())
            {
                this.m_heartsButton = new JButton();
                attachButtonHandlers(this.m_heartsButton, this.onHeartsButtonClick);
                this.makeButton(EmbeddedArt.hud_actMenu_hearts, this.m_heartsButton, new JLabel(ZLoc.t("Main", "Hearts")));
            }
            if (RemodelManager.isFeatureAvailable())
            {
                this.m_remodelButton = new JButton();
                this.makeButton(EmbeddedArt.hud_actMenu_remodel, this.m_remodelButton, new JLabel(ZLoc.t("Main", "Remodel")));
                attachButtonHandlers(this.m_remodelButton, this.onRemodelButtonClick);
            }
            this.m_leftIconPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_rightIconPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            int _loc_1 =0;
            if (this.m_buttons.length > SINGLE_COLUMN_THRESHOLD)
            {
                this.m_isDoubleWide = true;
                _loc_7 = this.m_buttons.length / 2 > MINIMUM_COLUMN_ROW_COUNT ? (Math.ceil(this.m_buttons.length / 2)) : (MINIMUM_COLUMN_ROW_COUNT);
                _loc_8 = this.m_buttons.slice(0, _loc_7);
                _loc_9 = this.m_buttons.slice(_loc_7, this.m_buttons.length());
                _loc_1 = _loc_8.length - 1;
                while (_loc_1 >= 0)
                {

                    this.m_leftIconPanel.append(_loc_8.get(_loc_1));
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1 = _loc_9.length - 1;
                while (_loc_1 >= 0)
                {

                    this.m_rightIconPanel.append(_loc_9.get(_loc_1));
                    _loc_1 = _loc_1 - 1;
                }
                m_contentCount = _loc_7;
                m_bg =(DisplayObject) new EmbeddedArt.hud_act_doublewide_bg();
            }
            else
            {
                _loc_1 = this.m_buttons.length - 1;
                while (_loc_1 >= 0)
                {

                    this.m_leftIconPanel.append(this.m_buttons.get(_loc_1));
                    _loc_1 = _loc_1 - 1;
                }
                m_contentCount = this.m_buttons.length;
                m_bg =(DisplayObject) new EmbeddedArt.hud_act_bg();
            }
            _loc_2 = this.m_isDoubleWide? (m_bg.width * 0.5 - 8) : (m_bg.width);
            _loc_3 = this.m_isDoubleWide? (30) : (20);
            _loc_4 = Global.localizer.langCode!= "en" ? (10) : (0);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_5.append(this.m_leftIconPanel);
            this.m_leftIconPanel.setPreferredWidth(_loc_2);
            if (this.m_isDoubleWide)
            {
                _loc_5.append(this.m_rightIconPanel);
                this.m_rightIconPanel.setPreferredWidth(_loc_2);
            }
            m_content = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 0);
            m_content.appendAll(ASwingHelper.verticalStrut(15), _loc_5, ASwingHelper.verticalStrut(_loc_3));
            AssetBackground _loc_6 =new AssetBackground(m_bg );
            m_content.setBackgroundDecorator(_loc_6);
            m_content.setPreferredWidth(m_bg.width);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButton (Class param1 ,JButton param2 ,JLabel param3 =null )
        {


            AssetPane _loc_7 =null ;
            double _loc_11 =0;
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical();
            DisplayObject _loc_5 =(DisplayObject)new param1;
            DisplayObject _loc_6 =(DisplayObject)new param1;
            _loc_8 = param3.getText();
            _loc_6.filters = .get(glow_btn);
            SimpleButton _loc_9 =new SimpleButton(_loc_5 ,_loc_6 ,_loc_5 ,_loc_5 );
            param2.wrapSimpleButton(_loc_9);
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            _loc_10.setPreferredHeight(45);
            _loc_10.append(param2);
            if (param3)
            {
                _loc_11 = TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:9}]);
                _loc_7 = ASwingHelper.makeMultilineText(_loc_8, 65, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, _loc_11, 16777215);
                _loc_4.appendAll(_loc_10, _loc_7);
            }
            else
            {
                _loc_4.appendAll(_loc_10);
            }
            if (!this.m_buttons)
            {
                this.m_buttons = new Array();
            }
            this.m_buttons.push(_loc_4);
            return;
        }//end

        private void  onAutoMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "auto");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.AUTO));
            return;
        }//end

        private void  onMoveMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "move");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.MOVE));
            return;
        }//end

        private void  onRotateMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "rotate");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.ROTATE));
            return;
        }//end

        private void  onRemoveMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "remove");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.REMOVE));
            return;
        }//end

        private void  onStoreMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "store");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.STORE));
            return;
        }//end

        private void  onHeartsButtonClick (AWEvent event =null )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "heart_tool", "select_tool");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.HEARTS));
            return;
        }//end

        private void  onRemodelButtonClick (AWEvent event =null )
        {
            _loc_2 = RemodelManager.isFeatureAvailable();
            _loc_3 = _loc_2? ("") : ("locked");
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "remodel_tool", "select_tool", _loc_3);
            if (RemodelManager.isFeatureAvailable())
            {
                dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.REMODEL));
            }
            else
            {
                RemodelManager.runIntroFlow();
                if (RemodelManager.isFeatureAvailable())
                {
                    dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.REMODEL));
                }
            }
            return;
        }//end

        private void  onCursorMenuClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "tools");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.CURSOR));
            return;
        }//end

         public Vector2  getDesiredPosition (Catalog param1)
        {
            _loc_2 = super.getDesiredPosition(param1);
            if (!this.m_isDoubleWide)
            {
                return _loc_2;
            }
            return new Vector2(_loc_2.x - 30, _loc_2.y);
        }//end

    }



