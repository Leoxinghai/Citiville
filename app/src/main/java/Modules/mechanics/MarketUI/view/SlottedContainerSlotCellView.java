package Modules.mechanics.MarketUI.view;

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
import Display.*;
import Display.GridlistUI.model.*;
import Display.aswingui.*;
import GameMode.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.ui.*;
import Modules.mechanics.ui.items.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class SlottedContainerSlotCellView extends SlottedContainerCellView
    {
        protected AssetPane m_decoPane ;
        protected JPanel m_rightPanel ;
        protected JPanel m_removePane ;
        protected boolean m_showTooltip =false ;
        protected MapResource m_mapResource ;
        protected boolean m_inFillMode =false ;
        protected String m_buttonText ;
        protected JButton m_removeButton ;
        protected CustomButton m_upgradeButton ;
        protected CustomButton m_fillButton ;

        public  SlottedContainerSlotCellView (IMechanicUser param1 ,SlottedContainerConfig param2 ,int param3 ,int param4 ,MapResource param5 =null ,boolean param6 =true )
        {
            super(param1, param2, param3, param4, param6);
            this.m_decoPane = new AssetPane();
            this.m_rightPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_mapResource = param5;
            return;
        }//end

        protected boolean  isSlotEmpty ()
        {
            return this.m_mapResource == null;
        }//end

        protected DisplayObject  panelBackground ()
        {
            return m_assetDict.get("bg_footer");
        }//end

         public void  makeAssets (Dictionary param1 )
        {
            DisplayObject _loc_3 =null ;
            super.makeAssets(param1);
            _loc_2 = param1.get(DelayedAssetLoader.SLOTTED_CONTAINER_ASSETS) ;
            m_assetDict.put("bg", (DisplayObject) new _loc_2.get("mall_card_active"));
            m_assetDict.put("bg_footer", (DisplayObject) new _loc_2.get("mall_card_footer"));
            if (!this.isSlotEmpty())
            {
                _loc_3 = param1.get(DelayedAssetLoader.INVENTORY_ASSETS);
                m_assetDict.put("btn_close_small_up", (DisplayObject) new _loc_3.get("btn_close_small_up"));
                m_assetDict.put("btn_close_small_over", (DisplayObject) new _loc_3.get("btn_close_small_over"));
                m_assetDict.put("btn_close_small_down", (DisplayObject) new _loc_3.get("btn_close_small_down"));
            }
            return;
        }//end

         protected void  initLayout ()
        {
            JPanel _loc_2 =null ;
            super.initLayout();
            _loc_1 = this.isSlotEmpty ();
            if (!_loc_1)
            {
                _loc_2 = this.makeRemoveButtonPanel();
                this.m_rightPanel.append(_loc_2);
                this.append(ASwingHelper.horizontalStrut(-49));
                this.append(this.m_rightPanel);
            }
            if (m_footPanel)
            {
                if (!_loc_1)
                {
                    this.initFilledCellLayout();
                }
                else
                {
                    this.initEmptyCellLayout();
                }
            }
            ASwingHelper.setEasyBorder(m_panel, 0, 13, 0);
            return;
        }//end

         public void  updateView (Object param1)
        {
            if (this.m_upgradeButton)
            {
                m_bodyPanelInsets.left = 3;
                updateBodyPanel();
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  initFilledCellLayout ()
        {
            ZlocItem _loc_1 =null ;
            Component _loc_2 =null ;
            if (m_footPanel == null)
            {
                return;
            }
            this.updateDecoPane();
            if (this.m_mapResource && this.m_mapResource.isUpgradePossible())
            {
                _loc_1 = m_config.getButtonZlocItem("upgrade");
                this.m_buttonText = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_1.pkg, _loc_1.key));
                this.m_upgradeButton = new CustomButton(this.m_buttonText, null, "CoinsButtonUI");
                this.m_upgradeButton.setPreferredWidth(m_bodyPanel.getWidth() + 6);
                this.m_upgradeButton.addEventListener(MouseEvent.CLICK, this.onUpgradeActionClick, false, 0, true);
                m_footPanel.append(this.m_upgradeButton);
                ASwingHelper.setEasyBorder(this.m_upgradeButton, 3, 1, 1, 2);
            }
            else
            {
                _loc_2 = this.m_decoPane;
                m_footPanel.append(this.m_decoPane);
                ASwingHelper.setEasyBorder(this.m_decoPane, 3, -2, -2, 2);
            }
            return;
        }//end

        protected void  initEmptyCellLayout ()
        {
            if (m_footPanel == null)
            {
                return;
            }
            _loc_1 = m_config.getButtonZlocItem("fill");
            this.m_buttonText = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_1.pkg, _loc_1.key));
            this.m_fillButton = new CustomButton(this.m_buttonText, null, "CoinsButtonUI");
            this.m_fillButton.setPreferredWidth(m_bodyPanel.getWidth());
            this.m_fillButton.addEventListener(MouseEvent.CLICK, this.onFillActionClick, false, 0, true);
            _loc_2 = this.m_fillButton.getFont ();
            this.m_fillButton.setFont(_loc_2.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_2.getSize(), 1, [{locale:"de", ratio:0.8}, {locale:"id", ratio:0.8}, {locale:"es", ratio:0.9}, {locale:"fr", ratio:0.8}, {locale:"it", ratio:0.8}, {locale:"pt", ratio:0.8}, {locale:"tr", ratio:0.8}, {locale:"ja", ratio:0.9}])));
            this.m_fillButton.setPreferredWidth(m_bodyPanel.getWidth());
            m_footPanel.append(this.m_fillButton);
            ASwingHelper.setEasyBorder(this.m_fillButton, 3, 1, 1, 2);
            return;
        }//end

         protected DisplayObject  createContentPlaceholder ()
        {
            DisplayObject _loc_1 =null ;
            if (!this.isSlotEmpty())
            {
                _loc_1 = super.createContentPlaceholder();
            }
            else
            {
                _loc_1 = new Sprite();
            }
            return _loc_1;
        }//end

        protected void  updateDecoPane ()
        {
            _loc_1 = this.panelBackground ;
            if (_loc_1)
            {
                _loc_1.alpha = 0.75;
                this.m_decoPane.setAsset(_loc_1);
            }
            return;
        }//end

        protected JPanel  makeRemoveButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.TOP );
            _loc_2 = m_assetDict.get("btn_close_small_up") ;
            _loc_3 = m_assetDict.get("btn_close_small_over") ;
            _loc_4 = m_assetDict.get("btn_close_small_down") ;
            this.m_removePane = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.RIGHT);
            this.m_removeButton = new JButton();
            this.m_removeButton.wrapSimpleButton(new SimpleButton(_loc_2, _loc_3, _loc_4, _loc_2));
            this.m_removeButton.addEventListener(MouseEvent.CLICK, this.onRemoveClick, false, 0, true);
            this.m_removeButton.addEventListener(MouseEvent.MOUSE_OVER, this.onRemoveOver, false, 0, true);
            this.m_removeButton.addEventListener(MouseEvent.MOUSE_OUT, this.onRemoveOut, false, 0, true);
            this.m_removePane.append(this.m_removeButton);
            this.m_removePane.append(ASwingHelper.horizontalStrut(27));
            ASwingHelper.prepare(this.m_removePane);
            _loc_1.append(this.m_removePane);
            ASwingHelper.prepare(_loc_1);
            this.m_rightPanel.setMaximumHeight(_loc_3.height);
            return _loc_1;
        }//end

        public void  disableCell ()
        {
            this.filters = .get(ASwingHelper.makeDisabledFilter());
            this.m_removeButton.setEnabled(false);
            return;
        }//end

        public void  enableCell ()
        {
            this.filters = new Array();
            this.m_removeButton.setEnabled(true);
            return;
        }//end

        protected void  onRemoveClick (MouseEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            this.disableCell();
            this.dispatchEvent(new ViewActionEvent("onRemove"));
            return;
        }//end

        protected void  onRemoveOver (MouseEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_showTooltip)
            {
                dispatchEvent(new Event("turnOffToolTip", true));
            }
            return;
        }//end

        protected void  onRemoveOut (MouseEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_showTooltip)
            {
                if (!this.isSlotEmpty())
                {
                    this.displayFilledCellTooltip();
                }
                else
                {
                    this.displayEmptyCellTooltip();
                }
            }
            return;
        }//end

        protected void  onFillActionClick (MouseEvent event =null )
        {
            ZlocItem _loc_2 =null ;
            ZlocItem _loc_3 =null ;
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (!(Global.world.getTopGameMode() instanceof GMMechanicStore))
            {
                this.dispatchEvent(new ViewActionEvent("onEnterStoreMode"));
                _loc_2 = m_config.getButtonZlocItem("cancel");
                this.m_buttonText = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_2.pkg, _loc_2.key));
                this.m_fillButton.setText(this.m_buttonText);
                this.m_inFillMode = true;
            }
            else if (this.m_inFillMode)
            {
                this.dispatchEvent(new ViewActionEvent("onExitStoreMode"));
                _loc_3 = m_config.getButtonZlocItem("fill");
                this.m_buttonText = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_3.pkg, _loc_3.key));
                this.m_fillButton.setText(this.m_buttonText);
                this.m_inFillMode = false;
            }
            return;
        }//end

        protected void  onUpgradeActionClick (MouseEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            this.dispatchEvent(new ViewActionEvent("onUpgrade"));
            return;
        }//end

         protected void  onRollOver (MouseEvent event =null )
        {
            this.m_showTooltip = true;
            if (!this.isSlotEmpty())
            {
                this.displayFilledCellTooltip();
            }
            else
            {
                this.displayEmptyCellTooltip();
            }
            return;
        }//end

        private void  displayFilledCellTooltip ()
        {
            this.dispatchEvent(new ViewActionEvent("onDisplayFilledCellTooltip"));
            return;
        }//end

        private void  displayEmptyCellTooltip ()
        {
            this.dispatchEvent(new ViewActionEvent("onDisplayEmptyCellTooltip"));
            return;
        }//end

         protected void  onRollOut (MouseEvent event =null )
        {
            this.m_showTooltip = false;
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

    }



