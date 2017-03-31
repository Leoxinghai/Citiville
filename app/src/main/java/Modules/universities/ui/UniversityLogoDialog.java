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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class UniversityLogoDialog extends FlyFishDialog
    {
        private JButton m_rightBtn ;
        private JButton m_leftBtn ;
        private MechanicMapResource m_spawner ;
        private int m_currentPage =0;
        private int m_totalPages ;
        private String m_currentLogo ;
        private SubLicensedPropertyMechanic m_mechanic ;
        private JPanel m_whitePanel ;
        private JPanel m_itemListHolder ;
        private Dictionary m_logoPages ;
        private Array m_logoData ;
        private JPanel m_toolTipPanel ;
        private AssetPane m_imageAP ;
        private static  int NUM_LOGOS =18;
        private static int OFFSETY =150;
        private static int OFFSETX =14;

        public  UniversityLogoDialog (MechanicMapResource param1 )
        {
            this.m_logoPages = new Dictionary(false);
            this.m_logoData = new Array();
            this.m_spawner = param1;
            this.m_mechanic =(SubLicensedPropertyMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_spawner, "universityLogo", MechanicManager.ALL);
            super("assets/flyfish/UniversityLogo.xml");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.UNIVERSITY_ASSETS, DelayedAssetLoader.ATTRACTIONS_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected void  performDialogActions ()
        {
            JPanel universityHolder ;
            this.m_toolTipPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            DisplayObject bg =new EmbeddedArt.mkt_pop_info ()as DisplayObject ;
            this.m_toolTipPanel.setBackgroundDecorator(new AssetBackground(bg));
            this.m_toolTipPanel.setPreferredWidth(bg.width);
            ASwingHelper.prepare(this.m_toolTipPanel);
            m_jpanel.addChild(this.m_toolTipPanel);
            titleText = (JLabel)pane.getComponent("Title_Text")
            titleText.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "University_logo_title")));
            ASwingHelper.setProperFont(titleText, EmbeddedArt.titleFont);
            samText = (MultilineLabel)pane.getComponent("sam_text")
            samText.setText(ZLoc.t("Dialogs", "University_complete_logo", {item:this.m_spawner.getItem().localizedName}));
            ASwingHelper.setProperFont(samText, EmbeddedArt.defaultFontNameBold);
            samText.setForeground(new ASColor(7553852));
            samText.setColumns(28);
            samText.setRows(5);
            samText.setEnabled(false);
            logoText = (JLabel)pane.getComponent("logoText")
            logoText.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "University_currentLogo")));
            ASwingHelper.setProperFont(logoText, EmbeddedArt.defaultFontNameBold);
            acceptbtn = pane.getComponent("saveBtn");
            acceptbtn.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "OkButton")));
            acceptbtn.setFont(new ASFont(EmbeddedArt.titleFont, 18, false, false, false, EmbeddedArt.advancedFontProps));
            acceptbtn.addActionListener(this.closeDialog, 0, true);
            this.m_currentLogo = this.m_mechanic.getProperty();
            if (this.m_currentLogo == null || this.m_currentLogo.length == 0)
            {
                this.m_currentLogo = "logo_CVU";
            }
            universityHolder =(JPanel) pane.getComponent("universityHolder");
            LoadingManager .load (Global .gameSettings .getItemByName (this .m_currentLogo ).icon ,void  (Event event )
            {
                universityHolder.removeAll();
                m_imageAP = new AssetPane(event.target.content);
                universityHolder.append(m_imageAP);
                _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","University_currentLogo"),EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor );
                universityHolder.append(_loc_2);
                return;
            }//end
            );
            this.m_logoData = Global.gameSettings().getItemsByKeywordMatch("University_logo");
            this.m_logoData.sort(this.sortUniversityLogos);
            this.m_totalPages = Math.ceil(this.m_logoData.length / NUM_LOGOS);
            this.m_whitePanel = pane.getComponent("whiteBox");
            this.m_itemListHolder = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            this.m_whitePanel.append(this.m_itemListHolder);
            if (this.m_totalPages > 1)
            {
                this.makeButtons();
            }
            this.swapPage();
            closebtn = pane.getComponent("close_btn");
            closebtn.addActionListener(this.closeDialog, 0, true);
            return;
        }//end

        private int  sortUniversityLogos (Item param1 ,Item param2 )
        {
            if (param1.name == "logo_CVU")
            {
                if (param2.name == "logo_CVU")
                {
                    return 0;
                }
                return -1;
            }
            if (param2.name == "logo_CVU")
            {
                return 1;
            }
            _loc_3 =Global.player.checkSubLicense(this.m_spawner.getItemName (),param1.name );
            _loc_4 =Global.player.checkSubLicense(this.m_spawner.getItemName (),param2.name );
            if (_loc_3 && !_loc_4)
            {
                return -1;
            }
            if (!_loc_3 && _loc_4)
            {
                return 1;
            }
            return param1.name.localeCompare(param2.name);
        }//end

        private void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "btn_left_offstate");
            DisplayObject _loc_2 =(DisplayObject)new Catalog.assetDict.get( "btn_left_onstate");
            DisplayObject _loc_3 =(DisplayObject)new Catalog.assetDict.get( "btn_left_downstate");
            DisplayObject _loc_4 =(DisplayObject)new Catalog.assetDict.get( "btn_right_offstate");
            DisplayObject _loc_5 =(DisplayObject)new Catalog.assetDict.get( "btn_right_onstate");
            DisplayObject _loc_6 =(DisplayObject)new Catalog.assetDict.get( "btn_right_downstate");
            this.m_leftBtn = new JButton();
            this.m_leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
            this.m_leftBtn.setEnabled(false);
            this.m_rightBtn = new JButton();
            this.m_rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
            if (this.m_totalPages == 1)
            {
                this.m_rightBtn.setEnabled(false);
            }
            this.m_rightBtn.addActionListener(this.moveRight, 0, true);
            this.m_leftBtn.addActionListener(this.moveLeft, 0, true);
            this.m_whitePanel.insert(0, this.m_leftBtn);
            this.m_whitePanel.append(this.m_rightBtn);
            return;
        }//end

        private void  makePage ()
        {
            UniversityLogoCell _loc_5 =null ;
            JPanel _loc_1 =new JPanel(new FlowWrapLayout(560,FlowWrapLayout.LEFT ,13,6,false ));
            _loc_1.setPreferredHeight(370);
            _loc_2 = this.m_currentPage *NUM_LOGOS ;
            _loc_3 = _loc_2+NUM_LOGOS ;
            if (this.m_logoData.length < _loc_3)
            {
                _loc_3 = this.m_logoData.length;
            }
            _loc_4 = _loc_2;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = new UniversityLogoCell(this.m_spawner);
                _loc_5.setCellValue(this.m_logoData.get(_loc_4));
                _loc_1.append(_loc_5);
                _loc_5.addEventListener(DataItemEvent.MARKET_BUY, this.buyLogo, false, 0, true);
                _loc_5.addEventListener(MouseEvent.MOUSE_OUT, this.turnOffToolTip, false, 0, true);
                _loc_5.addEventListener(MouseEvent.MOUSE_OVER, this.turnOnToolTip, false, 0, true);
                _loc_4++;
            }
            this.m_logoPages.put(this.m_currentPage,  _loc_1);
            return;
        }//end

        private void  turnOffToolTip (MouseEvent event )
        {
            this.m_toolTipPanel.visible = false;
            return;
        }//end

        private void  turnOnToolTip (MouseEvent event )
        {
            Item _loc_3 =null ;
            this.m_toolTipPanel.visible = true;
            _loc_2 =(UniversityLogoCell) event.currentTarget;
            if (_loc_2 != null)
            {
                _loc_3 = _loc_2.getCellValue();
                this.m_toolTipPanel.removeAll();
                this.m_toolTipPanel = TooltipFactory.getInstance().initTitleTooltip(this.m_toolTipPanel, ZLoc.t("Items", _loc_3.name + "_friendlyName"), 14, 130, true);
                ASwingHelper.prepare(this.m_toolTipPanel);
                this.m_toolTipPanel.x = _loc_2.x + OFFSETX;
                this.m_toolTipPanel.y = OFFSETY + _loc_2.y - this.m_toolTipPanel.getHeight();
                this.m_toolTipPanel.doLayout();
            }
            return;
        }//end

        private void  swapPage ()
        {
            this.m_itemListHolder.removeAll();
            if (!this.m_logoPages.get(this.m_currentPage))
            {
                this.makePage();
            }
            this.m_leftBtn.setEnabled(this.m_currentPage > 0);
            this.m_rightBtn.setEnabled(this.m_currentPage < (this.m_totalPages - 1));
            this.m_itemListHolder.append(this.m_logoPages.get(this.m_currentPage));
            return;
        }//end

        private void  moveRight (AWEvent event )
        {
            this.m_currentPage++;
            this.swapPage();
            return;
        }//end

        private void  moveLeft (AWEvent event )
        {
            this.m_currentPage--;
            this.swapPage();
            return;
        }//end

        private void  buyLogo (DataItemEvent event )
        {
            int variant ;
            e = event;
            this.close();
            this.m_currentLogo = e.item.name;
            LoadingManager .load (e .item .icon ,void  (Event event )
            {
                m_imageAP.setAsset(event.target.content);
                ASwingHelper.prepare(pane);
                return;
            }//end
            );
            if (e.pt != null)
            {
                variant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_UNIVERSITIES_FEED);
                if (variant == 1)
                {
                    Global.world.viralMgr.sendUniversityLogoFeed(Global.player, this.m_spawner.getItem().localizedName, e.item.name, e.item.localizedName);
                }
            }
            return;
        }//end

        private void  closeDialog (AWEvent event )
        {
            this.close();
            if (this.m_mechanic.getProperty() == null)
            {
                this.m_mechanic.setProperty(this.m_currentLogo);
            }
            return;
        }//end

    }



