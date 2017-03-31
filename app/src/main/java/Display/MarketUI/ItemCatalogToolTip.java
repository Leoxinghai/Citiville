package Display.MarketUI;

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
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.sim.*;
import Display.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.stats.experiments.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;

    public class ItemCatalogToolTip extends JPanel
    {
        protected Item m_dataItem ;
        private Object m_params ;
        protected AssetPane m_coinAP ;
        protected AssetPane m_personAP ;
        protected AssetPane m_xpAP ;
        protected AssetPane m_foodAP ;
        protected AssetPane m_goodsAP ;
        protected JPanel m_contentPanel ;
        protected AssetPane m_titleText ;
        protected AssetPane m_hr ;
        protected TextField m_titleTextField ;
        protected DisplayObject m_ttbg ;
        protected JPanel m_centerHrPanel ;
        protected double originalTTWidth ;
        protected boolean m_showMe =true ;

        public  ItemCatalogToolTip ()
        {
            super(ASwingHelper.softBoxLayoutVertical);
            this.init();
            return;
        }//end

        public boolean  showMe ()
        {
            return this.m_showMe;
        }//end

        protected void  init ()
        {
            TextFormat _loc_1 =new TextFormat ();
            _loc_1.align = TextFormatAlign.CENTER;
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_ttbg =(DisplayObject) new EmbeddedArt.mkt_pop_info();
            this.setBackgroundDecorator(new AssetBackground(this.m_ttbg));
            DisplayObject _loc_2 =new EmbeddedArt.mkt_rollover_horizontalRule ()as DisplayObject ;
            _loc_2.width = this.m_ttbg.width - 10;
            this.m_hr = new AssetPane(_loc_2);
            this.m_titleText = ASwingHelper.makeMultilineCapsText("", this.m_ttbg.width - 10, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 16, EmbeddedArt.brownTextColor);
            this.m_titleTextField =(TextField) this.m_titleText.getAsset();
            this.setPreferredWidth(this.m_ttbg.width);
            this.originalTTWidth = this.m_ttbg.width;
            this.m_centerHrPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_hr.setPreferredWidth(this.m_ttbg.width);
            this.m_centerHrPanel.appendAll(ASwingHelper.horizontalStrut(5), this.m_hr);
            _loc_3 = this.GetSalePanelForItem ();
            this.appendAll(this.m_titleText, this.m_centerHrPanel, this.m_contentPanel, ASwingHelper.verticalStrut(25), _loc_3);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  GetSalePanelForItem ()
        {
            Class _loc_1 =null ;
            if (this.m_dataItem == null)
            {
                return null;
            }
            if (!Global.marketSaleManager.isItemOnSale(this.m_dataItem))
            {
                return null;
            }
            if (this.m_dataItem.cost > 0)
            {
                _loc_1 = EmbeddedArt.icon_coin;
            }
            else if (this.m_dataItem.cash > 0)
            {
                _loc_1 = EmbeddedArt.icon_cash;
            }
            else
            {
                return null;
            }
            int _loc_2 =0;
            if (Global.marketSaleManager.isItemOnSale(this.m_dataItem))
            {
                _loc_2 = Global.marketSaleManager.getDiscountPercent(this.m_dataItem);
            }
            return TooltipFactory.getInstance().createSalePanel(_loc_1, this.m_dataItem.GetItemCost(), this.m_dataItem.GetItemSalePrice(), _loc_2);
        }//end

        protected JPanel  getSpecialsPanelForItem ()
        {
            if (this.m_dataItem == null)
            {
                return null;
            }
            _loc_1 = Specials.getInstance();
            if (_loc_1.isValidSpecial(this.m_dataItem.name) == false)
            {
                return null;
            }
            return TooltipFactory.getInstance().createSpecialsPane(this.m_dataItem);
        }//end

        protected int  getTextColor ()
        {
            return EmbeddedArt.orangeTextColor;
        }//end

        protected void  changeBackground ()
        {
            return;
        }//end

        protected String  getTitleFont ()
        {
            return EmbeddedArt.titleFont;
        }//end

        protected int  getTitleFontSize ()
        {
            String _loc_4 =null ;
            _loc_1 = this.m_dataItem.localizedName ;
            int _loc_2 =16;
            _loc_3 = _loc_1.split(" ");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4.length >= 13)
                {
                    _loc_2 = 12;
                }
            }
            return _loc_2;
        }//end

        public void  changeInfo (Item param1 ,Object param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Item _loc_7 =null ;
            String _loc_8 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            String _loc_11 =null ;
            Item _loc_12 =null ;
            String _loc_13 =null ;
            int _loc_14 =0;
            String _loc_15 =null ;
            Item _loc_16 =null ;
            String _loc_17 =null ;
            String _loc_18 =null ;
            String _loc_19 =null ;
            String _loc_20 =null ;
            Item _loc_21 =null ;
            this.m_dataItem = param1;
            this.m_params = param2;
            this.m_contentPanel.removeAll();
            if (this.m_dataItem != null)
            {
                this.prepareFilteredTitle();
            }
            if (this.m_dataItem != null)
            {
                this.handleTooltips();
            }
            else if (param2 != null)
            {
                if (this.containsChild(this.m_centerHrPanel))
                {
                    this.remove(this.m_centerHrPanel);
                }
                switch(param2.get("type"))
                {
                    case TooltipType.DESCRIPTION:
                    {
                        _loc_3 = param2.get("title");
                        _loc_4 = param2.get("description");
                        _loc_5 = param2.get("requirement");
                        this.m_contentPanel = TooltipFactory.getInstance().createDescriptionTooltip(this.m_contentPanel, _loc_3, _loc_4, _loc_5);
                        break;
                    }
                    case TooltipType.BUSINESS:
                    {
                        _loc_6 = param2.get("title");
                        _loc_7 = param2.get("item");
                        _loc_8 = param2.get("crewName");
                        _loc_9 = param2.get("upgradeLevel");
                        _loc_10 = param2.get("upgradeProgress");
                        this.m_contentPanel = TooltipFactory.getInstance().createBusinessTooltip(this.m_contentPanel, _loc_6, _loc_7, _loc_8, _loc_9, _loc_10);
                        break;
                    }
                    case TooltipType.RESIDENCE:
                    {
                        _loc_11 = param2.get("title");
                        _loc_12 = param2.get("item");
                        _loc_13 = param2.get("crewName");
                        _loc_14 = int(param2.get("population"));
                        this.m_contentPanel = TooltipFactory.getInstance().createResidenceTooltip(this.m_contentPanel, _loc_11, _loc_12, _loc_13, _loc_14);
                        break;
                    }
                    case TooltipType.MUNICIPAL:
                    {
                        _loc_15 = param2.get("title");
                        _loc_16 = param2.get("item");
                        _loc_17 = param2.get("crewName");
                        this.m_contentPanel = TooltipFactory.getInstance().createMunicipalTooltip(this.m_contentPanel, _loc_15, _loc_16, _loc_17);
                        break;
                    }
                    case TooltipType.RECEIVABLE_XGAME_GIFT:
                    {
                        _loc_18 = param2.get("title");
                        _loc_19 = param2.get("requiredLevel");
                        this.m_contentPanel = TooltipFactory.getInstance().createReceivableXGameGiftTooltip(this.m_contentPanel, _loc_18, _loc_19);
                        break;
                    }
                    case TooltipType.SHORT_BUSINESS:
                    {
                        _loc_20 = param2.get("title");
                        _loc_21 = param2.get("item");
                        this.m_contentPanel = TooltipFactory.getInstance().createShortBusinessTooltip(this.m_contentPanel, _loc_20, _loc_21);
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
            }
            if (this.m_dataItem != null)
            {
                this.changeBackground();
            }
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected int  getTextWidth ()
        {
            return this.m_ttbg.width - 15;
        }//end

        protected void  prepareFilteredTitle ()
        {
            String _loc_4 =null ;
            JPanel _loc_5 =null ;
            _loc_1 = this.m_dataItem.localizedName ;
            if (this.m_dataItem.type == "business" && Global.franchiseManager.model.getOwnsFranchise(this.m_dataItem.name))
            {
                _loc_1 = Global.franchiseManager.getFranchiseName(this.m_dataItem.name, Global.player.uid);
            }
            int _loc_2 =16;
            _loc_3 = _loc_1.split(" ");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4.length >= 13)
                {
                    _loc_2 = 12;
                }
            }
            this.m_titleText = ASwingHelper.makeMultilineCapsText(_loc_1, this.m_ttbg.width - 15, this.getTitleFont(), TextFormatAlign.CENTER, _loc_2, this.getTextColor());
            _loc_5 = ASwingHelper.centerElement(this.m_titleText);
            this.m_contentPanel.appendAll(_loc_5);
            return;
        }//end

        protected void  handleTooltips ()
        {
            Object _loc_3 =null ;
            Component _loc_4 =null ;
            Object _loc_5 =null ;
            Component _loc_6 =null ;
            Object _loc_7 =null ;
            Component _loc_8 =null ;
            Object _loc_9 =null ;
            Component _loc_10 =null ;
            Object _loc_11 =null ;
            Component _loc_12 =null ;
            Object _loc_13 =null ;
            Class _loc_14 =null ;
            Object _loc_15 =null ;
            Component _loc_16 =null ;
            Object _loc_17 =null ;
            Component _loc_18 =null ;
            Object _loc_19 =null ;
            Object _loc_20 =null ;
            Component _loc_21 =null ;
            String _loc_22 =null ;
            int _loc_23 =0;
            String _loc_24 =null ;
            LocalizationObjectToken _loc_25 =null ;
            int _loc_26 =0;
            Object _loc_27 =null ;
            Component _loc_28 =null ;
            double _loc_29 =0;
            String _loc_30 =null ;
            MasteryGoal _loc_31 =null ;
            Object _loc_32 =null ;
            Component _loc_33 =null ;
            Object _loc_34 =null ;
            Component _loc_35 =null ;
            Object _loc_36 =null ;
            Component _loc_37 =null ;
            Component _loc_38 =null ;
            Object _loc_39 =null ;
            Component _loc_40 =null ;
            Object _loc_41 =null ;
            Component _loc_42 =null ;
            Object _loc_43 =null ;
            Component _loc_44 =null ;
            Object _loc_45 =null ;
            Component _loc_46 =null ;
            Object _loc_47 =null ;
            Component _loc_48 =null ;
            Component _loc_49 =null ;
            Object _loc_50 =null ;
            Class _loc_51 =null ;
            Component _loc_52 =null ;
            Object _loc_53 =null ;
            Component _loc_54 =null ;
            Object _loc_55 =null ;
            Component _loc_56 =null ;
            Object _loc_57 =null ;
            int _loc_58 =0;
            int _loc_59 =0;
            int _loc_60 =0;
            int _loc_61 =0;
            Object _loc_62 =null ;
            Object _loc_63 =null ;
            Component _loc_64 =null ;
            Object _loc_65 =null ;
            Component _loc_66 =null ;
            Object _loc_67 =null ;
            Component _loc_68 =null ;
            boolean _loc_69 =false ;
            Object _loc_70 =null ;
            Component _loc_71 =null ;
            Object _loc_72 =null ;
            Class _loc_73 =null ;
            Component _loc_74 =null ;
            Object _loc_75 =null ;
            Component _loc_76 =null ;
            Object _loc_77 =null ;
            Object _loc_78 =null ;
            Component _loc_79 =null ;
            DisplayObject _loc_80 =null ;
            DisplayObject _loc_81 =null ;
            int _loc_82 =0;
            Object _loc_83 =null ;
            Component _loc_84 =null ;
            Object _loc_85 =null ;
            Component _loc_86 =null ;
            Object _loc_87 =null ;
            Component _loc_88 =null ;
            Object _loc_89 =null ;
            Component _loc_90 =null ;
            double _loc_91 =0;
            Item _loc_92 =null ;
            double _loc_93 =0;
            double _loc_94 =0;
            double _loc_95 =0;
            Item _loc_96 =null ;
            double _loc_97 =0;
            double _loc_98 =0;
            Component _loc_99 =null ;
            Object _loc_100 =null ;
            Component _loc_101 =null ;
            Component _loc_102 =null ;
            Object _loc_103 =null ;
            Component _loc_104 =null ;
            Object _loc_105 =null ;
            Component _loc_106 =null ;
            Object _loc_107 =null ;
            Component _loc_108 =null ;
            Object _loc_109 =null ;
            Component _loc_110 =null ;
            Component _loc_111 =null ;
            Object _loc_112 =null ;
            Component _loc_113 =null ;
            Object _loc_114 =null ;
            Component _loc_115 =null ;
            Object _loc_116 =null ;
            JPanel _loc_117 =null ;
            JPanel _loc_118 =null ;
            Item _loc_119 =null ;
            int _loc_120 =0;
            int _loc_121 =0;
            int _loc_122 =0;
            int _loc_123 =0;
            Object _loc_124 =null ;
            Object _loc_125 =null ;
            Component _loc_126 =null ;
            ItemBonus _loc_127 =null ;
            Object _loc_128 =null ;
            Component _loc_129 =null ;
            String _loc_130 =null ;
            Object _loc_131 =null ;
            Component _loc_132 =null ;
            Object _loc_133 =null ;
            Component _loc_134 =null ;
            Component _loc_135 =null ;
            String _loc_136 =null ;
            ItemBonus _loc_137 =null ;
            Object _loc_138 =null ;
            ItemBonus _loc_139 =null ;
            Object _loc_140 =null ;
            Component _loc_141 =null ;
            String _loc_142 =null ;
            Object _loc_143 =null ;
            Component _loc_144 =null ;
            Object _loc_145 =null ;
            Object _loc_146 =null ;
            Object _loc_147 =null ;
            Object _loc_148 =null ;
            Component _loc_149 =null ;
            Array _loc_150 =null ;
            Object _loc_151 =null ;
            Component _loc_152 =null ;
            String _loc_153 =null ;
            Object _loc_154 =null ;
            Component _loc_155 =null ;
            _loc_1 = this.m_dataItem.buildTimeInDays ();
            this.setPreferredWidth(this.originalTTWidth);
            _loc_2 = this.GetSalePanelForItem ();
            switch(this.m_dataItem.type)
            {
                case "permit_pack2":
                case "permit_pack3":
                case "permit_pack4":
                {
                    _loc_3 = new Object();
                    _loc_3.type = TooltipType.PERMIT_PACK;
                    _loc_3.content = ZLoc.t("Items", "permit_pack_friendlyName");
                    _loc_3.dataItem = this.m_dataItem;
                    _loc_3.contentPanel = this.m_contentPanel;
                    _loc_4 = TooltipFactory.getInstance().createTooltip(_loc_3);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_3);
                    break;
                }
                case "genericBundle":
                {
                    _loc_5 = new Object();
                    _loc_5.type = TooltipType.GENERIC_BUNDLE;
                    _loc_5.content = ZLoc.t("Items", this.m_dataItem.name + "_friendlyName");
                    _loc_5.dataItem = this.m_dataItem;
                    _loc_5.contentPanel = this.m_contentPanel;
                    _loc_6 = TooltipFactory.getInstance().createTooltip(_loc_5);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_6);
                    break;
                }
                case "themed_bundle":
                {
                    _loc_7 = new Object();
                    _loc_7.type = TooltipType.THEMED_BUNDLE;
                    _loc_7.content = ZLoc.t("Items", this.m_dataItem.name + "_friendlyName");
                    _loc_7.dataItem = this.m_dataItem;
                    _loc_7.contentPanel = this.m_contentPanel;
                    _loc_8 = TooltipFactory.getInstance().createTooltip(_loc_7);
                    ASwingHelper.prepare(_loc_8);
                    this.setPreferredWidth(_loc_8.getWidth());
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_8);
                    break;
                }
                case "starter_pack2_1":
                case "starter_pack2_2":
                case "starter_pack2_3":
                case "starter_pack2_4":
                {
                    _loc_9 = new Object();
                    _loc_9.type = TooltipType.STARTER_PACK;
                    _loc_9.content = ZLoc.t("Items", "starter_pack_friendlyName");
                    _loc_9.dataItem = this.m_dataItem;
                    _loc_9.contentPanel = this.m_contentPanel;
                    _loc_10 = TooltipFactory.getInstance().createTooltip(_loc_9);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_10);
                    break;
                }
                case "mystery_crate":
                {
                    _loc_11 = new Object();
                    _loc_11.type = "mystery_crate";
                    _loc_11.content = ZLoc.t("Items", "mystery_crate_friendlyName");
                    _loc_11.dataItem = this.m_dataItem;
                    _loc_11.contentPanel = this.m_contentPanel;
                    _loc_12 = TooltipFactory.getInstance().createTooltip(_loc_11);
                    ASwingHelper.prepare(_loc_12);
                    this.setPreferredWidth(_loc_12.getWidth());
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_12);
                    break;
                }
                case "residence":
                {
                    _loc_13 = new Object();
                    _loc_14 = PopulationHelper.getItemPopulationIcon(this.m_dataItem);
                    _loc_13.type = TooltipType.ONE_ITEM;
                    _loc_13.title = ZLoc.t("Dialogs", "TT_Population");
                    _loc_13.firstIcon =(DisplayObject) new _loc_14;
                    _loc_15 = {maxPop:this.m_dataItem.populationMax * Global.gameSettings().getNumber("populationScale", 1), minPop:this.m_dataItem.populationBase * Global.gameSettings().getNumber("populationScale", 1)};
                    if (this.m_dataItem.hasRemodel() && this.m_dataItem.isRemodelSkin())
                    {
                        _loc_91 = 0;
                        _loc_92 = this.m_dataItem.getRemodelBase();
                        _loc_93 = _loc_92.populationBase;
                        _loc_94 = this.m_dataItem.populationBase;
                        _loc_91 = Math.round((_loc_94 - _loc_93) / _loc_93 * 100);
                        _loc_13.firstData = ZLoc.t("Dialogs", "TT_PopulationUpTo", _loc_15) + " (+" + _loc_91 + "%)   ";
                        _loc_13.fontSize = 12;
                    }
                    else
                    {
                        _loc_13.firstData = ZLoc.t("Dialogs", "TT_PopulationUpTo", _loc_15) + "   ";
                    }
                    _loc_16 = TooltipFactory.getInstance().createTooltip(_loc_13);
                    _loc_17 = new Object();
                    _loc_17.type = TooltipType.TWO_ITEMS;
                    _loc_17.title = "";
                    _loc_17.firstIcon = null;
                    _loc_17.firstData = "";
                    _loc_17.lastLine = PopulationHelper.getItemPopulationSubTitle(this.m_dataItem);
                    _loc_18 = TooltipFactory.getInstance().createTooltip(_loc_17);
                    _loc_19 = new Object();
                    _loc_19.type = TooltipType.TWO_ITEMS;
                    _loc_19.title = ZLoc.t("Dialogs", "TT_Rent");
                    _loc_19.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                    if (this.m_dataItem.hasRemodel() && this.m_dataItem.isRemodelSkin())
                    {
                        _loc_95 = 0;
                        _loc_96 = this.m_dataItem.getRemodelBase();
                        _loc_97 = Global.player.GetDooberMinimums(_loc_96, Doober.DOOBER_COIN);
                        _loc_98 = Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_COIN);
                        _loc_95 = (_loc_98 - _loc_97) / _loc_97 * 100;
                        _loc_19.firstData = String(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_COIN)) + " (" + _loc_95 + "%)";
                        _loc_19.lastLine = "";
                        _loc_99 = TooltipFactory.getInstance().createTooltip(_loc_19);
                        _loc_100 = new Object();
                        _loc_100.type = TooltipType.TWO_ITEMS;
                        _loc_100.title = "";
                        _loc_100.firstIcon = null;
                        _loc_100.firstData = "";
                        _loc_100.lastLine = CardUtil.localizeTimeLeftLanguage(this.m_dataItem.growTime);
                        _loc_101 = TooltipFactory.getInstance().createTooltip(_loc_100);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_16, _loc_18, _loc_99, _loc_101);
                    }
                    else
                    {
                        _loc_19.firstData = String(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_COIN));
                        _loc_19.lastLine = CardUtil.localizeTimeLeftLanguage(this.m_dataItem.growTime);
                        _loc_102 = TooltipFactory.getInstance().createTooltip(_loc_19);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_16, _loc_102);
                    }
                    break;
                }
                case "business":
                {
                    if (this.m_dataItem.localizedName == ZLoc.t("Items", "biz_lotsite_4x4_friendlyName"))
                    {
                        _loc_103 = new Object();
                        _loc_103.type = TooltipType.EMPTY_LOT;
                        _loc_103.ttbgWidth = this.m_ttbg.width;
                        _loc_104 = TooltipFactory.getInstance().createTooltip(_loc_103);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_104);
                    }
                    else if (this.m_dataItem.localizedName == ZLoc.t("Items", "supply_all_business_friendlyName"))
                    {
                        _loc_105 = new Object();
                        _loc_105.type = TooltipType.DESCRIPTION;
                        _loc_105.ttbgWidth = this.m_ttbg.width;
                        _loc_105.content = ZLoc.t("Items", "supply_all_business_content");
                        _loc_106 = TooltipFactory.getInstance().createTooltip(_loc_105);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_106);
                    }
                    else if (this.m_dataItem.localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName"))
                    {
                        _loc_107 = new Object();
                        _loc_107.type = TooltipType.DESCRIPTION;
                        _loc_107.ttbgWidth = this.m_ttbg.width;
                        _loc_107.content = ZLoc.t("Items", "mark_all_business_ready_content");
                        _loc_108 = TooltipFactory.getInstance().createTooltip(_loc_107);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_108);
                    }
                    else if (this.m_dataItem.localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
                    {
                        _loc_109 = new Object();
                        _loc_109.type = TooltipType.DESCRIPTION;
                        _loc_109.ttbgWidth = this.m_ttbg.width;
                        _loc_109.content = ZLoc.t("Items", "instant_ready_crop_content");
                        _loc_110 = TooltipFactory.getInstance().createTooltip(_loc_109);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_110);
                    }
                    else
                    {
                        if (this.m_dataItem.upgrade != null && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) && (!this.m_dataItem.upgrade || this.m_dataItem.upgrade && this.m_dataItem.upgrade.meetsExperimentRequirement) && Global.player.level >= Global.gameSettings().getInt("businessUpgradesRequiredLevel", 15))
                        {
                            _loc_116 = new Object();
                            _loc_116.type = TooltipType.ONE_ITEM;
                            _loc_116.firstIcon =(DisplayObject) new EmbeddedArt.mkt_masteryStar();
                            _loc_116.firstData = String(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "TT_Upgradeable") + " "));
                            _loc_111 = TooltipFactory.getInstance().createTooltip(_loc_116);
                        }
                        _loc_112 = new Object();
                        _loc_112.type = TooltipType.ONE_ITEM;
                        _loc_112.title = ZLoc.t("Dialogs", "TT_Earnings");
                        _loc_112.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                        _loc_112.firstData = String(Math.ceil(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_COIN) * this.m_dataItem.commodityReq));
                        _loc_112.lastLine = ZLoc.t("Dialogs", "TT_perPerson");
                        _loc_113 = TooltipFactory.getInstance().createTooltip(_loc_112);
                        _loc_114 = new Object();
                        _loc_114.type = TooltipType.ONE_ITEM_W_LINE;
                        _loc_114.title = ZLoc.t("Dialogs", "TT_SupplyReq");
                        if (this.m_dataItem.commodityName == Commodities.GOODS_COMMODITY)
                        {
                            _loc_114.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                            _loc_114.firstData = String(this.m_dataItem.commodityReq);
                        }
                        else if (this.m_dataItem.commodityName == Commodities.PREMIUM_GOODS_COMMODITY)
                        {
                            _loc_114.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                            _loc_114.firstData = String(this.m_dataItem.commodityReq);
                        }
                        _loc_114.lastLine = ZLoc.t("Dialogs", "TT_perPerson");
                        _loc_115 = TooltipFactory.getInstance().createTooltip(_loc_114);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_111, _loc_2, _loc_113, _loc_115);
                    }
                    break;
                }
                case "storage":
                {
                    _loc_20 = new Object();
                    _loc_20.type = TooltipType.ONE_ITEM;
                    _loc_20.title = ZLoc.t("Dialogs", "TT_Storage");
                    _loc_20.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_20.firstData = String(this.m_dataItem.commodityCapacity);
                    _loc_21 = TooltipFactory.getInstance().createTooltip(_loc_20);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_21);
                    break;
                }
                case "attraction":
                {
                    if (this.containsChild(this.m_centerHrPanel))
                    {
                        this.remove(this.m_centerHrPanel);
                    }
                    this.m_contentPanel = TooltipFactory.getInstance().createAttractionPanel(this.m_contentPanel, this.m_dataItem);
                    break;
                }
                case "neighborhood":
                {
                    _loc_22 = this.m_dataItem.name;
                    _loc_23 = Neighborhood.getCapacityForNeighborhood(_loc_22);
                    _loc_24 = Neighborhood.getResidenceTypeForNeighborhood(_loc_22);
                    _loc_25 = ZLoc.tk("Dialogs", "Neighborhood_" + _loc_24, "", _loc_23);
                    _loc_26 = 200;
                    if (Global.localizer.langCode == "ja")
                    {
                        _loc_26 = 170;
                    }
                    _loc_27 = {type:TooltipType.DESCRIPTION, content:ZLoc.t("Dialogs", "TT_NeighborhoodSaveSpace", {count:_loc_23, pluralizedType:_loc_25}), ttbgWidth:_loc_26};
                    _loc_28 = TooltipFactory.getInstance().createTooltip(_loc_27);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_28);
                    break;
                }
                case "plot":
                {
                    break;
                }
                case "plot_contract":
                {
                    _loc_29 = 1;
                    _loc_30 = ZLoc.t("Dialogs", "TT_Makes");
                    _loc_31 =(MasteryGoal) Global.goalManager.getGoal(GoalManager.GOAL_MASTERY);
                    if (_loc_31 && Global.player.isEligibleForMastery(this.m_dataItem))
                    {
                        _loc_29 = _loc_31.getBonusMultiplier(this.m_dataItem.name) / 100 + 1;
                        _loc_30 = ZLoc.t("Dialogs", "TT_CurrentlyMakes");
                        _loc_117 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_118 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                        _loc_118.setPreferredWidth(this.m_contentPanel.getPreferredWidth());
                        _loc_119 = this.m_dataItem;
                        _loc_120 = _loc_31.getLevel(this.m_dataItem.name);
                        _loc_121 = _loc_31.getProgressTowardNextLevel(this.m_dataItem.name);
                        _loc_122 = _loc_31.getNextLevelDiff(this.m_dataItem.name);
                        _loc_123 = _loc_31.getMaxLevel(this.m_dataItem.name);
                        _loc_118 = TooltipFactory.getInstance().createMasteryTooltip(_loc_118, _loc_119, _loc_120, _loc_121, _loc_122, _loc_123);
                        _loc_117.append(_loc_118);
                    }
                    _loc_32 = new Object();
                    _loc_32.type = TooltipType.ONE_ITEM;
                    _loc_32.title = _loc_30;
                    if (Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_PREMIUM_GOODS) > 0)
                    {
                        _loc_32.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                        _loc_32.firstData = String(Math.ceil(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_PREMIUM_GOODS) * _loc_29));
                    }
                    else
                    {
                        _loc_32.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_32.firstData = String(Math.ceil(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_GOODS) * _loc_29));
                    }
                    _loc_33 = TooltipFactory.getInstance().createTooltip(_loc_32);
                    _loc_34 = new Object();
                    if (_loc_31 && Global.player.isEligibleForMastery(this.m_dataItem) && !_loc_31.isMaxLevel(this.m_dataItem.name))
                    {
                        _loc_34.type = TooltipType.ONE_ITEM;
                        _loc_34.title = ZLoc.t("Dialogs", "TT_StarBonus", {num:(_loc_31.getLevel(this.m_dataItem.name) + 1)});
                        if (Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_PREMIUM_GOODS) > 0)
                        {
                            _loc_34.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                        }
                        else
                        {
                            _loc_34.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        }
                        _loc_34.firstData = ZLoc.t("Dialogs", "TT_BonusPercent", {amount:_loc_31.getNextBonusMultiplier(this.m_dataItem.name)});
                    }
                    _loc_35 = TooltipFactory.getInstance().createTooltip(_loc_34);
                    _loc_36 = new Object();
                    _loc_36.type = TooltipType.ONE_LINE;
                    _loc_36.title = ZLoc.t("Dialogs", "TT_Harvest");
                    if (this.m_dataItem.growTime > 2.4e-005)
                    {
                        _loc_36.firstData = CardUtil.localizeTimeLeft(this.m_dataItem.growTime);
                    }
                    else
                    {
                        _loc_36.firstData = ZLoc.t("Dialogs", "TT_InstantGrow");
                    }
                    if (this.m_dataItem.name == "plot_lettuce")
                    {
                        _loc_124 = new Object();
                        _loc_124.type = TooltipType.DESCRIPTION;
                        _loc_124.ttbgWidth = this.m_ttbg.width;
                        _loc_124.content = ZLoc.t("Dialogs", "TT_PotatoRules");
                        _loc_37 = TooltipFactory.getInstance().createTooltip(_loc_124);
                    }
                    _loc_38 = TooltipFactory.getInstance().createTooltip(_loc_36);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_117, _loc_2, _loc_33, _loc_35, _loc_38, _loc_37);
                    break;
                }
                case "factory_contract":
                {
                    _loc_39 = new Object();
                    _loc_39.type = TooltipType.ONE_ITEM;
                    _loc_39.title = ZLoc.t("Dialogs", "TT_Makes");
                    if (Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_PREMIUM_GOODS) > 0)
                    {
                        _loc_39.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                        _loc_39.firstData = ZLoc.t("Dialogs", "TT_PerWorker", {amount:Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_PREMIUM_GOODS)});
                    }
                    else
                    {
                        _loc_39.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_39.firstData = ZLoc.t("Dialogs", "TT_PerWorker", {amount:Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_GOODS)});
                    }
                    _loc_40 = TooltipFactory.getInstance().createTooltip(_loc_39);
                    _loc_41 = new Object();
                    _loc_41.type = TooltipType.ONE_LINE;
                    _loc_41.title = ZLoc.t("Dialogs", "TT_BuildsIn");
                    if (this.m_dataItem.growTime > 2.4e-005)
                    {
                        _loc_41.firstData = CardUtil.localizeTimeLeft(this.m_dataItem.growTime);
                    }
                    else
                    {
                        _loc_41.firstData = ZLoc.t("Dialogs", "TT_InstantGrow");
                    }
                    _loc_42 = TooltipFactory.getInstance().createTooltip(_loc_41);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_40, _loc_42);
                    break;
                }
                case "decoration":
                {
                    if (this.m_dataItem.bonusPercent > 0)
                    {
                        _loc_125 = new Object();
                        _loc_125.type = TooltipType.TITLE;
                        _loc_125.title = ZLoc.t("Dialogs", "TT_Bonus");
                        _loc_126 = TooltipFactory.getInstance().createTooltip(_loc_125);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_126);
                        if (this.m_dataItem.bonuses.length > 0)
                        {
                            for(int i0 = 0; i0 < this.m_dataItem.bonuses.size(); i0++)
                            {
                            		_loc_127 = this.m_dataItem.bonuses.get(i0);

                                _loc_128 = new Object();
                                _loc_128.type = TooltipType.DESCRIPTION;
                                _loc_128.ttbgWidth = this.m_ttbg.width;
                                _loc_128.content = _loc_127.getMarketPlaceString();
                                _loc_129 = TooltipFactory.getInstance().createTooltip(_loc_128);
                                this.m_contentPanel.appendAll(_loc_129);
                                for(int i0 = 0; i0 < _loc_127.allowedTypes.size(); i0++)
                                {
                                		_loc_130 = _loc_127.allowedTypes.get(i0);

                                    _loc_131 = {type:"indentedDescriptor", firstData:ZLoc.t("Dialogs", "TT_Payout_" + _loc_130)};
                                    _loc_132 = TooltipFactory.getInstance().createTooltip(_loc_131);
                                    this.m_contentPanel.appendAll(_loc_132);
                                }
                                if (ItemBonus.keywordExperimentEnabled())
                                {
                                    if (_loc_127.allowedQueries.length > 0)
                                    {
                                        _loc_133 = {type:"indentedDescriptor", firstData:ZLoc.t("Keywords", _loc_127.name + "_AOEName")};
                                        _loc_134 = TooltipFactory.getInstance().createTooltip(_loc_133);
                                        this.m_contentPanel.appendAll(_loc_134);
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (this.m_dataItem.bonuses != null && this.m_dataItem.bonuses.length > 0)
                        {
                            _loc_136 = "";
                            for(int i0 = 0; i0 < this.m_dataItem.bonuses.size(); i0++)
                            {
                            		_loc_137 = this.m_dataItem.bonuses.get(i0);

                                if (_loc_137.description != null && _loc_137.description != "")
                                {
                                    if (_loc_136.length > 0)
                                    {
                                        _loc_136 = _loc_136 + "\n";
                                    }
                                    _loc_136 = _loc_136 + ZLoc.t("Items", _loc_137.description);
                                }
                            }
                            if (_loc_136.length > 0)
                            {
                                _loc_138 = new Object();
                                _loc_138.type = TooltipType.DESCRIPTION;
                                _loc_138.ttbgWidth = this.m_ttbg.width;
                                _loc_138.content = _loc_136;
                                _loc_135 = TooltipFactory.getInstance().createTooltip(_loc_138);
                            }
                        }
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_135);
                    }
                    break;
                }
                case "garden":
                {
                    if (this.m_dataItem.bonuses.length > 0)
                    {
                        for(int i0 = 0; i0 < this.m_dataItem.bonuses.size(); i0++)
                        {
                        		_loc_139 = this.m_dataItem.bonuses.get(i0);

                            _loc_140 = new Object();
                            _loc_140.type = TooltipType.DESCRIPTION;
                            _loc_140.ttbgWidth = 100;
                            _loc_140.content = _loc_139.getMarketPlaceString();
                            _loc_141 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_140);
                            this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_141);
                            for(int i0 = 0; i0 < _loc_139.allowedTypes.size(); i0++)
                            {
                            		_loc_142 = _loc_139.allowedTypes.get(i0);

                                _loc_143 = {type:"indentedDescriptor", firstData:ZLoc.t("Dialogs", "TT_Payout_" + _loc_142)};
                                _loc_144 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_143);
                                this.m_contentPanel.appendAll(_loc_144);
                            }
                        }
                    }
                    else
                    {
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2);
                    }
                    break;
                }
                case "pier":
                {
                    break;
                }
                case "ship":
                case "harvest_ship":
                {
                    _loc_43 = new Object();
                    _loc_43.type = TooltipType.DESCRIPTION;
                    _loc_43.ttbgWidth = this.m_ttbg.width;
                    _loc_43.content = ZLoc.t("Dialogs", "TT_ShipRules");
                    _loc_44 = TooltipFactory.getInstance().createTooltip(_loc_43);
                    _loc_45 = new Object();
                    _loc_45.type = TooltipType.REQUIREMENT;
                    _loc_45.ttbgWidth = this.m_ttbg.width;
                    _loc_45.content = ZLoc.t("Dialogs", "TT_ShipReq");
                    _loc_46 = TooltipFactory.getInstance().createTooltip(_loc_45);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_44, _loc_46);
                    break;
                }
                case "ship_contract":
                {
                    _loc_32 = new Object();
                    _loc_32.type = TooltipType.ONE_ITEM;
                    _loc_32.title = ZLoc.t("Dialogs", "TT_Makes");
                    _loc_32.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_32.firstData = String(Math.round(Global.player.GetDooberMinimums(this.m_dataItem, Doober.DOOBER_GOODS) * this.m_params.firstDataMultiplier));
                    _loc_33 = TooltipFactory.getInstance().createTooltip(_loc_32);
                    _loc_47 = new Object();
                    _loc_47.type = TooltipType.ONE_LINE;
                    _loc_47.title = ZLoc.t("Dialogs", "TT_RoundTrip");
                    _loc_47.firstData = CardUtil.localizeTimeLeft(this.m_dataItem.growTime);
                    _loc_48 = TooltipFactory.getInstance().createTooltip(_loc_47);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_33, _loc_48);
                    break;
                }
                case "university":
                case "municipal":
                {
                    if (this.m_dataItem.name == "mun_policestation" && Global.player.level >= 10)
                    {
                        _loc_145 = new Object();
                        _loc_145.type = TooltipType.ONE_ITEM;
                        _loc_145.title = ZLoc.t("Dialogs", "TT_Unlocks");
                        _loc_145.firstIcon =(DisplayObject) new EmbeddedArt.mkt_shieldIcon();
                        _loc_145.firstData = ZLoc.t("Dialogs", "TT_copsnbandits") + " ";
                        _loc_49 = TooltipFactory.getInstance().createTooltip(_loc_145);
                    }
                    _loc_50 = new Object();
                    _loc_51 = PopulationHelper.getItemPopulationIcon(this.m_dataItem);
                    _loc_50.type = TooltipType.ONE_ITEM;
                    _loc_50.title = ZLoc.t("Dialogs", "TT_Allows");
                    _loc_50.firstIcon =(DisplayObject) new _loc_51;
                    _loc_50.firstData = String(this.m_dataItem.populationCapYield * Global.gameSettings().getNumber("populationScale", 1));
                    _loc_52 = TooltipFactory.getInstance().createTooltip(_loc_50);
                    _loc_53 = new Object();
                    _loc_53.type = TooltipType.TWO_ITEMS;
                    _loc_53.title = "";
                    _loc_53.firstIcon = null;
                    _loc_53.firstData = "";
                    _loc_53.lastLine = PopulationHelper.getItemPopulationSubTitle(this.m_dataItem);
                    _loc_54 = TooltipFactory.getInstance().createTooltip(_loc_53);
                    _loc_55 = new Object();
                    _loc_55.type = TooltipType.DESCRIPTION;
                    _loc_55.ttbgWidth = this.m_ttbg.width;
                    _loc_55.content = ZLoc.t("Dialogs", "TT_MuniRules");
                    _loc_56 = TooltipFactory.getInstance().createTooltip(_loc_55);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_49, _loc_52, _loc_54, _loc_56);
                    break;
                }
                case "expansion":
                {
                    this.m_contentPanel.append(this.m_centerHrPanel);
                    _loc_57 = {type:"requirement", content:ZLoc.t("Dialogs", "TT_Requires")};
                    this.m_contentPanel.append(TooltipFactory.getInstance().createTooltip(_loc_57));
                    _loc_58 = ExpansionManager.instance.getNextExpansionPermitRequirement();
                    _loc_59 = Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM);
                    _loc_60 = ExpansionManager.instance.getNextExpansionPopulationRequirement();
                    _loc_61 = Global.world.citySim.getPopulation();
                    if (_loc_58 > 0)
                    {
                        _loc_146 = {type:"checkmarkwLine", firstData:ZLoc.t("Dialogs", "TT_ExpansionPermitsReq", {permits:Math.max(0, _loc_58 - _loc_59)}), isComplete:_loc_59 >= _loc_58};
                        this.m_contentPanel.append(TooltipFactory.getInstance().createTooltip(_loc_146));
                    }
                    if (_loc_60 > 0)
                    {
                        _loc_147 = {type:"checkmarkwLine", firstColor:EmbeddedArt.greenTextColor, firstData:ZLoc.t("Dialogs", "TT_ExpansionPopulationReq", {population:_loc_60 * Global.gameSettings().getNumber("populationScale", 1)}), isComplete:_loc_61 >= _loc_60};
                        this.m_contentPanel.append(TooltipFactory.getInstance().createTooltip(_loc_147));
                    }
                    _loc_62 = new Object();
                    _loc_62.type = TooltipType.DESCRIPTION;
                    _loc_62.ttbgWidth = this.m_ttbg.width;
                    _loc_62.content = ZLoc.t("Dialogs", "TT_ExpansionRules");
                    this.m_contentPanel.append(TooltipFactory.getInstance().createTooltip(_loc_62));
                    break;
                }
                case "energy":
                {
                    _loc_63 = new Object();
                    _loc_63.type = TooltipType.ONE_ITEM;
                    _loc_63.title = ZLoc.t("Dialogs", "TT_Energy");
                    _loc_63.firstIcon =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_63.firstData = String(this.m_dataItem.energyRewards);
                    _loc_64 = TooltipFactory.getInstance().createTooltip(_loc_63);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_64);
                    break;
                }
                case "gas":
                {
                    _loc_65 = new Object();
                    _loc_65.type = TooltipType.ONE_ITEM;
                    _loc_65.title = ZLoc.t("Dialogs", "TT_Gas");
                    _loc_65.firstIcon =(DisplayObject) new EmbeddedArt.mkt_gasIcon();
                    _loc_65.firstData = String(this.m_dataItem.getRegenerableRewardAmountByName(RegenerableResource.GAS));
                    _loc_66 = TooltipFactory.getInstance().createTooltip(_loc_65);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_66);
                    break;
                }
                case "goods":
                {
                    _loc_67 = new Object();
                    _loc_67.type = TooltipType.ONE_ITEM;
                    _loc_67.title = ZLoc.t("Dialogs", "TT_Goods");
                    _loc_67.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_67.firstData = String(this.m_dataItem.goodsReward);
                    _loc_68 = TooltipFactory.getInstance().createTooltip(_loc_67);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_68);
                    break;
                }
                case "theme_collection":
                {
                    _loc_69 = !Global.world.isThemeCollectionEnabled(this.m_dataItem);
                    _loc_70 = new Object();
                    _loc_70.type = TooltipType.DESCRIPTION;
                    _loc_70.ttbgWidth = this.m_ttbg.width;
                    _loc_70.content = ZLoc.t("Dialogs", "TT_ThemeCollection_" + this.m_dataItem.name + "_" + (_loc_69 ? ("on") : ("off")));
                    _loc_71 = TooltipFactory.getInstance().createTooltip(_loc_70);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_71);
                    break;
                }
                case "landmark":
                {
                    _loc_72 = new Object();
                    _loc_73 = PopulationHelper.getItemPopulationIcon(this.m_dataItem);
                    _loc_72.type = TooltipType.ONE_ITEM;
                    _loc_72.title = ZLoc.t("Dialogs", "TT_Allows");
                    _loc_72.firstIcon =(DisplayObject) new _loc_73;
                    _loc_72.firstData = String(this.m_dataItem.populationCapYield * Global.gameSettings().getNumber("populationScale", 1));
                    _loc_74 = TooltipFactory.getInstance().createTooltip(_loc_72);
                    _loc_75 = new Object();
                    _loc_75.type = TooltipType.TWO_ITEMS;
                    _loc_75.title = "";
                    _loc_75.firstIcon = null;
                    _loc_75.firstData = "";
                    _loc_75.lastLine = PopulationHelper.getItemPopulationSubTitle(this.m_dataItem);
                    _loc_76 = TooltipFactory.getInstance().createTooltip(_loc_75);
                    _loc_77 = new Object();
                    _loc_77.type = TooltipType.DESCRIPTION;
                    _loc_77.ttbgWidth = this.m_ttbg.width;
                    _loc_77.content = ZLoc.t("Dialogs", "TT_MuniRules");
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_74, _loc_76);
                    break;
                }
                case "wonder":
                {
                    break;
                }
                case "xgamegift":
                {
                    this.m_contentPanel.append(this.m_centerHrPanel);
                    if (Global.player.level < this.m_dataItem.requiredLevel)
                    {
                        _loc_148 = new Object();
                        _loc_148.type = TooltipType.REQUIREMENT;
                        _loc_148.ttbgWidth = this.m_ttbg.width;
                        _loc_148.content = ZLoc.t("Dialogs", "level_text", {amount:this.m_dataItem.requiredLevel});
                        _loc_149 = TooltipFactory.getInstance().createTooltip(_loc_148);
                        this.m_contentPanel.append(_loc_149);
                    }
                    _loc_78 = new Object();
                    _loc_78.type = TooltipType.DESCRIPTION;
                    _loc_78.ttbgWidth = this.m_ttbg.width;
                    _loc_78.content = ZLoc.t("Items", this.m_dataItem.name + "_toolTip");
                    _loc_79 = TooltipFactory.getInstance().createTooltip(_loc_78);
                    this.m_contentPanel.append(_loc_79);
                    break;
                }
                case "train":
                {
                    if (this.m_dataItem.goods > 0)
                    {
                        _loc_80 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_81 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                        _loc_82 = this.m_dataItem.goods;
                    }
                    else
                    {
                        _loc_81 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_80 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                        _loc_82 = this.m_dataItem.cost;
                    }
                    _loc_83 = {type:"oneItem", title:ZLoc.t("Dialogs", "TT_Costs"), firstIcon:_loc_80, firstData:_loc_82};
                    _loc_84 = TooltipFactory.getInstance().createTooltip(_loc_83);
                    _loc_85 = {type:"oneItem", title:ZLoc.t("Dialogs", "TT_Earns"), firstIcon:_loc_81, firstData:Global.gameSettings().getTieredInt(this.m_dataItem.trainPayout, this.m_dataItem.workers.amount)};
                    _loc_86 = TooltipFactory.getInstance().createTooltip(_loc_85);
                    _loc_87 = {type:"oneLine", title:ZLoc.t("Dialogs", "TT_StopsAvailable"), firstData:this.m_dataItem.workers ? (this.m_dataItem.workers.amount) : (0)};
                    _loc_88 = TooltipFactory.getInstance().createTooltip(_loc_87);
                    _loc_89 = {type:"oneLine", title:ZLoc.t("Dialogs", "TT_RoundTrip"), firstData:CardUtil.localizeTimeLeft(this.m_dataItem.trainTripTime / 86400)};
                    _loc_90 = TooltipFactory.getInstance().createTooltip(_loc_89);
                    this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_84, _loc_86, _loc_88, _loc_90);
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            if (ItemBonus.keywordExperimentEnabled())
            {
                if (this.m_dataItem.type != "decoration")
                {
                    _loc_150 = this.m_dataItem.getMarketVisibleItemKeywords();
                    if (_loc_150.length > 0)
                    {
                        _loc_151 = new Object();
                        _loc_151.type = TooltipType.TITLE;
                        _loc_151.title = ZLoc.t("Dialogs", "TT_Bonus");
                        _loc_152 = TooltipFactory.getInstance().createTooltip(_loc_151);
                        this.m_contentPanel.appendAll(this.m_centerHrPanel, _loc_2, _loc_152);
                        for(int i0 = 0; i0 < _loc_150.size(); i0++)
                        {
                        		_loc_153 = _loc_150.get(i0);

                            _loc_154 = {type:"indentedDescriptor", firstData:ZLoc.t("Keywords", _loc_153 + "_friendlyName")};
                            _loc_155 = TooltipFactory.getInstance().createTooltip(_loc_154);
                            this.m_contentPanel.appendAll(_loc_155);
                        }
                    }
                }
            }
            return;
        }//end

    }



