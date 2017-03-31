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
import Display.aswingui.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
//import flash.display.*;
import org.aswing.*;

    public class LargeItemCatalogToolTip extends ItemCatalogToolTip
    {

        public  LargeItemCatalogToolTip ()
        {
            return;
        }//end

         protected String  getTitleFont ()
        {
            return EmbeddedArt.defaultFontNameBold;
        }//end

         protected int  getTitleFontSize ()
        {
            return 16;
        }//end

         protected int  getTextColor ()
        {
            if (m_dataItem.startDate)
            {
                return 16160555;
            }
            if (m_dataItem.isNew)
            {
                return 8938745;
            }
            if (BuyLogic.isLocked(m_dataItem))
            {
                return 5921370;
            }
            return 5155839;
        }//end

         public boolean  showMe ()
        {
            _loc_1 = m_dataItem.getMarketVisibleItemKeywords();
            _loc_2 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (BuyLogic.isLocked(m_dataItem))
            {
                return true;
            }
            if (m_dataItem.type == "starter_pack2_1" || m_dataItem.type == "starter_pack2_2" || m_dataItem.type == "starter_pack2_3" || m_dataItem.type == "starter_pack2_4" || m_dataItem.type == "mystery_crate" || m_dataItem.type == "neighborhood")
            {
                return true;
            }
            if (m_dataItem.type == "business" || m_dataItem.type == "residence")
            {
                if (_loc_1.length > 0)
                {
                    return true;
                }
            }
            if (m_dataItem.type == "plot_contract")
            {
                if (_loc_2 && Global.player.isEligibleForMastery(m_dataItem) || _loc_1.length > 0)
                {
                    return true;
                }
            }
            if (m_dataItem.type == "themed_bundle")
            {
                return true;
            }
            if (m_dataItem.isBrandedBusiness)
            {
                return true;
            }
            if (Global.marketSaleManager.isItemOnSale(m_dataItem))
            {
                return true;
            }
            if (Specials.getInstance().isValidSpecial(m_dataItem.name))
            {
                return true;
            }
            return false;
        }//end

         protected int  getTextWidth ()
        {
            return 200;
        }//end

         protected void  changeBackground ()
        {
            if (m_dataItem.startDate && Catalog.assetDict.hasOwnProperty("market2_leCard"))
            {
                m_ttbg =(DisplayObject) new Catalog.assetDict.get("market2_leCardRO");
            }
            else if (m_dataItem.isNew)
            {
                m_ttbg =(DisplayObject) new Catalog.assetDict.get("market2_newCardRO");
            }
            else if (BuyLogic.isLocked(m_dataItem))
            {
                m_ttbg =(DisplayObject) new Catalog.assetDict.get("market2_lockedCardRO");
            }
            else
            {
                m_ttbg =(DisplayObject) new Catalog.assetDict.get("market2_marketCardRO");
            }
            ASwingHelper.prepare(this);
            m_ttbg.width = Math.max(this.getWidth(), 200);
            this.setPreferredWidth(m_ttbg.width);
            this.setBackgroundDecorator(new AssetBackground(m_ttbg));
            ASwingHelper.prepare(this);
            if (this.getHeight() < 60)
            {
                m_contentPanel.append(ASwingHelper.verticalStrut(15));
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  handleTooltips ()
        {
            double _loc_1 =0;
            String _loc_2 =null ;
            MasteryGoal _loc_3 =null ;
            Component _loc_4 =null ;
            JPanel _loc_5 =null ;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_13 =null ;
            String _loc_14 =null ;
            Item _loc_15 =null ;
            JPanel _loc_16 =null ;
            JPanel _loc_17 =null ;
            JPanel _loc_18 =null ;
            Item _loc_19 =null ;
            int _loc_20 =0;
            int _loc_21 =0;
            int _loc_22 =0;
            int _loc_23 =0;
            Object _loc_24 =null ;
            Array _loc_25 =null ;
            Object _loc_26 =null ;
            Component _loc_27 =null ;
            String _loc_28 =null ;
            Object _loc_29 =null ;
            Component _loc_30 =null ;
            this.setPreferredWidth(originalTTWidth);
            if (BuyLogic.isLocked(m_dataItem))
            {
                super.handleTooltips();
                return;
            }
            switch(m_dataItem.type)
            {
                case "residence":
                case "business":
                {
                    this.remove(m_centerHrPanel);
                    if (m_dataItem.isBrandedBusiness)
                    {
                        m_contentPanel.removeAll();
                        _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                        _loc_6 = {};
                        _loc_6.put("business",  m_dataItem.localizedName);
                        _loc_7 = ZLoc.t("Dialogs", "branded_business_tooltip_title", _loc_6);
                        _loc_8 = ZLoc.t("Dialogs", "branded_business_tooltip_subtitle");
                        _loc_9 = m_dataItem.name + "_tooltip_message_reward";
                        _loc_10 = ZLoc.t("Items", _loc_9);
                        _loc_11 = m_dataItem.name + "_tooltip_message_campaign";
                        _loc_12 = ZLoc.t("Items", _loc_11);
                        _loc_13 = "";
                        _loc_14 = m_dataItem.harvestBasedMasteryRewardItemName;
                        if (_loc_14 != null && _loc_14 != "")
                        {
                            _loc_15 = Global.gameSettings().getItemByName(_loc_14);
                            if (_loc_15 != null)
                            {
                                _loc_13 = _loc_15.getRelativeImageByName("icon");
                            }
                        }
                        _loc_5 = TooltipFactory.getInstance().createBrandedBusinessTooltip(m_dataItem, _loc_5, _loc_7, originalTTWidth, _loc_13, _loc_10, _loc_8, _loc_12);
                        m_contentPanel.append(_loc_5);
                    }
                    else
                    {
                        _loc_16 = getSpecialsPanelForItem();
                        if (_loc_16 != null)
                        {
                            m_contentPanel.appendAll(m_centerHrPanel, GetSalePanelForItem(), _loc_16);
                        }
                        else
                        {
                            m_contentPanel.appendAll(m_centerHrPanel, GetSalePanelForItem());
                        }
                    }
                    break;
                }
                case "plot_contract":
                {
                    _loc_1 = 1;
                    _loc_2 = ZLoc.t("Dialogs", "TT_Makes");
                    _loc_3 =(MasteryGoal) Global.goalManager.getGoal(GoalManager.GOAL_MASTERY);
                    if (_loc_3 && Global.player.isEligibleForMastery(m_dataItem))
                    {
                        _loc_1 = _loc_3.getBonusMultiplier(m_dataItem.name) / 100 + 1;
                        _loc_2 = ZLoc.t("Dialogs", "TT_CurrentlyMakes");
                        _loc_17 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_18 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                        _loc_18.setPreferredWidth(m_contentPanel.getPreferredWidth());
                        _loc_19 = m_dataItem;
                        _loc_20 = _loc_3.getLevel(m_dataItem.name);
                        _loc_21 = _loc_3.getProgressTowardNextLevel(m_dataItem.name);
                        _loc_22 = _loc_3.getNextLevelDiff(m_dataItem.name);
                        _loc_23 = _loc_3.getMaxLevel(m_dataItem.name);
                        _loc_18 = TooltipFactory.getInstance().createMasteryTooltip(_loc_18, _loc_19, _loc_20, _loc_21, _loc_22, _loc_23);
                        _loc_17.append(_loc_18);
                    }
                    if (m_dataItem.name == "plot_lettuce")
                    {
                        _loc_24 = new Object();
                        _loc_24.type = TooltipType.DESCRIPTION;
                        _loc_24.ttbgWidth = m_ttbg.width;
                        _loc_24.content = ZLoc.t("Dialogs", "TT_PotatoRules");
                        _loc_4 = TooltipFactory.getInstance().createTooltip(_loc_24);
                    }
                    m_contentPanel.appendAll(m_centerHrPanel, _loc_17, GetSalePanelForItem(), _loc_4);
                    break;
                }
                default:
                {
                    super.handleTooltips();
                    break;
                    break;
                }
            }
            if (ItemBonus.keywordExperimentEnabled())
            {
                if (m_dataItem.type != "decoration")
                {
                    _loc_25 = m_dataItem.getMarketVisibleItemKeywords();
                    if (_loc_25.length > 0)
                    {
                        _loc_26 = new Object();
                        _loc_26.type = TooltipType.TITLE;
                        _loc_26.title = ZLoc.t("Dialogs", "TT_Bonus");
                        _loc_27 = TooltipFactory.getInstance().createTooltip(_loc_26);
                        m_contentPanel.appendAll(_loc_27);
                        for(int i0 = 0; i0 < _loc_25.size(); i0++)
                        {
                        	_loc_28 = _loc_25.get(i0);

                            _loc_29 = {type:"indentedDescriptor", firstData:ZLoc.t("Keywords", _loc_28 + "_friendlyName")};
                            _loc_30 = TooltipFactory.getInstance().createTooltip(_loc_29);
                            m_contentPanel.appendAll(_loc_30);
                        }
                    }
                }
            }
            return;
        }//end

    }



