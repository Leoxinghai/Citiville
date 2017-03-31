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
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.GridlistUI.model.*;
import Display.aswingui.*;
import Display.aswingui.buttonui.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.ui.*;
import Modules.mechanics.ui.items.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class SlottedContainerCrewableCellView extends SlottedContainerCellView
    {
        private JPanel m_footAlignPanel ;
        private JButton m_askButton ;
        private String m_askText ;
        private JButton m_buyButton ;
        private String m_buyText ;

        public  SlottedContainerCrewableCellView (IMechanicUser param1 ,SlottedContainerConfig param2 ,int param3 ,boolean param4 =true )
        {
            super(param1, param2, param3, 0, param4);
            _loc_5 = m_config.getButtonZlocItem("ask");
            this.m_askText = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_5.pkg, _loc_5.key));
            _loc_6 = m_config.slotGate;
            _loc_7 = param1(as MechanicMapResource ).getItemName ();
            this.m_buyText = TextFieldUtil.formatSmallCapsString(String(CrewGate.getCrewCost(_loc_7, _loc_6.name)));
            this.m_footAlignPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_askButton = new CustomButton("", null, "CoinsButtonUI");
            this.m_buyButton = new CustomButton("", null, "CashButtonUI");
            return;
        }//end

        protected DisplayObject  cashButtonAsset ()
        {
            return m_assetDict.get("iconCash");
        }//end

         public void  makeAssets (Dictionary param1 )
        {
            super.makeAssets(param1);
            _loc_2 = param1.get(DelayedAssetLoader.SLOTTED_CONTAINER_ASSETS) ;
            m_assetDict.put("bg", (DisplayObject) new _loc_2.get("mall_card_staff"));
            _loc_3 = param1.get(DelayedAssetLoader.MARKET_ASSETS) ;
            m_assetDict.put("iconCash", (DisplayObject) new _loc_3.get("cash"));
            return;
        }//end

         public void  updateView (Object param1)
        {
            this.m_askButton.setText(this.m_askText);
            this.m_askButton.setUI(new CoinsButtonUI());
            this.m_askButton.setUIClassID("CoinsButtonUI");
            _loc_2 = this.m_askButton.getFont ();
            this.m_askButton.setFont(_loc_2.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_2.getSize(), 1, [{locale:"de", ratio:0.8}, {locale:"id", ratio:0.75}, {locale:"es", ratio:0.9}, {locale:"fr", ratio:0.8}, {locale:"it", ratio:0.8}, {locale:"pt", ratio:0.9}, {locale:"tr", ratio:0.8}])));
            this.m_buyButton.setText(this.m_buyText);
            this.m_buyButton.setUI(new CashButtonUI());
            this.m_buyButton.setUIClassID("CashButtonUI");
            this.m_buyButton.setIcon(new AssetIcon(this.cashButtonAsset));
            this.m_buyButton.addEventListener(MouseEvent.CLICK, this.onBuyButtonClick, false, 0, true);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  initLayout ()
        {
            super.initLayout();
            ASwingHelper.setEasyBorder(this.m_askButton, 3, 0, 0, 0);
            ASwingHelper.setEasyBorder(this.m_buyButton, 1, 0, 0, 0);
            if (m_footPanel)
            {
                this.m_footAlignPanel.append(this.m_askButton);
                this.m_footAlignPanel.append(this.m_buyButton);
                m_footPanel.append(this.m_footAlignPanel);
            }
            ASwingHelper.setEasyBorder(m_panel, 0, 13, 0);
            return;
        }//end

         protected DisplayObject  createContentPlaceholder ()
        {
            return new Sprite();
        }//end

         protected void  onClick (MouseEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            this.dispatchEvent(new ViewActionEvent("onAskCrew"));
            return;
        }//end

        protected void  onBuyButtonClick (MouseEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            this.dispatchEvent(new ViewActionEvent("onBuyCrew"));
            return;
        }//end

         protected void  onRollOver (MouseEvent event =null )
        {
            this.dispatchEvent(new ViewActionEvent("onDisplayCrewableCellTooltip"));
            return;
        }//end

         protected void  onRollOut (MouseEvent event =null )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

    }



