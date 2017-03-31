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
import Classes.bonus.*;
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.franchise.display.*;
import ZLocalization.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;

import com.xinghai.Debug;

    public class TooltipFactory
    {
        protected AssetPane m_titleText ;
        protected AssetPane m_hr ;
        protected TextField m_titleTextField ;
        protected DisplayObject m_ttbg ;
        protected JPanel m_centerHrPanel ;
        protected String m_animateRelative ;
        protected IProgressBar m_levelProgressComponent ;
        private static TooltipFactory s_instance =null ;

        public  TooltipFactory ()
        {
            if (TooltipFactory.s_instance != null)
            {
                throw new Error("Attempting to instantiate more than one TooltipFactory");
            }
            this.initializeResuableComponents();
            return;
        }//end

        private void  initializeResuableComponents ()
        {
            this.m_ttbg =(DisplayObject) new EmbeddedArt.mkt_pop_info();
            DisplayObject _loc_1 =new EmbeddedArt.mkt_rollover_horizontalRule ()as DisplayObject ;
            _loc_1.width = this.m_ttbg.width - 10;
            this.m_hr = new AssetPane(_loc_1);
            this.m_titleText = ASwingHelper.makeMultilineCapsText("", this.m_ttbg.width - 10, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 16, EmbeddedArt.brownTextColor);
            this.m_titleTextField =(TextField) this.m_titleText.getAsset();
            this.m_centerHrPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_hr.setPreferredWidth(this.m_ttbg.width);
            this.m_centerHrPanel.appendAll(ASwingHelper.horizontalStrut(5), this.m_hr);
            return;
        }//end

        public JPanel  createDescriptionTooltip (JPanel param1 ,String param2 ,String param3 ,String param4 =null )
        {
            Debug.debug4("TooltipFactory.createDescriptionTooltip."+param2);
            Object _loc_9 =null ;
            _loc_5 = this.getFilteredTitle(param2 );
            Component _loc_6 =null ;
            Component _loc_7 =null ;
            Object _loc_8 =new Object ();
            _loc_8.type = TooltipType.DESCRIPTION;
            _loc_8.ttbgWidth = this.m_ttbg.width;
            _loc_8.content = param3;
            _loc_6 = this.createTooltip(_loc_8);
            if (param4 != null)
            {
                _loc_9 = new Object();
                _loc_9.type = TooltipType.REQUIREMENT;
                _loc_9.ttbgWidth = this.m_ttbg.width;
                _loc_9.content = param4;
                _loc_7 = this.createTooltip(_loc_9);
            }
            param1.appendAll(_loc_5, this.m_centerHrPanel, _loc_6, _loc_7);
            return param1;
        }//end

        public JPanel  createMasteryTooltip (JPanel param1 ,Item param2 ,int param3 =-1,int param4 =-1,int param5 =0,int param6 =3)
        {
            Object _loc_9 =null ;
            int _loc_10 =0;
            Object _loc_11 =null ;
            Component _loc_7 =null ;
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (Global.player.isEligibleForMastery(param2) && param3 != -1)
            {
                _loc_9 = new Object();
                _loc_9.type = TooltipType.STARS_BG;
                _loc_9.numStars = param3;
                _loc_9.maxStars = param6;
                _loc_7 = this.createTooltip(_loc_9);
                if (param5 > 0)
                {
                    _loc_11 = new Object();
                    _loc_11.type = TooltipType.PROGRESS_BAR;
                    _loc_11.total = param5;
                    _loc_11.progress = param4 != -1 ? (Math.min(param4, param5)) : (-1);
                    _loc_8.append(this.createTooltip(_loc_11));
                    _loc_11.type = TooltipType.PROGRESS_COUNT;
                    _loc_11.bottomString = ZLoc.t("Dialogs", "TT_ProgressHarvests");
                    _loc_8.append(this.createTooltip(_loc_11));
                }
                _loc_10 = param1.getPreferredWidth() / 2 - _loc_7.getPreferredWidth() / 2;
                ASwingHelper.setEasyBorder(_loc_7, 0, _loc_10, 0, _loc_10);
            }
            param1.appendAll(_loc_7, _loc_8);
            ASwingHelper.prepare(param1);
            return param1;
        }//end

        public JPanel  createAttractionPanel (JPanel param1 ,Item param2 )
        {
            CustomerMaintenanceBonus _loc_17 =null ;
            _loc_3 = param2.getMechanicConfigByEventAndName(MechanicManager.PLAY ,"StartTimerHarvestSleepMechanic");
            _loc_4 = _loc_3&& _loc_3.params && _loc_3.params.hasOwnProperty("totalTime") ? (int(_loc_3.params.get("totalTime"))) : (0);
            _loc_5 = _loc_3(&& _loc_3.params && _loc_3.params.hasOwnProperty("totalTime") ? (int(_loc_3.params.get("totalTime"))) : (0)) / (60 * 60 * 24);
            _loc_6 = param2.getMechanicConfigByEventAndName(MechanicManager.PLAY ,"TimerHarvestSleepMechanic");
            _loc_7 = TimerHarvestSleepMechanic.getSleepDuration(_loc_6.params);
            _loc_8 = TimerHarvestSleepMechanic.getSleepDuration(_loc_6.params)/(60*60*24);
            double _loc_9 =0;
            double _loc_10 =0;
            if (param2 && param2.harvestBonuses && param2.harvestBonuses.length > 0)
            {
                _loc_17 =(CustomerMaintenanceBonus) param2.harvestBonuses.get(0);
                if (_loc_17 != null)
                {
                    _loc_9 = _loc_17.minPayout;
                    _loc_10 = _loc_17.maxPercentModifier;
                }
            }
            Object _loc_11 ={type TooltipType.ONE_LINE ,title.t("Dialogs","TT_RunningTime"),firstData.localizeTimeLeft(_loc_5 )};
            _loc_12 = this.createTooltip(_loc_11 );
            Object _loc_13 ={type TooltipType.ONE_LINE ,title.t("Dialogs","TT_MaintenanceTime"),firstData.localizeTimeLeft(_loc_8 )};
            _loc_14 = this.createTooltip(_loc_13 );
            Object _loc_15 ={type TooltipType.ONE_ITEM ,title.t("Dialogs","TT_Earnings"),firstIcon EmbeddedArt.mkt_coinIcon ()as DisplayObject ,firstData.t("Dialogs","TT_MinMax",{min ,max })};
            _loc_16 = this.createTooltip(_loc_15 );
            param1.appendAll(this.m_centerHrPanel, _loc_12, _loc_14, _loc_16);
            return param1;
        }//end

        public JPanel  createNeighborhoodPanel (JPanel param1 ,Item param2 )
        {
            _loc_3 = param2.storables ;
            _loc_4 = param2.name ;
            _loc_5 = Neighborhood.getCapacityForNeighborhood(_loc_4);
            _loc_6 = Neighborhood.getResidenceTypeForNeighborhood(_loc_4);
            _loc_7 = ZLoc.tk("Dialogs","Neighborhood_"+_loc_6 ,"",_loc_5 );
            Object _loc_8 ={type TooltipType.ONE_LINE ,title.t("Dialogs","TT_NeighborhoodCapacityTitle"),firstData.t("Dialogs","TT_NeighborhoodResCount",{count ,pluralizedType })};
            _loc_9 = this.createTooltip(_loc_8 );
            _loc_10 =Global.world.getObjectsByKeywords(_loc_6 ).length ;
            _loc_11 = ZLoc.tk("Dialogs","Neighborhood_"+_loc_6,"",_loc_10);
            Object _loc_12 ={type TooltipType.ONE_LINE ,title.t("Dialogs","TT_NeighborhoodCurrCountTitle"),firstData.t("Dialogs","TT_NeighborhoodResCount",{count ,pluralizedType })};
            _loc_13 = this.createTooltip(_loc_12 );
            param1.appendAll(this.m_centerHrPanel, _loc_9, _loc_13);
            return param1;
        }//end

        public JPanel  createBusinessTooltip (JPanel param1 ,String param2 ,Item param3 ,String param4 =null ,int param5 =-1,int param6 =-1)
        {
            Object _loc_15 =null ;
            double _loc_16 =0;
            Object _loc_17 =null ;
            Object _loc_18 =null ;
            _loc_7 = this.getFilteredTitle(param2 );
            Component _loc_8 =null ;
            Component _loc_9 =null ;
            if (Global.player.isEligibleForBusinessUpgrades() && param5 != -1)
            {
                if (param3.canCountUpgradeActions() || param5 > 1)
                {
                    _loc_15 = new Object();
                    _loc_15.type = TooltipType.STARS;
                    _loc_15.title = ZLoc.t("Dialogs", "TT_Upgrade_Level");
                    _loc_15.numStars = param5;
                    _loc_8 = this.createTooltip(_loc_15);
                }
                if (param3.canCountUpgradeActions())
                {
                    _loc_16 = Number(param3.upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS));
                    _loc_17 = new Object();
                    _loc_17.type = TooltipType.SIMPLE_PROGRESS_BAR;
                    _loc_17.title = ZLoc.t("Dialogs", "TT_Upgrade_Progress");
                    _loc_17.total = _loc_16;
                    _loc_17.progress = param6 != -1 ? (Math.min(param6, _loc_16)) : (-1);
                    _loc_9 = this.createTooltip(_loc_17);
                }
            }
            Component _loc_10 =null ;
            if (param4 != null && param4 != "")
            {
                _loc_18 = new Object();
                _loc_18.type = "oneLine";
                _loc_18.title = ZLoc.t("Dialogs", "TT_Staff");
                _loc_18.firstData = param4;
                _loc_10 = this.createTooltip(_loc_18);
            }
            Object _loc_11 =new Object ();
            _loc_11.type = "oneItem";
            _loc_11.title = ZLoc.t("Dialogs", "TT_Earnings");
            _loc_11.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
            _loc_11.firstData = String(Math.ceil(Global.player.GetDooberMinimums(param3, Doober.DOOBER_COIN) * param3.commodityReq));
            _loc_11.lastLine = ZLoc.t("Dialogs", "TT_perPerson");
            _loc_12 = this.createTooltip(_loc_11 );
            Object _loc_13 =new Object ();
            _loc_13.type = "oneItemwLine";
            _loc_13.title = ZLoc.t("Dialogs", "TT_SupplyReq");
            if (param3.commodityName == Commodities.GOODS_COMMODITY)
            {
                _loc_13.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                _loc_13.firstData = String(param3.commodityReq);
            }
            else if (param3.commodityName == Commodities.PREMIUM_GOODS_COMMODITY)
            {
                _loc_13.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                _loc_13.firstData = String(param3.commodityReq);
            }
            _loc_13.lastLine = ZLoc.t("Dialogs", "TT_perPerson");
            _loc_14 = this.createTooltip(_loc_13 );
            param1.appendAll(_loc_7, this.m_centerHrPanel, _loc_10, _loc_12, _loc_14, _loc_8, _loc_9);
            return param1;
        }//end

        public JPanel  createBrandedBusinessTooltip (Item param1 ,JPanel param2 ,String param3 ,double param4 ,String param5 =null ,String param6 =null ,String param7 =null ,String param8 =null )
        {
            _loc_9 = this.getBrandedTitle(param3 );
            _loc_10 = ASwingHelper.makeLabel(param7 ,EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.lightOrangeTextColor ,JLabel.CENTER );
            _loc_11 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            AssetPane _loc_12 =new AssetPane ();
            _loc_13 = this.createIconPane(param1 ,param5 ,null );
            _loc_14 = ASwingHelper.makeMultilineText(param6 ,100,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,12,EmbeddedArt.brownTextColor );
            _loc_11.appendAll(_loc_13, _loc_14);
            _loc_15 = ASwingHelper.makeMultilineText(param8 ,param4 -10,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,12,EmbeddedArt.brownTextColor );
            ASwingHelper.setEasyBorder(param2, 0, 10);
            param2.appendAll(_loc_9, this.m_centerHrPanel, _loc_10, _loc_11, ASwingHelper.verticalStrut(10), _loc_15);
            return param2;
        }//end

        public JPanel  createShortBusinessTooltip (JPanel param1 ,String param2 ,Item param3 )
        {
            Array _loc_4 =null ;
            Object _loc_5 =null ;
            Component _loc_6 =null ;
            String _loc_7 =null ;
            Object _loc_8 =null ;
            Component _loc_9 =null ;
            if (ItemBonus.keywordExperimentEnabled())
            {
                _loc_4 = param3.getMarketVisibleItemKeywords();
                if (_loc_4.length > 0)
                {
                    _loc_5 = new Object();
                    _loc_5.type = TooltipType.TITLE;
                    _loc_5.title = ZLoc.t("Dialogs", "TT_Bonus");
                    _loc_6 = this.createTooltip(_loc_5);
                    param1.appendAll(_loc_6);
                    for(int i0 = 0; i0 < _loc_4.size(); i0++)
                    {
                    	_loc_7 = _loc_4.get(i0);

                        _loc_8 = {type:"indentedDescriptor", firstData:ZLoc.t("Keywords", _loc_7 + "_friendlyName")};
                        _loc_9 = TooltipFactory.getInstance().createTooltip(_loc_8);
                        param1.appendAll(_loc_9);
                    }
                }
            }
            return param1;
        }//end

        public JPanel  createReceivableXGameGiftTooltip (JPanel param1 ,String param2 ,String param3 )
        {
            _loc_4 = this.getFilteredTitle(param2 );
            Component _loc_5 =null ;
            Object _loc_6 =new Object ();
            _loc_6.type = TooltipType.REQUIREMENT;
            _loc_6.ttbgWidth = this.m_ttbg.width;
            _loc_6.content = param3;
            _loc_5 = this.createTooltip(_loc_6);
            param1.appendAll(_loc_4, this.m_centerHrPanel, _loc_5);
            return param1;
        }//end

        public JPanel  createResidenceTooltip (JPanel param1 ,String param2 ,Item param3 ,String param4 =null ,int param5 =0)
        {
            Object _loc_26 =null ;
            _loc_6 = this.getFilteredTitle(param2 );
            param1.append(_loc_6);
            Component _loc_7 =null ;
            if (param4 != null && param4 != "")
            {
                _loc_26 = new Object();
                _loc_26.type = TooltipType.ONE_ITEM;
                _loc_26.title = ZLoc.t("Dialogs", "TT_Staff");
                _loc_26.firstData = param4;
                _loc_7 = this.createTooltip(_loc_26);
            }
            param1.append(_loc_7);
            Item _loc_8 =null ;
            if (param3.hasRemodel() && param3.isRemodelSkin())
            {
                _loc_8 = param3.getRemodelBase();
            }
            Component _loc_9 =null ;
            double _loc_10 =0;
            _loc_11 =Global.gameSettings().getNumber("populationScale",1);
            String _loc_12 ="TT_PopulationRatio";
            Object _loc_13 =new Object ();
            _loc_14 = PopulationHelper.getItemPopulationIcon(param3);
            _loc_13.type = TooltipType.ONE_ITEM;
            _loc_13.title = ZLoc.t("Dialogs", "TT_Population");
            _loc_13.firstIcon =(DisplayObject) new _loc_14;
            if (_loc_8)
            {
                _loc_10 = Math.round(100 * (param3.populationBase - _loc_8.populationBase) / _loc_8.populationBase);
                _loc_13.fontSize = 12;
                _loc_12 = "TT_PopulationRatioPlus";
            }
            _loc_15 = param3.populationBase;
            if (param5 > 0)
            {
                _loc_15 = param5;
            }
            _loc_13.firstData = ZLoc.t("Dialogs", _loc_12, {maxPop:param3.populationMax * _loc_11, minPop:_loc_15 * _loc_11, bonus:_loc_10});
            _loc_13.firstData = _loc_13.firstData + "   ";
            _loc_9 = TooltipFactory.getInstance().createTooltip(_loc_13);
            param1.append(_loc_9);
            Component _loc_16 =null ;
            Object _loc_17 =new Object ();
            _loc_17.type = TooltipType.TWO_ITEMS;
            _loc_17.title = "";
            _loc_17.firstIcon = null;
            _loc_17.firstData = "";
            _loc_17.lastLine = PopulationHelper.getItemPopulationSubTitle(param3);
            _loc_16 = TooltipFactory.getInstance().createTooltip(_loc_17);
            param1.append(_loc_16);
            Component _loc_18 =null ;
            double _loc_19 =0;
            String _loc_20 ="TT_RentPayout";
            _loc_21 = Global.player.GetDooberMinimums(param3,Doober.DOOBER_COIN);
            double _loc_22 =0;
            Object _loc_23 =new Object ();
            _loc_23.type = TooltipType.TWO_ITEMS;
            _loc_23.title = ZLoc.t("Dialogs", "TT_Rent");
            _loc_23.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
            _loc_23.lastLine = "";
            if (_loc_8)
            {
                _loc_22 = Global.player.GetDooberMinimums(_loc_8, Doober.DOOBER_COIN);
                _loc_21 = Global.player.GetDooberMinimums(param3, Doober.DOOBER_COIN);
                _loc_19 = Math.round(100 * (_loc_21 - _loc_22) / _loc_22);
                _loc_20 = "TT_RentPayoutPlus";
            }
            _loc_23.firstData = ZLoc.t("Dialogs", _loc_20, {payout:_loc_21, bonus:_loc_19});
            _loc_18 = TooltipFactory.getInstance().createTooltip(_loc_23);
            param1.append(_loc_18);
            Component _loc_24 =null ;
            Object _loc_25 =new Object ();
            _loc_25.type = TooltipType.TWO_ITEMS;
            _loc_25.title = "";
            _loc_25.firstIcon = null;
            _loc_25.firstData = "";
            _loc_25.lastLine = CardUtil.localizeTimeLeftLanguage(param3.growTime);
            _loc_24 = TooltipFactory.getInstance().createTooltip(_loc_25);
            param1.append(_loc_24);
            return param1;
        }//end

        public JPanel  createMunicipalTooltip (JPanel param1 ,String param2 ,Item param3 ,String param4 =null )
        {
            _loc_5 = this.getFilteredTitle(param2 );
            param1.append(_loc_5);
            Component _loc_6 =null ;
            Object _loc_7 ={type TooltipType.TWO_ITEMS ,title.t("Dialogs","TT_Allows"),firstIcon EmbeddedArt.mkt_populationIcon_citizen ()as DisplayObject ,lastLine "",.t("Dialogs","TT_PopulationNow",{nowPop.formatNumber(param3.populationCapYield *Global.gameSettings().getNumber("populationScale",10))})};
            _loc_6 = TooltipFactory.getInstance().createTooltip(_loc_7);
            param1.append(_loc_6);
            return param1;
        }//end

        private JPanel  getFilteredTitle (String param1 )
        {
            String _loc_4 =null ;
            JPanel _loc_5 =null ;
            int _loc_2 =16;
            _loc_3 = param1.split(" ");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4.length >= 13)
                {
                    _loc_2 = 12;
                }
            }
            this.m_titleText = ASwingHelper.makeMultilineCapsText(param1, this.m_ttbg.width - 15, EmbeddedArt.titleFont, TextFormatAlign.CENTER, _loc_2, EmbeddedArt.orangeTextColor);
            _loc_5 = ASwingHelper.centerElement(this.m_titleText);
            return _loc_5;
        }//end

        private JPanel  getBrandedTitle (String param1 )
        {
            String _loc_4 =null ;
            JPanel _loc_5 =null ;
            int _loc_2 =16;
            _loc_3 = param1.split(" ");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4.length >= 13)
                {
                    _loc_2 = 12;
                }
            }
            this.m_titleText = ASwingHelper.makeMultilineText(param1, this.m_ttbg.width - 15, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, _loc_2, EmbeddedArt.lightOrangeTextColor);
            _loc_5 = ASwingHelper.centerElement(this.m_titleText);
            return _loc_5;
        }//end

        private JPanel  createTooltipPanel ()
        {
            return ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, 0);
        }//end

        private JPanel  createTitlePanel ()
        {
            return ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
        }//end

        private JPanel  createRowPanel ()
        {
            return ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
        }//end

        public JPanel  initTitleTooltip (JPanel param1 ,String param2 ,int param3 =14,int param4 =130,boolean param5 =false )
        {
            String _loc_7 =null ;
            JPanel _loc_8 =null ;
            param3 = 16;
            _loc_6 = param2.split(" ");
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);

                if (_loc_7.length >= 13)
                {
                    param3 = 12;
                }
            }
            this.m_titleText = ASwingHelper.makeMultilineText(param2, param4, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, param3, EmbeddedArt.blueTextColor, null, param5);
            _loc_8 = ASwingHelper.centerElement(this.m_titleText);
            ASwingHelper.setEasyBorder(_loc_8, 5, 0, 20);
            param1.appendAll(_loc_8);
            return param1;
        }//end

        public Component  createTooltip (Object param1 )
        {
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            JLabel _loc_4 =null ;
            JPanel _loc_5 =null ;
            JPanel _loc_6 =null ;
            JLabel _loc_7 =null ;
            JTextField _loc_8 =null ;
            JTextField _loc_9 =null ;
            Item _loc_10 =null ;
            Array _loc_11 =null ;
            int _loc_12 =0;
            Array _loc_13 =null ;
            int _loc_14 =0;
            boolean _loc_15 =false ;
            Object _loc_16 =null ;
            String _loc_17 =null ;
            Component _loc_18 =null ;
            JPanel _loc_19 =null ;
            int _loc_20 =0;
            JLabel _loc_21 =null ;
            Array _loc_22 =null ;
            int _loc_23 =0;
            int _loc_24 =0;
            String _loc_25 =null ;
            JLabel _loc_26 =null ;
            Array _loc_27 =null ;
            int _loc_28 =0;
            int _loc_29 =0;
            String _loc_30 =null ;
            String _loc_31 =null ;
            int _loc_32 =0;
            AssetPane _loc_33 =null ;
            AssetPane _loc_34 =null ;
            JPanel _loc_35 =null ;
            Sprite _loc_36 =null ;
            int _loc_37 =0;
            JLabel _loc_38 =null ;
            int _loc_39 =0;
            AssetPane _loc_40 =null ;
            Component _loc_41 =null ;
            Object _loc_42 =null ;
            Component _loc_43 =null ;
            Object _loc_44 =null ;
            Component _loc_45 =null ;
            Object _loc_46 =null ;
            Component _loc_47 =null ;
            Array _loc_48 =null ;
            String _loc_49 =null ;
            Object _loc_50 =null ;
            Component _loc_51 =null ;
            JPanel _loc_52 =null ;
            int _loc_53 =0;
            Array _loc_54 =null ;
            String _loc_55 =null ;
            int _loc_56 =0;
            Object _loc_57 =null ;
            Object _loc_58 =null ;
            Component _loc_59 =null ;
            Object _loc_60 =null ;
            Component _loc_61 =null ;
            JPanel _loc_62 =null ;
            String _loc_63 =null ;
            int _loc_64 =0;
            JPanel _loc_65 =null ;
            int _loc_66 =0;
            int _loc_67 =0;
            Array _loc_68 =null ;
            String _loc_69 =null ;
            String _loc_70 =null ;
            JPanel _loc_71 =null ;
            int _loc_72 =0;
            JPanel _loc_73 =null ;
            int _loc_74 =0;
            int _loc_75 =0;
            Array _loc_76 =null ;
            JPanel _loc_77 =null ;
            _loc_10 = param1.dataItem;
            switch(param1.type)
            {
                case TooltipType.ONE_ITEM:
                {
                    _loc_32 = TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]);
                    if (String(param1.firstData).length >= 10 && String(param1.firstData).length < 15)
                    {
                        _loc_32 = 12;
                    }
                    else if (String(param1.firstData).length >= 15)
                    {
                        _loc_32 = 9;
                    }
                    if (param1.hasOwnProperty("fontSize"))
                    {
                        _loc_32 = param1.fontSize;
                    }
                    _loc_8 = ASwingHelper.makeTextField(String(param1.firstData) + "  ", EmbeddedArt.defaultFontNameBold, _loc_32, EmbeddedArt.greenTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), new AssetPane(param1.firstIcon), _loc_8);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.ONE_ITEM_W_LINE:
                {
                    _loc_8 = ASwingHelper.makeTextField(String(param1.firstData), EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), EmbeddedArt.redTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), new AssetPane(param1.firstIcon), _loc_8);
                    _loc_6 = this.createRowPanel();
                    _loc_6.appendAll(ASwingHelper.horizontalStrut(10), _loc_7);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.append(_loc_5);
                    _loc_2.append(_loc_6);
                    break;
                }
                case TooltipType.ONE_LINE:
                {
                    _loc_7 = ASwingHelper.makeLabel(String(param1.firstData), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.brownTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_7);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.TITLE:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    break;
                }
                case TooltipType.INDENTED_DESCRIPTOR:
                {
                    _loc_7 = ASwingHelper.makeLabel("   " + String(param1.firstData), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.lightGrayTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_7);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.TWO_ITEMS:
                {
                    _loc_8 = ASwingHelper.makeTextField(String(param1.firstData), EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), EmbeddedArt.greenTextColor);
                    _loc_7 = ASwingHelper.makeLabel(String(param1.lastLine), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.brownTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), new AssetPane(param1.firstIcon), _loc_8, _loc_7);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.DESCRIPTION:
                {
                    _loc_33 = ASwingHelper.makeMultilineText(String(param1.content), param1.ttbgWidth - 20, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, EmbeddedArt.brownTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_33);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.REQUIREMENT:
                {
                    _loc_34 = ASwingHelper.makeMultilineText(String(param1.content), param1.ttbgWidth - 20, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, EmbeddedArt.blueTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_34, ASwingHelper.horizontalStrut(10), _loc_7);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.SALE:
                {
                    _loc_8 = ASwingHelper.makeTextField(String(param1.firstData), EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), EmbeddedArt.fadedBrownTextColor);
                    _loc_9 = ASwingHelper.makeTextField(String(param1.lastLine), EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), EmbeddedArt.greenTextColor);
                    _loc_35 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 0);
                    _loc_35.appendAll(_loc_8, _loc_9);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(ASwingHelper.horizontalStrut(10), new AssetPane(param1.firstIcon), _loc_35);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.TEXT_SALE_COLOR, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.append(_loc_5);
                    _loc_36 = new Sprite();
                    _loc_36.graphics.beginFill(16711680, 1);
                    _loc_36.graphics.drawRect(0, _loc_8.getHeight() / 2, _loc_8.getWidth() * 4 / 5, 2);
                    _loc_36.graphics.endFill();
                    _loc_5.addChild(_loc_36);
                    _loc_36.x = 42;
                    _loc_36.y = -2;
                    break;
                }
                case TooltipType.CHECKMARK_W_LINE:
                {
                    _loc_37 = param1.hasOwnProperty("firstColor") ? (param1.firstColor) : (EmbeddedArt.redTextColor);
                    _loc_8 = ASwingHelper.makeTextField(String(param1.firstData) + " ", EmbeddedArt.defaultFontNameBold, 11, _loc_37);
                    _loc_5 = this.createRowPanel();
                    if (param1.isComplete)
                    {
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(10), new AssetPane(new EmbeddedArt.mkt_checkmark()));
                    }
                    else
                    {
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(25));
                    }
                    _loc_5.append(_loc_8);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.EMPTY_LOT:
                {
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(this.doEmptyLotToolTip(param1.ttbgWidth));
                    break;
                }
                case TooltipType.SIMPLE_PROGRESS_BAR:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 90, 10, 2, -5);
                        }
                        this.m_levelProgressComponent.setBgAlpha(1);
                        this.m_levelProgressComponent.setBgColor(2450817);
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(3), this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.PROGRESS_BAR:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new ProgressBar(DelayedAssetLoader.MASTERY_ASSETS, 90, 10);
                        }
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(3), this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.SPECIAL_OFFER:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new ProgressBar(DelayedAssetLoader.MASTERY_ASSETS, 90, 10);
                        }
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(3), this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.PROGRESS_COUNT:
                {
                    _loc_38 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "TT_ProgressRatio", {progress:param1.progress, total:param1.total}) + " ", EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.blueTextColor);
                    _loc_7 = ASwingHelper.makeLabel(String(param1.bottomString), EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.blueTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.append(_loc_38);
                    _loc_6 = this.createRowPanel();
                    _loc_6.append(_loc_7);
                    _loc_2 = this.createTooltipPanel();
                    if (param1.title)
                    {
                        _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                        _loc_3 = this.createTitlePanel();
                        _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                        _loc_2.append(_loc_3);
                    }
                    _loc_2.appendAll(_loc_5, ASwingHelper.verticalStrut(-5), _loc_6);
                    break;
                }
                case TooltipType.STARS:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.numStars)
                    {
                        _loc_39 = 0;
                        while (_loc_39 < param1.numStars)
                        {

                            _loc_40 = new AssetPane(new EmbeddedArt.mkt_masteryStar());
                            _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_40);
                            _loc_39++;
                        }
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.STARS_BG:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.numStars != -1 && param1.maxStars != -1)
                    {
                        _loc_41 = new StarRatingComponent(param1.numStars, true, DelayedAssetLoader.MASTERY_ASSETS, param1.maxStars, 10, "cropMastery_hoverStarLarge");
                        _loc_5.append(_loc_41);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.STARTER_PACK:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_42 = new Object();
                    _loc_42.type = TooltipType.ONE_ITEM;
                    _loc_42.title = ZLoc.t("Dialogs", "TT_Energy");
                    _loc_42.firstIcon =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_42.firstData = String(_loc_10.energyRewards);
                    _loc_43 = this.createTooltip(_loc_42);
                    _loc_44 = new Object();
                    _loc_44.type = TooltipType.ONE_ITEM;
                    _loc_44.title = ZLoc.t("Dialogs", "TT_Cash");
                    _loc_44.firstIcon =(DisplayObject) new EmbeddedArt.icon_cash();
                    _loc_44.firstData = String(_loc_10.cashRewards);
                    _loc_45 = this.createTooltip(_loc_44);
                    _loc_46 = new Object();
                    _loc_46.type = TooltipType.ONE_ITEM;
                    _loc_46.title = ZLoc.t("Dialogs", "TT_Coins");
                    _loc_46.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                    _loc_46.firstData = String(_loc_10.coinRewards);
                    _loc_46.lastLine = CardUtil.localizeTimeLeftLanguage(_loc_10.growTime);
                    _loc_47 = this.createTooltip(_loc_46);
                    _loc_48 = _loc_10.itemRewards;
                    _loc_49 = _loc_48.length + " " + ZLoc.t("Items", _loc_48.get(0) + "_friendlyName");
                    _loc_50 = new Object();
                    _loc_50.type = TooltipType.ONE_ITEM;
                    _loc_50.title = ZLoc.t("Dialogs", "TT_Items");
                    _loc_50.firstData = _loc_49;
                    _loc_51 = this.createTooltip(_loc_50);
                    _loc_52 = param1.contentPanel;
                    _loc_52.appendAll(_loc_43, _loc_45, _loc_47, _loc_51);
                    break;
                }
                case TooltipType.PERMIT_PACK:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_53 = Global.player.inventory.getItemCountByName("permits");
                    _loc_54 = _loc_10.itemRewards;
                    _loc_56 = _loc_54.length;
                    _loc_55 = "+" + _loc_56 + " " + ZLoc.t("Items", "permits_friendlyName") + "  ";
                    _loc_57 = {};
                    _loc_57.put("permits",  _loc_53);
                    _loc_58 = new Object();
                    _loc_58.type = TooltipType.INDENTED_DESCRIPTOR;
                    _loc_58.title = "";
                    _loc_58.firstData = ZLoc.t("Dialogs", "PermitToolTipCount", _loc_57);
                    _loc_59 = this.createTooltip(_loc_58);
                    _loc_60 = new Object();
                    _loc_60.type = TooltipType.ONE_ITEM;
                    _loc_60.title = ZLoc.t("Dialogs", "TT_Items");
                    _loc_60.firstData = _loc_55;
                    _loc_61 = this.createTooltip(_loc_60);
                    _loc_62 = param1.contentPanel;
                    _loc_62.appendAll(_loc_59, _loc_61);
                    break;
                }
                case TooltipType.GENERIC_BUNDLE:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_11 = _loc_10.itemRewards;
                    _loc_12 = _loc_11.length;
                    _loc_13 = new Array();
                    _loc_14 = 0;
                    _loc_15 = false;
                    for(int i0 = 0; i0 < _loc_11.size(); i0++)
                    {
                    		_loc_63 = _loc_11.get(i0);

                        _loc_14 = 0;
                        while (_loc_14 < _loc_13.length())
                        {

                            if (_loc_13.get(_loc_14).name == _loc_63)
                            {
                                _loc_15 = true;
                                (_loc_13.get(_loc_14).cnt + 1);
                            }
                            _loc_14++;
                        }
                        if (_loc_15 == false)
                        {
                            _loc_13.put(_loc_14,  new Array());
                            _loc_13.get(_loc_14).get("name") = _loc_63;
                            _loc_13.get(_loc_14).get("cnt") = 1;
                        }
                        _loc_15 = false;
                    }
                    _loc_16 = new Object();
                    _loc_19 = param1.contentPanel;
                    _loc_20 = 0;
                    _loc_20 = 0;
                    while (_loc_20 < _loc_13.length())
                    {

                        _loc_16 = new Object();
                        _loc_16.type = TooltipType.ONE_ITEM;
                        if (_loc_20 == 0)
                        {
                            _loc_16.title = ZLoc.t("Dialogs", "TT_Items");
                        }
                        _loc_16.firstData = "+" + _loc_13.get(_loc_20).get("cnt") + " " + ZLoc.t("Items", _loc_13.get(_loc_20).get("name") + "_friendlyName") + " ";
                        _loc_18 = this.createTooltip(_loc_16);
                        _loc_19.appendAll(_loc_18);
                        _loc_20++;
                    }
                    break;
                }
                case TooltipType.THEMED_BUNDLE:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_22 = param1.dataItem.randomRewards;
                    _loc_23 = 2;
                    _loc_24 = _loc_22.length / 2;
                    _loc_25 = ZLoc.t("Items", "themed_bundle_tooltip_subText");
                    _loc_21 = ASwingHelper.makeLabel(_loc_25, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_21);
                    _loc_64 = 0;
                    while (_loc_64 < _loc_23)
                    {

                        _loc_65 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_66 = 0;
                        while (_loc_66 < _loc_24)
                        {

                            _loc_67 = _loc_24 * _loc_64 + _loc_66;
                            _loc_68 = new Array();
                            _loc_68.push(ZLoc.t("Items", _loc_22.get(_loc_67).item.name + "_friendlyName"));
                            _loc_69 = this.getShortItemInfo(_loc_22.get(_loc_67).item);
                            if (_loc_69 != "Not found")
                            {
                                _loc_68.push(_loc_69);
                            }
                            if (_loc_22.get(_loc_67).item.cash)
                            {
                                _loc_70 = _loc_22.get(_loc_67).item.cash + " " + ZLoc.t("Main", "Cash");
                            }
                            else
                            {
                                _loc_70 = _loc_22.get(_loc_67).item.cost + " " + ZLoc.t("Main", "Coins");
                            }
                            _loc_68.push(ZLoc.t("Dialogs", "Originalcost") + " " + _loc_70);
                            _loc_71 = this.itemCellExtended(_loc_22.get(_loc_67).item, _loc_68, _loc_22.get(_loc_67).item.iconRelative);
                            _loc_65.append(_loc_71);
                            _loc_66++;
                        }
                        if (_loc_72 == (_loc_23 - 1))
                        {
                            ASwingHelper.setEasyBorder(_loc_65, 8, 5, 8, 5);
                        }
                        else
                        {
                            ASwingHelper.setEasyBorder(_loc_65, 8, 5, 0, 5);
                        }
                        _loc_2.append(_loc_65);
                        _loc_64++;
                    }
                    break;
                }
                case TooltipType.MYSTERY_CRATE:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_27 = param1.dataItem.randomRewards;
                    _loc_28 = 2;
                    _loc_29 = _loc_27.length / 2;
                    _loc_30 = ZLoc.t("Items", "mystery_crate_tooltip_subText");
                    _loc_26 = ASwingHelper.makeLabel(_loc_30, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_26);
                    _loc_31 = Global.getAssetURL("assets/dialogs/mystery_crate_anim70x74.png");
                    this.m_animateRelative = "assets/dialogs/mystery_crate_anim70x74.png";
                    if (!Catalog.assetDict.get(this.m_animateRelative))
                    {
                        LoadingManager.load(_loc_31, this.slotCallBack);
                    }
                    _loc_72 = 0;
                    while (_loc_72 < _loc_28)
                    {

                        _loc_73 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_74 = 0;
                        while (_loc_74 < _loc_29)
                        {

                            _loc_75 = _loc_29 * _loc_72 + _loc_74;
                            _loc_76 = new Array();
                            _loc_76.push(ZLoc.t("Items", _loc_27.get(_loc_75).item.name + "_friendlyName"));
                            _loc_76.push(this.getShortItemInfo(_loc_27.get(_loc_75).item));
                            if (_loc_27.get(_loc_75).hasOwnProperty("rarity"))
                            {
                                _loc_76.push(ZLoc.t("Items", "rarity_" + _loc_27.get(_loc_75).rarity));
                            }
                            _loc_77 = this.itemCellExtended(_loc_27.get(_loc_75).item, _loc_76, _loc_27.get(_loc_75).item.iconRelative);
                            _loc_73.append(_loc_77);
                            _loc_74++;
                        }
                        if (_loc_72 == (_loc_28 - 1))
                        {
                            ASwingHelper.setEasyBorder(_loc_73, 8, 5, 8, 5);
                        }
                        else
                        {
                            ASwingHelper.setEasyBorder(_loc_73, 8, 5, 0, 5);
                        }
                        _loc_2.append(_loc_73);
                        _loc_72++;
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_2;
        }//end

        private JPanel  createLargeMarketInfoPanel ()
        {
            return ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -3);
        }//end

        public DisplayObject  createLargeMarketInfoTitle (String param1 )
        {
            Sprite _loc_2 =new Sprite ();
            _loc_3 = ASwingHelper.makeText(param1 ,100,EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor ,"left");
            _loc_3.x = 5;
            _loc_2.addChild(_loc_3);
            return _loc_2;
        }//end

        private Component  createLargeMarketInfoTitleComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoTitle(param1.title));
        }//end

        public DisplayObject  createLargeMarketInfoDescription (String param1 )
        {
            return ASwingHelper.makeText(param1, 100, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.brownTextColor, "left");
        }//end

        private Component  createLargeMarketInfoDescriptionComponent (Object param1 )
        {
            if (param1.ttbgWidth == null || param1.ttbgWidth == 0)
            {
                return new AssetPane();
            }
            return preparedAssetPane(this.createLargeMarketInfoDescription(param1.content));
        }//end

        public TextField  createLargeMarketInfoIndentedDescriptorTextField (String param1 )
        {
            return ASwingHelper.makeText(param1, 90, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.lightGrayTextColor, "left");
        }//end

        public DisplayObject  createLargeMarketInfoIndentedDescriptor (String param1 )
        {
            _loc_2 = this.createLargeMarketInfoIndentedDescriptorTextField(param1 );
            _loc_2.x = 10;
            Sprite _loc_3 =new Sprite ();
            _loc_3.addChild(_loc_2);
            return _loc_3;
        }//end

        private Component  createLargeMarketInfoIndentedDescriptorComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoIndentedDescriptor(param1.firstData));
        }//end

        public DisplayObject  createLargeMarketInfoOneItem (String param1 ,DisplayObject param2 ,String param3 )
        {
            int _loc_5 =0;
            TextField _loc_9 =null ;
            double _loc_10 =0;
            Sprite _loc_4 =new Sprite ();
            double _loc_6 =0;
            if (param1 != null && param1.length > 0)
            {
                _loc_5 = ASwingHelper.shrinkFontSizeToFit(100, param1 + "   ", EmbeddedArt.defaultFontNameBold, 12, null, null, null, 8);
                _loc_9 = ASwingHelper.makeText(param1, 100, EmbeddedArt.defaultFontNameBold, _loc_5, EmbeddedArt.blueTextColor, "left");
                _loc_4.addChild(_loc_9);
                _loc_6 = _loc_9.height;
            }
            _loc_7 = param2!= null ? (param2.width) : (0);
            param3 = param3 + "   ";
            _loc_5 = ASwingHelper.shrinkFontSizeToFit(92 - _loc_7, param3, EmbeddedArt.defaultFontNameBold, 16, null, null, null, 9);
            _loc_8 = ASwingHelper.makeText(param3 ,100-_loc_7 ,EmbeddedArt.defaultFontNameBold ,_loc_5 ,EmbeddedArt.greenTextColor ,"left");
            _loc_8.x = ASwingHelper.makeText(param3, 100 - _loc_7, EmbeddedArt.defaultFontNameBold, _loc_5, EmbeddedArt.greenTextColor, "left").x + _loc_7;
            _loc_4.addChild(_loc_8);
            if (param2 != null)
            {
                _loc_4.addChild(param2);
                _loc_10 = Math.max(_loc_8.height, param2.height);
                param2.y = _loc_6 + (_loc_10 - param2.height) * 0.5;
                _loc_8.y = _loc_6 + (_loc_10 - _loc_8.height) * 0.5;
            }
            else
            {
                _loc_8.y = _loc_6;
            }
            return _loc_4;
        }//end

        private Component  createLargeMarketInfoOneItemComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoOneItem(param1.title, param1.firstIcon, param1.firstData));
        }//end

        public DisplayObject  createLargeMarketInfoOneItemWLine (String param1 ,DisplayObject param2 ,String param3 )
        {
            int _loc_5 =0;
            TextField _loc_9 =null ;
            double _loc_10 =0;
            Sprite _loc_4 =new Sprite ();
            double _loc_6 =0;
            if (param1 != null && param1.length > 0)
            {
                _loc_5 = ASwingHelper.shrinkFontSizeToFit(100, param1 + "   ", EmbeddedArt.defaultFontNameBold, 12, null, null, null, 8);
                _loc_9 = ASwingHelper.makeText(param1, 100, EmbeddedArt.defaultFontNameBold, _loc_5, EmbeddedArt.blueTextColor, "left");
                _loc_4.addChild(_loc_9);
                _loc_6 = _loc_9.height;
            }
            _loc_7 = param2!= null ? (param2.width) : (0);
            _loc_8 = ASwingHelper.makeText(param3 ,100-_loc_7 ,EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.redTextColor ,"left");
            _loc_8.x = ASwingHelper.makeText(param3, 100 - _loc_7, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.redTextColor, "left").x + _loc_7;
            _loc_4.addChild(_loc_8);
            if (param2 != null)
            {
                _loc_4.addChild(param2);
                _loc_10 = Math.max(_loc_8.height, param2.height);
                param2.y = _loc_6 + (_loc_10 - param2.height) * 0.5;
                _loc_8.y = _loc_6 + (_loc_10 - _loc_8.height) * 0.5;
            }
            else
            {
                _loc_8.y = _loc_6;
            }
            return _loc_4;
        }//end

        private Component  createLargeMarketInfoOneItemWLineComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoOneItemWLine(param1.title, param1.firstIcon, param1.firstData));
        }//end

        public DisplayObject  createLargeMarketInfoOneLine (String param1 ,String param2 )
        {
            int _loc_4 =0;
            TextField _loc_7 =null ;
            Sprite _loc_3 =new Sprite ();
            double _loc_5 =0;
            if (param1 != null && param1.length > 0)
            {
                _loc_4 = ASwingHelper.shrinkFontSizeToFit(90, param1 + "   ", EmbeddedArt.defaultFontNameBold, 12, null, null, null, 8);
                _loc_7 = ASwingHelper.makeText(param1, 95, EmbeddedArt.defaultFontNameBold, _loc_4, EmbeddedArt.blueTextColor, "left");
                _loc_3.addChild(_loc_7);
                _loc_5 = _loc_5 + _loc_7.height;
            }
            _loc_4 = ASwingHelper.shrinkFontSizeToFit(95, param2, EmbeddedArt.defaultFontNameBold, 11, null, null, null, 9);
            _loc_6 = ASwingHelper.makeText(param2 ,95,EmbeddedArt.defaultFontNameBold ,_loc_4 ,EmbeddedArt.brownTextColor ,"left");
            _loc_6.y = _loc_5;
            _loc_3.addChild(_loc_6);
            return _loc_3;
        }//end

        private Component  createLargeMarketInfoOneLineComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoOneLine(param1.title, param1.firstData));
        }//end

        public DisplayObject  createLargeMarketInfoTwoItems (String param1 ,DisplayObject param2 ,String param3 ,String param4 )
        {
            TextField _loc_11 =null ;
            double _loc_12 =0;
            Sprite _loc_5 =new Sprite ();
            double _loc_6 =0;
            int _loc_7 =0;
            if (param1 != null && param1.length > 0)
            {
                _loc_11 = ASwingHelper.makeText(param1, 100, EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.blueTextColor, "left");
                _loc_5.addChild(_loc_11);
                _loc_6 = _loc_11.height;
            }
            _loc_8 = param2!= null ? (param2.width) : (0);
            _loc_7 = ASwingHelper.shrinkFontSizeToFit(92 - _loc_8, param3, EmbeddedArt.defaultFontNameBold, 16, null, null, null, 9);
            _loc_9 = ASwingHelper.makeText(param3 ,100-_loc_8 ,EmbeddedArt.defaultFontNameBold ,_loc_7 ,EmbeddedArt.greenTextColor ,"left");
            _loc_9.x = ASwingHelper.makeText(param3, 100 - _loc_8, EmbeddedArt.defaultFontNameBold, _loc_7, EmbeddedArt.greenTextColor, "left").x + _loc_8;
            _loc_5.addChild(_loc_9);
            if (param2 != null)
            {
                _loc_5.addChild(param2);
                _loc_12 = Math.max(_loc_9.height, param2.height);
                param2.y = _loc_6 + (_loc_12 - param2.height) * 0.5;
                _loc_9.y = _loc_6 + (_loc_12 - _loc_9.height) * 0.5;
                _loc_6 = _loc_6 + _loc_12;
            }
            else
            {
                _loc_9.y = _loc_6;
                _loc_6 = _loc_6 + _loc_9.height;
            }
            _loc_7 = ASwingHelper.shrinkFontSizeToFit(100, param4, EmbeddedArt.defaultFontNameBold, 11, null, null, null, 9);
            _loc_10 = ASwingHelper.makeText(param4 ,100,EmbeddedArt.defaultFontNameBold ,_loc_7 ,EmbeddedArt.brownTextColor ,"left");
            _loc_10.y = _loc_6;
            _loc_5.addChild(_loc_10);
            return _loc_5;
        }//end

        private Component  createLargeMarketInfoTwoItemsComponent (Object param1 )
        {
            return preparedAssetPane(this.createLargeMarketInfoTwoItems(param1.title, param1.firstIcon, param1.firstData, param1.lastLine));
        }//end

        public Component  createLargeMarketInfo (Object param1 )
        {
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            JLabel _loc_4 =null ;
            JPanel _loc_5 =null ;
            JPanel _loc_6 =null ;
            JLabel _loc_7 =null ;
            JLabel _loc_8 =null ;
            JTextField _loc_9 =null ;
            AssetPane _loc_12 =null ;
            JPanel _loc_13 =null ;
            Sprite _loc_14 =null ;
            int _loc_15 =0;
            AssetPane _loc_16 =null ;
            JLabel _loc_17 =null ;
            int _loc_18 =0;
            AssetPane _loc_19 =null ;
            Component _loc_20 =null ;
            Object _loc_21 =null ;
            Component _loc_22 =null ;
            Object _loc_23 =null ;
            Component _loc_24 =null ;
            Object _loc_25 =null ;
            Component _loc_26 =null ;
            Array _loc_27 =null ;
            String _loc_28 =null ;
            int _loc_29 =0;
            Object _loc_30 =null ;
            Component _loc_31 =null ;
            JPanel _loc_32 =null ;
            String _loc_33 =null ;
            int _loc_34 =0;
            Array _loc_35 =null ;
            String _loc_36 =null ;
            int _loc_37 =0;
            Object _loc_38 =null ;
            Object _loc_39 =null ;
            Component _loc_40 =null ;
            Object _loc_41 =null ;
            Component _loc_42 =null ;
            JPanel _loc_43 =null ;
            Array _loc_44 =null ;
            int _loc_45 =0;
            Array _loc_46 =null ;
            int _loc_47 =0;
            boolean _loc_48 =false ;
            String _loc_49 =null ;
            Object _loc_50 =null ;
            String _loc_51 =null ;
            Component _loc_52 =null ;
            JPanel _loc_53 =null ;
            Array _loc_54 =null ;
            int _loc_55 =0;
            Array _loc_56 =null ;
            String _loc_57 =null ;
            Object _loc_58 =null ;
            String _loc_59 =null ;
            Component _loc_60 =null ;
            JPanel _loc_61 =null ;
            String _loc_62 =null ;
            int _loc_10 =10;
            _loc_11 = param1.dataItem ;
            switch(param1.type)
            {
                case TooltipType.ONE_ITEM:
                {
                    return this.createLargeMarketInfoOneItemComponent(param1);
                }
                case TooltipType.ONE_ITEM_W_LINE:
                {
                    return this.createLargeMarketInfoOneItemWLineComponent(param1);
                }
                case TooltipType.ONE_LINE:
                {
                    return this.createLargeMarketInfoOneLineComponent(param1);
                }
                case TooltipType.INDENTED_DESCRIPTOR:
                {
                    return this.createLargeMarketInfoIndentedDescriptorComponent(param1);
                }
                case TooltipType.TWO_ITEMS:
                {
                    return this.createLargeMarketInfoTwoItemsComponent(param1);
                }
                case TooltipType.TITLE:
                {
                    return this.createLargeMarketInfoTitleComponent(param1);
                }
                case TooltipType.DESCRIPTION:
                {
                    return this.createLargeMarketInfoDescriptionComponent(param1);
                }
                case TooltipType.REQUIREMENT:
                {
                    _loc_12 = ASwingHelper.makeMultilineText(String(param1.content), param1.ttbgWidth, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, EmbeddedArt.blueTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(_loc_12, ASwingHelper.horizontalStrut(10), _loc_7);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.SALE:
                {
                    _loc_8 = ASwingHelper.makeLabel(String(param1.firstData), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.fadedBrownTextColor);
                    _loc_9 = ASwingHelper.makeTextField(String(param1.lastLine), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.greenTextColor);
                    _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 0);
                    _loc_13.appendAll(_loc_8, _loc_9);
                    _loc_5 = this.createRowPanel();
                    _loc_5.appendAll(new AssetPane(param1.firstIcon), _loc_13);
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, _loc_10, EmbeddedArt.TEXT_SALE_COLOR, JLabel.LEFT);
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(_loc_4);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_3);
                    _loc_5 = this.createRowPanel();
                    _loc_2.append(_loc_5);
                    _loc_14 = new Sprite();
                    _loc_14.graphics.beginFill(16711680, 1);
                    _loc_14.graphics.drawRect(0, _loc_8.getHeight() / 2, _loc_8.getWidth() * 4 / 5, 2);
                    _loc_14.graphics.endFill();
                    _loc_5.addChild(_loc_14);
                    _loc_14.x = 42;
                    _loc_14.y = -2;
                    break;
                }
                case TooltipType.CHECKMARK_W_LINE:
                {
                    _loc_15 = param1.hasOwnProperty("firstColor") ? (param1.firstColor) : (EmbeddedArt.redTextColor);
                    _loc_8 = ASwingHelper.makeLabel(String(param1.firstData) + " ", EmbeddedArt.defaultFontNameBold, 11, _loc_15);
                    _loc_5 = this.createRowPanel();
                    if (param1.isComplete)
                    {
                        _loc_5.appendAll(new AssetPane(new EmbeddedArt.mkt_checkmark()));
                    }
                    else
                    {
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(15));
                    }
                    if (_loc_8.getWidth() > 86)
                    {
                        _loc_16 = ASwingHelper.makeMultilineText(String(param1.firstData), 86, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, _loc_15);
                        _loc_5.appendAll(_loc_16);
                    }
                    else
                    {
                        _loc_5.appendAll(_loc_8);
                    }
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_5);
                    break;
                }
                case TooltipType.EMPTY_LOT:
                {
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(this.doEmptyLotToolTip(param1.ttbgWidth));
                    break;
                }
                case TooltipType.SIMPLE_PROGRESS_BAR:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, _loc_10, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 90, 10, 2, -5);
                        }
                        this.m_levelProgressComponent.setBgAlpha(1);
                        this.m_levelProgressComponent.setBgColor(2450817);
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5 = this.createRowPanel();
                        _loc_5.appendAll(this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(_loc_4);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.SPECIAL_OFFER:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    _loc_5 = this.createRowPanel();
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new ProgressBar(DelayedAssetLoader.MASTERY_ASSETS, 90, 10);
                        }
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5.appendAll(ASwingHelper.horizontalStrut(3), this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(ASwingHelper.horizontalStrut(5), _loc_4);
                    _loc_2 = this.createTooltipPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.PROGRESS_BAR:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, _loc_10, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    if (param1.progress != -1)
                    {
                        if (!this.m_levelProgressComponent)
                        {
                            this.m_levelProgressComponent = new ProgressBar(DelayedAssetLoader.MASTERY_ASSETS, 90, 10);
                        }
                        this.m_levelProgressComponent.setProgressRatio(param1.progress, param1.total);
                        _loc_5 = this.createRowPanel();
                        _loc_5.appendAll(this.m_levelProgressComponent);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(_loc_4);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.PROGRESS_COUNT:
                {
                    _loc_17 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "TT_ProgressRatio", {progress:param1.progress, total:param1.total}) + " ", EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.blueTextColor);
                    _loc_7 = ASwingHelper.makeLabel(String(param1.bottomString), EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.blueTextColor);
                    _loc_5 = this.createRowPanel();
                    _loc_5.append(_loc_17);
                    _loc_6 = this.createRowPanel();
                    _loc_6.append(_loc_7);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    if (param1.title)
                    {
                        _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor, JLabel.LEFT);
                        _loc_3 = this.createTitlePanel();
                        _loc_3.appendAll(_loc_4);
                        _loc_2.append(_loc_3);
                    }
                    _loc_2.appendAll(_loc_5, ASwingHelper.verticalStrut(-5), _loc_6);
                    break;
                }
                case TooltipType.STARS:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, _loc_10, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    if (param1.numStars)
                    {
                        _loc_5 = this.createRowPanel();
                        _loc_18 = 0;
                        while (_loc_18 < param1.numStars)
                        {

                            _loc_19 = new AssetPane(new EmbeddedArt.mkt_masteryStar());
                            _loc_5.appendAll(_loc_19);
                            _loc_18++;
                        }
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(_loc_4);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.STARS_BG:
                {
                    _loc_4 = ASwingHelper.makeLabel(param1.title, EmbeddedArt.defaultFontNameBold, _loc_10, EmbeddedArt.blueTextColor, JLabel.LEFT);
                    if (param1.numStars != -1 && param1.maxStars != -1)
                    {
                        _loc_20 = new StarRatingComponent(param1.numStars, true, DelayedAssetLoader.MASTERY_ASSETS, param1.maxStars, 10, "cropMastery_hoverStarLarge");
                        _loc_5 = this.createRowPanel();
                        _loc_5.append(_loc_20);
                    }
                    _loc_3 = this.createTitlePanel();
                    _loc_3.appendAll(_loc_4);
                    _loc_2 = this.createLargeMarketInfoPanel();
                    _loc_2.append(_loc_3);
                    _loc_2.appendAll(_loc_5);
                    break;
                }
                case TooltipType.STARTER_PACK:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_21 = new Object();
                    _loc_21.type = TooltipType.ONE_ITEM;
                    _loc_21.title = ZLoc.t("Dialogs", "TT_Energy");
                    _loc_21.firstIcon =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_21.firstData = String(_loc_11.energyRewards);
                    _loc_22 = this.createTooltip(_loc_21);
                    _loc_23 = new Object();
                    _loc_23.type = TooltipType.ONE_ITEM;
                    _loc_23.title = ZLoc.t("Dialogs", "TT_Cash");
                    _loc_23.firstIcon =(DisplayObject) new EmbeddedArt.icon_cash();
                    _loc_23.firstData = String(_loc_11.cashRewards);
                    _loc_24 = this.createTooltip(_loc_23);
                    _loc_25 = new Object();
                    _loc_25.type = TooltipType.ONE_ITEM;
                    _loc_25.title = ZLoc.t("Dialogs", "TT_Coins");
                    _loc_25.firstIcon =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                    _loc_25.firstData = String(_loc_11.coinRewards);
                    _loc_25.lastLine = CardUtil.localizeTimeLeftLanguage(_loc_11.growTime);
                    _loc_26 = this.createTooltip(_loc_25);
                    _loc_27 = _loc_11.itemRewards;
                    _loc_28 = "";
                    while (_loc_29 < _loc_27.length())
                    {

                        _loc_33 = _loc_27.get(_loc_29);
                        _loc_28 = _loc_28 + ZLoc.t("Items", _loc_33 + "_friendlyName");
                        if (_loc_29 < (_loc_27.length - 1))
                        {
                            _loc_28 = _loc_28 + ", ";
                        }
                        _loc_29++;
                    }
                    _loc_30 = new Object();
                    _loc_30.type = TooltipType.ONE_ITEM;
                    _loc_30.title = ZLoc.t("Dialogs", "TT_Items");
                    _loc_30.firstData = _loc_28;
                    _loc_31 = this.createTooltip(_loc_30);
                    _loc_32 = param1.contentPanel;
                    _loc_32.appendAll(_loc_22, _loc_24, _loc_26, _loc_31);
                    break;
                }
                case TooltipType.PERMIT_PACK:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_34 = Global.player.inventory.getItemCountByName("permits");
                    _loc_35 = _loc_11.itemRewards;
                    _loc_37 = _loc_35.length;
                    _loc_36 = "+" + _loc_37 + " " + ZLoc.t("Items", "permits_friendlyName") + "  ";
                    _loc_38 = {};
                    _loc_38.put("permits",  _loc_34);
                    _loc_39 = new Object();
                    _loc_39.type = TooltipType.DESCRIPTION;
                    _loc_39.content = ZLoc.t("Dialogs", "PermitToolTipCount", _loc_38);
                    _loc_39.ttbgWidth = 100;
                    _loc_40 = this.createLargeMarketInfo(_loc_39);
                    _loc_41 = new Object();
                    _loc_41.type = TooltipType.ONE_ITEM;
                    _loc_41.title = ZLoc.t("Dialogs", "TT_Items");
                    _loc_41.firstData = _loc_36;
                    _loc_42 = this.createLargeMarketInfo(_loc_41);
                    _loc_43 = param1.contentPanel;
                    _loc_43.appendAll(_loc_40, _loc_42);
                    break;
                }
                case TooltipType.GENERIC_BUNDLE:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_44 = _loc_11.itemRewards;
                    _loc_45 = _loc_44.length;
                    _loc_46 = new Array();
                    _loc_47 = 0;
                    _loc_48 = false;
                    for(int i0 = 0; i0 < _loc_44.size(); i0++)
                    {
                    	_loc_49 = _loc_44.get(i0);

                        _loc_47 = 0;
                        while (_loc_47 < _loc_46.length())
                        {

                            if (_loc_46.get(_loc_47).name == _loc_49)
                            {
                                _loc_48 = true;
                                (_loc_46.get(_loc_47).cnt + 1);
                            }
                            _loc_47++;
                        }
                        if (_loc_48 == false)
                        {
                            _loc_46.put(_loc_47,  new Array());
                            _loc_46.get(_loc_47).get("name") = _loc_49;
                            _loc_46.get(_loc_47).get("cnt") = 1;
                        }
                        _loc_48 = false;
                    }
                    _loc_50 = new Object();
                    _loc_53 = param1.contentPanel;
                    _loc_29 = 0;
                    _loc_29 = 0;
                    while (_loc_29 < _loc_46.length())
                    {

                        _loc_50 = new Object();
                        _loc_50.type = TooltipType.ONE_ITEM;
                        if (_loc_29 == 0)
                        {
                            _loc_50.title = ZLoc.t("Dialogs", "TT_Items");
                        }
                        _loc_50.firstData = "+" + _loc_46.get(_loc_29).get("cnt") + " " + ZLoc.t("Items", _loc_46.get(_loc_29).get("name") + "_friendlyName") + " ";
                        _loc_52 = this.createLargeMarketInfo(_loc_50);
                        _loc_53.appendAll(_loc_52);
                        _loc_29++;
                    }
                    break;
                }
                case TooltipType.THEMED_BUNDLE:
                {
                    if (param1.dataItem == null || param1.contentPanel == null)
                    {
                        return null;
                    }
                    _loc_54 = _loc_11.itemRewards;
                    _loc_55 = _loc_54.length;
                    _loc_56 = new Array();
                    for(int i0 = 0; i0 < _loc_54.size(); i0++)
                    {
                    	_loc_57 = _loc_54.get(i0);

                        _loc_47 = 0;
                        while (_loc_47 < _loc_56.length())
                        {

                            if (_loc_56.get(_loc_47).name == _loc_57)
                            {
                                _loc_48 = true;
                                (_loc_56.get(_loc_47).cnt + 1);
                            }
                            _loc_47++;
                        }
                        if (_loc_48 == false)
                        {
                            _loc_56.put(_loc_47,  new Array());
                            _loc_56.get(_loc_47).get("name") = _loc_57;
                            _loc_56.get(_loc_47).get("cnt") = 1;
                        }
                        _loc_48 = false;
                    }
                    _loc_58 = new Object();
                    _loc_61 = param1.contentPanel;
                    _loc_29 = 0;
                    _loc_29 = 0;
                    while (_loc_29 < _loc_56.length())
                    {

                        _loc_58 = new Object();
                        _loc_58.type = TooltipType.ONE_ITEM;
                        if (_loc_29 == 0)
                        {
                            _loc_58.title = ZLoc.t("Dialogs", "TT_Items");
                        }
                        _loc_58.firstData = "+" + _loc_56.get(_loc_29).get("cnt") + " " + ZLoc.t("Items", _loc_56.get(_loc_29).get("name") + "_friendlyName") + " ";
                        _loc_60 = this.createLargeMarketInfo(_loc_58);
                        _loc_61.appendAll(_loc_60);
                        _loc_29++;
                    }
                    _loc_62 = ZLoc.t("Dialogs", "HugeSavings");
                    _loc_9 = ASwingHelper.makeTextField(_loc_62, EmbeddedArt.defaultFontNameBold, 16, 16730583);
                    _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 0);
                    _loc_13.appendAll(_loc_8, _loc_9);
                    _loc_61.appendAll(_loc_13);
                    break;
                }
                default:
                {
                    return this.createTooltip(param1);
                    break;
                }
            }
            return _loc_2;
        }//end

        public JPanel  createSalePanel (Class param1 ,int param2 ,int param3 ,int param4 )
        {
            AssetPane _loc_5 =new AssetPane(new EmbeddedArt.mkt_saleIcon ()as DisplayObject );
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","TT_Sale"),EmbeddedArt.defaultFontNameBold ,10,EmbeddedArt.TEXT_SALE_COLOR ,JLabel.LEFT );
            _loc_7 = ASwingHelper.makeLabel(param2.toString (),EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.fadedBrownTextColor ,JLabel.LEFT );
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,0);
            _loc_8.appendAll(new AssetPane(new (DisplayObject)param1), _loc_7);
            ASwingHelper.prepare(_loc_8);
            Sprite _loc_9 =new Sprite ();
            _loc_9.graphics.beginFill(16711680, 1);
            _loc_9.graphics.drawRect(0, _loc_8.getHeight() * 0.5 - 1, _loc_8.getWidth(), 2);
            _loc_9.graphics.endFill();
            _loc_8.addChild(_loc_9);
            _loc_10 = ASwingHelper.makeLabel(param3.toString (),EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.greenTextColor ,JLabel.LEFT );
            _loc_11 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","TT_SavePct",{pct param4.toString ()}),EmbeddedArt.defaultFontNameBold ,10,EmbeddedArt.greenTextColor ,JLabel.LEFT );
            _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,0);
            _loc_12.appendAll(new AssetPane(new (DisplayObject)param1), _loc_10, _loc_11);
            _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_13.appendAll(_loc_6, _loc_8, _loc_12);
            _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,0);
            _loc_14.appendAll(_loc_5, _loc_13);
            return _loc_14;
        }//end

        public JPanel  createSpecialsPane (Item param1 )
        {
            _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Market","Specials_special_offer"),EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.TEXT_SALE_COLOR ,JLabel.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,5);
            _loc_3.append(_loc_2);
            ASwingHelper.prepare(_loc_3);
            Object _loc_4 ={};
            _loc_5 = Specials.getInstance().getSpecialDataByName(param1.name);
            _loc_4.put("n",  _loc_5.get("n"));
            _loc_4.put("m",  _loc_5.get("m"));
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Market","Specials_bogo",_loc_4 ),EmbeddedArt.defaultFontName ,12,EmbeddedArt.SUB_HIGHLIGHT_BLUE ,JLabel.CENTER );
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,5);
            _loc_7.append(_loc_6);
            ASwingHelper.prepare(_loc_7);
            _loc_8 = Specials.getInstance().getUserProgressByItemName(param1.name);
            _loc_9 = Specials.getInstance().getUserProgressByItemName(param1.name)+"/"+_loc_5.get("n");
            _loc_10 = ASwingHelper.makeLabel(_loc_9 ,EmbeddedArt.defaultFontName ,10,EmbeddedArt.TEXT_MAIN_COLOR ,JLabel.CENTER );
            ProgressBar _loc_11 =new ProgressBar(DelayedAssetLoader.MASTERY_ASSETS ,90,10);
            _loc_11.setProgressRatio(_loc_8, _loc_5.get("n"));
            _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,0);
            _loc_12.appendAll(_loc_11, _loc_10);
            _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_13.appendAll(_loc_3, _loc_7, _loc_12);
            return _loc_13;
        }//end

        private String  getShortItemInfo (Item param1 )
        {
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            String _loc_2 ="";
            Object _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            switch(param1.type)
            {
                case "decoration":
                {
                    _loc_3 = {amount:param1.bonusPercent};
                    _loc_2 = ZLoc.t("Dialogs", "TT_MC_Payout", _loc_3);
                    break;
                }
                case "municipal":
                case "landmark":
                {
                    _loc_5 = "TT_MC_Allows";
                    _loc_7 = param1.populationCapYield;
                    _loc_3 = {amount:Global.world.citySim.applyPopulationScale(_loc_7)};
                    if (Global.gameSettings().hasMultiplePopulations())
                    {
                        _loc_4 = param1.populationType;
                        _loc_6 = PopulationHelper.getPopulationZlocType(_loc_4, true);
                        _loc_3.popType = _loc_6;
                        _loc_5 = "TT_MC_AllowsType";
                    }
                    _loc_2 = ZLoc.t("Dialogs", _loc_5, _loc_3);
                    break;
                }
                case "business":
                {
                    _loc_8 = param1.commodityReq;
                    _loc_9 = Global.player.GetDooberMinimums(param1, Doober.DOOBER_COIN);
                    _loc_10 = Math.ceil(_loc_9 * _loc_8);
                    _loc_3 = {amount:_loc_10};
                    _loc_2 = ZLoc.t("Dialogs", "TT_MC_Earnings", _loc_3);
                    break;
                }
                case "residence":
                {
                    _loc_5 = "TT_MC_Population";
                    _loc_11 = param1.populationBase;
                    _loc_12 = param1.populationMax;
                    _loc_3 = {base:Global.world.citySim.applyPopulationScale(_loc_11), max:Global.world.citySim.applyPopulationScale(_loc_12)};
                    if (Global.gameSettings().hasMultiplePopulations())
                    {
                        _loc_4 = param1.populationType;
                        _loc_6 = PopulationHelper.getPopulationZlocType(_loc_4, true);
                        _loc_3.popType = _loc_6;
                        _loc_5 = "TT_MC_PopulationType";
                    }
                    _loc_2 = ZLoc.t("Dialogs", _loc_5, _loc_3);
                    break;
                }
                default:
                {
                    _loc_2 = "Not found";
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

        private void  slotCallBack (Event event )
        {
            Bitmap _loc_3 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                Catalog.assetDict.get(this.m_animateRelative) = _loc_3;
            }
            return;
        }//end

        protected JPanel  createIconPane (Item param1 ,String param2 ,DisplayObject param3 )
        {
            AssetPane iconPane ;
            JPanel iconInnerPane ;
            String itemName ;
            Loader iconLoader ;
            double offy ;
            double offx ;
            iconItem = param1;
            iconRelativePath = param2;
            bkgAsset = param3;
            iconPane = new AssetPane();
            iconString = Global.getAssetURL(iconRelativePath);
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            itemName = iconItem.name;
            if (!Catalog.assetDict.get(itemName))
            {
                iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                _loc_3 = null;
                _loc_4 = null;
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param11);
                if (bkgAsset)
                {
                    _loc_3 = Math.abs(bkgAsset.height - iconLoader.content.height) / 2;
                    _loc_4 = Math.abs(bkgAsset.width - iconLoader.content.width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, _loc_3, _loc_4, _loc_3, _loc_4);
                }
                Catalog.assetDict.put(itemName,  iconLoader.content);
                param11.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , iconInnerPane));
            }
            else
            {
                iconPane.setAsset(Catalog.assetDict.get(itemName));
                ASwingHelper.prepare(iconInnerPane);
                if (bkgAsset)
                {
                    offy = Math.abs(bkgAsset.height - Catalog.assetDict.get(itemName).height) / 2;
                    offx = Math.abs(bkgAsset.width - Catalog.assetDict.get(itemName).width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, offy, offx, offy, offx);
                }
            }
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

        protected JPanel  itemCell (Item param1 ,String param2 ,String param3 ,String param4 )
        {
            _loc_5 = Catalog.assetDict.get("card_available_unselected");
            DisplayObject _loc_6 =(DisplayObject)new Catalog.assetDict.get( "card_available_unselected");
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_9 = this.createIconPane(param1 ,param4 ,_loc_6 );
            _loc_10 = ASwingHelper.makeLabel(param2 ,EmbeddedArt.defaultFontNameBold ,11,EmbeddedArt.brownTextColor );
            _loc_11 = ASwingHelper.makeLabel(param3 ,EmbeddedArt.defaultFontNameBold ,11,EmbeddedArt.brownTextColor );
            ASwingHelper.setSizedBackground(_loc_8, _loc_6);
            _loc_8.append(_loc_9);
            _loc_7.appendAll(_loc_8, _loc_10, ASwingHelper.verticalStrut(-2), _loc_11);
            ASwingHelper.setEasyBorder(_loc_7, 0, 9, 0, 9);
            return _loc_7;
        }//end

        protected JPanel  itemCellExtended (Item param1 ,Array param2 ,String param3 )
        {
            String _loc_9 =null ;
            JLabel _loc_10 =null ;
            _loc_4 = Catalog.assetDict.get("card_available_unselected");
            DisplayObject _loc_5 =(DisplayObject)new Catalog.assetDict.get( "card_available_unselected");
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_8 = this.createIconPane(param1 ,param3 ,_loc_5 );
            ASwingHelper.setSizedBackground(_loc_7, _loc_5);
            _loc_7.appendAll(_loc_8);
            _loc_6.append(_loc_7);
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_9 = param2.get(i0);

                _loc_10 = ASwingHelper.makeLabel(_loc_9, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.brownTextColor);
                if (_loc_9 != param2.get((param2.length - 1)))
                {
                    _loc_6.appendAll(_loc_10, ASwingHelper.verticalStrut(-2));
                    continue;
                }
                _loc_6.appendAll(_loc_10);
            }
            ASwingHelper.setEasyBorder(_loc_6, 0, 9, 0, 9);
            return _loc_6;
        }//end

        private JPanel  doEmptyLotToolTip (double param1 )
        {
            AssetPane _loc_9 =null ;
            AssetPane _loc_10 =null ;
            AssetPane _loc_11 =null ;
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,0);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            String _loc_6 ="";
            String _loc_7 ="";
            String _loc_8 ="";
            if (BuyLogic.isAtMaxFranchiseCount())
            {
                _loc_6 = ZLoc.t("Dialogs", "EmptyLot_MaxedOut", {maxnum:BuyLogic.maxFranchiseCount});
                _loc_9 = ASwingHelper.makeMultilineText(_loc_6, param1 - 30, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, EmbeddedArt.redTextColor);
                _loc_3.append(_loc_9);
                _loc_2.appendAll(_loc_3);
                return _loc_2;
            }
            _loc_6 = ZLoc.t("Dialogs", "FranchiseLimit", {numItems:BuyLogic.getFranchisesCount(Global.player.uid), totalLimit:BuyLogic.maxAllowedFranchiseCount});
            _loc_9 = ASwingHelper.makeMultilineText(_loc_6, param1 - 30, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 11, EmbeddedArt.brownTextColor);
            _loc_3.append(_loc_9);
            _loc_2.appendAll(_loc_3);
            return _loc_2;
        }//end

        public static TooltipFactory  getInstance ()
        {
            if (!TooltipFactory.s_instance)
            {
                TooltipFactory.s_instance = new TooltipFactory;
            }
            return s_instance;
        }//end

        private static AssetPane  preparedAssetPane (DisplayObject param1 )
        {
            AssetPane _loc_2 =new AssetPane(param1 );
            ASwingHelper.prepare(_loc_2);
            return _loc_2;
        }//end

    }



