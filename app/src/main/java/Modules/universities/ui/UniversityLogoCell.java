package Modules.universities.ui;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;

    public class UniversityLogoCell extends DataItemCell
    {
        private JPanel m_itemIconPane ;
        private AssetPane m_iconPane ;
        private MechanicMapResource m_spawner ;
        private CustomButton m_costButton ;

        public  UniversityLogoCell (MechanicMapResource param1 )
        {
            this.m_spawner = param1;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 2, SoftBoxLayout.CENTER));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            m_item = param1;
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            this.buildCell();
            return;
        }//end

         protected void  initializeCell ()
        {
            this.m_iconPane.setAsset(m_itemIcon);
            this.m_iconPane.mouseEnabled = false;
            this.m_iconPane.mouseChildren = false;
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  updateCell ()
        {
            this.remove(this.m_costButton);
            this.m_costButton = new CustomButton(ZLoc.t("Dialogs", "Use"), null, "GreenButtonUI");
            this.m_costButton.setMargin(new Insets(0, 5, 0, 5));
            this.m_costButton.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            this.addEventListener(MouseEvent.CLICK, this.useLogo, false, 0, true);
            this.append(this.m_costButton);
            return;
        }//end

        private void  buildCell ()
        {
            AssetIcon _loc_2 =null ;
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "card_available_unselected");
            this.m_itemIconPane = new JPanel(new CenterLayout());
            ASwingHelper.setSizedBackground(this.m_itemIconPane, _loc_1);
            this.setPreferredWidth(_loc_1.width);
            this.m_iconPane = new AssetPane();
            this.m_itemIconPane.append(this.m_iconPane);
            int _loc_3 =14;
            if (Global.player.checkSubLicense(this.m_spawner.getItem().name, m_item.name) || m_item.name == "logo_CVU")
            {
                this.m_costButton = new CustomButton(ZLoc.t("Dialogs", "Use"), null, "GreenButtonUI");
                _loc_3 = ASwingHelper.shrinkFontSizeToFit(_loc_1.width, ZLoc.t("Dialogs", "Use"), EmbeddedArt.titleFont, 14, EmbeddedArt.commonBlackFilter, null, null, 8);
                this.addEventListener(MouseEvent.CLICK, this.useLogo, false, 0, true);
                this.m_costButton.setMargin(new Insets(0, 0, 0, 0));
            }
            else if (m_item.unlockCostCoin > 0)
            {
                _loc_2 = new AssetIcon(new EmbeddedArt.icon_coin());
                this.m_costButton = new CustomButton(String(m_item.unlockCostCoin), _loc_2, "GreenButtonUI");
                this.addEventListener(MouseEvent.CLICK, this.buyLogo, false, 0, true);
            }
            else if (m_item.unlockCostCash > 0)
            {
                _loc_2 = new AssetIcon(new EmbeddedArt.icon_cash());
                this.m_costButton = new CustomButton(String(m_item.unlockCostCash), _loc_2, "CashButtonUI");
                this.m_costButton.setMargin(new Insets(-5, 5, -2, 5));
                this.addEventListener(MouseEvent.CLICK, this.buyLogo, false, 0, true);
            }
            this.m_costButton.setFont(new ASFont(EmbeddedArt.titleFont, _loc_3, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_costButton.setPreferredHeight(22);
            this.appendAll(this.m_itemIconPane, this.m_costButton);
            return;
        }//end

        private boolean  canAfford ()
        {
            if (m_item.unlockCostCash > 0)
            {
                return Global.player.cash >= m_item.unlockCostCash;
            }
            if (m_item.unlockCostCoin > 0)
            {
                return Global.player.gold >= m_item.unlockCostCoin;
            }
            return false;
        }//end

        private void  doPurchase ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            this.removeEventListener(MouseEvent.CLICK, this.buyLogo);
            Global.player.addSubLicense(this.m_spawner.getItem().name, m_item.name);
            if (m_item.unlockCostCash > 0)
            {
                _loc_2 = Global.player.cash - m_item.unlockCostCash;
                if (_loc_2 < 0)
                {
                    _loc_2 = 0;
                }
                Global.player.cash = _loc_2;
            }
            else if (m_item.unlockCostCoin > 0)
            {
                _loc_3 = Global.player.gold - m_item.unlockCostCoin;
                if (_loc_3 < 0)
                {
                    _loc_3 = 0;
                }
                Global.player.gold = _loc_3;
            }
            this.updateCell();
            GameTransactionManager.addTransaction(new TAcquireSubLicense(this.m_spawner.getItem().name, m_item.name));
            _loc_1 = (SubLicensedPropertyMechanic)MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"universityLogo",MechanicManager.ALL)
            _loc_1.setProperty(m_item.name);
            return;
        }//end

        private void  buyLogo (MouseEvent event )
        {
            String _loc_2 =null ;
            if (this.canAfford())
            {
                this.doPurchase();
            }
            else
            {
                _loc_2 = m_item.unlockCostCoin > 0 ? (ImpulseBuy.TYPE_MARKET_COINS) : (ImpulseBuy.TYPE_MARKET_CASH);
                UI.displayImpulseBuyPopup(_loc_2, null, m_item);
            }
            dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, m_item, new Point(0, 0), true));
            return;
        }//end

        private void  useLogo (MouseEvent event )
        {
            dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, m_item, null, true));
            _loc_2 = (SubLicensedPropertyMechanic)MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"universityLogo",MechanicManager.ALL)
            _loc_2.setProperty(m_item.name);
            return;
        }//end

    }



